package com.app;

import com.app.car.*;
import com.app.color.Color;
import com.app.json.converter.impl.GsonConverter;
import com.app.json.deserialize.impl.CarsDeserializer;
import com.app.model.Cars;
import com.app.repository.impl.CarsRepositoryImpl;
import com.app.service.impl.CarServiceImpl;
import com.app.validate.impl.CarsValidator;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;

public class App {
    public static void main(String[] args) {


        var filename = "";
        try {
            filename = Paths.get(App.class.getResource("/carservice.json").toURI()).toString();
        }catch (Exception e){
            System.out.println(STR."Error: \{e.getMessage()}");
        }

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

        // CAR_CRITERION
        var carCriterion = CarCriterion.of(
                "AUDI", "[A-Z0-9]+",
                190, 290,
                BigDecimal.valueOf(150000), BigDecimal.valueOf(200000),
                List.of("AIR CONDITION"), carCriterionValidator);

        var carService = new CarServiceImpl(carsRepository);

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
