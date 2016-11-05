package arrow.framework.persistence;

import java.io.Serializable;

import javax.enterprise.inject.Vetoed;

import org.apache.deltaspike.data.api.AbstractEntityRepository;

import arrow.businesstraceability.persistence.entity.Abstract_entity;


@Vetoed
public abstract class Abstract_entityRepositoryWrapper<E extends Abstract_entity, P extends Serializable>
        extends AbstractEntityRepository<E, P> implements Serializable {

    /**
     * Find and refresh.
     *
     * @param com the com
     * @return the e
     */
    @SuppressWarnings("unchecked")
    public E findAndRefresh(final E com) {
        E foundEntity = this.findBy((P) com.getPk());
        this.refresh(foundEntity);
        return foundEntity;
    }
}
