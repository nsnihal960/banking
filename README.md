# banking

How to start the banking application
---

1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/banking-1.0-SNAPSHOT.jar server`

# Currency supported
All time is stored in epoch and thus can be used by clients to represent in their own format.
Currency of bank is maintained in single currency(USD here), but APIs support input in any currency. Clients always get conversion rate with user preferred currency for display purposes.
Since transaction currencies can be different from sender/receiver, transaction amount is displayed with transaction currency type. For brevity, I have only implemented few currencies which are listed as follows:

``USD, INR, GBP, JPY, AUD``

APIs
---

# Profile

## createProfile

#### Request

User can create profiles by providing registration details. Profiles are uniquely identified by mobile and email. So, appropriate validation are made on these fields. UserId generation is incremental from 1(Left here for simplicity in testing/evaluation; Not to be used in production, as it results in predicting user's Ids)
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
  "currency":"INR"
	}
}
```

#### Response
IsVerified is short circuited, but should be used to allow any transaction from/to this profile.
```json
{
    "id": 1,
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
    "currency": "INR"
}
```


## getProfile

``curl -X GET http://localhost:8080/profile/id/{id}/``

``curl -X GET http://localhost:8080/profile/mobile?code={code}&number={number}``
```json
```
# Account
## getBalance
``curl -X GET http://localhost:8080/account/balance/{id}``

## getStatement
Get statement is a paged API, with 3 parameters: `count`, `startTime`, `endTime`. They have genuine defaults in case not provided. Response contains a pointer to next page information, clients can stop requesting if either no item received or page size is less than requested. The start-end time range is (inclusive, exclusive] in nature.

#### Request
``curl -X POST http://localhost:8080/account/statement?id={}&count={}&endTime=1535108770916&startTime=1535108770915``

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
      "balance":"1.00",
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

## addBalance

#### Request
``curl -X POST http://localhost:8080/transaction/add/``
```json
{
  "userId" : 1, // rely on token in prod rather ids
  "amount" : 10,
  "currency" : "INR", //can be any currency
  "token" : "fweefewfwe" // short circuited, but a must
}
```
#### Response
```json
{
    "balance": 0.5625,
    "conversionRate": 0.015625,
    "userCurrency": "INR"
}
```


## deductBalance

#### Request

``curl -X POST http://localhost:8080/transaction/deduct/``
```json
{
  "userId" : 1, // rely on token in prod rather ids
  "amount" : 1,
  "currency" : "INR", //can be any currency
  "token" : "fweefewfwe" // short circuited, but a must
}
```
#### Response
```json
{
    "balance": 0.5125,
    "conversionRate": 0.015625,
    "userCurrency": "INR"
}
```

## transferBalance
This is expected to happen from sender's account. So, his balance is returned after successful transaction.
#### Request
``curl -X POST http://localhost:8080/transaction/transfer/``
```json
{
    "fromUserId" : 1,
    "toUserId" : 2,
    "amount" : 10,
    "currency" : "INR", //can be any currency
    "token" : "fweefewfwe" // short circuited, but a must
}
```
#### Response
```json
{
    "balance": 9.375,
    "conversionRate": 0.015625,
    "userCurrency": "INR"
}
```



