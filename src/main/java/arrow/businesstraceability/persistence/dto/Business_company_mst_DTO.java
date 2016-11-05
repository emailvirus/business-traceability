//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Business_company_mst;
import arrow.businesstraceability.persistence.mapped.Business_company_mst_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Business_company_mst_DTO extends Business_company_mst {
    private Business_company_mst_MAPPED.Pk pk;

    public void setPk(final Business_company_mst_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Business_company_mst_MAPPED.Pk getPk() {
        return this.pk;
    }

    private java.lang.String buc_business_code;

    @Override
    public java.lang.String getBuc_business_code() {
        return this.buc_business_code;
    }

    public void setBuc_business_code(final java.lang.String buc_business_code) {
        this.buc_business_code = buc_business_code;
    }
  
    private java.lang.String buc_company_code;

    @Override
    public java.lang.String getBuc_company_code() {
        return this.buc_company_code;
    }

    public void setBuc_company_code(final java.lang.String buc_company_code) {
        this.buc_company_code = buc_company_code;
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