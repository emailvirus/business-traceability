//@formatter:off
package arrow.businesstraceability.persistence.mapped;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import arrow.businesstraceability.persistence.entity.Abstract_entity;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@MappedSuperclass
public class User_login_MAPPED extends Abstract_entity {
    // method for ArrowEntity
    @Override
    public Class<? extends arrow.businesstraceability.persistence.entity.User_login> getEntityClass() {
        return arrow.businesstraceability.persistence.entity.User_login.class;
    }

    @Embeddable
    public static class Pk  extends arrow.framework.persistence.BasePk{
        public Pk(){
        }

        /**
         * PK constructor.
         *
         * @param ul_user_code Data type: int
         *
         */
        public Pk(final int ul_user_code) {
            this.ul_user_code = ul_user_code;
        }

        @Column(name = "UL_USER_CODE")
        protected int ul_user_code;

        public int getUl_user_code() {
            return this.ul_user_code;
        }
    }

    //default constructor
    public User_login_MAPPED() {
    }

    protected User_login_MAPPED(final arrow.businesstraceability.persistence.entity.Employee_mst employee_mst) {
        this.checkFKConsistency(employee_mst);
        this.pk = new Pk(employee_mst.getEmp_code());
        this.employee_mst = employee_mst;
        this.ul_user_code = employee_mst.getEmp_code();
    }

    private void checkFKConsistency(arrow.businesstraceability.persistence.entity.Employee_mst employee_mst) {
        if (employee_mst == null) {
            throw new IllegalArgumentException("Parameter employee_mst must not be null");
        }
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
    protected int ul_user_code;

    public int getUl_user_code() {
        return this.ul_user_code;
    }

    //Normal columns

    @Column
    private java.lang.String ul_locale;

    public java.lang.String getUl_locale() {
        return this.ul_locale;
    }

    public final java.lang.String getUl_locale_DIRECT() {
        return this.ul_locale;
    }

    public void setUl_locale(final java.lang.String ul_locale) {
        this.ul_locale = ul_locale;
    }

    public final void setUl_locale_DIRECT(final java.lang.String ul_locale) {
        this.ul_locale = ul_locale;
    }

    @Column
    private java.util.Date ul_login_time;

    public java.util.Date getUl_login_time() {
        return this.ul_login_time;
    }

    public final java.util.Date getUl_login_time_DIRECT() {
        return this.ul_login_time;
    }

    public void setUl_login_time(final java.util.Date ul_login_time) {
        this.ul_login_time = ul_login_time;
    }

    public final void setUl_login_time_DIRECT(final java.util.Date ul_login_time) {
        this.ul_login_time = ul_login_time;
    }

    @Column
    private java.lang.String ul_passsalt;

    public java.lang.String getUl_passsalt() {
        return this.ul_passsalt;
    }

    public final java.lang.String getUl_passsalt_DIRECT() {
        return this.ul_passsalt;
    }

    public void setUl_passsalt(final java.lang.String ul_passsalt) {
        this.ul_passsalt = ul_passsalt;
    }

    public final void setUl_passsalt_DIRECT(final java.lang.String ul_passsalt) {
        this.ul_passsalt = ul_passsalt;
    }

    @Column
    private java.lang.String ul_password;

    public java.lang.String getUl_password() {
        return this.ul_password;
    }

    public final java.lang.String getUl_password_DIRECT() {
        return this.ul_password;
    }

    public void setUl_password(final java.lang.String ul_password) {
        this.ul_password = ul_password;
    }

    public final void setUl_password_DIRECT(final java.lang.String ul_password) {
        this.ul_password = ul_password;
    }

    @Column
    private java.lang.String ul_session_id;

    public java.lang.String getUl_session_id() {
        return this.ul_session_id;
    }

    public final java.lang.String getUl_session_id_DIRECT() {
        return this.ul_session_id;
    }

    public void setUl_session_id(final java.lang.String ul_session_id) {
        this.ul_session_id = ul_session_id;
    }

    public final void setUl_session_id_DIRECT(final java.lang.String ul_session_id) {
        this.ul_session_id = ul_session_id;
    }

    @Column
    private java.util.Date ul_update_date;

    public java.util.Date getUl_update_date() {
        return this.ul_update_date;
    }

    public final java.util.Date getUl_update_date_DIRECT() {
        return this.ul_update_date;
    }

    public void setUl_update_date(final java.util.Date ul_update_date) {
        this.ul_update_date = ul_update_date;
    }

    public final void setUl_update_date_DIRECT(final java.util.Date ul_update_date) {
        this.ul_update_date = ul_update_date;
    }

    //Foreign keys

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "UL_USER_CODE", referencedColumnName = "EMP_CODE", insertable = false, updatable = false)
        })
    @org.hibernate.annotations.LazyToOne(org.hibernate.annotations.LazyToOneOption.NO_PROXY)
    @org.hibernate.annotations.NotFound(action = org.hibernate.annotations.NotFoundAction.IGNORE)
    protected arrow.businesstraceability.persistence.entity.Employee_mst employee_mst;

    public arrow.businesstraceability.persistence.entity.Employee_mst getEmployee_mst() {
        return this.employee_mst;
    }

    /**
     * Set Employee_mst for User_login_MAPPED.
     *
     * @param employee_mst Employee_mst.
     *
     **/
    public void setEmployee_mst(final arrow.businesstraceability.persistence.entity.Employee_mst employee_mst) {
        if (employee_mst == null) {
            throw new IllegalArgumentException(
                    "Param of User_login_MAPPED.setEmployee_mst(Employee_mst employee_mst) must not be null");
        }
        else {
            this.ul_user_code = employee_mst.getEmp_code();
        }
        this.employee_mst = employee_mst;
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */



    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}