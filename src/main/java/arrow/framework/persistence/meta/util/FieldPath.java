package arrow.framework.persistence.meta.util;

import java.util.List;
import java.util.Objects;

import arrow.framework.util.StringUtils;
import arrow.framework.util.collections.ArrayUtils;


/**
 * An object of this class represents a 'field path' in the system. Let say we have two entity classes: Person and
 * Address. address is an object of type Person. street_name is an attribute of class Address. Then there are two field
 * paths associated with the entity class Person:<br />
 * 1. address (a field is also a field path).<br />
 * 2. address.street_name.<br />
 * To avoid confusing between a field path (represented by a string) and other strings, we represent all field paths by
 * instances of this class.
 *
 * @author linhbn
 */
public class FieldPath {

    /** The Constant SEPARATOR. */
    public static final String SEPARATOR = ".";

    /** The fields. */
    private final String[] fields;

    /**
     * Gets the field path.
     *
     * @return the field path
     */
    public String getFieldPath() {
        StringBuffer fieldPath = new StringBuffer();

        for (final String field : this.fields) {
            if (fieldPath.length() > 0) {
                fieldPath.append(FieldPath.SEPARATOR);
            }

            fieldPath.append(field);
        }

        return fieldPath.toString();
    }

    /**
     * Gets the fields.
     *
     * @return the fields
     */
    public String[] getFields() {
        return this.fields;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof FieldPath)) {
            return false;
        }

        final FieldPath fieldPath = (FieldPath) object;

        if (this.fields.length != fieldPath.getFields().length) {
            return false;
        }

        for (int i = 0; i < this.fields.length; ++i) {
            if (!StringUtils.areEqual(this.fields[i], fieldPath.getFields()[i])) {
                return false;
            }
        }

        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.getFieldPath();
    }

    /**
     * Instantiates a new field path.
     *
     * @param fieldPath the field path
     */
    public FieldPath(final String fieldPath) {
        if (StringUtils.isEmpty(fieldPath)) {
            throw new IllegalArgumentException("fieldPath can't be empty.");
        }

        this.fields = fieldPath.split("\\" + FieldPath.SEPARATOR);
    }

    /**
     * Instantiates a new field path.
     *
     * @param fields the fields
     */
    public FieldPath(final String[] fields) {
        if (ArrayUtils.isEmpty(fields)) {
            throw new IllegalArgumentException("fields can't be empty.");
        }

        for (int i = 0; i < fields.length; ++i) {
            if (StringUtils.isEmpty(fields[i])) {
                throw new IllegalArgumentException("Element #" + (i + 1) + " in the arguments is empty.");
            }
        }

        this.fields = fields;
    }

    /**
     * Value of.
     *
     * @param fieldPath the field path
     * @return the field path
     */
    public static FieldPath valueOf(final String fieldPath) {
        return new FieldPath(fieldPath);
    }

    /**
     * Value of.
     *
     * @param fields the fields
     * @return the field path
     */
    public static FieldPath valueOf(final String... fields) {
        return new FieldPath(fields);
    }

    /**
     * Value of.
     *
     * @param fieldList the field list
     * @return the field path
     */
    public static FieldPath valueOf(final List<String> fieldList) {
        return new FieldPath(fieldList.toArray(new String[fieldList.size()]));
    }

}
