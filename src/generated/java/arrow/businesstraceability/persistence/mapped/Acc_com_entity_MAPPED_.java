//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.San_com_info;

@StaticMetamodel(Acc_com_entity_MAPPED.class)
public class Acc_com_entity_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, Acc_com_entity_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, java.lang.Integer> id_com_entity;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, java.lang.Integer> ac_creation;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, java.lang.Integer> ac_update;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, java.lang.String> code_acc_client;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, java.util.Date> date_creation;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, java.lang.Integer> id_credit;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, java.lang.String> id_san_company;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, java.lang.String> indx_customer;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, java.lang.String> mynum_com;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, java.lang.String> name_company;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, java.lang.String> name_com_kana;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, java.util.Date> ts_export;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, java.util.Date> ts_update;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, java.lang.Integer> y_establish;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, java.lang.Integer> y_start;
    public static volatile SingularAttribute<Acc_com_entity_MAPPED, San_com_info> san_com_info;
}