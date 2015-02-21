package orz.mongo.tochka.dsl

import scala.reflect.runtime.universe._

import com.mongodb.casbah.Imports._

import orz.mongo.tochka.conv.Converter

private[tochka]
class Schema[EntityType: TypeTag] {

  private type SchemaType = this.type

  private type ToCondition = SchemaType => Condition[EntityType]

  def find(condition: ToCondition*): Query[SchemaType, EntityType] = {
    new Query[SchemaType, EntityType](this, condition.map(_(this)) )
  }

  def insert(entity: EntityType)(implicit database: MongoDB): WriteResult = {
    val name = getCollctionName(typeOf[EntityType])
    val coll = database(name)
    coll.insert(Converter.toMap(entity))
  }

}
