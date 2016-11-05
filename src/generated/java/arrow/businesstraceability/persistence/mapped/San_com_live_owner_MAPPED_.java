//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.San_card_data;
import arrow.businesstraceability.persistence.entity.San_com_info;

@StaticMetamodel(San_com_live_owner_MAPPED.class)
public class San_com_live_owner_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<San_com_live_owner_MAPPED, San_com_live_owner_MAPPED.Pk> pk;
    public static volatile SingularAttribute<San_com_live_owner_MAPPED, java.lang.Integer> id_company;
    public static volatile SingularAttribute<San_com_live_owner_MAPPED, java.lang.String> ac_user;
    public static volatile SingularAttribute<San_com_live_owner_MAPPED, java.lang.Integer> cn_card_perowner;
    public static volatile SingularAttribute<San_com_live_owner_MAPPED, java.lang.Integer> cn_cp;
    public static volatile SingularAttribute<San_com_live_owner_MAPPED, java.lang.Integer> cn_vp;
    public static volatile SingularAttribute<San_com_live_owner_MAPPED, java.util.Date> date_latest_exchange;
    public static volatile SingularAttribute<San_com_live_owner_MAPPED, java.lang.Integer> id_card;
    public static volatile SingularAttribute<San_com_live_owner_MAPPED, java.lang.Integer> maxn_cp;
    public static volatile SingularAttribute<San_com_live_owner_MAPPED, java.lang.Integer> maxn_vp;
    public static volatile SingularAttribute<San_com_live_owner_MAPPED, java.lang.String> name_user;
    public static volatile SingularAttribute<San_com_live_owner_MAPPED, San_com_info> san_com_info;
    public static volatile SingularAttribute<San_com_live_owner_MAPPED, San_card_data> san_card_data;
}