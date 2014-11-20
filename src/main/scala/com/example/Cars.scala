package com.example

import scala.slick.driver.SQLiteDriver.simple._

case class Car(id: Int, make: String, modelName: String, year: Int)

class Cars(tag: Tag) extends Table[Car](tag, "cars") {
  def id        = column[Int]("id", O.PrimaryKey)
  def make      = column[String]("make")
  def modelName = column[String]("model_name")
  def year      = column[Int]("year")
  def *         = (id, make, modelName, year) <> (Car.tupled, Car.unapply)
}
