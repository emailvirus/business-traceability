package arrow.businesstraceability.control.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import arrow.businesstraceability.auth.event.DataChange;
import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.control.exception.TransactionRollbackException;
import arrow.businesstraceability.control.helper.DataChangeInfo;
import arrow.businesstraceability.control.helper.DataChangeInfo.Action;
import arrow.businesstraceability.control.helper.ScreenContext;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.AddressPointService;
import arrow.businesstraceability.control.service.CompanyManagementService;
import arrow.businesstraceability.control.service.DailyReportService;
import arrow.businesstraceability.control.service.ElasticSearchService;
import arrow.businesstraceability.persistence.dto.Daily_report_DTO;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Customer_info_view;
import arrow.businesstraceability.persistence.entity.Daily_activity_type;
import arrow.businesstraceability.persistence.entity.Daily_report;
import arrow.businesstraceability.persistence.entity.Industry_big_mst;
import arrow.businesstraceability.persistence.entity.Monthly_report_revision;
import arrow.businesstraceability.util.TransformUtils;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.util.DateUtils;
import arrow.framework.util.EventUtils;
import arrow.framework.util.StringUtils;
import arrow.framework.util.collections.CollectionUtils;


// TODO: Auto-generated Javadoc
/**
 * The Class RegisterActivityBean.
 *
 * @author vanlongdao
 */
@Named
@ViewScoped
public class RegisterActivityBean extends AbstractBean {

    /** The daily report service. */
    @Inject
    private DailyReportService dailyReportService;

    /** The user credential. */
    @Inject
    private UserCredential userCredential;

    /** The screen bean. */
    @Inject
    private ScreenBean screenBean;

    /** The address service. */
    @Inject
    private AddressPointService addressService;

    @Inject
    private ElasticSearchService elasticSearchService;

    /** The company service. */
    @Inject
    private CompanyManagementService companyService;

    /** The current report. */
    private Daily_report_DTO currentReport;

    /** The all report. */
    private List<Daily_report> allReport;

    /** The all activity type. */
    private List<Daily_activity_type> allActivityType;

    /** The list activity types. */
    private List<Daily_activity_type> listActivityTypes;

    /** The list industry big mst. */
    private List<Industry_big_mst> listIndustryBigMST;

    /** The report owner employee code. */
    private int reportOwnerEmployeeCode = 0;

    /** The parent screen is history report. */
    private boolean parentScreenIsHistoryReport;

    /** The has report selected. */
    private boolean hasReportSelected;

    /** The number of checked reports. */
    private int numberOfCheckedReports = 0;

    /** The all addrsesspoints. */
    private List<Addresspoint_mst> allAddrsesspoints;

    private List<Company_mst_suggest> listCompanySuggest;

    private Company_mst_suggest companySuggest;

    /**
     * <pre>
     * Business Flow:
     * When Sales Person click Register link, open this screen and:
     * - Set Date = current Date
     * - Create an Empty Report for Current Date.
     * </pre>
     */
    @PostConstruct
    public void init() {
        Map<String, Object> attrs =
            (this.screenBean.getCurrentScreenContext() == null) ? null : this.screenBean.getCurrentScreenContext()
                .getObjectAttributes();
        if ((attrs == null) || (attrs.get(ScreenContext.SharingDataKey.DAILY_REPORT) == null)) {
            this.currentReport = this.initEmptyReport(DateUtils.getCurrentDatetime());
            this.currentReport.setAddresspoint_mst(this.userCredential.getUserEmployee().getAddresspoint_mst());
            this.getReportListAndSetDefaultWorkingTime();
            // Set previous value as default value for next entry
            Short previous_buscode = this.dailyReportService.getLastestBuscode(this.userCredential.getUserCode());
            if (previous_buscode != null) {
                this.currentReport.setDai_bus_code(previous_buscode);
            }
        } else {
            this.currentReport = (Daily_report_DTO) attrs.get(ScreenContext.SharingDataKey.DAILY_REPORT);
            if (!ScreenContext.COMPANY_MANAGEMENT_FORM_CODE.equals(attrs.get(ScreenContext.SharingDataKey.SCREEN_CODE))) {
                this.toggleSelectReport(this.currentReport);
                this.reportOwnerEmployeeCode = this.currentReport.getDai_employee_code();
                this.parentScreenIsHistoryReport = true;
                this.initReportList();
            }
            this.updateDataForCompanyInfo(this.currentReport);
            RequestContext.getCurrentInstance().execute("reSelectRow();");
        }

        ServiceResult<List<Addresspoint_mst>> allAddressResult = this.addressService.getAllAddressPoints();
        if (allAddressResult.isSuccessful()) {
            this.allAddrsesspoints = allAddressResult.getData();
        }
    }

    /**
     * Gets the report list and set default working time.
     *
     * @return the report list and set default working time
     */
    private void getReportListAndSetDefaultWorkingTime() {
        this.initReportList();
        if (CollectionUtils.isNotEmpty(this.allReport)) {
            this.currentReport.setDai_work_stime(this.allReport.get(this.allReport.size() - 1).getDai_work_etime());
            this.currentReport.setDai_work_etime(DateUtils.getNextHour(this.currentReport.getDai_work_stime()));
        } else {
            this.currentReport.setDai_work_stime(DateUtils.getConvertToTime(Constants.DEFAULT_START_HOUR,
                Constants.DEFAULT_START_MINUTE));
            this.currentReport.setDai_work_etime(DateUtils.getConvertToTime(Constants.DEFAULT_END_HOUR,
                Constants.DEFAULT_END_MINUTE));
        }

    }

    /**
     * Inits the empty report.
     *
     * @param selectedDate the selected date
     * @return the daily_report_ dto
     */
    private Daily_report_DTO initEmptyReport(final Date selectedDate) {
        final ServiceResult<Daily_report_DTO> result = this.dailyReportService.addNewReport(selectedDate);
        if (result.isNotSuccessful()) {
            return null;
        }
        return result.getData();
    }

    /**
     * When Sales Person click Add button:
     * <ul>
     * <li>Create empty report for date selected.
     * <li>Refresh screen.
     * </ul>
     */
    private void initNewReport() {
        this.currentReport = this.initEmptyReport(this.currentReport.getDai_work_date());
        this.getReportListAndSetDefaultWorkingTime();
        this.reset();
    }

    /**
     * save new report.
     *
     * @throws TransactionRollbackException the transaction rollback exception
     */
    public void addAndSaveNewReport() throws TransactionRollbackException {
        // Create new report instead of keep using currentReport
        // for case: user select exists record, then click Add instead of Save, the currentReport is still persisted,
        // which is wrong for adding new case.
        Daily_report_DTO newReport = this.initEmptyReport(this.currentReport.getDai_work_date());
        BeanCopier.copy(this.currentReport, newReport);
        final ServiceResult<Daily_report> results =
            this.dailyReportService.saveWhenAddNewReport(newReport, this.userCredential.getUserEmployee(),
                this.userCredential.getUserEmployee().getAddresspoint_mst());

        this.afterSaving(results);
        if (results.isSuccessful()) {
            int countExsitDailyReport =
                this.dailyReportService.getCountDailyReportByEmpCodeAndAddressCodeAndEmpNameAndEmpPost(results
                    .getData().getDai_company_code(), results.getData().getDai_comppoint_code(), results.getData()
                    .getDai_compemp_name(), results.getData().getDai_employee_post());
            if (countExsitDailyReport == 1) {
                Customer_info_view customer_info_view =
                    this.companyService.getCustomerInfo(results.getData().getDai_company_code(), results.getData()
                        .getDai_comppoint_code(), results.getData().getDai_compemp_name(), results.getData()
                        .getDai_employee_post());
                EventUtils.fireAsyncEvent(new DataChangeInfo(customer_info_view,
                    results.getData().getLast_updated_at(), Action.UPSERT), DataChange.LITERAL);

            }
            this.initNewReport();
        }
    }

    /** The monthly report disable for update. */
    private Boolean monthlyReportDisableForUpdate = null;

    /**
     * Checks if is monthly report open for update.
     *
     * @return true, if is monthly report open for update
     */
    public boolean isMonthlyReportDisableForUpdate() {
        if (this.monthlyReportDisableForUpdate == null) {
            final Monthly_report_revision monthlyReport =
                this.dailyReportService.getMonthlyReportByEmpCodeAndTimeOfReport(this.userCredential.getUserEmployee()
                    .getEmp_code(), DateUtils.getMonth(this.currentReport.getDai_work_date()), DateUtils
                    .getYear(this.currentReport.getDai_work_date()));
            this.monthlyReportDisableForUpdate = (monthlyReport != null) && !monthlyReport.isOpenStatus();
        }
        return this.monthlyReportDisableForUpdate;
    }

    /**
     * After saving.
     *
     * @param results the results
     */
    private void afterSaving(final ServiceResult<Daily_report> results) {
        if (results.isNotSuccessful()) {
            results.showMessages(Faces.getContext());
            return;
        }

        InfoMessage.daily_001_save_successfully().show();
        this.allReport =
            this.dailyReportService.getListReport(results.getData().getDai_work_date(),
                (this.reportOwnerEmployeeCode != 0) ? this.reportOwnerEmployeeCode : this.userCredential.getUserCode());
        this.reset();

    }

    /**
     * Save report. When Sales Person click Save button: (Case edit)
     *
     * @throws TransactionRollbackException the transaction rollback exception
     */
    public void saveWhenEditReport() throws TransactionRollbackException {
        final ServiceResult<Daily_report> results = this.dailyReportService.saveWhenEditReport(this.currentReport);
        this.afterSaving(results);
        if (results.isSuccessful()) {
            int countExsitDailyReport =
                this.dailyReportService.getCountDailyReportByEmpCodeAndAddressCodeAndEmpNameAndEmpPost(results
                    .getData().getDai_company_code(), results.getData().getDai_comppoint_code(), results.getData()
                    .getDai_compemp_name(), results.getData().getDai_employee_post());
            if (countExsitDailyReport == 1) {
                Customer_info_view customer_info_view =
                    this.companyService.getCustomerInfo(results.getData().getDai_company_code(), results.getData()
                        .getDai_comppoint_code(), results.getData().getDai_compemp_name(), results.getData()
                        .getDai_employee_post());
                EventUtils.fireAsyncEvent(new DataChangeInfo(customer_info_view,
                    results.getData().getLast_updated_at(), Action.UPSERT), DataChange.LITERAL);

            }

            this.initNewReport();
        }
    }

    /**
     * Gets the activity types.
     *
     * @return the activity types
     */
    public List<Daily_activity_type> getActivityTypes() {
        if (this.listActivityTypes == null) {
            this.listActivityTypes = this.dailyReportService.getActivityTypes();
        }
        return this.listActivityTypes;

    }

    /**
     * Inits the report list.
     *
     * @return the list
     */
    private List<Daily_report> initReportList() {
        if (this.allReport == null) {
            this.allReport =
                this.dailyReportService.getListReport(this.currentReport.getDai_work_date(),
                    this.reportOwnerEmployeeCode == 0 ? this.userCredential.getUserCode()
                        : this.reportOwnerEmployeeCode);
        }
        return this.allReport;
    }

    /**
     * Gets the all report.
     *
     * @return the all report
     */
    public List<Daily_report> getAllReport() {
        this.initReportList();
        return this.allReport;
    }

    /**
     * Sets the all report.
     *
     * @param allReport the new all report
     */
    public void setAllReport(final List<Daily_report> allReport) {
        this.allReport = allReport;
    }

    /**
     * Search daily report.
     */
    public void searchDailyReport() {
        this.monthlyReportDisableForUpdate = null;
        if (this.isMonthlyReportDisableForUpdate()) {
            InfoMessage.daily_018_the_selected_month_is_in_reviewing_you_cant_modify_daily_report_in_this_month()
                .show();
        }

        this.allReport =
            this.dailyReportService.getListReport(this.currentReport.getDai_work_date(),
                (this.reportOwnerEmployeeCode != 0) ? this.reportOwnerEmployeeCode : this.userCredential.getUserCode());
        this.initNewReport();
    }

    /**
     * Gets the current report.
     *
     * @return the current report
     */
    public Daily_report_DTO getCurrentReport() {
        return this.currentReport;
    }

    /**
     * Sets the current report.
     *
     * @param currentReport the new current report
     */
    public void setCurrentReport(final Daily_report_DTO currentReport) {
        this.currentReport = currentReport;
        if (this.userCredential.getUserCode() != currentReport.getDai_employee_code()) {
            this.allReport = new ArrayList<>();
        }
    }

    /**
     * Gets the all activity type.
     *
     * @return the all activity type
     */
    public List<Daily_activity_type> getAllActivityType() {
        return this.allActivityType;
    }

    /**
     * Sets the all activity type.
     *
     * @param allActivityType the new all activity type
     */
    public void setAllActivityType(final List<Daily_activity_type> allActivityType) {
        this.allActivityType = allActivityType;
    }

    /**
     * Edits the report.
     *
     * @param event the event
     */
    public void editReport(final SelectEvent event) {
        this.reset();
        this.currentReport = ((Daily_report) event.getObject()).getDto();
        this.updateDataForCompanyInfo(this.currentReport);
    }

    /**
     * Update data for company info.
     *
     * @param selectedReport the selected report
     */
    private void updateDataForCompanyInfo(final Daily_report_DTO selectedReport) {
        if (selectedReport == null) {
            return;
        }
        this.setCompanySuggest(TransformUtils.transform(selectedReport.getCompany_mst()));
    }

    /**
     * Gets the selected reports.
     *
     * @return the selected reports
     */
    private List<Daily_report> getSelectedReports() {
        final List<Daily_report> selectedReports = new ArrayList<Daily_report>();
        for (final Daily_report report : this.allReport) {
            if (report.isSelected()) {
                selectedReports.add(report);
            }
        }

        return selectedReports;
    }

    /**
     * Delete report.
     *
     * @throws TransactionRollbackException the transaction rollback exception
     */
    public void deleteReport() throws TransactionRollbackException {
        if ((this.allReport == null) || CollectionUtils.isEmpty(this.allReport)) {
            return;
        }
        final List<Daily_report> selectedReports = this.getSelectedReports();
        if (CollectionUtils.isEmpty(selectedReports)) {
            ErrorMessage.daily_005_you_must_select_atleast_1_item().show();
            return;
        }

        this.numberOfCheckedReports -= selectedReports.size();
        final ServiceResult<Daily_report> deletedReport = this.dailyReportService.deleteReport(selectedReports);

        if (!deletedReport.isSuccessful()) {
            ErrorMessage.daily_002_delete_unsucessfully().show();
            return;
        }

        this.allReport =
            this.dailyReportService.getListReport(this.currentReport.getDai_work_date(),
                (this.reportOwnerEmployeeCode != 0) ? this.reportOwnerEmployeeCode : this.userCredential.getUserCode());
        this.currentReport = this.initEmptyReport(this.currentReport.getDai_work_date());

        InfoMessage.daily_002_delete_successfully().show();
        this.reset();
    }

    /**
     * Checks if is checks for report selected.
     *
     * @return true, if is checks for report selected
     */
    public boolean isHasReportSelected() {
        return this.hasReportSelected;
    }

    /**
     * Sets the checks for report selected.
     *
     * @param hasReportSelected the new checks for report selected
     */
    public void setHasReportSelected(final boolean hasReportSelected) {
        this.hasReportSelected = hasReportSelected;
    }

    /**
     * Checks if is enable delete.
     *
     * @return true, if is enable delete
     */
    public boolean isEnableDelete() {
        return ((this.reportOwnerEmployeeCode == 0) || this.parentScreenIsHistoryReport)
            && (this.numberOfCheckedReports > 0);
    }

    /**
     * Checks if is disable add.
     *
     * @return true, if is disable add
     */
    public boolean isDisableAdd() {
        return this.isMonthlyReportDisableForUpdate()
            || ((this.reportOwnerEmployeeCode != 0) && this.parentScreenIsHistoryReport);
    }

    /**
     * Checks if is disable update.
     *
     * @return true, if is disable update
     */
    public boolean isDisableUpdate() {
        return this.isMonthlyReportDisableForUpdate()
            || ((this.reportOwnerEmployeeCode != 0) && !this.parentScreenIsHistoryReport)
            || ((this.currentReport != null) && !this.currentReport.isPersisted());
    }

    /**
     * Toggle select report.
     *
     * @param report the report
     */
    public void toggleSelectReport(final Daily_report report) {
        this.numberOfCheckedReports -= report.isSelected() ? 1 : -1;

    }

    /**
     * Checks if is enable save.
     *
     * @return true, if is enable save
     */
    public boolean isEnableSave() {
        return (this.currentReport != null) && this.currentReport.isValid();
    }

    /**
     * Check all reports.
     */
    public void checkAllReports() {
        boolean isNotEmpty = CollectionUtils.isNotEmpty(this.allReport);
        this.numberOfCheckedReports = isNotEmpty ? this.allReport.size() : 0;
        if (isNotEmpty) {
            for (Daily_report report : this.allReport) {
                report.setSelected(true);
            }
        }
        this.hasReportSelected = isNotEmpty;
    }

    /**
     * Un check all reports.
     */
    public void unCheckAllReports() {
        this.numberOfCheckedReports = 0;
        if (CollectionUtils.isNotEmpty(this.allReport)) {
            for (Daily_report report : this.allReport) {
                report.setSelected(false);
            }
        }
        this.hasReportSelected = false;
    }

    /**
     * Gets the report owner employee.
     *
     * @return the report owner employee
     */
    public int getReportOwnerEmployee() {
        return this.reportOwnerEmployeeCode;
    }

    /**
     * Sets the report owner employee.
     *
     * @param reportOwner the new report owner employee
     */
    public void setReportOwnerEmployee(final int reportOwner) {
        this.reportOwnerEmployeeCode = reportOwner;
    }

    /**
     * Checks if is parent screen is history report.
     *
     * @return true, if is parent screen is history report
     */
    public boolean isParentScreenIsHistoryReport() {
        return this.parentScreenIsHistoryReport;
    }

    /**
     * Sets the parent screen is history report.
     *
     * @param parentScreenIsHistoryReport the new parent screen is history report
     */
    public void setParentScreenIsHistoryReport(final boolean parentScreenIsHistoryReport) {
        this.parentScreenIsHistoryReport = parentScreenIsHistoryReport;
    }

    /**
     * Creates the new company.
     */
    public void createNewCompany() {
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(ScreenContext.SharingDataKey.DAILY_REPORT, this.currentReport);
        attrs.put(ScreenContext.SharingDataKey.SCREEN_CODE, ScreenContext.DAILY_REPORT_REGISTER);
        this.screenBean.switchScreenWithObjectData(ScreenContext.COMPANY_MANAGEMENT_FORM_CODE, true, attrs);
    }

    /**
     * Gets the list industry big mst.
     *
     * @return the list industry big mst
     */
    public List<Industry_big_mst> getListIndustryBigMST() {
        if (this.listIndustryBigMST == null) {
            this.listIndustryBigMST = this.dailyReportService.getAllIndustryBigMST();
        }
        return this.listIndustryBigMST;
    }

    /**
     * Sets the list industry big mst.
     *
     * @param listIndustryBigMST the new list industry big mst
     */
    public void setListIndustryBigMST(final List<Industry_big_mst> listIndustryBigMST) {
        this.listIndustryBigMST = listIndustryBigMST;
    }


    /**
     * Change employee and department name.
     */
    public void changeEmployeeAndDepartmentName() {
        final FacesContext context = FacesContext.getCurrentInstance();
        final Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        final String employeeName = map.get("employeeName");
        this.currentReport.setDai_compemp_name(employeeName);
        final String departmentName = map.get("departmentName");
        this.currentReport.setDai_employee_post(departmentName);
    }

    public List<Addresspoint_mst> getBranchOfCompany() {
        return this.allAddrsesspoints;
    }

    /**
     * Filter company.
     *
     * @param query the query
     * @return the list
     */
    public List<Company_mst_suggest> filterCompany(final String query) {
        ServiceResult<List<Company_mst_suggest>> result = this.elasticSearchService.suggestCompany(query);
        if (result.isSuccessful()) {
            this.listCompanySuggest = result.getData();
        } else {
            this.listCompanySuggest = this.searchCompanyOnDatabase(query);
        }
        return this.listCompanySuggest;
    }

    public List<Company_mst_suggest> getListCompanySuggest() {
        return this.listCompanySuggest;
    }

    public void setListCompanySuggest(final List<Company_mst_suggest> listCompanySuggest) {
        this.listCompanySuggest = listCompanySuggest;
    }

    private List<Company_mst_suggest> searchCompanyOnDatabase(final String query) {
        List<Company_mst> companies = this.companyService.searchCompany(query);
        if (CollectionUtils.isEmpty(companies)) {
            return Collections.emptyList();
        }
        List<Company_mst_suggest> listSuggestItems = new ArrayList<>();
        companies.stream().map(company -> listSuggestItems.add(TransformUtils.transform(company)))
            .collect(Collectors.toList());
        return listSuggestItems;
    }

    /**
     * Reset current screen status.
     */
    public void reset() {
        this.companySuggest = null;
    }

    /**
     * Gets the company suggest.
     *
     * @return the company suggest
     */
    public Company_mst_suggest getCompanySuggest() {
        if ((this.currentReport.getCompany_mst() != null) && (this.companySuggest == null)) {
            this.companySuggest = TransformUtils.transform(this.currentReport.getCompany_mst());
        }
        return this.companySuggest;
    }

    /**
     * Sets the company suggest.
     *
     * @param companySuggest the new company suggest
     */
    public void setCompanySuggest(final Company_mst_suggest companySuggest) {
        boolean isInitCompanySuggestForEditReport =
            StringUtils.isNotEmpty(this.currentReport.getDai_comppoint_code()) && (null == this.companySuggest);
        this.companySuggest = companySuggest;
        if ((companySuggest != null)) {
            if (!isInitCompanySuggestForEditReport) {
                Company_mst company =
                    this.companyService.findCompanyByCompanyCode(companySuggest.getCom_company_code());
                this.currentReport.setCompany_mst(company);
                this.currentReport.setDai_comppoint_code((null != company) ? company.getAddresspoint_mst()
                    .getAdp_code() : null);
            }
        } else {
            this.currentReport.setCompany_mst(null);
            this.currentReport.setDai_comppoint_code(null);
        }
    }

    /**
     * Gets the listed label.
     *
     * @param valueListed the value listed
     * @return the listed label
     */
    public String getListedLabel(final String valueListed) {
        return this.companyService.getLabelCompanyListed(valueListed);
    }

    public void changeValueEmployeeNameListener(final AjaxBehaviorEvent event) {
        this.currentReport.setDai_compemp_name(((UIOutput) event.getSource()).getValue().toString());
    }

    public void changeValueDepartmentNameListener(final AjaxBehaviorEvent event) {
        this.currentReport.setDai_employee_post(((UIOutput) event.getSource()).getValue().toString());
    }
}
