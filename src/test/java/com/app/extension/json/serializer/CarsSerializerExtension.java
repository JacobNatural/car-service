package com.app.extension.json.serializer;

import com.app.data_provider.DataProvider;
import com.app.json.converter.impl.GsonConverter;
import com.app.json.serialiaze.impl.CarsSerializer;
import com.app.service.impl.CarServiceImpl;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class CarsSerializerExtension implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(CarsSerializer.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {

        var converter = new GsonConverter<CarServiceImpl>(DataProvider.gson);
        return new CarsSerializer(converter);
    }
}
