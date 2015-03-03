package orz.mongo.tochka.test

import scala.util.Random

import com.mongodb.casbah.Imports._

import orz.mongo.tochka._
import orz.mongo.tochka.test.util.Mongo

class LogicalOpeTest extends TestSuiteBase[Triple] {
  
  val testee = Seq(
    Triple(false, false, false),
    Triple(false, false, true),
    Triple(false, true,  false),
    Triple(true,  false, false),
    Triple(false, true,  true),
    Triple(true,  false, true),
    Triple(true,  true,  false),
    Triple(true,  true,  true)
  )
  
  val random = new Random
  
  test("find ? and ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = (random.nextBoolean, random.nextBoolean)
      
      info(s"Triple.where(one == ${cond._1} && two == ${cond._2}).find")
      val result = Triple.where(_.one == cond._1 && _.two == cond._2).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => it.one == cond._1 && it.two == cond._2).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Triple.find($$and(one $$eq ${cond._1}, two $$eq ${cond._2})) @casbah")
      val casbah = db("Triple").find($and("one" $eq cond._1, "two" $eq cond._2)).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
  test("find ? and ? and ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = (random.nextBoolean, random.nextBoolean, random.nextBoolean)
      
      info(s"Triple.where(one == ${cond._1} && two == ${cond._2} && three == ${cond._3}).find")
      val result = Triple.where(_.one == cond._1 && _.two == cond._2 && _.three == cond._3).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => it.one == cond._1 && it.two == cond._2 && it.three == cond._3).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Triple.find($$and(one $$eq ${cond._1}, two $$eq ${cond._2}, three $$eq ${cond._3})) @casbah")
      val casbah = db("Triple").find($and("one" $eq cond._1, "two" $eq cond._2, "three" $eq cond._3)).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
  test("find ? or ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = random.nextBoolean
      
      info(s"Triple.where(one == $cond || two == $cond).find")
      val result = Triple.where(_.one == cond || _.two == cond).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => it.one == cond || it.two == cond).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Triple.find($$or(one $$eq $cond, two $$eq $cond)) @casbah")
      val casbah = db("Triple").find($or("one" $eq cond, "two" $eq cond)).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
  test("find ? or ? or ?") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = random.nextBoolean
      
      info(s"Triple.where(one == $cond || two == $cond || three == $cond).find")
      val result = Triple.where(_.one == cond || _.two == cond || _.three == cond).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => it.one == cond || it.two == cond || it.three == cond).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Triple.find($$or(one $$eq $cond, two $$eq $cond, three $$eq $cond)) @casbah")
      val casbah = db("Triple").find($or("one" $eq cond, "two" $eq cond, "three" $eq cond)).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
  test("find complex condition") {
    Mongo.drive(conf) { implicit db =>
      init()
      
      val cond = random.nextBoolean
      
      info(s"Triple.where( (one == $cond && two == $cond) || (two == $cond && three == $cond) ).find")
      val result = Triple.where(it => (it.one == cond && it.two == cond) || (it.two == cond && it.three == cond)).find
      result.foreach(it => info(s"-> $it"))
      
      val expect = testee.filter(it => (it.one == cond && it.two == cond) || (it.two == cond && it.three == cond)).sortBy(_._id)
      
      result.size shouldEqual expect.size
      result.sortBy(_._id) shouldEqual expect
      
      info(s"Triple.find( $$or( $$and(one $$eq $cond, two $$eq $cond), $$and(two $$eq $cond, three $$eq $cond) ) ) @casbah")
      val casbah = db("Triple").find($or($and("one" $eq cond, "two" $eq cond), $and("two" $eq cond, "three" $eq cond))).toList
      casbah.foreach(it => info(s"-> $it"))
      
      assertEquals2casbah(result, casbah)
    }
  }
  
}

case class Triple(one: Boolean, two: Boolean, three: Boolean, _id: ObjectId = new ObjectId) {
  
  override def toString = s"Triple(one=${one}, two=${two}, three=${three}, _id=${_id})"
  
}

object Triple extends Schema[Triple] {
  
  case object one extends BoolField
  
  case object two extends BoolField
  
  case object three extends BoolField
  
  case object _id extends AnyRefField[ObjectId]
  
}
