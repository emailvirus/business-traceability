//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Acc_com_industry;
import arrow.businesstraceability.persistence.mapped.Acc_com_industry_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Acc_com_industryRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Acc_com_industry, Acc_com_industry_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT MAX(a.id_industry) FROM Acc_com_industry a")
    public abstract QueryResult<Integer> getMaxIdOfIndustry();

    @Query("SELECT a FROM Acc_com_industry a WHERE a.id_industry = ?1")
    public abstract Acc_com_industry findByIdIndustry(int i);

    @Query("SELECT a FROM Acc_com_industry a WHERE a.id_com_entity = ?1 AND a.id_credit = ?2")
    public abstract QueryResult<Acc_com_industry> findAccComIndustyByEntityIdAndCreditId(int id_com_entity,
        int id_credit);

    @Query("SELECT a FROM Acc_com_industry a WHERE a.id_credit = ?1")
    public abstract QueryResult<Acc_com_industry> findIndustryByIdCredit(int idCredit);


    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}