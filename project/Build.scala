import sbt._
import Keys._
import com.typesafe.sbt.osgi.OsgiKeys
import com.typesafe.sbt.osgi._

object ScalaCSVProject extends Build {

  lazy val osgisettings = Seq(
    OsgiKeys.exportPackage := Seq("com.github.tototoshi.csv"),
    OsgiKeys.additionalHeaders := Map("Include-Resource" -> ""))

  lazy val root = Project(
    id = "scala-csv",
    base = file("."),
    settings = Defaults.defaultSettings ++ Seq(
      name := "scala-csv",
      version := "1.0.0-SNAPSHOT",
      scalaVersion := "2.10.0",
      crossScalaVersions := Seq("2.10.0", "2.9.1", "2.9.2", "2.9.3"),
      organization := "com.github.tototoshi",
      libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "1.9.1" % "test",
        "org.osgi" % "org.osgi.core" % "4.2.0"),
      scalacOptions <<= scalaVersion.map { sv =>
        if (sv.startsWith("2.10")) {
          Seq(
            "-deprecation",
            "-language:_")
        } else {
          Seq("-deprecation")
        }
      },
      
      publishMavenStyle := true,
      publishTo <<= version { (v: String) =>
        val nexus = "https://oss.sonatype.org/"
        if (v.trim.endsWith("SNAPSHOT"))
          Some("snapshots" at nexus + "content/repositories/snapshots")
        else
          Some("releases" at nexus + "service/local/staging/deploy/maven2")
      },
      publishArtifact in Test := false,
      pomExtra := _pomExtra) ++
      com.typesafe.sbt.osgi.SbtOsgi.osgiSettings ++ osgisettings)

  val _pomExtra =
    <url>http://github.com/tototoshi/scala-csv</url>
    <licenses>
      <license>
        <name>Apache License, Version 2.0</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:tototoshi/scala-csv.git</url>
      <connection>scm:git:git@github.com:tototoshi/scala-csv.git</connection>
    </scm>
    <developers>
      <developer>
        <id>tototoshi</id>
        <name>Toshiyuki Takahashi</name>
        <url>http://tototoshi.github.com</url>
      </developer>
    </developers>

}

