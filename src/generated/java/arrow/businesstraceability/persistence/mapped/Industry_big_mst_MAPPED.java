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
public class Industry_big_mst_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Industry_big_mst> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Industry_big_mst.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param big_code Data type: short
         *
         */
        public Pk(final short big_code) {
            this.big_code = big_code;
        }

        @Column(name = "BIG_CODE")
        protected short big_code;

        public short getBig_code() {
            return this.big_code;
        }
    }

    //default constructor
    public Industry_big_mst_MAPPED() {
    }

    protected Industry_big_mst_MAPPED(final short big_code) {
        this.checkFKConsistency(big_code);
        this.pk = new Pk(big_code);
        this.big_code = big_code;
    }

    private void checkFKConsistency(short big_code) {
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
    protected short big_code;

    public short getBig_code() {
        return this.big_code;
    }

    //Normal columns

    @Column
    private java.lang.String big_name;

    public java.lang.String getBig_name() {
        return this.big_name;
    }

    public final java.lang.String getBig_name_DIRECT() {
        return this.big_name;
    }

    public void setBig_name(final java.lang.String big_name) {
        this.big_name = big_name;
    }

    public final void setBig_name_DIRECT(final java.lang.String big_name) {
        this.big_name = big_name;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}