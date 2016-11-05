//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Acc_com_finance;
import arrow.businesstraceability.persistence.mapped.Acc_com_finance_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Acc_com_financeRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Acc_com_finance, Acc_com_finance_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT cf FROM Acc_com_finance cf WHERE cf.id_finance = ?1")
    public abstract QueryResult<Acc_com_finance> findFinanceById(int idFinance);

    @Query("SELECT COALESCE(MAX(cf.id_finance), 0) FROM Acc_com_finance cf")
    public abstract QueryResult<Integer> getMaxAccComFinanceId();

    @Query("SELECT cf FROM Acc_com_finance cf WHERE cf.id_com_entity = ?1")
    public abstract QueryResult<Acc_com_finance> getAllFinanceByIdComEntity(final int idComEntity);

    @Query("SELECT cf FROM Acc_com_finance cf WHERE cf.id_credit = ?1")
    public abstract QueryResult<Acc_com_finance> getAllFinanceByIdCredit(final int idCredit);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}