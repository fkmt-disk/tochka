package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[dsl]
class WhereClause(val value: DBObject) {

  def &&(clause: WhereClause): WhereClause = new WhereClause($and(value, clause.value))

  def ||(clause: WhereClause): WhereClause = new WhereClause($or(value, clause.value))

}
