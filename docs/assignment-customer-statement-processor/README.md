# Customer Statement Processor

In our bank we receives monthly deliveries of customer statement records. This information is delivered in JSON Format.
These records need to be validated.

## Input

The format is as follows: Table 1. Record description

| Field                  | Description                                      | 
|------------------------|------------------------------------------------- |
| Transaction reference  |A numeric value                                   |
| Account number         |  An IBAN                                         |
| Start Balance          |  The starting balance in Euros                   |
| Mutation               |  Either an addition (+) or a deduction (-)       |
| Description            | Free text                                        |
| End Balance            | The end balance in Euros                         |

## Assignment

Implement a REST service which receives the customer statement JSON as a POST data, Perform the below validations

1. All transaction references should be unique
2. The end balance needs to be validated ( Start Balance +/- Mutation = End Balance )

## Expected Output

| Http Status Code  | Condition                                                         |  Response format |
|---                |---                                                                |---               |
| 200               | When there are no duplicate reference and correct end balance     | `{"result" : "SUCCESSFUL", "errorRecords" : []}`|
| 200               |When there are duplicate reference and correct balance             |[duplicateReferenceAndcorrectBalance Json](./duplicateReferenceAndcorrectBalance.json)|
| 200               |When there are no duplicate reference and In correct balance       |[IncorrectBalance Json](./IncorrectBalance.json)|
| 200               |When there are duplicate reference and In correct balance          |[duplicateReferenceAndIncorrectBalance Json](./duplicateReferenceAndIncorrectBalance.json)|
| 400               |Error during parsing JSON                                          | `{"result" : "BAD_REQUEST", "errorRecords" : []}`|
| 500               |Any other situation                                                |`{"result" : "INTERNAL_SERVER_ERROR","errorRecords" : [] }`|

## Instructions

* Assignment should be submitted as a maven project
* Junit should be well written.
* Integration test should be implemented ( For Mediors only )
