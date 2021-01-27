# Install

	Java 11
	Maven 3.6.3

## Build and Run

$ mvn clean spring-boot:run


## Test Endpoints (Local)
Get - http://localhost:8080/api/statement-service/status

Get - http://localhost:9000/api/statement-service/validate


	Sample Request Body: 
		
	[{
       "transactionRef" : 12,
       "accountNumber" : 2,
       "startBalance" : "10",
       "mutation" : "5",
       "endBalance" : 5
     },
      {
       "transactionRef" : 13,
       "accountNumber" : 3,
       "startBalance" : "17",
       "mutation" : "7",
       "endBalance" : 10
     },
     {
       "transactionRef" : 14,
       "accountNumber" : 3,
       "startBalance" : "17",
       "mutation" : "7",
       "endBalance" : 24
     }]

## Unit and Integration Test 
- StmtBuilderUnitTest
- StmtFilterValidationUnitTest
- StmtValidatorTest
- StmtIntegrationTests (StmtController)

