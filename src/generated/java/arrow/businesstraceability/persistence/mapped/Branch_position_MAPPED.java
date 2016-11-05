//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Branch_position_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Branch_position> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Branch_position.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param pos_id Data type: int
         *
         */
        public Pk(final int pos_id) {
            this.pos_id = pos_id;
        }

        @Column(name = "POS_ID")
        protected int pos_id;

        public int getPos_id() {
            return this.pos_id;
        }
    }

    //default constructor
    public Branch_position_MAPPED() {
    }

    protected Branch_position_MAPPED(final int pos_id) {
        this.checkFKConsistency(pos_id);
        this.pk = new Pk(pos_id);
        this.pos_id = pos_id;
    }

    private void checkFKConsistency(int pos_id) {
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
    protected int pos_id;

    public int getPos_id() {
        return this.pos_id;
    }

    //Normal columns

    @Column
    private java.lang.String adp_code;

    public java.lang.String getAdp_code() {
        return this.adp_code;
    }

    public final java.lang.String getAdp_code_DIRECT() {
        return this.adp_code;
    }

    public void setAdp_code(final java.lang.String adp_code) {
        this.adp_code = adp_code;
    }

    public final void setAdp_code_DIRECT(final java.lang.String adp_code) {
        this.adp_code = adp_code;
    }

    @Column
    private java.lang.Boolean delete_flg;

    public java.lang.Boolean getDelete_flg() {
        return this.delete_flg;
    }

    public final java.lang.Boolean getDelete_flg_DIRECT() {
        return this.delete_flg;
    }

    public void setDelete_flg(final java.lang.Boolean delete_flg) {
        this.delete_flg = delete_flg;
    }

    public final void setDelete_flg_DIRECT(final java.lang.Boolean delete_flg) {
        this.delete_flg = delete_flg;
    }

    @Column
    private int employee_code;

    public int getEmployee_code() {
        return this.employee_code;
    }

    public final int getEmployee_code_DIRECT() {
        return this.employee_code;
    }

    public void setEmployee_code(final int employee_code) {
        this.employee_code = employee_code;
    }

    public final void setEmployee_code_DIRECT(final int employee_code) {
        this.employee_code = employee_code;
    }

    @Column
    private short pos_code;

    public short getPos_code() {
        return this.pos_code;
    }

    public final short getPos_code_DIRECT() {
        return this.pos_code;
    }

    public void setPos_code(final short pos_code) {
        this.pos_code = pos_code;
    }

    public final void setPos_code_DIRECT(final short pos_code) {
        this.pos_code = pos_code;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "EMPLOYEE_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Employee_mst employee_mst;

    public arrow.businesstraceability.persistence.entity.Employee_mst getEmployee_mst() {
        return this.employee_mst;
    }

    /**
     * Set Employee_mst for Branch_position_MAPPED.
     *
     * @param employee_mst Employee_mst.
     *
     **/
    public void setEmployee_mst(final arrow.businesstraceability.persistence.entity.Employee_mst employee_mst) {
        if (employee_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Branch_position_MAPPED.setEmployee_mst(Employee_mst employee_mst) must not be null");
        }
        else {
            this.employee_code = employee_mst.getEmp_code();
        }
        this.employee_mst = employee_mst;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "ADP_CODE", referencedColumnName = "ADP_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst;

    public arrow.businesstraceability.persistence.entity.Addresspoint_mst getAddresspoint_mst() {
        return this.addresspoint_mst;
    }

    /**
     * Set Addresspoint_mst for Branch_position_MAPPED.
     *
     * @param addresspoint_mst Addresspoint_mst.
     *
     **/
    public void setAddresspoint_mst(final arrow.businesstraceability.persistence.entity.Addresspoint_mst addresspoint_mst) {
        if (addresspoint_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Branch_position_MAPPED.setAddresspoint_mst(Addresspoint_mst addresspoint_mst) must not be null");
        }
        else {
            this.adp_code = addresspoint_mst.getAdp_code();
        }
        this.addresspoint_mst = addresspoint_mst;
    }

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "POS_CODE", referencedColumnName = "POS_CODE", insertable = false, updatable = false)
        })
    protected arrow.businesstraceability.persistence.entity.Position_mst position_mst;

    public arrow.businesstraceability.persistence.entity.Position_mst getPosition_mst() {
        return this.position_mst;
    }

    /**
     * Set Position_mst for Branch_position_MAPPED.
     *
     * @param position_mst Position_mst.
     *
     **/
    public void setPosition_mst(final arrow.businesstraceability.persistence.entity.Position_mst position_mst) {
        if (position_mst == null) {
            throw new IllegalArgumentException(
                    "Param of Branch_position_MAPPED.setPosition_mst(Position_mst position_mst) must not be null");
        }
        else {
            this.pos_code = position_mst.getPos_code();
        }
        this.position_mst = position_mst;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}