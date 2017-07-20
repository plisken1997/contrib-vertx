import sbt.Package.ManifestAttributes

name := "contrib-vertx"

version := "0.1-dev"

organization := "LinkValue"

organizationName := "Link Value"

scalaVersion := "2.12.1"

libraryDependencies ++= Vector (
  Library.vertx_lang_scala,
  Library.vertx_web,
  Library.vertx_mongo_client,
  Library.scalaTest       % "test",
  Library.vertx_codegen
)

packageOptions += ManifestAttributes(
  ("Main-Verticle", "scala:HttpVerticle"))

//
//val circeVersion = "0.8.0"
//
//libraryDependencies ++= Seq(
//  "io.circe" %% "circe-core",
//  "io.circe" %% "circe-generic",
//  "io.circe" %% "circe-parser"
//).map(_ % circeVersion)
