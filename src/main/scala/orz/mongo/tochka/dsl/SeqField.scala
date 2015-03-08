package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[tochka]
class SeqField[T](protected val __prefix: String = "") extends Field {
  
  protected
  type FieldType = Seq[T]
  
  def /++(value: Seq[T]): WhereClause = WhereClause(__name, "$all", value)
  
  def /+(value: Seq[T]): WhereClause = WhereClause(__name, "$in", value)
  
  def /-(value: Seq[T]): WhereClause = WhereClause(__name, "$nin", value)
  
}
