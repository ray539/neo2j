package my.learning.cypher


case class TypeError(msg: String)
// given something like
//         +
//       +   2
//    2    2
// will crush it to be '4'
// but, if any tree has a 'variable', then it will just fail immediately
// - I

class ASTTypeChecker(vartype: Map[String, DType]) {
  // MATCH (n: )
  // focus: check whether the types are correct
  def checkBinaryExpression(binaryExpression: BinaryExpression): Either[DType, TypeError] = {
    val ltype = checkExpression(binaryExpression.left)
    val rtype = checkExpression(binaryExpression.right)
    val op = binaryExpression.operator

    // when you index into something, like for example n.Node, type becomes 'Any'
    (ltype, op, rtype) match
      case (Int_t, Add | Sub | Mul | Div | Mod | Pow, Int_t) =>  Left(Int_t)
      case (_, Eq | Neq | Le | Ge | Lt | Gt, _) => Left(Bool_t)
      case (Bool_t, And | Or, Bool_t) => Left(Bool_t)
      case (Str_t, Add, Str_t) => Left(Str_t)
      case (Node_t | Relationship_t | Path_t, GetAttr, Str_t) => Left(Any_t)
      case (Any_t, _, _) => Left(Any_t)
      case (_, _, Any_t) => Left(Any_t)
      case _ => Right(TypeError("type error"))
  }

  def checkUnaryExpression(unaryExpression: UnaryExpression): Either[DType, TypeError] = {
    val optype = checkExpression(unaryExpression.operand)
    val op = unaryExpression.operator
    (op, optype) match
      case (Add | Sub, Int_t) => Left(Int_t)
      case (Not, Bool_t) => Left(Bool_t)
      case _ => Right(TypeError("type error"))
  }

  // ok, need to get the type of each variable and stuff
  def checkExpression(expression: Expression): Either[DType, TypeError] =
    expression match
      case b: BinaryExpression => checkBinaryExpression(b)
      case u: UnaryExpression => checkUnaryExpression(u)
      case i: IntLiteral => Left( checkIntLiteral(i))
      case s: StringLiteral => Left( checkStrLiteral(s))
      case b:BoolLiteral => Left( checkBoolLiteral(b))
      case v: Variable => Left(checkVariable(v))

  def checkIntLiteral(intLiteral: IntLiteral) = Int_t
  def checkStrLiteral(strLiteral: StringLiteral) = Str_t
  def checkBoolLiteral(boolLiteral: BoolLiteral) = Bool_t
  def checkVariable(variable: Variable) = vartype(variable.name)
}

object Tmp extends App {}
