version := "1.0"
organization := "com.shamison"
scalaVersion := "2.11.5"
libraryDependencies ++= Seq(
	"org.twitter4j" % "twitter4j-core" % "4.0.4",
	"org.twitter4j" % "twitter4j-stream" % "4.0.4",
  "com.typesafe.akka" % "akka-actor_2.11" % "2.3.12"
)