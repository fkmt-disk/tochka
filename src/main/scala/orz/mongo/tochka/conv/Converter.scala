package orz.mongo.tochka.conv

import scala.reflect.runtime.universe._

import com.mongodb.casbah.Imports._

private[tochka]
object Converter {

  def toEntity[T: TypeTag](dbo: MongoDBObject): T = ConvertEntity.convert[T](dbo)

  def toMap(entity: Any): Map[String, Any] = ConvertMap.convert(entity)

}
