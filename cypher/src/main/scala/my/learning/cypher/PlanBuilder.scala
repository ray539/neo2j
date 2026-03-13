package my.learning.cypher
import scala.collection.mutable
import java.util.function.BinaryOperator
import scala.annotation.targetName

case class QueryResult(tableData: Map[String, List[Expression]]) {}

// these are stuff that yield iterators over ROWS
trait PlanOperator extends Iterable[Map[String, LiteralExpression]];

// ok, so a row is just going to be
trait LeafPlanOperator extends PlanOperator;
trait NonLeafPlanOperator extends PlanOperator;
// scan database to get list of all nodes
// produces something like
// (a=<record1>), (a=<record2>), ...

case class AllNodesScan(vname: String, ktx: KernelTransaction)
    extends LeafPlanOperator {

  // this will return a iterator overall the nodes in the database named 'vanme'
  // - for example [(a: node1), (a: node2), (a: node3) ..]
  override def iterator: Iterator[Map[String, LiteralExpression]] =
    new Iterator[Map[String, LiteralExpression]] {
      var nCursor: NodeCursor = null;
      var initialized = false
      def _initialize() = {
        if !initialized then {
          nCursor = ktx.getNodeCursor()
          ktx.readApi.allNodesScan(nCursor)
          initialized = true
        }
      }

      override def hasNext: Boolean = {
        _initialize()
        nCursor.hasNext()
      }

      override def next(): Map[String, LiteralExpression] = {
        _initialize()
        val nodeId = nCursor.getNodeReference()
        val nodeRecord = ktx.readApi.nodeById(nodeId)
        Map((vname, nodeRecord))
      }
    }
}

case class EmptyResult() extends LeafPlanOperator {
  override def iterator: Iterator[Map[String, LiteralExpression]] =
    Iterator.empty
}

case class EmptyRow() extends LeafPlanOperator {
  override def iterator: Iterator[Map[String, LiteralExpression]] = List(
    Map()
  ).iterator
}

case class Filter(
    predicate: (Map[String, LiteralExpression]) => Boolean,
    operand: PlanOperator,
    ktx: KernelTransaction,
    details: String = ""
) extends NonLeafPlanOperator {
  override def iterator: Iterator[Map[String, LiteralExpression]] =
    operand.filter(row => predicate(row)).iterator
}

enum RelDir {
  case Forward, Backward, Both
}

case class CProduct(left: PlanOperator, right: PlanOperator)
    extends NonLeafPlanOperator {

  override def iterator: Iterator[Map[String, LiteralExpression]] =
    (for
      row1 <- left;
      row2 <- right
    yield (row1.concat(row2))).iterator
}

case class Expand(
    nodeVname: String,
    relVname: String,
    dir: RelDir,
    operand: PlanOperator,
    ktx: KernelTransaction
) extends NonLeafPlanOperator {
  //   for row in rows
  //      use dir to look at the relevant outgoing / incoming relationships, and add to row under name 'relVname'
  //      add the record under
  // }
  override def iterator: Iterator[Map[String, LiteralExpression]] = {
    def adjRels(nodeId: Int) = new Iterator[RelationshipRecord] {
      var rCursor: RelationshipCursor = ktx.getRelationshipCursor()
      ktx.readApi.relsAdjToNodeScan(rCursor, nodeId, dir)
      override def hasNext: Boolean = rCursor.hasNext()
      override def next(): RelationshipRecord = {
        val rId = rCursor.getRelationshipReference()
        ktx.readApi.relationshipById(rId)
      }
    }
    (for
      row <- operand;
      rRecord <- adjRels(row(nodeVname).asInstanceOf[NodeRecord].id)
    yield row.concat(Map((relVname, rRecord)))).iterator
  }
}

case class Projection(
    targetVname: String,
    expr: (Map[String, LiteralExpression]) => Expression,
    operand: PlanOperator,
    ktx: KernelTransaction
) extends NonLeafPlanOperator {

  override def iterator: Iterator[Map[String, LiteralExpression]] =
    operand
      .map(row =>
        row.concat(Map((targetVname, expr(row).getLiteralValue(row, ktx))))
      )
      .iterator
}

case class CreateRelationship(
    label: String,
    properties: Map[String, Expression],
    startColName: String,
    endColName: String,
    direction: RelDir,
    vname: String,
    operand: PlanOperator,
    ktx: KernelTransaction
) extends NonLeafPlanOperator {

  def _createRelationship(row: Map[String, LiteralExpression]) = {
    // println("_createRelationship")
    // println(row)
    val n1 = row(startColName).asInstanceOf[NodeRecord].id
    val n2 = row(endColName).asInstanceOf[NodeRecord].id

    val props: Map[String, LiteralExpression] =
      properties.map((k, v) => (k, v.getLiteralValue(row, ktx)))

    direction match
      case RelDir.Forward => {
        val newId = ktx.writeApi.relationshipCreate(label, props, n1, n2)
        row.concat(Map((vname, RelationshipRecord(newId, label, props, n1, n2))))
      }
      case RelDir.Backward => {
        val newId = ktx.writeApi.relationshipCreate(label, props, n2, n1)
        row.concat(Map((vname, RelationshipRecord(newId, label, props, n2, n1))))
      }
      case RelDir.Both => throw Exception("can't create relationship with direction both")
  }

  override def iterator: Iterator[Map[String, LiteralExpression]] = operand
    .map(row => {
      _createRelationship(row)
    })
    .iterator
}

case class CreateNode(
    label: String,
    properties: Map[String, Expression],
    vname: String,
    operand: PlanOperator,
    ktx: KernelTransaction
) extends NonLeafPlanOperator {

  // when iterated over a row, it will create the node using that row and return it

  def _createNode(row: Map[String, LiteralExpression]) = {
    val props = properties.map((k, v) => (k, v.getLiteralValue(row, ktx)))
    val newId = ktx.writeApi.nodeCreate(label, props)
    row.concat(Map((vname, NodeRecord(newId, label, props))))
  }

  override def iterator: Iterator[Map[String, LiteralExpression]] = operand
    .map(row => _createNode(row))
    .iterator
}

case class DeleteVariable(
    vname: String,
    operand: PlanOperator,
    ktx: KernelTransaction
) extends NonLeafPlanOperator {

  def _deleteObj(row: Map[String, LiteralExpression]) = {
    val obj = row(vname)
    obj match {
      case n: NodeRecord         => ktx.writeApi.nodeDelete(n.id)
      case r: RelationshipRecord => ktx.writeApi.relationshipDelete(r.id)
      case p: Path               => {
        val nodeIds = p.relationships.value
          .flatMap(rel =>
            List(
              rel.asInstanceOf[RelationshipRecord].startNode,
              rel.asInstanceOf[RelationshipRecord].endNode
            )
          )
          .toSet
        // delete all relationships first, then all nodes
        val relIds = p.relationships.value.map(rel =>
          rel.asInstanceOf[RelationshipRecord].id
        )
        for relId <- relIds do {
          ktx.writeApi.relationshipDelete(relId)
        }
        for nodeId <- nodeIds do {
          ktx.writeApi.nodeDelete(nodeId)
        }
      }
    }
    row
  }
  override def iterator: Iterator[Map[String, LiteralExpression]] = operand
    .map(row => _deleteObj(row))
    .iterator
}

case class ProduceResults(retVnames: Set[String], operand: PlanOperator) extends NonLeafPlanOperator {
  override def iterator: Iterator[Map[String, LiteralExpression]] = operand.map(
    (row) => retVnames.map(vname => (vname, row(vname))).toMap
  ).iterator
}

trait BasePlanBuilder(val ktx: KernelTransaction) {
  // val ktx = _ktx
  var boundVars: mutable.Set[String] = mutable.Set()
  var plan: PlanOperator = EmptyRow()

  var anonId = 0
  def genAnonVarName = {
    val ret = s"_$$anon$anonId"
    anonId += 1
    ret
  }
}

case object PredBuilder {

  def hasAllProperties(
      subject: Expression,
      properties: Map[String, Expression]
  ): Option[Expression] = {
    val pairs = properties.toList
    return smartAnd(pairs.map((p, v) => {
      Some(subject.getAttr(p).exprEq(v))
    }))
  }

  def smartAnd(exprs: List[Option[Expression]]): Option[Expression] = {
    val exprsFiltered = exprs.filter(_.isDefined)
    if exprsFiltered.size == 0 then {
      return None
    }
    var res = exprsFiltered(0).get
    for pi <- exprsFiltered.slice(1, exprsFiltered.size) do {
      res = res && pi.get
    }
    return Some(res)
  }
}

trait MatchClauseBuilder extends BasePlanBuilder {

  def visitMatchSegment(
      leftNodeName: String,
      segment: (RelationshipPattern, NodePattern)
  ): Unit = {
    assert(boundVars.contains(leftNodeName), "matchClauseBuilder: internal error")
    val (rp, np) = segment
    val rname = rp.bindVariable.name
    // if relationship variable is already bound, entire query returns nothing
    if boundVars.contains(rname) then {
      plan = EmptyResult()
      return
    }

    val dir = (rp.leftArrow, rp.rightArrow) match
      case (true, false) => RelDir.Backward
      case (false, true) => RelDir.Forward
      case _             => RelDir.Both

    // expand current rows based on stuff adjacent to 'leftNodeName'

    boundVars.add(rname)
    plan = Expand(
      leftNodeName,
      rname,
      dir,
      plan,
      ktx
    )

    filterBasedOnLabelRelationships(rp, rname)

    // get other node in the relationship that isn't equal to the one bound to 'leftNodeName'
    val getotherNode = (row: Map[String, LiteralExpression]) => {
      val rel = row(rname).asInstanceOf[RelationshipRecord]
      val leftNode = row(leftNodeName).asInstanceOf[NodeRecord]
      if rel.startNode == leftNode.id then {
        ktx.readApi.nodeById(rel.endNode)
      } else {
        assert(rel.endNode == leftNode.id)
        ktx.readApi.nodeById(rel.startNode)
      }
    }

    if boundVars.contains(np.bindVariable.name) then
      val anonVname = genAnonVarName
      // project the right
      boundVars.add(anonVname)
      plan = Projection(
        anonVname,
        getotherNode,
        plan,
        ktx
      )
      plan = Filter(
        (row) =>
          row(anonVname)
            .asInstanceOf[NodeRecord]
            .id == row(np.bindVariable.name).asInstanceOf[NodeRecord].id,
        plan,
        ktx,
        s"row(${anonVname}).asInstanceOf[NodeRecord].id == row(${np.bindVariable.name}).asInstanceOf[NodeRecord].id"
      )
    else
      boundVars.add(np.bindVariable.name)
      plan = Projection(
        np.bindVariable.name,
        getotherNode,
        plan,
        ktx
      )
      // do the Cproduct again to populate other field
      // visitMatchNodePattern(np)
      filterBasedOnLabelRelationships(np, np.bindVariable.name)
  }

  def filterBasedOnLabelRelationships(
      p: HasLabelAndProperties,
      bindVarName: String
  ) = {

    // <bindVarName>.label == p.label
    // - here,
    // - note that
    // NOTE:
    // - dk if we want to make 'label' and 'properties' accessible to user
    val pred1 = if p.label.isDefined then {
      Some(Variable(bindVarName).getLabel().exprEq(p.label.get))
    } else {
      None
    }

    // <bindVarName>.properties has all properties outlined in 'p'
    val pred2 = PredBuilder.hasAllProperties(
      subject = Variable(bindVarName).getProperties(),
      properties = p.properties
    )

    // will be None if both pred1, pred2 are None
    val pred = PredBuilder.smartAnd(List(pred1, pred2))

    if pred.isDefined then {
      // println("generated pred: ")
      // println(pred.get.accept(ASTPrinter()))

      plan = Filter(
        predicate = (row) => pred.get.getLiteralValue(row, ktx).isTruthy,
        plan,
        ktx,
        pred.get.toString()
      )
    }
  }

  def visitMatchNodePattern(np: NodePattern) = {
    val vname = np.bindVariable.name
    if !boundVars.contains(vname) then {
      boundVars.add(vname)
      plan = CProduct(
        plan,
        AllNodesScan(vname, ktx)
      )
    }
    filterBasedOnLabelRelationships(np, vname)
  }

  def visitMatchPattern(p: Pattern) = {
    val pathVname = p.bindVariable.name
    val relationshipVnames: mutable.Set[String] = mutable.Set()
    visitMatchNodePattern(p.firstNode)
    var bname = p.firstNode.bindVariable.name
    for (rp, np) <- p.segments do {
      visitMatchSegment(bname, (rp, np))
      bname = np.bindVariable.name
      relationshipVnames.add(rp.bindVariable.name)
    }

    plan = Projection(
      targetVname = pathVname,
      expr = (_) =>
        PathConstructorCall(
          ListConstructorCall(
            relationshipVnames.toList.map(Variable(_))
          )
        ),
      plan,
      ktx
    )
  }

  def visitMatchClause(matchClause: MatchClause) = {
    val pattern = matchClause.pattern
    visitMatchPattern(pattern)
  }
}

trait CreateClauseBuilder extends BasePlanBuilder {
  def visitCreateSegment(
      leftNodeName: String,
      segment: (RelationshipPattern, NodePattern)
  ): Unit = {
    assert(boundVars.contains(leftNodeName), "createClauseBuilder:internal error")
    val (rp, np) = segment
    val rname = rp.bindVariable.name
    assert(!boundVars.contains(rname), s"variable ${rname} already used")
    val dir = (rp.leftArrow, rp.rightArrow) match
      case (true, false) => RelDir.Backward
      case (false, true) => RelDir.Forward
      case _             => throw Exception("can't create bidirectional relationship")

    val rightNodeName = np.bindVariable.name

    // create next node pattern first
    visitCreateNodePattern(np)

    boundVars.add(rname)
    plan = CreateRelationship(
      label = if rp.label.isEmpty then "" else rp.label.get,
      properties = rp.properties,
      startColName = leftNodeName,
      endColName = rightNodeName,
      direction = dir,
      vname = rname,
      plan,
      ktx
    )
  }

  def visitCreateNodePattern(np: NodePattern) = {
    // println("visitCreateNodePattern")
    val vname = np.bindVariable.name
    if boundVars.contains(vname) then {
      assert(np.label.isEmpty && np.properties.isEmpty, s"variable ${vname} already used and can't be changed")
      // do nothing
    } else {
      boundVars.add(vname)

      // for each incoming row, create a node and do row(vname) = node
      plan = CreateNode(
        label = if np.label.isEmpty then "" else np.label.get,
        properties = np.properties,
        vname = vname,
        plan,
        ktx
      )
    }
  }

  def visitCreatePattern(p: Pattern) = {
    // println("visitCreatePattern")
    val colname = p.bindVariable.name

    visitCreateNodePattern(p.firstNode)
    var bname = p.firstNode.bindVariable.name

    val relationshipVnames: mutable.Set[String] = mutable.Set()
    for (rp, np) <- p.segments do {
      relationshipVnames.add(rp.bindVariable.name)
      visitCreateSegment(bname, (rp, np))
      bname = np.bindVariable.name
    }

    plan = Projection(
      targetVname = colname,
      expr = (_) =>
        PathConstructorCall(
          ListConstructorCall(
            relationshipVnames.toList.map(Variable(_))
          )
        ),
      plan,
      ktx
    )
  }

  def visitCreateClause(createClause: CreateClause) = {
    // println("visitCreateClause")
    val patterns = createClause.patterns
    for p <- patterns do {
      visitCreatePattern(p)
    }
  }
}

trait WhereClauseBuilder extends BasePlanBuilder {

  def visitWhereClause(whereClause: WhereClause) = {
    val expr = whereClause.expr
    plan = Filter(
      predicate = (row) => expr.getLiteralValue(row, ktx).isTruthy,
      plan,
      ktx,
      expr.toString()
    )
  }
}

trait DeleteClauseBuilder extends BasePlanBuilder {

  def visitDeleteClause(deleteClause: DeleteClause) = {
    val varnames = deleteClause.variables.map(_.name)
    for vname <- varnames do {
      plan = DeleteVariable(vname, plan, ktx)
    }
  }
}

trait ReturnClauseBuilder extends BasePlanBuilder {
  def visitReturnClause(returnClause: ReturnClause) = {
    // val expressions = returnClause.expressions.map(_.name).toSet
    for expr <- returnClause.expressions do {
      // project it
      plan = Projection(
        expr.getOriginalText(),
        (row) => expr.getLiteralValue(row, ktx),
        plan,
        ktx
      )
    }

    plan = ProduceResults(
      returnClause.expressions.map((v) => v.getOriginalText()).toSet,
      plan
    )

  }
}

class PlanBuilder(ktx: KernelTransaction)
    extends CreateClauseBuilder
    with MatchClauseBuilder
    with DeleteClauseBuilder
    with WhereClauseBuilder
    with ReturnClauseBuilder
    with BasePlanBuilder(ktx) {

  def visitClause(clause: Clause) = {
    clause match
      case c: CreateClause => visitCreateClause(c)
      case m: MatchClause  => visitMatchClause(m)
      case d: DeleteClause => visitDeleteClause(d)
      case w: WhereClause  => visitWhereClause(w)
      case r: ReturnClause => visitReturnClause(r)
  }
  def getPhysicalPlan(statement: Statement) = {
    val clauses = statement.clauses
    for clause <- clauses do {
      visitClause(clause)
    }

    // if last is not an return, answer is empty row
    clauses.last match
      case r: ReturnClause => {}
      case _ => {
        plan = CProduct(
          plan,
          EmptyResult()
        )
      }
    plan
  }
}

@main
def temp() = {
  var it1 = List(1, 2, 3).iterator
  var it2 = List(4, 5, 6).iterator

  val it3 = (for x <- it1; y <- it2 yield (x, y))
  print(it3.next())
  print(it3.next())
  print(it3.next())
}
