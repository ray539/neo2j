lexer grammar CypherLexer;
@header {
    package my.learning.generated;
}

CREATE: 'CREATE';
MATCH: 'MATCH';
DELETE: 'DELETE';
WHERE: 'WHERE';
RETURN: 'RETURN';
COMMA: ',';
EQ: '=';
NE: '<>';
LT: '<';
LE: '<=';
GT: '>';
GE: '>=';
LPAREN: '(';
RPAREN: ')';
LBRACKET: '[';
RBRACKET: ']';
LCURLY: '{';
RCURLY: '}';
COLON: ':';
OR: 'OR';
AND: 'AND';
NOT: 'NOT';
POW: '^';
ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';
PERCENT: '%';
TRUE: 'TRUE';
FALSE: 'FALSE';
DOT: '.';

ID: [a-zA-Z][a-zA-Z0-9]*;
INTEGER_LITERAL: [0-9]+;
STRING_LITERAL: '"' (~["])* '"';
WS: [ \t]* -> skip;