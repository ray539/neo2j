package my.learning.cypher

import org.scalatest.funsuite.AnyFunSuite

class IntegrationTest extends AnyFunSuite {

  def print_rows(rows: List[Map[String, LiteralExpression]]) = {
    println(s"${rows.size} rows")
    for row <- rows do println(row)
    println()
  }

  test("random") {
    val app = CypherRuntime()
    print_rows(app.executeQuery("CREATE ({x:1})"))
    print_rows(app.executeQuery("CREATE ({x:2})"))
    print_rows(app.executeQuery("MATCH (n) RETURN n"))
    print_rows(
      app.executeQuery(
        "MATCH (n1 {x:1}) MATCH (n2{x:2}) CREATE (n1) - [r:R] -> (n2) RETURN n1, r, n2"
      )
    )
    print_rows(
      app.executeQuery(
        "MATCH (n1{x:1}) MATCH (n2{x:2}) CREATE (n1) - [r:R] -> (n2) RETURN n1, n2, r"
      )
    )

    print_rows(
      app.executeQuery("MATCH (n1{x:1}) RETURN n1")
    )

    print_rows(
      app.executeQuery("MATCH (n1{x:1}) MATCH(n2 {x:2}) RETURN n1, n2")
    )

    print_rows(
      app.executeQuery("MATCH (n1{x:1}) MATCH(n2 {x:2}) MATCH (n1) - [r] -> (n2) RETURN r")
    )

    print_rows(
      app.executeQuery("MATCH () - [r] -> () RETURN r")
    )
  }

  test("loop") {
    val app = CypherRuntime()
    print_rows(app.executeQuery("CREATE ({x:1}) CREATE ({x:2})"))
    print_rows(app.executeQuery("MATCH (n {x:1}) CREATE (n) - [r:R] -> (n) RETURN r"))
    print_rows(app.executeQuery("MATCH (n {x:1}) MATCH (n2 {x:2}) CREATE (n) - [r:R] -> (n2) RETURN r"))

    print_rows(app.executeQuery("MATCH (n {x:1}) MATCH (n) - [r:R] -> (n) RETURN r"))
  }

  test("pattern") {
    val app = CypherRuntime()
    print_rows(app.executeQuery("CREATE ({x:1})"))
    print_rows(app.executeQuery("CREATE ({x:2})"))
    print_rows(
      app.executeQuery(
        "MATCH (n1 {x:1}) MATCH (n2{x:2}) CREATE (n1) - [r:R] -> (n2)"
      )
    )
    print_rows(app.executeQuery("MATCH p = () - [r] -> () RETURN p"))
  }

  test("return node property") {
    val app = CypherRuntime()
    print_rows(app.executeQuery("CREATE ({x:1}) CREATE ({x:2})"))
    print_rows(app.executeQuery("MATCH (n) RETURN n.x"))
    print_rows(app.executeQuery("MATCH (n) RETURN n.x ^ 2 % 4"))
  }


  test("string type") {
    val app = CypherRuntime()
    print_rows(app.executeQuery("CREATE ({x: \"a\", y: \"b\"})"))
    print_rows(app.executeQuery("CREATE ({x: \"c\", y: \"d\"})"))
    print_rows(app.executeQuery("MATCH (n) RETURN n.x + n.y"))
  }

  test("where") {
    val app = CypherRuntime()
    print_rows(app.executeQuery("CREATE ({x:1}) CREATE ({x:2}) CREATE ({x:3})"))
    print_rows(app.executeQuery("MATCH (n) WHERE n.x >= 2 RETURN n"))
    print_rows(app.executeQuery("MATCH (n {x:1}) WHERE n.x >= 2 RETURN n"))
  }

  test("long relationship") {
    val app = CypherRuntime()
    print_rows(app.executeQuery("CREATE (a) <- [:R] - (b) - [:R] -> (c)", showPlan=true))
  }

  test("long relationship 2") {
    val app = CypherRuntime()
    print_rows(app.executeQuery("CREATE (xyz) <- [:WORKS_FOR] - (sally) - [:LIKES] -> (integrations) <- [:LIKES] - (dan)", showPlan=true, showast = true))
  }

  test("long relationship 3") {
    val app = CypherRuntime()
    print_rows(app.executeQuery("CREATE (a) <- [:R1] - (b) - [:R2] -> (c) <- [:R3] - (d)", showPlan=true))
  }
}
