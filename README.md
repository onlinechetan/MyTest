# MyRetail Application

Retrieves product details for a given product id and responses back to the caller of the rest endpoint.

Endpoint URL: http://localhost:8080/products/{productId}

Technology Stack:

Postman v6.0.10- Rest endoint response verification. Ref: https://www.getpostman.com
Spring boot version 2.0.1.RELEASE Ref: https://start.spring.io/
MongoDB version: 3.6.3 Ref: https://www.mongodb.com
Mongo DB Compass community edition: Manually load no sql data.
Unit test: Junit 4


Response sample:
Response:
{
    "id": "13860428",
    "name": "The Big Lebowski (Blu-ray)",
    "currency_price": {
        "value": 13.49,
        "currency_code": "USD"
    }
}

HTTP Method: GET

HTTP Response code:
200 – Product details included
404 – Product detail not found.
500- Internal server error. For any unhandled exception occurred within application.

Error handling:
An error message will be send back to client containing error description and the product id. Here is sample error message for product information not found.
{
    "errorMessage": "Product not found, please check product id 138604281"
}

Example response for 200 OK http response status
 ---------------------------------
 ![Alt text](/Postman-200-OK.png?raw=true "GET information sucess")


Example response for 200 OK http response status (Multiple currency)
 ---------------------------------
 ![Alt text](/Postman-200-OK-multipleCurrency.PNG?raw=true "GET information sucess")

Example response for 404 OK http response status
  ---------------------------------
  ![Alt text](/Postman-404-NotFound.png?raw=true "GET information not found")

Mongo DB myRetail.pricing collection
  ![Alt text](/MongoDB-CompassEdition.png?raw=true "GET mongo DB compass community edition")





