package orz.mongo.tochka.dsl

import scala.reflect.runtime.universe._

import com.mongodb.casbah.Imports._

import orz.mongo.tochka.conv.Converter
import orz.mongo.tochka.util.ReflectionUtil._

private[tochka]
class Schema[EntityType: TypeTag] {

  private type SchemaType = this.type

  private type ToCondition = SchemaType => MongoDBObject

  private val name = {
    val cls = typeOf[EntityType].toClass
    cls.getAnnot[CollectionName].map(_.name).getOrElse(cls.getSimpleName)
  }

  def find(condition: ToCondition*)(implicit database: MongoDB): Seq[EntityType] = {
    val cond = {
      val builder = MongoDBObject.newBuilder
      for (c <- condition) builder ++= c(this)
      builder.result
    }

    //cond.foreach(println)

    database(name).find(cond).toList.map(Converter.toEntity[EntityType](_))
  }

  def insert(entity: EntityType)(implicit database: MongoDB) {
    database(name).insert(Converter.toMap(entity))
  }

  def drop(implicit database: MongoDB) {
    database(name).drop
  }

}
