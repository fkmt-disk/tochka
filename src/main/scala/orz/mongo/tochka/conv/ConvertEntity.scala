package orz.mongo.tochka.conv

import scala.reflect.runtime.universe._

import com.mongodb.casbah.Imports._

import orz.mongo.tochka.conv
import orz.mongo.tochka.util.ReflectionUtil._

private[conv]
object ConvertEntity {

  private
  implicit class Cast(any: Any) {
    def asDbObj: BasicDBObject = any.asInstanceOf[BasicDBObject]
    def asDbList: BasicDBList = any.asInstanceOf[BasicDBList]
  }

  private
  def inspect(typ: Type, comp: Type, dbo: MongoDBObject): Seq[(Type, Option[_], Symbol)] = {
    val constructor = typ.getConstructor
    for {
      (p, i) <- constructor.paramLists.flatten.zipWithIndex
      typ = p.toType
      name = p.getName
      value = dbo.get(name)
      getDefault = comp.getMember(s"apply$$default$$${i + 1}")
    } yield (typ, value, getDefault)
  }

  def convert[T: TypeTag](dbo: MongoDBObject): T = convert(typeOf[T], dbo).asInstanceOf[T]

  private
  def convert(typ: Type, dbo: MongoDBObject): AnyRef = {
    require(conv.isEntity(typ))

    val comp = typ.getCompanion
    val compTyp = comp.toType

    val instance = comp.reflectInstance

    val args = inspect(typ, compTyp, dbo).map {
      case (_, None, getDefault) =>
        instance.invoke(getDefault)
      case (t, Some(value), _) if t <:< OptionType =>
        convOption(t, value)
      case (t, Some(value), _) if t <:< SeqType =>
        convSeq(t, value.asDbList)
      case (t, Some(value), _) if conv.isEntity(t) =>
        convert(typ, value.asDbObj)
      case (_, Some(value), _) =>
        value
    }

    val apply = compTyp.getMembers("apply").find(_.toType.resultType =:= typ).get
    instance.invoke(apply, args: _*).asInstanceOf[AnyRef]
  }

  private
  def convOption(typ: Type, value: Any): Option[_] = {
    if (value == null)
      None
    else
      typ.typeArgs.head match {
        case t if t <:< OptionType =>
          convOption(t, value)
        case t if t <:< SeqType =>
          Some(convSeq(t, value.asDbList))
        case t if conv.isEntity(t) =>
          Some(convert(t, value.asDbObj))
        case _ =>
          Some(value)
      }
  }

  private
  def convSeq(typ: Type, list: MongoDBList): Seq[_] = {
    typ.typeArgs.head match {
      case t if t <:< OptionType =>
        list.toList.map(e => convOption(t, e))
      case t if t <:< SeqType =>
        list.toList.map(e => convSeq(t, e.asDbList))
      case t if conv.isEntity(t) =>
        list.toList.map(e => convert(t, e.asDbObj))
      case _ =>
        list.toList
    }
  }

}
