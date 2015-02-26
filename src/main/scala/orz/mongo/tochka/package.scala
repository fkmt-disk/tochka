package orz.mongo

package object tochka {

  type Schema[T] = dsl.Schema[T]

  type BoolField = dsl.BoolField

  type IntField = dsl.IntField

  type LongField = dsl.LongField

  type DoubleField = dsl.DoubleField

  type SeqField[T] = dsl.SeqField[T]

  type AnyRefField[T <: AnyRef] = dsl.AnyRefField[T]

  type CollectionName = dsl.CollectionName

  type Entity = dsl.Entity

}
