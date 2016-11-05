//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Addresspoint_mst_MAPPED.class)
public class Addresspoint_mst_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Addresspoint_mst_MAPPED, Addresspoint_mst_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Addresspoint_mst_MAPPED, java.lang.String> adp_code;
    public static volatile SingularAttribute<Addresspoint_mst_MAPPED, java.lang.String> adp_name;
}