/*-------------------------------------------------------------------------------
 * All Rights Reserved. Copyright(C) Arrow Technology, Ltd.
 * revision : ???
 * vendor : Arrow Technology, Ltd.
 * author 　: HaiND
 * since : Dec 9, 2015
 *-------------------------------------------------------------------------------
 * revision marking
 * Dec 9, 2015 : first version: {creator}
 *-----------------------------------------------------------------------------*/
package arrow.businesstraceability.export.factory;

import arrow.businesstraceability.constant.SummaryReportConstants;


/*
 * Description of class.
 * @author ArrowTechnology, Ltd.
 * @version Dec 9, 2015
 */
public class ExportFileFactory {

    /**
     * get factory .
     *
     * @param typeReport
     * @return AbstractExportFactory
     */
    public static AbstractExportFactory getFactory(final String typeReport) {
        AbstractExportFactory ef = null;
        switch (typeReport) {
            case SummaryReportConstants.PeriodicReport.PERIODIC_REPORT_TYPE:
            case SummaryReportConstants.VisitTimeReport.VISIT_TIME_REPORT_TYPE:
                ef = new ExcelReportFactory(typeReport);
                break;
            default:
                break;
        }

        return ef;
    }

}
