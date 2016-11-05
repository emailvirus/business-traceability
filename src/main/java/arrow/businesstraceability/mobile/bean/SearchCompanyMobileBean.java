package arrow.businesstraceability.mobile.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import arrow.businesstraceability.constant.HistoryDailyReportConstants;
import arrow.businesstraceability.control.bean.MobileScreenFlowBean;
import arrow.businesstraceability.mobile.service.CompanyMobileService;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.framework.bean.AbstractBean;


/**
 * The Class SearchCompanyMobileBean.
 *
 * @author tainguyen
 */
@Named
@ViewScoped
public class SearchCompanyMobileBean extends AbstractBean {

    /** The service. */
    @Inject
    private CompanyMobileService service;

    /** The register activity mobile bean. */
    @Inject
    private RegisterActivityMobileBean registerActivityMobileBean;

    /** The mobile screen flow bean. */
    @Inject
    private MobileScreenFlowBean mobileScreenFlowBean;

    /** Query current user input. */
    private String conditionSearch;

    /** List company return after search. */
    private List<Company_mst> listCompanies;

    /** Company current user selected on table. */
    private Company_mst selectedCompany;

    /** Count total result after search. */
    private long totalResult;


    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        this.listCompanies = null;
        this.selectedCompany = null;
        this.totalResult = 0;
        this.conditionSearch = null;
    }

    /**
     * Search company with conditon of current user input.
     */
    public void searchCompany() {
        this.listCompanies = this.service.searchCompany(this.conditionSearch);
        this.totalResult = this.service.countResultCompany(this.conditionSearch);

        if (HistoryDailyReportConstants.MAX_RECORDS <= this.totalResult) {
            RequestContext.getCurrentInstance().execute("PF('warningDialog_mobile').show()");
        }
    }

    /**
     * Disable search button.
     *
     * @return true, if successful
     */
    public boolean disableSearchButton() {
        return StringUtils.isEmpty(this.conditionSearch);
    }

    /**
     * Select company and back to screen edit daily report.
     *
     * @return the string
     */
    public String selectCompanyAndBackToParentScreen() {
        this.registerActivityMobileBean.getCurrentReport().setCompany_mst(this.selectedCompany);
        return this.mobileScreenFlowBean.goBack();
    }

    /**
     * Event selected a row on table.
     *
     * @param event the event
     */
    public void onRowSelect(final SelectEvent event) {
        this.selectedCompany = (Company_mst) event.getObject();
    }

    /**
     * Event unselected a row on table.
     *
     * @param event the event
     */
    public void onRowUnselect(final UnselectEvent event) {
        this.selectedCompany = null;
    }

    /**
     * Gets the condition search.
     *
     * @return the condition search
     */
    public String getConditionSearch() {
        return this.conditionSearch;
    }

    /**
     * Sets the condition search.
     *
     * @param conditionSearch the new condition search
     */
    public void setConditionSearch(final String conditionSearch) {
        this.conditionSearch = conditionSearch;
    }

    /**
     * Gets the list companies.
     *
     * @return the list companies
     */
    public List<Company_mst> getListCompanies() {
        return this.listCompanies;
    }

    /**
     * Sets the list companies.
     *
     * @param listCompanies the new list companies
     */
    public void setListCompanies(final List<Company_mst> listCompanies) {
        this.listCompanies = listCompanies;
    }

    /**
     * Gets the selected company.
     *
     * @return the selected company
     */
    public Company_mst getSelectedCompany() {
        return this.selectedCompany;
    }

    /**
     * Sets the selected company.
     *
     * @param selectedCompany the new selected company
     */
    public void setSelectedCompany(final Company_mst selectedCompany) {
        this.selectedCompany = selectedCompany;
    }

    /**
     * Gets the total result.
     *
     * @return the total result
     */
    public long getTotalResult() {
        return this.totalResult;
    }

    /**
     * Sets the total result.
     *
     * @param totalResult the new total result
     */
    public void setTotalResult(final long totalResult) {
        this.totalResult = totalResult;
    }

}
