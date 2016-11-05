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
public class Position_mst_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Position_mst> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Position_mst.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param pos_code Data type: short
         *
         */
        public Pk(final short pos_code) {
            this.pos_code = pos_code;
        }

        @Column(name = "POS_CODE")
        protected short pos_code;

        public short getPos_code() {
            return this.pos_code;
        }
    }

    //default constructor
    public Position_mst_MAPPED() {
    }

    protected Position_mst_MAPPED(final short pos_code) {
        this.checkFKConsistency(pos_code);
        this.pk = new Pk(pos_code);
        this.pos_code = pos_code;
    }

    private void checkFKConsistency(short pos_code) {
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
    protected short pos_code;

    public short getPos_code() {
        return this.pos_code;
    }

    //Normal columns

    @Column
    private java.lang.String pos_name;

    public java.lang.String getPos_name() {
        return this.pos_name;
    }

    public final java.lang.String getPos_name_DIRECT() {
        return this.pos_name;
    }

    public void setPos_name(final java.lang.String pos_name) {
        this.pos_name = pos_name;
    }

    public final void setPos_name_DIRECT(final java.lang.String pos_name) {
        this.pos_name = pos_name;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}