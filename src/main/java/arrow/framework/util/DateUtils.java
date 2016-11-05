package arrow.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * The Class DateUtils.
 */
public class DateUtils {

    /** The Constant JAPANESE_FULL_DATETIME_FORMAT. */
    private static final String JAPANESE_FULL_DATETIME_FORMAT = "yyyy'年'M'月'd'日' H'時'mm'分'ss'秒'";

    /** The Constant DATE_FORMATTER. */
    private static final String DATE_FORMATTER = "dd/MM/yyyy";

    /** The Constant YEARMONTHDAY_FORMATTER. */
    private static final String YEARMONTHDAY_FORMATTER = "yyyy/MM/dd";

    /** The Constant YEARMONTHDAYTIME_FORMATTER. */
    private static final String YEARMONTHDAYTIME_FORMATTER = "yyyy/MM/dd HH:mm:ss";

    /** The Constant TIME_FORMATTER. */
    private static final String TIME_FORMATTER = "HH:mm:ss";

    /** The Constant MIN_DATE. */
    public static final Date MIN_DATE = DateUtils.buildDate(1, 1, 1);

    /** The Constant MAX_DATE. */
    public static final Date MAX_DATE = DateUtils.buildDate(31, 12, 9999);

    /** The Constant YYYY_MM_DD_FORMATTER. */
    private static final DateTimeFormatter YYYY_MM_DD_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /** The Constant YYYY_MM_FORMATTER. */
    private static final DateTimeFormatter YYYY_MM_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM");

    /** The Constant YYYY_FORMATTER. */
    private static final DateTimeFormatter YYYY_FORMATTER = DateTimeFormatter.ofPattern("yyyy");

    /**
     * Parses the yyyymmdd.
     *
     * @param value the value
     * @return the local date time
     */
    public static LocalDateTime parseYYYYMMDD(final String value) {
        return LocalDateTime.parse(value, DateUtils.YYYY_MM_DD_FORMATTER);
    }

    /**
     * Parses the yyyymm.
     *
     * @param value the value
     * @return the local date time
     */
    public static LocalDateTime parseYYYYMM(final String value) {
        return LocalDateTime.parse(value, DateUtils.YYYY_MM_FORMATTER);
    }

    /**
     * Parses the yyyy.
     *
     * @param value the value
     * @return the local date time
     */
    public static LocalDateTime parseYYYY(final String value) {
        return LocalDateTime.parse(value, DateUtils.YYYY_FORMATTER);
    }

    /**
     * Gets the date format.
     *
     * @return the date format
     */
    private static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat(DateUtils.DATE_FORMATTER);
    }

    /**
     * Gets the time format.
     *
     * @return the time format
     */
    private static SimpleDateFormat getTimeFormat() {
        return new SimpleDateFormat(DateUtils.TIME_FORMATTER);
    }

    /**
     * Gets the year month day format.
     *
     * @return the year month day format
     */
    private static SimpleDateFormat getYearMonthDayFormat() {
        return new SimpleDateFormat(DateUtils.YEARMONTHDAY_FORMATTER);
    }

    /**
     * Gets the date time format.
     *
     * @return the date time format
     */
    private static SimpleDateFormat getDateTimeFormat() {
        return new SimpleDateFormat(DateUtils.YEARMONTHDAY_FORMATTER);
    }

    /**
     * This method compares two dates without considering hour / minute / second. For example firstDate = 2011/03/08
     * 10:20:30 secondDate = 2011/03/08 5:10:20 these two dates are equal since they have the same year, the same month
     * and the same day of month.
     *
     * @param firstDate the first date
     * @param secondDate the second date
     * @return The distance in days between the two dates. A value < 0 means the <code>firstDate</code> is BEFORE the
     *         <code>secondDate</code>. A value > 0 means the <code>firstDate</code> is AFTER the
     *         <code>secondDate</code>. Zero value means they are equal.
     */
    public static int compareDateOnly(final Date firstDate, final Date secondDate) {

        if ((firstDate == null) || (secondDate == null)) {
            throw new IllegalArgumentException("The arguments cannot be null");
        }

        final Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.setTime(firstDate);

        final Calendar secondCalendar = Calendar.getInstance();
        secondCalendar.setTime(secondDate);

        final int year = secondCalendar.get(Calendar.YEAR);
        final int month = secondCalendar.get(Calendar.MONTH);
        final int date = secondCalendar.get(Calendar.DATE);

        secondCalendar.setTime(firstDate);
        secondCalendar.set(Calendar.YEAR, year);
        secondCalendar.set(Calendar.MONTH, month);
        secondCalendar.set(Calendar.DATE, date);

        final long oneDayInMillis = 24 * 60 * 60 * 1000L;
        return (int) ((firstCalendar.getTimeInMillis() - secondCalendar.getTimeInMillis()) / oneDayInMillis);
    }

    /**
     * Are equal.
     *
     * @param firstValue the first value
     * @param secondValue the second value
     * @return true, if successful
     */
    public static boolean areEqual(final Date firstValue, final Date secondValue) {
        if ((firstValue == null) || (secondValue == null)) {
            return (firstValue == null) && (secondValue == null);
        }

        return firstValue.getTime() == secondValue.getTime();
    }

    /**
     * Gets the lower bound.
     *
     * @param date the date
     * @return the lower bound
     */
    public static Date getLowerBound(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Gets the upper bound.
     *
     * @param date the date
     * @return the upper bound
     */
    public static Date getUpperBound(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * Gets the current datetime.
     *
     * @return the current datetime
     */
    public static Date getCurrentDatetime() {
        return Date.from(Instant.now());
    }

    /**
     * Removes the time.
     *
     * @param date the date
     * @return the date
     */
    public static Date removeTime(final Date date) {
        if (date == null) {
            return null;
        }

        // Get an instance of the Calendar.
        final Calendar calendar = Calendar.getInstance();

        // Make sure the calendar will not perform automatic correction.
        // calendar.setLenient(false);

        // Set the time of the calendar to the given date.
        calendar.setTime(date);

        // Remove the hours, minutes, seconds and milliseconds.

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Return the date again.
        return calendar.getTime();
    }

    /**
     * Removes the date.
     *
     * @param date the date
     * @return the date
     */
    public static Date removeDate(final Date date) {
        if (date == null) {
            return null;
        }

        // Get an instance of the Calendar.
        final Calendar calendar = Calendar.getInstance();

        // Make sure the calendar will not perform automatic correction.
        // calendar.setLenient(false);

        // Set the time of the calendar to the given date.
        calendar.setTime(date);

        // Remove the date , month , year

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, 1970);
        calendar.set(Calendar.MONTH, 0);

        // Return the date again.
        return calendar.getTime();
    }

    /**
     * Gets the date difference.
     *
     * @param fromDate the from date
     * @param toDate the to date
     * @param type the type
     * @return the date difference
     */
    private static boolean getDateDifference(final Date fromDate, final Date toDate, final String type) {
        boolean result = false;

        final Date firstDate = DateUtils.removeTime(fromDate);
        final Date secondDate = DateUtils.removeTime(toDate);

        if (type.equalsIgnoreCase("after")) {
            result = firstDate.after(secondDate);
        } else if (type.equalsIgnoreCase("before")) {
            result = firstDate.before(secondDate);
        }

        return result;
    }

    /**
     * Gets the time difference.
     *
     * @param fromDate the from date
     * @param toDate the to date
     * @param type the type
     * @return the time difference
     */
    private static boolean getTimeDifference(final Date fromDate, final Date toDate, final String type) {
        boolean result = false;

        Date firstDate = DateUtils.removeDate(fromDate);
        Date secondDate = DateUtils.removeDate(toDate);

        if (type.equalsIgnoreCase("after")) {
            result = firstDate.after(secondDate);
        } else if (type.equalsIgnoreCase("before")) {
            result = secondDate.before(secondDate);
        }
        return result;
    }

    /**
     * Time after.
     *
     * @param fromDate the from date
     * @param toDate the to date
     * @return true, if successful
     */
    public static boolean timeAfter(final Date fromDate, final Date toDate) {
        return DateUtils.getTimeDifference(fromDate, toDate, "after");
    }

    /**
     * Time before.
     *
     * @param fromDate the from date
     * @param toDate the to date
     * @return true, if successful
     */
    public static boolean timeBefore(final Date fromDate, final Date toDate) {
        return DateUtils.getTimeDifference(fromDate, toDate, "before");
    }

    /**
     * After.
     *
     * @param fromDate the from date
     * @param toDate the to date
     * @return true, if successful
     */
    // Returns only the date difference.
    public static boolean after(final Date fromDate, final Date toDate) {
        return DateUtils.getDateDifference(fromDate, toDate, "after");
    }

    /**
     * Before.
     *
     * @param fromDate the from date
     * @param toDate the to date
     * @return true, if successful
     */
    // Returns only the date difference.
    public static boolean before(final Date fromDate, final Date toDate) {
        return DateUtils.getDateDifference(fromDate, toDate, "before");
    }

    /**
     * Check if the given date is between the given date range.
     *
     * @param date to check
     * @param fromDate the from date
     * @param toDate the to date
     * @return <code>true</code> if the given date is between the given date range
     */
    public static boolean between(final Date date, final Date fromDate, final Date toDate) {
        return (date != null) && !DateUtils.before(date, fromDate) && DateUtils.after(date, toDate);
    }

    /**
     * Gets the calendar.
     *
     * @param date the date
     * @return the calendar
     */
    public static Calendar getCalendar(final Date date) {
        final Calendar calendar = new GregorianCalendar();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        calendar.setTime(date);
        return calendar;
    }

    /**
     * Gets the calendar.
     *
     * @return the calendar
     */
    public static Calendar getCalendar() {
        return DateUtils.getCalendar(new Date());
    }

    /**
     * Gets the day of month.
     *
     * @param date the date
     * @return the day of month
     */
    public static int getDayOfMonth(final Date date) {
        return DateUtils.getCalendar(date).get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Gets the month.
     *
     * @param date the date
     * @return the month
     */
    public static int getMonth(final Date date) {
        return DateUtils.getCalendar(date).get(Calendar.MONTH) + 1;
    }

    /**
     * Gets the year.
     *
     * @param date the date
     * @return the year
     */
    public static int getYear(final Date date) {
        return DateUtils.getCalendar(date).get(Calendar.YEAR);
    }

    /**
     * Gets the days in month.
     *
     * @param date the date
     * @return the days in month
     */
    public static int getDaysInMonth(final Date date) {
        final Calendar calendar = DateUtils.getCalendar();
        calendar.setTime(date);
        // return calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // subbu:getActualMaximum
        // calculate
        // leap year
        // wrongly.Ex: 200 year it consider as leap year.But its not a leap year.
        return DateUtils.getDaysInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
    }

    /**
     * Gets the days in month.
     *
     * @param year the year
     * @param month the month
     * @return the days in month
     */
    public static int getDaysInMonth(int year, int month) {
        while (month > 12) {
            month -= 12;
            year++;
        }

        while (month < 1) {
            month += 12;
            year--;
        }

        switch (month) {
            case 2:
                return DateUtils.isLeapYear(year) ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    /**
     * Checks if is leap year.
     *
     * @param year the year
     * @return true, if is leap year
     */
    public static boolean isLeapYear(final int year) {
        return ((((year % 4) == 0) && ((year % 100) != 0)) || ((year % 400) == 0));
    }

    /**
     * Checks if is valid date.
     *
     * @param date the date
     * @return true, if is valid date
     */
    public static boolean isValidDate(final Date date) {
        if (date == null) {
            return false;
        }
        if (!DateUtils.between(date, DateUtils.MIN_DATE, DateUtils.MAX_DATE)
            || (DateUtils.getDaysInMonth(date) < DateUtils.getDayOfMonth(date))) {
            return false;
        }
        return true;
    }

    /**
     * Parses the date range.
     *
     * @param range the range
     * @return the date[]
     */
    public static Date[] parseDateRange(final String range) {
        try {
            final String[] tokens = range.split("-");
            if ((tokens.length != 1) && (tokens.length != 2)) {
                return new Date[] {};
            }
            Date dateFrom = DateUtils.getDateFormat().parse(tokens[0].trim());
            if (!DateUtils.isValidDate(dateFrom)) {
                return new Date[] {};
            }

            Date dateTo = tokens.length == 2 ? DateUtils.getTimeFormat().parse(tokens[1].trim()) : dateFrom;
            if (!DateUtils.isValidDate(dateTo)) {
                return new Date[] {};
            }
            if (dateFrom.after(dateTo)) {
                return new Date[] {};
            }
            // If the date range doesn't contain TIME information, the date range must be from the
            // beginning moment of the 'from' date to the end moment of the 'to' date.
            // Several screens update both date and time to date fields, hence can't search by date
            // alone
            // in the
            // summary screen. This modification is to cater for such screens.
            dateFrom = DateUtils.getLowerBound(dateFrom);
            dateTo = DateUtils.getUpperBound(dateTo);
            return new Date[] {dateFrom, dateTo};
        } catch (final ParseException e) {
            System.out.println(e.getMessage());
            return new Date[] {};
        }
    }

    /**
     * Adjust date.
     *
     * @param date the date
     * @param type the type
     * @param amount the amount
     * @return the date
     */
    public static Date adjustDate(final Date date, final int type, final int amount) {
        final Calendar calendar = DateUtils.getCalendar();
        calendar.setTime(date);
        calendar.add(type, amount);
        return calendar.getTime();
    }


    /**
     * Adjust month.
     *
     * @param date the date
     * @param months the months
     * @return the date
     */
    public static Date adjustMonth(final Date date, final int months) {
        return DateUtils.adjustDate(date, Calendar.MONTH, months);
    }

    /**
     * Adjust day.
     *
     * @param date the date
     * @param days the days
     * @return the date
     */
    public static Date adjustDay(final Date date, final int days) {
        return DateUtils.adjustDate(date, Calendar.DAY_OF_YEAR, days);
    }

    /**
     * To local date time.
     *
     * @param value the value
     * @return the local date time
     */
    private static LocalDateTime toLocalDateTime(final Date value) {
        return LocalDateTime.ofInstant(value.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Gets the hour.
     *
     * @param date the date
     * @return the hour
     */
    public static int getHour(final Date date) {
        return DateUtils.getCalendar(date).get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Gets the minute.
     *
     * @param date the date
     * @return the minute
     */
    public static int getMinute(final Date date) {
        return DateUtils.getCalendar(date).get(Calendar.MINUTE);
    }

    /**
     * Compare date.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return return 1 if startDate after endDate, return -1 if startDate before endDate, otherwise return 0
     */
    public static int compareDate(final Date startDate, final Date endDate) {
        if (startDate.compareTo(endDate) > 0) {
            return 1;
        } else if (startDate.compareTo(endDate) < 0) {
            return -1;
        } else if (startDate.compareTo(endDate) == 0) {
            return 0;
        }
        return -1;
    }

    /**
     * Extract duration.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return the date
     */
    public static Date extractDuration(final Date startDate, final Date endDate) {
        AssertUtils.assertNotNull(startDate);
        AssertUtils.assertNotNull(endDate);
        LocalDateTime fromDate = DateUtils.toLocalDateTime(startDate);
        LocalDateTime toDate = DateUtils.toLocalDateTime(endDate);
        Duration duration = Duration.between(fromDate, toDate);
        return Date.from(LocalDateTime.of(LocalDate.now(), LocalTime.ofSecondOfDay(duration.getSeconds()))
            .atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Builds the time.
     *
     * @param hour the hour
     * @param minute the minute
     * @param second the second
     * @return the date
     */
    public static Date buildTime(final int hour, final int minute, final int second) {
        final LocalTime test = LocalTime.of(hour, minute, second);
        return Date.from(LocalDateTime.of(LocalDate.now(), test).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Builds the date.
     *
     * @param day the day
     * @param month the month
     * @param year the year
     * @return the date
     */
    public static Date buildDate(final int day, final int month, final int year) {
        final LocalDate test = LocalDate.of(year, month, day);
        return Date.from(test.atTime(0, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Format date.
     *
     * @param value the value
     * @return the string
     */
    public static String formatDate(final Date value) {
        return DateUtils.getYearMonthDayFormat().format(value);
    }

    /**
     * Format date time.
     *
     * @param value the value
     * @return the string
     */
    public static String formatDateTime(final Date value) {
        return DateUtils.getYearMonthDayFormat().format(value);
    }

    /**
     * Format date time not slash.
     *
     * @param value the value
     * @return the string
     */
    public static String formatDateTimeNotSlash(final Date value) {
        return DateUtils.getDateTimeFormat().format(value);
    }

    /**
     * Format time.
     *
     * @param value the value
     * @return the string
     */
    public static String formatTime(final Date value) {
        return DateUtils.getTimeFormat().format(value);
    }

    /**
     * Gets the first day of month.
     *
     * @param inputMonth the input month
     * @return the first day of month
     */
    public static Date getFirstDayOfMonth(final Date inputMonth) {
        final LocalDateTime date = DateUtils.toLocalDateTime(inputMonth);
        return Date.from(date.with(TemporalAdjusters.firstDayOfMonth()).atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * Gets the last day of month.
     *
     * @param inputMonth the input month
     * @return the last day of month
     */
    public static Date getLastDayOfMonth(final Date inputMonth) {
        final LocalDateTime date = DateUtils.toLocalDateTime(inputMonth);
        return Date.from(date.with(TemporalAdjusters.lastDayOfMonth()).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Format full date.
     *
     * @param date the date
     * @return the string
     */
    public static String formatFullDate(final Date date) {
        final LocalDateTime dt = DateUtils.toLocalDateTime(date);
        return DateTimeFormatter.ISO_DATE_TIME.withLocale(Locale.JAPANESE).format(dt);
    }


    /**
     * Format japanese full date.
     *
     * @param date the date
     * @return the string
     */
    public static String formatJapaneseFullDate(final Date date) {
        final DateTime dt = new DateTime(date);
        return DateTimeFormat.fullDate().withLocale(Locale.JAPANESE).print(dt);

    }


    /**
     * Format japanese full date time.
     *
     * @param date the date
     * @return the string
     */
    public static String formatJapaneseFullDateTime(final Date date) {
        final DateTime dt = new DateTime(date);
        return org.joda.time.format.DateTimeFormat.forPattern(DateUtils.JAPANESE_FULL_DATETIME_FORMAT).print(dt);

    }

    /**
     * Format full date time.
     *
     * @param date the date
     * @param locale the locale
     * @return the string
     */
    public static String formatFullDateTime(final Date date, final Locale locale) {
        if (locale.getLanguage().equals(Locale.JAPANESE.getLanguage())) {
            return DateUtils.formatJapaneseFullDateTime(date);
        }
        return DateTimeFormat.forPattern(DateUtils.YEARMONTHDAYTIME_FORMATTER).print(new DateTime(date));
    }

    /**
     * Gets the previous month from current date.
     *
     * @param currentDate the current date
     * @return the previous month from current date
     */
    public static Date getPreviousMonthFromCurrentDate(final Date currentDate) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    /**
     * Gets the previous day from current date.
     *
     * @param currentDate the current date
     * @return the previous day from current date
     */
    public static Date getPreviousDayFromCurrentDate(final Date currentDate) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * Gets the current year.
     *
     * @return the current year
     */
    public static String getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return Integer.toString(cal.get(Calendar.YEAR));
    }

    /**
     * Gets the current month.
     *
     * @return the current month
     */
    public static String getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        return Integer.toString(cal.get(Calendar.MONTH) + 1);
    }

    /**
     * Gets the date time from string.
     *
     * @param strTime the str time
     * @return the date time from string
     */
    public static Date getDateTimeFromString(final String strTime) {
        try {
            return DateUtils.getDateTimeFormat().parse(strTime);
        } catch (ParseException pe) {
            System.err.println(pe.getMessage());
            return new Date();
        }
    }

    /**
     * Gets the convert to time.
     *
     * @param hour the hour
     * @param minute the minute
     * @return the convert to time
     */
    public static Date getConvertToTime(final int hour, final int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        return calendar.getTime();
    }

    /**
     * Gets the next hour.
     *
     * @param date the date
     * @return the next hour
     */
    public static Date getNextHour(final Date date) {
        // Set next hour for new date
        Date newDate = new Date();
        newDate.setTime(date.getTime() + (60 * 60 * 1000));
        return newDate;
    }

    /**
     * Convert string to date.
     *
     * @param dateStr the date str
     * @return the date
     */
    public static Date convertStringToDate(final String dateStr) {
        try {
            return DateUtils.getDateTimeFormat().parse(dateStr);
        } catch (ParseException pe) {
            return new Date();
        }
    }
}
