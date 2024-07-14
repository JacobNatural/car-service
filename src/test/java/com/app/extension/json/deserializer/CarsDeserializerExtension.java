package com.app.extension.json.deserializer;

import com.app.data_provider.DataProvider;
import com.app.json.converter.impl.GsonConverter;
import com.app.json.deserialize.impl.CarsDeserializer;
import com.app.model.Cars;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class CarsDeserializerExtension implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(CarsDeserializer.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {

        var converter = new GsonConverter<Cars>(DataProvider.gson);
        return new CarsDeserializer(converter);
    }
}
