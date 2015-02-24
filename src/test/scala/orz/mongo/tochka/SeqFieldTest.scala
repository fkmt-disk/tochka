package orz.mongo.tochka

import com.typesafe.config.ConfigFactory
import org.scalatest._
import orz.mongo.tochka.test.Mongo

class SeqFieldTest extends FunSuite with Matchers with BeforeAndAfterAll {

  val conf = ConfigFactory.load

  override def beforeAll {
    Mongo.drive(conf) { implicit db =>
      TextList.drop

      TextList.insert(TextList(Seq("a", "b", "c")))
      TextList.insert(TextList(Seq("b", "c", "d")))
      TextList.insert(TextList(Seq("c", "d", "e")))
      TextList.insert(TextList(Seq("d", "e", "f")))
    }
  }

  test("all") {
    Mongo.drive(conf) { implicit db =>
      {
        val result = TextList.find(_.chars all Seq("a", "b", "c")).fetch
        result.size shouldBe 1
      }

      {
        val result = TextList.find(_.chars all Seq("b", "c")).fetch
        result.size shouldBe 2
      }

      {
        val result = TextList.find(_.chars all Seq("c")).fetch
        result.size shouldBe 3
      }

      {
        val result = TextList.find(_.chars all Seq("f", "d", "e")).fetch
        result.size shouldBe 1
      }

      {
        val result = TextList.find(_.chars all Seq("c", "d", "a")).fetch
        result.size shouldBe 0
      }
    }
  }

}

@Entity @CollectionName(name = "textlist")
case class TextList(chars: Seq[String])

object TextList extends Schema[TextList] {
  case object chars extends SeqField[TextList, Seq[String]]
}