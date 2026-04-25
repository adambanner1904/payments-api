# Phase 2

Early ideas: 
* add users and items to the data model to provide better analytical value

## Models added

```Scala
PaymentRecord(
  payment: Payment,
  itemPuchased: Item,
)

Item(
  id: Optional[UUID],
  name: String,
  inSale: Boolean,
  category: Category
)

enum Category:
  

```

## Analytical value added:
* Ability to group by sale purchases, item category
* Trending items


## Add user functionality 

* Get payments for user
  * `GET /get-payments/user/:userId`
  * responseMessage returns a `List[Record]` filtered by `userId`
  * Can fail for:
    * id not valid format
    * user does not exist
  * Can return an empty array if user exists and has no payments
