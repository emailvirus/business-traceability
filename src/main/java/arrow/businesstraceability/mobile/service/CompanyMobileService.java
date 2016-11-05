package arrow.businesstraceability.mobile.service;

import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.util.StringUtils;


/**
 * The Class CompanyMobileService.
 *
 * @author tainguyen
 */
@Named
@ConversationScoped
public class CompanyMobileService extends AbstractService {

    /**
     * Search company with condition of current user input.
     *
     * @param condition the condition
     * @return the list
     */
    public List<Company_mst> searchCompany(final String condition) {
        ArrowQuery<Company_mst> arrowQuery = this.createQuery(condition);
        return arrowQuery.getResultList();
    }

    /**
     * Count original company when user search company with condition.
     *
     * @param condition the condition
     * @return the int
     */
    public int countResultCompany(final String condition) {
        ArrowQuery<Company_mst> arrowQuery = this.createQuery(condition);
        return (int) (long) arrowQuery.getJPAOriginalCountQuery().getSingleResult();
    }

    /**
     * Creates the query.
     *
     * @param condition the condition
     * @return the arrow query
     */
    private ArrowQuery<Company_mst> createQuery(final String condition) {
        ArrowQuery<Company_mst> arrowQuery = new ArrowQuery<Company_mst>(super.emMain);
        arrowQuery.select("c").from("Company_mst c");
        arrowQuery.innerJoin("c.addresspoint_mst a");
        String whereCondition =
                "c.com_company_name LIKE ? OR c.com_customer_code LIKE ? "
                        + "OR c.com_company_code LIKE ? OR (c.com_point_code = a.adp_code AND a.adp_name LIKE ?)";
        arrowQuery.where(whereCondition, StringUtils.likePattern(condition), StringUtils.likePattern(condition),
                StringUtils.likePattern(condition), StringUtils.likePattern(condition));
        arrowQuery.orderBy("c.com_company_code ASC");
        return arrowQuery;
    }

}
