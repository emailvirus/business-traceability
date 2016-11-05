// @formatter:off
package arrow.businesstraceability.persistence.repository;

import org.apache.deltaspike.data.api.EntityManagerConfig;
/* =================== Start import section after the marker line ================== */
/* =================== Please ensure all new imports go into this section ================== */
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Customer_info_view;
import arrow.businesstraceability.persistence.mapped.Customer_info_view_MAPPED;
import arrow.businesstraceability.persistence.repository.resolver.Resolver;

/* =================== End of import section before the marker line =================== */
/* =================== There must be one blank line before the marker line =================== */

@Repository
@EntityManagerConfig(entityManagerResolver = Resolver.class)
public abstract class CustomerInfoStartUpViewRepository extends
    arrow.framework.persistence.Abstract_entityRepositoryWrapper<Customer_info_view, Customer_info_view_MAPPED.Pk> {
    // @formatter:on
    /* =================== Start of manually added code after the marker line ================== */
    /* =================== End of manually added code before the marker line =================== */
    // @formatter:off
}
