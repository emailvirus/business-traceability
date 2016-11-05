package arrow.businesstraceability.rest;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.deltaspike.core.api.config.ConfigProperty;

import arrow.businesstraceability.scheduler.NoticeJobFactory;
import arrow.businesstraceability.scheduler.NoticeMailJob;
import arrow.framework.logging.BaseLogger;


/**
 * The Class ScheduleRESTService.
 */
@Path("/schedule")
@RequestScoped
public class ScheduleRESTService implements Serializable {

    /** The local log. */
    @Inject
    protected BaseLogger localLog;

    /** The secret key from file. */
    @Inject
    @ConfigProperty(name = "arrowSecretKey")
    private String secretKeyFromFile;

    /** The local em main. */
    @Inject
    private EntityManager localEmMain;

    /**
     * Gets the msg.
     *
     * @param uri the uri
     * @param secretKey the secret key
     * @param jobName the job name
     * @return the msg
     */
    @GET
    @Path("/{secretKey}/{jobName}")
    public Response getMsg(@Context final UriInfo uri, @PathParam("secretKey") final String secretKey,
            @PathParam("jobName") final String jobName) {
        if (secretKey.equals(this.secretKeyFromFile)) {
            NoticeMailJob job = NoticeJobFactory.createJob(jobName, uri, this.localEmMain, this.localLog);
            job.execute();
        }
        String output = "Output: " + this.secretKeyFromFile + "/" + jobName;
        System.out.println("***********Ouput:**********: " + output);
        return Response.ok(output).build();
    }
}
