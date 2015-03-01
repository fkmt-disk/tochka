package orz.mongo.tochka.conv

import scala.reflect.runtime.universe._

import com.mongodb.casbah.Imports._

import orz.mongo.tochka.conv
import orz.mongo.tochka.util.ReflectionUtil._

private[conv]
object ConvertEntity {

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
        convert(t, value.asDbObj)
      case (t, Some(value), _) =>
        value.cast(t)
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
        case t =>
          Some(value.cast(t))
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
      case t =>
        list.toList.map(_.cast(t))
    }
  }

  private implicit
  class Castable(any: Any) {

    def asDbObj: BasicDBObject = any.asInstanceOf[BasicDBObject]

    def asDbList: BasicDBList = any.asInstanceOf[BasicDBList]

    def cast(typ: Type) = any match {
      case s: String  => ofString(typ, s)
      case i: Int     => ofInt(typ, i)
      case l: Long    => ofLong(typ, l)
      case d: Double  => ofDouble(typ, d)
      case b: Boolean => b
      case obj        => scala.reflect.runtime.currentMirror.runtimeClass(typ).cast(obj)
    }

    private
    def ofString(typ: Type, s: String) = typ match {
      case t if t <:< typeOf[String] =>
        s
      case t if t <:< typeOf[Symbol] =>
        Symbol(s)
      case t if t <:< typeOf[Char] =>
        if (s.size == 1)
          s.charAt(0)
        else
          throw new ClassCastException(s"String cannot be cast to $t")
      case t =>
        throw new ClassCastException(s"String cannot be cast to $t")
    }

    private
    def ofInt(typ: Type, i: Int) = typ match {
      case t if t <:< typeOf[Int] =>
        i
      case t if t <:< typeOf[Long] =>
        i.toLong
      case t if t <:< typeOf[Double] =>
        i.toDouble
      case t if t <:< typeOf[BigInt] =>
        BigInt(i)
      case t if t <:< typeOf[BigDecimal] =>
        BigDecimal(i)
      case t =>
        throw new ClassCastException(s"Int cannot be cast to $t")
    }

    private
    def ofLong(typ: Type, l: Long) = typ match {
      case t if t <:< typeOf[Int] =>
        l.toInt match {
          case i if i == l => i
          case _ => throw new ClassCastException(s"Long cannot be cast to $t")
        }
      case t if t <:< typeOf[Long] =>
        l
      case t if t <:< typeOf[Double] =>
        l.toDouble
      case t if t <:< typeOf[BigInt] =>
        BigInt(l)
      case t if t <:< typeOf[BigDecimal] =>
        BigDecimal(l)
      case t =>
        throw new ClassCastException(s"Long cannot be cast to $t")
    }

    private
    def ofDouble(typ: Type, d: Double) = typ match {
      case t if t <:< typeOf[Int] =>
        d.toInt match {
          case i if i == d => i
          case _ => throw new ClassCastException(s"Double cannot be cast to $t")
        }
      case t if t <:< typeOf[Long] =>
        d.toLong match {
          case l if l == d => l
          case _ => throw new ClassCastException(s"Double cannot be cast to $t")
        }
      case t if t <:< typeOf[Double] =>
        d
      case t if t <:< typeOf[BigInt] =>
        d.toInt match {
          case i if i == d => BigInt(i)
          case _ => throw new ClassCastException(s"Double cannot be cast to $t")
        }
      case t if t <:< typeOf[BigDecimal] =>
        BigDecimal(d)
      case t =>
        throw new ClassCastException(s"Double cannot be cast to $t")
    }

  }

}
