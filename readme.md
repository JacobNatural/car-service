# Car Processing Application

## Overview

This project is designed to process and manage car data, including functionalities like validation, data conversion, and statistical analysis.

## [Testing and Coverage](https://jacobnatural.github.io/Car_Service/)

All components of the application are thoroughly tested using JUnit, achieving 81% code coverage.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 22
- A Java IDE or text editor (e.g., IntelliJ IDEA, Eclipse, VSCode)

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/yourusername/car-processing-app.git
    cd car-processing-app
    ```

2. Open the project in your preferred Java IDE.

## Usage

### Running the Application

1. Open [App.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/App.java)
2. Execute its `main` method to launch the application and see example usages.

### Integrate into Your Project

1. Import the necessary packages into your Java project.
2. Utilize the provided classes and methods to handle car data processing, validation, and statistics.

## Project Structure

### Package: `com.app.car`

- [Car.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/car/Car.java)
    - Represents a single car with fields:
        - `final String brand`
        - `final String model`
        - `final BigDecimal price`
        - `final int speed`
        - `final Color color`
        - `final List<String> components`
    - Methods:
        - `public boolean hasSpeedBetween(int minSpeed, int maxSpeed)`
        - `public Car carWithSortedComponents(Comparator<String> comparator)`
        - `public boolean hasComponent(String component)`
        - `public BigDecimal getDifferentBetweenPrice(BigDecimal price)`
        - `public boolean hasPriceBetween(BigDecimal minPrice, BigDecimal maxPrice)`
        - `public boolean hasBrandPattern(String brand)`
        - `public boolean hasModelPattern(String model)`
        - `public boolean hasCarCriterion(CarCriterion carCriterion)`

- [CarCriterion.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/car/CarCriterion.java)
    - Criteria for searching cars with fields:
        - `final String brand`
        - `final String model`
        - `final int minSpeed`
        - `final int maxSpeed`
        - `final BigDecimal minPrice`
        - `final BigDecimal maxPrice`
        - `final List<String> components`

- [CarCriterionValidator.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/car/CarCriterionValidator.java)
    - Methods:
        - `public List<String> validate(CarCriterion carCriterion)`

- [CarMapper.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/car/CarMapper.java)
    - Interface with functional methods for mapping car fields.

- [CarValidator.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/car/CarValidator.java)
    - Validates a single car object.
    - Fields:
        - `private final String regex`
        - `private final int minValue`
    - Methods:
        - `public List<String> validate(Car car)`

### Package: `com.app.collectors.generic`

- [CollectorGeneric.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/collectors/generic/CollectorGeneric.java)
    - Abstract class implementing generic methods for collectors.
    - Fields:
        - `private final Function<T, U> mapper`
    - Methods:
        - `public BiConsumer<List<U>, T> accumulator()`
        - `public Supplier<List<U>> supplier()`
        - `public BinaryOperator<List<U>> combiner()`
        - `public Set<Characteristics> characteristics()`

### Package: `com.app.collectors`

- [CarMinMaxCollector.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/collectors/CarMinMaxCollector.java)
    - Extends `CollectorGeneric` to calculate Min, Max of a field.
    - Methods:
        - `public Function<List<BigDecimal>, Statistic<BigDecimal>> finisher()`

- [CarStatisticCollector.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/collectors/CarStatisticCollector.java)
    - Extends `CollectorGeneric` to calculate min, max, avg of a field.
    - Methods:
        - `public Function<List<BigDecimal>, Statistic<BigDecimal>> finisher()`

### Package: `com.app.color`

- [Color.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/color/Color.java)
    - Enum values:
        - `WHITE`, `GRAY`, `BLACK`, `SILVER`, `BLUE`, `RED`, `GREEN`, `BROWN`, `ORANGE`, `GOLD`, `PURPLE`

### Package: `com.app.json.converter.impl`

- [GsonConverter.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/json/converter/impl/GsonConverter.java)
    - Handles JSON conversion of objects.
    - Fields:
        - `private final Gson gson`
    - Methods:
        - `public void toJson(T data, FileWriter fileWriter)`
        - `public T fromJson(FileReader fileReader, Class<T> tClass)`

### Package: `com.app.json.deserialize.generic`

- [AbstractJsonDeserializer.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/json/deserialize/generic/AbstractJsonDeserializer.java)
    - Abstract class for generic JSON deserialization.
    - Fields:
        - `private final JsonConverter<T> jsonConverter`
        - `private final Class<T> tClass`
    - Methods:
        - `public T deserialize(String filename)`

### Package: `com.app.json.deserialize.impl`

- [CarsDeserializer.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/json/deserialize/impl/CarsDeserializer.java)
    - Extends `AbstractJsonDeserializer` to handle `Car` objects.

### Package: `com.app.json.serialize.generic`

- [AbstractJsonSerializer.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/json/serialiaze/generic/AbstractJsonSerializer.java)
    - Abstract class for generic JSON serialization.
    - Fields:
        - `private final JsonConverter<T> jsonConverter`
    - Methods:
        - `public void serialize(T data, String filename)`

### Package: `com.app.json.serialize.impl`

- [CarSerializer.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/json/serialiaze/impl/CarSerializer.java)
    - Extends `AbstractJsonSerializer` to handle single `Car` objects.

- [CarsSerializer.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/json/serialiaze/impl/CarsSerializer.java)
    - Extends `AbstractJsonSerializer` to handle lists of `Car` objects.

### Package: `com.app.model`

- [Cars.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/model/Cars.java)
    - Record used for loading data from JSON files.

### Package: `com.app.repository.impl`

- [CarsRepositoryImpl.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/repository/impl/CarsRepositoryImpl.java)
    - Retrieves car data from a JSON file and stores a list of cars.
    - Fields:
        - `private final List<Car> cars`
    - Methods:
        - `public List<Car> getAll()`

### Package: `com.app.service.impl`

- [CarServiceImpl.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/service/impl/CarServiceImpl.java)
    - Processes the list of cars.
    - Fields:
        - `private final Repository<Car> carRepository`
    - Methods:
        - `public List<Car> sortedCarsBy(Comparator<Car> comparator)`
        - `public List<Car> getCarsWithSpeedInterval(int minSpeed, int maxSpeed)`
        - `public List<Car> getCarsFilterBy(Predicate<Car> filter)`
        - `public Map<Color, Long> groupByColorAndAmountOfCars()`
        - `<T> public Map<T, Long> groupByAndAmountOfCars(Function<Car, T> carMapper)`
        - `public Map<String, Statistic<BigDecimal>> groupByBrandAndMinMaxPriceStatistic()`
        - `<T, U, W> public Map<T, Statistic<W>> groupByAndMinMaxPriceStatistic(Function<Car, T> carMapper, Collector<Car, U, Statistic<W>> collectors)`
        - `public List<Statistic<BigDecimal>> priceSpeedStatistic()`
        - `<T, W> public Statistic<W> getStatistic(Collector<Car, T, Statistic<W>> collector)`
        - `public List<Car> getCarsWithSortedComponents(Comparator<String> comparator)`
        - `public Map<String, List<Car>> groupByComponentsAndCarsSortedByAmountOfComponents(Comparator<Integer> comparator)`
        - `public List<Car> getCarsCloseToPrice(BigDecimal price)`
        - `public List<Car> getCarsWithCriterion(CarCriterion carCriterion)`

### Package: `com.app.statistic.impl`

- [CarMinMax.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/statistic/impl/CarMinMax.java)
    - Generic model to store Min and Max values.

- [CarStatistic.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/statistic/impl/CarStatistic.java)
    - Generic model to store statistics (min, max, avg).

### Package: `com.app.validate.impl`

- [CarsValidator.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/validate/impl/CarsValidator.java )
    - Validates a list of cars.
    - Fields:
        - `private final Validator<Car> carValidator`
    - Methods:
        - `public List<String> validate(Cars cars)`

### Package: `com.app`

- [App.java](https://github.com/JacobNatural/Car_Service/blob/master/src/main/java/com/app/App.java)
    - Main class demonstrating usage of the application.

## Contributing

We welcome contributions to improve the Car Processing Application. Here's how you can contribute:

1. Fork the repository on GitHub.
2. Make enhancements or fix issues in your forked copy.
3. Submit a pull request to merge your changes into the main repository.

Please ensure your code adheres to our coding standards and is thoroughly tested before submitting a pull request.