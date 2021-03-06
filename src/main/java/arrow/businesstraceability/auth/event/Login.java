package arrow.businesstraceability.auth.event;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;


/**
 * The Interface Login.
 */
@Qualifier
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface Login {
    
    /** The Constant LITERAL. */
    public static final AnnotationLiteral<Login> LITERAL = new AnnotationLiteral<Login>() {};
}
