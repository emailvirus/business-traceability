package arrow.businesstraceability.mobile.bean;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.constant.MobilePageId;
import arrow.businesstraceability.control.bean.MobileScreenFlowBean;
import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.control.exception.TransactionRollbackException;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.AddressPointService;
import arrow.businesstraceability.control.service.DailyReportService;
import arrow.businesstraceability.event.observe.CompanySearchEvent;
import arrow.businesstraceability.persistence.dto.Daily_report_DTO;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Customer_info_view;
import arrow.businesstraceability.persistence.entity.Daily_activity_type;
import arrow.businesstraceability.persistence.entity.Daily_report;
import arrow.businesstraceability.persistence.entity.Industry_big_mst;
import arrow.businesstraceability.persistence.entity.Monthly_report_revision;
import arrow.framework.bean.AbstractBean;
import arrow.framework.exception.ArrException;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.util.DateUtils;
import arrow.framework.util.JsonUtils;
import arrow.framework.util.StringUtils;
import arrow.framework.util.cdi.Instance;
import arrow.framework.util.collections.CollectionUtils;
import arrow.framework.util.i18n.Messages;


/**
 * The Class RegisterActivityMobileBean.
 *
 * @author Tai
 */
@Named
@ViewScoped
public class RegisterActivityMobileBean extends AbstractBean {

    /** The html5 date format. */
    private final SimpleDateFormat html5DateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /** The mobile screen flow bean. */
    @Inject
    MobileScreenFlowBean mobileScreenFlowBean;

    /** The notification mobile bean. */
    @Inject
    NotificationMobileBean notificationMobileBean;

    /** The daily report service. */
    @Inject
    private DailyReportService dailyReportService;

    /** The user credential. */
    @Inject
    private UserCredential userCredential;

    /** The current face context. */
    @Inject
    private FacesContext currentFaceContext;

    /** The current report. */
    private Daily_report_DTO currentReport;

    private Monthly_report_revision monthlyReport;

    /** The all report. */
    private List<Daily_report> allReport;

    /** The list selected report. */
    private List<Daily_report> listSelectedReport;

    /** The selected report. */
    private List<Daily_report> selectedReport;

    /** The all activity type. */
    private List<Daily_activity_type> allActivityType;

    /** The list activity types. */
    private List<Daily_activity_type> listActivityTypes;

    /** The list industry big mst. */
    private List<Industry_big_mst> listIndustryBigMST;

    /** The previous_buscode. */
    private Short previousBuscode;

    /** The render confirm dlg. */
    private boolean renderConfirmDlg;

    /** The choosed register. */
    private boolean choosedRegister;

    /** The view report. */
    private boolean viewReport;

    /** The start work date. */
    private Date startWorkDate;

    /** The end work date. */
    private Date endWorkDate;

    /** The work date input. */
    private String workDateInput;

    /** The selected date. */
    private String selectedDate;

    /** The start time hour. */
    private int startTimeHour = 0;

    /** The start time minute. */
    private int startTimeMinute = -1;

    /** The end time hour. */
    private int endTimeHour = 0;

    /** The end time minute. */
    private int endTimeMinute = -1;

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
        this.selectedDate = this.getHtml5DateFormat(DateUtils.getCurrentDatetime());
        this.allReport =
            this.dailyReportService.getListReport(DateUtils.getCurrentDatetime(), this.userCredential.getUserCode());
        final ServiceResult<Daily_report_DTO> result =
            this.dailyReportService.addNewReport(DateUtils.getCurrentDatetime());
        if (result.isSuccessful()) {
            this.currentReport = result.getData();
            this.currentReport.setDai_work_date(DateUtils.getCurrentDatetime());
        }
        // Set previous value as default value for next entry
        this.previousBuscode = this.dailyReportService.getLastestBuscode(this.userCredential.getUserCode());
        if (this.previousBuscode != null) {
            this.currentReport.setDai_bus_code(this.previousBuscode);
        }
    }

    /**
     * When Sales Person click Add button:
     * <ul>
     * <li>Create empty report for date selected.
     * <li>Refresh screen.
     * </ul>
     */
    public void addNewReport() {
        this.selectedDate = this.getHtml5DateFormat(this.currentReport.getDai_work_date());
        final ServiceResult<Daily_report_DTO> results =
            this.dailyReportService.addNewReport(this.currentReport.getDai_work_date());
        if (results.isSuccessful()) {
            this.currentReport = results.getData();
            this.currentReport.setDai_bus_code(this.previousBuscode);
            this.companyName = StringUtils.EMPTY_STRING;
        }
    }

    /**
     * save new report.
     *
     * @throws ArrException the arr exception
     */
    public void addAndSaveNewReport() throws ArrException {
        // update data company point code same as address point of current company
        this.currentReport.setDai_comppoint_code(this.currentReport.getCompany_mst().getCom_point_code());

        // update work time to DTO object.
        this.currentReport.setDai_work_stime(this.startWorkDate);
        this.currentReport.setDai_work_etime(this.endWorkDate);

        if (!this.currentReport.getDai_work_type()) {
            // If not select visit in category => refresh data in corporation information
            this.refreshDataCategoryInfo();
        }

        // set status dai_rimaindar_flg
        if ((this.currentReport.getDai_summary_stime() != null) && (this.currentReport.getDai_summary_etime() != null)) {
            // if startReminderTime and endReminderTime has content => dai_rimaindar_flg = true
            this.currentReport.setDai_rimaindar_flg(true);
        } else {
            this.setStartReminderTime("");
            this.setEndReminderTime("");
            this.currentReport.setDai_rimaindar_flg(false);
        }

        // Create new report to save new report
        ServiceResult<Daily_report_DTO> resultCreateNewReport =
            this.dailyReportService.addNewReport(this.currentReport.getDai_work_date());
        if (resultCreateNewReport.isNotSuccessful()) {
            return;
        }

        Daily_report_DTO newReport = resultCreateNewReport.getData();
        BeanCopier.copy(this.currentReport, newReport);

        // try to save the report.
        final ServiceResult<Daily_report> results =
            this.dailyReportService.saveWhenAddNewReport(newReport, this.userCredential.getUserEmployee(),
                this.userCredential.getUserEmployee().getAddresspoint_mst());
        if (results.isSuccessful()) {
            if (this.userCredential.getUserCode() == results.getData().getDai_employee_code()) {
                // If the current user edit report of him , => show report that save success
                // If the current user edit report that is not of him (other employee) => No
                // display
                this.allReport =
                    this.dailyReportService.getListReport(results.getData().getDai_work_date(),
                        this.userCredential.getUserCode());

            }
            // Clear data in right panel
            this.clearField();
            this.setJobTimeAfterAddReport();
            this.addNewReport();
            InfoMessage.daily_001_save_successfully().show();
        } else {
            results.showMessages(this.currentFaceContext);
            ErrorMessage.daily_001_save_unsuccessfully().show();
        }
    }

    /**
     * Save report. When Sales Person click Update button: (Case edit)
     *
     * @throws TransactionRollbackException the transaction rollback exception
     */
    public void saveWhenEditReport() throws TransactionRollbackException {
        // update work time to DTO object.
        this.currentReport.setDai_work_stime(this.startWorkDate);
        this.currentReport.setDai_work_etime(this.endWorkDate);
        if (!this.currentReport.getDai_work_type()) {
            // If not select visit in category => refresh data in corporation information
            this.refreshDataCategoryInfo();
        }

        // set status dai_rimaindar_flg
        if ((this.currentReport.getDai_summary_stime() != null) && (this.currentReport.getDai_summary_etime() != null)) {
            // if startReminderTime and endReminderTime has content => dai_rimaindar_flg = true
            this.currentReport.setDai_rimaindar_flg(true);
        } else {
            this.setStartReminderTime(null);
            this.setEndReminderTime(null);
            this.currentReport.setDai_rimaindar_flg(false);
        }

        // try to save the report.
        final ServiceResult<Daily_report> results = this.dailyReportService.saveWhenEditReport(this.currentReport);
        if (results.isSuccessful()) {
            // Case : Edit report success => not clear right panel
            if (this.userCredential.getUserCode() == results.getData().getDai_employee_code()) {
                // If the current user edit report of him , => show report that save success
                // If the current user edit report that is not of him (other employee) => No
                // display
                this.allReport =
                    this.dailyReportService.getListReport(results.getData().getDai_work_date(),
                        this.userCredential.getUserCode());
            }

            BeanCopier.copy(results.getData(), this.currentReport);
            InfoMessage.daily_001_save_successfully().show();
        } else {
            results.showMessages(this.currentFaceContext);
            ErrorMessage.daily_001_save_unsuccessfully().show();
        }
    }

    /**
     * Open search company screen.
     *
     * @return the string
     */
    public String openSearchCompanyScreen() {
        Instance.get(SearchCompanyMobileBean.class).init();
        return this.mobileScreenFlowBean.gotoPage(MobilePageId.SEARCH_COMPANY);
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
     * Gets the all report.
     *
     * @return the all report
     */
    public List<Daily_report> getAllReport() {
        this.allReport =
            this.dailyReportService.getListReport(this.parseHtml5DateFormat(this.selectedDate),
                this.userCredential.getUserCode());
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
        this.allReport =
            this.dailyReportService.getListReport(this.parseHtml5DateFormat(this.selectedDate),
                this.userCredential.getUserCode());
        this.addNewReport();
    }

    /**
     * Gets the selected date.
     *
     * @return the selected date
     */
    public String getSelectedDate() {
        return this.selectedDate;
    }

    /**
     * Sets the selected date.
     *
     * @param selectedDate the new selected date
     */
    public void setSelectedDate(final String selectedDate) {
        this.selectedDate = selectedDate;
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
        // enable delete button and edit button
        this.choosedRegister = false;
        this.setJobTime();
        if (this.userCredential.getUserCode() != currentReport.getDai_employee_code()) {
            this.allReport = new ArrayList<>();
            // this.dailyReportService.getListReportCondictionEmp(currentReport.getDai_work_date(),
            // currentReport.getDai_employee_code());
        }
    }

    /**
     * Sets the job time.
     */
    public void setJobTime() {
        this.workDateInput = this.getHtml5DateFormat(this.currentReport.getDai_work_date());

        this.startWorkDate = this.currentReport.getDai_work_stime();
        this.startTimeHour = DateUtils.getHour(this.startWorkDate);
        this.startTimeMinute = DateUtils.getMinute(this.startWorkDate);

        this.endWorkDate = this.currentReport.getDai_work_etime();
        this.endTimeHour = DateUtils.getHour(this.endWorkDate);
        this.endTimeMinute = DateUtils.getMinute(this.endWorkDate);
    }

    /**
     * Sets the job time after add report.
     */
    public void setJobTimeAfterAddReport() {
        this.setWorkDateInput(this.getHtml5DateFormat(this.currentReport.getDai_work_date()));

        this.endWorkDate = this.allReport.get(this.allReport.size() - 1).getDai_work_etime();
        this.startTimeHour = DateUtils.getHour(this.endWorkDate);
        this.startTimeMinute = DateUtils.getMinute(this.endWorkDate);

        this.endTimeHour = 0;
        this.endTimeMinute = -1;
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
     * Checks if is selected all.
     *
     * @return true, if is selected all
     */
    public boolean isSelectedAll() {
        if ((this.allReport == null) || CollectionUtils.isEmpty(this.allReport)) {
            return false;
        }

        for (final Daily_report report : this.allReport) {
            if (!report.isSelected()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sets the selected all.
     *
     * @param selectedAll the new selected all
     */
    public void setSelectedAll(final boolean selectedAll) {
        if ((this.allReport == null) || CollectionUtils.isEmpty(this.allReport)) {
            return;
        }
        for (final Daily_report report : this.allReport) {
            report.setSelected(selectedAll);
        }
    }

    /**
     * Delete report.
     *
     * @return the string
     * @throws TransactionRollbackException the transaction rollback exception
     */
    public String deleteReport() throws TransactionRollbackException {
        // store report is selected.
        final List<Daily_report> selectedReports = new ArrayList<Daily_report>();
        selectedReports.add(this.currentReport);

        final ServiceResult<Daily_report> deletedReport = this.dailyReportService.deleteReport(selectedReports);

        if (deletedReport.isSuccessful()) {
            // Reset table daily report and reset form add new report
            this.allReport =
                this.dailyReportService.getListReport(this.parseHtml5DateFormat(this.selectedDate),
                    this.userCredential.getUserCode());

            this.currentReport = new Daily_report_DTO();
            this.currentReport.setDai_work_date(this.parseHtml5DateFormat(this.selectedDate));
            InfoMessage.daily_002_delete_successfully().show();
            Faces.getContext().addMessage("deleteReport",
                InfoMessage.daily_002_delete_successfully().createFaceMessage());
            return this.mobileScreenFlowBean.goBack();
        } else {
            ErrorMessage.daily_002_delete_unsucessfully().show();
        }
        return StringUtils.EMPTY_STRING;
    }

    /**
     * Gets the end time hour.
     *
     * @return the end time hour
     */
    public int getEndTimeHour() {
        return this.endTimeHour;
    }

    /**
     * Sets the end time hour.
     *
     * @param endTimeHour the new end time hour
     */
    public void setEndTimeHour(final int endTimeHour) {
        this.endTimeHour = endTimeHour;

        if ((this.endTimeHour != 0) && (this.endTimeMinute >= 0)) {
            this.endWorkDate = DateUtils.buildTime(this.endTimeHour, this.endTimeMinute, 0);
        }
    }

    /**
     * Gets the end time minute.
     *
     * @return the end time minute
     */
    public int getEndTimeMinute() {
        return this.endTimeMinute;
    }

    /**
     * Sets the end time minute.
     *
     * @param endTimeMinute the new end time minute
     */
    public void setEndTimeMinute(final int endTimeMinute) {
        this.endTimeMinute = endTimeMinute;
        if ((this.endTimeHour != 0) && (this.endTimeMinute >= 0)) {
            this.endWorkDate = DateUtils.buildTime(this.endTimeHour, this.endTimeMinute, 0);
        }
    }

    /**
     * Gets the start time hour.
     *
     * @return the start time hour
     */
    public int getStartTimeHour() {
        return this.startTimeHour;
    }

    /**
     * Sets the start time hour.
     *
     * @param startTimeHour the new start time hour
     */
    public void setStartTimeHour(final int startTimeHour) {
        this.startTimeHour = startTimeHour;

        if ((this.startTimeHour != 0) && (this.startTimeMinute >= 0)) {
            this.startWorkDate = DateUtils.buildTime(this.startTimeHour, this.startTimeMinute, 0);
            this.log.info(DateUtils.formatTime(this.startWorkDate));
        }

    }

    /**
     * Gets the start time minute.
     *
     * @return the start time minute
     */
    public int getStartTimeMinute() {
        return this.startTimeMinute;
    }

    /**
     * Sets the start time minute.
     *
     * @param startTimeMinute the new start time minute
     */
    public void setStartTimeMinute(final int startTimeMinute) {
        this.startTimeMinute = startTimeMinute;

        if ((this.startTimeHour != 0) && (this.startTimeMinute >= 0)) {
            this.startWorkDate = DateUtils.buildTime(this.startTimeHour, this.startTimeMinute, 0);
            this.log.info(DateUtils.formatTime(this.startWorkDate));
        }

    }

    /**
     * Observe event.
     *
     * @param event the event
     */
    public void observeEvent(@Observes final CompanySearchEvent event) {
        if (event.getEventName().equalsIgnoreCase("afterSelectCompanyEvent")) {
            final Company_mst selectedCompany = event.getSelectedCompany();
            if (this.currentReport != null) {
                this.currentReport.setCompany_mst(selectedCompany);
            }
        }
    }

    /**
     * Gets the previous_buscode.
     *
     * @return the previous_buscode
     */
    public Short getPreviousBuscode() {
        return this.previousBuscode;
    }

    /**
     * Sets the previous_buscode.
     *
     * @param previousBuscode the new previous_buscode
     */
    public void setPreviousBuscode(final Short previousBuscode) {
        this.previousBuscode = previousBuscode;
    }

    /**
     * Gets the list selected report.
     *
     * @return the list selected report
     */
    public List<Daily_report> getListSelectedReport() {
        return this.listSelectedReport;
    }

    /**
     * Sets the list selected report.
     *
     * @param listSelectedReport the new list selected report
     */
    public void setListSelectedReport(final List<Daily_report> listSelectedReport) {
        this.listSelectedReport = listSelectedReport;
    }

    /**
     * Gets the selected report.
     *
     * @return the selected report
     */
    public List<Daily_report> getSelectedReport() {
        return this.selectedReport;
    }

    /**
     * Sets the selected report.
     *
     * @param selectedReport the new selected report
     */
    public void setSelectedReport(final List<Daily_report> selectedReport) {
        this.selectedReport = selectedReport;
    }


    /**
     * Checks if is render confirm dlg.
     *
     * @return true, if is render confirm dlg
     */
    public boolean isRenderConfirmDlg() {
        return this.renderConfirmDlg;
    }

    /**
     * Sets the render confirm dlg.
     *
     * @param renderConfirmDlg the new render confirm dlg
     */
    public void setRenderConfirmDlg(final boolean renderConfirmDlg) {
        this.renderConfirmDlg = renderConfirmDlg;
    }

    /**
     * Refresh data category info.
     */
    public void refreshDataCategoryInfo() {
        this.currentReport.setDai_compemp_name(null);
        this.currentReport.setDai_employee_post(null);
        this.currentReport.setCompany_mst(null);
        this.currentReport.setDai_bus_code(null);
        this.currentReport.setDaily_activity_type(null);
    }

    /**
     * Gets the work date input.
     *
     * @return the work date input
     */
    public String getWorkDateInput() {
        return this.workDateInput;
    }

    /**
     * Sets the work date input.
     *
     * @param workDateInput the new work date input
     */
    public void setWorkDateInput(final String workDateInput) {
        this.currentReport.setDai_work_date(this.parseHtml5DateFormat(workDateInput));
        this.workDateInput = workDateInput;

        this.allReport =
            this.dailyReportService.getListReport(this.currentReport.getDai_work_date(),
                this.userCredential.getUserCode());
        if (this.allReport.size() != 0) {
            this.setStartTimeHour(DateUtils.getHour(this.allReport.get(this.allReport.size() - 1).getDai_work_etime()));
            this.setStartTimeMinute(DateUtils.getMinute(this.allReport.get(this.allReport.size() - 1)
                .getDai_work_etime()));
        } else {
            this.startTimeHour = 0;
            this.startTimeMinute = -1;
        }
        this.endTimeHour = 0;
        this.endTimeMinute = -1;
        this.clearField();
        this.choosedRegister = true;
        this.monthlyReport = null;
        if (this.isDisabledRegisterDailyReportWhenMonthlyReportWasCreated()) {
            InfoMessage.daily_018_the_selected_month_is_in_reviewing_you_cant_modify_daily_report_in_this_month()
                .show();
        }
    }

    /**
     * Gets the choosed register.
     *
     * @return the choosed register
     */
    public boolean getChoosedRegister() {
        return this.choosedRegister;
    }

    /**
     * Sets the choosed register.
     *
     * @param choosedRegister the new choosed register
     */
    public void setChoosedRegister(final boolean choosedRegister) {
        this.choosedRegister = choosedRegister;
        // clear all field not use
        this.clearField();
        // select current date
        this.setWorkDateInput(this.getHtml5DateFormat(DateUtils.getCurrentDatetime()));
        this.viewReport = false;

        // set Start time = latest end time of current day
        this.allReport =
            this.dailyReportService.getListReport(this.currentReport.getDai_work_date(),
                this.userCredential.getUserCode());
        if (this.allReport.size() != 0) {
            this.setStartTimeHour(DateUtils.getHour(this.allReport.get(this.allReport.size() - 1).getDai_work_etime()));
            this.setStartTimeMinute(DateUtils.getMinute(this.allReport.get(this.allReport.size() - 1)
                .getDai_work_etime()));
        } else {
            this.startTimeHour = 0;
            this.startTimeMinute = -1;
        }
        this.endTimeHour = 0;
        this.endTimeMinute = -1;
    }

    /**
     * Clear field.
     */
    public void clearField() {
        this.currentReport.setDai_work_type(true);
        this.currentReport.setDai_rimaindar_flg(false);
        this.currentReport.setDai_bus_code(null);
        this.currentReport.setDai_business_details(null);
        this.currentReport.setCompany_mst(null);
        this.currentReport.setDai_compemp_name(null);
        this.currentReport.setDai_employee_post(null);
        this.currentReport.setDaily_activity_type(null);
        this.setStartReminderTime(null);
        this.setEndReminderTime(null);
    }

    /**
     * Checks if is view report.
     *
     * @return true, if is view report
     */
    public boolean isViewReport() {
        return this.viewReport;
    }

    /**
     * Sets the view report.
     *
     * @param viewReport the new view report
     */
    public void setViewReport(final boolean viewReport) {
        this.viewReport = viewReport;
    }

    /**
     * Gets the start reminder time.
     *
     * @return the start reminder time
     */
    public String getStartReminderTime() {
        if (this.currentReport.getDai_summary_stime() != null) {
            return this.getHtml5DateFormat(this.currentReport.getDai_summary_stime());
        }
        return StringUtils.EMPTY_STRING;
    }

    /**
     * Sets the start reminder time.
     *
     * @param startReminderTime the new start reminder time
     */
    public void setStartReminderTime(final String startReminderTime) {
        if (startReminderTime != null) {
            this.currentReport.setDai_summary_stime(this.parseHtml5DateFormat(startReminderTime));
        } else {
            this.currentReport.setDai_summary_stime(null);
        }
    }

    /**
     * Gets the end reminder time.
     *
     * @return the end reminder time
     */
    public String getEndReminderTime() {
        if (this.currentReport.getDai_summary_etime() != null) {
            return this.getHtml5DateFormat(this.currentReport.getDai_summary_etime());
        }
        return StringUtils.EMPTY_STRING;
    }

    /**
     * Sets the end reminder time.
     *
     * @param endReminderTime the new end reminder time
     */
    public void setEndReminderTime(final String endReminderTime) {
        if (endReminderTime != null) {
            this.currentReport.setDai_summary_etime(this.parseHtml5DateFormat(endReminderTime));
        } else {
            this.currentReport.setDai_summary_etime(null);
        }
    }

    /**
     * Parses the html5 date format.
     *
     * @param selectDate the select date
     * @return the date
     */
    public Date parseHtml5DateFormat(final String selectDate) {
        if (StringUtils.isEmpty(selectDate)) {
            return null;
        } else {
            try {
                return this.html5DateFormat.parse(selectDate);
            } catch (ParseException pe) {
                super.log.debug("Parse Date Exception", pe);
            }
        }
        return null;
    }

    /**
     * Gets the html5 date format.
     *
     * @param date the date
     * @return the html5 date format
     */
    public String getHtml5DateFormat(final Date date) {
        return this.html5DateFormat.format(date);
    }

    /**
     * Change time.
     *
     * @param timeString the s
     * @return the string
     */
    public String changeTime(final String timeString) {
        String[] str = timeString.split(":");
        int hour = Integer.parseInt(str[0]);
        int minute = Integer.parseInt(str[1]);

        if ((minute % 10) >= 5) {
            minute = (minute + 10) - (minute % 10);
            if (minute == 60) {
                minute = 0;
                hour++;
            }
        } else {
            minute = minute - (minute % 10);
        }
        String hh = "";
        String mm = "";

        if (hour < 10) {
            hh = "0" + hour;
        } else {
            hh = hour + "";
        }

        if (minute < 10) {
            mm = "0" + minute;
        } else {
            mm = minute + "";
        }
        return hh + ":" + mm;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        if (this.viewReport && (this.notificationMobileBean != null)) {
            return Messages.get("daily_report") + " - "
                + this.notificationMobileBean.getEmployeeByCode(this.currentReport.getDai_employee_code());
        }
        return Messages.get("daily_report");
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
     * Gets the monthly report.
     *
     * @return the monthly report
     */
    public Monthly_report_revision getMonthlyReport() {
        return this.monthlyReport;
    }

    /**
     * Sets the monthly report.
     *
     * @param monthlyReport the new monthly report
     */
    public void setMonthlyReport(final Monthly_report_revision monthlyReport) {
        this.monthlyReport = monthlyReport;
    }

    /**
     * Checks if is disabled register daily report when monthly report was created.
     *
     * @return true, if is disabled register daily report when monthly report was created
     */
    public boolean isDisabledRegisterDailyReportWhenMonthlyReportWasCreated() {
        if (this.monthlyReport == null) {
            this.monthlyReport =
                this.dailyReportService.getMonthlyReportByEmpCodeAndTimeOfReport(this.userCredential.getUserEmployee()
                    .getEmp_code(), DateUtils.getMonth(this.currentReport.getDai_work_date()), DateUtils
                    .getYear(this.currentReport.getDai_work_date()));
        }
        return (this.monthlyReport != null) && !this.monthlyReport.isOpenStatus();
    }

    /**
     * Change employee and department name.
     */
    public void changeEmployeeAndDepartmentName() {
        final FacesContext context = FacesContext.getCurrentInstance();
        final Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String data = map.get("data");
        System.out.println(data);
        try {
            Customer_info_view obj = JsonUtils.toEntity(data, Customer_info_view.class);
            this.currentReport.setDai_compemp_name(obj.getDai_compemp_name());
            this.currentReport.setDai_employee_post(obj.getDai_employee_post());

        } catch (final IOException e) {
            this.log.debug(e.getMessage());
        }
    }

    public void changeValueEmployeeNameListener(final AjaxBehaviorEvent event) {
        this.currentReport.setDai_compemp_name(((UIOutput) event.getSource()).getValue().toString());
    }

    public void changeValueDepartmentNameListener(final AjaxBehaviorEvent event) {
        this.currentReport.setDai_employee_post(((UIOutput) event.getSource()).getValue().toString());
    }

    /**
     * Change company.
     */
    public void changeCompany() {
        final FacesContext context = FacesContext.getCurrentInstance();
        final Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String data = map.get("data");
        try {
            Company_mst_suggest obj = JsonUtils.toEntity(data, Company_mst_suggest.class);
            this.setCompanyName(obj.getCom_company_name());
            Company_mst com = this.dailyReportService.getActiveCompanyByCode(obj.getCom_company_code());
            if (com != null) {
                this.currentReport.setCompany_mst(com);
                this.currentReport.setDai_comppoint_code(com.getCom_point_code());
            }

        } catch (final IOException e) {
            this.log.debug(e.getMessage());
        }
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    private String companyName;

    private List<Addresspoint_mst> allAddrsesspoints;

    @Inject
    private AddressPointService addressService;

    /**
     * Gets the all branch.
     *
     * @return the all branch
     */
    public List<Addresspoint_mst> getAllBranch() {
        if (this.allAddrsesspoints == null) {
            ServiceResult<List<Addresspoint_mst>> allAddressResult = this.addressService.getAllAddressPoints();
            if (allAddressResult.isSuccessful()) {
                this.allAddrsesspoints = allAddressResult.getData();
            }
        }
        return this.allAddrsesspoints;


    }
}
