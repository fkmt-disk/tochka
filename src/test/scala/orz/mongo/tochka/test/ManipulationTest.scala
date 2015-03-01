package orz.mongo.tochka.test

import scala.util.Random

import com.mongodb.casbah.Imports._

import orz.mongo.tochka._
import orz.mongo.tochka.test.util.Mongo

class ManipulationTest extends TestSuiteBase[Book] {
  
  val random = new Random
  
  val testee = Seq(
    Book("cobol",     "coboler",     random.nextInt),
    Book("lisp",      "lisper",      random.nextInt),
    Book("perl",      "perl monger", random.nextInt),
    Book("ruby",      "rubyist",     random.nextInt),
    Book("python",    "pythonista",  random.nextInt),
    Book("smalltalk", "smalltalker", Int.MaxValue)
  )

  test("update price") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(random.nextInt(testee.size))
      val updatePrice = 1
      
      info(s"Book.where(title == ${cond.title}) <BeforeUpdate>")
      val before = Book.where(_.title == cond.title).find
      before.foreach(it => info(s"-> $it"))
      
      info(s"Book.where(title == ${cond.title}).set(price := $updatePrice).update")
      val count = Book.where(_.title == cond.title).set(_.price := updatePrice).update
      info(s"$count document updated!")
      
      info(s"Book.find(title == ${cond.title}) <AfterUpdate>")
      val after = Book.where(_.title == cond.title).find
      after.foreach { book =>
        info(s"-> $book")
        book.price shouldEqual updatePrice
      }
    }
  }

  test("remove") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      info(s"Book.where(title =~ /^p/).find")
      val before = Book.where(_.title =~ "^p".r).find
      before.foreach(book => info(s"-> $book"))
      
      info(s"before.size=${before.size}")
      
      info(s"Book.where(title =~ /^p/).remove")
      val count = Book.where(_.title =~ "^p".r).remove
      info(s"$count document removed!")
      
      before.size shouldEqual count
      
      info(s"Book.where(title =~ /^p/).find")
      val after = Book.where(_.title =~ "^p".r).find
      
      info(s"after.size=${after.size}")
      
      after.size shouldEqual 0
    }
  }

}

@Entity
case class Book(title: String, author: String, price: Int, _id: ObjectId = new ObjectId) {

  override def toString = s"Book(title=${title}, author=${author}, price=${price}, _id=${_id})"

}

object Book extends Schema[Book] {

  case object title extends TextField

  case object author extends TextField

  case object price extends IntField

  case object _id extends AnyRefField[ObjectId]

}


