package arrow.businesstraceability.scheduler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;


@Startup
@Singleton
public class ScheduleTaskMonitor {

    @Resource
    private TimerService timerService;

    private final Map<String, AbstractTimerTask> listTask = new HashMap<String, AbstractTimerTask>();

    /**
     * Add Timer key.
     *
     * @param key
     * @param task
     */
    public void addTimerKey(final String key, final AbstractTimerTask task) {
        this.listTask.put(key, task);
    }

    /**
     * Change Timer Value.
     *
     * @param timeBeforeStart
     * @param intervalDuration
     * @param timerKey
     */
    public void changeTimer(final long timeBeforeStart, final long intervalDuration, final String timerKey) {
        this.stopTimer(timerKey);
        this.createTimer(timeBeforeStart, intervalDuration, timerKey);
    }

    /**
     * Create Timer.
     *
     * @param timeBeforeStart
     * @param intervalDuration
     * @param timerKey
     */
    public void createTimer(final long timeBeforeStart, final long intervalDuration, final String timerKey) {
        this.timerService.createTimer(timeBeforeStart, intervalDuration, timerKey);
    }

    /**
     * Stop Timer.
     *
     * @param timerKey
     */
    public void stopTimer(final String timerKey) {
        final Collection<Timer> listTimer = this.timerService.getAllTimers();
        for (final Timer timer : listTimer) {
            if (timerKey.equals(timer.getInfo())) {
                timer.cancel();
            }
        }
    }

    /**
     * Stop All Timers.
     */
    public void stopAllTimer() {
        final Collection<Timer> listTimer = this.timerService.getAllTimers();
        for (final Timer timer : listTimer) {
            timer.cancel();
        }
    }

    /**
     * Check Timer is alive.
     *
     * @param timerKey
     * @return
     */
    public boolean isAlive(final String timerKey) {
        final Collection<Timer> listTimer = this.timerService.getAllTimers();
        for (final Timer timer : listTimer) {
            if (timerKey.equals(timer.getInfo())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method when timer timeout.
     *
     * @param timer
     */
    @Timeout
    public void execute(final Timer timer) {
        for (final Entry<String, AbstractTimerTask> entry : this.listTask.entrySet()) {
            if (entry.getKey().equals(timer.getInfo())) {
                entry.getValue().run();
            }
        }
    }
}
