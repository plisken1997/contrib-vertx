package org.lv.connector.mongoDB

import io.vertx.scala.ext.mongo.MongoClient
import io.vertx.lang.scala.json.{Json, JsonObject}
import io.vertx.scala.core.Vertx

import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

trait Logger {
  def log(content: String){
    println(content)
  }
}

class MongoDBClient(db: String, vertx: Vertx) extends Logger {
  // @todo use a dynamic configuration
  val mongoDBconfig = Json.obj("host" -> "127.1", "port" -> 27017, "db_name" -> db)

  val client = MongoClient.createShared(vertx, mongoDBconfig, db)

  def save(collection: String)(obj: JsonObject): Future[String] =
    client.saveFuture(collection, obj).map { id =>
      log(s"Saved ${obj.encode()} into $db.$collection with id ${id}")
      id
    }

  def find(collection: String, query: JsonObject): Future[mutable.Buffer[JsonObject]] = client.findFuture(collection, query)
}

object MongoDBClient {
  private val pool = new mutable.HashMap[String, MongoDBClient]

  def initClient(db: String, vertx: Vertx): Try[MongoDBClient] =
    try {
      val client = pool.getOrElseUpdate(db, {
        val conn = createConnection(db, vertx)
        pool.put(db, conn)
        conn
      })
      Success(client)
    } catch {
      case e: Throwable => Failure(e)
    }

  def getClient(db: String): Option[MongoDBClient] = pool.get(db)

  private def createConnection(db: String, vertx: Vertx) = new MongoDBClient(db, vertx)
}
