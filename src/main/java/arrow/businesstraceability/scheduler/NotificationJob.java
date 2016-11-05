package arrow.businesstraceability.scheduler;

import java.util.concurrent.Callable;

import org.apache.deltaspike.scheduler.api.Scheduled;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import arrow.framework.async.AsyncExecutor;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DateUtils;



/**
 * The Class NotificationJob.
 */
// auto run at 4:00 AM everyday.
@Scheduled(cronExpression = "0 0 4 * * ?", onStartup = true)
@DisallowConcurrentExecution
public class NotificationJob implements org.quartz.Job {

    /* (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        System.out.println("Running Notification Job");
        AsyncExecutor.executeAsynchronouslyWithIsolatedTransaction(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {

                // Delete data in Notification_data
                final StringBuilder sb1 = new StringBuilder();
                sb1.append("DELETE FROM Notification_data nd").append(" WHERE nd.nd_item_key IN (");
                sb1.append("SELECT ni.ni_item_key FROM Notification_item ni").append(" WHERE ");
                sb1.append(" ni.ni_created_at < '"
                        + DateUtils.getPreviousMonthFromCurrentDate(DateUtils.getCurrentDatetime()) + "')");
                EmLocator.getEm().createQuery(sb1.toString()).executeUpdate();

                // Check to delete data in Notification_item

                final StringBuilder sb3 = new StringBuilder();
                sb3.append("DELETE FROM Notification_item ni ").append(" WHERE ");
                sb3.append(" ni.ni_created_at < '"
                        + DateUtils.getPreviousMonthFromCurrentDate(DateUtils.getCurrentDatetime()) + "'");
                EmLocator.getEm().createQuery(sb3.toString()).executeUpdate();

                return true;
            }

        });
    }
}
