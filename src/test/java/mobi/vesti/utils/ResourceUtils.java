package mobi.vesti.utils;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.rmi.UnexpectedException;

public class ResourceUtils {
    private ResourceUtils() {
    }

    public static String loadResourceAsString(String pathAsString) throws UnexpectedException {
        try {
            return IOUtils.toString(loadResourceAsInputStream(pathAsString), StandardCharsets.UTF_8);
        } catch (IOException e) {
            final String errorMessage = String.format("Erro ao carregar arquivo '%s'", pathAsString);
            throw new UnexpectedException(errorMessage, e);
        }
    }

    private static InputStream loadResourceAsInputStream(String pathAsString) throws IOException {
        return new ResourceUtils().getClass().getResourceAsStream(pathAsString);
    }
}
