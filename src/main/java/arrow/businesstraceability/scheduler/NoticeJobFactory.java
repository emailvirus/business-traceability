package arrow.businesstraceability.scheduler;

import javax.persistence.EntityManager;
import javax.ws.rs.core.UriInfo;

import arrow.framework.logging.BaseLogger;

public class NoticeJobFactory {

    private NoticeJobFactory() {
        // hide default constructor
    }

    /**
     * Creates the job.
     *
     * @param jobName the job name
     * @param uri the uri
     * @param localEmMain the local em main
     * @param localLog the local log
     * @return the notice mail job
     */
    public static NoticeMailJob createJob(final String jobName, final UriInfo uri, final EntityManager localEmMain,
            final BaseLogger localLog) {
        String baseUrl = uri.getBaseUri().toString();
        baseUrl = baseUrl.replaceAll("rest/", "");
        if ("registerDailyReport".equalsIgnoreCase(jobName)) {
            return new NewReportNotificationJob(baseUrl, localEmMain, localLog);
        }
        else if ("noRegisterDailyReport".equalsIgnoreCase(jobName)) {
            return new NoReportNotificationJob(localEmMain, localLog);
        }
        return new DoNothingJob(localEmMain, localLog);
    }
}
