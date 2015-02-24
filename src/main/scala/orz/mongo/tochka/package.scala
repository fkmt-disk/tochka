package orz.mongo

package object tochka {

  type Schema[EntityType] = dsl.Schema[EntityType]

  type GenericField[EntityType, FieldType] = dsl.GenericField[EntityType, FieldType]

  type TextField[EntityType] = dsl.TextField[EntityType]

  type NumberField[EntityType, FieldType <: Number] = dsl.NumberField[EntityType, FieldType]

  type SeqField[EntityType, FieldType] = dsl.SeqField[EntityType, FieldType]

  type CollectionName = dsl.CollectionName

  type Entity = dsl.Entity

}
