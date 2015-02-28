package orz.mongo.tochka.dsl

import scala.reflect.runtime.universe._

import com.mongodb.casbah.Imports._

import orz.mongo.tochka.conv.Converter
import orz.mongo.tochka.util.ReflectionUtil._

private[tochka]
class Schema[EntityType: TypeTag] {

  private type SchemaType = this.type
  private type S = SchemaType

  private type ToCondition1  = (S) => Condition
  private type ToCondition2  = (S,S) => Condition
  private type ToCondition3  = (S,S,S) => Condition
  private type ToCondition4  = (S,S,S,S) => Condition
  private type ToCondition5  = (S,S,S,S,S) => Condition
  private type ToCondition6  = (S,S,S,S,S,S) => Condition
  private type ToCondition7  = (S,S,S,S,S,S,S) => Condition
  private type ToCondition8  = (S,S,S,S,S,S,S,S) => Condition
  private type ToCondition9  = (S,S,S,S,S,S,S,S,S) => Condition
  private type ToCondition10 = (S,S,S,S,S,S,S,S,S,S) => Condition
  private type ToCondition11 = (S,S,S,S,S,S,S,S,S,S,S) => Condition
  private type ToCondition12 = (S,S,S,S,S,S,S,S,S,S,S,S) => Condition
  private type ToCondition13 = (S,S,S,S,S,S,S,S,S,S,S,S,S) => Condition
  private type ToCondition14 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S) => Condition
  private type ToCondition15 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => Condition
  private type ToCondition16 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => Condition
  private type ToCondition17 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => Condition
  private type ToCondition18 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => Condition
  private type ToCondition19 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => Condition
  private type ToCondition20 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => Condition
  private type ToCondition21 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => Condition
  private type ToCondition22 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => Condition

  private
  val name = {
    val cls = typeOf[EntityType].toClass
    cls.getAnnot[CollectionName].map(_.name).getOrElse(cls.getSimpleName)
  }

  private
  def find(condition: DBObject)(implicit database: MongoDB): Seq[EntityType] = {
    database(name).find(condition).toList.map(Converter.toEntity[EntityType](_))
  }

  def find(implicit database: MongoDB): Seq[EntityType] = find(DBObject.empty)

  def find(toc: ToCondition1)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this).value)
  def find(toc: ToCondition2)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this).value)
  def find(toc: ToCondition3)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this).value)
  def find(toc: ToCondition4)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this).value)
  def find(toc: ToCondition5)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this).value)
  def find(toc: ToCondition6)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this).value)
  def find(toc: ToCondition7)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this,this).value)
  def find(toc: ToCondition8)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this,this,this).value)
  def find(toc: ToCondition9)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this,this,this,this).value)
  def find(toc: ToCondition10)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this,this,this,this,this).value)
  def find(toc: ToCondition11)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this,this,this,this,this,this).value)
  def find(toc: ToCondition12)(implicit database: MongoDB): Seq[EntityType] = 
    find(toc.apply(this,this,this,this,this,this,this,this,this,this,this,this).value)
  def find(toc: ToCondition13)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def find(toc: ToCondition14)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def find(toc: ToCondition15)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def find(toc: ToCondition16)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def find(toc: ToCondition17)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def find(toc: ToCondition18)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def find(toc: ToCondition19)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def find(toc: ToCondition20)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def find(toc: ToCondition21)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def find(toc: ToCondition22)(implicit database: MongoDB): Seq[EntityType] =
    find(toc.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)

  def insert(entity: EntityType)(implicit database: MongoDB) {
    database(name).insert(Converter.toMap(entity))
  }

  def drop(implicit database: MongoDB) {
    database(name).drop
  }

}
