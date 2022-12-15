ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.12"

lazy val root = (project in file("."))
  .settings(
    name := "Marczyk_Scala_BI"
  )

Compile / mainClass := Some("App")

val postgresVersion = "42.3.4"
val slickVersion = "3.3.3"
val slickPostgresVersion = "0.20.3"
val akkaVersion = "2.6.5"
val akkaHttpVersion = "10.2.0"
val akkaHttpJsonSerializersVersion = "1.34.0"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % postgresVersion,
  "org.slf4j" % "slf4j-nop" % "2.0.5", // required by other dependencies

  // ORM - Slick
  "com.typesafe.slick" %% "slick" % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "com.github.tminglei" %% "slick-pg" % slickPostgresVersion,
  "com.github.tminglei" %% "slick-pg_play-json" % slickPostgresVersion,

  // API - Akka
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "de.heikoseeberger" %% "akka-http-jackson" % akkaHttpJsonSerializersVersion,
)

// Fat jar
lazy val runAll = taskKey[Unit]("Run all main classes")

def runAllIn(config: Configuration) = Def.task {
  val s = streams.value
  val cp = (config / fullClasspath).value
  val r = (config / run / runner).value
  val classes = (config / discoveredMainClasses).value
  classes.foreach { className =>
    r.run(className, cp.files, Seq(), s.log).get
  }
}

runAll := {
  runAllIn(Compile).value
  runAllIn(Test).value
}

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case "reference.conf" => MergeStrategy.concat
  case _ => MergeStrategy.first
}