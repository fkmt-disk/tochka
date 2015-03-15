package orz.mongo.tochka.test

import scala.util.Random

import com.mongodb.casbah.Imports._

import orz.mongo.tochka._
import orz.mongo.tochka.test.util.Mongo

class StringFieldTest extends TestSuiteBase[Text] {
  
  val random = new Random
  
  val testee = Seq(
    Text("abc def ghi"),
    Text("def ghi jkl"),
    Text("ghi jkl mno")
  )
  
  test("insert") {
    Mongo.drive(conf) { implicit db =>
      val testee = Text("foo bar qux")
      
      init(Seq(testee))
      
      info(s"Text.findOne(_id $$eq ${testee._id}) @casbah")
      val casbah = db("Text").findOne("_id" $eq testee._id)
      
      casbah match {
        case Some(casb) =>
          info(s"-> $casb")
          casb.get("_id") shouldEqual testee._id
          casb.get("data") shouldEqual testee.data
        case None =>
          fail("find by casbah result is None")
      }
    }
  }
  
  test("find data eql ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(random.nextInt(testee.size))
      
      info(s"Text.where(data == ${cond.data}).find")
      val result = Text.where(_.data == cond.data).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => it.data == cond.data).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Text.find(data $$eq ${cond.data}) @casbah")
      val casbah = db("Text").find("data" $eq cond.data).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
  test("find data neq ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(random.nextInt(testee.size))
      
      info(s"Text.where(data != ${cond.data}).find")
      val result = Text.where(_.data != cond.data).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => it.data != cond.data).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Text.find(data $$ne ${cond.data}) @casbah")
      val casbah = db("Text").find("data" $ne cond.data).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
  test("find data regex ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = "def"
      
      info(s"Text.where(data =~ ${cond}).find")
      val result = Text.where(_.data =~ cond.r).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => cond.r.findFirstIn(it.data).isDefined).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Text.find(data $$eq ${cond}) @casbah")
      val casbah = db("Text").find("data" $eq cond.r).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
}

case class Text(data: String, _id: ObjectId = new ObjectId) {
  override def toString = s"Text(data=${data}, _id=${_id})"
}

object Text extends Schema[Text] {
  case object data extends StringField
  case object _id extends IdField
}
