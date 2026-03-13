package my.learning.app

import my.learning.cypher.Expression
import my.learning.cypher.LiteralExpression
import my.learning.cypher.IntLiteral
import my.learning.cypher.StringLiteral
import my.learning.cypher.BoolLiteral
import my.learning.cypher.MapLiteral
import my.learning.cypher.NodeRecord
import my.learning.cypher.RelationshipRecord
import my.learning.cypher.Path


// prints an LiteralExpression

// Node(:A, {x:1,...})
object CLIPrinter {
  def prettyPrint(expr: LiteralExpression): String = {
    expr match
      case IntLiteral(v) => v.toString()
      case StringLiteral(v) => s"\"$v\""
      case BoolLiteral(b) => if b then "true" else "false"
      case MapLiteral(mp) =>  {
        val pairs = mp.toList
        if pairs.size == 0 then
          "{}"
        else if pairs.size == 1 then
          val (k, v) = pairs(0)
          s"{$k:${prettyPrint(v)}}"
        else
          val (k, v) = pairs(0)
          s"{$k:${{prettyPrint(v)}},...}"
      }
      case NodeRecord(_, label, properties) => s"Node(:${label}, ${{prettyPrint(MapLiteral(properties))}})"
      case RelationshipRecord(_, label, properties, _, _) => s"Rel(:${label}, ${{prettyPrint(MapLiteral(properties))}})"
      case Path(relationships) => "Path"
      case _ => "<unprintable>"
  }


}
