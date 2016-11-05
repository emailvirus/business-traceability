//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.mapped.Company_mst_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Company_mst_DTO extends Company_mst {
    private Company_mst_MAPPED.Pk pk;

    public void setPk(final Company_mst_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Company_mst_MAPPED.Pk getPk() {
        return this.pk;
    }

    private java.lang.String com_company_code;

    @Override
    public java.lang.String getCom_company_code() {
        return this.com_company_code;
    }

    public void setCom_company_code(final java.lang.String com_company_code) {
        this.com_company_code = com_company_code;
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