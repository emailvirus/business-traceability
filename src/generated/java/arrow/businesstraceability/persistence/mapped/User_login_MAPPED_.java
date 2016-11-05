//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Employee_mst;

@StaticMetamodel(User_login_MAPPED.class)
public class User_login_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<User_login_MAPPED, User_login_MAPPED.Pk> pk;
    public static volatile SingularAttribute<User_login_MAPPED, java.lang.Integer> ul_user_code;
    public static volatile SingularAttribute<User_login_MAPPED, java.lang.String> ul_locale;
    public static volatile SingularAttribute<User_login_MAPPED, java.util.Date> ul_login_time;
    public static volatile SingularAttribute<User_login_MAPPED, java.lang.String> ul_passsalt;
    public static volatile SingularAttribute<User_login_MAPPED, java.lang.String> ul_password;
    public static volatile SingularAttribute<User_login_MAPPED, java.lang.String> ul_session_id;
    public static volatile SingularAttribute<User_login_MAPPED, java.util.Date> ul_update_date;
    public static volatile SingularAttribute<User_login_MAPPED, Employee_mst> employee_mst;
}