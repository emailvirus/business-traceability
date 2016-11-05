package arrow.businesstraceability.json.deserializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.math.NumberUtils;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

/**
 * Created by michael on 30/06/2016.
 */
public class DateDeserializer implements ObjectDeserializer {
    public static final DateDeserializer INSTANCE = new DateDeserializer();

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

            Date date = new Date(longVal);
            return (T) date;

        }
        if (this.isDateOnlyFormat(objVal.toString())) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return (T) df.parse(objVal.toString());
            } catch (ParseException pe) {
                return null;
            }
        }
        if (objVal.toString().contains("T")) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            String relaceTempValue = objVal.toString().replace("T", " ");
            try {
                return (T) df.parse(relaceTempValue);
            } catch (ParseException pe) {
                return null;
            }
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return (T) df.parse(objVal.toString().split(" ")[0]);
            } catch (ParseException pe) {
                return null;
            }
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
