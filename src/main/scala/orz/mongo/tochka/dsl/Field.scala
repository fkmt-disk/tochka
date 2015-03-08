package orz.mongo.tochka.dsl

private[dsl]
trait Field {
  
  protected
  type FieldType
  
  protected
  val __prefix: String
  
  protected
  val __name = Option(__prefix).map(_.trim) match {
    case Some(pref) if pref.nonEmpty =>
      s"${pref}.${toString}"
    case _ =>
      toString
  }
  
  def ==(value: FieldType): WhereClause = WhereClause(__name, "$eq", value)
  
  def ==(option: Option[FieldType]): WhereClause = this.==(option.getOrElse(null).asInstanceOf[FieldType])
  
  def !=(value: FieldType): WhereClause = WhereClause(__name, "$ne", value)
  
  def !=(option: Option[FieldType]): WhereClause = this.!=(option.getOrElse(null).asInstanceOf[FieldType])
  
  def :=(value: FieldType): (String, FieldType) = __name -> value
  
  def :=(option: Option[FieldType]): (String, FieldType) = this.:=(option.getOrElse(null).asInstanceOf[FieldType])
  
}
