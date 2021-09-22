# Customer-Credit

## What's this?
Customer-Credit Application implemented with Spring Boot, Spring Data Jdbc on REST api

## How to Run?
### Run using docker(preferred) by docker-compose
Make sure docker is up and running on your local machine

1. Download the repo and execute the following commands in the same order
2. Build the project
    ```shell script
    mvn clean package -DskipTests
      ```
3. Build credit dockers images
    ```shell script
    docker-compose up --force-recreate
      ```   
## Technologies 
1. Java 11
2. Spring Boot and listed Spring modules 
    - Spring Data Jdbc
4. MySQL database

## Sample use
1. Add new credit:
	GET
    ```shell script
    http://localhost:8080/credit/
      ```   
	with JSON body:
    ```shell script
    $ {
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
	should return id of this credit
    ```shell script
    {
    "creditId": 1234567
	}
      ```   
