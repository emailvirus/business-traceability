//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.Date;

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Acc_com_credit;
import arrow.businesstraceability.persistence.mapped.Acc_com_credit_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Acc_com_creditRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Acc_com_credit, Acc_com_credit_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT ac FROM Acc_com_credit ac WHERE ac.id_credit = ?1")
    public abstract QueryResult<Acc_com_credit> getCreditById(final int idCredit);

    @Query("SELECT MAX(a.id_credit) FROM Acc_com_credit a")
    public abstract QueryResult<Integer> getMaxIdOfCredit();

    @Query("SELECT a FROM Acc_com_credit a WHERE a.id_credit = ?1")
    public abstract Acc_com_credit findByIdCredit(int i);

    @Query("SELECT a FROM Acc_com_credit a WHERE a.id_com_entity = ?1 ORDER BY a.status ASC, a.date_survey DESC")
    public abstract QueryResult<Acc_com_credit> getAllCreditByIdComEntity(int id_com_entity);

    @Query("SELECT COUNT(a) FROM Acc_com_credit a WHERE a.id_com_entity = ?1 AND a.status = ?2")
    public abstract long countAllCreditWithStatus(int id_com_entity, char status);

    @Query("SELECT COUNT(a) FROM Acc_com_credit a WHERE a.id_com_entity = ?1 AND a.ac_request = ?2 AND a.status = ?3")
    public abstract long countAllCreditWithAcRequestAndStatus(int id_com_entity, int ac_request, char status);

    @Query("SELECT COUNT(a) FROM Acc_com_credit a WHERE a.id_com_entity = ?1 AND a.id_credit <> ?2 AND a.ac_request = ?3 AND a.code_acc_surveyer = ?4 AND a.date_survey = ?5")
    public abstract long countCreditSameInfoSurvey(int id_com_entity, int id_credit, int ac_request,
        String code_acc_surveyer, Date date_survey);

    @Query("SELECT a.date_survey FROM Acc_com_credit a WHERE a.id_com_entity = ?1 AND a.id_credit = ?2")
    public abstract QueryResult<Date> findSurveyDateByIdEntityAndIdCredit(int id_com_entity, int id_credit);

    @Query(
        value = "SELECT a.id_credit FROM Acc_com_credit a WHERE (a.id_com_entity, a.date_survey) IN (SELECT a1.id_com_entity, MAX(a1.date_survey) FROM Acc_com_credit a1 WHERE a1.id_com_entity = ?1 AND a1.status = ?2 GROUP BY a1.id_com_entity) AND a.status = ?2",
        max = 1)
    public abstract int findLatestIdCreditByIdEntityAndStatus(int id_com_entity, char status);

    @Query("SELECT a FROM Acc_com_credit a WHERE a.id_com_entity = ?1 AND a.ac_request = ?2 AND a.status = 'D'")
    public abstract QueryResult<Acc_com_credit> findDraftCreditIdEntityAndByAcRequest(int id_com_entity, int ac_request);

    @Query("SELECT COUNT(a) FROM Acc_com_credit a WHERE a.id_com_entity = ?1 AND a.id_credit <> ?2 AND a.ac_request = ?3 AND a.status = ?4")
    public abstract long countAllOtherCreditWithAcRequestAndStatus(int id_com_entity, int id_credit, int ac_request,
        char draftCreditStatus);

    @Query("SELECT a FROM Acc_com_credit a WHERE a.id_com_entity = ?1 AND a.id_credit = ?2")
    public abstract QueryResult<Acc_com_credit> findAccComCreditByIdEntityAndIdCredit(int id_com_entity, int id_credit);

    @Query("SELECT a.code_acc_prohibitcause FROM Acc_com_credit a WHERE a.id_com_entity = ?1 AND a.id_credit = ?2")
    public abstract QueryResult<String> findCode_acc_prohibitcauseByAccCom(int id_com_entity, int id_credit);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}