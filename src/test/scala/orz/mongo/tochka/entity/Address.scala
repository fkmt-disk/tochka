package orz.mongo.tochka.entity

import orz.mongo.tochka._

case class Address(zip: String, address: String)

object Address extends Schema[Address] {

  case object zip extends AnyRefField[String]

  case object address extends AnyRefField[String]

}
