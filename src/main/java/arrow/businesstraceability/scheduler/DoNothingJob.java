package arrow.businesstraceability.scheduler;

import javax.persistence.EntityManager;

import arrow.framework.logging.BaseLogger;

/**
 * The Class DoNothingJob.
 */
public class DoNothingJob extends NoticeMailJob {

    /**
     * Instantiates a new do nothing job.
     *
     * @param localEmMain the local em main
     * @param localLog the local log
     */
    public DoNothingJob(final EntityManager localEmMain, final BaseLogger localLog) {
        super.emMain = localEmMain;
        super.log = localLog;
    }

    /* (non-Javadoc)
     * @see arrow.businesstraceability.scheduler.NoticeMailJob#runJob()
     */
    @Override
    protected void runJob() {
        // TODO Auto-generated method stub

    }

}
