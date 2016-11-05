package arrow.businesstraceability.util.cdi;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.inject.Inject;

import arrow.framework.logging.BaseLogger;


/**
 * The Class Bootstrap.
 */
public class Bootstrap implements Serializable {

    /** The log. */

    @Inject
    private BaseLogger log;

    // find the service which control scheduled job.
    // defined in standalone.xml

    /**
     * ref: http://blog.chris-ritchie.com/2013/09/how-to-configure-scheduled-tasks-in-wildfly..html <br/>
     * <code>
     * <managed-scheduled-executor-service name="business_traceability"<br />
     * jndi-name="java:jboss/ee/concurrency/scheduler/business_traceability"
     * context-service="default" hung-task-threshold="60000" core-threads="2"<br />
     * keepalive-time="3000" reject-policy="RETRY_ABORT"/>
     * </code>
     */
    @Resource(lookup = "java:jboss/ee/concurrency/scheduler/business_traceability")
    private ManagedScheduledExecutorService executorService;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        this.log.debug("Startup");

        // this.executorService.scheduleAtFixedRate(new NotificationJob(), 0L,
        // TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
    }
}
