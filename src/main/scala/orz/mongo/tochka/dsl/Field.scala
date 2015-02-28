package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[dsl]
trait Field {

  type FieldType

  val name = toString

  def ==(value: FieldType): Condition = new Condition(name $eq value)

  def !=(value: FieldType): Condition = new Condition(name $ne value)

  protected implicit
  val TypeQueryParam = new AsQueryParam[FieldType] {
    def asQueryParam(a: FieldType) = identity(a)
  }

}
