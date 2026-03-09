import org.scalatest.funsuite.AnyFunSuite
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import my.learning.generated.*
import my.learning.generated.Cypher.StatementContext
import my.learning.cypher.*


case object TestHelper {
  def statementFromString(input: String) = {
    val _input: CharStream = CharStreams.fromString(input)
    val lexer: CypherLexer = CypherLexer(_input)
    val tokens = CommonTokenStream(lexer)
    val parser = Cypher(tokens)
    val statementCtx = parser.statement().asInstanceOf[StatementContext]
    val sb = StatementBuilder()
    sb.visitStatement(statementCtx)
  }
}

class ExampleTest extends AnyFunSuite {

  test("create stmt") {
    val stmt1 = TestHelper.statementFromString("CREATE (a:A) - [r1] -> (b:A) - [r2] -> (c:A)")
    val printer = ASTPrinter()
    val x = stmt1.accept(printer)
    println(x)
  }

  test("create where") {
    val where = TestHelper.statementFromString("WHERE a >= 2 AND 1 + 2 = 3")
    val printer = ASTPrinter()
    val x = where.accept(printer)
    println(x)
  }

}