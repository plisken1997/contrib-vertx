package org.lv

import io.vertx.scala.ext.web.RoutingContext

package object server {
  def defaultRoute(routingContext: RoutingContext) = {

    val response = routingContext.response()
    response.putHeader("content-type", "application/json")
    val content = "{\"result\": \"Nothing to do here\"}"
    routingContext.fail(404)
    response.end(content)
  }
}
