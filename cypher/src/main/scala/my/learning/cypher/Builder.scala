package my.learning.cypher

import my.learning.generated.CypherListener
import my.learning.generated.CypherLexer
import my.learning.generated.Cypher
import my.learning.generated.CypherBaseListener

import my.learning.generated.Cypher.PatternElementContext
import my.learning.generated.Cypher.ParenthesizedExpressionContext
import my.learning.generated.Cypher.Expression2Context
import my.learning.generated.Cypher.Expression4Context
import my.learning.generated.Cypher.StatementContext
import my.learning.generated.Cypher.LabelExpressionContext
import my.learning.generated.Cypher.MatchClauseContext
import my.learning.generated.Cypher.Expression11Context
import my.learning.generated.Cypher.ClauseContext
import my.learning.generated.Cypher.ExpressionContext
import my.learning.generated.Cypher.CreateClauseContext
import my.learning.generated.Cypher.RelationshipPatternContext
import my.learning.generated.Cypher.Expression5Context
import my.learning.generated.Cypher.WhereClauseContext
import my.learning.generated.Cypher.VariableContext
import my.learning.generated.Cypher.DeleteClauseContext
import my.learning.generated.Cypher.Expression9Context
import my.learning.generated.Cypher.LiteralContext
import my.learning.generated.Cypher.Expression8Context
import my.learning.generated.Cypher.PostFixContext
import my.learning.generated.Cypher.Expression6Context
import my.learning.generated.Cypher.PropertiesContext
import my.learning.generated.Cypher.PatternContext
import my.learning.generated.Cypher.Expression1Context
import my.learning.generated.Cypher.NodePatternContext
import my.learning.generated.Cypher.PropertyContext
import my.learning.generated.Cypher.Expression3Context
import my.learning.generated.Cypher.PatternListContext

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.gui.Trees

import scala.io.StdIn.readLine
import my.learning.generated.CypherBaseVisitor
import scala.quoted.Expr

import scala.jdk.CollectionConverters.*
import my.learning.generated.Cypher.IndexContext
import java.util.function.BinaryOperator
import scala.collection.mutable

// case class Node(
//     id: Option[String],
//     label: Option[String],
//     properties: Map[String, AnyRef]
// ) {}
// case class Relationship(
//     id: Option[String], // if None, create request otherwise...
//     label: String,
//     properties: Map[String, AnyRef],
//     start: Node,
//     end: Node
// ) {}

// types
// - expression2 ((ADD | SUB) expression2)*
// - expression2

// case class PatternListWrapper(pl: List[Pattern]) extends ASTNode
// case class PathWrapper(segments: List[(NodePattern, RelationshipPattern)], finalNode: Node)


// trait because we want class which can extend this to inherit the functionality
// (this is the 'mixin' pattern)
trait StatementBuilder extends CypherBaseVisitor[AnyRef] {


  def genVarname() = "TODO"

  override def visitStatement(ctx: StatementContext): AnyRef = 
    return (for clauseCtx <- ctx.clause.asScala yield visitClause(clauseCtx)).toList

  override def visitClause(ctx: ClauseContext): Clause = 
    if ctx.createClause() != null then
      return visitCreateClause(ctx.createClause())
    else if ctx.matchClause() != null then
      return visitMatchClause(ctx.matchClause())
    else if ctx.deleteClause() != null then
      return visitDeleteClause(ctx.deleteClause())
    else if ctx.whereClause() != null then
      return visitWhereClause(ctx.whereClause())
    return null

  override def visitCreateClause(ctx: CreateClauseContext): CreateClause =
    return CreateClause(visitPatternList(ctx.patternList()))

  override def visitMatchClause(ctx: MatchClauseContext): MatchClause = 
    val v = visitPattern(ctx.pattern())
    return MatchClause(v)

  override def visitDeleteClause(ctx: DeleteClauseContext): DeleteClause = 
    val vars = (for varctx <- ctx.variable().asScala yield visitVariable(varctx)).toList
    return DeleteClause(vars)

  override def visitWhereClause(ctx: WhereClauseContext): WhereClause = 
    return WhereClause(visitExpression(ctx.expression()))

  override def visitPatternList(ctx: PatternListContext): List[Pattern] =
    return (for p <- ctx.pattern().asScala yield visitPattern(p)).toList

  // fill in bind variable
  // - if nothing, we have to fill in something because
  override def visitPattern(ctx: PatternContext): Pattern =
    val vname = if ctx.variable() != null then visitVariable(ctx.variable()) else Variable(genVarname())
    return visitPatternElement(ctx.patternElement()).copy(bindVariable = vname)

  // no bind variable
  override def visitPatternElement(ctx: PatternElementContext): Pattern =
    // n1 - [r1] -> n2 - [r2] -> n3
    val nodes = ctx.nodePattern()
    val relationships = ctx.relationshipPattern()
    val firstNode = visitNodePattern(nodes.get(0))
    var segments = (for idx <- 1 until nodes.size()
    yield (
      visitRelationshipPattern(relationships.get(idx - 1)),
      visitNodePattern(nodes.get(idx))
    )).toList
    return Pattern(Variable("none"), firstNode, segments)

  override def visitNodePattern(ctx: NodePatternContext): NodePattern =
    val variable =
      if ctx.variable() != null then visitVariable(ctx.variable())
      else Variable(genVarname())
    val label =
      if ctx.labelExpression() != null then
        Some(visitLabelExpression(ctx.labelExpression()))
      else None
    val props =
      if ctx.properties() != null then visitProperties(ctx.properties())
      else Map()
    return NodePattern(variable, label, props)

  override def visitRelationshipPattern(
      ctx: RelationshipPatternContext
  ): RelationshipPattern =
    val pointLeft = ctx.LT() != null
    val vname =
      if ctx.variable() != null then visitVariable(ctx.variable())
      else Variable(genVarname())
    val label =
      if ctx.labelExpression != null then
        Some(visitLabelExpression(ctx.labelExpression()))
      else None
    val pointRight = ctx.GT() != null
    return RelationshipPattern(vname, label, Map(), pointLeft, pointRight)

  override def visitVariable(ctx: VariableContext): Variable =
    return Variable(ctx.ID().getText())

  // probably make this a map sometime
  override def visitLabelExpression(ctx: LabelExpressionContext): String =
    return ctx.ID().getText()

  override def visitProperties(
      ctx: PropertiesContext
  ): Map[String, Expression] =
    val ids = ctx.ID()
    val exprs = ctx.expression()
    val pairs = (for idx <- (0 until ids.size())
    yield (ids.get(idx).getText(), visitExpression(exprs.get(idx)))).toList

    return pairs.toMap

  override def visitExpression(ctx: ExpressionContext): Expression =
    val exprs = ctx.expression11()
    var res = visitExpression11(exprs.get(0))
    for idx <- (1 until exprs.size()) do {
      val right = visitExpression11(exprs.get(idx))
      val opToken = ctx.getChild(2 * idx - 1).getText()
      val operator = opToken match
        case "OR" => Or
      res = BinaryExpression(res, operator, right)
    }
    return res

  override def visitExpression11(ctx: Expression11Context): Expression =
    val exprs = ctx.expression9()
    var res = visitExpression9(exprs.get(0))
    for idx <- (1 until exprs.size()) do {
      val right = visitExpression9(exprs.get(idx))
      val opToken = ctx.getChild(2 * idx - 1).getText()
      val operator = opToken match
        case "AND" => And
      res = BinaryExpression(res, operator, right)
    }
    return res

  override def visitExpression9(ctx: Expression9Context): Expression =
    var yes = ctx.NOT().size() % 2 == 0
    val expr8 = visitExpression8(ctx.expression8())
    return if yes then expr8 else UnaryExpression(Not, expr8)

  override def visitExpression8(ctx: Expression8Context): Expression =
    val exprs = ctx.expression6()
    var res = visitExpression6(exprs.get(0))
    for idx <- (1 until exprs.size()) do {
      val right = visitExpression6(exprs.get(idx))
      val opToken = ctx.getChild(2 * idx - 1).getText()
      val operator = opToken match
        case "="  => Eq
        case "<>" => Neq
        case "<=" => Le
        case ">=" => Ge
        case "<"  => Lt
        case ">"  => Gt
      res = BinaryExpression(res, operator, right)
    }
    return res

  override def visitExpression6(ctx: Expression6Context): Expression =
    val exprs = ctx.expression5()
    var res = visitExpression5(exprs.get(0))
    for idx <- (1 until exprs.size()) do {
      val right = visitExpression5(exprs.get(idx))
      val opToken = ctx.getChild(2 * idx - 1).getText()
      val operator = opToken match
        case "+" => Add
        case "-" => Sub
      res = BinaryExpression(res, operator, right)
    }
    return res

  override def visitExpression5(ctx: Expression5Context): Expression =
    val exprs = ctx.expression4()
    var res = visitExpression4(exprs.get(0))
    for idx <- (1 until exprs.size()) do {
      val right = visitExpression4(exprs.get(idx))
      val opToken = ctx.getChild(2 * idx - 1).getText()
      val operator = opToken match
        case "*" => Mul
        case "/" => Div
        case "%" => Mod
      res = BinaryExpression(res, operator, right)
    }
    return res

  override def visitExpression4(ctx: Expression4Context): Expression =
    val exprs = ctx.expression3().asScala.reverse.toList
    var res = visitExpression3(exprs(0));
    for idx <- (1 until exprs.length) do {
      val left = visitExpression3(exprs(idx))
      val opToken = ctx.getChild(2 * idx - 1).getText()
      val operator = opToken match
        case "^" => Pow
      res = BinaryExpression(left, operator, res)
    }
    return res

  override def visitExpression3(ctx: Expression3Context): Expression =
    if ctx.ADD() != null then
      return UnaryExpression(Add, visitExpression2(ctx.expression2()))
    if ctx.SUB() != null then
      return UnaryExpression(Sub, visitExpression2(ctx.expression2()))
    return visitExpression2(ctx.expression2())

  override def visitExpression2(ctx: Expression2Context): Expression =
    var base = visitExpression1(ctx.expression1())
    val postFixes = ctx.postFix()
    for postOp <- postFixes.asScala do {
      var tmp = visitPostFix(postOp)
      assert(tmp.left == null)
      base = tmp.copy(left = base)
    }
    return base

  override def visitPostFix(ctx: PostFixContext): BinaryExpression =
    // array indexing as well...
    if ctx.property() != null then return visitProperty(ctx.property())
    if ctx.index() != null then return visitIndex(ctx.index())
    return null

  override def visitProperty(ctx: PropertyContext): BinaryExpression =
    return BinaryExpression(
      null,
      GetAttr,
      StringLiteral(ctx.ID().getText())
    )

  override def visitIndex(ctx: IndexContext): BinaryExpression =
    return BinaryExpression(null, Index, visitExpression(ctx.expression()))

  override def visitExpression1(ctx: Expression1Context): Expression =
    if ctx.literal() != null then return visitLiteral(ctx.literal())
    else if ctx.variable() != null then return visitVariable(ctx.variable())
    else if ctx.parenthesizedExpression() != null then
      return visitParenthesizedExpression(ctx.parenthesizedExpression())
    return null

  override def visitParenthesizedExpression(
      ctx: ParenthesizedExpressionContext
  ): Expression =
    return visitExpression(ctx.expression())

  override def visitLiteral(ctx: LiteralContext): Expression =
    if ctx.STRING_LITERAL() != null then
      return StringLiteral(ctx.STRING_LITERAL().getText())
    else if ctx.INTEGER_LITERAL() != null then
      return IntLiteral(ctx.INTEGER_LITERAL().getText().toInt)
    else if ctx.TRUE() != null then return BoolLiteral(ctx.TRUE().getText().toLowerCase.toBoolean)
    else if ctx.FALSE() != null then return BoolLiteral(ctx.FALSE().getText().toLowerCase.toBoolean)
    return null
}

@main
def main() =
  val input: CharStream =
    CharStreams.fromString("CREATE (a:A) - [r1] -> (b:A) - [r2] -> (c:A)")
  val lexer: CypherLexer = CypherLexer(input)
  val tokens: CommonTokenStream = CommonTokenStream(lexer)

  tokens.fill()
  tokens.getTokens.forEach(println)

  val parser: Cypher = Cypher(tokens)
  val tree: ParseTree = parser.statement();

  // println(tree.toStringTree(parser)) // enrich with parser to get further information
  Trees.inspect(tree, parser)

  

  // val walker: ParseTreeWalker = ParseTreeWalker()

  // val builder: StatementBuilder = StatementBuilder()

  // walker.walk(builder, tree)
