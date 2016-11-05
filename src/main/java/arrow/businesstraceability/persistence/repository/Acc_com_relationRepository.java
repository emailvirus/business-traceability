//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.List;

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Acc_com_relation;
import arrow.businesstraceability.persistence.mapped.Acc_com_relation_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Acc_com_relationRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Acc_com_relation, Acc_com_relation_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */


    @Query(value = "SELECT a FROM Acc_com_relation a WHERE a.id_com_relation = ?1")
    public abstract Acc_com_relation getAccComRelationByIdCompany(int id);

    @Query(value = "SELECT a FROM Acc_com_relation a WHERE a.id_com_entity = ?1")
    public abstract List<Acc_com_relation> getListAccComRelationByIdComEntity(int id);

    @Modifying
    @Query(value = "DELETE FROM Acc_com_relation a WHERE a.id_com_relation = ?1")
    public abstract void removeAccComRelationByIdComRelation(int id);

    @Query(value = "SELECT MAX(a.id_com_relation) FROM Acc_com_relation a")
    public abstract QueryResult<Integer> getMaxIdComRelation();

    @Query(value = "SELECT a FROM Acc_com_relation a WHERE a.id_com_entity = ?1 AND a.id_credit = ?2")
    public abstract List<Acc_com_relation> getListAccComRelationByIdComEntityAndIdCredit(int idComEntity, int idCredit);

    @Query("SELECT a FROM Acc_com_relation a WHERE a.id_credit = ?1")
    public abstract QueryResult<Acc_com_relation> getAllRelationByIdCredit(int idCredit);


    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}