package orz.mongo.tochka.test.util

import com.mongodb.casbah.Imports._

import com.typesafe.config.Config

object Mongo {

  def drive[T](host: String, port: Int = 27017, database: String)(f: MongoDB => T) = {
    val client = MongoClient(host, port)
    try
      f(client(database))
    finally
      client.close
  }

  def drive[T](host: String, port: Int, database: String, username: String, password: String)(f: MongoDB => T) = {
    val client = MongoClient(MongoClientURI(s"mongodb://$username:$password@$host:$port/$database"))
    try
      f(client(database))
    finally
      client.close
  }

  def drive[T](conf: Config)(f: MongoDB => T): T = {
    val host = conf.get[String]("host").getOrElse("localhost")
    val port = conf.get[Int]("port").getOrElse(27017)
    val database = conf.get[String]("database").getOrElse("test")

    (conf.get[String]("username"), conf.get[String]("password")) match {
      case (Some(user), Some(pswd)) =>
        drive[T](host, port, database, user, pswd)(f)
      case _ =>
        drive[T](host, port, database)(f)
    }
  }

}
