//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Industry_big_info;

@StaticMetamodel(Industry_mdl_info_MAPPED.class)
public class Industry_mdl_info_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Industry_mdl_info_MAPPED, Industry_mdl_info_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Industry_mdl_info_MAPPED, java.lang.String> inm_company_code;
    public static volatile SingularAttribute<Industry_mdl_info_MAPPED, java.lang.String> inm_big_code;
    public static volatile SingularAttribute<Industry_mdl_info_MAPPED, java.lang.String> inm_mdl_code;
    public static volatile SingularAttribute<Industry_mdl_info_MAPPED, java.lang.Boolean> inm_delete_flg;
    public static volatile SingularAttribute<Industry_mdl_info_MAPPED, java.util.Date> inm_update_date;
    public static volatile SingularAttribute<Industry_mdl_info_MAPPED, Company_mst> company_mst;
    public static volatile SingularAttribute<Industry_mdl_info_MAPPED, Industry_big_info> industry_big_info;
}