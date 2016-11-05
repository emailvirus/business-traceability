//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Company_mst;

@StaticMetamodel(San_com_info_MAPPED.class)
public class San_com_info_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<San_com_info_MAPPED, San_com_info_MAPPED.Pk> pk;
    public static volatile SingularAttribute<San_com_info_MAPPED, java.lang.String> id_san_company;
    public static volatile SingularAttribute<San_com_info_MAPPED, java.lang.Integer> cn_update;
    public static volatile SingularAttribute<San_com_info_MAPPED, java.lang.Integer> id_company;
    public static volatile SingularAttribute<San_com_info_MAPPED, java.lang.String> id_sarscom;
    public static volatile SingularAttribute<San_com_info_MAPPED, java.lang.String> mynum_com;
    public static volatile SingularAttribute<San_com_info_MAPPED, java.lang.String> name_company;
    public static volatile SingularAttribute<San_com_info_MAPPED, java.lang.String> name_com_eng;
    public static volatile SingularAttribute<San_com_info_MAPPED, java.lang.String> name_com_kana;
    public static volatile SingularAttribute<San_com_info_MAPPED, java.util.Date> ts_create;
    public static volatile SingularAttribute<San_com_info_MAPPED, java.util.Date> ts_export;
    public static volatile SingularAttribute<San_com_info_MAPPED, java.util.Date> ts_update;
    public static volatile SingularAttribute<San_com_info_MAPPED, Company_mst> company_mst;
}