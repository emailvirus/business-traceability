//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Authority_mst;
import arrow.businesstraceability.persistence.entity.Position_mst;

@StaticMetamodel(Employee_mst_MAPPED.class)
public class Employee_mst_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Employee_mst_MAPPED, Employee_mst_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Employee_mst_MAPPED, java.lang.Integer> emp_code;
    public static volatile SingularAttribute<Employee_mst_MAPPED, java.lang.String> emp_adpcode;
    public static volatile SingularAttribute<Employee_mst_MAPPED, java.lang.Character> emp_condi_code;
    public static volatile SingularAttribute<Employee_mst_MAPPED, java.lang.Boolean> emp_delete_flg;
    public static volatile SingularAttribute<Employee_mst_MAPPED, java.util.Date> emp_entery_date;
    public static volatile SingularAttribute<Employee_mst_MAPPED, java.lang.String> emp_mail;
    public static volatile SingularAttribute<Employee_mst_MAPPED, java.lang.String> emp_mobile;
    public static volatile SingularAttribute<Employee_mst_MAPPED, java.lang.String> emp_name;
    public static volatile SingularAttribute<Employee_mst_MAPPED, java.lang.Short> emp_poscode;
    public static volatile SingularAttribute<Employee_mst_MAPPED, java.lang.String> emp_post;
    public static volatile SingularAttribute<Employee_mst_MAPPED, java.lang.Short> emp_settle_authority;
    public static volatile SingularAttribute<Employee_mst_MAPPED, java.lang.Boolean> emp_system_authority;
    public static volatile SingularAttribute<Employee_mst_MAPPED, java.lang.String> emp_tel;
    public static volatile SingularAttribute<Employee_mst_MAPPED, java.util.Date> emp_update_date;
    public static volatile SingularAttribute<Employee_mst_MAPPED, Position_mst> position_mst;
    public static volatile SingularAttribute<Employee_mst_MAPPED, Addresspoint_mst> addresspoint_mst;
    public static volatile SingularAttribute<Employee_mst_MAPPED, Authority_mst> authority_mst;
}