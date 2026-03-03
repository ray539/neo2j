package my.learning.cypher
import scala.collection.mutable


case class QueryResult(tableData: Map[String, List[DVal]]) {
}

trait PlanOperator {}

trait LeafPlanOperator extends PlanOperator{
  def execute(): List[Map[String, DVal]]
}
trait NonLeafPlanOperator extends PlanOperator{
  def execute(rows: List[Map[String, DVal]]): List[Map[String, DVal]]
}


// scan database to get list of all nodes
// produces something like
// (a=<record1>), (a=<record2>), ...
case class AllNodesScan(vname: String) extends LeafPlanOperator {
  override def execute() = {
    return null
  }
}

case class FromRows(rows: List[Map[String, DVal]]) extends LeafPlanOperator {
  override def execute(): List[Map[String, DVal]] = rows
}

case class Id(
  operand: PlanOperator
) extends NonLeafPlanOperator {
  override def execute(rows: List[Map[String, DVal]]): List[Map[String, DVal]] = {
    return rows
  }
}


case class Filter(predicate: (Map[String, DVal]) => Boolean, operand: PlanOperator) extends NonLeafPlanOperator {
  override def execute(rows: List[Map[String, DVal]]): List[Map[String, DVal]] = {
    return null
  }
}

enum RelDir {
  case Forward, Backward, Both
}

case class Expand(node_col_name: String, rel_col_name: String, dir: RelDir, operand: PlanOperator) extends NonLeafPlanOperator {
  override def execute(rows: List[Map[String, DVal]]): List[Map[String, DVal]] = {
    return null
  }
}

// // row.<aname> = 
// case class Assign(aname: String, operand: PlanOperator) {

// }

case class CProduct(left: PlanOperator, right: PlanOperator) extends NonLeafPlanOperator {
  // for row_a in A
  //    for row_b in B
  //       row_res.append(...row_a, ...row_b)
  override def execute(rows: List[Map[String, DVal]]): List[Map[String, DVal]] = {
    return null
  }
}



case class CreateRelationship(label: String, properties: Map[String, DVal], startColName: String, endColName: String, direction: RelDir, colname: String) extends NonLeafPlanOperator {
  override def execute(rows: List[Map[String, DVal]]): List[Map[String, DVal]] = {
    // for each row in rows (a, b, ...)
    //  create an edge between startColName, endColName and name it colname
    return null
  }
}

case class CreateNode(label: String, properties: Map[String, DVal], colname: String) extends NonLeafPlanOperator {
  override def execute(rows: List[Map[String, DVal]]): List[Map[String, DVal]] = {
    // create node with label and properties
    // - for row in rows:
    //    create node 'n' with label and properties, and set row[colname] = 'n'
    return null
  }
}


trait Base {
  var boundVars: mutable.Set[String] = mutable.Set()
  var plan: PlanOperator = FromRows(List())
}

trait MatchClauseBuilder extends Base {

  def visitSegment(leftNodeName: String, segment: (RelationshipPattern, NodePattern)): Unit = {
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
      case _ => RelDir.Both
    
    plan = Expand(
      leftNodeName,
      rname,
      dir,
      plan
    )
    // filter
    def pred(row: Map[String, DVal]): Boolean = {
      val g_rel = row(rname).asInstanceOf[Relationship]
      val b1 = !np.label.isDefined || np.label.get == g_rel.label
      val b2 = np.properties.forall((k, v) => g_rel.properties(k).equals(v))
      b1 && b2
    }
    plan = Filter(
      pred,
      plan
    )
    visitNodePattern(np)
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

    // nonlocal return...
    def pred(row: Map[String, DVal]): Boolean = {
      val gnode = row(colname).asInstanceOf[GraphNode]
      val b1 = !np.label.isDefined || np.label.get == gnode.label
      val b2 = np.properties.forall((k, v) => gnode.properties(k).equals(v))
      b1 && b2
    }

    plan = Filter(
      predicate = pred ,
      plan
    )
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

trait CreateClauseBuilder extends Base {
  def visitSegment(leftNodeName: String, segment: (RelationshipPattern, NodePattern)): Unit = {
    assert(boundVars.contains(leftNodeName))
    val (rp, np) = segment
    val rname = rp.bindVariable.name
    assert(!boundVars.contains(rname))
    val dir = (rp.leftArrow, rp.rightArrow) match
      case (true, false) => RelDir.Backward
      case (false, true) => RelDir.Forward
      case _ => RelDir.Both
    
    val rightNodeName = np.bindVariable.name

    // create next node pattern first
    visitNodePattern(np)
    plan = CreateRelationship(
      label=if rp.relationshipType.isEmpty then "" else rp.relationshipType.get,
      properties=rp.properties,
      startColName = leftNodeName,
      endColName = rightNodeName,
      direction=dir,
      colname= rname
    )
  }

  def visitNodePattern(np: NodePattern) = {
    val colname = np.bindVariable.name
    if boundVars.contains(colname) then {
      assert(np.label.isEmpty && np.properties.isEmpty)
      // do nothing
    } else {
      plan = CreateNode(
        label=if np.label.isEmpty then "" else np.label.get,
        properties=np.properties,
        colname=colname
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
  }
}



trait PlanBuilder {
  def visitClause(clause: Clause) = {
    clause match
      case CreateClause(patterns) => 
      case MatchClause(pattern) =>
      case DeleteClause(variables) =>
      case WhereClause(expr) =>
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
