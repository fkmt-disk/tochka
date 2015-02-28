package orz.mongo.tochka

import scala.util.Random
import com.mongodb.casbah.Imports._
import com.typesafe.config.ConfigFactory
import org.bson.types.ObjectId
import org.scalatest._
import orz.mongo.tochka.test.Mongo

class NumbersTest extends FunSuite with Matchers with BeforeAndAfterAll {

  val testee = {
    val r = new Random
    for (_ <- 1 to 6) yield Numbers( r.nextInt, r.nextLong, r.nextDouble )
  }

  val random = new Random

  def init(implicit db: MongoDB) {
    info("Numbers.drop")
    Numbers.drop
    
    info("insret testee into Numbers")
    testee.foreach(Numbers.insert)
    
    testee.foreach(n => info(n.toString))
  }

  def eauqlsCasbahResult(actual: Seq[Numbers], expect: Seq[DBObject]) {
    actual.size shouldEqual expect.size
    actual.map(_._id).sorted shouldEqual expect.map(_.get("_id").asInstanceOf[ObjectId]).sorted
  }

  val conf = ConfigFactory.load

  //---------------------------------------------------------------------------- insert
  test("insert") {
    Mongo.drive(conf) { implicit db =>
      info("Numbers.drop")
      Numbers.drop
      
      val sample = Numbers(Int.MaxValue, Long.MaxValue, Double.MaxValue)
      
      info(s"Numbers.insert($sample)")
      Numbers.insert(sample)
      
      val result = db("Numbers").findOne("_id" $eq sample._id)
      
      info(s"find(_id $$eq ${sample._id}) by casbah")
      info(result.toString)
      
      result.get("int") shouldEqual sample.int
      result.get("long") shouldEqual sample.long
      result.get("double") shouldEqual sample.double
      result.get("_id") shouldEqual sample._id
    }
  }

  //---------------------------------------------------------------------------- find all
  test("find all") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val result = Numbers.find
      
      info("Numbers.find all")
      result.foreach(n => info(n.toString))

      val expect = testee.sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find.toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  //---------------------------------------------------------------------------- find by int
  test("find(int eql ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee(random.nextInt(testee.size))
      
      val result = Numbers.find(_.int == sample.int)
      
      info(s"Numbers.find(int eql ${sample.int})")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.int == sample.int).sortBy(_.int)
      
      result.size shouldBe expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("int" $eq sample.int).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  test("find(int neq ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee(random.nextInt(testee.size))
      
      val result = Numbers.find(_.int != sample.int)
      
      info(s"Numbers.find(int neq ${sample.int})")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.int != sample.int).sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("int" $ne sample.int).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  test("find(int lt ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee.map(_.int).sorted.apply(testee.size / 2)
      
      val result = Numbers.find(_.int < sample)
      
      info(s"Numbers.find(int lt $sample)")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.int < sample).sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("int" $lt sample).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  test("find(int lte ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee.map(_.int).sorted.apply(testee.size / 2)
      
      val result = Numbers.find(_.int <= sample)
      
      info(s"Numbers.find(int lte $sample)")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.int <= sample).sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("int" $lte sample).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  test("find(int gt ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee.map(_.int).sorted.apply(testee.size / 2)
      
      val result = Numbers.find(_.int > sample)
      
      info(s"Numbers.find(int gt $sample)")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.int > sample).sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("int" $gt sample).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  test("find(int gte ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee.map(_.int).sorted.apply(testee.size / 2)
      
      val result = Numbers.find(_.int >= sample)
      
      info(s"Numbers.find(int gte $sample)")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.int >= sample).sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("int" $gte sample).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  //---------------------------------------------------------------------------- find by long
  test("find(long eql ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee(random.nextInt(testee.size))
      
      val result = Numbers.find(_.long == sample.long)
      
      info(s"Numbers.find(long eql ${sample.long})")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.long == sample.long).sortBy(_.int)
      
      result.size shouldBe expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("long" $eq sample.long).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  test("find(long neq ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee(random.nextInt(testee.size))
      
      val result = Numbers.find(_.long != sample.long)
      
      info(s"Numbers.find(long neq ${sample.long})")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.long != sample.long).sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("long" $ne sample.long).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  test("find(long lt ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee.map(_.long).sorted.apply(testee.size / 2)
      
      val result = Numbers.find(_.long < sample)
      
      info(s"Numbers.find(long lt $sample)")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.long < sample).sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("long" $lt sample).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  test("find(long lte ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee.map(_.long).sorted.apply(testee.size / 2)
      
      val result = Numbers.find(_.long <= sample)
      
      info(s"Numbers.find(long lte $sample)")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.long <= sample).sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("long" $lte sample).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  test("find(long gt ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee.map(_.long).sorted.apply(testee.size / 2)
      
      val result = Numbers.find(_.long > sample)
      
      info(s"Numbers.find(long gt $sample)")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.long > sample).sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("long" $gt sample).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  test("find(long gte ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee.map(_.long).sorted.apply(testee.size / 2)
      
      val result = Numbers.find(_.long >= sample)
      
      info(s"Numbers.find(long gte $sample)")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.long >= sample).sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("long" $gte sample).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  //---------------------------------------------------------------------------- find by double
  test("find(double eql ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee(random.nextInt(testee.size))
      
      val result = Numbers.find(_.double == sample.double)
      
      info(s"Numbers.find(double eql ${sample.double})")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.double == sample.double).sortBy(_.int)
      
      result.size shouldBe expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("double" $eq sample.double).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  test("find(double neq ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee(random.nextInt(testee.size))
      
      val result = Numbers.find(_.double != sample.double)
      
      info(s"Numbers.find(double neq ${sample.double})")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.double != sample.double).sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("double" $ne sample.double).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  test("find(double lt ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee.map(_.double).sorted.apply(testee.size / 2)
      
      val result = Numbers.find(_.double < sample)
      
      info(s"Numbers.find(double lt $sample)")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.double < sample).sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("double" $lt sample).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  test("find(double lte ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee.map(_.double).sorted.apply(testee.size / 2)
      
      val result = Numbers.find(_.double <= sample)
      
      info(s"Numbers.find(double lte $sample)")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.double <= sample).sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("double" $lte sample).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  test("find(double gt ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee.map(_.double).sorted.apply(testee.size / 2)
      
      val result = Numbers.find(_.double > sample)
      
      info(s"Numbers.find(double gt $sample)")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.double > sample).sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("double" $gt sample).toList
      
      eauqlsCasbahResult(result, casbahResult)
    }
  }

  test("find(double gte ?)") {
    Mongo.drive(conf) { implicit db =>
      init
      
      val sample = testee.map(_.double).sorted.apply(testee.size / 2)
      
      val result = Numbers.find(_.double >= sample)
      
      info(s"Numbers.find(double gte $sample)")
      result.foreach(n => info(n.toString))
      
      val expect = testee.filter(_.double >= sample).sortBy(_.int)
      
      result.size shouldEqual expect.size
      
      result.sortBy(_.int) shouldEqual expect
      
      val casbahResult = db("Numbers").find("double" $gte sample).toList
      
      eauqlsCasbahResult(result, casbahResult)
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
