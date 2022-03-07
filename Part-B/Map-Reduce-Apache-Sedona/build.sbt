import sbt.Keys.{libraryDependencies, scalaVersion, version}

lazy val root = (project in file(".")).
  settings(
    name := "Map-Reduce-Apache-Sedona",

    version := "0.1",

    scalaVersion := "2.11.12",

    organization := "org.apache.sedona",

    publishMavenStyle := true,
    mainClass := Some("Entrance")
  )

val SparkVersion = "2.4.5"

val SparkCompatibleVersion = "2.4"

val HadoopVersion = "2.7.2"

val SedonaVersion = "1.0.0-incubating"

val ScalaCompatibleVersion = "2.11"

// Change the dependency scope to "provided" when you run "sbt assembly"
val dependencyScope = "provided"

val geotoolsVersion = "1.1.0-24.1"

val jacksonVersion = "2.10.0"

logLevel := Level.Warn

logLevel in assembly := Level.Error

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % SparkVersion % dependencyScope exclude("org.apache.hadoop", "*"),
  "org.apache.spark" %% "spark-sql" % SparkVersion % dependencyScope exclude("org.apache.hadoop", "*"),
  "org.apache.hadoop" % "hadoop-mapreduce-client-core" % HadoopVersion % dependencyScope,
  "org.apache.hadoop" % "hadoop-common" % HadoopVersion % dependencyScope,
  "org.apache.hadoop" % "hadoop-hdfs" % HadoopVersion % dependencyScope,
  "org.apache.sedona" % "sedona-core-".concat(SparkCompatibleVersion).concat("_").concat(ScalaCompatibleVersion) % SedonaVersion,
  "org.apache.sedona" % "sedona-sql-".concat(SparkCompatibleVersion).concat("_").concat(ScalaCompatibleVersion) % SedonaVersion ,
  "org.apache.sedona" % "sedona-viz-".concat(SparkCompatibleVersion).concat("_").concat(ScalaCompatibleVersion) % SedonaVersion,
  "org.locationtech.jts"% "jts-core"% "1.18.0" % "compile",
  "org.wololo" % "jts2geojson" % "0.14.3" % "compile", // Only needed if you read GeoJSON files. Under MIT License
  //  The following GeoTools packages are only required if you need CRS transformation. Under GNU Lesser General Public License (LGPL) license
  "org.datasyslab" % "geotools-wrapper" % geotoolsVersion % "compile"
)

assemblyMergeStrategy in assembly := {
  case PathList("org.datasyslab", "geospark", xs@_*) => MergeStrategy.first
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case path if path.endsWith(".SF") => MergeStrategy.discard
  case path if path.endsWith(".DSA") => MergeStrategy.discard
  case path if path.endsWith(".RSA") => MergeStrategy.discard
  case _ => MergeStrategy.first
}

resolvers ++= Seq(
  "Open Source Geospatial Foundation Repository" at "https://repo.osgeo.org/repository/release/",
  "Apache Software Foundation Snapshots" at "https://repository.apache.org/content/groups/snapshots",
  "Java.net repository" at "https://download.java.net/maven/2"
)
