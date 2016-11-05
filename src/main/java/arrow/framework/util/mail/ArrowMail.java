package arrow.framework.util.mail;

import java.io.Serializable;


/**
 * The Class ArrowMail.
 */
public class ArrowMail implements Serializable {

    /**
     * Instantiates a new arrow mail.
     */

    public ArrowMail() {

    }

    /**
     * Instantiates a new arrow mail.
     *
     * @param to the to
     * @param subject the subject
     * @param content the content
     */
    public ArrowMail(final String to, final String subject, final String content) {
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    /** The subject. */
    private String subject;

    /** The to. */
    private String to;

    // private String cc;
    /** The content. */
    // private String bcc;
    private String content;

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return this.subject;
    }

    /**
     * Sets the subject.
     *
     * @param subject the new subject
     */
    public void setSubject(final String subject) {
        this.subject = subject;
    }

    /**
     * Gets the to.
     *
     * @return the to
     */
    public String getTo() {
        return this.to;
    }

    /**
     * Sets the to.
     *
     * @param to the new to
     */
    public void setTo(final String to) {
        this.to = to;
    }

    // public String getCc()
    // {
    // return this.cc;
    // }
    //
    // public void setCc(final String cc)
    // {
    // this.cc = cc;
    // }
    //
    // public String getBcc()
    // {
    // return this.bcc;
    // }
    //
    // public void setBcc(final String bcc)
    // {
    // this.bcc = bcc;
    // }

    /**
     * Gets the content.
     *
     * @return the content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Sets the content.
     *
     * @param content the new content
     */
    public void setContent(final String content) {
        this.content = content;
    }
}
