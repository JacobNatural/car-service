package com.app.extension.json.serializer;

import com.app.car.Car;
import com.app.data_provider.DataProvider;
import com.app.json.converter.impl.GsonConverter;
import com.app.json.serialiaze.impl.CarSerializer;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class CarSerializerExtension implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(CarSerializer.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {

        var converter = new GsonConverter<Car>(DataProvider.gson);
        return new CarSerializer(converter);
    }
}
