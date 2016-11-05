package arrow.framework.persistence;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.enterprise.inject.Vetoed;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;
import arrow.framework.persistence.provider.BasePersistenceProvider;


/**
 * Implementation for BaseEntityManager.
 *
 */

@Vetoed
public class BaseEntityManagerImpl extends EntityManagerAbstractWrapper implements BaseEntityManager {

    /** The Constant LOG. */
    // log injection is not available
    private static final BaseLogger LOG = BaseLoggerProducer.getLogger();

    /**
     * Constructor with params.
     *
     * @param wrappedEm the wrapped em
     * @param provider the provider
     * @param qualifiers the qualifiers
     */
    public BaseEntityManagerImpl(final EntityManager wrappedEm, final BasePersistenceProvider provider,
            final Annotation... qualifiers) {
        this.wrappedEm = wrappedEm;
        this.qualifiers = qualifiers.clone();
        this.provider = provider;
    }

    /** The wrapped em. */
    private final EntityManager wrappedEm;

    /* (non-Javadoc)
     * @see arrow.framework.persistence.EntityManagerAbstractWrapper#getWrapped()
     */
    @Override
    protected EntityManager getWrapped() {
        return this.wrappedEm;
    }

    /** The qualifiers. */
    private final Annotation[] qualifiers;

    /* (non-Javadoc)
     * @see arrow.framework.persistence.BaseEntityManager#getQualifiers()
     */
    @Override
    public Annotation[] getQualifiers() {
        return this.qualifiers;
    }


    /** The provider. */
    private final BasePersistenceProvider provider;

    /* (non-Javadoc)
     * @see arrow.framework.persistence.BaseEntityManager#getProvider()
     */
    @Override
    public BasePersistenceProvider getProvider() {
        return this.provider;
    }


    // Override some method implementation of EntityManager interface to add additional checking and
    // debug logging

    /* (non-Javadoc)
     * @see arrow.framework.persistence.EntityManagerAbstractWrapper#close()
     */
    @Override
    public void close() {
        boolean isOpen = true;
        try {
            isOpen = this.getWrapped().isOpen();
        } catch (final Exception e) {
            BaseEntityManagerImpl.LOG.warn(e.getMessage());
        }

        if (isOpen) {
            BaseEntityManagerImpl.LOG.debug("Closing SynEntityManager " + this);
            this.getWrapped().close();
        }
    }


    /* (non-Javadoc)
     * @see arrow.framework.persistence.BaseEntityManager#findAll(java.lang.Class)
     */
    @Override
    public <E extends BaseEntity> List<E> findAll(final Class<E> clazz) {
        final CriteriaBuilder cb = this.getCriteriaBuilder();
        final CriteriaQuery<E> criteria = cb.createQuery(clazz);
        final Root<E> root = criteria.from(clazz);
        criteria.select(root);
        return this.createQuery(criteria).getResultList();
    }

    /* (non-Javadoc)
     * @see arrow.framework.persistence.BaseEntityManager#findAndRefresh(arrow.framework.persistence.BaseEntity)
     */
    @Override
    public <E extends BaseEntity> E findAndRefresh(final E entity) {
        if (entity == null) {
            return null;
        }

        if (this.contains(entity)) {
            this.refresh(entity);
            return entity;
        }


        @SuppressWarnings("unchecked")
        final E foundEntity = this.find((Class<E>) entity.getEntityClass(), entity.getPk());
        if (foundEntity != null) {
            this.refresh(foundEntity);
        }

        return foundEntity;
    }


    /* (non-Javadoc)
     * @see arrow.framework.persistence.BaseEntityManager<br />
     * #getResultListForQuery(javax.persistence.criteria.CriteriaQuery)
     */
    @Override
    public <E extends BaseEntity> List<E> getResultListForQuery(final CriteriaQuery<E> query) {
        return this.createQuery(query).getResultList();
    }

    /* (non-Javadoc)
     * @see arrow.framework.persistence.BaseEntityManager<br />
     * #getResultListObjectForQuery(javax.persistence.criteria.CriteriaQuery)
     */
    @Override
    public <E extends Object> List<E> getResultListObjectForQuery(final CriteriaQuery<E> query) {
        return this.createQuery(query).getResultList();
    }

    /* (non-Javadoc)
     * @see arrow.framework.persistence.BaseEntityManager<br />
     * #getSingleResultForQuery(javax.persistence.criteria.CriteriaQuery)
     */
    @Override
    public <E extends BaseEntity> E getSingleResultForQuery(final CriteriaQuery<E> query) {
        return this.createQuery(query).getSingleResult();
    }

    /* (non-Javadoc)
     * @see arrow.framework.persistence.BaseEntityManager#getSingleRowForQuery(javax.persistence.criteria.CriteriaQuery)
     */
    @Override
    public <E extends Object> E getSingleRowForQuery(final CriteriaQuery<E> query) {
        return this.createQuery(query).getSingleResult();
    }

}
