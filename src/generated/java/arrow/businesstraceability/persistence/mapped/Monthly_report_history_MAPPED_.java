//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Monthly_report_revision;

@StaticMetamodel(Monthly_report_history_MAPPED.class)
public class Monthly_report_history_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Monthly_report_history_MAPPED, Monthly_report_history_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Monthly_report_history_MAPPED, java.lang.Integer> bajon_bangou;
    public static volatile SingularAttribute<Monthly_report_history_MAPPED, java.lang.Integer> shain_kodo;
    public static volatile SingularAttribute<Monthly_report_history_MAPPED, java.lang.Integer> repoto_no_getsudo;
    public static volatile SingularAttribute<Monthly_report_history_MAPPED, java.lang.Integer> repoto_no_nendo;
    public static volatile SingularAttribute<Monthly_report_history_MAPPED, java.lang.String> sousa;
    public static volatile SingularAttribute<Monthly_report_history_MAPPED, java.lang.String> comment;
    public static volatile SingularAttribute<Monthly_report_history_MAPPED, java.lang.Integer> shouninsha_kodo;
    public static volatile SingularAttribute<Monthly_report_history_MAPPED, java.util.Date> sousa_jiten;
    public static volatile SingularAttribute<Monthly_report_history_MAPPED, Monthly_report_revision> monthly_report_revision;
    public static volatile SingularAttribute<Monthly_report_history_MAPPED, Employee_mst> employee_mst;
}