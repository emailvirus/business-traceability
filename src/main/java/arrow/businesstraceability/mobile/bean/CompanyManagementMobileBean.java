package arrow.businesstraceability.mobile.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.Predicate;
import org.omnifaces.cdi.ViewScoped;

import arrow.businesstraceability.constant.MobilePageId;
import arrow.businesstraceability.control.bean.MobileScreenFlowBean;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.CompanyManagementService;
import arrow.businesstraceability.control.service.IndustryService;
import arrow.businesstraceability.persistence.dto.Company_mst_DTO;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Industry_big_mst;
import arrow.businesstraceability.util.ArrowStringUtils;
import arrow.businesstraceability.util.SelectItemGenerator;
import arrow.businesstraceability.util.SelectItemGenerator.ListType;
import arrow.framework.bean.AbstractBean;
import arrow.framework.util.StringUtils;
import arrow.framework.util.collections.CollectionUtils;


/**
 * The Class CompanyManagementMobileBean.
 */
@Named
@ViewScoped
public class CompanyManagementMobileBean extends AbstractBean {

    /** The company service. */
    @Inject
    private CompanyManagementService companyService;

    /** The industry service. */
    @Inject
    private IndustryService industryService;

    /** The mobile screen flow bean. */
    @Inject
    MobileScreenFlowBean mobileScreenFlowBean;

    /** The register activity mobile bean. */
    @Inject
    RegisterActivityMobileBean registerActivityMobileBean;

    /** The company head office. */
    private List<Addresspoint_mst> companyHeadOffice;

    /** The company type of work. */
    private List<Industry_big_mst> companyTypeOfWork;

    /** The list addresses. */
    private List<Addresspoint_mst> listAddresses;

    /** The list business types. */
    private List<Industry_big_mst> listBusinessTypes;

    /** The company management. */
    private Company_mst_DTO companyManagement;

    /**
     * Gets the company management.
     *
     * @return the company management
     */
    public Company_mst_DTO getCompanyManagement() {
        return this.companyManagement;
    }

    /**
     * Sets the company management.
     *
     * @param companyManagement the new company management
     */
    public void setCompanyManagement(final Company_mst_DTO companyManagement) {
        this.companyManagement = companyManagement;
    }

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        this.companyHeadOffice = this.companyService.getAllAddresspoints();
        this.companyTypeOfWork = this.industryService.getAllIndustryBig();
    }

    /**
     * Get list addresses.
     *
     * @return the list addresses
     */
    public List<Addresspoint_mst> getListAddresses() {
        if (this.listAddresses == null) {
            this.listAddresses = this.companyService.getAllAddresspoints();
        }
        return this.listAddresses;
    }

    /**
     * Get list business types.
     *
     * @return the list business types
     */
    public List<Industry_big_mst> getListBusinessTypes() {
        if (this.listBusinessTypes == null) {
            this.listBusinessTypes = this.industryService.getAllIndustryBig();
        }
        return this.listBusinessTypes;
    }

    /**
     * Gets the company listed codes.
     *
     * @return the company listed codes
     */
    public List<SelectItem> getCompanyListedCodes() {
        return SelectItemGenerator.getListSelectItem(ListType.COMP_LISTED_CODE);
    }

    /**
     * Gets the company limited codes.
     *
     * @return the company limited codes
     */
    public List<SelectItem> getCompanyLimitedCodes() {
        return SelectItemGenerator.getListSelectItem(ListType.COMP_LIMITED_CODE);
    }

    /**
     * Gets the types of work.
     *
     * @return the types of work
     */
    public List<SelectItem> getTypesOfWork() {
        List<SelectItem> items = new ArrayList<>();
        for (Industry_big_mst industry : this.companyTypeOfWork) {
            items.add(new SelectItem(industry.getBig_code(), industry.getBig_name()));
        }

        return items;
    }

    /**
     * Filter head office.
     *
     * @param query the query
     * @return the list
     */
    public List<Addresspoint_mst> filterHeadOffice(final String query) {
        if (StringUtils.isEmpty(query)) {
            return this.getListHeadOffice();
        }

        return CollectionUtils.filter(this.getListHeadOffice(), new Predicate() {

            @Override
            public boolean evaluate(final Object filterName) {
                // return true if the item matched with query
                final Addresspoint_mst item = (Addresspoint_mst) filterName;
                return item.getAdp_name().contains(query) || String.valueOf(item.getAdp_code()).contains(query);
            }
        });
    }

    /**
     * Gets the list head office.
     *
     * @return the list head office
     *
     */
    public List<Addresspoint_mst> getListHeadOffice() {
        if (this.listAddresses == null) {
            this.listAddresses = new ArrayList<>();
            this.listAddresses.addAll(this.companyService.autoCompleteHeadOffice("",
                this.companyService.getAllAddresspoints()));
        }
        return this.listAddresses;
    }

    /**
     * Auto complete head office.
     *
     * @param query the query
     * @return the list
     */
    public List<Addresspoint_mst> autoCompleteHeadOffice(final String query) {
        return this.companyService.autoCompleteHeadOffice(query, this.companyHeadOffice);
    }


    /**
     * Clean stage.
     */
    public void cleanStage() {
        this.companyManagement = null;
    }

    /**
     * Gets the company type of work.
     *
     * @return the company type of work
     */
    public List<Industry_big_mst> getCompanyTypeOfWork() {
        return this.industryService.getAllIndustryBig();
    }

    /**
     * Adds the new company screen. Init Company Management DTO Init Company Management Bean
     *
     * @return the string
     */
    public String addNewCompanyMobileScreen() {
        this.companyManagement = new Company_mst_DTO();
        return this.mobileScreenFlowBean.gotoPage(MobilePageId.ADD_COMPANY);
    }

    /**
     * Save company. listBranch. This only use for web, for mobile it's null parameter.
     *
     * @return the string
     */
    public String saveCompany() {
        List<Addresspoint_mst> listBranch = null;
        ServiceResult<Company_mst> result = this.companyService.saveCompany(this.companyManagement, listBranch);
        // Set Company_mst value in RegisterActivityMobileBean after save successful
        if (result.isSuccessful()) {
            this.registerActivityMobileBean.getCurrentReport().setCompany_mst(result.getData());
            this.registerActivityMobileBean.getCurrentReport().setDai_comppoint_code(
                result.getData().getCom_point_code());
            this.registerActivityMobileBean.setCompanyName(result.getData().getCom_company_name());
            return this.mobileScreenFlowBean.goBack();
        }
        return StringUtils.EMPTY_STRING;
    }

    /**
     * Change company name. Furigana textinput will change when company name has text
     *
     * @return No return value.
     * @exception IOException Signals that an I/O exception has occurred.
     */
    public void changeCompanyName() throws IOException {
        this.companyManagement.setCom_company_furigana((ArrowStringUtils.translateTextToHiragana(this.companyManagement
            .getCom_company_name())));
    }
}
