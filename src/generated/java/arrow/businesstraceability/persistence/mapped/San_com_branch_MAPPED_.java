//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.San_card_data;
import arrow.businesstraceability.persistence.entity.San_com_info;

@StaticMetamodel(San_com_branch_MAPPED.class)
public class San_com_branch_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<San_com_branch_MAPPED, San_com_branch_MAPPED.Pk> pk;
    public static volatile SingularAttribute<San_com_branch_MAPPED, java.lang.Integer> id_branch;
    public static volatile SingularAttribute<San_com_branch_MAPPED, java.util.Date> date_latest_exchange;
    public static volatile SingularAttribute<San_com_branch_MAPPED, java.lang.Integer> id_card;
    public static volatile SingularAttribute<San_com_branch_MAPPED, java.lang.Integer> id_company;
    public static volatile SingularAttribute<San_com_branch_MAPPED, java.lang.String> name_branch;
    public static volatile SingularAttribute<San_com_branch_MAPPED, java.lang.Integer> n_dupinfo;
    public static volatile SingularAttribute<San_com_branch_MAPPED, San_com_info> san_com_info;
    public static volatile SingularAttribute<San_com_branch_MAPPED, San_card_data> san_card_data;
}