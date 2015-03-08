package orz.mongo.tochka.dsl

import scala.util.matching.Regex

import com.mongodb.casbah.Imports._

private[tochka]
class TextField(protected val __prefix: String = "") extends Field {
  
  protected
  type FieldType = String
  
  def =~(value: Regex): WhereClause = WhereClause(__name, "$regex", value)
  
}
