# eBroker

* Create an eBroker with a single Persona (Trader) 
* Trader should be able to
  * Buy an equity
  * Should be able to buy from 9 to 5 and Monday to Friday only
  * Should have suitable funds to buy the equity
* Sell an equity
  * Should be able to sell from 9 to 5 and Monday to Friday only
  * Can sell only those equities which the trader hold
  * Money should be added back to funds
* Add Fund
  * Can add funds any time

* ##### Note - Project is built with H2 Memory database with empty database.


## Table of contents
* [Technologies](#technologies)
* [Setup](#setup)
* [APIs](#apis)
* [Test Report](#TestReport)


## Technologies

Project is created with:
* Java: 11
* Kotlin: 1.6
* Springboot: 2.6.1
* Hibernate 
* H2 Database
* JUnit5

## Setup
* Import the project as gradle project
* Go to project root folder and use **./gradlew bootRun** to run up the application
* Application will run on port number 8080 
* H2 console link - http://localhost:8080/h2-console

## APIs

* [API Documentation](https://documenter.getpostman.com/view/2141799/UVRDHksP)

[![Run in Postman](https://run.pstmn.io/button.svg)](https://www.getpostman.com/run-collection/36f1ceca4865d3e0cb7a)

## Test Report
