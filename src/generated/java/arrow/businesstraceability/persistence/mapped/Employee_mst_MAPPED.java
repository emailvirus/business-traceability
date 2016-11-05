//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Employee_mst_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Employee_mst> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Employee_mst.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param emp_code Data type: int
         *
         */
        public Pk(final int emp_code) {
            this.emp_code = emp_code;
        }

        @Column(name = "EMP_CODE")
        protected int emp_code;

        public int getEmp_code() {
            return this.emp_code;
        }
    }

    //default constructor
    public Employee_mst_MAPPED() {
    }

    protected Employee_mst_MAPPED(final int emp_code) {
        this.checkFKConsistency(emp_code);
        this.pk = new Pk(emp_code);
        this.emp_code = emp_code;
    }

    private void checkFKConsistency(int emp_code) {
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
    protected int emp_code;

    public int getEmp_code() {
        return this.emp_code;
    }

    //Normal columns

    @Column
    private java.lang.String emp_adpcode;

    public java.lang.String getEmp_adpcode() {
        return this.emp_adpcode;
    }

    public final java.lang.String getEmp_adpcode_DIRECT() {
        return this.emp_adpcode;
    }

    public void setEmp_adpcode(final java.lang.String emp_adpcode) {
        this.emp_adpcode = emp_adpcode;
    }

    public final void setEmp_adpcode_DIRECT(final java.lang.String emp_adpcode) {
        this.emp_adpcode = emp_adpcode;
    }

    @Column
    private java.lang.Character emp_condi_code;

    public java.lang.Character getEmp_condi_code() {
        return this.emp_condi_code;
    }

    public final java.lang.Character getEmp_condi_code_DIRECT() {
        return this.emp_condi_code;
    }

    public void setEmp_condi_code(final java.lang.Character emp_condi_code) {
        this.emp_condi_code = emp_condi_code;
    }

    public final void setEmp_condi_code_DIRECT(final java.lang.Character emp_condi_code) {
        this.emp_condi_code = emp_condi_code;
    }

    @Column
    private boolean emp_delete_flg;

    public boolean getEmp_delete_flg() {
        return this.emp_delete_flg;
    }

    public final boolean getEmp_delete_flg_DIRECT() {
        return this.emp_delete_flg;
    }

    public void setEmp_delete_flg(final boolean emp_delete_flg) {
        this.emp_delete_flg = emp_delete_flg;
    }

    public final void setEmp_delete_flg_DIRECT(final boolean emp_delete_flg) {
        this.emp_delete_flg = emp_delete_flg;
    }

    @Column
    private java.util.Date emp_entery_date;

    public java.util.Date getEmp_entery_date() {
        return this.emp_entery_date;
    }

    public final java.util.Date getEmp_entery_date_DIRECT() {
        return this.emp_entery_date;
    }

    public void setEmp_entery_date(final java.util.Date emp_entery_date) {
        this.emp_entery_date = emp_entery_date;
    }

    public final void setEmp_entery_date_DIRECT(final java.util.Date emp_entery_date) {
        this.emp_entery_date = emp_entery_date;
    }

    @Column
    private java.lang.String emp_mail;

    public java.lang.String getEmp_mail() {
        return this.emp_mail;
    }

    public final java.lang.String getEmp_mail_DIRECT() {
        return this.emp_mail;
    }

    public void setEmp_mail(final java.lang.String emp_mail) {
        this.emp_mail = emp_mail;
    }

    public final void setEmp_mail_DIRECT(final java.lang.String emp_mail) {
        this.emp_mail = emp_mail;
    }

    @Column
    private java.lang.String emp_mobile;

    public java.lang.String getEmp_mobile() {
        return this.emp_mobile;
    }

    public final java.lang.String getEmp_mobile_DIRECT() {
        return this.emp_mobile;
    }

    public void setEmp_mobile(final java.lang.String emp_mobile) {
        this.emp_mobile = emp_mobile;
    }

    public final void setEmp_mobile_DIRECT(final java.lang.String emp_mobile) {
        this.emp_mobile = emp_mobile;
    }

    @Column
    private java.lang.String emp_name;

    public java.lang.String getEmp_name() {
        return this.emp_name;
    }

    public final java.lang.String getEmp_name_DIRECT() {
        return this.emp_name;
    }

    public void setEmp_name(final java.lang.String emp_name) {
        this.emp_name = emp_name;
    }

    public final void setEmp_name_DIRECT(final java.lang.String emp_name) {
        this.emp_name = emp_name;
    }

    @Column
    private short emp_poscode;

    public short getEmp_poscode() {
        return this.emp_poscode;
    }

    public final short getEmp_poscode_DIRECT() {
        return this.emp_poscode;
    }

    public void setEmp_poscode(final short emp_poscode) {
        this.emp_poscode = emp_poscode;
    }

    public final void setEmp_poscode_DIRECT(final short emp_poscode) {
        this.emp_poscode = emp_poscode;
    }

    @Column
    private java.lang.String emp_post;

    public java.lang.String getEmp_post() {
        return this.emp_post;
    }

    public final java.lang.String getEmp_post_DIRECT() {
        return this.emp_post;
    }

    public void setEmp_post(final java.lang.String emp_post) {
        this.emp_post = emp_post;
    }

    public final void setEmp_post_DIRECT(final java.lang.String emp_post) {
        this.emp_post = emp_post;
    }

    @Column
    private short emp_settle_authority;

    public short getEmp_settle_authority() {
        return this.emp_settle_authority;
    }

    public final short getEmp_settle_authority_DIRECT() {
        return this.emp_settle_authority;
    }

    public void setEmp_settle_authority(final short emp_settle_authority) {
        this.emp_settle_authority = emp_settle_authority;
    }

    public final void setEmp_settle_authority_DIRECT(final short emp_settle_authority) {
        this.emp_settle_authority = emp_settle_authority;
    }

    @Column
    private boolean emp_system_authority;

    public boolean getEmp_system_authority() {
        return this.emp_system_authority;
    }

    public final boolean getEmp_system_authority_DIRECT() {
        return this.emp_system_authority;
    }

    public void setEmp_system_authority(final boolean emp_system_authority) {
        this.emp_system_authority = emp_system_authority;
    }

    public final void setEmp_system_authority_DIRECT(final boolean emp_system_authority) {
        this.emp_system_authority = emp_system_authority;
    }

    @Column
    private java.lang.String emp_tel;

    public java.lang.String getEmp_tel() {
        return this.emp_tel;
    }

    public final java.lang.String getEmp_tel_DIRECT() {
        return this.emp_tel;
    }

    public void setEmp_tel(final java.lang.String emp_tel) {
        this.emp_tel = emp_tel;
    }

    public final void setEmp_tel_DIRECT(final java.lang.String emp_tel) {
        this.emp_tel = emp_tel;
    }

    @Column
    private java.util.Date emp_update_date;

    public java.util.Date getEmp_update_date() {
        return this.emp_update_date;
    }

    public final java.util.Date getEmp_update_date_DIRECT() {
        return this.emp_update_date;
    }

    public void setEmp_update_date(final java.util.Date emp_update_date) {
        this.emp_update_date = emp_update_date;
    }

    public final void setEmp_update_date_DIRECT(final java.util.Date emp_update_date) {
        this.emp_update_date = emp_update_date;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "EMP_POSCODE", referencedColumnName = "POS_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Position_mst position_mst;

    public arrow.businesstraceability.persistence.entity.Position_mst getPosition_mst() {
        return this.position_mst;
    }

    /**
     * Set Position_mst for Employee_mst_MAPPED.
     *
     * @param position_mst Position_mst.
     *
     **/
    public void setPosition_mst(final arrow.businesstraceability.persistence.entity.Position_mst position_mst) {
        if (position_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Employee_mst_MAPPED.setPosition_mst(Position_mst position_mst) must not be null");
        }
        else {
            this.emp_poscode = position_mst.getPos_code();
        }
        this.position_mst = position_mst;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "EMP_ADPCODE", referencedColumnName = "ADP_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst;

    public arrow.businesstraceability.persistence.entity.Addresspoint_mst getAddresspoint_mst() {
        return this.addresspoint_mst;
    }

    /**
     * Set Addresspoint_mst for Employee_mst_MAPPED.
     *
     * @param addresspoint_mst Addresspoint_mst.
     *
     **/
    public void setAddresspoint_mst(final arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst) {
        if (addresspoint_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Employee_mst_MAPPED.setAddresspoint_mst(Addresspoint_mst addresspoint_mst) must not be null");
        }
        else {
            this.emp_adpcode = addresspoint_mst.getAdp_code();
        }
        this.addresspoint_mst = addresspoint_mst;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "EMP_SETTLE_AUTHORITY", referencedColumnName = "AUT_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Authority_mst authority_mst;

    public arrow.businesstraceability.persistence.entity.Authority_mst getAuthority_mst() {
        return this.authority_mst;
    }

    /**
     * Set Authority_mst for Employee_mst_MAPPED.
     *
     * @param authority_mst Authority_mst.
     *
     **/
    public void setAuthority_mst(final arrow.businesstraceability.persistence.entity.Authority_mst authority_mst) {
        if (authority_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Employee_mst_MAPPED.setAuthority_mst(Authority_mst authority_mst) must not be null");
        }
        else {
            this.emp_settle_authority = authority_mst.getAut_code();
        }
        this.authority_mst = authority_mst;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}