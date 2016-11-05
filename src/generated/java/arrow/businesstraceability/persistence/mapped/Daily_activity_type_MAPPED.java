//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Daily_activity_type_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Daily_activity_type> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Daily_activity_type.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param dat_code Data type: short
         *
         */
        public Pk(final short dat_code) {
            this.dat_code = dat_code;
        }

        @Column(name = "DAT_CODE")
        protected short dat_code;

        public short getDat_code() {
            return this.dat_code;
        }
    }

    //default constructor
    public Daily_activity_type_MAPPED() {
    }

    protected Daily_activity_type_MAPPED(final short dat_code) {
        this.checkFKConsistency(dat_code);
        this.pk = new Pk(dat_code);
        this.dat_code = dat_code;
    }

    private void checkFKConsistency(short dat_code) {
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
    protected short dat_code;

    public short getDat_code() {
        return this.dat_code;
    }

    //Normal columns

    @Column
    private java.lang.String dat_name;

    public java.lang.String getDat_name() {
        return this.dat_name;
    }

    public final java.lang.String getDat_name_DIRECT() {
        return this.dat_name;
    }

    public void setDat_name(final java.lang.String dat_name) {
        this.dat_name = dat_name;
    }

    public final void setDat_name_DIRECT(final java.lang.String dat_name) {
        this.dat_name = dat_name;
    }

    @Column
    private short dat_order;

    public short getDat_order() {
        return this.dat_order;
    }

    public final short getDat_order_DIRECT() {
        return this.dat_order;
    }

    public void setDat_order(final short dat_order) {
        this.dat_order = dat_order;
    }

    public final void setDat_order_DIRECT(final short dat_order) {
        this.dat_order = dat_order;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}