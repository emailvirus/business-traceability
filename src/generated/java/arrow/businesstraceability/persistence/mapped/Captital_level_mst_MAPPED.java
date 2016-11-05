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
public class Captital_level_mst_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.Captital_level_mst> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.Captital_level_mst.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param cal_level Data type: int
         *
         */
        public Pk(final int cal_level) {
            this.cal_level = cal_level;
        }

        @Column(name = "CAL_LEVEL")
        protected int cal_level;

        public int getCal_level() {
            return this.cal_level;
        }
    }

    //default constructor
    public Captital_level_mst_MAPPED() {
    }

    protected Captital_level_mst_MAPPED(final int cal_level) {
        this.checkFKConsistency(cal_level);
        this.pk = new Pk(cal_level);
        this.cal_level = cal_level;
    }

    private void checkFKConsistency(int cal_level) {
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
    protected int cal_level;

    public int getCal_level() {
        return this.cal_level;
    }

    //Normal columns

    @Column
    private java.lang.String cal_grade;

    public java.lang.String getCal_grade() {
        return this.cal_grade;
    }

    public final java.lang.String getCal_grade_DIRECT() {
        return this.cal_grade;
    }

    public void setCal_grade(final java.lang.String cal_grade) {
        this.cal_grade = cal_grade;
    }

    public final void setCal_grade_DIRECT(final java.lang.String cal_grade) {
        this.cal_grade = cal_grade;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}