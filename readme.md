# Car Processing Application
![Static Badge](https://img.shields.io/badge/Build-passing-flat)
[![Static Badge](https://img.shields.io/badge/Coverage-99%25-flat)](https://jacobnatural.github.io/car-service/jacoco/index.html)
[![Static Badge](https://img.shields.io/badge/docs-blue)](https://jacobnatural.github.io/car-service/apidocs/index.html)

## Overview

This project is designed to process and manage car data, including functionalities like validation, data conversion, and statistical analysis.
All components of the application are thoroughly tested using JUnit.    

## Getting Started

### Prerequisites

- **Java Development Kit (JDK) 22+**
- **Apache Maven 3.9.6+**

 
### Cloning the Repository:
- To clone the repository and navigate into the project directory, run:
```bash
git clone https://github.com/JacobNatural/car-service.git
cd car-service
```

### Running the application:
- To build the application and run it, use the following commands:
```bash
mvn clean package -DskipTests
cd target  
java --enable-preview -jar cars-1.0.jar
```
### Running Tests
- To execute the tests, use the following command:
```bash
mvn clean test
```

### Integrate into Your Project
- If you want to use this application as a dependency in another Maven project,
  first install it into your local Maven repository by running:
```bash
mvn clean install -DskipTests
```
- Then, you can add the following dependency to your pom.xml file:

```xml
<dependency>
    <groupId>com.app</groupId>
    <artifactId>cars</artifactId>
    <version>1.0</version>
</dependency>
```

## Contributing

We welcome contributions to improve the Car Processing Application. Here's how you can contribute:

1. Fork the repository on GitHub.
2. Make enhancements or fix issues in your forked copy.
3. Submit a pull request to merge your changes into the main repository.

Please ensure your code adheres to our coding standards and is thoroughly tested before submitting a pull request.