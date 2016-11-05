package arrow.businesstraceability.constant;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Named;

import arrow.framework.faces.util.LabelKeySelectItem;

/**
 * The Class AccConstants.
 */
@Named
public class AccConstants {

    /** The my lock. */
    private static volatile Object myLock = new Object();

    /** The list finance statement. */
    private static List<SelectItem> listFinanceStatement;

    /**
     * Gets the map finance statement.
     *
     * @return the map finance statement
     */
    public static List<SelectItem> getMapFinanceStatement() {
        synchronized (AccConstants.myLock) {
            if (AccConstants.listFinanceStatement == null) {
                AccConstants.listFinanceStatement = new ArrayList<SelectItem>();
                AccConstants.listFinanceStatement.add(new LabelKeySelectItem(null,
                    AccConstants.AccComFinance.UNKNOWN_STATEMENT));
                AccConstants.listFinanceStatement.add(new LabelKeySelectItem(AccConstants.AccComFinance.YES_VALUE,
                    AccConstants.AccComFinance.YES_STATEMENT));
                AccConstants.listFinanceStatement.add(new LabelKeySelectItem(AccConstants.AccComFinance.NO_VALUE,
                    AccConstants.AccComFinance.NO_STATEMENT));
            }
        }
        return AccConstants.listFinanceStatement;
    }

    /**
     * The Class AccComFinance.
     */
    public static class AccComFinance {

        /**
         * Instantiates a new acc com finance.
         */
        private AccComFinance() {

        }

        /** The Constant UNKNOWN_STATEMENT. */
        public static final String UNKNOWN_STATEMENT = "unknown";

        /** The Constant YES_STATEMENT. */
        public static final String YES_STATEMENT = "statement_yes";

        /** The Constant NO_STATEMENT. */
        public static final String NO_STATEMENT = "statement_no";

        /** The Constant YES_VALUE. */
        public static final short YES_VALUE = 1;

        /** The Constant NO_VALUE. */
        public static final short NO_VALUE = 0;
    }

    /**
     * The Class AccComCredit.
     */
    public static class AccComCredit {

        /**
         * Instantiates a new acc com credit.
         */
        private AccComCredit() {


        }

        /** The Constant DRAFT_STATUS. */
        public static final Character DRAFT_STATUS = 'D';

        /** The Constant SAVED_STATUS. */
        public static final Character SAVED_STATUS = 'S';
    }

    /** The Constant JP_NO_DATA. */
    public static final String JP_NO_DATA_CODE = "09999";

}
