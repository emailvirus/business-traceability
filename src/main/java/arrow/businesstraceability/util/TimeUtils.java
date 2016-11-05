package arrow.businesstraceability.util;

import java.util.ArrayList;
import java.util.List;

public class TimeUtils {

    static final long MILLISECONDS_PER_HOUR = 1000L * 3600;

    /**
     * Calculate millisecond between two hours.
     *
     * @param selectHour
     * @return
     */
    public static long millisBettweenTwoHours(final int selectHour, final int targetHour) {
        return (selectHour - targetHour) * TimeUtils.MILLISECONDS_PER_HOUR;
    }

    /**
     * Convert hour to millisecond.
     *
     * @param hour
     * @return
     */
    public static long convertHourToMills(final int hour) {
        return hour * TimeUtils.MILLISECONDS_PER_HOUR;
    }

    /**
     * Hour of Day.
     *
     * @return
     */
    public static List<Integer> getListHourOfDay() {
        final List<Integer> hours = new ArrayList<Integer>();
        for (int hour = 0; hour < 24; hour++) {
            hours.add(hour);
        }
        return hours;
    }

    /**
     * List Hour of Day.
     *
     * @return
     */
    public static List<Integer> getListCountHourOfDay() {
        final List<Integer> hours = new ArrayList<Integer>();
        for (int hour = 1; hour <= 24; hour++) {
            hours.add(hour);
        }
        return hours;
    }
}
