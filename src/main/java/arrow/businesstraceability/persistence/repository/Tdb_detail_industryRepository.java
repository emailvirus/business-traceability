//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Tdb_detail_industry;
import arrow.businesstraceability.persistence.mapped.Tdb_detail_industry_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Tdb_detail_industryRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Tdb_detail_industry, Tdb_detail_industry_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT t FROM Tdb_detail_industry t WHERE t.code = ?1")
    public abstract QueryResult<Tdb_detail_industry> findIndustryByCode(String code);


    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}