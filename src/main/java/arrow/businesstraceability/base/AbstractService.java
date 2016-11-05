package arrow.businesstraceability.base;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.control.exception.TransactionRollbackException;
import arrow.framework.logging.BaseLogger;


/**
 * The Class AbstractService.
 */
@Transactional(rollbackOn = {TransactionRollbackException.class})
public class AbstractService implements Serializable {
    
    /** The log. */
    @Inject
    protected BaseLogger log;

    /** The em main. */
    @Inject
    protected EntityManager emMain;

    /** The current user. */
    @Inject
    protected UserCredential currentUser;
}
