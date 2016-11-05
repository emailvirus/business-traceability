//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import java.util.Date;

import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.San_com_info;
import arrow.businesstraceability.persistence.mapped.San_com_info_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class San_com_infoRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<San_com_info, San_com_info_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT s FROM San_com_info s WHERE s.id_company = ?1")
    public abstract QueryResult<San_com_info> getSanComInfoWithIdCompany(int idCompany);

    @Modifying
    @Query(value = "UPDATE San_com_info SET ts_export = ?1 WHERE id_san_company NOT LIKE '#%'")
    public abstract void updateTimeExport(Date timeExport);

    @Query("SELECT s FROM San_com_info s WHERE s.id_san_company = ?1")
    public abstract QueryResult<San_com_info> getSanComInfoWithIdSanCompany(String idSanCompany);

    @Query("SELECT s FROM San_com_info s WHERE s.id_san_company = ?1 AND s.id_company IS NOT NULL")
    public abstract QueryResult<San_com_info> getSanComInfoWithIdSanCompanyAndIdMappingNotNull(String idSanCompany);

    @Query("SELECT s.id_san_company FROM San_com_info s WHERE s.id_company = ?1")
    public abstract QueryResult<String> getIdSanCompanyInSanComInfoWithIdCompany(int idCompany);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}