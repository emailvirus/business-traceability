/*
 * JBoss, Home of Professional Open Source Copyright 2011, Red Hat, Inc., and individual contributors by the @authors
 * tag. See the copyright.txt in the distribution for a full listing of individual contributors. Licensed under the
 * Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package arrow.framework.faces.event.bridge;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import javax.enterprise.inject.spi.BeanManager;
import javax.faces.component.UIViewRoot;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PostConstructViewMapEvent;
import javax.faces.event.PreDestroyViewMapEvent;
import javax.faces.event.PreRenderViewEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.inject.Inject;

import arrow.framework.faces.event.qualifier.Component;
import arrow.framework.faces.event.qualifier.View;
import arrow.framework.logging.BaseLogger;





/**
 * A SystemEventListener used to bridge JSF system events to the CDI event model.
 * For each JSF system event (e.g: {@link PostConstructApplicationEvent}, a corresponding Solder CDI event will be
 * fired.
 * Event listeners can be registered by observing the appropriate Solder CDI event (see @ {@link Observes}):
 * <b>For example:</b>
 * <code>
 * public void listener(@Observes javax.faces.event.ExceptionQueuedEvent event)
 * {
 * //do something
 * }
 * </code>
 *
 * @author Nicklas Karlsson
 * @author Hugh Nguyen - adapted for TH6
 */
public class SystemEventBridge implements SystemEventListener {

    /**
     * The bean manager.
     */
    @Inject BeanManager beanManager;

    /**
     * The log.
     */
    @Inject private BaseLogger log;

    /* (non-Javadoc)
     * @see javax.faces.event.SystemEventListener#isListenerForSource(java.lang.Object)
     */
    @Override public boolean isListenerForSource(final Object source) {
        return true;
    }

    /* (non-Javadoc)
     * @see javax.faces.event.SystemEventListener#processEvent(javax.faces.event.SystemEvent)
     */
    @Override public void processEvent(final SystemEvent event) throws AbortProcessingException {
        final Object payload = event.getClass().cast(event);
        final Annotation[] qualifiers = this.getQualifiers(event);

        this.log.trace(
                "Firing " + event.getClass().getSimpleName() + " with qualifiers: " + Arrays.toString(qualifiers));

        try {
            this.beanManager.fireEvent(payload, qualifiers);
        } catch (final RuntimeException e) {
            this.log.error("----------------------------------------");
            this.log.error("Exception from a SystemEvent observer, "
                    + "causes the processing of observer chain for this event to be halted, "
                    + "but everything else would still proceed", e);
            this.log.error("----------------------------------------");
        }
    }

    /**
     * Gets the qualifiers.
     *
     * @param event the event
     * @return the qualifiers
     */
    private Annotation[] getQualifiers(final SystemEvent event) {
        if ((event instanceof PreRenderViewEvent) || (event instanceof PostConstructViewMapEvent)
                || (event instanceof PreDestroyViewMapEvent)) {
            final String id = ((UIViewRoot) event.getSource()).getViewId();
            return new Annotation[] {new View.Literal(id)};
        } else if (event instanceof ComponentSystemEvent) {
            final String id = ((ComponentSystemEvent) event).getComponent().getId();
            return new Annotation[] {new Component.Literal(id)};
        } else {
            return new Annotation[] {};
        }
    }
}
