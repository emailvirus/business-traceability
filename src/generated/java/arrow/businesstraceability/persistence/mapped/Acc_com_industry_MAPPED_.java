//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Acc_com_credit;
import arrow.businesstraceability.persistence.entity.Acc_com_entity;

@StaticMetamodel(Acc_com_industry_MAPPED.class)
public class Acc_com_industry_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Acc_com_industry_MAPPED, Acc_com_industry_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Acc_com_industry_MAPPED, java.lang.Integer> id_industry;
    public static volatile SingularAttribute<Acc_com_industry_MAPPED, java.lang.String> code_industry_main;
    public static volatile SingularAttribute<Acc_com_industry_MAPPED, java.lang.String> code_industry_sub;
    public static volatile SingularAttribute<Acc_com_industry_MAPPED, java.lang.Integer> id_com_entity;
    public static volatile SingularAttribute<Acc_com_industry_MAPPED, java.lang.Integer> id_credit;
    public static volatile SingularAttribute<Acc_com_industry_MAPPED, java.lang.Integer> pop_industry_nation;
    public static volatile SingularAttribute<Acc_com_industry_MAPPED, java.lang.Integer> pop_industry_pref;
    public static volatile SingularAttribute<Acc_com_industry_MAPPED, java.lang.Integer> rank_industry_nation;
    public static volatile SingularAttribute<Acc_com_industry_MAPPED, java.lang.Integer> rank_industry_pref;
    public static volatile SingularAttribute<Acc_com_industry_MAPPED, java.lang.Integer> ref_industry_code;
    public static volatile SingularAttribute<Acc_com_industry_MAPPED, Acc_com_credit> acc_com_credit;
    public static volatile SingularAttribute<Acc_com_industry_MAPPED, Acc_com_entity> acc_com_entity;
}