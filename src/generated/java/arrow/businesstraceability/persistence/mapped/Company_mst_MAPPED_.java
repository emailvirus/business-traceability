//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Captital_level_mst;
import arrow.businesstraceability.persistence.entity.Industry_big_mst;

@StaticMetamodel(Company_mst_MAPPED.class)
public class Company_mst_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Company_mst_MAPPED, Company_mst_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.lang.String> com_company_code;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.lang.Integer> com_capital_level;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.lang.String> com_company_furigana;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.lang.String> com_company_name;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.lang.String> com_customer_code;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.lang.Boolean> com_delete_flg;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.lang.String> com_delete_reason;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.lang.Short> com_indbig_code;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.lang.Short> com_indmdl_code;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.lang.Short> com_indsml_code;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.lang.Short> com_limited_code;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.lang.Short> com_listed_code;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.lang.String> com_point_code;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.lang.String> com_remarks;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.util.Date> com_update_date;
    public static volatile SingularAttribute<Company_mst_MAPPED, java.lang.String> com_url;
    public static volatile SingularAttribute<Company_mst_MAPPED, Captital_level_mst> captital_level_mst;
    public static volatile SingularAttribute<Company_mst_MAPPED, Industry_big_mst> industry_big_mst;
    public static volatile SingularAttribute<Company_mst_MAPPED, Addresspoint_mst> addresspoint_mst;
}