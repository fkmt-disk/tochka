### tochka

wrapper of casbah(mongodb-driver) @scala

---

#### build.sbt

```scala
lazy val root = (project in file("."))
  .dependsOn(
    RootProject(uri("git://github.com/fkmt-disk/tochka.git#v0.1"))
  )
  .settings(
    name := "sample",
    version := "1.0",
    scalaVersion := "2.11.5"
  )
```

---

#### sample

```scala
import com.mongodb.casbah.Imports._
import orz.mongo.tochka._

case class Book(author: String, title: String, price: Int)

object Book extends Schema[Book] {
  case object author extends StringField
  case object title extends StringField
  case object price extends IntField
}

object Test extends App {
  val client: MongoClient = MongoClient("localhost", 27017)
  try {
    
    implicit db: MongoDB = client("test")
    
    // insert
    Book.insert( Book(author="FooBar", title="HogeFuga", price=1200) )
    
    // find
    val books: Seq[Book] = Book.where(_.author == "foo" && _.price >= 2000).find
    
    // update
    Book.where(_.title == "hoge").set(price = 1000).update
    
    // remove
    Book.where(_.price <= 1000).remove
    
  }
  finally {
    client.close
  }
}
```

---

[wiki](https://github.com/fkmt-disk/tochka/wiki)
