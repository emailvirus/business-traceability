package arrow.framework.util.mail;

import java.io.Serializable;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import arrow.framework.logging.BaseLogger;


/**
 * The Class MailService.
 */
public abstract class MailService implements Serializable {

    /** The logger. */
    @Inject
    BaseLogger logger;

    /**
     * Gets the encoding.
     *
     * @return the encoding
     */
    protected String getEncoding() {
        return "UTF-8";
    }

    /**
     * Gets the format.
     *
     * @return the format
     */
    protected String getFormat() {
        return "HTML";
    }

    /**
     * Send mail.
     *
     * @param email the email
     * @return true, if successful
     * @throws MessagingException the messaging exception
     */
    public boolean sendMail(final ArrowMail email) throws MessagingException {
        final MimeMessage m = new MimeMessage(this.getMailSession());
        final InternetAddress[] to = new InternetAddress[] {new InternetAddress(email.getTo())};
        m.setRecipients(Message.RecipientType.TO, to);

        m.setSubject(email.getSubject());
        m.setSentDate(new java.util.Date());
        m.setText(email.getContent(), this.getEncoding(), this.getFormat());
        Transport.send(m);
        return true;
    }

    /**
     * Gets the mail session.
     *
     * @return the mail session
     * @throws MessagingException the messaging exception
     */
    public abstract Session getMailSession() throws MessagingException;
}
