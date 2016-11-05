package arrow.framework.faces.transaction;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import arrow.framework.faces.event.qualifier.After;
import arrow.framework.faces.event.qualifier.Before;
import arrow.framework.faces.event.qualifier.RenderResponse;
import arrow.framework.faces.event.qualifier.RestoreView;
import arrow.framework.logging.BaseLogger;
import arrow.framework.persistence.transaction.BaseTransaction;


/**
 * This class contain observers that observe JSF lifecyle events and start/end transactions as appropriate.
 *
 * @author Hugh Nguyen
 *
 */
@ApplicationScoped
public class TransactionManagementObserver implements Serializable {

    /** The log. */
    @Inject
    private BaseLogger log;

    /**
     * This method is called when information about an TransactionManagement which was previously requested using an
     * asynchronous interface becomes available.
     *
     * @param event the event
     * @throws SystemException the system exception
     * @throws NotSupportedException the not supported exception
     */
    public void startTransactionBeforeRenderResponse(@Observes @Before @RenderResponse final PhaseEvent event)
            throws SystemException, NotSupportedException {
        this.startTransaction("@Before", PhaseId.RENDER_RESPONSE);
    }

    /**
     * This method is called when information about an TransactionManagement which was previously requested using an
     * asynchronous interface becomes available.
     *
     * @param event the event
     * @throws SystemException the system exception
     * @throws NotSupportedException the not supported exception
     */
    public void startTransactionAfterRestoreView(@Observes @After @RestoreView final PhaseEvent event)
            throws SystemException, NotSupportedException {
        this.startTransaction("@After", PhaseId.RESTORE_VIEW);
    }

    /**
     * This method is called when information about an TransactionManagement which was previously requested using an
     * asynchronous interface becomes available.
     *
     * @param when the when
     * @param phaseId the phase id
     * @throws SystemException the system exception
     * @throws NotSupportedException the not supported exception
     */
    // parameters for this method are used for logging information only
    private void startTransaction(final String when, final PhaseId phaseId)
            throws SystemException, NotSupportedException {
        if (BaseTransaction.isMarkedRollback()) {
            this.log.debug("Rolling back transaction " + when + " phase " + phaseId);
            BaseTransaction.rollback();
        }

        if (BaseTransaction.isRolledBackOrCommitted()) {
            this.log.warn("Transaction was already rolled back or committed before this listener process " + when
                    + " PhaseEvent for phase " + phaseId);
            BaseTransaction.cleanup();
        }

        // by this point, transaction status should be STATUS_NO_TRANSACTION
        if (BaseTransaction.isNoTransaction()) {
            this.log.debug("Beginning transaction " + when + " phase " + phaseId);
            BaseTransaction.begin();
        }
        else {
            this.log.warn(when + " phase: " + phaseId + ": Unexpected transaction status: "
                    + BaseTransaction.getStatusString());
        }
    }

    /**
     * End transaction.
     *
     * @param event the event
     * @throws SecurityException the security exception
     * @throws SystemException the system exception
     */
    public void endTransaction(@Observes @After final PhaseEvent event) throws SecurityException, SystemException {
        final PhaseId phaseId = event.getPhaseId();

        // end transaction after INVOKE_APPLICATION, or whenever we detect that renderResponse() or
        // responseComplete() has been called
        final boolean endTx = (phaseId == PhaseId.INVOKE_APPLICATION) || (phaseId == PhaseId.RENDER_RESPONSE)
                || event.getFacesContext().getRenderResponse() || event.getFacesContext().getResponseComplete();

        if (endTx) {
            if (BaseTransaction.isActive()) {
                try {
                    this.log.debug("Committing transaction after phase " + phaseId);
                    BaseTransaction.commit();
                } catch (final IllegalStateException | RollbackException | HeuristicMixedException
                        | HeuristicRollbackException e) {
                    this.log.error("Transaction commit failed. "
                            + "This may be because it was timed out and rolled back in the background.", e);
                }
            }
            else if (BaseTransaction.isMarkedRollback()) {
                this.log.debug("Rolling back transaction after phase: " + phaseId);
                BaseTransaction.rollback();
            }

            if (BaseTransaction.isRolledBackOrCommitted()) {
                this.log.warn("Transaction was already rolled back or committed "
                        + "before this listener process @After PhaseEvent for phase " + phaseId);

                // need to clean up the transaction if the current phase is not RENDER_RESPONSE,
                // because RENDER_RESPONSE phase will need to begin a new transaction
                if (phaseId != PhaseId.RENDER_RESPONSE) {
                    BaseTransaction.cleanup();
                }
            }

            // by this point, transaction status should be NO_TRANSACTION, unless it's RENDER_RESPONSE
            // phase,
            // in which case the legal status would be either NO_TRANSACTION, ROLLEDBACK or COMMITTED
            if (((phaseId != PhaseId.RENDER_RESPONSE) && !BaseTransaction.isNoTransaction())
                    || ((phaseId == PhaseId.RENDER_RESPONSE) && !BaseTransaction.isNoTransaction()
                            && !BaseTransaction.isRolledBackOrCommitted())) {
                this.log.warn("After phase: " + phaseId + ": Unexpected transaction status: "
                        + BaseTransaction.getStatusString());
            }
        }
    }

    /**
     * End transaction when encounter unexpected exception.
     *
     * @param event the event
     * @throws SystemException the system exception
     */
    public void endTransactionWhenEncounterUnexpectedException(@Observes final ExceptionQueuedEvent event)
            throws SystemException {
        this.log.warnDrawLine();
        this.log.warnv("Unexpected Exception in JSF lifecycle phase " + event.getContext().getPhaseId());
        this.log.warnv("The exception is: {0}", event.getContext().getException().toString());

        if (BaseTransaction.isActiveOrMarkedRollback()) {
            this.log.warnv("Transaction is currently {0}. It will be rolled back now...",
                    BaseTransaction.getStatusString());
            BaseTransaction.rollback();
            BaseTransaction.cleanup();
        }
        else if (BaseTransaction.isRolledBackOrCommitted()) {
            this.log.warn("Transaction has already been rolled back "
                    + "or committed even before this handler processes this exception.");
            BaseTransaction.cleanup();
        }
        else if (BaseTransaction.isNoTransaction()) {
            this.log.warn(
                    "Transaction is not active at this point. " + "It's probably cleaned up due to a prior exception.");
        }
        else {
            this.log.warnv("In endTransactionWhenEncounterUnexpectedException: Unexpected transaction status: {0}",
                    BaseTransaction.getStatusString());
        }

        this.log.warnDrawLine();
    }
}
