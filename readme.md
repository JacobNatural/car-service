
# Car Processing Application

![Static Badge](https://img.shields.io/badge/Build-passing-flat)
[![Static Badge](https://img.shields.io/badge/Coverage-98%25-flat)](https://jacobnatural.github.io/car-service/jacoco/index.html)
[![Static Badge](https://img.shields.io/badge/docs-blue)](https://jacobnatural.github.io/car-service/apidocs/index.html)
## Overview
The Car Processing Application is a powerful and scalable backend solution built using Spring Boot, 
designed for comprehensive car data management and analytics. This project streamlines the processing, 
validation, and analysis of car data while ensuring high performance and reliability.
The application supports a wide range of features, including dynamic data filtering, 
component management, and advanced statistical reporting on car attributes like speed and pricing. 
It follows best practices in software development, with robust test coverage using JUnit to ensure quality and stability.
Designed with scalability in mind, it seamlessly integrates with Docker for containerization, 
making deployment and maintenance efficient and straightforward. Its clean API design ensures easy 
integration with frontend systems or external services, offering RESTful endpoints documented through Swagger.

## Technology Stack

- Backend: Java, Spring Boot

- Database: (MySQL)

- Build Tool: Maven

-  Testing: Junit, Mockito, MockMvc 

- Other Tools: Hibernate, Lombok

- Containerization: Docker

## Getting Started


### Prerequisites
- **Java Development Kit (JDK) 23+**
- **Apache Maven 3.9.6+**
- **Docker**

### Cloning the repository:
- To clone the repository and navigate into the project directory, run:

```bash
git clone https://github.com/JacobNatural/car-service.git
cd car-service
```

### Running the application:
- To build the application and run it using Docker Compose:
```bash
docker-compose up -d
```

### Running Tests
- To execute the tests, ensure that your database configuration for tests is set up in 
the docker-compose-test.yml file and then run:
```bash
docker-compose -f docker-compose-test.yml up -d 
mvn clean test
```

### Access to the Swagger UI:
```bash
http://localhost:8080/swagger-ui/index.html
```
## How It Works
### **Car Management:**

- You can add, update, delete, and filter cars.
Advanced operations are supported, such as grouping cars by color and generating statistics on prices and speeds.
Component Management:

- The application allows you to create, delete, and search for car components.
- You can also group components and sort them by the number of associated cars.

### **Data Filtering and Sorting:**

- Dynamic data filtering and sorting are supported based on multiple parameters in ascending and descending order.

## Contributing
We welcome contributions to improve the Shop Statistic Application. Here's how you can contribute:

1. Fork the repository on GitHub.
2. Make enhancements or fix issues in your forked copy.
3. Submit a pull request to merge your changes into the main repository.

Please ensure your code adheres to our coding standards and is thoroughly tested before submitting a pull request.

