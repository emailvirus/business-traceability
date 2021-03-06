//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Company_mst;

@StaticMetamodel(Credit_info_MAPPED.class)
public class Credit_info_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Credit_info_MAPPED, Credit_info_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Credit_info_MAPPED, java.lang.String> crd_company_code;
    public static volatile SingularAttribute<Credit_info_MAPPED, java.util.Date> crd_date;
    public static volatile SingularAttribute<Credit_info_MAPPED, java.lang.Boolean> crd_business_state;
    public static volatile SingularAttribute<Credit_info_MAPPED, java.lang.String> crd_details;
    public static volatile SingularAttribute<Credit_info_MAPPED, java.util.Date> crd_update_date;
    public static volatile SingularAttribute<Credit_info_MAPPED, Company_mst> company_mst;
}