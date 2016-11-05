//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Tdb_industry_category_MAPPED.class)
public class Tdb_industry_category_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Tdb_industry_category_MAPPED, Tdb_industry_category_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Tdb_industry_category_MAPPED, java.lang.String> code;
    public static volatile SingularAttribute<Tdb_industry_category_MAPPED, java.lang.String> title;
}