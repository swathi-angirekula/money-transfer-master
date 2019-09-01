# Money Transfer Application


##About
A RESTful API that allows transfer money from one Bank Account to another in GBP.

It uses just to entities:
* _transfer_ - the money transfer transfer used to initialize the transfer
* _bankAccount_ - the bank account which has balance in the specified currency


## How to start

    mvn clean install
    
To run the app execute:

    mvn exec:java
or

    java -jar /target/<filename>.jar

Refer console for port details 

## API Definition

{
"info": {
"_postman_id": "5dc745b1-78e3-4223-82ff-b67b2ab8208d",
"name": "MoneyTransferApplication",
"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
},
"item": [
{
"name": "Create Bank Account",
"request": {
"method": "POST",
"header": [
{
"key": "Content-Type",
"name": "Content-Type",
"value": "application/json",
"type": "text"
}
],
"body": {
"mode": "raw",
"raw": "{\n\t\"name\" : \"Kusuma Angirekula\"\n}"
},
"url": {
"raw": "http://localhost:8080/bankAccounts",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"bankAccounts"
]
}
},
"response": []
},
{
"name": "Get All Bank Accounts",
"request": {
"method": "GET",
"header": [
{
"key": "Content-Type",
"name": "Content-Type",
"value": "application/json",
"type": "text"
}
],
"body": {
"mode": "raw",
"raw": ""
},
"url": {
"raw": "http://localhost:8080/bankAccounts",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"bankAccounts"
]
}
},
"response": []
},
{
"name": "Update Bank Account_Change name",
"request": {
"method": "PUT",
"header": [
{
"key": "Content-Type",
"name": "Content-Type",
"type": "text",
"value": "application/json"
}
],
"body": {
"mode": "raw",
"raw": ""
},
"url": {
"raw": "http://localhost:8080/bankAccounts/6?action=updateName&name=Malleswari Angirekula",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"bankAccounts",
"6"
],
"query": [
{
"key": "action",
"value": "updateName"
},
{
"key": "name",
"value": "Malleswari Angirekula"
}
]
}
},
"response": []
},
{
"name": "Update Bank Account_Deposit Money",
"request": {
"method": "PUT",
"header": [
{
"key": "Content-Type",
"name": "Content-Type",
"type": "text",
"value": "application/json"
}
],
"body": {
"mode": "raw",
"raw": ""
},
"url": {
"raw": "http://localhost:8080/bankAccounts/6?action=deposit&amount=5000",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"bankAccounts",
"6"
],
"query": [
{
"key": "action",
"value": "deposit"
},
{
"key": "amount",
"value": "5000"
}
]
}
},
"response": []
},
{
"name": "Create Transfer Request",
"request": {
"method": "POST",
"header": [
{
"key": "Content-Type",
"name": "Content-Type",
"type": "text",
"value": "application/json"
}
],
"body": {
"mode": "raw",
"raw": "{\r\n\"fromBankAccountId\" : 2,\r\n\"toBankAccountId\" : 1,\r\n\"amount\" : 5\r\n}"
},
"url": {
"raw": "http://localhost:8080/transfers",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"transfers"
]
}
},
"response": []
},
{
"name": "Get All transfers",
"request": {
"method": "GET",
"header": [
{
"key": "Content-Type",
"name": "Content-Type",
"type": "text",
"value": "application/json"
}
],
"body": {
"mode": "raw",
"raw": ""
},
"url": {
"raw": "http://localhost:8080/transfers",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"transfers"
]
}
},
"response": []
},
{
"name": "Get transfer by Id",
"request": {
"method": "GET",
"header": [
{
"key": "Content-Type",
"name": "Content-Type",
"type": "text",
"value": "application/json"
}
],
"body": {
"mode": "raw",
"raw": ""
},
"url": {
"raw": "http://localhost:8080/transfers",
"protocol": "http",
"host": [
"localhost"
],
"port": "8080",
"path": [
"transfers"
]
}
},
"response": []
}
]
}