package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[tochka]
trait SeqField[T] extends Field {

  type FieldType = Seq[T]

  def /++(value: Seq[T]): Condition = new Condition(name $all value)

  def /+(value: Seq[T]): Condition = new Condition(name $in value)

  def /-(value: Seq[T]): Condition = new Condition(name $nin value)

}
