//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Basepoint_info;
import arrow.businesstraceability.persistence.mapped.Basepoint_info_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Basepoint_info_DTO extends Basepoint_info {
    private Basepoint_info_MAPPED.Pk pk;

    public void setPk(final Basepoint_info_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Basepoint_info_MAPPED.Pk getPk() {
        return this.pk;
    }

    private java.lang.String bas_point_code;

    @Override
    public java.lang.String getBas_point_code() {
        return this.bas_point_code;
    }

    public void setBas_point_code(final java.lang.String bas_point_code) {
        this.bas_point_code = bas_point_code;
    }
  
    private java.lang.String bas_company_code;

    @Override
    public java.lang.String getBas_company_code() {
        return this.bas_company_code;
    }

    public void setBas_company_code(final java.lang.String bas_company_code) {
        this.bas_company_code = bas_company_code;
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