package arrow.businesstraceability.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import arrow.framework.faces.message.ErrorMessage;


// TODO: Auto-generated Javadoc
/**
 * The Class SummaryReportConstants.
 */
public class SummaryReportConstants {

    /**
     * The Class MonthlyReport.
     */
    public static class MonthlyReport {

        /** The Constant MONTHLY_REPORT_TEMPLATE. */
        public static final String MONTHLY_REPORT_TEMPLATE = "monthly_reports_jp.xls";

        /** The Constant OUTPUT_FILENAME. */
        public static final String OUTPUT_FILENAME = "monthly_report.xls";

        /** The Constant HEADER_INFO_ROW_INDEX. */
        public static final int HEADER_INFO_ROW_INDEX = 0;

        /** The Constant HEADER_INFO_CREATOR_COL_INDEX. */
        public static final int HEADER_INFO_CREATOR_COL_INDEX = 6;

        /** The Constant HEADER_MONTH_INFO_ROW_INDEX. */
        public static final int HEADER_MONTH_INFO_ROW_INDEX = 2;

        /** The Constant HEADER_MONTH_INFO_COL_INDEX. */
        public static final int HEADER_MONTH_INFO_COL_INDEX = 6;

        /** The Constant HEADER_MONTH_INFO_NEXT_COL_INDEX. */
        public static final int HEADER_MONTH_INFO_NEXT_COL_INDEX = 7;

        /** The Constant BUSINESS_TYPE_TITLE_ROW_INDEX. */
        public static final int BUSINESS_TYPE_TITLE_ROW_INDEX = 18;

        /** The Constant BUSINESS_TYPE_HEADER_ROW_INDEX_LINE_1. */
        public static final int BUSINESS_TYPE_HEADER_ROW_INDEX_LINE_1 = 16;

        /** The Constant BUSINESS_TYPE_HEADER_ROW_INDEX_LINE_2. */
        public static final int BUSINESS_TYPE_HEADER_ROW_INDEX_LINE_2 = 18;

        /** The Constant BUSINESS_TYPE_VISIT_TIME_ROW_INDEX_LINE_1. */
        public static final int BUSINESS_TYPE_VISIT_TIME_ROW_INDEX_LINE_1 = 17;

        /** The Constant BUSINESS_TYPE_VISIT_TIME_ROW_INDEX_LINE_2. */
        public static final int BUSINESS_TYPE_VISIT_TIME_ROW_INDEX_LINE_2 = 19;

        /** The Constant BUSINESS_TYPE_HEADER_START_COL_INDEX. */
        public static final int BUSINESS_TYPE_HEADER_START_COL_INDEX = 1;

        /** The Constant PURPOSE_TITLE_ROW_INDEX. */
        public static final int PURPOSE_TITLE_ROW_INDEX = 20;

        /** The Constant PURPOSE_HEADER_ROW_INDEX. */
        public static final int PURPOSE_HEADER_ROW_INDEX = 21;

        /** The Constant PURPOSE_VISIT_TIME_ROW_INDEX. */
        public static final int PURPOSE_VISIT_TIME_ROW_INDEX = 22;

        /** The Constant PURPOSE_HEADER_START_COL_INDEX. */
        public static final int PURPOSE_HEADER_START_COL_INDEX = 1;

        /** The Constant TOTAL_ROW_INDEX. */
        public static final int TOTAL_ROW_INDEX = 16;

        /** The Constant TOTAL_COL_INDEX. */
        public static final int TOTAL_COL_INDEX = 0;

        /** The Constant SUMMARY_ROW_INDEX. */
        public static final int SUMMARY_ROW_INDEX = 50;

        /** The Constant SUMMARY_COL_INDEX. */
        public static final int SUMMARY_COL_INDEX = 0;

        /** The Constant LIMITED_COLUMN_INDEX. */
        public static final int LIMITED_COLUMN_INDEX = 8;

        /** The Constant HEADER_COL_YEAR_INDEX. */
        public static final int HEADER_COL_YEAR_INDEX = 0;

        /** The Constant HEADER_COL_MONTH_INDEX. */
        public static final int HEADER_COL_MONTH_INDEX = 1;

        /** The Constant HEADER_COL_TITTLE_INDEX. */
        public static final int HEADER_COL_TITTLE_INDEX = 2;

        /** The Constant HEADER_COL_BRANCH_NAME_INDEX. */
        public static final int HEADER_COL_BRANCH_NAME_INDEX = 7;

        /** The Constant HEADER_COL_POSITION_INDEX. */
        public static final int HEADER_COL_POSITION_INDEX = 7;

        /** The Constant HEADER_COL_CREATOR_INDEX. */
        public static final int HEADER_COL_CREATOR_INDEX = 7;

        /** The Constant HEADER_POSITION_INFO_INDEX. */
        public static final int HEADER_POSITION_INFO_INDEX = 1;

        /** The Constant HEADER_CREATOR_INFO_INDEX. */
        public static final int HEADER_CREATOR_INFO_INDEX = 2;

        /** The Constant COMPANY_VISITED_ROW_INDEX. */
        public static final int COMPANY_VISITED_ROW_INDEX = 41;

        /** The Constant COMPANY_VISITED_COL_INDEX. */
        public static final int COMPANY_VISITED_COL_INDEX = 0;

        /** The Constant HEADER_VISIT_INPUT_INDEX. */
        public static final int HEADER_WORKING_DAY_INDEX = 6;

        /** The Constant HEADER_COL_WORKING_DAY. */
        public static final int HEADER_COL_WORKING_DAY = 4;

        /** The Constant HEADER_COL_DAILY_INPUT. */
        public static final int HEADER_COL_DAILY_INPUT = 5;

        /** The Constant HEADER_COL_MONTH_1ST. */
        public static final int HEADER_COL_MONTH_1ST = 6;

        /** The Constant HEADER_COL_NEXT_MONTH_1ST. */
        public static final int HEADER_COL_NEXT_MONTH_1ST = 7;

        /** The Constant STAFF_MANAGEMENT_ROW_INDEX. */
        public static final int STAFF_MANAGEMENT_ROW_INDEX = 24;

        /** The Constant PHONE_FOLLOW_COL_INDEX. */
        public static final int PHONE_FOLLOW_COL_INDEX = 1;

        /** The Constant DIRECT_FOLLOW_COL_INDEX. */
        public static final int DIRECT_FOLLOW_COL_INDEX = 2;

        /** The Constant STAFF_CLAIM_COL_INDEX. */
        public static final int STAFF_CLAIM_COL_INDEX = 3;

        /** The Constant STAFF_OTHER_COL_INDEX. */
        public static final int STAFF_OTHER_COL_INDEX = 4;

        /** The Constant RECRUIT_ROW_INDEX. */
        public static final int RECRUIT_ROW_INDEX = 28;

        /** The Constant PRIMARY_INTEVIEW_COL_INDEX. */
        public static final int PRIMARY_INTEVIEW_COL_INDEX = 0;

        /** The Constant SECONDARY_INTEVIEW_COL_INDEX. */
        public static final int SECONDARY_INTEVIEW_COL_INDEX = 1;

        /** The Constant ADOPTION_COL_INDEX. */
        public static final int ADOPTION_COL_INDEX = 2;

        /** The Constant EMPLOYEE_FOLOW_COL_INDEX. */
        public static final int EMPLOYEE_FOLLOW_COL_INDEX = 3;

        /** The Constant EMPLOYEE_FOLOW_COL_INDEX. */
        public static final int NEW_DEVELOPMENT_COL_INDEX = 5;

        /** The Constant EMPLOYEE_FOLOW_COL_INDEX. */
        public static final int INCREASE_COL_INDEX = 6;

        /** The Constant EMPLOYEE_FOLOW_COL_INDEX. */
        public static final int SALE_PH_COL_INDEX = 7;

        /** The Constant EMPLOYEE_FOLOW_COL_INDEX. */
        public static final int COST_RATE_COL_INDEX = 8;

        /** The Constant MAX_VISITED_COMPANIES_RECORD. */
        public static final int MAX_VISITED_COMPANIES_RECORD = 20;

        /** The Constant CAUSE_CURRENT_MONTH. */
        public static final int CAUSE_CURRENT_MONTH = 32;

        /** The Constant CONCREATE_ACTION. */
        public static final int CONCREATE_ACTION = 35;

        /** The Constant LIST_VISITED. */
        public static final int LIST_VISITED = 38;


        /** Map coordinates of visit time cells. */
        private static Map<Short, String> mapVisitTimeCellCoordinates;

        /** Map coordinates of purpose cells. */
        private static Map<Short, String> mapPurposeCellCoordinates;

        /** The my lock. */
        private static volatile Object myLock = new Object();

        /**
         * Get mapVisitTimeCellCoordinates.
         *
         * @return mapVisitTimeCellCoordinates map coordinates of visit time cells
         */
        public static Map<Short, String> getMapVisitTimeCellCoordinates() {
            synchronized (MonthlyReport.myLock) {
                if (MonthlyReport.mapVisitTimeCellCoordinates == null) {
                    MonthlyReport.mapVisitTimeCellCoordinates = new HashMap<Short, String>();

                    MonthlyReport.mapVisitTimeCellCoordinates.put((short) 201, "B18");
                    MonthlyReport.mapVisitTimeCellCoordinates.put((short) 202, "C18");
                    MonthlyReport.mapVisitTimeCellCoordinates.put((short) 101, "D18");
                    MonthlyReport.mapVisitTimeCellCoordinates.put((short) 102, "E18");
                    MonthlyReport.mapVisitTimeCellCoordinates.put((short) 104, "F18");
                    MonthlyReport.mapVisitTimeCellCoordinates.put((short) 103, "G18");
                    MonthlyReport.mapVisitTimeCellCoordinates.put((short) 501, "H18");
                    MonthlyReport.mapVisitTimeCellCoordinates.put((short) 601, "I18");
                    MonthlyReport.mapVisitTimeCellCoordinates.put((short) 301, "B20");
                    MonthlyReport.mapVisitTimeCellCoordinates.put((short) 302, "C20");
                    MonthlyReport.mapVisitTimeCellCoordinates.put((short) 401, "D20");
                    MonthlyReport.mapVisitTimeCellCoordinates.put((short) 602, "E20");
                    MonthlyReport.mapVisitTimeCellCoordinates.put((short) 603, "F20");
                }
            }
            return MonthlyReport.mapVisitTimeCellCoordinates;
        }

        /**
         * Get mapPurposeCellCoordinates.
         *
         * @return mapVisitTimeCellCoordinates map coordinates of purpose cells
         */
        public static Map<Short, String> getMapPurposeCellCoordinates() {
            synchronized (MonthlyReport.myLock) {
                if (MonthlyReport.mapPurposeCellCoordinates == null) {
                    MonthlyReport.mapPurposeCellCoordinates = new HashMap<Short, String>();

                    MonthlyReport.mapPurposeCellCoordinates.put((short) 2, "B23");
                    MonthlyReport.mapPurposeCellCoordinates.put((short) 4, "C23");
                    MonthlyReport.mapPurposeCellCoordinates.put((short) 3, "D23");
                    MonthlyReport.mapPurposeCellCoordinates.put((short) 5, "E23");
                    MonthlyReport.mapPurposeCellCoordinates.put((short) 6, "F23");
                    MonthlyReport.mapPurposeCellCoordinates.put((short) 7, "G23");
                    MonthlyReport.mapPurposeCellCoordinates.put((short) 8, "H23");
                    MonthlyReport.mapPurposeCellCoordinates.put((short) 1, "I23");
                }
            }
            return MonthlyReport.mapPurposeCellCoordinates;
        }

        /**
         * Builds the messages code notice monthly report was changed by another user.
         *
         * @return the list
         */
        public static List<String> buildMessagesCodeNoticeMonthlyReportWasChangedByAnotherUser() {
            List<String> messageCodes = new ArrayList<String>();
            messageCodes.add(ErrorMessage.monthlyreport_008_reject_failed_by_other_person_rejected().getMessageCode());
            messageCodes.add(ErrorMessage.monthlyreport_009_reopen_failed_by_other_person_reopened().getMessageCode());
            messageCodes.add(ErrorMessage.monthlyreport_010_approve_failed_by_other_person_approved().getMessageCode());
            return messageCodes;
        }

        /** The Constant STATUS_OPEN. */
        public static final String STATUS_OPEN = "OP";

        /** The Constant STATUS_WAITING. */
        public static final String STATUS_WAITING = "WT";

        /** The Constant STATUS_REJECT. */
        public static final String STATUS_REJECT = "RE";

        /** The Constant STATUS_APPROVED. */
        public static final String STATUS_APPROVED = "AP";

        /** The Constant STATUS_APPROVED. */
        public static final String STATUS_REOPEN = "RO";

    }

    /**
     * The Class PeriodicReport.
     */
    public static class PeriodicReport {

        /** The Constant OUTPUT_FILENAME. */
        public static final String OUTPUT_FILENAME = "statistic_report.xls";

        /** The Constant PURPOSE_TITLE_JP. */
        public static final String PURPOSE_TITLE_JP = "目的別";

        /** The Constant BRANCH_TOTAL_TITLE_JP. */
        public static final String BRANCH_TOTAL_TITLE_JP = "合計";

        /** The Constant HEADER_COL_INDEX. */
        public static final int HEADER_COL_INDEX = 1;

        /** The Constant HEADER_BRANCH_INFO_ROW_INDEX. */
        public static final int HEADER_BRANCH_INFO_ROW_INDEX = 0;

        /** The Constant HEADER_DURATION_INFO_ROW_INDEX. */
        public static final int HEADER_DURATION_INFO_ROW_INDEX = 1;

        /** The Constant HEADER_CREATED_DATE_ROW_INDEX. */
        public static final int HEADER_CREATED_DATE_ROW_INDEX = 2;

        /** The Constant BUSINESS_TYPE_HEADER_ROW_INDEX. */
        public static final int BUSINESS_TYPE_HEADER_ROW_INDEX = 5;

        /** The Constant BUSINESS_TYPE_HEADER_START_COL_INDEX. */
        public static final int BUSINESS_TYPE_HEADER_START_COL_INDEX = 3;

        /** The Constant PURPOSE_HEADER_ROW_INDEX. */
        public static final int PURPOSE_HEADER_ROW_INDEX = 5;

        /** The Constant PURPOSE_TITLE_ROW_INDEX. */
        public static final int PURPOSE_TITLE_ROW_INDEX = 4;

        /** The Constant DATA_START_ROW_INDEX. */
        public static final int DATA_START_ROW_INDEX = 6;

        /** The Constant BRANCH_COL_INDEX. */
        public static final int BRANCH_COL_INDEX = 0;

        /** The Constant EMPLOYEE_COL_INDEX. */
        public static final int EMPLOYEE_COL_INDEX = 1;

        /** The Constant TOTAL_COL_INDEX. */
        public static final int TOTAL_COL_INDEX = 2;

        /** The Constant COMPANY_VISIT_DATA_START_ROW_INDEX. */
        public static final int COMPANY_VISIT_DATA_START_ROW_INDEX = 5;

        /** The Constant COMPANY_NAME_COL_INDEX. */
        public static final int COMPANY_NAME_COL_INDEX = 2;

        /** The Constant COMPANY_VISI_TIME_COL_INDEX. */
        public static final int COMPANY_VISI_TIME_COL_INDEX = 3;

        /** The periodic type report. */
        public static final String PERIODIC_REPORT_TYPE = "periodic";

    }

    /**
     * The Class VisitTimeReport.
     */
    public static class VisitTimeReport {

        /** The Constant VISIT_TIME_REPORT_TEMPLATE. */
        public static final String VISIT_TIME_REPORT_TEMPLATE = "visited_report.xls";

        /** The Constant OUTPUT_FILENAME. */
        public static final String OUTPUT_FILENAME = "visit_time_report.xls";

        /** The Constant PURPOSE_TITLE_JP. */
        public static final String PURPOSE_TITLE_JP = "訪問目的";

        /** The Constant VISIT_TIME_TITLE_JP. */
        public static final String VISIT_TIME_TITLE_JP = "訪問回数";

        /** The Constant TOTAL_TILTLE_JP. */
        public static final String TOTAL_TILTLE_JP = "全体";

        /** The Constant HEADER_COL_INDEX. */
        public static final int HEADER_COL_INDEX = 1;

        /** The Constant HEADER_BRANCH_INFO_ROW_INDEX. */
        public static final int HEADER_BRANCH_INFO_ROW_INDEX = 0;

        /** The Constant HEADER_DURATION_INFO_ROW_INDEX. */
        public static final int HEADER_DURATION_INFO_ROW_INDEX = 1;

        /** The Constant HEADER_CREATED_DATE_ROW_INDEX. */
        public static final int HEADER_CREATED_DATE_ROW_INDEX = 2;

        /** The Constant DATA_START_ROW_INDEX. */
        public static final int DATA_START_ROW_INDEX = 3;

        /** The Constant COMPANY_NAME_COL_INDEX. */
        public static final int COMPANY_NAME_COL_INDEX = 0;

        /** The Constant BRANCH_COL_INDEX. */
        public static final int BRANCH_COL_INDEX = 0;

        /** The Constant EMPLOYEE_COL_INDEX. */
        public static final int EMPLOYEE_COL_INDEX = 1;

        /** The Constant PURPOSE_COL_INDEX. */
        public static final int PURPOSE_COL_INDEX = 2;

        /** The Constant VISIT_TIME_COL_INDEX. */
        public static final int VISIT_TIME_COL_INDEX = 3;

        /** The visit time type report. */
        public static final String VISIT_TIME_REPORT_TYPE = "visit_time";

    }

    /** The Constant HEAD_QUATER_CODE. */
    public static final String HQ_CODE = "00";

    /** The Constant HEAD_QUATER_NAME. */
    public static final String HQ_NAME = "本社";

    /** The Constant TEMPLATE_DIR. */
    public static final String TEMPLATE_DIR = "/excel-template/";

    /** The Constant YEAR_JP. */
    public static final String YEAR_JP = "年";

    /** The Constant MONTHLY_JP. */
    public static final String MONTHLY_JP = "月度";

    /** The Constant MONTH_JP. */
    public static final String MONTH_JP = "月";

    /** The Constant DAY_JP. */
    public static final String DAY_JP = "日";

    /** The Constant HQ_NAME_KEY. */
    public static final String HQ_NAME_KEY = "hq_name.key";

    /**
     * The class ReportType.
     */
    public static class ReportType {

        /** The Constant EXPORT_MONTHLY_TYPE. */
        public static final String EXPORT_MONTHLY_TYPE = "MONTHLY";

        /** The Constant EXPORT_PERIODIC_TYPE. */
        public static final String EXPORT_PERIODIC_TYPE = "PERIODIC";

    }


    /**
     * The class Operation of Monthly Report Screen.
     */
    public static class OperationType {

        /** The Constant TEMPORARY_SAVE. */
        public static final int TEMPORARY_SAVE = 1;

        /** The Constant SUBMISSION. */
        public static final int SUBMISSION = 2;

        /** The Constant REVISE. */
        public static final int REJECT = 3;

        /** The Constant APPROVAL. */
        public static final int APPROVAL = 4;

        /** The Constant REOPEN. */
        public static final int REOPEN = 5;
    }

    public static class MonthlyReportHisotry {

        public static final String SUBMISSION = "SB";

        public static final String REJECT = "RE";

        public static final String APPROVE = "AP";

        public static final String REOPEN = "RO";

        public static final int POSSITIONCOUNTSUBMISSION = 0;

        public static final int POSSITIONCOUNTREJECT = 1;

        public static final int POSSITIONCOUNTAPPROVE = 2;

        public static final int POSSITIONCOUNTREOPEN = 3;

        public static final int POSLASTSUBMISSION = 4;

        public static final int POSLASTREJECT = 5;

        public static final int POSLASTAPPROVE = 6;

        public static final int POSLASTREOPEN = 7;

        public static final int EMPTY = -1;

    }

}
