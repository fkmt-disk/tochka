package orz.mongo.tochka.dsl

private[tochka]
abstract
class GenericField[E, T] extends Field {

  type FieldType = T

  type EntityType = E

}
