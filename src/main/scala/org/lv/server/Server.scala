package org.lv.server

import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.Router

object Server {

  def boostrap(port: Int = 8080, api: Router)(implicit vertx: Vertx) = {
    val server = vertx.createHttpServer()

    val router = Router.router(vertx)

    router.mountSubRouter("/", api)
    router.route().handler(defaultRoute)

    server.requestHandler(router.accept _).listen(port)
  }
}