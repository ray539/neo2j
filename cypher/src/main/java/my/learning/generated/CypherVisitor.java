// Generated from Cypher.g4 by ANTLR 4.13.2

    package my.learning.generated;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Cypher}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CypherVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Cypher#test}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTest(Cypher.TestContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(Cypher.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClause(Cypher.ClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#createClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreateClause(Cypher.CreateClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#matchClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMatchClause(Cypher.MatchClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#deleteClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteClause(Cypher.DeleteClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#whereClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhereClause(Cypher.WhereClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#patternList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPatternList(Cypher.PatternListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPattern(Cypher.PatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#patternElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPatternElement(Cypher.PatternElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#relationshipPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationshipPattern(Cypher.RelationshipPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#nodePattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNodePattern(Cypher.NodePatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#properties}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperties(Cypher.PropertiesContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(Cypher.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#labelExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelExpression(Cypher.LabelExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(Cypher.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#expression11}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression11(Cypher.Expression11Context ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#expression9}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression9(Cypher.Expression9Context ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#expression8}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression8(Cypher.Expression8Context ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#expression6}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression6(Cypher.Expression6Context ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#expression5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression5(Cypher.Expression5Context ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#expression4}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression4(Cypher.Expression4Context ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#expression3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression3(Cypher.Expression3Context ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#expression2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression2(Cypher.Expression2Context ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#postFix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostFix(Cypher.PostFixContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#property}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperty(Cypher.PropertyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#index}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndex(Cypher.IndexContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#expression1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression1(Cypher.Expression1Context ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#parenthesizedExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedExpression(Cypher.ParenthesizedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Cypher#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(Cypher.LiteralContext ctx);
}