/*-------------------------------------------------------------------------------
 * All Rights Reserved. Copyright(C) Arrow Technology, Ltd.
 * revision : ???
 * vendor : Arrow Technology, Ltd.
 * author 　: {creator}
 * since : Dec 9, 2015
 *-------------------------------------------------------------------------------
 * revision marking
 * Dec 9, 2015 : first version: {creator}
 *-----------------------------------------------------------------------------*/
package arrow.businesstraceability.export.base.imp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

import arrow.businesstraceability.constant.SummaryReportConstants;
import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.export.base.AbstractExportExcel;
import arrow.businesstraceability.export.param.ReportParameter;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Monthly_report_revision;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.businesstraceability.persistence.repository.Daily_reportRepository;
import arrow.businesstraceability.persistence.repository.Employee_mstRepository;
import arrow.businesstraceability.util.ExportExcelUtils;
import arrow.businesstraceability.util.excel.TemplateFactory;
import arrow.framework.logging.BaseLogger;
import arrow.framework.util.DateUtils;
import arrow.framework.util.StringUtils;
import arrow.framework.util.cdi.Instance;
import arrow.framework.util.collections.CollectionUtils;


/**
 * The Class PeriodicReportExportExcel.
 */
/*
 * Description of class.
 * @author ArrowTechnology, Ltd.
 * @version Dec 9, 2015 {revision}
 */
public class PeriodicReportExportExcel extends AbstractExportExcel {

    /** The branch code. */
    private String branchCode;

    /** The employee code. */
    private final String employeeCode;

    /** The start date. */
    private final Date startDate;

    /** The end date. */
    private final Date endDate;

    /** The daily repo. */
    private Daily_reportRepository dailyRepo;

    /** The employee repo. */
    private Employee_mstRepository employeeRepo;

    /** The address repo. */
    private Addresspoint_mstRepository addressRepo;

    private final Monthly_report_revision monthlyReportRevision;

    /**
     * Constructor PeriodicReportExportExcel.
     *
     * @param param The ReportParameter
     */
    public PeriodicReportExportExcel(final ReportParameter param) {
        this.branchCode = param.getBranchCode();
        this.employeeCode = param.getEmployeeCode();
        this.startDate = param.getStartDate();
        this.endDate = param.getEndDate();
        this.monthlyReportRevision = param.getMonthlyReportRevision();
    }


    /**
     * Inits the repository.
     */
    private void initRepository() {
        if (this.dailyRepo == null) {
            this.dailyRepo = Instance.get(Daily_reportRepository.class);
        }
        if (this.employeeRepo == null) {
            this.employeeRepo = Instance.get(Employee_mstRepository.class);
        }
        if (this.addressRepo == null) {
            this.addressRepo = Instance.get(Addresspoint_mstRepository.class);
        }
    }

    /* (non-Javadoc)
     * @see arrow.businesstraceability.export.base.AbstractExportExcel#createContent()
     */
    @Override
    @SuppressWarnings(value = "DMI_HARDCODED_ABSOLUTE_FILENAME", justification = "We use relative path already.")
    public Workbook createContent() {
        this.initRepository();
        if ((StringUtils.isEmpty(this.branchCode) || SummaryReportConstants.HQ_CODE.equals(this.branchCode))
                && StringUtils.isNotEmpty(this.employeeCode)) {
            this.branchCode = this.getBranchCodeOfEmployee();
        }

        final List<Object[]> listCompanyVisited = this.getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate();

        final Employee_mst selectedEmployee = this.findEmployeeByCode();


        try {
            final Workbook workbook =
                    ExportExcelUtils.createWorkbook(SummaryReportConstants.TEMPLATE_DIR
                            + SummaryReportConstants.MonthlyReport.MONTHLY_REPORT_TEMPLATE);

            final Sheet sheet = workbook.getSheetAt(0);

            // 1. Write the header
            this.writeHeader(sheet, selectedEmployee, this.monthlyReportRevision);

            // 2. Write Visit times
            this.writeVisitTimes(sheet, workbook, this.createListVisitTimeValue());

            // 3. Write Purpose
            this.writePurpose(sheet, workbook, this.createListPurposeValue());

            // 4. Write Company visited
            this.writeListCompanyVisited(sheet, workbook, listCompanyVisited);

            // 5. Write Comment
            this.writeComment(sheet, workbook, this.monthlyReportRevision);

            // 6. Write Staff Management
            this.writeStaffManagement(sheet, workbook);

            // 7. Write Recruit info
            this.writeRecruitInfo(sheet, workbook);


            return workbook;
        } catch (final IOException e) {
            Instance.get(BaseLogger.class).debug("error on generating report:", e);
        } catch (final InvalidFormatException e) {
            Instance.get(BaseLogger.class).debug("error on generating report:", e);
        }
        return null;
    }

    /**
     * Write header of report.
     *
     * @param sheet Current sheet of report
     * @param selectedEmployee the selected employee
     */
    private void writeHeader(final Sheet sheet, final Employee_mst selectedEmployee,
            final Monthly_report_revision monthlyReportRevision) {
        final Addresspoint_mst branch = this.getAddresspointByCode();

        final Row headerBranchRow = sheet.getRow(SummaryReportConstants.PeriodicReport.HEADER_BRANCH_INFO_ROW_INDEX);

        final Cell reportYear = headerBranchRow.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_YEAR_INDEX);
        TemplateFactory.process(reportYear, TemplateFactory.Parameters.REPORT_YEAR, DateUtils.getYear(this.startDate));

        final Cell reportMonth = headerBranchRow.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_MONTH_INDEX);
        TemplateFactory.process(reportMonth, TemplateFactory.Parameters.REPORT_MONTH,
                DateUtils.getMonth(this.startDate));

        final Cell branchName =
                headerBranchRow.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_BRANCH_NAME_INDEX);
        TemplateFactory.process(branchName, TemplateFactory.Parameters.BRANCH_NAME, branch.getAdp_name());

        final Row headerPositionRow = sheet.getRow(SummaryReportConstants.MonthlyReport.HEADER_POSITION_INFO_INDEX);
        final Cell positionCell =
                headerPositionRow.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_POSITION_INDEX);
        TemplateFactory.process(positionCell, TemplateFactory.Parameters.POSITION, selectedEmployee.getPosition_mst()
                .getPos_name());

        final Row headerCreatorRow = sheet.getRow(SummaryReportConstants.MonthlyReport.HEADER_CREATOR_INFO_INDEX);
        final Cell creator = headerCreatorRow.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_POSITION_INDEX);
        TemplateFactory.process(creator, TemplateFactory.Parameters.CREATOR, this.getCreatorName());

        final Row headerWorkingDay = sheet.getRow(SummaryReportConstants.MonthlyReport.HEADER_WORKING_DAY_INDEX);
        final Cell workingDay = headerWorkingDay.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_WORKING_DAY);
        final Integer workingDayValue =
                monthlyReportRevision.getKadou_nissuu() != null ? monthlyReportRevision.getKadou_nissuu() : 0;
        TemplateFactory.process(workingDay, TemplateFactory.Parameters.WORKING_DAY, workingDayValue);
        // workingDay.setCellValue(monthlyReportRevision.getKadou_nissuu());

        final Cell dailyInput = headerWorkingDay.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_DAILY_INPUT);
        final Integer dailyInputValue = monthlyReportRevision
                .getNyuryoku_nissuu() != null ? monthlyReportRevision.getNyuryoku_nissuu() : 0;
        TemplateFactory.process(dailyInput, TemplateFactory.Parameters.DAILY_INPUT, dailyInputValue);

        final Cell month1st = headerWorkingDay.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_MONTH_1ST);
        Integer month1stValue = monthlyReportRevision
                .getTougetsu_tsuitachi_kara_no_haken_shain_suu() != null ? monthlyReportRevision
                                        .getTougetsu_tsuitachi_kara_no_haken_shain_suu() : 0;
        TemplateFactory.process(month1st, TemplateFactory.Parameters.MONTH_1ST, month1stValue);

        final Cell nextMonth1st =
                headerWorkingDay.getCell(SummaryReportConstants.MonthlyReport.HEADER_COL_NEXT_MONTH_1ST);
        Integer nextMonth1stValue = monthlyReportRevision
                .getJigetsu_tsuitachi_kara_no_haken_shain_suu() != null ? monthlyReportRevision
                .getJigetsu_tsuitachi_kara_no_haken_shain_suu() : 0;
        TemplateFactory.process(nextMonth1st, TemplateFactory.Parameters.NEXT_MONTH_1ST, nextMonth1stValue);
    }

    /**
     * Write visit time info into report.
     *
     * @param sheet Current sheet of report
     * @param workbook The workbook to generate report
     * @param listVisitTimes The list info of visit time
     * */
    private void writeVisitTimes(final Sheet sheet, final Workbook workbook, final List<Object[]> listVisitTimes) {
        int count = 0;
        for (Map.Entry<Short, String> entry : SummaryReportConstants.MonthlyReport.getMapVisitTimeCellCoordinates()
                .entrySet()) {
            CellReference cr = new CellReference(entry.getValue());
            Row row = sheet.getRow(cr.getRow());
            Cell cell = row.getCell(cr.getCol());

            final List<Object[]> object =
                    listVisitTimes.stream().filter(p -> p[1] != null ? entry.getKey() == (short) p[1] : false)
                            .collect(Collectors.toList());
            final int cellValue =
                    CollectionUtils.isNotEmpty(object) ? (object.get(0)[0] != null ? (int) object.get(0)[0] : 0) : 0;

            cell.setCellValue(cellValue);
            count += cellValue;
        }

        // Set value Total visited.
        final Cell totalCell =
                sheet.getRow(SummaryReportConstants.MonthlyReport.TOTAL_ROW_INDEX).getCell(
                        SummaryReportConstants.MonthlyReport.TOTAL_COL_INDEX);
        TemplateFactory.process(totalCell, TemplateFactory.Parameters.TOTAL, count);
        final CellStyle totalCellStyle = workbook.createCellStyle();

        totalCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        totalCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        totalCell.setCellStyle(totalCellStyle);
    }

    /**
     * Write purpose info into report.
     *
     * @param sheet Current sheet of report
     * @param workbook The workbook to generate report
     * @param listPurpose The list info of purpose
     * */
    private void writePurpose(final Sheet sheet, final Workbook workbook, final List<Object[]> listPurpose) {
        final CellStyle myStyle = ExportExcelUtils.makeFullBorderStyle(workbook);
        for (Map.Entry<Short, String> entry : SummaryReportConstants.MonthlyReport.getMapPurposeCellCoordinates()
                .entrySet()) {
            CellReference cr = new CellReference(entry.getValue());
            Row row = sheet.getRow(cr.getRow());
            Cell cell = row.getCell(cr.getCol());

            final List<Object[]> object =
                    listPurpose.stream().filter(p -> p[1] != null ? entry.getKey() == (short) p[1] : false)
                            .collect(Collectors.toList());
            final int cellValue =
                    CollectionUtils.isNotEmpty(object) ? (object.get(0)[0] != null ? (int) object.get(0)[0] : 0) : 0;

            cell.setCellValue(cellValue);
            cell.setCellStyle(myStyle);
        }
    }

    /**
     * Write list companies visited into report.
     *
     * @param sheet Current sheet of report
     * @param workbook The workbook to generate report
     * @param listCompanyVisited The list info of companies
     * */
    private void writeListCompanyVisited(final Sheet sheet, final Workbook workbook,
            final List<Object[]> listCompanyVisited) {
        if (CollectionUtils.isEmpty(listCompanyVisited)) {
            return;
        }
        Row row = sheet.getRow(SummaryReportConstants.MonthlyReport.COMPANY_VISITED_ROW_INDEX);
        Cell cell = row.getCell(SummaryReportConstants.MonthlyReport.COMPANY_VISITED_COL_INDEX);
        StringBuilder sb = new StringBuilder();
        for (Object[] object : listCompanyVisited) {
            sb.append("; " + object[0].toString() + "(" + object[1].toString() + ")");
        }
        cell.setCellValue(sb.toString().replaceFirst("; ", ""));
    }

    private void writeComment(final Sheet sheet, final Workbook workbook,
            final Monthly_report_revision monthlyReportRevision) {
        final CellStyle myStyle = ExportExcelUtils.getTextCellStyle(workbook);
        final Row reasonRow = sheet.getRow(SummaryReportConstants.MonthlyReport.CAUSE_CURRENT_MONTH);
        Cell reasonCell = reasonRow.createCell(SummaryReportConstants.MonthlyReport.SUMMARY_COL_INDEX);
        reasonCell.setCellValue(monthlyReportRevision.getNo_kekka_ni_kansuru_genin());
        reasonCell.setCellStyle(myStyle);
        final Row motiveRow = sheet.getRow(SummaryReportConstants.MonthlyReport.CONCREATE_ACTION);
        Cell motiveCell = motiveRow.createCell(SummaryReportConstants.MonthlyReport.SUMMARY_COL_INDEX);
        motiveCell.setCellValue(monthlyReportRevision.getSono_genin_ga_shoujita_riyuu());
        motiveCell.setCellStyle(myStyle);
        final Row targetRow = sheet.getRow(SummaryReportConstants.MonthlyReport.LIST_VISITED);
        Cell targetCell = targetRow.createCell(SummaryReportConstants.MonthlyReport.SUMMARY_COL_INDEX);
        targetCell.setCellValue(monthlyReportRevision.getJigetsuno_mokuhyou());
        targetCell.setCellStyle(myStyle);

    }

    private void writeStaffManagement(final Sheet sheet, final Workbook workbook) {
        Row staffManagementRow = sheet.getRow(SummaryReportConstants.MonthlyReport.STAFF_MANAGEMENT_ROW_INDEX);

        Cell phoneFollow = staffManagementRow.getCell(SummaryReportConstants.MonthlyReport.PHONE_FOLLOW_COL_INDEX);
        this.checkAndSetCellValue(phoneFollow, this.monthlyReportRevision.getSutaffu_kanri_denwa_forosuu());

        Cell directFollow = staffManagementRow.getCell(SummaryReportConstants.MonthlyReport.DIRECT_FOLLOW_COL_INDEX);
        this.checkAndSetCellValue(directFollow, this.monthlyReportRevision.getSutaffu_kanri_mensetsu_forosuu());

        Cell staffClaim = staffManagementRow.getCell(SummaryReportConstants.MonthlyReport.STAFF_CLAIM_COL_INDEX);
        this.checkAndSetCellValue(staffClaim, this.monthlyReportRevision.getSutaffu_kanri_kuremu_suu());

        Cell staffOther = staffManagementRow.getCell(SummaryReportConstants.MonthlyReport.STAFF_OTHER_COL_INDEX);
        this.checkAndSetCellValue(staffOther, this.monthlyReportRevision.getSutaffu_kanri_sono_ta_no_kazu());
    }

    private void writeRecruitInfo(final Sheet sheet, final Workbook workbook) {
        Row recruitRow = sheet.getRow(SummaryReportConstants.MonthlyReport.RECRUIT_ROW_INDEX);

        Cell primaryInteview = recruitRow.getCell(SummaryReportConstants.MonthlyReport.PRIMARY_INTEVIEW_COL_INDEX);
        this.checkAndSetCellValue(primaryInteview, this.monthlyReportRevision.getIchiji_mensetsu_no_kensuu());

        Cell secondaryInteview = recruitRow.getCell(SummaryReportConstants.MonthlyReport.SECONDARY_INTEVIEW_COL_INDEX);
        this.checkAndSetCellValue(secondaryInteview, this.monthlyReportRevision.getNiji_mensetsu_no_kensuu());

        Cell adoption = recruitRow.getCell(SummaryReportConstants.MonthlyReport.ADOPTION_COL_INDEX);
        this.checkAndSetCellValue(adoption, this.monthlyReportRevision.getSaiyou_suu());

        Cell employeeFollow = recruitRow.getCell(SummaryReportConstants.MonthlyReport.EMPLOYEE_FOLLOW_COL_INDEX);
        this.checkAndSetCellValue(employeeFollow, this.monthlyReportRevision.getSaiyou_sapoto_ninzuu());

        Cell newDevelopment = recruitRow.getCell(SummaryReportConstants.MonthlyReport.NEW_DEVELOPMENT_COL_INDEX);
        this.checkAndSetCellValue(newDevelopment, this.monthlyReportRevision.getShinki_kaitaku_suu());

        Cell increase = recruitRow.getCell(SummaryReportConstants.MonthlyReport.INCREASE_COL_INDEX);
        this.checkAndSetCellValue(increase, this.monthlyReportRevision.getZouka_suu());

        Cell salePh = recruitRow.getCell(SummaryReportConstants.MonthlyReport.SALE_PH_COL_INDEX);
        this.checkAndSetCellValue(salePh, this.monthlyReportRevision.getUriage_ph());
        Cell costRate = recruitRow.getCell(SummaryReportConstants.MonthlyReport.COST_RATE_COL_INDEX);
        this.checkAndSetCellValue(costRate, this.monthlyReportRevision.getGenka_ritsu());
    }

    private void checkAndSetCellValue(final Cell cell, final Object value) {
        if (value != null) {
            cell.setCellValue(value.toString());
        }
        else {
            cell.setCellValue(0);
        }
    }

    /**
     * Create list visit time info from monthly_report_revision.
     *
     * @return list visit time info.
     */
    private List<Object[]> createListVisitTimeValue() {
        List<Object[]> objects = new ArrayList<Object[]>();
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_shokushu_sofuto_wea(), (short) 201});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_shokushu_netto_waku(), (short) 202});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_shokushu_kenchiku(), (short) 101});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_shokushu_doboku(), (short) 102});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_shokushu_setsubi(), (short) 104});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_shokushu_denki(), (short) 103});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_shokushu_puranto(), (short) 501});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_shokushu_ippan(), (short) 601});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_shokushu_tsuushin_musen(), (short) 301});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_shokushu_tsuushin_yuusen(), (short) 302});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_shokushu_kikai_hado(), (short) 401});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_shokushu_jimu(), (short) 602});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_shokushu_koru_centa(), (short) 603});
        return objects;
    }

    /**
     * Create list purpose info from monthly_report_revision.
     *
     * @return list purpose info.
     */
    private List<Object[]> createListPurposeValue() {
        List<Object[]> objects = new ArrayList<Object[]>();
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_mokuteki_shinkihoumon(), (short) 2});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_mokuteki_kizon_no_teiki_houmon(),
                                  (short) 4});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_mokuteki_kizon_igai_no_teiki_houmon(),
                                  (short) 3});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_mokuteki_gijutsusha_shoukai(), (short) 5});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_mokuteki_gyouda(), (short) 6});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_mokuteki_mitsumori(), (short) 7});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_mokuteki_kuremu(), (short) 8});
        objects.add(new Object[] {this.monthlyReportRevision.getHoumon_kensuu_mokuteki_sonota(), (short) 1});
        return objects;
    }

    /**
     * Gets the all company visited info from daily report by start date and end date.
     *
     * @return the all company visited info from daily report by start date and end date
     */
    private List<Object[]> getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate() {
        return this.dailyRepo
                .getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate(Integer.parseInt(this.employeeCode),
                        this.startDate, this.endDate)
                .maxResults(SummaryReportConstants.MonthlyReport.MAX_VISITED_COMPANIES_RECORD).getResultList();
    }

    /**
     * Gets the branch code of employee.
     *
     * @return the branch code of employee
     */
    private String getBranchCodeOfEmployee() {
        return this.addressRepo.getBranchCodeOfEmployee(Integer.parseInt(this.employeeCode));
    }

    /**
     * Gets the creator name.
     *
     * @return the creator name
     */
    private String getCreatorName() {
        return Instance.get(UserCredential.class).getEmployeeName();
    }

    /**
     * Find employee by code.
     *
     * @return the employee_mst
     */
    private Employee_mst findEmployeeByCode() {
        return this.employeeRepo.findEmployeeByCode(Integer.parseInt(this.employeeCode)).getAnyResult();
    }

    /**
     * Gets the addresspoint by code.
     *
     * @return the addresspoint by code
     */
    private Addresspoint_mst getAddresspointByCode() {
        return this.addressRepo.findAddresspointByCode(this.branchCode).getAnyResult();
    }

}
