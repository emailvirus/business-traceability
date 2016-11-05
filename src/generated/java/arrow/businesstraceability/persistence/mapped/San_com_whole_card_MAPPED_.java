//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.San_card_data;
import arrow.businesstraceability.persistence.entity.San_com_info;

@StaticMetamodel(San_com_whole_card_MAPPED.class)
public class San_com_whole_card_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<San_com_whole_card_MAPPED, San_com_whole_card_MAPPED.Pk> pk;
    public static volatile SingularAttribute<San_com_whole_card_MAPPED, java.lang.Integer> id_card;
    public static volatile SingularAttribute<San_com_whole_card_MAPPED, java.lang.String> ac_user;
    public static volatile SingularAttribute<San_com_whole_card_MAPPED, java.util.Date> date_card_deletion;
    public static volatile SingularAttribute<San_com_whole_card_MAPPED, java.util.Date> date_exchange;
    public static volatile SingularAttribute<San_com_whole_card_MAPPED, java.util.Date> date_register;
    public static volatile SingularAttribute<San_com_whole_card_MAPPED, java.lang.String> flg_card_deletion;
    public static volatile SingularAttribute<San_com_whole_card_MAPPED, java.lang.Integer> id_company;
    public static volatile SingularAttribute<San_com_whole_card_MAPPED, San_com_info> san_com_info;
    public static volatile SingularAttribute<San_com_whole_card_MAPPED, San_card_data> san_card_data;
}