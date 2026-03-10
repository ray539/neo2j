package my.learning.cypher

import scala.collection.mutable
import com.ibm.icu.impl.Relation
import com.ibm.icu.impl.locale.LocaleDistance.Data
import scala.collection.mutable.ListBuffer



/**
  * Pretend this interacts with the actual database and does no error checking
  */
class StorageEngine {
  val idToNodeRecord: mutable.Map[Int, NodeRecord] = mutable.Map()
  val idToRelationshipRecord: mutable.Map[Int, RelationshipRecord] = mutable.Map()

  val nodeToOutgoing: mutable.Map[Int, mutable.Set[Int]] = mutable.Map()
  val nodeToIncoming: mutable.Map[Int, mutable.Set[Int]] = mutable.Map()

  private var nextId = 0;

  def createNode(node: NodeRecord) = {
    idToNodeRecord(node.id) = node
  }

  def deleteNode(nodeId: Int) = {
    idToNodeRecord.remove(nodeId)
  }

  def createRelationship(rel: RelationshipRecord) = {
    idToRelationshipRecord(rel.id) = rel
    nodeToOutgoing(rel.startNode).add(rel.id)
    nodeToIncoming(rel.endNode).add(rel.id)
  }

  def deleteRelationship(relId: Int) = {
    val rel = getRel(relId).get
    nodeToOutgoing(rel.startNode).remove(relId)
    nodeToIncoming(rel.endNode).remove(relId)
    idToRelationshipRecord.remove(relId)
  }

  def getNode(id: Int) = idToNodeRecord.get(id)

  def getRel(id: Int) = idToRelationshipRecord.get(id)

  def getNextId = {
    val tmp = nextId;
    nextId += 1;
    tmp;
  }
}

class NodeCursor {
  var _it: Iterator[Int] = Iterator()
  def hasNext(): Boolean = _it.hasNext
  def getNodeReference(): Int = _it.next()
}

class RelationshipCursor {
  var _it: Iterator[Int] = Iterator()
  def hasNext(): Boolean = _it.hasNext
  def getRelationshipReference(): Int = _it.next()
}

// tracks state
// - this is basically a 'diff set' from the existing database
// - when we do deletes and creations, don't do IMMEDIATELY on
//   StorageEngine
// - do it here, and upon committing we 
class TxState(store: StorageEngine) {
  private val nodeIdsToDel: mutable.Set[Int] = mutable.Set()
  private val idToNodeToCreate: mutable.Map[Int, NodeRecord] = mutable.Map()

  private val relIdsToDel: mutable.Set[Int] = mutable.Set()
  private val idToRelToCreate: mutable.Map[Int, RelationshipRecord] = mutable.Map()

  private val nodeToEffectiveOutDegree: mutable.Map[Int, Int] = mutable.Map()
  private val nodeToEffectiveInDegree: mutable.Map[Int, Int] = mutable.Map()

  def getNodesToCreate() = {
    idToNodeToCreate.values.toList
  }

  def getNodesToDelete() = {
    nodeIdsToDel.toList
  }

  def getEffectiveOutDegree(nodeId: Int) = {
    // if newly created, showed show up in 'nodeToEffectiveOutDegree'
    // else we are enquiring about 
    if nodeToEffectiveOutDegree.contains(nodeId) then
      nodeToEffectiveOutDegree(nodeId)
    else if store.nodeToOutgoing.contains(nodeId) then
      val outDegree = store.nodeToOutgoing(nodeId).size
      nodeToEffectiveOutDegree(nodeId) = outDegree
      outDegree
    else
      throw new Exception("getEffectiveOutDegree: invalid state")
  }

  def getEffectiveInDegree(nodeId: Int) = {
    nodeToEffectiveInDegree.getOrElseUpdate(nodeId, store.nodeToIncoming(nodeId).size)
  }

  def getEffectiveDegree(nodeId: Int) = getEffectiveInDegree(nodeId) + getEffectiveOutDegree(nodeId)

  def _createNode(node: NodeRecord) = {
    assert(!idToNodeToCreate.contains(node.id))
    idToNodeToCreate(node.id) = node
    nodeToEffectiveInDegree(node.id) = 0
    nodeToEffectiveOutDegree(node.id) = 0
  }

  def _uncreateNode(nodeId: Int) = {
    assert(idToNodeToCreate.contains(nodeId))
    idToNodeToCreate.remove(nodeId)
    nodeToEffectiveInDegree.remove(nodeId)
    nodeToEffectiveOutDegree.remove(nodeId)
  }

  def _deleteNodeFromStore(nodeId: Int) = {
    assert(store.idToNodeRecord.contains(nodeId), "deleting an invalid node")
    assert(!nodeIdsToDel.contains(nodeId), "node is already deleted")
    nodeIdsToDel.add(nodeId)
  }


  def _getNodeRecord(nodeId: Int) = {
    if store.idToNodeRecord.contains(nodeId) then store.idToNodeRecord.get(nodeId)
    else idToNodeToCreate.get(nodeId)
  }


  def markNodeDeleted(nodeId: Int) = {
    println(s"tx.markNodeDeleted ${nodeId}")
    if getEffectiveDegree(nodeId) > 0 then {
      throw Exception("runtime error: can't delete node with relationships")
    }

    if idToNodeToCreate.contains(nodeId) then {
      // delete a previously created node
      _uncreateNode(nodeId)
    } else {
      // not in our newly created nodes, so in store
      _deleteNodeFromStore(nodeId)
    }
  }

  def createNode(label: String, props: Map[String, LiteralExpression]) = {
    val newId = store.getNextId
    println(s"tx.createNode ${newId}")

    val nodeRecord = NodeRecord(newId, label, props)
    _createNode(nodeRecord)
    newId
  }

  def _createRel(rel: RelationshipRecord) = {
    assert(!idToRelToCreate.contains(rel.id))
    idToRelToCreate(rel.id) = rel
  }

  def _uncreateRel(relId: Int) = {
    assert(idToRelToCreate.contains(relId))
    idToRelToCreate.remove(relId)
  }

  def _deleteRelFromStore(relId: Int) = {
    assert(store.idToRelationshipRecord.contains(relId), "deleting invalid relationship from store")
    assert(!relIdsToDel.contains(relId), "relationship already deleted")

    relIdsToDel.add(relId)
  }

  def _getRelRecord(relId: Int) = {
    if store.idToRelationshipRecord.contains(relId) then store.idToRelationshipRecord.get(relId)
    else idToRelToCreate.get(relId)
  }

  def markRelDeleted(relId: Int) = {
    assert(idToNodeToCreate.contains(relId) || store.getRel(relId).isDefined)
    if idToRelToCreate.contains(relId) then {
      _uncreateRel(relId)
    } else {
      _deleteRelFromStore(relId)
    }

    val rr = _getRelRecord(relId).get
    nodeToEffectiveOutDegree(rr.startNode) = getEffectiveOutDegree(rr.startNode) - 1
    nodeToEffectiveInDegree(rr.endNode) = getEffectiveInDegree(rr.endNode) - 1
    
  }

  def createRel(label: String, props: Map[String, LiteralExpression], startNode: Int, endNode: Int) = {
    val newId = store.getNextId
    val rel = RelationshipRecord(newId, label, props, startNode, endNode)
    
    _createRel(rel)
    nodeToEffectiveOutDegree(rel.startNode) = getEffectiveOutDegree(rel.startNode) + 1
    nodeToEffectiveInDegree(rel.endNode) = getEffectiveInDegree(rel.endNode) + 1
    newId
  }

  def apply(store: StorageEngine) = {
    // apply everything to the actual store
    // - we modify relationships before nodes for obvious reasons
    // - vallidity checks already checked above, so don't need to worry... (in theory)
    for rr <- idToRelToCreate.values do {
      store.createRelationship(rr)
    }
    for rId <- relIdsToDel do {
      store.deleteRelationship(rId)
    }

    for nr <- idToNodeToCreate.values do {
      store.createNode(nr)
    }
    for nId <- nodeIdsToDel do {
      store.deleteNode(nId)
    }
  }
}


// we use composition pattern to divide `KernelTransaction` into 2 smaller groups of functions
// DataRead: functions related to reading
class DataRead(store: StorageEngine, txState: TxState) {
  // wire cursor to correct source in storage engine where it can
  // iterate through the ndoes
  def allNodesScan(cursor: NodeCursor) = {
    cursor._it = store.idToNodeRecord.keys.iterator
  }

  def relsAdjToNodeScan(cursor: RelationshipCursor, nodeId: Int, dir: RelDir) = {
    val outgoing = store.nodeToOutgoing(nodeId).iterator
    val incoming = store.nodeToIncoming(nodeId).iterator
    cursor._it = dir match {
      case RelDir.Forward => outgoing
      case RelDir.Backward => incoming
      case RelDir.Both => outgoing.concat(incoming)
    }
  }
  // return the node record
  def nodeById(id: Int) = store.idToNodeRecord(id)
  // return the relationship record
  def relationshipById(id: Int) = store.idToRelationshipRecord(id)
}

class DataWrite(store: StorageEngine, txState: TxState) {
  // these functions must change txState only
  def nodeCreate(label: String, props: Map[String, LiteralExpression]) = {
    txState.createNode(label, props)
  }

  def nodeDelete(nodeId: Int) = {
    txState.markNodeDeleted(nodeId)
  }

  def relationshipCreate(label: String, props: Map[String, LiteralExpression], startNode: Int, endNode: Int) = {
    txState.createRel(label, props, startNode, endNode)
  }

  def relationshipDelete(relId: Int) = {
    txState.markRelDeleted(relId)
  }
}

class KernelTransaction(store: StorageEngine) {

  private val txState: TxState = TxState(store)
  val readApi: DataRead = DataRead(store, txState)
  val writeApi: DataWrite = DataWrite(store, txState)

  def getNodeCursor(): NodeCursor = NodeCursor()
  def getRelationshipCursor(): RelationshipCursor = RelationshipCursor()

  def commit() = {
    // apply all the chages in txState to the actual store
    txState.apply(store)
  }
}
