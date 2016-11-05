package arrow.businesstraceability.constant;


/**
 * The Class Constants.
 */
public class Constants {

    /** The Constant CUSTOMER_CODE_MAX_LENGTH. */
    public static final int CUSTOMER_CODE_MAX_LENGTH = 4;

    /** The Constant DEFAULT_START_HOUR. */
    public static final int DEFAULT_START_HOUR = 9;

    /** The Constant DEFAULT_START_MINUTE. */
    public static final int DEFAULT_START_MINUTE = 0;

    /** The Constant DEFAULT_END_HOUR. */
    public static final int DEFAULT_END_HOUR = 10;

    /** The Constant DEFAULT_END_MINUTE. */
    public static final int DEFAULT_END_MINUTE = 0;

    /** The Constant HEAD_OFFICE. */
    public static final String HEAD_OFFICE = "本社";

    /** The Constant UTF8_ENCODING. */
    public static final String UTF8_ENCODING = "UTF-8";

    /** The Constant ACTIVE. */
    public static final boolean IS_DELETED = true;

    /** The Constant INACTIVE. */
    public static final boolean IS_NOT_DELETED = false;

    /** The Constant ENCODING_ISO_8859_1. */
    public static final String ENCODING_ISO_8859_1 = "ISO-8859-1";

    /** The Constant DATA_NATION_WIDE. */
    public static final String DATA_NATION_WIDE = "00001";

    /** The Constant SANSAN_FILE_DEFAULT_ENCODING. */
    public static final String SANSAN_FILE_DEFAULT_ENCODING = "MS932";

    /** The Constant SANSAN_FILE_SHIFT_JIS_ENCODING. */
    public static final String SANSAN_FILE_SHIFT_JIS_ENCODING = "Shift_JIS";

    /**
     * The Class BusinessTypeConstant.
     */
    public static class BusinessTypeConstant {

        /** The Constant ARCHITECT. */
        public static final short ARCHITECT = 101;

        /** The Constant BUILDING. */
        public static final short BUILDING = 102;

        /** The Constant ELECTRIC. */
        public static final short ELECTRIC = 103;

        /** The Constant EQUIPMENT. */
        public static final short EQUIPMENT = 104;

        /** The Constant SOFTWARE. */
        public static final short SOFTWARE = 201;

        /** The Constant NETWORK. */
        public static final short NETWORK = 202;

        /** The Constant WIRELESS_COMMUNICATION. */
        public static final short WIRELESS_COMMUNICATION = 301;

        /** The Constant WIRED_COMMUNICATION. */
        public static final short WIRED_COMMUNICATION = 302;

        /** The Constant HARDWARE. */
        public static final short HARDWARE = 401;

        /** The Constant FACTORY. */
        public static final short FACTORY = 501;

        /** The Constant COMMON. */
        public static final short COMMON = 601;

        /** The Constant OFFICE. */
        public static final short OFFICE = 602;

        /** The Constant CALL_CENTER. */
        public static final short CALL_CENTER = 603;
    }

    /**
     * The Class PurposeConstant.
     */
    public static class PurposeConstant {

        /** The Constant OTHER. */
        public static final short OTHER = 1;

        /** The Constant NEW_VISIT. */
        public static final short NEW_VISIT = 2;

        /** The Constant NO_PERIODIC_VISIT. */
        public static final short NO_PERIODIC_VISIT = 3;

        /** The Constant HAVE_PERIODIC_VISIT. */
        public static final short HAVE_PERIODIC_VISIT = 4;

        /** The Constant INTRODUCTION_ENGINEER. */
        public static final short INTRODUCTION_ENGINEER = 5;

        /** The Constant TECHNICAL_DISCUSS. */
        public static final short TECHNICAL_DISCUSS = 6;

        /** The Constant QUOTATION. */
        public static final short QUOTATION = 7;

        /** The Constant COMPLAIN. */
        public static final short COMPLAIN = 8;

    }

    /**
     * Schedule Task Constant.
     *
     * @author ArrowTechnology, Ltd.
     * @version Jan 27, 2016
     */
    public static class ScheduleTask {

        /** Sync keyword. */
        public static final String SYNC_DATA_TO_ELASTIC = "synDataToElastic";

        /** Sync start time. */
        public static final int HOUR_START_SYNC = 24;

        /** Sync inteval time. */
        public static final int HOUR_SYNC_REPEAT = 24;
    }

    /** The Constant AFTER_SELECT_BRANCH_EVENT. */
    public static final String AFTER_SELECT_BRANCH_EVENT = "afterSelectBranchEvent";

    /** The Constant AFTER_SELECT_COMPANY_EVENT. */
    public static final String AFTER_SELECT_COMPANY_EVENT = "afterSelectCompanyEvent";

    /** The Constant MAX_RESULT_SEARCH_COMPANY. */
    public static final int MAX_RESULT_SEARCH_COMPANY = 20;

    /** The Constant CSV_FILE_ALLOW_TYPE. */
    public static final String CSV_FILE_ALLOW_TYPE = "/(\\.|\\/)(csv)$/";

    /** The Constant CSV_FILE_ALLOW_TYPE. */
    public static final String EXCEL_FILE_ALLOW_TYPE = "/(\\.|\\/)(xls)$/";

    /** The Constant CSV_FILE_SIZE_LIMIT. */
    public static final int CSV_FILE_SIZE_LIMIT = 41943040;

    /** The Constant CSV_FILE_SIZE_LIMIT. */
    public static final int EXCEL_FILE_SIZE_LIMIT = 41943040;

    /** The Constant UPLOAD_CSV_DIR_PARAM_NAME. */
    public static final String UPLOAD_CSV_DIR_PARAM_NAME = "UPLOAD_DIR";

    /** The Constant DEFAULT_ENCODING. */
    public static final String DEFAULT_ENCODING = "UTF-8";

    /** The Constant TEMPORAL_FREEZE. */
    public static final String TEMPORAL_FREEZE = "00001";

    /** The Constant BASS_UPLOAD_DIR_PARAM_NAME. */
    public static final String BASS_UPLOAD_DIR_PARAM_NAME = "BASS_UPLOAD_DIR";

    /** The Constant BASS_CONFIG_DIR_PARAM_NAME. */
    public static final String BASS_CONFIG_DIR_PARAM_NAME = "BASS_CONFIG_DIR";

    /** The Constant BASS_CONFIG_EXPORT_PATH. */
    public static final String BASS_CONFIG_EXPORT_PATH = "saving-path";

    /** The Constant BASS_CONFIG_EXPORT_FILE_NAME. */
    public static final String BASS_CONFIG_EXPORT_FILE_NAME = "/config.properties";

    /** The Constant UPLOAD_CSV_DIR_PARAM_NAME. */
    public static final String SANSAN_VERIFY_CSV_DIR_PARAM_NAME = "SAN_VERIFY_UPLOAD_DIR";

    /** The Constant SANSAN_LOG_DOWNLOAD_PARAM. */
    public static final String SANSAN_LOG_DOWNLOAD_PARAM = "DOWNLOAD_DIR";

    /** The Constant DRAFT_CREDIT_STATUS. */
    public static final char DRAFT_CREDIT_STATUS = 'D';

    /** The Constant SAVED_CREDIT_STATUS. */
    public static final char SAVED_CREDIT_STATUS = 'S';

    /** The Constant UPLOAD_CSV_FILE_NAME_TEMPLATE. */
    public static final String UPLOAD_CSV_FILE_NAME_TEMPLATE = "sansan_YYYYMM.csv";

    /** The Constant EMPIRE_DATA_CODE. */
    public static final String EMPIRE_DATA_CODE = "00001";

    /** The Constant PDF_FILE_TYPE. */
    public static final String PDF_FILE_TYPE = "pdf";

    /** The Constant DOC_FILE_TYPE. */
    public static final String DOC_FILE_TYPE = "doc";

    /** The Constant DOCX_FILE_TYPE. */
    public static final String DOCX_FILE_TYPE = "docx";

    /** The Constant XLS_FILE_TYPE. */
    public static final String XLS_FILE_TYPE = "xls";

    /** The Constant XLSX_FILE_TYPE. */
    public static final String XLSX_FILE_TYPE = "xlsx";

    /** The Constant PDF_CONTENT_TYPE. */
    public static final String PDF_CONTENT_TYPE = "application/pdf";

    /** The Constant DOC_CONTENT_TYPE. */
    public static final String DOC_CONTENT_TYPE = "application/msword";

    /** The Constant DOCX_CONTENT_TYPE. */
    public static final String DOCX_CONTENT_TYPE =
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    /** The Constant XLS_CONTENT_TYPE. */
    public static final String XLS_CONTENT_TYPE = "application/vnd.ms-excel";

    /** The Constant XLSX_CONTENT_TYPE. */
    public static final String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    /** The Constant CUSTOMER_CODE_SUFFIX. */
    public static final String CUSTOMER_CODE_SUFFIX = "0000";

}
