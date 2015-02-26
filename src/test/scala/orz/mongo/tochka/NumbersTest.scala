package orz.mongo.tochka

import com.mongodb.casbah.Imports._
import com.typesafe.config.ConfigFactory
import org.bson.types.ObjectId
import org.scalatest._
import orz.mongo.tochka.test.Mongo

class NumbersTest extends FunSuite with Matchers with BeforeAndAfterAll {

  val conf = ConfigFactory.load

  override protected def beforeAll {
    Mongo.drive(conf) { implicit db =>
      Numbers.drop
    }
  }

  test("insert") {
    Mongo.drive(conf) { implicit db =>
      val testee = Numbers(Int.MaxValue, Long.MaxValue, Double.MaxValue)
      Numbers.insert(testee)

      val result = db("Numbers").findOne("_id" $eq testee._id)

      result.get("int") shouldEqual testee.int
      result.get("long") shouldEqual testee.long
      result.get("double") shouldEqual testee.double
    }
  }

  test("find(eql)") {
    Mongo.drive(conf) { implicit db =>
      val testee = {
        val dbo = MongoDBObject.newBuilder
        dbo += "int" -> Int.MinValue
        dbo += "long" -> Long.MinValue
        dbo += "double" -> Double.MinValue
        dbo += "_id" -> new ObjectId
        dbo.result
      }

      db("Numbers").insert(testee)

      val result = Numbers.find(_._id eql testee.get("_id").asInstanceOf[ObjectId])

      result.size shouldBe 1

      result.head.int shouldEqual testee.get("int")
      result.head.long shouldEqual testee.get("long")
      result.head.double shouldEqual testee.get("double")
    }
  }

  test("find(neq)") {
    Mongo.drive(conf) { implicit db =>
      {
        val max = DBObject("int" -> Int.MaxValue) ++ DBObject("long" -> Long.MaxValue) ++ DBObject("double" -> Double.MaxValue)
        val min = DBObject("int" -> Int.MinValue) ++ DBObject("long" -> Long.MinValue) ++ DBObject("double" -> Double.MinValue)
        val seq = DBObject("int" -> 1) ++ DBObject("long" -> 2L) ++ DBObject("double" -> 3d)

        val coll = db("Numbers")

        coll.insert(max)
        coll.insert(min)
        coll.insert(seq)
      }

      val list = db("Numbers").find.toList

      val id = list.head.get("_id").asInstanceOf[ObjectId]

      val result = Numbers.find(_._id neq id)

      result.size shouldEqual list.size - 1

      assert( result.map(_._id).find(_ == id).isEmpty )

      //info(s"list.size=${list.size}, result.size=${result.size}")
    }
  }

}

@Entity
case class Numbers(int: Int, long: Long, double: Double, _id: ObjectId = new ObjectId)

object Numbers extends Schema[Numbers] {

  case object int extends IntField

  case object long extends LongField

  case object double extends DoubleField

  case object _id extends AnyRefField[ObjectId]

}
