package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[tochka]
class SeqField[T](val prefix: String = "") extends Field {

  type FieldType = Seq[T]

  def /++(value: Seq[T]): WhereClause = new WhereClause(name $all value)

  def /+(value: Seq[T]): WhereClause = new WhereClause(name $in value)

  def /-(value: Seq[T]): WhereClause = new WhereClause(name $nin value)

}
