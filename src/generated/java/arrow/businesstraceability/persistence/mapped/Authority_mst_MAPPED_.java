//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Authority_mst_MAPPED.class)
public class Authority_mst_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Authority_mst_MAPPED, Authority_mst_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Authority_mst_MAPPED, java.lang.Short> aut_code;
    public static volatile SingularAttribute<Authority_mst_MAPPED, java.lang.String> aut_name;
}