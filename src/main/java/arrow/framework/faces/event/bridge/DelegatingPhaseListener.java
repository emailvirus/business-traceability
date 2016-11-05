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

import java.util.List;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;



/**
 * Provide CDI injection to PhaseListener artifacts by delegating through this class.
 *
 * @author <a href="mailto:lincolnbaxter@gmail.com>Lincoln Baxter, III</a>
 * @author Hugh Nguyen - adapted for TH6
 */

public class DelegatingPhaseListener extends AbstractListener<PhaseListener> implements PhaseListener {

    /** The Constant log. */
    // log injection is not available
    private static final BaseLogger LOG = BaseLoggerProducer.getLogger();

    /* (non-Javadoc)
     * @see javax.faces.event.PhaseListener#getPhaseId()
     */
    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    /* (non-Javadoc)
     * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
     */
    @Override
    public void beforePhase(final PhaseEvent event) {
        DelegatingPhaseListener.LOG.debug("-------- BEFORE PHASE: " + event.getPhaseId());

        for (final PhaseListener listener : this.getPhaseListeners()) {
            if (this.shouldProcessPhase(listener, event)) {
                listener.beforePhase(event);
            }
        }
    }

    /* (non-Javadoc)
     * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
     */
    @Override
    public void afterPhase(final PhaseEvent event) {
        DelegatingPhaseListener.LOG.debug("-------- AFTER PHASE: " + event.getPhaseId());
        for (final PhaseListener listener : this.getPhaseListeners()) {
            if (this.shouldProcessPhase(listener, event)) {
                listener.afterPhase(event);
            }
        }
    }

    /**
     * Determine if the {@link PhaseListener} should process the given {@link PhaseEvent}.
     *
     * @param listener the listener
     * @param event the event
     * @return true, if successful
     */
    private boolean shouldProcessPhase(final PhaseListener listener, final PhaseEvent event) {
        return (PhaseId.ANY_PHASE.equals(listener.getPhaseId()) || event.getPhaseId().equals(listener.getPhaseId()));
    }


    /**
     * Gets the phase listeners.
     *
     * @return the phase listeners
     */
    @SuppressWarnings("unchecked")
    private List<PhaseListener> getPhaseListeners() {
        return this.getEnabledListeners(PhaseEventBridge.class);
    }
}
