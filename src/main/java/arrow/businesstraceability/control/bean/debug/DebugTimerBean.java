package arrow.businesstraceability.control.bean.debug;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;
import javax.inject.Named;

import arrow.framework.logging.BaseLogger;

@Stateless
@Named
public class DebugTimerBean implements Serializable {

    @Inject
    private BaseLogger logger;

    @Resource
    private TimerService timerService;

    /**
     * Check timer.
     */
    public void checkTimer() {
        if (this.timerService != null) {
            for (Timer timer : this.timerService.getAllTimers()) {
                if (timer.isPersistent()) {
                    this.logger.debugf("Persisted Timer %s", timer.getInfo());
                }
            }
        }
    }
}
