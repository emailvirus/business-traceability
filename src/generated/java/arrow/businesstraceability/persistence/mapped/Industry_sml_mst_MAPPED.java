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
public class Industry_sml_mst_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Industry_sml_mst> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Industry_sml_mst.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param sml_code Data type: short
         * @param sml_md_code Data type: short
         * @param sml_sm_code Data type: short
         *
         */
        public Pk(final short sml_code, final short sml_md_code, final short sml_sm_code) {
            this.sml_code = sml_code;
            this.sml_md_code = sml_md_code;
            this.sml_sm_code = sml_sm_code;
        }

        @Column(name = "SML_CODE")
        protected short sml_code;

        public short getSml_code() {
            return this.sml_code;
        }

        @Column(name = "SML_MD_CODE")
        protected short sml_md_code;

        public short getSml_md_code() {
            return this.sml_md_code;
        }

        @Column(name = "SML_SM_CODE")
        protected short sml_sm_code;

        public short getSml_sm_code() {
            return this.sml_sm_code;
        }
    }

    //default constructor
    public Industry_sml_mst_MAPPED() {
    }

    protected Industry_sml_mst_MAPPED(final short sml_code, final short sml_md_code, final short sml_sm_code) {
        this.checkFKConsistency(sml_code, sml_md_code, sml_sm_code);
        this.pk = new Pk(sml_code, sml_md_code, sml_sm_code);
        this.sml_code = sml_code;
        this.sml_md_code = sml_md_code;
        this.sml_sm_code = sml_sm_code;
    }

    private void checkFKConsistency(short sml_code, short sml_md_code, short sml_sm_code) {
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
    protected short sml_code;

    public short getSml_code() {
        return this.sml_code;
    }

    @Column(insertable = false, updatable = false)
    protected short sml_md_code;

    public short getSml_md_code() {
        return this.sml_md_code;
    }

    @Column(insertable = false, updatable = false)
    protected short sml_sm_code;

    public short getSml_sm_code() {
        return this.sml_sm_code;
    }

    //Normal columns

    @Column
    private java.lang.String sml_name;

    public java.lang.String getSml_name() {
        return this.sml_name;
    }

    public final java.lang.String getSml_name_DIRECT() {
        return this.sml_name;
    }

    public void setSml_name(final java.lang.String sml_name) {
        this.sml_name = sml_name;
    }

    public final void setSml_name_DIRECT(final java.lang.String sml_name) {
        this.sml_name = sml_name;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}