package arrow.businesstraceability.control.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.persistence.entity.Acc_com_credit;
import arrow.businesstraceability.persistence.entity.Acc_com_finance;
import arrow.businesstraceability.persistence.repository.Acc_com_financeRepository;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.util.BeanCopier;

/**
 * The Class InputAccDataService.
 */
public class InputFinancialDataService extends AbstractService {

    /** The finance repo. */
    @Inject
    private Acc_com_financeRepository financeRepo;

    /**
     * Delete map finance.
     *
     * @param deletedMap the deleted map
     * @return the service result
     */
    public ServiceResult<Acc_com_finance> deleteMapFinance(final Map<Integer, Acc_com_finance> deletedMap) {
        if (deletedMap.size() > 0) {
            for (Entry<Integer, Acc_com_finance> entry : deletedMap.entrySet()) {
                ServiceResult<Acc_com_finance> result = this.deleteFinance(entry.getValue());
                if (result.isNotSuccessful()) {
                    return result;
                }
            }
        }
        return new ServiceResult<Acc_com_finance>(true, InfoMessage.proc4_003_delete_financial_data_successfully());
    }

    /**
     * Delete finance.
     *
     * @param finance the finance
     * @return the service result
     */
    public ServiceResult<Acc_com_finance> deleteFinance(final Acc_com_finance finance) {
        try {
            Acc_com_finance oldFinance = this.financeRepo.findFinanceById(finance.getId_finance()).getAnyResult();
            if (oldFinance != null) {
                this.financeRepo.remove(oldFinance);
            }
            return new ServiceResult<Acc_com_finance>(true, InfoMessage.proc4_003_delete_financial_data_successfully());
        } catch (final PersistenceException e) {
            this.log.debug(e.getMessage(), e);
            return new ServiceResult<Acc_com_finance>(false,
                ErrorMessage.proc4_004_delete_financial_data_unsuccessfully());
        }
    }

    /**
     * Save map finance to db.
     *
     * @param mapFinance the map finance
     * @param idComEntity the id com entity
     * @param credit the credit
     * @return the service result
     */
    public ServiceResult<Acc_com_finance> saveMapFinanceToDb(final Map<Integer, Acc_com_finance> mapFinance,
        final int idComEntity, final Acc_com_credit credit) {
        if (mapFinance.size() > 0) {
            for (Entry<Integer, Acc_com_finance> entry : mapFinance.entrySet()) {
                ServiceResult<Acc_com_finance> result = this.saveAccDataToDB(entry.getValue(), idComEntity, credit);
                if (result.isNotSuccessful()) {
                    return result;
                }
            }
        }
        return new ServiceResult<Acc_com_finance>(true, InfoMessage.proc4_001_save_financial_data_successfully());
    }

    /**
     * Save acc data.
     *
     * @param selectedFinance the selected finance
     * @param idComEntity the id com entity
     * @param credit the credit
     * @return the service result
     */
    private ServiceResult<Acc_com_finance> saveAccDataToDB(final Acc_com_finance selectedFinance,
        final int idComEntity, final Acc_com_credit credit) {
        try {
            if (selectedFinance.isPersisted()) {
                final Acc_com_finance financeInDb =
                    this.financeRepo.findFinanceById(selectedFinance.getId_finance()).getAnyResult();
                if (financeInDb != null) {
                    BeanCopier.copy(selectedFinance, financeInDb);
                    this.financeRepo.saveAndFlushAndRefresh(financeInDb);
                }
            } else {
                final int maxId = this.financeRepo.getMaxAccComFinanceId().getAnyResult();
                Acc_com_finance newFinance = new Acc_com_finance(maxId + 1);
                BeanCopier.copy(selectedFinance, newFinance);
                newFinance.setId_com_entity(idComEntity);
                newFinance.setId_credit(credit.getId_credit());
                this.financeRepo.saveAndFlushAndRefresh(newFinance);
            }
            return new ServiceResult<Acc_com_finance>(true, InfoMessage.proc4_001_save_financial_data_successfully());
        } catch (final PersistenceException e) {
            this.log.debug(e.getMessage(), e);
            return new ServiceResult<Acc_com_finance>(false,
                ErrorMessage.proc4_002_save_financial_data_unsuccessfully());
        }
    }

    /**
     * Gets the list finance.
     *
     * @param currentIdComEntity the current id com entity
     * @return the list finance
     */
    public List<Acc_com_finance> getListFinanceByIdComEntity(final int currentIdComEntity) {
        return this.financeRepo.getAllFinanceByIdComEntity(currentIdComEntity).getResultList();
    }

    /**
     * Gets the list finance by id credit.
     *
     * @param idCredit the id credit
     * @return the list finance by id credit
     */
    public List<Acc_com_finance> getListFinanceByIdCredit(final int idCredit) {
        return this.financeRepo.getAllFinanceByIdCredit(idCredit).getResultList();
    }
}
