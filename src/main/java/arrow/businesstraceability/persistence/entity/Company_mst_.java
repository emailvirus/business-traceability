//@formatter:off
package arrow.businesstraceability.persistence.entity;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import arrow.businesstraceability.persistence.mapped.Company_mst_MAPPED;
import arrow.businesstraceability.persistence.mapped.Company_mst_MAPPED_;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@StaticMetamodel(Company_mst.class)
public class Company_mst_ extends Company_mst_MAPPED_ {

    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    public static volatile ListAttribute<Company_mst_MAPPED, arrow.businesstraceability.persistence.entity.Basepoint_info> basepoint_infos;

    public static volatile ListAttribute<Company_mst_MAPPED, arrow.businesstraceability.persistence.entity.Daily_report> daily_reports;

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}