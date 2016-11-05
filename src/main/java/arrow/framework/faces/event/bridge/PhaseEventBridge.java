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

import javax.enterprise.inject.spi.BeanManager;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;

import arrow.framework.faces.event.qualifier.After;
import arrow.framework.faces.event.qualifier.ApplyRequestValues;
import arrow.framework.faces.event.qualifier.Before;
import arrow.framework.faces.event.qualifier.InvokeApplication;
import arrow.framework.faces.event.qualifier.ProcessValidations;
import arrow.framework.faces.event.qualifier.RenderResponse;
import arrow.framework.faces.event.qualifier.RestoreView;
import arrow.framework.faces.event.qualifier.UpdateModelValues;
import arrow.framework.logging.BaseLogger;


/**
 * A PhaseListener used to bridge JSF phase events to the CDI event model.
 * <p/>
 * <p/>
 * For each JSF {@link PhaseEvent}, a corresponding Solder CDI event will be fired. Event listeners can be registered by
 * observing the appropriate Solder CDI event (see @{@link Observes}):
 * <p/>
 * <b>For example:</b>
 * <p/>
 * <code>
 * public void listener(@Observes @Before @RenderResponse PhaseEvent event)
 * {
 * //do something
 * }
 * </code>
 *
 * @author Nicklas Karlsson
 * @author Hugh Nguyen - adapted for TH6
 */

public class PhaseEventBridge implements PhaseListener {

    /** The log. */

    @Inject
    private BaseLogger log;

    /** The bean manager. */
    @Inject
    private BeanManager beanManager;

    /* (non-Javadoc)
     * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
     */
    @Override
    public void afterPhase(final PhaseEvent event) {
        this.handlePhase(After.LITERAL, event);
    }

    /* (non-Javadoc)
     * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
     */
    @Override
    public void beforePhase(final PhaseEvent event) {
        this.handlePhase(Before.LITERAL, event);
    }

    /* (non-Javadoc)
     * @see javax.faces.event.PhaseListener#getPhaseId()
     */
    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    /**
     * Gets the phase qualifier.
     *
     * @param id the id
     * @return the phase qualifier
     */
    private Annotation getPhaseQualifier(final PhaseId id) {
        if (PhaseId.RESTORE_VIEW.equals(id)) {
            return RestoreView.LITERAL;
        }
        if (PhaseId.PROCESS_VALIDATIONS.equals(id)) {
            return ProcessValidations.LITERAL;
        }
        if (PhaseId.APPLY_REQUEST_VALUES.equals(id)) {
            return ApplyRequestValues.LITERAL;
        }
        if (PhaseId.INVOKE_APPLICATION.equals(id)) {
            return InvokeApplication.LITERAL;
        }
        if (PhaseId.UPDATE_MODEL_VALUES.equals(id)) {
            return UpdateModelValues.LITERAL;
        }
        if (PhaseId.RENDER_RESPONSE.equals(id)) {
            return RenderResponse.LITERAL;
        }

        this.log.error("Unknown JSF PhaseId detected during CDI event broadcasting");
        return null;
    }

    /**
     * Handle phase.
     *
     * @param whenQualifier the when qualifier
     * @param event the event
     */
    private void handlePhase(final Annotation whenQualifier, final PhaseEvent event) {
        final Annotation phaseQualifier = this.getPhaseQualifier(event.getPhaseId());

        this.log.trace("Firing PhaseEvent with qualifiers [" + whenQualifier + ", " + phaseQualifier + "]");

        try {
            // This propagates the event to CDI
            /**
             * BeanManager.fireEvent(Object event, Annotation... qualifiers): if any observer throw exception, then the
             * the chain of observer processing would be halted. if the exception is RuntimeException, it would be
             * propagated thru if the exception is checked, it would be wrapped in a
             * javax.enterprise.event.ObserverException *
             */
            this.beanManager.fireEvent(event, whenQualifier, phaseQualifier);
        } catch (final RuntimeException e) {
            this.log.error("----------------------------------------");
            this.log.error("Exception from a PhaseEvent observer causes the processing "
                    + "of observer chain for this event to be halted, but everything else would still proceed", e);
            this.log.error("----------------------------------------");
        }

        /**
         * If an exception is thrown thru this method, it would be handled by the lifecycle handler. The lifecycle would
         * raise ExceptExceptionQueuedEvent which is listened by a SystemEventListener (which would be delegated to
         * Observers by our SystemEventBridge)
         */
    }
}
