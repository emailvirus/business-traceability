//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;

import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.persistence.dto.Employee_mst_DTO;
import arrow.businesstraceability.persistence.mapped.Employee_mst_MAPPED;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.DtoUtils;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Entity
public class Employee_mst extends Employee_mst_MAPPED {

    public Employee_mst() {
    }

    public Employee_mst(final int emp_code) {
        super(emp_code);
    }

    public static Employee_mst find(final int emp_code) {
        return EmLocator.getEm().find(Employee_mst.class, new Employee_mst.Pk(emp_code));
    }

    public Employee_mst_DTO getDto() {
        return DtoUtils.createDto(this, Employee_mst_DTO.class);
    }

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public boolean isRetired() {
        return (this.getEmp_condi_code_DIRECT() != null)
            && StringUtils.equalsIgnoreCase(this.getEmp_condi_code_DIRECT().toString(), "1");
    }

    public boolean isDeleted() {
        return this.getEmp_delete_flg();
    }

    public String getWorkingStatus() {
        if ((this.getEmp_condi_code_DIRECT() == null)
            || StringUtils.isEmpty(this.getEmp_condi_code_DIRECT().toString())) {
            return AuthenticationConstants.EmployeeStatus.WORKING;
        }
        return this.getEmp_condi_code_DIRECT().toString();
    }

    public void setWorkingStatus(final String pWorkingStatus) {
        this.setEmp_condi_code_DIRECT(pWorkingStatus.charAt(0));
    }

    public boolean isSupervisor() {
        return (AuthenticationConstants.EmployeePosition.MANAGEMENT == this.getEmp_poscode())
            || (AuthenticationConstants.EmployeePosition.REPRESENTER == this.getEmp_poscode());
    }

    public boolean isManager() {
        return AuthenticationConstants.EmployeePosition.MANAGEMENT == this.getEmp_poscode();
    }

    public boolean isRepresenter() {
        return AuthenticationConstants.EmployeePosition.REPRESENTER == this.getEmp_poscode();
    }

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}