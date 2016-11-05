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
public class Industry_mdl_mst_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Industry_mdl_mst> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Industry_mdl_mst.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param mdl_code Data type: short
         * @param mdl_md_code Data type: short
         *
         */
        public Pk(final short mdl_code, final short mdl_md_code) {
            this.mdl_code = mdl_code;
            this.mdl_md_code = mdl_md_code;
        }

        @Column(name = "MDL_CODE")
        protected short mdl_code;

        public short getMdl_code() {
            return this.mdl_code;
        }

        @Column(name = "MDL_MD_CODE")
        protected short mdl_md_code;

        public short getMdl_md_code() {
            return this.mdl_md_code;
        }
    }

    //default constructor
    public Industry_mdl_mst_MAPPED() {
    }

    protected Industry_mdl_mst_MAPPED(final short mdl_code, final short mdl_md_code) {
        this.checkFKConsistency(mdl_code, mdl_md_code);
        this.pk = new Pk(mdl_code, mdl_md_code);
        this.mdl_code = mdl_code;
        this.mdl_md_code = mdl_md_code;
    }

    private void checkFKConsistency(short mdl_code, short mdl_md_code) {
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
    protected short mdl_code;

    public short getMdl_code() {
        return this.mdl_code;
    }

    @Column(insertable = false, updatable = false)
    protected short mdl_md_code;

    public short getMdl_md_code() {
        return this.mdl_md_code;
    }

    //Normal columns

    @Column
    private java.lang.String mdl_name;

    public java.lang.String getMdl_name() {
        return this.mdl_name;
    }

    public final java.lang.String getMdl_name_DIRECT() {
        return this.mdl_name;
    }

    public void setMdl_name(final java.lang.String mdl_name) {
        this.mdl_name = mdl_name;
    }

    public final void setMdl_name_DIRECT(final java.lang.String mdl_name) {
        this.mdl_name = mdl_name;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}