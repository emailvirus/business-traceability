//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.User_login;
import arrow.businesstraceability.persistence.mapped.User_login_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class User_login_DTO extends User_login {
    private User_login_MAPPED.Pk pk;

    public void setPk(final User_login_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public User_login_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int ul_user_code;

    @Override
    public int getUl_user_code() {
        return this.ul_user_code;
    }

    public void setUl_user_code(final int ul_user_code) {
        this.ul_user_code = ul_user_code;
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