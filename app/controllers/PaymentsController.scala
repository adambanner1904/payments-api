package controllers

import models.Payment
import models.request.CreatePaymentRequest
import models.response.ErrorResponse
import repositories.PaymentRepository

import play.api.mvc.*
import play.api.libs.json.*

import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject
import scala.util.*
import org.bson.types.ObjectId

class PaymentsController @Inject() (
  cc: ControllerComponents,
  repo: PaymentRepository
)(using ExecutionContext)
  extends AbstractController(cc):
  
    def createPayment(): Action[JsValue] = Action(parse.json).async: request => 
      request.body.validate[CreatePaymentRequest].fold(
        errors => Future.successful(BadRequest(Json.toJson(ErrorResponse.fromJsErrors(errors)))),
        payment => repo
          .save(payment)
          .map(p => Created(Json.toJson(p)))
          .recover(_ => InternalServerError(Json.toJson(ErrorResponse.single("server", "An unexpected error occurred"))))
      )
      
    def getPayments() = Action.async: 
      repo.allPayments.map(payments => Ok(Json.toJson(payments)))
        .recover(_ => InternalServerError(Json.toJson(ErrorResponse.single("server", "An unexpected error occurred"))))
    
    def getPayment(id: String) = Action.async: 
      Try(ObjectId(id)) match 
        case Failure(_) => 
          Future.successful(BadRequest(Json.toJson(ErrorResponse.single("id", s"$id is not a valid id format"))))
        case Success(oid) =>
          repo.findPayment(oid).map {
            case Some(payment) => Ok(Json.toJson(payment))
            case None => NotFound(Json.toJson(ErrorResponse.single("id", s"Payment not found with id: $id")))
          }.recover(_ => InternalServerError(Json.toJson(ErrorResponse.single("server", "An unexpected error occurred"))))