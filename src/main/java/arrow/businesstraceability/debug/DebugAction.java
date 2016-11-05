package arrow.businesstraceability.debug;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.NonexistentConversationException;
import javax.enterprise.event.Observes;
import javax.faces.application.ViewExpiredException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import freemarker.template.TemplateException;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.control.helper.AuthenticationData;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.AuthenticationService;
import arrow.businesstraceability.event.observe.CompanySearchEvent;
import arrow.businesstraceability.global.UserSessionMonitor;
import arrow.businesstraceability.persistence.IdGenerator;
import arrow.businesstraceability.persistence.entity.Abstract_entity;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Daily_report;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.User_login;
import arrow.businesstraceability.persistence.mapped.Company_mst_MAPPED_;
import arrow.businesstraceability.persistence.mapped.User_login_MAPPED;
import arrow.businesstraceability.util.EncryptStringUtils;
import arrow.framework.async.AsyncExecutor;
import arrow.framework.exception.ArrException;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.persistence.BaseEntity;
import arrow.framework.persistence.event.qualifier.EntityUpdated;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DateUtils;
import arrow.framework.util.cdi.Instance;
import arrow.framework.util.mail.templating.freemarker.FreemarkerTemplate;

/**
 * The Class DebugAction.
 */
@Named
@ViewScoped
public class DebugAction extends AbstractService implements Runnable {

    /** The auth service. */
    @Inject
    AuthenticationService authService;

    /** The session monitor. */
    @Inject
    UserSessionMonitor sessionMonitor;

    /** The input value1. */
    private String inputValue1;

    /** The list company. */
    private List<Company_mst> listCompany;

    /** The executor service. */
    // find the service which control scheduled job.
    @Resource(lookup = "java:jboss/ee/concurrency/scheduler/business_traceability")
    private ManagedScheduledExecutorService executorService;

    /** The user code. */
    private int userCode;

    /**
     * On entity persisted or updated4.
     *
     * @param event the event
     */
    public static void onEntityPersistedOrUpdated4(
        @Observes @EntityUpdated(value = Employee_mst.class) final BaseEntity event) {
        System.out.println("Employee " + event.toString() + " persisted");
    }

    /**
     * Action1.
     */
    public void action1() {
        // ErrorMessage.ERR_AUTH_001_USER_MAX_LEN("test", 9).show();
        // ErrorMessage.ERR_AUTH_002_USER_NOT_EXISTS("ducva").show();
    }

    /**
     * Gets the input value1.
     *
     * @return the input value1
     */
    public String getInputValue1() {
        return this.inputValue1;
    }

    /**
     * Sets the input value1.
     *
     * @param inputValue1 the new input value1
     */
    public void setInputValue1(final String inputValue1) {
        this.inputValue1 = inputValue1;
    }

    /**
     * Insert data.
     */
    // Test insert data.
    public void insertData() {
        // Employee_mst emp = new Employee_mst(3303);
        // emp.setAddresspoint_mst(Addresspoint_mst.find("01"));
        // emp.setEmp_name("test");
        // this.emMain.persist(emp);
        // this.emMain.flush();
        // Employee_mst emp = Employee_mst.find(3302);
        // emp.setEmp_entery_date(new Date());
        // emp.setEmp_mail_DIRECT("Test@test.com");

        final User_login userLogin = User_login.find(9999999);
        userLogin.setUl_login_time_DIRECT(DateUtils.getCurrentDatetime());
        this.emMain.flush();
    }

    /**
     * Fetch lazy.
     *
     * @throws ArrException the arr exception
     */
    public void fetchLazy() throws ArrException {
        // this.emMain.clear();
        final ServiceResult<AuthenticationData> result = this.authService.login("9999999", "triarrow");
        System.out.println(result.getData().getEmployee().getEmp_adpcode());
        this.inputValue1 = result.getData().getEmployee().getEmp_mail_DIRECT();
    }

    /**
     * Clear entity manager.
     */
    public void clearEntityManager() {
        this.emMain.clear();
    }

    /**
     * On entity persisted or updated.
     *
     * @param event the event
     */
    public void onEntityPersistedOrUpdated(@Observes @EntityUpdated(value = Employee_mst.class) final BaseEntity event) {
        System.out.println("Employee " + event.toString() + " persisted");
    }

    /**
     * On entity persisted or updated2.
     *
     * @param event the event
     */
    public void onEntityPersistedOrUpdated2(
        @Observes @EntityUpdated(value = Abstract_entity.class) final BaseEntity event) {
        System.out.println("Employee " + event.toString() + " persisted");
    }

    /**
     * On entity persisted or updated3.
     *
     * @param event the event
     */
    public void onEntityPersistedOrUpdated3(@Observes @EntityUpdated(value = BaseEntity.class) final BaseEntity event) {
        System.out.println("Employee " + event.toString() + " persisted");
    }

    /**
     * Fire exception.
     *
     * @throws Exception the exception
     */
    public void fireException() throws Exception {
        throw new Exception("Test");
    }

    /**
     * Fire view expirted exception.
     */
    public void fireViewExpirtedException() {
        throw new ViewExpiredException();
    }

    /**
     * Fire nonexistent conversation exception.
     */
    public void fireNonexistentConversationException() {
        throw new NonexistentConversationException("test");
    }

    /**
     * Test query.
     */
    public void testQuery() {
        final CriteriaBuilder builder = this.emMain.getCriteriaBuilder();
        final CriteriaQuery<Company_mst> query = builder.createQuery(Company_mst.class);
        final Root<Company_mst> companyMst = query.from(Company_mst.class);
        query.select(companyMst);
        query.orderBy(builder.desc(companyMst.get(Company_mst_MAPPED_.com_company_code)));
        this.emMain.createQuery(query).getResultList();

    }

    /**
     * Observe event.
     *
     * @param event the event
     */
    public void observeEvent(@Observes final CompanySearchEvent event) {
        if ("afterSelectCompanyEvent".equalsIgnoreCase(event.getEventName())) {
            final Company_mst selectedCompany = event.getSelectedCompany();
            System.out.println(selectedCompany.getCom_company_code());
        }
    }

    /**
     * Test id generator.
     */
    public void testIdGenerator() {
        System.out.println(IdGenerator.getNextId(this.emMain, "Daily_report", "dai_report_id"));
    }

    /**
     * Gets the company list.
     *
     * @return the company list
     */
    private List<Company_mst> getCompanyList() {
        final ArrowQuery<Company_mst> query = new ArrowQuery<>(this.emMain);

        query.select("e").from("Company_mst e");
        query.addDateFromToFilter("last_updated_at", "e.last_updated_at");
        query.addFilter("company_name", "UPPER(e.com_company_name) LIKE ?");
        return query.getResultList();

    }

    /**
     * Gets the list company.
     *
     * @return the list company
     */
    public List<Company_mst> getListCompany() {
        return this.listCompany;

    }

    /**
     * Sets the list company.
     *
     * @param listCompany the new list company
     */
    public void setListCompany(final List<Company_mst> listCompany) {
        this.listCompany = listCompany;
    }

    /**
     * Search.
     */
    public void search() {
        this.listCompany = this.getCompanyList();
    }

    /**
     * Show modal panel.
     */
    public void showModalPanel() {
        Instance.get(DebugModalBean.class).show();
    }

    // public void observedSynEntityManagerCreated(@Observes @SynEntityManagerCreated final
    // SynEntityManager em)
    // {
    // // User_login user = em.find(User_login.class, new User_login_MAPPED.Pk(9999999));
    // // if (user != null)
    // // {
    // // System.out.println(user.getUl_password_DIRECT());
    // // System.out.println("test");
    // // }
    // }

    /**
     * Start job.
     */
    public void startJob() {
        // run this job each 5 minutes
        this.executorService.scheduleAtFixedRate(new DebugAction(), 0L, 30L, TimeUnit.SECONDS);

    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        AsyncExecutor.executeAsynchronouslyWithIsolatedTransaction(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                final User_login user = EmLocator.getEm().find(User_login.class, new User_login_MAPPED.Pk(9999999));
                assert user != null;
                System.out.println(user.getUl_password_DIRECT());
                System.out.println("test");
                return true;
            }
        });
    }

    /**
     * Test email.
     *
     * @throws MessagingException the messaging exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws TemplateException the template exception
     */
    public void testEmail() throws MessagingException, IOException, TemplateException {
        // MailService mailService = CDIUtils.getContextualInstance(MailService.class);

        final FreemarkerTemplate template =
            new FreemarkerTemplate(FreemarkerTemplate.loadTemplate("arrow/templates/createEmployee.ftl",
                Faces.getLocale()));
        final Map<String, Object> context = new HashMap<>();
        context.put("user", User_login.find(9999999));
        final String content = template.merge(context);
        System.out.println(content);
        // ArrowMail mail = new ArrowMail("ducva@arrow-tech.vn", "test", content);
        // mailService.sendMail(mail);
    }

    /**
     * Publish new report.
     */
    public void publishNewReport() {
        final EventBus bus = EventBusFactory.getDefault().eventBus();
        bus.publish("/newReport", new Daily_report());

    }

    /**
     * Correct ul passsalt column.
     */
    public void correctUlPasssaltColumn() {
        final List<User_login> allUsers =
            this.emMain.createQuery("SELECT e FROM User_login e", User_login.class).getResultList();
        for (final User_login user : allUsers) {
            if (StringUtils.isEmpty(user.getUl_passsalt())) {
                // if (StringUtils.isNotEmpty(user.getUl_password())) {
                user.setUl_passsalt(EncryptStringUtils.encrypt(user.getUl_password()));
                // }
            }
        }

        this.emMain.flush();
    }

    /**
     * Gets the user code.
     *
     * @return the user code
     */
    public int getUserCode() {
        return this.userCode;
    }

    /**
     * Sets the user code.
     *
     * @param userCode the new user code
     */
    public void setUserCode(final int userCode) {
        this.userCode = userCode;
    }

    /**
     * Kick user.
     */
    public void kickUser() {
        this.sessionMonitor.kickUser(this.userCode);
    }

}
