package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[dsl]
trait Field {

  type FieldType

  val prefix: String

  val name = Option(prefix).map(_.trim) match {
    case Some(pref) if pref.length > 0 =>
      s"${pref}.${toString}"
    case _ =>
      toString
  }

  def ==(value: FieldType): WhereClause = new WhereClause(name $eq value)

  def !=(value: FieldType): WhereClause = new WhereClause(name $ne value)

  def :=(value: FieldType): (String, FieldType) = name -> value

  protected implicit
  val TypeQueryParam = new AsQueryParam[FieldType] {
    def asQueryParam(a: FieldType) = identity(a)
  }

}
