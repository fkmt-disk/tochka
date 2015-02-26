package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[dsl]
trait ComparableField extends Field {

  type FieldType

  val name: String

  def lt(value: FieldType): MongoDBObject = name $lt value

  def lte(value: FieldType): MongoDBObject = name $lte value

  def gt(value: FieldType): MongoDBObject = name $gt value

  def gte(value: FieldType): MongoDBObject = name $gte value

}
