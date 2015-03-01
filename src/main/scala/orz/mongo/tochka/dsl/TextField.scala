package orz.mongo.tochka.dsl

import scala.util.matching.Regex

import com.mongodb.casbah.Imports._

private[tochka]
class TextField(val prefix: String = "") extends Field {

  type FieldType = String

  def =~(value: Regex): WhereClause = new WhereClause(name $eq value)

}
