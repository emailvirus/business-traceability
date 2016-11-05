package arrow.framework.persistence.event.qualifier;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;

import arrow.framework.persistence.BaseEntity;



/**
 * The Interface EntityUpdated.
 */
@Qualifier
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface EntityUpdated {

    /**
     * The Class Literal.
     */
    @SuppressWarnings("all")
    // MUST Always state reason for @SuppressWarnings("all")
    // Reason: to remove warning about implementing an Annotation. There's no work around
    public static class Literal extends AnnotationLiteral<EntityUpdated> implements EntityUpdated {

        /** The value. */
        private Class<? extends BaseEntity> value;

        /* (non-Javadoc)
         * @see arrow.framework.persistence.event.qualifier.EntityUpdated#value()
         */
        @Override
        public Class<? extends BaseEntity> value() {
            return this.value;
        }

        /**
         * Instantiates a new literal.
         *
         * @param value the value
         */
        public Literal(final Class<? extends BaseEntity> value) {
            if (value != null) {
                this.value = value;
            }
        }
    }

    /**
     * Value.
     *
     * @return the class<? extends base entity>
     */
    Class<? extends BaseEntity> value();
}
