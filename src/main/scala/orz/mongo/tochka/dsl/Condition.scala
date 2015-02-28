package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[dsl]
class Condition(val value: DBObject) {

  def &&(next: Condition): Condition = new Condition($and(value, next.value))

  def ||(next: Condition): Condition = new Condition($or(value, next.value))

}
