package arrow.framework.bean;

import java.io.Serializable;

import javax.inject.Inject;
import javax.transaction.Transactional;

import arrow.businesstraceability.control.bean.ScreenBean;
import arrow.businesstraceability.control.exception.TransactionRollbackException;
import arrow.framework.logging.BaseLogger;

/**
 * The Class AbstractBean.
 */
@Transactional(rollbackOn = {TransactionRollbackException.class})
public abstract class AbstractBean implements Serializable {

    /** The log. */
    @Inject
    protected BaseLogger log;

    @Inject
    protected ScreenBean screenBean;

}
