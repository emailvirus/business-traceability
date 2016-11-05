package arrow.framework.persistence;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.Vetoed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TransactionRequiredException;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;
import arrow.framework.persistence.event.qualifier.BaseEntityManagerCreated;
import arrow.framework.persistence.event.qualifier.BaseEntityManagerDisposed;
import arrow.framework.persistence.provider.BasePersistenceProvider;
import arrow.framework.util.AssertUtils;
import arrow.framework.util.cdi.CDIUtils;


/**
 * A factory for creating BaseEntityManager objects.
 */
@Vetoed
public class BaseEntityManagerFactory {

    /** The Constant LOGGER. */
    // log injection is not available
    private static final BaseLogger LOGGER = BaseLoggerProducer.getLogger();

    // maintain a registry of all BaseEntityManagers created by this Factory, so that they can join
    /** The current base entity managers. */
    // transaction later.
    private static Set<BaseEntityManager> currentBaseEntityManagers = new HashSet<BaseEntityManager>();

    /**
     * Gets the current base entity managers.
     *
     * @return the current base entity managers
     */
    public static Set<BaseEntityManager> getCurrentBaseEntityManagers() {
        return Collections.unmodifiableSet(BaseEntityManagerFactory.currentBaseEntityManagers);
    }

    /**
     * Create base entity manager.
     *
     * @param emf the emf
     * @param qualifiers the qualifiers
     * @return the base entity manager
     */
    public static BaseEntityManager createBaseEntityManager(final EntityManagerFactory emf,
            final Annotation... qualifiers) {
        try {
            final EntityManager entityManager = emf.createEntityManager();
            final BasePersistenceProvider provider = BasePersistenceProvider.ACTIVE_PERSISTENCE_PROVIDER;

            AssertUtils.assertTrueWithErrorMessage(provider.isCorrectProvider(entityManager),
                    "Invalid BasePersistenceProvider for EntityManager");

            final BaseEntityManager baseEngityManager = new BaseEntityManagerImpl(entityManager, provider, qualifiers);

            BaseEntityManagerFactory.LOGGER.debug("BaseEntityManager bean created: " + baseEngityManager);

            // Fire event to allow observers to perform additional initialization
            // and/or configuration of the newly created entity manager.
            final Annotation[] eventQualifiers = Arrays.copyOf(qualifiers, qualifiers.length + 1);
            eventQualifiers[qualifiers.length] = BaseEntityManagerCreated.LITERAL;
            CDIUtils.getBeanManager().fireEvent(baseEngityManager, eventQualifiers);

            // Join global JTA UserTransaction if one is currently active:
            if (!baseEngityManager.isJoinedToTransaction()) {
                try {
                    baseEngityManager.joinTransaction();
                    BaseEntityManagerFactory.LOGGER.debugf(
                            "Newly created BaseEntityManager {0} joined currently active JTA UserTransaction",
                            baseEngityManager);
                } catch (final TransactionRequiredException e) {
                    BaseEntityManagerFactory.LOGGER.warnf(
                            "Global JTA UserTransaction not active when this EntityManager is created: {0}",
                            baseEngityManager);
                }
            }


            BaseEntityManagerFactory.currentBaseEntityManagers.add(baseEngityManager);
            return baseEngityManager;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * to be called by @Dispose method.
     *
     * @param em the em
     */
    public static void disposeBaseEntityManager(final BaseEntityManager em) {
        em.close();

        // Fire event to allow observers to perform additional cleanup
        // regarding the disposed entity manager
        final Annotation[] qualifiers = em.getQualifiers();
        final Annotation[] eventQualifiers = Arrays.copyOf(qualifiers, qualifiers.length + 1);
        eventQualifiers[qualifiers.length] = BaseEntityManagerDisposed.LITERAL;
        CDIUtils.getBeanManager().fireEvent(em, eventQualifiers);

        BaseEntityManagerFactory.currentBaseEntityManagers.remove(em);
        BaseEntityManagerFactory.LOGGER.debug("BaseEntityManager closed by @Dispose method");
    }
}
