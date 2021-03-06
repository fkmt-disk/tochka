package orz.mongo.tochka.dsl

private[dsl]
trait ComparableField extends Field {
  
  def <(value: FieldType): WhereClause = WhereClause(__name, "$lt", value)
  
  def <=(value: FieldType): WhereClause = WhereClause(__name, "$lte", value)
  
  def >(value: FieldType): WhereClause = WhereClause(__name, "$gt", value)
  
  def >=(value: FieldType): WhereClause = WhereClause(__name, "$gte", value)
  
}
