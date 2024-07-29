package com.app;

import com.app.car.*;
import com.app.json.converter.impl.GsonConverter;
import com.app.json.deserialize.impl.CarsDeserializer;
import com.app.model.Cars;
import com.app.repository.impl.CarsRepositoryImpl;
import com.app.service.impl.CarServiceImpl;
import com.app.validate.impl.CarsValidator;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/**
 * The main entry point of the application.
 * <p>
 * This class is responsible for setting up the application's environment and demonstrating the usage of various
 * components involved in managing and analyzing car data. It initializes the necessary services, performs data
 * deserialization, applies validation, and invokes methods of the car service to showcase its functionality.
 * </p>
 *
 *
 * The class performs the following tasks:
 * <ul>
 *     <li>Initializes a JSON converter and deserializer for handling car data.</li>
 *     <li>Sets up validators for car data and car criteria.</li>
 *     <li>Configures a repository for accessing car data.</li>
 *     <li>Initializes the car service to perform various operations on the car data.</li>
 *     <li>Defines a car criterion for filtering cars.</li>
 *     <li>Calls different methods of the car service and prints their results to the console.</li>
 * </ul>
 *
 *
 * @see com.app.car.Car
 * @see com.app.json.converter.impl.GsonConverter
 * @see com.app.json.deserialize.impl.CarsDeserializer
 * @see com.app.model.Cars
 * @see com.app.repository.impl.CarsRepositoryImpl
 * @see com.app.service.impl.CarServiceImpl
 * @see com.app.validate.impl.CarsValidator
 */
public class App {
    public static void main(String[] args) {

        // FILENAME
        var filename = "car_service.json";

        // DESERIALIZER
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var carsConverter = new GsonConverter<Cars>(gson);
        var carsDeserializer = new CarsDeserializer(carsConverter);

        // VALIDATE
        var carValidator = new CarValidator("[A-Z0-9 ]+", 0);
        var carsValidator = new CarsValidator(carValidator);

        var carCriterionValidator = new CarCriterionValidator();

        // REPOSITORY
        var carsRepository = new CarsRepositoryImpl(filename, carsValidator,carsDeserializer );

        // CAR_SERVICE
        var carService = new CarServiceImpl(carsRepository);

        // CAR_CRITERION
        var carCriterion = CarCriterion.of(
                "AUDI", "[A-Z0-9]+",
                190, 290,
                BigDecimal.valueOf(150000), BigDecimal.valueOf(200000),
                List.of("AIR CONDITION"), carCriterionValidator);

        // BASIC METHOD OF CAR_SERVICE
        System.out.println("Method: 'sortedCarsBy'");
        System.out.println(carService.sortedCarsBy(Comparator.comparing(CarMapper.toSpeed)) + "\n");

        System.out.println("Method: 'getCarsWithSpeedInterval'");
        System.out.println(carService.getCarsWithSpeedInterval(180,300) + "\n");

        System.out.println("Method: 'groupByColorAndAmountOfCars'");
        System.out.println(carService.groupByColorAndAmountOfCars() + "\n");

        System.out.println("Method: 'groupByBrandAndMinMaxPriceStatistic'");
        System.out.println(carService.groupByBrandAndMinMaxPriceStatistic()+ "\n");

        System.out.println("Method: 'priceSpeedStatistic'");
        System.out.println(carService.priceSpeedStatistic()+ "\n");

        System.out.println("Method: 'getCarsWithSortedComponents'");
        System.out.println(carService.getCarsWithSortedComponents(Comparator.naturalOrder())+ "\n");

        System.out.println("Method: 'groupByComponentsAndCarsSortedByAmountOfComponents'");
        System.out.println(carService.groupByComponentsAndCarsSortedByAmountOfComponents(Comparator.naturalOrder())+ "\n");

        System.out.println("Method: 'getCarsCloseToPrice'");
        System.out.println(carService.getCarsCloseToPrice(BigDecimal.valueOf(150))+ "\n");

        System.out.println("Method: 'getCarsWithCriterion'");
        System.out.println(carService.getCarsWithCriterion(carCriterion));

    }
}
