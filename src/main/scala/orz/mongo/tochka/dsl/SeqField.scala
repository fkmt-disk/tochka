package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[tochka]
abstract
class SeqField[E, T] extends Field  {

  type FieldType = T

  type EntityType = E

  def all(value: T) = Condition[E](name $all value)

  def in(value: T) = Condition[E](name $in value)

  def nin(value: T) = Condition[E](name $nin value)

}
