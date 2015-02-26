package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[tochka] abstract
trait SeqField[T] extends Field  {

  type FieldType = Seq[T]

  def all(value: Seq[T]): MongoDBObject = name $all value

  def in(value: Seq[T]): MongoDBObject = name $in value

  def nin(value: Seq[T]): MongoDBObject = name $nin value

}
