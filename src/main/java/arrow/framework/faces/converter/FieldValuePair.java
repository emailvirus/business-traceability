/*
 * package: arrow.framework.faces.converter file: FieldValuePair.java time: Jun 27, 2014
 * @author michael
 */
package arrow.framework.faces.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.el.ValueExpression;
import javax.persistence.metamodel.SingularAttribute;

import arrow.framework.faces.util.FacesELUtils;


/**
 * The Class FieldValuePair.
 *
 * @param <T> the generic type
 */
public class FieldValuePair<T> {

    /** The field attribute. */
    private SingularAttribute<? super T, ?> fieldAttribute;

    /** The value. */
    private Object value;

    /**
     * Instantiates a new field value pair.
     *
     * @param fieldAttribute the field attribute
     * @param value the value
     */
    public FieldValuePair(final SingularAttribute<? super T, ?> fieldAttribute, final Object value) {
        this.fieldAttribute = fieldAttribute;
        this.value = value;
    }

    /**
     * Evaluate other fields.
     *
     * @param <T> the generic type
     * @param otherFieldMap the other field map
     * @return the list
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> List<FieldValuePair<T>> evaluateOtherFields(
            final Map<SingularAttribute<? super T, ?>, Object> otherFieldMap) {
        final List<FieldValuePair<T>> otherFieldValues = new ArrayList<FieldValuePair<T>>();
        if (otherFieldMap.isEmpty()) {
            return otherFieldValues;
        }
        for (final Entry<SingularAttribute<? super T, ?>, Object> entry : otherFieldMap.entrySet()) {
            Object evaluatedValue = null;
            if (otherFieldMap.get(entry.getKey()) instanceof ValueExpression) {
                evaluatedValue = FacesELUtils.evaluateValueExpression((ValueExpression) entry.getValue());
            }
            else {
                evaluatedValue = entry.getValue();
            }
            otherFieldValues.add(new FieldValuePair(entry.getKey(), evaluatedValue));

        }

        return otherFieldValues;
    }

    /**
     * Gets the field attribute.
     *
     * @return the field attribute
     */
    public SingularAttribute<? super T, ?> getFieldAttribute() {
        return this.fieldAttribute;
    }

    /**
     * Sets the field attribute.
     *
     * @param fieldAttribute the field attribute
     */
    public void setFieldAttribute(final SingularAttribute<? super T, ?> fieldAttribute) {
        this.fieldAttribute = fieldAttribute;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(final Object value) {
        this.value = value;
    }
}
