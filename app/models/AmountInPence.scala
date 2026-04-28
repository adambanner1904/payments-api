package models

import play.api.libs.json.{Format, JsNumber, JsValue}

type AmountInPence = Int

// object AmountInPence:
  // given Format[AmountInPence] = Format[AmountInPence](
  //   json => json.validate[Int],
  //   amount => JsNumber(amount)
  // )
