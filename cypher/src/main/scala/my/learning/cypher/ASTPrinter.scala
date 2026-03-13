package my.learning.cypher

trait ASTVisitor[T] {
  def visitStatement(node: Statement): T
  def visitCreateClause(node: CreateClause): T
  def visitMatchClause(node: MatchClause): T
  def visitDeleteClause(node: DeleteClause): T
  def visitReturnClause(node: ReturnClause): T
  def visitWhereClause(node: WhereClause): T
  def visitPattern(node: Pattern): T
  def visitNodePattern(node: NodePattern): T
  def visitRelationshipPattern(node: RelationshipPattern): T
  def visitBinaryExpression(node: BinaryExpression): T
  def visitUnaryExpression(node: UnaryExpression): T
  def visitVariable(node: Variable): T
  def visitLiteralExpression(node: LiteralExpression): T
  def visitListConstructorCall(node: ListConstructorCall): T
  def visitPathConstructorCall(node: PathConstructorCall): T

}

class ASTPrinter extends ASTVisitor[String] {

  override def visitVariable(node: Variable): String = node.toString()

  override def visitLiteralExpression(node: LiteralExpression): String =
    node.toString()

  override def visitListConstructorCall(node: ListConstructorCall): String = {
    val body = node.values.map(_.accept(this)).map(indent).mkString(",\n")
    s"""ListConstructorCall(
        |  List(
        |$body
        |  )
        |)
    """.stripMargin

  }

  override def visitPathConstructorCall(node: PathConstructorCall): String = ???

  private def indent(s: String): String =
    s.linesIterator.map("  " + _).mkString("\n")

  override def visitStatement(node: Statement): String = {
    val body =
      node.clauses.map(_.accept(this)).map(indent).mkString(",\n")
    s"""Statement(
       |  List(
       |$body
       |  )
       |)""".stripMargin
  }

  override def visitCreateClause(node: CreateClause): String = {
    val pats =
      node.patterns.map(_.accept(this)).map(indent).mkString(",\n")

    s"""CreateClause(
       |  List(
       |$pats
       |  )
       |)""".stripMargin
  }

  override def visitReturnClause(node: ReturnClause): String = {
    val pats =
      node.expressions.map(_.accept(this)).map(indent).mkString(",\n")

    s"""ReturnClause(
       |  List(
       |$pats
       |  )
       |)""".stripMargin
  }

  override def visitPattern(node: Pattern): String = {

    val first = indent(node.firstNode.accept(this))

    val segs =
      node.segments
        .map { (r, n) =>
          val rel = indent(r.accept(this))
          val nd = indent(n.accept(this))

          s"""(
           |$rel,
           |$nd
           |)""".stripMargin
        }
        .map(indent)
        .mkString(",\n")

    s"""Pattern(
       |  ${node.bindVariable},
       |$first,
       |  List(
       |$segs
       |  )
       |)""".stripMargin
  }

  override def visitNodePattern(node: NodePattern): String =
    s"""NodePattern(
       |  ${node.bindVariable},
       |  ${node.label},
       |  ${node.properties}
       |)""".stripMargin

  override def visitRelationshipPattern(node: RelationshipPattern): String =
    s"""RelationshipPattern(
       |  ${node.bindVariable},
       |  ${node.label},
       |  ${node.properties},
       |  ${node.leftArrow},
       |  ${node.rightArrow}
       |)""".stripMargin

  override def visitMatchClause(node: MatchClause): String =
    s"""MatchClause(
       |${indent(node.pattern.accept(this))}
       |)""".stripMargin

  override def visitDeleteClause(node: DeleteClause): String = {
    val vars = node.variables.map(v => s"  $v").mkString(",\n")

    s"""DeleteClause(
       |  List(
       |$vars
       |  )
       |)""".stripMargin
  }



  override def visitWhereClause(node: WhereClause): String =
    s"""WhereClause(
       |${indent(node.expr.accept(this))}
       |)""".stripMargin

  override def visitBinaryExpression(node: BinaryExpression): String =
    s"""BinaryExpression(
       |${node.originalText},
       |${indent(node.left.accept(this))},
       |  ${node.operator},
       |${indent(node.right.accept(this))}
       |)""".stripMargin

  override def visitUnaryExpression(node: UnaryExpression): String =
    s"""UnaryExpression(
       |  ${node.operator},
       |${indent(node.operand.accept(this))}
       |)""".stripMargin
}
