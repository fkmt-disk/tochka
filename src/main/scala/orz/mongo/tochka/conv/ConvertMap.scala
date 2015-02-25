package orz.mongo.tochka.conv

import scala.reflect.runtime.universe._

import orz.mongo.tochka.conv
import orz.mongo.tochka.util.ReflectionUtil._

private[conv]
object ConvertMap {

  private
  def inspect(obj: Any): Seq[(String, Any, Type)] = {
    val clsType = obj.getClass.toType
    val constructor = clsType.getConstructor
    val instance = obj.reflectInstance
    for {
      p <- constructor.paramLists.flatten
      name = p.getName
      term = clsType.getMember(name).asTerm
      value = instance.reflectField(term).get
      typ = p.toType
    } yield (name, value, typ)
  }

  def convert(obj: Any): Map[String, Any] = {
    require(isEntity(obj.getClass))
    inspect(obj).map {
      case (name, value, typ) if typ <:< OptionType =>
        name -> convOption(typ, value.asOpt)
      case (name, value, typ) if typ <:< SeqType =>
        name -> convSeq(typ, value.asSeq)
      case (name, value, typ) if typ <:< MapType =>
        name -> convMap(typ, value.asMap)
      case (name, value, typ) if conv.isEntity(typ) =>
        name -> convert(value)
      case (name, value, _) =>
        name -> value
    }.toMap
  }

  private
  def convOption(typ: Type, opt: Option[_]): Option[_] = {
    typ.typeArgs.head match {
      case t if t <:< OptionType =>
        opt.map(e => convOption(t, e.asOpt))
      case t if t <:< SeqType =>
        opt.map(e => convSeq(t, e.asSeq))
      case t if t <:< MapType =>
        opt.map(e => convMap(t, e.asMap))
      case t if conv.isEntity(t) =>
        opt.map(e => convert(e))
      case _ =>
        opt
    }
  }

  private
  def convSeq(typ: Type, seq: Seq[_]): Seq[_] = {
    typ.typeArgs.head match {
      case t if t <:< OptionType =>
        seq.map(e => convOption(t, e.asOpt))
      case t if t <:< SeqType =>
        seq.map(e => convSeq(t, e.asSeq))
      case t if t <:< MapType =>
        seq.map(e => convMap(t, e.asMap))
      case t if conv.isEntity(t) =>
        seq.map(e => convert(e))
      case _ =>
        seq
    }
  }

  private
  def convMap(typ: Type, map: Map[String, _]): Map[String, _] = {
    typ.typeArgs.last match {
      case t if t <:< OptionType =>
        map.mapValues(e => convOption(t, e.asOpt))
      case t if t <:< SeqType =>
        map.mapValues(e => convSeq(t, e.asSeq))
      case t if t <:< MapType =>
        map.mapValues(e => convMap(t, e.asMap))
      case t if conv.isEntity(t) =>
        map.mapValues(e => convert(e))
      case _ =>
        map
    }
  }

  private implicit
  class Castable(any: Any) {
    def asSeq: Seq[_] = any.asInstanceOf[Seq[_]]
    def asMap: Map[String, _] = any.asInstanceOf[Map[String, _]]
    def asOpt: Option[_] = any.asInstanceOf[Option[_]]
  }

}
