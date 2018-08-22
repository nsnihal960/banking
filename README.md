# banking

How to start the banking application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/banking-1.0-SNAPSHOT.jar server`
1. To check that your application is running enter url `http://localhost:8080`

# Currency supported

For brevity, I have only implemented few currencies which are listed as follows:

``USD, INR, GBP, JPY, AUD``

APIs
---

# Profile

## createProfile

#### Request
``
curl -X POST http://localhost:3000/profile/create
``
```json
{
"profile" : {
  "name" : {
    "firstName":"Nihal",
    "lastName":"Srivastava"
  },
  "email":"nsnihal960@gmail.com",
  "mobile":"8004968195",
  "currency":"INR"
	}
}
```

#### Response
```json
{
    "id": "e63b26ed-8105-4608-8b8b-3e7368add5a5",
    "name": {
        "firstName": "Nihal",
        "lastName": "Srivastava"
    },
    "email": "nsnihal960@gmail.com",
    "mobile": "8004968195",
    "currency": "INR"
}
```


## getProfile

``curl -X GET http://localhost:3000/profile/id/{id}/``

``curl -X GET http://localhost:3000/profile/mobile/{mobile}/``

``curl -X GET http://localhost:3000/profile/email/{email}/``

# Statement

## getStatement

#### Request
``curl -X GET http://localhost:3000/statement/{id}/``
#### Response
```json
{  
   "profile":{  
      "id":"e63b26ed-8105-4608-8b8b-3e7368add5a5",
      "name":{  
         "firstName":"Nihal",
         "lastName":"Srivastava"
      },
      "email":"nsnihal960@gmail.com",
      "mobile":"8004968195",
      "currency":"INR"
   },
   "balance":{  
      "balance":"Rs.0.50",
      "conversionRate":0.015625
   },
   "transactions":[  
      {  
         "id":"80a1a226-4d84-490c-8c87-c3768735553b",
         "from":null,
         "to":"e63b26ed-8105-4608-8b8b-3e7368add5a5",
         "date":1534939169839,
         "amount":"Rs.10.00",
         "conversionRate":0.015625,
         "transactionType":"CREDIT",
         "totalBalance":0.15625
      },
      {  
         "id":"afbd458a-45c8-4ade-a14c-d761bbdec792",
         "from":"e63b26ed-8105-4608-8b8b-3e7368add5a5",
         "to":null,
         "date":1534939358091,
         "amount":"Rs.8.50",
         "conversionRate":0.015625,
         "transactionType":"DEBIT",
         "totalBalance":0.0234375
      },
      {  
         "id":"0eca91ef-fc4a-41c0-b186-6df06c366eff",
         "from":"e63b26ed-8105-4608-8b8b-3e7368add5a5",
         "to":"2c7723e6-0ef7-4b7e-933e-24b240d2d72a",
         "date":1534939502636,
         "amount":"Rs.1.00",
         "conversionRate":0.015625,
         "transactionType":"DEBIT",
         "totalBalance":0.0078125
      }
   ],
   "generationData":1534939571230
}

```

#Transfers/Credit/Debits

All balance transfers(add/deduct/transfer) support any currency type. However, if not provided, it defaults to user's currency choice.
##getBalance
``--``

##addBalance

####Request
``curl -X POST http://localhost:3000/transaction/add/``
```json
{
  "userId" : "add3f304791-334a-4479-8268-362f79139c9b",
  "amount" : 10,
  "currency" : "INR"
}
```
####Response
```json
{
    "balance": "Rs.10.00",
    "conversionRate": 0.015625
}
```


##deductBalance

####Request
``curl -X POST http://localhost:3000/transaction/deduct/``


##TransferBalance

####Request
``curl -X POST http://localhost:3000/transaction/transfer/``
```json
{
  "fromUserId" : "e63b26ed-8105-4608-8b8b-3e7368add5a5",
  "toUserId" : "rewfewfwefwe-334a-4479-8268-362f79139c9b",
  "amount" : 10,
  "currency" : "INR"
}
```
####Response
```json
{
    "balance": "Rs.1.00",
    "conversionRate": 0.015625
}
```



