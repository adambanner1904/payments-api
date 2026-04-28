package controllers

import play.api.libs.json.* 
import scala.collection.Seq

object ErrorHandler:
  def formatErrors(errors: Seq[(JsPath, Seq[JsonValidationError])]): JsObject = 
    Json.obj(
      "errors" -> errors.map { case (path, validationErrors) =>
        Json.obj(
          "field"  -> path.path.lastOption.map(_.toString.tail).getOrElse("unknown"),
          "message" -> validationErrors.flatMap(_.messages).mkString(", ")
        )
      }
    )