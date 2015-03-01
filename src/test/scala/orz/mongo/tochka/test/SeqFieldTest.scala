package orz.mongo.tochka.test

import com.mongodb.casbah.Imports._

import orz.mongo.tochka._
import orz.mongo.tochka.test.util.Mongo

class SeqFieldTest extends TestSuiteBase[Texts] {
  
  val testee = Seq(
    Texts(Seq("a", "b", "c")),
    Texts(Seq("b", "c", "d")),
    Texts(Seq("c", "d", "e"))
  )
  
  test("insert") {
    Mongo.drive(conf) { implicit db =>
      val testee = Texts(Seq("x", "y", "z"))
      
      init(Seq(testee))
      
      info(s"Texts.findOne(_id $$eq ${testee._id}) @casbah")
      val casbah = db("Texts").findOne("_id" $eq testee._id)
      
      casbah match {
        case Some(casb) =>
          info(s"-> $casb")
          casb.get("_id") shouldEqual testee._id
          casb.get("list").asInstanceOf[BasicDBList].toList shouldEqual testee.list
        case None =>
          fail("find by casbah result is None")
      }
    }
  }

  test("find list all ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = Seq("b", "c")
      
      info(s"Texts.where(list /++ $cond).find")
      val result = Texts.where(_.list /++ cond).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => (it.list intersect cond).size == cond.size).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Texts.find(list $$all $cond) @casbah")
      val casbah = db("Texts").find("list" $all cond).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find list in ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = Seq("a", "e")
      
      info(s"Texts.where(list /+ $cond).find")
      val result = Texts.where(_.list /+ cond).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => (it.list intersect cond).size > 0).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Texts.find(list $$in $cond) @casbah")
      val casbah = db("Texts").find("list" $in cond).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find list not-in ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = Seq("a", "e")
      
      info(s"Texts.where(list /- $cond).find")
      val result = Texts.where(_.list /- cond).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => (it.list intersect cond).size == 0).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Texts.find(list $$nin $cond) @casbah")
      val casbah = db("Texts").find("list" $nin cond).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

}

@Entity
case class Texts(list: Seq[String], _id: ObjectId = new ObjectId) {
  
  override def toString = s"Texts(list=${list}, _id=${_id})"
  
}

object Texts extends Schema[Texts] {

  case object list extends SeqField[String]

  case object _id extends AnyRefField[ObjectId]

}
