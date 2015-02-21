package orz.mongo.tochka

import scala.language.implicitConversions
import scala.reflect.runtime.universe._

import com.typesafe.config.Config

package object test {

  private[test]
  implicit class ConfigW(conf: Config) {

    def get[T: TypeTag](name: String): Option[T] = if (!conf.hasPath(name)) None else {
      typeOf[T] match {
        case t if t =:= typeOf[String]  => conf.getString(name)
        case t if t =:= typeOf[Int]     => conf.getInt(name)
        case t if t =:= typeOf[Boolean] => conf.getBoolean(name)
        case t if t =:= typeOf[Config]  => conf.getConfig(name)
        case t =>
          throw new Exception("unsupported type: " + t)
      }
    }

    private
    implicit def toOpt[T](any: Any): Option[T] = Option(any.asInstanceOf[T])

  }

}
