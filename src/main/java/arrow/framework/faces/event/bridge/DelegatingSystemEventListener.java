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

import javax.faces.event.AbortProcessingException;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;



/**
 * Provide CDI injection to SystemEventListener artifacts by delegating through this class.
 *
 * @author <a href="mailto:lincolnbaxter@gmail.com>Lincoln Baxter, III</a>
 * @author Hugh Nguyen - adapted for TH6
 */
public class DelegatingSystemEventListener extends AbstractListener<SystemEventListener> implements SystemEventListener {

    /** The Constant log. */
    // log injection is not available
    private static final BaseLogger LOG = BaseLoggerProducer.getLogger();

    /* (non-Javadoc)
     * @see javax.faces.event.SystemEventListener#isListenerForSource(java.lang.Object)
     */
    @Override
    public boolean isListenerForSource(final Object source) {
        return true;
    }

    /* (non-Javadoc)
     * @see javax.faces.event.SystemEventListener#processEvent(javax.faces.event.SystemEvent)
     */
    @Override
    public void processEvent(final SystemEvent event) throws AbortProcessingException {
        if ((event instanceof PreDestroyApplicationEvent) && (this.getBeanManager() == null)) {
            DelegatingSystemEventListener.LOG
                    .info("BeanManager no longer available; Cannot notify CDI-managed listeners of "
                            + PreDestroyApplicationEvent.class.getSimpleName());
            return;
        }

        for (final SystemEventListener l : this.getEventListeners()) {
            if (l.isListenerForSource(event.getSource())) {
                l.processEvent(event);
            }
        }
    }

    /**
     * Gets the event listeners.
     *
     * @return the event listeners
     */
    @SuppressWarnings("unchecked")
    private List<SystemEventListener> getEventListeners() {
        return this.getListeners(SystemEventBridge.class);
    }
}
