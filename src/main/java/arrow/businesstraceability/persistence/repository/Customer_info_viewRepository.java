//@formatter:off
package arrow.businesstraceability.persistence.repository;

/*=================== Start import section after the marker line ==================*/
/*=================== Please ensure all new imports go into this section ==================*/

import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import arrow.businesstraceability.persistence.entity.Customer_info_view;
import arrow.businesstraceability.persistence.mapped.Customer_info_view_MAPPED;

/*=================== End of import section before the marker line ===================*/
/*=================== There must be one blank line before the marker line ===================*/

@Repository
public abstract class Customer_info_viewRepository extends arrow.framework.persistence.Abstract_entityRepositoryWrapper<Customer_info_view, Customer_info_view_MAPPED.Pk> {
    //@formatter:on
    /* =================== Start of manually added code after the marker line ================== */

    @Query("SELECT d FROM Customer_info_view d WHERE d.com_company_code = ?1 AND d.branch_point_code =?2 AND d.dai_compemp_name =?3 AND d.dai_employee_post = ?4")
    public abstract Customer_info_view getCustomerInfoByCompanyCodeAndBranchpointCodeAndPsnAndEmpPost(
        String companyCode, String addressCode, String empName, String empPost);

    /* =================== End of manually added code before the marker line =================== */
    //@formatter:off
}