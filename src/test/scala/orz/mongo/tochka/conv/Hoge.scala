package orz.mongo.tochka.conv

import org.bson.types.ObjectId

import orz.mongo.tochka._

case class Hoge(
    _id: Option[ObjectId]
  , fuga: Fuga
  , piyo: Piyo
)

object Hoge {

  val testee = Hoge(
      _id  = Some(new ObjectId)
    , fuga = Fuga.testee
    , piyo = Piyo.testee
  )

  val expect = Map(
      "_id"  -> testee._id
    , "fuga" -> Fuga.expect
    , "piyo" -> Piyo.expect
  )

}
