package orz.mongo.tochka.test

import scala.util.Random

import com.mongodb.casbah.Imports._

import orz.mongo.tochka._
import orz.mongo.tochka.test.util.Mongo

class NumberFieldTest extends TestSuiteBase[Numbers] {

  val testee = Seq(
    Numbers(10, 100L, 0.01d),
    Numbers(20, 200L, 0.02d),
    Numbers(30, 300L, 0.03d),
    Numbers(40, 400L, 0.04d),
    Numbers(50, 500L, 0.05d)
  )

  val randomIndex = new Random().nextInt(testee.size)

  test("insert") {
    Mongo.drive(conf) { implicit db =>
      val testee = Numbers(Int.MaxValue, Long.MaxValue, Double.MaxValue)
      
      init(Seq(testee))
      
      info(s"Numbers.findOne(_id $$eq ${testee._id}) @casbah")
      val casbah = db("Numbers").findOne("_id" $eq testee._id)
      
      casbah match {
        case Some(casb) =>
          info(s"-> $casb")
          casb.get("_id") shouldEqual testee._id
          casb.get("int") shouldEqual testee.int
          casb.get("long") shouldEqual testee.long
          casb.get("double") shouldEqual testee.double
        case None =>
          fail("find by casbah result is None")
      }
    }
  }

  test("find int eql ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(randomIndex)
      
      info(s"Numbers.where(int == ${cond.int}).find")
      val result = Numbers.where(_.int == cond.int).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.int == cond.int).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Numbers.find(int $$eq ${cond.int}) @casbah")
      val casbah = db("Numbers").find("int" $eq cond.int).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find int neq ?)") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(randomIndex)
      
      info(s"Numbers.where(int != ${cond.int}).find")
      val result = Numbers.where(_.int != cond.int).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.int != cond.int).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Numbers.find(int $$ne ${cond.int}) @casbah")
      val casbah = db("Numbers").find("int" $ne cond.int).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find int lt ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee.sortBy(_.int).apply(testee.size / 2)
      
      info(s"Numbers.where(int < ${cond.int}).find")
      val result = Numbers.where(_.int < cond.int).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.int < cond.int).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_.int) shouldEqual expect
      
      info(s"Numbers.find(int $$lt ${cond.int}) @casbah")
      val casbah = db("Numbers").find("int" $lt cond.int).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find int lte ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee.sortBy(_.int).apply(testee.size / 2)
      
      info(s"Numbers.where(int <= ${cond.int}).find")
      val result = Numbers.where(_.int <= cond.int).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.int <= cond.int).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_.int) shouldEqual expect
      
      info(s"Numbers.find(int $$lte ${cond.int}) @casbah")
      val casbah = db("Numbers").find("int" $lte cond.int).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find int gt ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee.sortBy(_.int).apply(testee.size / 2)
      
      info(s"Numbers.where(int > ${cond.int}).find")
      val result = Numbers.where(_.int > cond.int).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.int > cond.int).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_.int) shouldEqual expect
      
      info(s"Numbers.find(int $$gt ${cond.int}) @casbah")
      val casbah = db("Numbers").find("int" $gt cond.int).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find int gte ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee.sortBy(_.int).apply(testee.size / 2)
      
      info(s"Numbers.where(int >= ${cond.int}).find")
      val result = Numbers.where(_.int >= cond.int).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.int >= cond.int).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_.int) shouldEqual expect
      
      info(s"Numbers.find(int $$gte ${cond.int}) @casbah")
      val casbah = db("Numbers").find("int" $gte cond.int).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find long eql ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(randomIndex)
      
      info(s"Numbers.where(long == ${cond.long}).find")
      val result = Numbers.where(_.long == cond.long).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.long == cond.long).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Numbers.find(long $$eq ${cond.long}) @casbah")
      val casbah = db("Numbers").find("long" $eq cond.long).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find long neq ?)") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(randomIndex)
      
      info(s"Numbers.where(long != ${cond.long}).find")
      val result = Numbers.where(_.long != cond.long).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.long != cond.long).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Numbers.find(long $$ne ${cond.long}) @casbah")
      val casbah = db("Numbers").find("long" $ne cond.long).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find long lt ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee.sortBy(_.long).apply(testee.size / 2)
      
      info(s"Numbers.where(long < ${cond.long}).find")
      val result = Numbers.where(_.long < cond.long).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.long < cond.long).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_.long) shouldEqual expect
      
      info(s"Numbers.find(long $$lt ${cond.long}) @casbah")
      val casbah = db("Numbers").find("long" $lt cond.long).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find long lte ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee.sortBy(_.long).apply(testee.size / 2)
      
      info(s"Numbers.where(long <= ${cond.long}).find")
      val result = Numbers.where(_.long <= cond.long).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.long <= cond.long).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_.long) shouldEqual expect
      
      info(s"Numbers.find(long $$lte ${cond.long}) @casbah")
      val casbah = db("Numbers").find("long" $lte cond.long).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find long gt ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee.sortBy(_.long).apply(testee.size / 2)
      
      info(s"Numbers.where(long > ${cond.long}).find")
      val result = Numbers.where(_.long > cond.long).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.long > cond.long).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_.long) shouldEqual expect
      
      info(s"Numbers.find(long $$gt ${cond.long}) @casbah")
      val casbah = db("Numbers").find("long" $gt cond.long).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find long gte ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee.sortBy(_.long).apply(testee.size / 2)
      
      info(s"Numbers.where(long >= ${cond.long}).find")
      val result = Numbers.where(_.long >= cond.long).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.long >= cond.long).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_.long) shouldEqual expect
      
      info(s"Numbers.find(long $$gte ${cond.long}) @casbah")
      val casbah = db("Numbers").find("long" $gte cond.long).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find double eql ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(randomIndex)
      
      info(s"Numbers.where(double == ${cond.double}).find")
      val result = Numbers.where(_.double == cond.double).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.double == cond.double).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Numbers.find(double $$eq ${cond.double}) @casbah")
      val casbah = db("Numbers").find("double" $eq cond.double).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find double neq ?)") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(randomIndex)
      
      info(s"Numbers.where(double != ${cond.double}).find")
      val result = Numbers.where(_.double != cond.double).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.double != cond.double).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Numbers.find(double $$ne ${cond.double}) @casbah")
      val casbah = db("Numbers").find("double" $ne cond.double).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find double lt ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee.sortBy(_.double).apply(testee.size / 2)
      
      info(s"Numbers.where(double < ${cond.double}).find")
      val result = Numbers.where(_.double < cond.double).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.double < cond.double).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_.double) shouldEqual expect
      
      info(s"Numbers.find(double $$lt ${cond.double}) @casbah")
      val casbah = db("Numbers").find("double" $lt cond.double).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find double lte ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee.sortBy(_.double).apply(testee.size / 2)
      
      info(s"Numbers.where(double <= ${cond.double}).find")
      val result = Numbers.where(_.double <= cond.double).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.double <= cond.double).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_.double) shouldEqual expect
      
      info(s"Numbers.find(double $$lte ${cond.double}) @casbah")
      val casbah = db("Numbers").find("double" $lte cond.double).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find double gt ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee.sortBy(_.double).apply(testee.size / 2)
      
      info(s"Numbers.where(double > ${cond.double}).find")
      val result = Numbers.where(_.double > cond.double).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.double > cond.double).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_.double) shouldEqual expect
      
      info(s"Numbers.find(double $$gt ${cond.double}) @casbah")
      val casbah = db("Numbers").find("double" $gt cond.double).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

  test("find double gte ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee.sortBy(_.double).apply(testee.size / 2)
      
      info(s"Numbers.where(double >= ${cond.double}).find")
      val result = Numbers.where(_.double >= cond.double).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(_.double >= cond.double).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_.double) shouldEqual expect
      
      info(s"Numbers.find(double $$gte ${cond.double}) @casbah")
      val casbah = db("Numbers").find("double" $gte cond.double).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }

}

@Entity
case class Numbers(int: Int, long: Long, double: Double, _id: ObjectId = new ObjectId) {
  
  override def toString = s"Numbers(int=${int}, long=${long}, double=${double}, _id=${_id})"
  
}

object Numbers extends Schema[Numbers] {

  case object int extends IntField

  case object long extends LongField

  case object double extends DoubleField

  case object _id extends AnyRefField[ObjectId]

}
