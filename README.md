# MyRetail Application

Retrieves product details for a given product id and responses back to the caller of the rest endpoint.

Endpoint URL: http://localhost:8080/products/{productId}

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


 ---------------------------------
 ![Alt text](/Postman-200-OK.png?raw=true "GET information sucess")






