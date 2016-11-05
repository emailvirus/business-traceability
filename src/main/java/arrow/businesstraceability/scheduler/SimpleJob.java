package arrow.businesstraceability.scheduler;

import java.util.concurrent.Callable;

import arrow.businesstraceability.persistence.entity.User_login;
import arrow.businesstraceability.persistence.mapped.User_login_MAPPED;
import arrow.framework.async.AsyncExecutor;
import arrow.framework.persistence.locator.EmLocator;


/**
 * The Class SimpleJob.
 */
public class SimpleJob implements Runnable {

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        AsyncExecutor.executeAsynchronouslyWithIsolatedTransaction(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final User_login user = EmLocator.getEm().find(User_login.class, new User_login_MAPPED.Pk(9999999));
                System.out.println(user.getUl_password_DIRECT());
                return true;
            }
        });

    }

}
