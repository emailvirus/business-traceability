package arrow.businesstraceability.control.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.control.helper.ServiceErrorCode;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.framework.util.cdi.Instance;

/**
 * The Class AddressPointService.
 */
@Named
public class AddressPointService extends AbstractService {

    /** The repo. */
    @Inject
    private Addresspoint_mstRepository repo;

    /**
     * Gets the single instance of AddressPointService.
     *
     * @return single instance of AddressPointService
     */
    public static AddressPointService getInstance() {
        return Instance.get(AddressPointService.class);
    }

    /**
     * Gets the all address points.
     *
     * @return the all address points
     */
    public ServiceResult<List<Addresspoint_mst>> getAllAddressPoints() {
        List<Addresspoint_mst> points = this.repo.findAll();
        if (points.isEmpty()) {
            return new ServiceResult<>(false, points, ServiceErrorCode.ERR_NOT_FOUND);
        }
        return new ServiceResult<>(true, points);
    }

    /**
     * Gets the address points of employee who visited company.
     *
     * @param companyCode the company code
     * @return the address points of employee who visited company
     */
    public ServiceResult<List<Addresspoint_mst>> getAddressPointsOfEmployeeWhoVisitedCompany(final String companyCode) {
        List<Addresspoint_mst> allAddressPoints =
                this.repo.getDistinctAddressPointOfSalesPersonBaseOnCustomerCode(companyCode);
        if (allAddressPoints.isEmpty()) {
            return new ServiceResult<>(false, allAddressPoints, ServiceErrorCode.ERR_NOT_FOUND);
        }

        return new ServiceResult<>(true, allAddressPoints);
    }

    public Addresspoint_mst findAddresspointByAdpCode(final String adpCode) {
        return this.repo.findAddresspointByCode(adpCode).getAnyResult();
    }


}
