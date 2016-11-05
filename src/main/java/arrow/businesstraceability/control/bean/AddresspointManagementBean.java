package arrow.businesstraceability.control.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.context.RequestContext;

import arrow.businesstraceability.constant.BranchPositionConstant;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.BranchPositionService;
import arrow.businesstraceability.control.service.EmployeeManagementService;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Branch_position;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.util.collections.CollectionUtils;

/**
 * Description of class.
 *
 * @author ArrowTechnology, Ltd.
 * @version Jan 12, 2016 {revision}
 */
@Named
@ViewScoped
public class AddresspointManagementBean extends AbstractBean {

    /** current address point. */
    private Addresspoint_mst currentAddresspoint;

    /** current list branch position. */
    private List<Branch_position> currentListBranchPosition;

    /** current employee. */
    private Employee_mst currentEmp;

    /** selected address point. */
    private Addresspoint_mst selectedAddresspoint;

    /** list selected branch position. */
    private List<Branch_position> listSelectedBranchPosition;

    /** list Address point. */
    private List<Addresspoint_mst> listAddresspoint;

    /** list management employee on current address point. */
    private List<Employee_mst> listManagementEmpOnCurrentAdp;

    /** branch position service. */
    @Inject
    private BranchPositionService branchPositionService;

    /** The employee service. */
    @Inject
    private EmployeeManagementService employeeService;

    /**
     * auto complete find branch.
     *
     * @param keyword
     */
    public List<Addresspoint_mst> autoCompleteHeadOffice(final String keyword) {
        return this.employeeService.autoCompleteHeadOffice(keyword, this.listAddresspoint);
    }

    /**
     * auto complete find Employee.
     *
     * @param keyword
     */
    public List<Employee_mst> filterEmployee(final String query) {
        if (StringUtils.isEmpty(query)) {
            return this.getListManagementEmpOnCurrentAdp();
        }

        return CollectionUtils.filter(this.getListManagementEmpOnCurrentAdp(), new Predicate() {

            @Override
            public boolean evaluate(final Object arg0) {
                final Employee_mst item = (Employee_mst) arg0;
                return item.getEmp_name().contains(query) || String.valueOf(item.getEmp_code()).contains(query);
            }
        });
    }

    /**
     * update list management employee on current branch.
     */
    public void updateCurentListManagerEmp() {
        this.currentEmp = null;
        if (this.selectedAddresspoint != null) {
            this.listManagementEmpOnCurrentAdp =
                this.branchPositionService.getActiveEmpByAdpCodeAndPostion(this.selectedAddresspoint.getAdp_code(),
                    (short) BranchPositionConstant.BranchPositionType.MANAGER);
        }
    }

    /**
     * Call Detail position screen.
     */
    public void settingSuppervisor() {
        final ServiceResult<Addresspoint_mst> result =
            this.branchPositionService.selectAddressPointInDb(this.selectedAddresspoint);
        if (result.isSuccessful()) {
            this.currentAddresspoint = result.getData();
            this.listManagementEmpOnCurrentAdp =
                this.branchPositionService.getActiveEmpByAdpCodeAndPostion(this.currentAddresspoint.getAdp_code(),
                    (short) BranchPositionConstant.BranchPositionType.MANAGER);
            this.currentListBranchPosition =
                this.branchPositionService
                    .getListBranchPositionByAdpCode(this.currentAddresspoint.getAdp_code(), false);

        } else {
            this.currentAddresspoint = null;

        }
    }

    /**
     * toggle address Selection.
     *
     * @param addressPoint
     */
    public void toggleAddressSelection(final Addresspoint_mst addressPoint) {
        if (addressPoint.isSelected()) {
            if (this.selectedAddresspoint != null) {
                this.selectedAddresspoint.setSelected(false);
            }
            this.selectedAddresspoint = addressPoint;
        } else {
            this.selectedAddresspoint = null;
        }
    }

    /**
     * toggle branch Selection.
     *
     * @param branch_position
     */
    public void toggleBranchSelection(final Branch_position branch_position) {
        if (this.listSelectedBranchPosition == null) {
            this.listSelectedBranchPosition = new ArrayList<Branch_position>();
        }
        if (branch_position.isSelected()) {
            this.listSelectedBranchPosition.add(branch_position);
        } else {
            for (int i = 0; i < this.listSelectedBranchPosition.size(); i++) {
                if (this.listSelectedBranchPosition.get(i).getEmployee_code() == branch_position.getEmployee_code()) {
                    this.listSelectedBranchPosition.remove(i);
                }
            }
        }
    }

    /**
     * remove supervisor.
     */
    public void removeSupervisor() {

        for (Iterator<Branch_position> branchCurrentIterator = this.currentListBranchPosition.iterator();
                branchCurrentIterator.hasNext();) {
            Branch_position branchCurrent = branchCurrentIterator.next();
            for (Iterator<Branch_position> branchSelectedIterator = this.listSelectedBranchPosition.iterator();
                    branchSelectedIterator.hasNext();) {
                Branch_position branchSelected = branchSelectedIterator.next();
                if (branchSelected.getEmployee_code() == branchCurrent.getEmployee_code()) {
                    branchSelectedIterator.remove();
                    branchCurrentIterator.remove();
                }
            }
        }
    }

    /**
     * chosen To Position.
     */
    public void chosenToPosition(final int postionType) {
        if (this.currentEmp == null) {
            ErrorMessage.apd_detail_002_no_employee_select().show();
            return;
        }
        for (Branch_position branch_position : this.currentListBranchPosition) {
            if (branch_position.getEmployee_code() == this.currentEmp.getEmp_code()) {
                ErrorMessage.apd_detail_001_supervisor_existed().show();
                return;
            }
        }
        Branch_position branch_position = new Branch_position();
        branch_position.setAddresspoint_mst(this.currentAddresspoint);
        branch_position.setEmployee_mst(this.currentEmp);
        branch_position.setDelete_flg(false);
        branch_position.setPosition_mst(this.branchPositionService.getPositionByCode((short) postionType));
        this.currentListBranchPosition.add(branch_position);
        this.selectedAddresspoint = this.currentAddresspoint;
        this.currentEmp = null;
        this.listManagementEmpOnCurrentAdp =
            this.branchPositionService.getActiveEmpByAdpCodeAndPostion(this.selectedAddresspoint.getAdp_code(),
                (short) BranchPositionConstant.BranchPositionType.MANAGER);

    }

    /**
     * save Branch position to database.
     */
    public void saveBranchPosition() {
        ServiceResult<Branch_position> result =
            this.branchPositionService.saveBranchPosition(this.currentAddresspoint.getAdp_code(),
                this.currentListBranchPosition);
        // this.listAddresspoint = this.branchPositionService.getListAddresspoint();
        if (result.isSuccessful()) {
            this.clearScreen();
        }
        result.showMessages(FacesContext.getCurrentInstance());
    }

    /**
     * reset Branch position.
     */
    public void resetBranchPosition() {
        this.currentListBranchPosition =
            this.branchPositionService.getListBranchPositionByAdpCode(this.currentAddresspoint.getAdp_code(), false);
        this.listSelectedBranchPosition = null;
        this.currentEmp = null;
        this.selectedAddresspoint = this.currentAddresspoint;
    }

    /**
     * show cancel dialog.
     */
    public void showCancelDialog() {
        if (this.branchPositionService.isChange(this.currentAddresspoint.getAdp_code(), this.currentListBranchPosition)) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('cancelDialog').show();");
            return;
        }
        this.clearScreen();

    }

    /**
     * is have branch chief.
     *
     * @return
     */
    public boolean haveBranchChief() {
        if (this.currentListBranchPosition != null) {
            for (Branch_position branch_position : this.currentListBranchPosition) {
                if (branch_position.getPos_code() == BranchPositionConstant.BranchPositionType.MANAGER) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * clear screen.
     */
    public void clearScreen() {
        this.currentAddresspoint = null;
        this.currentListBranchPosition = null;
        this.listSelectedBranchPosition = null;
        this.selectedAddresspoint = null;
        this.listAddresspoint = null;
        this.currentEmp = null;
    }

    public Addresspoint_mst getCurrentAddresspoint() {
        return this.currentAddresspoint;
    }

    public void setCurrentAddresspoint(final Addresspoint_mst currentAddresspoint) {
        this.currentAddresspoint = currentAddresspoint;
    }

    /**
     * get list Address point.
     *
     * @return
     */
    public List<Addresspoint_mst> getListAddresspoint() {
        if (CollectionUtils.isEmpty(this.listAddresspoint)) {
            this.listAddresspoint = this.branchPositionService.getListAddresspoint();
        }
        return this.listAddresspoint;
    }

    public void setListAddresspoint(final List<Addresspoint_mst> listAddresspoint) {
        this.listAddresspoint = listAddresspoint;
    }

    /**
     * get Current List Branch Position.
     *
     * @return
     */
    public List<Branch_position> getCurrentListBranchPosition() {
        return this.currentListBranchPosition;
    }

    public void setCurrentListBranchPosition(final List<Branch_position> currentListBranchPosition) {
        this.currentListBranchPosition = currentListBranchPosition;
    }

    public List<Branch_position> getListSelectedBranchPosition() {
        return this.listSelectedBranchPosition;
    }

    public void setListSelectedBranchPosition(final List<Branch_position> listSelectedBranchPosition) {
        this.listSelectedBranchPosition = listSelectedBranchPosition;
    }

    public List<Employee_mst> getListManagementEmpOnCurrentAdp() {
        return this.listManagementEmpOnCurrentAdp;
    }

    public void setListManagementEmpOnCurrentAdp(final List<Employee_mst> listManagementEmpOnCurrentAdp) {
        this.listManagementEmpOnCurrentAdp = listManagementEmpOnCurrentAdp;
    }

    public Employee_mst getCurrentEmp() {
        return this.currentEmp;
    }

    public void setCurrentEmp(final Employee_mst currentEmp) {
        this.currentEmp = currentEmp;
    }

    public Addresspoint_mst getSelectedAddresspoint() {
        return this.selectedAddresspoint;
    }

    public void setSelectedAddresspoint(final Addresspoint_mst selectedAddresspoint) {
        this.selectedAddresspoint = selectedAddresspoint;
    }

}
