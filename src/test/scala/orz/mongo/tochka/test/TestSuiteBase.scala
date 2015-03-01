package orz.mongo.tochka.test

import scala.language.reflectiveCalls

import scala.reflect.runtime.universe._

import org.scalatest._

import com.mongodb.casbah.Imports._

import com.typesafe.config.ConfigFactory

import orz.mongo.tochka.util.ReflectionUtil._

abstract
class TestSuiteBase[T: TypeTag] extends FunSuite with Matchers {
  
  val conf = ConfigFactory.load
  
  val testee: Seq[T]
  
  private
  val comp = typeOf[T].getCompanion
  
  private
  val name = comp.getName
  
  private
  val instance = comp.reflectInstance
  
  private
  val drop = comp.toType.getMember("drop")
  
  private
  val insert = comp.toType.getMember("insert")
  
  def init(initial: Seq[T] = testee)(implicit db: MongoDB) {
    info(s"${name}.drop")
    instance.invoke(drop, db)
    db(name).find.count shouldEqual 0
    
    info(s"${name}.insert")
    initial.foreach { it =>
      info(s"<- $it")
      instance.invoke(insert, it, db)
    }
  }
  
  type IDer = {
    val _id: ObjectId
  }
  
  def assertEquals2casbah[E <: IDer](tochka: Seq[E], casbah: Seq[DBObject]) {
    tochka.size shouldEqual casbah.size
    tochka.map(_._id).sorted shouldEqual casbah.map(_.get("_id").asInstanceOf[ObjectId]).sorted
  }
  
}
