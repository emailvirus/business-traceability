//@formatter:off
package arrow.businesstraceability.persistence.mapped;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.entity.Acc_com_entity;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Employee_mst;

@StaticMetamodel(Acc_com_credit_MAPPED.class)
public class Acc_com_credit_MAPPED_ extends Abstract_entity_MAPPED_ {
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, Acc_com_credit_MAPPED.Pk> pk;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.Integer> id_credit;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> acc_direction;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.Integer> ac_final_authorize;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.Integer> ac_middle_authorize;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.Integer> ac_request;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> ac_request_branch;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> addr_all_hq;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> addr_all_hq2;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> addr_city;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> addr_pref;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.Integer> capital;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> cause_decision;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> code_acc_bankacc;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> code_acc_capital;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> code_acc_creditscore;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> code_acc_fiscalmonth;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> code_acc_market;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> code_acc_payment;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> code_acc_prohibitcause;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> code_acc_resurvey;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> code_acc_surveyer;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> code_acc_suverysite;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> code_acc_tradecond;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> code_zip;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.util.Date> date_authorize;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.util.Date> date_survey;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.util.Date> date_valid_from;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.util.Date> date_valid_to;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> fax_hq;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.Integer> id_com_entity;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> indx_acc;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> indx_com;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> memo1;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> memo2;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> memo3;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> name_company2;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.Integer> n_employee;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> other_direction;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> path_report;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.Integer> score_credit;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.Character> status;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> tel_hq;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, java.lang.String> url;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, Acc_com_entity> acc_com_entity;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, Addresspoint_mst> addresspoint_mst;
    public static volatile SingularAttribute<Acc_com_credit_MAPPED, Employee_mst> employee_mst;
}