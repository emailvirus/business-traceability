package arrow.framework.logging;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;

import org.apache.deltaspike.core.util.ReflectionUtils;

/**
 * The Class BaseLoggerProducer.
 */
public class BaseLoggerProducer {

    /**
     * IMPORTANT NOTE: This method automatically find the correct logger name without providing the class. It involves
     * SLOW OPERATION of accessing the stack trace, so it should only be called from static initialization, and only
     * when injection of logger is not available
     *
     * @return the logger
     */
    public static BaseLogger getLogger() {
        final String className = Thread.currentThread().getStackTrace()[2].getClassName();

        return BaseLoggerProducer.getLogger(className);
    }

    /**
     * Gets the logger.
     *
     * @param clazz the clazz
     * @return the logger
     */
    public static BaseLogger getLogger(final Class<?> clazz) {
        return BaseLoggerProducer.getLogger(clazz.getName());
    }

    // When use with a different AS, implement the adaptor for the AS's provided logger,
    /**
     * Gets the logger.
     *
     * @param name the name
     * @return the logger
     */
    // and update this method to call that adaptor
    public static BaseLogger getLogger(final String name) {
        return JBossLoggingAdaptor.getLogger(name);
    }

    /**
     * Produce log.
     *
     * @param injectionPoint the injection point
     * @return the base logger
     */
    @Produces
    public BaseLogger produceLog(final InjectionPoint injectionPoint) {
        final Annotated annotated = injectionPoint.getAnnotated();
        if (annotated.isAnnotationPresent(Category.class)) {
            return BaseLoggerProducer.getLogger(annotated.getAnnotation(Category.class).value());
        }
        else {
            final Bean<?> bean = injectionPoint.getBean();
            final Class<?> type = ReflectionUtils
                    .getRawType(bean != null ? bean.getBeanClass() : injectionPoint.getMember().getDeclaringClass());
            return BaseLoggerProducer.getLogger(type);
        }
    }
}
