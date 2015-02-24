package orz.mongo.tochka.dsl

import com.mongodb.casbah.Imports._

private[dsl]
case class Condition[E](value: MongoDBObject)
