package orz.mongo.tochka.dsl

private[tochka]
trait AnyRefField[T <: AnyRef] extends Field {

  type FieldType = T

}
