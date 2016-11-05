//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Industry_mdl_info;
import arrow.businesstraceability.persistence.mapped.Industry_mdl_info_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Industry_mdl_info_DTO extends Industry_mdl_info {
    private Industry_mdl_info_MAPPED.Pk pk;

    public void setPk(final Industry_mdl_info_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Industry_mdl_info_MAPPED.Pk getPk() {
        return this.pk;
    }

    private java.lang.String inm_company_code;

    @Override
    public java.lang.String getInm_company_code() {
        return this.inm_company_code;
    }

    public void setInm_company_code(final java.lang.String inm_company_code) {
        this.inm_company_code = inm_company_code;
    }
  
    private java.lang.String inm_big_code;

    @Override
    public java.lang.String getInm_big_code() {
        return this.inm_big_code;
    }

    public void setInm_big_code(final java.lang.String inm_big_code) {
        this.inm_big_code = inm_big_code;
    }
  
    private java.lang.String inm_mdl_code;

    @Override
    public java.lang.String getInm_mdl_code() {
        return this.inm_mdl_code;
    }

    public void setInm_mdl_code(final java.lang.String inm_mdl_code) {
        this.inm_mdl_code = inm_mdl_code;
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