package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[dsl]
trait ComparableField extends Field {

  def <(value: FieldType): WhereClause = new WhereClause(name $lt value)

  def <=(value: FieldType): WhereClause = new WhereClause(name $lte value)

  def >(value: FieldType): WhereClause = new WhereClause(name $gt value)

  def >=(value: FieldType): WhereClause = new WhereClause(name $gte value)

}
