//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.San_com_mdomain;
import arrow.businesstraceability.persistence.mapped.San_com_mdomain_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class San_com_mdomainRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<San_com_mdomain, San_com_mdomain_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT COALESCE(MAX(m.id_maildomain), 0) FROM San_com_mdomain m")
    public abstract QueryResult<Integer> getMaxSanComMDomainId();

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}