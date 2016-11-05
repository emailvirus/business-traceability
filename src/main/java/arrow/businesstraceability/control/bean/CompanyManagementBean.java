package arrow.businesstraceability.control.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.Predicate;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.DualListModel;

import arrow.businesstraceability.auth.event.DataChange;
import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.control.helper.DataChangeInfo;
import arrow.businesstraceability.control.helper.DataChangeInfo.Action;
import arrow.businesstraceability.control.helper.ScreenContext;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.BasepointInfoService;
import arrow.businesstraceability.control.service.CompanyManagementService;
import arrow.businesstraceability.control.service.IndustryService;
import arrow.businesstraceability.persistence.dto.Company_mst_DTO;
import arrow.businesstraceability.persistence.dto.Daily_report_DTO;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Basepoint_info;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Industry_big_mst;
import arrow.businesstraceability.util.ArrowStringUtils;
import arrow.businesstraceability.util.SelectItemGenerator;
import arrow.businesstraceability.util.SelectItemGenerator.ListType;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.util.FaceletUtils;
import arrow.framework.util.EventUtils;
import arrow.framework.util.StringUtils;
import arrow.framework.util.cdi.Instance;
import arrow.framework.util.collections.CollectionUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyManagementBean.
 */
@Named
@ViewScoped
public class CompanyManagementBean extends AbstractBean {

    /** The company service. */
    @Inject
    private CompanyManagementService companyService;

    /** The industry service. */
    @Inject
    private IndustryService industryService;

    /** The current faces context. */
    @Inject
    private FacesContext currentFacesContext;

    /** The current user. */
    @Inject
    private UserCredential currentUser;

    /** The screen bean. */
    @Inject
    private ScreenBean screenBean;

    /** The current company. */
    private Company_mst_DTO currentCompany;

    /** The selected company. */
    // company which selected on screen.
    private Company_mst selectedCompany;

    /** The list companies. */
    private List<Company_mst> listCompanies;

    /** The list branch. */
    private List<Addresspoint_mst> listBranch;

    /** The branch. */
    private String branch;

    /** The parent screen. */
    private String parentScreen;

    /** The report from register daily report. */
    private Daily_report_DTO reportFromRegisterDailyReport;

    /** The show all company info. */
    private boolean showAllCompanyInfo = false;

    /** The company head office. */
    private List<Addresspoint_mst> companyHeadOffice;

    /** The company type of work. */
    private List<Industry_big_mst> companyTypeOfWork;

    /** The company branch. */
    private DualListModel<Addresspoint_mst> companyBranch;

    /** The list addresses. */
    private List<Addresspoint_mst> listAddresses;

    /** The list business types. */
    private List<Industry_big_mst> listBusinessTypes;

    /**
     * Checks if is enable edit.
     *
     * @return true, if is enable edit
     */
    public boolean isEnableEdit() {
        return this.selectedCompany != null;
    }

    /**
     * Checks if is enable delete.
     *
     * @return true, if is enable delete
     */
    public boolean isEnableDelete() {
        return (this.selectedCompany != null) && this.companyService.isAllowDelete();
    }

    /**
     * Checks if is show all company info.
     *
     * @return true, if is show all company info
     */
    public boolean isShowAllCompanyInfo() {
        return this.showAllCompanyInfo;
    }

    /**
     * Toggle show all company info.
     */
    public void toggleShowAllCompanyInfo() {
        this.showAllCompanyInfo = !this.showAllCompanyInfo;
    }

    /**
     * Inits bean.
     */
    @PostConstruct
    public void init() {
        Map<String, Object> attrs = this.screenBean.getCurrentScreenContext().getObjectAttributes();
        if (attrs != null) {
            this.parentScreen = (String) attrs.get(ScreenContext.SharingDataKey.SCREEN_CODE);
            this.reportFromRegisterDailyReport =
                (Daily_report_DTO) attrs.get(ScreenContext.SharingDataKey.DAILY_REPORT);
            this.addNewCompany();
        }
        this.companyHeadOffice = this.companyService.getAllAddresspoints();
        this.companyTypeOfWork = this.industryService.getAllIndustryBig();
    }

    /**
     * Destroy bean.
     */
    @PreDestroy
    public void destroyBean() {
        super.log.debug("ViewScoped Bean " + this.getClass().getSimpleName() + " was destroyed");
    }

    /**
     * action autoComplete.
     *
     * @author lehoangngochan
     * @param query the query
     * @return list Addresspoint
     */
    public List<Addresspoint_mst> autoCompleteHeadOffice(final String query) {
        return this.companyService.autoCompleteHeadOffice(query, this.companyHeadOffice);
    }

    /**
     * action autoComplete.
     *
     * @author lehoangngochan
     * @param query the query
     * @return list IndustryBigInfo
     */
    public List<Industry_big_mst> autoCompleteTypeOfWork(final String query) {
        return this.companyService.autoCompleteTypeOfWork(query, this.companyTypeOfWork);
    }

    /**
     * save company when click Insert or update.
     *
     * @author lehoangngochan
     */
    public void saveCompany() {
        if (this.currentUser.isHeadQuarterOfficer() && !StringUtils.isEmpty(this.currentCompany.getCom_customer_code())
            && (this.currentCompany.getCom_customer_code().length() > Constants.CUSTOMER_CODE_MAX_LENGTH)) {
            ErrorMessage.comp_004_costomer_code_greater_four_characters().show();
            return;
        }

        ServiceResult<Company_mst> duplicatedCustomerCode =
            this.companyService.findDuplicateCustomerCode(this.currentCompany.getCom_company_code(),
                this.currentCompany.getCom_customer_code());
        if (duplicatedCustomerCode.isSuccessful() && (duplicatedCustomerCode.getData() != null)) {
            ErrorMessage.comp_005_customer_code_duplicated().show();
            return;
        }
        List<Addresspoint_mst> listTargetBranch = this.companyBranch.getTarget();
        ServiceResult<Company_mst> result = this.companyService.saveCompany(this.currentCompany, listTargetBranch);
        result.showMessages(this.currentFacesContext);
        if (result.isNotSuccessful()) {
            return;
        }
        EventUtils.fireAsyncEvent(new DataChangeInfo(result.getData(), Action.UPSERT), DataChange.LITERAL);

        this.log.debug("Out of fire event");
        if (this.isSubscreenOfRegisterActivityScreen()) {
            this.reportFromRegisterDailyReport.setCompany_mst(result.getData());
            this.reportFromRegisterDailyReport.setDai_comppoint_code(result.getData().getAddresspoint_mst()
                .getAdp_code());
            this.closeThisScreenAndShareDataToRegisterActivityScreen(this.reportFromRegisterDailyReport);
        }
        this.cleanStage();
    }

    /**
     * Close this screen and share data to register activity screen.
     *
     * @param reportFromRegisterDailyReport the report from register daily report
     */
    private void closeThisScreenAndShareDataToRegisterActivityScreen(
        final Daily_report_DTO reportFromRegisterDailyReport) {
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(ScreenContext.SharingDataKey.DAILY_REPORT, reportFromRegisterDailyReport);
        attrs.put(ScreenContext.SharingDataKey.SCREEN_CODE, ScreenContext.COMPANY_MANAGEMENT_FORM_CODE);
        this.screenBean.switchScreenWithObjectData(ScreenContext.DAILY_REPORT_REGISTER, true, attrs);
    }

    /**
     * Clean stage.
     */
    public void cleanStage() {
        this.currentCompany = null;
        this.listCompanies = null;
        if (this.selectedCompany != null) {
            this.selectedCompany.setSelected(false);
            this.selectedCompany = null;
        }
    }

    /**
     * call Add new Company screen.
     *
     * @author lehoangngochan
     */
    public void addNewCompany() {
        this.currentCompany = new Company_mst_DTO();
    }

    /**
     * Call Edit Company screen.
     */
    public void editCompany() {
        final ServiceResult<Company_mst_DTO> result = this.companyService.createDTO(this.selectedCompany);
        if (result.isSuccessful()) {
            this.currentCompany = result.getData();
        } else {
            this.currentCompany = null;
        }
    }

    /**
     * Call action delete company.
     *
     * @author lehoangngochan
     */
    public void deleteCompany() {
        if (this.selectedCompany == null) {
            return;
        }
        final ServiceResult<Company_mst> result = this.companyService.deleteCompany(this.selectedCompany);
        if (result.isSuccessful()) {
            EventUtils.fireAsyncEvent(new DataChangeInfo(this.selectedCompany, Action.DELETE), DataChange.LITERAL);
            this.cleanStage();

        }

        result.showMessages(this.currentFacesContext);
    }

    /**
     * Close screen add or edit company.
     *
     * @author lehoangngochan
     */
    public void closeCompany() {
        this.currentCompany = null;

        if (this.selectedCompany != null) {
            this.selectedCompany.setSelected(false);
            this.selectedCompany = null;
        }
        // Close mean users don't change any data, so don't need to clean stage
        if (this.isSubscreenOfRegisterActivityScreen()) {
            this.closeThisScreenAndShareDataToRegisterActivityScreen(this.reportFromRegisterDailyReport);
        }
    }

    /**
     * Checks if is subscreen of register activity screen.
     *
     * @return true, if is subscreen of register activity screen
     */
    private boolean isSubscreenOfRegisterActivityScreen() {
        return ScreenContext.DAILY_REPORT_REGISTER.equals(this.parentScreen);
    }

    /**
     * reset action.
     *
     * @author lehoangngochan
     */
    public void reset() {
        if ((this.currentCompany != null) && (this.currentCompany.getCom_company_code() == null)) {
            // Reset at add new Company screen
            this.currentCompany = new Company_mst_DTO();
        } else {
            // Reset at Edit company Screen
            this.editCompany();
        }
        this.getCompanyBranch();
    }

    /**
     * NOTE: this will be called after value is updated. checkbox handling
     *
     * @author lehoangngochan
     * @param comp the comp
     */
    public void toggleSelection(final Company_mst comp) {
        if (comp.isSelected()) {
            if (this.selectedCompany != null) {
                this.selectedCompany.setSelected(false);
            }
            this.selectedCompany = comp;
        } else {
            this.selectedCompany = null;
        }
    }

    /**
     * Gets the current company.
     *
     * @return the current company
     */
    public Company_mst getCurrentCompany() {
        return this.currentCompany;
    }

    /**
     * Sets the current company.
     *
     * @param currentCompany the new current company
     */
    public void setCurrentCompany(final Company_mst_DTO currentCompany) {
        this.currentCompany = currentCompany;
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
     * Gets the company type of work.
     *
     * @return the company type of work
     */
    public List<Industry_big_mst> getCompanyTypeOfWork() {
        return this.industryService.getAllIndustryBig();
    }

    /**
     * get data for Item branch.
     *
     * @author lehoangngochan
     * @return list data
     */
    public DualListModel<Addresspoint_mst> getCompanyBranch() {
        if (this.currentCompany == null) {
            return null;
        }

        final List<Addresspoint_mst> branchSource = new ArrayList<Addresspoint_mst>();
        final List<Addresspoint_mst> branchTarget = new ArrayList<Addresspoint_mst>();
        branchSource.addAll(this.companyHeadOffice);

        // new company
        if (!this.currentCompany.isPersisted()) {
            return new DualListModel<Addresspoint_mst>(branchSource, branchTarget);
        }

        final List<Basepoint_info> allCompanyBasepoints =
            Instance.get(BasepointInfoService.class).getBasepointInfoByCompanyCode(
                this.currentCompany.getCom_company_code());
        if (CollectionUtils.isNotEmpty(allCompanyBasepoints)) {
            for (final Basepoint_info basePoint : allCompanyBasepoints) {
                branchSource.remove(basePoint.getAddresspoint_mst());
                branchTarget.add(basePoint.getAddresspoint_mst());
            }
        }
        return new DualListModel<>(branchSource, branchTarget);
    }

    /**
     * Sets the company branch.
     *
     * @param companyBranch the new company branch
     */
    public void setCompanyBranch(final DualListModel<Addresspoint_mst> companyBranch) {
        this.companyBranch = companyBranch;
    }

    /**
     * Gets the list addresses.
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
     * Gets the list business types.
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
     * get all companies.
     *
     * @author lehoangngochan
     * @return list companies
     */
    public List<Company_mst> getListCompanies() {
        if (this.listCompanies == null) {
            this.listCompanies = this.companyService.getListCompanies();
        }
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
     * Gets the select item listed codes.
     *
     * @return the select item listed codes
     */
    public List<SelectItem> getSelectItemListedCodes() {
        return SelectItemGenerator.getListSelectItem(ListType.COMP_LISTED_CODE);
    }

    /**
     * Gets the list branch.
     *
     * @return the list branch
     */
    public List<Addresspoint_mst> getListBranch() {
        return this.listBranch;
    }

    /**
     * Sets the list branch.
     *
     * @param listBranch the new list branch
     */
    public void setListBranch(final List<Addresspoint_mst> listBranch) {
        this.listBranch = listBranch;
    }

    /**
     * Gets the branch.
     *
     * @return the branch
     */
    public String getBranch() {
        return this.branch;
    }

    /**
     * Sets the branch.
     *
     * @param branch the new branch
     */
    public void setBranch(final String branch) {
        this.branch = branch;
    }

    /**
     * Gets the listed label.
     *
     * @param valueListed the value listed
     * @return the listed label
     */
    public String getListedLabel(final String valueListed) {
        return this.companyService.getLabelCompanyListed(valueListed);
    }

    /**
     * Gets the checks for company selected.
     *
     * @return the checks for company selected
     */
    public Boolean getHasCompanySelected() {
        return this.selectedCompany != null;
    }

    /**
     * Gets the expansion all.
     *
     * @return the expansion all
     */
    public Boolean getExpansionAll() {
        return this.showAllCompanyInfo;
    }

    /**
     * Sets the expansion all.
     *
     * @param expansionAll the new expansion all
     */
    public void setExpansionAll(final Boolean expansionAll) {
        this.showAllCompanyInfo = expansionAll;
    }

    /**
     * Gets the file.
     *
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public void getFile() throws UnsupportedEncodingException {
        try {
            final String fileExport = this.companyService.doExport();
            final FileInputStream newFile = new FileInputStream(new File(fileExport));
            final HSSFWorkbook workbook = new HSSFWorkbook(newFile);
            FaceletUtils.sendFileToClient(fileExport, workbook);
        } catch (final IOException | URISyntaxException ex) {
            super.log.debug("error while write file:", ex);
        }

    }

    /**
     * Change company name. Furigana inputText will change when company name has text value
     *
     * @exception IOException Signals that an I/O exception has occurred.
     */
    public void changeCompanyName() throws IOException {
        if (this.currentCompany == null) {
            return;
        }
        this.currentCompany.setCom_company_furigana((ArrowStringUtils.translateTextToHiragana(this.currentCompany
            .getCom_company_name())));
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
            public boolean evaluate(final Object arg0) {
                // return true if the item matched with query
                final Addresspoint_mst item = (Addresspoint_mst) arg0;
                return item.getAdp_name().contains(query) || String.valueOf(item.getAdp_code()).contains(query);
            }
        });
    }

    /**
     * Filter head office for search company.
     *
     * @param inputQuery the input query
     * @return the list
     */
    public List<Addresspoint_mst> filterHeadOfficeForSearchCompany(final String inputQuery) {
        return this.companyService.filterHeadOfficeForSearchCompany(inputQuery);
    }

    /**
     * Gets the list head office.
     *
     * @return the list head office
     */
    public List<Addresspoint_mst> getListHeadOffice() {
        if (this.listAddresses == null) {
            this.listAddresses = new ArrayList<>();
            this.listAddresses.addAll(this.companyService.autoCompleteHeadOffice(StringUtils.EMPTY_STRING,
                this.companyHeadOffice));
        }
        return this.listAddresses;
    }

    /**
     * Gets the parent screen.
     *
     * @return the parent screen
     */
    public String getParentScreen() {
        return this.parentScreen;
    }

    /**
     * Sets the parent screen.
     *
     * @param parentScreen the new parent screen
     */
    public void setParentScreen(final String parentScreen) {
        this.parentScreen = parentScreen;
    }

    /**
     * Gets the report from register daily report.
     *
     * @return the report from register daily report
     */
    public Daily_report_DTO getReportFromRegisterDailyReport() {
        return this.reportFromRegisterDailyReport;
    }

    /**
     * Sets the report from register daily report.
     *
     * @param reportFromRegisterDailyReport the new report from register daily report
     */
    public void setReportFromRegisterDailyReport(final Daily_report_DTO reportFromRegisterDailyReport) {
        this.reportFromRegisterDailyReport = reportFromRegisterDailyReport;
    }

    /**
     * Checks if is head office user.
     *
     * @return true, if is head office user
     */
    public boolean isHeadOfficeUser() {
        return StringUtils.equalsIgnoreCase(Constants.HEAD_OFFICE, this.currentUser.getUserEmployee()
            .getAuthority_mst().getAut_name());
    }

    /**
     * Checks if is disabled edit company.
     *
     * @return true, if is disabled edit company
     */
    public boolean isDisabledEditCompany() {
        if (!this.currentCompany.isPersisted() || StringUtils.isEmpty(this.currentCompany.getCom_customer_code())
            || this.isHeadOfficeUser()) {
            return false;
        }
        return true;
    }
}
