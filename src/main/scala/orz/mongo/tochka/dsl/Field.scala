package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[dsl]
trait Field {

  type FieldType

  val name = toString

  def eql(value: FieldType): MongoDBObject = name $eq value

  def neq(value: FieldType): MongoDBObject = name $ne value

  protected
  implicit val TypeQueryParam = new AsQueryParam[FieldType] {
    def asQueryParam(a: FieldType) = identity(a)
  }

}
