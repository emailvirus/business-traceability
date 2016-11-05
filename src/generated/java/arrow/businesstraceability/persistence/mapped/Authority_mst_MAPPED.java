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
public class Authority_mst_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Authority_mst> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Authority_mst.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param aut_code Data type: short
         *
         */
        public Pk(final short aut_code) {
            this.aut_code = aut_code;
        }

        @Column(name = "AUT_CODE")
        protected short aut_code;

        public short getAut_code() {
            return this.aut_code;
        }
    }

    //default constructor
    public Authority_mst_MAPPED() {
    }

    protected Authority_mst_MAPPED(final short aut_code) {
        this.checkFKConsistency(aut_code);
        this.pk = new Pk(aut_code);
        this.aut_code = aut_code;
    }

    private void checkFKConsistency(short aut_code) {
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
    protected short aut_code;

    public short getAut_code() {
        return this.aut_code;
    }

    //Normal columns

    @Column
    private java.lang.String aut_name;

    public java.lang.String getAut_name() {
        return this.aut_name;
    }

    public final java.lang.String getAut_name_DIRECT() {
        return this.aut_name;
    }

    public void setAut_name(final java.lang.String aut_name) {
        this.aut_name = aut_name;
    }

    public final void setAut_name_DIRECT(final java.lang.String aut_name) {
        this.aut_name = aut_name;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}