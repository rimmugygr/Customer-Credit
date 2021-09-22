# Customer-Credit

## What's this?
Customer-Credit Application implemented with Spring Boot, Spring Data Jdbc
## How to Run?
### Run using docker(preferred) by docker-compose
Make sure docker is up and running on your local machine

1. Download the repo and execute the following commands in the same order
2. Build the project
    ```shell script
    $ mvn clean package -DskipTests
      ```
3. Build credit dockers images
    ```shell script
    $ docker-compose up --force-recreate
      ```   
## Technologies 
1. Java 11
2. Spring Boot and listed Spring modules 
    - Spring Data Jdbc
4. MySQL database