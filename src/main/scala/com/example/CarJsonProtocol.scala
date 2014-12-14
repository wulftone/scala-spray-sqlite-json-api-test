package com.example

import spray.json._
import DefaultJsonProtocol._

object CarJsonProtocol extends DefaultJsonProtocol {

  implicit object TaskJsonFormat extends RootJsonFormat[Car] {

    def write(c: Car) = JsObject(
      "id"        -> JsNumber(c.id.toInt),
      "make"      -> JsString(c.make.toString()),
      "modelName" -> JsString(c.modelName.toString()),
      "year"      -> JsNumber(c.year.toInt)
    )

    def read(j: JsValue) = {
      j.asJsObject.getFields("id", "make", "modelName", "year") match {
        case Seq(JsNumber(id), JsString(make), JsString(modelName), JsNumber(year)) =>
          new Car(id.toInt, make, modelName, year.toInt)
        case _ => throw new DeserializationException("Improperly formed Car object")
      }
    }
  }
}
