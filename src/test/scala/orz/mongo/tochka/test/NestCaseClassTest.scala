package orz.mongo.tochka.test

import scala.language.implicitConversions

import scala.util.Random

import com.mongodb.casbah.Imports._

import orz.mongo.tochka._
import orz.mongo.tochka.conv._
import orz.mongo.tochka.test.util.Mongo

class NestCaseClassTest extends TestSuiteBase[Parent] {
  
  implicit def toOpt[T](any: Any): Option[T] = Option(any.asInstanceOf[T])
  
  val testee = Seq(
    Parent("alpha", Child("spam", "hoge")),
    Parent("bravo", Child("ham", "hoge")),
    Parent("charlie", Child("eggs", "fuga"))
  )
  
  test("insert") {
    Mongo.drive(conf) { implicit db =>
      val testee = Parent("foo", Child("foobar", "bazqux"))
      
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
              child.get("name") shouldEqual testee.child.get.name
              child.get("tag") shouldEqual testee.child.get.tag
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
      
      info(s"Parent.where(child.name == ${cond.child.get.name}).find")
      val result = Parent.where(_.child.name == cond.child.get.name).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => it.child.get.name == cond.child.get.name).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Parent.find(child.name $$eq ${cond.child.get.name}) @casbah")
      val casbah = db("Parent").find("child.name" $eq cond.child.get.name).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
  test("find child.tag neq ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(new Random().nextInt(testee.size))
      
      info(s"Parent.where(child.tag != ${cond.child.get.tag}).find")
      val result = Parent.where(_.child.tag != cond.child.get.tag).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => it.child.get.tag != cond.child.get.tag).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Parent.find(child.tag $$ne ${cond.child.get.tag}) @casbah")
      val casbah = db("Parent").find("child.tag" $ne cond.child.get.tag).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
  test("find child eql None") {
    Mongo.drive(conf) { implicit db =>
      val cond = Parent("delta", None)
      
      init(testee :+ cond)
      
      info(s"Parent.where(child == ${cond.child}).find")
      val result = Parent.where(_.child == cond.child).findOne.get
      info(s"-> $result")
      
      result shouldEqual cond
      
      info(s"Parent.findOne(child $$eq None) @casbah")
      // なんだこの書き方...ありえねぇ
      val casbah = db("Parent").findOne(DBObject("child" -> DBObject("$eq" -> cond.child))).get
      info(s"-> $casbah")
      
      assertEquals2casbah(Seq(result), Seq(casbah))
    }
  }
  
  test("find child eql ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(new Random().nextInt(testee.size))
      
      info(s"Parent.where(child == ${cond.child}).find")
      val result = Parent.where(_.child == cond.child).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => it.child == cond.child).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Parent.find($$and(child.name $$eq ${cond.child.get.name}, child.tag $$eq ${cond.child.get.tag})) @casbah")
      val casbah = db("Parent").find($and("child.name" $eq cond.child.get.name, "child.tag" $eq cond.child.get.tag)).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
  test("find child neq ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(new Random().nextInt(testee.size))
      
      info(s"Parent.where(child != ${cond.child}).find")
      val result = Parent.where(_.child != cond.child).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => it.child != cond.child).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Parent.find($$or(child.name $$ne ${cond.child.get.name}, child.tag $$ne ${cond.child.get.tag})) @casbah")
      val casbah = db("Parent").find($or("child.name" $ne cond.child.get.name, "child.tag" $ne cond.child.get.tag)).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
}

case class Child(name: String, tag: String) {
  
  override def toString = s"Child(name=${name}, tag=${tag})"
  
}

case class Parent(name: String, child: Option[Child], _id: ObjectId = new ObjectId) {
  
  override def toString = s"Parent(name=${name}, child=${child})"
  
}

object Parent extends Schema[Parent] {
  
  case object name extends StringField
  
  case object child extends AnyRefField[Child] {
    
    case object name extends StringField("child")
    
    case object tag extends StringField("child")
    
  }
  
}
