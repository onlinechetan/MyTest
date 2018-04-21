# MyRetail Application

Retrieves product details for a given product id and responses back to the caller of the rest endpoint.

Endpoint URL: http://localhost:8080/products/{productId}

## Response sample:
Response:
{
    "id": "16696652",
    "name": "Beats Solo 2 Wireless - Black",
    "currency_price": [
        {
            "value": 9.99,
            "currency_code": "USD"
        }
    ]
}  

## HTTP Method: GET

HTTP Response code:  
200 – Product details included.  
404 – Product detail not found.  
500- Internal server error. For any unhandled exception occurred within application.  

## Error handling:
An error message will be send back to client containing error description and the product id. Here is sample error message for product information not found.
{
    "errorMessage": "Product not found, please check product id 138604281"
}

## Example response for 200 OK http response status
 ---------------------------------
 ![Alt text](/Postman-200-OK.png?raw=true "GET information sucess")


## Example response for 200 OK http response status (Multiple currency)
 ---------------------------------
 ![Alt text](/Postman-200-OK-multipleCurrency.PNG?raw=true "GET information sucess")

## Example response for 404 OK http response status
  ---------------------------------
  ![Alt text](/Postman-404-NotFound.png?raw=true "GET information not found")

## Mongo DB myRetail.pricing collection
  ![Alt text](/MongoDB-CompassEdition.png?raw=true "GET mongo DB compass community edition")

## Unit test results
  ![Alt text](/UnitTest.PNG?raw=true "Controller unit test")



## Technology Stack:

Postman v6.0.10- Rest endoint response verification. Ref: https://www.getpostman.com  
Spring boot version 2.0.1.RELEASE Ref: https://start.spring.io/  
Lombak support for Getters and setters: https://projectlombok.org/download  
MongoDB version: 3.6.3 Ref: https://www.mongodb.com  
Mongo DB Compass community edition: Manually load no sql data.  
Unit test: Junit 4. https://junit.org    

## Loading no sql data
pre-requisite: MongoDB is deployed and running
Mongo DB: MongoDB server version: 3.6.3
port: 27017(default)
commands: 
connect to database (will create if not exist)   
> use myRetail 
switched to db myRetail    
create pricing collection  
> db.createCollection("pricing")
{ "ok" : 1 }    
Insert data for get operations  
> db.pricing.insert({productId: "13860428", currency_code: "USD", value:13.49})
WriteResult({ "nInserted" : 1 })  
> db.pricing.insert({productId: "13860428", currency_code: "INR", value:750})
WriteResult({ "nInserted" : 1 })  
> db.pricing.insert({productId: "16696652", currency_code: "USD", value:9.99})
WriteResult({ "nInserted" : 1 })  

         

## Deployment
Please follow below instructions for deploying the application.

Instructions using: Operating System: windows 10.

1. Install JRE http://www.oracle.com/technetwork/java/javase/8u161-relnotes-4021379.html.  
2. Install MONGO DB: Follow instructions from "MongoDB Installation for windows.pdf".  
3. Maven install: https://maven.apache.org/install.html  
4. Clone myRetail application: https://github.com/onlinechetan/MyTest.git  
5. Navigate to cloned repository path.    
6. Create a deployable artifact (Ref: https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html)  
7. "C:\Program Files\Java\jdk1.8.0_161\bin\java" "-Dmaven.multiModuleProjectDirectory=C:\Users\Chetan Saraf\target\MyTest" "-Dmaven.home=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2017.3.1\plugins\maven\lib\maven3" "-Dclassworlds.conf=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2017.3.1\plugins\maven\lib\maven3\bin\m2.conf" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2017.3.1\lib\idea_rt.jar=53589:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2017.3.1\bin" -Dfile.encoding=UTF-8 -classpath "C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2017.3.1\plugins\maven\lib\maven3\boot\plexus-classworlds-2.5.2.jar" org.codehaus.classworlds.Launcher clean install  
Note: I used intellij to generate artifact output, here is the command, please adjust the path to run the maven "install clean command."  
8. Run the applcation: from installation directory.    
    java -jar target\target-0.0.1-SNAPSHOT.jar

## Testing
1. Download post man from https://www.getpostman.com/  
2. Example urls: http://localhost:8080/products/16696652 - gets product details for 16696652  
3. Unit test located under: src\test folder.  


## Considerations for productions deployment:

Project is developed as POC as expected during the first phase. Further enhancements  
could be done for further development and production readiness.  
	Meeting the Service Level Aggrements.  
   Automated e2e integration test cases using tools like Rest Assured.  
	Addtional automated unit testing unit.  
   Support for encironments using profiles.  
	Performance testing.  
	Automation supports for build and deployments.  
	Sonar and security scans.  
	Disaster recovery plan and drills.  
	Support documentations.

