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
      varValues: Map[String, LiteralExpression]
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
case object GetLabel extends Operator
case object GetProperties extends Operator

case class BinaryExpression(
    left: Expression,
    operator: Operator,
    right: Expression
) extends Expression {

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression]
  ): LiteralExpression = {
    val lval = left.getLiteralValue(varValues)
    val rval = right.getLiteralValue(varValues)

    // REFACTORING
    // - make each operator handle the the types instead of a giant pattern match here ...
    (lval, operator, rval) match
      // arithmetic
      case (IntLiteral(x1), Add, IntLiteral(x2)) => IntLiteral(x1 + x2)
      case (IntLiteral(x1), Sub, IntLiteral(x2)) => IntLiteral(x1 - x2)
      case (IntLiteral(x1), Mul, IntLiteral(x2)) => IntLiteral(x1 * x2)
      case (IntLiteral(x1), Div, IntLiteral(x2)) => IntLiteral(x1 / x2)
      case (IntLiteral(x1), Mod, IntLiteral(x2)) => IntLiteral(x1 % x2)
      case (IntLiteral(x1), Pow, IntLiteral(x2)) =>
        IntLiteral(Math.pow(x1, x2).toInt)

      // string concat
      case (StringLiteral(s1), Add, StringLiteral(s2)) =>
        StringLiteral(s1 + s2)

      // comparisons (ints)
      case (IntLiteral(x1), Lt, IntLiteral(x2)) => BoolLiteral(x1 < x2)
      case (IntLiteral(x1), Le, IntLiteral(x2)) => BoolLiteral(x1 <= x2)
      case (IntLiteral(x1), Gt, IntLiteral(x2)) => BoolLiteral(x1 > x2)
      case (IntLiteral(x1), Ge, IntLiteral(x2)) => BoolLiteral(x1 >= x2)

      // equality
      case (v1, Eq, v2)  => BoolLiteral(v1 == v2)
      case (v1, Neq, v2) => BoolLiteral(v1 != v2)

      // boolean logic
      case (BoolLiteral(b1), And, BoolLiteral(b2)) =>
        BoolLiteral(b1 && b2)

      case (BoolLiteral(b1), Or, BoolLiteral(b2)) =>
        BoolLiteral(b1 || b2)

      // attribute lookup
      case (MapLiteral(map), GetAttr, StringLiteral(key)) =>
        map.getOrElse(
          key,
          throw new RuntimeException(s"Key '$key' not found in map")
        )

      // fallback
      case _ =>
        throw new RuntimeException(
          s"Unsupported operation: $lval $operator $rval"
        )
  }
}

// case object Not extends UnaryOperator
// case object Plus extends UnaryOperator
// case object Neg extends UnaryOperator
case class UnaryExpression(operator: Operator, operand: Expression)
    extends Expression {
  override def getLiteralValue(
      varValues: Map[String, LiteralExpression]
  ): LiteralExpression = {
    val cval = operand.getLiteralValue(varValues)

    (operator, cval) match
      case (Add, IntLiteral(x)) =>
        IntLiteral(x)

      case (Sub, IntLiteral(x)) =>
        IntLiteral(-x)

      case (Not, BoolLiteral(b)) =>
        BoolLiteral(!b)

      case (GetLabel, NodeRecord(id, label, properties)) => StringLiteral(label)
      case (GetProperties, NodeRecord(id, label, properties)) => MapLiteral(properties)
      case (GetLabel, RelationshipRecord(id, label, properties, _, _)) => StringLiteral(label)
      case (GetProperties, RelationshipRecord(id, label, properties, _, _)) => MapLiteral(properties)

      case _ =>
        throw new RuntimeException(
          s"Invalid unary operation: $operator on $cval"
        )
  }
}

case class Variable(name: String) extends Expression {

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression]
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
      varValues: Map[String, LiteralExpression]
  ): LiteralExpression = {
    this
  }
}


case class NodeId(id: Int) extends LiteralExpression {
  override def isTruthy: Boolean = true
  override def getLiteralValue(
      varValues: Map[String, LiteralExpression]
  ): LiteralExpression = this
}


case class RelId(id: Int) extends LiteralExpression {
  override def isTruthy: Boolean = true
  override def getLiteralValue(
      varValues: Map[String, LiteralExpression]
  ): LiteralExpression = this
}

case class StringLiteral(value: String) extends LiteralExpression {

  override def isTruthy: Boolean = value.size != 0
  override def getLiteralValue(
      varValues: Map[String, LiteralExpression]
  ): LiteralExpression = {
    this
  }


}

case class BoolLiteral(value: Boolean) extends LiteralExpression {

  override def isTruthy: Boolean = value

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression]
  ): LiteralExpression = {
    this
  }
}

case class MapLiteral(value: Map[String, LiteralExpression])
    extends LiteralExpression {

  override def isTruthy: Boolean = value.size > 0

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression]
  ): LiteralExpression = {
    this
  }
}

// Seq is a trait which covers List, .. and bunch of others
case class ListLiteral(value: List[LiteralExpression])
    extends LiteralExpression {

  override def isTruthy: Boolean = value.size > 0

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression]
  ): LiteralExpression = {
    this
  }
}

// models a constructor CALL which takes in an arbritary amount of arguments
case class ListConstructorCall(values: Seq[Expression]) extends Expression {

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression]
  ): ListLiteral = {
    ListLiteral(values.map(v => v.getLiteralValue(varValues)).toList)
  }
}

case class NodeRecord(
    id: Int,
    label: String,
    properties: Map[String, LiteralExpression]
) extends LiteralExpression {

  override def isTruthy: Boolean = true

  override def getLiteralValue(
      varValues: Map[String, LiteralExpression]
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
      varValues: Map[String, LiteralExpression]
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
      varValues: Map[String, LiteralExpression]
  ): Path = {
    this
  }
}

case class PathConstructorCall(relationships: Expression) extends Expression {
  override def getLiteralValue(
      varValues: Map[String, LiteralExpression]
  ): LiteralExpression = {
    relationships.getLiteralValue(varValues) match {
      case lst: ListLiteral => Path(lst)
      case _                => throw Exception("expected list")
    }
  }
}
