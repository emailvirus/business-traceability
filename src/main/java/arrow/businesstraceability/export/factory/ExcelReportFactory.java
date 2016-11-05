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
package arrow.businesstraceability.export.factory;

import arrow.businesstraceability.constant.SummaryReportConstants;
import arrow.businesstraceability.export.base.AbstractExportExcel;
import arrow.businesstraceability.export.base.imp.PeriodicReportExportExcel;
import arrow.businesstraceability.export.base.imp.VisitTimeReportExportExcel;
import arrow.businesstraceability.export.param.ReportParameter;


/*
 * Description of class.
 * @author ArrowTechnology, Ltd.
 * @version Dec 9, 2015 {revision}
 */
public class ExcelReportFactory extends AbstractExportFactory {

    private final String reportType;

    public ExcelReportFactory(final String typeReport) {
        this.reportType = typeReport;
    }

    @Override
    public AbstractExportExcel createReport(final ReportParameter param) {
        switch (this.reportType) {
            case SummaryReportConstants.VisitTimeReport.VISIT_TIME_REPORT_TYPE:
                return new VisitTimeReportExportExcel(param);
            case SummaryReportConstants.PeriodicReport.PERIODIC_REPORT_TYPE:
                return new PeriodicReportExportExcel(param);
            default:
                return null;
        }
    }


}
