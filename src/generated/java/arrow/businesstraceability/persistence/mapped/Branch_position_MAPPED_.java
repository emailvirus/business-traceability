//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Position_mst;

@StaticMetamodel(Branch_position_MAPPED.class)
public class Branch_position_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Branch_position_MAPPED, Branch_position_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Branch_position_MAPPED, java.lang.Integer> pos_id;
    public static volatile SingularAttribute<Branch_position_MAPPED, java.lang.String> adp_code;
    public static volatile SingularAttribute<Branch_position_MAPPED, java.lang.Boolean> delete_flg;
    public static volatile SingularAttribute<Branch_position_MAPPED, java.lang.Integer> employee_code;
    public static volatile SingularAttribute<Branch_position_MAPPED, java.lang.Short> pos_code;
    public static volatile SingularAttribute<Branch_position_MAPPED, Employee_mst> employee_mst;
    public static volatile SingularAttribute<Branch_position_MAPPED, Addresspoint_mst> addresspoint_mst;
    public static volatile SingularAttribute<Branch_position_MAPPED, Position_mst> position_mst;
}