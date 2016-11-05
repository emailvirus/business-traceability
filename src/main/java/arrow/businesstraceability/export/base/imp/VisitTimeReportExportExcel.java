package arrow.businesstraceability.export.base.imp;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import edu.umd.cs.findbugs.annotations.SuppressWarnings;

import arrow.businesstraceability.constant.SummaryReportConstants;
import arrow.businesstraceability.export.base.AbstractExportExcel;
import arrow.businesstraceability.export.param.ReportParameter;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Daily_activity_type;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.businesstraceability.persistence.repository.Daily_activity_typeRepository;
import arrow.businesstraceability.persistence.repository.Daily_reportRepository;
import arrow.businesstraceability.persistence.repository.Employee_mstRepository;
import arrow.businesstraceability.util.ExportExcelUtils;
import arrow.framework.util.DateUtils;
import arrow.framework.util.cdi.Instance;
import arrow.framework.util.collections.CollectionUtils;




/**
 * The Class VisitTimeReportExportExcel.
 */
public class VisitTimeReportExportExcel extends AbstractExportExcel {

    /** The start date. */
    private Date startDate;

    /** The end date. */
    private Date endDate;

    /** The companies. */
    private List<Company_mst> companies;


    /**
     * Get parameter.
     *
     * @param param the param
     */
    public VisitTimeReportExportExcel(final ReportParameter param) {
        this.startDate = param.getStartDate();
        this.endDate = param.getEndDate();
        this.companies = param.getCompanies();
    }

    /* (non-Javadoc)
     * @see arrow.businesstraceability.export.base.AbstractExportExcel#createContent()
     */
    @Override
    @SuppressWarnings(value = "DMI_HARDCODED_ABSOLUTE_FILENAME", justification = "We use relative path already.")
    public Workbook createContent() throws InvalidFormatException, IOException {
        Workbook workbook =
                ExportExcelUtils.createWorkbook(SummaryReportConstants.TEMPLATE_DIR
                        + SummaryReportConstants.VisitTimeReport.VISIT_TIME_REPORT_TEMPLATE);

        final Sheet sheet = workbook.getSheetAt(0);

        // create style for cell
        CellStyle textCellStyle = ExportExcelUtils.getTextCellStyleVisitedTimes(workbook);
        CellStyle numberCellStyle = ExportExcelUtils.getNumberCellStyleVisitedTimes(workbook);
        CellStyle spaceCellStyle = ExportExcelUtils.getSpaceCellStyle(workbook);
        CellStyle emptyValueCellStyle = ExportExcelUtils.getEmptyValueCellStyle(workbook);

        // Write Header: B2 & B3
        this.writeVisitTimeReportHeader(sheet, this.startDate, this.endDate);

        // Write Data
        int nextRowIndex = SummaryReportConstants.VisitTimeReport.DATA_START_ROW_INDEX;

        for (final Company_mst company : this.companies) {
            ++nextRowIndex; // create empty row

            // write company name and table header for each company
            nextRowIndex = this.writeVisitTimeReportCompanyHeader(sheet, company, nextRowIndex, textCellStyle);

            // write first line: total visited times
            ++nextRowIndex; // create new row
            final Row summaryRow = sheet.createRow(nextRowIndex);
            // Total base point
            this.createCellContent(summaryRow, SummaryReportConstants.VisitTimeReport.TOTAL_TILTLE_JP, textCellStyle,
                    SummaryReportConstants.VisitTimeReport.BRANCH_COL_INDEX);

            // Total employee
            this.createCellContent(summaryRow, SummaryReportConstants.VisitTimeReport.TOTAL_TILTLE_JP, textCellStyle,
                    SummaryReportConstants.VisitTimeReport.EMPLOYEE_COL_INDEX);

            // Total purpose
            this.createCellContent(summaryRow, SummaryReportConstants.VisitTimeReport.TOTAL_TILTLE_JP, textCellStyle,
                    SummaryReportConstants.VisitTimeReport.PURPOSE_COL_INDEX);

            // Total visited times
            long totalVisitedTimesToCompany =
                    this.countTotalVisitedTimesToCompany(company.getCom_company_code(), this.startDate, this.endDate);
            this.createCellContent(summaryRow, String.valueOf(totalVisitedTimesToCompany), numberCellStyle,
                    SummaryReportConstants.VisitTimeReport.VISIT_TIME_COL_INDEX);

            if (totalVisitedTimesToCompany == 0) {
                nextRowIndex = ExportExcelUtils.createSpaceRow(sheet, spaceCellStyle, nextRowIndex);
                continue;
            }

            // get base point to visited times report
            List<Addresspoint_mst> listBasepointOfCompany =
                    this.getAddresspointEmployeeWasVisitedCompany(company.getCom_company_code(), this.startDate,
                            this.endDate);
            if (CollectionUtils.isNotEmpty(listBasepointOfCompany)) {
                // write data for each base point
                for (Addresspoint_mst basepoint : listBasepointOfCompany) {
                    ++nextRowIndex; // create new row
                    // return current row after write data
                    nextRowIndex =
                            this.writeVisitedTimesWithBasepoint(sheet, company, basepoint, nextRowIndex, textCellStyle,
                                    numberCellStyle, emptyValueCellStyle, this.startDate, this.endDate);
                }
            }
            nextRowIndex = ExportExcelUtils.createSpaceRow(sheet, spaceCellStyle, nextRowIndex);

        }
        return workbook;
    }

    /**
     * Write visit time report header.
     *
     * @param sheet the sheet
     * @param startDate the start date
     * @param endDate the end date
     */
    private void writeVisitTimeReportHeader(final Sheet sheet, final Date startDate, final Date endDate) {
        final Row durationRow = sheet.getRow(SummaryReportConstants.VisitTimeReport.HEADER_DURATION_INFO_ROW_INDEX);
        final String duration =
                DateUtils.formatJapaneseFullDate(startDate) + "-" + DateUtils.formatJapaneseFullDate(endDate);
        final Cell durationCell = durationRow.getCell(SummaryReportConstants.VisitTimeReport.HEADER_COL_INDEX);
        durationCell.setCellValue(duration);

        final Row createdDateRow = sheet.getRow(SummaryReportConstants.VisitTimeReport.HEADER_CREATED_DATE_ROW_INDEX);
        final Cell createdDateCell = createdDateRow.getCell(SummaryReportConstants.VisitTimeReport.HEADER_COL_INDEX);
        createdDateCell.setCellValue(DateUtils.formatJapaneseFullDate(DateUtils.getCurrentDatetime()));
    }

    /**
     * Write visit time report company header.
     *
     * @param sheet the sheet
     * @param company the company
     * @param currentRowIndex the current row index
     * @param textCellStyle the text cell style
     * @return the int
     */
    private int writeVisitTimeReportCompanyHeader(final Sheet sheet, final Company_mst company, int currentRowIndex,
            final CellStyle textCellStyle) {

        // Write Company Name
        final Row companyNameRow = sheet.createRow(currentRowIndex);
        final Cell companyNameCell =
                companyNameRow.createCell(SummaryReportConstants.VisitTimeReport.COMPANY_NAME_COL_INDEX);
        companyNameCell.setCellValue(company.getCom_company_name());

        // Purpose Title
        final Row headerRow = sheet.createRow(++currentRowIndex);
        Cell emptyCell = headerRow.createCell(SummaryReportConstants.VisitTimeReport.COMPANY_NAME_COL_INDEX);
        emptyCell.setCellStyle(textCellStyle);
        emptyCell = headerRow.createCell(SummaryReportConstants.VisitTimeReport.EMPLOYEE_COL_INDEX);
        emptyCell.setCellStyle(textCellStyle);

        final Cell purposeHeaderCell = headerRow.createCell(SummaryReportConstants.VisitTimeReport.PURPOSE_COL_INDEX);
        purposeHeaderCell.setCellValue(SummaryReportConstants.VisitTimeReport.PURPOSE_TITLE_JP);
        purposeHeaderCell.setCellStyle(textCellStyle);

        // Visit Time Title
        final Cell visitTimeHeaderCell =
                headerRow.createCell(SummaryReportConstants.VisitTimeReport.VISIT_TIME_COL_INDEX);
        visitTimeHeaderCell.setCellValue(SummaryReportConstants.VisitTimeReport.VISIT_TIME_TITLE_JP);
        visitTimeHeaderCell.setCellStyle(textCellStyle);
        return currentRowIndex;
    }


    /**
     * Count total visited times to company.
     *
     * @param companyCode the company code
     * @return the long
     */
    private long countTotalVisitedTimesToCompany(final String companyCode, final Date startDate, final Date endDate) {
        return Instance.get(Daily_reportRepository.class).countTotalVisitedTimesToCompany(companyCode, startDate,
                endDate);
    }

    /**
     * Gets the addresspoint employee was visited company.
     *
     * @param companyCode the company code
     * @return the addresspoint employee was visited company
     */
    private List<Addresspoint_mst> getAddresspointEmployeeWasVisitedCompany(final String companyCode,
            final Date startDate, final Date endDate) {

        return Instance.get(Addresspoint_mstRepository.class).getAddressByCompanyCode(companyCode, startDate, endDate)
                .getResultList();
    }

    /**
     * Count total visited times to company with basepoint.
     *
     * @param companyCode the company code
     * @param adpCode the adp code
     * @return the long
     */
    private long countTotalVisitedTimesToCompanyWithBasepoint(final String companyCode, final String adpCode,
            final Date startDate, final Date endDate) {

        return Instance.get(Daily_reportRepository.class).countTotalVisitedTimesToCompanyWithBasepoint(companyCode,
                adpCode, startDate, endDate);
    }

    /**
     * Gets the all purpose when visited company with basepoint.
     *
     * @param companyCode the company code
     * @param adpCode the adp code
     * @return the all purpose when visited company with basepoint
     */
    private List<Daily_activity_type> getAllPurposeWhenVisitedCompanyWithBasepoint(final String companyCode,
            final String adpCode, final Date startDate, final Date endDate) {

        return Instance.get(Daily_activity_typeRepository.class)
                .getAllPurposeWhenVisitedCompanyWithBasepoint(companyCode, adpCode, startDate, endDate).getResultList();
    }

    /**
     * Count visited times of company with purpose and basepoint.
     *
     * @param companyCode the company code
     * @param datCode the dat code
     * @param adpCode the adp code
     * @return the long
     */
    private long countVisitedTimesOfCompanyWithPurposeAndBasepoint(final String companyCode, final short datCode,
            final String adpCode, final Date startDate, final Date endDate) {

        return Instance.get(Daily_reportRepository.class).countVisitedTimesOfCompanyWithPurposeAndBasepoint(
                companyCode, datCode, adpCode, startDate, endDate);
    }

    /**
     * Gets the all employee went to visited company with basepoint.
     *
     * @param companyCode the company code
     * @param adpCode the adp code
     * @return the all employee went to visited company with basepoint
     */
    private List<Employee_mst> getAllEmployeeWentToVisitedCompanyWithBasepoint(final String companyCode,
            final String adpCode, final Date startDate, final Date endDate) {

        return Instance.get(Employee_mstRepository.class)
                .getAllEmployeeWentToVisitedCompanyWithBasepoint(companyCode, adpCode, startDate, endDate)
                .getResultList();

    }

    /**
     * Count total visited times to company with employee and basepoint.
     *
     * @param companyCode the company code
     * @param empCode the emp code
     * @param adpCode the adp code
     * @return the long
     */
    private long countTotalVisitedTimesToCompanyWithEmployeeAndBasepoint(final String companyCode, final int empCode,
            final String adpCode, final Date startDate, final Date endDate) {

        return Instance.get(Daily_reportRepository.class).countTotalVisitedTimesToCompanyWithEmployeeAndBasepoint(
                companyCode, empCode, adpCode, startDate, endDate);

    }

    /**
     * Gets the all purpose when visited company with employee and basepoint.
     *
     * @param companyCode the company code
     * @param empCode the emp code
     * @param adpCode the adp code
     * @return the all purpose when visited company with employee and basepoint
     */
    private List<Daily_activity_type> getAllPurposeWhenVisitedCompanyWithEmployeeAndBasepoint(final String companyCode,
            final int empCode, final String adpCode, final Date startDate, final Date endDate) {

        return Instance
                .get(Daily_activity_typeRepository.class)
                .getAllPurposeWhenVisitedCompanyWithEmployeeAndBasepoint(companyCode, empCode, adpCode, startDate,
                        endDate).getResultList();

    }

    /**
     * Count visited times of company with purpose and employee and basepoint.
     *
     * @param companyCode the company code
     * @param datCode the dat code
     * @param empCode the emp code
     * @param adpCode the adp code
     * @return the long
     */
    private long countVisitedTimesOfCompanyWithPurposeAndEmployeeAndBasepoint(final String companyCode,
            final short datCode, final int empCode, final String adpCode, final Date startDate, final Date endDate) {

        return Instance.get(Daily_reportRepository.class).countVisitedTimesOfCompanyWithPurposeAndEmployeeAndBasepoint(
                companyCode, datCode, empCode, adpCode, startDate, endDate);

    }

    /**
     * Write visited times for each base point.
     *
     * @param sheet the sheet
     * @param company the company
     * @param basepoint the basepoint
     * @param nextRowIndex the next row index
     * @param textCellStyle the text cell style
     * @param numberCellStyle the number cell style
     * @param emptyValueCellStyle the empty value cell style
     * @return the int
     */
    private int writeVisitedTimesWithBasepoint(final Sheet sheet, final Company_mst company,
            final Addresspoint_mst basepoint, int nextRowIndex, final CellStyle textCellStyle,
            final CellStyle numberCellStyle, final CellStyle emptyValueCellStyle, final Date startDate,
            final Date endDate) {
        final Row newRow = sheet.createRow(nextRowIndex);

        // Summary report
        this.createCellContent(newRow, basepoint.getAdp_name(), textCellStyle,
                SummaryReportConstants.VisitTimeReport.BRANCH_COL_INDEX);

        // Total employee
        this.createCellContent(newRow, SummaryReportConstants.VisitTimeReport.TOTAL_TILTLE_JP, textCellStyle,
                SummaryReportConstants.VisitTimeReport.EMPLOYEE_COL_INDEX);

        // Total purpose
        this.createCellContent(newRow, SummaryReportConstants.VisitTimeReport.TOTAL_TILTLE_JP, textCellStyle,
                SummaryReportConstants.VisitTimeReport.PURPOSE_COL_INDEX);

        // Total visited times
        this.createCellContent(newRow, String.valueOf(this.countTotalVisitedTimesToCompanyWithBasepoint(
                company.getCom_company_code(), basepoint.getAdp_code(), startDate, endDate)), numberCellStyle,
                SummaryReportConstants.VisitTimeReport.VISIT_TIME_COL_INDEX);

        // get all purpose for company was visited
        List<Daily_activity_type> purposes =
                this.getAllPurposeWhenVisitedCompanyWithBasepoint(company.getCom_company_code(),
                        basepoint.getAdp_code(), startDate, endDate);
        // write all visited times for each purpose
        for (Daily_activity_type purpose : purposes) {
            ++nextRowIndex; // create new row
            final Row newPurposeRow = sheet.createRow(nextRowIndex);
            this.createCellContent(newPurposeRow, null, emptyValueCellStyle,
                    SummaryReportConstants.VisitTimeReport.BRANCH_COL_INDEX);
            this.createCellContent(newPurposeRow, null, emptyValueCellStyle,
                    SummaryReportConstants.VisitTimeReport.EMPLOYEE_COL_INDEX);
            this.createCellContent(newPurposeRow, purpose.getDat_name(), textCellStyle,
                    SummaryReportConstants.VisitTimeReport.PURPOSE_COL_INDEX);
            this.createCellContent(newPurposeRow, String.valueOf(this
                    .countVisitedTimesOfCompanyWithPurposeAndBasepoint(company.getCom_company_code(),
                            purpose.getDat_code(), basepoint.getAdp_code(), startDate, endDate)), numberCellStyle,
                    SummaryReportConstants.VisitTimeReport.VISIT_TIME_COL_INDEX);
        }

        return this.writeVisitedTimesForEachEmployee(sheet, nextRowIndex, company, basepoint, textCellStyle,
                numberCellStyle, emptyValueCellStyle, startDate, endDate);
    }

    /**
     * write visited times for each employee with for each base point.
     *
     * @param sheet the sheet
     * @param nextRowIndex the next row index
     * @param company the company
     * @param basepoint the basepoint
     * @param textCellStyle the text cell style
     * @param numberCellStyle the number cell style
     * @param emptyValueCellStyle the empty value cell style
     * @return the int
     */
    private int writeVisitedTimesForEachEmployee(final Sheet sheet, int nextRowIndex, final Company_mst company,
            final Addresspoint_mst basepoint, final CellStyle textCellStyle, final CellStyle numberCellStyle,
            final CellStyle emptyValueCellStyle, final Date startDate, final Date endDate) {
        List<Employee_mst> listEmployee =
                this.getAllEmployeeWentToVisitedCompanyWithBasepoint(company.getCom_company_code(),
                        basepoint.getAdp_code(), startDate, endDate);
        for (Employee_mst employee : listEmployee) {
            ++nextRowIndex; // create new row
            final Row newRow = sheet.createRow(nextRowIndex);

            // Base point column
            this.createCellContent(newRow, null, emptyValueCellStyle,
                    SummaryReportConstants.VisitTimeReport.BRANCH_COL_INDEX);

            // Employee name
            this.createCellContent(newRow, employee.getEmp_name(), textCellStyle,
                    SummaryReportConstants.VisitTimeReport.EMPLOYEE_COL_INDEX);

            // Total purpose title
            this.createCellContent(newRow, SummaryReportConstants.VisitTimeReport.TOTAL_TILTLE_JP, textCellStyle,
                    SummaryReportConstants.VisitTimeReport.PURPOSE_COL_INDEX);

            // Total visited times
            this.createCellContent(newRow,
                    String.valueOf(this.countTotalVisitedTimesToCompanyWithEmployeeAndBasepoint(
                            company.getCom_company_code(), employee.getEmp_code(), basepoint.getAdp_code(), startDate,
                            endDate)), numberCellStyle, SummaryReportConstants.VisitTimeReport.VISIT_TIME_COL_INDEX);

            // get all purpose for company was visited with employee
            List<Daily_activity_type> purposes =
                    this.getAllPurposeWhenVisitedCompanyWithEmployeeAndBasepoint(company.getCom_company_code(),
                            employee.getEmp_code(), basepoint.getAdp_code(), startDate, endDate);
            for (Daily_activity_type purpose : purposes) {
                ++nextRowIndex; // create new row
                final Row newPurposeRow = sheet.createRow(nextRowIndex);
                this.createCellContent(newPurposeRow, null, emptyValueCellStyle,
                        SummaryReportConstants.VisitTimeReport.PURPOSE_COL_INDEX);
                this.createCellContent(newPurposeRow, null, emptyValueCellStyle,
                        SummaryReportConstants.VisitTimeReport.EMPLOYEE_COL_INDEX);
                this.createCellContent(newPurposeRow, purpose.getDat_name(), textCellStyle,
                        SummaryReportConstants.VisitTimeReport.PURPOSE_COL_INDEX);
                this.createCellContent(
                        newPurposeRow,
                        String.valueOf(this.countVisitedTimesOfCompanyWithPurposeAndEmployeeAndBasepoint(
                                company.getCom_company_code(), purpose.getDat_code(), employee.getEmp_code(),
                                basepoint.getAdp_code(), startDate, endDate)), numberCellStyle,
                        SummaryReportConstants.VisitTimeReport.VISIT_TIME_COL_INDEX);
            }
        }

        return nextRowIndex;
    }


    /**
     * Creates the cell content.
     *
     * @param row the row
     * @param value the value
     * @param cellStyle the cell style
     * @param colIndex the column index
     */
    private void createCellContent(final Row row, final String value, final CellStyle cellStyle, final int colIndex) {
        Cell newCell = row.createCell(colIndex);
        newCell.setCellValue(value);
        newCell.setCellStyle(cellStyle);

    }


    /**
     * Gets the start date.
     *
     * @return the start date
     */
    // set get
    public Date getStartDate() {
        return this.startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the new start date
     */
    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return the end date
     */
    public Date getEndDate() {
        return this.endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the new end date
     */
    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the companies.
     *
     * @return the companies
     */
    public List<Company_mst> getCompanies() {
        return this.companies;
    }

    /**
     * Sets the companies.
     *
     * @param companies the new companies
     */
    public void setCompanies(final List<Company_mst> companies) {
        this.companies = companies;
    }
}
