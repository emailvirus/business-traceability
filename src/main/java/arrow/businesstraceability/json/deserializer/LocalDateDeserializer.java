package arrow.businesstraceability.json.deserializer;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.math.NumberUtils;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

/**
 * Created by michael on 30/06/2016.
 */
public class LocalDateDeserializer implements ObjectDeserializer {
    public static final LocalDateDeserializer INSTANCE = new LocalDateDeserializer();

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

            LocalDate date = (Instant.ofEpochMilli(longVal).atZone(ZoneId.systemDefault()).toLocalDate());
            return (T) date;

        }
        if (this.isDateOnlyFormat(objVal.toString())) {
            return (T) LocalDate.parse(objVal.toString(), DateTimeFormatter.ISO_DATE);
        }
        if (objVal.toString().contains(" ")) {
            return (T) LocalDate.parse(objVal.toString().split(" ")[0], DateTimeFormatter.ISO_DATE);
        } else {
            return (T) LocalDate.parse(objVal.toString(), DateTimeFormatter.ISO_DATE_TIME);
        }

    }

    boolean isDateOnlyFormat(final String input) {
        return input.length() <= 10; // 2017-07-07
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
