package com.app.data_provider;

import com.app.car.Car;
import com.app.car.CarCriterionValidator;
import com.app.color.Color;
import com.app.car.CarCriterion;
import com.app.statistic.impl.CarStatistic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface DataProvider {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    Map<String, String> FILE_NAMES_DATA = Map.of(
            "test_save", "test.json",
            "car json", "json/car.json",
            "cars json", "json/cars.json");

    Map<String, Car> CAR_DATA = Map.of(
            "car1", new Car(
                    "BMW", "X3",
                        BigDecimal.valueOf(250000), 250,
                    Color.BLACK, List.of("ABS", "AIR CONDITION", "CB RADIO"))
    );

    Map<String, List<Car>> CARS_DATA = Map.of(
            "cars1", List.of(new Car(
                    "BMW", "X3",
                    BigDecimal.valueOf(250000), 250,
                    Color.BLACK, List.of("ABS", "AIR CONDITION")),
                    new Car(
                            "AUDI", "A1",
                            BigDecimal.valueOf(300000), 280,
                            Color.BLUE, List.of("RADIO", "ABS")
                    ),
                    new Car(
                            "FIAT", "PANDA",
                            BigDecimal.valueOf(120000), 170,
                            Color.RED, List.of("BACKUP CAMERA","CB RADIO" )
                    )),
            "cars2",List.of(new Car(
                            "BMW", "X3",
                            BigDecimal.valueOf(250000), 250,
                            Color.BLACK, List.of("ABS", "AIR CONDITION")),
                    new Car(
                            "AUDI", "A1",
                            BigDecimal.valueOf(300000), 280,
                            Color.BLUE, List.of("RADIO", "ABS")
                    ),
                    new Car(
                            "AUDI", "A3",
                            BigDecimal.valueOf(120000), 170,
                            Color.BLUE, List.of("BACKUP CAMERA","CB RADIO" )
                    )),
            "cars3", List.of(
                    new Car(
                            "BMW", "X3",
                            BigDecimal.valueOf(250000), 250,
                            Color.BLACK, List.of("ABS", "AIR CONDITION")),
                    new Car(
                            "BMW", "Z3",
                            BigDecimal.valueOf(350000), 290,
                            Color.BLACK, List.of("ABS","BACKUP CAMERA", "AIR CONDITION")),

                    new Car(
                            "AUDI", "A1",
                            BigDecimal.valueOf(300000), 280,
                            Color.BLUE, List.of("RADIO", "ABS")
                    ),
                    new Car(
                            "AUDI", "A3",
                            BigDecimal.valueOf(120000), 170,
                            Color.BLUE, List.of("BACKUP CAMERA","CB RADIO" )
                    ),
                    new Car(
                            "AUDI", "A4",
                            BigDecimal.valueOf(200000), 195,
                            Color.RED, List.of("BACKUP CAMERA","ABS" )
                    ))
    );

    Map<String, List<CarCriterion>> CAR_CRITERIA = Map.of(
            "criterion1",List.of(
                    CarCriterion.of(
                            "BMW","A1",
                            100, 120,
                            BigDecimal.valueOf(80),BigDecimal.valueOf(100),
                            List.of("HEATED SEATS"), new CarCriterionValidator()),
                    CarCriterion.of(
                            "BMW", "X3",
                            300, 320,
                            BigDecimal.valueOf(300000),BigDecimal.valueOf(400000),
                            List.of("HEATED SEATS"),new CarCriterionValidator()),
                    CarCriterion.of(
                            "BMW", "X3", 150, 300,
                            BigDecimal.valueOf(80),BigDecimal.valueOf(100),
                            List.of("HEATED SEATS"),new CarCriterionValidator()),
                    CarCriterion.of(
                            "BMW", "X3", 150, 300,
                            BigDecimal.valueOf(80000),BigDecimal.valueOf(400000),
                            List.of("HEATED SEATS"),new CarCriterionValidator()),
                    CarCriterion.of(
                            "AUDI","A1",
                            100, 120,
                            BigDecimal.valueOf(80),BigDecimal.valueOf(100),
                            List.of("ABS"),new CarCriterionValidator()),
                    CarCriterion.of(
                            "AUDI","A1",
                            100, 120,
                            BigDecimal.valueOf(8000),BigDecimal.valueOf(260000),
                            List.of("ABS"), new CarCriterionValidator()),
                    CarCriterion.of(
                            "AUDI","A1",
                            100, 350,
                            BigDecimal.valueOf(8000),BigDecimal.valueOf(260000),
                            List.of("ABS"),new CarCriterionValidator()),
                    CarCriterion.of(
                            "AUDI","X3",
                            100, 350,
                            BigDecimal.valueOf(8000),BigDecimal.valueOf(260000),
                            List.of("ABS"), new CarCriterionValidator())),
            "criterion2",List.of(
                    CarCriterion.of(
                            "BMW", "X3",
                            200, 260,
                            BigDecimal.valueOf(240000),BigDecimal.valueOf(300000),
                             List.of("ABS"),new CarCriterionValidator()),
                    CarCriterion.of(
                            "AUDI", "A3",
                            150,190,
                            BigDecimal.valueOf(100000),
                             BigDecimal.valueOf(150000),
                             List.of("CB RADIO"),new CarCriterionValidator()),
                    CarCriterion.of(
                            "AUDI", "[A-Z0-9]+",
                            100,350,
                            BigDecimal.valueOf(100000), BigDecimal.valueOf(350000),
                             List.of("ABS"),new CarCriterionValidator()),
                    CarCriterion.of(
                            "FORD","FOCUS",
                            200,290,
                            BigDecimal.valueOf(100500), BigDecimal.valueOf(200200),
                            List.of("ABS","RADIO"),new CarCriterionValidator()))
            );

    static Stream<Arguments> speedPriceStatisticProvider(){
        return Stream.of(Arguments.of(CARS_DATA.get("cars1"),List.of(
                        new CarStatistic<>(
                                BigDecimal.valueOf(120000),
                                BigDecimal.valueOf(300000),
                                BigDecimal.valueOf(223333.33)),
                        new CarStatistic<>(
                                BigDecimal.valueOf(170),
                                BigDecimal.valueOf(280),
                                BigDecimal.valueOf(233.33)))),
                Arguments.of(CARS_DATA.get("cars3"),List.of(
                        new CarStatistic<>(
                                BigDecimal.valueOf(120000),
                                BigDecimal.valueOf(350000),
                                BigDecimal.valueOf(244000.00).setScale(2, RoundingMode.HALF_UP)
                        ),
                        new CarStatistic<>(
                                BigDecimal.valueOf(170),
                                BigDecimal.valueOf(290),
                                BigDecimal.valueOf(237.00).setScale(2, RoundingMode.HALF_UP)
                        )
                )));
    }

    static Stream<CarCriterion> carCriterionProvider(){
        return CAR_CRITERIA.get("criterion1").stream();
    }

    static Stream<Arguments> getCarsCriterionProvider(){
        return Stream.of(Arguments.of(
                CARS_DATA.get("cars1"),
                CAR_CRITERIA.get("criterion2").getFirst(),
                List.of(CARS_DATA.get("cars3").getFirst())),
                Arguments.of(
                        CARS_DATA.get("cars3"),
                        CAR_CRITERIA.get("criterion2").get(1),
                        List.of(CARS_DATA.get("cars3").get(3))),
                Arguments.of(
                        CARS_DATA.get("cars3"),
                        CAR_CRITERIA.get("criterion2").get(2),
                        List.of(
                                CARS_DATA.get("cars3").get(2),
                                CARS_DATA.get("cars3").get(4)
                                ))
                        );
    }
    static Stream<Arguments> carsSortedComponentsProvider(){
        return Stream.of(
                Arguments.of(CARS_DATA.get("cars1"),
                List.of(new Car(
                        "BMW", "X3",
                        BigDecimal.valueOf(250000), 250,
                        Color.BLACK, List.of("ABS","AIR CONDITION")),
                        new Car(
                        "AUDI","A1",
                                BigDecimal.valueOf(300000),280,
                                Color.BLUE, List.of("ABS","RADIO")),
                        new Car(
                                "FIAT", "PANDA",
                                BigDecimal.valueOf(120000), 170,
                                Color.RED, List.of("BACKUP CAMERA","CB RADIO"))
                        ),
                        Comparator.naturalOrder()
        ),Arguments.of(CARS_DATA.get("cars1"),
                        List.of(new Car(
                                        "BMW", "X3",
                                        BigDecimal.valueOf(250000), 250,
                                        Color.BLACK, List.of("AIR CONDITION","ABS")),
                                new Car(
                                        "AUDI","A1",
                                        BigDecimal.valueOf(300000),280,
                                        Color.BLUE, List.of("RADIO","ABS")),
                                new Car(
                                        "FIAT", "PANDA",
                                        BigDecimal.valueOf(120000), 170,
                                        Color.RED, List.of("CB RADIO","BACKUP CAMERA"))
                        ),
                        Comparator.reverseOrder()
                ));
    }
    static Stream<Arguments> groupByComponentsAndCarSortedByAmountComponentsProvider(){
        return Stream.of(
                Arguments.of(DataProvider.CARS_DATA.get("cars1"),
                        Map.of("ABS", List.of(
                                CARS_DATA.get("cars1").getFirst(),
                                CARS_DATA.get("cars1").get(1)),
                                "AIR CONDITION", List.of(
                                        DataProvider.CARS_DATA.get("cars1").getFirst()),
                                "BACKUP CAMERA", List.of(
                                        DataProvider.CARS_DATA.get("cars1").get(2)),
                                "CB RADIO", List.of(
                                        DataProvider.CARS_DATA.get("cars1").get(2)),
                                "RADIO", List.of(
                                        DataProvider.CARS_DATA.get("cars1").get(1))
                        )
                )
        );
    }

    static Stream<Arguments> priceCloseToPriceProvider(){
        return Stream.of(
                Arguments.of(
                        BigDecimal.valueOf(100000),
                        List.of(DataProvider.CARS_DATA.get("cars3").get(3))),
                Arguments.of(
                        BigDecimal.valueOf(220000),
                        List.of(DataProvider.CARS_DATA.get("cars3").get(4))),
                        Arguments.of(
                                BigDecimal.valueOf(350000),
                                List.of(DataProvider.CARS_DATA.get("cars3").get(1)))
        );
    }
}
