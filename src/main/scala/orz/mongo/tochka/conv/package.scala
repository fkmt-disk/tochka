package orz.mongo.tochka

import orz.mongo.tochka.util.ReflectionUtil._

import scala.reflect.runtime.universe._

package object conv {

  private[conv]
  val SeqType = typeOf[Seq[_]]

  private[conv]
  val MapType = typeOf[Map[String, _]]

  private[conv]
  val OptionType = typeOf[Option[_]]

  private[conv]
  def isEntity(cls: Class[_]): Boolean = cls.hasAnnot[Entity]

  private[conv]
  def isEntity(typ: Type): Boolean = isEntity(typ.toClass)

}
