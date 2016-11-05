package arrow.businesstraceability.scheduler;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.control.service.ElasticSearchService;
import arrow.framework.logging.BaseLogger;

@Startup
@Singleton
public class SynchronizeDatabaseElasticJob extends AbstractTimerTask {

    @Inject
    private BaseLogger log;

    @Inject
    private ElasticSearchService service;

    @PostConstruct
    public void init() {
        this.addToSchedule();
    }

    @Override
    public String getTimerKey() {
        return Constants.ScheduleTask.SYNC_DATA_TO_ELASTIC;
    }

    /**
     * Implement method when timer run.
     */
    @Override
    public void run() {
        try {
            this.service.pushAllData();
        } catch (Exception ex) {
            this.log.debug(ex.getMessage());
        }
    }
}
