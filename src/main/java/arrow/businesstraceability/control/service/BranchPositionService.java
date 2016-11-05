package arrow.businesstraceability.control.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Branch_position;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Position_mst;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.businesstraceability.persistence.repository.Branch_positionRepository;
import arrow.businesstraceability.persistence.repository.Employee_mstRepository;
import arrow.businesstraceability.persistence.repository.Position_mstRepository;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.util.cdi.Instance;

/**
 * The Class AddressPointService.
 */
@Named
public class BranchPositionService extends AbstractService {

    /** The repo. */
    @Inject
    private Branch_positionRepository branchPositionRepo;

    /** The address point repo. */
    @Inject
    private Addresspoint_mstRepository addresspointRepo;

    /** The Employee_mst Repository . */
    @Inject
    private Employee_mstRepository empRepo;

    /** The Position_mst Repository . */
    @Inject
    private Position_mstRepository posRepo;

    /**
     * Gets the single instance of BranchPositionService.
     *
     * @return single instance of BranchPositionService
     */
    public static BranchPositionService getInstance() {
        return Instance.get(BranchPositionService.class);
    }

    /**
     * get List Branch Position By Adp_Code.
     *
     * @param adpCode
     * @param isDeleted
     * @return
     */
    public List<Branch_position> getListBranchPositionByAdpCode(final String adpCode, final boolean isDeleted) {
        return this.branchPositionRepo.findByAdpCode(adpCode, isDeleted).getResultList();
    }

    /**
     * get postion_mst by pos_code.
     *
     * @param pos_code
     * @return
     */
    public Position_mst getPositionByCode(final short pos_code) {
        return this.posRepo.findByPostCode(pos_code);
    }

    /**
     * get all active employee with type 101 by adp_code.
     *
     * @param adpCode
     * @return
     */
    public List<Employee_mst> getActiveEmpByAdpCodeAndPostion(final String adpCode, final Short posCode) {
        return this.empRepo.getActiveEmpByAdpCodeAndPostion(adpCode, posCode).getResultList();
    }

    /**
     * find selected address point in database.
     *
     * @param selectedAddresspoint
     * @return
     */
    public ServiceResult<Addresspoint_mst> selectAddressPointInDb(final Addresspoint_mst selectedAddresspoint) {
        this.addresspointRepo.findAndRefresh(selectedAddresspoint);

        return new ServiceResult<>(true, selectedAddresspoint.getDto());
    }

    /**
     * Filter datatable.
     *
     * @author honglm
     * @return list companies
     */
    public List<Addresspoint_mst> getListAddresspoint() {
        final ArrowQuery<Addresspoint_mst> query = new ArrowQuery<>(super.emMain);
        query.select("e").from("Addresspoint_mst e ");
        query.addSorter("adp_name", "e.adp_name");
        query.addFilter("adp_name", "e.adp_name LIKE ?");
        query.addSorter("adp_code", "e.adp_code");
        query.addFilter("adp_code", "e.adp_code LIKE ?");

        return query.getResultList();
    }

    /**
     * save Branch position.
     *
     * @param adpCode
     * @param currentListBranchPosition
     * @return
     */
    public ServiceResult<Branch_position> saveBranchPosition(final String adpCode,
            final List<Branch_position> currentListBranchPosition) {
        List<Branch_position> currentBranchListInDb = this.getListBranchPositionByAdpCode(adpCode, false);
        List<Message> messages = new ArrayList<>();
        try {
            for (Iterator<Branch_position> branchInScreenIterator =
                    currentListBranchPosition.iterator(); branchInScreenIterator.hasNext();) {
                Branch_position branchInScreen = branchInScreenIterator.next();
                boolean isUpdate = false;
                Branch_position branchInDb = null;
                for (Iterator<Branch_position> branchCurrentInDbIterator =
                        currentBranchListInDb.iterator(); branchCurrentInDbIterator.hasNext();) {
                    branchInDb = branchCurrentInDbIterator.next();
                    if (branchInDb.getEmployee_code() == branchInScreen.getEmployee_code()) {
                        branchCurrentInDbIterator.remove();
                        BeanCopier.copy(branchInScreen, branchInDb, "Addresspoint_mst", "Position_mst", "Employee_mst");
                        this.branchPositionRepo.saveAndFlushAndRefresh(branchInDb);
                        isUpdate = true;
                        break;
                    }
                }
                if (!isUpdate) {
                    Branch_position newPosition = new Branch_position(this.branchPositionRepo.getMaxId() + 1);
                    BeanCopier.copy(branchInScreen, newPosition, "Addresspoint_mst", "Position_mst", "Employee_mst");
                    this.branchPositionRepo.saveAndFlushAndRefresh(newPosition);
                }
            }
            for (Branch_position branchPosition : currentBranchListInDb) {

                branchPosition.setDelete_flg(true);
                this.branchPositionRepo.saveAndFlushAndRefresh(branchPosition);
            }

            messages.add(InfoMessage.common_001_save_successfully());
            return new ServiceResult<Branch_position>(true, messages);
        } catch (PersistenceException pex) {
            this.log.debug(pex.getMessage());
        }
        messages.add(ErrorMessage.common_002_save_unsuccessfully());
        return new ServiceResult<Branch_position>(false, messages);
    }

    /**
     * check for change.
     *
     * @param adpCode
     * @param currentListBranchPosition
     * @return
     */
    public boolean isChange(final String adpCode, final List<Branch_position> currentListBranchPosition) {
        List<Branch_position> listBranchInDb = this.branchPositionRepo.findByAdpCode(adpCode, false).getResultList();
        if (listBranchInDb.size() != currentListBranchPosition.size()) {
            return true;
        }

        for (Branch_position branchInScreen : currentListBranchPosition) {
            boolean isExistedInDb = false;
            for (Branch_position branchInDb : listBranchInDb) {
                if (branchInDb.getEmployee_code() == branchInScreen.getEmployee_code()) {
                    isExistedInDb = true;
                    if (branchInDb.getPos_code() != branchInScreen.getPos_code()) {
                        return true;
                    }
                }
            }
            if (!isExistedInDb) {
                return true;
            }
        }
        return false;
    }

}
