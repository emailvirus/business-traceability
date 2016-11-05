//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Industry_big_mst;
import arrow.businesstraceability.persistence.mapped.Industry_big_mst_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Industry_big_mstRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Industry_big_mst, Industry_big_mst_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT i FROM Industry_big_mst i ORDER BY big_code ASC")
    public abstract QueryResult<Industry_big_mst> getAllIndustryBigMst();

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}