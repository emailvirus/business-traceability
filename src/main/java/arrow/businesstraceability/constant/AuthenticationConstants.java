package arrow.businesstraceability.constant;


/**
 * The Class AuthenticationConstants.
 */
public class AuthenticationConstants {

    /** The Constant USER_SESSION_KEY. */
    public static final String USER_SESSION_KEY = "blah";

    /** The session timeout. */
    public static final int SESSION_TIMEOUT = 60 * 60 * 1000; // 60 minutes

    /** The index page. */
    public static final String INDEX_PAGE = "index.xhtml";

    /**
     * The Class EmployeeStatus.
     */
    public static class EmployeeStatus {

        /** The Constant RETIRED. */
        public static final String RETIRED = "1";

        /** The Constant WORKING. */
        public static final String WORKING = "0";

        /** The Constant IS_DELETED. */
        public static final boolean IS_DELETED = false;
    }

    public static class EmployeePosition {
        /** The Constant MANAGEMENT. */
        public static final short MANAGEMENT = 101;

        /** The Constant REPRESENTATIVE. */
        public static final short REPRESENTER = 102;

        /** The Constant STAFF. */
        public static final short STAFF = 103;
    }

    public static class BranchPosition {
        /** The Constant LEADER. */
        public static final short LEADER = 101;

        /** The Constant VICE_LEADER. */
        public static final short VICE_LEADER = 102;
    }
}
