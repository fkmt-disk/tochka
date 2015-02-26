package orz.mongo.tochka

import org.scalatest._
import org.bson.types.ObjectId
import com.mongodb.casbah.Imports._
import com.typesafe.config.ConfigFactory
import orz.mongo.tochka.test.Mongo

class CharSeqTest extends FunSuite with Matchers with BeforeAndAfterAll {

  var testee = Seq(
    CharSeq(Seq('a', 'b', 'c')),
    CharSeq(Seq('b', 'c', 'd')),
    CharSeq(Seq('c', 'd', 'e')),
    CharSeq(Seq('d', 'e', 'f'))
  )

  val conf = ConfigFactory.load

  override def beforeAll {
    Mongo.drive(conf) { implicit db =>
      CharSeq.drop
      testee.foreach(CharSeq.insert)
    }
  }

  test("chars $all Seq(a,b,c)") {
    Mongo.drive(conf) { implicit db =>
      val condition = "abc".toCharArray

      val tochka = CharSeq.find(_.chars all condition)

      tochka.size shouldBe 1
      tochka(0) shouldEqual testee(0)

      val casbah = db("CharSeq").find("chars" $all condition).toList

      tochka.size shouldBe casbah.size

      val tochkaIds = tochka.map(_._id.get).sorted
      val casbahIds = casbah.map(_.get("_id").asInstanceOf[ObjectId]).sorted

      tochkaIds shouldEqual casbahIds

      info(s"casbah:all id=$casbahIds")
      info(s"tochka:all id=$tochkaIds")
    }
  }

  test("chars $all Seq(b,c)") {
    Mongo.drive(conf) { implicit db =>
      val condition = "bc".toCharArray

      val tochka = CharSeq.find(_.chars all condition)

      tochka.size shouldBe 2
      assert(tochka.find(_ == testee(0)).isDefined)
      assert(tochka.find(_ == testee(1)).isDefined)

      val casbah = db("CharSeq").find("chars" $all condition).toList

      tochka.size shouldBe casbah.size

      val casbahIds = casbah.map(_.get("_id").asInstanceOf[ObjectId]).sorted
      val tochkaIds = tochka.map(_._id.get).sorted

      tochkaIds shouldEqual casbahIds

      info(s"casbah:all id=$casbahIds")
      info(s"tochka:all id=$tochkaIds")
    }
  }

  test("chars $all Seq(c)") {
    Mongo.drive(conf) { implicit db =>
      val condition = Seq('c')

      val tochka = CharSeq.find(_.chars all condition)

      tochka.size shouldBe 3
      assert(tochka.find(_ == testee(0)).isDefined)
      assert(tochka.find(_ == testee(1)).isDefined)
      assert(tochka.find(_ == testee(2)).isDefined)

      val casbah = db("CharSeq").find("chars" $all condition).toList

      tochka.size shouldBe casbah.size

      val casbahIds = casbah.map(_.get("_id").asInstanceOf[ObjectId]).sorted
      val tochkaIds = tochka.map(_._id.get).sorted

      tochkaIds shouldEqual casbahIds

      info(s"casbah:all id=$casbahIds")
      info(s"tochka:all id=$tochkaIds")
    }
  }

  test("chars $all Seq(f,e,d)") {
    Mongo.drive(conf) { implicit db =>
      val condition = "fed".toCharArray

      val tochka = CharSeq.find(_.chars all condition)

      tochka.size shouldBe 1
      tochka(0) shouldEqual testee(3)

      val casbah = db("CharSeq").find("chars" $all condition).toList

      tochka.size shouldBe casbah.size

      val casbahIds = casbah.map(_.get("_id").asInstanceOf[ObjectId]).sorted
      val tochkaIds = tochka.map(_._id.get).sorted

      tochkaIds shouldEqual casbahIds

      info(s"casbah:all id=$casbahIds")
      info(s"tochka:all id=$tochkaIds")
    }
  }

  test("chars $all Seq(a,b,d)") {
    Mongo.drive(conf) { implicit db =>
      val condition = "abd".toCharArray

      val tochka = CharSeq.find(_.chars all condition)

      tochka.size shouldBe 0

      val casbah = db("CharSeq").find("chars" $all condition).toList

      tochka.size shouldBe casbah.size
    }
  }

}

@Entity
case class CharSeq(chars: Seq[Char], _id: Option[ObjectId] = Some(new ObjectId))

object CharSeq extends Schema[CharSeq] {

  case object chars extends SeqField[Char]

  case object _id extends AnyRefField[Option[ObjectId]]

}