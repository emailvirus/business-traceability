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
public class Addresspoint_mst_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Addresspoint_mst> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Addresspoint_mst.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param adp_code Data type: java.lang.String
         *
         */
        public Pk(final java.lang.String adp_code) {
            this.adp_code = adp_code;
        }

        @Column(name = "ADP_CODE")
        protected java.lang.String adp_code;

        public java.lang.String getAdp_code() {
            return this.adp_code;
        }
    }

    //default constructor
    public Addresspoint_mst_MAPPED() {
    }

    protected Addresspoint_mst_MAPPED(final java.lang.String adp_code) {
        this.checkFKConsistency(adp_code);
        this.pk = new Pk(adp_code);
        this.adp_code = adp_code;
    }

    private void checkFKConsistency(java.lang.String adp_code) {
        if (adp_code == null) {
            throw new IllegalArgumentException("Parameter adp_code must not be null");
        }
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
    protected java.lang.String adp_code;

    public java.lang.String getAdp_code() {
        return this.adp_code;
    }

    //Normal columns

    @Column
    private java.lang.String adp_name;

    public java.lang.String getAdp_name() {
        return this.adp_name;
    }

    public final java.lang.String getAdp_name_DIRECT() {
        return this.adp_name;
    }

    public void setAdp_name(final java.lang.String adp_name) {
        this.adp_name = adp_name;
    }

    public final void setAdp_name_DIRECT(final java.lang.String adp_name) {
        this.adp_name = adp_name;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}