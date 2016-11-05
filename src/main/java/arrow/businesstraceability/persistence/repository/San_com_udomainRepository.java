//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.San_com_udomain;
import arrow.businesstraceability.persistence.mapped.San_com_udomain_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class San_com_udomainRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<San_com_udomain, San_com_udomain_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT COALESCE(MAX(u.id_urldomain), 0) FROM San_com_udomain u")
    public abstract QueryResult<Integer> getMaxSanComUDomainId();


    @Query(value = "SELECT COUNT(id_company) FROM San_com_udomain WHERE id_company = ?1")
    public abstract int countNumberUrlOfCompanyByIdCompany(Integer id);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}