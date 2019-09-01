# Money Transfer Application


## About
A RESTful API that allows transfer money from one Bank Account to another in GBP.

## How to start

    mvn clean install
    
To run the app execute:

    mvn exec:java
or

    java -jar /target/<filename>.jar

Refer console for port details 

## To Test: 

This Application is updated with Unit tests which tests multiple scenarios. 
In order to test the application via postman, please copy the contents under Postman test suite and run via Postman.


In order to test the application via SoapUI, please copy the contents under SoapUI test suite and run via Postman.

## Postman Test Suite

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

## SoapUI Test 

<?xml version="1.0" encoding="UTF-8"?>
<con:soapui-project id="5b7f35b2-4c26-4348-94d1-4549e4938e5e" activeEnvironment="Default" name="application" resourceRoot="" soapui-version="5.5.0" xmlns:con="http://eviware.com/soapui/config"><con:settings/><con:interface xsi:type="con:RestService" id="d09debf7-62f9-4aef-a277-77da1693a995" wadlVersion="http://wadl.dev.java.net/2009/02" name="application" type="rest" basePath="/" definitionUrl="file:/C:/Users/eliyaz.ahmed.shaik/OneDrive%20-%20Accenture/Desktop/application.wadl" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><con:settings/><con:definitionCache type="TEXT" rootPart="file:/C:/Users/eliyaz.ahmed.shaik/OneDrive%20-%20Accenture/Desktop/application.wadl"><con:part><con:url>file:/C:/Users/eliyaz.ahmed.shaik/OneDrive%20-%20Accenture/Desktop/application.wadl</con:url><con:content><![CDATA[<application xmlns="http://wadl.dev.java.net/2009/02">
  <doc jersey:generatedBy="Jersey: 2.26-b07 2017-06-30 12:48:47" xmlns:jersey="http://jersey.java.net/"/>
  <doc jersey:hint="This is simplified WADL with user and core resources only. To get full WADL with extended resources use the query parameter detail. Link: http://localhost:8080/application.wadl?detail=true" xmlns:jersey="http://jersey.java.net/"/>
  <grammars/>
  <resources base="http://localhost:8080/">
    <resource path="/transfers">
      <method id="createTransactionToTransferMoney" name="POST">
        <response>
          <representation mediaType="application/json"/>
        </response>
      </method>
      <method id="getAllPendingTransfers" name="GET">
        <response>
          <representation mediaType="application/json"/>
        </response>
      </method>
      <resource path="{id}">
        <param name="id" style="template" type="xs:long" xmlns:xs="http://www.w3.org/2001/XMLSchema"/>
        <method id="getTransactionById" name="GET">
          <response>
            <representation mediaType="application/json"/>
          </response>
        </method>
      </resource>
    </resource>
    <resource path="/bankAccounts">
      <method id="getAllBankAccounts" name="GET">
        <response>
          <representation mediaType="application/json"/>
        </response>
      </method>
      <method id="createBankAccount" name="POST">
        <response>
          <representation mediaType="application/json"/>
        </response>
      </method>
      <resource path="{id}">
        <param name="id" style="template" type="xs:long" xmlns:xs="http://www.w3.org/2001/XMLSchema"/>
        <method id="getBankAccountById" name="GET">
          <response>
            <representation mediaType="application/json"/>
          </response>
        </method>
        <method id="updateBankAccount" name="PUT">
          <request>
            <param name="action" style="query" type="xs:string" xmlns:xs="http://www.w3.org/2001/XMLSchema"/>
            <param name="name" style="query" type="xs:string" xmlns:xs="http://www.w3.org/2001/XMLSchema"/>
            <param name="amount" style="query" type="xs:string" xmlns:xs="http://www.w3.org/2001/XMLSchema"/>
          </request>
          <response>
            <representation mediaType="application/json"/>
          </response>
        </method>
      </resource>
    </resource>
  </resources>
</application>]]></con:content><con:type>http://wadl.dev.java.net/2009/02</con:type></con:part></con:definitionCache><con:endpoints><con:endpoint>http://localhost:8080</con:endpoint></con:endpoints><con:resource name="/transfers" path="/transfers" id="c55da34a-fc64-40f0-9965-307b26c048e5"><con:settings/><con:parameters/><con:resource name="{id}" path="{id}" id="9917797b-506b-4922-bfe1-fea6259edcec"><con:settings/><con:parameters><con:parameter><con:name>id</con:name><con:value xsi:nil="true"/><con:style>TEMPLATE</con:style><con:type xmlns:xs="http://www.w3.org/2001/XMLSchema">xs:long</con:type><con:default xsi:nil="true"/></con:parameter></con:parameters><con:method name="GET - getTransactionById" id="456507ff-ca1a-4aa8-b9e6-b3d6546ece67" method="GET"><con:settings/><con:parameters/><con:representation type="RESPONSE" id=""><con:mediaType>application/json</con:mediaType><con:params/><con:element xsi:nil="true"/><con:description xsi:nil="true"/></con:representation><con:request name="Request 1" id="55a81473-44c5-4420-aa8b-5f3f3b634622" mediaType="application/json"><con:settings/><con:endpoint>http://localhost:8080</con:endpoint><con:parameters/></con:request></con:method></con:resource><con:method name="POST - createTransactionToTransferMoney" id="77214531-5bd5-41cb-a568-53255107ee4b" method="POST"><con:settings/><con:parameters/><con:representation type="RESPONSE" id=""><con:mediaType>application/json</con:mediaType><con:params/><con:element xsi:nil="true"/><con:description xsi:nil="true"/></con:representation><con:request name="Request 1" id="b0a7c7b1-a772-4f86-b464-ae8b6b6b6752" mediaType="application/json" postQueryString="false"><con:settings/><con:endpoint>http://localhost:8080</con:endpoint><con:request/><con:credentials><con:authType>No Authorization</con:authType></con:credentials><con:jmsConfig JMSDeliveryMode="PERSISTENT"/><con:jmsPropertyConfig/><con:parameters/></con:request></con:method><con:method name="GET - getAllPendingTransfers" id="c132b31d-0c14-4320-b4c6-53bc6c60d045" method="GET"><con:settings/><con:parameters/><con:representation type="RESPONSE" id=""><con:mediaType>application/json</con:mediaType><con:params/><con:element xsi:nil="true"/><con:description xsi:nil="true"/></con:representation><con:request name="Request 1" id="3581ba1c-de56-4aa0-a013-bf8d221af957" mediaType="application/json"><con:settings/><con:endpoint>http://localhost:8080</con:endpoint><con:parameters/></con:request></con:method></con:resource><con:resource name="/bankAccounts" path="/bankAccounts" id="27654787-a3b5-4fed-b99a-28f91c9eb61c"><con:settings/><con:parameters/><con:resource name="{id}" path="{id}" id="67a0d90e-dd68-4224-b16a-df2c114f327e"><con:settings/><con:parameters><con:parameter><con:name>id</con:name><con:value xsi:nil="true"/><con:style>TEMPLATE</con:style><con:type xmlns:xs="http://www.w3.org/2001/XMLSchema">xs:long</con:type><con:default xsi:nil="true"/></con:parameter></con:parameters><con:method name="GET - getBankAccountById" id="5d92f4de-64d3-4232-8cd3-85efda2d6715" method="GET"><con:settings/><con:parameters/><con:representation type="RESPONSE" id=""><con:mediaType>application/json</con:mediaType><con:params/><con:element xsi:nil="true"/><con:description xsi:nil="true"/></con:representation><con:request name="Request 1" id="1b9e9509-f01e-48f7-9206-6e2eb456249f" mediaType="application/json"><con:settings/><con:endpoint>http://localhost:8080</con:endpoint><con:parameters/></con:request></con:method><con:method name="PUT - updateBankAccount" id="10de96a9-4a31-41dd-91ae-defe2ade158f" method="PUT"><con:settings/><con:parameters><con:parameter><con:name>action</con:name><con:value xsi:nil="true"/><con:style>QUERY</con:style><con:default xsi:nil="true"/></con:parameter><con:parameter><con:name>name</con:name><con:value xsi:nil="true"/><con:style>QUERY</con:style><con:default xsi:nil="true"/></con:parameter><con:parameter><con:name>amount</con:name><con:value xsi:nil="true"/><con:style>QUERY</con:style><con:default xsi:nil="true"/></con:parameter></con:parameters><con:representation type="RESPONSE" id=""><con:mediaType>application/json</con:mediaType><con:params/><con:element xsi:nil="true"/><con:description xsi:nil="true"/></con:representation><con:request name="Request 1" id="9a5523a4-71a6-4a73-b2ae-79bfe2d1c55f" mediaType="application/json"><con:settings/><con:endpoint>http://localhost:8080</con:endpoint><con:parameters/></con:request></con:method></con:resource><con:method name="GET - getAllBankAccounts" id="7f256aac-2d59-4833-83bc-604ae69cae2a" method="GET"><con:settings/><con:parameters/><con:representation type="RESPONSE" id=""><con:mediaType>application/json</con:mediaType><con:params/><con:element xsi:nil="true"/><con:description xsi:nil="true"/></con:representation><con:request name="Request 1" id="5a854d27-07c3-4e1d-a480-1b59ad49bf12" mediaType="application/json"><con:settings/><con:endpoint>http://localhost:8080</con:endpoint><con:parameters/></con:request></con:method><con:method name="POST - createBankAccount" id="3afd8651-fa73-4199-95c2-3b19d6177adf" method="POST"><con:settings/><con:parameters/><con:representation type="RESPONSE" id=""><con:mediaType>application/json</con:mediaType><con:params/><con:element xsi:nil="true"/><con:description xsi:nil="true"/></con:representation><con:request name="Request 1" id="90a9c155-fe67-44e6-b71c-aa932c7d0e87" mediaType="application/json"><con:settings/><con:endpoint>http://localhost:8080</con:endpoint><con:parameters/></con:request></con:method></con:resource></con:interface><con:testSuite id="322cc3e5-fcb0-4d44-a273-8ee02c230945" name="application TestSuite"><con:description>TestSuite generated for REST Service [application]</con:description><con:settings/><con:runType>SEQUENTIAL</con:runType><con:testCase id="fd0a88cc-7745-4c63-b4aa-40d6287baef1" failOnError="true" failTestCaseOnErrors="true" keepSession="false" maxResults="0" name="application TestSuite" searchProperties="true"><con:settings/><con:testStep type="restrequest" name="Create Bank Account" id="a8cbe823-3a84-46ed-9a42-6d645d3d9f7a"><con:settings/><con:config service="application" resourcePath="//bankAccounts" methodName="POST - createBankAccount" xsi:type="con:RestRequestStep" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><con:restRequest name="/bankAccounts" id="90a9c155-fe67-44e6-b71c-aa932c7d0e87" mediaType="application/json" postQueryString="false"><con:settings><con:setting id="com.eviware.soapui.impl.wsdl.WsdlRequest@request-headers">&lt;xml-fragment/></con:setting></con:settings><con:endpoint>http://localhost:8080</con:endpoint><con:request>{
"name" : "Kusuma Angirekula"
}</con:request><con:originalUri>http://localhost//bankAccounts</con:originalUri><con:assertion type="Simple Contains" id="1061369d-78c7-4eb8-826a-05079abfee00" name="Contains"><con:configuration><token>Kusuma Angirekula</token><ignoreCase>false</ignoreCase><useRegEx>false</useRegEx></con:configuration></con:assertion><con:assertion type="Valid HTTP Status Codes" id="bcb693e8-8116-4dff-b2cb-739df564f35e" name="Valid HTTP Status Codes"><con:configuration><codes>200</codes></con:configuration></con:assertion><con:credentials><con:authType>No Authorization</con:authType></con:credentials><con:jmsConfig JMSDeliveryMode="PERSISTENT"/><con:jmsPropertyConfig/><con:parameters/></con:restRequest></con:config></con:testStep><con:testStep type="restrequest" name="Get All Bank accounts" id="f957ea6a-9591-496d-b676-5f105760ea48"><con:settings/><con:config service="application" resourcePath="//bankAccounts" methodName="GET - getAllBankAccounts" xsi:type="con:RestRequestStep" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><con:restRequest name="/bankAccounts" id="5a854d27-07c3-4e1d-a480-1b59ad49bf12" mediaType="application/json"><con:settings><con:setting id="com.eviware.soapui.impl.wsdl.WsdlRequest@request-headers">&lt;xml-fragment/></con:setting></con:settings><con:endpoint>http://localhost:8080</con:endpoint><con:request/><con:originalUri>http://localhost//bankAccounts</con:originalUri><con:assertion type="Valid HTTP Status Codes" id="a2ddd8e8-b5cb-4fcd-8d60-f454eb7e1933" name="Valid HTTP Status Codes"><con:configuration><codes>200
</codes></con:configuration></con:assertion><con:assertion type="GroovyScriptAssertion" id="d8a29575-7215-4b16-9ac8-7db59e7df212" name="Script Assertion"><con:configuration><scriptText>def list = new groovy.json.JsonSlurper().parseText( context.response )
assert list.size() >= 4</scriptText></con:configuration></con:assertion><con:credentials><con:authType>No Authorization</con:authType></con:credentials><con:jmsConfig JMSDeliveryMode="PERSISTENT"/><con:jmsPropertyConfig/><con:parameters/></con:restRequest></con:config></con:testStep><con:testStep type="restrequest" name="Get Bank account by ID" id="9aae5315-5540-44e3-9166-dee693f4e93f"><con:settings/><con:config service="application" resourcePath="//bankAccounts/{id}" methodName="GET - getBankAccountById" xsi:type="con:RestRequestStep" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><con:restRequest name="{id}" id="1b9e9509-f01e-48f7-9206-6e2eb456249f" mediaType="application/json"><con:settings><con:setting id="com.eviware.soapui.impl.wsdl.WsdlRequest@request-headers">&lt;xml-fragment/></con:setting></con:settings><con:endpoint>http://localhost:8080</con:endpoint><con:request/><con:originalUri>http://localhost/6//bankAccounts/</con:originalUri><con:assertion type="Valid HTTP Status Codes" id="46b79e0c-a1d7-4a41-8328-221d9575ba86" name="Valid HTTP Status Codes"><con:configuration><codes>200</codes></con:configuration></con:assertion><con:assertion type="JsonPath Match" id="968685a9-dc1a-40b0-bf4a-4de7e74efb67" name="JsonPath Match"><con:configuration><path>$.name</path><content>Swathi Angirekula</content><allowWildcards>false</allowWildcards><ignoreNamspaceDifferences>false</ignoreNamspaceDifferences><ignoreComments>false</ignoreComments></con:configuration></con:assertion><con:credentials><con:authType>No Authorization</con:authType></con:credentials><con:jmsConfig JMSDeliveryMode="PERSISTENT"/><con:jmsPropertyConfig/><con:parameters><entry key="id" value="1" xmlns="http://eviware.com/soapui/config"/></con:parameters></con:restRequest></con:config></con:testStep><con:testStep type="restrequest" name="Update bank account _ Change Name" id="29f9544f-173b-40da-838a-d1a87d47ed3c"><con:settings/><con:config service="application" resourcePath="//bankAccounts/{id}" methodName="PUT - updateBankAccount" xsi:type="con:RestRequestStep" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><con:restRequest name="{id}" id="9a5523a4-71a6-4a73-b2ae-79bfe2d1c55f" mediaType="application/json" postQueryString="false"><con:settings><con:setting id="com.eviware.soapui.impl.wsdl.WsdlRequest@request-headers">&lt;xml-fragment/></con:setting></con:settings><con:endpoint>http://localhost:8080</con:endpoint><con:request>{
    "id": 6,
    "name": "Malleswari Angirekula",
    "balance": 0,
    "blockedAmount": 0,
    "currency": "GBP"
}</con:request><con:originalUri>http://localhost//bankAccounts/</con:originalUri><con:assertion type="Valid HTTP Status Codes" id="8700d1f4-c5e9-4f94-a347-15a472c101af" name="Valid HTTP Status Codes"><con:configuration><codes>200</codes></con:configuration></con:assertion><con:assertion type="JsonPath Match" id="407148d1-5a73-41ec-be58-0da777a5de6a" name="JsonPath Match"><con:configuration><path>$.name</path><content>Malleswari Angirekula</content><allowWildcards>false</allowWildcards><ignoreNamspaceDifferences>false</ignoreNamspaceDifferences><ignoreComments>false</ignoreComments></con:configuration></con:assertion><con:credentials><con:authType>No Authorization</con:authType></con:credentials><con:jmsConfig JMSDeliveryMode="PERSISTENT"/><con:jmsPropertyConfig/><con:parameters>
  <con:entry key="name" value="Malleswari Angirekula"/>
  <con:entry key="action" value="updateName"/>
  <con:entry key="id" value="4"/>
</con:parameters></con:restRequest></con:config></con:testStep><con:testStep type="restrequest" name="Update bank account _ Deposit Money" id="eeb8337c-a1e7-482e-92e1-5b30b87433c0"><con:settings/><con:config service="application" resourcePath="//bankAccounts/{id}" methodName="PUT - updateBankAccount" xsi:type="con:RestRequestStep" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><con:restRequest name="Update bank account _ Deposit Money" id="9a5523a4-71a6-4a73-b2ae-79bfe2d1c55f" mediaType="application/json" postQueryString="false"><con:settings><con:setting id="com.eviware.soapui.impl.wsdl.WsdlRequest@request-headers">&lt;xml-fragment/></con:setting></con:settings><con:endpoint>http://localhost:8080</con:endpoint><con:request>{
    "id": 6,
    "name": "Malleswari Angirekula",
    "balance": 5000,
    "blockedAmount": 0,
    "currency": "GBP"
}</con:request><con:originalUri>http://localhost//bankAccounts/</con:originalUri><con:assertion type="Valid HTTP Status Codes" id="b9a1bfa6-faf4-42e2-9f49-38617014e163" name="Valid HTTP Status Codes"><con:configuration><codes>200</codes></con:configuration></con:assertion><con:assertion type="JsonPath Match" id="6ab784c6-20ab-4e5e-b13f-75d777d060e2" name="JsonPath Match"><con:configuration><path>$.name</path><content>Malleswari Angirekula</content><allowWildcards>false</allowWildcards><ignoreNamspaceDifferences>false</ignoreNamspaceDifferences><ignoreComments>false</ignoreComments></con:configuration></con:assertion><con:assertion type="JsonPath Match" id="a9aa907c-6bdc-4abe-b97d-ff0969268030" name="JsonPath Match Amount"><con:configuration><path>$.balance</path><content>5000.5</content><allowWildcards>false</allowWildcards><ignoreNamspaceDifferences>false</ignoreNamspaceDifferences><ignoreComments>false</ignoreComments></con:configuration></con:assertion><con:credentials><con:authType>No Authorization</con:authType></con:credentials><con:jmsConfig JMSDeliveryMode="PERSISTENT"/><con:jmsPropertyConfig/><con:parameters>
  <con:entry key="amount" value="5000.5"/>
  <con:entry key="name" value=""/>
  <con:entry key="action" value="deposit"/>
  <con:entry key="id" value="4"/>
</con:parameters></con:restRequest></con:config></con:testStep><con:testStep type="restrequest" name="Update bank account _ Deposit Money Again" id="fc3b9033-058f-4fd6-bfd7-a86b9df4ff72"><con:settings/><con:config service="application" resourcePath="//bankAccounts/{id}" methodName="PUT - updateBankAccount" xsi:type="con:RestRequestStep" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><con:restRequest name="Update bank account _ Deposit Money Again" id="9a5523a4-71a6-4a73-b2ae-79bfe2d1c55f" mediaType="application/json" postQueryString="false"><con:settings><con:setting id="com.eviware.soapui.impl.wsdl.WsdlRequest@request-headers">&lt;xml-fragment/></con:setting></con:settings><con:endpoint>http://localhost:8080</con:endpoint><con:request>{
    "id": 6,
    "name": "Malleswari Angirekula",
    "balance": 5000,
    "blockedAmount": 0,
    "currency": "GBP"
}</con:request><con:originalUri>http://localhost//bankAccounts/</con:originalUri><con:assertion type="Valid HTTP Status Codes" id="b9a1bfa6-faf4-42e2-9f49-38617014e163" name="Valid HTTP Status Codes"><con:configuration><codes>200</codes></con:configuration></con:assertion><con:assertion type="JsonPath Match" id="6ab784c6-20ab-4e5e-b13f-75d777d060e2" name="JsonPath Match"><con:configuration><path>$.name</path><content>Malleswari Angirekula</content><allowWildcards>false</allowWildcards><ignoreNamspaceDifferences>false</ignoreNamspaceDifferences><ignoreComments>false</ignoreComments></con:configuration></con:assertion><con:assertion type="JsonPath Match" id="a9aa907c-6bdc-4abe-b97d-ff0969268030" name="JsonPath Match Amount"><con:configuration><path>$.balance</path><content>5011.0</content><allowWildcards>false</allowWildcards><ignoreNamspaceDifferences>false</ignoreNamspaceDifferences><ignoreComments>false</ignoreComments></con:configuration></con:assertion><con:credentials><con:authType>No Authorization</con:authType></con:credentials><con:jmsConfig JMSDeliveryMode="PERSISTENT"/><con:jmsPropertyConfig/><con:parameters>
  <con:entry key="amount" value="10.5"/>
  <con:entry key="name" value=""/>
  <con:entry key="action" value="deposit"/>
  <con:entry key="id" value="4"/>
</con:parameters></con:restRequest></con:config></con:testStep><con:testStep type="restrequest" name="Get All Bank accounts Again" id="4e4535b6-9378-4d90-9b60-c69f814912c9"><con:settings/><con:config service="application" resourcePath="//bankAccounts" methodName="GET - getAllBankAccounts" xsi:type="con:RestRequestStep" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><con:restRequest name="Get All Bank accounts Again" id="5a854d27-07c3-4e1d-a480-1b59ad49bf12" mediaType="application/json"><con:settings><con:setting id="com.eviware.soapui.impl.wsdl.WsdlRequest@request-headers">&lt;xml-fragment/></con:setting></con:settings><con:endpoint>http://localhost:8080</con:endpoint><con:request/><con:originalUri>http://localhost//bankAccounts</con:originalUri><con:assertion type="Valid HTTP Status Codes" id="37a637d4-67be-4aa5-bca3-8ed87cc28d4b" name="Valid HTTP Status Codes"><con:configuration><codes>200</codes></con:configuration></con:assertion><con:assertion type="GroovyScriptAssertion" id="fc1c2ff0-56b0-42c5-a2da-7ccb986b3089" name="Script Assertion"><con:configuration><scriptText>def list = new groovy.json.JsonSlurper().parseText( context.response )
assert list.size() >= 4</scriptText></con:configuration></con:assertion><con:credentials><con:authType>No Authorization</con:authType></con:credentials><con:jmsConfig JMSDeliveryMode="PERSISTENT"/><con:jmsPropertyConfig/><con:parameters/></con:restRequest></con:config></con:testStep><con:testStep type="restrequest" name="Get all transfers Initial" id="eeadd8bd-6b9e-4415-b79c-a9725f9ff93c"><con:settings/><con:config service="application" resourcePath="//transfers" methodName="GET - getAllPendingTransfers" xsi:type="con:RestRequestStep" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><con:restRequest name="Get All transfers" id="3581ba1c-de56-4aa0-a013-bf8d221af957" mediaType="application/json"><con:settings><con:setting id="com.eviware.soapui.impl.wsdl.WsdlRequest@request-headers">&lt;xml-fragment/></con:setting></con:settings><con:endpoint>http://localhost:8080</con:endpoint><con:request/><con:originalUri>http://localhost//transfers</con:originalUri><con:assertion type="Valid HTTP Status Codes" id="e257d5fa-558f-422e-8246-b0bf661b9fa1" name="Valid HTTP Status Codes"><con:configuration><codes>200</codes></con:configuration></con:assertion><con:assertion type="GroovyScriptAssertion" id="bc36aec8-8a4e-4d55-a4c4-77839638dd82" name="Script Assertion"><con:configuration><scriptText>def list = new groovy.json.JsonSlurper().parseText( context.response )
assert list.size() >= 0</scriptText></con:configuration></con:assertion><con:credentials><con:authType>No Authorization</con:authType></con:credentials><con:jmsConfig JMSDeliveryMode="PERSISTENT"/><con:jmsPropertyConfig/><con:parameters/></con:restRequest></con:config></con:testStep><con:testStep type="restrequest" name="Create Transfers" id="3f40901d-7ce7-41d6-baae-25fec0a7cbab"><con:settings/><con:config service="application" resourcePath="//transfers" methodName="POST - createTransactionToTransferMoney" xsi:type="con:RestRequestStep" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><con:restRequest name="/transfers" id="b0a7c7b1-a772-4f86-b464-ae8b6b6b6752" mediaType="application/json" postQueryString="false"><con:settings><con:setting id="com.eviware.soapui.impl.wsdl.WsdlRequest@request-headers">&lt;xml-fragment/></con:setting></con:settings><con:endpoint>http://localhost:8080</con:endpoint><con:request>{
"fromBankAccountId" : 2,
"toBankAccountId" : 1,
"amount" : 5
}</con:request><con:originalUri>http://localhost//transfers</con:originalUri><con:assertion type="Valid HTTP Status Codes" id="c207fe9c-8e19-4f10-8e2a-7fe48b9c2076" name="Valid HTTP Status Codes"><con:configuration><codes>200</codes></con:configuration></con:assertion><con:assertion type="JsonPath Match" id="d573d109-aa85-4edd-93e5-727bb45ea558" name="JsonPath Match"><con:configuration><path>$.status</path><content>INITIATED</content><allowWildcards>false</allowWildcards><ignoreNamspaceDifferences>false</ignoreNamspaceDifferences><ignoreComments>false</ignoreComments></con:configuration></con:assertion><con:assertion type="JsonPath Match" id="12766e42-ec58-469d-8108-2417bc540a2c" name="JsonPath Match Amount"><con:configuration><path>$.amount</path><content>5</content><allowWildcards>false</allowWildcards><ignoreNamspaceDifferences>false</ignoreNamspaceDifferences><ignoreComments>false</ignoreComments></con:configuration></con:assertion><con:assertion type="JsonPath Match" id="2dea444f-bf97-47cc-9202-6d42e70c4a65" name="JsonPath Match failMessage"><con:configuration><path>$.failMessage</path><content/><allowWildcards>false</allowWildcards><ignoreNamspaceDifferences>false</ignoreNamspaceDifferences><ignoreComments>false</ignoreComments></con:configuration></con:assertion><con:credentials><con:authType>No Authorization</con:authType></con:credentials><con:jmsConfig JMSDeliveryMode="PERSISTENT"/><con:jmsPropertyConfig/><con:parameters/></con:restRequest></con:config></con:testStep><con:testStep type="restrequest" name="Get All transfers" id="e52f2fb5-c0fc-4bf0-a3d8-f7413528a849"><con:settings/><con:config service="application" resourcePath="//transfers" methodName="GET - getAllPendingTransfers" xsi:type="con:RestRequestStep" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><con:restRequest name="/transfers" id="3581ba1c-de56-4aa0-a013-bf8d221af957" mediaType="application/json"><con:settings><con:setting id="com.eviware.soapui.impl.wsdl.WsdlRequest@request-headers">&lt;xml-fragment/></con:setting></con:settings><con:endpoint>http://localhost:8080</con:endpoint><con:request/><con:originalUri>http://localhost//transfers</con:originalUri><con:assertion type="Valid HTTP Status Codes" id="d5ca42d3-ca0c-47e8-893d-46423436cba6" name="Valid HTTP Status Codes"><con:configuration><codes>200
</codes></con:configuration></con:assertion><con:assertion type="GroovyScriptAssertion" id="cdafd18a-2a3e-4891-bdf6-db3bea89873b" name="Script Assertion"><con:configuration><scriptText>def list = new groovy.json.JsonSlurper().parseText( context.response )
assert list.size() >= 1</scriptText></con:configuration></con:assertion><con:credentials><con:authType>No Authorization</con:authType></con:credentials><con:jmsConfig JMSDeliveryMode="PERSISTENT"/><con:jmsPropertyConfig/><con:parameters/></con:restRequest></con:config></con:testStep><con:testStep type="restrequest" name="Get Transfer by ID" id="9cba45e7-9131-4577-a9f9-9c40205882ff"><con:settings/><con:config service="application" resourcePath="//transfers/{id}" methodName="GET - getTransactionById" xsi:type="con:RestRequestStep" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><con:restRequest name="{id}" id="55a81473-44c5-4420-aa8b-5f3f3b634622" mediaType="application/json"><con:settings><con:setting id="com.eviware.soapui.impl.wsdl.WsdlRequest@request-headers">&lt;xml-fragment/></con:setting></con:settings><con:endpoint>http://localhost:8080</con:endpoint><con:request/><con:originalUri>http://localhost//transfers/</con:originalUri><con:assertion type="Valid HTTP Status Codes" id="e50f1a70-48ce-4257-b7a6-7a893bc2eace" name="Valid HTTP Status Codes"><con:configuration><codes>200</codes></con:configuration></con:assertion><con:credentials><con:authType>No Authorization</con:authType></con:credentials><con:jmsConfig JMSDeliveryMode="PERSISTENT"/><con:jmsPropertyConfig/><con:parameters><entry key="id" value="1" xmlns="http://eviware.com/soapui/config"/></con:parameters></con:restRequest></con:config></con:testStep><con:properties/></con:testCase><con:properties/></con:testSuite><con:properties/><con:wssContainer/><con:oAuth2ProfileContainer/><con:oAuth1ProfileContainer/></con:soapui-project>
