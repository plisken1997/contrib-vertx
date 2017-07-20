package org.lv.routing

import io.vertx.lang.scala.json.JsonObject
import io.vertx.scala.ext.web.RoutingContext
import org.lv.adapter.MongoDBCheckinRepository
import scala.concurrent.ExecutionContext.Implicits.global

class Checkin(checkinRepository: MongoDBCheckinRepository) {

  def checkin(routingContext: RoutingContext): Unit = routingContext.getBodyAsJson().fold(onBodyError(routingContext))(body => handleCheckin(body, routingContext))

  private def onBodyError(context: RoutingContext) = context.response().write("Empty body").setStatusCode(401).end()

  private def handleCheckin(body: JsonObject, context: RoutingContext) = {
    val badgeId = body.getInteger("badgeId")
    val buildingId = body.getInteger("buildingId")
    val checkin = CheckinInput(buildingId, badgeId)

    checkinRepository
      .save(checkin)
      .map { savedCheckin =>
        context
          .response()
          .setStatusCode(201)
          .end(savedCheckin.encode())
      }
  }
}
