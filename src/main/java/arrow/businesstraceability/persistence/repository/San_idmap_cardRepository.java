//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.San_idmap_card;
import arrow.businesstraceability.persistence.mapped.San_idmap_card_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class San_idmap_cardRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<San_idmap_card, San_idmap_card_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT c.id_san_card FROM San_idmap_card c WHERE c.id_card = ?1")
    public abstract String findIdSanCardByIdCard(final int idCard);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}