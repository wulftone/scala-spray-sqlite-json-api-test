package com.example

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import spray.json._
import DefaultJsonProtocol._ // if you don't supply your own Protocol (see below)
import scala.slick.driver.SQLiteDriver.simple._
import org.sqlite.JDBC

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MyServiceActor extends Actor with MyService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}

// this trait defines our service behavior independently from the service actor
trait MyService extends HttpService {
  val cars: TableQuery[Cars] = TableQuery[Cars]
  // cars.ddl.create

  val db = Database.forURL("jdbc:sqlite:./test.sqlite3", driver = "org.sqlite.JDBC")

  val myRoute = path("") {
    get {
      complete {
        val result = db.withSession {
          implicit session =>
            cars.map(c => (c.id, c.make, c.modelName, c.year)).list.toJson.prettyPrint
        }

        result.toJson.prettyPrint
      }
    } ~
    post {
      complete {
        val result = db.withSession {
          implicit session =>
            cars.insert(Car(0, "Ford", "Taurus", 2015))
        }

        result.toJson.prettyPrint
      }
    }
  }
}
