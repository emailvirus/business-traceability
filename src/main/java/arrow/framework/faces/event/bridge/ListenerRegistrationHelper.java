package arrow.framework.faces.event.bridge;

import javax.annotation.PostConstruct;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.PhaseListener;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.PostConstructCustomScopeEvent;
import javax.faces.event.PostConstructViewMapEvent;
import javax.faces.event.PostPutFlashValueEvent;
import javax.faces.event.PostRestoreStateEvent;
import javax.faces.event.PostValidateEvent;
import javax.faces.event.PreClearFlashEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.PreDestroyCustomScopeEvent;
import javax.faces.event.PreDestroyViewMapEvent;
import javax.faces.event.PreRemoveFlashValueEvent;
import javax.faces.event.PreRemoveFromViewEvent;
import javax.faces.event.PreRenderComponentEvent;
import javax.faces.event.PreRenderViewEvent;
import javax.faces.event.PreValidateEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;


/**
 * The Class ListenerRegistrationHelper.
 */
/*
 * This is a JSF ManagedBean, not CDI Bean
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class ListenerRegistrationHelper {

    /** The log. */
    private final BaseLogger log = BaseLoggerProducer.getLogger();

    /**
     * Initialize.
     */
    @PostConstruct
    private void initialize() {
        // PhaseListener
        final LifecycleFactory lcFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        final Lifecycle lifecycle = lcFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
        lifecycle.addPhaseListener(new DelegatingPhaseListener());
        for (final PhaseListener pl : lifecycle.getPhaseListeners()) {
            this.log.info("PhaseListener: " + pl.getClass().getName() + " loaded.");
        }

        // SystemEventListener
        final ApplicationFactory appFactory =
                (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        final Application application = appFactory.getApplication();

        final SystemEventListener delegatingListener = new DelegatingSystemEventListener();

        // SystemEvents
        // application
        // registration of PostConstructApplicationEvent is useless
        // because the event would have been fired already at this point
        // application.subscribeToEvent(PostConstructApplicationEvent.class, delegatingListener);
        application.subscribeToEvent(PreDestroyApplicationEvent.class, delegatingListener);

        // custom scope
        application.subscribeToEvent(PostConstructCustomScopeEvent.class, delegatingListener);
        application.subscribeToEvent(PreDestroyCustomScopeEvent.class, delegatingListener);

        // flash context
        application.subscribeToEvent(PostPutFlashValueEvent.class, delegatingListener);
        application.subscribeToEvent(PreRemoveFlashValueEvent.class, delegatingListener);
        application.subscribeToEvent(PreClearFlashEvent.class, delegatingListener);

        // exception handling
        application.subscribeToEvent(ExceptionQueuedEvent.class, delegatingListener);


        // ComponentSystemEvents
        // apply to UIViewRoot only
        application.subscribeToEvent(PostConstructViewMapEvent.class, delegatingListener);
        application.subscribeToEvent(PreDestroyViewMapEvent.class, delegatingListener);
        application.subscribeToEvent(PreRenderViewEvent.class, delegatingListener);

        // apply to any component
        // view
        application.subscribeToEvent(PostAddToViewEvent.class, delegatingListener);
        application.subscribeToEvent(PreRemoveFromViewEvent.class, delegatingListener);

        // state
        application.subscribeToEvent(PostRestoreStateEvent.class, delegatingListener);

        // validation
        application.subscribeToEvent(PreValidateEvent.class, delegatingListener);
        application.subscribeToEvent(PostValidateEvent.class, delegatingListener);

        // render
        application.subscribeToEvent(PreRenderComponentEvent.class, delegatingListener);
    }
}
