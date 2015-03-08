package orz.mongo.tochka.dsl

import scala.reflect.runtime.universe._

import com.mongodb.casbah.Imports._

import orz.mongo.tochka.util.ReflectionUtil._

private[tochka]
class AnyRefField[T <: AnyRef](protected val __prefix: String = "") extends Field {
  
  protected
  type FieldType = T
  
  override
  def ==(value: FieldType): WhereClause = WhereClause(toCondition(value, "$eq"))
  
  override
  def !=(value: FieldType): WhereClause = WhereClause(toCondition(value, "$ne"))
  
  /**
   * TODO: なんか微妙。。。もっとキレイになるのでは。。。
   */
  private
  def toCondition(value: Any, ope: String, prefix: String = __name): DBObject =
    value match {
      case any if isCaseClass(any) =>
        val p = any.asInstanceOf[Product]
        val list = p.getClass.getDeclaredFields.map(_.getName).zip(p.productIterator.toList).map {
          case (k, v) if isCaseClass(v) =>
            toCondition(v, ope)
          case (k, v) =>
            DBObject(s"${prefix}.$k" -> DBObject(ope -> v))
        }
        if (ope == "$eq")
          $and( list:_* )
        else
          $or( list:_* )
      case any =>
        DBObject(prefix -> DBObject(ope -> any))
    }
  
}
