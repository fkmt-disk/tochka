package orz.mongo.tochka.conv

import java.util.Date

import orz.mongo.tochka._

@Entity
case class Fuga(
    date: Date
  , piyo: Piyo
  , optPiyo: Option[Piyo]
  , piyoSeq: Seq[Piyo]
  , piyoSeqOpt: Option[Seq[Piyo]]
  , piyoMap: Map[String, Piyo]
  , piyoMapOpt: Option[Map[String,Piyo]]
  , optPiyoSeq: Seq[Option[Piyo]]
  , optPiyoMap: Map[String, Option[Piyo]]
)

object Fuga {

  val testee: Fuga = Fuga(
      date        = new Date
    , piyo        = Piyo.testee
    , optPiyo     = Some(Piyo.testee)
    , piyoSeq     = Seq(Piyo.testee, Piyo.testee)
    , piyoSeqOpt  = Some(Seq(Piyo.testee, Piyo.testee))
    , piyoMap     = Map("1" -> Piyo.testee, "2" -> Piyo.testee)
    , piyoMapOpt  = Some(Map("1" -> Piyo.testee, "2" -> Piyo.testee))
    , optPiyoSeq  = Seq(None, Some(Piyo.testee), None)
    , optPiyoMap  = Map("a" -> None, "b" -> Some(Piyo.testee), "c" -> None)
  )

  val expect: Map[String, Any] = Map(
      "date"        -> testee.date
    , "piyo"        -> Piyo.expect
    , "optPiyo"     -> Some(Piyo.expect)
    , "piyoSeq"     -> Seq(Piyo.expect, Piyo.expect)
    , "piyoSeqOpt"  -> Some(Seq(Piyo.expect, Piyo.expect))
    , "piyoMap"     -> Map("1" -> Piyo.expect, "2" -> Piyo.expect)
    , "piyoMapOpt"  -> Some(Map("1" -> Piyo.expect, "2" -> Piyo.expect))
    , "optPiyoSeq"  -> Seq(None, Some(Piyo.expect), None)
    , "optPiyoMap"  -> Map("a" -> None, "b" -> Some(Piyo.expect), "c" -> None)
  )

}
