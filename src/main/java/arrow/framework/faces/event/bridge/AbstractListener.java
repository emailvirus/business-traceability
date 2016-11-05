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

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import arrow.framework.util.cdi.CDIUtils;


/**
 * Superclass for event listeners.
 *
 * @author Nicklas Karlsson
 * @author Hugh Nguyen - Adapted for TH6
 * @param <T> Listener class
 */
public class AbstractListener<T extends EventListener> {

    /** The bean manager. */
    @Inject
    private BeanManager beanManager;

    /**
     * Gets the bean manager.
     *
     * @return the bean manager
     */
    public BeanManager getBeanManager() {
        if (this.beanManager == null) {
            this.beanManager = CDIUtils.getBeanManager();
        }

        return this.beanManager;
    }


    /**
     * Gets the listeners.
     *
     * @param classes the classes
     * @return the listeners
     */
    @SuppressWarnings("unchecked")
    protected List<T> getListeners(final Class<? extends T>... classes) {
        final List<T> listeners = new ArrayList<T>();
        for (final Class<? extends T> clazz : classes) {
            final Bean<? extends T> bean = (Bean<? extends T>) this.getBeanManager().getBeans(clazz).iterator().next();
            final CreationalContext<? extends T> context = this.getBeanManager().createCreationalContext(bean);
            final T listener = (T) this.getBeanManager().getReference(bean, clazz, context);
            listeners.add(listener);
        }
        return listeners;
    }

    /**
     * Create contextual instances for the specified listener classes, excluding any listeners that do not correspond to
     * an enabled bean.
     *
     * @param classes the classes
     * @return the enabled listeners
     */
    @SuppressWarnings("unchecked")
    protected List<T> getEnabledListeners(final Class<? extends T>... classes) {
        final List<T> listeners = new ArrayList<T>();
        for (final Class<? extends T> clazz : classes) {
            final Set<Bean<?>> beans = this.getBeanManager().getBeans(clazz);
            if (!beans.isEmpty()) {
                final Bean<T> bean = (Bean<T>) this.getBeanManager().resolve(beans);
                final CreationalContext<T> context = this.getBeanManager().createCreationalContext(bean);
                listeners.add((T) this.getBeanManager().getReference(bean, clazz, context));
            }
        }
        return listeners;
    }
}
