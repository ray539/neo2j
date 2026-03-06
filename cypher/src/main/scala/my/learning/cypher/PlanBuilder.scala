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
    predicate: Expression,
    operand: PlanOperator
) extends NonLeafPlanOperator {
  override def iterator: Iterator[Map[String, LiteralExpression]] =
    operand.filter(row => predicate.getLiteralValue(row).isTruthy).iterator
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
    expr: Expression,
    operand: PlanOperator,
    ktx: KernelTransaction
) extends NonLeafPlanOperator {

  override def iterator: Iterator[Map[String, LiteralExpression]] =
    operand.map(row => row.concat(Map((targetVname, expr.getLiteralValue(row))))).iterator
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
  override def iterator: Iterator[Map[String, LiteralExpression]] =
    new Iterator[Map[String, LiteralExpression]] {
      // this DOESN'T CHANGE the iterator at all, except that the first instace 'next'
      // is called, it will create the relationship

      var _it: Iterator[Map[String, LiteralExpression]] = operand.iterator
      var initialized = false

      def _initialize() = {
        if !initialized then {
          // called once when some 'next' is called
          // - after it creates all the nodes, it just turns into a normal iterator
          for row <- operand do {
            val n1 = row(startColName).asInstanceOf[NodeRecord].id
            val n2 = row(endColName).asInstanceOf[NodeRecord].id

            val props: Map[String, LiteralExpression] =
              properties.map((k, v) => (k, v.getLiteralValue(row)))
            ktx.writeApi.relationshipCreate(label, props, n1, n2)
          }
          initialized = true
        }
      }

      override def hasNext: Boolean = {
        _it.hasNext
      }

      override def next(): Map[String, LiteralExpression] = {
        _initialize()
        _it.next()
      }
    }
}

case class CreateNode(
    label: String,
    properties: Map[String, Expression],
    vname: String,
    operand: PlanOperator,
    ktx: KernelTransaction
) extends NonLeafPlanOperator {
  // this DOESN'T CHANGE the iterator at all, except that the first instace 'next'
  // is called, it will create the node
  // - upon further calls to 'next' it just iterates over 'operand' as normal
  override def iterator: Iterator[Map[String, LiteralExpression]] =
    new Iterator[Map[String, LiteralExpression]] {
      var _it: Iterator[Map[String, LiteralExpression]] = operand.iterator
      var initialized = false

      def _initialize() = {
        if !initialized then {
          // called once when some 'next' is called
          // - after it creates all the nodes, it just turns into a normal iterator
          for row <- operand do {
            val props: Map[String, LiteralExpression] =
              properties.map((k, v) => (k, v.getLiteralValue(row)))
            ktx.writeApi.nodeCreate(label, props)
          }
          initialized = true
        }
      }

      override def hasNext: Boolean = {
        _it.hasNext
      }

      override def next(): Map[String, LiteralExpression] = {
        _initialize()
        _it.next()
      }
    }
}

case class DeleteVariable(
    vname: String,
    operand: PlanOperator,
    ktx: KernelTransaction
) extends NonLeafPlanOperator {

  override def iterator: Iterator[Map[String, LiteralExpression]] =
    new Iterator[Map[String, LiteralExpression]] {
      var _it: Iterator[Map[String, LiteralExpression]] = operand.iterator
      var initialized = false

      def _initialize() = {
        if !initialized then {
          // called once when some 'next' is called
          // - after it creates all the nodes, it just turns into a normal iterato
          val nodeIdsBuf: mutable.Buffer[Int] = mutable.Buffer()
          val relIdsBuf: mutable.Buffer[Int] = mutable.Buffer()
          for row <- operand do {
            val obj = row(vname)
            obj match {
              case n: NodeRecord         => nodeIdsBuf.addOne(n.id)
              case r: RelationshipRecord => relIdsBuf.addOne(r.id)
              case _ => throw Exception("unexpected input to delete operator")
            }
          }
          relIdsBuf.foreach(relId => ktx.writeApi.relationshipDelete(relId))
          nodeIdsBuf.foreach(nodeId => ktx.writeApi.nodeDelete(nodeId))
          initialized = true
        }
      }

      override def hasNext: Boolean = {
        _it.hasNext
      }

      override def next(): Map[String, LiteralExpression] = {
        _initialize()
        _it.next()
      }
    }

}

trait BasePlanBuilder {
  var boundVars: mutable.Set[String] = mutable.Set()
  var plan: PlanOperator = EmptyRow()
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

  val store: StorageEngine = StorageEngine()
  val ktx: KernelTransaction = KernelTransaction(store)

  def visitSegment(
      leftNodeName: String,
      segment: (RelationshipPattern, NodePattern)
  ): Unit = {
    assert(boundVars.contains(leftNodeName))
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
    plan = Expand(
      leftNodeName,
      rname,
      dir,
      plan,
      ktx
    )
    // filter so that it isn't the same as any other visited relationship
    // - in the current line

    filterBasedOnLabelRelationships(rp, rname)

    // not quite right... have to fix this later
    visitNodePattern(np)
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
      plan = Filter(
        predicate = pred.get,
        plan
      )
    }
  }

  def visitNodePattern(np: NodePattern) = {
    val vname = np.bindVariable.name
    if !boundVars.contains(vname) then {
      plan = CProduct(
        plan,
        AllNodesScan(vname, ktx)
      )
      boundVars.add(vname)
    }
    filterBasedOnLabelRelationships(np, vname)
  }

  def visitPattern(p: Pattern) = {
    val vname = p.bindVariable.name
    val relationshipVnames: mutable.Set[String] = mutable.Set()
    visitNodePattern(p.firstNode)
    var bname = p.firstNode.bindVariable.name
    for (rp, np) <- p.segments do {
      visitSegment(bname, (rp, np))
      bname = np.bindVariable.name
      relationshipVnames.add(rp.bindVariable.name)
    }

    plan = Projection(
      targetVname = vname,
      expr = PathConstructorCall(
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
    visitPattern(pattern)
  }
}

trait CreateClauseBuilder extends BasePlanBuilder {
  val store: StorageEngine = StorageEngine()
  val ktx: KernelTransaction = KernelTransaction(store)

  def visitSegment(
      leftNodeName: String,
      segment: (RelationshipPattern, NodePattern)
  ): Unit = {
    assert(boundVars.contains(leftNodeName))
    val (rp, np) = segment
    val rname = rp.bindVariable.name
    assert(!boundVars.contains(rname))
    val dir = (rp.leftArrow, rp.rightArrow) match
      case (true, false) => RelDir.Backward
      case (false, true) => RelDir.Forward
      case _             => RelDir.Both

    val rightNodeName = np.bindVariable.name

    // create next node pattern first
    visitNodePattern(np)
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

  def visitNodePattern(np: NodePattern) = {
    val colname = np.bindVariable.name
    if boundVars.contains(colname) then {
      assert(np.label.isEmpty && np.properties.isEmpty)
      // do nothing
    } else {
      // create the nodes and also cartesian product with the result (if we return it)
      plan = CProduct(
        plan,
        CreateNode(
          label = if np.label.isEmpty then "" else np.label.get,
          properties = np.properties,
          vname = colname,
          plan,
          ktx
        )
      )
    }
  }

  def visitPattern(p: Pattern) = {
    val colname = p.bindVariable.name
    visitNodePattern(p.firstNode)
    var bname = p.firstNode.bindVariable.name

    val relationshipVnames: mutable.Set[String] = mutable.Set()
    for (rp, np) <- p.segments do {
      relationshipVnames.add(rp.bindVariable.name)
      visitSegment(bname, (rp, np))
      bname = np.bindVariable.name
    }

    plan = Projection(
      targetVname = colname,
      expr = PathConstructorCall(
        ListConstructorCall(
          relationshipVnames.toList.map(Variable(_))
        )
      ),
      plan,
      ktx
    )
  }

  def visitCreateClause(createClause: CreateClause) = {
    val patterns = createClause.patterns
    for p <- patterns do {
      visitPattern(p)
    }
  }
}

trait WhereClauseBuilder extends BasePlanBuilder {
  def visitWhereClause(whereClause: WhereClause) = {
    val expr = whereClause.expr
    plan = Filter(
      predicate = expr,
      plan
    )
  }
}

trait DeleteClauseBuilder extends BasePlanBuilder {
  val store: StorageEngine = StorageEngine()
  val ktx: KernelTransaction = KernelTransaction(store)
  def visitDeleteClause(deleteClause: DeleteClause) = {
    val varnames = deleteClause.variables.map(_.name)
    for vname <- varnames do {
      plan = DeleteVariable(vname, plan, ktx)
    }
  }
}

trait PlanBuilder {
  def visitClause(clause: Clause) = {
    clause match
      case CreateClause(patterns)  =>
      case MatchClause(pattern)    =>
      case DeleteClause(variables) =>
      case WhereClause(expr)       =>
  }

  def executeStatement(statement: Statement): Unit = {
    // execution plan
    // - we want to turn the statement into these operators
    // - then, we can evaluate the plan

    // MATCH ...
    // MATCH ...
    // WHERE ...

  }
}

@main
def temp() = {
  var it1 = List(1, 2, 3).iterator
  var it2 = List(4, 5, 6).iterator

  val asd = (for x <- it1; y <- it2 yield (x, y)).map((x, y) => x + y)
}
