//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.San_card_data;
import arrow.businesstraceability.persistence.mapped.San_card_data_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class San_card_dataRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<San_card_data, San_card_data_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */


    @Query("SELECT s FROM San_card_data s WHERE s.id_card = ?1 ")
    public abstract San_card_data findSancardDataByIdCard(String id);


    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}