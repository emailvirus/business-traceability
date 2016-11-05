//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Notification_item;

@StaticMetamodel(Notification_data_MAPPED.class)
public class Notification_data_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Notification_data_MAPPED, Notification_data_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Notification_data_MAPPED, java.lang.Integer> nd_employee_code;
    public static volatile SingularAttribute<Notification_data_MAPPED, java.lang.Integer> nd_target_employee;
    public static volatile SingularAttribute<Notification_data_MAPPED, java.lang.Integer> nd_item_key;
    public static volatile SingularAttribute<Notification_data_MAPPED, java.lang.String> nd_type;
    public static volatile SingularAttribute<Notification_data_MAPPED, Employee_mst> employee_mst;
    public static volatile SingularAttribute<Notification_data_MAPPED, Notification_item> notification_item;
}