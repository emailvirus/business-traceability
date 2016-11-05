//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Position_mst_MAPPED.class)
public class Position_mst_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Position_mst_MAPPED, Position_mst_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Position_mst_MAPPED, java.lang.Short> pos_code;
    public static volatile SingularAttribute<Position_mst_MAPPED, java.lang.String> pos_name;
}