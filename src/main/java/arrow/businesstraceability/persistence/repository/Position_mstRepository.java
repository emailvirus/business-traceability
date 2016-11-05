//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Position_mst;
import arrow.businesstraceability.persistence.mapped.Position_mst_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Position_mstRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Position_mst, Position_mst_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT e FROM Position_mst e WHERE e.pos_code = ?1")
    public abstract Position_mst findByPostCode(short pos_code);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}