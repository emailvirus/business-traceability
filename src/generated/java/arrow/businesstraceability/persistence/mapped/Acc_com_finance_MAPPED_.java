//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Acc_com_credit;
import arrow.businesstraceability.persistence.entity.Acc_com_entity;

@StaticMetamodel(Acc_com_finance_MAPPED.class)
public class Acc_com_finance_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, Acc_com_finance_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, java.lang.Integer> id_finance;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, java.lang.Integer> current_profit;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, java.lang.Short> flg_fstatement;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, java.lang.Integer> gross_profit;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, java.lang.Integer> id_com_entity;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, java.lang.Integer> id_credit;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, java.lang.Integer> m_fclose;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, java.lang.Integer> op_profit;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, java.lang.Integer> posttax_profit;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, java.lang.Integer> pretax_profit;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, java.lang.Integer> price_cost;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, java.lang.Integer> r_capital;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, java.lang.Integer> sales_amount;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, java.lang.Integer> sga_cost;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, java.lang.Integer> y_fclose;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, Acc_com_credit> acc_com_credit;
    public static volatile SingularAttribute<Acc_com_finance_MAPPED, Acc_com_entity> acc_com_entity;
}