package arrow.framework.persistence;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import arrow.framework.persistence.provider.BasePersistenceProvider;


/**
 * Additional operations for BaseEntityManager The operations should be implemented in BaseEntityManagerImpl.
 */
public interface BaseEntityManager extends EntityManager, Serializable {

    /**
     * Gets the qualifiers.
     *
     * @return the persistence contexts qualifiers
     */
    Annotation[] getQualifiers();

    /**
     * Returns the appropriate {@link BasePersistenceProvider} implementation for this persistence context.
     *
     * @return the provider
     */
    BasePersistenceProvider getProvider();

    /**
     * Find all.
     *
     * @param <E> the element type
     * @param clazz the clazz
     * @return the list
     */
    // additional business methods
    <E extends BaseEntity> List<E> findAll(final Class<E> clazz);

    /**
     * Find and refresh.
     *
     * @param <E> the element type
     * @param entity the entity
     * @return the e
     */
    <E extends BaseEntity> E findAndRefresh(final E entity);

    /**
     * Gets the result list for query.
     *
     * @param <E> the element type
     * @param query the query
     * @return the result list for query
     */
    <E extends BaseEntity> List<E> getResultListForQuery(CriteriaQuery<E> query);

    /**
     * Gets the result list object for query.
     *
     * @param <E> the element type
     * @param query the query
     * @return the result list object for query
     */
    <E extends Object> List<E> getResultListObjectForQuery(final CriteriaQuery<E> query);

    /**
     * Gets the single result for query.
     *
     * @param <E> the element type
     * @param query the query
     * @return the single result for query
     */
    <E extends BaseEntity> E getSingleResultForQuery(final CriteriaQuery<E> query);

    /**
     * Gets the single row for query.
     *
     * @param <E> the element type
     * @param query the query
     * @return the single row for query
     */
    <E extends Object> E getSingleRowForQuery(final CriteriaQuery<E> query);

}
