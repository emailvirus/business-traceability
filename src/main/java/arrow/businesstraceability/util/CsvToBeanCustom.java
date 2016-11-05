package arrow.businesstraceability.util;


import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opencsv.bean.CsvToBean;

public class CsvToBeanCustom<T> extends CsvToBean<T> {

    /*
     * (non-Javadoc)
     *
     * @see
     * com.opencsv.bean.CsvToBean#processLine(com.opencsv.bean.MappingStrategy,
     * java.lang.String[])
     */
    /** The log. */
    private static Log log = LogFactory.getLog(CsvToBeanCustom.class);

    @Override
    protected Object convertValue(final String value, final PropertyDescriptor prop) throws InstantiationException,
        IllegalAccessException {
        return this.tryParseValue(value, prop);
    }

    /**
     * Try to get value from line. If input is empty, we should consider it as OK and return the default value of field.
     * by right, we should not see any exception while reading file. So if we have, just throw it out.
     *
     * @param value
     * @param prop
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    Object tryParseValue(final String value, final PropertyDescriptor prop) throws InstantiationException,
        IllegalAccessException {
        if (prop.getPropertyType().equals(String.class)) {
            return StringUtils.isNotEmpty(value) ? super.convertValue(value, prop) : null;
        }

        if (prop.getPropertyType().equals(Integer.class) || prop.getPropertyType().equals(int.class)) {
            return StringUtils.isNotEmpty(value) ? Integer.parseInt(value) : null;
        }
        if (prop.getPropertyType().equals(Character.class) || prop.getPropertyType().equals(char.class)) {
            return StringUtils.isNotBlank(value) ? value.charAt(0) : "";
        }
        if (prop.getPropertyType().equals(Short.class) || prop.getPropertyType().equals(short.class)) {
            return StringUtils.isNotEmpty(value) ? Short.parseShort(value) : null;
        }
        if (prop.getPropertyType().equals(Date.class)) {
            SimpleDateFormat format = null;
            if (!StringUtils.isEmpty(value)) {
                if (value.length() <= 10) {
                    format = new SimpleDateFormat("yyyy-MM-dd");
                } else if (value.length() <= 30) {
                    format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
                } else {
                    return null;
                }
            } else {
                return null;
            }
            try {
                return format.parse(value);
            } catch (final ParseException e) {
                CsvToBeanCustom.log.debug(e.getMessage());
            }

        }
        return super.convertValue(value, prop);
    }
}
