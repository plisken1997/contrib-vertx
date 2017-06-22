package org.lv.server

import io.vertx.core.Vertx

object Server {
  def boostrap(port: Int = 8080) = {
    val vertx = Vertx.vertx()
    var server = vertx.createHttpServer()

    server.requestHandler((request) => {

      // This handler gets called for each request that arrives on the server
      var response = request.response()
      response.putHeader("content-type", "application/json")

      // Write to the response and end it
      response.end("{\"response\": \"Hello World!\"}")
    })

    server.listen(port)
  }
}