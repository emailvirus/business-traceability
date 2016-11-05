// @formatter:off
package arrow.businesstraceability.persistence.repository;

/* =================== Start import section after the marker line ================== */
/* =================== Please ensure all new imports go into this section ================== */

import org.apache.deltaspike.data.api.EntityManagerConfig;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.mapped.Company_mst_MAPPED;
import arrow.businesstraceability.persistence.repository.resolver.Resolver;

/* =================== End of import section before the marker line =================== */
/* =================== There must be one blank line before the marker line =================== */

@Repository
@EntityManagerConfig(entityManagerResolver = Resolver.class)
public abstract class CompanyStartUpRepository extends
    arrow.framework.persistence.Abstract_entityRepositoryWrapper<Company_mst, Company_mst_MAPPED.Pk> {
    // @formatter:on
    /* =================== Start of manually added code after the marker line ================== *

    /* =================== End of manually added code before the marker line =================== */
    // @formatter:off
}
