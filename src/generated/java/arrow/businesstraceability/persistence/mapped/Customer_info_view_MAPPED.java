//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Customer_info_view_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Customer_info_view> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Customer_info_view.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id Data type: int
         *
         */
        public Pk(final int id) {
            this.id = id;
        }

        @Column(name = "ID")
        protected int id;

        public int getId() {
            return this.id;
        }
    }

    //default constructor
    public Customer_info_view_MAPPED() {
    }

    protected Customer_info_view_MAPPED(final int id) {
        this.checkFKConsistency(id);
        this.pk = new Pk(id);
        this.id = id;
    }

    private void checkFKConsistency(int id) {
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
    protected int id;

    public int getId() {
        return this.id;
    }

    //Normal columns

    @Column
    private java.lang.String base_office;

    public java.lang.String getBase_office() {
        return this.base_office;
    }

    public final java.lang.String getBase_office_DIRECT() {
        return this.base_office;
    }

    public void setBase_office(final java.lang.String base_office) {
        this.base_office = base_office;
    }

    public final void setBase_office_DIRECT(final java.lang.String base_office) {
        this.base_office = base_office;
    }

    @Column
    private java.lang.String branch_office;

    public java.lang.String getBranch_office() {
        return this.branch_office;
    }

    public final java.lang.String getBranch_office_DIRECT() {
        return this.branch_office;
    }

    public void setBranch_office(final java.lang.String branch_office) {
        this.branch_office = branch_office;
    }

    public final void setBranch_office_DIRECT(final java.lang.String branch_office) {
        this.branch_office = branch_office;
    }

    @Column
    private java.lang.String branch_point_code;

    public java.lang.String getBranch_point_code() {
        return this.branch_point_code;
    }

    public final java.lang.String getBranch_point_code_DIRECT() {
        return this.branch_point_code;
    }

    public void setBranch_point_code(final java.lang.String branch_point_code) {
        this.branch_point_code = branch_point_code;
    }

    public final void setBranch_point_code_DIRECT(final java.lang.String branch_point_code) {
        this.branch_point_code = branch_point_code;
    }

    @Column
    private java.lang.String com_company_code;

    public java.lang.String getCom_company_code() {
        return this.com_company_code;
    }

    public final java.lang.String getCom_company_code_DIRECT() {
        return this.com_company_code;
    }

    public void setCom_company_code(final java.lang.String com_company_code) {
        this.com_company_code = com_company_code;
    }

    public final void setCom_company_code_DIRECT(final java.lang.String com_company_code) {
        this.com_company_code = com_company_code;
    }

    @Column
    private java.lang.String com_company_furigana;

    public java.lang.String getCom_company_furigana() {
        return this.com_company_furigana;
    }

    public final java.lang.String getCom_company_furigana_DIRECT() {
        return this.com_company_furigana;
    }

    public void setCom_company_furigana(final java.lang.String com_company_furigana) {
        this.com_company_furigana = com_company_furigana;
    }

    public final void setCom_company_furigana_DIRECT(final java.lang.String com_company_furigana) {
        this.com_company_furigana = com_company_furigana;
    }

    @Column
    private java.lang.String com_company_name;

    public java.lang.String getCom_company_name() {
        return this.com_company_name;
    }

    public final java.lang.String getCom_company_name_DIRECT() {
        return this.com_company_name;
    }

    public void setCom_company_name(final java.lang.String com_company_name) {
        this.com_company_name = com_company_name;
    }

    public final void setCom_company_name_DIRECT(final java.lang.String com_company_name) {
        this.com_company_name = com_company_name;
    }

    @Column
    private java.lang.String com_customer_code;

    public java.lang.String getCom_customer_code() {
        return this.com_customer_code;
    }

    public final java.lang.String getCom_customer_code_DIRECT() {
        return this.com_customer_code;
    }

    public void setCom_customer_code(final java.lang.String com_customer_code) {
        this.com_customer_code = com_customer_code;
    }

    public final void setCom_customer_code_DIRECT(final java.lang.String com_customer_code) {
        this.com_customer_code = com_customer_code;
    }

    @Column
    private java.lang.Short com_indbig_code;

    public java.lang.Short getCom_indbig_code() {
        return this.com_indbig_code;
    }

    public final java.lang.Short getCom_indbig_code_DIRECT() {
        return this.com_indbig_code;
    }

    public void setCom_indbig_code(final java.lang.Short com_indbig_code) {
        this.com_indbig_code = com_indbig_code;
    }

    public final void setCom_indbig_code_DIRECT(final java.lang.Short com_indbig_code) {
        this.com_indbig_code = com_indbig_code;
    }

    @Column
    private java.lang.Short com_limited_code;

    public java.lang.Short getCom_limited_code() {
        return this.com_limited_code;
    }

    public final java.lang.Short getCom_limited_code_DIRECT() {
        return this.com_limited_code;
    }

    public void setCom_limited_code(final java.lang.Short com_limited_code) {
        this.com_limited_code = com_limited_code;
    }

    public final void setCom_limited_code_DIRECT(final java.lang.Short com_limited_code) {
        this.com_limited_code = com_limited_code;
    }

    @Column
    private java.lang.Short com_listed_code;

    public java.lang.Short getCom_listed_code() {
        return this.com_listed_code;
    }

    public final java.lang.Short getCom_listed_code_DIRECT() {
        return this.com_listed_code;
    }

    public void setCom_listed_code(final java.lang.Short com_listed_code) {
        this.com_listed_code = com_listed_code;
    }

    public final void setCom_listed_code_DIRECT(final java.lang.Short com_listed_code) {
        this.com_listed_code = com_listed_code;
    }

    @Column
    private java.lang.String com_point_code;

    public java.lang.String getCom_point_code() {
        return this.com_point_code;
    }

    public final java.lang.String getCom_point_code_DIRECT() {
        return this.com_point_code;
    }

    public void setCom_point_code(final java.lang.String com_point_code) {
        this.com_point_code = com_point_code;
    }

    public final void setCom_point_code_DIRECT(final java.lang.String com_point_code) {
        this.com_point_code = com_point_code;
    }

    @Column
    private java.util.Date com_update_date;

    public java.util.Date getCom_update_date() {
        return this.com_update_date;
    }

    public final java.util.Date getCom_update_date_DIRECT() {
        return this.com_update_date;
    }

    public void setCom_update_date(final java.util.Date com_update_date) {
        this.com_update_date = com_update_date;
    }

    public final void setCom_update_date_DIRECT(final java.util.Date com_update_date) {
        this.com_update_date = com_update_date;
    }

    @Column
    private java.lang.String com_url;

    public java.lang.String getCom_url() {
        return this.com_url;
    }

    public final java.lang.String getCom_url_DIRECT() {
        return this.com_url;
    }

    public void setCom_url(final java.lang.String com_url) {
        this.com_url = com_url;
    }

    public final void setCom_url_DIRECT(final java.lang.String com_url) {
        this.com_url = com_url;
    }

    @Column
    private java.lang.String dai_compemp_name;

    public java.lang.String getDai_compemp_name() {
        return this.dai_compemp_name;
    }

    public final java.lang.String getDai_compemp_name_DIRECT() {
        return this.dai_compemp_name;
    }

    public void setDai_compemp_name(final java.lang.String dai_compemp_name) {
        this.dai_compemp_name = dai_compemp_name;
    }

    public final void setDai_compemp_name_DIRECT(final java.lang.String dai_compemp_name) {
        this.dai_compemp_name = dai_compemp_name;
    }

    @Column
    private java.lang.String dai_employee_post;

    public java.lang.String getDai_employee_post() {
        return this.dai_employee_post;
    }

    public final java.lang.String getDai_employee_post_DIRECT() {
        return this.dai_employee_post;
    }

    public void setDai_employee_post(final java.lang.String dai_employee_post) {
        this.dai_employee_post = dai_employee_post;
    }

    public final void setDai_employee_post_DIRECT(final java.lang.String dai_employee_post) {
        this.dai_employee_post = dai_employee_post;
    }

    @Column
    private java.lang.String field_name;

    public java.lang.String getField_name() {
        return this.field_name;
    }

    public final java.lang.String getField_name_DIRECT() {
        return this.field_name;
    }

    public void setField_name(final java.lang.String field_name) {
        this.field_name = field_name;
    }

    public final void setField_name_DIRECT(final java.lang.String field_name) {
        this.field_name = field_name;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}