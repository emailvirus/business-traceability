package arrow.businesstraceability.control.service;

import java.util.List;

import arrow.businesstraceability.base.AbstractService;
import arrow.framework.persistence.locator.EmLocator;


/**
 * The Class EmployeeService.
 */
public class EmployeeService extends AbstractService {

    /**
     * Gets the employee for auto numberring.
     *
     * @param adpCode the adp code
     * @return the employee for auto numberring
     */
    public int getEmployeeForAutoNumberring(final int adpCode) {
        StringBuilder query = new StringBuilder();
        final String query1 =
                "SELECT max(max_emp_code) FROM (SELECT max(emp_code) AS max_emp_code FROM employee_mst em "
                        + "WHERE CAST(em.emp_code as text) LIKE :adpCode "
                        + "AND Length(CAST(em.emp_code AS text)) = 7 GROUP BY emp_adpcode) AS t";
        final String query2 =
                "SELECT max(max_emp_code) FROM (SELECT max(emp_code) AS max_emp_code FROM employee_mst em "
                        + "WHERE CAST(em.emp_code as text) LIKE :adpCode "
                        + "AND Length(CAST(em.emp_code AS text)) = 8 GROUP BY emp_adpcode) AS t";
        query = (adpCode < 10) ? query.append(query1) : query.append(query2);
        final List<?> result = EmLocator.getEm().createNativeQuery(query.toString())
                .setParameter("adpCode", adpCode + "%").getResultList();
        if ((result.size() == 0) || (result.get(0) == null)) {
            return 0;
        }
        return Integer.parseInt(result.get(0).toString());
    }


}
