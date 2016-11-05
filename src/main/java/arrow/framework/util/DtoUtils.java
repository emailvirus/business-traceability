package arrow.framework.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;
import arrow.framework.persistence.BaseEntity;

/**
 * The Class DtoUtils.
 */
public class DtoUtils {

    private static final BaseLogger LOGGER = BaseLoggerProducer.getLogger(DtoUtils.class);

    /**
     * Creates the dto.
     *
     * @param <T> the generic type
     * @param <E> the element type
     * @param sourceEntity the source entity
     * @param dtoClass the dto class
     * @return the t
     */
    public static <T, E extends BaseEntity> T createDto(final E sourceEntity, final Class<T> dtoClass) {
        try {
            final T test = dtoClass.newInstance();
            BeanUtils.copyProperties(test, sourceEntity);
            return test;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException iae) {
            DtoUtils.LOGGER.debug("Failed when copy data", iae);
        }
        return null;
    }
}
