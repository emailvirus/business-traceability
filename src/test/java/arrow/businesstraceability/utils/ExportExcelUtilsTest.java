package arrow.businesstraceability.utils;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Test;

import arrow.businesstraceability.constant.SummaryReportConstants;
import arrow.businesstraceability.util.ExportExcelUtils;

public class ExportExcelUtilsTest {
    @Test
    public void createWorkbookForExportExcelMonthlyReportTest() throws InvalidFormatException, IOException {
        final File file = new File(".");
        final String projectDir = file.getCanonicalPath();
        final String url =
                projectDir + "/src/main/resources" + SummaryReportConstants.TEMPLATE_DIR
                        + SummaryReportConstants.MonthlyReport.MONTHLY_REPORT_TEMPLATE;
        Workbook workbook = ExportExcelUtils.createWorkbook(url);
        Assert.assertNotNull(workbook);
    }
}
