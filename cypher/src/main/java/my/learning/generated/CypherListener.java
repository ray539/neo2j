// Generated from Cypher.g4 by ANTLR 4.13.2

    package my.learning.generated;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Cypher}.
 */
public interface CypherListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link Cypher#test}.
	 * @param ctx the parse tree
	 */
	void enterTest(Cypher.TestContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#test}.
	 * @param ctx the parse tree
	 */
	void exitTest(Cypher.TestContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(Cypher.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(Cypher.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#clause}.
	 * @param ctx the parse tree
	 */
	void enterClause(Cypher.ClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#clause}.
	 * @param ctx the parse tree
	 */
	void exitClause(Cypher.ClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#createClause}.
	 * @param ctx the parse tree
	 */
	void enterCreateClause(Cypher.CreateClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#createClause}.
	 * @param ctx the parse tree
	 */
	void exitCreateClause(Cypher.CreateClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#matchClause}.
	 * @param ctx the parse tree
	 */
	void enterMatchClause(Cypher.MatchClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#matchClause}.
	 * @param ctx the parse tree
	 */
	void exitMatchClause(Cypher.MatchClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#deleteClause}.
	 * @param ctx the parse tree
	 */
	void enterDeleteClause(Cypher.DeleteClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#deleteClause}.
	 * @param ctx the parse tree
	 */
	void exitDeleteClause(Cypher.DeleteClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#whereClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereClause(Cypher.WhereClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#whereClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereClause(Cypher.WhereClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#patternList}.
	 * @param ctx the parse tree
	 */
	void enterPatternList(Cypher.PatternListContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#patternList}.
	 * @param ctx the parse tree
	 */
	void exitPatternList(Cypher.PatternListContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#pattern}.
	 * @param ctx the parse tree
	 */
	void enterPattern(Cypher.PatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#pattern}.
	 * @param ctx the parse tree
	 */
	void exitPattern(Cypher.PatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#patternElement}.
	 * @param ctx the parse tree
	 */
	void enterPatternElement(Cypher.PatternElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#patternElement}.
	 * @param ctx the parse tree
	 */
	void exitPatternElement(Cypher.PatternElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#relationshipPattern}.
	 * @param ctx the parse tree
	 */
	void enterRelationshipPattern(Cypher.RelationshipPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#relationshipPattern}.
	 * @param ctx the parse tree
	 */
	void exitRelationshipPattern(Cypher.RelationshipPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#nodePattern}.
	 * @param ctx the parse tree
	 */
	void enterNodePattern(Cypher.NodePatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#nodePattern}.
	 * @param ctx the parse tree
	 */
	void exitNodePattern(Cypher.NodePatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#properties}.
	 * @param ctx the parse tree
	 */
	void enterProperties(Cypher.PropertiesContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#properties}.
	 * @param ctx the parse tree
	 */
	void exitProperties(Cypher.PropertiesContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(Cypher.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(Cypher.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#labelExpression}.
	 * @param ctx the parse tree
	 */
	void enterLabelExpression(Cypher.LabelExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#labelExpression}.
	 * @param ctx the parse tree
	 */
	void exitLabelExpression(Cypher.LabelExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(Cypher.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(Cypher.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#expression11}.
	 * @param ctx the parse tree
	 */
	void enterExpression11(Cypher.Expression11Context ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#expression11}.
	 * @param ctx the parse tree
	 */
	void exitExpression11(Cypher.Expression11Context ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#expression9}.
	 * @param ctx the parse tree
	 */
	void enterExpression9(Cypher.Expression9Context ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#expression9}.
	 * @param ctx the parse tree
	 */
	void exitExpression9(Cypher.Expression9Context ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#expression8}.
	 * @param ctx the parse tree
	 */
	void enterExpression8(Cypher.Expression8Context ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#expression8}.
	 * @param ctx the parse tree
	 */
	void exitExpression8(Cypher.Expression8Context ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#expression6}.
	 * @param ctx the parse tree
	 */
	void enterExpression6(Cypher.Expression6Context ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#expression6}.
	 * @param ctx the parse tree
	 */
	void exitExpression6(Cypher.Expression6Context ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#expression5}.
	 * @param ctx the parse tree
	 */
	void enterExpression5(Cypher.Expression5Context ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#expression5}.
	 * @param ctx the parse tree
	 */
	void exitExpression5(Cypher.Expression5Context ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#expression4}.
	 * @param ctx the parse tree
	 */
	void enterExpression4(Cypher.Expression4Context ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#expression4}.
	 * @param ctx the parse tree
	 */
	void exitExpression4(Cypher.Expression4Context ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#expression3}.
	 * @param ctx the parse tree
	 */
	void enterExpression3(Cypher.Expression3Context ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#expression3}.
	 * @param ctx the parse tree
	 */
	void exitExpression3(Cypher.Expression3Context ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#expression2}.
	 * @param ctx the parse tree
	 */
	void enterExpression2(Cypher.Expression2Context ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#expression2}.
	 * @param ctx the parse tree
	 */
	void exitExpression2(Cypher.Expression2Context ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#postFix}.
	 * @param ctx the parse tree
	 */
	void enterPostFix(Cypher.PostFixContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#postFix}.
	 * @param ctx the parse tree
	 */
	void exitPostFix(Cypher.PostFixContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#property}.
	 * @param ctx the parse tree
	 */
	void enterProperty(Cypher.PropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#property}.
	 * @param ctx the parse tree
	 */
	void exitProperty(Cypher.PropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#index}.
	 * @param ctx the parse tree
	 */
	void enterIndex(Cypher.IndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#index}.
	 * @param ctx the parse tree
	 */
	void exitIndex(Cypher.IndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#expression1}.
	 * @param ctx the parse tree
	 */
	void enterExpression1(Cypher.Expression1Context ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#expression1}.
	 * @param ctx the parse tree
	 */
	void exitExpression1(Cypher.Expression1Context ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#parenthesizedExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedExpression(Cypher.ParenthesizedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#parenthesizedExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedExpression(Cypher.ParenthesizedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link Cypher#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(Cypher.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link Cypher#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(Cypher.LiteralContext ctx);
}