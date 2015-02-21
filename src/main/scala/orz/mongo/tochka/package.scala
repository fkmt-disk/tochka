package orz.mongo

package object tochka {

  type Schema[EntityType] = dsl.Schema[EntityType]

  type Field[FieldType, EntityType] = dsl.Field[FieldType, EntityType]

  type CollectionName = dsl.CollectionName

  type Entity = dsl.Entity

}
