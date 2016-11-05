//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Personnel_affairs_info_MAPPED.class)
public class Personnel_affairs_info_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Personnel_affairs_info_MAPPED, Personnel_affairs_info_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Personnel_affairs_info_MAPPED, java.lang.String> pea_business_code;
    public static volatile SingularAttribute<Personnel_affairs_info_MAPPED, java.util.Date> pea_date;
    public static volatile SingularAttribute<Personnel_affairs_info_MAPPED, java.lang.String> pea_compemp_name;
    public static volatile SingularAttribute<Personnel_affairs_info_MAPPED, java.lang.String> pea_comppoint_code;
    public static volatile SingularAttribute<Personnel_affairs_info_MAPPED, java.lang.String> pea_details;
    public static volatile SingularAttribute<Personnel_affairs_info_MAPPED, java.lang.String> pea_employee_post;
    public static volatile SingularAttribute<Personnel_affairs_info_MAPPED, java.lang.String> pea_emp_adpcode;
    public static volatile SingularAttribute<Personnel_affairs_info_MAPPED, java.lang.Integer> pea_emp_code;
    public static volatile SingularAttribute<Personnel_affairs_info_MAPPED, java.util.Date> pea_update_date;
}