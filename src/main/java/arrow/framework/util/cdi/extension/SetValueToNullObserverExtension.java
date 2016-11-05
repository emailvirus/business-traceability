package arrow.framework.util.cdi.extension;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ObserverMethod;
import javax.enterprise.inject.spi.ProcessManagedBean;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;


/**
 * The Class SetValueToNullObserverExtension.
 */
@SuppressWarnings(value = "DP_DO_INSIDE_DO_PRIVILEGED",
        justification = "Have to ignore because we don't clearly understand")
public class SetValueToNullObserverExtension implements Extension {

    /** The Constant log. */
    // log injection is not available
    private static final BaseLogger LOG = BaseLoggerProducer.getLogger();

    /** The observer methods. */
    private final Set<ObserverMethod<?>> observerMethods = new HashSet<ObserverMethod<?>>();


    /**
     * loops through the fields on a ManagedBean looking for @SetFieldToNullIfObserved declarations.
     *
     * @param <T> the generic type
     * @param event the event
     * @param manager the manager
     */
    public <T> void processManagedBean(@Observes final ProcessManagedBean<T> event, final BeanManager manager) {
        final AnnotatedType<T> annotatedType = event.getAnnotatedBeanClass();
        final Bean<T> bean = event.getBean();


        // process @SetFieldToNullIfObserved FIELDs
        for (final AnnotatedField<? super T> field : annotatedType.getFields()) {
            final Field javaField = field.getJavaMember();
            javaField.setAccessible(true);

            if (field.isAnnotationPresent(SetValueToNullIfObserved.class)) {
                final SetValueToNullIfObserved setValueToNullIfObsservedAnnotation =
                        field.getAnnotation(SetValueToNullIfObserved.class);
                final Class<?> eventClass = setValueToNullIfObsservedAnnotation.eventClass();

                final Set<Annotation> qualifiers = new HashSet<Annotation>();
                for (final Annotation annotation : field.getAnnotations()) {
                    if (manager.isQualifier(annotation.annotationType())) {
                        qualifiers.add(annotation);
                    }
                }

                final ObserverMethod<?> m = new ObserverMethod<Object>() {
                    @Override
                    public Class<?> getBeanClass() {
                        return annotatedType.getJavaClass();
                    }

                    @Override
                    public Type getObservedType() {
                        return eventClass;
                    }

                    @Override
                    public Set<Annotation> getObservedQualifiers() {
                        return qualifiers;
                    }

                    @Override
                    public Reception getReception() {
                        return Reception.IF_EXISTS;
                    }

                    @Override
                    public TransactionPhase getTransactionPhase() {
                        return TransactionPhase.IN_PROGRESS;
                    }

                    @Override
                    public void notify(final Object event) {
                        try {
                            final Object notifiedBean = manager.getContext(bean.getScope()).get(bean);

                            if (notifiedBean != null) {
                                javaField.set(notifiedBean, null);
                            }

                            SetValueToNullObserverExtension.LOG.debug("Event " + event + " observed by " + field);
                        } catch (final IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public String toString() {
                        return setValueToNullIfObsservedAnnotation.toString() + " on field " + field.toString();
                    }
                };

                this.observerMethods.add(m);
            }

        }
    }

    /**
     * After bean discovery.
     *
     * @param event the event
     */
    public void afterBeanDiscovery(@Observes final AfterBeanDiscovery event) {
        // register observer methods
        for (final ObserverMethod<?> m : this.observerMethods) {
            event.addObserverMethod(m);
            SetValueToNullObserverExtension.LOG.debug("Registered observer method: " + m);
        }
    }
}
