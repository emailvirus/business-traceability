//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Monthly_report_history;
import arrow.businesstraceability.persistence.mapped.Monthly_report_history_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Monthly_report_historyRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Monthly_report_history, Monthly_report_history_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT m FROM Monthly_report_history m WHERE m.shain_kodo = ?1 " + " AND m.repoto_no_getsudo= ?2 "
            + " AND m.repoto_no_nendo = ?3 " + " ORDER BY m.sousa_jiten DESC ")
    public abstract QueryResult<Monthly_report_history> findMonthlyReportHistoryByEmployeeAndStartDate(int empCode,
            int month, int Year);

    @Query("SELECT m.comment FROM Monthly_report_history m " + " WHERE m.shain_kodo= ?1 "
            + " AND m.repoto_no_getsudo = ?2 " + " AND m.repoto_no_nendo = ?3 " + " AND (m.sousa='RE' OR m.sousa='RO') "
            + " ORDER BY m.sousa_jiten DESC ")
    public abstract QueryResult<String> findLastCommentMonthlyReportHistoryByEmployeeAndStartDate(final int empCode,
            final int month, final int Year);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}