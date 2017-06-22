import sbt.Package.ManifestAttributes

name := "contrib-vertx"

version := "0.1-dev"

organization := "LinkValue"

organizationName := "Link Value"

scalaVersion := "2.12.1"

libraryDependencies ++= Vector (
  Library.vertx_lang_scala,
  Library.vertx_web,
  Library.scalaTest       % "test",
  Library.vertx_codegen
)

packageOptions += ManifestAttributes(
  ("Main-Verticle", "scala:HttpVerticle"))
