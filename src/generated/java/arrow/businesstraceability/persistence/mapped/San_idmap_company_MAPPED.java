//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class San_idmap_company_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.San_idmap_company> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.San_idmap_company.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_company Data type: int
         *
         */
        public Pk(final int id_company) {
            this.id_company = id_company;
        }

        @Column(name = "ID_COMPANY")
        protected int id_company;

        public int getId_company() {
            return this.id_company;
        }
    }

    //default constructor
    public San_idmap_company_MAPPED() {
    }

    protected San_idmap_company_MAPPED(final int id_company) {
        this.checkFKConsistency(id_company);
        this.pk = new Pk(id_company);
        this.id_company = id_company;
    }

    private void checkFKConsistency(int id_company) {
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
    protected int id_company;

    public int getId_company() {
        return this.id_company;
    }

    //Normal columns

    @Column
    private java.lang.String id_san_company;

    public java.lang.String getId_san_company() {
        return this.id_san_company;
    }

    public final java.lang.String getId_san_company_DIRECT() {
        return this.id_san_company;
    }

    public void setId_san_company(final java.lang.String id_san_company) {
        this.id_san_company = id_san_company;
    }

    public final void setId_san_company_DIRECT(final java.lang.String id_san_company) {
        this.id_san_company = id_san_company;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}