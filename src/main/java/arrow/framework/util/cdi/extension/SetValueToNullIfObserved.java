package arrow.framework.util.cdi.extension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * The Interface SetValueToNullIfObserved.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
/**
 * Use to compact the idiom of private field + lazy getter + setNull method.
 * When the stated event is observed, the container would reset the field to null
 * @author HUGH
 *
 */
public @interface SetValueToNullIfObserved {

    /**
     * Event class.
     *
     * @return the class
     */
    public Class<?> eventClass() default Object.class;
}
