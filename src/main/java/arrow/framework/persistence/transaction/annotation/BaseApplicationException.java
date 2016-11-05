package arrow.framework.persistence.transaction.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation for identifying an Exception class as an Application Exception, which does not cause a transaction
 * rollback. This will NOT control the behavior of EJB container managed transactions.
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface BaseApplicationException {

    /**
     * Indicates whether the application exception designation should apply to subclasses of the annotated exception
     * class.
     *
     * @return true, if successful
     */
    boolean inherited() default true;

    /**
     * Indicates whether the container should cause the transaction to rollback when the exception is thrown.
     *
     * @return true, if successful
     */
    boolean rollback() default false;
}
