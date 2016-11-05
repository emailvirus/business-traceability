package arrow.businesstraceability.util.mail;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Singleton;
import javax.mail.MessagingException;
import javax.mail.Session;

import arrow.framework.util.mail.MailService;


/**
 * The Class ArrowMailService.
 */
@Singleton
public class ArrowMailService extends MailService {

    /** The mail session. */
    /**
     * <mail-session name="businessTraceability" debug="true"<br />
     * jndi-name="java:jboss/mail/arrowMail" from="noreply@arrow-tech.vn"> <smtp-server
     * outbound-socket-binding-ref="arrow-smtp"<br />
     * ssl="false" tls="true" username="noreply@arrow-tech.vn" password="tri2014&amp;"/><br />
     * <outbound-socket-binding name="arrow-smtp"><br />
     * <remote-destination host="smtp-mail.outlook.com" port="587"/> </outbound-socket-binding>
     */
    @Resource(lookup = "java:jboss/mail/arrowMail")
    private Session mailSession;

    /**
     * Start.
     */
    @PostConstruct
    public void start() {

    }

    /* (non-Javadoc)
     * @see arrow.framework.util.mail.MailService#getMailSession()
     */
    @Override
    public Session getMailSession() throws MessagingException {
        return this.mailSession;
    }
}
