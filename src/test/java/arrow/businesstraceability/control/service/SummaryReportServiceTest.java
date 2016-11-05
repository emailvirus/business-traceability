package arrow.businesstraceability.control.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jxls.util.JxlsHelper;

import arrow.businesstraceability.cache.entity.ApprovalAndSubmissionInfo;
import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.constant.SummaryReportConstants;
import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Branch_position;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Monthly_report_history;
import arrow.businesstraceability.persistence.entity.Monthly_report_revision;
import arrow.businesstraceability.persistence.entity.Position_mst;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.businesstraceability.persistence.repository.Branch_positionRepository;
import arrow.businesstraceability.persistence.repository.Company_mstRepository;
import arrow.businesstraceability.persistence.repository.Daily_reportRepository;
import arrow.businesstraceability.persistence.repository.Employee_mstRepository;
import arrow.businesstraceability.persistence.repository.Monthly_report_historyRepository;
import arrow.businesstraceability.persistence.repository.Monthly_report_revisionRepository;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.util.FaceletUtils;
import arrow.framework.logging.BaseLogger;
import arrow.framework.util.DateUtils;
import arrow.framework.util.StringUtils;
import arrow.framework.util.cdi.Instance;

@RunWith(JMockit.class)
public class SummaryReportServiceTest {

    private static final String NAME_OF_BRANCH = "nameOfBranch";

    @Mocked
    @Tested
    private SummaryReportService service;

    @Mocked
    @Injectable
    private Company_mstRepository company_mstRepository;

    @Injectable
    private Monthly_report_historyRepository monthly_report_historyRepo;

    @Mocked
    @Injectable
    private Daily_reportRepository dailyRepo;

    @Mocked
    @Injectable
    private Monthly_report_revisionRepository monthly_reportRepository;

    @Mocked
    @Injectable
    private Addresspoint_mstRepository addressRepo;

    @Mocked
    @Injectable
    private BaseLogger logger;

    @Mocked
    @Injectable
    protected EntityManager emMain;


    @Injectable
    private Employee_mstRepository employee_mstRepo;

    @Injectable
    private Monthly_report_revisionRepository monthly_report_revisionRepository;

    @Injectable
    private Branch_positionRepository branchPositionRepo;

    @Mocked
    @Injectable
    protected UserCredential currentUser;

    private Addresspoint_mst branch;

    private Employee_mst selectEmployee;

    private Position_mst position;

    private Branch_position branchPosition;

    private Monthly_report_revision monthlyReport;

    private Monthly_report_revision monthlyReportHasChangedStatus;

    private Monthly_report_revision monthlyReportApproved;

    private Monthly_report_revision monthlyReportOpened;

    private Date currentDate;

    private List<Object[]> listVisitTimes;

    private List<Object[]> listPurpose;

    private List<Object[]> listCompaniesVisitTimes;

    private List<Monthly_report_history> listMonthlyReportHistory;

    private Date testDate;

    private Employee_mst tempEm1;

    private Employee_mst tempEm2;

    private static final int EMP_CODE = 100000004;

    private static final long WORKING_DAYS = 10;

    private static final long TOTAL_VISIT_TIMES = 100;

    private static final short POS_CODE = 1;

    private static final int POS_ID = 999;

    private static final int VERSION = 0;

    private static final int NUMBER_MESSAGES_RETURN = 1;

    private static final String POS_NAME = "postName";

    private static final String BRANCH_CODE = "1111";

    private static final String CREATOR_NAME = "creatorName";

    private static final String COMMENT = "comment";

    @Before
    public void initDataBeforeTest() {
        this.branch = new Addresspoint_mst(SummaryReportServiceTest.BRANCH_CODE);
        this.branch.setAdp_name(SummaryReportServiceTest.NAME_OF_BRANCH);
        this.listVisitTimes = this.createListVisitTimes();
        this.listPurpose = this.createListPurpose();
        this.listCompaniesVisitTimes = this.createListCompaniesVisited();

        this.position = new Position_mst(SummaryReportServiceTest.POS_CODE);
        this.position.setPos_name(SummaryReportServiceTest.POS_NAME);

        this.selectEmployee = new Employee_mst(SummaryReportServiceTest.EMP_CODE);
        this.selectEmployee.setPosition_mst(this.position);

        this.branchPosition = new Branch_position(SummaryReportServiceTest.POS_ID);
        this.branchPosition.setEmployee_code(SummaryReportServiceTest.EMP_CODE);
        this.branchPosition.setAdp_code(SummaryReportServiceTest.BRANCH_CODE);
        this.branchPosition.setPos_code(SummaryReportServiceTest.POS_CODE);
        this.branchPosition.setDelete_flg(Constants.IS_NOT_DELETED);

        this.tempEm1 = new Employee_mst(3000001);
        this.tempEm1.setEmp_name("Faker");
        this.tempEm2 = new Employee_mst(4000001);
        this.tempEm2.setEmp_name("Qwerty");
        Monthly_report_revision monthlyReportRevision = new Monthly_report_revision(0, this.tempEm1, 1, 2016);
        Monthly_report_history temp1MH = new Monthly_report_history(monthlyReportRevision, "SB");
        temp1MH.setEmployee_mst(this.tempEm1);
        this.testDate = DateUtils.MIN_DATE;
        temp1MH.setSousa_jiten(this.testDate);
        Monthly_report_history temp2MH = new Monthly_report_history(monthlyReportRevision, "RE");
        temp2MH.setEmployee_mst(this.tempEm2);
        temp2MH.setSousa_jiten(this.testDate);
        Monthly_report_history temp3MH = new Monthly_report_history(monthlyReportRevision, "AP");
        temp3MH.setEmployee_mst(this.tempEm2);
        temp3MH.setSousa_jiten(this.testDate);
        Monthly_report_history temp4MH = new Monthly_report_history(monthlyReportRevision, "RO");
        temp4MH.setEmployee_mst(this.tempEm1);
        temp4MH.setSousa_jiten(this.testDate);
        Monthly_report_history temp5MH = new Monthly_report_history(monthlyReportRevision, "SB");
        temp5MH.setEmployee_mst(this.tempEm1);
        Date testDate2 = DateUtils.MAX_DATE;
        temp5MH.setSousa_jiten(testDate2);
        this.listMonthlyReportHistory = new ArrayList<Monthly_report_history>();
        this.listMonthlyReportHistory.add(temp5MH);
        this.listMonthlyReportHistory.add(temp1MH);
        this.listMonthlyReportHistory.add(temp2MH);
        this.listMonthlyReportHistory.add(temp3MH);
        this.listMonthlyReportHistory.add(temp4MH);

        this.currentDate = new Date();

        this.monthlyReport =
                new Monthly_report_revision(SummaryReportServiceTest.VERSION, this.selectEmployee,
                        DateUtils.getMonth(this.currentDate), DateUtils.getYear(this.currentDate));
        this.monthlyReport.setShounin_joutai(SummaryReportConstants.MonthlyReport.STATUS_OPEN);

        this.monthlyReportHasChangedStatus =
                new Monthly_report_revision(SummaryReportServiceTest.VERSION, this.selectEmployee,
                        DateUtils.getMonth(this.currentDate), DateUtils.getYear(this.currentDate));
        this.monthlyReportHasChangedStatus.setShounin_joutai(SummaryReportConstants.MonthlyReport.STATUS_APPROVED);

        this.monthlyReportApproved =
                new Monthly_report_revision(SummaryReportServiceTest.VERSION, this.selectEmployee,
                        DateUtils.getMonth(this.currentDate), DateUtils.getYear(this.currentDate));
        this.monthlyReportApproved.setShounin_joutai(SummaryReportConstants.MonthlyReport.STATUS_APPROVED);

        this.monthlyReportOpened =
                new Monthly_report_revision(SummaryReportServiceTest.VERSION, this.selectEmployee,
                        DateUtils.getMonth(this.currentDate), DateUtils.getYear(this.currentDate));
        this.monthlyReportOpened.setShounin_joutai(SummaryReportConstants.MonthlyReport.STATUS_OPEN);
    }

    @Test
    public void testGenerateStatisticReportSuccess(@Mocked final FacesContext fc, @Mocked final JxlsHelper jxlsHelper) {
        new Expectations() {
            {
                Deencapsulation.invoke(SummaryReportServiceTest.this.service,
                        "getAllVisitInfoFromDailyReportByStartDateAndEndDate", this.withAny(Date.class),
                        this.withAny(Date.class), this.withAny(Employee_mst.class));
                this.result = SummaryReportServiceTest.this.listVisitTimes;

                Deencapsulation.invoke(SummaryReportServiceTest.this.service,
                        "getAllPurposeInfoFromDailyReportByStartDateAndEndDate", this.withAny(Date.class),
                        this.withAny(Date.class), this.withAny(Employee_mst.class));
                this.result = SummaryReportServiceTest.this.listPurpose;

                Deencapsulation.invoke(SummaryReportServiceTest.this.service,
                        "getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate", this.withAny(Date.class),
                        this.withAny(Date.class), this.withAny(Employee_mst.class));
                this.result = SummaryReportServiceTest.this.listCompaniesVisitTimes;

                Deencapsulation.invoke(SummaryReportServiceTest.this.service, "getAddresspointByCode", this.anyString);
                this.result = SummaryReportServiceTest.this.branch;

                SummaryReportServiceTest.this.dailyRepo.getWorkingDaysOfEmployeeByStartDateAndEndDate(this.anyInt,
                        this.withAny(new Date()), this.withAny(new Date())).getAnyResult();
                this.result = SummaryReportServiceTest.WORKING_DAYS;

                SummaryReportServiceTest.this.dailyRepo.countTotalDailyReportOfEmployeeByStartDateAndEndDate(
                        this.anyInt, this.withAny(new Date()), this.withAny(new Date())).getAnyResult();
                this.result = SummaryReportServiceTest.TOTAL_VISIT_TIMES;

            }
        };
        ServiceResult<Object> result =
                this.service.generateStatisticReport(new Date(), new Date(), SummaryReportServiceTest.BRANCH_CODE,
                        this.selectEmployee, SummaryReportServiceTest.CREATOR_NAME);

        Assertions.assertThat(result.getData()).isNull();
        Assertions.assertThat(result.isSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages()).isEmpty();
    }

    @Test
    public void testGenerateStatisticReportSuccessNoneWorkingDays(@Mocked final FacesContext fc,
            @Mocked final JxlsHelper jxlsHelper) {
        new Expectations() {
            {
                Deencapsulation.invoke(SummaryReportServiceTest.this.service,
                        "getAllVisitInfoFromDailyReportByStartDateAndEndDate", this.withAny(Date.class),
                        this.withAny(Date.class), this.withAny(Employee_mst.class));
                this.result = SummaryReportServiceTest.this.listVisitTimes;

                Deencapsulation.invoke(SummaryReportServiceTest.this.service,
                        "getAllPurposeInfoFromDailyReportByStartDateAndEndDate", this.withAny(Date.class),
                        this.withAny(Date.class), this.withAny(Employee_mst.class));
                this.result = SummaryReportServiceTest.this.listPurpose;

                Deencapsulation.invoke(SummaryReportServiceTest.this.service,
                        "getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate", this.withAny(Date.class),
                        this.withAny(Date.class), this.withAny(Employee_mst.class));
                this.result = SummaryReportServiceTest.this.listCompaniesVisitTimes;

                Deencapsulation.invoke(SummaryReportServiceTest.this.service, "getAddresspointByCode", this.anyString);
                this.result = SummaryReportServiceTest.this.branch;

                SummaryReportServiceTest.this.dailyRepo.getWorkingDaysOfEmployeeByStartDateAndEndDate(this.anyInt,
                        this.withAny(new Date()), this.withAny(new Date())).getAnyResult();
                this.result = null;

                SummaryReportServiceTest.this.dailyRepo.countTotalDailyReportOfEmployeeByStartDateAndEndDate(
                        this.anyInt, this.withAny(new Date()), this.withAny(new Date())).getAnyResult();
                this.result = SummaryReportServiceTest.TOTAL_VISIT_TIMES;

            }
        };
        ServiceResult<Object> result =
                this.service.generateStatisticReport(new Date(), new Date(), SummaryReportServiceTest.BRANCH_CODE,
                        this.selectEmployee, SummaryReportServiceTest.CREATOR_NAME);

        Assertions.assertThat(result.getData()).isNull();
        Assertions.assertThat(result.isSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages()).isEmpty();
    }

    @Test
    public void testGenerateStatisticReportSuccessNoneTotalVisitTimes(@Mocked final FacesContext fc,
            @Mocked final JxlsHelper jxlsHelper) {
        new Expectations() {
            {
                Deencapsulation.invoke(SummaryReportServiceTest.this.service,
                        "getAllVisitInfoFromDailyReportByStartDateAndEndDate", this.withAny(Date.class),
                        this.withAny(Date.class), this.withAny(Employee_mst.class));
                this.result = SummaryReportServiceTest.this.listVisitTimes;

                Deencapsulation.invoke(SummaryReportServiceTest.this.service,
                        "getAllPurposeInfoFromDailyReportByStartDateAndEndDate", this.withAny(Date.class),
                        this.withAny(Date.class), this.withAny(Employee_mst.class));
                this.result = SummaryReportServiceTest.this.listPurpose;

                Deencapsulation.invoke(SummaryReportServiceTest.this.service,
                        "getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate", this.withAny(Date.class),
                        this.withAny(Date.class), this.withAny(Employee_mst.class));
                this.result = SummaryReportServiceTest.this.listCompaniesVisitTimes;

                Deencapsulation.invoke(SummaryReportServiceTest.this.service, "getAddresspointByCode", this.anyString);
                this.result = SummaryReportServiceTest.this.branch;

                SummaryReportServiceTest.this.dailyRepo.getWorkingDaysOfEmployeeByStartDateAndEndDate(this.anyInt,
                        this.withAny(new Date()), this.withAny(new Date())).getAnyResult();
                this.result = SummaryReportServiceTest.WORKING_DAYS;

                SummaryReportServiceTest.this.dailyRepo.countTotalDailyReportOfEmployeeByStartDateAndEndDate(
                        this.anyInt, this.withAny(new Date()), this.withAny(new Date())).getAnyResult();
                this.result = null;

            }
        };
        ServiceResult<Object> result =
                this.service.generateStatisticReport(new Date(), new Date(), SummaryReportServiceTest.BRANCH_CODE,
                        this.selectEmployee, SummaryReportServiceTest.CREATOR_NAME);

        Assertions.assertThat(result.getData()).isNull();
        Assertions.assertThat(result.isSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages()).isEmpty();
    }

    @Test
    public void testGenerateStatisticReportThrowException(@Mocked final FacesContext fc,
            @Mocked final JxlsHelper jxlsHelper, @Mocked final FaceletUtils fu) throws IOException {
        new Expectations() {
            {
                Deencapsulation.invoke(SummaryReportServiceTest.this.service,
                        "getAllVisitInfoFromDailyReportByStartDateAndEndDate", this.withAny(Date.class),
                        this.withAny(Date.class), this.withAny(Employee_mst.class));
                this.result = SummaryReportServiceTest.this.listVisitTimes;

                Deencapsulation.invoke(SummaryReportServiceTest.this.service,
                        "getAllPurposeInfoFromDailyReportByStartDateAndEndDate", this.withAny(Date.class),
                        this.withAny(Date.class), this.withAny(Employee_mst.class));
                this.result = SummaryReportServiceTest.this.listPurpose;

                Deencapsulation.invoke(SummaryReportServiceTest.this.service,
                        "getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate", this.withAny(Date.class),
                        this.withAny(Date.class), this.withAny(Employee_mst.class));
                this.result = SummaryReportServiceTest.this.listCompaniesVisitTimes;

                Deencapsulation.invoke(SummaryReportServiceTest.this.service, "getAddresspointByCode", this.anyString);
                this.result = SummaryReportServiceTest.this.branch;

                FaceletUtils.getOutputStreamFromHttpServlet(this.anyString);
                this.result = new IOException();

            }
        };
        ServiceResult<Object> result =
                this.service.generateStatisticReport(new Date(), new Date(), SummaryReportServiceTest.BRANCH_CODE,
                        this.selectEmployee, SummaryReportServiceTest.CREATOR_NAME);

        Assertions.assertThat(result.getData()).isNull();
        Assertions.assertThat(result.isNotSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages()).isNotEmpty();
        Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                ErrorMessage.sum_007_export_periodic_report_failed().getMessageCode());
    }

    private List<Object[]> createListCompaniesVisited() {
        return Arrays.asList(new Object[] {"Company 1", 2}, new Object[] {"Company 2", 1});
    }

    private List<Object[]> createListPurpose() {
        return Arrays.asList(new Object[] {(long) 9, (short) 1}, new Object[] {(long) 10, (short) 7});
    }

    private List<Object[]> createListVisitTimes() {
        return Arrays.asList(new Object[] {(long) 11, (short) 101}, new Object[] {(long) 12, (short) 201});
    }

    @Test
    public void testGetActiveBranchPositionByEmployeeCodeAndTargetBranch() {
        new Expectations() {
            {
                SummaryReportServiceTest.this.branchPositionRepo.findByEmployeeCodeAndAddressPointCodeAndDeleteFlag(
                        this.anyInt, this.anyString, Constants.IS_NOT_DELETED).getAnyResult();
                this.result = SummaryReportServiceTest.this.branchPosition;
            }
        };
        Branch_position result =
                this.service.getActiveBranchPositionByEmployeeCodeAndTargetBranch(SummaryReportServiceTest.EMP_CODE,
                        SummaryReportServiceTest.BRANCH_CODE);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getEmployee_code()).isEqualTo(SummaryReportServiceTest.EMP_CODE);
        Assertions.assertThat(result.getAdp_code()).isEqualTo(SummaryReportServiceTest.BRANCH_CODE);
        Assertions.assertThat(result.getPos_id()).isEqualTo(SummaryReportServiceTest.POS_ID);
        Assertions.assertThat(result.getPos_code()).isEqualTo(SummaryReportServiceTest.POS_CODE);
        Assertions.assertThat(result.getDelete_flg()).isFalse();
    }

    // Verify approvalAndSubmissionInfoTest
    @Test
    public void approvalAndSubmissionInfoTest(@Mocked final Instance instance) {


        new Expectations() {
            {

                SummaryReportServiceTest.this.monthly_report_historyRepo
                        .findMonthlyReportHistoryByEmployeeAndStartDate(this.anyInt, this.anyInt, this.anyInt)
                        .getResultList();
                this.result = SummaryReportServiceTest.this.listMonthlyReportHistory;

            }
        };
        ApprovalAndSubmissionInfo approvalAndSubmissionInfo =
                this.service.getApprovalAndSubmissionInfo(this.tempEm1, this.testDate);
        Assert.assertTrue(approvalAndSubmissionInfo.getNumberOfSubmissions() == 2);
        Assert.assertTrue(approvalAndSubmissionInfo.getNumberOfReject() == 1);
        Assert.assertTrue(approvalAndSubmissionInfo.getNumberOfReOpen() == 1);
        Assert.assertTrue(approvalAndSubmissionInfo.getNumberOfApprove() == 1);
        Assert.assertTrue(approvalAndSubmissionInfo.getDateSubmissions().equals((DateUtils.MAX_DATE)));
        Assert.assertTrue(approvalAndSubmissionInfo.getDateReject().equals((DateUtils.MIN_DATE)));
        Assert.assertTrue(approvalAndSubmissionInfo.getDateReOpen().equals((DateUtils.MIN_DATE)));
        Assert.assertTrue(approvalAndSubmissionInfo.getDateApproval().equals((DateUtils.MIN_DATE)));
        Assert.assertTrue(approvalAndSubmissionInfo.getRejectedBy().equals("Qwerty"));
        Assert.assertTrue(approvalAndSubmissionInfo.getApprover().equals("Qwerty"));
        Assert.assertTrue(approvalAndSubmissionInfo.getReOpenedBy().equals("Faker"));

    }

    @Test
    public void testApproveMonthlyReportFailed() {
        new Expectations() {
            {
                SummaryReportServiceTest.this.monthly_reportRepository.findAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.result = SummaryReportServiceTest.this.monthlyReportHasChangedStatus;
            }
        };

        ServiceResult<Monthly_report_revision> result =
                this.service.approveMonthlyReport(this.monthlyReport, this.selectEmployee.getEmp_code(),
                        SummaryReportServiceTest.COMMENT);

        new Verifications() {
            {
                SummaryReportServiceTest.this.monthly_reportRepository.findAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.times = 1;
            }
        };

        Assertions.assertThat(result.isNotSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(SummaryReportServiceTest.NUMBER_MESSAGES_RETURN);
        Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                ErrorMessage.monthlyreport_010_approve_failed_by_other_person_approved().getMessageCode());

    }

    @Test
    public void testApproveMonthlyReportSuccess() {
        new Expectations() {
            {
                SummaryReportServiceTest.this.monthly_reportRepository.findAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.result = SummaryReportServiceTest.this.monthlyReport;

                SummaryReportServiceTest.this.monthly_reportRepository
                        .saveAndFlushAndRefresh(SummaryReportServiceTest.this.monthlyReport);
                this.result = SummaryReportServiceTest.this.monthlyReportApproved;
            }
        };

        ServiceResult<Monthly_report_revision> result =
                this.service.approveMonthlyReport(this.monthlyReport, this.selectEmployee.getEmp_code(),
                        SummaryReportServiceTest.COMMENT);

        new Verifications() {
            {
                SummaryReportServiceTest.this.monthly_reportRepository.findAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.times = 1;

                SummaryReportServiceTest.this.monthly_reportRepository
                .saveAndFlushAndRefresh(SummaryReportServiceTest.this.monthlyReport);
                this.times = 1;

                SummaryReportServiceTest.this.monthly_report_historyRepo.saveAndFlushAndRefresh(this
                        .withAny(new Monthly_report_history()));
                this.times = 1;
            }
        };

        Assertions.assertThat(result.isSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(SummaryReportServiceTest.NUMBER_MESSAGES_RETURN);
        Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                InfoMessage.monthlyreport_004_approved().getMessageCode());
        Assertions.assertThat(result.getData()).isNotNull();
        Assertions.assertThat(result.getData().getShounin_joutai()).isEqualTo(
                SummaryReportConstants.MonthlyReport.STATUS_APPROVED);
    }

    @Test
    public void testReopenMonthlyReportWithoutComment() {
        ServiceResult<Monthly_report_revision> result =
                this.service.reopenMonthlyReport(this.monthlyReport, this.selectEmployee.getEmp_code(),
                        StringUtils.EMPTY_STRING);

        Assertions.assertThat(result.isNotSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(SummaryReportServiceTest.NUMBER_MESSAGES_RETURN);
        Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                ErrorMessage.monthlyreport_006_comment_is_required().getMessageCode());
        Assertions.assertThat(result.getData()).isNull();
    }

    @Test
    public void testReopenMonthlyReportWasChangeByAnotherUser() {
        new Expectations() {
            {
                SummaryReportServiceTest.this.monthly_reportRepository.findAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.result = SummaryReportServiceTest.this.monthlyReportHasChangedStatus;
            }
        };

        ServiceResult<Monthly_report_revision> result =
                this.service.reopenMonthlyReport(this.monthlyReport, this.selectEmployee.getEmp_code(),
                        SummaryReportServiceTest.COMMENT);

        new Verifications() {
            {
                SummaryReportServiceTest.this.monthly_reportRepository.findAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.times = 1;
            }
        };

        Assertions.assertThat(result.isNotSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(SummaryReportServiceTest.NUMBER_MESSAGES_RETURN);
        Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                ErrorMessage.monthlyreport_009_reopen_failed_by_other_person_reopened().getMessageCode());
        Assertions.assertThat(result.getData()).isNull();
    }

    @Test
    public void testReopenMonthlyReportSuccess() {
        new Expectations() {
            {
                SummaryReportServiceTest.this.monthly_reportRepository.findAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.result = SummaryReportServiceTest.this.monthlyReport;

                SummaryReportServiceTest.this.monthly_reportRepository.saveAndFlushAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.result = SummaryReportServiceTest.this.monthlyReportOpened;
            }
        };

        ServiceResult<Monthly_report_revision> result =
                this.service.reopenMonthlyReport(this.monthlyReport, this.selectEmployee.getEmp_code(),
                        SummaryReportServiceTest.COMMENT);

        new Verifications() {
            {
                SummaryReportServiceTest.this.monthly_reportRepository.findAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.times = 1;

                SummaryReportServiceTest.this.monthly_reportRepository.saveAndFlushAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.times = 2;

                SummaryReportServiceTest.this.monthly_report_historyRepo.saveAndFlushAndRefresh(this
                        .withAny(new Monthly_report_history()));
                this.times = 1;
            }
        };

        Assertions.assertThat(result.isSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(SummaryReportServiceTest.NUMBER_MESSAGES_RETURN);
        Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                InfoMessage.monthlyreport_007_reopened().getMessageCode());
        Assertions.assertThat(result.getData()).isNotNull();
        Assertions.assertThat(result.getData().getShounin_joutai()).isEqualTo(
                SummaryReportConstants.MonthlyReport.STATUS_OPEN);
    }

    @Test
    public void testRejectMonthlyReportWithoutComment() {
        ServiceResult<Monthly_report_revision> result =
                this.service.rejectMonthlyReport(this.monthlyReport, this.selectEmployee.getEmp_code(),
                        StringUtils.EMPTY_STRING);

        Assertions.assertThat(result.isNotSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(SummaryReportServiceTest.NUMBER_MESSAGES_RETURN);
        Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                ErrorMessage.monthlyreport_006_comment_is_required().getMessageCode());
        Assertions.assertThat(result.getData()).isNull();
    }

    @Test
    public void testRejectMonthlyReportWasChangeByAnotherUser() {
        new Expectations() {
            {
                SummaryReportServiceTest.this.monthly_reportRepository.findAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.result = SummaryReportServiceTest.this.monthlyReportHasChangedStatus;
            }
        };

        ServiceResult<Monthly_report_revision> result =
                this.service.rejectMonthlyReport(this.monthlyReport, this.selectEmployee.getEmp_code(),
                        SummaryReportServiceTest.COMMENT);

        new Verifications() {
            {
                SummaryReportServiceTest.this.monthly_reportRepository.findAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.times = 1;
            }
        };

        Assertions.assertThat(result.isNotSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(SummaryReportServiceTest.NUMBER_MESSAGES_RETURN);
        Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                ErrorMessage.monthlyreport_008_reject_failed_by_other_person_rejected().getMessageCode());
        Assertions.assertThat(result.getData()).isNull();
    }

    @Test
    public void testRejectMonthlyReportSuccess() {
        new Expectations() {
            {
                SummaryReportServiceTest.this.monthly_reportRepository.findAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.result = SummaryReportServiceTest.this.monthlyReport;

                SummaryReportServiceTest.this.monthly_reportRepository.saveAndFlushAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.result = SummaryReportServiceTest.this.monthlyReportOpened;
            }
        };

        ServiceResult<Monthly_report_revision> result =
                this.service.rejectMonthlyReport(this.monthlyReport, this.selectEmployee.getEmp_code(),
                        SummaryReportServiceTest.COMMENT);

        new Verifications() {
            {
                SummaryReportServiceTest.this.monthly_reportRepository.findAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.times = 1;

                SummaryReportServiceTest.this.monthly_reportRepository.saveAndFlushAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.times = 2;

                SummaryReportServiceTest.this.monthly_report_historyRepo.saveAndFlushAndRefresh(this
                        .withAny(new Monthly_report_history()));
                this.times = 1;
            }
        };

        Assertions.assertThat(result.isSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(SummaryReportServiceTest.NUMBER_MESSAGES_RETURN);
        Assertions.assertThat(result.getFirstMessage().getMessageCode()).isEqualTo(
                InfoMessage.monthlyreport_003_rejected().getMessageCode());
        Assertions.assertThat(result.getData()).isNotNull();
        Assertions.assertThat(result.getData().getShounin_joutai()).isEqualTo(
                SummaryReportConstants.MonthlyReport.STATUS_OPEN);
    }
}
