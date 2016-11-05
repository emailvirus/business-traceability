package arrow.businesstraceability.control.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.cache.entity.ApprovalAndSubmissionInfo;
import arrow.businesstraceability.cache.entity.PurposeCellData;
import arrow.businesstraceability.cache.entity.VisitTimesCellData;
import arrow.businesstraceability.constant.AuthorityLevels;
import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.constant.SummaryReportConstants;
import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.export.base.AbstractExportExcel;
import arrow.businesstraceability.export.factory.AbstractExportFactory;
import arrow.businesstraceability.export.factory.ExportFileFactory;
import arrow.businesstraceability.export.param.ReportParameter;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Branch_position;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Daily_activity_type;
import arrow.businesstraceability.persistence.entity.Daily_report;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Industry_big_mst;
import arrow.businesstraceability.persistence.entity.Monthly_report_history;
import arrow.businesstraceability.persistence.entity.Monthly_report_revision;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.businesstraceability.persistence.repository.Branch_positionRepository;
import arrow.businesstraceability.persistence.repository.Company_mstRepository;
import arrow.businesstraceability.persistence.repository.Daily_reportRepository;
import arrow.businesstraceability.persistence.repository.Monthly_report_historyRepository;
import arrow.businesstraceability.persistence.repository.Monthly_report_revisionRepository;
import arrow.businesstraceability.util.ExportExcelUtils;
import arrow.framework.exception.ArrException;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.faces.util.FaceletUtils;
import arrow.framework.faces.util.LabelKeySelectItem;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DateUtils;
import arrow.framework.util.StringUtils;
import arrow.framework.util.collections.CollectionUtils;
import arrow.framework.util.i18n.Messages;

/**
 * The Class SummaryReportService.
 */
@ConversationScoped
public class SummaryReportService extends AbstractService {


    /** The Constant TOTAL_VISIT_TIMES. */
    private static final String TOTAL_VISIT_TIMES = "totalVisitTimes";

    /** The Constant WORKING_DAYS. */
    private static final String WORKING_DAYS = "workingDays";

    /** The Constant COMPANY_WITH_VISIT_TIMES. */
    private static final String COMPANY_WITH_VISIT_TIMES = "companyWithVisitTimes";

    /** The Constant PURPOSE. */
    private static final String PURPOSE = "purpose";

    /** The Constant VISIT_TIMES_DATA. */
    private static final String VISIT_TIMES_DATA = "visitTimesData";

    /** The Constant CREATOR. */
    private static final String CREATOR = "creator";

    /** The Constant POSITION. */
    private static final String POSITION = "position";

    /** The Constant BRANCH_NAME. */
    private static final String BRANCH_NAME = "branchName";

    /** The Constant REPORT_MONTH. */
    private static final String END_DATE = "endDate";

    /** The Constant REPORT_YEAR. */
    private static final String START_DATE = "startDate";

    /** The company_mst repository. */
    @Inject
    private Company_mstRepository company_mstRepository;

    /** The daily repo. */
    @Inject
    private Daily_reportRepository dailyRepo;

    /** The address repo. */
    @Inject
    private Addresspoint_mstRepository addressRepo;

    /** The monthly_report repository. */
    @Inject
    private Monthly_report_revisionRepository monthly_reportRepository;

    /** The monthly_report_history repository. */
    @Inject
    private Monthly_report_historyRepository monthly_report_historyRepository;

    /** The employee_mst repo. */
    @Inject
    private Branch_positionRepository branchPositionRepo;

    /** The Constant TEMPLATE_DIR. */
    private static final String TEMPLATE_DIR = "/excel-template/";

    /** The Constant STATISTIC_REPORT_TEMPLATE. */
    private static final String STATISTIC_REPORT_TEMPLATE = "statistic_report.xls";

    /** The Constant PERIODIC_REPORT_NAME. */
    private static final String PERIODIC_REPORT_NAME = "periodic_report.xls";

    /** The Constant ALL_BRANCH. */
    private static final String ALL_BRANCH = "allBranches";

    /** The Constant BRANCHES. */
    private static final Map<String, String> BRANCHES = new HashMap<String, String>();

    /** The Constant COMPANY_BRANCH_KEY_PATTERN. */
    private static final String COMPANY_BRANCH_KEY_PATTERN = "%s_%s";

    /** The Constant COMPANY_BRANCH_PURPOSE_KEY_PATTERN. */
    private static final String COMPANY_BRANCH_PURPOSE_KEY_PATTERN = "%s_%s_%d";

    /** The Constant COMPANY_BRANCH_EMPLOYEE_KEY_PATTERN. */
    private static final String COMPANY_BRANCH_EMPLOYEE_KEY_PATTERN = "%s_%s_%d";

    /** The Constant COMPANY_BRANCH_EMPLOYEE_PURPOSE_KEY_PATTERN. */
    private static final String COMPANY_BRANCH_EMPLOYEE_PURPOSE_KEY_PATTERN = "%s_%s_%d_%d";

    /** The Constant BRANCH_BUSINESS_KEY_PATTERN. */
    private static final String BRANCH_BUSINESS_KEY_PATTERN = "%s_%d";

    /** The Constant BRANCH_PURPOSE_KEY_PATTERN. */
    private static final String BRANCH_PURPOSE_KEY_PATTERN = "%s_%d";

    /** The Constant BRANCH_EMPLOYEE_KEY_PATTERN. */
    private static final String BRANCH_EMPLOYEE_KEY_PATTERN = "%s_%d";

    /** The Constant BRANCH_EMPLOYEE_BUSINESS_KEY_PATTERN. */
    private static final String BRANCH_EMPLOYEE_BUSINESS_KEY_PATTERN = "%s_%d_%d";

    /** The Constant BRANCH_EMPLOYEE_PURPOSE_KEY_PATTERN. */
    private static final String BRANCH_EMPLOYEE_PURPOSE_KEY_PATTERN = "%s_%d_%d";

    /** The Constant BRANCH_COMPANY_KEY_PATTERN. */
    private static final String BRANCH_COMPANY_KEY_PATTERN = "%s_%s";

    /** The Constant BRANCH_EMPLOYEE_COMPANY_KEY_PATTERN. */
    private static final String BRANCH_EMPLOYEE_COMPANY_KEY_PATTERN = "%s_%d_%s";

    /** The Constant MAX_VISITED_COMPANIES_RECORD. */
    private static final int MAX_VISITED_COMPANIES_RECORD = 20;

    static {
        SummaryReportService.BRANCHES.put("00", "本社");
        SummaryReportService.BRANCHES.put("01", "北海道支店");
        SummaryReportService.BRANCHES.put("04", "東北支店");
        SummaryReportService.BRANCHES.put("13", "東京支店");
        SummaryReportService.BRANCHES.put("14", "横浜営業所");
        SummaryReportService.BRANCHES.put("23", "名古屋支店");
        SummaryReportService.BRANCHES.put("27", "大阪支店");
        SummaryReportService.BRANCHES.put("34", "広島営業所");
        SummaryReportService.BRANCHES.put("40", "福岡支店");
    }

    /** The map branch vs visit times. */
    // BranchCode => Visite Times
    Map<String, Integer> mapBranchVsVisitTimes = new HashMap<>();

    /** The map branch business type vs visit times. */
    // BranchCode_BusinessTypeCode => Visit Times
    Map<String, Integer> mapBranchBusinessTypeVsVisitTimes = new HashMap<>();

    /** The map branch purpose vs visit times. */
    // BranchCode_Purpose => Visit Times
    Map<String, Integer> mapBranchPurposeVsVisitTimes = new HashMap<>();

    /** The map branch employee vs visit times. */
    // BranchCode_EmployeeCode => Visit TImes
    Map<String, Integer> mapBranchEmployeeVsVisitTimes = new HashMap<>();

    /** The map branch employee business vs visit times. */
    // BranchCode_EmployeeCode_BusinessType => Visit Times
    Map<String, Integer> mapBranchEmployeeBusinessVsVisitTimes = new HashMap<>();

    /** The map branch employee purpose vs visit times. */
    // BranchCode_EmployeeCode_Purpose => Visit Times
    Map<String, Integer> mapBranchEmployeePurposeVsVisitTimes = new HashMap<>();

    /** The map branch company vs visit times. */
    // BranchCode_Company => Visit Times
    Map<String, Integer> mapBranchCompanyVsVisitTimes = new HashMap<>();

    /** The map branch employee company vs visit times. */
    // BranchCode_Employee_Company => Visit Times
    Map<String, Integer> mapBranchEmployeeCompanyVsVisitTimes = new HashMap<>();

    /** The map branch vs list employee. */
    Map<String, List<Integer>> mapBranchVsListEmployee = new HashMap<>();

    /** The reported companies. */
    List<String> reportedCompanies = new ArrayList<>();

    /** The addresspoint service. */

    /** The map company branch vs list of purposes. */
    // CompanyCode_BranchCode => List of Purpose
    private Map<String, List<Short>> mapCompanyBranchVsListOfPurposes;

    /** The map company branch vs list employee. */
    // CompanyCode_BranchCode => List Employee
    private Map<String, List<Integer>> mapCompanyBranchVsListEmployee;

    /** The map company branch vs visit times. */
    // CompanyCode_BranchCode => Visit Times
    private Map<String, Integer> mapCompanyBranchVsVisitTimes;

    /** The map company branch purpose vs visit times. */
    // CompanyCode_BranchCode_Purpose => Visit Times
    private Map<String, Integer> mapCompanyBranchPurposeVsVisitTimes;

    /** The map company branch employee vs visit times. */
    // CompanyCode_BranchCode_EmployeeCode => List Purpose
    private Map<String, Integer> mapCompanyBranchEmployeeVsVisitTimes;

    /** The map company branch employee purpose vs visit times. */
    // CompanyCode_BranchCode_EmployeeCode_Purpose => Visit Times
    private Map<String, Integer> mapCompanyBranchEmployeePurposeVsVisitTimes;

    /**
     * sort map.
     *
     * @param <K> the key type
     * @param map the map
     * @return the map
     */
    public static <K> Map<K, Integer> sortMap(final Map<K, Integer> map) {
        final List<Map.Entry<K, Integer>> entries = new LinkedList<Map.Entry<K, Integer>>(map.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<K, Integer>>() {
            @Override
            public int compare(final Entry<K, Integer> o1, final Entry<K, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        final Map<K, Integer> sortedMap = new LinkedHashMap<K, Integer>();
        for (final Map.Entry<K, Integer> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }


    /**
     * Populate data for visit time report.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param companyCodes the company codes
     */
    public void populateDataForVisitTimeReport(final Date startDate, final Date endDate,
            final List<String> companyCodes) {

        // reset
        this.mapCompanyBranchVsListEmployee = new HashMap<>();
        this.mapCompanyBranchVsListOfPurposes = new HashMap<>();
        this.mapCompanyBranchVsVisitTimes = new HashMap<>();
        this.mapCompanyBranchPurposeVsVisitTimes = new HashMap<>();
        this.mapCompanyBranchEmployeePurposeVsVisitTimes = new HashMap<>();
        this.mapCompanyBranchEmployeeVsVisitTimes = new HashMap<>();
        // init
        for (final String companyCode : companyCodes) {
            for (final String branchCode : SummaryReportService.BRANCHES.keySet()) {
                final String key =
                        String.format(SummaryReportService.COMPANY_BRANCH_KEY_PATTERN, companyCode, branchCode);
                this.mapCompanyBranchVsListEmployee.put(key, new ArrayList<Integer>());
                this.mapCompanyBranchVsListOfPurposes.put(key, new ArrayList<Short>());
                this.mapCompanyBranchVsVisitTimes.put(key, 0);
            }
        }

        final List<Daily_report> listDailyReport = this.getListVisited(startDate, endDate, companyCodes);

        for (final Daily_report daily : listDailyReport) {
            final String companyCode = daily.getDai_company_code();
            String branchCode = daily.getDai_point_code();

            final int employeeCode = daily.getDai_employee_code();
            final boolean isHQ = daily.getEmployee_mst().getEmp_settle_authority() == AuthorityLevels.HEAD_QUARTER;
            if (isHQ) {
                branchCode = SummaryReportConstants.HQ_CODE;
            }
            final String companyBranchKey =
                    String.format(SummaryReportService.COMPANY_BRANCH_KEY_PATTERN, companyCode, branchCode);

            if (this.mapCompanyBranchVsListEmployee.containsKey(companyBranchKey)) {
                if (!this.mapCompanyBranchVsListEmployee.get(companyBranchKey).contains(employeeCode)) {
                    this.mapCompanyBranchVsListEmployee.get(companyBranchKey).add(employeeCode);
                }
            }
            if (this.mapCompanyBranchVsListOfPurposes.containsKey(companyBranchKey)) {
                if (!this.mapCompanyBranchVsListOfPurposes.get(companyBranchKey)
                        .contains(daily.getDai_work_tancode())) {
                    this.mapCompanyBranchVsListOfPurposes.get(companyBranchKey).add(daily.getDai_work_tancode());
                }
            }
            if (this.mapCompanyBranchVsVisitTimes.containsKey(companyBranchKey)) {
                this.mapCompanyBranchVsVisitTimes.put(companyBranchKey,
                        this.mapCompanyBranchVsVisitTimes.get(companyBranchKey) + 1);
            }
            else {
                this.mapCompanyBranchVsVisitTimes.put(companyBranchKey, 0);
            }

            final String companyBranchPurposeKey =
                    String.format(SummaryReportService.COMPANY_BRANCH_PURPOSE_KEY_PATTERN, companyCode, branchCode,
                            daily.getDai_work_tancode());
            this.updateCountersOfMap(this.mapCompanyBranchPurposeVsVisitTimes, companyBranchPurposeKey);

            final String companyBranchEmployeeKey = String.format(
                    SummaryReportService.COMPANY_BRANCH_EMPLOYEE_KEY_PATTERN, companyCode, branchCode, employeeCode);
            this.updateCountersOfMap(this.mapCompanyBranchEmployeeVsVisitTimes, companyBranchEmployeeKey);

            final String companyBranchEmployeePurposeKey =
                    String.format(SummaryReportService.COMPANY_BRANCH_EMPLOYEE_PURPOSE_KEY_PATTERN, companyCode,
                            branchCode, employeeCode, daily.getDai_work_tancode());
            this.updateCountersOfMap(this.mapCompanyBranchEmployeePurposeVsVisitTimes, companyBranchEmployeePurposeKey);
        }

    }


    /**
     * get Export excel.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param branchCode the branch code
     * @param selectedEmployee the selected employee
     * @param monthlyReportRevision the monthly report revision
     * @return file name
     * @throws URISyntaxException the URI syntax exception
     * @throws InvalidFormatException the invalid format exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Workbook generateMonthlyReport(final Date startDate, final Date endDate, final String branchCode,
            final Employee_mst selectedEmployee, final Monthly_report_revision monthlyReportRevision)
                    throws URISyntaxException, InvalidFormatException, IOException {

        final AbstractExportFactory exportFactory =
                ExportFileFactory.getFactory(SummaryReportConstants.PeriodicReport.PERIODIC_REPORT_TYPE);
        final ReportParameter param = new ReportParameter(startDate, endDate, branchCode,
                String.valueOf(selectedEmployee.getEmp_code()), monthlyReportRevision);
        final AbstractExportExcel exportExcel = exportFactory.createReport(param);
        return exportExcel.createContent();
    }

    /**
     * firt class.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param companies the companies
     * @return the workbook
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InvalidFormatException the invalid format exception
     */
    public Workbook generateVisitTimeReport(final Date startDate, final Date endDate, final List<Company_mst> companies)
            throws IOException, InvalidFormatException {
        AbstractExportFactory factory =
                ExportFileFactory.getFactory(SummaryReportConstants.VisitTimeReport.VISIT_TIME_REPORT_TYPE);
        ReportParameter param = new ReportParameter(startDate, endDate, companies);
        // set param
        return factory.createReport(param).createContent();
    }

    /**
     * get list all branch.
     *
     * @author lehoangngochan
     * @return list select Item
     */
    public List<SelectItem> getListSelectItemAllBranch() {
        final List<SelectItem> selectItemAllBranch = new ArrayList<SelectItem>();
        final List<Addresspoint_mst> listAddress = new ArrayList<Addresspoint_mst>();
        for (final Employee_mst emp : this.listAllBranch()) {
            if (!listAddress.contains(emp.getAddresspoint_mst())) {
                listAddress.add(emp.getAddresspoint_mst());
            }
        }

        if (listAddress.size() > 0) {
            selectItemAllBranch.add(
                    new LabelKeySelectItem(StringUtils.EMPTY_STRING, Messages.get(SummaryReportService.ALL_BRANCH)));
            selectItemAllBranch
                    .add(new LabelKeySelectItem(SummaryReportConstants.HQ_CODE, SummaryReportConstants.HQ_NAME_KEY));
            for (final Addresspoint_mst add : listAddress) {
                selectItemAllBranch.add(new SelectItem(add.getAdp_code(), add.getAdp_name_DIRECT()));
            }
        }

        return selectItemAllBranch;
    }

    /**
     * get list employee.
     *
     * @author lehoangngochan
     * @param branch the branch
     * @return list select Item
     */
    public List<SelectItem> getListSelectItemEmployee(final String branch) {
        final List<SelectItem> selectItemAllEmployee = new ArrayList<SelectItem>();
        final List<Employee_mst> listEmp = this.listEmployeeByBranch(branch);
        if (listEmp.size() > 0) {
            if (!SummaryReportConstants.HQ_CODE.equals(branch)) {
                selectItemAllEmployee.add(new LabelKeySelectItem(StringUtils.EMPTY_STRING, StringUtils.EMPTY_STRING));
            }
            for (final Employee_mst emp : listEmp) {
                selectItemAllEmployee.add(new SelectItem(emp.getEmp_code(), emp.getEmp_name_DIRECT()));
            }
        }

        return selectItemAllEmployee;
    }


    /**
     * Reset maps.
     */
    private void resetMaps() {
        this.mapBranchVsVisitTimes = new HashMap<>();
        this.mapBranchBusinessTypeVsVisitTimes = new HashMap<>();
        this.mapBranchPurposeVsVisitTimes = new HashMap<>();
        this.mapBranchEmployeeVsVisitTimes = new HashMap<>();
        this.mapBranchEmployeeBusinessVsVisitTimes = new HashMap<>();
        this.mapBranchEmployeePurposeVsVisitTimes = new HashMap<>();
        this.mapBranchCompanyVsVisitTimes = new HashMap<>();
        this.mapBranchEmployeeCompanyVsVisitTimes = new HashMap<>();
        this.reportedCompanies = new ArrayList<>();
        this.mapBranchVsListEmployee = new HashMap<>();
    }

    /**
     * Update counters of maps.
     *
     * @param report the report
     */
    private void updateCountersOfMaps(final Daily_report report) {

        final boolean isHQ = AuthorityLevels.HEAD_QUARTER == report.getEmployee_mst().getEmp_settle_authority();
        final String branchCode = isHQ ? SummaryReportConstants.HQ_CODE : report.getDai_point_code();
        if (!this.reportedCompanies.contains(report.getDai_company_code())) {
            this.reportedCompanies.add(report.getCompany_mst().getCom_company_code());
        }
        this.updateCountersOfMap(this.mapBranchVsVisitTimes, branchCode);

        final String branchBusinessKey =
                String.format(SummaryReportService.BRANCH_BUSINESS_KEY_PATTERN, branchCode, report.getDai_bus_code());
        this.updateCountersOfMap(this.mapBranchBusinessTypeVsVisitTimes, branchBusinessKey);

        final String branchPurposeKey = String.format(SummaryReportService.BRANCH_PURPOSE_KEY_PATTERN, branchCode,
                report.getDai_work_tancode());
        this.updateCountersOfMap(this.mapBranchPurposeVsVisitTimes, branchPurposeKey);

        if (!this.mapBranchVsListEmployee.containsKey(branchCode)) {
            this.mapBranchVsListEmployee.put(branchCode, new ArrayList<Integer>());
        }
        final List<Integer> listEmployeesOfBranch = this.mapBranchVsListEmployee.get(branchCode);
        if (!listEmployeesOfBranch.contains(report.getDai_employee_code())) {
            listEmployeesOfBranch.add(report.getDai_employee_code());
        }

        this.mapBranchVsListEmployee.put(branchCode, listEmployeesOfBranch);

        final String branchEmployeeKey = String.format(SummaryReportService.BRANCH_EMPLOYEE_KEY_PATTERN, branchCode,
                report.getDai_employee_code());
        this.updateCountersOfMap(this.mapBranchEmployeeVsVisitTimes, branchEmployeeKey);

        final String branchEmployeeBusinessKey =
                String.format(SummaryReportService.BRANCH_EMPLOYEE_BUSINESS_KEY_PATTERN, branchCode,
                        report.getDai_employee_code(), report.getDai_bus_code());
        this.updateCountersOfMap(this.mapBranchEmployeeBusinessVsVisitTimes, branchEmployeeBusinessKey);

        final String branchEmployeePurposeKey = String.format(SummaryReportService.BRANCH_EMPLOYEE_PURPOSE_KEY_PATTERN,
                branchCode, report.getDai_employee_code(), report.getDai_work_tancode());
        this.updateCountersOfMap(this.mapBranchEmployeePurposeVsVisitTimes, branchEmployeePurposeKey);

        final String branchCompanyKey = String.format(SummaryReportService.BRANCH_COMPANY_KEY_PATTERN, branchCode,
                report.getDai_company_code());
        this.updateCountersOfMap(this.mapBranchCompanyVsVisitTimes, branchCompanyKey);

        final String branchEmployeeCompanyKey = String.format(SummaryReportService.BRANCH_EMPLOYEE_COMPANY_KEY_PATTERN,
                branchCode, report.getDai_employee_code(), report.getDai_company_code());
        this.updateCountersOfMap(this.mapBranchEmployeeCompanyVsVisitTimes, branchEmployeeCompanyKey);
    }

    /**
     * Populate statistic report.
     *
     * @param dailyReports the daily reports
     */
    public void populateStatisticReport(final List<Daily_report> dailyReports) {
        this.resetMaps();
        for (final Daily_report report : dailyReports) {
            this.updateCountersOfMaps(report);
        }
    }

    /**
     * Update counters of map.
     *
     * @param map the map
     * @param key the key
     */
    private void updateCountersOfMap(final Map<String, Integer> map, final String key) {
        Integer visitTimes = map.get(key);
        visitTimes = (visitTimes != null ? visitTimes : 0);
        map.put(key, visitTimes + 1);
    }

    /**
     * Generate statistic report.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param branchCode the branch code
     * @param selectedEmployee the selected employee
     * @param creator the creator
     * @return the service result
     */
    public ServiceResult<Object> generateStatisticReport(final Date startDate, final Date endDate,
            final String branchCode, final Employee_mst selectedEmployee, final String creator) {
        final List<Object[]> listVisitTimes =
                this.getAllVisitInfoFromDailyReportByStartDateAndEndDate(startDate, endDate, selectedEmployee);

        final List<Object[]> listPurpose =
                this.getAllPurposeInfoFromDailyReportByStartDateAndEndDate(startDate, endDate, selectedEmployee);

        final List<Object[]> listCompanyVisited =
                this.getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate(startDate, endDate, selectedEmployee);

        final Addresspoint_mst branch = this.getAddresspointByCode(branchCode);

        final long workingDays =
                this.getWorkingDayOfEmployeeByStartDateAndEndDate(startDate, endDate, selectedEmployee.getEmp_code());

        final long totalVisitTimes = this.countTotalVisitTimesOfEmployeeByStartDateAndEndDate(
                selectedEmployee.getEmp_code(), startDate, endDate);

        try (final InputStream is = SummaryReportService.class.getResourceAsStream(
                SummaryReportService.TEMPLATE_DIR + SummaryReportService.STATISTIC_REPORT_TEMPLATE)) {
            try (final OutputStream os =
                    FaceletUtils.getOutputStreamFromHttpServlet(SummaryReportService.PERIODIC_REPORT_NAME)) {
                Context context = new Context();

                // Data for header
                context.putVar(SummaryReportService.START_DATE, DateUtils.formatDate(startDate));
                context.putVar(SummaryReportService.END_DATE, DateUtils.formatDate(endDate));
                context.putVar(SummaryReportService.BRANCH_NAME, branch.getAdp_name());
                context.putVar(SummaryReportService.POSITION, selectedEmployee.getPosition_mst().getPos_name());
                context.putVar(SummaryReportService.CREATOR, creator);

                // Data for working days
                context.putVar(SummaryReportService.WORKING_DAYS, workingDays);

                // Data for total visit times
                context.putVar(SummaryReportService.TOTAL_VISIT_TIMES, totalVisitTimes);

                // Data for visited times
                VisitTimesCellData visitTimesData = this.getVisitTimesForCells(listVisitTimes);
                context.putVar(SummaryReportService.VISIT_TIMES_DATA, visitTimesData);

                // Data for purpose
                PurposeCellData purposeData = this.getPurposeForCells(listPurpose);
                context.putVar(SummaryReportService.PURPOSE, purposeData);

                // Data for company
                String companyWithVisitTimes = this.createTextCompanyWithVisitTimes(listCompanyVisited);
                context.putVar(SummaryReportService.COMPANY_WITH_VISIT_TIMES, companyWithVisitTimes);
                JxlsHelper.getInstance().processTemplate(is, os, context);
                FaceletUtils.closeStreamAndForceCompleteResponse();
                return new ServiceResult<Object>(true, new ArrayList<>());
            }
        } catch (IOException ex) {
            super.log.debug(ex);
            return new ServiceResult<>(false, ErrorMessage.sum_007_export_periodic_report_failed());
        }
    }

    /**
     * Count total visit times of employee by start date and end date.
     *
     * @param emp_code the emp_code
     * @param startDate the start date
     * @param endDate the end date
     * @return the long
     */
    private long countTotalVisitTimesOfEmployeeByStartDateAndEndDate(final int emp_code, final Date startDate,
            final Date endDate) {
        Long result = this.dailyRepo.countTotalDailyReportOfEmployeeByStartDateAndEndDate(emp_code, startDate, endDate)
                .getAnyResult();
        return (null != result) ? result : 0;
    }


    /**
     * Gets the working day of employee by start date and end date.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param emp_code the emp_code
     * @return the working day of employee by start date and end date
     */
    private long getWorkingDayOfEmployeeByStartDateAndEndDate(final Date startDate, final Date endDate,
            final int emp_code) {
        Long result = this.dailyRepo.getWorkingDaysOfEmployeeByStartDateAndEndDate(emp_code, startDate, endDate)
                .getAnyResult();
        return (null != result) ? result : 0;
    }


    /**
     * Creates the text company with visit times.
     *
     * @param listCompanyAndVisitTimes the list company and visit times
     * @return the string
     */
    private String createTextCompanyWithVisitTimes(final List<Object[]> listCompanyAndVisitTimes) {
        StringBuilder sb = new StringBuilder();
        for (Object[] object : listCompanyAndVisitTimes) {
            sb.append("; " + object[0].toString() + "(" + object[1].toString() + ")");
        }
        return sb.toString().replaceFirst("; ", "");
    }

    /**
     * Gets the purpose for cells.
     *
     * @param listPurpose the list purpose
     * @return the purpose for cells
     */
    private PurposeCellData getPurposeForCells(final List<Object[]> listPurpose) {
        PurposeCellData purpose = new PurposeCellData();
        Map<Short, Long> mapValues = new HashMap<>();
        for (Map.Entry<Short, String> entry : SummaryReportConstants.MonthlyReport.getMapPurposeCellCoordinates()
                .entrySet()) {
            final List<Object[]> object = listPurpose.stream()
                    .filter(p -> p[1] != null ? entry.getKey() == (short) p[1] : false).collect(Collectors.toList());
            final long cellValue = CollectionUtils.isNotEmpty(object) ? (long) object.get(0)[0] : 0;
            mapValues.put(entry.getKey(), cellValue);
        }
        purpose.setNewVisit(mapValues.get(Constants.PurposeConstant.NEW_VISIT));
        purpose.setHavePeriodicVisit(mapValues.get(Constants.PurposeConstant.HAVE_PERIODIC_VISIT));
        purpose.setNoPeriodicVisit(mapValues.get(Constants.PurposeConstant.NO_PERIODIC_VISIT));
        purpose.setIntroductionEngineer(mapValues.get(Constants.PurposeConstant.INTRODUCTION_ENGINEER));
        purpose.setTechnicalDiscuss(mapValues.get(Constants.PurposeConstant.TECHNICAL_DISCUSS));
        purpose.setQuotation(mapValues.get(Constants.PurposeConstant.QUOTATION));
        purpose.setComplain(mapValues.get(Constants.PurposeConstant.COMPLAIN));
        purpose.setOther(mapValues.get(Constants.PurposeConstant.OTHER));
        return purpose;
    }

    /**
     * Gets the visit times for cells.
     *
     * @param listVisitTimes the list visit times
     * @return the visit times for cells
     */
    private VisitTimesCellData getVisitTimesForCells(final List<Object[]> listVisitTimes) {
        VisitTimesCellData visitTimesCellData = new VisitTimesCellData();
        Map<Short, Long> mapValues = new HashMap<>();
        for (Map.Entry<Short, String> entry : SummaryReportConstants.MonthlyReport.getMapVisitTimeCellCoordinates()
                .entrySet()) {
            final List<Object[]> object = listVisitTimes.stream()
                    .filter(p -> p[1] != null ? entry.getKey() == (short) p[1] : false).collect(Collectors.toList());
            final long cellValue = CollectionUtils.isNotEmpty(object) ? (long) object.get(0)[0] : 0;
            mapValues.put(entry.getKey(), cellValue);
        }
        visitTimesCellData.setSoftware(mapValues.get(Constants.BusinessTypeConstant.SOFTWARE));
        visitTimesCellData.setNetwork(mapValues.get(Constants.BusinessTypeConstant.NETWORK));
        visitTimesCellData.setArchitect(mapValues.get(Constants.BusinessTypeConstant.ARCHITECT));
        visitTimesCellData.setBuilding(mapValues.get(Constants.BusinessTypeConstant.BUILDING));
        visitTimesCellData.setEquipment(mapValues.get(Constants.BusinessTypeConstant.EQUIPMENT));
        visitTimesCellData.setFactory(mapValues.get(Constants.BusinessTypeConstant.FACTORY));
        visitTimesCellData.setElectric(mapValues.get(Constants.BusinessTypeConstant.ELECTRIC));
        visitTimesCellData.setCommon(mapValues.get(Constants.BusinessTypeConstant.COMMON));
        visitTimesCellData
                .setWirelessCommunication(mapValues.get(Constants.BusinessTypeConstant.WIRELESS_COMMUNICATION));
        visitTimesCellData.setWiredCommunication(mapValues.get(Constants.BusinessTypeConstant.WIRED_COMMUNICATION));
        visitTimesCellData.setHardware(mapValues.get(Constants.BusinessTypeConstant.HARDWARE));
        visitTimesCellData.setOffice(mapValues.get(Constants.BusinessTypeConstant.OFFICE));
        visitTimesCellData.setCallCenter(mapValues.get(Constants.BusinessTypeConstant.CALL_CENTER));
        return visitTimesCellData;
    }

    /**
     * Gets the all visit info from daily report by start date and end date.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param selectedEmployee the selected employee
     * @return the all visit info from daily report by start date and end date
     */
    private List<Object[]> getAllVisitInfoFromDailyReportByStartDateAndEndDate(final Date startDate, final Date endDate,
            final Employee_mst selectedEmployee) {
        return this.dailyRepo
                .getAllVisitInfoFromDailyReportByStartDateAndEndDate(selectedEmployee.getEmp_code(), startDate, endDate)
                .getResultList();
    }

    /**
     * Gets the all purpose info from daily report by start date and end date.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param selectedEmployee the selected employee
     * @return the all purpose info from daily report by start date and end date
     */
    private List<Object[]> getAllPurposeInfoFromDailyReportByStartDateAndEndDate(final Date startDate,
            final Date endDate, final Employee_mst selectedEmployee) {
        return this.dailyRepo.getAllPurposeInfoFromDailyReportByStartDateAndEndDate(selectedEmployee.getEmp_code(),
                startDate, endDate).getResultList();
    }

    /**
     * Gets the all company visited info from daily report by start date and end date.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param selectedEmployee the selected employee
     * @return the all company visited info from daily report by start date and end date
     */
    private List<Object[]> getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate(final Date startDate,
            final Date endDate, final Employee_mst selectedEmployee) {
        return this.dailyRepo
                .getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate(selectedEmployee.getEmp_code(), startDate,
                        endDate)
                .maxResults(SummaryReportService.MAX_VISITED_COMPANIES_RECORD).getResultList();
    }

    /**
     * *************** HQL *******************.
     *
     * @return the list
     */
    protected List<Employee_mst> listAllBranch() {
        final StringBuilder query = new StringBuilder();
        query.append("FROM Employee_mst A INNER JOIN FETCH A.addresspoint_mst B WHERE A.emp_delete_flg = 'false'");
        return EmLocator.getEm().createQuery(query.toString(), Employee_mst.class).getResultList();
    }

    /**
     * Employee of Head Quarter if Authority Code =1 Employee of Branch if AUthority Code = 0.
     *
     * @param branch the branch
     * @return the list
     */
    public List<Employee_mst> listEmployeeByBranch(final String branch) {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT e FROM Employee_mst e, Authority_mst a WHERE e.emp_delete_flg = 'false' ");
        query.append(" AND (e.emp_condi_code = '0' OR e.emp_condi_code IS NULL) ");
        query.append(" AND e.emp_settle_authority=a.aut_code ");

        if (SummaryReportConstants.HQ_CODE.equals(branch)) {
            query.append(" AND a.aut_code = " + AuthorityLevels.HEAD_QUARTER);
        }
        else if (!StringUtils.isEmpty(branch)) {
            query.append(" AND a.aut_code = " + AuthorityLevels.BRANCH).append(" AND e.emp_adpcode = '" + branch + "'");
        }

        query.append(" ORDER BY e.emp_code");
        List<Employee_mst> results = super.emMain.createQuery(query.toString(), Employee_mst.class).getResultList();
        return results;
    }

    /**
     * Gets the list periodic daily report.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param branchCode the branch code
     * @param selectedEmployee the selected employee
     * @return the list periodic daily report
     */
    protected List<Daily_report> getListPeriodicDailyReport(final Date startDate, final Date endDate,
            final String branchCode, final Employee_mst selectedEmployee) {
        TypedQuery<Daily_report> typedQuery = null;
        final StringBuilder query = new StringBuilder();
        query.append(" FROM Daily_report A LEFT JOIN FETCH A.daily_activity_type B");
        query.append(" LEFT JOIN FETCH A.company_mst C LEFT JOIN FETCH A.industry_big_mst D");
        query.append(" LEFT JOIN FETCH A.employee_mst E");
        query.append(" WHERE C.com_delete_flg = 'false' AND E.emp_delete_flg = 'false'");
        query.append(" AND A.dai_work_date >= :startDate AND A.dai_work_date <= :endDate");

        // not monthly report
        if (StringUtils.isNotEmpty(branchCode)) {
            query.append(" AND A.pk.dai_point_code = :branchCode");
        }
        if (selectedEmployee != null) {
            query.append(" AND A.pk.dai_employee_code = :employeeCode");
        }

        query.append(" ORDER BY A.pk.dai_point_code, A.pk.dai_employee_code");
        typedQuery = super.emMain.createQuery(query.toString(), Daily_report.class);

        if (StringUtils.isNotEmpty(branchCode)) {
            typedQuery.setParameter("branchCode", branchCode);
        }
        if (selectedEmployee != null) {
            typedQuery.setParameter("employeeCode", selectedEmployee.getEmp_code());
        }

        typedQuery.setParameter("startDate", startDate).setParameter("endDate", endDate);

        // order by
        List<Daily_report> results = typedQuery.getResultList();
        return results;
    }

    /**
     * Gets the industry big mts types.
     *
     * @return the industry big mts types
     */
    protected List<Industry_big_mst> getIndustryBigMTSTypes() {
        return super.emMain.createQuery("FROM Industry_big_mst ORDER BY big_code ASC", Industry_big_mst.class)
                .getResultList();
    }

    /**
     * Gets the daily activity type.
     *
     * @return the daily activity type
     */
    protected List<Daily_activity_type> getDailyActivityType() {
        return super.emMain.createQuery("FROM Daily_activity_type WHERE dat_code <> 0 ORDER BY dat_order ASC",
                Daily_activity_type.class).getResultList();
    }

    /**
     * Gets the company for report.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param minVisitTime the minVisitTime
     * @return the company for report
     */
    public List<Company_mst> getCompanyForVisitTimeReport(final Date startDate, final Date endDate,
            final int minVisitTime) {

        return this.company_mstRepository.getCompanyForVisitTimeReport(startDate, endDate, Long.valueOf(minVisitTime));

    }

    /**
     * Gets the list visited.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param companyCodes the company codes
     * @return the list visited
     */
    protected List<Daily_report> getListVisited(final Date startDate, final Date endDate,
            final List<String> companyCodes) {
        TypedQuery<Daily_report> typedQuery = null;
        final StringBuilder query = new StringBuilder();
        query.append(" FROM Daily_report A LEFT JOIN FETCH A.daily_activity_type B");
        query.append(" LEFT JOIN FETCH A.company_mst C");
        query.append(" LEFT JOIN FETCH A.employee_mst D ");

        query.append(" WHERE C.com_delete_flg = 'false' AND D.emp_delete_flg = 'false' ");
        query.append(" AND A.dai_work_date >= :startDate AND A.dai_work_date <= :endDate");
        query.append(" AND C.com_company_code IN (:companyCodes)");
        query.append(" ORDER BY C.com_company_code DESC");

        typedQuery = super.emMain.createQuery(query.toString(), Daily_report.class).setParameter("startDate", startDate)
                .setParameter("endDate", endDate);

        typedQuery.setParameter("companyCodes", companyCodes);
        List<Daily_report> results = typedQuery.getResultList();
        return results;
    }

    /**
     * Login.
     *
     * @param monthly the monthly
     * @param startDate the start date
     * @param endDate the end date
     * @param companyCodes the company codes
     * @return the service result
     * @throws ArrException the arr exception
     */
    public ServiceResult<Daily_report> login(final Date monthly, final Date startDate, final Date endDate,
            final List<String> companyCodes) throws ArrException {
        final List<Message> errors = new ArrayList<>();
        // Validate input

        // this case, we cannot ignore because ul_user_code is integer.
        if (startDate.compareTo(endDate) > 0) {
            errors.add(ErrorMessage.auth_001_invalid_user_or_password());
        }

        if (companyCodes.size() == 0) {
            errors.add(ErrorMessage.auth_001_invalid_user_or_password());
        }

        return new ServiceResult<>(errors.isEmpty(), errors);
    }


    /**
     * Make style for empty title cell.
     *
     * @param workbook the workbook
     * @return the HSSF cell style
     */
    protected CellStyle makeStyleForEmptyTitleCell(final Workbook workbook) {
        final CellStyle myStyle = workbook.createCellStyle();
        myStyle.setBorderTop(CellStyle.BORDER_THIN);
        myStyle.setBorderBottom(CellStyle.BORDER_THIN);
        myStyle.setBorderLeft(CellStyle.BORDER_NONE);
        myStyle.setBorderRight(CellStyle.BORDER_NONE);
        return myStyle;
    }

    /**
     * Gets the text cell style.
     *
     * @param workbook the workbook
     * @return the text cell style
     */
    protected CellStyle getTextCellStyle(final Workbook workbook) {
        final CellStyle myStyle = ExportExcelUtils.makeFullBorderStyle(workbook);
        myStyle.setAlignment(CellStyle.ALIGN_LEFT);
        return myStyle;
    }

    /**
     * Gets the number cell style.
     *
     * @param workbook the workbook
     * @return the number cell style
     */
    protected CellStyle getNumberCellStyle(final Workbook workbook) {
        final CellStyle numberStyle = ExportExcelUtils.makeFullBorderStyle(workbook);
        numberStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        return numberStyle;
    }


    /**
     * Gets the number cell style visited times.
     *
     * @param workbook the workbook
     * @return the number cell style visited times
     */
    protected CellStyle getNumberCellStyleVisitedTimes(final Workbook workbook) {
        final CellStyle numberStyle = ExportExcelUtils.makeFullBorderStyle(workbook);
        numberStyle.setBorderBottom(CellStyle.BORDER_NONE);
        numberStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        return numberStyle;
    }

    /**
     * Gets the addresspoint by code.
     *
     * @param branchCode the branch code
     * @return the addresspoint by code
     */
    private Addresspoint_mst getAddresspointByCode(final String branchCode) {
        return this.addressRepo.findAddresspointByCode(branchCode).getAnyResult();

    }

    /**
     * Gets the current user.
     *
     * @return the current user
     */
    public UserCredential getCurrentUser() {
        return this.currentUser;
    }

    /**
     * Get latest active monthly report by employee code and time of report.
     *
     * @param selectedEmployee The Employee_mst.
     * @param startDate Start date of monthly report.
     * @param endDate End date of monthly report.
     * @return the active monthly report by emp code and time of report order by version
     */
    public Monthly_report_revision getActiveMonthlyReportByEmpCodeAndTimeOfReportOrderByVersion(
            final Employee_mst selectedEmployee, final Date startDate, final Date endDate) {
        Monthly_report_revision monthlyReport = null;
        monthlyReport =
                this.monthly_reportRepository
                        .getActiveMonthlyReportByEmpCodeAndTimeOfReportOrderByVersion(selectedEmployee.getEmp_code(),
                                DateUtils.getYear(startDate), DateUtils.getMonth(startDate))
                        .maxResults(1).getAnyResult();
        if (monthlyReport == null) {
            monthlyReport = new Monthly_report_revision(0, selectedEmployee, DateUtils.getMonth(startDate),
                    DateUtils.getYear(startDate));
            this.updateDefaultValueForMonthlyReportWhenCreateNew(monthlyReport);

            monthlyReport.setShounin_joutai(SummaryReportConstants.MonthlyReport.STATUS_OPEN);
            return this.getLatestInfoForMonthlyReport(monthlyReport, selectedEmployee, startDate, endDate);
        }

        if (monthlyReport.isOpenStatus()) {
            return this.getLatestInfoForMonthlyReport(monthlyReport, selectedEmployee, startDate, endDate);
        }

        return monthlyReport;
    }

    private void updateDefaultValueForMonthlyReportWhenCreateNew(final Monthly_report_revision monthlyReport) {
        monthlyReport.setKadou_nissuu(1);
        monthlyReport.setTougetsu_tsuitachi_kara_no_haken_shain_suu(0);
        monthlyReport.setJigetsu_tsuitachi_kara_no_haken_shain_suu(0);
        monthlyReport.setHoumon_kensuu_shokushu_sofuto_wea(0);
        monthlyReport.setHoumon_kensuu_shokushu_netto_waku(0);
        monthlyReport.setHoumon_kensuu_shokushu_kenchiku(0);
        monthlyReport.setHoumon_kensuu_shokushu_doboku(0);
        monthlyReport.setHoumon_kensuu_shokushu_setsubi(0);
        monthlyReport.setHoumon_kensuu_shokushu_denki(0);
        monthlyReport.setHoumon_kensuu_shokushu_puranto(0);
        monthlyReport.setHoumon_kensuu_shokushu_ippan(0);
        monthlyReport.setHoumon_kensuu_shokushu_tsuushin_musen(0);
        monthlyReport.setHoumon_kensuu_shokushu_tsuushin_yuusen(0);
        monthlyReport.setHoumon_kensuu_shokushu_kikai_hado(0);
        monthlyReport.setHoumon_kensuu_shokushu_jimu(0);
        monthlyReport.setHoumon_kensuu_shokushu_koru_centa(0);
        monthlyReport.setHoumon_kensuu_mokuteki_shinkihoumon(0);
        monthlyReport.setHoumon_kensuu_mokuteki_kizon_no_teiki_houmon(0);
        monthlyReport.setHoumon_kensuu_mokuteki_kizon_igai_no_teiki_houmon(0);
        monthlyReport.setHoumon_kensuu_mokuteki_gijutsusha_shoukai(0);
        monthlyReport.setHoumon_kensuu_mokuteki_gyouda(0);
        monthlyReport.setHoumon_kensuu_mokuteki_mitsumori(0);
        monthlyReport.setHoumon_kensuu_mokuteki_mitsumori(0);
        monthlyReport.setHoumon_kensuu_mokuteki_mitsumori(0);
        monthlyReport.setSutaffu_kanri_denwa_forosuu(0);
        monthlyReport.setSutaffu_kanri_mensetsu_forosuu(0);
        monthlyReport.setSutaffu_kanri_kuremu_suu(0);
        monthlyReport.setSutaffu_kanri_sono_ta_no_kazu(0);
        monthlyReport.setIchiji_mensetsu_no_kensuu(0);
        monthlyReport.setNiji_mensetsu_no_kensuu(0);
        monthlyReport.setSaiyou_suu(0);
        monthlyReport.setSaiyou_sapoto_ninzuu(0);
        monthlyReport.setShinki_kaitaku_suu(0);
        monthlyReport.setZouka_suu(0);
        monthlyReport.setUriage_ph(0);
        monthlyReport.setGenka_ritsu(0d);
    }

    /**
     * Get latest info when monthly report not existed or in open status.
     *
     * @param monthlyReport The Monthly_report_revision.
     * @param selectedEmp The Employee_mst.
     * @param startDate Start date of report.
     * @param endDate End date of report.
     * @return the latest info for monthly report
     */
    public Monthly_report_revision getLatestInfoForMonthlyReport(final Monthly_report_revision monthlyReport,
            final Employee_mst selectedEmp, final Date startDate, final Date endDate) {

        // Working days
        final int actualWorkingDay = this.dailyRepo
                .getListActualWorkingDay(selectedEmp.getEmp_code(), startDate, endDate).getResultList().size();
        monthlyReport.setNyuryoku_nissuu(actualWorkingDay);

        // Total visit
        final long totalVisit = this.dailyRepo
                .countTotalDailyReportOfEmployeeByStartDateAndEndDate(selectedEmp.getEmp_code(), startDate, endDate)
                .getAnyResult();
        monthlyReport.setSou_houmon_kensuu((int) totalVisit);

        // Software
        this.setVisitInfoForMonthlyReport(monthlyReport, selectedEmp.getEmp_code(), startDate, endDate);

        // Purpose
        this.setPurposeInfoForMonthlyReport(monthlyReport, selectedEmp.getEmp_code(), startDate, endDate);

        monthlyReport.setHoumon_saki_kigyou_ichiran(
                this.getListVisitedCompanies(selectedEmp.getEmp_code(), startDate, endDate));

        return monthlyReport;
    }

    /**
     * Sets the visit info for monthly report.
     *
     * @param monthlyReport the monthly report
     * @param empCode the emp code
     * @param startDate the start date
     * @param endDate the end date
     */
    private void setVisitInfoForMonthlyReport(final Monthly_report_revision monthlyReport, final int empCode,
            final Date startDate, final Date endDate) {
        final List<Object[]> listVisitInfo = this.dailyRepo
                .getAllVisitInfoFromDailyReportByStartDateAndEndDate(empCode, startDate, endDate).getResultList();
        int software = this.getValue(listVisitInfo, (short) 201);
        monthlyReport.setHoumon_kensuu_shokushu_sofuto_wea(software);
        int network = this.getValue(listVisitInfo, (short) 202);
        monthlyReport.setHoumon_kensuu_shokushu_netto_waku(network);
        int architecture = this.getValue(listVisitInfo, (short) 101);
        monthlyReport.setHoumon_kensuu_shokushu_kenchiku(architecture);
        int construction = this.getValue(listVisitInfo, (short) 102);
        monthlyReport.setHoumon_kensuu_shokushu_doboku(construction);
        int equipment = this.getValue(listVisitInfo, (short) 104);
        monthlyReport.setHoumon_kensuu_shokushu_setsubi(equipment);
        int electrical = this.getValue(listVisitInfo, (short) 103);
        monthlyReport.setHoumon_kensuu_shokushu_denki(electrical);
        int plant = this.getValue(listVisitInfo, (short) 501);
        monthlyReport.setHoumon_kensuu_shokushu_puranto(plant);
        int common = this.getValue(listVisitInfo, (short) 601);
        monthlyReport.setHoumon_kensuu_shokushu_ippan(common);
        int communicationWireless = this.getValue(listVisitInfo, (short) 301);
        monthlyReport.setHoumon_kensuu_shokushu_tsuushin_musen(communicationWireless);
        int communicationWired = this.getValue(listVisitInfo, (short) 302);
        monthlyReport.setHoumon_kensuu_shokushu_tsuushin_yuusen(communicationWired);
        int machineryAndHard = this.getValue(listVisitInfo, (short) 401);
        monthlyReport.setHoumon_kensuu_shokushu_kikai_hado(machineryAndHard);
        int bussiness = this.getValue(listVisitInfo, (short) 602);
        monthlyReport.setHoumon_kensuu_shokushu_jimu(bussiness);
        int callCenter = this.getValue(listVisitInfo, (short) 603);
        monthlyReport.setHoumon_kensuu_shokushu_koru_centa(callCenter);
    }

    /**
     * Sets the purpose info for monthly report.
     *
     * @param monthlyReport the monthly report
     * @param empCode the emp code
     * @param startDate the start date
     * @param endDate the end date
     */
    private void setPurposeInfoForMonthlyReport(final Monthly_report_revision monthlyReport, final int empCode,
            final Date startDate, final Date endDate) {
        final List<Object[]> listPurpose = this.dailyRepo
                .getAllPurposeInfoFromDailyReportByStartDateAndEndDate(empCode, startDate, endDate).getResultList();
        int newVisit = this.getValue(listPurpose, (short) 2);
        monthlyReport.setHoumon_kensuu_mokuteki_shinkihoumon(newVisit);
        int regularVisit = this.getValue(listPurpose, (short) 4);
        monthlyReport.setHoumon_kensuu_mokuteki_kizon_no_teiki_houmon(regularVisit);
        int nonRegularVisit = this.getValue(listPurpose, (short) 3);
        monthlyReport.setHoumon_kensuu_mokuteki_kizon_igai_no_teiki_houmon(nonRegularVisit);
        int introductionTechnician = this.getValue(listPurpose, (short) 5);
        monthlyReport.setHoumon_kensuu_mokuteki_gijutsusha_shoukai(introductionTechnician);
        int visitation = this.getValue(listPurpose, (short) 6);
        monthlyReport.setHoumon_kensuu_mokuteki_gyouda(visitation);
        int estimate = this.getValue(listPurpose, (short) 7);
        monthlyReport.setHoumon_kensuu_mokuteki_mitsumori(estimate);
        int claim = this.getValue(listPurpose, (short) 8);
        monthlyReport.setHoumon_kensuu_mokuteki_kuremu(claim);
        int other = this.getValue(listPurpose, (short) 1);
        monthlyReport.setHoumon_kensuu_mokuteki_sonota(other);
    }

    /**
     * Gets the value.
     *
     * @param list the list
     * @param key the key
     * @return the value
     */
    private int getValue(final List<Object[]> list, final short key) {
        List<Object[]> objects =
                list.stream().filter(p -> p[1] != null ? key == (short) p[1] : false).collect(Collectors.toList());
        return CollectionUtils.isNotEmpty(objects) ? (int) Long.parseLong(objects.get(0)[0].toString()) : 0;
    }

    /**
     * Get list visited companies and number visited.
     *
     * @param emp_code Code of employee.
     * @param startDate Start date of report.
     * @param endDate End date of report.
     * @return the list visited companies
     */
    public String getListVisitedCompanies(final int emp_code, final Date startDate, final Date endDate) {
        final List<Object[]> listCompanyVisited = this.dailyRepo
                .getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate(emp_code, startDate, endDate)
                .maxResults(SummaryReportConstants.MonthlyReport.MAX_VISITED_COMPANIES_RECORD).getResultList();
        if (CollectionUtils.isEmpty(listCompanyVisited)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (Object[] object : listCompanyVisited) {
            sb.append("; " + object[0].toString() + "(" + object[1].toString() + ")");
        }
        return sb.toString().replaceFirst("; ", "");
    }

    /**
     * Compare current monthly report with one in database.
     *
     * @param monthlyReportRevision Current monthly report.
     * @return true, if successful
     */
    public boolean checkModifiedMonthlyReport(final Monthly_report_revision monthlyReportRevision) {
        final Monthly_report_revision monthlyReportInDB =
                this.monthly_reportRepository.findBy(monthlyReportRevision.getPk());
        return EqualsBuilder.reflectionEquals(monthlyReportRevision, monthlyReportInDB, "object_version",
                "employee_mst", "last_updated_at", "bajon_bangou", "shain_kodo", "repoto_no_getsudo",
                "repoto_no_nendo");
    }


    /**
     * Gets the active branch position by employee code and target branch.
     *
     * @param empCode the emp code
     * @param adpCode the adp code
     * @return the active branch position by employee code and target branch
     */
    public Branch_position getActiveBranchPositionByEmployeeCodeAndTargetBranch(final int empCode,
            final String adpCode) {
        return this.branchPositionRepo
                .findByEmployeeCodeAndAddressPointCodeAndDeleteFlag(empCode, adpCode, Constants.IS_NOT_DELETED)
                .getAnyResult();
    }


    /**
     * Gets the list monthly report history.
     *
     * @param selectedEmployee the selected employee
     * @param startDate the start date
     * @return the list monthly report history
     */
    public ApprovalAndSubmissionInfo getApprovalAndSubmissionInfo(final Employee_mst selectedEmployee,
            final Date startDate) {
        List<Monthly_report_history> listMonthlyReportHistory = this.monthly_report_historyRepository
                .findMonthlyReportHistoryByEmployeeAndStartDate(selectedEmployee.getEmp_code(),
                        DateUtils.getMonth(startDate), DateUtils.getYear(startDate))
                .getResultList();
        ApprovalAndSubmissionInfo info = new ApprovalAndSubmissionInfo();
        int countSubmission = 0;
        int countReject = 0;
        int countApprove = 0;
        int countReOpen = 0;

        for (int i = 0; i < listMonthlyReportHistory.size(); i++) {

            String type = listMonthlyReportHistory.get(i).getSousa();
            Monthly_report_history history = listMonthlyReportHistory.get(i);
            switch (type) {
                case SummaryReportConstants.MonthlyReportHisotry.SUBMISSION:
                    countSubmission++;
                    if (info.getDateSubmissions() == null) {
                        info.setDateSubmissions(listMonthlyReportHistory.get(i).getSousa_jiten());
                    }
                    break;
                case SummaryReportConstants.MonthlyReportHisotry.REJECT:
                    countReject++;
                    if (info.getDateReject() == null) {
                        info.setDateReject(listMonthlyReportHistory.get(i).getSousa_jiten());
                        info.setRejectedBy(listMonthlyReportHistory.get(i).getEmployee_mst().getEmp_name());
                        info.setRejectComment(history.getComment());
                    }
                    break;
                case SummaryReportConstants.MonthlyReportHisotry.APPROVE:
                    countApprove++;
                    if (info.getDateApproval() == null) {
                        info.setDateApproval(listMonthlyReportHistory.get(i).getSousa_jiten());
                        info.setApprover(listMonthlyReportHistory.get(i).getEmployee_mst().getEmp_name());
                    }
                    break;
                case SummaryReportConstants.MonthlyReportHisotry.REOPEN:
                    countReOpen++;
                    if (info.getDateReOpen() == null) {
                        info.setDateReOpen(listMonthlyReportHistory.get(i).getSousa_jiten());
                        info.setReOpenedBy(listMonthlyReportHistory.get(i).getEmployee_mst().getEmp_name());
                        info.setReopenComment(history.getComment());
                    }
                    break;
                default:
                    break;
            }
        }

        info.setNumberOfSubmissions(countSubmission);
        info.setNumberOfReject(countReject);
        info.setNumberOfApprove(countApprove);
        info.setNumberOfReOpen(countReOpen);
        return info;
    }

    /**
     * Save monthly report.
     *
     * @param monthlyReport the monthly report
     * @param reportStatus the report status
     * @return the service result
     */
    private Monthly_report_revision saveMonthlyReport(final Monthly_report_revision monthlyReport,
            final String reportStatus) {
        monthlyReport.setShounin_joutai(reportStatus);
        monthlyReport.setRepoto_no_ribijon_no_sakujo_furagu(false);

        Monthly_report_revision newReport = this.monthly_reportRepository.saveAndFlushAndRefresh(monthlyReport);
        return newReport;
    }

    /**
     * Save temporary monthly report.
     *
     * @param monthlyReport the monthly report
     * @return the service result
     */
    public ServiceResult<Monthly_report_revision> saveTemporaryMonthlyReport(
            final Monthly_report_revision monthlyReport) {
        Monthly_report_revision newReport = this.saveMonthlyReport(monthlyReport, monthlyReport.isPersisted()
                ? monthlyReport.getShounin_joutai() : SummaryReportConstants.MonthlyReport.STATUS_OPEN);

        return new ServiceResult<Monthly_report_revision>(true, newReport, InfoMessage.common_001_save_successfully());
    }

    /**
     * Submit monthly report.
     *
     * @param monthlyReport the monthly report
     * @param userEmpCode the user emp code
     * @return the service result
     */
    public ServiceResult<Monthly_report_revision> submitMonthlyReport(final Monthly_report_revision monthlyReport,
            final int userEmpCode) {
        Monthly_report_revision result =
                this.saveMonthlyReport(monthlyReport, SummaryReportConstants.MonthlyReport.STATUS_WAITING);
        this.saveMonthlyReporHistorytWithoutComment(monthlyReport,
                SummaryReportConstants.MonthlyReportHisotry.SUBMISSION, userEmpCode);
        return new ServiceResult<Monthly_report_revision>(true, result,
                InfoMessage.common_003_submission_successfully());
    }

    /**
     * Approve monthly report.
     *
     * @param monthlyReport the monthly report
     * @param userEmpCode the user emp code
     * @param comment the comment
     * @return the service result
     */
    public ServiceResult<Monthly_report_revision> approveMonthlyReport(final Monthly_report_revision monthlyReport,
            final int userEmpCode, final String comment) {
        if (this.isCurrentDataWasChanedByAnotherUser(monthlyReport)) {
            return new ServiceResult<>(false, ErrorMessage.monthlyreport_010_approve_failed_by_other_person_approved());
        }
        Monthly_report_revision result =
                this.saveMonthlyReport(monthlyReport, SummaryReportConstants.MonthlyReport.STATUS_APPROVED);
        this.saveMonthlyReportHistory(monthlyReport, SummaryReportConstants.MonthlyReportHisotry.APPROVE, userEmpCode,
                comment);
        return new ServiceResult<Monthly_report_revision>(true, result, InfoMessage.monthlyreport_004_approved());
    }

    /**
     * Reopen monthly report.
     *
     * @param monthlyReport the monthly report
     * @param userEmpCode the user emp code
     * @param comment the comment
     * @return the service result
     */
    public ServiceResult<Monthly_report_revision> reopenMonthlyReport(final Monthly_report_revision monthlyReport,
            final int userEmpCode, final String comment) {
        if (StringUtils.isEmpty(comment)) {
            return new ServiceResult<>(false, ErrorMessage.monthlyreport_006_comment_is_required());
        }

        if (this.isCurrentDataWasChanedByAnotherUser(monthlyReport)) {
            return new ServiceResult<>(false, ErrorMessage.monthlyreport_009_reopen_failed_by_other_person_reopened());
        }
        Monthly_report_revision result = this.updateReportWithNewStatusAndCreateOpenReport(monthlyReport,
                SummaryReportConstants.MonthlyReport.STATUS_REOPEN);
        this.saveMonthlyReportHistory(monthlyReport, SummaryReportConstants.MonthlyReportHisotry.REOPEN, userEmpCode,
                comment);
        return new ServiceResult<Monthly_report_revision>(true, result, InfoMessage.monthlyreport_007_reopened());
    }

    /**
     * Update report with new status and create open report.
     *
     * @param monthlyReport the monthly report
     * @param reportStatus the report status
     * @return the service result
     */
    private Monthly_report_revision updateReportWithNewStatusAndCreateOpenReport(
            final Monthly_report_revision monthlyReport, final String reportStatus) {
        this.saveMonthlyReport(monthlyReport, reportStatus);
        Monthly_report_revision newReport =
                new Monthly_report_revision(monthlyReport.getBajon_bangou() + 1, monthlyReport.getEmployee_mst(),
                        monthlyReport.getRepoto_no_getsudo(), monthlyReport.getRepoto_no_nendo());
        BeanCopier.copy(monthlyReport, newReport);
        return this.saveMonthlyReport(newReport, SummaryReportConstants.MonthlyReport.STATUS_OPEN);
    }


    /**
     * Reject monthly report.
     *
     * @param monthlyReport the monthly report
     * @param userEmpCode the user emp code
     * @param comment the comment
     * @return the service result
     */
    public ServiceResult<Monthly_report_revision> rejectMonthlyReport(final Monthly_report_revision monthlyReport,
            final int userEmpCode, final String comment) {
        if (StringUtils.isEmpty(comment)) {
            return new ServiceResult<>(false, ErrorMessage.monthlyreport_006_comment_is_required());
        }

        if (this.isCurrentDataWasChanedByAnotherUser(monthlyReport)) {
            return new ServiceResult<>(false, ErrorMessage.monthlyreport_008_reject_failed_by_other_person_rejected());
        }

        Monthly_report_revision result = this.updateReportWithNewStatusAndCreateOpenReport(monthlyReport,
                SummaryReportConstants.MonthlyReport.STATUS_REJECT);
        this.saveMonthlyReportHistory(monthlyReport, SummaryReportConstants.MonthlyReportHisotry.REJECT, userEmpCode,
                comment);
        return new ServiceResult<Monthly_report_revision>(true, result, InfoMessage.monthlyreport_003_rejected());
    }

    /**
     * Save monthly report without comment.
     *
     * @param monthlyReport the monthly report
     * @param action the action
     * @param userEmpCode the user emp code
     * @return the service result
     */
    private ServiceResult<Monthly_report_history> saveMonthlyReporHistorytWithoutComment(
            final Monthly_report_revision monthlyReport, final String action, final int userEmpCode) {
        return this.saveMonthlyReportHistory(monthlyReport, action, userEmpCode, StringUtils.EMPTY_STRING);
    }

    /**
     * Save monthy report history.
     *
     * @param monthlyReport the monthly report
     * @param action the action
     * @param userEmpCode the user emp code
     * @param comment the comment
     * @return the service result
     */
    private ServiceResult<Monthly_report_history> saveMonthlyReportHistory(final Monthly_report_revision monthlyReport,
            final String action, final int userEmpCode, final String comment) {
        Monthly_report_history history = new Monthly_report_history(monthlyReport, action);
        history.setSousa_jiten(new Date());
        history.setShouninsha_kodo(userEmpCode);
        history.setComment(comment);
        Monthly_report_history newHistory = this.monthly_report_historyRepository.saveAndFlushAndRefresh(history);
        return new ServiceResult<Monthly_report_history>(true, newHistory, InfoMessage.common_001_save_successfully());
    }

    /**
     * Checks if is current data was chaned by another user.
     *
     * @param monthlyReport the monthly report
     * @return true, if is current data was chaned by another user
     */
    private boolean isCurrentDataWasChanedByAnotherUser(final Monthly_report_revision monthlyReport) {
        Monthly_report_revision currentMonthlyReport = this.monthly_reportRepository.findAndRefresh(monthlyReport);
        return !StringUtils.equalsIgnoreCase(monthlyReport.getShounin_joutai(),
                currentMonthlyReport.getShounin_joutai());
    }


    /**
     * Find old comment.
     *
     * @param selectedEmployee the selected employee
     * @param startDate the start date
     * @return the string
     */
    public String findOldComment(final Employee_mst selectedEmployee, final Date startDate) {
        List<String> listComment = this.monthly_report_historyRepository
                .findLastCommentMonthlyReportHistoryByEmployeeAndStartDate(selectedEmployee.getEmp_code(),
                        DateUtils.getMonth(startDate), DateUtils.getYear(startDate))
                .getResultList();
        if (CollectionUtils.isNotEmpty(listComment)) {
            return listComment.get(0);
        }
        return "";
    }
}
