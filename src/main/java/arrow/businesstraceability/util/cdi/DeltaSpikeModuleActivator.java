package arrow.businesstraceability.util.cdi;

import org.apache.deltaspike.core.spi.activation.ClassDeactivator;
import org.apache.deltaspike.core.spi.activation.Deactivatable;


/**
 * The Class DeltaSpikeModuleActivator.
 */
public class DeltaSpikeModuleActivator implements ClassDeactivator {

    /* (non-Javadoc)
     * @see org.apache.deltaspike.core.spi.activation.ClassDeactivator#isActivated(java.lang.Class)
     */
    @Override
    public Boolean isActivated(final Class<? extends Deactivatable> targetClass) {
        // if (pTargetClass.equals(SchedulerExtension.class)) {
        // return false;
        // }
        return true;
    }
}
