package orz.mongo.tochka

import scala.reflect.runtime.universe._

import orz.mongo.tochka.util.ReflectionUtil._

package object dsl {

  private[dsl]
  def getCollctionName(typ: Type): String = {
    typ.toClass.getAnnot[CollectionName].map(_.name).getOrElse(typ.toClass.getSimpleName)
  }

}
