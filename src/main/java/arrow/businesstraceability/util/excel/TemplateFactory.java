package arrow.businesstraceability.util.excel;

import org.apache.poi.ss.usermodel.Cell;


/**
 * A factory for creating Template objects.
 */
public class TemplateFactory {


    /**
     * Process.
     *
     * @param cell the cell
     * @param parameter the parameter
     * @param value the value
     */
    public static void process(final Cell cell, final String parameter, final Object value) {
        String cellValue = cell.getStringCellValue();
        cellValue = cellValue.replace(parameter, value.toString());
        cell.setCellValue(cellValue);
    }

    /**
     * The Class Parameters.
     */
    public static class Parameters {

        /** The Constant CREATOR. */
        public static final String CREATOR = "${CREATOR}";

        /** The Constant CURRENT_MONTH. */
        public static final String CURRENT_MONTH = "${CURRENT_MONTH}";

        /** The Constant NEXT_MONTH. */
        public static final String NEXT_MONTH = "${NEXT_MONTH}";

        /** The Constant TOTAL. */
        public static final String TOTAL = "${TOTAL}";

        public static final String REPORT_YEAR = "${REPORT_YEAR}";

        public static final String REPORT_MONTH = "${REPORT_MONTH}";

        public static final String POSITION = "${POSITION}";

        public static final String BRANCH_NAME = "${BRANCH_NAME}";

        public static final String WORKING_DAY = "${WORKING_DAY}";

        public static final String DAILY_INPUT = "${DAILY_INPUT}";

        public static final String MONTH_1ST = "${MONTH_1ST}";

        public static final String NEXT_MONTH_1ST = "${NEXT_MONTH_1ST}";
    }

}
