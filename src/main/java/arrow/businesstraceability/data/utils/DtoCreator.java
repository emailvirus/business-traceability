package arrow.businesstraceability.data.utils;

import java.lang.reflect.InvocationTargetException;

import arrow.businesstraceability.persistence.entity.Abstract_entity;
import arrow.framework.faces.util.BeanCopier;


/**
 * The Class DtoCreator.
 *
 * @param <T> the generic type
 * @param <K> the key type
 */
public class DtoCreator<T extends Abstract_entity, K> {
    
    /** The entity object. */
    private final T entityObject;

    private final K dtoObject;

    /**
     * Instantiates a new dto creator.
     *
     * @param entityObject the entity object
     * @param dtoObject the dto object
     */
    public DtoCreator(final T entityObject, final K dtoObject) {
        this.entityObject = entityObject;
        this.dtoObject = dtoObject;
    }

    /**
     * Gets the entity.
     *
     * @param dtoObject the dto object
     * @return the entity
     * @throws IllegalAccessException the illegal access exception
     * @throws InvocationTargetException the invocation target exception
     */
    public T getEntity(final K dtoObject) throws IllegalAccessException, InvocationTargetException {
        BeanCopier.copyWithPk(dtoObject, this.entityObject);
        // BeanUtils.copyProperties(this.entityObject, dtoObject);
        // currently PK and Foreign Key hasn't been copied.

        return this.entityObject;

    }

    /**
     * Gets the dto.
     *
     * @param entityObject the entity object
     * @return the dto
     */
    public K getDto(final T entityObject) {
        BeanCopier.copyWithPk(entityObject, this.dtoObject);
        return this.dtoObject;
    }

}
