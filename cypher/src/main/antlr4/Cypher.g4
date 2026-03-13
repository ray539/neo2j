parser grammar Cypher;
@header {
    package my.learning.generated;
}

options {
	tokenVocab = CypherLexer;
}

test: CREATE;

statement: clause+ EOF;
clause: createClause | matchClause | deleteClause | whereClause | returnClause;

createClause: CREATE patternList;
// could be patternList to match a 'cartesian product', but don't worry for now
matchClause: MATCH pattern;

// 'variable' could also be anything that evaluates to a node, relationship, path - path: runtime
// type symbolising bunch of nodes and relationships - when deleted, deletes all these stuff
deleteClause: DELETE variable (COMMA variable)*;

whereClause: WHERE expression;

returnClause: RETURN expression (COMMA expression)*;

patternList: pattern (COMMA pattern)*;
pattern: (variable EQ)? patternElement;

patternElement: (nodePattern (relationshipPattern nodePattern)*);

// semantic analysis: - if both end pointed, undirected - if one end pointed, directed - if no end
// pointed, undirected () - [r] -> ()
relationshipPattern:
	LT? SUB (LBRACKET variable? labelExpression? RBRACKET) SUB GT?;

nodePattern:
	LPAREN variable? labelExpression? properties? RPAREN;

properties:
	LCURLY (ID COLON expression (COMMA ID COLON expression)*)? RCURLY;

variable:
	ID; // can also be escaped using '`', but won't worry for now
// future: can be like : (A|B) & (C|D)
labelExpression: COLON ID;

// normal expression (which may evaluate to basically anything)
expression: expression11 (OR expression11)*;
expression11: expression9 (AND expression9)*;
expression9: NOT* expression8;
expression8:
	expression6 ((EQ | NE | LE | GE | LT | GT) expression6)*;
expression6: expression5 ((ADD | SUB) expression5)*;
expression5: expression4 ((MUL | DIV | PERCENT) expression4)*;
expression4: expression3 (POW expression3)*;
expression3: expression2 | (ADD | SUB) expression2;
expression2: expression1 postFix*;
postFix: property | index;
// | '[' expression ']' // for list indices. not going to care

property: DOT ID;
index: LBRACKET expression RBRACKET;

expression1: literal | variable | parenthesizedExpression;

parenthesizedExpression: '(' expression ')';

literal: STRING_LITERAL | INTEGER_LITERAL | TRUE | FALSE;