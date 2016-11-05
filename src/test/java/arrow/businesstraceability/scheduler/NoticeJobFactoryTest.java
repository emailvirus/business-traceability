package arrow.businesstraceability.scheduler;

import java.net.URI;

import javax.ws.rs.core.UriInfo;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import arrow.businesstraceability.scheduler.constant.NoticeJobNames;
import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class NoticeJobFactoryTest {

    @Test
    public void testCreateNewReportJob(@Mocked final UriInfo uriInfo) throws Exception {
        new Expectations() {
            {
                uriInfo.getBaseUri();
                this.result = new URI("test");
            }
        };
        NoticeMailJob job = NoticeJobFactory.createJob(NoticeJobNames.NEW_REPORT_JOB, uriInfo, null, null);
        Assertions.assertThat(job).isInstanceOf(NewReportNotificationJob.class);

    }

    @Test
    public void testCreateNoReportJob(@Mocked final UriInfo uriInfo) throws Exception {
        new Expectations() {
            {
                uriInfo.getBaseUri();
                this.result = new URI("test");
            }
        };
        NoticeMailJob job = NoticeJobFactory.createJob(NoticeJobNames.NO_REPORT_JOB, uriInfo, null, null);
        Assertions.assertThat(job).isInstanceOf(NoReportNotificationJob.class);

    }

}
