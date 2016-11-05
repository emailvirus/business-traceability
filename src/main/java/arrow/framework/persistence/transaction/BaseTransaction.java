package arrow.framework.persistence.transaction;

import javax.enterprise.inject.Vetoed;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;
import arrow.framework.persistence.BaseEntityManager;
import arrow.framework.persistence.BaseEntityManagerFactory;



/**
 * Static methods for controlling transaction via TransactionManager.
 *
 * @author Hugh Nguyen
 */
@Vetoed
public abstract class BaseTransaction {

    /** The Constant LOGGER. */
    private static final BaseLogger LOGGER = BaseLoggerProducer.getLogger();

    /** The Constant JNDI_LOCATIONS. */
    private static final String[] JNDI_LOCATIONS =
        {"java:jboss/TransactionManager", "java:/TransactionManager", "java:appserver/TransactionManager",
        "java:comp/TransactionManager"};

    /** The transaction manager. */
    private static transient javax.transaction.TransactionManager transactionManager;

    /** The my lock. */
    private static volatile Object myLock = new Object(); // must be declared volatile

    /** The found jndi location. */
    private static volatile String foundJndiLocation;

    /**
     * Gets the transaction manager.
     *
     * @return the transaction manager
     */
    public static TransactionManager getTransactionManager() {
        synchronized (BaseTransaction.myLock) {
            if (BaseTransaction.transactionManager == null) {
                if (BaseTransaction.foundJndiLocation != null) {
                    try {
                        BaseTransaction.transactionManager =
                                (TransactionManager) new InitialContext().lookup(BaseTransaction.foundJndiLocation);
                        return BaseTransaction.transactionManager;
                    } catch (final NamingException e) {
                        BaseTransaction.LOGGER.trace("Could not find transaction manager under"
                                + BaseTransaction.foundJndiLocation);
                    }
                }


                for (final String location : BaseTransaction.JNDI_LOCATIONS) {
                    try {
                        BaseTransaction.transactionManager = (TransactionManager) new InitialContext().lookup(location);
                        BaseTransaction.foundJndiLocation = location;
                        return BaseTransaction.transactionManager;
                    } catch (final NamingException e) {
                        BaseTransaction.LOGGER.trace("Could not find transaction manager under" + location);
                    }
                }
                throw new RuntimeException("Could not find TransactionManager in JNDI");
            }

        }

        return BaseTransaction.transactionManager;
    }


    /**
     * Begin.
     *
     * @throws NotSupportedException the not supported exception
     * @throws SystemException the system exception
     */
    // transaction control methods
    public static void begin() throws NotSupportedException, SystemException {
        BaseTransaction.getTransactionManager().begin();

        // whenever a transaction begin(), the active EntityManagers that want
        // to participate in the global UserTransaction must call joinTransaction()

        // if conversation scope is active, enlist all SynEntityManager in the conversation
        // if (CDIUtils.isConversationContextActive()) {
        // for (final BaseEntityManager em :
        // Instance.get(ConversationScopedEntityManagerRegistry.class).getSynEntityManagers()) {
        // BaseTransaction.LOGGER.debug("Enlisting SynEntityManager " + em +
        // " to global JTA UserTransaction as it begins.");
        // em.joinTransaction();
        // }
        // }

        // if conversation scope is NOT active, then entity manager must be created after the
        // transaction start,
        // and then join the transaction. This is done by SynEntityManagerFactory

        BaseTransaction.LOGGER.debug("JTA transaction begun");
    }


    /**
     * Commit.
     *
     * @throws RollbackException the rollback exception
     * @throws HeuristicMixedException the heuristic mixed exception
     * @throws HeuristicRollbackException the heuristic rollback exception
     * @throws SecurityException the security exception
     * @throws IllegalStateException the illegal state exception
     * @throws SystemException the system exception
     */
    public static void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException,
    SecurityException, IllegalStateException, SystemException {
        BaseTransaction.getTransactionManager().commit();
        BaseTransaction.LOGGER.debug("JTA transaction committed");

    }

    /**
     * Rollback.
     *
     * @throws IllegalStateException the illegal state exception
     * @throws SecurityException the security exception
     * @throws SystemException the system exception
     */
    public static void rollback() throws IllegalStateException, SecurityException, SystemException {
        BaseTransaction.getTransactionManager().rollback();
        BaseTransaction.LOGGER.debug("JTA transaction rolled back");
    }


    /**
     * Cleanup.
     *
     * @throws SystemException the system exception
     */
    public static void cleanup() throws SystemException {
        if (BaseTransaction.isRolledBackOrCommitted()) {
            BaseTransaction.LOGGER.warn("Transaction status is " + BaseTransaction.getStatusString()
                    + ". Cleaning up by disassociating it from the current thread.");
            BaseTransaction.getTransactionManager().suspend();

            for (final BaseEntityManager em : BaseEntityManagerFactory.getCurrentBaseEntityManagers()) {
                // Hibernate leaves some dirty status that need to be cleared
                try {
                    em.isOpen();
                } catch (final Exception e) {
                    BaseTransaction.LOGGER.warn(e.getMessage());
                }
            }

            // TODO: must find way to warn user of transaction timeout/rolledback from a different thread
        }
    }


    /**
     * Checks if is active.
     *
     * @return true, if is active
     * @throws SystemException the system exception
     */
    // status query methods
    public static boolean isActive() throws SystemException {
        return BaseTransaction.getStatus() == Status.STATUS_ACTIVE;
    }

    /**
     * Checks if is active or marked rollback.
     *
     * @return true, if is active or marked rollback
     * @throws SystemException the system exception
     */
    public static boolean isActiveOrMarkedRollback() throws SystemException {
        final int status = BaseTransaction.getStatus();
        return (status == Status.STATUS_ACTIVE) || (status == Status.STATUS_MARKED_ROLLBACK);
    }

    /**
     * Checks if is rolled back or marked rollback.
     *
     * @return true, if is rolled back or marked rollback
     * @throws SystemException the system exception
     */
    public static boolean isRolledBackOrMarkedRollback() throws SystemException {
        final int status = BaseTransaction.getStatus();
        return (status == Status.STATUS_ROLLEDBACK) || (status == Status.STATUS_MARKED_ROLLBACK);
    }

    /**
     * Checks if is marked rollback.
     *
     * @return true, if is marked rollback
     * @throws SystemException the system exception
     */
    public static boolean isMarkedRollback() throws SystemException {
        return BaseTransaction.getStatus() == Status.STATUS_MARKED_ROLLBACK;
    }

    /**
     * Checks if is no transaction.
     *
     * @return true, if is no transaction
     * @throws SystemException the system exception
     */
    public static boolean isNoTransaction() throws SystemException {
        return BaseTransaction.getStatus() == Status.STATUS_NO_TRANSACTION;
    }

    /**
     * Checks if is rolled back.
     *
     * @return true, if is rolled back
     * @throws SystemException the system exception
     */
    public static boolean isRolledBack() throws SystemException {
        return BaseTransaction.getStatus() == Status.STATUS_ROLLEDBACK;
    }

    /**
     * Checks if is committed.
     *
     * @return true, if is committed
     * @throws SystemException the system exception
     */
    public static boolean isCommitted() throws SystemException {
        return BaseTransaction.getStatus() == Status.STATUS_COMMITTED;
    }

    /**
     * Checks if is rolled back or committed.
     *
     * @return true, if is rolled back or committed
     * @throws SystemException the system exception
     */
    public static boolean isRolledBackOrCommitted() throws SystemException {
        final int status = BaseTransaction.getStatus();
        return (status == Status.STATUS_ROLLEDBACK) || (status == Status.STATUS_COMMITTED);
    }

    /**
     * Gets the status.
     *
     * @return the status
     * @throws SystemException the system exception
     */
    public static int getStatus() throws SystemException {
        return BaseTransaction.getTransactionManager().getStatus();
    }

    /**
     * Gets the status string.
     *
     * @return the status string
     */
    public static String getStatusString() {
        try {
            return BaseTransaction.convertTransactionStatusCodeToFriendlyString(BaseTransaction.getTransactionManager()
                    .getStatus());
        } catch (final Exception e) {
            return "Encountered Exception in getting transaction status " + e.getMessage();
        }
    }

    /**
     * Convert transaction status code to friendly string.
     *
     * @param status the status
     * @return the string
     */
    // helper
    private static String convertTransactionStatusCodeToFriendlyString(final int status) {
        switch (status) {
            case Status.STATUS_ACTIVE:
                return "Active";
            case Status.STATUS_MARKED_ROLLBACK:
                return "Marked Rollback";
            case Status.STATUS_PREPARED:
                return "Prepared";
            case Status.STATUS_PREPARING:
                return "Preparing";
            case Status.STATUS_COMMITTED:
                return "Committed";
            case Status.STATUS_COMMITTING:
                return "Committing";
            case Status.STATUS_ROLLING_BACK:
                return "Rolling Back";
            case Status.STATUS_ROLLEDBACK:
                return "Rolledback";
            case Status.STATUS_NO_TRANSACTION:
                return "No Transaction";
            default:
                return "Unknown";
        }
    }
}
