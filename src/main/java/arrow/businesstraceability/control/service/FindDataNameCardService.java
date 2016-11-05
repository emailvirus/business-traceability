package arrow.businesstraceability.control.service;

import javax.inject.Inject;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.persistence.entity.Acc_com_entity;
import arrow.businesstraceability.persistence.entity.San_com_info;
import arrow.businesstraceability.persistence.repository.Acc_com_entityRepository;
import arrow.businesstraceability.persistence.repository.San_com_infoRepository;
import arrow.businesstraceability.persistence.repository.San_com_udomainRepository;

/**
 * The Class FindDataNameCardService.
 */
public class FindDataNameCardService extends AbstractService {


    @Inject
    private San_com_udomainRepository san_com_udomainRepository;

    @Inject
    private San_com_infoRepository san_com_infoRepository;

    @Inject
    private Acc_com_entityRepository acc_com_entityRepository;

    /**
     * Count number url of company by id company.
     *
     * @param idCompany the id company
     * @return the int
     */
    public int countNumberUrlOfCompanyByIdCompany(final Integer idCompany) {
        return this.san_com_udomainRepository.countNumberUrlOfCompanyByIdCompany(idCompany);
    }

    /**
     * Gets the com entity by customer code.
     *
     * @param customerCode the customer code
     * @return the com entity by customer code
     */
    public Acc_com_entity getComEntityByCustomerCode(final String customerCode) {
        return this.acc_com_entityRepository.getComEntityByCustomerCode(customerCode).getAnyResult();
    }

    public San_com_info getSanComInfoByIdCompany(final Integer idCompany) {
        return this.san_com_infoRepository.getSanComInfoWithIdCompany(idCompany).getAnyResult();
    }

}
