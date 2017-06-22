package org.lv.checkout.routing

import io.vertx.lang.scala.json.Json
import io.vertx.scala.ext.web.RoutingContext

object Checkout {
  def checkout(routingContext: RoutingContext) = {
    val badgeId = routingContext.request().getParam("badgeId").get
    val buildingId = routingContext.request().getParam("buildingId").get

    val response = routingContext.response()
    response.putHeader("content-type", "application/json")
    val content = Json.obj(("result", s"checkout -> BuildingId ${buildingId} | badgeId ${badgeId}}"))

    response.end(content.encode())
  }
}
