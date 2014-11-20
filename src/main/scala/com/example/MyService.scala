    package com.example

    import akka.actor.Actor
    import spray.routing._
    import spray.http._
    import MediaTypes._
    import spray.json._
    import DefaultJsonProtocol._
    import scala.slick.driver.SQLiteDriver.simple._

    class MyServiceActor extends Actor with MyService {

      def actorRefFactory = context

      def receive = runRoute(myRoute)
    }


    trait MyService extends HttpService {
      val cars: TableQuery[Cars] = TableQuery[Cars]

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
