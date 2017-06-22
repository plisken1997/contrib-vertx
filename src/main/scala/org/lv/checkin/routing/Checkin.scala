package org.lv.checkin.routing

import io.vertx.scala.ext.web.RoutingContext
import io.vertx.lang.scala.json.Json

object Checkin {
  def checkin(routingContext: RoutingContext) = {
    val badgeId = routingContext.request().getParam("badgeId").get
    val buildingId = routingContext.request().getParam("buildingId").get

    val response = routingContext.response()
    response.putHeader("content-type", "application/json")
    val content = Json.obj(("result", s"checkin -> BuildingId ${buildingId} | badgeId ${badgeId}"))

    response.end(content.encode())
  }
}
