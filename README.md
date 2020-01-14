# Spring Movie List Sample Application 
A sample web app made with Spring Boot 🎞️🍿

## Summary
1. [Stack](#stack)
2. [Getting started](#getting-started)
    * [Running app directly on your system](#running-app-directly-on-your-system)
    * [Running app with Docker](#running-app-with-docker)

## Stack
[![Spring Boot](https://github.com/lgcolella/spring-movie-list/raw/master/repository/spring-boot.png "Spring Boot")](https://spring.io/projects/spring-boot)
[![Thymeleaf](https://github.com/lgcolella/spring-movie-list/raw/master/repository/thymeleaf.png "Thymeleaf")](https://www.thymeleaf.org/)
[![Bootstrap](https://github.com/lgcolella/spring-movie-list/raw/master/repository/bootstrap.png "Bootstrap")](https://getbootstrap.com/)
[![Docker](https://github.com/lgcolella/spring-movie-list/raw/master/repository/docker.png "Docker")](https://www.docker.com/)

## Getting started

The application is dockerized, then you can choose to run it directly on your system or by using Docker.

### Running app directly on your system

This way requires having Java 8 or newer and (optionally) Maven installed on the system.

Start app in development mode by executing in the terminal
```sh
mvn spring-boot:run # or ./mvnw spring-boot:run
```

or build and run it by executing
```sh
mvn package # or ./mvnw package
java -jar target/spring-movie-list-0.0.1-SNAPSHOT.jar
```

Then you can access it by browser at http://localhost:8080/

To run tests
```sh
mvn test # or ./mvnw test
```

### Running app with Docker

This way requires having Docker Compose installed on the system.

Start app in development mode by executing in the terminal
```sh
docker-compose run --service-ports app mvn spring-boot:run
```

or build and run it by executing
```sh
docker-compose run --service-ports app bash -c "mvn package && java -jar target/spring-movie-list-0.0.1-SNAPSHOT.jar"
# or
docker-compose up -d
```

Then you can access it by browser at http://localhost:8080/

To run tests
```sh
docker-compose run app mvn test
```

If you run one of the above commands for the first time, they may be take time because Maven has to fetch all dependencies needed to build the application.
To speed up this process, all downloaded dependencies are stored in a Docker volume (referenced as `maven-repository` in `docker-compose.yml`) to be reused every time the Docker app is restarted.

**Note for Windows users**
If you have used Docker Toolbox to install Docker, it's likely you have to replace `localhost` with the Docker Machine ip to access to your application outside the container.
You can know what it is by executing in the terminal

```sh
docker-machine ip
```
