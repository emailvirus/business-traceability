//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Notification_item;
import arrow.businesstraceability.persistence.mapped.Notification_item_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Notification_itemRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Notification_item, Notification_item_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT ni FROM Notification_item ni WHERE ni.ni_item_key = ?1")
    public abstract QueryResult<Notification_item> findNotificationItemByItemKey(int itemKey);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}