name := """bimpru-ocr"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.asprise.ocr" % "java-ocr-api" % "[15,)",
  "de.vorb" % "jtesseract" % "0.0.4"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
