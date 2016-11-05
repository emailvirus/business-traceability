package arrow.framework.persistence.provider;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import arrow.framework.persistence.provider.hibernate.HibernatePersistenceProvider;


/**
 * The purpose of this abstract classes and its implementations is to isolate the dependencies to a concrete JPA
 * implementations as far as possible.
 */
public abstract class BasePersistenceProvider {

    /** The Constant ACTIVE_PERSISTENCE_PROVIDER. */
    public static final BasePersistenceProvider ACTIVE_PERSISTENCE_PROVIDER = new HibernatePersistenceProvider();


    /**
     * The Enum Feature.
     */
    public enum Feature {
        /**
         * Identifies whether this JPA provider supports using a wildcard as the subject of a count query.
         * <p/>
         * <p>
         * Here's a count query that uses a wildcard as the subject.
         * </p>
         * <p/>
         *
         * <pre>
         * select count(*) from Vehicle v
         * </pre>
         * <p>
         * Per the JPA 1.0 spec, using a wildcard as a subject of a count query is not permitted. Instead, the subject
         * must be the entity or the alias, as in this count query:
         * </p>
         * <p/>
         *
         * <pre>
         * select count(v) from Vehicle v
         * </pre>
         * <p>
         * Hibernate supports the wildcard syntax as an vendor extension. Furthermore, Hibernate produces an invalid SQL
         * query when using the compliant subject if the entity has a composite primary key. Therefore, we prefer to use
         * the wildcard syntax if it is supported.
         * </p>
         */
        WILDCARD_AS_COUNT_QUERY_SUBJECT
    }

    /** The feature set. */
    protected Set<Feature> featureSet = new HashSet<Feature>();

    /**
     * Indicate whether this JPA provider supports the feature defined by the provided Feature enum value.
     *
     * @param feature the feature
     * @return true, if successful
     */
    public boolean supportsFeature(final Feature feature) {
        return this.featureSet.contains(feature);
    }

    /**
     * Should return true if this is the correct persistence provider for the given entity manager factory.
     *
     * @param em the em
     * @return true, if is correct provider
     */
    public abstract boolean isCorrectProvider(EntityManager em);


    /**
     * Checks if is dirty.
     *
     * @param entityManager the entity manager
     * @return true to indicate that there are unflushed changes
     */
    public abstract boolean isDirty(EntityManager entityManager);

    /**
     * Get the value of the entity identifier attribute.
     *
     * @param bean a managed entity instance
     * @param entityManager the entity manager
     * @return the id
     */
    public abstract Object getId(Object bean, EntityManager entityManager);


    /**
     * Sets the tenant id.
     *
     * @param tenantId the new tenant id
     */
    // Support for multi-tenancy feature
    public abstract void setTenantId(Object tenantId);

    /**
     * Reset tenant id.
     */
    public abstract void resetTenantId();
}
