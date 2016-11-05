//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Notification_config;
import arrow.businesstraceability.persistence.mapped.Notification_config_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Notification_configRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Notification_config, Notification_config_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT nc FROM Notification_config nc WHERE nc.nc_employee_code = ?1 AND nc.nc_target_employee = ?2")
    public abstract QueryResult<Notification_config> findNotificationConfigByEmpCodeAndTargetCode(int empCode,
        int targetCode);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}