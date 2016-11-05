//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Branch_position;
import arrow.businesstraceability.persistence.mapped.Branch_position_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Branch_positionRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Branch_position, Branch_position_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query(value = "SELECT b FROM Branch_position b WHERE b.employee_code = ?1 AND b.adp_code = ?2 AND b.delete_flg = ?3")
    public abstract QueryResult<Branch_position> findByEmployeeCodeAndAddressPointCodeAndDeleteFlag(int empCode,
            String adpCode, boolean isDeleted);

    @Query(value = "SELECT b FROM Branch_position b WHERE b.adp_code = ?1 AND b.delete_flg = ?2")
    public abstract QueryResult<Branch_position> findByAdpCode(String adpCode, boolean isDeleted);

    @Query(value = "SELECT COALESCE(MAX(pos_id), 0) FROM Branch_position b")
    public abstract int getMaxId();

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}