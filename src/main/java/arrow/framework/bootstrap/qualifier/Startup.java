package arrow.framework.bootstrap.qualifier;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * The Interface Startup.
 */
@Qualifier
@Target({TYPE})
@Retention(RUNTIME)
public @interface Startup {

}
