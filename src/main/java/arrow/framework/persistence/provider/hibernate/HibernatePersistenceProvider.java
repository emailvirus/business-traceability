package arrow.framework.persistence.provider.hibernate;

import javax.enterprise.inject.Vetoed;
import javax.persistence.EntityManager;

import org.hibernate.Session;

import arrow.framework.persistence.provider.BasePersistenceProvider;


/**
 * Support for non-standardized features of Hibernate, when used as the JPA persistence provider.
 *
 */

@Vetoed
public class HibernatePersistenceProvider extends BasePersistenceProvider {

    /**
     * Instantiates a new hibernate persistence provider.
     */
    public HibernatePersistenceProvider() {
        this.featureSet.add(Feature.WILDCARD_AS_COUNT_QUERY_SUBJECT);
    }

    /* (non-Javadoc)
     * @see arrow.framework.persistence.provider.BasePersistenceProvider<br />
     * #isCorrectProvider(javax.persistence.EntityManager)
     */
    @Override
    public boolean isCorrectProvider(final EntityManager em) {
        return em.getDelegate() instanceof Session;
    }

    /* (non-Javadoc)
     * @see arrow.framework.persistence.provider.BasePersistenceProvider#isDirty(javax.persistence.EntityManager)
     */
    @Override
    public boolean isDirty(final EntityManager entityManager) {
        return this.getSession(entityManager).isDirty();
    }

    /* (non-Javadoc)
     * @see arrow.framework.persistence.provider.BasePersistenceProvider<br />
     * #getId(java.lang.Object, javax.persistence.EntityManager)
     */
    @Override
    public Object getId(final Object bean, final EntityManager entityManager) {
        return this.getSession(entityManager).getIdentifier(bean);
    }

    /* (non-Javadoc)
     * @see arrow.framework.persistence.provider.BasePersistenceProvider#setTenantId(java.lang.Object)
     */
    @Override
    public void setTenantId(final Object tenantId) {
        ThreadLocalCurrentTenantIdentifierResolver.set(tenantId.toString());
    }

    /* (non-Javadoc)
     * @see arrow.framework.persistence.provider.BasePersistenceProvider#resetTenantId()
     */
    @Override
    public void resetTenantId() {
        ThreadLocalCurrentTenantIdentifierResolver.reset();
    }


    /**
     * Gets the session.
     *
     * @param entityManager the entity manager
     * @return the session
     */
    // helper
    private Session getSession(final EntityManager entityManager) {
        return (Session) entityManager.getDelegate();
    }
}
