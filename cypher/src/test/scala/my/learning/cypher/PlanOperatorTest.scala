package my.learning.cypher

import org.scalatest.funsuite.AnyFunSuite

class PlanOperatorTest extends AnyFunSuite {

  test("all nodes scan") {
    val store = StorageEngine()
    val ktx = KernelTransaction(store)
    ktx.writeApi.nodeCreate("A", Map(("x", IntLiteral(0))))
    ktx.writeApi.nodeCreate("A", Map(("x", IntLiteral(1))))
    ktx.writeApi.nodeCreate("A", Map(("x", IntLiteral(2))))
    ktx.commit()

    for row <- AllNodesScan("a", ktx) do {
      println(row)
    }
  }

  test("all nodes scan and cproduct") {
    val store = StorageEngine()
    val ktx1 = KernelTransaction(store)
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(0))))
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(1))))
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(2))))
    ktx1.commit()

    val ktx2 = KernelTransaction(store)
    val plan = CProduct(
      EmptyRow(),
      AllNodesScan("a", ktx2)
    )
    for row <- plan do {
      println(row)
    }
  }

  test("expand operator") {
    val store = StorageEngine()
    val ktx1 = KernelTransaction(store)
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(0))))
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(1))))
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(2))))
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(3))))

    ktx1.writeApi.relationshipCreate("R", Map(), 0, 1)
    ktx1.writeApi.relationshipCreate("R", Map(), 0, 2)
    ktx1.writeApi.relationshipCreate("R", Map(), 0, 3)
    ktx1.commit()
    val ktx2 = KernelTransaction(store)
    val plan = 
      Expand(
        "a0",
        "r",
        RelDir.Forward,
        AllNodesScan("a0", ktx2),
        ktx2
      )
    for row <- plan do {
      println(row)
    }
  }

  test("simple match statement") {
    val store = StorageEngine()
    val ktx1 = KernelTransaction(store)
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(0))))
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(1))))
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(2))))
    ktx1.writeApi.nodeCreate("B", Map(("x", IntLiteral(3))))
    ktx1.writeApi.nodeCreate("B", Map(("x", IntLiteral(3))))
    ktx1.writeApi.nodeCreate("B", Map(("x", IntLiteral(3))))

    ktx1.writeApi.relationshipCreate("R", Map(), 0, 1)
    ktx1.writeApi.relationshipCreate("R", Map(), 0, 2)
    ktx1.writeApi.relationshipCreate("R", Map(), 0, 3)
    ktx1.commit()

    val ktx2 = KernelTransaction(store)
    val planBuilder = PlanBuilder(ktx2)
    val stmt1 = ASTParser.parseAST("MATCH (a:A)")

    val plan = planBuilder.getPhysicalPlan(stmt1)
    println(PlanPrinter.print(plan))

    for row <- plan do {
      println(row)
    }
  }

  test("match statement with relationships") {
    val store = StorageEngine()
    val ktx1 = KernelTransaction(store)
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(0))))
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(1))))
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(2))))
    ktx1.writeApi.relationshipCreate("R", Map(), 0, 1)
    ktx1.writeApi.relationshipCreate("R", Map(), 0, 2)
    ktx1.writeApi.relationshipCreate("R", Map(), 2, 0)
    ktx1.commit()

    val ktx2 = KernelTransaction(store)
    val planBuilder = PlanBuilder(ktx2)
    val stmt1 = ASTParser.parseAST("MATCH (a0:A {x: 0})") //  - [] -> (a1:A{x:1})")

    val plan = planBuilder.getPhysicalPlan(stmt1)
    println(PlanPrinter.print(plan))

    for row <- plan do {
      println(row)
    }
  }

  test("direction expansion correct - forward") {
    val store = StorageEngine()
    val ktx1 = KernelTransaction(store)
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(0))))
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(1))))
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(2))))
    ktx1.writeApi.relationshipCreate("R", Map(), 0, 1)
    ktx1.writeApi.relationshipCreate("R", Map(), 0, 2)
    ktx1.writeApi.relationshipCreate("R", Map(), 2, 0)
    ktx1.commit()

    val ktx2 = KernelTransaction(store)
    val planBuilder = PlanBuilder(ktx2)
    val stmt1 = ASTParser.parseAST("MATCH (a0:A {x: 0}) - [] -> (a1)")
    val plan = planBuilder.getPhysicalPlan(stmt1)
    println(PlanPrinter.print(plan))
    for row <- plan do {
      println(row)
    }
  }

  test("direction expansion correct - backward") {
    val store = StorageEngine()
    val ktx1 = KernelTransaction(store)
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(0))))
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(1))))
    ktx1.writeApi.nodeCreate("A", Map(("x", IntLiteral(2))))
    ktx1.writeApi.relationshipCreate("R", Map(), 0, 1)
    ktx1.writeApi.relationshipCreate("R", Map(), 0, 2)
    ktx1.writeApi.relationshipCreate("R", Map(), 2, 0)
    ktx1.commit()

    val ktx2 = KernelTransaction(store)
    val planBuilder = PlanBuilder(ktx2)
    val stmt1 = ASTParser.parseAST("MATCH (a0:A {x: 0}) <- [] - (a1)")
    val plan = planBuilder.getPhysicalPlan(stmt1)
    println(PlanPrinter.print(plan))
    for row <- plan do {
      println(row)
    }
  }
}
