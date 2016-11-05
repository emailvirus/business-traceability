package arrow.businesstraceability.control.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import arrow.businesstraceability.constant.SummaryReportConstants;
import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.export.base.AbstractExportExcel;
import arrow.businesstraceability.export.base.imp.PeriodicReportExportExcel;
import arrow.businesstraceability.export.factory.AbstractExportFactory;
import arrow.businesstraceability.export.factory.ExcelReportFactory;
import arrow.businesstraceability.export.factory.ExportFileFactory;
import arrow.businesstraceability.export.param.ReportParameter;
import arrow.businesstraceability.persistence.entity.Abstract_entity;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
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
import arrow.businesstraceability.util.ExportExcelUtils;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.logging.BaseLogger;
import arrow.framework.util.DateUtils;
import arrow.framework.util.cdi.Instance;

@RunWith(JMockit.class)
public class MonthlyReportServiceTest {

    @Mocked
    @Tested
    SummaryReportService service;

    @Injectable
    Company_mstRepository company_mstRepository;

    @Mocked
    @Tested
    PeriodicReportExportExcel periodicReportExportExcel;

    @Injectable
    Monthly_report_historyRepository monthly_report_historyRepo;

    @Injectable
    AddressPointService addresspointService;

    @Injectable
    Employee_mstRepository employee_mstRepo;


    @Injectable
    DailyReportService dailyService;

    @Injectable
    BaseLogger log;

    @Injectable
    EntityManager emMain;

    @Injectable
    UserCredential currentUser;

    @Injectable
    ReportParameter param;

    @Mocked
    @Injectable
    Monthly_report_revisionRepository monthly_report_revisionRepository;

    @Injectable
    private Daily_reportRepository dailyRepo;

    @Injectable
    private Addresspoint_mstRepository addressRepo;

    @Injectable
    private Branch_positionRepository branchPositionRepo;

    String excelTemplatePath;

    Date startDate;

    Date endDate;

    String branchCode;

    Employee_mst employee;

    Addresspoint_mst addresspoint;

    ExcelReportFactory exportFactory;

    Workbook workbook;

    List<Object[]> listVisitTimes;

    List<Object[]> listPurpose;

    List<Object[]> listCompanyVisited;

    List<Employee_mst> listEmployee;

    String companiesVisitedInfo;

    int countVisitTime = 0;

    int countPurpose = 0;

    Monthly_report_revision monthlyReportRevision;

    Monthly_report_revision monthlyReportRevision0;

    Monthly_report_revision monthlyReportResult;

    Monthly_report_history monthlyReportHistory;

    @Before
    public void before() throws InvalidFormatException, IOException {
        this.excelTemplatePath = "/src/main/resource/excel-template/monthly_reports_jp.xls";
        this.startDate = DateUtils.adjustMonth(DateUtils.getCurrentDatetime(), -1);
        this.endDate = DateUtils.getCurrentDatetime();
        this.branchCode = "01";

        final Position_mst position = new Position_mst(Short.valueOf("11"));
        position.setPos_name("position_faked_name");
        this.employee = new Employee_mst(11111);
        this.employee.setPosition_mst(position);

        this.listEmployee = new ArrayList<Employee_mst>();
        this.listEmployee.add(this.employee);

        this.addresspoint = new Addresspoint_mst(this.branchCode);
        this.addresspoint.setAdp_name("addresspoin_faked_name");

        File file = new File(".");
        final String url =
                file.getCanonicalPath() + "/src/main/resources" + SummaryReportConstants.TEMPLATE_DIR
                + SummaryReportConstants.MonthlyReport.MONTHLY_REPORT_TEMPLATE;
        this.workbook = ExportExcelUtils.createWorkbook(url);

        // Create expected list visit time
        this.listVisitTimes = new ArrayList<Object[]>();
        for (Map.Entry<Short, String> entry : SummaryReportConstants.MonthlyReport.getMapVisitTimeCellCoordinates()
                .entrySet()) {
            Object[] object = {1l, entry.getKey()};
            this.listVisitTimes.add(object);
            this.countVisitTime += 1;
        }

        // Create expected list purpose
        this.listPurpose = new ArrayList<Object[]>();
        for (Map.Entry<Short, String> entry : SummaryReportConstants.MonthlyReport.getMapPurposeCellCoordinates()
                .entrySet()) {
            Object[] object = {1l, entry.getKey()};
            this.listPurpose.add(object);
            this.countPurpose += 1;
        }

        final StringBuilder sb = new StringBuilder();
        // Create expected list companies visited
        this.listCompanyVisited = new ArrayList<Object[]>();
        int total = 10;
        for (int i = 0; i < total; i++) {
            Object[] object = {"company" + i, total - i};
            this.listCompanyVisited.add(object);
            sb.append("; " + object[0].toString() + "(" + object[1].toString() + ")");
        }

        this.companiesVisitedInfo = sb.toString().replaceFirst("; ", "");

        this.monthlyReportRevision =
                new Monthly_report_revision(Integer.valueOf("1"), this.employee, Integer.valueOf("1"),
                        Integer.valueOf("1"));
        this.monthlyReportRevision.setNo_kekka_ni_kansuru_genin("abc");
        this.monthlyReportRevision.setSono_genin_ga_shoujita_riyuu("xyz");
        this.monthlyReportRevision.setJigetsuno_mokuhyou("qwerty");

        this.monthlyReportRevision.setHoumon_kensuu_mokuteki_shinkihoumon(1);
        this.monthlyReportRevision.setHoumon_kensuu_mokuteki_kizon_no_teiki_houmon(1);
        this.monthlyReportRevision.setHoumon_kensuu_mokuteki_kizon_igai_no_teiki_houmon(1);
        this.monthlyReportRevision.setHoumon_kensuu_mokuteki_gijutsusha_shoukai(1);
        this.monthlyReportRevision.setHoumon_kensuu_mokuteki_gyouda(1);
        this.monthlyReportRevision.setHoumon_kensuu_mokuteki_mitsumori(1);
        this.monthlyReportRevision.setHoumon_kensuu_mokuteki_kuremu(1);
        this.monthlyReportRevision.setHoumon_kensuu_mokuteki_sonota(1);
        this.monthlyReportRevision.setGenka_ritsu(1d);
        this.monthlyReportRevision.setHoumon_kensuu_shokushu_sofuto_wea(1);
        this.monthlyReportRevision.setHoumon_kensuu_shokushu_netto_waku(1);
        this.monthlyReportRevision.setHoumon_kensuu_shokushu_kenchiku(1);
        this.monthlyReportRevision.setHoumon_kensuu_shokushu_doboku(1);
        this.monthlyReportRevision.setHoumon_kensuu_shokushu_setsubi(1);
        this.monthlyReportRevision.setHoumon_kensuu_shokushu_denki(1);
        this.monthlyReportRevision.setHoumon_kensuu_shokushu_puranto(1);
        this.monthlyReportRevision.setHoumon_kensuu_shokushu_ippan(1);
        this.monthlyReportRevision.setHoumon_kensuu_shokushu_tsuushin_musen(1);
        this.monthlyReportRevision.setHoumon_kensuu_shokushu_tsuushin_yuusen(1);
        this.monthlyReportRevision.setHoumon_kensuu_shokushu_kikai_hado(1);
        this.monthlyReportRevision.setHoumon_kensuu_shokushu_jimu(1);
        this.monthlyReportRevision.setHoumon_kensuu_shokushu_koru_centa(1);

        this.monthlyReportRevision0 = new Monthly_report_revision(0, this.employee, 1, 2015);

        this.monthlyReportResult =
                new Monthly_report_revision(Integer.valueOf("1"), this.employee, Integer.valueOf("1"),
                        Integer.valueOf("1"));
        BeanCopier.copy(this.monthlyReportRevision, this.monthlyReportResult);
        this.monthlyReportResult.setShounin_joutai(SummaryReportConstants.MonthlyReport.STATUS_OPEN);
        this.monthlyReportResult.setRepoto_no_ribijon_no_sakujo_furagu(false);

        this.monthlyReportHistory = new Monthly_report_history();
    }

    @Test
    public void testTemplateExportExcelMonthlyReport() throws IOException {
        final File currentDirFile = new File(".");
        final String projectDir = currentDirFile.getCanonicalPath();
        final File template = new File(projectDir + this.excelTemplatePath);
        Assert.assertEquals(template.getName(), "monthly_reports_jp.xls");
    }

    @Test
    public void testExportExcelMonthlyReportCreateClass(@Mocked final Instance instance) {
        AbstractExportFactory factory =
                ExportFileFactory.getFactory(SummaryReportConstants.PeriodicReport.PERIODIC_REPORT_TYPE);
        ReportParameter param =
                new ReportParameter(this.startDate, this.endDate, this.branchCode, String.valueOf(this.employee
                        .getEmp_code()), this.monthlyReportRevision);
        AbstractExportExcel output = factory.createReport(param);
        Assert.assertNotNull(output);
        Assert.assertTrue(output instanceof PeriodicReportExportExcel);
    }

    /**
     * Test generate monthly report.
     * */
    @Test
    public void createMonthlyReportTest(@Mocked final Instance instance) throws InvalidFormatException, IOException {

        new MockUp<ExportExcelUtils>() {
            @Mock
            private Workbook createWorkbook(final String filePath) {
                return MonthlyReportServiceTest.this.workbook;
            }
        };

        new Expectations() {
            {
                MonthlyReportServiceTest.this.dailyRepo
                .getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate(this.anyInt,
                        MonthlyReportServiceTest.this.startDate, MonthlyReportServiceTest.this.endDate)
                        .maxResults(SummaryReportConstants.MonthlyReport.MAX_VISITED_COMPANIES_RECORD).getResultList();
                this.result = MonthlyReportServiceTest.this.listCompanyVisited;

                Deencapsulation.invoke(MonthlyReportServiceTest.this.periodicReportExportExcel, "findEmployeeByCode");
                this.result = MonthlyReportServiceTest.this.employee;

                Deencapsulation
                .invoke(MonthlyReportServiceTest.this.periodicReportExportExcel, "getAddresspointByCode");
                this.result = MonthlyReportServiceTest.this.addresspoint;

                Deencapsulation.invoke(MonthlyReportServiceTest.this.periodicReportExportExcel, "getCreatorName");
                this.result = "faked_creator_name";

            }
        };
        AbstractExportFactory factory =
                ExportFileFactory.getFactory(SummaryReportConstants.PeriodicReport.PERIODIC_REPORT_TYPE);
        ReportParameter param =
                new ReportParameter(this.startDate, this.endDate, this.branchCode, String.valueOf(this.employee
                        .getEmp_code()), this.monthlyReportRevision);
        AbstractExportExcel periodicReportExportExcel = factory.createReport(param);
        Workbook testExcelActual = periodicReportExportExcel.createContent();


        new Verifications() {
            {
                MonthlyReportServiceTest.this.dailyRepo
                .getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate(this.anyInt,
                        MonthlyReportServiceTest.this.startDate, MonthlyReportServiceTest.this.endDate)
                        .maxResults(SummaryReportConstants.MonthlyReport.MAX_VISITED_COMPANIES_RECORD).getResultList();
                this.times = 1;

                Deencapsulation
                .invoke(MonthlyReportServiceTest.this.periodicReportExportExcel, "getAddresspointByCode");
                this.times = 1;

                Deencapsulation.invoke(MonthlyReportServiceTest.this.periodicReportExportExcel, "getCreatorName");
                this.times = 1;
                Deencapsulation.invoke(MonthlyReportServiceTest.this.periodicReportExportExcel, "writeComment",
                        this.withAny(Sheet.class), this.withAny(Workbook.class),
                        this.withAny(Monthly_report_revision.class));
                this.times = 1;
            }

            {
                Assert.assertEquals(MonthlyReportServiceTest.this.workbook.getNumberOfSheets(), 1);
            }

            Sheet sheet = testExcelActual.getSheetAt(0);

            // Verify header
            {
                Row headerRow = this.sheet.getRow(SummaryReportConstants.PeriodicReport.HEADER_BRANCH_INFO_ROW_INDEX);

                // Verify created year of report
                Assertions.assertThat(
                        headerRow.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_YEAR_INDEX)
                        .getStringCellValue()).isEqualTo(
                                DateUtils.getYear(MonthlyReportServiceTest.this.startDate) + SummaryReportConstants.YEAR_JP);

                // Verify created month of report
                Assertions.assertThat(
                        headerRow.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_MONTH_INDEX)
                        .getStringCellValue())
                        .isEqualTo(
                                DateUtils.getMonth(MonthlyReportServiceTest.this.startDate)
                                        + SummaryReportConstants.MONTHLY_JP);

                // Verify branch name
                Assertions.assertThat(
                        headerRow.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_BRANCH_NAME_INDEX)
                        .getStringCellValue()).isEqualTo(
                                MonthlyReportServiceTest.this.addresspoint.getAdp_name());

                Row headerPositionRow =
                        this.sheet.getRow(SummaryReportConstants.MonthlyReport.HEADER_POSITION_INFO_INDEX);

                // Verify position
                Assertions.assertThat(
                        headerPositionRow.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_POSITION_INDEX)
                        .getStringCellValue()).isEqualTo(
                                MonthlyReportServiceTest.this.employee.getPosition_mst().getPos_name());

                Row headerCreatorRow =
                        this.sheet.getRow(SummaryReportConstants.MonthlyReport.HEADER_CREATOR_INFO_INDEX);

                // Verify creator
                Assertions.assertThat(
                        headerCreatorRow.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_POSITION_INDEX)
                        .getStringCellValue()).isEqualTo("faked_creator_name");
            }

            // Verify list companies visited
            {
                Cell companiesVisit =
                        this.sheet.getRow(SummaryReportConstants.MonthlyReport.COMPANY_VISITED_ROW_INDEX).getCell(
                                SummaryReportConstants.MonthlyReport.COMPANY_VISITED_COL_INDEX);
                Assertions.assertThat(companiesVisit.getStringCellValue()).isEqualTo(
                        MonthlyReportServiceTest.this.companiesVisitedInfo);
            }

        };

        // Verify visit times
        for (Map.Entry<Short, String> entry : SummaryReportConstants.MonthlyReport.getMapVisitTimeCellCoordinates()
                .entrySet()) {
            CellReference cr = new CellReference(entry.getValue());
            Row row = this.workbook.getSheetAt(0).getRow(cr.getRow());
            Cell cell = row.getCell(cr.getCol());

            Assertions.assertThat(cell.getNumericCellValue()).isEqualTo(1);
        }

        // Verify purpose
        for (Map.Entry<Short, String> entry : SummaryReportConstants.MonthlyReport.getMapPurposeCellCoordinates()
                .entrySet()) {
            CellReference cr = new CellReference(entry.getValue());
            Row row = this.workbook.getSheetAt(0).getRow(cr.getRow());
            Cell cell = row.getCell(cr.getCol());

            Assertions.assertThat(cell.getNumericCellValue()).isEqualTo(1);
        }
    }

    /**
     * Test when branch is HQ.
     * */
    @Test
    public void createMonthlyReportWhenChooseBranchIsHQTest(@Mocked final Instance instance)
            throws InvalidFormatException, IOException {

        new MockUp<ExportExcelUtils>() {
            @Mock
            private Workbook createWorkbook(final String filePath) {
                return MonthlyReportServiceTest.this.workbook;
            }
        };

        new Expectations() {
            {
                Deencapsulation.invoke(MonthlyReportServiceTest.this.periodicReportExportExcel,
                        "getBranchCodeOfEmployee");
                this.result = MonthlyReportServiceTest.this.branchCode;

                MonthlyReportServiceTest.this.dailyRepo
                        .getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate(this.anyInt,
                                MonthlyReportServiceTest.this.startDate, MonthlyReportServiceTest.this.endDate)
                        .maxResults(SummaryReportConstants.MonthlyReport.MAX_VISITED_COMPANIES_RECORD).getResultList();
                this.result = MonthlyReportServiceTest.this.listCompanyVisited;

                Deencapsulation.invoke(MonthlyReportServiceTest.this.periodicReportExportExcel, "findEmployeeByCode");
                this.result = MonthlyReportServiceTest.this.employee;

                Deencapsulation
                        .invoke(MonthlyReportServiceTest.this.periodicReportExportExcel, "getAddresspointByCode");
                this.result = MonthlyReportServiceTest.this.addresspoint;

                Deencapsulation.invoke(MonthlyReportServiceTest.this.periodicReportExportExcel, "getCreatorName");
                this.result = "faked_creator_name";

            }
        };
        AbstractExportFactory factory =
                ExportFileFactory.getFactory(SummaryReportConstants.PeriodicReport.PERIODIC_REPORT_TYPE);
        ReportParameter param =
                new ReportParameter(this.startDate, this.endDate, SummaryReportConstants.HQ_CODE,
                        String.valueOf(this.employee.getEmp_code()), this.monthlyReportRevision);
        AbstractExportExcel periodicReportExportExcel = factory.createReport(param);
        Workbook testExcelActual = periodicReportExportExcel.createContent();


        new Verifications() {
            {
                Deencapsulation.invoke(MonthlyReportServiceTest.this.periodicReportExportExcel,
                        "getBranchCodeOfEmployee");
                this.times = 1;

                MonthlyReportServiceTest.this.dailyRepo
                        .getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate(this.anyInt,
                                MonthlyReportServiceTest.this.startDate, MonthlyReportServiceTest.this.endDate)
                        .maxResults(SummaryReportConstants.MonthlyReport.MAX_VISITED_COMPANIES_RECORD).getResultList();
                this.times = 1;

                Deencapsulation
                        .invoke(MonthlyReportServiceTest.this.periodicReportExportExcel, "getAddresspointByCode");
                this.times = 1;

                Deencapsulation.invoke(MonthlyReportServiceTest.this.periodicReportExportExcel, "getCreatorName");
                this.times = 1;
                Deencapsulation.invoke(MonthlyReportServiceTest.this.periodicReportExportExcel, "writeComment",
                        this.withAny(Sheet.class), this.withAny(Workbook.class),
                        this.withAny(Monthly_report_revision.class));
                this.times = 1;
            }

            {
                Assert.assertEquals(MonthlyReportServiceTest.this.workbook.getNumberOfSheets(), 1);
            }

            Sheet sheet = testExcelActual.getSheetAt(0);

            // Verify header
            {
                Row headerRow = this.sheet.getRow(SummaryReportConstants.PeriodicReport.HEADER_BRANCH_INFO_ROW_INDEX);

                // Verify created year of report
                Assertions.assertThat(
                        headerRow.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_YEAR_INDEX)
                                .getStringCellValue()).isEqualTo(
                        DateUtils.getYear(MonthlyReportServiceTest.this.startDate) + SummaryReportConstants.YEAR_JP);

                // Verify created month of report
                Assertions.assertThat(
                        headerRow.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_MONTH_INDEX)
                                .getStringCellValue())
                        .isEqualTo(
                                DateUtils.getMonth(MonthlyReportServiceTest.this.startDate)
                                        + SummaryReportConstants.MONTHLY_JP);

                // Verify branch name
                Assertions.assertThat(
                        headerRow.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_BRANCH_NAME_INDEX)
                                .getStringCellValue()).isEqualTo(
                        MonthlyReportServiceTest.this.addresspoint.getAdp_name());

                Row headerPositionRow =
                        this.sheet.getRow(SummaryReportConstants.MonthlyReport.HEADER_POSITION_INFO_INDEX);

                // Verify position
                Assertions.assertThat(
                        headerPositionRow.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_POSITION_INDEX)
                                .getStringCellValue()).isEqualTo(
                        MonthlyReportServiceTest.this.employee.getPosition_mst().getPos_name());

                Row headerCreatorRow =
                        this.sheet.getRow(SummaryReportConstants.MonthlyReport.HEADER_CREATOR_INFO_INDEX);

                // Verify creator
                Assertions.assertThat(
                        headerCreatorRow.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_POSITION_INDEX)
                                .getStringCellValue()).isEqualTo("faked_creator_name");
            }

            // Verify list companies visited
            {
                Cell companiesVisit =
                        this.sheet.getRow(SummaryReportConstants.MonthlyReport.COMPANY_VISITED_ROW_INDEX).getCell(
                                SummaryReportConstants.MonthlyReport.COMPANY_VISITED_COL_INDEX);
                Assertions.assertThat(companiesVisit.getStringCellValue()).isEqualTo(
                        MonthlyReportServiceTest.this.companiesVisitedInfo);
            }

        };

        // Verify visit times
        for (Map.Entry<Short, String> entry : SummaryReportConstants.MonthlyReport.getMapVisitTimeCellCoordinates()
                .entrySet()) {
            CellReference cr = new CellReference(entry.getValue());
            Row row = this.workbook.getSheetAt(0).getRow(cr.getRow());
            Cell cell = row.getCell(cr.getCol());

            Assertions.assertThat(cell.getNumericCellValue()).isEqualTo(1);
        }

        // Verify purpose
        for (Map.Entry<Short, String> entry : SummaryReportConstants.MonthlyReport.getMapPurposeCellCoordinates()
                .entrySet()) {
            CellReference cr = new CellReference(entry.getValue());
            Row row = this.workbook.getSheetAt(0).getRow(cr.getRow());
            Cell cell = row.getCell(cr.getCol());

            Assertions.assertThat(cell.getNumericCellValue()).isEqualTo(1);
        }
    }

    @Test
    public void saveNewMonthlyReportTest() {
        new Expectations() {
            {
                MonthlyReportServiceTest.this.monthly_report_revisionRepository.saveAndFlushAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.result = MonthlyReportServiceTest.this.monthlyReportResult;
            }
        };

        ServiceResult<Monthly_report_revision> result =
                this.service.saveTemporaryMonthlyReport(this.monthlyReportRevision);

        new Verifications() {
            {
                MonthlyReportServiceTest.this.monthly_report_revisionRepository.saveAndFlushAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.times = 1;
            }
        };

        Assertions.assertThat(result.isSuccessful()).isTrue();
        Assertions.assertThat(result.getData()).isNotNull();
        Assertions.assertThat(result.getData().getShounin_joutai()).isEqualTo(
                SummaryReportConstants.MonthlyReport.STATUS_OPEN);
        Assertions.assertThat(result.getData().getRepoto_no_ribijon_no_sakujo_furagu()).isFalse();
        Assertions.assertThat(result.getData().getGenka_ritsu()).isEqualTo(1d);
        Assertions.assertThat(result.getMessages().size()).isEqualTo(1);
        Assertions.assertThat(result.getMessages().get(0).getMessageCode()).isEqualTo(
                InfoMessage.common_001_save_successfully().getMessageCode());
    }

    @Test
    public void saveExistedMonthlyReportTest() {
        new MockUp<Abstract_entity>() {
            @Mock
            private boolean isPersisted() {
                return true;
            }
        };

        new Expectations() {
            {
                MonthlyReportServiceTest.this.monthly_report_revisionRepository.saveAndFlushAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.result = MonthlyReportServiceTest.this.monthlyReportResult;
            }
        };

        ServiceResult<Monthly_report_revision> result =
                this.service.saveTemporaryMonthlyReport(this.monthlyReportRevision);

        new Verifications() {
            {
                MonthlyReportServiceTest.this.monthly_report_revisionRepository.saveAndFlushAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.times = 1;
            }
        };

        Assertions.assertThat(result.isSuccessful()).isTrue();
        Assertions.assertThat(result.getData()).isNotNull();
        Assertions.assertThat(result.getData().getShounin_joutai()).isEqualTo(
                SummaryReportConstants.MonthlyReport.STATUS_OPEN);
        Assertions.assertThat(result.getData().getRepoto_no_ribijon_no_sakujo_furagu()).isFalse();
        Assertions.assertThat(result.getData().getGenka_ritsu()).isEqualTo(1d);
        Assertions.assertThat(result.getMessages().size()).isEqualTo(1);
        Assertions.assertThat(result.getMessages().get(0).getMessageCode()).isEqualTo(
                InfoMessage.common_001_save_successfully().getMessageCode());
    }

    @Test
    public void submitMonthlyReportTest() {
        MonthlyReportServiceTest.this.monthlyReportResult
        .setShounin_joutai(SummaryReportConstants.MonthlyReport.STATUS_WAITING);
        new Expectations() {
            {
                MonthlyReportServiceTest.this.monthly_report_revisionRepository.saveAndFlushAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.result = MonthlyReportServiceTest.this.monthlyReportResult;

                MonthlyReportServiceTest.this.monthly_report_historyRepo.saveAndFlushAndRefresh(this
                        .withAny(new Monthly_report_history()));
                this.result = MonthlyReportServiceTest.this.monthlyReportHistory;
            }
        };

        ServiceResult<Monthly_report_revision> result =
                this.service.submitMonthlyReport(this.monthlyReportRevision, 11111);

        new Verifications() {
            {
                MonthlyReportServiceTest.this.monthly_report_revisionRepository.saveAndFlushAndRefresh(this
                        .withAny(new Monthly_report_revision()));
                this.times = 1;

                MonthlyReportServiceTest.this.monthly_report_historyRepo.saveAndFlushAndRefresh(this
                        .withAny(new Monthly_report_history()));
                this.times = 1;
            }
        };

        Assertions.assertThat(result.isSuccessful()).isTrue();
        Assertions.assertThat(result.getData()).isNotNull();
        Assertions.assertThat(result.getData().getShounin_joutai()).isEqualTo(
                SummaryReportConstants.MonthlyReport.STATUS_WAITING);
        Assertions.assertThat(result.getData().getRepoto_no_ribijon_no_sakujo_furagu()).isFalse();
        Assertions.assertThat(result.getData().getGenka_ritsu()).isEqualTo(1d);
        Assertions.assertThat(result.getMessages().size()).isEqualTo(1);
        Assertions.assertThat(result.getMessages().get(0).getMessageCode()).isEqualTo(
                InfoMessage.common_003_submission_successfully().getMessageCode());
    }
}
