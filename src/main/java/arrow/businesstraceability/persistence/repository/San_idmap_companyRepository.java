//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.San_idmap_company;
import arrow.businesstraceability.persistence.mapped.San_idmap_company_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class San_idmap_companyRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<San_idmap_company, San_idmap_company_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT c.id_san_company FROM San_idmap_company c WHERE c.id_company = ?1")
    public abstract String findIdSanCompanyByIdCompany(int idCompany);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}