package orz.mongo.tochka.test

import scala.util.Random

import com.mongodb.casbah.Imports._

import orz.mongo.tochka._
import orz.mongo.tochka.test.util.Mongo

class ManipulationTest extends TestSuiteBase[Book] {

  val random = new Random

  val testee = Seq(
    Book("cobol-88",  "coboler",     random.nextInt),
    Book("cobol-93",  "coboler",     random.nextInt),
    Book("lisp",      "lisper",      random.nextInt),
    Book("scheme",    "lisper",      random.nextInt),
    Book("ruby",      "rubyist",     random.nextInt),
    Book("rails",     "rubyist",     random.nextInt),
    Book("python",    "pythonista",  random.nextInt),
    Book("python3",   "pythonista",  random.nextInt),
    Book("simula",    "smalltalker", Int.MaxValue),
    Book("smalltalk", "smalltalker", Int.MaxValue)
  )

  test("Book update") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(random.nextInt(testee.size))
      val updatePrice = 1
      
      info(s"Book.where(author == ${cond.author}).find <BeforeUpdate>")
      val before = Book.where(_.author == cond.author).find
      before.foreach(it => info(s"-> $it"))
      
      info(s"Book.where(author == ${cond.author}).set(price := ${updatePrice}).update")
      val count = Book.where(_.author == cond.author).set(_.price := updatePrice).update
      info(s"$count document updated!")
      
      count shouldEqual before.size
      
      info(s"Book.where(author == ${cond.author}).find <AfterUpdate>")
      val after = Book.where(_.author == cond.author).find
      after.foreach(it => info(s"-> $it"))
      
      after.forall(it => it.price == updatePrice) shouldEqual true
    }
  }

  test("Book updateOne") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = testee(random.nextInt(testee.size))
      val updatePrice = 1
      
      info(s"Book.where(author == ${cond.author}).find <BeforeUpdate>")
      val before = Book.where(_.author == cond.author).find
      before.foreach(it => info(s"-> $it"))
      
      info(s"Book.where(author == ${cond.author}).set(price := ${updatePrice}).updateOne")
      val count = Book.where(_.author == cond.author).set(_.price := updatePrice).updateOne
      info(s"$count document updated!")
      
      count shouldEqual 1
      
      info(s"Book.where(author == ${cond.author}).find <AfterUpdate>")
      val after = Book.where(_.author == cond.author).find
      after.foreach(it => info(s"-> $it"))
      
      after.filter(it => it.price == updatePrice).size shouldEqual 1
    }
  }

  test("Book upsert") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val newBook = Book("parl", "perl monger", random.nextInt)
      
      info(s"Book.where(_id == ${newBook._id}).find <BeforeUpdate>")
      val before = Book.where(_._id == newBook._id).find
      info(s"-> size=${before.size}")
      
      assert(before.size == 0)
      
      info(s"Book.where(_id == ${newBook._id}).set(${newBook}).upsert")
      val count = Book.where(_._id == newBook._id).set(newBook).upsert
      info(s"$count document updated!")
      
      count shouldEqual 1
      
      info(s"Book.where(_id == ${newBook._id}).find <AfterUpdate>")
      val after = Book.where(_._id == newBook._id).find
      after.foreach(it => info(s"-> $it"))
      
      after.size shouldEqual 1
      after.head shouldEqual newBook
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


