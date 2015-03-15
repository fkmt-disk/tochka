package orz.mongo.tochka.dsl

import scala.util.matching.Regex

private[tochka]
class StringField(protected val __prefix: String = "") extends Field {
  
  protected
  type FieldType = String
  
  def =~(value: Regex): WhereClause = WhereClause(__name, "$regex", value)
  
}
