package my.learning.cypher

object PlanPrinter {

  def print(plan: PlanOperator): String =
    visit(plan, 0)

  private def indent(level: Int): String =
    "  " * level

  private def visit(plan: PlanOperator, level: Int): String = {
    val pad = indent(level)

    plan match {

      case AllNodesScan(vname, _) =>
        s"${pad}AllNodesScan($vname)\n"

      case EmptyResult() =>
        s"${pad}EmptyResult\n"

      case EmptyRow() =>
        s"${pad}EmptyRow\n"

      case Filter(_, operand, _, details) =>
        s"${pad}Filter(${details})\n" +
        visit(operand, level + 1)

      case CProduct(left, right) =>
        s"${pad}CProduct\n" +
        visit(left, level + 1) +
        visit(right, level + 1)

      case Expand(nodeVname, relVname, dir, operand, _) =>
        s"${pad}Expand(node=$nodeVname rel=$relVname dir=$dir)\n" +
        visit(operand, level + 1)

      case Projection(targetVname, _, operand, _) =>
        s"${pad}Projection(target=$targetVname)\n" +
        visit(operand, level + 1)

      case CreateRelationship(label, _, start, end, dir, vname, operand, _) =>
        s"${pad}CreateRelationship(label=$label start=$start end=$end dir=$dir v=$vname)\n" +
        visit(operand, level + 1)

      case CreateNode(label, _, vname, operand, _) =>
        s"${pad}CreateNode(label=$label v=$vname)\n" +
        visit(operand, level + 1)

      case DeleteVariable(vname, operand, _) =>
        s"${pad}DeleteVariable($vname)\n" +
        visit(operand, level + 1)
      
      case ProduceResults(retVnames, operand) => 
        s"${pad}ProduceResults(${retVnames})\n" +
        visit(operand, level + 1)
    }
  }
}
