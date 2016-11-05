package arrow.businesstraceability.control.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.control.bean.RegisterActivityHistorySearchBean;
import arrow.businesstraceability.control.exception.TransactionRollbackException;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.helper.ServiceSearchResult;
import arrow.businesstraceability.control.service.NotificationService.RegisterActivityEvent;
import arrow.businesstraceability.persistence.dto.Daily_report_DTO;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Basepoint_info;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Daily_activity_type;
import arrow.businesstraceability.persistence.entity.Daily_report;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Industry_big_mst;
import arrow.businesstraceability.persistence.entity.Monthly_report_revision;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.businesstraceability.persistence.repository.Basepoint_infoRepository;
import arrow.businesstraceability.persistence.repository.Company_mstRepository;
import arrow.businesstraceability.persistence.repository.Daily_activity_typeRepository;
import arrow.businesstraceability.persistence.repository.Daily_reportRepository;
import arrow.businesstraceability.persistence.repository.Employee_mstRepository;
import arrow.businesstraceability.persistence.repository.Industry_big_mstRepository;
import arrow.businesstraceability.persistence.repository.Monthly_report_revisionRepository;
import arrow.businesstraceability.persistence.repository.RepoResult;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.util.DateUtils;
import arrow.framework.util.StringUtils;

/**
 * The Class DailyReportService.
 */
@Named
@ConversationScoped
public class DailyReportService extends AbstractService {

    /** The event src. */
    @Inject
    private Event<RegisterActivityEvent> eventSrc;

    /** The address repo. */
    @Inject
    private Addresspoint_mstRepository addressRepo;

    /** The employee repo. */
    @Inject
    private Employee_mstRepository employeeRepo;

    /** The daily_activity_type repo. */
    @Inject
    private Daily_activity_typeRepository dailyActivityTypeRe;

    @Inject
    private Company_mstRepository companyRepo;

    @Inject
    private Industry_big_mstRepository industryBigMstRepo;

    @Inject
    private Monthly_report_revisionRepository monthlyReportRepo;

    /**
     * The daily report repo.
     */
    @Inject
    private Daily_reportRepository dailyreportRepo;

    @Inject
    private Basepoint_infoRepository basepointRepo;

    /**
     * Create an empty report for selectedDate.
     *
     * @param selectedDate the selected date
     * @return the service result
     * @see Refresh Daily Report form
     */
    public ServiceResult<Daily_report_DTO> addNewReport(final Date selectedDate) {
        final Daily_report_DTO newReport = new Daily_report_DTO();
        newReport.setDai_work_type(true);
        newReport.setDai_rimaindar_flg(false);
        newReport.setDai_anken_flg(false);
        newReport.setDai_matter_flg(false);
        newReport.setDai_work_date(selectedDate);
        return new ServiceResult<Daily_report_DTO>(true, newReport, new ArrayList<Message>());
    }

    /**
     * Gets the list report.
     *
     * @param currentDate the current date
     * @param currentEmployeeCode the current_employee code
     * @return the list report
     */
    public List<Daily_report> getListReport(final Date currentDate, final int currentEmployeeCode) {
        return this.dailyreportRepo.getReportByWorkDateAndEmployeeCode(DateUtils.removeTime(currentDate),
            currentEmployeeCode).getResultList();
    }

    /**
     * Gets the activity types.
     *
     * @return the activity types
     */
    public List<Daily_activity_type> getActivityTypes() {
        return this.dailyActivityTypeRe.getAllDailyActivityTypeAndOrderByDatCodeAsc().getResultList();
    }

    /**
     * Delete report.
     *
     * @param selectedReports the selected reports
     * @return the service result
     */
    public ServiceResult<Daily_report> deleteReport(final Daily_report selectedReports) {
        try {
            final Daily_report result = this.dailyreportRepo.findBy(selectedReports.getPk());
            this.dailyreportRepo.removeAndFlush(result);
            this.eventSrc.fire(new RegisterActivityEvent("afterClickDeleteDailyReport", selectedReports));
        } catch (final PersistenceException e) {
            super.log.debug(e);
            return new ServiceResult<>(false, Arrays.asList(ErrorMessage.daily_002_delete_unsucessfully()));
        }
        return new ServiceResult<Daily_report>(true, selectedReports, new ArrayList<Message>());
    }

    /**
     * Delete report.
     *
     * @param selectedReports the selected reports
     * @return the service result
     */
    public ServiceResult<Daily_report> deleteReport(final List<Daily_report> selectedReports) {
        for (Daily_report report : selectedReports) {
            this.deleteReport(report);
        }
        return new ServiceResult<Daily_report>(true, new ArrayList<Message>());
    }

    /**
     * Check overlap case add new.
     *
     * @param currentReport the current report
     * @return true, if successful
     */
    private boolean checkOverlapCaseAddNew(final Daily_report currentReport) {
        long result =
            this.dailyreportRepo.countDailyReportOverlappedCaseAddNew(currentReport.getDai_employee_code(),
                currentReport.getDai_point_code(), currentReport.getDai_work_date(), currentReport.getDai_work_stime(),
                currentReport.getDai_work_etime());
        return result != 0;
    }

    /**
     * Check overlap case edit.
     *
     * @param currentReport the current report
     * @param oldSelectedReportToEdit the old selected report to edit
     * @return true, if successful
     */
    private boolean checkOverlapCaseEdit(final Daily_report currentReport, final Daily_report oldSelectedReportToEdit) {
        long result =
            this.dailyreportRepo.countDailyReportOverlappedCaseEdit(oldSelectedReportToEdit.getDai_employee_code(),
                oldSelectedReportToEdit.getDai_point_code(), oldSelectedReportToEdit.getDai_work_date(),
                oldSelectedReportToEdit.getDai_work_stime(), oldSelectedReportToEdit.getDai_work_etime(),
                currentReport.getDai_work_date(), currentReport.getDai_work_stime(), currentReport.getDai_work_etime());
        return result != 0;

    }

    /**
     * Validate daily report.
     *
     * @param reportDTO the report dto
     * @return the list
     */
    private List<Message> validateDailyReport(final Daily_report_DTO reportDTO) {
        final List<Message> errors = new ArrayList<>();

        if ((reportDTO.getDai_work_stime() == null) || (reportDTO.getDai_work_etime() == null)) {
            errors.add(ErrorMessage.daily_014_working_time_is_required());
            return errors;
        }

        if (DateUtils.timeAfter(reportDTO.getDai_work_stime(), reportDTO.getDai_work_etime())) {
            errors.add(ErrorMessage.daily_004_the_start_job_time_cannot_greater_end_job_time());
            return errors;
        }

        if (!reportDTO.isOtherWork()) {
            if (reportDTO.getDai_rimaindar_flg()) {
                if ((reportDTO.getDai_summary_stime() == null) || (reportDTO.getDai_summary_etime() == null)) {
                    errors.add(ErrorMessage.daily_013_start_time_and_end_time_are_required());
                } else if (this.hasStartTimeEqualsOrGreaterThanEndTime(reportDTO)) {
                    errors.add(ErrorMessage.daily_011_the_start_time_reminder_cannot_greater_end_time_reminder());
                }
            }
            if (reportDTO.getDaily_activity_type() == null) {
                errors.add(ErrorMessage.daily_016_purpose_is_required());
            }

            if (reportDTO.getDai_bus_code() == null) {
                errors.add(ErrorMessage.daily_017_business_type_is_required());
            }
        }

        if (StringUtils.isEmpty(reportDTO.getDai_business_details_DIRECT())) {
            errors.add(ErrorMessage.daily_006_content_is_required());
        }

        return errors;
    }

    /**
     * Checks for start time equals or greater than end time.
     *
     * @param reportDTO the report dto
     * @return true, if successful
     */
    private boolean hasStartTimeEqualsOrGreaterThanEndTime(final Daily_report_DTO reportDTO) {
        return DateUtils.compareDate(reportDTO.getDai_summary_stime(), reportDTO.getDai_summary_etime()) >= 0;
    }

    /**
     * Save when edit report.
     *
     * @param reportDTO the report dto
     * @return the service result
     */
    public ServiceResult<Daily_report> saveWhenEditReport(final Daily_report_DTO reportDTO) {

        final List<Message> errors = this.validateDailyReport(reportDTO);

        if (!errors.isEmpty()) {
            return new ServiceResult<>(false, errors);
        }
        reportDTO.adjustData();
        // edit case
        if (!reportDTO.isPersisted()) {
            errors.add(ErrorMessage.daily_009_report_does_not_exist_please_add_new_report());
            return new ServiceResult<>(false, errors);
        }

        if (reportDTO.getDai_work_type()) {
            ServiceResult<Basepoint_info> saveBasepointResult = this.checkAndAddNewBranchIfNotExisted(reportDTO);
            if (saveBasepointResult.isNotSuccessful()) {
                return new ServiceResult<Daily_report>(false, null, saveBasepointResult.getMessages());
            }
        }

        final Daily_report persistedReport = this.dailyreportRepo.findBy(reportDTO.getPk());

        if (persistedReport == null) {
            errors.add(ErrorMessage.daily_008_daily_report_not_found());
            return new ServiceResult<>(false, errors);
        }

        final Daily_report newDailyReport =
            new Daily_report(persistedReport.getEmployee_mst(), persistedReport.getAddresspoint_mst(),
                reportDTO.getDai_work_date(), DateUtils.removeDate(reportDTO.getDai_work_stime()),
                DateUtils.removeDate(reportDTO.getDai_work_etime()));
        BeanCopier.copy(reportDTO, newDailyReport);
        if (reportDTO.getDai_work_type()) {
            reportDTO.setIndustry_big_mst(Industry_big_mst.find(reportDTO.getDai_bus_code()));
        } else {
            newDailyReport.setDai_required_time(reportDTO.getDai_required_time_DIRECT());
            newDailyReport.setDai_business_details(reportDTO.getDai_business_details());
        }

        if (this.checkOverlapCaseEdit(reportDTO, persistedReport)) {
            errors.add(ErrorMessage.daily_007_period_is_overlapped_with_existing_data());
            return new ServiceResult<>(false, persistedReport, errors);
        }

        // Remove old data
        ServiceResult<Daily_report> deletePersistedReport = this.deleteReport(persistedReport);
        if (deletePersistedReport.isSuccessful()) {
            // And add new data
            this.dailyreportRepo.saveAndFlush(newDailyReport);

            this.eventSrc.fire(new RegisterActivityEvent("afterClickSaveDailyReportWhenEdit", newDailyReport));
            errors.add(InfoMessage.daily_001_save_successfully());
            return new ServiceResult<>(true, newDailyReport, errors);
        } else {
            return new ServiceResult<>(false, newDailyReport, deletePersistedReport.getMessages());
        }
    }

    /**
     * Save when add new report.
     *
     * @param reportDTO the report dto
     * @param currentEmployee the current employee
     * @param addrPointOfCurrentEmp the addr point of current emp
     * @return the service result
     */
    public ServiceResult<Daily_report> saveWhenAddNewReport(final Daily_report_DTO reportDTO,
        final Employee_mst currentEmployee, final Addresspoint_mst addrPointOfCurrentEmp) {

        final List<Message> errors = this.validateDailyReport(reportDTO);
        if (!errors.isEmpty()) {
            return new ServiceResult<>(false, errors);
        }
        reportDTO.adjustData();
        // If you are editing case
        if (reportDTO.isPersisted()) {
            if (!this.isValidReportWithPK(reportDTO)) {
                errors.add(ErrorMessage.daily_007_period_is_overlapped_with_existing_data());
                return new ServiceResult<>(false, errors);
            }
        }
        return this.saveDailyReport(currentEmployee, reportDTO);
    }

    /**
     * Save daily report.
     *
     * @param currentEmployee the current employee
     * @param reportDTO the report dto
     * @return the service result
     * @throws TransactionRollbackException the transaction rollback exception
     */
    private ServiceResult<Daily_report> saveDailyReport(final Employee_mst currentEmployee,
        final Daily_report_DTO reportDTO) {
        if (reportDTO.getDai_work_type()) {
            ServiceResult<Basepoint_info> saveBasepointResult = this.checkAndAddNewBranchIfNotExisted(reportDTO);
            if (saveBasepointResult.isNotSuccessful()) {
                return new ServiceResult<Daily_report>(false, null, saveBasepointResult.getMessages());
            }
        }
        List<Message> errors = new ArrayList<Message>();
        final Daily_report newDailyReport =
            new Daily_report(currentEmployee, currentEmployee.getAddresspoint_mst(), reportDTO.getDai_work_date(),
                DateUtils.removeDate(reportDTO.getDai_work_stime()),
                DateUtils.removeDate(reportDTO.getDai_work_etime()));
        if (!reportDTO.isOtherWork()) {
            reportDTO.setIndustry_big_mst(Industry_big_mst.find(reportDTO.getDai_bus_code()));
        }
        BeanCopier.copy(reportDTO, newDailyReport);
        if (this.checkOverlapCaseAddNew(newDailyReport)) {
            errors.add(ErrorMessage.daily_007_period_is_overlapped_with_existing_data());
            return new ServiceResult<>(false, newDailyReport, errors);
        }
        this.dailyreportRepo.saveAndFlush(newDailyReport);
        this.eventSrc.fire(new RegisterActivityEvent("afterClickSaveDailyReportWhenAddNew", newDailyReport));
        errors.add(InfoMessage.daily_001_save_successfully());
        return new ServiceResult<Daily_report>(true, newDailyReport, errors);
    }

    private ServiceResult<Basepoint_info> checkAndAddNewBranchIfNotExisted(final Daily_report_DTO reportDTO) {
        try {
            final Basepoint_info basepointInDB =
                this.basepointRepo.findBasepoinByCompanyCodeAndBranchCode(reportDTO.getDai_company_code(),
                    reportDTO.getDai_comppoint_code()).getAnyResult();
            if (basepointInDB == null) {
                Addresspoint_mst branch =
                    this.addressRepo.findAddresspointByCode(reportDTO.getDai_comppoint_code()).getAnyResult();
                final Basepoint_info newBasepoint = new Basepoint_info(branch, reportDTO.getCompany_mst());
                this.basepointRepo.saveAndFlush(newBasepoint);
            }
            return new ServiceResult<Basepoint_info>(true, InfoMessage.comp_001_save_successfully());
        } catch (final PersistenceException e) {
            this.log.debug(e.getMessage());
            return new ServiceResult<Basepoint_info>(false, ErrorMessage.comp_001_save_unsuccessfully());
        }

    }

    /**
     * Checks if is null list report by pk.
     *
     * @param report the report
     * @return true, if is null list report by pk , and false if not null
     */
    private boolean isValidReportWithPK(final Daily_report_DTO report) {
        Daily_report daily_report = this.dailyreportRepo.findBy(report.getPk());
        return daily_report == null;
    }

    /**
     * Gets the all active employee.
     *
     * @return the all active employee
     */
    public List<Employee_mst> getAllActiveEmployee() {
        return this.employeeRepo.getAllActiveEmpByCondiCodeIsNullOrZero().getResultList();
    }

    /**
     * Gets the all not deleted employee.
     *
     * @return the all not deleted employee
     */
    public List<Employee_mst> getAllNotDeletedEmployee() {
        return this.employeeRepo.getAllActiveEmp().getResultList();
    }

    /**
     * Gets the all address point.
     *
     * @return the all address point
     */
    public List<Addresspoint_mst> getAllAddressPoint() {
        final List<Addresspoint_mst> listAllAddressPoint = this.addressRepo.findAll();
        return listAllAddressPoint;
    }

    /**
     * Gets the list company.
     *
     * @return the list company
     */
    public List<Company_mst> getListCompany() {
        return this.companyRepo.findAll();
    }

    /**
     * Gets the list position employee.
     *
     * @return the list position employee
     */
    public List<Addresspoint_mst> getListPositionEmployee() {
        return this.addressRepo.getListPositionOfEmployee().getResultList();
    }

    /**
     * Gets the list active employee by addr point.
     *
     * @param adpCode the apd_code
     * @param includeRetiredEmployee the include retired employee
     * @return the list active employee by addr point
     */
    public List<Employee_mst> getListActiveEmployeeByAddrPoint(final String adpCode,
        final boolean includeRetiredEmployee) {

        if (!includeRetiredEmployee && StringUtils.isNotEmpty(adpCode)) {
            return this.employeeRepo.getAllActiveEmpNotRetiredByAdpCode(adpCode).getResultList();
        }

        if (includeRetiredEmployee && StringUtils.isNotEmpty(adpCode)) {
            return this.employeeRepo.getAllActiveEmpByAdpCode(adpCode).getResultList();
        }

        if (!includeRetiredEmployee && StringUtils.isEmpty(adpCode)) {
            return this.employeeRepo.getAllActiveEmpNotRetired().getResultList();
        }

        return this.employeeRepo.getAllActiveEmpDistinctJoinWithAddressPoint().getResultList();
    }

    /**
     * Search history daily report.
     *
     * @param searchHistoryBean the search history bean
     * @return the map
     */
    public ServiceSearchResult<Daily_report> searchHistoryDailyReport(
        final RegisterActivityHistorySearchBean searchHistoryBean) {
        RepoResult<Daily_report> result = this.dailyreportRepo.searchHistoryDailyReport(searchHistoryBean);
        return new ServiceSearchResult<Daily_report>(true, result.getListItems(), result.getTotalItems());
    }

    /**
     * Search employee report.
     *
     * @param selectedDate the selected date
     * @param selectedEmployee the selected employee
     * @return the list
     */
    public List<Daily_report> searchEmployeeReport(final Date selectedDate, final Employee_mst selectedEmployee) {
        if (selectedEmployee != null) {
            return this.dailyreportRepo
                .getReportByWorkDateAndEmployeeCode(selectedDate, selectedEmployee.getEmp_code()).getResultList();
        }
        return this.dailyreportRepo.getReportByWorkDate(selectedDate).getResultList();
    }

    /**
     * Search report by date and emp code.
     *
     * @param selectedEmpCode the selected emp code
     * @param selectedDate the selected date
     * @return the list
     */
    public List<Daily_report> searchReportByDateAndEmpCode(final int selectedEmpCode, final Date selectedDate) {
        return this.dailyreportRepo.getReportByWorkDateAndEmployeeCode(selectedDate, selectedEmpCode).getResultList();
    }

    /**
     * Gets the employee by addr and retired condition.
     *
     * @param addressPoint the address point
     * @return the employee by addr and retired condition
     */
    public List<Employee_mst> getEmployeeByAddrAndRetiredCondition(final String addressPoint) {
        return this.employeeRepo.getAllActiveEmpNotRetiredByAdpCode(addressPoint).getResultList();
    }

    /**
     * Gets the employee by addr.
     *
     * @param selectAddressPoint the select address point
     * @return the employee by addr
     */
    public List<Employee_mst> getEmployeeByAddr(final String selectAddressPoint) {
        return this.employeeRepo.getAllActiveEmpByAdpCode(selectAddressPoint).getResultList();
    }

    /**
     * Gets the list addr contraint employee.
     *
     * @return the list addr contraint employee
     */
    public List<Addresspoint_mst> getListAddrContraintEmployee() {
        return this.addressRepo.getAllAddressPointJoinWithEmployee().getResultList();
    }

    /**
     * Gets the all industry big mst.
     *
     * @return the all industry big mst
     */
    public List<Industry_big_mst> getAllIndustryBigMST() {
        return this.industryBigMstRepo.getAllIndustryBigMst().getResultList();
    }

    /**
     * Gets the lastest buscode.
     *
     * @param currentEmployeeCode the current_employee code
     * @return the lastest buscode
     */
    public Short getLastestBuscode(final int currentEmployeeCode) {
        Daily_report reportLatestUpdated =
            this.dailyreportRepo.getReportLatestUpdatedByEmpCode(currentEmployeeCode).getAnyResult();
        if (reportLatestUpdated != null) {
            return reportLatestUpdated.getDai_bus_code();
        }
        return null;
    }

    /**
     * Gets the employee by emp code.
     *
     * @param empCode the emp code
     * @return the employee by emp code
     */
    public Employee_mst getEmployeeByEmpCode(final int empCode) {
        return this.employeeRepo.findEmployeeByCode(empCode).getAnyResult();
    }

    public Monthly_report_revision getMonthlyReportByEmpCodeAndTimeOfReport(final int empCode, final int month,
        final int year) {
        return this.monthlyReportRepo
            .getActiveMonthlyReportByEmpCodeAndTimeOfReportOrderByVersion(empCode, year, month).getAnyResult();
    }

    public int getCountDailyReportByEmpCodeAndAddressCodeAndEmpNameAndEmpPost(final String dai_company_code,
        final String dai_comppoint_code, final String dai_compemp_name, final String string) {
        return this.dailyreportRepo.getCountDailyReportByEmpCodeAndAddressCodeAndEmpNameAndEmpPost(dai_company_code,
            dai_comppoint_code, dai_compemp_name, string);
    }

    public Company_mst getActiveCompanyByCode(final String companyCode) {
        return this.companyRepo.findActiveCompanyByCompanyCode(companyCode).getAnyResult();
    }


}
