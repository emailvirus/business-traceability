//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.Date;
import java.util.List;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.mapped.Addresspoint_mst_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Addresspoint_mstRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Addresspoint_mst, Addresspoint_mst_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    /**
     * Gets the distinct address point of sales person base on customer code.
     *
     * @param companyCode the company code
     * @return the distinct address point of sales person base on customer code
     */
    @Query("SELECT DISTINCT ap FROM Addresspoint_mst ap, Daily_report r WHERE ap.pk.adp_code = r.dai_point_code AND"
            + " r.dai_company_code = ?1 ORDER BY ap.pk.adp_code ASC")
    public abstract List<Addresspoint_mst> getDistinctAddressPointOfSalesPersonBaseOnCustomerCode(
            final String companyCode);

    @Query("select a from Addresspoint_mst a where a.adp_code = ?1")
    public abstract QueryResult<Addresspoint_mst> findAddresspointByCode(String addresspoinCode);

    @Query("SELECT a.adp_code FROM Employee_mst e, Addresspoint_mst a WHERE e.emp_adpcode = a.adp_code AND e.emp_code = ?1")
    public abstract String getBranchCodeOfEmployee(int employeeCode);

    @Query("SELECT DISTINCT a FROM Addresspoint_mst a, Daily_report r  WHERE a.adp_code = r.dai_point_code AND r.dai_company_code = ?1 "
            + " AND r.dai_work_date >= ?2 AND r.dai_work_date <= ?3 " + " ORDER BY a.adp_code ASC")
    public abstract QueryResult<Addresspoint_mst> getAddressByCompanyCode(String companyCode, Date startDate,
            Date endDate);

    @Query("SELECT a.adp_code FROM Employee_mst e, Addresspoint_mst a WHERE e.emp_adpcode = a.adp_code AND e.emp_code = ?1")
    public abstract String getBranchNameOfEmployee(int employeeCode);

    @Query(value = "SELECT DISTINCT ad FROM Employee_mst em, Addresspoint_mst ad "
            + "WHERE em.emp_adpcode = ad.adp_code AND em.emp_delete_flg = false ORDER BY ad.adp_code ASC")
    public abstract QueryResult<Addresspoint_mst> getListPositionOfEmployee();

    @Query(value = "SELECT DISTINCT ad FROM Employee_mst em, Addresspoint_mst ad "
            + "WHERE em.emp_adpcode = ad.adp_code ORDER BY ad.adp_code")
    public abstract QueryResult<Addresspoint_mst> getAllAddressPointJoinWithEmployee();

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}