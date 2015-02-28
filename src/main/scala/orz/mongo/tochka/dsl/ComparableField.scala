package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[dsl]
trait ComparableField extends Field {

  def <(value: FieldType): Condition = new Condition(name $lt value)

  def <=(value: FieldType): Condition = new Condition(name $lte value)

  def >(value: FieldType): Condition = new Condition(name $gt value)

  def >=(value: FieldType): Condition = new Condition(name $gte value)

}
