//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Tdb_detail_industry_MAPPED.class)
public class Tdb_detail_industry_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Tdb_detail_industry_MAPPED, Tdb_detail_industry_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Tdb_detail_industry_MAPPED, java.lang.String> code;
    public static volatile SingularAttribute<Tdb_detail_industry_MAPPED, java.lang.String> parent_code;
    public static volatile SingularAttribute<Tdb_detail_industry_MAPPED, java.lang.String> title;
}