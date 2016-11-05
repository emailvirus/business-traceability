package arrow.businesstraceability.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import arrow.businesstraceability.rest.endpoint.DailyReportEndpoint;
import arrow.businesstraceability.rest.endpoint.DownloadCsvFileEndPoint;
import arrow.framework.util.cdi.Instance;


/**
 * The Class RestApplication.
 */
public class RestApplication extends Application {

    /** The singletons. */
    private final Set<Object> singletons = new HashSet<Object>();


    /**
     * Instantiates a new rest application.
     */
    public RestApplication() {
        this.singletons.add(Instance.get(ScheduleRESTService.class));
        this.singletons.add(Instance.get(DailyReportEndpoint.class));
        this.singletons.add(Instance.get(DownloadCsvFileEndPoint.class));
    }

    /* (non-Javadoc)
     * @see javax.ws.rs.core.Application#getSingletons()
     */
    @Override
    public Set<Object> getSingletons() {
        return this.singletons;
    }
}
