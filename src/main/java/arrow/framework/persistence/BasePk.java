/*
 * package: arrow.framework.persistence file: BasePk.java time: Jun 27, 2014
 * @author michael
 */
package arrow.framework.persistence;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Embeddable;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;


/**
 * The Class BasePk.
 */
@SuppressWarnings(value = "DP_DO_INSIDE_DO_PRIVILEGED",
        justification = "Have to ignore because we don't clearly understand")
@Embeddable
public abstract class BasePk implements Serializable {

    /** The Constant LOGGER. */
    // log injection is not available
    private static final BaseLogger LOGGER = BaseLoggerProducer.getLogger();

    /**
     * Gets the columns.
     *
     * @return the columns
     */
    private HashMap<String, Object> getColumns() {
        final HashMap<String, Object> columns = new HashMap<String, Object>();

        final Field[] fields = this.getClass().getDeclaredFields();

        for (final Field f : fields) {
            if (f.getAnnotation(javax.persistence.Column.class) != null) {
                try {
                    f.setAccessible(true);
                    final Object column = f.get(this);
                    columns.put(f.getName(), column);
                } catch (final IllegalArgumentException | IllegalAccessException e) {
                    BasePk.LOGGER.debug("Error while reading fields", e);
                }
            }
        }

        return columns;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        final Map<String, Object> columns = this.getColumns();
        for (final Object column : columns.values()) {
            result = (prime * result) + (column == null ? 0 : column.hashCode());
        }
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if ((other == null) || !other.getClass().equals(this.getClass())) {
            return false;
        }

        final Map<String, Object> thisColumns = this.getColumns();
        final Map<String, Object> otherColumns = ((BasePk) other).getColumns();
        for (final Entry<String, Object> thisColItem : thisColumns.entrySet()) {
            final Object thisColumn = thisColItem.getValue();
            final Object otherColumn = otherColumns.get(thisColItem.getKey());
            if (!(thisColumn == null ? otherColumn == null : thisColumn.equals(otherColumn))) {
                return false;
            }
        }

        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("(");

        final Field[] fields = this.getClass().getDeclaredFields();

        for (final Field f : fields) {
            if (f.getAnnotation(javax.persistence.Column.class) != null) {
                try {
                    f.setAccessible(true);
                    sb.append(f.getName()).append("=").append(f.get(this)).append(";");
                } catch (final IllegalArgumentException | IllegalAccessException e) {
                    BasePk.LOGGER.debug("Error while reading fields", e);
                }
            }
        }

        return sb.append(")").toString();
    }

    /**
     * Gets the field by Name.
     *
     * @param fieldName the field name
     * @return the field
     */
    public Object getField(final String fieldName) {
        try {
            final Field field = this.getClass().getDeclaredField(fieldName);
            if (field.getAnnotation(javax.persistence.Column.class) == null) {
                throw new IllegalArgumentException("Field not annotated with @Column");
            }
            field.setAccessible(true);
            return field.get(this);

        } catch (final NoSuchFieldException e) {
            throw new IllegalArgumentException("Invalid Field Name");
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the field.
     *
     * @param fieldName the field name
     * @param value the value
     */
    public void setField(final String fieldName, final Object value) {
        try {
            final Field field = this.getClass().getDeclaredField(fieldName.trim());
            if (field.getAnnotation(javax.persistence.Column.class) == null) {
                throw new IllegalArgumentException("Field not annotated with @Column");
            }
            field.setAccessible(true);
            field.set(this, value);

        } catch (final NoSuchFieldException e) {
            throw new IllegalArgumentException("Invalid Field Name");
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if is sets the.
     *
     * @return true, if is sets the
     */
    public boolean isSet() {
        final Class<?> klass = this.getClass();
        final Field[] fields = klass.getDeclaredFields();
        for (final Field field : fields) {
            if (field.getAnnotation(javax.persistence.Column.class) != null) {
                field.setAccessible(true);
                Object obj = null;

                try {
                    obj = field.get(this);
                } catch (final IllegalArgumentException | IllegalAccessException e) {
                    BasePk.LOGGER.debug("error while reading field", e);
                }

                if (obj == null) {
                    return false;
                }
            }
        }
        return true;
    }


}
