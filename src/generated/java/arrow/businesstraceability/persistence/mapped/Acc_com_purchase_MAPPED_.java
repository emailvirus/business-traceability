//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Acc_com_credit;

@StaticMetamodel(Acc_com_purchase_MAPPED.class)
public class Acc_com_purchase_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Acc_com_purchase_MAPPED, Acc_com_purchase_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Acc_com_purchase_MAPPED, java.lang.Integer> id_com_relation;
    public static volatile SingularAttribute<Acc_com_purchase_MAPPED, java.lang.String> code_acc_surveyer;
    public static volatile SingularAttribute<Acc_com_purchase_MAPPED, java.util.Date> date_survey;
    public static volatile SingularAttribute<Acc_com_purchase_MAPPED, java.lang.Character> enum_com_relation;
    public static volatile SingularAttribute<Acc_com_purchase_MAPPED, java.lang.Integer> id_credit;
    public static volatile SingularAttribute<Acc_com_purchase_MAPPED, java.lang.String> indx_customer;
    public static volatile SingularAttribute<Acc_com_purchase_MAPPED, java.lang.String> name_com_relation;
    public static volatile SingularAttribute<Acc_com_purchase_MAPPED, Acc_com_credit> acc_com_credit;
}