package arrow.businesstraceability.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.constant.SummaryReportTypes;
import arrow.framework.faces.util.LabelKeySelectItem;
import arrow.framework.util.i18n.Messages;


/**
 * The Class SelectItemGenerator.
 */
public class SelectItemGenerator {

    /** The my lock. */
    private static volatile Object myLock = new Object(); // must be declared volatile

    /**
     * The Enum ListType.
     */
    public static enum ListType {

        /** The comp listed code. */
        COMP_LISTED_CODE,
        /** The period report types. */
        PERIOD_REPORT_TYPES,
        /** The comp limited code. */
        COMP_LIMITED_CODE,
        /** The employee status. */
        EMPLOYEE_STATUS
    }

    /** The company listed codes. */
    private static List<SelectItem> companyListedCodes;

    /** The company limited codes. */
    private static List<SelectItem> companyLimitedCodes;

    /** The periodic report types. */
    private static List<SelectItem> periodicReportTypes;

    /** The employee status select items. */
    private static List<SelectItem> employeeStatusSelectItems;

    /**
     * Gets the list select item.
     *
     * @param listType the list type
     * @return the list select item
     */
    public static List<SelectItem> getListSelectItem(final ListType listType) {
        switch (listType) {
            case COMP_LISTED_CODE:
                return SelectItemGenerator.getCompanyListedCodes();
            case PERIOD_REPORT_TYPES:
                return SelectItemGenerator.getPeriodicReportTypes();
            case COMP_LIMITED_CODE:
                return SelectItemGenerator.getCompanyLimitedCodes();
            case EMPLOYEE_STATUS:
                return SelectItemGenerator.getEmployeeStatusSelectItems();
            default:
                return null;
        }
    }

    /**
     * Gets the periodic report types.
     *
     * @return the periodic report types
     */
    private static List<SelectItem> getPeriodicReportTypes() {
        synchronized (SelectItemGenerator.myLock) {
            if (SelectItemGenerator.periodicReportTypes == null) {
                SelectItemGenerator.periodicReportTypes = new ArrayList<>();
                SelectItemGenerator.periodicReportTypes
                        .add(new LabelKeySelectItem(SummaryReportTypes.PeriodicReportTypes.MONTHLY_REPORT, "monthly"));
                SelectItemGenerator.periodicReportTypes
                        .add(new LabelKeySelectItem(SummaryReportTypes.PeriodicReportTypes.PERIOD_REPORT, "period"));
            }
        }
        return SelectItemGenerator.periodicReportTypes;
    }

    /**
     * Gets the company listed codes.
     *
     * @return the company listed codes
     */
    private static List<SelectItem> getCompanyListedCodes() {
        synchronized (SelectItemGenerator.myLock) {
            if (SelectItemGenerator.companyListedCodes == null) {
                SelectItemGenerator.companyListedCodes = new ArrayList<>();
                SelectItemGenerator.companyListedCodes.add(new LabelKeySelectItem("1", "compListedCode.typeOne"));
                SelectItemGenerator.companyListedCodes.add(new LabelKeySelectItem("2", "compListedCode.typeTwo"));
                SelectItemGenerator.companyListedCodes.add(new LabelKeySelectItem("3", "compListedCode.otherType"));
                SelectItemGenerator.companyListedCodes.add(new LabelKeySelectItem("4", "compListedCode.NotListed"));

            }
        }
        return SelectItemGenerator.companyListedCodes;
    }

    /**
     * Gets the company limited codes.
     *
     * @return the company limited codes
     */
    private static List<SelectItem> getCompanyLimitedCodes() {
        synchronized (SelectItemGenerator.myLock) {
            if (SelectItemGenerator.companyLimitedCodes == null) {
                SelectItemGenerator.companyLimitedCodes = new ArrayList<>();
                SelectItemGenerator.companyLimitedCodes
                        .add(new LabelKeySelectItem("0", "companyLimitedCodes.noAttribute"));
                SelectItemGenerator.companyLimitedCodes
                        .add(new LabelKeySelectItem("1", "companyLimitedCodes.beforeLtd"));
                SelectItemGenerator.companyLimitedCodes
                        .add(new LabelKeySelectItem("2", "companyLimitedCodes.afterLtd"));
                SelectItemGenerator.companyLimitedCodes
                        .add(new LabelKeySelectItem("3", "companyLimitedCodes.beforeWith"));
                SelectItemGenerator.companyLimitedCodes
                        .add(new LabelKeySelectItem("4", "companyLimitedCodes.afterWit"));
            }
        }
        return SelectItemGenerator.companyLimitedCodes;
    }

    /**
     * Gets the employee status select items.
     *
     * @return the employee status select items
     */
    private static List<SelectItem> getEmployeeStatusSelectItems() {
        synchronized (SelectItemGenerator.myLock) {
            if (SelectItemGenerator.employeeStatusSelectItems == null) {
                SelectItemGenerator.employeeStatusSelectItems = new ArrayList<>();
                SelectItemGenerator.employeeStatusSelectItems.add(new LabelKeySelectItem(
                        AuthenticationConstants.EmployeeStatus.WORKING, Messages.get("working")));
                SelectItemGenerator.employeeStatusSelectItems.add(new LabelKeySelectItem(
                        AuthenticationConstants.EmployeeStatus.RETIRED, Messages.get("retired")));
            }
        }
        return SelectItemGenerator.employeeStatusSelectItems;
    }
}
