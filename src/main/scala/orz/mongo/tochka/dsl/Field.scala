package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[dsl]
trait Field {

  type FieldType

  type EntityType

  protected
  implicit val TypeQueryParam = new AsQueryParam[FieldType] {
    def asQueryParam(a: FieldType) = identity(a)
  }

  val name = toString

  def eql(value: FieldType) = Condition[EntityType](name $eq value)

  def neq(value: FieldType) = Condition[EntityType](name $ne value)

}
