### tochka

wrapper of casbah(mongodb-driver) by scala

#### find

Given("a test database, like this")

```javascript
db.User = [
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
case class User(code: Int, name: String, email: Option[String], _id: ObjectId=new ObjectId)
```

When("executed this statement")

```scala
import sample.entity.User
val users: Seq[User] = User.where(_.name == "user1").find
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


#### insert

```scala
import sample.entity.User
val user = User(2, "user2", None)
User.insert(user)
```


#### update

```scala
import sample.entity.User
val count: Int = User.where(_.name == "user1").set(_.code := 100).update
```


#### remove

```scala
import sample.entity.User
val count: Int = User.where(_.name =~ "^user".r).remove
```
