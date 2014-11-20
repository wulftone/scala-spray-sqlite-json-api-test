package com.example

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import spray.json._
import DefaultJsonProtocol._ // if you don't supply your own Protocol (see below)
import scala.slick.driver.SQLiteDriver.simple._

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

  // val db = Database.forURL("jdbc:sqlite:./test.sqlite3", driver = "org.sqlite.jdbc.Driver")
  val db = Database.forURL("jdbc:sqlite:./test.sqlite3", driver = "scala.slick.driver.SQLiteDriver")

  val myRoute = path("") {
    get {
      respondWithMediaType(`application/json`) {
        complete {

          val result = db.withSession {

            implicit session =>

              cars.ddl.create
              val myCar = Car(-1, "Ford", "Taurus", 2015)
              cars.insert(myCar)
          }
          "Hi"
        }
      }
    }
  }
}
