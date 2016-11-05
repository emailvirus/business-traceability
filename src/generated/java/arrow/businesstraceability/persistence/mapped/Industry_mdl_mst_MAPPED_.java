//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Industry_mdl_mst_MAPPED.class)
public class Industry_mdl_mst_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Industry_mdl_mst_MAPPED, Industry_mdl_mst_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Industry_mdl_mst_MAPPED, java.lang.Short> mdl_code;
    public static volatile SingularAttribute<Industry_mdl_mst_MAPPED, java.lang.Short> mdl_md_code;
    public static volatile SingularAttribute<Industry_mdl_mst_MAPPED, java.lang.String> mdl_name;
}