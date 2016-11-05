//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Employee_mst;

@StaticMetamodel(Business_cons_info_MAPPED.class)
public class Business_cons_info_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Business_cons_info_MAPPED, Business_cons_info_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Business_cons_info_MAPPED, java.lang.String> bco_business_code;
    public static volatile SingularAttribute<Business_cons_info_MAPPED, java.util.Date> bco_date;
    public static volatile SingularAttribute<Business_cons_info_MAPPED, java.lang.String> bco_consideration;
    public static volatile SingularAttribute<Business_cons_info_MAPPED, java.lang.String> bco_emp_adpcode;
    public static volatile SingularAttribute<Business_cons_info_MAPPED, java.lang.Integer> bco_emp_code;
    public static volatile SingularAttribute<Business_cons_info_MAPPED, java.util.Date> bco_update_date;
    public static volatile SingularAttribute<Business_cons_info_MAPPED, Employee_mst> employee_mst;
}