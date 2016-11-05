package arrow.businesstraceability.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import arrow.businesstraceability.constant.SummaryReportConstants;
import arrow.businesstraceability.control.service.CompanyManagementService;

public class ExportExcelUtils {

    /**
     * Creates the workbook.
     *
     * @param filePath the file path
     * @return the workbook
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InvalidFormatException
     */
    public static Workbook createWorkbook(final String filePath) throws InvalidFormatException, IOException {
        final InputStream file = CompanyManagementService.class.getResourceAsStream(filePath);
        if (file != null) {
            return WorkbookFactory.create(file);
        }
        else {
            // try create workbook by create new file with filePath
            final File tempFile = new File(filePath);
            if (tempFile.exists() && !tempFile.isDirectory()) {
                return WorkbookFactory.create(tempFile);
            }
            return null;
        }
    }


    /**
     * Gets the text cell style visited times.
     *
     * @param workbook the workbook
     * @return the text cell style visited times
     */
    public static CellStyle getTextCellStyleVisitedTimes(final Workbook workbook) {
        final CellStyle myStyle = ExportExcelUtils.makeFullBorderStyle(workbook);
        myStyle.setBorderBottom(CellStyle.BORDER_NONE);
        myStyle.setAlignment(CellStyle.ALIGN_LEFT);
        return myStyle;
    }

    /**
     * Make full border style.
     *
     * @param workbook the workbook
     * @return the cell style
     */
    public static CellStyle makeFullBorderStyle(final Workbook workbook) {
        final CellStyle myStyle = workbook.createCellStyle();
        myStyle.setBorderLeft(CellStyle.BORDER_THIN);
        myStyle.setBorderRight(CellStyle.BORDER_THIN);
        myStyle.setBorderTop(CellStyle.BORDER_THIN);
        myStyle.setBorderBottom(CellStyle.BORDER_THIN);
        myStyle.setAlignment(CellStyle.ALIGN_CENTER);
        myStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return myStyle;
    }

    /**
     * Gets the number cell style visited times.
     *
     * @param workbook the workbook
     * @return the number cell style visited times
     */
    public static CellStyle getNumberCellStyleVisitedTimes(final Workbook workbook) {
        final CellStyle numberStyle = ExportExcelUtils.makeFullBorderStyle(workbook);
        numberStyle.setBorderBottom(CellStyle.BORDER_NONE);
        numberStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        return numberStyle;
    }

    /**
     * Create space row style for last company.
     *
     * @param workbook the workbook
     * @return the space cell style
     */
    public static CellStyle getSpaceCellStyle(final Workbook workbook) {
        CellStyle spaceNameStyle = ExportExcelUtils.getTextCellStyle(workbook);
        spaceNameStyle.setBorderBottom(CellStyle.BORDER_NONE);
        spaceNameStyle.setBorderLeft(CellStyle.BORDER_NONE);
        spaceNameStyle.setBorderRight(CellStyle.BORDER_NONE);
        spaceNameStyle.setBorderTop(CellStyle.BORDER_THIN);
        return spaceNameStyle;
    }

    /**
     * Gets the text cell style.
     *
     * @param workbook the workbook
     * @return the text cell style
     */
    public static CellStyle getTextCellStyle(final Workbook workbook) {
        final CellStyle myStyle = ExportExcelUtils.makeFullBorderStyle(workbook);
        myStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        myStyle.setAlignment(CellStyle.ALIGN_LEFT);
        return myStyle;
    }

    /**
     * Gets the empty value cell style.
     *
     * @param workbook the workbook
     * @return the empty value cell style
     */
    public static CellStyle getEmptyValueCellStyle(final Workbook workbook) {
        final CellStyle myStyle = ExportExcelUtils.makeFullBorderStyle(workbook);
        myStyle.setBorderBottom(CellStyle.BORDER_NONE);
        myStyle.setBorderTop(CellStyle.BORDER_NONE);
        return myStyle;
    }

    /**
     * Create space row for last company.
     *
     * @param sheet the sheet
     * @param spaceCellStyle the space cell style
     * @param nextRowIndex the next row index
     * @return the int
     */
    public static int createSpaceRow(final Sheet sheet, final CellStyle spaceCellStyle, int nextRowIndex) {
        ++nextRowIndex;
        Row spaceRow = sheet.createRow(nextRowIndex);
        Cell spaceCellA = spaceRow.createCell(SummaryReportConstants.VisitTimeReport.BRANCH_COL_INDEX);
        spaceCellA.setCellStyle(spaceCellStyle);
        Cell spaceCellB = spaceRow.createCell(SummaryReportConstants.VisitTimeReport.EMPLOYEE_COL_INDEX);
        spaceCellB.setCellStyle(spaceCellStyle);
        Cell spaceCellC = spaceRow.createCell(SummaryReportConstants.VisitTimeReport.PURPOSE_COL_INDEX);
        spaceCellC.setCellStyle(spaceCellStyle);
        Cell spaceCellD = spaceRow.createCell(SummaryReportConstants.VisitTimeReport.VISIT_TIME_COL_INDEX);
        spaceCellD.setCellStyle(spaceCellStyle);
        return nextRowIndex;
    }


}
