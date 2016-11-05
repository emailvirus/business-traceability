//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.San_com_live_owner;
import arrow.businesstraceability.persistence.mapped.San_com_live_owner_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class San_com_live_ownerRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<San_com_live_owner, San_com_live_owner_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */


    @Modifying
    @Query(value = "DELETE FROM San_com_live_owner WHERE id_company =?1")
    public abstract void deleteAllRecordByCompanyId(String idComp);

    @Query(value = "SELECT COALESCE(MAX(id_cardowner), 0) FROM San_com_live_owner b")
    public abstract int getMaxIdCardOwner();

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}