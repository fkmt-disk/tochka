package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[dsl]
class WhereClause private(val value: DBObject) {
  
  def &&(clause: WhereClause): WhereClause = new WhereClause($and(value, clause.value))
  
  def ||(clause: WhereClause): WhereClause = new WhereClause($or(value, clause.value))
  
}

private[dsl]
object WhereClause {
  
  def apply(name: String, ope: String, value: Any): WhereClause =
    new WhereClause(DBObject(name -> DBObject(ope -> value)))
  
  def apply(dbo: DBObject): WhereClause = new WhereClause(dbo)
  
}
