package my.learning.cypher
import scala.collection.mutable
import java.util.function.BinaryOperator
import scala.annotation.targetName

case class QueryResult(tableData: Map[String, List[Expression]]) {}

trait PlanOperator {}

trait LeafPlanOperator extends PlanOperator {
  def execute(): List[Map[String, Expression]]
}
trait NonLeafPlanOperator extends PlanOperator {
  def execute(
      rows: List[Map[String, Expression]]
  ): List[Map[String, Expression]]
}

// scan database to get list of all nodes
// produces something like
// (a=<record1>), (a=<record2>), ...
case class AllNodesScan(vname: String) extends LeafPlanOperator {
  override def execute() = {
    return null
  }
}

case class FromRows(rows: List[Map[String, Expression]])
    extends LeafPlanOperator {
  override def execute(): List[Map[String, Expression]] = rows
}

case class Id(
    operand: PlanOperator
) extends NonLeafPlanOperator {
  override def execute(
      rows: List[Map[String, Expression]]
  ): List[Map[String, Expression]] = {
    return rows
  }
}

case class Filter(
    predicate: Expression,
    operand: PlanOperator
) extends NonLeafPlanOperator {
  override def execute(
      rows: List[Map[String, Expression]]
  ): List[Map[String, Expression]] = {
    return null
  }
}

enum RelDir {
  case Forward, Backward, Both
}

case class Expand(
    nodeVname: String,
    relVname: String,
    dir: RelDir,
    operand: PlanOperator
) extends NonLeafPlanOperator {
  override def execute(
      rows: List[Map[String, Expression]]
  ): List[Map[String, Expression]] = {
    // for row in rows
    //   use dir to look at the relevant outgoing / incoming edges, and add to the row under the correct name
    return null
  }
}

case class CProduct(left: PlanOperator, right: PlanOperator)
    extends NonLeafPlanOperator {
  // for row_a in A
  //    for row_b in B
  //       row_res.append(...row_a, ...row_b)
  override def execute(
      rows: List[Map[String, Expression]]
  ): List[Map[String, Expression]] = {
    return null
  }
}

case class CreateRelationship(
    label: String,
    properties: Map[String, Expression],
    startColName: String,
    endColName: String,
    direction: RelDir,
    vname: String
) extends NonLeafPlanOperator {
  override def execute(
      rows: List[Map[String, Expression]]
  ): List[Map[String, Expression]] = {
    // for each row in rows (a, b, ...)
    //  create an edge between startColName, endColName and name it colname
    return null
  }
}

case class CreateNode(
    label: String,
    properties: Map[String, Expression],
    vname: String
) extends NonLeafPlanOperator {
  override def execute(
      rows: List[Map[String, Expression]]
  ): List[Map[String, Expression]] = {
    // create node with label and properties
    // - for row in rows:
    //    create node 'n' with label and properties, and set row[colname] = 'n'
    return null
  }
}

case class DeleteVariable(vname: String, operand: PlanOperator) extends NonLeafPlanOperator {
  override def execute(
      rows: List[Map[String, Expression]]
  ): List[Map[String, Expression]] = {
    // delete variable named 'vname'
    // - if it is a node, delete node
    // - if relationship, delete relationship
    // - if path,

    return null
  }
}

case class Projection(targetVname: String, expr: Expression)
    extends NonLeafPlanOperator {
  override def execute(
      rows: List[Map[String, Expression]]
  ): List[Map[String, Expression]] = {
    // for each row in rows
    //   evaluate expr with variables filled by 'row' and
    return null
  }
}


trait BasePlanBuilder {
  var boundVars: mutable.Set[String] = mutable.Set()
  var plan: PlanOperator = FromRows(List())
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

  def visitSegment(
      leftNodeName: String,
      segment: (RelationshipPattern, NodePattern)
  ): Unit = {
    assert(boundVars.contains(leftNodeName))
    val (rp, np) = segment
    val rname = rp.bindVariable.name
    // if relationship variable is already bound, entire query returns nothing
    if boundVars.contains(rname) then {
      plan = FromRows(List())
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
      plan
    )
    // filter so that it isn't the same as any other visited relationship
    // - in the current line

    filterBasedOnLabelRelationships(rp, rname)
    visitNodePattern(np)
  }

  def filterBasedOnLabelRelationships(
      p: HasLabelAndProperties,
      bindVarName: String
  ) = {

    val pred1 = if p.label.isDefined then {
      Some(Variable(bindVarName).getAttr("label").exprEq(p.label.get))
    } else {
      None
    }

    val pred2 = PredBuilder.hasAllProperties(
      subject = Variable(bindVarName).getAttr("properties"),
      properties = p.properties
    )

    val pred = PredBuilder.smartAnd(List(pred1, pred2))
    if pred.isDefined then {
      plan = Filter(
        predicate = pred.get,
        plan
      )
    }
  }

  def visitNodePattern(np: NodePattern) = {
    val colname = np.bindVariable.name
    if !boundVars.contains(colname) then {
      plan = CProduct(
        plan,
        AllNodesScan(colname)
      )
      boundVars.add(colname)
    }
    filterBasedOnLabelRelationships(np, colname)
  }

  def visitPattern(p: Pattern) = {
    val colname = p.bindVariable.name

    // (a1) - [r1:R] -> (a2) - [r2:R] -> (a3) - [r3:R] -> (a4)
    // - so current context needs to include all relationship variables in the current pattern
    // - this way we can create the Path variable 


    // projection:
    // destColName: String
    // Expr, for example
    //   row -> Path(List[row.a, row.b, row.c ...])


    // case class Projection(destColName: String, expr: <expr involving variables>)
    // for row in rows
    //  - row.destColName = expr.execute(row)
    val relationshipVnames: mutable.Set[String] = mutable.Set()
    visitNodePattern(p.firstNode)
    var bname = p.firstNode.bindVariable.name
    for (rp, np) <- p.segments do {
      visitSegment(bname, (rp, np))
      bname = np.bindVariable.name
      relationshipVnames.add(rp.bindVariable.name)
    }

    plan = Projection(
      targetVname = colname,
      expr = PathConstructor(
        ListConstructor(
          relationshipVnames.toList.map(Variable(_))
        )
      )
    )
  }

  def visitMatchClause(matchClause: MatchClause) = {
    val pattern = matchClause.pattern
    visitPattern(pattern)
  }
}

trait CreateClauseBuilder extends BasePlanBuilder {
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
      vname = rname
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
          vname = colname
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
      expr = PathConstructor(
        ListConstructor(
          relationshipVnames.toList.map(Variable(_))
        )
      )
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
  def visitDeleteClause(deleteClause: DeleteClause) = {
    val varnames = deleteClause.variables.map(_.name)
    for vname <- varnames do {
      plan = DeleteVariable(vname, plan)
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
