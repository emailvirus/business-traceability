//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Personnel_affairs_info;
import arrow.businesstraceability.persistence.mapped.Personnel_affairs_info_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Personnel_affairs_info_DTO extends Personnel_affairs_info {
    private Personnel_affairs_info_MAPPED.Pk pk;

    public void setPk(final Personnel_affairs_info_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Personnel_affairs_info_MAPPED.Pk getPk() {
        return this.pk;
    }

    private java.lang.String pea_business_code;

    @Override
    public java.lang.String getPea_business_code() {
        return this.pea_business_code;
    }

    public void setPea_business_code(final java.lang.String pea_business_code) {
        this.pea_business_code = pea_business_code;
    }
  
    private java.util.Date pea_date;

    @Override
    public java.util.Date getPea_date() {
        return this.pea_date;
    }

    public void setPea_date(final java.util.Date pea_date) {
        this.pea_date = pea_date;
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