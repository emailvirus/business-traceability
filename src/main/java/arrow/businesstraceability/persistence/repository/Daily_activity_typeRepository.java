//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.Date;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Daily_activity_type;
import arrow.businesstraceability.persistence.mapped.Daily_activity_type_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Daily_activity_typeRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Daily_activity_type, Daily_activity_type_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT dat FROM Daily_activity_type dat WHERE dat.dat_code <> 0 ORDER BY dat_order ASC")
    public abstract QueryResult<Daily_activity_type> getAllDailyActivityType();

    /**
     * Gets the all purpose when visited company with employee and basepoint.
     *
     * @param companyCode the company code
     * @param empCode the emp code
     * @param adpCode the adp code
     * @return the all purpose when visited company with employee and basepoint
     */
    @Query(value = "SELECT DISTINCT d FROM Daily_activity_type d , Daily_report r "
            + "WHERE d.dat_code = r.dai_work_tancode  AND r.dai_company_code = ?1 AND r.dai_employee_code = ?2 "
            + "AND r.dai_point_code = ?3  " + " AND r.dai_work_date >= ?4 AND r.dai_work_date <= ?5 "
            + " ORDER BY d.dat_name ASC")
    public abstract QueryResult<Daily_activity_type> getAllPurposeWhenVisitedCompanyWithEmployeeAndBasepoint(
            final String companyCode, final int empCode, final String adpCode, Date startDate, Date endDate);

    /**
     * Gets the all purpose when visited company with employee and basepoint.
     *
     * @param companyCode the company code
     * @param empCode the emp code
     * @param adpCode the adp code
     * @return the all purpose when visited company with employee and basepoint
     */
    @Query(
            value = "SELECT DISTINCT d FROM Daily_activity_type d , Daily_report r, Employee_mst e "
                    + "WHERE d.dat_code = r.dai_work_tancode AND e.emp_code = r.dai_employee_code AND e.emp_delete_flg = false "
                    + "AND r.dai_company_code = ?1 AND r.dai_point_code = ?2 "
                    + " AND r.dai_work_date >= ?3 AND r.dai_work_date <= ?4 " + " ORDER BY d.dat_code ASC")
    public abstract QueryResult<Daily_activity_type> getAllPurposeWhenVisitedCompanyWithBasepoint(
            final String companyCode, final String adpCode, Date startDate, Date endDate);

    @Query(value = "SELECT d FROM Daily_activity_type d WHERE d.dat_code <> 0 ORDER BY d.dat_code ASC")
    public abstract QueryResult<Daily_activity_type> getAllDailyActivityTypeAndOrderByDatCodeAsc();

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}