package orz.mongo.tochka.suite

import com.mongodb.casbah.Imports._

import com.typesafe.config.ConfigFactory

import orz.mongo.tochka._
import orz.mongo.tochka.test.Mongo

class BoolFieldTest extends FieldTest[Flags] {
  
  val conf = ConfigFactory.load
  
  val testee = Seq(Flags(false), Flags(true), Flags(false))
  
  test("insert") {
    Mongo.drive(conf) { implicit db =>
      val testee = Flags(true)
      
      init(Seq(testee))
      
      info(s"Flags.findOne(_id $$eq ${testee._id}) @casbah")
      val casbah = db("Flags").findOne("_id" $eq testee._id)
      
      casbah match {
        case Some(casb) =>
          info(s"-> $casb")
          casb.get("_id") shouldEqual testee._id
          casb.get("flag") shouldEqual testee.flag
        case None =>
          fail("find by casbah result is None")
      }
    }
  }
  
  test("find flag eql ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = true
      
      info(s"Flags.find(flag == $cond)")
      val result = Flags.find(_.flag == cond)
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.flag == cond).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Flags.find(flag $$eq $cond) @casbah")
      val casbah = db("Flags").find("flag" $eq cond).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
  test("find flag neq ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = true
      
      info(s"Flags.find(flag != $cond)")
      val result = Flags.find(_.flag != cond)
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.flag != cond).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Flags.find(flag $$ne $cond) @casbah")
      val casbah = db("Flags").find("flag" $ne cond).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
}

@Entity
case class Flags(flag: Boolean, _id: ObjectId = new ObjectId) {
  
  override def toString = s"Flags(flag=${flag}, _id=${_id})"
  
}

object Flags extends Schema[Flags] {
  
  case object flag extends BoolField
  
}
