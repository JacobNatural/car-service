package com.app.extension.json.converter;

import com.app.data_provider.DataProvider;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import java.io.FileReader;
import java.util.Objects;

public class FileReaderExtension implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(FileReader.class);
    }

    @Override
    @SneakyThrows
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {

        var path = Objects
                .requireNonNull(
                        getClass()
                                .getClassLoader()
                                .getResource(DataProvider.FILE_NAMES_DATA.get("car json")))
                .toURI();

        return new FileReader(path.getPath());
    }
}
