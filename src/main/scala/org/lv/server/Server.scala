package org.lv.server

import io.vertx.scala.core.Vertx
import io.vertx.scala.core.http.HttpServer
import io.vertx.scala.ext.web.handler.{BodyHandler, TimeoutHandler}
import io.vertx.scala.ext.web.{Router, RoutingContext}
import org.lv.adapter.MongoDBCheckinRepository
import org.lv.routing.{Checkin, Checkout}
import org.lv.server.Server.Port

import scala.util.{Failure, Success, Try}


class Server(vertxApp: Vertx) {
  def init(port: Port) = {

    val api = Router.router(vertxApp)

    api.route()
      .handler(BodyHandler.create())
      //.handler(TimeoutHandler.create(2000))

    implicit val vertx = vertxApp
    // where's DI ???
    MongoDBCheckinRepository.create("contrib").map { dbRepository =>
      api.post("/checkin").consumes("application/json").produces("application/json").handler(new Checkin(dbRepository).checkin)
      api.post("/checkout").consumes("application/json").produces("application/json").handler(Checkout.checkout)
    }

    val server = vertx.createHttpServer()
    val router = Router.router(vertx)

    router.mountSubRouter("/building/", api)

    Router.router(vertx).route().failureHandler((routingContext: RoutingContext) => {
      routingContext.response()
          .setStatusCode(500)
        .write("mince !").end()
    })

    server
      .requestHandler(router.accept _)
      .listen(port)
  }

}

object Server {

  type Port = Int
  var server: Option[HttpServer] = None

  def boostrap(vertx: Vertx, port: Port = 8080): Try[HttpServer] =
    if (server.nonEmpty) {
        Failure(new Error(s"HttpServer is already running on $port"))
    } else {
      val httpServer = new Server(vertx).init(port)
      server = Some(httpServer)
      Success(httpServer)
    }
}
