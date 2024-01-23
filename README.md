# Enviro-365-appliction
Enviro365 Investments Automated Withdrawal Notice System
#Overview:
Enviro365 Investments aims to enhance the withdrawal notice process for its investors by automating the existing manual and time-consuming procedure. The current process involves investors downloading PDF forms, completing them, signing, and then emailing them to the service area. This manual effort not only consumes time but also introduces the possibility of errors during data re-capture. Automating this process will lead to an improved investor experience, eliminate delays in processing withdrawals, and allow the service team to focus on more critical aspects of the business.

#Objectives:
The goal of this project is to build a Spring Boot application that exposes a REST API for investors to interact with and perform the following actions:

Retrieve investor information (personal, address, and contact details) along with a list of products the investor has invested in. Each product should include a product ID, type (RETIREMENT/SAVINGS), name, and the current balance.

Create a new WITHDRAWAL NOTICE for a selected product, allowing investors to specify withdrawal details such as the withdrawal amount, dates, and banking information.

Download a statement of all notices created for a selected product as a CSV file, with a specified date range

#Validation Rules:
If PRODUCT is RETIREMENT, the investor's age must be greater than 65.
If WITHDRAWAL AMOUNT is greater than the current BALANCE, a validation error must be returned.
Investors cannot withdraw an AMOUNT more than 90% of the current BALANCE; otherwise, respond with a validation error.

#Getting Started:
To run the Enviro365 Investments Automated Withdrawal Notice System locally, follow these steps:

Clone the repository
Navigate to the project directory
Navigate into KamwanyaMukendi
Run mvn clean install and mvn package
Build and run the Spring Boot application: java -jar target/KamwanyaMukendi-0.0.1-SNAPSHOT.jar
The application will be accessible at http://localhost:8080.
Once there:
Click on the menu and login with either:
-Client1:
 email:client1@example.com
 password:password
-Client2:
 email:client2@example.com
 password:password


#Contributors
Kamwanya Gloria Mukendi(kamuken022@student.wethinkcode.co.za)
