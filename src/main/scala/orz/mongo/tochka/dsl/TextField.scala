package orz.mongo.tochka.dsl

private[tochka]
abstract
class TextField[E] extends Field {

  type FieldType = String

  type EntityType = E

}
