package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[tochka]
class IdField(protected val __prefix: String = "") extends Field {
  
  protected
  type FieldType = ObjectId
  
  /**
   * ObjectIdは特別対応
   * https://gist.github.com/fkmt-disk/3e30fc7e9e77d30ce2b7
   */
  override
  def ==(value: FieldType): WhereClause = WhereClause(DBObject(__name -> value))
  
}
