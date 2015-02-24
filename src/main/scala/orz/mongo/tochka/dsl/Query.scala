package orz.mongo.tochka.dsl

import scala.reflect.runtime.universe._

import com.mongodb.casbah.Imports._

import orz.mongo.tochka.conv.Converter

private[dsl]
class Query[SchemaType, EntityType: TypeTag](
  self: SchemaType,
  condition: Seq[Condition[EntityType]]
) {

  def fetch(implicit database: MongoDB): Seq[EntityType] = {
    val cond = {
      val builder = MongoDBObject.newBuilder
      for (c <- condition) builder ++= c.value
      builder.result
    }

    val name = getCollctionName(typeOf[EntityType])

    //cond.foreach(println)

    database(name).find(cond).toList.map { dbo =>
      Converter.toEntity[EntityType](dbo)
    }
  }

}
