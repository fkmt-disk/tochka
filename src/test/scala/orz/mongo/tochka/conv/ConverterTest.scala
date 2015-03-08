package orz.mongo.tochka.conv

import org.scalatest._

class ConverterTest extends FunSuite with Matchers {

  test("toMap") {
    val result = toMap(Hoge.testee)
    result shouldEqual Hoge.expect
  }

}
