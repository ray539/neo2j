package my.learning.cypher

// AST: simplified parse tree
// - a lot of it is just a copy of the parse tree really
sealed trait ASTNode;
case class RelationshipPattern(
    bindVariable: Option[Variable],
    relationshipType: Option[String],
    properties: Map[String, AnyRef],
    leftArrow: Boolean,
    rightArrow: Boolean
) extends ASTNode
case class NodePattern(
    bindVariable: Option[Variable],
    label: Option[String],
    properties: Map[String, AnyRef]
) extends ASTNode

// node, relationship, node, relationship ...
// (noe, relationship)* node
case class Pattern(
    bindVariable: Option[Variable],
    firstNode: NodePattern,
    segments: List[(RelationshipPattern, NodePattern)]
) extends ASTNode

case class Statement(clauses: List[Clause]) extends ASTNode {}

sealed trait Clause extends ASTNode
case class CreateClause(patterns: List[Pattern]) extends Clause
case class MatchClause(pattern: Pattern) extends Clause
case class DeleteClause(variables: List[Variable]) extends Clause
case class WhereClause(expr: Expression) extends Clause

// expressions
//  (1 + 2) / 3 + a + TRUE / FALSE
// - just make it a tree
sealed trait Expression extends ASTNode

sealed trait Operator

case object And extends Operator
case object Or extends Operator
case object Eq extends Operator
case object Neq extends Operator
case object Le extends Operator
case object Ge extends Operator
case object Lt extends Operator
case object Gt extends Operator
case object Add extends Operator
case object Sub extends Operator
case object Mul extends Operator
case object Div extends Operator
case object Mod extends Operator
case object Pow extends Operator
case object Not extends Operator

case object GetAttr extends Operator
case object Index extends Operator

sealed trait DType

// can't extend Int_t, because it is a VALUE
// - a case object is a VALUE
case object Int_t extends DType
case object Str_t extends DType
case object Bool_t extends DType

case object Node_t extends DType
case object Relationship_t extends DType
case object Path_t extends DType
case object Any_t extends DType

case class BinaryExpression(
    left: Expression,
    operator: Operator,
    right: Expression
) extends Expression {
}

// case object Not extends UnaryOperator
// case object Plus extends UnaryOperator
// case object Neg extends UnaryOperator
case class UnaryExpression(operator: Operator, operand: Expression)
    extends Expression {
}

case class IntLiteral(value: Int) extends Expression {
}

case class StringLiteral(value: String) extends Expression {
}

case class BoolLiteral(value: Boolean) extends Expression {
}

case class Variable(name: String) extends Expression {
}
