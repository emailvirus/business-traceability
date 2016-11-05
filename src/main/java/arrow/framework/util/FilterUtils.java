package arrow.framework.util;

import java.text.ParseException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * The Class FilterUtils.
 */
public class FilterUtils {

    /** The Constant MULTIPLE_RANGES_SEPARATOR. */
    private static final String MULTIPLE_RANGES_SEPARATOR = ",";

    /** The Constant RANGE_FROM_TO_SEPARATOR. */
    private static final String RANGE_FROM_TO_SEPARATOR = "-";

    /**
     * Parse multiple date ranges.
     *
     * @param str Multiple date ranges separated by commas (,) .
     * @return List of ranges, each range is a Date[2] from-date to-date array
     * @throws ParseException the parse exception
     */
    public static List<Date[]> parseMultipleDateRanges(String str) throws ParseException {
        if ((str == null) || (str = str.trim()).isEmpty()) {
            return null;
        }


        final List<Date[]> dateRanges = new ArrayList<Date[]>();

        final String[] ss = str.split(",");

        for (final String singleRangeString : ss) {
            final Date[] dateRange = FilterUtils.parseSingleDateRange(singleRangeString);
            if (dateRange.length > 0) {
                dateRanges.add(dateRange);
            }
        }

        return dateRanges;
    }


    /**
     * Parse single date range.
     *
     * @param str Single date range either in year-only, or year-month only representation, or two date representation
     *        connected by a dash (-)
     * @return A date range as a Date[2] from-date to-date array
     * @throws ParseException the parse exception
     */
    public static Date[] parseSingleDateRange(String str) throws ParseException {
        if ((str == null) || (str = str.trim()).isEmpty()) {
            return new Date[] {};
        }

        if (str.contains(FilterUtils.RANGE_FROM_TO_SEPARATOR)) {
            final Date[] dateRange = new Date[2];
            final String[] ss = str.split("\\" + FilterUtils.RANGE_FROM_TO_SEPARATOR);

            if (ss.length != 2) {
                throw new ParseException(str, 0);
            }

            final String fromDateString = ss[0];
            final String toDateString = ss[1];


            final Date[] fromDates = FilterUtils.parseImplicitDateRange(fromDateString);
            final Date[] toDates = FilterUtils.parseImplicitDateRange(toDateString);

            if ((fromDates.length == 0) || (toDates.length == 0)) {
                throw new ParseException(str, 0);
            }

            dateRange[0] = fromDates[0];
            dateRange[1] = toDates[1];

            return dateRange;
        }
        else {
            return FilterUtils.parseImplicitDateRange(str);
        }
    }


    /**
     * Parse implicit date range.
     *
     * @param str Single date range in year-only or year-month only representation
     * @return A date range as a Date[2] from-date to-date array
     * @throws ParseException the parse exception
     */
    public static Date[] parseImplicitDateRange(String str) throws ParseException {
        if ((str == null) || (str = str.trim()).isEmpty()) {
            return new Date[] {};
        }

        final Date[] dateRange = new Date[2];


        Date date = null;
        int type = 0; // 1: yyyy, 2: yyyy/MM, 3: yyyy/MM/dd

        date = Date.from(DateUtils.parseYYYY(str).atZone(ZoneId.systemDefault()).toInstant());
        type++;
        date = Date.from(DateUtils.parseYYYYMM(str).atZone(ZoneId.systemDefault()).toInstant());
        type++;
        date = Date.from(DateUtils.parseYYYYMMDD(str).atZone(ZoneId.systemDefault()).toInstant());
        type++;

        dateRange[0] = date;

        if (type == 3) {
            dateRange[1] = date;
        }
        else if (type == 2) {
            final Calendar c = new GregorianCalendar();
            c.setTime(date);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DAY_OF_MONTH, -1);
            dateRange[1] = c.getTime();
        }
        else if (type == 1) {
            final Calendar c = new GregorianCalendar();
            c.setTime(date);
            c.add(Calendar.YEAR, 1);
            c.add(Calendar.DAY_OF_MONTH, -1);
            dateRange[1] = c.getTime();
        }
        else {
            throw new ParseException(str, 0);
        }


        return dateRange;
    }


    /** The Constant SPLIT_BY_COMMAS_EXCEPT_THOSE_IN_QUOTE. */
    // http://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes
    private static final String SPLIT_BY_COMMAS_EXCEPT_THOSE_IN_QUOTE = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    /**
     * Parse string for like operator.
     *
     * @param str the str
     * @return the string[]
     */
    public static String[] parseStringForLikeOperator(String str) {
        if ((str == null) || (str = str.trim()).isEmpty()) {
            return new String[] {};
        }

        final String[] ss = str.split(FilterUtils.SPLIT_BY_COMMAS_EXCEPT_THOSE_IN_QUOTE);

        for (int i = 0; i < ss.length; i++) {
            ss[i] = ss[i].trim().toLowerCase().replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
            if (ss[i].startsWith("\"") && ss[i].endsWith("\"")) {
                ss[i] = ss[i].substring(1, ss[i].length() - 1);
            }

            ss[i] = "%" + ss[i] + "%";
        }

        return ss;
    }

    /** The Constant INFINITY. */
    private static final String INFINITY = "f";

    /**
     * Parse mutilple number ranges.
     *
     * @param str the str
     * @return the list
     * @throws ParseException the parse exception
     */
    public static List<Double[]> parseMultipleNumberRanges(String str) throws ParseException {
        if ((str == null) || (str = str.trim()).isEmpty()) {
            return null;
        }
        final List<Double[]> numberRanges = new ArrayList<Double[]>();

        final String[] ss = str.split(FilterUtils.MULTIPLE_RANGES_SEPARATOR);

        for (final String singleRangeString : ss) {
            final Double[] numberRange = FilterUtils.parseSingleNumberRange(singleRangeString);
            if (numberRange.length > 0) {
                numberRanges.add(numberRange);
            }
        }

        return numberRanges;
    }

    /**
     * Parse sigle number range.
     *
     * @param str the str
     * @return the double[]
     * @throws ParseException the parse exception
     */
    public static Double[] parseSingleNumberRange(String str) throws ParseException {
        if ((str == null) || (str = str.trim()).isEmpty()) {
            return new Double[] {};
        }
        final Double[] numberRange = new Double[2];
        if (str.lastIndexOf(FilterUtils.RANGE_FROM_TO_SEPARATOR) > 0) { // If "-" is not the first char,
            // then this is a range
            String tmpString = str;
            String startNegativeSign = ""; // Save the negative sign, in case the filter is not started
            // with infinity, we must added it back later
            if (tmpString.startsWith(FilterUtils.RANGE_FROM_TO_SEPARATOR)) {
                startNegativeSign = FilterUtils.RANGE_FROM_TO_SEPARATOR;
                tmpString = tmpString.substring(1);
            }
            final String[] ss = tmpString.split(FilterUtils.RANGE_FROM_TO_SEPARATOR, 2);

            if (ss.length != 2) {
                throw new ParseException(str, 0);
            }

            final String fromNumberString = ss[0];
            final String toNumberString = ss[1];

            final Double fromNumber = FilterUtils.INFINITY.equalsIgnoreCase(fromNumberString) ? null
                    : Double.parseDouble(startNegativeSign + fromNumberString);
            final Double toNumber =
                    FilterUtils.INFINITY.equalsIgnoreCase(toNumberString) ? null : Double.parseDouble(toNumberString);

            if ((fromNumber == null) && (toNumber == null)) {
                throw new ParseException(str, 0);
            }

            numberRange[0] = fromNumber;
            numberRange[1] = toNumber;
        }
        else { // This is a single number, mean equal
            final double number = Double.parseDouble(str);
            numberRange[0] = number;
            numberRange[1] = number;
        }
        return numberRange;
    }

    /**
     * Filter date.
     *
     * @param valueDate the value date
     * @param filter the filter
     * @return true, if successful
     * @throws ParseException the parse exception
     */
    public static boolean filterDate(final Date valueDate, final String filter) throws ParseException {
        final List<Date[]> dateRangeList = FilterUtils.parseMultipleDateRanges(filter);
        for (final Date[] dateRange : dateRangeList) {
            if (DateUtils.areEqual(dateRange[0], dateRange[1])) {
                if (DateUtils.areEqual(dateRange[0], valueDate)) {
                    return true;
                }
            }
            else {
                if ((DateUtils.compareDateOnly(dateRange[0], valueDate) <= 0)
                        && (DateUtils.compareDateOnly(dateRange[1], valueDate) >= 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Filter number.
     *
     * @param valueNumber the value number
     * @param filter the filter
     * @return true, if successful
     * @throws ParseException the parse exception
     */
    public static boolean filterNumber(final Number valueNumber, final String filter) throws ParseException {
        final double modelValue = valueNumber.doubleValue();
        final List<Double[]> numberRangeList = FilterUtils.parseMultipleNumberRanges(filter);
        for (final Double[] numberRange : numberRangeList) {
            if (numberRange[0] == null) { // From negative infinity to numberRange[1]
                if (numberRange[1] >= modelValue) {
                    return true;
                }
            }
            else if (numberRange[1] == null) { // From numberRange[0] to infinity
                if (numberRange[0] <= modelValue) {
                    return true;
                }
            }
            else if (numberRange[0].equals(numberRange[1])) { // Equal
                if (Double.doubleToLongBits(numberRange[0]) == Double.doubleToLongBits(modelValue)) {
                    return true;
                }
            }
            else { // Between
                if ((numberRange[0] <= modelValue) && (modelValue <= numberRange[1])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if the item fail the filter constraint.
     *
     * @param item the item
     * @param field the field
     * @param value the value
     * @return true, if successful
     */
    public static boolean itemFailedFilterConstraint(final Object item, final String field, final String value) {
        // TODO: Check if the item fail the filter constraint
        return false;
    }
}
