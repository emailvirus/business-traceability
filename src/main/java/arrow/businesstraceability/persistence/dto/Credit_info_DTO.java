//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Credit_info;
import arrow.businesstraceability.persistence.mapped.Credit_info_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Credit_info_DTO extends Credit_info {
    private Credit_info_MAPPED.Pk pk;

    public void setPk(final Credit_info_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Credit_info_MAPPED.Pk getPk() {
        return this.pk;
    }

    private java.lang.String crd_company_code;

    @Override
    public java.lang.String getCrd_company_code() {
        return this.crd_company_code;
    }

    public void setCrd_company_code(final java.lang.String crd_company_code) {
        this.crd_company_code = crd_company_code;
    }
  
    private java.util.Date crd_date;

    @Override
    public java.util.Date getCrd_date() {
        return this.crd_date;
    }

    public void setCrd_date(final java.util.Date crd_date) {
        this.crd_date = crd_date;
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