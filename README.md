### tochka

wrapper of casbah(mongodb-driver) by scala

#### find.wip

Given("a test database, like this")

```javascript
db.users = [
  {
    _id: ObjectId(...),
    code: 1,
    name: "user1",
    email: "user1@example"
  }
]
```

And("given a case class, like this")

```scala
package sample.entity
case class User(_id: ObjectId, code: Int, name: String, email: Option[String])
```

When("executed this statement")

```scala
import sample.entity.User
val users: Seq[User] = User.find(_.code eql 1).fetch
```

Then("sequence of User obtained")  
And("MongoDBObject data stored into a User")

```scala
val user = users.head
println(user.code) // => 1
println(user.nane) // => user1
println(user.email) // => Some(user1@example)
println(user._id) // => ObjectId(...)
```

#### insert.wip

```scala
import sample.entity.User

val user = User(new ObjectId, 2, "user2", None)

User.insert(user)
```
