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




class CypherRuntime {
  // store one instance of the backend
  val store = StorageEngine()
  def executeQuery(input: String) = {
    val statement = ASTParser.parseAST(input)
    // typechecking and semantic analysis...
    val ktx = KernelTransaction(store)
    val planBuilder = PlanBuilder(ktx)
    val plan = planBuilder.getPhysicalPlan(statement)
    val resRows = (for row <- plan yield row).toList
    ktx.commit()
    resRows
  }

}
