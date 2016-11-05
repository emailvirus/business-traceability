//@formatter:off
package arrow.businesstraceability.persistence.dto;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.mapped.Employee_mst_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

public class Employee_mst_DTO extends Employee_mst {
    private Employee_mst_MAPPED.Pk pk;

    public void setPk(final Employee_mst_MAPPED.Pk pk) {
        this.pk = pk;
    }
    
    @Override
    public Employee_mst_MAPPED.Pk getPk() {
        return this.pk;
    }

    private int emp_code;

    @Override
    public int getEmp_code() {
        return this.emp_code;
    }

    public void setEmp_code(final int emp_code) {
        this.emp_code = emp_code;
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