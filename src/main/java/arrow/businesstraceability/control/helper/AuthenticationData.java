package arrow.businesstraceability.control.helper;

import java.io.Serializable;

import arrow.businesstraceability.persistence.entity.Employee_mst;


/**
 * The Class AuthenticationData.
 */
public class AuthenticationData implements Serializable {

    /** The usercode. */
    private final int usercode;

    /** The employee. */
    private final Employee_mst employee;

    /** The is first login. */
    private boolean isFirstLogin;

    /**
     * Gets the employee.
     *
     * @return the employee
     */
    public Employee_mst getEmployee() {
        return this.employee;
    }

    /**
     * Instantiates a new authentication data.
     *
     * @param usercode the usercode
     * @param employee the employee
     */
    public AuthenticationData(final int usercode, final Employee_mst employee) {
        this.usercode = usercode;
        this.employee = employee;
    }

    /**
     * Gets the usercode.
     *
     * @return the usercode
     */
    public int getUsercode() {
        return this.usercode;
    }

    /**
     * Checks if is first login.
     *
     * @return true, if is first login
     */
    public boolean isFirstLogin() {
        return this.isFirstLogin;
    }

    /**
     * Sets the first login.
     *
     * @param isFirstLogin the new first login
     */
    public void setFirstLogin(final boolean isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }
}
