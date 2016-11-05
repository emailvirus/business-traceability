package arrow.businesstraceability.control.service;

import java.util.List;

import javax.inject.Inject;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.persistence.entity.Industry_big_mst;
import arrow.businesstraceability.persistence.repository.Industry_big_mstRepository;

/**
 * The Class IndustryService.
 */
public class IndustryService extends AbstractService {

    /** The repo. */
    @Inject
    private Industry_big_mstRepository repo;

    /**
     * Gets the all industry big.
     *
     * @return the all industry big
     */
    public List<Industry_big_mst> getAllIndustryBig() {
        return this.repo.findAll();
    }
}
