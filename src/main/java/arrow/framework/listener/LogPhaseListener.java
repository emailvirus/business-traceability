package arrow.framework.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogPhaseListener implements PhaseListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogPhaseListener.class);

    private long startTime;


    @Override
    public void afterPhase(final PhaseEvent event) {
        long diff = System.currentTimeMillis() - this.startTime;
        LogPhaseListener.LOGGER.debug("End phase: " + event.getPhaseId());
        LogPhaseListener.LOGGER.debug("Phase duration: " + diff + " milliseconds");
    }

    @Override
    public void beforePhase(final PhaseEvent event) {
        this.startTime = System.currentTimeMillis();
        LogPhaseListener.LOGGER.debug("Start phase: " + event.getPhaseId());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
