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
  def executeQuery(input: String, showPlan: Boolean = false, showast: Boolean = false) = {
    val statement = ASTParser.parseAST(input)

    if showast then
      println(statement.accept(ASTPrinter()))

    // typechecking and semantic analysis...
    val ktx = KernelTransaction(store)
    val planBuilder = PlanBuilder(ktx)
    val plan = planBuilder.getPhysicalPlan(statement)

    if showPlan then
      println(PlanPrinter.print(plan))

    val resRows = (for row <- plan yield row).toList
    ktx.commit()
    resRows
  }

}
