package my.learning.cypher
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import my.learning.generated.CypherListener
import my.learning.generated.CypherLexer
import my.learning.generated.Cypher
import my.learning.generated.CypherBaseListener
import my.learning.generated.Cypher.StatementContext;
import scala.collection.mutable

object RuntimeHelper {
  def getStatementAST(input: String) = {
    val cs: CharStream = CharStreams.fromString(input)
    val lexer: CypherLexer = CypherLexer(cs)
    val tokens: CommonTokenStream = CommonTokenStream(lexer)
    val parser: Cypher = Cypher(tokens)
    val tree: ParseTree = parser.statement(); // build tree traversing statements

    val sb = StatementBuilder() // 
    sb.visitStatement(tree.asInstanceOf[StatementContext])
  }
}


class CypherRuntime {
  // store one instance of the backend
  val store = StorageEngine()
  def executeQuery(input: String) = {
    val statement = RuntimeHelper.getStatementAST(input)
    // typechecking and semantic analysis...
    val ktx = KernelTransaction(store)
    val planBuilder = PlanBuilder(ktx)
    val plan = planBuilder.getPhysicalPlan(statement)
    val resRows = (for row <- plan yield row).toList
    resRows
  }

}
