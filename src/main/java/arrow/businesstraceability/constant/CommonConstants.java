package arrow.businesstraceability.constant;

/**
 * The Class CommonConstants.
 */
public class CommonConstants {

    /** The Constant EMPTY_STRING. */
    public static final String EMPTY_STRING = "";

    /** The Constant SANSAN_FILE_DEFAULT_ENCODING. */
    // why use MS932 : http://tools.m-bsys.com/ex/sjis.php
    public static final String SANSAN_FILE_DEFAULT_ENCODING = "MS932";

    /** The Constant SANSAN_FILE_SHIFT_JIS_ENCODING. */
    public static final String SANSAN_FILE_SHIFT_JIS_ENCODING = "Shift_JIS";

    /**
     * The Class ChangeStatus.
     */
    public class ChangeStatus {

        /** The Constant NO_CHANGED. */
        public static final int NO_CHANGED = 0;

        /** The Constant ID_COMPANY_CHANGED. */
        public static final int ID_COMPANY_CHANGED = 1;

        /** The Constant OTHER_ATTR_CHANGED. */
        public static final int OTHER_ATTR_CHANGED = 2;

        /** The Constant NO_CHANGED_BUT_TAG. */
        public static final int NO_CHANGED_BUT_TAG = 3;

        /** The Constant NO_CHANGED_BUT_DATE_EXCHANGE. */
        public static final int NO_CHANGED_BUT_DATE_EXCHANGE = 4;

        /** The Constant COMPANYNAME_OR_URL_CHANGED. */
        public static final int COMPANYNAME_OR_URL_CHANGED = 5;

        /** The Constant DELETED. */
        public static final int DELETED = -1;
    }
}
