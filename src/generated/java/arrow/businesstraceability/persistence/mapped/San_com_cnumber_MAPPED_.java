//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.San_card_data;
import arrow.businesstraceability.persistence.entity.San_com_branch;
import arrow.businesstraceability.persistence.entity.San_com_info;
import arrow.businesstraceability.persistence.entity.San_com_site;

@StaticMetamodel(San_com_cnumber_MAPPED.class)
public class San_com_cnumber_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<San_com_cnumber_MAPPED, San_com_cnumber_MAPPED.Pk> pk;
    public static volatile SingularAttribute<San_com_cnumber_MAPPED, java.lang.Integer> id_cnumber;
    public static volatile SingularAttribute<San_com_cnumber_MAPPED, java.util.Date> date_latest_exchange;
    public static volatile SingularAttribute<San_com_cnumber_MAPPED, java.lang.String> fax;
    public static volatile SingularAttribute<San_com_cnumber_MAPPED, java.lang.Integer> id_branch;
    public static volatile SingularAttribute<San_com_cnumber_MAPPED, java.lang.Integer> id_card;
    public static volatile SingularAttribute<San_com_cnumber_MAPPED, java.lang.Integer> id_company;
    public static volatile SingularAttribute<San_com_cnumber_MAPPED, java.lang.Integer> id_site;
    public static volatile SingularAttribute<San_com_cnumber_MAPPED, java.lang.Integer> n_dupinfo;
    public static volatile SingularAttribute<San_com_cnumber_MAPPED, java.lang.String> tel11;
    public static volatile SingularAttribute<San_com_cnumber_MAPPED, San_com_info> san_com_info;
    public static volatile SingularAttribute<San_com_cnumber_MAPPED, San_card_data> san_card_data;
    public static volatile SingularAttribute<San_com_cnumber_MAPPED, San_com_branch> san_com_branch;
    public static volatile SingularAttribute<San_com_cnumber_MAPPED, San_com_site> san_com_site;
}