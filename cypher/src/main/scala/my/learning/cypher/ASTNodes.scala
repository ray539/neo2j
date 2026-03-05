package my.learning.cypher

import scala.compiletime.ops.boolean
import scala.quoted.Expr

// AST: simplified parse tree
// - a lot of it is just a copy of the parse tree really
sealed trait ASTNode;

trait HasLabelAndProperties {
  def label: Option[String]
  def properties: Map[String, Expression]
}

case class RelationshipPattern(
    bindVariable: Variable,
    label: Option[String],
    properties: Map[String, Expression],
    leftArrow: Boolean,
    rightArrow: Boolean
) extends ASTNode,
      HasLabelAndProperties

case class NodePattern(
    bindVariable: Variable,
    label: Option[String],
    properties: Map[String, Expression]
) extends ASTNode,
      HasLabelAndProperties

// node, relationship, node, relationship ...
// (noe, relationship)* node
case class Pattern(
    bindVariable: Variable,
    firstNode: NodePattern,
    segments: List[(RelationshipPattern, NodePattern)]
) extends ASTNode

case class Statement(clauses: List[Clause]) extends ASTNode {}

sealed trait Clause extends ASTNode
case class CreateClause(patterns: List[Pattern]) extends Clause
case class MatchClause(pattern: Pattern) extends Clause
case class DeleteClause(variables: List[Variable]) extends Clause
case class WhereClause(expr: Expression) extends Clause

// sealed trait DVal
// case class DInt(value: Int)
// case class DStr(value: String)
// case class DBool(value: Boolean)

// expressions
//  (1 + 2) / 3 + a + TRUE / FALSE
// - just make it a tree
sealed trait Expression extends ASTNode {

  def +(that: Expression) = {
    BinaryExpression(
      this,
      Add,
      that
    )
  }

  def -(that: Expression) = {
    BinaryExpression(
      this,
      Sub,
      that
    )
  }

  def *(that: Expression) = {
    BinaryExpression(
      this,
      Mul,
      that
    )
  }
  def /(that: Expression) = {
    BinaryExpression(
      this,
      Div,
      that
    )
  }

  def &&(that: Expression) = {
    BinaryExpression(
      this,
      And,
      that
    )
  }

  def ||(that: Expression) = {
    BinaryExpression(
      this,
      Or,
      that
    )
  }

  def !() = {
    UnaryExpression(
      Sub,
      this
    )
  }

  def getAttr(that: Expression) = {
    BinaryExpression(
      this,
      GetAttr,
      that
    )
  }

  def getAttr(that: String) = {
    BinaryExpression(
      this,
      GetAttr,
      StringLiteral(that)
    )
  }

  def exprEq(that: Expression) = {
    BinaryExpression(
      this,
      Eq,
      that
    )
  }
  def exprEq(that: String) = {
    BinaryExpression(
      this,
      Eq,
      StringLiteral(that)
    )
  }

}

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

case class BinaryExpression(
    left: Expression,
    operator: Operator,
    right: Expression
) extends Expression {}

// case object Not extends UnaryOperator
// case object Plus extends UnaryOperator
// case object Neg extends UnaryOperator
case class UnaryExpression(operator: Operator, operand: Expression)
    extends Expression {}

case class Variable(name: String) extends Expression {}

trait LiteralExpression extends Expression;

case class IntLiteral(value: Int) extends LiteralExpression {}

case class StringLiteral(value: String) extends LiteralExpression {}

case class BoolLiteral(value: Boolean) extends LiteralExpression {}

case class MapLiteral(value: Map[String, LiteralExpression])
    extends LiteralExpression {}

// Seq is a trait which covers List, .. and bunch of others
case class ListLiteral(value: Seq[LiteralExpression])
    extends LiteralExpression {}
// constructor which takes in an arbritary amount of arguments
case class ListConstructor(values: Seq[Expression]) extends Expression {}

case class Relationship(
    label: StringLiteral,
    start: GraphNode,
    end: GraphNode,
    properties: MapLiteral
) extends LiteralExpression {}

case class GraphNode(
    id: StringLiteral,
    label: StringLiteral,
    properties: MapLiteral
) extends LiteralExpression {}
//   var outgoing: List[Relationship] = List()
//   var incoming: List[Relationship] = List()}

// should be 'list of relationships'
case class Path(relationships: ListLiteral) extends LiteralExpression {}

case class PathConstructor(relationships: Expression)
    extends Expression {}
