package arrow.framework.logging;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;



/**
 * The Interface Category.
 */
@Target({METHOD, FIELD, PARAMETER, TYPE})
@Retention(RUNTIME)
@Documented
public @interface Category {

    /**
     * Value.
     *
     * @return the string
     */
    String value();
}
