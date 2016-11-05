package arrow.framework.persistence.event.qualifier;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;


/**
 * Qualifies observer method parameters to select events that occur in a "after" phase in the JSF lifecycle.
 */
@Qualifier
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface BaseEntityManagerDisposed {

    /** The Constant LITERAL. */
    public static final AnnotationLiteral<BaseEntityManagerDisposed> LITERAL =
            new AnnotationLiteral<BaseEntityManagerDisposed>() {};
}
