package my.learning.cypher

import org.scalatest.funsuite.AnyFunSuite

class PlanBuilderTest extends AnyFunSuite {
  test("plan creation is sane - create query") {
    val stmt1 = TestHelper.statementFromString("CREATE (a:A) - [r1] -> (b:A) - [r2] -> (c:A)")
    val store = StorageEngine()
    val ktx = KernelTransaction(store)
    val planBuilder = PlanBuilder(ktx)

    val printer = ASTPrinter()
    stmt1.accept(printer)


    val tmp = planBuilder.getPhysicalPlan(stmt1)
    val res = PlanPrinter.print(tmp)
    println(res)
  }

  test("plan creation is sane - match query") {
    val stmt1 = TestHelper.statementFromString("MATCH (a:A) - [r1] -> (b:A) - [r2] -> (c:A)")
    val store = StorageEngine()
    val ktx = KernelTransaction(store)
    val planBuilder = PlanBuilder(ktx)

    val printer = ASTPrinter()
    stmt1.accept(printer)

    val tmp = planBuilder.getPhysicalPlan(stmt1)
    val res = PlanPrinter.print(tmp)
    println(res)
  }
}
