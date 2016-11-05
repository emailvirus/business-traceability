//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Employee_mst;

@StaticMetamodel(Notification_config_MAPPED.class)
public class Notification_config_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Notification_config_MAPPED, Notification_config_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Notification_config_MAPPED, java.lang.Integer> nc_employee_code;
    public static volatile SingularAttribute<Notification_config_MAPPED, java.lang.Integer> nc_target_employee;
    public static volatile SingularAttribute<Notification_config_MAPPED, java.lang.Boolean> nc_enable;
    public static volatile SingularAttribute<Notification_config_MAPPED, java.lang.String> nc_type;
    public static volatile SingularAttribute<Notification_config_MAPPED, Employee_mst> employee_mst;
}