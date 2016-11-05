package arrow.businesstraceability.scheduler;

import javax.inject.Inject;

public abstract class AbstractTimerTask {

    public abstract String getTimerKey();

    @Inject
    protected ScheduleTaskMonitor scheduleMonitor;

    public abstract void run();

    public void addToSchedule() {
        this.scheduleMonitor.addTimerKey(this.getTimerKey(), this);
    }
}
