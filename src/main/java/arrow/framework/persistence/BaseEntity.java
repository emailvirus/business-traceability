package arrow.framework.persistence;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import arrow.framework.persistence.event.qualifier.EntityPersisted;
import arrow.framework.persistence.event.qualifier.EntityPersistedOrRemoved;
import arrow.framework.persistence.event.qualifier.EntityPersistedOrUpdated;
import arrow.framework.persistence.event.qualifier.EntityPersistedOrUpdatedOrRemoved;
import arrow.framework.persistence.event.qualifier.EntityRemoved;
import arrow.framework.persistence.event.qualifier.EntityUpdated;
import arrow.framework.util.cdi.CDIUtils;

/**
 * The Class BaseEntity.
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    /**
     * Gets the entity class.
     *
     * @return the entity class
     */

    // to return the original Entity class, instead of the proxied class, if the entity is proxied.
    public abstract Class<? extends BaseEntity> getEntityClass();

    /**
     * Gets the pk.
     *
     * @return the pk
     */
    public abstract BasePk getPk();

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.getPk() != null ? this.getPk().hashCode() : 0;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object) {
        if ((object == null) || (object.getClass() != this.getClass())) {
            return false;
        }

        final BaseEntity other = (BaseEntity) object;
        return (this.getPk() == null) ? other.getPk() == null : this.getPk().equals(other.getPk());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.getClass().getName() + "[PK: " + ((this.getPk() != null) ? this.getPk().toString() : "null") + "]";
    }

    /**
     * Post persist.
     */
    @PostPersist
    public void postPersist() {
        CDIUtils.getBeanManager().fireEvent(this, new EntityPersistedOrUpdatedOrRemoved.Literal(this.getEntityClass()),
                new EntityPersisted.Literal(this.getEntityClass()),
                new EntityPersistedOrRemoved.Literal(this.getEntityClass()),
                new EntityPersistedOrUpdated.Literal(this.getEntityClass()));
    }

    /**
     * Post update.
     */
    @PostUpdate
    public void postUpdate() {
        CDIUtils.getBeanManager().fireEvent(this, new EntityPersistedOrUpdatedOrRemoved.Literal(this.getEntityClass()),
                new EntityUpdated.Literal(this.getEntityClass()),
                new EntityPersistedOrUpdated.Literal(this.getEntityClass()));
    }

    /**
     * Post remove.
     */
    @PostRemove
    public void postRemove() {
        CDIUtils.getBeanManager().fireEvent(this, new EntityPersistedOrUpdatedOrRemoved.Literal(this.getEntityClass()),
                new EntityRemoved.Literal(this.getEntityClass()),
                new EntityPersistedOrRemoved.Literal(this.getEntityClass()));
    }

}
