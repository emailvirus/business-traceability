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

import arrow.businesstraceability.export.base.AbstractExportExcel;
import arrow.businesstraceability.export.param.ReportParameter;

/*
 * Description of class.
 * @author ArrowTechnology, Ltd.
 * @version Dec 9, 2015 {revision}
 */
public abstract class AbstractExportFactory {


    public AbstractExportExcel createReport(final ReportParameter param) {

        return null;
    }
}
