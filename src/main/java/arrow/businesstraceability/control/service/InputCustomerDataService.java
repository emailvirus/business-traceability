package arrow.businesstraceability.control.service;

import java.util.List;

import javax.inject.Inject;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.cache.entity.InputCustomerDataEntity;
import arrow.businesstraceability.persistence.entity.Acc_com_credit;
import arrow.businesstraceability.persistence.entity.Acc_com_relation;
import arrow.businesstraceability.persistence.mapped.Acc_com_relation_MAPPED;
import arrow.businesstraceability.persistence.mapped.Acc_com_relation_MAPPED.Pk;
import arrow.businesstraceability.persistence.repository.Acc_com_entityRepository;
import arrow.businesstraceability.persistence.repository.Acc_com_relationRepository;

/**
 * The Class InputCustomerDataService.
 */
public class InputCustomerDataService extends AbstractService {

    /** The acc_com_relation repository. */
    @Inject
    Acc_com_relationRepository acc_com_relationRepository;

    /** The acc_com_entity repository. */
    @Inject
    Acc_com_entityRepository acc_com_entityRepository;


    /**
     * Gets the acc com relation by pk.
     *
     * @param pk the pk
     * @return the acc com relation by pk
     */
    public Acc_com_relation getAccComRelationByPk(final int pk) {
        Acc_com_relation_MAPPED.Pk key = new Pk(pk);
        return this.acc_com_relationRepository.findBy(key);
    }

    /**
     * Removes the acc com relation by id com relation.
     *
     * @param id the id
     */
    public void removeAccComRelationByIdComRelation(final int id) {
        this.acc_com_relationRepository.removeAccComRelationByIdComRelation(id);
    }


    /**
     * Gets the list acc com relation by id com entity and id credit.
     *
     * @param idComEntity the id com entity
     * @param idCredit the id credit
     * @return the list acc com relation by id com entity and id credit
     */
    public List<Acc_com_relation> getListAccComRelationByIdComEntityAndIdCredit(final int idComEntity,
        final int idCredit) {
        return this.acc_com_relationRepository.getListAccComRelationByIdComEntityAndIdCredit(idComEntity, idCredit);
    }


    /**
     * Save acc com relation.
     *
     * @param entity the entity
     */
    public void saveAccComRelation(final Acc_com_relation entity) {

        this.acc_com_relationRepository.saveAndFlushAndRefresh(entity);
    }

    /**
     * Gen next id com relation.
     *
     * @return the int
     */
    public int genNextIdComRelation() {
        Integer currentId = this.acc_com_relationRepository.getMaxIdComRelation().getAnyResult();
        if (null == currentId) {
            return 1;
        }
        return currentId + 1;
    }

    /**
     * Creates the input customer data entity by acc com relation and status acc com credit.
     *
     * @param tempAccComRelation the temp acc com relation
     * @return the input customer data entity
     */
    public InputCustomerDataEntity createInputCustomerDataEntityByAccComRelation(
        final Acc_com_relation tempAccComRelation) {
        InputCustomerDataEntity temp = new InputCustomerDataEntity();
        temp.setIdComRelation(tempAccComRelation.getId_com_relation());
        temp.setId_com_entity(tempAccComRelation.getId_com_entity());
        temp.setName_com_relation(tempAccComRelation.getName_com_relation());
        temp.setEnum_com_relation(tempAccComRelation.getEnum_com_relation());
        temp.setId_credit(tempAccComRelation.getId_credit());
        temp.setStatus(tempAccComRelation.getAcc_com_credit().getStatus());
        temp.setClassify(String.valueOf(tempAccComRelation.getEnum_com_relation()));
        return temp;
    }

    /**
     * Update acc com relation by acc com credit and input customer data entity.
     *
     * @param tempAccComRelation the temp acc com relation
     * @param credit the credit
     * @param currentInputCustomerDataEntity the current input customer data entity
     * @return the acc_com_relation
     */
    public Acc_com_relation updateAccComRelationByAccComCreditAndInputCustomerDataEntity(
        final Acc_com_relation tempAccComRelation, final Acc_com_credit credit,
        final InputCustomerDataEntity currentInputCustomerDataEntity) {
        tempAccComRelation.setId_com_entity(credit.getId_com_entity());
        tempAccComRelation.setId_credit(credit.getId_credit());
        tempAccComRelation.setEnum_com_relation(currentInputCustomerDataEntity.getEnum_com_relation());
        tempAccComRelation.setName_com_relation(currentInputCustomerDataEntity.getName_com_relation());
        return tempAccComRelation;
    }
}
