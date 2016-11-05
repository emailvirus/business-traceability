//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Company_mst;

@StaticMetamodel(Basepoint_info_MAPPED.class)
public class Basepoint_info_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Basepoint_info_MAPPED, Basepoint_info_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Basepoint_info_MAPPED, java.lang.String> bas_point_code;
    public static volatile SingularAttribute<Basepoint_info_MAPPED, java.lang.String> bas_company_code;
    public static volatile SingularAttribute<Basepoint_info_MAPPED, java.lang.Boolean> bas_delete_flg;
    public static volatile SingularAttribute<Basepoint_info_MAPPED, java.util.Date> bas_update_date;
    public static volatile SingularAttribute<Basepoint_info_MAPPED, Company_mst> company_mst;
    public static volatile SingularAttribute<Basepoint_info_MAPPED, Addresspoint_mst> addresspoint_mst;
}