//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Company_mst;

@StaticMetamodel(Industry_big_info_MAPPED.class)
public class Industry_big_info_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Industry_big_info_MAPPED, Industry_big_info_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Industry_big_info_MAPPED, java.lang.String> inb_big_code;
    public static volatile SingularAttribute<Industry_big_info_MAPPED, java.lang.String> inb_company_code;
    public static volatile SingularAttribute<Industry_big_info_MAPPED, java.lang.Boolean> inb_delete_flg;
    public static volatile SingularAttribute<Industry_big_info_MAPPED, java.util.Date> inb_update_date;
    public static volatile SingularAttribute<Industry_big_info_MAPPED, Company_mst> company_mst;
}