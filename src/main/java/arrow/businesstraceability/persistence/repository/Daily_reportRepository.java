//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.control.bean.RegisterActivityHistorySearchBean;
import arrow.businesstraceability.persistence.entity.Daily_report;
import arrow.businesstraceability.persistence.mapped.Daily_report_MAPPED;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.persistence.util.Condition;
import arrow.framework.persistence.util.Condition.Junction;
import arrow.framework.util.StringUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Daily_reportRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Daily_report, Daily_report_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    /**
     * Gets the list periodic daily report.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @param branchCode the branch code
     * @param employeeCode the employee code
     * @return the list periodic daily report
     */
    public List<Daily_report> getListPeriodicDailyReport(final Date startDate, final Date endDate,
        final String branchCode, final String employeeCode) {
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
        if (StringUtils.isNotEmpty(employeeCode)) {
            query.append(" AND A.pk.dai_employee_code = :employeeCode");
        }

        query.append(" ORDER BY A.pk.dai_point_code, A.pk.dai_employee_code");
        typedQuery = this.entityManager().createQuery(query.toString(), Daily_report.class);

        if (StringUtils.isNotEmpty(branchCode)) {
            typedQuery.setParameter("branchCode", branchCode);
        }
        if (StringUtils.isNotEmpty(employeeCode)) {
            typedQuery.setParameter("employeeCode", Integer.parseInt(employeeCode));
        }

        typedQuery.setParameter("startDate", startDate).setParameter("endDate", endDate);

        // order by
        List<Daily_report> results = typedQuery.getResultList();
        return results;
    }

    @Query("SELECT COUNT(dai_bus_code), dai_bus_code FROM Daily_report WHERE dai_employee_code = ?1 AND dai_work_date BETWEEN ?2  AND ?3 group by (dai_bus_code) order by dai_bus_code")
    public abstract QueryResult<Object[]> getAllVisitInfoFromDailyReportByStartDateAndEndDate(int employeeCode,
        Date startDate, Date endDate);

    @Query("SELECT COUNT(dai_work_tancode), dai_work_tancode FROM Daily_report WHERE dai_employee_code = ?1 AND dai_work_date BETWEEN ?2  AND ?3 GROUP BY (dai_work_tancode) order by dai_work_tancode")
    public abstract QueryResult<Object[]> getAllPurposeInfoFromDailyReportByStartDateAndEndDate(int employeeCode,
        Date startDate, Date endDate);

    @Query("SELECT c.com_company_name, COUNT(c.com_company_name) FROM Daily_report d, Company_mst c WHERE d.dai_company_code IS NOT NULL AND d.dai_company_code = c.com_company_code AND d.dai_employee_code = ?1 AND d.dai_work_date BETWEEN ?2 AND ?3 GROUP BY c.com_company_name ORDER BY COUNT(c.com_company_name) DESC")
    public abstract QueryResult<Object[]> getAllCompanyVisitedInfoFromDailyReportByStartDateAndEndDate(int employeeCode,
        Date startDate, Date endDate);

    /**
     * Count total visited times to company.
     *
     * @param companyCode the company code
     * @return the long
     */
    @Query(value = "SELECT COUNT(r) FROM Daily_report r "
        + ",Employee_mst e WHERE e.emp_code = r.dai_employee_code AND e.emp_delete_flg = false "
        + "AND r.dai_company_code = ?1 AND r.dai_work_tancode = ?2 " + "AND r.dai_point_code = ?3"
        + " AND r.dai_work_date >= ?4 AND r.dai_work_date <= ?5 ")
    public abstract long countVisitedTimesOfCompanyWithPurposeAndBasepoint(final String companyCode,
        final Short datCode, final String adpCode, Date startDate, Date endDate);

    /**
     * Count total visited times to company with employee and basepoint.
     *
     * @param companyCode the company code
     * @param empCode the emp code
     * @param adpCode the adp code
     * @return the long
     */
    @Query(
        value = "SELECT COUNT(r) FROM Daily_report r " + "WHERE r.dai_company_code = ?1 AND r.dai_employee_code = ?2 "
            + "AND r.dai_point_code = ?3 " + " AND r.dai_work_date >= ?4 AND r.dai_work_date <= ?5 ")
    public abstract long countTotalVisitedTimesToCompanyWithEmployeeAndBasepoint(final String companyCode,
        final int empCode, final String adpCode, Date startDate, Date endDate);

    /**
     * Count total visited times to company.
     *
     * @param companyCode the company code
     * @return the long
     */
    @Query(value = "SELECT COUNT(r) FROM Daily_report r , Employee_mst e "
        + "WHERE e.emp_code = r.dai_employee_code AND e.emp_delete_flg = false AND r.dai_company_code = ?1 "
        + " AND r.dai_work_date >= ?2 AND r.dai_work_date <= ?3 ")
    public abstract long countTotalVisitedTimesToCompany(final String companyCode, Date startDate, Date endDate);

    /**
     * Count total visited times to company with basepoint.
     *
     * @param companyCode the company code
     * @param adpCode the adp code
     * @return the long
     */
    @Query(value = "SELECT COUNT(r) FROM Daily_report r , Employee_mst e "
        + "WHERE e.emp_code = r.dai_employee_code AND e.emp_delete_flg = false AND r.dai_company_code = ?1 AND r.dai_point_code = ?2 "
        + " AND r.dai_work_date >= ?3 AND r.dai_work_date <= ?4 ")
    public abstract long countTotalVisitedTimesToCompanyWithBasepoint(final String companyCode, final String adpCode,
        Date startDate, Date endDate);

    /**
     * Count visited times of company with purpose and employee and basepoint.
     *
     * @param companyCode the company code
     * @param datCode the dat code
     * @param empCode the emp code
     * @param adpCode the adp code
     * @return the long
     */
    @Query(value = "SELECT COUNT(r) FROM Daily_report r " + "WHERE r.dai_company_code = ?1 AND r.dai_work_tancode = ?2 "
        + "AND r.dai_employee_code = ?3 AND r.dai_point_code = ?4 "
        + " AND r.dai_work_date >= ?5 AND r.dai_work_date <= ?6 ")
    public abstract long countVisitedTimesOfCompanyWithPurposeAndEmployeeAndBasepoint(final String companyCode,
        final short datCode, final int empCode, final String adpCode, Date startDate, Date endDate);

    @Query(
        value = "SELECT e FROM Daily_report e WHERE e.dai_work_date = ?1 AND e.dai_employee_code = ?2 ORDER BY e.dai_work_stime")
    public abstract QueryResult<Daily_report> getReportByWorkDateAndEmployeeCode(Date workDate, int employeeCode);

    @Query(
        value = "SELECT COUNT(e) FROM Daily_report e WHERE e.dai_employee_code = ?1 AND e.dai_point_code = ?2 AND e.dai_work_date = ?3 "
            + "AND ((e.dai_work_stime <= ?4 AND ?4 < e.dai_work_etime) OR (e.dai_work_stime < ?5 AND ?5 <= e.dai_work_etime) OR "
            + "(?4 <= e.dai_work_stime AND e.dai_work_stime < ?5) OR (?4 < e.dai_work_etime AND e.dai_work_etime <= ?5)  )")
    public abstract long countDailyReportOverlappedCaseAddNew(int employeeCode, String pointCode, Date workDate,
        Date startWorkTime, Date endWorkTime);

    @Query(value = "SELECT COUNT(d) FROM Daily_report d WHERE d.dai_employee_code = ?1 AND d.dai_point_code = ?2 "
        + "AND d.dai_work_date = ?3 AND d.dai_work_stime <> ?4 AND d.dai_work_etime <> ?5 "
        + "AND (d.dai_work_date = ?6 AND ( (d.dai_work_stime <= ?7 AND ?7 < d.dai_work_etime) OR "
        + "(d.dai_work_stime < ?8 AND ?8 <=d.dai_work_etime) OR (?7 <= d.dai_work_stime AND d.dai_work_stime < ?8) OR "
        + "(?7 < d.dai_work_etime AND d.dai_work_etime <= ?8)  ) )")
    public abstract long countDailyReportOverlappedCaseEdit(final int employeeCode, final String pointCode,
        final Date oldWorkDate, final Date oldWorkStime, final Date oldWorkEtime, final Date newWorkDate,
        final Date newWorkStime, final Date newWorkEtime);

    @Query(
        value = "SELECT e FROM Daily_report e WHERE e.dai_work_date = ?1 ORDER BY e.dai_work_stime, e.dai_work_etime")
    public abstract QueryResult<Daily_report> getReportByWorkDate(Date selectedDate);

    @Query(
        value = "SELECT e FROM Daily_report e "
            + "WHERE e.last_updated_at = (SELECT MAX(e1.last_updated_at) FROM Daily_report e1 WHERE e1.dai_employee_code = ?1)",
        max = 1)
    public abstract QueryResult<Daily_report> getReportLatestUpdatedByEmpCode(int currentEmployeeCode);

    public RepoResult<Daily_report> searchHistoryDailyReport(
        final RegisterActivityHistorySearchBean searchHistoryBean) {

        ArrowQuery<Daily_report> searchQuery = new ArrowQuery<Daily_report>(this.entityManager());
        searchQuery.select("e");
        searchQuery.from("Daily_report e").from("Employee_mst em");
        searchQuery.where("e.dai_employee_code=em.emp_code");
        searchQuery.where("em.emp_delete_flg = ?", AuthenticationConstants.EmployeeStatus.IS_DELETED);

        if ((StringUtils.isNotEmpty(searchHistoryBean.getInputTextCompanyActivity()))
            || (StringUtils.isNotEmpty(searchHistoryBean.getCompanyName()))
            || (StringUtils.isNotEmpty(searchHistoryBean.getCompanyDepartment()))
            || (StringUtils.isNotEmpty(searchHistoryBean.getSelectAddressPoint()))) {
            searchQuery.from("Daily_activity_type da").from("Company_mst c").from("Addresspoint_mst a ");
            searchQuery.where("da.dat_code=e.dai_work_tancode AND c.com_company_code = e.dai_company_code "
                + "AND em.emp_code = e.dai_employee_code AND c.com_point_code = a.adp_code");
        }

        if (searchHistoryBean.isEnableStartTime()) {
            Junction fromDate = new Condition.Conjunction();
            fromDate.add("e.dai_work_date >=?", searchHistoryBean.getStartDate());
            if (searchHistoryBean.isEnableEndDate()) {
                fromDate.add("e.dai_work_date <=?", searchHistoryBean.getEndDate());
            }
            searchQuery.where(fromDate);
        }

        if (searchHistoryBean.isBusinessTask() && searchHistoryBean.isOtherTask()) {
            searchQuery.where("e.dai_work_type=true OR e.dai_work_type=false");
        } else if (searchHistoryBean.isBusinessTask() && !searchHistoryBean.isOtherTask()) {
            searchQuery.where("e.dai_work_type=true");
        } else if (searchHistoryBean.isOtherTask() && !searchHistoryBean.isBusinessTask()) {
            searchQuery.where("e.dai_work_type=false");
        }

        if (StringUtils.isNotEmpty(searchHistoryBean.getSelectPosition())) {
            searchQuery.where("em.emp_adpcode=?", searchHistoryBean.getSelectPosition());
        }

        if (searchHistoryBean.getSelectedEmployee() != null) {
            searchQuery.where("e.dai_employee_code=?", searchHistoryBean.getSelectedEmployee().getEmp_code());
        } else if (!searchHistoryBean.isIncludeRetiredEmployee()) {
            searchQuery.where("em.emp_condi_code IS NULL OR em.emp_condi_code = '0'");
        }

        if (StringUtils.isNotEmpty(searchHistoryBean.getSelectAddressPoint())) {
            searchQuery.where("a.adp_code=?", searchHistoryBean.getSelectAddressPoint());
        }

        // Reminder
        if (searchHistoryBean.getCheckBoxReminder().length == 1) {
            searchQuery.where("e.dai_rimaindar_flg = ?", searchHistoryBean.getCheckBoxReminder()[0]);
        } else if (searchHistoryBean.getCheckBoxReminder().length == 2) {
            searchQuery.where("e.dai_rimaindar_flg = ? OR e.dai_rimaindar_flg = ?",
                searchHistoryBean.getCheckBoxReminder()[0], searchHistoryBean.getCheckBoxReminder()[1]);
        }

        // Project Flag
        if (searchHistoryBean.getCheckBoxProjectFlag().length == 1) {
            searchQuery.where("e.dai_anken_flg = ?", searchHistoryBean.getCheckBoxProjectFlag()[0]);
        } else if (searchHistoryBean.getCheckBoxProjectFlag().length == 2) {
            searchQuery.where("e.dai_anken_flg = ? OR e.dai_anken_flg = ?",
                searchHistoryBean.getCheckBoxProjectFlag()[0], searchHistoryBean.getCheckBoxProjectFlag()[1]);
        }
        // Open Flag
        if (searchHistoryBean.getCheckBoxOpenFlag().length == 1) {
            searchQuery.where("e.dai_matter_flg =?", searchHistoryBean.getCheckBoxOpenFlag()[0]);
        } else if (searchHistoryBean.getCheckBoxOpenFlag().length == 2) {
            searchQuery.where("e.dai_matter_flg = ? OR e.dai_matter_flg = ?",
                searchHistoryBean.getCheckBoxOpenFlag()[0], searchHistoryBean.getCheckBoxOpenFlag()[1]);
        }

        if (StringUtils.isNotEmpty(searchHistoryBean.getInputTextCompanyActivity())) {
            searchQuery.where("e.dai_business_details LIKE ?", StringUtils
                .buildLikeValueExpression(StringUtils.nullTrim(searchHistoryBean.getInputTextCompanyActivity())));
        }
        if (StringUtils.isNotEmpty(searchHistoryBean.getCompanyName().toUpperCase())) {
            searchQuery.where("UPPER(c.com_company_name) LIKE ? OR UPPER(c.com_company_furigana) LIKE ?",
                StringUtils
                    .buildLikeValueExpression(StringUtils.nullTrim(searchHistoryBean.getCompanyName()).toUpperCase()),
                StringUtils
                    .buildLikeValueExpression(StringUtils.nullTrim(searchHistoryBean.getCompanyName()).toUpperCase()));
        }
        if (StringUtils.isNotEmpty(searchHistoryBean.getCompanyDepartment())) {
            searchQuery.where("UPPER(e.dai_employee_post) LIKE ?", StringUtils.buildLikeValueExpression(
                StringUtils.nullTrim(searchHistoryBean.getCompanyDepartment()).toUpperCase()));
        }
        if (StringUtils.isNotEmpty(searchHistoryBean.getPersonInChanrge())) {
            searchQuery.where("UPPER(e.dai_compemp_name) LIKE ?", StringUtils
                .buildLikeValueExpression(StringUtils.nullTrim(searchHistoryBean.getPersonInChanrge()).toUpperCase()));
        }
        searchQuery.orderBy("e.dai_work_date ASC, e.dai_work_stime, e.dai_work_etime ASC");

        RepoResult<Daily_report> result = new RepoResult<>();
        result.setListItems(searchQuery.getResultList());
        Long realRecords = (Long) searchQuery.getJPACountQuery().getSingleResult();
        result.setTotalItems(realRecords);
        return result;
    }

    @Query(
        value = "SELECT COUNT(DISTINCT dai_work_date ) FROM Daily_report WHERE dai_employee_code = ?1 AND dai_work_date BETWEEN ?2 AND ?3")
    public abstract QueryResult<Long> getWorkingDaysOfEmployeeByStartDateAndEndDate(int emp_code, Date startDate,
        Date endDate);

    @Query(
        value = "SELECT COUNT(e) FROM Daily_report e WHERE e.dai_employee_code = ?1 AND e.dai_work_date BETWEEN ?2 AND ?3")
    public abstract QueryResult<Long> countTotalDailyReportOfEmployeeByStartDateAndEndDate(int emp_code, Date startDate,
        Date endDate);

    @Query("SELECT DISTINCT(d.dai_work_date) FROM Daily_report d WHERE d.dai_employee_code = ?1 AND d.dai_work_date BETWEEN ?2 and ?3")
    public abstract QueryResult<Date> getListActualWorkingDay(int employeeCode, Date startDate, Date endDate);

    @Query("SELECT COUNT(d.dai_bus_code) FROM Daily_report d WHERE d.dai_employee_code = ?1 AND d.dai_work_date between ?2 AND ?3")
    public abstract int getTotalVisitTimeOfMonthlyReport(int employeeCode, Date startDate, Date endDate);

    @Query("SELECT COUNT(d) FROM Daily_report d WHERE d.dai_company_code = ?1 AND d.dai_comppoint_code =?2 AND d.dai_compemp_name =?3 AND d.dai_employee_post = ?4")
    public abstract int getCountDailyReportByEmpCodeAndAddressCodeAndEmpNameAndEmpPost(String companyCode,
        String daiComppointCode, String empName, String empPost);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}