package models

import play.api.libs.json.*
import scala.util.Try

enum PaymentMethod:
  case Card, PayByBank
  
object PaymentMethod:
  given Reads[PaymentMethod] = Reads[PaymentMethod]: json => 
    json.validate[String].flatMap: s => 
      Try(PaymentMethod.valueOf(s))
        .fold(
          _ => JsError(s"'$s' is not a valid PaymentMethod. Must be one of: ${PaymentMethod.values.mkString(", ")}"),
          m => JsSuccess(m)
        )
        
  given Writes[PaymentMethod] = Writes[PaymentMethod]: pm => 
    JsString(pm.toString)