package org.lv

import io.vertx.scala.ext.web.Router
import io.vertx.scala.core.Vertx
import org.lv.checkin.routing.Checkin
import org.lv.checkout.routing.Checkout
import org.lv.server.Server

object App {
  def main(args: Array[String]): Unit = {
    implicit val vertx = Vertx.vertx()

    val api = Router.router(vertx)
    api.post("/:buildingId/:badgeId/checkin").handler(Checkin.checkin)
    api.post("/:buildingId/:badgeId/checkout").handler(Checkout.checkout)

    Server.boostrap(8000, api)
  }
}
