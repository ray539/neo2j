import my.learning.cypher.*;
import scala.io.StdIn.readLine

@main
def CLI() = {
  // val statementBuilder = StatementBuilder()
  val app = CypherRuntime()
  while true do {
    println("enter query you would like to execute: ")
    val input = readLine()
    try {
      val res = app.executeQuery(input)
      println("result")
      for row <- res do {
        println(row)
      }
      println(s"${res.size} rows")
    } catch {
      case e: Throwable => {
        println(e)
      }
    }
  }

}
