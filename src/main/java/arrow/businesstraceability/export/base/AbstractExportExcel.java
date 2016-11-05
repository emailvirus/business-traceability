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
package arrow.businesstraceability.export.base;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

/*
 * Description of class.
 * @author ArrowTechnology, Ltd.
 * @version Dec 9, 2015 {revision}
 */
public abstract class AbstractExportExcel {

    public abstract Workbook createContent() throws InvalidFormatException, IOException;
}
