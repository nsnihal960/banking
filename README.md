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
curl -X POST http://localhost:8080/profile/create
``
```json
{
"profile" : {
  "name" : {
    "firstName":"Nihal",
    "lastName":"Srivastava"
  },
  "mobile":{
      "countryCode":"91",
      "number":"8004968195"
  },
  "email":"nsnihal960@gmail.com",
  "mobile":"8004968195",
  "currency":"INR"
	}
}
```

#### Response
IsVerified is short circuited, but should be used to allow any transaction from/to this profile.
```json
{
    "id": "e63b26ed-8105-4608-8b8b-3e7368add5a5",
    "name": {
        "firstName": "Nihal",
        "lastName": "Srivastava"
    },
    "mobile": {
        "countryCode": "91",
        "number": "8004968195",
        "isVerified": true
    },
    "email": "nsnihal960@gmail.com",
    "mobile": "8004968195",
    "currency": "INR"
}
```


## getProfile

``curl -X GET http://localhost:8080/profile/id/{id}/``

``curl -X POST http://localhost:8080/profile/mobile/{mobile}/``
```json
{
    "countryCode":"91",
    "number":"8004968195"
}
```
# Statement

## getStatement
Get statement is a paged API, with 3 parameters: `count`, `startTime`, `endTime`. They have genuine defaults in case not provided. Response contains a pointer to next page information, if it is present.

#### Request
``curl -X POST http://localhost:8080/statement/{id}/``
```json
{
  "id" : 1,
  "endTime": 1534954568485,
  "startTime": 1534954553876,
  "count":10
}
```
#### Response
```json
{  
   "profile":{  
      "id":1,
      "name":{  
         "firstName":"Nihal",
         "lastName":"Srivastava"
      },
      "email":"nsnihal960@gmail.comww",
      "mobile":{  
         "countryCode":"91",
         "number":"8004968195",
         "isVerified":true
      },
      "currency":"INR"
   },
   "generationDate":1534955186120,
   "balance":{  
      "balance":"Rs.66.00",
      "conversionRate":0.015625
   },
   "transactions":[  
      {  
         "id":"e26bd143-2596-4bc5-9028-7373a1d53a90",
         "from":null,
         "to":1,
         "date":1534954562432,
         "amount":"Rs.10.00",
         "conversionRate":0.015625,
         "transactionType":"CREDIT",
         "balance":0.859375
      },
      {  
         "id":"cdb2a2dd-c49e-40e6-afc3-68b21f4cb09b",
         "from":null,
         "to":1,
         "date":1534954556835,
         "amount":"Rs.9.00",
         "conversionRate":0.015625,
         "transactionType":"CREDIT",
         "balance":0.703125
      }
   ],
   "nextPageEndTime":1534954556835
}


```

# Transfers/Credit/Debits

All balance transfers(add/deduct/transfer) support any currency type. However, if not provided, it defaults to user's currency choice.
Here, for simplicity purposes, we are directly using ids to operate on users, but we should rely on token mechanism. Id should not be passed in such APIs(since we are not implementing any authentication mechanism, i have used id for demo purposes)
## getBalance
``--``

## addBalance

#### Request
``curl -X POST http://localhost:8080/transaction/add/``
```json
{
  "userId" : "add3f304791-334a-4479-8268-362f79139c9b", // rely on token in prod rather ids
  "amount" : 10,
  "currency" : "INR",
  "token" : "fweefewfwe" // short circuited, but a must
}
```
#### Response
```json
{
    "balance": "Rs.10.00",
    "conversionRate": 0.015625
}
```


## deductBalance

####Request
``curl -X POST http://localhost:8080/transaction/deduct/``


## transferBalance

#### Request
``curl -X POST http://localhost:8080/transaction/transfer/``
```json
{
  "fromUserId" : "e63b26ed-8105-4608-8b8b-3e7368add5a5",
  "toUserId" : "rewfewfwefwe-334a-4479-8268-362f79139c9b",
  "amount" : 10,
  "currency" : "INR",
  "token" : "fweefewfwe" // short circuited, but a must
}
```
#### Response
```json
{
    "balance": "Rs.1.00",
    "conversionRate": 0.015625
}
```



