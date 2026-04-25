# payments-api 

## Description

This is an API that takes in payments made and stores them in MongoDB for persistence. 

It then surfaces this data in various analytics APIs.

The main domain business model is: 

```Scala
Payment(
  id: Optional[UUID], // the id for the payment once generated
  amountInPence: AmountInPence, // the amount paid in pence
  method: Method, // the method that was used to make the payment
  paymentTime: Datetime // the time the payment was made
)

type AmountInPence = Integer // AmountInPence is just an alias for Integer

enum Method: // Method is defined small set of payment methods
  ...
  
```

This model is the request/response model. We expect the 3 required fields to be sent in and we send the 4 fields back out for GET requests. 

A `Payment` represents a payment that has happened as part of a purchase. 

Metrics that are supported from this: 

* Volume of payments
* Total value
* Trend analysis over time
* Ability to group by payment method

## Endpoints

* Create a payment
  * `POST /payments`
  * requestBody has `amountInPence`, `method`, `paymentTime`
  * responseMessage either a success code and id created or reason for failure
  * Can fail for:
    * missing required fields
    * bad data (i.e. letters in amount)
* Get a payment by id 
  * `GET /payments/:id`
  * responseMessage returns the full `Payment`
  * Can fail for:
    * id not valid format
    * id not found
    * database crashes
* Get all payments
  * `GET /payments` 
  * No validation needed
  * Can fail for:
    * only database
  * response message returns a `List[Payment]`

## Database 

One collection is needed for payments.

Document should store all the fields from `Payment` and initially generate the id to be returned back on `POST /payment`. 

Additional fields: 

* The `createdAt` timestamp for the mongo event which is the time that the event is inserted into Mongo
* An `auditSource` field to say where the event came from in our case this will always be `payments-api`. 
* A `schemaVersion` as in future iterations schema is possible to change. 

Most probable access patterns:

* fetch by id (for frontend services that want to display a payment to a user)
* aggregate volumes and totals by day over a time range
* fetch payments where `paymentTime` is between start and end
* fetch payments where `method = X`

Potential indexes: `id`, `paymentTime`, `method` + `paymentTime`