//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class Tdb_detail_industry_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Tdb_detail_industry> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Tdb_detail_industry.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param code Data type: java.lang.String
         *
         */
        public Pk(final java.lang.String code) {
            this.code = code;
        }

        @Column(name = "CODE")
        protected java.lang.String code;

        public java.lang.String getCode() {
            return this.code;
        }
    }

    //default constructor
    public Tdb_detail_industry_MAPPED() {
    }

    protected Tdb_detail_industry_MAPPED(final java.lang.String code) {
        this.checkFKConsistency(code);
        this.pk = new Pk(code);
        this.code = code;
    }

    private void checkFKConsistency(java.lang.String code) {
        if (code == null) {
            throw new IllegalArgumentException("Parameter code must not be null");
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
    protected java.lang.String code;

    public java.lang.String getCode() {
        return this.code;
    }

    //Normal columns

    @Column
    private java.lang.String parent_code;

    public java.lang.String getParent_code() {
        return this.parent_code;
    }

    public final java.lang.String getParent_code_DIRECT() {
        return this.parent_code;
    }

    public void setParent_code(final java.lang.String parent_code) {
        this.parent_code = parent_code;
    }

    public final void setParent_code_DIRECT(final java.lang.String parent_code) {
        this.parent_code = parent_code;
    }

    @Column
    private java.lang.String title;

    public java.lang.String getTitle() {
        return this.title;
    }

    public final java.lang.String getTitle_DIRECT() {
        return this.title;
    }

    public void setTitle(final java.lang.String title) {
        this.title = title;
    }

    public final void setTitle_DIRECT(final java.lang.String title) {
        this.title = title;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}