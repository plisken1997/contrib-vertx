package org.lv.adapter

import io.vertx.lang.scala.json.{Json, JsonObject}
import io.vertx.scala.core.Vertx
import org.lv.connector.mongoDB.MongoDBClient
import org.lv.routing.CheckinInput

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

sealed class MongoDBCheckinRepository(private val client: MongoDBClient) {

  def save(checkin: CheckinInput): Future[JsonObject] = {
    val checkinJson = Json.obj(("badgeId", checkin.badgeId), ("buildingId", checkin.buildingId))
    client
      .save("vertx")(checkinJson)
      .map { id =>
        checkinJson.copy().put("id", checkinJson)
      }
  }
}

object MongoDBCheckinRepository {
  def create(dbName: String)(implicit vertx: Vertx): Try[MongoDBCheckinRepository] =
    MongoDBClient.initClient(dbName, vertx) match {
      case Success(client) => Success(new MongoDBCheckinRepository(client))
      case Failure(e) => Failure(e)
    }
}