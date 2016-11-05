package arrow.framework.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import arrow.businesstraceability.json.deserializer.DateDeserializer;
import arrow.businesstraceability.json.deserializer.LocalDateDeserializer;
import arrow.businesstraceability.json.deserializer.LocalDateTimeDeserializer;

/**
 * The Class JsonUtils.
 */
public class JsonUtils {


    /**
     * Gets the json.
     *
     * @param obj the obj
     * @return the json
     * @throws JsonProcessingException
     * @throws Exception the exception
     */
    public static String getJson(final Object obj) throws JsonProcessingException {
        // JacksonConfig config = new JacksonConfig();
        // return config.getContext(obj.getClass()).writeValueAsString(obj);
        return JSON.toJSONString(obj, SerializerFeature.IgnoreNonFieldGetter, SerializerFeature.UseISO8601DateFormat);
    }

    /**
     * convert json to Object.
     *
     * @param json the json
     * @param obj the obj
     * @return the object
     * @throws JsonParseException the json parse exception
     * @throws JsonMappingException the json mapping exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static Object convertJsonToObject(final String json, final Object obj) throws JsonParseException,
        JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, obj.getClass());

    }

    /**
     * Convert to other type of entity.
     *
     * @param json
     * @param entityClazz
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public static <T> T toEntity(final String json, final Class<T> entityClazz) throws JsonParseException,
        JsonMappingException, IOException {

        ParserConfig config = ParserConfig.getGlobalInstance();
        config.putDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
        config.putDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
        config.putDeserializer(Date.class, DateDeserializer.INSTANCE);
        return new DefaultJSONParser(json, config).parseObject(entityClazz);
    }

    /**
     * Gets the json.
     *
     * @param objs the objs
     * @param clazz the clazz
     * @return the json
     * @throws JsonProcessingException the json processing exception
     */
    public static String getJson(final List<Object> objs, final Class<?> clazz) throws JsonProcessingException {
        StringBuilder jsonBuilder = new StringBuilder();
        int size = objs.size();
        jsonBuilder.append("[");
        for (int i = 0; i < (size - 1); i++) {
            jsonBuilder.append(JSON.toJSONString(objs.get(i), SerializerFeature.IgnoreNonFieldGetter,
                SerializerFeature.UseISO8601DateFormat));
            jsonBuilder.append(",");
        }
        jsonBuilder.append(JSON.toJSONString(objs.get(size - 1), SerializerFeature.IgnoreNonFieldGetter,
            SerializerFeature.UseISO8601DateFormat));
        jsonBuilder.append("]");
        return jsonBuilder.toString().replaceAll("'", "");
    }

}
