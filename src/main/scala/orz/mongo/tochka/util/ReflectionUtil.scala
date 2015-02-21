package orz.mongo.tochka.util

import scala.reflect.runtime.universe._

import java.lang.annotation.Annotation

private[tochka]
object ReflectionUtil {

  private
  val mirror = scala.reflect.runtime.currentMirror

  implicit class TypeW(typ: Type) {

    def toClass: Class[_] = mirror.runtimeClass(typ)

    def toClass[T: TypeTag]: Class[T] = mirror.runtimeClass(typ).asInstanceOf[Class[T]]

    def toSymbol: Symbol = typ.typeSymbol

    def getConstructor: MethodSymbol = typ.member(termNames.CONSTRUCTOR).asMethod

    def getCompanion: ModuleSymbol = toSymbol.companion.asModule

    def getMember(name: String): Symbol = typ.member(TermName(name))

    def getMembers(name: String): Seq[Symbol] = getMember(name).alternatives

  }

  implicit class ClassW(cls: Class[_]) {

    def toSymbol: Symbol = mirror.classSymbol(cls)

    def toType: Type = toSymbol.typeSignature

    def getAnnot[T <: Annotation: TypeTag]: Option[T] = Option(cls.getAnnotation[T](typeOf[T].toClass[T]))

    def hasAnnot[T <: Annotation: TypeTag]: Boolean = getAnnot[T].isDefined

  }

  implicit class SymbolW(sym: Symbol) {

    def toType: Type = sym.typeSignature

    def getName: String = sym.name.decodedName.toString

    def reflectModule: ModuleMirror = mirror.reflectModule(sym.asModule)

    def reflectInstance: InstanceMirror = reflectModule.instance.reflectInstance

  }

  implicit class MirrorProvider(any: Any) {

    def reflectInstance: InstanceMirror = mirror.reflect(any)

  }

  implicit class InstanceMirrorW(instance: InstanceMirror) {

    def invoke(method: MethodSymbol, args: Any*): Any = instance.reflectMethod(method.asMethod)(args: _*)

    def invoke(method: Symbol, args: Any*): Any = invoke(method.asMethod, args: _*)

  }

}
