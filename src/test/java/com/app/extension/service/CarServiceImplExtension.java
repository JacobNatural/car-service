package com.app.extension.service;

import com.app.car.CarValidator;
import com.app.data_provider.DataProvider;
import com.app.json.converter.impl.GsonConverter;
import com.app.json.deserialize.impl.CarsDeserializer;
import com.app.model.Cars;
import com.app.repository.impl.CarsRepositoryImpl;
import com.app.service.impl.CarServiceImpl;
import com.app.validate.impl.CarsValidator;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.nio.file.Paths;
import java.util.Objects;

public class CarServiceImplExtension implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(CarServiceImpl.class);
    }

    @Override
    @SneakyThrows
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {

        var path = Paths
                .get(Objects.requireNonNull(
                                getClass()
                                        .getClassLoader()
                                        .getResource(
                                                DataProvider.FILE_NAMES_DATA.get("cars json")))
                        .toURI());

        var converter = new GsonConverter<Cars>(DataProvider.gson);
        var jsonDeserializer = new CarsDeserializer(converter);

        var carRepository = new CarsRepositoryImpl(
                path.toString(), new CarsValidator(new CarValidator("[ A-Z0-9]+", 0)), jsonDeserializer);

        return new CarServiceImpl(carRepository);
    }
}
