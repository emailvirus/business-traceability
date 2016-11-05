//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Acc_com_credit_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Acc_com_credit> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Acc_com_credit.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_credit Data type: int
         *
         */
        public Pk(final int id_credit) {
            this.id_credit = id_credit;
        }

        @Column(name = "ID_CREDIT")
        protected int id_credit;

        public int getId_credit() {
            return this.id_credit;
        }
    }

    //default constructor
    public Acc_com_credit_MAPPED() {
    }

    protected Acc_com_credit_MAPPED(final int id_credit) {
        this.checkFKConsistency(id_credit);
        this.pk = new Pk(id_credit);
        this.id_credit = id_credit;
    }

    private void checkFKConsistency(int id_credit) {
    }

    //Primary Key
    //Should be initialized only once by the constructor, thus there's no setters
    @EmbeddedId
    protected Pk pk;
    @Override
    public Pk getPk() {
        return this.pk;
    }

    //PK columns
    //Should have insertable=false, updatable=false, and no setters

    @Column(insertable = false, updatable = false)
    protected int id_credit;

    public int getId_credit() {
        return this.id_credit;
    }

    //Normal columns

    @Column
    private java.lang.String acc_direction;

    public java.lang.String getAcc_direction() {
        return this.acc_direction;
    }

    public final java.lang.String getAcc_direction_DIRECT() {
        return this.acc_direction;
    }

    public void setAcc_direction(final java.lang.String acc_direction) {
        this.acc_direction = acc_direction;
    }

    public final void setAcc_direction_DIRECT(final java.lang.String acc_direction) {
        this.acc_direction = acc_direction;
    }

    @Column
    private java.lang.Integer ac_final_authorize;

    public java.lang.Integer getAc_final_authorize() {
        return this.ac_final_authorize;
    }

    public final java.lang.Integer getAc_final_authorize_DIRECT() {
        return this.ac_final_authorize;
    }

    public void setAc_final_authorize(final java.lang.Integer ac_final_authorize) {
        this.ac_final_authorize = ac_final_authorize;
    }

    public final void setAc_final_authorize_DIRECT(final java.lang.Integer ac_final_authorize) {
        this.ac_final_authorize = ac_final_authorize;
    }

    @Column
    private java.lang.Integer ac_middle_authorize;

    public java.lang.Integer getAc_middle_authorize() {
        return this.ac_middle_authorize;
    }

    public final java.lang.Integer getAc_middle_authorize_DIRECT() {
        return this.ac_middle_authorize;
    }

    public void setAc_middle_authorize(final java.lang.Integer ac_middle_authorize) {
        this.ac_middle_authorize = ac_middle_authorize;
    }

    public final void setAc_middle_authorize_DIRECT(final java.lang.Integer ac_middle_authorize) {
        this.ac_middle_authorize = ac_middle_authorize;
    }

    @Column
    private int ac_request;

    public int getAc_request() {
        return this.ac_request;
    }

    public final int getAc_request_DIRECT() {
        return this.ac_request;
    }

    public void setAc_request(final int ac_request) {
        this.ac_request = ac_request;
    }

    public final void setAc_request_DIRECT(final int ac_request) {
        this.ac_request = ac_request;
    }

    @Column
    private java.lang.String ac_request_branch;

    public java.lang.String getAc_request_branch() {
        return this.ac_request_branch;
    }

    public final java.lang.String getAc_request_branch_DIRECT() {
        return this.ac_request_branch;
    }

    public void setAc_request_branch(final java.lang.String ac_request_branch) {
        this.ac_request_branch = ac_request_branch;
    }

    public final void setAc_request_branch_DIRECT(final java.lang.String ac_request_branch) {
        this.ac_request_branch = ac_request_branch;
    }

    @Column
    private java.lang.String addr_all_hq;

    public java.lang.String getAddr_all_hq() {
        return this.addr_all_hq;
    }

    public final java.lang.String getAddr_all_hq_DIRECT() {
        return this.addr_all_hq;
    }

    public void setAddr_all_hq(final java.lang.String addr_all_hq) {
        this.addr_all_hq = addr_all_hq;
    }

    public final void setAddr_all_hq_DIRECT(final java.lang.String addr_all_hq) {
        this.addr_all_hq = addr_all_hq;
    }

    @Column
    private java.lang.String addr_all_hq2;

    public java.lang.String getAddr_all_hq2() {
        return this.addr_all_hq2;
    }

    public final java.lang.String getAddr_all_hq2_DIRECT() {
        return this.addr_all_hq2;
    }

    public void setAddr_all_hq2(final java.lang.String addr_all_hq2) {
        this.addr_all_hq2 = addr_all_hq2;
    }

    public final void setAddr_all_hq2_DIRECT(final java.lang.String addr_all_hq2) {
        this.addr_all_hq2 = addr_all_hq2;
    }

    @Column
    private java.lang.String addr_city;

    public java.lang.String getAddr_city() {
        return this.addr_city;
    }

    public final java.lang.String getAddr_city_DIRECT() {
        return this.addr_city;
    }

    public void setAddr_city(final java.lang.String addr_city) {
        this.addr_city = addr_city;
    }

    public final void setAddr_city_DIRECT(final java.lang.String addr_city) {
        this.addr_city = addr_city;
    }

    @Column
    private java.lang.String addr_pref;

    public java.lang.String getAddr_pref() {
        return this.addr_pref;
    }

    public final java.lang.String getAddr_pref_DIRECT() {
        return this.addr_pref;
    }

    public void setAddr_pref(final java.lang.String addr_pref) {
        this.addr_pref = addr_pref;
    }

    public final void setAddr_pref_DIRECT(final java.lang.String addr_pref) {
        this.addr_pref = addr_pref;
    }

    @Column
    private java.lang.Integer capital;

    public java.lang.Integer getCapital() {
        return this.capital;
    }

    public final java.lang.Integer getCapital_DIRECT() {
        return this.capital;
    }

    public void setCapital(final java.lang.Integer capital) {
        this.capital = capital;
    }

    public final void setCapital_DIRECT(final java.lang.Integer capital) {
        this.capital = capital;
    }

    @Column
    private java.lang.String cause_decision;

    public java.lang.String getCause_decision() {
        return this.cause_decision;
    }

    public final java.lang.String getCause_decision_DIRECT() {
        return this.cause_decision;
    }

    public void setCause_decision(final java.lang.String cause_decision) {
        this.cause_decision = cause_decision;
    }

    public final void setCause_decision_DIRECT(final java.lang.String cause_decision) {
        this.cause_decision = cause_decision;
    }

    @Column
    private java.lang.String code_acc_bankacc;

    public java.lang.String getCode_acc_bankacc() {
        return this.code_acc_bankacc;
    }

    public final java.lang.String getCode_acc_bankacc_DIRECT() {
        return this.code_acc_bankacc;
    }

    public void setCode_acc_bankacc(final java.lang.String code_acc_bankacc) {
        this.code_acc_bankacc = code_acc_bankacc;
    }

    public final void setCode_acc_bankacc_DIRECT(final java.lang.String code_acc_bankacc) {
        this.code_acc_bankacc = code_acc_bankacc;
    }

    @Column
    private java.lang.String code_acc_capital;

    public java.lang.String getCode_acc_capital() {
        return this.code_acc_capital;
    }

    public final java.lang.String getCode_acc_capital_DIRECT() {
        return this.code_acc_capital;
    }

    public void setCode_acc_capital(final java.lang.String code_acc_capital) {
        this.code_acc_capital = code_acc_capital;
    }

    public final void setCode_acc_capital_DIRECT(final java.lang.String code_acc_capital) {
        this.code_acc_capital = code_acc_capital;
    }

    @Column
    private java.lang.String code_acc_creditscore;

    public java.lang.String getCode_acc_creditscore() {
        return this.code_acc_creditscore;
    }

    public final java.lang.String getCode_acc_creditscore_DIRECT() {
        return this.code_acc_creditscore;
    }

    public void setCode_acc_creditscore(final java.lang.String code_acc_creditscore) {
        this.code_acc_creditscore = code_acc_creditscore;
    }

    public final void setCode_acc_creditscore_DIRECT(final java.lang.String code_acc_creditscore) {
        this.code_acc_creditscore = code_acc_creditscore;
    }

    @Column
    private java.lang.String code_acc_fiscalmonth;

    public java.lang.String getCode_acc_fiscalmonth() {
        return this.code_acc_fiscalmonth;
    }

    public final java.lang.String getCode_acc_fiscalmonth_DIRECT() {
        return this.code_acc_fiscalmonth;
    }

    public void setCode_acc_fiscalmonth(final java.lang.String code_acc_fiscalmonth) {
        this.code_acc_fiscalmonth = code_acc_fiscalmonth;
    }

    public final void setCode_acc_fiscalmonth_DIRECT(final java.lang.String code_acc_fiscalmonth) {
        this.code_acc_fiscalmonth = code_acc_fiscalmonth;
    }

    @Column
    private java.lang.String code_acc_market;

    public java.lang.String getCode_acc_market() {
        return this.code_acc_market;
    }

    public final java.lang.String getCode_acc_market_DIRECT() {
        return this.code_acc_market;
    }

    public void setCode_acc_market(final java.lang.String code_acc_market) {
        this.code_acc_market = code_acc_market;
    }

    public final void setCode_acc_market_DIRECT(final java.lang.String code_acc_market) {
        this.code_acc_market = code_acc_market;
    }

    @Column
    private java.lang.String code_acc_payment;

    public java.lang.String getCode_acc_payment() {
        return this.code_acc_payment;
    }

    public final java.lang.String getCode_acc_payment_DIRECT() {
        return this.code_acc_payment;
    }

    public void setCode_acc_payment(final java.lang.String code_acc_payment) {
        this.code_acc_payment = code_acc_payment;
    }

    public final void setCode_acc_payment_DIRECT(final java.lang.String code_acc_payment) {
        this.code_acc_payment = code_acc_payment;
    }

    @Column
    private java.lang.String code_acc_prohibitcause;

    public java.lang.String getCode_acc_prohibitcause() {
        return this.code_acc_prohibitcause;
    }

    public final java.lang.String getCode_acc_prohibitcause_DIRECT() {
        return this.code_acc_prohibitcause;
    }

    public void setCode_acc_prohibitcause(final java.lang.String code_acc_prohibitcause) {
        this.code_acc_prohibitcause = code_acc_prohibitcause;
    }

    public final void setCode_acc_prohibitcause_DIRECT(final java.lang.String code_acc_prohibitcause) {
        this.code_acc_prohibitcause = code_acc_prohibitcause;
    }

    @Column
    private java.lang.String code_acc_resurvey;

    public java.lang.String getCode_acc_resurvey() {
        return this.code_acc_resurvey;
    }

    public final java.lang.String getCode_acc_resurvey_DIRECT() {
        return this.code_acc_resurvey;
    }

    public void setCode_acc_resurvey(final java.lang.String code_acc_resurvey) {
        this.code_acc_resurvey = code_acc_resurvey;
    }

    public final void setCode_acc_resurvey_DIRECT(final java.lang.String code_acc_resurvey) {
        this.code_acc_resurvey = code_acc_resurvey;
    }

    @Column
    private java.lang.String code_acc_surveyer;

    public java.lang.String getCode_acc_surveyer() {
        return this.code_acc_surveyer;
    }

    public final java.lang.String getCode_acc_surveyer_DIRECT() {
        return this.code_acc_surveyer;
    }

    public void setCode_acc_surveyer(final java.lang.String code_acc_surveyer) {
        this.code_acc_surveyer = code_acc_surveyer;
    }

    public final void setCode_acc_surveyer_DIRECT(final java.lang.String code_acc_surveyer) {
        this.code_acc_surveyer = code_acc_surveyer;
    }

    @Column
    private java.lang.String code_acc_suverysite;

    public java.lang.String getCode_acc_suverysite() {
        return this.code_acc_suverysite;
    }

    public final java.lang.String getCode_acc_suverysite_DIRECT() {
        return this.code_acc_suverysite;
    }

    public void setCode_acc_suverysite(final java.lang.String code_acc_suverysite) {
        this.code_acc_suverysite = code_acc_suverysite;
    }

    public final void setCode_acc_suverysite_DIRECT(final java.lang.String code_acc_suverysite) {
        this.code_acc_suverysite = code_acc_suverysite;
    }

    @Column
    private java.lang.String code_acc_tradecond;

    public java.lang.String getCode_acc_tradecond() {
        return this.code_acc_tradecond;
    }

    public final java.lang.String getCode_acc_tradecond_DIRECT() {
        return this.code_acc_tradecond;
    }

    public void setCode_acc_tradecond(final java.lang.String code_acc_tradecond) {
        this.code_acc_tradecond = code_acc_tradecond;
    }

    public final void setCode_acc_tradecond_DIRECT(final java.lang.String code_acc_tradecond) {
        this.code_acc_tradecond = code_acc_tradecond;
    }

    @Column
    private java.lang.String code_zip;

    public java.lang.String getCode_zip() {
        return this.code_zip;
    }

    public final java.lang.String getCode_zip_DIRECT() {
        return this.code_zip;
    }

    public void setCode_zip(final java.lang.String code_zip) {
        this.code_zip = code_zip;
    }

    public final void setCode_zip_DIRECT(final java.lang.String code_zip) {
        this.code_zip = code_zip;
    }

    @Column
    private java.util.Date date_authorize;

    public java.util.Date getDate_authorize() {
        return this.date_authorize;
    }

    public final java.util.Date getDate_authorize_DIRECT() {
        return this.date_authorize;
    }

    public void setDate_authorize(final java.util.Date date_authorize) {
        this.date_authorize = date_authorize;
    }

    public final void setDate_authorize_DIRECT(final java.util.Date date_authorize) {
        this.date_authorize = date_authorize;
    }

    @Column
    private java.util.Date date_survey;

    public java.util.Date getDate_survey() {
        return this.date_survey;
    }

    public final java.util.Date getDate_survey_DIRECT() {
        return this.date_survey;
    }

    public void setDate_survey(final java.util.Date date_survey) {
        this.date_survey = date_survey;
    }

    public final void setDate_survey_DIRECT(final java.util.Date date_survey) {
        this.date_survey = date_survey;
    }

    @Column
    private java.util.Date date_valid_from;

    public java.util.Date getDate_valid_from() {
        return this.date_valid_from;
    }

    public final java.util.Date getDate_valid_from_DIRECT() {
        return this.date_valid_from;
    }

    public void setDate_valid_from(final java.util.Date date_valid_from) {
        this.date_valid_from = date_valid_from;
    }

    public final void setDate_valid_from_DIRECT(final java.util.Date date_valid_from) {
        this.date_valid_from = date_valid_from;
    }

    @Column
    private java.util.Date date_valid_to;

    public java.util.Date getDate_valid_to() {
        return this.date_valid_to;
    }

    public final java.util.Date getDate_valid_to_DIRECT() {
        return this.date_valid_to;
    }

    public void setDate_valid_to(final java.util.Date date_valid_to) {
        this.date_valid_to = date_valid_to;
    }

    public final void setDate_valid_to_DIRECT(final java.util.Date date_valid_to) {
        this.date_valid_to = date_valid_to;
    }

    @Column
    private java.lang.String fax_hq;

    public java.lang.String getFax_hq() {
        return this.fax_hq;
    }

    public final java.lang.String getFax_hq_DIRECT() {
        return this.fax_hq;
    }

    public void setFax_hq(final java.lang.String fax_hq) {
        this.fax_hq = fax_hq;
    }

    public final void setFax_hq_DIRECT(final java.lang.String fax_hq) {
        this.fax_hq = fax_hq;
    }

    @Column
    private int id_com_entity;

    public int getId_com_entity() {
        return this.id_com_entity;
    }

    public final int getId_com_entity_DIRECT() {
        return this.id_com_entity;
    }

    public void setId_com_entity(final int id_com_entity) {
        this.id_com_entity = id_com_entity;
    }

    public final void setId_com_entity_DIRECT(final int id_com_entity) {
        this.id_com_entity = id_com_entity;
    }

    @Column
    private java.lang.String indx_acc;

    public java.lang.String getIndx_acc() {
        return this.indx_acc;
    }

    public final java.lang.String getIndx_acc_DIRECT() {
        return this.indx_acc;
    }

    public void setIndx_acc(final java.lang.String indx_acc) {
        this.indx_acc = indx_acc;
    }

    public final void setIndx_acc_DIRECT(final java.lang.String indx_acc) {
        this.indx_acc = indx_acc;
    }

    @Column
    private java.lang.String indx_com;

    public java.lang.String getIndx_com() {
        return this.indx_com;
    }

    public final java.lang.String getIndx_com_DIRECT() {
        return this.indx_com;
    }

    public void setIndx_com(final java.lang.String indx_com) {
        this.indx_com = indx_com;
    }

    public final void setIndx_com_DIRECT(final java.lang.String indx_com) {
        this.indx_com = indx_com;
    }

    @Column
    private java.lang.String memo1;

    public java.lang.String getMemo1() {
        return this.memo1;
    }

    public final java.lang.String getMemo1_DIRECT() {
        return this.memo1;
    }

    public void setMemo1(final java.lang.String memo1) {
        this.memo1 = memo1;
    }

    public final void setMemo1_DIRECT(final java.lang.String memo1) {
        this.memo1 = memo1;
    }

    @Column
    private java.lang.String memo2;

    public java.lang.String getMemo2() {
        return this.memo2;
    }

    public final java.lang.String getMemo2_DIRECT() {
        return this.memo2;
    }

    public void setMemo2(final java.lang.String memo2) {
        this.memo2 = memo2;
    }

    public final void setMemo2_DIRECT(final java.lang.String memo2) {
        this.memo2 = memo2;
    }

    @Column
    private java.lang.String memo3;

    public java.lang.String getMemo3() {
        return this.memo3;
    }

    public final java.lang.String getMemo3_DIRECT() {
        return this.memo3;
    }

    public void setMemo3(final java.lang.String memo3) {
        this.memo3 = memo3;
    }

    public final void setMemo3_DIRECT(final java.lang.String memo3) {
        this.memo3 = memo3;
    }

    @Column
    private java.lang.String name_company2;

    public java.lang.String getName_company2() {
        return this.name_company2;
    }

    public final java.lang.String getName_company2_DIRECT() {
        return this.name_company2;
    }

    public void setName_company2(final java.lang.String name_company2) {
        this.name_company2 = name_company2;
    }

    public final void setName_company2_DIRECT(final java.lang.String name_company2) {
        this.name_company2 = name_company2;
    }

    @Column
    private java.lang.Integer n_employee;

    public java.lang.Integer getN_employee() {
        return this.n_employee;
    }

    public final java.lang.Integer getN_employee_DIRECT() {
        return this.n_employee;
    }

    public void setN_employee(final java.lang.Integer n_employee) {
        this.n_employee = n_employee;
    }

    public final void setN_employee_DIRECT(final java.lang.Integer n_employee) {
        this.n_employee = n_employee;
    }

    @Column
    private java.lang.String other_direction;

    public java.lang.String getOther_direction() {
        return this.other_direction;
    }

    public final java.lang.String getOther_direction_DIRECT() {
        return this.other_direction;
    }

    public void setOther_direction(final java.lang.String other_direction) {
        this.other_direction = other_direction;
    }

    public final void setOther_direction_DIRECT(final java.lang.String other_direction) {
        this.other_direction = other_direction;
    }

    @Column
    private java.lang.String path_report;

    public java.lang.String getPath_report() {
        return this.path_report;
    }

    public final java.lang.String getPath_report_DIRECT() {
        return this.path_report;
    }

    public void setPath_report(final java.lang.String path_report) {
        this.path_report = path_report;
    }

    public final void setPath_report_DIRECT(final java.lang.String path_report) {
        this.path_report = path_report;
    }

    @Column
    private java.lang.Integer score_credit;

    public java.lang.Integer getScore_credit() {
        return this.score_credit;
    }

    public final java.lang.Integer getScore_credit_DIRECT() {
        return this.score_credit;
    }

    public void setScore_credit(final java.lang.Integer score_credit) {
        this.score_credit = score_credit;
    }

    public final void setScore_credit_DIRECT(final java.lang.Integer score_credit) {
        this.score_credit = score_credit;
    }

    @Column
    private java.lang.Character status;

    public java.lang.Character getStatus() {
        return this.status;
    }

    public final java.lang.Character getStatus_DIRECT() {
        return this.status;
    }

    public void setStatus(final java.lang.Character status) {
        this.status = status;
    }

    public final void setStatus_DIRECT(final java.lang.Character status) {
        this.status = status;
    }

    @Column
    private java.lang.String tel_hq;

    public java.lang.String getTel_hq() {
        return this.tel_hq;
    }

    public final java.lang.String getTel_hq_DIRECT() {
        return this.tel_hq;
    }

    public void setTel_hq(final java.lang.String tel_hq) {
        this.tel_hq = tel_hq;
    }

    public final void setTel_hq_DIRECT(final java.lang.String tel_hq) {
        this.tel_hq = tel_hq;
    }

    @Column
    private java.lang.String url;

    public java.lang.String getUrl() {
        return this.url;
    }

    public final java.lang.String getUrl_DIRECT() {
        return this.url;
    }

    public void setUrl(final java.lang.String url) {
        this.url = url;
    }

    public final void setUrl_DIRECT(final java.lang.String url) {
        this.url = url;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ID_COM_ENTITY", referencedColumnName = "ID_COM_ENTITY", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Acc_com_entity acc_com_entity;

    public arrow.businesstraceability.persistence.entity.Acc_com_entity getAcc_com_entity() {
        return this.acc_com_entity;
    }

    /**
     * Set Acc_com_entity for Acc_com_credit_MAPPED.
     *
     * @param acc_com_entity Acc_com_entity.
     *
     **/
    public void setAcc_com_entity(final arrow.businesstraceability.persistence.entity.Acc_com_entity acc_com_entity) {
        if (acc_com_entity == null) {
            throw new IllegalArgumentException(
                    "Param of Acc_com_credit_MAPPED.setAcc_com_entity(Acc_com_entity acc_com_entity) must not be null");
        }
        else {
            this.id_com_entity = acc_com_entity.getId_com_entity();
        }
        this.acc_com_entity = acc_com_entity;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "AC_REQUEST_BRANCH", referencedColumnName = "ADP_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst;

    public arrow.businesstraceability.persistence.entity.Addresspoint_mst getAddresspoint_mst() {
        return this.addresspoint_mst;
    }

    /**
     * Set Addresspoint_mst for Acc_com_credit_MAPPED.
     *
     * @param addresspoint_mst Addresspoint_mst.
     *
     **/
    public void setAddresspoint_mst(final arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst) {
        if (addresspoint_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Acc_com_credit_MAPPED.setAddresspoint_mst(Addresspoint_mst addresspoint_mst) must not be null");
        }
        else {
            this.ac_request_branch = addresspoint_mst.getAdp_code();
        }
        this.addresspoint_mst = addresspoint_mst;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "AC_REQUEST", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Employee_mst employee_mst;

    public arrow.businesstraceability.persistence.entity.Employee_mst getEmployee_mst() {
        return this.employee_mst;
    }

    /**
     * Set Employee_mst for Acc_com_credit_MAPPED.
     *
     * @param employee_mst Employee_mst.
     *
     **/
    public void setEmployee_mst(final arrow.businesstraceability.persistence.entity.Employee_mst employee_mst) {
        if (employee_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Acc_com_credit_MAPPED.setEmployee_mst(Employee_mst employee_mst) must not be null");
        }
        else {
            this.ac_request = employee_mst.getEmp_code();
        }
        this.employee_mst = employee_mst;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}