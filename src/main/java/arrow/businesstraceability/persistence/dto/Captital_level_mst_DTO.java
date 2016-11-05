//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Captital_level_mst;
import arrow.businesstraceability.persistence.mapped.Captital_level_mst_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Captital_level_mst_DTO extends Captital_level_mst {
    private Captital_level_mst_MAPPED.Pk pk;

    public void setPk(final Captital_level_mst_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Captital_level_mst_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int cal_level;

    @Override
    public int getCal_level() {
        return this.cal_level;
    }

    public void setCal_level(final int cal_level) {
        this.cal_level = cal_level;
    }
  

    private boolean isPersisted;

    @Override
    public boolean isPersisted() {
        return this.isPersisted;
    }

    public void setPersisted(final boolean value) {
        this.isPersisted = value;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}