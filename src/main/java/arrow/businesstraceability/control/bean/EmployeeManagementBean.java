package arrow.businesstraceability.control.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.omnifaces.cdi.ViewScoped;

import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.control.exception.TransactionRollbackException;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.EmployeeManagementService;
import arrow.businesstraceability.persistence.dto.Employee_mst_DTO;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Authority_mst;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Position_mst;
import arrow.framework.bean.AbstractBean;
import arrow.framework.util.i18n.Messages;


/**
 * The Class EmployeeManagementBean.
 */
@Named
@ViewScoped
@Transactional
public class EmployeeManagementBean extends AbstractBean {

    /** The list employee. */
    private List<Employee_mst> listEmployee;

    /** The current employee. */
    private Employee_mst_DTO currentEmployee;

    /** The employee service. */
    @Inject
    private EmployeeManagementService employeeService;

    /** The current faces context. */
    @Inject
    private FacesContext currentFacesContext;

    /** The Constant MAX_RECORD_PER_PAGE. */
    public static final int MAX_RECORD_PER_PAGE = 20;

    /** The employee head office. */
    private List<Addresspoint_mst> employeeHeadOffice;

    /** The employee position. */
    private List<Position_mst> employeePosition;

    /** The employee authority. */
    private List<Authority_mst> employeeAuthority;

    /**
     * Init data when open screen.
     */
    @PostConstruct
    public void initData() {
        this.employeeHeadOffice = this.employeeService.getAllAddress();
        this.employeePosition = this.employeeService.getAllPosition();
        this.employeeAuthority = this.employeeService.getAllAuthority();
    }

    /**
     * Get employee status name with code.
     *
     * @param empCondiCode the emp condi code
     * @return the employee status name
     */
    public String getEmployeeStatusName(final String empCondiCode) {
        if (AuthenticationConstants.EmployeeStatus.RETIRED.equalsIgnoreCase(empCondiCode)) {
            return Messages.get("retired");
        } else {
            return Messages.get("working");
        }
    }

    /**
     * call Add new Employee screen.
     *
     * @author lehoangngochan
     */
    public void addNewEmployee() {
        this.currentEmployee = new Employee_mst_DTO();
        this.currentEmployee.setEmp_condi_code_DIRECT(AuthenticationConstants.EmployeeStatus.WORKING.charAt(0));
    }

    /**
     * Call Edit Employee screen.
     *
     * @author lehoangngochan
     */
    public void editEmployee() {
        final ServiceResult<Employee_mst_DTO> result = this.employeeService.prepareForEditEmployee(this.listEmployee);

        if (result.isSuccessful()) {
            this.currentEmployee = result.getData();
        } else {
            result.showMessages(this.currentFacesContext);;
        }
    }

    /**
     * Call action delete Employee.
     *
     * @author lehoangngochan
     */
    public void deleteEmployee() {
        final ServiceResult<Employee_mst> result = this.employeeService.deleteEmployee(this.listEmployee);
        if (result.isSuccessful()) {
            this.listEmployee = this.employeeService.getListEmployee();
            this.selectedEmp = null;
            this.currentEmployee = null;
        }
        result.showMessages(this.currentFacesContext);
    }

    /**
     * Only change status employee : retired to working and vice versa.
     *
     * @author vanlongdao
     * @since 2015/05/29
     */
    public void changeEmployeeStatus() {
        final ServiceResult<Employee_mst> result = this.employeeService.changeEmployeeStatus(this.listEmployee);
        if (result.isSuccessful()) {
            this.listEmployee = null;
        }
        result.showMessages(this.currentFacesContext);
    }

    /**
     * save Employee when click Insert or update.
     *
     * @author lehoangngochan
     * @throws TransactionRollbackException the transaction rollback exception
     */

    public void saveEmployee() throws TransactionRollbackException {
        final ServiceResult<Employee_mst> result = this.employeeService.saveEmployee(this.currentEmployee);
        result.showMessages(this.currentFacesContext);
        this.currentEmployee = null;
        this.listEmployee = null;
    }

    /**
     * Close screen add or edit Employee.
     *
     * @author lehoangngochan
     */
    public void closeEmployee() {
        this.currentEmployee = null;
    }

    /**
     * reset action.
     *
     * @author lehoangngochan
     */
    public void reset() {
        if ((this.currentEmployee != null) && (this.currentEmployee.getEmp_code() == 0)) {
            // Reset at add new Employee screen
            this.currentEmployee = new Employee_mst_DTO();
        } else {
            // Reset at Edit Employee Screen
            this.editEmployee();
        }
    }

    /** The selected emp. */
    private Employee_mst selectedEmp;

    /**
     * checkbox handling.
     *
     * @author lehoangngochan
     * @param emp the emp
     */
    public void toggleSelection(final Employee_mst emp) {
        if (emp.isSelected()) {
            if (this.selectedEmp != null) {
                this.selectedEmp.setSelected(false);
            }
            this.selectedEmp = emp;
        } else {
            this.selectedEmp = null;
        }
    }

    /**
     * action autoComplete.
     *
     * @author lehoangngochan
     * @param query the query
     * @return list Addresspoint
     */
    public List<Addresspoint_mst> autoCompleteHeadOffice(final String query) {
        return this.employeeService.autoCompleteHeadOffice(query, this.employeeHeadOffice);
    }

    /**
     * action autoComplete.
     *
     * @author lehoangngochan
     * @param query the query
     * @return list Position
     */
    public List<Position_mst> autoCompletePosition(final String query) {
        return this.employeeService.autoCompletePosition(query, this.employeePosition);
    }

    /**
     * action autoComplete.
     *
     * @author lehoangngochan
     * @param query the query
     * @return list Authority
     */
    public List<Authority_mst> autoCompleteAuthority(final String query) {
        return this.employeeService.autoCompleteAuthority(query, this.employeeAuthority);
    }

    /**
     * Gets the select item head office.
     *
     * @return the select item head office
     */
    // Getter/Setter
    public List<SelectItem> getSelectItemHeadOffice() {
        return this.employeeService.getSelectItemAddresspoint();
    }

    /**
     * Gets the select item position.
     *
     * @return the select item position
     */
    public List<SelectItem> getSelectItemPosition() {
        return this.employeeService.getSelectItemPosition();
    }

    /**
     * Gets the select item authority.
     *
     * @return the select item authority
     */
    public List<SelectItem> getSelectItemAuthority() {
        return this.employeeService.getSelectItemAuthority();
    }

    /**
     * Get list employee.
     *
     * @return the list employee
     */
    public List<Employee_mst> getListEmployee() {
        if (this.listEmployee == null) {
            this.listEmployee = this.employeeService.getListEmployee();
        }
        return this.listEmployee;
    }

    /**
     * Gets the current employee.
     *
     * @return the current employee
     */
    public Employee_mst_DTO getCurrentEmployee() {
        return this.currentEmployee;
    }

    /**
     * Sets the current employee.
     *
     * @param currentEmployee the new current employee
     */
    public void setCurrentEmployee(final Employee_mst_DTO currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

    /**
     * Gets the checks for employee selected.
     *
     * @return the checks for employee selected
     */
    public Boolean getHasEmployeeSelected() {
        return this.selectedEmp != null;
    }
}
