package orz.mongo.tochka.dsl

private[tochka] abstract
class Field[FieldType, EntityType] {

  val name = toString

  def eql(value: FieldType): Condition[EntityType] = Condition[EntityType](name -> value)

}
