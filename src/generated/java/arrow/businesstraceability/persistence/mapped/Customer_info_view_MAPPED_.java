//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Customer_info_view_MAPPED.class)
public class Customer_info_view_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Customer_info_view_MAPPED, Customer_info_view_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.Integer> id;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.String> base_office;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.String> branch_office;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.String> branch_point_code;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.String> com_company_code;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.String> com_company_furigana;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.String> com_company_name;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.String> com_customer_code;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.Short> com_indbig_code;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.Short> com_limited_code;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.Short> com_listed_code;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.String> com_point_code;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.util.Date> com_update_date;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.String> com_url;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.String> dai_compemp_name;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.String> dai_employee_post;
    public static volatile SingularAttribute<Customer_info_view_MAPPED, java.lang.String> field_name;
}