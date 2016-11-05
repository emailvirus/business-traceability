//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Basepoint_info;
import arrow.businesstraceability.persistence.mapped.Basepoint_info_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Basepoint_infoRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Basepoint_info, Basepoint_info_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT b FROM Basepoint_info b WHERE b.bas_company_code = ?1 AND b.bas_point_code = ?2")
    public abstract QueryResult<Basepoint_info> findBasepoinByCompanyCodeAndBranchCode(String companyCode,
        String branchCode);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}