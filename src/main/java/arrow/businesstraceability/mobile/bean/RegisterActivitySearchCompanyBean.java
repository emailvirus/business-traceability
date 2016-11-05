package arrow.businesstraceability.mobile.bean;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;

import arrow.businesstraceability.control.service.CompanyManagementService;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.framework.util.collections.CollectionUtils;


/**
 * The Class RegisterActivitySearchCompanyBean.
 *
 * @author Tai
 */

@Named
@ViewScoped
public class RegisterActivitySearchCompanyBean implements Serializable {

    /**
     * Instantiates a new register activity search company bean.
     */
    public RegisterActivitySearchCompanyBean() {}

    /** The company management service. */
    @Inject
    private CompanyManagementService companyManagementService;

    /** The company service. */
    @Inject
    private CompanyManagementService companyService;


    /** The all company. */
    private List<Company_mst> allCompany;

    /**
     * Filter company.
     *
     * @param query the query
     * @return the list
     */
    public List<Company_mst> filterCompany(final String query) {
        if (StringUtils.isEmpty(query)) {
            return this.getAllCompany();
        }

        this.companyName = query;

        this.search();

        return CollectionUtils.filter(this.getResults(), new Predicate() {
            @Override
            public boolean evaluate(final Object arg0) {
                // return true if the item matched with query
                final Company_mst item = (Company_mst) arg0;
                return item.getCom_company_name().contains(query)
                        || String.valueOf(item.getCom_customer_code()).contains(query)
                        || item.getAddresspoint_mst().getAdp_name().contains(query)
                        || item.getCom_company_code().contains(query);
            }
        });
    }

    /**
     * Gets the all company.
     *
     * @return the all company
     */
    public List<Company_mst> getAllCompany() {
        if (this.allCompany == null) {
            this.allCompany = this.companyManagementService.getListCompanies();
        }
        return this.allCompany;
    }

    /** The results. */
    private List<Company_mst> results;

    /**
     * Gets the results.
     *
     * @return the results
     */
    public List<Company_mst> getResults() {
        return this.results;
    }

    /**
     * Sets the results.
     *
     * @param results the new results
     */
    public void setResults(final List<Company_mst> results) {
        this.results = results;
    }

    /**
     * Search.
     */
    public void search() {
        this.results = this.companyService.searchCompany(this.companyName);
    }

    /** The company name. */
    private String companyName;
}
