package orz.mongo.tochka.test

import scala.util.Random

import com.mongodb.casbah.Imports._

import orz.mongo.tochka._
import orz.mongo.tochka.test.util.Mongo

class NestCaseClassTest extends TestSuiteBase[Parent] {
  
  val testee = Seq(
    Parent("alpha", Child("spam")),
    Parent("bravo", Child("ham")),
    Parent("charlie", Child("eggs"))
  )
  
  test("insert") {
    Mongo.drive(conf) { implicit db =>
      val testee = Parent("foo", Child("foobar"))
      
      init(Seq(testee))
      
      info(s"Parent.findOne(_id $$eq ${testee._id}) @casbah")
      val casbah = db("Parent").findOne("_id" $eq testee._id)
      
      casbah match {
        case Some(casb) =>
          info(s"-> $casb")
          casb.get("_id") shouldEqual testee._id
          casb.get("name") shouldEqual testee.name
          casb.get("child") match {
            case child: DBObject =>
              child.get("name") shouldEqual testee.child.name
            case any =>
              fail(s"child type is not MongoDBObject: ${any}")
          }
        case None =>
          fail("find by casbah result is None")
      }
    }
  }
  
  test("find child.name eql ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(new Random().nextInt(testee.size))
      
      info(s"Parent.where(child.name == ${cond.child.name}).find")
      val result = Parent.where(_.child.name == cond.child.name).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => it.child.name == cond.child.name).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Parent.find(child.name $$eq ${cond.child.name}) @casbah")
      val casbah = db("Parent").find("child.name" $eq cond.child.name).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
}

@Entity
case class Child(name: String) {
  
  override def toString = s"Child(name=${name})"
  
}

@Entity
case class Parent(name: String, child: Child, _id: ObjectId = new ObjectId) {
  
  override def toString = s"Parent(name=${name}, child=${child})"
  
}

object Parent extends Schema[Parent] {
  
  case object name extends AnyRefField[String]
  
  case object child {
    
    case object name extends AnyRefField[String]("child")
    
  }
  
}
