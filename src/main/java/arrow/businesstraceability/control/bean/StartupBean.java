package arrow.businesstraceability.control.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.concurrent.Callable;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.control.service.ElasticSearchService;
import arrow.businesstraceability.scheduler.ScheduleTaskMonitor;
import arrow.businesstraceability.util.TimeUtils;
import arrow.framework.async.EJBAsyncExecutor;
import arrow.framework.logging.BaseLogger;

@Startup
@Singleton
public class StartupBean implements Serializable {
    @Inject
    private ElasticSearchService service;

    @Inject
    private BaseLogger log;

    @Inject
    private ScheduleTaskMonitor scheduleTaskMonitor;

    /**
     * Method when startup server.
     */
    @PostConstruct
    public void init() {
        EJBAsyncExecutor.executeAsynchronouslyWithoutTransaction(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (!StartupBean.this.scheduleTaskMonitor.isAlive(Constants.ScheduleTask.SYNC_DATA_TO_ELASTIC)) {
                    final long timeBeforeStart =
                        TimeUtils.convertHourToMills(Constants.ScheduleTask.HOUR_START_SYNC)
                            - LocalDateTime.now().getLong(ChronoField.MILLI_OF_DAY);
                    final long intervalDuration = TimeUtils.convertHourToMills(Constants.ScheduleTask.HOUR_SYNC_REPEAT);
                    StartupBean.this.scheduleTaskMonitor.createTimer(timeBeforeStart, intervalDuration,
                        Constants.ScheduleTask.SYNC_DATA_TO_ELASTIC);
                }
                try {
                    StartupBean.this.log.debug("start push data...");
                    // StartupBean.this.service.pushAllData();
                    StartupBean.this.log.debug("finish push data");
                } catch (Exception ex) {
                    StartupBean.this.log.debug(ex.getMessage());
                    return false;
                }
                return true;
            }
        });
    }
}
