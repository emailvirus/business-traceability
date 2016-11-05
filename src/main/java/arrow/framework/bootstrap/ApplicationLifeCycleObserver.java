package arrow.framework.bootstrap;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

import arrow.framework.bootstrap.qualifier.Startup;
import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;


/**
 * An asynchronous update interface for receiving notifications about ApplicationLifeCycle information as the
 * ApplicationLifeCycle is constructed.
 */
public class ApplicationLifeCycleObserver implements Extension {

    /** The Constant log. */
    // log injection is not available
    private static final BaseLogger LOG = BaseLoggerProducer.getLogger(ApplicationLifeCycleObserver.class);

    /** The startup beans. */
    private final Set<Bean<?>> startupBeans = new LinkedHashSet<Bean<?>>();

    /**
     * This method is called when information about an ApplicationLifeCycle which was previously requested using an
     * asynchronous interface becomes available.
     *
     * @param <X> the generic type
     * @param event the event
     */
    <X> void processBean(@Observes final ProcessBean<X> event) {
        if (event.getAnnotated().isAnnotationPresent(Startup.class)
            && event.getAnnotated().isAnnotationPresent(ApplicationScoped.class)) {
            this.startupBeans.add(event.getBean());
        }
    }

    /**
     * This method is called when information about an ApplicationLifeCycle which was previously requested using an
     * asynchronous interface becomes available.
     *
     * @param event the event
     * @param manager the manager
     */
    @SuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT",
        justification = "Cheat to force the bean to be initialized")
    void observeAfterDeploymentValidation(@Observes final AfterDeploymentValidation event, final BeanManager manager) {
        ApplicationLifeCycleObserver.LOG.debug("After Deployment Validation");
        for (final Bean<?> bean : this.startupBeans) {
            // the call to toString() is a cheat to force the bean to be initialized
            manager.getReference(bean, bean.getBeanClass(), manager.createCreationalContext(bean)).toString();
        }
    }
}
