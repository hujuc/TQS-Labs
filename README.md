# TQS_113889 – Software Testing and Quality

This repository contains the work developed for the Software Testing and Quality (TQS) course at the University of Aveiro.

## Overview

The project is organized into multiple labs (Lab 1 to Lab 8), each focusing on specific topics of software testing, including:

- Unit Testing with JUnit 5  
- Mocking with Mockito  
- Integration Testing  
- Spring Boot Testing  
- CI/CD pipelines and code quality  
- Performance Testing with k6  
- Code Analysis with SonarQube  

Each lab includes Java code, automated tests, and tool configurations to explore best practices in testing and software maintainability.

## Technologies Used

- Java 17  
- JUnit 5 – Unit testing  
- Mockito – Mocking dependencies  
- JaCoCo – Code coverage reports  
- Spring Boot – Web app and API testing  
- MySQL & Docker – Integration test environments  
- k6 – Load testing for APIs  
- Lighthouse – Frontend performance & accessibility testing  
- SonarQube – Code quality metrics  
- Maven – Build and test automation  

## How to Run and Test

### Prerequisites

- Java 17+  
- Maven  
- Docker (for DB containers)  
- Internet connection (for integration tests)  

### Run tests and generate reports

```bash
# Run all unit tests
mvn clean test

# Generate JaCoCo code coverage report
mvn jacoco:report
# View report in: target/site/jacoco/index.html

# Run integration tests (with Failsafe)
mvn failsafe:integration-test

# Run SonarQube analysis
mvn sonar:sonar
```

### Load Dockerized MySQL for Lab 3

```bash
docker run --name mysql-test \
  -e MYSQL_ROOT_PASSWORD=secret \
  -e MYSQL_DATABASE=car_service \
  -p 3306:3306 -d mysql:latest
```

## Sample Test Strategies

- Unit tests use mocking where appropriate (e.g., API clients)  
- Integration tests verify real HTTP requests and DB persistence  
- End-to-end scenarios tested with Spring Boot, MockMvc and TestRestTemplate  
- Load testing with k6 under high user concurrency  
- Frontend accessibility tested with Lighthouse  

## Author

Hugo Castro
Student No.: 113889  
University of Aveiro  
Course: Software Testing and Quality (TQS)
