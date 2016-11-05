package arrow.businesstraceability.util.cdi;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;



/**
 * The Class TestDeltaSpikeScheduler.
 */
// @Scheduled(cronExpression = "0/10 * * * * ?")
public class TestDeltaSpikeScheduler implements org.quartz.Job {

    /* (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        // ...

        //
        // ContextControl ctxCtrl = BeanProvider.getContextualReference(ContextControl.class);
        //
        // // this will implicitly bind a new RequestContext to your current thread
        // ctxCtrl.startContext(RequestScoped.class);

        System.out.println("Test Delta Spike schedule");

        // at the end of the Job, we gonna stop the RequestContext
        // to ensure that all beans get properly cleaned up.
        // ctxCtrl.stopContext(RequestScoped.class);
    }

}
