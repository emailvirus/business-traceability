//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Captital_level_mst_MAPPED.class)
public class Captital_level_mst_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Captital_level_mst_MAPPED, Captital_level_mst_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Captital_level_mst_MAPPED, java.lang.Integer> cal_level;
    public static volatile SingularAttribute<Captital_level_mst_MAPPED, java.lang.String> cal_grade;
}