# Customer-Credit

## What's this?
Customer-Credit Application implemented with Spring Boot, Spring Data Jdbc on REST api

## Technologies used
1. Java 11
2. Spring Boot and listed Spring modules 
    - Spring Data Jdbc
3. Lombok
4. Swagger2 and Swagger-ui
4. MySQL database

## Some information about it
This application is for store customer credits which menage data of customer, product and credit.
Application each customer, credit and product treat as new.
Date structure of consumer credit:
- Credit
  - creditName (string, not null, not empty)
- Product
  - productName (string, not null, not empty)
  - value (integer, 0 or larger)
- Customer
  - firstName (string, not null, not empty)
  - surname (string, not null, not empty)
  - pesel (string, not null, not empty)

## How to Run?
### Run using docker(preferred) by docker-compose
Make sure docker is up and running on your local machine, and ports 8080, 8081, 8082, 8090, 3306  are free

1. Download the repo and execute the following commands in the same order
2. Build the project
    ```shell script
    mvn clean package -DskipTests
      ```
3. Build credit dockers images
    ```shell script
    docker-compose up --force-recreate
      ```   

## Documentation
### Swagger
```
http://localhost:8080/api-docs
```
### Swagger-ui
```
http://localhost:8080/swagger-ui.html
```
### View database by phpMyAdmin
```
http://localhost:8090/
```
User:
```
user
```
Password:
```
user
```


## Sample use
Sample use by postman application:
1. Add new credit:
	- Method POST on
    ```shell script
    http://localhost:8080/credit/
      ```   
	- with JSON body
    ```shell script
    {
		"credit" : {
			"creditName" : "Some credit name"
		},
		"product"  :  {
			"productName" : "Some product name",
			"value" : 123456
		},
		"customer" : {
			"firstName" : "Jan",
			"surname" : "Kowalski",
			"pesel" : "1234567890"
		}
	}
      ```   
	- should return id of this credit
    ```shell script
    {
		"creditId": 1234567
	}
      ```   
2. Get list of all credits:
	- Method GET on with empty body
    ```shell script
    http://localhost:8080/credit/
      ```   
	- should return list of all credits
    ```shell script
    {
		"credits": [
			{
				"credit" : {
					"creditName" : "Some credit name"
				},
				"product"  :  {
					"productName" : "Some product name",
					"value" : 123456
				},
				"customer" : {
					"firstName" : "Jan",
					"surname" : "Kowalski",
					"pesel" : "1234567890"
				}
			},
			{
				"credit" : {
					"creditName" : "Another credit name"
				},
				"product"  :  {
					"productName" : "Another product name",
					"value" : 654321
				},
				"customer" : {
					"firstName" : "John",
					"surname" : "Smith",
					"pesel" : "0987654321"
				}
			}
		]
	}
      ```   
