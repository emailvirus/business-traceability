//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Notification_item_MAPPED.class)
public class Notification_item_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Notification_item_MAPPED, Notification_item_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Notification_item_MAPPED, java.lang.Integer> ni_item_key;
    public static volatile SingularAttribute<Notification_item_MAPPED, java.util.Date> ni_created_at;
    public static volatile SingularAttribute<Notification_item_MAPPED, java.lang.Integer> ni_employee_code;
    public static volatile SingularAttribute<Notification_item_MAPPED, java.lang.String> ni_point_code;
    public static volatile SingularAttribute<Notification_item_MAPPED, java.lang.String> ni_type;
    public static volatile SingularAttribute<Notification_item_MAPPED, java.util.Date> ni_work_date;
    public static volatile SingularAttribute<Notification_item_MAPPED, java.util.Date> ni_work_etime;
    public static volatile SingularAttribute<Notification_item_MAPPED, java.util.Date> ni_work_stime;
}