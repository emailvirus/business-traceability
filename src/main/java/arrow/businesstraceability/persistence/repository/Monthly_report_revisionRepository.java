//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Monthly_report_revision;
import arrow.businesstraceability.persistence.mapped.Monthly_report_revision_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Monthly_report_revisionRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Monthly_report_revision, Monthly_report_revision_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT m FROM Monthly_report_revision m where m.repoto_no_ribijon_no_sakujo_furagu = false AND m.shain_kodo = ?1 AND m.repoto_no_nendo = ?2 AND m.repoto_no_getsudo = ?3 ORDER BY m.bajon_bangou DESC")
    public abstract QueryResult<Monthly_report_revision> getActiveMonthlyReportByEmpCodeAndTimeOfReportOrderByVersion(
            int employeeCode, int year, int month);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}