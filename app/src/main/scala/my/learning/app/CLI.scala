import my.learning.cypher.*;
import scala.io.StdIn.readLine
import org.scalatest.tools.PrettyPrinter
import my.learning.app.CLIPrinter

def centerPad(s: String, totalWidth: Int, padChar: Char): String = {
  val currentLength = s.length
  if (currentLength >= totalWidth) {
    s // Return original string if it's already long enough or longer
  } else {
    val totalPadding = totalWidth - currentLength
    val leftPaddingLength = totalPadding / 2
    val rightPaddingLength =
      totalPadding - leftPaddingLength // Handles odd padding lengths

    val leftPad = padChar.toString * leftPaddingLength
    val rightPad = padChar.toString * rightPaddingLength

    leftPad + s + rightPad
  }
}

@main
def CLI(): Unit = {
  val app = CypherRuntime()

  println()
  println("====================================")
  println("      neo2j Command Line")
  println("====================================")
  println("Type a Cypher query and press Enter.")
  println("Type 'exit' or 'quit' to leave.")
  println()

  while true do
    print("neo2j> ")
    val input = readLine()

    if input == null then
      println("goodbye")
      return

    input.trim().toLowerCase() match
      case "quit" | "exit" => {
        println("goodbye")
        return
      }
      case _ => {}

    try
      val res = app.executeQuery(input)
      val numRows = res.size
      println()
      if numRows == 0 then
        println(
          "no rows"
        )
      else if res(0).size == 0 then
        println(
          "no columns"
        )
      else
        val cols = res(0).keySet.toList

        // default type is null
        val table = Array.ofDim[String](numRows + 1, cols.size)

        for colIdx <- (0 until cols.size) do table(0)(colIdx) = cols(colIdx)

        for rowIdx <- (1 to numRows) do
          for colIdx <- (0 until cols.size) do
            table(rowIdx)(colIdx) =
              CLIPrinter.prettyPrint(res(rowIdx - 1)(cols(colIdx)))

        val colSizes = Array.ofDim[Int](cols.size)

        for colIdx <- (0 until cols.size) do
          for rowIdx <- (0 to numRows) do
            colSizes(colIdx) =
              Math.max(table(rowIdx)(colIdx).size, colSizes(colIdx))

        for rowIdx <- (0 to numRows) do
          for colIdx <- (0 until cols.size) do
            print("+")
            print("-" * (colSizes(colIdx) + 2))
          print("+\n")

          for colIdx <- (0 until cols.size) do
            print("| ")
            print(centerPad(table(rowIdx)(colIdx), colSizes(colIdx), ' '))
            print(" ")
          print("|\n")

        for colIdx <- (0 until cols.size) do
          print("+")
          print("-" * (colSizes(colIdx) + 2))
        print("+\n")

        println(s"${res.size} rows")

      println()

    catch
      case e: Throwable => {
        println()
        println("Error:")
        println(e.getMessage)
        println()
      }

}
