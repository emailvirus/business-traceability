//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.San_card_data;
import arrow.businesstraceability.persistence.entity.San_com_info;

@StaticMetamodel(San_com_live_stat_MAPPED.class)
public class San_com_live_stat_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, San_com_live_stat_MAPPED.Pk> pk;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, java.lang.Integer> id_company;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, java.lang.Integer> cn_attr_correction;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, java.lang.Integer> cn_cp;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, java.lang.Integer> cn_vp;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, java.util.Date> date_latest_exchange;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, java.util.Date> date_oldest_exchange;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, java.lang.Integer> id_latest_card;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, java.lang.Integer> id_max_cp_card;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, java.lang.Integer> id_max_vp_card;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, java.lang.Integer> maxn_cp;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, java.lang.Integer> maxn_vp;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, java.lang.Integer> sn_card;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, java.lang.Integer> sn_client;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, java.lang.Integer> sn_owner;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, San_com_info> san_com_info;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, San_card_data> latestCard;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, San_card_data> maxVPCard;
    public static volatile SingularAttribute<San_com_live_stat_MAPPED, San_card_data> maxCPCard;
}