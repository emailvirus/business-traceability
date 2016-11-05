//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Industry_sml_mst_MAPPED.class)
public class Industry_sml_mst_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Industry_sml_mst_MAPPED, Industry_sml_mst_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Industry_sml_mst_MAPPED, java.lang.Short> sml_code;
    public static volatile SingularAttribute<Industry_sml_mst_MAPPED, java.lang.Short> sml_md_code;
    public static volatile SingularAttribute<Industry_sml_mst_MAPPED, java.lang.Short> sml_sm_code;
    public static volatile SingularAttribute<Industry_sml_mst_MAPPED, java.lang.String> sml_name;
}