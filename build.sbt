version := "developing"
organization := "com.shamison"
scalaVersion := "2.11.5"
libraryDependencies ++= Seq(
	"org.twitter4j" % "twitter4j-core" % "4.0.2",
	"org.twitter4j" % "twitter4j-stream" % "4.0.2",
)

lazy val commonSettings = Seq(
	version := "0.1",
	organization := "com.shamison",
	scalaVersion := "2.11.5"

)

lazy val app = (project in file("app")).
	settings(commonSettings: _*).
	settings(
    name := "update_name_sc",
    libraryDependencies ++= Seq(
	    "org.twitter4j" % "twitter4j-core" % "4.0.2",
	    "org.twitter4j" % "twitter4j-stream" % "4.0.2"
    ),
    mainClass in assembly := Some("Main"),
    assemblyJarName in assembly := "app.jar"
	)