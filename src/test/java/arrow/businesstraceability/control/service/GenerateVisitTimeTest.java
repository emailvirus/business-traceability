package arrow.businesstraceability.control.service;


import org.junit.Assert;
import org.junit.Test;

import arrow.businesstraceability.constant.SummaryReportConstants;
import arrow.businesstraceability.export.base.AbstractExportExcel;
import arrow.businesstraceability.export.base.imp.VisitTimeReportExportExcel;
import arrow.businesstraceability.export.factory.AbstractExportFactory;
import arrow.businesstraceability.export.factory.ExcelReportFactory;
import arrow.businesstraceability.export.factory.ExportFileFactory;
import arrow.businesstraceability.export.param.ReportParameter;


public class GenerateVisitTimeTest {

    private AbstractExportFactory factory;

    private AbstractExportExcel aeExcell;

    private ReportParameter param;

    @Test
    public void testCreateExcelFactory() {

        this.factory = ExportFileFactory.getFactory(SummaryReportConstants.PeriodicReport.PERIODIC_REPORT_TYPE);
        Assert.assertTrue(this.factory instanceof ExcelReportFactory);

        this.factory = ExportFileFactory.getFactory(SummaryReportConstants.VisitTimeReport.VISIT_TIME_REPORT_TYPE);
        Assert.assertTrue(this.factory instanceof ExcelReportFactory);
    }

    @Test
    public void testCreateVisitTimeReportExcell() {

        this.param = new ReportParameter();
        this.aeExcell = ExportFileFactory.getFactory(SummaryReportConstants.VisitTimeReport.VISIT_TIME_REPORT_TYPE)
                .createReport(this.param);
        Assert.assertTrue(this.aeExcell instanceof VisitTimeReportExportExcel);
    }
}
