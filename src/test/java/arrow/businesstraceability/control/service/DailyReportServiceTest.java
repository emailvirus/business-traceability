package arrow.businesstraceability.control.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import arrow.businesstraceability.control.bean.RegisterActivityHistorySearchBean;
import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.helper.ServiceSearchResult;
import arrow.businesstraceability.control.service.NotificationService.RegisterActivityEvent;
import arrow.businesstraceability.persistence.dto.Daily_report_DTO;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Daily_activity_type;
import arrow.businesstraceability.persistence.entity.Daily_report;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Industry_big_mst;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.businesstraceability.persistence.repository.Basepoint_infoRepository;
import arrow.businesstraceability.persistence.repository.Company_mstRepository;
import arrow.businesstraceability.persistence.repository.Daily_activity_typeRepository;
import arrow.businesstraceability.persistence.repository.Daily_reportRepository;
import arrow.businesstraceability.persistence.repository.Employee_mstRepository;
import arrow.businesstraceability.persistence.repository.Industry_big_mstRepository;
import arrow.businesstraceability.persistence.repository.Monthly_report_revisionRepository;
import arrow.businesstraceability.persistence.repository.RepoResult;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.logging.BaseLogger;
import arrow.framework.util.DateUtils;
import arrow.framework.util.StringUtils;
import arrow.framework.util.cdi.Instance;

@RunWith(JMockit.class)
public class DailyReportServiceTest {

    @Mocked
    @Tested
    private DailyReportService service;

    @Mocked
    @Injectable
    private BaseLogger logger;

    @Mocked
    @Injectable
    protected EntityManager emMain;

    @Mocked
    @Injectable
    protected UserCredential currentUser;

    @Mocked
    @Injectable
    private Event<RegisterActivityEvent> eventSrc;

    @Mocked
    @Injectable
    private Addresspoint_mstRepository addressRepo;

    @Mocked
    @Injectable
    private Employee_mstRepository employeeRepo;

    @Mocked
    @Injectable
    private Daily_activity_typeRepository dailyActivityTypeRepo;

    @Mocked
    @Injectable
    private Daily_reportRepository dailyreportRepo;

    @Mocked
    @Injectable
    private Company_mstRepository companyRepo;

    @Mocked
    @Injectable
    private Industry_big_mstRepository industryBigMstRepo;

    @Injectable
    private Monthly_report_revisionRepository monthlyReportRepo;

    @Injectable
    private Basepoint_infoRepository basepointRepo;

    // Params prepared for test
    private Date selectedDate;

    /**
     * Daily report can add into DB.
     */
    private Daily_report dailyReportValid;

    /** The daily report with bus code. */
    private Daily_report dailyReportWithBusCode;

    /** The report have not start work time. */
    private Daily_report_DTO reportHaveNotStime;

    /** The report have not end work time. */
    private Daily_report_DTO reportHaveNotEtime;

    /** The report have start work time after end work time. */
    private Daily_report_DTO reportStimeAfterEtime;

    /** The report overlap case. */
    private Daily_report_DTO reportOverlapCase;

    /** The report no content. */
    private Daily_report_DTO reportNoContent;

    /**
     * The report missing info case1. Missing: company name, Reminder time ranger, purpose and business type.
     **/
    private Daily_report_DTO editReportMissingInfo;

    /** The edit report have start time reminder after end time. */
    private Daily_report_DTO editReportHaveStimeReminderAfterEtime;

    /** The edit report not persisted. */
    private Daily_report_DTO editReportNotPersisted;

    /** The daily report existed. */
    private Daily_report_DTO dailyReportExisted;

    /** The daily report not existed valid. */
    private Daily_report_DTO newDailyReportNotExisted;

    /**
     * Daily report existing with type is Other work.
     */
    private Daily_report_DTO dailyReportExistedTypeOtherWork;

    private Employee_mst currentEmployee;

    private Addresspoint_mst addressPoint;

    private Date daiWorkDate;

    private Date daiWorkSTimeValid;

    private Date daiWorkETimeValid;

    private Date dateFindReport;

    private List<Daily_activity_type> listActivityTypes;

    private List<Daily_report> listDailyReports;

    private List<Employee_mst> listEmployees;

    private List<Addresspoint_mst> listAddrs;

    private List<Company_mst> listComps;

    private List<Industry_big_mst> listIndustry;

    /** The list error messages when test with variable reportMissingInfoCase1. */
    private List<Message> listErrorMessages;

    private RepoResult<Daily_report> resultSearch;

    private static final long COUNTER_RESULT_ZERO = 0;

    private static final long COUNTER_RESULT_ONE = 1;

    private static final int RETURN_ONE_MESSAGE = 1;

    private static final int EMPLOYEE_CODE = 9999999;

    private static final int EMPLOYEE_CODE_2 = 33;

    private static final int NUMBER_EMPLOYEE_RESULT = 2;

    private static final int NUMBER_ADDRESS_POINT_RESULT = 2;

    private static final int NUMBER_COMPANY_RESULT = 2;

    private static final int NUMBER_INDUSTRY_BIG_RESULT = 2;

    private static final int NUMBER_ACTIVITY_TYPE_RESULT = 3;

    private static final short CODE_SHORT_TYPE_1 = 1;

    private static final short CODE_SHORT_TYPE_2 = 2;

    private static final short CODE_SHORT_TYPE_3 = 3;

    private static final String CONTENT_REPORT = "content";

    private static final String ADP_CODE = "13";

    private static final String ADP_CODE_2 = "14";

    private static final String ADP_NAME = "test";

    private static final String COMPANY_CODE_1 = "11";

    private static final String COMPANY_CODE_2 = "12";

    private static final String COMPANY_NAME = "testCompany";

    private static final String BIG_NAME = "testBigName";

    private static final String TYPE_NAME = "testType";

    @Before
    public void initDataBeforeRunningTest() {
        this.selectedDate = new Date();
        this.addressPoint = new Addresspoint_mst(DailyReportServiceTest.ADP_CODE);
        this.addressPoint.setAdp_name(DailyReportServiceTest.ADP_NAME);
        this.currentEmployee = new Employee_mst(9999999);
        this.currentEmployee.setAddresspoint_mst(this.addressPoint);
        this.currentEmployee.setEmp_delete_flg(false);
        this.currentEmployee.setEmp_condi_code(null);
        this.daiWorkDate = new Date();
        this.dateFindReport = new Date();

        this.reportHaveNotStime = new Daily_report_DTO();

        this.reportHaveNotEtime = new Daily_report_DTO();
        this.reportHaveNotEtime.setDai_work_stime(new Date());

        this.reportStimeAfterEtime = new Daily_report_DTO();
        this.reportStimeAfterEtime =
            this.createDailyReportDto(this.reportStimeAfterEtime, false, new Date(),
                this.getDateWithValue(12, 0, 0, 0), this.getDateWithValue(9, 0, 0, 0));

        this.reportNoContent = new Daily_report_DTO();
        this.reportNoContent =
            this.createDailyReportDto(this.reportNoContent, false, new Date(), this.getDateWithValue(9, 0, 0, 0),
                this.getDateWithValue(12, 0, 0, 0));

        this.editReportMissingInfo = new Daily_report_DTO();
        this.editReportMissingInfo =
            this.createDailyReportDto(this.editReportMissingInfo, true, new Date(), this.getDateWithValue(9, 0, 0, 0),
                this.getDateWithValue(10, 0, 0, 0));
        this.editReportMissingInfo.setDai_business_details(DailyReportServiceTest.CONTENT_REPORT);
        this.editReportMissingInfo.setDai_rimaindar_flg(true);

        this.editReportHaveStimeReminderAfterEtime = new Daily_report_DTO();
        this.editReportHaveStimeReminderAfterEtime =
            this.createDailyReportDto(this.editReportHaveStimeReminderAfterEtime, true, new Date(),
                this.getDateWithValue(9, 0, 0, 0), this.getDateWithValue(10, 0, 0, 0));
        this.editReportHaveStimeReminderAfterEtime.setDai_business_details(DailyReportServiceTest.CONTENT_REPORT);
        this.editReportHaveStimeReminderAfterEtime.setDai_rimaindar_flg(true);
        this.editReportHaveStimeReminderAfterEtime.setDaily_activity_type(new Daily_activity_type());
        this.editReportHaveStimeReminderAfterEtime.setCompany_mst(new Company_mst());
        this.editReportHaveStimeReminderAfterEtime.setDai_bus_code((short) 1);
        this.editReportHaveStimeReminderAfterEtime.setDai_summary_stime(this.getDateWithValue(10, 0, 0, 0));
        this.editReportHaveStimeReminderAfterEtime.setDai_summary_etime(this.getDateWithValue(9, 0, 0, 0));

        this.editReportNotPersisted = new Daily_report_DTO();
        this.editReportNotPersisted =
            this.createDailyReportDto(this.editReportNotPersisted, false, new Date(),
                this.getDateWithValue(9, 0, 0, 0), this.getDateWithValue(10, 0, 0, 0));
        this.editReportNotPersisted.setDai_business_details(DailyReportServiceTest.CONTENT_REPORT);
        this.editReportNotPersisted.setPersisted(false);

        this.reportOverlapCase = new Daily_report_DTO();
        this.reportOverlapCase =
            this.createDailyReportDto(this.reportOverlapCase, true, new Date(), this.getDateWithValue(9, 0, 0, 0),
                this.getDateWithValue(10, 0, 0, 0));
        this.reportOverlapCase.setDai_business_details(DailyReportServiceTest.CONTENT_REPORT);
        this.reportOverlapCase.setDai_rimaindar_flg(false);
        this.reportOverlapCase.setDaily_activity_type(new Daily_activity_type());
        this.reportOverlapCase.setCompany_mst(new Company_mst());
        this.reportOverlapCase.setDai_bus_code((short) 1);
        this.reportOverlapCase.setPersisted(true);
        this.reportOverlapCase.setEmployee_mst(new Employee_mst(DailyReportServiceTest.EMPLOYEE_CODE));
        this.reportOverlapCase.setAddresspoint_mst(new Addresspoint_mst(DailyReportServiceTest.ADP_CODE));

        this.dailyReportExisted = new Daily_report_DTO();
        this.dailyReportExisted =
            this.createDailyReportDto(this.dailyReportExisted, false, new Date(), this.getDateWithValue(9, 0, 0, 0),
                this.getDateWithValue(10, 0, 0, 0));
        this.dailyReportExisted.setDai_business_details(DailyReportServiceTest.CONTENT_REPORT);
        this.dailyReportExisted.setPersisted(true);

        this.newDailyReportNotExisted = new Daily_report_DTO();
        this.newDailyReportNotExisted =
            this.createDailyReportDto(this.newDailyReportNotExisted, true, new Date(),
                this.getDateWithValue(9, 0, 0, 0), this.getDateWithValue(10, 0, 0, 0));
        this.newDailyReportNotExisted.setDai_business_details(DailyReportServiceTest.CONTENT_REPORT);
        this.newDailyReportNotExisted.setDai_rimaindar_flg(false);
        this.newDailyReportNotExisted.setDaily_activity_type(new Daily_activity_type());
        this.newDailyReportNotExisted.setCompany_mst(new Company_mst());
        this.newDailyReportNotExisted.setDai_bus_code((short) 1);
        this.newDailyReportNotExisted.setPersisted(false);
        this.newDailyReportNotExisted.setEmployee_mst(this.currentEmployee);
        this.newDailyReportNotExisted.setAddresspoint_mst(this.addressPoint);

        this.listErrorMessages =
            Arrays.asList(ErrorMessage.daily_013_start_time_and_end_time_are_required(),
                ErrorMessage.daily_016_purpose_is_required(), ErrorMessage.daily_017_business_type_is_required());

        this.daiWorkSTimeValid = this.getDateWithValue(11, 0, 0, 0);
        this.daiWorkETimeValid = this.getDateWithValue(12, 0, 0, 0);

        this.dailyReportValid =
            new Daily_report(this.currentEmployee, this.addressPoint, this.daiWorkDate,
                DateUtils.removeDate(this.daiWorkSTimeValid), DateUtils.removeDate(this.daiWorkETimeValid));

        Daily_activity_type type1 = new Daily_activity_type(DailyReportServiceTest.CODE_SHORT_TYPE_1);
        type1.setDat_name(DailyReportServiceTest.TYPE_NAME);
        Daily_activity_type type2 = new Daily_activity_type(DailyReportServiceTest.CODE_SHORT_TYPE_2);
        type2.setDat_name(DailyReportServiceTest.TYPE_NAME);
        Daily_activity_type type3 = new Daily_activity_type(DailyReportServiceTest.CODE_SHORT_TYPE_3);
        type3.setDat_name(DailyReportServiceTest.TYPE_NAME);
        this.listActivityTypes = Arrays.asList(type1, type2, type3);

        this.listDailyReports = Arrays.asList(this.dailyReportValid);

        this.dailyReportExistedTypeOtherWork = new Daily_report_DTO();
        BeanCopier.copy(this.dailyReportValid, this.dailyReportExistedTypeOtherWork);
        this.dailyReportExistedTypeOtherWork.setDai_work_type(false);
        this.dailyReportExistedTypeOtherWork.setDai_business_details(DailyReportServiceTest.CONTENT_REPORT);
        this.dailyReportExistedTypeOtherWork.setPersisted(true);

        Employee_mst emp2 = new Employee_mst(DailyReportServiceTest.EMPLOYEE_CODE_2);
        emp2.setEmp_delete_flg(false);
        emp2.setEmp_condi_code('0');
        this.listEmployees = Arrays.asList(this.currentEmployee, emp2);

        Addresspoint_mst addr2 = new Addresspoint_mst(DailyReportServiceTest.ADP_CODE_2);
        addr2.setAdp_name(DailyReportServiceTest.ADP_NAME);
        this.listAddrs = Arrays.asList(this.addressPoint, addr2);

        Company_mst comp1 = new Company_mst(DailyReportServiceTest.COMPANY_CODE_1);
        comp1.setCom_company_name(DailyReportServiceTest.COMPANY_NAME);
        Company_mst comp2 = new Company_mst(DailyReportServiceTest.COMPANY_CODE_2);
        comp2.setCom_company_name(DailyReportServiceTest.COMPANY_NAME);
        this.listComps = Arrays.asList(comp1, comp2);

        Industry_big_mst ind1 = new Industry_big_mst(DailyReportServiceTest.CODE_SHORT_TYPE_1);
        ind1.setBig_name(DailyReportServiceTest.BIG_NAME);
        Industry_big_mst ind2 = new Industry_big_mst(DailyReportServiceTest.CODE_SHORT_TYPE_2);
        ind1.setBig_name(DailyReportServiceTest.BIG_NAME);
        this.listIndustry = Arrays.asList(ind1, ind2);

        this.dailyReportWithBusCode = new Daily_report();
        this.dailyReportWithBusCode.setDai_bus_code(DailyReportServiceTest.CODE_SHORT_TYPE_1);

        this.resultSearch = new RepoResult<Daily_report>();
        this.resultSearch.setListItems(this.listDailyReports);
        this.resultSearch.setTotalItems(DailyReportServiceTest.COUNTER_RESULT_ONE);
    }

    private Daily_report_DTO createDailyReportDto(final Daily_report_DTO report, final boolean type,
        final Date workDate, final Date sTime, final Date eTime) {
        report.setDai_work_type(type);
        report.setDai_work_date(workDate);
        report.setDai_work_stime(sTime);
        report.setDai_work_etime(eTime);
        return report;
    }

    private Date getDateWithValue(final int hourOfDay, final int minute, final int second, final int millisecond) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND, millisecond);
        return cal.getTime();
    }

    @Test
    public void testAddNewReport() {
        ServiceResult<Daily_report_DTO> resultAddNewReport = this.service.addNewReport(this.selectedDate);

        new Verifications() {
            {
                Assertions.assertThat(resultAddNewReport).isNotNull();
                Assertions.assertThat(resultAddNewReport.isSuccessful()).isTrue();
                Assertions.assertThat(resultAddNewReport.getData()).isNotNull();
                Assertions.assertThat(resultAddNewReport.getMessages()).isEmpty();
                Assertions.assertThat(resultAddNewReport.getData().getDai_work_type()).isTrue();
                Assertions.assertThat(resultAddNewReport.getData().getDai_work_date()).isEqualTo(
                    DailyReportServiceTest.this.selectedDate);
                Assertions.assertThat(resultAddNewReport.getData().getDai_rimaindar_flg()).isFalse();
                Assertions.assertThat(resultAddNewReport.getData().getDai_anken_flg()).isFalse();
                Assertions.assertThat(resultAddNewReport.getData().getDai_matter_flg()).isFalse();
            }
        };
    }

    @Test
    public void testGetListReport() {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo.getReportByWorkDateAndEmployeeCode(
                    DateUtils.removeTime(DailyReportServiceTest.this.dateFindReport), this.anyInt).getResultList();
                this.result = DailyReportServiceTest.this.listDailyReports;
            }
        };
        List<Daily_report> listDailyReports =
            this.service.getListReport(this.dateFindReport, DailyReportServiceTest.EMPLOYEE_CODE);

        Assertions.assertThat(listDailyReports).isNotNull();
        Assertions.assertThat(listDailyReports.size()).isEqualTo(DailyReportServiceTest.this.listDailyReports.size());
        for (int i = 0; i < listDailyReports.size(); i++) {
            Assertions.assertThat(listDailyReports.get(i).getDai_work_date()).isEqualTo(
                DailyReportServiceTest.this.listDailyReports.get(i).getDai_work_date());
            Assertions.assertThat(DateUtils.removeDate(listDailyReports.get(i).getDai_work_stime())).isEqualTo(
                DateUtils.removeDate(DailyReportServiceTest.this.listDailyReports.get(i).getDai_work_stime()));
            Assertions.assertThat(DateUtils.removeDate(listDailyReports.get(i).getDai_work_etime())).isEqualTo(
                DateUtils.removeDate(DailyReportServiceTest.this.listDailyReports.get(i).getDai_work_etime()));
            Assertions.assertThat(listDailyReports.get(i).getDai_employee_code()).isEqualTo(
                DailyReportServiceTest.this.listDailyReports.get(i).getDai_employee_code());
        }
    }

    @Test
    public void testGetActivityTypes() {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyActivityTypeRepo.getAllDailyActivityTypeAndOrderByDatCodeAsc()
                    .getResultList();
                this.result = DailyReportServiceTest.this.listActivityTypes;

            }
        };
        List<Daily_activity_type> listActivityTypes = this.service.getActivityTypes();

        Assertions.assertThat(listActivityTypes).isNotNull();
        Assertions.assertThat(listActivityTypes.size()).isEqualTo(DailyReportServiceTest.NUMBER_ACTIVITY_TYPE_RESULT);
        for (int i = 0; i < listActivityTypes.size(); i++) {
            Assertions.assertThat(listActivityTypes.get(i).getDat_code()).isEqualTo(
                DailyReportServiceTest.this.listActivityTypes.get(i).getDat_code());
            Assertions.assertThat(listActivityTypes.get(i).getDat_name()).isEqualTo(
                DailyReportServiceTest.this.listActivityTypes.get(i).getDat_name());
        }
    }

    @Test
    public void testDeleteReportWithCaseSuccess() {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo
                    .findBy(DailyReportServiceTest.this.dailyReportValid.getPk());
                this.result = DailyReportServiceTest.this.dailyReportValid;
            }
        };
        ServiceResult<Daily_report> result = this.service.deleteReport(this.dailyReportValid);
        new Verifications() {
            {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.isSuccessful()).isTrue();
                Assertions.assertThat(result.getMessages()).isEmpty();
            }
        };
    }

    @Test
    public void testDeleteReportWithCaseException() {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo
                    .findBy(DailyReportServiceTest.this.dailyReportValid.getPk());
                this.result = new PersistenceException();
            }
        };
        ServiceResult<Daily_report> result = this.service.deleteReport(this.dailyReportValid);
        new Verifications() {
            {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.isNotSuccessful()).isTrue();
                Assertions.assertThat(result.getMessages().size()).isEqualTo(DailyReportServiceTest.RETURN_ONE_MESSAGE);
                Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                    ErrorMessage.daily_002_delete_unsucessfully().getMessageCode());
            }
        };
    }

    @Test
    public void testDeleteListDailyReport() {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo
                    .findBy(DailyReportServiceTest.this.dailyReportValid.getPk());
                this.result = DailyReportServiceTest.this.dailyReportValid;
            }
        };
        ServiceResult<Daily_report> result = this.service.deleteReport(this.listDailyReports);
        new Verifications() {
            {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.isSuccessful()).isTrue();
                Assertions.assertThat(result.getMessages()).isEmpty();
            }
        };
    }

    @Test
    public void testSaveWhenEditReportSuccessWithWorkTypeOther() {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo
                    .findBy(DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getPk());
                this.result = DailyReportServiceTest.this.dailyReportExistedTypeOtherWork;

                DailyReportServiceTest.this.dailyreportRepo.countDailyReportOverlappedCaseEdit(this.anyInt,
                    this.anyString, DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_work_date(),
                    DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_work_stime(),
                    DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_work_etime(),
                    DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_work_date(),
                    DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_work_stime(),
                    DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_work_etime());
                this.result = DailyReportServiceTest.COUNTER_RESULT_ZERO;
            }
        };
        ServiceResult<Daily_report> result = this.service.saveWhenEditReport(this.dailyReportExistedTypeOtherWork);
        new Verifications() {
            {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.isSuccessful()).isTrue();
                Assertions.assertThat(result.getMessages()).isNotEmpty();
                Assertions.assertThat(result.getMessages().size()).isEqualTo(DailyReportServiceTest.RETURN_ONE_MESSAGE);
                Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                    InfoMessage.daily_001_save_successfully().getMessageCode());
                Assertions.assertThat(result.getData()).isNotNull();
                Assertions.assertThat(result.getData().getDai_work_type()).isFalse();
                Assertions.assertThat(result.getData().getDai_employee_code()).isEqualTo(
                    DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_employee_code());
                Assertions.assertThat(result.getData().getDai_point_code()).isEqualTo(
                    DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_point_code());
                Assertions.assertThat(result.getData().getDai_work_date()).isEqualTo(
                    DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_work_date());
                Assertions.assertThat(result.getData().getDai_work_stime()).isEqualTo(
                    DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_work_stime());
                Assertions.assertThat(result.getData().getDai_work_etime()).isEqualTo(
                    DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_work_etime());
            }
        };
    }

    @Test
    public void testSaveWhenEditReportWhenNotFoundCurrentReportToDelete() {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo
                    .findBy(DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getPk());
                this.result =
                    new Object[] {DailyReportServiceTest.this.dailyReportExistedTypeOtherWork,
                        new PersistenceException()};

                DailyReportServiceTest.this.dailyreportRepo.countDailyReportOverlappedCaseEdit(this.anyInt,
                    this.anyString, DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_work_date(),
                    DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_work_stime(),
                    DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_work_etime(),
                    DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_work_date(),
                    DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_work_stime(),
                    DailyReportServiceTest.this.dailyReportExistedTypeOtherWork.getDai_work_etime());
                this.result = DailyReportServiceTest.COUNTER_RESULT_ZERO;

            }
        };
        ServiceResult<Daily_report> result = this.service.saveWhenEditReport(this.dailyReportExistedTypeOtherWork);
        new Verifications() {
            {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.isNotSuccessful()).isTrue();
                Assertions.assertThat(result.getMessages()).isNotEmpty();
                Assertions.assertThat(result.getMessages().size()).isEqualTo(DailyReportServiceTest.RETURN_ONE_MESSAGE);
                Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                    ErrorMessage.daily_002_delete_unsucessfully().getMessageCode());
            }
        };
    }

    @Test
    public void testSaveWhenEditReportValidateFailedNotSetStartWorkTime() {
        ServiceResult<Daily_report> result = this.service.saveWhenEditReport(this.reportHaveNotStime);
        new Verifications() {
            {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.isNotSuccessful()).isTrue();
                Assertions.assertThat(result.getMessages()).isNotEmpty();
                Assertions.assertThat(result.getMessages().size()).isEqualTo(DailyReportServiceTest.RETURN_ONE_MESSAGE);
                Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                    ErrorMessage.daily_014_working_time_is_required().getMessageCode());
            }
        };
    }

    @Test
    public void testSaveWhenEditReportValidateFailedNotSetEndWorkTime() {
        ServiceResult<Daily_report> result = this.service.saveWhenEditReport(this.reportHaveNotEtime);
        new Verifications() {
            {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.isNotSuccessful()).isTrue();
                Assertions.assertThat(result.getMessages()).isNotEmpty();
                Assertions.assertThat(result.getMessages().size()).isEqualTo(DailyReportServiceTest.RETURN_ONE_MESSAGE);
                Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                    ErrorMessage.daily_014_working_time_is_required().getMessageCode());
            }
        };
    }

    @Test
    public void testSaveWhenEditReportValidateFailedStimeAfterEtime() {
        ServiceResult<Daily_report> result = this.service.saveWhenEditReport(this.reportStimeAfterEtime);
        new Verifications() {
            {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.isNotSuccessful()).isTrue();
                Assertions.assertThat(result.getMessages()).isNotEmpty();
                Assertions.assertThat(result.getMessages().size()).isEqualTo(DailyReportServiceTest.RETURN_ONE_MESSAGE);
                Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                    ErrorMessage.daily_004_the_start_job_time_cannot_greater_end_job_time().getMessageCode());
            }
        };
    }

    @Test
    public void testSaveWhenEditReportValidateFailedNoContent() {
        ServiceResult<Daily_report> result = this.service.saveWhenEditReport(this.reportNoContent);
        new Verifications() {
            {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.isNotSuccessful()).isTrue();
                Assertions.assertThat(result.getMessages()).isNotEmpty();
                Assertions.assertThat(result.getMessages().size()).isEqualTo(DailyReportServiceTest.RETURN_ONE_MESSAGE);
                Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                    ErrorMessage.daily_006_content_is_required().getMessageCode());
            }
        };
    }

    @Test
    public void testSaveWhenEditReportValidateMissingInfoCase1() {
        ServiceResult<Daily_report> result = this.service.saveWhenEditReport(this.editReportMissingInfo);
        new Verifications() {
            {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.isNotSuccessful()).isTrue();
                Assertions.assertThat(result.getMessages()).isNotEmpty();
                Assertions.assertThat(result.getMessages().size()).isEqualTo(
                    DailyReportServiceTest.this.listErrorMessages.size());
            }
        };
        for (int i = 0; i < result.getMessages().size(); i++) {
            Assertions.assertThat(result.getMessages().get(i).getMessageCode()).isEqualTo(
                DailyReportServiceTest.this.listErrorMessages.get(i).getMessageCode());
        }
    }

    @Test
    public void testSaveWhenEditReportHaveReminderStimeAfterEtime() {
        ServiceResult<Daily_report> result =
            this.service.saveWhenEditReport(this.editReportHaveStimeReminderAfterEtime);
        new Verifications() {
            {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.isNotSuccessful()).isTrue();
                Assertions.assertThat(result.getMessages()).isNotEmpty();
                Assertions.assertThat(result.getMessages().size()).isEqualTo(DailyReportServiceTest.RETURN_ONE_MESSAGE);
                Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                    ErrorMessage.daily_011_the_start_time_reminder_cannot_greater_end_time_reminder().getMessageCode());
            }
        };
    }

    @Test
    public void testSaveWhenEditReportNotPersisted() {
        ServiceResult<Daily_report> result = this.service.saveWhenEditReport(this.editReportNotPersisted);
        new Verifications() {
            {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.isNotSuccessful()).isTrue();
                Assertions.assertThat(result.getMessages()).isNotEmpty();
                Assertions.assertThat(result.getMessages().size()).isEqualTo(DailyReportServiceTest.RETURN_ONE_MESSAGE);
                Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                    ErrorMessage.daily_009_report_does_not_exist_please_add_new_report().getMessageCode());
            }
        };
    }

    @Test
    public void testSaveWhenEditReportNotFoundInDB() {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo.findBy(DailyReportServiceTest.this.reportOverlapCase
                    .getPk());
                this.result = null;
            }
        };
        ServiceResult<Daily_report> result = this.service.saveWhenEditReport(this.reportOverlapCase);
        new Verifications() {
            {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.isNotSuccessful()).isTrue();
                Assertions.assertThat(result.getMessages()).isNotEmpty();
                Assertions.assertThat(result.getMessages().size()).isEqualTo(DailyReportServiceTest.RETURN_ONE_MESSAGE);
                Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                    ErrorMessage.daily_008_daily_report_not_found().getMessageCode());
            }
        };
    }

    @Test
    public void testSaveWhenEditIsOverlapCase(@Mocked final Instance instance) {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo.findBy(DailyReportServiceTest.this.reportOverlapCase
                    .getPk());
                this.result = DailyReportServiceTest.this.reportOverlapCase;

                DailyReportServiceTest.this.dailyreportRepo.countDailyReportOverlappedCaseEdit(this.anyInt,
                    this.anyString, DailyReportServiceTest.this.reportOverlapCase.getDai_work_date(),
                    DailyReportServiceTest.this.reportOverlapCase.getDai_work_stime(),
                    DailyReportServiceTest.this.reportOverlapCase.getDai_work_etime(),
                    DailyReportServiceTest.this.reportOverlapCase.getDai_work_date(),
                    DailyReportServiceTest.this.reportOverlapCase.getDai_work_stime(),
                    DailyReportServiceTest.this.reportOverlapCase.getDai_work_etime());
                this.result = DailyReportServiceTest.COUNTER_RESULT_ONE;
            }
        };
        ServiceResult<Daily_report> result = this.service.saveWhenEditReport(this.reportOverlapCase);
        new Verifications() {
            {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.isNotSuccessful()).isTrue();
                Assertions.assertThat(result.getMessages()).isNotEmpty();
                Assertions.assertThat(result.getMessages().size()).isEqualTo(DailyReportServiceTest.RETURN_ONE_MESSAGE);
                Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                    ErrorMessage.daily_007_period_is_overlapped_with_existing_data().getMessageCode());
                Assertions.assertThat(result.getData()).isNotNull();
                Assertions.assertThat(result.getData().getDai_work_type()).isTrue();
                Assertions.assertThat(result.getData().getDai_employee_code()).isEqualTo(
                    DailyReportServiceTest.this.reportOverlapCase.getDai_employee_code());
                Assertions.assertThat(result.getData().getDai_point_code()).isEqualTo(
                    DailyReportServiceTest.this.reportOverlapCase.getDai_point_code());
                Assertions.assertThat(result.getData().getDai_work_date()).isEqualTo(
                    DailyReportServiceTest.this.reportOverlapCase.getDai_work_date());
                Assertions.assertThat(result.getData().getDai_work_stime()).isEqualTo(
                    DailyReportServiceTest.this.reportOverlapCase.getDai_work_stime());
                Assertions.assertThat(result.getData().getDai_work_etime()).isEqualTo(
                    DailyReportServiceTest.this.reportOverlapCase.getDai_work_etime());
            }
        };
    }

    @Test
    public void testSaveWhenAddNewValidateFailed() {
        ServiceResult<Daily_report> result =
            this.service.saveWhenAddNewReport(this.reportHaveNotStime, new Employee_mst(), new Addresspoint_mst());
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.isNotSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(DailyReportServiceTest.RETURN_ONE_MESSAGE);
        Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
            ErrorMessage.daily_014_working_time_is_required().getMessageCode());
    }

    @Test
    public void testSaveWhenAddNewReportPersisted() {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo.findBy(DailyReportServiceTest.this.dailyReportExisted
                    .getPk());
                this.result = new Daily_report();
            }
        };
        ServiceResult<Daily_report> result =
            this.service.saveWhenAddNewReport(this.dailyReportExisted, new Employee_mst(), new Addresspoint_mst());
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.isNotSuccessful()).isTrue();
        Assertions.assertThat(result.getData()).isNull();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(DailyReportServiceTest.RETURN_ONE_MESSAGE);
        Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
            ErrorMessage.daily_007_period_is_overlapped_with_existing_data().getMessageCode());
    }

    @Test
    public void testSaveWhenAddNewReportOverlapCase(@Mocked final Instance instance) {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo.countDailyReportOverlappedCaseAddNew(this.anyInt,
                    this.anyString, this.withAny(new Date()), this.withAny(new Date()), this.withAny(new Date()));
                this.result = DailyReportServiceTest.COUNTER_RESULT_ONE;
            }
        };

        ServiceResult<Daily_report> result =
            this.service.saveWhenAddNewReport(this.newDailyReportNotExisted, this.currentEmployee, this.addressPoint);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.isNotSuccessful()).isTrue();
        Assertions.assertThat(result.getData()).isNotNull();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(DailyReportServiceTest.RETURN_ONE_MESSAGE);
        Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
            ErrorMessage.daily_007_period_is_overlapped_with_existing_data().getMessageCode());
        Assertions.assertThat(result.getData().getDai_work_type()).isTrue();
        Assertions.assertThat(result.getData().getDai_work_date()).isEqualTo(
            this.newDailyReportNotExisted.getDai_work_date());
        Assertions.assertThat(DateUtils.removeDate(result.getData().getDai_work_stime())).isEqualTo(
            DateUtils.removeDate(this.newDailyReportNotExisted.getDai_work_stime()));
        Assertions.assertThat(DateUtils.removeDate(result.getData().getDai_work_etime())).isEqualTo(
            DateUtils.removeDate(this.newDailyReportNotExisted.getDai_work_etime()));
        Assertions.assertThat(result.getData().getDai_bus_code()).isEqualTo(
            this.newDailyReportNotExisted.getDai_bus_code());
        Assertions.assertThat(result.getData().getDai_business_details()).isEqualTo(
            this.newDailyReportNotExisted.getDai_business_details());
    }

    @Test
    public void testSaveWhenAddNewReportSuccess(@Mocked final Instance instance) {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo.countDailyReportOverlappedCaseAddNew(this.anyInt,
                    this.anyString, this.withAny(new Date()), this.withAny(new Date()), this.withAny(new Date()));
                this.result = DailyReportServiceTest.COUNTER_RESULT_ZERO;
            }
        };

        ServiceResult<Daily_report> result =
            this.service.saveWhenAddNewReport(this.newDailyReportNotExisted, this.currentEmployee, this.addressPoint);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.isSuccessful()).isTrue();
        Assertions.assertThat(result.getData()).isNotNull();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(DailyReportServiceTest.RETURN_ONE_MESSAGE);
        Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
            InfoMessage.daily_001_save_successfully().getMessageCode());
        Assertions.assertThat(result.getData().getDai_work_type()).isTrue();
        Assertions.assertThat(result.getData().getDai_work_date()).isEqualTo(
            this.newDailyReportNotExisted.getDai_work_date());
        Assertions.assertThat(DateUtils.removeDate(result.getData().getDai_work_stime())).isEqualTo(
            DateUtils.removeDate(this.newDailyReportNotExisted.getDai_work_stime()));
        Assertions.assertThat(DateUtils.removeDate(result.getData().getDai_work_etime())).isEqualTo(
            DateUtils.removeDate(this.newDailyReportNotExisted.getDai_work_etime()));
        Assertions.assertThat(result.getData().getDai_bus_code()).isEqualTo(
            this.newDailyReportNotExisted.getDai_bus_code());
        Assertions.assertThat(result.getData().getDai_business_details()).isEqualTo(
            this.newDailyReportNotExisted.getDai_business_details());
    }

    @Test
    public void testGetAllActiveEmployee() {
        new Expectations() {
            {
                DailyReportServiceTest.this.employeeRepo.getAllActiveEmpByCondiCodeIsNullOrZero().getResultList();
                this.result = DailyReportServiceTest.this.listEmployees;
            }
        };

        List<Employee_mst> listEmp = this.service.getAllActiveEmployee();
        Assertions.assertThat(listEmp).isNotEmpty();
        Assertions.assertThat(listEmp.size()).isEqualTo(DailyReportServiceTest.NUMBER_EMPLOYEE_RESULT);
        for (int i = 0; i < listEmp.size(); i++) {
            Assertions.assertThat(listEmp.get(i).getEmp_code()).isEqualTo(this.listEmployees.get(i).getEmp_code());
            Assertions.assertThat(listEmp.get(i).getEmp_delete_flg()).isFalse();
            Assertions.assertThat(this.isEmpCondiCodeNullOrZero(listEmp.get(i).getEmp_condi_code())).isTrue();
        }
    }

    private boolean isEmpCondiCodeNullOrZero(final Character emp_condi_code) {
        return (emp_condi_code == null) || emp_condi_code.equals('0');
    }

    @Test
    public void testGetAllNotDeletedEmployee() {
        new Expectations() {
            {
                DailyReportServiceTest.this.employeeRepo.getAllActiveEmp().getResultList();
                this.result = DailyReportServiceTest.this.listEmployees;
            }
        };

        List<Employee_mst> listEmp = this.service.getAllNotDeletedEmployee();
        Assertions.assertThat(listEmp).isNotEmpty();
        Assertions.assertThat(listEmp.size()).isEqualTo(DailyReportServiceTest.NUMBER_EMPLOYEE_RESULT);
        for (int i = 0; i < listEmp.size(); i++) {
            Assertions.assertThat(listEmp.get(i).getEmp_code()).isEqualTo(this.listEmployees.get(i).getEmp_code());
            Assertions.assertThat(listEmp.get(i).getEmp_delete_flg()).isFalse();
        }
    }

    @Test
    public void testGetAllAddressPoint() {
        new Expectations() {
            {
                DailyReportServiceTest.this.addressRepo.findAll();
                this.result = DailyReportServiceTest.this.listAddrs;
            }
        };

        List<Addresspoint_mst> addrs = this.service.getAllAddressPoint();
        Assertions.assertThat(addrs).isNotEmpty();
        Assertions.assertThat(addrs.size()).isEqualTo(DailyReportServiceTest.NUMBER_ADDRESS_POINT_RESULT);
        for (int i = 0; i < addrs.size(); i++) {
            Assertions.assertThat(addrs.get(i).getAdp_code()).isEqualTo(this.listAddrs.get(i).getAdp_code());
            Assertions.assertThat(addrs.get(i).getAdp_name()).isEqualTo(this.listAddrs.get(i).getAdp_name());
        }
    }

    @Test
    public void testGetListCompany() {
        new Expectations() {
            {
                DailyReportServiceTest.this.companyRepo.findAll();
                this.result = DailyReportServiceTest.this.listComps;
            }
        };

        List<Company_mst> listComps = this.service.getListCompany();
        Assertions.assertThat(listComps).isNotEmpty();
        Assertions.assertThat(listComps.size()).isEqualTo(DailyReportServiceTest.NUMBER_COMPANY_RESULT);
        for (int i = 0; i < listComps.size(); i++) {
            Assertions.assertThat(listComps.get(i).getCom_company_code()).isEqualTo(
                this.listComps.get(i).getCom_company_code());
            Assertions.assertThat(listComps.get(i).getCom_company_name()).isEqualTo(
                this.listComps.get(i).getCom_company_name());
        }
    }

    @Test
    public void testGetListPositionEmployee() {
        new Expectations() {
            {
                DailyReportServiceTest.this.addressRepo.getListPositionOfEmployee().getResultList();
                this.result = DailyReportServiceTest.this.listAddrs;
            }
        };

        List<Addresspoint_mst> addrs = this.service.getListPositionEmployee();
        Assertions.assertThat(addrs).isNotEmpty();
        Assertions.assertThat(addrs.size()).isEqualTo(DailyReportServiceTest.NUMBER_ADDRESS_POINT_RESULT);
        for (int i = 0; i < addrs.size(); i++) {
            Assertions.assertThat(addrs.get(i).getAdp_code()).isEqualTo(this.listAddrs.get(i).getAdp_code());
            Assertions.assertThat(addrs.get(i).getAdp_name()).isEqualTo(this.listAddrs.get(i).getAdp_name());
        }
    }

    @Test
    public void testGetListActiveEmployeeByAddrPointIncludeRetiredByAdpCode() {
        new Expectations() {
            {
                DailyReportServiceTest.this.employeeRepo.getAllActiveEmpNotRetiredByAdpCode(this.anyString)
                    .getResultList();
                this.result = DailyReportServiceTest.this.listEmployees;
            }
        };

        List<Employee_mst> listEmp =
            this.service.getListActiveEmployeeByAddrPoint(DailyReportServiceTest.ADP_CODE, false);
        Assertions.assertThat(listEmp).isNotEmpty();
        Assertions.assertThat(listEmp.size()).isEqualTo(DailyReportServiceTest.NUMBER_EMPLOYEE_RESULT);
        for (int i = 0; i < listEmp.size(); i++) {
            Assertions.assertThat(listEmp.get(i).getEmp_code()).isEqualTo(this.listEmployees.get(i).getEmp_code());
            Assertions.assertThat(listEmp.get(i).getEmp_delete_flg()).isFalse();
        }
    }

    @Test
    public void testGetListActiveEmployeeByAddrPointByAdpCode() {
        new Expectations() {
            {
                DailyReportServiceTest.this.employeeRepo.getAllActiveEmpByAdpCode(this.anyString).getResultList();
                this.result = DailyReportServiceTest.this.listEmployees;
            }
        };

        List<Employee_mst> listEmp =
            this.service.getListActiveEmployeeByAddrPoint(DailyReportServiceTest.ADP_CODE, true);
        Assertions.assertThat(listEmp).isNotEmpty();
        Assertions.assertThat(listEmp.size()).isEqualTo(DailyReportServiceTest.NUMBER_EMPLOYEE_RESULT);
        for (int i = 0; i < listEmp.size(); i++) {
            Assertions.assertThat(listEmp.get(i).getEmp_code()).isEqualTo(this.listEmployees.get(i).getEmp_code());
            Assertions.assertThat(listEmp.get(i).getEmp_delete_flg()).isFalse();
        }
    }

    @Test
    public void testGetListActiveEmployeeByAddrPointIncludeRetired() {
        new Expectations() {
            {
                DailyReportServiceTest.this.employeeRepo.getAllActiveEmpNotRetired().getResultList();
                this.result = DailyReportServiceTest.this.listEmployees;
            }
        };

        List<Employee_mst> listEmp = this.service.getListActiveEmployeeByAddrPoint(StringUtils.EMPTY_STRING, false);
        Assertions.assertThat(listEmp).isNotEmpty();
        Assertions.assertThat(listEmp.size()).isEqualTo(DailyReportServiceTest.NUMBER_EMPLOYEE_RESULT);
        for (int i = 0; i < listEmp.size(); i++) {
            Assertions.assertThat(listEmp.get(i).getEmp_code()).isEqualTo(this.listEmployees.get(i).getEmp_code());
            Assertions.assertThat(listEmp.get(i).getEmp_delete_flg()).isFalse();
        }
    }

    @Test
    public void testGetListActiveEmployeeByAddrPoint() {
        new Expectations() {
            {
                DailyReportServiceTest.this.employeeRepo.getAllActiveEmpDistinctJoinWithAddressPoint().getResultList();
                this.result = DailyReportServiceTest.this.listEmployees;
            }
        };

        List<Employee_mst> listEmp = this.service.getListActiveEmployeeByAddrPoint(StringUtils.EMPTY_STRING, true);
        Assertions.assertThat(listEmp).isNotEmpty();
        Assertions.assertThat(listEmp.size()).isEqualTo(DailyReportServiceTest.NUMBER_EMPLOYEE_RESULT);
        for (int i = 0; i < listEmp.size(); i++) {
            Assertions.assertThat(listEmp.get(i).getEmp_code()).isEqualTo(this.listEmployees.get(i).getEmp_code());
            Assertions.assertThat(listEmp.get(i).getEmp_delete_flg()).isFalse();
        }
    }

    @Test
    public void testSearchEmployeeReportNoEmployeeCode() {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo.getReportByWorkDate(this.withAny(new Date()))
                    .getResultList();
                this.result = DailyReportServiceTest.this.listDailyReports;
            }
        };
        List<Daily_report> listDailyReports = this.service.searchEmployeeReport(new Date(), null);

        Assertions.assertThat(listDailyReports).isNotNull();
        Assertions.assertThat(listDailyReports.size()).isEqualTo(DailyReportServiceTest.this.listDailyReports.size());
        for (int i = 0; i < listDailyReports.size(); i++) {
            Assertions.assertThat(listDailyReports.get(i).getDai_work_date()).isEqualTo(
                DailyReportServiceTest.this.listDailyReports.get(i).getDai_work_date());
            Assertions.assertThat(DateUtils.removeDate(listDailyReports.get(i).getDai_work_stime())).isEqualTo(
                DateUtils.removeDate(DailyReportServiceTest.this.listDailyReports.get(i).getDai_work_stime()));
            Assertions.assertThat(DateUtils.removeDate(listDailyReports.get(i).getDai_work_etime())).isEqualTo(
                DateUtils.removeDate(DailyReportServiceTest.this.listDailyReports.get(i).getDai_work_etime()));
            Assertions.assertThat(listDailyReports.get(i).getDai_employee_code()).isEqualTo(
                DailyReportServiceTest.this.listDailyReports.get(i).getDai_employee_code());
        }
    }

    @Test
    public void testSearchEmployeeReportWithEmployeeCode() {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo.getReportByWorkDateAndEmployeeCode(
                    this.withAny(new Date()), this.anyInt).getResultList();
                this.result = DailyReportServiceTest.this.listDailyReports;
            }
        };
        List<Daily_report> listDailyReports = this.service.searchEmployeeReport(new Date(), this.currentEmployee);

        Assertions.assertThat(listDailyReports).isNotNull();
        Assertions.assertThat(listDailyReports.size()).isEqualTo(DailyReportServiceTest.this.listDailyReports.size());
        for (int i = 0; i < listDailyReports.size(); i++) {
            Assertions.assertThat(listDailyReports.get(i).getDai_work_date()).isEqualTo(
                DailyReportServiceTest.this.listDailyReports.get(i).getDai_work_date());
            Assertions.assertThat(DateUtils.removeDate(listDailyReports.get(i).getDai_work_stime())).isEqualTo(
                DateUtils.removeDate(DailyReportServiceTest.this.listDailyReports.get(i).getDai_work_stime()));
            Assertions.assertThat(DateUtils.removeDate(listDailyReports.get(i).getDai_work_etime())).isEqualTo(
                DateUtils.removeDate(DailyReportServiceTest.this.listDailyReports.get(i).getDai_work_etime()));
            Assertions.assertThat(listDailyReports.get(i).getDai_employee_code()).isEqualTo(
                DailyReportServiceTest.this.listDailyReports.get(i).getDai_employee_code());
        }
    }

    @Test
    public void testSearchReportByDateAndEmpCode() {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo.getReportByWorkDateAndEmployeeCode(
                    this.withAny(new Date()), this.anyInt).getResultList();
                this.result = DailyReportServiceTest.this.listDailyReports;
            }
        };
        List<Daily_report> listDailyReports =
            this.service.searchReportByDateAndEmpCode(DailyReportServiceTest.EMPLOYEE_CODE, new Date());

        Assertions.assertThat(listDailyReports).isNotNull();
        Assertions.assertThat(listDailyReports.size()).isEqualTo(DailyReportServiceTest.this.listDailyReports.size());
        for (int i = 0; i < listDailyReports.size(); i++) {
            Assertions.assertThat(listDailyReports.get(i).getDai_work_date()).isEqualTo(
                DailyReportServiceTest.this.listDailyReports.get(i).getDai_work_date());
            Assertions.assertThat(DateUtils.removeDate(listDailyReports.get(i).getDai_work_stime())).isEqualTo(
                DateUtils.removeDate(DailyReportServiceTest.this.listDailyReports.get(i).getDai_work_stime()));
            Assertions.assertThat(DateUtils.removeDate(listDailyReports.get(i).getDai_work_etime())).isEqualTo(
                DateUtils.removeDate(DailyReportServiceTest.this.listDailyReports.get(i).getDai_work_etime()));
            Assertions.assertThat(listDailyReports.get(i).getDai_employee_code()).isEqualTo(
                DailyReportServiceTest.this.listDailyReports.get(i).getDai_employee_code());
        }
    }

    @Test
    public void testGetEmployeeByAddrAndRetiredCondition() {
        new Expectations() {
            {
                DailyReportServiceTest.this.employeeRepo.getAllActiveEmpNotRetiredByAdpCode(this.anyString)
                    .getResultList();
                this.result = DailyReportServiceTest.this.listEmployees;
            }
        };

        List<Employee_mst> listEmp = this.service.getEmployeeByAddrAndRetiredCondition(DailyReportServiceTest.ADP_CODE);
        Assertions.assertThat(listEmp).isNotEmpty();
        Assertions.assertThat(listEmp.size()).isEqualTo(DailyReportServiceTest.NUMBER_EMPLOYEE_RESULT);
        for (int i = 0; i < listEmp.size(); i++) {
            Assertions.assertThat(listEmp.get(i).getEmp_code()).isEqualTo(this.listEmployees.get(i).getEmp_code());
            Assertions.assertThat(listEmp.get(i).getEmp_delete_flg()).isFalse();
        }
    }

    @Test
    public void testGetEmployeeByAddr() {
        new Expectations() {
            {
                DailyReportServiceTest.this.employeeRepo.getAllActiveEmpByAdpCode(this.anyString).getResultList();
                this.result = DailyReportServiceTest.this.listEmployees;
            }
        };

        List<Employee_mst> listEmp = this.service.getEmployeeByAddr(DailyReportServiceTest.ADP_CODE);
        Assertions.assertThat(listEmp).isNotEmpty();
        Assertions.assertThat(listEmp.size()).isEqualTo(DailyReportServiceTest.NUMBER_EMPLOYEE_RESULT);
        for (int i = 0; i < listEmp.size(); i++) {
            Assertions.assertThat(listEmp.get(i).getEmp_code()).isEqualTo(this.listEmployees.get(i).getEmp_code());
            Assertions.assertThat(listEmp.get(i).getEmp_delete_flg()).isFalse();
        }
    }

    @Test
    public void testGetListAddrContraintEmployee() {
        new Expectations() {
            {
                DailyReportServiceTest.this.addressRepo.getAllAddressPointJoinWithEmployee().getResultList();
                this.result = DailyReportServiceTest.this.listAddrs;
            }
        };

        List<Addresspoint_mst> addrs = this.service.getListAddrContraintEmployee();
        Assertions.assertThat(addrs).isNotEmpty();
        Assertions.assertThat(addrs.size()).isEqualTo(DailyReportServiceTest.NUMBER_ADDRESS_POINT_RESULT);
        for (int i = 0; i < addrs.size(); i++) {
            Assertions.assertThat(addrs.get(i).getAdp_code()).isEqualTo(this.listAddrs.get(i).getAdp_code());
            Assertions.assertThat(addrs.get(i).getAdp_name()).isEqualTo(this.listAddrs.get(i).getAdp_name());
        }
    }

    @Test
    public void testGetAllIndustryBigMST() {
        new Expectations() {
            {
                DailyReportServiceTest.this.industryBigMstRepo.getAllIndustryBigMst().getResultList();
                this.result = DailyReportServiceTest.this.listIndustry;
            }
        };

        List<Industry_big_mst> listIndustry = this.service.getAllIndustryBigMST();
        Assertions.assertThat(listIndustry).isNotEmpty();
        Assertions.assertThat(listIndustry.size()).isEqualTo(DailyReportServiceTest.NUMBER_INDUSTRY_BIG_RESULT);
        for (int i = 0; i < listIndustry.size(); i++) {
            Assertions.assertThat(listIndustry.get(i).getBig_code()).isEqualTo(this.listIndustry.get(i).getBig_code());
            Assertions.assertThat(listIndustry.get(i).getBig_name()).isEqualTo(this.listIndustry.get(i).getBig_name());
        }
    }

    @Test
    public void testGetEmployeeByEmpCode() {
        new Expectations() {
            {
                DailyReportServiceTest.this.employeeRepo.findEmployeeByCode(this.anyInt).getAnyResult();
                this.result = DailyReportServiceTest.this.currentEmployee;
            }
        };

        Employee_mst emp = this.service.getEmployeeByEmpCode(DailyReportServiceTest.EMPLOYEE_CODE);
        Assertions.assertThat(emp).isNotNull();
        Assertions.assertThat(emp.getEmp_code()).isEqualTo(this.currentEmployee.getEmp_code());
        Assertions.assertThat(emp.getAddresspoint_mst().getAdp_code()).isEqualTo(this.addressPoint.getAdp_code());
        Assertions.assertThat(emp.getEmp_delete_flg()).isEqualTo(this.currentEmployee.getEmp_delete_flg());
        Assertions.assertThat(emp.getEmp_condi_code()).isNull();
    }

    // @Test
    // public void testCountTotalVisitedTimesToCompany() {
    // new Expectations() {
    // {
    // DailyReportServiceTest.this.dailyreportRepo.countTotalVisitedTimesToCompany(this.anyString);
    // this.result = DailyReportServiceTest.COUNTER_RESULT_ONE;
    // }
    // };
    //
    // long result = this.service.countTotalVisitedTimesToCompany(DailyReportServiceTest.COMPANY_CODE_1);
    // Assertions.assertThat(result).isEqualTo(DailyReportServiceTest.COUNTER_RESULT_ONE);
    // }


    @Test
    public void testGetLastestBuscodeReturnNull() {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo.getReportLatestUpdatedByEmpCode(this.anyInt).getAnyResult();
                this.result = null;
            }
        };

        Short result = this.service.getLastestBuscode(DailyReportServiceTest.EMPLOYEE_CODE);
        Assertions.assertThat(result).isNull();
    }

    @Test
    public void testGetLastestBuscodeReturnBusCode() {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo.getReportLatestUpdatedByEmpCode(this.anyInt).getAnyResult();
                this.result = DailyReportServiceTest.this.dailyReportWithBusCode;
            }
        };

        Short result = this.service.getLastestBuscode(DailyReportServiceTest.EMPLOYEE_CODE);
        Assertions.assertThat(result).isEqualTo(DailyReportServiceTest.CODE_SHORT_TYPE_1);
    }

    @Test
    public void testSearchHistoryDailyReport() {
        new Expectations() {
            {
                DailyReportServiceTest.this.dailyreportRepo.searchHistoryDailyReport(this
                    .withAny(new RegisterActivityHistorySearchBean()));
                this.result = DailyReportServiceTest.this.resultSearch;
            }
        };

        ServiceSearchResult<Daily_report> result =
            this.service.searchHistoryDailyReport(new RegisterActivityHistorySearchBean());
        Assertions.assertThat(result.getTotalItems()).isEqualTo(this.resultSearch.getTotalItems());
        List<Daily_report> listReports1 = result.getListItems();
        List<Daily_report> listReports2 = this.resultSearch.getListItems();
        Assertions.assertThat(listReports1.size()).isEqualTo(listReports2.size());
        for (int i = 0; i < listReports1.size(); i++) {
            Assertions.assertThat(listReports1.get(i).getDai_work_date()).isEqualTo(
                listReports2.get(i).getDai_work_date());
            Assertions.assertThat(listReports1.get(i).getDai_work_type()).isEqualTo(
                listReports2.get(i).getDai_work_type());
            Assertions.assertThat(DateUtils.removeDate(listReports1.get(i).getDai_work_stime())).isEqualTo(
                DateUtils.removeDate(listReports2.get(i).getDai_work_stime()));
            Assertions.assertThat(DateUtils.removeDate(listReports1.get(i).getDai_work_etime())).isEqualTo(
                DateUtils.removeDate(listReports2.get(i).getDai_work_etime()));
        }
    }
}
