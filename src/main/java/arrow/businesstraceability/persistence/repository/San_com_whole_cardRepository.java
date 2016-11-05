//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.San_com_whole_card;
import arrow.businesstraceability.persistence.mapped.San_com_whole_card_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class San_com_whole_cardRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<San_com_whole_card, San_com_whole_card_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Modifying
    @Query(value = "DELETE FROM San_com_whole_card WHERE id_company= ?1")
    public abstract void deleteAllRecordByCompanyId(String id_company);

    @Query(value = "SELECT COALESCE(MAX(id_wholecard_indxer), 0) FROM San_com_whole_card b")
    public abstract int getMaxIdWholeCard();


    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}