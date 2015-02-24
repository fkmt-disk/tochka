package orz.mongo.tochka.entity

import orz.mongo.tochka._

@Entity
case class Address(zip: String, address: String)

object Address extends Schema[Address] {

  case object zip extends TextField[Address]

  case object address extends TextField[Address]

}