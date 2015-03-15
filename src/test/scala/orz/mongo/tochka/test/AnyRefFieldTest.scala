package orz.mongo.tochka.test

import java.util.Date
import java.text.SimpleDateFormat

import scala.util.Random

import com.mongodb.casbah.Imports._

import orz.mongo.tochka._
import orz.mongo.tochka.test.util.Mongo

class AnyRefFieldTest extends TestSuiteBase[Box] {

  val testee = Seq(
    Box("alpha", new Date),
    Box("bravo", new Date),
    Box("charlie", new Date)
  )
  
  implicit
  class DateW(date: Date) {
    def fmtStr: String = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date)
  }
  
  test("insert") {
    Mongo.drive(conf) { implicit db =>
      val testee = Box("moji", new Date)
      
      init(Seq(testee))
      
      info(s"Box.findOne(_id $$eq ${testee._id}) @casbah")
      val casbah = db("Box").findOne("_id" $eq testee._id)
      
      casbah match {
        case Some(casb) =>
          info(s"-> $casb")
          casb.get("_id") shouldEqual testee._id
          casb.get("text") shouldEqual testee.text
          casb.get("date") shouldEqual testee.date
        case None =>
          fail("find by casbah result is None")
      }
    }
  }
  
  test("find text eql ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(new Random().nextInt(testee.size))
      
      info(s"Box.where(text == ${cond.text}).find")
      val result = Box.where(_.text == cond.text).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.text == cond.text).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Box.find(text $$eq ${cond.text}) @casbah")
      val casbah = db("Box").find("text" $eq cond.text).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
  test("find text neq ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(new Random().nextInt(testee.size))
      
      info(s"Box.where(text != ${cond.text}).find")
      val result = Box.where(_.text != cond.text).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.text != cond.text).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Box.find(text $$ne ${cond.text}) @casbah")
      val casbah = db("Box").find("text" $ne cond.text).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
  test("find date eql ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(new Random().nextInt(testee.size))
      
      info(s"Box.where(date == ${cond.date.fmtStr}).find")
      val result = Box.where(_.date == cond.date).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.date == cond.date).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Box.find(date $$eq ${cond.date.fmtStr}) @casbah")
      val casbah = db("Box").find("date" $eq cond.date).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
  test("find date neq ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(new Random().nextInt(testee.size))
      
      info(s"Box.where(date != ${cond.date.fmtStr}).find")
      val result = Box.where(_.date != cond.date).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.date != cond.date).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Box.find(date $$ne ${cond.date.fmtStr}) @casbah")
      val casbah = db("Box").find("date" $ne cond.date).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
  test("find _id eql ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(new Random().nextInt(testee.size))
      
      info(s"Box.where(_id == ${cond._id}).find")
      val result = Box.where(_._id == cond._id).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_._id == cond._id).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Box.find(_id $$eq ${cond._id}) @casbah")
      val casbah = db("Box").find("_id" $eq cond._id).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
  test("find _id neq ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(new Random().nextInt(testee.size))
      
      info(s"Box.where(_id != ${cond._id}).find")
      val result = Box.where(_._id != cond._id).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_._id != cond._id).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Box.find(_id $$ne ${cond._id}) @casbah")
      val casbah = db("Box").find("_id" $ne cond._id).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
}

case class Box(text: String, date: Date, _id: ObjectId = new ObjectId) {
  
  private
  val fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date)
  
  override def toString = s"Box(text=${text}, date=${fmtDate}, _id=${_id})"
  
}

object Box extends Schema[Box] {
  
  case object text extends StringField
  
  case object date extends AnyRefField[Date]
  
  case object _id extends IdField
  
}
