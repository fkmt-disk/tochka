package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[tochka]
abstract
class NumberField[E, T <: Number] extends Field {

  type FieldType = T

  type EntityType = E

  def lt(value: FieldType) = Condition[EntityType](name $lt value)

  def lte(value: FieldType) = Condition[EntityType](name $lte value)

  def gt(value: FieldType) = Condition[EntityType](name $gt value)

  def gte(value: FieldType) = Condition[EntityType](name $gte value)

}
