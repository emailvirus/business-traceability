package arrow.businesstraceability.control.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.event.Event;
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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import arrow.businesstraceability.constant.SummaryReportConstants;
import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.control.service.NotificationService.RegisterActivityEvent;
import arrow.businesstraceability.export.base.AbstractExportExcel;
import arrow.businesstraceability.export.base.imp.VisitTimeReportExportExcel;
import arrow.businesstraceability.export.factory.AbstractExportFactory;
import arrow.businesstraceability.export.factory.ExportFileFactory;
import arrow.businesstraceability.export.param.ReportParameter;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Basepoint_info;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Daily_activity_type;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.businesstraceability.persistence.repository.Company_mstRepository;
import arrow.businesstraceability.persistence.repository.Daily_activity_typeRepository;
import arrow.businesstraceability.persistence.repository.Daily_reportRepository;
import arrow.businesstraceability.persistence.repository.Employee_mstRepository;
import arrow.businesstraceability.util.ExportExcelUtils;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DateUtils;
import arrow.framework.util.cdi.Instance;

/**
 * The Class VisitTimeTest.
 */
@RunWith(JMockit.class)
public class VisitTimeReportExportExcelTest {


    @Mocked
    @Tested
    VisitTimeReportExportExcel visitTime;

    /** The daily report service. */
    @Injectable
    DailyReportService dailyReportService;

    @Injectable
    ReportParameter param;

    /** The daily report repo. */
    @Injectable
    Daily_reportRepository dailyReportRepo;

    /** The daily_activity_type repo. */
    @Injectable
    Daily_activity_typeRepository daily_activity_typeRepo;

    /** The company repo. */
    @Injectable
    Company_mstRepository companyRepo;

    /** The export excel utils. */
    @Tested
    @Injectable
    ExportExcelUtils exportExcelUtils;

    /** The em locator. */
    @Tested
    @Injectable
    EmLocator emLocator;

    /** The em main. */
    @Injectable
    protected EntityManager emMain;

    /** The event src. */
    @Injectable
    private Event<RegisterActivityEvent> eventSrc;

    /** The address repo. */
    @Injectable
    private Addresspoint_mstRepository addressRepo;

    /** The employee repo. */
    @Injectable
    private Employee_mstRepository employeeRepo;

    /** The user credential. */
    @Injectable
    private UserCredential userCredential;

    @Injectable
    /** The start date. */
    private Date startDate;

    /** The end date. */
    private Date endDate;

    /** The list test. */
    private List<Addresspoint_mst> listTestAddresspoint_mst;

    /** The list company. */
    private List<Company_mst> listCompany;

    /** The workbook. */
    private Workbook workbook;

    /** The workbook expected. */
    private Workbook workbookExpected;

    /** The company. */
    private Company_mst company;

    /** The listEmployee. */
    private List<Employee_mst> listEmployee;

    /** The employee. */
    private Employee_mst employee_mst;

    /** The Daily_activity_type. */
    private List<Daily_activity_type> purposes;

    private Daily_activity_type onetype;

    private Addresspoint_mst testAddresspoint_mst;

    private static Date currentDate = DateUtils.MIN_DATE;

    /**
     * Before.
     *
     * @throws InvalidFormatException the invalid format exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ParseException
     */
    @Before
    public void before() throws InvalidFormatException, IOException, ParseException {
        this.startDate = new GregorianCalendar(2015, Calendar.NOVEMBER, 20).getTime();
        this.endDate = new GregorianCalendar(2015, Calendar.DECEMBER, 30).getTime();
        InputStream file = this.getClass().getResourceAsStream("/visited_report.xls");
        this.workbook = WorkbookFactory.create(file);
        InputStream fileExpected = this.getClass().getResourceAsStream("/expectedExel.xls");
        this.workbookExpected = WorkbookFactory.create(fileExpected);
        this.listTestAddresspoint_mst = new ArrayList<Addresspoint_mst>();
        this.testAddresspoint_mst = new Addresspoint_mst("8888888");
        this.testAddresspoint_mst.setAdp_name("Chi nhanh A");
        this.listTestAddresspoint_mst.add(this.testAddresspoint_mst);
        this.listCompany = new ArrayList<Company_mst>();
        this.company = new Company_mst("999999");
        Basepoint_info basePoint = new Basepoint_info(this.testAddresspoint_mst, this.company);
        List<Basepoint_info> listBase = new ArrayList<Basepoint_info>();
        listBase.add(basePoint);
        this.company.setAddresspoint_mst(this.testAddresspoint_mst);
        this.company.setBasepoint_infos(listBase);
        this.company.setCom_company_furigana("abc");
        this.company.setCom_company_name("Cong ty gi do");
        this.company.setCom_customer_code("1111");
        this.company.setCom_url("http://test");
        this.listCompany.add(this.company);
        this.listEmployee = new ArrayList<Employee_mst>();
        int emp_Code = 11111;
        this.employee_mst = new Employee_mst(emp_Code);
        this.employee_mst.setEmp_name("Ngoc");
        this.listEmployee.add(this.employee_mst);
        short dat_code = (short) 123456;
        this.onetype = new Daily_activity_type(dat_code);
        this.onetype.setDat_name("Toi choi");
        this.purposes = new ArrayList<Daily_activity_type>();
        this.purposes.add(this.onetype);
    }

    @Test
    public void testCreateWorkbookTemplateExactly(@Mocked final Instance instance) throws IOException,
            InvalidFormatException {
        // Define the Expectations block here
        new Expectations() {
            {
                ExportExcelUtils.createWorkbook(this.anyString);
                this.result = VisitTimeReportExportExcelTest.this.workbook;
            }
        };
        Workbook testExcel =
                ExportExcelUtils.createWorkbook(SummaryReportConstants.TEMPLATE_DIR
                        + SummaryReportConstants.VisitTimeReport.VISIT_TIME_REPORT_TEMPLATE);
        final Sheet sheet = testExcel.getSheetAt(0);
        final Row row0 = sheet.getRow(0);
        final Row row1 = sheet.getRow(1);
        final Row row2 = sheet.getRow(2);
        final Cell inCell0Row0 = row0.getCell(0);
        String value00 = inCell0Row0.getStringCellValue();
        String value01 = row0.getCell(1).getStringCellValue();
        String value10 = row1.getCell(0).getStringCellValue();
        Cell value11 = row1.getCell(1);
        String value20 = row2.getCell(0).getStringCellValue();
        Cell value21 = row2.getCell(1);
        row1.getCell(1).getNumericCellValue();
        new Verifications() {
            {
                ExportExcelUtils.createWorkbook(this.anyString);
                this.times = 1;
            }
            {
                // Verify read Template exactly
                Assert.assertEquals(VisitTimeReportExportExcelTest.this.workbook, testExcel);
                Assert.assertEquals("集計対象", value00);
                Assert.assertEquals("全拠点", value01);
                Assert.assertEquals("集計期間", value10);
                Assert.assertEquals("01-Apr-2014", value11.toString());
                Assert.assertEquals("出力日", value20);
                Assert.assertEquals("07-Apr-2014", value21.toString());
            }
        };
    }

    // Verify throw IOException
    @Test(expected = IOException.class)
    public void testIOException() throws InvalidFormatException, IOException {

        new Expectations() {
            {
                ExportExcelUtils.createWorkbook(this.anyString);
                this.result = new IOException();
            }
        };
        AbstractExportFactory factory =
                ExportFileFactory.getFactory(SummaryReportConstants.VisitTimeReport.VISIT_TIME_REPORT_TYPE);
        ReportParameter param = new ReportParameter(this.startDate, this.endDate, this.listCompany);
        AbstractExportExcel test2 = factory.createReport(param);
        test2.createContent();
    }

    // Verify throw InvalidFormatException
    @Test(expected = InvalidFormatException.class)
    public void testInvalidFormatExceptionException() throws InvalidFormatException, IOException {

        new Expectations() {
            {
                ExportExcelUtils.createWorkbook(this.anyString);
                this.result = new InvalidFormatException("");
            }
        };
        AbstractExportFactory factory =
                ExportFileFactory.getFactory(SummaryReportConstants.VisitTimeReport.VISIT_TIME_REPORT_TYPE);
        ReportParameter param = new ReportParameter(this.startDate, this.endDate, this.listCompany);
        AbstractExportExcel test2 = factory.createReport(param);
        test2.createContent();
    }

    /**
     * TestOutput Excel.
     *
     * @param instance the instance
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InvalidFormatException the invalid format exception
     */
    @Test
    public void testOutputExcel(@Mocked final Instance instance) throws IOException, InvalidFormatException {
        new MockUp<DateUtils>() {
            @Mock
            Date getCurrentDatetime() // no access modifier required
            {
                return VisitTimeReportExportExcelTest.currentDate;
            }
        };
        // Define the Expectations block here
        new Expectations() {
            {
                ExportExcelUtils.createWorkbook(this.anyString);
                this.result = VisitTimeReportExportExcelTest.this.workbook;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "countTotalVisitedTimesToCompany", this.anyString, this.withAny(Date.class),
                        this.withAny(Date.class));
                this.result = 1.0;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "getAllEmployeeWentToVisitedCompanyWithBasepoint", this.anyString, this.anyString,
                        this.withAny(Date.class), this.withAny(Date.class));
                this.result = VisitTimeReportExportExcelTest.this.listEmployee;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "getAddresspointEmployeeWasVisitedCompany", this.anyString, this.withAny(Date.class),
                        this.withAny(Date.class));
                this.result = VisitTimeReportExportExcelTest.this.listTestAddresspoint_mst;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "countTotalVisitedTimesToCompanyWithBasepoint", this.anyString, this.anyString,
                        this.withAny(Date.class), this.withAny(Date.class));
                this.result = 1.0;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "getAllPurposeWhenVisitedCompanyWithBasepoint", this.anyString, this.anyString,
                        this.withAny(Date.class), this.withAny(Date.class));
                this.result = VisitTimeReportExportExcelTest.this.purposes;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "countVisitedTimesOfCompanyWithPurposeAndBasepoint", this.anyString, this.anyShort,
                        this.anyString, this.withAny(Date.class), this.withAny(Date.class));
                this.result = 1.0;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "countTotalVisitedTimesToCompanyWithEmployeeAndBasepoint", this.anyString, this.anyInt,
                        this.anyString, this.withAny(Date.class), this.withAny(Date.class));
                this.result = 1.0;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "getAllPurposeWhenVisitedCompanyWithEmployeeAndBasepoint", this.anyString, this.anyInt,
                        this.anyString, this.withAny(Date.class), this.withAny(Date.class));
                this.result = VisitTimeReportExportExcelTest.this.purposes;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "countVisitedTimesOfCompanyWithPurposeAndEmployeeAndBasepoint", this.anyString, this.anyShort,
                        this.anyInt, this.anyString, this.withAny(Date.class), this.withAny(Date.class));
                this.result = 1L;
            }
        };
        AbstractExportFactory factory =
                ExportFileFactory.getFactory(SummaryReportConstants.VisitTimeReport.VISIT_TIME_REPORT_TYPE);
        ReportParameter param = new ReportParameter(this.startDate, this.endDate, this.listCompany);
        this.visitTime = (VisitTimeReportExportExcel) factory.createReport(param);
        Workbook testExcelActual = this.visitTime.createContent();
        final Sheet sheet = testExcelActual.getSheetAt(0);
        // for (int i = 0; i < sheet.getLastRowNum(); i++) {
        // if ((sheet.getRow(i) != null)) {
        // for (short j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
        // System.out.println(sheet.getRow(i).getCell(j).toString());
        // }
        // }
        // }
        final Sheet sheetExpected = this.workbookExpected.getSheetAt(0);

        new Verifications() {
            {
                ExportExcelUtils.createWorkbook(this.anyString);
                this.times = 1;

                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "countTotalVisitedTimesToCompany", this.anyString, this.withAny(Date.class),
                        this.withAny(Date.class));
                this.times = 1;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "getAllEmployeeWentToVisitedCompanyWithBasepoint", this.anyString, this.anyString,
                        this.withAny(Date.class), this.withAny(Date.class));
                this.times = 1;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "getAddresspointEmployeeWasVisitedCompany", this.anyString, this.withAny(Date.class),
                        this.withAny(Date.class));
                this.times = 1;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "countTotalVisitedTimesToCompanyWithBasepoint", this.anyString, this.anyString,
                        this.withAny(Date.class), this.withAny(Date.class));
                this.times = 1;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "getAllPurposeWhenVisitedCompanyWithBasepoint", this.anyString, this.anyString,
                        this.withAny(Date.class), this.withAny(Date.class));
                this.times = 1;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "countVisitedTimesOfCompanyWithPurposeAndBasepoint", this.anyString, this.anyShort,
                        this.anyString, this.withAny(Date.class), this.withAny(Date.class));
                this.times = 1;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "countTotalVisitedTimesToCompanyWithEmployeeAndBasepoint", this.anyString, this.anyInt,
                        this.anyString, this.withAny(Date.class), this.withAny(Date.class));
                this.times = 1;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "getAllPurposeWhenVisitedCompanyWithEmployeeAndBasepoint", this.anyString, this.anyInt,
                        this.anyString, this.withAny(Date.class), this.withAny(Date.class));
                this.times = 1;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "countVisitedTimesOfCompanyWithPurposeAndEmployeeAndBasepoint", this.anyString, this.anyShort,
                        this.anyInt, this.anyString, this.withAny(Date.class), this.withAny(Date.class));
                this.times = 1;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "writeVisitTimeReportCompanyHeader", this.withAny(Sheet.class),
                        this.withAny(Company_mst.class), this.anyInt, this.withAny(CellStyle.class));
                this.times = 1;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime, "writeVisitTimeReportHeader",
                        this.withAny(Sheet.class), this.withAny(Date.class), this.withAny(Date.class));
                this.times = 1;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime, "writeVisitedTimesWithBasepoint",
                        this.withAny(Sheet.class), this.withAny(Company_mst.class),
                        this.withAny(Addresspoint_mst.class), this.anyInt, this.withAny(CellStyle.class),
                        this.withAny(CellStyle.class), this.withAny(CellStyle.class), this.withAny(Date.class),
                        this.withAny(Date.class));
                this.times = 1;
                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime,
                        "writeVisitedTimesForEachEmployee", this.withAny(Sheet.class), this.anyInt,
                        this.withAny(Company_mst.class), this.withAny(Addresspoint_mst.class),
                        this.withAny(CellStyle.class), this.withAny(CellStyle.class), this.withAny(CellStyle.class),
                        this.withAny(Date.class), this.withAny(Date.class));
                this.times = 1;

                Deencapsulation.invoke(VisitTimeReportExportExcelTest.this.visitTime, "createCellContent",
                        this.withAny(Row.class), this.anyString, this.withAny(CellStyle.class), this.anyInt);
                this.times = 20;
            }
        };
        // Verify output with expected by compare 2 file excel
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i) == null) {
                continue;
            }
            for (short j = 0; j < sheet.getRow(i).getLastCellNum(); j++) {
                if (sheet.getRow(i).getCell(j) == null) {
                    continue;
                }
                if (sheetExpected.getRow(i).getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    Assertions.assertThat(Double.valueOf(sheetExpected.getRow(i).getCell(j).toString())).isEqualTo(
                            Double.valueOf(sheet.getRow(i).getCell(j).toString()));
                }
                else {
                    Assertions.assertThat(sheetExpected.getRow(i).getCell(j).toString()).isEqualTo(
                            sheet.getRow(i).getCell(j).toString());
                }
            }
        }

    }
}
