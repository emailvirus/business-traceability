package arrow.businesstraceability.control.service;

import java.util.List;

import javax.inject.Inject;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.persistence.entity.Acc_com_credit;
import arrow.businesstraceability.persistence.entity.Acc_com_entity;
import arrow.businesstraceability.persistence.entity.San_com_info;
import arrow.businesstraceability.persistence.repository.Acc_com_creditRepository;
import arrow.businesstraceability.persistence.repository.Acc_com_entityRepository;
import arrow.businesstraceability.persistence.repository.San_com_infoRepository;
import arrow.businesstraceability.persistence.repository.San_com_udomainRepository;
import arrow.framework.util.StringUtils;


/**
 * The Class MaintenanceAcountingDataService.
 */
public class MaintenanceAcountingDataService extends AbstractService {

    @Inject
    private Acc_com_entityRepository acc_com_entityRepository;

    @Inject
    private San_com_infoRepository san_com_infoRepository;

    @Inject
    private San_com_udomainRepository san_com_udomainRepository;

    @Inject
    private Acc_com_creditRepository acc_com_creditRepository;

    /**
     * Convert to id int san company.
     *
     * @param value the value
     * @return the integer
     */
    public Integer convertToIdIntSanCompany(final String value) {
        if (StringUtils.isIntegerValue(value)) {
            return Integer.parseInt(value);
        }
        San_com_info com_info =
            this.san_com_infoRepository.getSanComInfoWithIdSanCompany(value.toUpperCase()).getAnyResult();
        return com_info != null ? com_info.getId_company() : null;
    }

    /**
     * Gets the company info by id int sansan company.
     *
     * @param idSanCompany the id san company
     * @return the company info by id int sansan company
     */
    public San_com_info getCompanyInfoByIdIntSansanCompany(final int idSanCompany) {
        return this.san_com_infoRepository.getSanComInfoWithIdCompany(idSanCompany).getAnyResult();
    }

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
     * Gets the accounting data by id int company.
     *
     * @param idCompany the id company
     * @return the accounting data by id int company
     */
    public Acc_com_entity getAccountingDataByIdIntCompany(final Integer idCompany) {
        return this.acc_com_entityRepository.getComEntityByIdIntSanCom(idCompany).getAnyResult();
    }

    /**
     * Save accounting data to DB.
     *
     * @param accComEntity the acc com entity
     * @param idCompanySansan the id company sansan
     * @return the acc_com_entity
     */
    public Acc_com_entity saveAccComEntityData(final Acc_com_entity accComEntity, final Integer idCompanySansan) {
        accComEntity.setAc_update(this.currentUser.getUserCode());
        accComEntity.setId_int_san_company(idCompanySansan);
        return this.acc_com_entityRepository.saveAndFlushAndRefresh(accComEntity);
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

    /**
     * Gets the com entity by company name and id mapping not null.
     *
     * @param companyName the company name
     * @return the com entity by company name and id mapping not null
     */
    public List<Acc_com_entity> getComEntityByCompanyNameAndIdMappingNotNull(final String companyName) {
        return this.acc_com_entityRepository.getComEntityByCompanyNameAndIdMappingNotNull(
            StringUtils.buildLikeValueExpression(companyName)).getResultList();
    }

    /**
     * Gets the com entity by company name and id mapping null.
     *
     * @param companyName the company name
     * @return the com entity by company name and id mapping null
     */
    public List<Acc_com_entity> getComEntityByCompanyNameAndIdMappingNull(final String companyName) {
        return this.acc_com_entityRepository.getComEntityByNameNotMappingSansan(
            StringUtils.buildLikeValueExpression(companyName)).getResultList();
    }

    /**
     * Gets the acc com with id san company and id mapping not null.
     *
     * @param idSanCompany the id san company
     * @return the acc com with id san company and id mapping not null
     */
    public List<Acc_com_entity> getAccComWithIdSanCompanyAndIdMappingNotNull(final String idSanCompany) {
        San_com_info com_info =
            this.san_com_infoRepository.getSanComInfoWithIdSanCompany(idSanCompany.toUpperCase()).getAnyResult();
        if ((com_info != null) && (com_info.getId_company() != null)) {
            return this.acc_com_entityRepository.getComEntityByIdIntSanCom(com_info.getId_company()).getResultList();
        }
        return null;
    }


    /**
     * Gets the com entity by id int san company and name.
     *
     * @param idIntSanCompany the id int san company
     * @param companyName the company name
     * @return the com entity by id int san company and name
     */
    public List<Acc_com_entity> getComEntityByIdIntSanCompanyAndName(final int idIntSanCompany, final String companyName) {
        return this.acc_com_entityRepository.getComEntityByIdSanCompanyAndName(idIntSanCompany,
            StringUtils.buildLikeValueExpression(companyName)).getResultList();
    }

    /**
     * Gets the acc com entity by id san company and name.
     *
     * @param idSanCompany the id san company
     * @param companyName the company name
     * @return the acc com entity by id san company and name
     */
    public List<Acc_com_entity> getAccComEntityByIdSanCompanyAndName(final String idSanCompany, final String companyName) {
        San_com_info com_info =
            this.san_com_infoRepository.getSanComInfoWithIdSanCompany(idSanCompany.toUpperCase()).getAnyResult();
        if ((com_info != null) && (com_info.getId_company() != null)) {
            return this.acc_com_entityRepository.getComEntityByIdSanCompanyAndName(com_info.getId_company(),
                StringUtils.buildLikeValueExpression(companyName)).getResultList();
        }
        return null;
    }

    /**
     * Gets the acc com entity by id int company.
     *
     * @param idCompany the id company
     * @return the acc com entity by id int company
     */
    public List<Acc_com_entity> getAccComEntityByIdIntCompany(final Integer idCompany) {
        return this.acc_com_entityRepository.getComEntityByIdIntSanCom(idCompany).getResultList();
    }

    public List<Acc_com_entity> getAllAccComEntityNotMapping() {
        return this.acc_com_entityRepository.getComEntityNotMappingSansan().getResultList();
    }

    /**
     * Save code_acc_prohibitcause.
     *
     * @param prohibitCause the prohibit cause
     * @param currentAccCom the current acc com
     */
    public void saveCode_acc_prohibitcause(final String prohibitCause, final Acc_com_entity currentAccCom) {
        Acc_com_credit accComCredit =
            this.acc_com_creditRepository.findAccComCreditByIdEntityAndIdCredit(currentAccCom.getId_com_entity(),
                currentAccCom.getId_credit()).getAnyResult();
        accComCredit.setCode_acc_prohibitcause(prohibitCause);
        this.acc_com_creditRepository.saveAndFlushAndRefresh(accComCredit);
    }

    public String getIdSanCompanyInSanComInfoWithIdCompany(final int idCompany) {
        return this.san_com_infoRepository.getIdSanCompanyInSanComInfoWithIdCompany(idCompany).getAnyResult();
    }

    public String getCode_acc_prohibitcauseByAccComEntity(final Acc_com_entity currentAccCom) {
        return this.acc_com_creditRepository.findCode_acc_prohibitcauseByAccCom(currentAccCom.getId_com_entity(),
            currentAccCom.getId_credit()).getAnyResult();
    }

    public Acc_com_entity findAccComEntityByIdComEntity(final int idComEntity) {
        return this.acc_com_entityRepository.findByIdComEntity(idComEntity).getAnyResult();
    }

}
