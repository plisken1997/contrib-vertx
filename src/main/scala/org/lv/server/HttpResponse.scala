package org.lv.server

import io.vertx.scala.ext.web.RoutingContext

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object HttpResponse {
  def withAsyncResponse(routingContext: RoutingContext)(handleResponse: RoutingContext => Future[String]): Unit = {
    handleResponse(routingContext).map { content =>
      val response = routingContext.response()
      response.putHeader("content-type", "application/json")
      response.end(content)
    }
  }
}
