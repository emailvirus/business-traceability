//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.San_com_branch;
import arrow.businesstraceability.persistence.mapped.San_com_branch_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class San_com_branchRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<San_com_branch, San_com_branch_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT COALESCE(MAX(b.id_branch), 0) FROM San_com_branch b")
    public abstract QueryResult<Integer> getMaxSanComBranchId();

    @Query("select b from San_com_branch b where b.id_company = ?1 and b.name_branch = ?2")
    public abstract QueryResult<San_com_branch> getSanComBranchByCompanyIdAndBranchName(String compId,
        String branchName);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}