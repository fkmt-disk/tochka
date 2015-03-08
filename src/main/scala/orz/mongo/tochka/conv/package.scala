package orz.mongo.tochka

import scala.reflect.runtime.universe._

import com.mongodb.casbah.Imports._

import orz.mongo.tochka.util.ReflectionUtil._

package object conv {
  
  private[tochka]
  def toEntity[T: TypeTag](dbo: DBObject): T = ConvertEntity.convert[T](dbo)
  
  private[tochka]
  def toMap(entity: Any): Map[String, Any] = ConvertMap.convert(entity)
  
  private[conv]
  val SeqType = typeOf[Seq[_]]
  
  private[conv]
  val MapType = typeOf[Map[String, _]]
  
  private[conv]
  val OptionType = typeOf[Option[_]]
  
}
