//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Daily_activity_type_MAPPED.class)
public class Daily_activity_type_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Daily_activity_type_MAPPED, Daily_activity_type_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Daily_activity_type_MAPPED, java.lang.Short> dat_code;
    public static volatile SingularAttribute<Daily_activity_type_MAPPED, java.lang.String> dat_name;
    public static volatile SingularAttribute<Daily_activity_type_MAPPED, java.lang.Short> dat_order;
}