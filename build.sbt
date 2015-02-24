name := "tochka"

version := "0.1"

scalaVersion := "2.11.5"

libraryDependencies += "org.mongodb" % "casbah_2.11" % "2.8.0"

libraryDependencies += "com.typesafe" % "config" % "1.2.1"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalacOptions += "-target:jvm-1.8"
