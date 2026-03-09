package my.learning.cypher

import scala.compiletime.ops.boolean
import scala.quoted.Expr
import com.ibm.icu.impl.Relation
import scala.math.pow

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

  def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): LiteralExpression

  def +(that: Expression): Expression = {
    BinaryExpression(
      this,
      Add,
      that
    )
  }

  def -(that: Expression): Expression = {
    BinaryExpression(
      this,
      Sub,
      that
    )
  }

  def *(that: Expression): Expression = {
    BinaryExpression(
      this,
      Mul,
      that
    )
  }
  def /(that: Expression): Expression = {
    BinaryExpression(
      this,
      Div,
      that
    )
  }

  def &&(that: Expression): Expression = {
    BinaryExpression(
      this,
      And,
      that
    )
  }

  def ||(that: Expression): Expression = {
    BinaryExpression(
      this,
      Or,
      that
    )
  }

  def !(): Expression = {
    UnaryExpression(
      Sub,
      this
    )
  }

  def getAttr(that: Expression): Expression = {
    BinaryExpression(
      this,
      GetAttr,
      that
    )
  }

  def getAttr(that: String): Expression = {
    BinaryExpression(
      this,
      GetAttr,
      StringLiteral(that)
    )
  }

  def getLabel(): Expression = {
    UnaryExpression(
      GetLabel,
      this
    )
  }

  def getProperties(): Expression = {
    UnaryExpression(
      GetProperties,
      this
    )
  }

  def getStartNode(): Expression = {
    UnaryExpression(
      GetStartNode,
      this
    )
  }

  def getEndNode(): Expression = {
    UnaryExpression(
      GetEndNode,
      this
    )
  }

  def idEq(that: Expression): Expression = {
    BinaryExpression(
      this,
      IdEq,
      that
    )
  }

  def exprEq(that: Expression): Expression = {
    BinaryExpression(
      this,
      Eq,
      that
    )
  }
  def exprEq(that: String): Expression = {
    BinaryExpression(
      this,
      Eq,
      StringLiteral(that)
    )
  }
}

sealed trait Operator {
  def binOp(
      lval: LiteralExpression,
      rval: LiteralExpression
  ): LiteralExpression = throw Exception("not implemented")
  def unOp(operand: LiteralExpression): LiteralExpression = throw Exception("not implemented")
}

case object And extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (BoolLiteral(b1), BoolLiteral(b2)) => BoolLiteral(b1 && b2)
      case _ => throw Exception(s"invalid operands to And: $lval $rval")
  }
}

case object Or extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (BoolLiteral(b1), BoolLiteral(b2)) => BoolLiteral(b1 || b2)
      case _ => throw Exception(s"invalid operands to And: $lval $rval")
  }
}

case object Eq extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (v1, v2) => BoolLiteral(v1.equals(v2))
  }
}

case object Neq extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (v1, v2) => BoolLiteral(!v1.equals(v2))
  }
}

case object Le extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (IntLiteral(x1), IntLiteral(x2)) => BoolLiteral(x1 <= x2)
      case _ => throw Exception(s"invalid operands to And: $lval $rval")
  }
}
case object Ge extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (IntLiteral(x1), IntLiteral(x2)) => BoolLiteral(x1 >= x2)
      case _ => throw Exception(s"invalid operands to And: $lval $rval")
  }
}
case object Lt extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (IntLiteral(x1), IntLiteral(x2)) => BoolLiteral(x1 < x2)
      case _ => throw Exception(s"invalid operands to And: $lval $rval")
  }
}
case object Gt extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (IntLiteral(x1), IntLiteral(x2)) => BoolLiteral(x1 > x2)
      case _ => throw Exception(s"invalid operands to And: $lval $rval")
  }
}
case object Add extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (IntLiteral(x1), IntLiteral(x2))       => IntLiteral(x1 + x2)
      case (StringLiteral(s1), StringLiteral(s2)) => StringLiteral(s1 + s2)
      case _ => throw Exception(s"invalid operands to And: $lval $rval")
  }

  override def unOp(operand: LiteralExpression): LiteralExpression = {
    operand match
      case IntLiteral(x1) => IntLiteral(x1) 
      case _ => throw Exception(s"invalid operands to And: $operand")
  }
}

case object Sub extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (IntLiteral(x1), IntLiteral(x2)) => IntLiteral(x1 - x2)
      case _ => throw Exception(s"invalid operands to And: $lval $rval")
  }

  override def unOp(operand: LiteralExpression): LiteralExpression = {
    operand match
      case IntLiteral(x1) => IntLiteral(-x1) 
      case _ => throw Exception(s"invalid operands to And: $operand")
  }
}
case object Mul extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (IntLiteral(x1), IntLiteral(x2)) => IntLiteral(x1 * x2)
      case _ => throw Exception(s"invalid operands to And: $lval $rval")
  }
}
case object Div extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (IntLiteral(x1), IntLiteral(x2)) => IntLiteral(x1 / x2)
      case _ => throw Exception(s"invalid operands to And: $lval $rval")
  }
}
case object Mod extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (IntLiteral(x1), IntLiteral(x2)) => IntLiteral(x1 % x2)
      case _ => throw Exception(s"invalid operands to And: $lval $rval")
  }
}
case object Pow extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (IntLiteral(x1), IntLiteral(x2)) =>
        IntLiteral(Math.pow(x1, x2).toInt)
      case _ => throw Exception(s"invalid operands to And: $lval $rval")
  }
}
case object Not extends Operator {
  override def unOp(operand: LiteralExpression): LiteralExpression = {
    operand match
      case BoolLiteral(b) => BoolLiteral(!b)
      case _ => throw Exception(s"invalid operands to And: $operand")
  }
}
case object IdEq extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (v1: NodeRecord, v2: NodeRecord) => BoolLiteral(v1.id == v2.id)
      case (v1: RelationshipRecord, v2: RelationshipRecord) =>
        BoolLiteral(v1.id == v2.id)
      case _ => throw Exception(s"invalid operands to And: $lval $rval")
  }
}

case object GetAttr extends Operator {
  override def binOp(lval: LiteralExpression, rval: LiteralExpression) = {
    (lval, rval) match
      case (MapLiteral(map), StringLiteral(key)) =>
        map.getOrElse(
          key,
          throw new RuntimeException(s"Key '$key' not found in map")
        )
      case _ => throw Exception(s"invalid operands to And: $lval $rval")
  }
}
case object Index extends Operator
case object GetLabel extends Operator
case object GetProperties extends Operator
case object GetStartNode extends Operator
case object GetEndNode extends Operator

case class BinaryExpression(
    left: Expression,
    operator: Operator,
    right: Expression
) extends Expression {

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): LiteralExpression = {
    val lval = left.getLiteralValue(varValues, ktx)
    val rval = right.getLiteralValue(varValues, ktx)
    operator.binOp(lval, rval)
  }
}

// case object Not extends UnaryOperator
// case object Plus extends UnaryOperator
// case object Neg extends UnaryOperator
case class UnaryExpression(operator: Operator, operand: Expression)
    extends Expression {
  override def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): LiteralExpression = {
    val cval = operand.getLiteralValue(varValues, ktx)
    operator.unOp(cval)
  }
}

case class Variable(name: String) extends Expression {

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): LiteralExpression = {
    varValues(name)
  }
}

trait LiteralExpression extends Expression {
  def isTruthy: Boolean
}

case class IntLiteral(value: Int) extends LiteralExpression {
  override def isTruthy: Boolean = (value != 0)
  override def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): LiteralExpression = {
    this
  }
}

case class NodeId(id: Int) extends LiteralExpression {
  override def isTruthy: Boolean = true
  override def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): LiteralExpression = this
}

case class RelId(id: Int) extends LiteralExpression {
  override def isTruthy: Boolean = true
  override def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): LiteralExpression = this
}

case class StringLiteral(value: String) extends LiteralExpression {

  override def isTruthy: Boolean = value.size != 0
  override def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): LiteralExpression = {
    this
  }

}

case class BoolLiteral(value: Boolean) extends LiteralExpression {

  override def isTruthy: Boolean = value

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): LiteralExpression = {
    this
  }
}

case class MapLiteral(value: Map[String, LiteralExpression])
    extends LiteralExpression {

  override def isTruthy: Boolean = value.size > 0

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): LiteralExpression = {
    this
  }
}

// Seq is a trait which covers List, .. and bunch of others
case class ListLiteral(value: List[LiteralExpression])
    extends LiteralExpression {

  override def isTruthy: Boolean = value.size > 0

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): LiteralExpression = {
    this
  }
}

// models a constructor CALL which takes in an arbritary amount of arguments
case class ListConstructorCall(values: Seq[Expression]) extends Expression {

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): ListLiteral = {
    ListLiteral(values.map(v => v.getLiteralValue(varValues, ktx)).toList)
  }
}

case class NodeRecord(
    id: Int,
    label: String,
    properties: Map[String, LiteralExpression]
) extends LiteralExpression {

  override def isTruthy: Boolean = true

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): NodeRecord = {
    this
  }
}

case class RelationshipRecord(
    id: Int,
    label: String,
    properties: Map[String, LiteralExpression],
    startNode: Int,
    endNode: Int
) extends LiteralExpression {

  override def isTruthy: Boolean = true

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): RelationshipRecord = {
    this
  }
}

// should be 'list of relationships'
case class Path(relationships: ListLiteral) extends LiteralExpression {

  override def isTruthy: Boolean = true

  for rel <- relationships.value do {
    assert(rel.isInstanceOf[RelationshipRecord])
  }

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): Path = {
    this
  }
}

case class PathConstructorCall(relationships: Expression) extends Expression {
  override def getLiteralValue(
      varValues: Map[String, LiteralExpression],
      ktx: KernelTransaction
  ): LiteralExpression = {
    relationships.getLiteralValue(varValues, ktx) match {
      case lst: ListLiteral => Path(lst)
      case _                => throw Exception("expected list")
    }
  }
}
