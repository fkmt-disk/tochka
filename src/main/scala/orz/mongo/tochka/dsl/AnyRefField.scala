package orz.mongo.tochka.dsl

private[tochka]
class AnyRefField[T <: AnyRef](val prefix: String = "") extends Field {

  type FieldType = T

}
