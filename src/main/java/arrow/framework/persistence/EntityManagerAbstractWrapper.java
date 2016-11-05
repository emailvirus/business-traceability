package arrow.framework.persistence;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;



/**
 * Pure wrapper that does absolutely nothing except delegating method calls to wrapped object DO NOT MODIFY THIS CLASS.
 * To add functionalities to SynEntityManager, update SynEntityManager interface and modify SynEntityManagerImpl
 *
 * @author Hugh Nguyen
 */
@SuppressWarnings("rawtypes")
public abstract class EntityManagerAbstractWrapper implements EntityManager {

    /**
     * Gets the wrapped.
     *
     * @return the wrapped
     */
    protected abstract EntityManager getWrapped();

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#persist(java.lang.Object)
     */
    @Override
    public void persist(final Object entity) {
        this.getWrapped().persist(entity);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#merge(java.lang.Object)
     */
    @Override
    public <T> T merge(final T entity) {
        return this.getWrapped().merge(entity);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#remove(java.lang.Object)
     */
    @Override
    public void remove(final Object entity) {
        this.getWrapped().remove(entity);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#find(java.lang.Class, java.lang.Object)
     */
    @Override
    public <T> T find(final Class<T> entityClass, final Object primaryKey) {
        return this.getWrapped().find(entityClass, primaryKey);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#find(java.lang.Class, java.lang.Object, java.util.Map)
     */
    @Override
    public <T> T find(final Class<T> entityClass, final Object primaryKey, final Map<String, Object> properties) {
        return this.getWrapped().find(entityClass, primaryKey, properties);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#find(java.lang.Class, java.lang.Object, javax.persistence.LockModeType)
     */
    @Override
    public <T> T find(final Class<T> entityClass, final Object primaryKey, final LockModeType lockMode) {
        return this.getWrapped().find(entityClass, primaryKey, lockMode);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager<br />
     * #find(java.lang.Class, java.lang.Object, javax.persistence.LockModeType, java.util.Map)
     */
    @Override
    public <T> T find(final Class<T> entityClass, final Object primaryKey, final LockModeType lockMode,
            final Map<String, Object> properties) {
        return this.getWrapped().find(entityClass, primaryKey, lockMode, properties);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#getReference(java.lang.Class, java.lang.Object)
     */
    @Override
    public <T> T getReference(final Class<T> entityClass, final Object primaryKey) {
        return this.getWrapped().getReference(entityClass, primaryKey);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#flush()
     */
    @Override
    public void flush() {
        this.getWrapped().flush();

    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#setFlushMode(javax.persistence.FlushModeType)
     */
    @Override
    public void setFlushMode(final FlushModeType flushMode) {
        this.getWrapped().setFlushMode(flushMode);

    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#getFlushMode()
     */
    @Override
    public FlushModeType getFlushMode() {
        return this.getWrapped().getFlushMode();
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#lock(java.lang.Object, javax.persistence.LockModeType)
     */
    @Override
    public void lock(final Object entity, final LockModeType lockMode) {
        this.getWrapped().lock(entity, lockMode);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#lock(java.lang.Object, javax.persistence.LockModeType, java.util.Map)
     */
    @Override
    public void lock(final Object entity, final LockModeType lockMode, final Map<String, Object> properties) {
        this.getWrapped().lock(entity, lockMode, properties);

    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#refresh(java.lang.Object)
     */
    @Override
    public void refresh(final Object entity) {
        this.getWrapped().refresh(entity);

    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#refresh(java.lang.Object, java.util.Map)
     */
    @Override
    public void refresh(final Object entity, final Map<String, Object> properties) {
        this.getWrapped().refresh(entity, properties);

    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#refresh(java.lang.Object, javax.persistence.LockModeType)
     */
    @Override
    public void refresh(final Object entity, final LockModeType lockMode) {
        this.getWrapped().refresh(entity, lockMode);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#refresh(java.lang.Object, javax.persistence.LockModeType, java.util.Map)
     */
    @Override
    public void refresh(final Object entity, final LockModeType lockMode, final Map<String, Object> properties) {
        this.getWrapped().refresh(entity, lockMode, properties);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#clear()
     */
    @Override
    public void clear() {
        this.getWrapped().clear();
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#detach(java.lang.Object)
     */
    @Override
    public void detach(final Object entity) {
        this.getWrapped().detach(entity);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#contains(java.lang.Object)
     */
    @Override
    public boolean contains(final Object entity) {
        return this.getWrapped().contains(entity);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#getLockMode(java.lang.Object)
     */
    @Override
    public LockModeType getLockMode(final Object entity) {
        return this.getWrapped().getLockMode(entity);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#setProperty(java.lang.String, java.lang.Object)
     */
    @Override
    public void setProperty(final String propertyName, final Object value) {
        this.getWrapped().setProperty(propertyName, value);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#getProperties()
     */
    @Override
    public Map<String, Object> getProperties() {
        return this.getWrapped().getProperties();
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createQuery(java.lang.String)
     */
    @Override
    public Query createQuery(final String qlString) {
        return this.getWrapped().createQuery(qlString);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createQuery(javax.persistence.criteria.CriteriaQuery)
     */
    @Override
    public <T> TypedQuery<T> createQuery(final CriteriaQuery<T> criteriaQuery) {
        return this.getWrapped().createQuery(criteriaQuery);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createQuery(java.lang.String, java.lang.Class)
     */
    @Override
    public <T> TypedQuery<T> createQuery(final String qlString, final Class<T> resultClass) {
        return this.getWrapped().createQuery(qlString, resultClass);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createNamedQuery(java.lang.String)
     */
    @Override
    public Query createNamedQuery(final String name) {
        return this.getWrapped().createNamedQuery(name);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createNamedQuery(java.lang.String, java.lang.Class)
     */
    @Override
    public <T> TypedQuery<T> createNamedQuery(final String name, final Class<T> resultClass) {
        return this.getWrapped().createNamedQuery(name, resultClass);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String)
     */
    @Override
    public Query createNativeQuery(final String sqlString) {
        return this.getWrapped().createNativeQuery(sqlString);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String, java.lang.Class)
     */
    @Override
    public Query createNativeQuery(final String sqlString, final Class resultClass) {
        return this.getWrapped().createNativeQuery(sqlString, resultClass);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createNativeQuery(java.lang.String, java.lang.String)
     */
    @Override
    public Query createNativeQuery(final String sqlString, final String resultSetMapping) {
        return this.getWrapped().createNativeQuery(sqlString, resultSetMapping);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#joinTransaction()
     */
    @Override
    public void joinTransaction() {
        this.getWrapped().joinTransaction();
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#unwrap(java.lang.Class)
     */
    @Override
    public <T> T unwrap(final Class<T> cls) {
        return this.getWrapped().unwrap(cls);
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#getDelegate()
     */
    @Override
    public Object getDelegate() {
        return this.getWrapped().getDelegate();
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#close()
     */
    @Override
    public void close() {
        this.getWrapped().close();
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#isOpen()
     */
    @Override
    public boolean isOpen() {
        return this.getWrapped().isOpen();
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#getTransaction()
     */
    @Override
    public EntityTransaction getTransaction() {
        return this.getWrapped().getTransaction();
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#getEntityManagerFactory()
     */
    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return this.getWrapped().getEntityManagerFactory();
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#getCriteriaBuilder()
     */
    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return this.getWrapped().getCriteriaBuilder();
    }

    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#getMetamodel()
     */
    @Override
    public Metamodel getMetamodel() {
        return this.getWrapped().getMetamodel();
    }


    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createQuery(javax.persistence.criteria.CriteriaUpdate)
     */
    @Override
    public Query createQuery(final CriteriaUpdate updateQuery) {
        return this.getWrapped().createQuery(updateQuery);
    }


    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createQuery(javax.persistence.criteria.CriteriaDelete)
     */
    @Override
    public Query createQuery(final CriteriaDelete deleteQuery) {
        return this.getWrapped().createQuery(deleteQuery);
    }


    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createNamedStoredProcedureQuery(java.lang.String)
     */
    @Override
    public StoredProcedureQuery createNamedStoredProcedureQuery(final String name) {
        return this.getWrapped().createNamedStoredProcedureQuery(name);
    }


    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createStoredProcedureQuery(java.lang.String)
     */
    @Override
    public StoredProcedureQuery createStoredProcedureQuery(final String procedureName) {
        return this.getWrapped().createStoredProcedureQuery(procedureName);
    }


    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createStoredProcedureQuery(java.lang.String, java.lang.Class[])
     */
    @Override
    public StoredProcedureQuery createStoredProcedureQuery(final String procedureName, final Class... resultClasses) {
        return this.getWrapped().createStoredProcedureQuery(procedureName, resultClasses);
    }


    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createStoredProcedureQuery(java.lang.String, java.lang.String[])
     */
    @Override
    public StoredProcedureQuery createStoredProcedureQuery(final String procedureName,
            final String... resultSetMappings) {
        return this.getWrapped().createStoredProcedureQuery(procedureName, resultSetMappings);
    }


    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#isJoinedToTransaction()
     */
    @Override
    public boolean isJoinedToTransaction() {
        return this.getWrapped().isJoinedToTransaction();
    }


    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createEntityGraph(java.lang.Class)
     */
    @Override
    public <T> EntityGraph<T> createEntityGraph(final Class<T> rootType) {
        return this.getWrapped().createEntityGraph(rootType);
    }


    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#createEntityGraph(java.lang.String)
     */
    @Override
    public EntityGraph<?> createEntityGraph(final String graphName) {
        return this.getWrapped().createEntityGraph(graphName);
    }


    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#getEntityGraph(java.lang.String)
     */
    @Override
    public EntityGraph<?> getEntityGraph(final String graphName) {
        return this.getWrapped().getEntityGraph(graphName);
    }


    /* (non-Javadoc)
     * @see javax.persistence.EntityManager#getEntityGraphs(java.lang.Class)
     */
    @Override
    public <T> List<EntityGraph<? super T>> getEntityGraphs(final Class<T> entityClass) {
        return this.getWrapped().getEntityGraphs(entityClass);
    }
}
