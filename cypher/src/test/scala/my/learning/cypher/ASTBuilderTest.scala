package my.learning.cypher

import org.scalatest.funsuite.AnyFunSuite
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.gui.Trees
import my.learning.cypher.*;
import my.learning.generated.*;
import my.learning.generated.Cypher.*;

// case object TestHelper {
//   def statementFromString(input: String) = {
//     val _input: CharStream = CharStreams.fromString(input)
//     val lexer: CypherLexer = CypherLexer(_input)
//     val tokens = CommonTokenStream(lexer)

//     val parser = Cypher(tokens)
//     parser.setErrorHandler(new BailErrorStrategy())

//     val statementCtx = parser.statement().asInstanceOf[StatementContext]
//     val sb = StatementBuilder()
//     sb.visitStatement(statementCtx)
//   }
// }

class ExampleTest extends AnyFunSuite {

  test("create stmt") {
    val stmt1 = ASTParser.parseAST(
      "CREATE (a:A) - [r1] -> (b:A) - [r2] -> (c:A)"
    )
    val printer = ASTPrinter()
    val x = stmt1.accept(printer)
    println(x)
  }

  test("temp") {
    val stmt1 = ASTParser.parseAST("CREATE (a:A)")
    val printer = ASTPrinter()
    val x = stmt1.accept(printer)
    println(x)
  }

  test("create where") {
    val where = ASTParser.parseAST("WHERE a >= 2 AND 1 + 2 = 3")
    val printer = ASTPrinter()
    val x = where.accept(printer)
    println(x)
  }

  test("create properties") {
    val input = ASTParser.parseAST("CREATE ({x: 0})")
    val printer = ASTPrinter()
    val x = input.accept(printer)
    println(x)
  }

  test("return") {
    val input = ASTParser.parseAST("RETURN a,b,c")
    val printer = ASTPrinter()
    val x = input.accept(printer)
    println(x)
  }

  test("named create") {
    val input = ASTParser.parseAST("CREATE(n:A{x:1}) RETURN n")
    val printer = ASTPrinter()
    val x = input.accept(printer)
    println(x)
  }

  test("return expression") {
    val input = ASTParser.parseAST("CREATE(n:A{x:1}) RETURN n.x")
    val printer = ASTPrinter()
    val x = input.accept(printer)
    println(x)
  }

  test("invalid") {
    assertThrows(ASTParser.parseAST("asd"))
  }

  test("underscore") {
    val input = ASTParser.parseAST("CREATE (xyz) <- [:WORKS_FOR] - (sally)")
    val printer = ASTPrinter()
    val x = input.accept(printer)
    println(x)
  }

  test("invalid characters") {
    assertThrows(ASTParser.parseAST("你好"))
  }


}
