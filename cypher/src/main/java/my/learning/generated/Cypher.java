// Generated from Cypher.g4 by ANTLR 4.13.2

    package my.learning.generated;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class Cypher extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		CREATE=1, MATCH=2, DELETE=3, WHERE=4, COMMA=5, EQ=6, NE=7, LT=8, LE=9, 
		GT=10, GE=11, DASH=12, LPAREN=13, RPAREN=14, LBRACKET=15, RBRACKET=16, 
		LCURLY=17, RCURLY=18, COLON=19, OR=20, AND=21, NOT=22, POW=23, ADD=24, 
		SUB=25, MUL=26, DIV=27, PERCENT=28, TRUE=29, FALSE=30, DOT=31, ID=32, 
		INTEGER_LITERAL=33, STRING_LITERAL=34, WS=35;
	public static final int
		RULE_test = 0, RULE_statement = 1, RULE_clause = 2, RULE_createClause = 3, 
		RULE_matchClause = 4, RULE_deleteClause = 5, RULE_whereClause = 6, RULE_patternList = 7, 
		RULE_pattern = 8, RULE_patternElement = 9, RULE_relationshipPattern = 10, 
		RULE_nodePattern = 11, RULE_properties = 12, RULE_variable = 13, RULE_labelExpression = 14, 
		RULE_expression = 15, RULE_expression11 = 16, RULE_expression9 = 17, RULE_expression8 = 18, 
		RULE_expression6 = 19, RULE_expression5 = 20, RULE_expression4 = 21, RULE_expression3 = 22, 
		RULE_expression2 = 23, RULE_postFix = 24, RULE_property = 25, RULE_index = 26, 
		RULE_expression1 = 27, RULE_parenthesizedExpression = 28, RULE_literal = 29;
	private static String[] makeRuleNames() {
		return new String[] {
			"test", "statement", "clause", "createClause", "matchClause", "deleteClause", 
			"whereClause", "patternList", "pattern", "patternElement", "relationshipPattern", 
			"nodePattern", "properties", "variable", "labelExpression", "expression", 
			"expression11", "expression9", "expression8", "expression6", "expression5", 
			"expression4", "expression3", "expression2", "postFix", "property", "index", 
			"expression1", "parenthesizedExpression", "literal"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'CREATE'", "'MATCH'", "'DELETE'", "'WHERE'", "','", "'='", "'<>'", 
			"'<'", "'<='", "'>'", "'>='", null, "'('", "')'", "'['", "']'", "'{'", 
			"'}'", "':'", "'OR'", "'AND'", "'NOT'", "'^'", "'+'", null, "'*'", "'/'", 
			"'%'", "'TRUE'", "'FALSE'", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "CREATE", "MATCH", "DELETE", "WHERE", "COMMA", "EQ", "NE", "LT", 
			"LE", "GT", "GE", "DASH", "LPAREN", "RPAREN", "LBRACKET", "RBRACKET", 
			"LCURLY", "RCURLY", "COLON", "OR", "AND", "NOT", "POW", "ADD", "SUB", 
			"MUL", "DIV", "PERCENT", "TRUE", "FALSE", "DOT", "ID", "INTEGER_LITERAL", 
			"STRING_LITERAL", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Cypher.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public Cypher(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TestContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(Cypher.CREATE, 0); }
		public TestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_test; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterTest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitTest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitTest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TestContext test() throws RecognitionException {
		TestContext _localctx = new TestContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_test);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			match(CREATE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public List<ClauseContext> clause() {
			return getRuleContexts(ClauseContext.class);
		}
		public ClauseContext clause(int i) {
			return getRuleContext(ClauseContext.class,i);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(62);
				clause();
				}
				}
				setState(65); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 30L) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClauseContext extends ParserRuleContext {
		public CreateClauseContext createClause() {
			return getRuleContext(CreateClauseContext.class,0);
		}
		public MatchClauseContext matchClause() {
			return getRuleContext(MatchClauseContext.class,0);
		}
		public DeleteClauseContext deleteClause() {
			return getRuleContext(DeleteClauseContext.class,0);
		}
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public ClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClauseContext clause() throws RecognitionException {
		ClauseContext _localctx = new ClauseContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_clause);
		try {
			setState(71);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CREATE:
				enterOuterAlt(_localctx, 1);
				{
				setState(67);
				createClause();
				}
				break;
			case MATCH:
				enterOuterAlt(_localctx, 2);
				{
				setState(68);
				matchClause();
				}
				break;
			case DELETE:
				enterOuterAlt(_localctx, 3);
				{
				setState(69);
				deleteClause();
				}
				break;
			case WHERE:
				enterOuterAlt(_localctx, 4);
				{
				setState(70);
				whereClause();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CreateClauseContext extends ParserRuleContext {
		public TerminalNode CREATE() { return getToken(Cypher.CREATE, 0); }
		public PatternListContext patternList() {
			return getRuleContext(PatternListContext.class,0);
		}
		public CreateClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterCreateClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitCreateClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitCreateClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CreateClauseContext createClause() throws RecognitionException {
		CreateClauseContext _localctx = new CreateClauseContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_createClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(CREATE);
			setState(74);
			patternList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MatchClauseContext extends ParserRuleContext {
		public TerminalNode MATCH() { return getToken(Cypher.MATCH, 0); }
		public PatternContext pattern() {
			return getRuleContext(PatternContext.class,0);
		}
		public MatchClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_matchClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterMatchClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitMatchClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitMatchClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MatchClauseContext matchClause() throws RecognitionException {
		MatchClauseContext _localctx = new MatchClauseContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_matchClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			match(MATCH);
			setState(77);
			pattern();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeleteClauseContext extends ParserRuleContext {
		public TerminalNode DELETE() { return getToken(Cypher.DELETE, 0); }
		public List<VariableContext> variable() {
			return getRuleContexts(VariableContext.class);
		}
		public VariableContext variable(int i) {
			return getRuleContext(VariableContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Cypher.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Cypher.COMMA, i);
		}
		public DeleteClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deleteClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterDeleteClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitDeleteClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitDeleteClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeleteClauseContext deleteClause() throws RecognitionException {
		DeleteClauseContext _localctx = new DeleteClauseContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_deleteClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			match(DELETE);
			setState(80);
			variable();
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(81);
				match(COMMA);
				setState(82);
				variable();
				}
				}
				setState(87);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhereClauseContext extends ParserRuleContext {
		public TerminalNode WHERE() { return getToken(Cypher.WHERE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public WhereClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whereClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterWhereClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitWhereClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitWhereClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhereClauseContext whereClause() throws RecognitionException {
		WhereClauseContext _localctx = new WhereClauseContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_whereClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88);
			match(WHERE);
			setState(89);
			expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PatternListContext extends ParserRuleContext {
		public List<PatternContext> pattern() {
			return getRuleContexts(PatternContext.class);
		}
		public PatternContext pattern(int i) {
			return getRuleContext(PatternContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Cypher.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Cypher.COMMA, i);
		}
		public PatternListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_patternList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterPatternList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitPatternList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitPatternList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PatternListContext patternList() throws RecognitionException {
		PatternListContext _localctx = new PatternListContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_patternList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			pattern();
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(92);
				match(COMMA);
				setState(93);
				pattern();
				}
				}
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PatternContext extends ParserRuleContext {
		public PatternElementContext patternElement() {
			return getRuleContext(PatternElementContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode EQ() { return getToken(Cypher.EQ, 0); }
		public PatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PatternContext pattern() throws RecognitionException {
		PatternContext _localctx = new PatternContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_pattern);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(99);
				variable();
				setState(100);
				match(EQ);
				}
			}

			setState(104);
			patternElement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PatternElementContext extends ParserRuleContext {
		public List<NodePatternContext> nodePattern() {
			return getRuleContexts(NodePatternContext.class);
		}
		public NodePatternContext nodePattern(int i) {
			return getRuleContext(NodePatternContext.class,i);
		}
		public List<RelationshipPatternContext> relationshipPattern() {
			return getRuleContexts(RelationshipPatternContext.class);
		}
		public RelationshipPatternContext relationshipPattern(int i) {
			return getRuleContext(RelationshipPatternContext.class,i);
		}
		public PatternElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_patternElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterPatternElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitPatternElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitPatternElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PatternElementContext patternElement() throws RecognitionException {
		PatternElementContext _localctx = new PatternElementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_patternElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(106);
			nodePattern();
			setState(112);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LT || _la==DASH) {
				{
				{
				setState(107);
				relationshipPattern();
				setState(108);
				nodePattern();
				}
				}
				setState(114);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RelationshipPatternContext extends ParserRuleContext {
		public List<TerminalNode> DASH() { return getTokens(Cypher.DASH); }
		public TerminalNode DASH(int i) {
			return getToken(Cypher.DASH, i);
		}
		public TerminalNode LBRACKET() { return getToken(Cypher.LBRACKET, 0); }
		public TerminalNode RBRACKET() { return getToken(Cypher.RBRACKET, 0); }
		public TerminalNode LT() { return getToken(Cypher.LT, 0); }
		public TerminalNode GT() { return getToken(Cypher.GT, 0); }
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public LabelExpressionContext labelExpression() {
			return getRuleContext(LabelExpressionContext.class,0);
		}
		public RelationshipPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationshipPattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterRelationshipPattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitRelationshipPattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitRelationshipPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationshipPatternContext relationshipPattern() throws RecognitionException {
		RelationshipPatternContext _localctx = new RelationshipPatternContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_relationshipPattern);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(115);
				match(LT);
				}
			}

			setState(118);
			match(DASH);
			{
			setState(119);
			match(LBRACKET);
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(120);
				variable();
				}
			}

			setState(124);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(123);
				labelExpression();
				}
			}

			setState(126);
			match(RBRACKET);
			}
			setState(128);
			match(DASH);
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==GT) {
				{
				setState(129);
				match(GT);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class NodePatternContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(Cypher.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(Cypher.RPAREN, 0); }
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public LabelExpressionContext labelExpression() {
			return getRuleContext(LabelExpressionContext.class,0);
		}
		public PropertiesContext properties() {
			return getRuleContext(PropertiesContext.class,0);
		}
		public NodePatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodePattern; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterNodePattern(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitNodePattern(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitNodePattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NodePatternContext nodePattern() throws RecognitionException {
		NodePatternContext _localctx = new NodePatternContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_nodePattern);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			match(LPAREN);
			setState(134);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(133);
				variable();
				}
			}

			setState(137);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(136);
				labelExpression();
				}
			}

			setState(140);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LCURLY) {
				{
				setState(139);
				properties();
				}
			}

			setState(142);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PropertiesContext extends ParserRuleContext {
		public TerminalNode LCURLY() { return getToken(Cypher.LCURLY, 0); }
		public TerminalNode RCURLY() { return getToken(Cypher.RCURLY, 0); }
		public List<TerminalNode> ID() { return getTokens(Cypher.ID); }
		public TerminalNode ID(int i) {
			return getToken(Cypher.ID, i);
		}
		public List<TerminalNode> COLON() { return getTokens(Cypher.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(Cypher.COLON, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(Cypher.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(Cypher.COMMA, i);
		}
		public PropertiesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_properties; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterProperties(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitProperties(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitProperties(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertiesContext properties() throws RecognitionException {
		PropertiesContext _localctx = new PropertiesContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_properties);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(LCURLY);
			setState(157);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(145);
				match(ID);
				setState(146);
				match(COLON);
				setState(147);
				expression();
				setState(154);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(148);
					match(COMMA);
					setState(149);
					match(ID);
					setState(150);
					match(COLON);
					setState(151);
					expression();
					}
					}
					setState(156);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(159);
			match(RCURLY);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(Cypher.ID, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LabelExpressionContext extends ParserRuleContext {
		public TerminalNode COLON() { return getToken(Cypher.COLON, 0); }
		public TerminalNode ID() { return getToken(Cypher.ID, 0); }
		public LabelExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterLabelExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitLabelExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitLabelExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelExpressionContext labelExpression() throws RecognitionException {
		LabelExpressionContext _localctx = new LabelExpressionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_labelExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			match(COLON);
			setState(164);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public List<Expression11Context> expression11() {
			return getRuleContexts(Expression11Context.class);
		}
		public Expression11Context expression11(int i) {
			return getRuleContext(Expression11Context.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(Cypher.OR); }
		public TerminalNode OR(int i) {
			return getToken(Cypher.OR, i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			expression11();
			setState(171);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(167);
				match(OR);
				setState(168);
				expression11();
				}
				}
				setState(173);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expression11Context extends ParserRuleContext {
		public List<Expression9Context> expression9() {
			return getRuleContexts(Expression9Context.class);
		}
		public Expression9Context expression9(int i) {
			return getRuleContext(Expression9Context.class,i);
		}
		public List<TerminalNode> AND() { return getTokens(Cypher.AND); }
		public TerminalNode AND(int i) {
			return getToken(Cypher.AND, i);
		}
		public Expression11Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression11; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterExpression11(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitExpression11(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitExpression11(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression11Context expression11() throws RecognitionException {
		Expression11Context _localctx = new Expression11Context(_ctx, getState());
		enterRule(_localctx, 32, RULE_expression11);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			expression9();
			setState(179);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AND) {
				{
				{
				setState(175);
				match(AND);
				setState(176);
				expression9();
				}
				}
				setState(181);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expression9Context extends ParserRuleContext {
		public Expression8Context expression8() {
			return getRuleContext(Expression8Context.class,0);
		}
		public List<TerminalNode> NOT() { return getTokens(Cypher.NOT); }
		public TerminalNode NOT(int i) {
			return getToken(Cypher.NOT, i);
		}
		public Expression9Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression9; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterExpression9(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitExpression9(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitExpression9(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression9Context expression9() throws RecognitionException {
		Expression9Context _localctx = new Expression9Context(_ctx, getState());
		enterRule(_localctx, 34, RULE_expression9);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NOT) {
				{
				{
				setState(182);
				match(NOT);
				}
				}
				setState(187);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(188);
			expression8();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expression8Context extends ParserRuleContext {
		public List<Expression6Context> expression6() {
			return getRuleContexts(Expression6Context.class);
		}
		public Expression6Context expression6(int i) {
			return getRuleContext(Expression6Context.class,i);
		}
		public List<TerminalNode> EQ() { return getTokens(Cypher.EQ); }
		public TerminalNode EQ(int i) {
			return getToken(Cypher.EQ, i);
		}
		public List<TerminalNode> NE() { return getTokens(Cypher.NE); }
		public TerminalNode NE(int i) {
			return getToken(Cypher.NE, i);
		}
		public List<TerminalNode> LE() { return getTokens(Cypher.LE); }
		public TerminalNode LE(int i) {
			return getToken(Cypher.LE, i);
		}
		public List<TerminalNode> GE() { return getTokens(Cypher.GE); }
		public TerminalNode GE(int i) {
			return getToken(Cypher.GE, i);
		}
		public List<TerminalNode> LT() { return getTokens(Cypher.LT); }
		public TerminalNode LT(int i) {
			return getToken(Cypher.LT, i);
		}
		public List<TerminalNode> GT() { return getTokens(Cypher.GT); }
		public TerminalNode GT(int i) {
			return getToken(Cypher.GT, i);
		}
		public Expression8Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression8; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterExpression8(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitExpression8(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitExpression8(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression8Context expression8() throws RecognitionException {
		Expression8Context _localctx = new Expression8Context(_ctx, getState());
		enterRule(_localctx, 36, RULE_expression8);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			expression6();
			setState(195);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4032L) != 0)) {
				{
				{
				setState(191);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 4032L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(192);
				expression6();
				}
				}
				setState(197);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expression6Context extends ParserRuleContext {
		public List<Expression5Context> expression5() {
			return getRuleContexts(Expression5Context.class);
		}
		public Expression5Context expression5(int i) {
			return getRuleContext(Expression5Context.class,i);
		}
		public List<TerminalNode> ADD() { return getTokens(Cypher.ADD); }
		public TerminalNode ADD(int i) {
			return getToken(Cypher.ADD, i);
		}
		public List<TerminalNode> SUB() { return getTokens(Cypher.SUB); }
		public TerminalNode SUB(int i) {
			return getToken(Cypher.SUB, i);
		}
		public Expression6Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression6; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterExpression6(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitExpression6(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitExpression6(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression6Context expression6() throws RecognitionException {
		Expression6Context _localctx = new Expression6Context(_ctx, getState());
		enterRule(_localctx, 38, RULE_expression6);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			expression5();
			setState(203);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ADD || _la==SUB) {
				{
				{
				setState(199);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==SUB) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(200);
				expression5();
				}
				}
				setState(205);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expression5Context extends ParserRuleContext {
		public List<Expression4Context> expression4() {
			return getRuleContexts(Expression4Context.class);
		}
		public Expression4Context expression4(int i) {
			return getRuleContext(Expression4Context.class,i);
		}
		public List<TerminalNode> MUL() { return getTokens(Cypher.MUL); }
		public TerminalNode MUL(int i) {
			return getToken(Cypher.MUL, i);
		}
		public List<TerminalNode> DIV() { return getTokens(Cypher.DIV); }
		public TerminalNode DIV(int i) {
			return getToken(Cypher.DIV, i);
		}
		public List<TerminalNode> PERCENT() { return getTokens(Cypher.PERCENT); }
		public TerminalNode PERCENT(int i) {
			return getToken(Cypher.PERCENT, i);
		}
		public Expression5Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression5; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterExpression5(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitExpression5(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitExpression5(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression5Context expression5() throws RecognitionException {
		Expression5Context _localctx = new Expression5Context(_ctx, getState());
		enterRule(_localctx, 40, RULE_expression5);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			expression4();
			setState(211);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 469762048L) != 0)) {
				{
				{
				setState(207);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 469762048L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(208);
				expression4();
				}
				}
				setState(213);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expression4Context extends ParserRuleContext {
		public List<Expression3Context> expression3() {
			return getRuleContexts(Expression3Context.class);
		}
		public Expression3Context expression3(int i) {
			return getRuleContext(Expression3Context.class,i);
		}
		public List<TerminalNode> POW() { return getTokens(Cypher.POW); }
		public TerminalNode POW(int i) {
			return getToken(Cypher.POW, i);
		}
		public Expression4Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression4; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterExpression4(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitExpression4(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitExpression4(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression4Context expression4() throws RecognitionException {
		Expression4Context _localctx = new Expression4Context(_ctx, getState());
		enterRule(_localctx, 42, RULE_expression4);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			expression3();
			setState(219);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==POW) {
				{
				{
				setState(215);
				match(POW);
				setState(216);
				expression3();
				}
				}
				setState(221);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expression3Context extends ParserRuleContext {
		public Expression2Context expression2() {
			return getRuleContext(Expression2Context.class,0);
		}
		public TerminalNode ADD() { return getToken(Cypher.ADD, 0); }
		public TerminalNode SUB() { return getToken(Cypher.SUB, 0); }
		public Expression3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterExpression3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitExpression3(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitExpression3(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression3Context expression3() throws RecognitionException {
		Expression3Context _localctx = new Expression3Context(_ctx, getState());
		enterRule(_localctx, 44, RULE_expression3);
		int _la;
		try {
			setState(225);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
			case TRUE:
			case FALSE:
			case ID:
			case INTEGER_LITERAL:
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(222);
				expression2();
				}
				break;
			case ADD:
			case SUB:
				enterOuterAlt(_localctx, 2);
				{
				setState(223);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==SUB) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(224);
				expression2();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expression2Context extends ParserRuleContext {
		public Expression1Context expression1() {
			return getRuleContext(Expression1Context.class,0);
		}
		public List<PostFixContext> postFix() {
			return getRuleContexts(PostFixContext.class);
		}
		public PostFixContext postFix(int i) {
			return getRuleContext(PostFixContext.class,i);
		}
		public Expression2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterExpression2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitExpression2(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitExpression2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression2Context expression2() throws RecognitionException {
		Expression2Context _localctx = new Expression2Context(_ctx, getState());
		enterRule(_localctx, 46, RULE_expression2);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			expression1();
			setState(231);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBRACKET || _la==DOT) {
				{
				{
				setState(228);
				postFix();
				}
				}
				setState(233);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PostFixContext extends ParserRuleContext {
		public PropertyContext property() {
			return getRuleContext(PropertyContext.class,0);
		}
		public IndexContext index() {
			return getRuleContext(IndexContext.class,0);
		}
		public PostFixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postFix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterPostFix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitPostFix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitPostFix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PostFixContext postFix() throws RecognitionException {
		PostFixContext _localctx = new PostFixContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_postFix);
		try {
			setState(236);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DOT:
				enterOuterAlt(_localctx, 1);
				{
				setState(234);
				property();
				}
				break;
			case LBRACKET:
				enterOuterAlt(_localctx, 2);
				{
				setState(235);
				index();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PropertyContext extends ParserRuleContext {
		public TerminalNode DOT() { return getToken(Cypher.DOT, 0); }
		public TerminalNode ID() { return getToken(Cypher.ID, 0); }
		public PropertyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_property; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterProperty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitProperty(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitProperty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyContext property() throws RecognitionException {
		PropertyContext _localctx = new PropertyContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_property);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			match(DOT);
			setState(239);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IndexContext extends ParserRuleContext {
		public TerminalNode LBRACKET() { return getToken(Cypher.LBRACKET, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RBRACKET() { return getToken(Cypher.RBRACKET, 0); }
		public IndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_index; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitIndex(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitIndex(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexContext index() throws RecognitionException {
		IndexContext _localctx = new IndexContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_index);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241);
			match(LBRACKET);
			setState(242);
			expression();
			setState(243);
			match(RBRACKET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Expression1Context extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public ParenthesizedExpressionContext parenthesizedExpression() {
			return getRuleContext(ParenthesizedExpressionContext.class,0);
		}
		public Expression1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression1; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterExpression1(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitExpression1(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitExpression1(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expression1Context expression1() throws RecognitionException {
		Expression1Context _localctx = new Expression1Context(_ctx, getState());
		enterRule(_localctx, 54, RULE_expression1);
		try {
			setState(248);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TRUE:
			case FALSE:
			case INTEGER_LITERAL:
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(245);
				literal();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(246);
				variable();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 3);
				{
				setState(247);
				parenthesizedExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParenthesizedExpressionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(Cypher.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(Cypher.RPAREN, 0); }
		public ParenthesizedExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parenthesizedExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterParenthesizedExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitParenthesizedExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitParenthesizedExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParenthesizedExpressionContext parenthesizedExpression() throws RecognitionException {
		ParenthesizedExpressionContext _localctx = new ParenthesizedExpressionContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_parenthesizedExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250);
			match(LPAREN);
			setState(251);
			expression();
			setState(252);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode STRING_LITERAL() { return getToken(Cypher.STRING_LITERAL, 0); }
		public TerminalNode INTEGER_LITERAL() { return getToken(Cypher.INTEGER_LITERAL, 0); }
		public TerminalNode TRUE() { return getToken(Cypher.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(Cypher.FALSE, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CypherListener ) ((CypherListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CypherVisitor ) return ((CypherVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 27380416512L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001#\u0101\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0004\u0001@\b\u0001\u000b\u0001\f\u0001A\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0003\u0002H\b\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0005\u0005T\b\u0005\n\u0005\f\u0005W\t"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0005\u0007_\b\u0007\n\u0007\f\u0007b\t\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0003\bg\b\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0005"+
		"\to\b\t\n\t\f\tr\t\t\u0001\n\u0003\nu\b\n\u0001\n\u0001\n\u0001\n\u0003"+
		"\nz\b\n\u0001\n\u0003\n}\b\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u0083"+
		"\b\n\u0001\u000b\u0001\u000b\u0003\u000b\u0087\b\u000b\u0001\u000b\u0003"+
		"\u000b\u008a\b\u000b\u0001\u000b\u0003\u000b\u008d\b\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0005\f\u0099\b\f\n\f\f\f\u009c\t\f\u0003\f\u009e\b\f\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0005\u000f\u00aa\b\u000f\n\u000f\f\u000f\u00ad\t\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010\u00b2\b\u0010\n\u0010"+
		"\f\u0010\u00b5\t\u0010\u0001\u0011\u0005\u0011\u00b8\b\u0011\n\u0011\f"+
		"\u0011\u00bb\t\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0005\u0012\u00c2\b\u0012\n\u0012\f\u0012\u00c5\t\u0012\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0005\u0013\u00ca\b\u0013\n\u0013\f\u0013\u00cd"+
		"\t\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0005\u0014\u00d2\b\u0014"+
		"\n\u0014\f\u0014\u00d5\t\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0005"+
		"\u0015\u00da\b\u0015\n\u0015\f\u0015\u00dd\t\u0015\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0003\u0016\u00e2\b\u0016\u0001\u0017\u0001\u0017\u0005\u0017"+
		"\u00e6\b\u0017\n\u0017\f\u0017\u00e9\t\u0017\u0001\u0018\u0001\u0018\u0003"+
		"\u0018\u00ed\b\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0003"+
		"\u001b\u00f9\b\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001"+
		"\u001d\u0001\u001d\u0001\u001d\u0000\u0000\u001e\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,."+
		"02468:\u0000\u0004\u0001\u0000\u0006\u000b\u0001\u0000\u0018\u0019\u0001"+
		"\u0000\u001a\u001c\u0002\u0000\u001d\u001e!\"\u00ff\u0000<\u0001\u0000"+
		"\u0000\u0000\u0002?\u0001\u0000\u0000\u0000\u0004G\u0001\u0000\u0000\u0000"+
		"\u0006I\u0001\u0000\u0000\u0000\bL\u0001\u0000\u0000\u0000\nO\u0001\u0000"+
		"\u0000\u0000\fX\u0001\u0000\u0000\u0000\u000e[\u0001\u0000\u0000\u0000"+
		"\u0010f\u0001\u0000\u0000\u0000\u0012j\u0001\u0000\u0000\u0000\u0014t"+
		"\u0001\u0000\u0000\u0000\u0016\u0084\u0001\u0000\u0000\u0000\u0018\u0090"+
		"\u0001\u0000\u0000\u0000\u001a\u00a1\u0001\u0000\u0000\u0000\u001c\u00a3"+
		"\u0001\u0000\u0000\u0000\u001e\u00a6\u0001\u0000\u0000\u0000 \u00ae\u0001"+
		"\u0000\u0000\u0000\"\u00b9\u0001\u0000\u0000\u0000$\u00be\u0001\u0000"+
		"\u0000\u0000&\u00c6\u0001\u0000\u0000\u0000(\u00ce\u0001\u0000\u0000\u0000"+
		"*\u00d6\u0001\u0000\u0000\u0000,\u00e1\u0001\u0000\u0000\u0000.\u00e3"+
		"\u0001\u0000\u0000\u00000\u00ec\u0001\u0000\u0000\u00002\u00ee\u0001\u0000"+
		"\u0000\u00004\u00f1\u0001\u0000\u0000\u00006\u00f8\u0001\u0000\u0000\u0000"+
		"8\u00fa\u0001\u0000\u0000\u0000:\u00fe\u0001\u0000\u0000\u0000<=\u0005"+
		"\u0001\u0000\u0000=\u0001\u0001\u0000\u0000\u0000>@\u0003\u0004\u0002"+
		"\u0000?>\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000\u0000A?\u0001\u0000"+
		"\u0000\u0000AB\u0001\u0000\u0000\u0000B\u0003\u0001\u0000\u0000\u0000"+
		"CH\u0003\u0006\u0003\u0000DH\u0003\b\u0004\u0000EH\u0003\n\u0005\u0000"+
		"FH\u0003\f\u0006\u0000GC\u0001\u0000\u0000\u0000GD\u0001\u0000\u0000\u0000"+
		"GE\u0001\u0000\u0000\u0000GF\u0001\u0000\u0000\u0000H\u0005\u0001\u0000"+
		"\u0000\u0000IJ\u0005\u0001\u0000\u0000JK\u0003\u000e\u0007\u0000K\u0007"+
		"\u0001\u0000\u0000\u0000LM\u0005\u0002\u0000\u0000MN\u0003\u0010\b\u0000"+
		"N\t\u0001\u0000\u0000\u0000OP\u0005\u0003\u0000\u0000PU\u0003\u001a\r"+
		"\u0000QR\u0005\u0005\u0000\u0000RT\u0003\u001a\r\u0000SQ\u0001\u0000\u0000"+
		"\u0000TW\u0001\u0000\u0000\u0000US\u0001\u0000\u0000\u0000UV\u0001\u0000"+
		"\u0000\u0000V\u000b\u0001\u0000\u0000\u0000WU\u0001\u0000\u0000\u0000"+
		"XY\u0005\u0004\u0000\u0000YZ\u0003\u001e\u000f\u0000Z\r\u0001\u0000\u0000"+
		"\u0000[`\u0003\u0010\b\u0000\\]\u0005\u0005\u0000\u0000]_\u0003\u0010"+
		"\b\u0000^\\\u0001\u0000\u0000\u0000_b\u0001\u0000\u0000\u0000`^\u0001"+
		"\u0000\u0000\u0000`a\u0001\u0000\u0000\u0000a\u000f\u0001\u0000\u0000"+
		"\u0000b`\u0001\u0000\u0000\u0000cd\u0003\u001a\r\u0000de\u0005\u0006\u0000"+
		"\u0000eg\u0001\u0000\u0000\u0000fc\u0001\u0000\u0000\u0000fg\u0001\u0000"+
		"\u0000\u0000gh\u0001\u0000\u0000\u0000hi\u0003\u0012\t\u0000i\u0011\u0001"+
		"\u0000\u0000\u0000jp\u0003\u0016\u000b\u0000kl\u0003\u0014\n\u0000lm\u0003"+
		"\u0016\u000b\u0000mo\u0001\u0000\u0000\u0000nk\u0001\u0000\u0000\u0000"+
		"or\u0001\u0000\u0000\u0000pn\u0001\u0000\u0000\u0000pq\u0001\u0000\u0000"+
		"\u0000q\u0013\u0001\u0000\u0000\u0000rp\u0001\u0000\u0000\u0000su\u0005"+
		"\b\u0000\u0000ts\u0001\u0000\u0000\u0000tu\u0001\u0000\u0000\u0000uv\u0001"+
		"\u0000\u0000\u0000vw\u0005\f\u0000\u0000wy\u0005\u000f\u0000\u0000xz\u0003"+
		"\u001a\r\u0000yx\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000\u0000z|\u0001"+
		"\u0000\u0000\u0000{}\u0003\u001c\u000e\u0000|{\u0001\u0000\u0000\u0000"+
		"|}\u0001\u0000\u0000\u0000}~\u0001\u0000\u0000\u0000~\u007f\u0005\u0010"+
		"\u0000\u0000\u007f\u0080\u0001\u0000\u0000\u0000\u0080\u0082\u0005\f\u0000"+
		"\u0000\u0081\u0083\u0005\n\u0000\u0000\u0082\u0081\u0001\u0000\u0000\u0000"+
		"\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u0015\u0001\u0000\u0000\u0000"+
		"\u0084\u0086\u0005\r\u0000\u0000\u0085\u0087\u0003\u001a\r\u0000\u0086"+
		"\u0085\u0001\u0000\u0000\u0000\u0086\u0087\u0001\u0000\u0000\u0000\u0087"+
		"\u0089\u0001\u0000\u0000\u0000\u0088\u008a\u0003\u001c\u000e\u0000\u0089"+
		"\u0088\u0001\u0000\u0000\u0000\u0089\u008a\u0001\u0000\u0000\u0000\u008a"+
		"\u008c\u0001\u0000\u0000\u0000\u008b\u008d\u0003\u0018\f\u0000\u008c\u008b"+
		"\u0001\u0000\u0000\u0000\u008c\u008d\u0001\u0000\u0000\u0000\u008d\u008e"+
		"\u0001\u0000\u0000\u0000\u008e\u008f\u0005\u000e\u0000\u0000\u008f\u0017"+
		"\u0001\u0000\u0000\u0000\u0090\u009d\u0005\u0011\u0000\u0000\u0091\u0092"+
		"\u0005 \u0000\u0000\u0092\u0093\u0005\u0013\u0000\u0000\u0093\u009a\u0003"+
		"\u001e\u000f\u0000\u0094\u0095\u0005\u0005\u0000\u0000\u0095\u0096\u0005"+
		" \u0000\u0000\u0096\u0097\u0005\u0013\u0000\u0000\u0097\u0099\u0003\u001e"+
		"\u000f\u0000\u0098\u0094\u0001\u0000\u0000\u0000\u0099\u009c\u0001\u0000"+
		"\u0000\u0000\u009a\u0098\u0001\u0000\u0000\u0000\u009a\u009b\u0001\u0000"+
		"\u0000\u0000\u009b\u009e\u0001\u0000\u0000\u0000\u009c\u009a\u0001\u0000"+
		"\u0000\u0000\u009d\u0091\u0001\u0000\u0000\u0000\u009d\u009e\u0001\u0000"+
		"\u0000\u0000\u009e\u009f\u0001\u0000\u0000\u0000\u009f\u00a0\u0005\u0012"+
		"\u0000\u0000\u00a0\u0019\u0001\u0000\u0000\u0000\u00a1\u00a2\u0005 \u0000"+
		"\u0000\u00a2\u001b\u0001\u0000\u0000\u0000\u00a3\u00a4\u0005\u0013\u0000"+
		"\u0000\u00a4\u00a5\u0005 \u0000\u0000\u00a5\u001d\u0001\u0000\u0000\u0000"+
		"\u00a6\u00ab\u0003 \u0010\u0000\u00a7\u00a8\u0005\u0014\u0000\u0000\u00a8"+
		"\u00aa\u0003 \u0010\u0000\u00a9\u00a7\u0001\u0000\u0000\u0000\u00aa\u00ad"+
		"\u0001\u0000\u0000\u0000\u00ab\u00a9\u0001\u0000\u0000\u0000\u00ab\u00ac"+
		"\u0001\u0000\u0000\u0000\u00ac\u001f\u0001\u0000\u0000\u0000\u00ad\u00ab"+
		"\u0001\u0000\u0000\u0000\u00ae\u00b3\u0003\"\u0011\u0000\u00af\u00b0\u0005"+
		"\u0015\u0000\u0000\u00b0\u00b2\u0003\"\u0011\u0000\u00b1\u00af\u0001\u0000"+
		"\u0000\u0000\u00b2\u00b5\u0001\u0000\u0000\u0000\u00b3\u00b1\u0001\u0000"+
		"\u0000\u0000\u00b3\u00b4\u0001\u0000\u0000\u0000\u00b4!\u0001\u0000\u0000"+
		"\u0000\u00b5\u00b3\u0001\u0000\u0000\u0000\u00b6\u00b8\u0005\u0016\u0000"+
		"\u0000\u00b7\u00b6\u0001\u0000\u0000\u0000\u00b8\u00bb\u0001\u0000\u0000"+
		"\u0000\u00b9\u00b7\u0001\u0000\u0000\u0000\u00b9\u00ba\u0001\u0000\u0000"+
		"\u0000\u00ba\u00bc\u0001\u0000\u0000\u0000\u00bb\u00b9\u0001\u0000\u0000"+
		"\u0000\u00bc\u00bd\u0003$\u0012\u0000\u00bd#\u0001\u0000\u0000\u0000\u00be"+
		"\u00c3\u0003&\u0013\u0000\u00bf\u00c0\u0007\u0000\u0000\u0000\u00c0\u00c2"+
		"\u0003&\u0013\u0000\u00c1\u00bf\u0001\u0000\u0000\u0000\u00c2\u00c5\u0001"+
		"\u0000\u0000\u0000\u00c3\u00c1\u0001\u0000\u0000\u0000\u00c3\u00c4\u0001"+
		"\u0000\u0000\u0000\u00c4%\u0001\u0000\u0000\u0000\u00c5\u00c3\u0001\u0000"+
		"\u0000\u0000\u00c6\u00cb\u0003(\u0014\u0000\u00c7\u00c8\u0007\u0001\u0000"+
		"\u0000\u00c8\u00ca\u0003(\u0014\u0000\u00c9\u00c7\u0001\u0000\u0000\u0000"+
		"\u00ca\u00cd\u0001\u0000\u0000\u0000\u00cb\u00c9\u0001\u0000\u0000\u0000"+
		"\u00cb\u00cc\u0001\u0000\u0000\u0000\u00cc\'\u0001\u0000\u0000\u0000\u00cd"+
		"\u00cb\u0001\u0000\u0000\u0000\u00ce\u00d3\u0003*\u0015\u0000\u00cf\u00d0"+
		"\u0007\u0002\u0000\u0000\u00d0\u00d2\u0003*\u0015\u0000\u00d1\u00cf\u0001"+
		"\u0000\u0000\u0000\u00d2\u00d5\u0001\u0000\u0000\u0000\u00d3\u00d1\u0001"+
		"\u0000\u0000\u0000\u00d3\u00d4\u0001\u0000\u0000\u0000\u00d4)\u0001\u0000"+
		"\u0000\u0000\u00d5\u00d3\u0001\u0000\u0000\u0000\u00d6\u00db\u0003,\u0016"+
		"\u0000\u00d7\u00d8\u0005\u0017\u0000\u0000\u00d8\u00da\u0003,\u0016\u0000"+
		"\u00d9\u00d7\u0001\u0000\u0000\u0000\u00da\u00dd\u0001\u0000\u0000\u0000"+
		"\u00db\u00d9\u0001\u0000\u0000\u0000\u00db\u00dc\u0001\u0000\u0000\u0000"+
		"\u00dc+\u0001\u0000\u0000\u0000\u00dd\u00db\u0001\u0000\u0000\u0000\u00de"+
		"\u00e2\u0003.\u0017\u0000\u00df\u00e0\u0007\u0001\u0000\u0000\u00e0\u00e2"+
		"\u0003.\u0017\u0000\u00e1\u00de\u0001\u0000\u0000\u0000\u00e1\u00df\u0001"+
		"\u0000\u0000\u0000\u00e2-\u0001\u0000\u0000\u0000\u00e3\u00e7\u00036\u001b"+
		"\u0000\u00e4\u00e6\u00030\u0018\u0000\u00e5\u00e4\u0001\u0000\u0000\u0000"+
		"\u00e6\u00e9\u0001\u0000\u0000\u0000\u00e7\u00e5\u0001\u0000\u0000\u0000"+
		"\u00e7\u00e8\u0001\u0000\u0000\u0000\u00e8/\u0001\u0000\u0000\u0000\u00e9"+
		"\u00e7\u0001\u0000\u0000\u0000\u00ea\u00ed\u00032\u0019\u0000\u00eb\u00ed"+
		"\u00034\u001a\u0000\u00ec\u00ea\u0001\u0000\u0000\u0000\u00ec\u00eb\u0001"+
		"\u0000\u0000\u0000\u00ed1\u0001\u0000\u0000\u0000\u00ee\u00ef\u0005\u001f"+
		"\u0000\u0000\u00ef\u00f0\u0005 \u0000\u0000\u00f03\u0001\u0000\u0000\u0000"+
		"\u00f1\u00f2\u0005\u000f\u0000\u0000\u00f2\u00f3\u0003\u001e\u000f\u0000"+
		"\u00f3\u00f4\u0005\u0010\u0000\u0000\u00f45\u0001\u0000\u0000\u0000\u00f5"+
		"\u00f9\u0003:\u001d\u0000\u00f6\u00f9\u0003\u001a\r\u0000\u00f7\u00f9"+
		"\u00038\u001c\u0000\u00f8\u00f5\u0001\u0000\u0000\u0000\u00f8\u00f6\u0001"+
		"\u0000\u0000\u0000\u00f8\u00f7\u0001\u0000\u0000\u0000\u00f97\u0001\u0000"+
		"\u0000\u0000\u00fa\u00fb\u0005\r\u0000\u0000\u00fb\u00fc\u0003\u001e\u000f"+
		"\u0000\u00fc\u00fd\u0005\u000e\u0000\u0000\u00fd9\u0001\u0000\u0000\u0000"+
		"\u00fe\u00ff\u0007\u0003\u0000\u0000\u00ff;\u0001\u0000\u0000\u0000\u001a"+
		"AGU`fpty|\u0082\u0086\u0089\u008c\u009a\u009d\u00ab\u00b3\u00b9\u00c3"+
		"\u00cb\u00d3\u00db\u00e1\u00e7\u00ec\u00f8";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}