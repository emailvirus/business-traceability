//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Notification_data;
import arrow.businesstraceability.persistence.mapped.Notification_data_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Notification_dataRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Notification_data, Notification_data_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT nd FROM Notification_data nd WHERE nd.nd_employee_code = ?1 AND nd.nd_target_employee = ?2")
    public abstract QueryResult<Notification_data> findNotificationDataByEmpCodeAndTargetCode(int empCode,
        int targetEmpCode);

    @Query("SELECT nd FROM Notification_data nd WHERE nd.nd_item_key = ?1")
    public abstract QueryResult<Notification_data> findNotificationDataByItemKey(int itemKey);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}