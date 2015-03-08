package orz.mongo.tochka.dsl

private[tochka]
class IntField(protected val __prefix: String = "") extends ComparableField {
  
  protected
  type FieldType = Int
  
}
