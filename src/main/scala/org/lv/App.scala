package org.lv

import io.vertx.scala.core.Vertx
import org.lv.server.Server

import scala.util.{Failure, Success}

object App {
  def main(args: Array[String]): Unit =
    Server.boostrap(Vertx.vertx()) match {
      case Success(server) => println(s"Running app port ${server.actualPort()}")
      case Failure(error) => println(s"could not run vertx http server : ${error.getMessage}")
    }
}
