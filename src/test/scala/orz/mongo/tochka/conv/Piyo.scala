package orz.mongo.tochka.conv

import orz.mongo.tochka._

case class Piyo(
    int: Int
  , long: Long
  , double: Double
  , bigInt: BigInt
  , decimal: BigDecimal
  , text: String
  , seq: Seq[String]
  , seqOpt: Option[Seq[String]]
  , optSeq: Seq[Option[String]]
  , map: Map[String, String]
  , mapOpt: Option[Map[String, String]]
  , optMap: Map[String, Option[String]]
  , seqMap: Map[String, Seq[String]]
  , optSeqMap: Map[String, Option[Seq[Int]]]
  , mapSeq: Seq[Map[String, String]]
  , optMapSeq: Seq[Map[String, Option[Int]]]
)

object Piyo {

  val testee: Piyo = Piyo(
      int       = Int.MaxValue
    , long      = Long.MaxValue
    , double    = Double.MaxValue
    , bigInt    = BigInt(Int.MaxValue)
    , decimal   = BigDecimal(Double.MaxValue)
    , text      = "abcdefg"
    , seq       = Seq("a", "b", "c")
    , seqOpt    = Some(Seq("d", "e", "f"))
    , optSeq    = Seq(Some("g"), None, Some("i"))
    , map       = Map("a" -> "A", "b" -> "B", "c" -> "C")
    , mapOpt    = Some(Map("x" -> "1", "xx" -> "2", "xxx" -> "3"))
    , optMap    = Map("1" -> Some("1"), "2" -> None, "3" -> Some("3"))
    , seqMap    = Map("seq1" -> Seq("x", "y", "z"), "seq2" -> Seq("a","b","c"))
    , optSeqMap = Map("none" -> None, "some" -> Some(Seq(1,2,3)))
    , mapSeq    = Seq(Map("x" -> "X", "y" -> "Y", "z" -> "Z"), Map("a" -> "A", "b" -> "B", "c" -> "C"))
    , optMapSeq = Seq(Map("x" -> None, "y" -> None, "z" -> None), Map("a" -> Some(1), "b" -> Some(2), "c" -> Some(3)))
  )

  val expect: Map[String, Any] = Map(
      "int"       -> testee.int
    , "long"      -> testee.long
    , "double"    -> testee.double
    , "bigInt"    -> testee.bigInt
    , "decimal"   -> testee.decimal
    , "text"      -> testee.text
    , "seq"       -> testee.seq
    , "seqOpt"    -> testee.seqOpt
    , "optSeq"    -> testee.optSeq
    , "map"       -> testee.map
    , "mapOpt"    -> testee.mapOpt
    , "optMap"    -> testee.optMap
    , "seqMap"    -> testee.seqMap
    , "optSeqMap" -> testee.optSeqMap
    , "mapSeq"    -> testee.mapSeq
    , "optMapSeq" -> testee.optMapSeq
  )

}
