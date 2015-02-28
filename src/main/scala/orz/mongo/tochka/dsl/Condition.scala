package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[dsl]
class Condition(val value: DBObject) {

  def &&(cond: Condition): Condition = new Condition($and(value, cond.value))

  def ||(cond: Condition): Condition = new Condition($or(value, cond.value))

}
