package orz.mongo.tochka.entity

import org.scalatest._

import org.bson.types.ObjectId

import com.typesafe.config.ConfigFactory
import orz.mongo.tochka.test.util.Mongo

class CrudTest extends FunSuite with Matchers with BeforeAndAfterAll {

  val conf = ConfigFactory.load

  override def beforeAll {
    Mongo.drive(conf) { db =>
      db("users").drop
    }
  }

  var testee = User(
    Some(new ObjectId),
    "foo",
    "foo@example",
    Some(Seq(
      Address("000-1234", "aaa-bbb-ccc-1234"),
      Address("123-0000", "aaa-bbb-ccc-0000")
    ))
  )

  test("insert") {
    Mongo.drive(conf) { implicit db =>

      User.findAll.size shouldBe 0

      val result = User.insert(testee)

      info(result.toString)

      User.findAll.size shouldBe 1

    }
  }

  test("find") {
    Mongo.drive(conf) { implicit db =>

      val users = User.where(_._id == testee._id).find

      users.size shouldBe 1

      info(users.head.toString)

      users.head shouldEqual testee

    }
  }

}
