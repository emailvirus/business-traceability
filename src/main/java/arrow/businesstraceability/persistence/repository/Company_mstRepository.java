//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.Date;
import java.util.List;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.mapped.Company_mst_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Company_mstRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Company_mst, Company_mst_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query(" SELECT e FROM Daily_report d INNER JOIN d.company_mst e " + " WHERE e.com_delete_flg = 'false' "
            + " AND d.dai_work_date >= ?1 AND d.dai_work_date <= ?2 " + " GROUP BY e " + " HAVING COUNT(d) >= ?3 "
            + " ORDER BY e")
    public abstract List<Company_mst> getCompanyForVisitTimeReport(final Date startDate, final Date endDate,
            final long count);

    @Query("SELECT c FROM Company_mst c WHERE c.com_delete_flg = 'false' AND c.com_company_code = ?1")
    public abstract QueryResult<Company_mst> findActiveCompanyByCompanyCode(String companyCode);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}