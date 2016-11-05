package arrow.businesstraceability.control.misc;

import java.util.Date;

import arrow.framework.util.AssertUtils;
import arrow.framework.util.DateUtils;

/**
 * The Class WorkingTime.
 */
public class WorkingTime implements Comparable<WorkingTime> {

    /** The hour. */
    private int hour;

    /** The minute. */
    private int minute;

    /**
     * Instantiates a new working time.
     */
    public WorkingTime() {

    }

    /**
     * Constructor with param.
     *
     * @param workingTime the working time
     */
    public WorkingTime(final Date workingTime) {
        AssertUtils.assertNotNull(workingTime);
        this.hour = DateUtils.getHour(workingTime);
        this.minute = DateUtils.getMinute(workingTime);
    }

    /**
     * Setting working time is empty.
     *
     * @return the working time
     */
    public static WorkingTime emptyWorkingTime() {
        WorkingTime empty = new WorkingTime();
        empty.setHour(0);
        empty.setMinute(-1);
        return empty;
    }

    /**
     * Gets the hour.
     *
     * @return the hour
     */
    public int getHour() {
        return this.hour;
    }

    /**
     * Sets the hour.
     *
     * @param hour the new hour
     */
    public void setHour(final int hour) {
        this.hour = hour;
    }

    /**
     * Gets the minute.
     *
     * @return the minute
     */
    public int getMinute() {
        return this.minute;
    }

    /**
     * Sets the minute.
     *
     * @param minute the new minute
     */
    public void setMinute(final int minute) {
        this.minute = minute;
    }

    /**
     * Gets the time.
     *
     * @return the time
     */
    public Date getTime() {
        return DateUtils.buildTime(this.hour, this.minute, 0);
    }

    /**
     * Checks if is valid.
     *
     * @return true, if is valid
     */
    public boolean isValid() {
        return ((this != WorkingTime.emptyWorkingTime()) && (this.hour > 0) && (this.minute >= 0));
    }

    @Override
    public int compareTo(final WorkingTime obj) {
        if (obj == null) {
            return 1;
        }
        if (this.hour > obj.hour) {
            return 1;
        }
        else if (this.hour == obj.hour) {
            return (this.minute > obj.minute ? 1 : (this.minute == obj.minute ? 0 : -1));
        }
        return -1;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof WorkingTime) {
            return false;
        }
        return this.compareTo((WorkingTime) obj) == 0;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.hour) + Integer.hashCode(this.minute);
    }

}
