//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class San_idmap_person_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.San_idmap_person> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.San_idmap_person.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_person Data type: int
         *
         */
        public Pk(final int id_person) {
            this.id_person = id_person;
        }

        @Column(name = "ID_PERSON")
        protected int id_person;

        public int getId_person() {
            return this.id_person;
        }
    }

    //default constructor
    public San_idmap_person_MAPPED() {
    }

    protected San_idmap_person_MAPPED(final int id_person) {
        this.checkFKConsistency(id_person);
        this.pk = new Pk(id_person);
        this.id_person = id_person;
    }

    private void checkFKConsistency(int id_person) {
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
    protected int id_person;

    public int getId_person() {
        return this.id_person;
    }

    //Normal columns

    @Column
    private java.lang.String id_san_person;

    public java.lang.String getId_san_person() {
        return this.id_san_person;
    }

    public final java.lang.String getId_san_person_DIRECT() {
        return this.id_san_person;
    }

    public void setId_san_person(final java.lang.String id_san_person) {
        this.id_san_person = id_san_person;
    }

    public final void setId_san_person_DIRECT(final java.lang.String id_san_person) {
        this.id_san_person = id_san_person;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}