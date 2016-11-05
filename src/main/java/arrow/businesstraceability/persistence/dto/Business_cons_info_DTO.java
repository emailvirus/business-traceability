//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Business_cons_info;
import arrow.businesstraceability.persistence.mapped.Business_cons_info_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Business_cons_info_DTO extends Business_cons_info {
    private Business_cons_info_MAPPED.Pk pk;

    public void setPk(final Business_cons_info_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Business_cons_info_MAPPED.Pk getPk() {
        return this.pk;
    }

    private java.lang.String bco_business_code;

    @Override
    public java.lang.String getBco_business_code() {
        return this.bco_business_code;
    }

    public void setBco_business_code(final java.lang.String bco_business_code) {
        this.bco_business_code = bco_business_code;
    }
  
    private java.util.Date bco_date;

    @Override
    public java.util.Date getBco_date() {
        return this.bco_date;
    }

    public void setBco_date(final java.util.Date bco_date) {
        this.bco_date = bco_date;
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