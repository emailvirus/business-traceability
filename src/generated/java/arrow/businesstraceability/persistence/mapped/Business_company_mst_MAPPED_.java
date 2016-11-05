//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Company_mst;

@StaticMetamodel(Business_company_mst_MAPPED.class)
public class Business_company_mst_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Business_company_mst_MAPPED, Business_company_mst_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Business_company_mst_MAPPED, java.lang.String> buc_business_code;
    public static volatile SingularAttribute<Business_company_mst_MAPPED, java.lang.String> buc_company_code;
    public static volatile SingularAttribute<Business_company_mst_MAPPED, java.lang.Boolean> buc_delete_flg;
    public static volatile SingularAttribute<Business_company_mst_MAPPED, java.util.Date> buc_update_date;
    public static volatile SingularAttribute<Business_company_mst_MAPPED, Company_mst> company_mst;
}