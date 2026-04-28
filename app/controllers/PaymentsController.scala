package controllers

import javax.inject.Inject
import play.api.mvc.*
import play.api.libs.json.*

import repositories.PaymentRepository
import models.Payment
import models.request.CreatePaymentRequest
import controllers.ErrorHandler
import scala.concurrent.ExecutionContext

class PaymentsController @Inject() (
  cc: ControllerComponents,
  repo: PaymentRepository
)(using ExecutionContext)
  extends AbstractController(cc):
    def createPayment(): Action[JsValue] = Action(parse.json) { request => 
      val paymentResult = request.body.validate[CreatePaymentRequest]
      paymentResult.fold(
        errors => BadRequest(ErrorHandler.formatErrors(errors)),
        payment => {
          repo.save(payment)
          Ok(Json.obj("message" -> ("Payment saved")))
        }
      )
    }
      
    def getPayments() = Action.async {
      repo.allPayments.map(payments => Ok(Json.toJson(payments)))
    }
    
    def getPayment(id: Integer) = Action {
      val payment: Option[Payment] = repo.payments.find(_.paymentId == id)
      payment match 
        case None => NotFound(s"No payment found for id: $id")
        case _ => Ok(Json.toJson(payment))
    }
