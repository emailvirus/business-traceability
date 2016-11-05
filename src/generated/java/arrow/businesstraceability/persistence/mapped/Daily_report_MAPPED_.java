//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Daily_activity_type;
import arrow.businesstraceability.persistence.entity.Employee_mst;

@StaticMetamodel(Daily_report_MAPPED.class)
public class Daily_report_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Daily_report_MAPPED, Daily_report_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.lang.Integer> dai_employee_code;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.lang.String> dai_point_code;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.util.Date> dai_work_date;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.util.Date> dai_work_stime;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.util.Date> dai_work_etime;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.lang.Boolean> dai_anken_flg;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.lang.String> dai_business_details;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.lang.Short> dai_bus_code;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.lang.String> dai_company_code;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.lang.String> dai_compemp_name;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.lang.String> dai_comppoint_code;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.lang.String> dai_employee_post;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.lang.Boolean> dai_matter_flg;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.util.Date> dai_required_time;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.lang.Boolean> dai_rimaindar_flg;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.util.Date> dai_summary_etime;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.util.Date> dai_summary_stime;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.util.Date> dai_update_date;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.lang.Short> dai_work_tancode;
    public static volatile SingularAttribute<Daily_report_MAPPED, java.lang.Boolean> dai_work_type;
    public static volatile SingularAttribute<Daily_report_MAPPED, Employee_mst> employee_mst;
    public static volatile SingularAttribute<Daily_report_MAPPED, Addresspoint_mst> addresspoint_mst;
    public static volatile SingularAttribute<Daily_report_MAPPED, Company_mst> company_mst;
    public static volatile SingularAttribute<Daily_report_MAPPED, Daily_activity_type> daily_activity_type;
}