package my.learning.cypher
import scala.collection.mutable
import java.util.function.BinaryOperator
import my.learning.cypher.PredBuilder.getAttr

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
    node_col_name: String,
    rel_col_name: String,
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
    colname: String
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
    colname: String
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
    val (p0, v0) = pairs(0)
    val bes = pairs.map((p, v) => {
      Some(BinaryExpression(
        left = getAttr(subject, p).get,
        operator = Eq,
        right=v
      ))
    })
    return smartAnd(bes)
  }

  def getAttr(subject: Expression, attrName: String) = {
    Some(BinaryExpression(
      left = subject,
      operator=GetAttr,
      right=StringLiteral(attrName)
    ))
  }

  def smartAnd(exprs: List[Option[Expression]]): Option[Expression] = {
    val exprsFiltered = exprs.filter(_.isDefined)
    if exprsFiltered.size == 0 then {
      return None
    }
    var res = exprsFiltered(0).get
    for pi <- exprsFiltered.slice(1, exprsFiltered.size) do {
      res = BinaryExpression(
        res,
        And,
        pi.get
      )
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

    filterBasedOnLabelRelationships(rp, rname)
    visitNodePattern(np)
  }

  
  def filterBasedOnLabelRelationships(p: HasLabelAndProperties, bindVarName: String) = {
    val pred1 = if p.label.isDefined then {
      val label = StringLiteral(p.label.get)
      Some(BinaryExpression(
        left=PredBuilder.getAttr(Variable(bindVarName), "label").get,
        operator=Eq,
        right=label
      ))
    } else {
      None
    }
    val pred2 = PredBuilder.hasAllProperties(
        subject=PredBuilder.getAttr(Variable(bindVarName), "properties").get,
        properties=p.properties
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
    visitNodePattern(p.firstNode)
    var bname = p.firstNode.bindVariable.name
    for (rp, np) <- p.segments do {
      visitSegment(bname, (rp, np))
      bname = np.bindVariable.name
    }
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
      label =
        if rp.label.isEmpty then "" else rp.label.get,
      properties = rp.properties,
      startColName = leftNodeName,
      endColName = rightNodeName,
      direction = dir,
      colname = rname
    )
  }

  def visitNodePattern(np: NodePattern) = {
    val colname = np.bindVariable.name
    if boundVars.contains(colname) then {
      assert(np.label.isEmpty && np.properties.isEmpty)
      // do nothing
    } else {
      plan = CreateNode(
        label = if np.label.isEmpty then "" else np.label.get,
        properties = np.properties,
        colname = colname
      )
    }
    // create the nodes and also cartesian product with the result (if we return it)
  }

  def visitPattern(p: Pattern) = {
    val colname = p.bindVariable.name
    visitNodePattern(p.firstNode)
    var bname = p.firstNode.bindVariable.name
    for (rp, np) <- p.segments do {
      visitSegment(bname, (rp, np))
      bname = np.bindVariable.name
    }
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
