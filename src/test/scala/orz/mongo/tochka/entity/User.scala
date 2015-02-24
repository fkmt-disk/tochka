package orz.mongo.tochka.entity

import org.bson.types.ObjectId

import orz.mongo.tochka._

@Entity @CollectionName(name = "users")
case class User(
  _id: Option[ObjectId],
  name: String,
  email: String,
  address: Option[Seq[Address]]
)

object User extends Schema[User] {

  case object _id extends GenericField[User, Option[ObjectId]]

  case object name extends TextField[User]

  case object email extends TextField[User]

  case object address extends SeqField[User, Option[Seq[Address]]]

}
