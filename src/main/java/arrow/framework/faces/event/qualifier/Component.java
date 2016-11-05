/*
 * JBoss, Home of Professional Open Source Copyright 2011, Red Hat, Inc., and individual contributors by the @authors
 * tag. See the copyright.txt in the distribution for a full listing of individual contributors. Licensed under the
 * Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package arrow.framework.faces.event.qualifier;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;


/**
 * Qualifies observer method parameters to select events for a particular JSF component.
 *
 * @author Nicklas Karlsson
 */
@Qualifier
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface Component {

    /**
     * Value.
     *
     * @return the string
     */
    String value() default "";

    /**
     * The Class Literal.
     */
    @SuppressWarnings("all")
    // MUST Always state reason for @SuppressWarnings("all")
    // Reason: to remove warning about implementing an Annotation. There's no work around
    public static class Literal extends AnnotationLiteral<Component> implements Component {

        /** The value. */
        private String value = "";

        /* (non-Javadoc)
         * @see arrow.framework.faces.event.qualifier.Component#value()
         */
        @Override
        public String value() {
            return this.value;
        }

        /**
         * Instantiates a new literal.
         *
         * @param value the value
         */
        public Literal(final String value) {
            if (value != null) {
                this.value = value;
            }
        }
    }
}
