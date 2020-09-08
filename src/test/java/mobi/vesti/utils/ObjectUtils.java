package mobi.vesti.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toString(Object o) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(o);
    }
}
