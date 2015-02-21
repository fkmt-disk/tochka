package orz.mongo.tochka.entity

import org.bson.types.ObjectId

import orz.mongo.tochka._

@Entity @CollectionName(name = "users")
case class User(
    _id: Option[ObjectId]
  , name: String
  , email: String
  , address: Option[Seq[Address]]
)

object User extends Schema[User] {

  case object _id extends Field[Option[ObjectId], User]

  case object name extends Field[String, User]

  case object email extends Field[String, User]

  case object address extends Field[Option[Seq[Address]], User]

}