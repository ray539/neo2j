package my.learning.cypher

import org.scalatest.funsuite.AnyFunSuite


class TxStateTest extends AnyFunSuite {
  test("txstate - nodes") {
    val store = StorageEngine()
    val tx = TxState(store)
    tx.createNode("A", Map(("x", IntLiteral(0))))
    tx.createNode("A", Map(("x", IntLiteral(1))))
    tx.createNode("A", Map(("x", IntLiteral(2))))
    tx.apply(store)
    println(store.idToNodeRecord.keys)

    tx.createNode("A", Map(("x", IntLiteral(3))))
    tx.createNode("A", Map(("x", IntLiteral(4))))
    tx.createNode("A", Map(("x", IntLiteral(5))))

    tx.markNodeDeleted(3)
    tx.markNodeDeleted(4)
    tx.apply(store)
    println(store.idToNodeRecord.keys)
  }

  test("txstate - duplicate delete") {
    val store = StorageEngine()
    val tx = TxState(store)
    tx.createNode("A", Map(("x", IntLiteral(0))))
    tx.apply(store)
    tx.markNodeDeleted(0)
    assertThrows(tx.markNodeDeleted(0))
  }

  test("txstate - duplicate delete 2") {
    val store = StorageEngine()
    val tx = TxState(store)
    tx.createNode("A", Map(("x", IntLiteral(0))))
    tx.markNodeDeleted(0)
    assertThrows(tx.markNodeDeleted(0))
  }

  test("txstate - relationships") {
    val store = StorageEngine()
    val tx = TxState(store)
    tx.createNode("A", Map(("x", IntLiteral(0))))
    tx.createNode("A", Map(("x", IntLiteral(1))))
    tx.createNode("A", Map(("x", IntLiteral(2))))

    tx.apply(store)

    tx.createRel("R", Map(), 0, 1)
    tx.createRel("R", Map(), 0, 2)
    println(tx.getEffectiveOutDegree(0))
    println(tx.getEffectiveInDegree(0))
  }

  test("txstate - dependent relationship") {
    val store = StorageEngine()
    val tx = TxState(store)
    tx.createNode("A", Map(("x", IntLiteral(0))))
    tx.createNode("A", Map(("x", IntLiteral(1))))
    tx.createNode("A", Map(("x", IntLiteral(2))))
    tx.apply(store)
    tx.createRel("R", Map(), 0, 1)
    println(tx.getEffectiveOutDegree(0))
    println(tx.getEffectiveInDegree(0))

    assertThrows[Exception](tx.markNodeDeleted(0))
  }
}
