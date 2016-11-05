package arrow.businesstraceability.util.mail;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.MessagingException;

import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.omnifaces.util.Faces;

import freemarker.template.TemplateException;

import arrow.businesstraceability.persistence.entity.Daily_report;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.User_login;
import arrow.framework.util.DateUtils;
import arrow.framework.util.cdi.Instance;
import arrow.framework.util.i18n.Messages;
import arrow.framework.util.mail.ArrowMail;
import arrow.framework.util.mail.MailService;
import arrow.framework.util.mail.templating.freemarker.FreemarkerTemplate;



/**
 * The Class EmailHelper.
 */
@ApplicationScoped
public class EmailHelper {

    /** The email subject. */
    @Inject
    @ConfigProperty(name = "emailSubject")
    private String emailSubject;

    /** The Constant TEMPLATE_PARAMS_USER. */
    public static final String TEMPLATE_PARAMS_USER = "user";

    /** The Constant TEMPLATE_PARAMS_URL. */
    public static final String TEMPLATE_PARAMS_URL = "url";

    /** The Constant TEMPLATE_PARAMS_DATETIME. */
    public static final String TEMPLATE_PARAMS_DATETIME = "datetime";

    /** The Constant TEMPLATES_CREATE_EMPLOYEE_FTL. */
    public static final String TEMPLATES_CREATE_EMPLOYEE_FTL = "arrow/templates/createEmployee.ftl";

    /** The Constant TEMPLATES_RECOVER_PASSWORD_FTL. */
    public static final String TEMPLATES_RECOVER_PASSWORD_FTL = "arrow/templates/recoverPassword.ftl";

    /** The Constant TEMPLATES_CREATE_REPORT. */
    public static final String TEMPLATES_CREATE_REPORT = "arrow/templates/noticeNewReport.ftl";

    /** The Constant TEMPLATES_CREATE_REPORT_BY_DAY. */
    public static final String TEMPLATES_CREATE_REPORT_BY_DAY = "arrow/templates/noticeNewReportByDay.ftl";

    /** The Constant TEMPLATES_NOT_REGISTER_DAILY_REPORT. */
    public static final String TEMPLATES_NOT_REGISTER_DAILY_REPORT = "arrow/templates/noRegisterDailyReport.ftl";

    /** The Constant TEMPLATE_PARAMS_PWD. */
    private static final String TEMPLATE_PARAMS_PWD = "pwd";

    /**
     * Send new account notification.
     *
     * @param randomPass the random pass
     * @param newAccount the new account
     * @param toAddress the to address
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws TemplateException the template exception
     * @throws MessagingException the messaging exception
     */
    public static void sendNewAccountNotification(final String randomPass, final User_login newAccount,
            final String toAddress) throws IOException, TemplateException, MessagingException {
        final MailService mailService = Instance.get(MailService.class);
        final String subject = Messages.get("mail_subject_new_account_created");
        final FreemarkerTemplate template = new FreemarkerTemplate(
                FreemarkerTemplate.loadTemplate(EmailHelper.TEMPLATES_CREATE_EMPLOYEE_FTL, Faces.getDefaultLocale()));
        final Map<String, Object> context = new HashMap<>();
        context.put(EmailHelper.TEMPLATE_PARAMS_PWD, randomPass);
        context.put(EmailHelper.TEMPLATE_PARAMS_USER, newAccount);
        context.put(EmailHelper.TEMPLATE_PARAMS_URL, Faces.getRequestBaseURL());
        final String content = template.merge(context);
        final ArrowMail mail = new ArrowMail(toAddress, subject, content);
        mailService.sendMail(mail);
    }

    /**
     * Send recover password.
     *
     * @param accout the accout
     * @param toAddress the to address
     * @param url the url
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws TemplateException the template exception
     * @throws MessagingException the messaging exception
     */
    public static void sendRecoverPassword(final User_login accout, final String toAddress, final String url)
            throws IOException, TemplateException, MessagingException {
        final MailService mailService = Instance.get(MailService.class);
        final String subject = Messages.get("recover_password");
        final FreemarkerTemplate template = new FreemarkerTemplate(
                FreemarkerTemplate.loadTemplate(EmailHelper.TEMPLATES_RECOVER_PASSWORD_FTL, Faces.getDefaultLocale()));
        final Map<String, Object> context = new HashMap<>();
        context.put(EmailHelper.TEMPLATE_PARAMS_USER, accout);
        context.put(EmailHelper.TEMPLATE_PARAMS_URL, url);

        final String content = template.merge(context);
        final ArrowMail mail = new ArrowMail(toAddress, subject, content);
        mailService.sendMail(mail);
    }

    /**
     * Send infor when create report.
     *
     * @param accout the accout
     * @param toAddress the to address
     * @param url the url
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws TemplateException the template exception
     * @throws MessagingException the messaging exception
     */
    public static void sendInforWhenCreateReport(final Daily_report accout, final String toAddress, final String url)
            throws IOException, TemplateException, MessagingException {
        int year = DateUtils.getYear(accout.getDai_work_date());
        int month = DateUtils.getMonth(accout.getDai_work_date());
        int day = DateUtils.getDayOfMonth(accout.getDai_work_date());
        final MailService mailService = Instance.get(MailService.class);
        final String subject = year + "年" + month + "月" + day + "日 " + Messages.get("email_subject_create_report");
        final FreemarkerTemplate template = new FreemarkerTemplate(
                FreemarkerTemplate.loadTemplate(EmailHelper.TEMPLATES_CREATE_REPORT, Faces.getDefaultLocale()));
        final Map<String, Object> context = new HashMap<>();
        context.put(EmailHelper.TEMPLATE_PARAMS_USER, accout);
        context.put(EmailHelper.TEMPLATE_PARAMS_DATETIME, DateUtils.formatDateTime(accout.getDai_work_date()));
        context.put(EmailHelper.TEMPLATE_PARAMS_URL, url);

        final String content = template.merge(context);
        final ArrowMail mail = new ArrowMail(toAddress, subject, content);
        mailService.sendMail(mail);
    }

    /**
     * Send info report by day.
     *
     * @param accout the accout
     * @param toAddress the to address
     * @param listUri the list uri
     * @param currentDate the current date
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws TemplateException the template exception
     * @throws MessagingException the messaging exception
     */
    public static void sendInfoReportByDay(final List<Employee_mst> accout, final String toAddress,
            final List<String> listUri, final Date currentDate)
                    throws IOException, TemplateException, MessagingException {
        final String subject = EmailHelper.createSubjectName(currentDate, "日報レポート");
        final MailService mailService = Instance.get(MailService.class);
        final FreemarkerTemplate template = new FreemarkerTemplate(
                FreemarkerTemplate.loadTemplate(EmailHelper.TEMPLATES_CREATE_REPORT_BY_DAY, Locale.JAPANESE));
        final Map<String, Object> context = new HashMap<>();
        context.put(EmailHelper.TEMPLATE_PARAMS_USER, accout);
        context.put(EmailHelper.TEMPLATE_PARAMS_DATETIME, DateUtils.formatDateTime(currentDate));
        context.put(EmailHelper.TEMPLATE_PARAMS_URL, listUri);

        final String content = template.merge(context);
        final ArrowMail mail = new ArrowMail(toAddress, subject, content);
        mailService.sendMail(mail);
    }

    /**
     * Send email when not register daily report.
     *
     * @param accout the accout
     * @param toAddress the to address
     * @param currentDate the current date
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws TemplateException the template exception
     * @throws MessagingException the messaging exception
     */
    // send email when not register daily report
    public static void sendEmailWhenNotRegisterDailyReport(final Map<String, String> accout, final String toAddress,
            final Date currentDate) throws IOException, TemplateException, MessagingException {
        final String subject = EmailHelper.createSubjectName(currentDate, "日報未入力");
        final MailService mailService = Instance.get(MailService.class);
        Locale locale1 = new Locale("ja", "Japan", "WIN");
        final FreemarkerTemplate template = new FreemarkerTemplate(
                FreemarkerTemplate.loadTemplate(EmailHelper.TEMPLATES_NOT_REGISTER_DAILY_REPORT, locale1));
        final Map<String, Object> context = new HashMap<>();
        context.put(EmailHelper.TEMPLATE_PARAMS_USER, accout);
        context.put(EmailHelper.TEMPLATE_PARAMS_DATETIME, DateUtils.formatDateTime(currentDate));

        final String content = template.merge(context);
        final ArrowMail mail = new ArrowMail(toAddress, subject, content);
        mailService.sendMail(mail);
    }

    /**
     * Creates the subject name.
     *
     * @param currentDate the current date
     * @param reportName the report name
     * @return the string
     */
    public static String createSubjectName(final Date currentDate, final String reportName) {
        int year = DateUtils.getYear(currentDate);
        int month = DateUtils.getMonth(currentDate);
        int day = DateUtils.getDayOfMonth(currentDate);
        return year + "年" + month + "月" + day + "日 " + reportName;

    }
}
