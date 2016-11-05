//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.*;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class San_idmap_card_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.San_idmap_card> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.San_idmap_card.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param id_card Data type: int
         *
         */
        public Pk(final int id_card) {
            this.id_card = id_card;
        }

        @Column(name = "ID_CARD")
        protected int id_card;

        public int getId_card() {
            return this.id_card;
        }
    }

    //default constructor
    public San_idmap_card_MAPPED() {
    }

    protected San_idmap_card_MAPPED(final int id_card) {
        this.checkFKConsistency(id_card);
        this.pk = new Pk(id_card);
        this.id_card = id_card;
    }

    private void checkFKConsistency(int id_card) {
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
    protected int id_card;

    public int getId_card() {
        return this.id_card;
    }

    //Normal columns

    @Column
    private java.lang.String id_san_card;

    public java.lang.String getId_san_card() {
        return this.id_san_card;
    }

    public final java.lang.String getId_san_card_DIRECT() {
        return this.id_san_card;
    }

    public void setId_san_card(final java.lang.String id_san_card) {
        this.id_san_card = id_san_card;
    }

    public final void setId_san_card_DIRECT(final java.lang.String id_san_card) {
        this.id_san_card = id_san_card;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}