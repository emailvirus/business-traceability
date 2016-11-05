//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.Date;

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Acc_com_entity;
import arrow.businesstraceability.persistence.mapped.Acc_com_entity_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Acc_com_entityRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Acc_com_entity, Acc_com_entity_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT COUNT(s.id_com_entity) FROM Acc_com_entity s WHERE s.indx_customer = ?1")
    public abstract QueryResult<Long> countEntityByCustomerCode(String indx_customer);

    @Query("SELECT MAX(a.id_com_entity) FROM Acc_com_entity a")
    public abstract QueryResult<Integer> getMaxIdOfEntity();

    @Modifying
    @Query(value = "UPDATE Acc_com_entity SET ts_export = ?1")
    public abstract void updateTimeExport(Date timeExport);

    @Query("SELECT a FROM Acc_com_entity a WHERE a.id_com_entity = ?1")
    public abstract QueryResult<Acc_com_entity> findByIdComEntity(int i);

    @Query("SELECT c FROM Acc_com_entity c WHERE c.id_int_san_company = ?1")
    public abstract QueryResult<Acc_com_entity> getComEntityByIdIntSanCom(int idSanCom);

    @Query("SELECT c FROM Acc_com_entity c WHERE c.indx_customer = ?1")
    public abstract QueryResult<Acc_com_entity> getComEntityByCustomerCode(String customerCode);

    @Query("SELECT c FROM Acc_com_entity c WHERE c.indx_customer = ?1 AND c.id_int_san_company = ?2")
    public abstract QueryResult<Acc_com_entity> getComEntityByCustomerCodeAndIdSanCompanyAndIdSanCom(
        String customerCode, int idSanCompany);

    @Query("SELECT c FROM Acc_com_entity c WHERE c.indx_customer = ?1 AND c.id_int_san_company is null")
    public abstract QueryResult<Acc_com_entity> getComEntityByCustomerCodeAndIdSanCompanyAndNotMappingSansan(
        String customerCode);

    @Query("SELECT c FROM Acc_com_entity c WHERE c.id_int_san_company = ?1 AND c.name_company LIKE ?2")
    public abstract QueryResult<Acc_com_entity> getComEntityByIdSanCompanyAndName(int idSanCompany, String companyName);

    @Query("SELECT c FROM Acc_com_entity c WHERE c.id_int_san_company is NULL AND c.name_company LIKE ?1")
    public abstract QueryResult<Acc_com_entity> getComEntityByNameNotMappingSansan(String companyName);

    @Query("SELECT c FROM Acc_com_entity c WHERE c.id_int_san_company is NULL")
    public abstract QueryResult<Acc_com_entity> getComEntityNotMappingSansan();

    @Query("SELECT c FROM Acc_com_entity c WHERE c.id_int_san_company is NOT NULL AND c.name_company LIKE ?1")
    public abstract QueryResult<Acc_com_entity> getComEntityByCompanyNameAndIdMappingNotNull(String companyName);


    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}