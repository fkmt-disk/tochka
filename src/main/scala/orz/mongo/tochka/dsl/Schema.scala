package orz.mongo.tochka.dsl

import scala.reflect.runtime.universe._

import com.mongodb.casbah.Imports._

import orz.mongo.tochka.conv
import orz.mongo.tochka.util.ReflectionUtil._

private[tochka]
class Schema[EntityType: TypeTag] {
  
  private type SchemaType = this.type
  private type S = SchemaType
  
  private type ToWhere1 = (S) => WhereClause
  private type ToWhere2 = (S,S) => WhereClause
  private type ToWhere3 = (S,S,S) => WhereClause
  private type ToWhere4 = (S,S,S,S) => WhereClause
  private type ToWhere5 = (S,S,S,S,S) => WhereClause
  private type ToWhere6 = (S,S,S,S,S,S) => WhereClause
  private type ToWhere7 = (S,S,S,S,S,S,S) => WhereClause
  private type ToWhere8 = (S,S,S,S,S,S,S,S) => WhereClause
  private type ToWhere9 = (S,S,S,S,S,S,S,S,S) => WhereClause
  private type ToWhere10 = (S,S,S,S,S,S,S,S,S,S) => WhereClause
  private type ToWhere11 = (S,S,S,S,S,S,S,S,S,S,S) => WhereClause
  private type ToWhere12 = (S,S,S,S,S,S,S,S,S,S,S,S) => WhereClause
  private type ToWhere13 = (S,S,S,S,S,S,S,S,S,S,S,S,S) => WhereClause
  private type ToWhere14 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S) => WhereClause
  private type ToWhere15 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => WhereClause
  private type ToWhere16 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => WhereClause
  private type ToWhere17 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => WhereClause
  private type ToWhere18 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => WhereClause
  private type ToWhere19 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => WhereClause
  private type ToWhere20 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => WhereClause
  private type ToWhere21 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => WhereClause
  private type ToWhere22 = (S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S) => WhereClause
  
  def where(w: ToWhere1): Query = new Query(w.apply(this).value)
  def where(w: ToWhere2): Query = new Query(w.apply(this,this).value)
  def where(w: ToWhere3): Query = new Query(w.apply(this,this,this).value)
  def where(w: ToWhere4): Query = new Query(w.apply(this,this,this,this).value)
  def where(w: ToWhere5): Query = new Query(w.apply(this,this,this,this,this).value)
  def where(w: ToWhere6): Query = new Query(w.apply(this,this,this,this,this,this).value)
  def where(w: ToWhere7): Query = new Query(w.apply(this,this,this,this,this,this,this).value)
  def where(w: ToWhere8): Query = new Query(w.apply(this,this,this,this,this,this,this,this).value)
  def where(w: ToWhere9): Query = new Query(w.apply(this,this,this,this,this,this,this,this,this).value)
  def where(w: ToWhere10): Query = new Query(w.apply(this,this,this,this,this,this,this,this,this,this).value)
  def where(w: ToWhere11): Query = new Query(w.apply(this,this,this,this,this,this,this,this,this,this,this).value)
  def where(w: ToWhere12): Query = new Query(w.apply(this,this,this,this,this,this,this,this,this,this,this,this).value)
  def where(w: ToWhere13): Query = new Query(w.apply(this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def where(w: ToWhere14): Query = new Query(w.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def where(w: ToWhere15): Query = new Query(w.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def where(w: ToWhere16): Query = new Query(w.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def where(w: ToWhere17): Query = new Query(w.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def where(w: ToWhere18): Query = new Query(w.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def where(w: ToWhere19): Query = new Query(w.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def where(w: ToWhere20): Query = new Query(w.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def where(w: ToWhere21): Query = new Query(w.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  def where(w: ToWhere22): Query = new Query(w.apply(this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this,this).value)
  
  private
  val name = {
    val cls = typeOf[EntityType].toClass
    cls.getAnnot[CollectionName].map(_.name).getOrElse(cls.getSimpleName)
  }
  
  def findAll(implicit db: MongoDB): Seq[EntityType] = {
    db(name).find.toList.map(conv.toEntity[EntityType](_))
  }
  
  def insert(entity: EntityType)(implicit db: MongoDB) {
    db(name).insert(conv.toMap(entity))
  }
  
  def drop(implicit db: MongoDB) {
    db(name).drop
  }
  
  class Query(where: DBObject) {
    
    private
    type ToSet = SchemaType => (String, Any)
    
    def find(implicit db: MongoDB): Seq[EntityType] = {
      db(Schema.this.name).find(where).toList.map(conv.toEntity[EntityType](_))
    }
    
    def findOne(implicit db: MongoDB): Option[EntityType] = {
      find.headOption
    }
    
    def set(update: ToSet*): Updater = {
      new Updater(where, $set(update.map(_.apply(Schema.this)): _*))
    }
    
    def set(entity: EntityType): Updater = {
      new Updater(where, conv.toMap(entity))
    }
    
    def remove(implicit db: MongoDB): Int = {
      db(Schema.this.name).remove(where).getN
    }
    
  }
  
  class Updater(where: DBObject, set: DBObject) {
    
    def update(implicit db: MongoDB): Int = {
      db(Schema.this.name).update(where, set, multi=true).getN
    }
    
    def updateOne(implicit db: MongoDB): Int = {
      db(Schema.this.name).update(where, set).getN
    }
    
    def upsert(implicit db: MongoDB): Int = {
      db(Schema.this.name).update(where, set, upsert=true).getN
    }
    
  }
  
}
