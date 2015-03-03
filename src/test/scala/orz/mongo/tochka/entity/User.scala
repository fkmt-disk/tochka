package orz.mongo.tochka.entity

import org.bson.types.ObjectId

import orz.mongo.tochka._

@CollectionName(name = "users")
case class User(
  _id: Option[ObjectId],
  name: String,
  email: String,
  address: Option[Seq[Address]]
)

object User extends Schema[User] {

  case object _id extends AnyRefField[Option[ObjectId]]

  case object name extends AnyRefField[String]

  case object email extends AnyRefField[String]

  case object address extends SeqField[Address]

}
