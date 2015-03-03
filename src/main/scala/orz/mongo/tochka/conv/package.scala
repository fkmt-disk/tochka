package orz.mongo.tochka

import scala.reflect.runtime.universe._

import orz.mongo.tochka.util.ReflectionUtil._

package object conv {

  private[conv]
  val SeqType = typeOf[Seq[_]]

  private[conv]
  val MapType = typeOf[Map[String, _]]

  private[conv]
  val OptionType = typeOf[Option[_]]

  private[conv]
  def isCaseClass(typ: Type): Boolean =
    if (typ <:< typeOf[Product])
      ! typ.typeSymbol.fullName.startsWith("scala.")
    else
      false

  private[conv]
  def isCaseClass(cls: Class[_]): Boolean =
    if (classOf[Product].isAssignableFrom(cls))
      ! cls.getName.startsWith("scala.")
    else
      false

}
