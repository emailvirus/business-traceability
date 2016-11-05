//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.Date;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.mapped.Employee_mst_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Employee_mstRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Employee_mst, Employee_mst_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT e FROM Employee_mst e WHERE e.emp_code = ?1")
    public abstract QueryResult<Employee_mst> findEmployeeByCode(int employeeCode);

    /**
     * Gets the all employee went to visited company with basepoint.
     *
     * @param companyCode the company code
     * @param adpCode the adp code
     * @return the all employee went to visited company with basepoint
     */
    @Query(value = "SELECT DISTINCT e FROM Employee_mst e , Daily_report r "
        + "WHERE e.emp_code = r.dai_employee_code AND r.dai_company_code = ?1 AND r.dai_point_code = ?2 "
        + "AND e.emp_delete_flg = FALSE " + " AND r.dai_work_date >= ?3 AND r.dai_work_date <= ?4 "
        + " ORDER BY e.emp_name ASC ")
    public abstract QueryResult<Employee_mst> getAllEmployeeWentToVisitedCompanyWithBasepoint(final String companyCode,
        final String adpCode, Date startDate, Date endDate);

    @Query(value = "SELECT e FROM Employee_mst e "
        + "WHERE e.emp_delete_flg = false AND (e.emp_condi_code = '0' OR e.emp_condi_code IS NULL) ORDER BY e.emp_code")
    public abstract QueryResult<Employee_mst> getAllActiveEmpByCondiCodeIsNullOrZero();

    @Query(value = "SELECT e FROM Employee_mst e WHERE e.emp_delete_flg = false ORDER BY e.emp_code")
    public abstract QueryResult<Employee_mst> getAllActiveEmp();

    @Query(value = "SELECT DISTINCT em FROM Employee_mst em, Addresspoint_mst ad "
        + "WHERE em.emp_adpcode = ad.adp_code AND em.emp_delete_flg = false "
        + "AND (em.emp_condi_code = '0' OR em.emp_condi_code IS NULL) AND ad.adp_code = ?1")
    public abstract QueryResult<Employee_mst> getAllActiveEmpNotRetiredByAdpCode(String adpCode);

    @Query(value = "SELECT DISTINCT em FROM Employee_mst em, Addresspoint_mst ad "
        + "WHERE em.emp_adpcode = ad.adp_code AND em.emp_delete_flg = false AND ad.adp_code = ?1")
    public abstract QueryResult<Employee_mst> getAllActiveEmpByAdpCode(String adpCode);

    @Query(value = "SELECT DISTINCT em FROM Employee_mst em, Addresspoint_mst ad "
        + "WHERE em.emp_adpcode = ad.adp_code AND em.emp_delete_flg = false "
        + "AND (em.emp_condi_code = '0' OR em.emp_condi_code IS NULL)")
    public abstract QueryResult<Employee_mst> getAllActiveEmpNotRetired();

    @Query(value = "SELECT DISTINCT em FROM Employee_mst em, Addresspoint_mst ad "
        + "WHERE em.emp_adpcode = ad.adp_code AND em.emp_delete_flg = false")
    public abstract QueryResult<Employee_mst> getAllActiveEmpDistinctJoinWithAddressPoint();

    @Query(
        value = "SELECT DISTINCT em FROM Employee_mst em, Addresspoint_mst ad, Position_mst p  "
            + "WHERE em.emp_adpcode = ad.adp_code AND em.emp_poscode = p.pos_code AND ad.adp_code = ?1 AND em.emp_delete_flg = false AND p.pos_code = ?2")
    public abstract QueryResult<Employee_mst> getActiveEmpByAdpCodeAndPostion(final String adpCode, Short pos_code);

    @Query("SELECT e FROM Employee_mst e WHERE e.emp_delete_flg = false AND e.emp_poscode = ?1 AND e.emp_settle_authority = ?2")
    public abstract QueryResult<Employee_mst> getAllActiveEmployeeByPosititonAndAuthority(final short position,
        short authority);


    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}