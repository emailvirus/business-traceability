package arrow.businesstraceability.json.deserializer;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.math.NumberUtils;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

/**
 * Created by michael on 30/06/2016.
 */
public class LocalDateTimeDeserializer implements ObjectDeserializer {
    public static final LocalDateTimeDeserializer INSTANCE = new LocalDateTimeDeserializer();

    @Override
    public <T> T deserialze(final DefaultJSONParser parser, final Type type, final Object fieldName) {

        Object objVal = parser.parse();
        if (objVal == null) {
            return null;
        }

        if (NumberUtils.isNumber(objVal.toString())) {
            long longVal = Long.parseLong(objVal.toString());
            if (longVal <= 0) {
                return null;
            }

            LocalDateTime date = (Instant.ofEpochMilli(longVal).atZone(ZoneId.systemDefault()).toLocalDateTime());
            return (T) date;
        }
        return (T) LocalDateTime.parse(objVal.toString(), DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

}
