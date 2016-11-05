package arrow.businesstraceability.control.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import arrow.businesstraceability.cache.entity.FindDataNameCardEntity;
import arrow.businesstraceability.config.SysConfig;
import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.control.helper.ScreenContext;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.InputAccountingDataService;
import arrow.businesstraceability.persistence.entity.Acc_com_credit;
import arrow.businesstraceability.persistence.entity.Acc_com_entity;
import arrow.businesstraceability.persistence.entity.Acc_com_industry;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.San_com_info;
import arrow.businesstraceability.persistence.entity.Tdb_detail_industry;
import arrow.businesstraceability.util.upload.UploadHelper;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.util.FileUtils;
import arrow.framework.util.MessageUtils;
import arrow.framework.util.collections.CollectionUtils;

/**
 * The Class InputAccountingDataBean.
 */
@Named
@ViewScoped
public class InputAccountingDataBean extends AbstractBean {

    /** The accounting data service. */
    @Inject
    private InputAccountingDataService accountingDataService;

    /** The user credential. */
    @Inject
    private UserCredential userCredential;

    /** The input financial bean. */
    @Inject
    private InputFinancialDataBean inputFinancialBean;

    /** The input customer data bean. */
    @Inject
    private InputCustomerDataBean inputCustomerDataBean;

    /** The acc com entity. */
    private Acc_com_entity accComEntity;

    /** The acc com credit. */
    private Acc_com_credit accComCredit;

    /**
     * The freeze reason. <br>
     * Only clean this field after save Acc data.
     * */
    private String freezeReason;

    /** The view mode. */
    private boolean viewMode;

    /** The entity view mode. */
    private boolean entityViewMode;

    /** The temporary save. */
    private static int TEMPORARY_SAVE = 1;

    /** The approve. */
    private static int APPROVE = 2;

    /** The show detail report. */
    private boolean showDetailReport;

    /**
     * Gets the acc com entity.
     *
     * @return the acc com entity
     */
    public Acc_com_entity getAccComEntity() {
        return this.accComEntity;
    }

    /**
     * Sets the acc com entity.
     *
     * @param accComEntity the new acc com entity
     */
    public void setAccComEntity(final Acc_com_entity accComEntity) {
        this.accComEntity = accComEntity;
    }

    /**
     * Gets the acc com credit.
     *
     * @return the acc com credit
     */
    public Acc_com_credit getAccComCredit() {
        return this.accComCredit;
    }

    /**
     * Sets the acc com credit.
     *
     * @param accComCredit the new acc com credit
     */
    public void setAccComCredit(final Acc_com_credit accComCredit) {
        this.accComCredit = accComCredit;
    }

    /**
     * Gets the acc com industry.
     *
     * @return the acc com industry
     */
    public Acc_com_industry getAccComIndustry() {
        return this.accComIndustry;
    }

    /**
     * Sets the acc com industry.
     *
     * @param accComIndustry the new acc com industry
     */
    public void setAccComIndustry(final Acc_com_industry accComIndustry) {
        this.accComIndustry = accComIndustry;
    }

    /** The acc com industry. */
    private Acc_com_industry accComIndustry;

    /** The storage list card from proc2. */
    private List<FindDataNameCardEntity> storageListCardFromProc2;

    /** The storage key word id name card. */
    private String storageKeyWordIdNameCard;

    /** The storage key word company name. */
    private String storageKeyWordCompanyName;

    /** The storage key work customer code. */
    private String storageKeyWorkCustomerCode;

    /** The active index find accounting data. */
    private int activeIndexFindAccountingData;

    /**
     * Inits the.
     */
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        Map<String, Object> attrs = this.screenBean.getCurrentScreenContext().getObjectAttributes();

        // Get selected id_san_company from proc 2
        final int idIntSanCom = (int) attrs.get(ScreenContext.SharingDataKey.ID_INT_SAN_COMPANY);

        // Get list data use for close feature
        this.storageListCardFromProc2 =
            (List<FindDataNameCardEntity>) attrs.get(ScreenContext.SharingDataKey.LIST_COMPANY_PROC2);
        this.storageKeyWordIdNameCard = (String) attrs.get(ScreenContext.SharingDataKey.KEY_WORD_ID_NAME_CARD);
        this.storageKeyWordCompanyName = (String) attrs.get(ScreenContext.SharingDataKey.KEY_WORD_COMPANY_NAME);
        this.storageKeyWorkCustomerCode = (String) attrs.get(ScreenContext.SharingDataKey.KEY_WORD_CUSTOMER_CODE);
        this.activeIndexFindAccountingData =
            (int) attrs.get(ScreenContext.SharingDataKey.ACTIVE_INDEX_FIND_ACCOUNTING_DATA);

        this.accComEntity = this.accountingDataService.getAccComEntity(idIntSanCom);

        if (null != this.accComEntity) {
            this.entityViewMode = this.accountingDataService.getEntityViewMode(this.accComEntity.getId_com_entity());
            this.loadCreditHistory();
            if (CollectionUtils.isNotEmpty(this.listCredits)) {
                for (Acc_com_credit credit : this.listCredits) {
                    if ((this.accComEntity.getId_credit() != null)
                        && (credit.getId_credit() == this.accComEntity.getId_credit())) {
                        this.freezeReason = credit.getCode_acc_prohibitcause();
                        break;
                    }
                }
            }
        }

        if (this.accComEntity == null) {
            San_com_info sanComInfo = this.accountingDataService.getSanComInfo(idIntSanCom);
            this.accComEntity = new Acc_com_entity();
            this.accComEntity.setSan_com_info(sanComInfo);
            this.accComEntity.setName_company(sanComInfo.getName_company());
            this.accComEntity.setName_com_kana(sanComInfo.getName_com_kana());
            this.entityViewMode = false;
        }

    }

    /**
     * Creates the credit.
     */
    public void createCredit() {
        List<Message> errors = this.accountingDataService.validateAccComEntity(this.accComEntity);
        if (CollectionUtils.isNotEmpty(errors)) {
            MessageUtils.showMessages(errors);
            return;
        }

        // Show first tab in new screen
        this.tabView = 0;

        // Open next step
        this.accComCredit = new Acc_com_credit();
        this.accComIndustry = new Acc_com_industry();

        // Set default registration facility and applicant is current user and branch of user
        this.accComCredit.setAddresspoint_mst(this.userCredential.getUserEmployee().getAddresspoint_mst());
        this.accComCredit.setEmployee_mst(this.userCredential.getUserEmployee());
        // Reload list for user select other employee on this current branch
        this.reloadListApplicant();

        if (this.userCredential.isManagerHQ()) {
            this.accComCredit.setMiddleApprovalEmployee(this.userCredential.getUserEmployee());
            this.accComCredit.setFinalApprovalEmployee(this.userCredential.getUserEmployee());
        }

        this.viewMode = false;
        this.showDownload = false;

        // Share data on screen context with sub-bean (inputFinancialBean, inputCustomerDataBean)
        Map<String, Object> mapAttrs = new HashMap<>();
        mapAttrs.put(ScreenContext.SharingDataKey.VIEW_MODE, this.viewMode);
        mapAttrs.put(ScreenContext.SharingDataKey.ACC_SAN_COMPANY, this.accComEntity);
        mapAttrs.put(ScreenContext.SharingDataKey.CREDIT_ENTITY, this.accComCredit);
        this.screenBean.getCurrentScreenContext().setObjectAttributes(mapAttrs);

        this.inputFinancialBean.initDataWhenChangeScreen(this.accComEntity, this.accComCredit, this.viewMode);
        this.inputCustomerDataBean.init();
        this.showDetailReport = true;
    }

    /**
     * Update credit.
     */
    public void updateCredit() {
        this.accComIndustry =
            this.accountingDataService.getAccComIndustryByEntityAndCredit(this.accComEntity.getId_com_entity(),
                this.accComCredit.getId_credit());
        if (this.accComIndustry != null) {
            this.mainIndustry = this.accountingDataService.findIndustry(this.accComIndustry.getCode_industry_main());
            this.industry = this.accountingDataService.findIndustry(this.accComIndustry.getCode_industry_sub());
        } else {
            this.accComIndustry = new Acc_com_industry();
        }

        this.viewMode = Constants.SAVED_CREDIT_STATUS == this.accComCredit.getStatus();
        this.showDetailReport = true;

        if (StringUtils.isNotEmpty(this.accComCredit.getPath_report())) {
            this.showDownload = true;
            this.getDownloadFile(this.accComEntity, this.accComCredit);
        }

        Map<String, Object> mapAttrs = new HashMap<>();
        mapAttrs.put(ScreenContext.SharingDataKey.VIEW_MODE, this.viewMode);
        mapAttrs.put(ScreenContext.SharingDataKey.ACC_SAN_COMPANY, this.accComEntity);
        mapAttrs.put(ScreenContext.SharingDataKey.CREDIT_ENTITY, this.accComCredit);
        this.screenBean.getCurrentScreenContext().setObjectAttributes(mapAttrs);

        this.inputFinancialBean.initDataWhenChangeScreen(this.accComEntity, this.accComCredit, this.viewMode);
        this.inputCustomerDataBean.init();
    }

    /** The uploaded file. */
    private UploadedFile uploadedFile;

    /**
     * File user manual upload listener.
     *
     * @param event the event
     */
    public void fileUploadListener(final FileUploadEvent event) {
        this.uploadedFile = event.getFile();
        this.accComCredit.setPath_report(event.getFile().getFileName());
        ServiceResult<Object> result =
            this.accountingDataService.saveReportFileToTempPath(event.getFile(), SysConfig.getUploadTempDir());
        result.showMessages();
    }

    /**
     * Gets the uploaded file.
     *
     * @return the uploaded file
     */
    public UploadedFile getUploadedFile() {
        return this.uploadedFile;
    }

    /**
     * Sets the uploaded file.
     *
     * @param uploadedFile the new uploaded file
     */
    public void setUploadedFile(final UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    /**
     * Gets the freeze reason.
     *
     * @return the freeze reason
     */
    public String getFreezeReason() {
        return this.freezeReason;
    }

    /**
     * Sets the freeze reason.
     *
     * @param freezeReason the new freeze reason
     */
    public void setFreezeReason(final String freezeReason) {
        this.freezeReason = freezeReason;
    }

    /**
     * Change code state.
     */
    public void changeCodeState() {
        if (!this.accComEntity.isTemporalFreeze()) {
            this.freezeReason = null;
        }
    }

    /**
     * Cancel.
     */
    public void cancel() {
        this.resetDataCredit();
    }

    /**
     * Reset data credit.
     */
    private void resetDataCredit() {
        if ((this.accComCredit != null) && this.accComCredit.isPersisted()) {
            this.loadCreditHistory();
        }
        this.accComCredit = null;
        this.accComIndustry = null;
        this.industry = null;
        this.mainIndustry = null;
        this.uploadedFile = null;
        this.tabView = 0;
        this.showDetailReport = false;
        this.showDownload = false;
        this.inputFinancialBean.resetData();
        this.inputCustomerDataBean.resetAccComRelation();
    }

    /**
     * Save temporary credit.
     */
    public void saveTemporaryCredit() {
        // Validate all required fields before save temporary
        List<Message> errors = this.accountingDataService.validateAccComCreditBeforeSaveTemporary(this.accComCredit);
        if (CollectionUtils.isNotEmpty(errors)) {
            MessageUtils.showMessages(errors);
            return;
        }

        // Validate info of credit with ac_request
        // In one time, only persisted in DB a report with a user
        if (!this.accountingDataService.canSaveDraftCredit(this.accComEntity, this.accComCredit.getId_credit(),
            this.accComCredit.getAc_request())) {
            ErrorMessage.proc3_019_cannot_create_new_credit_because_selected_request_user_account().show();
            return;
        }

        // Validate info of credit before save
        // In one time, only persisted a credit(report) with source info, requester and date survey
        if (this.accComEntity.isPersisted()
            && this.accountingDataService.hasAccComCreditSameInfoSurvey(this.accComEntity.getId_com_entity(),
                this.accComCredit.getId_credit(), this.accComCredit.getAc_request(),
                this.accComCredit.getCode_acc_surveyer(), this.accComCredit.getDate_survey())) {
            ErrorMessage.proc3_018_cannot_create_a_report_duplicate_ac_request_date_survey_source_info().show();
            return;
        }

        this.saveAccComEntityData(InputAccountingDataBean.TEMPORARY_SAVE);

        // Update client code prohibition
        this.accComCredit.setCode_acc_prohibitcause(this.freezeReason);
        // Save acc_com_credit, industry, finance, relation
        if (this.accComCredit.isPersisted()) {
            this.updateAccCountingWithStatus(Constants.DRAFT_CREDIT_STATUS);
        } else {
            this.createAccCountingWithStatus(Constants.DRAFT_CREDIT_STATUS);
        }
    }

    /**
     * Save approve credit.
     */
    public void saveApproveCredit() {
        // Validate info of credit before save
        // In one time, only persisted a credit(report) with source info, requester and date survey
        if (this.accComEntity.isPersisted()
            && this.accountingDataService.hasAccComCreditSameInfoSurvey(this.accComEntity.getId_com_entity(),
                this.accComCredit.getId_credit(), this.accComCredit.getAc_request(),
                this.accComCredit.getCode_acc_surveyer(), this.accComCredit.getDate_survey())) {
            ErrorMessage.proc3_018_cannot_create_a_report_duplicate_ac_request_date_survey_source_info().show();
            return;
        }

        this.saveAccComEntityData(InputAccountingDataBean.APPROVE);

        // Update client code prohibition
        this.accComCredit.setCode_acc_prohibitcause(this.freezeReason);
        this.accComCredit.setDate_authorize(new Date());
        if (this.accComCredit.isPersisted()) {
            this.updateAccCountingWithStatus(Constants.SAVED_CREDIT_STATUS);
        } else {
            this.createAccCountingWithStatus(Constants.SAVED_CREDIT_STATUS);
        }
        this.entityViewMode = true;
    }

    /**
     * Save acc com entity data.
     *
     * @param action the action
     */
    private void saveAccComEntityData(final int action) {
        this.accComEntity.setAc_update(this.userCredential.getUserCode());
        if (action == InputAccountingDataBean.APPROVE) {
            this.accComEntity.setTs_update(new Date());
        }

        if (this.accComEntity.isPersisted()) {
            this.accComEntity = this.accountingDataService.updateAccComEntity(this.accComEntity).getData();
        } else {
            this.accComEntity.setAc_creation(this.userCredential.getUserCode());
            this.accComEntity.setDate_creation(new Date());
            this.accComEntity = this.accountingDataService.createAccComEntity(this.accComEntity).getData();
        }
    }

    /**
     * Checks for input industry code.
     *
     * @return true, if successful
     */
    public boolean hasInputIndustryCode() {
        if (null == this.accComCredit) {
            return false;
        }
        return !(StringUtils.isNotEmpty(this.accComCredit.getCode_acc_surveyer()) && StringUtils.equals(
            Constants.EMPIRE_DATA_CODE, this.accComCredit.getCode_acc_surveyer()));
    }

    /**
     * Update acc counting with status.
     *
     * @param status the status
     */
    private void updateAccCountingWithStatus(final char status) {
        this.accComCredit.setStatus(status);
        this.accComCredit = this.accountingDataService.updateAccComCredit(this.accComCredit).getData();

        if (Constants.SAVED_CREDIT_STATUS == status) {
            this.updateLatestCreditIdForAccEntity();
        }

        this.copyFileFromTempToRealPath();

        if ((this.accComIndustry != null) && this.accComIndustry.isPersisted()) {
            this.accComIndustry = this.accountingDataService.updateAccComIndustry(this.accComIndustry).getData();
        } else {
            // Save acc_com_industry
            if (this.accComIndustry == null) {
                this.accComIndustry = new Acc_com_industry();
            }
            this.accComIndustry.setAcc_com_entity(this.accComEntity);
            this.accComIndustry.setAcc_com_credit(this.accComCredit);
            this.accountingDataService.createAccComIndustry(this.accComIndustry);
        }

        // Save acc_com_finance
        this.inputFinancialBean.saveFinanceToDB(this.accComEntity.getId_com_entity(), this.accComCredit);
        this.inputFinancialBean.deleteFinanceInDB();
        this.inputFinancialBean.resetData();

        // save acc_com_relation
        this.inputCustomerDataBean.saveAccComRelation(this.accComCredit);

        // Back to accounting entity screen
        this.resetDataCredit();
        this.loadCreditHistory();
        InfoMessage.common_001_save_successfully().show();
    }

    /**
     * Update latest credit id for acc entity.
     */
    private void updateLatestCreditIdForAccEntity() {
        int latestIdCredit =
            this.accountingDataService.getLatestIdCreditByIdEntityAndStatus(this.accComEntity.getId_com_entity(),
                Constants.SAVED_CREDIT_STATUS);
        this.accComEntity.setId_credit(latestIdCredit);
        this.accComEntity = this.accountingDataService.updateAccComEntity(this.accComEntity).getData();
    }

    /**
     * Save credit with status.<br />
     * 1. Create new credit. <br />
     * 2. Update id_credit into entity. <br />
     * 3. Save file user upload. <br />
     * 4. Save industry. <br />
     * 5. Save finance. <br />
     * 6. Back to entity screen. <br />
     *
     * @param status the status
     */
    private void createAccCountingWithStatus(final char status) {
        // Update id_com_entity in acc_com_credit before save
        this.accComCredit.setId_com_entity(this.accComEntity.getId_com_entity());
        // Save acc_com_credit
        this.accComCredit =
            this.accountingDataService.createAccComCreditWithStatus(this.accComCredit, status).getData();

        if (Constants.SAVED_CREDIT_STATUS == status) {
            this.updateLatestCreditIdForAccEntity();
        }

        // Save report file after save credit, if save failed, show message to notice for user and continue process with
        // save credit
        this.copyFileFromTempToRealPath();

        // Save acc_com_industry
        this.accComIndustry.setAcc_com_entity(this.accComEntity);
        this.accComIndustry.setAcc_com_credit(this.accComCredit);
        this.accountingDataService.createAccComIndustry(this.accComIndustry);

        // Save acc_com_finance
        this.inputFinancialBean.saveFinanceToDB(this.accComEntity.getId_com_entity(), this.accComCredit);
        this.inputFinancialBean.deleteFinanceInDB();
        this.inputFinancialBean.resetData();

        // save acc_com_relation
        this.inputCustomerDataBean.saveAccComRelation(this.accComCredit);

        // Back to accounting entity screen
        this.resetDataCredit();
        this.loadCreditHistory();
        InfoMessage.common_001_save_successfully().show();
    }

    /**
     * Copy file from temporary path to real path after user click save and user uploaded file before click save.
     */
    private void copyFileFromTempToRealPath() {
        if (null != this.uploadedFile) {
            this.accountingDataService.saveReportFile(this.uploadedFile.getFileName(), UploadHelper.getUploadPath(
                SysConfig.getUploadAccountingDir(), this.accComCredit.getId_credit(),
                this.accComEntity.getId_com_entity()));

        }
    }

    /** The list branch. */
    private List<Addresspoint_mst> listBranch;

    /**
     * Gets the list branch.
     *
     * @return the list branch
     */
    public List<Addresspoint_mst> getListBranch() {
        if (CollectionUtils.isEmpty(this.listBranch)) {
            this.listBranch = this.accountingDataService.getListSelectItemAllBranch();
        }
        return this.listBranch;
    }

    /**
     * Filter branch.
     *
     * @param query the query
     * @return the list
     */
    public List<Addresspoint_mst> filterBranch(final String query) {
        if (StringUtils.isEmpty(query)) {
            return this.getListBranch();
        }

        return CollectionUtils.filter(this.getListBranch(), new Predicate() {

            @Override
            public boolean evaluate(final Object arg0) {
                final Addresspoint_mst item = (Addresspoint_mst) arg0;
                return item.getAdp_name().contains(query) || String.valueOf(item.getAdp_code()).contains(query);
            }
        });
    }

    /**
     * Filter applicant.
     *
     * @param query the query
     * @return the list
     */
    public List<Employee_mst> filterApplicant(final String query) {
        if (StringUtils.isEmpty(query)) {
            return this.listApplicant;
        }

        return CollectionUtils.filter(this.listApplicant, new Predicate() {

            @Override
            public boolean evaluate(final Object arg0) {
                Employee_mst item = (Employee_mst) arg0;
                return item.getEmp_name().contains(query) || String.valueOf(item.getEmp_code()).contains(query);
            }

        });
    }

    /** The list applicant. */
    private List<Employee_mst> listApplicant;

    /**
     * Reload list applicant.
     */
    public void reloadListApplicant() {
        if (null == this.accComCredit.getAddresspoint_mst()) {
            this.listApplicant = null;
        } else {
            this.listApplicant =
                this.accountingDataService
                    .getAllEmployeeInBranch(this.accComCredit.getAddresspoint_mst().getAdp_code());
        }
    }

    /** The main industry. */
    private Tdb_detail_industry mainIndustry;

    /** The industry. */
    private Tdb_detail_industry industry;

    /**
     * Load industry main.
     */
    public void loadIndustryMain() {
        String mainIndustryCode = this.accComIndustry.getCode_industry_main();
        if (StringUtils.isEmpty(mainIndustryCode)) {
            this.mainIndustry = null;
        } else {
            this.mainIndustry = this.accountingDataService.findIndustry(mainIndustryCode);
            if (null == this.mainIndustry) {
                this.accComIndustry.setCode_industry_main(null);
                ErrorMessage.proc3_006_main_industry_code_invalid().show();
            }
        }
    }

    /**
     * Load industry.
     */
    public void loadIndustry() {
        String industryCode = this.accComIndustry.getCode_industry_sub();
        if (StringUtils.isEmpty(industryCode)) {
            this.industry = null;
        } else {
            this.industry = this.accountingDataService.findIndustry(industryCode);
            if (null == this.industry) {
                this.accComIndustry.setCode_industry_sub(null);
                ErrorMessage.proc3_007_industry_code_invalid().show();
            }
        }
    }

    /**
     * Gets the main industry.
     *
     * @return the main industry
     */
    public Tdb_detail_industry getMainIndustry() {
        return this.mainIndustry;
    }

    /**
     * Sets the main industry.
     *
     * @param mainIndustry the new main industry
     */
    public void setMainIndustry(final Tdb_detail_industry mainIndustry) {
        this.mainIndustry = mainIndustry;
    }

    /**
     * Gets the industry.
     *
     * @return the industry
     */
    public Tdb_detail_industry getIndustry() {
        return this.industry;
    }

    /**
     * Sets the industry.
     *
     * @param industry the new industry
     */
    public void setIndustry(final Tdb_detail_industry industry) {
        this.industry = industry;
    }

    /**
     * Filter approval employee.
     *
     * @param query the query
     * @return the list
     */
    public List<Employee_mst> filterApprovalEmployee(final String query) {
        if (StringUtils.isEmpty(query)) {
            return this.getListApprovalEmployee();
        }

        return CollectionUtils.filter(this.getListApprovalEmployee(), new Predicate() {

            @Override
            public boolean evaluate(final Object arg0) {
                Employee_mst item = (Employee_mst) arg0;
                return item.getEmp_name().contains(query) || String.valueOf(item.getEmp_code()).contains(query);
            }
        });
    }

    /** The list approval employee. */
    private List<Employee_mst> listApprovalEmployee;

    /**
     * Gets the list approval employee.
     *
     * @return the list approval employee
     */
    private List<Employee_mst> getListApprovalEmployee() {
        if (CollectionUtils.isEmpty(this.listApprovalEmployee)) {
            this.listApprovalEmployee = this.accountingDataService.getAllEmployeeIsManagerHq();
        }
        return this.listApprovalEmployee;
    }

    /**
     * Checks if is view mode.
     *
     * @return true, if is view mode
     */
    public boolean isViewMode() {
        return this.viewMode;
    }

    /**
     * Sets the view mode.
     *
     * @param viewMode the new view mode
     */
    public void setViewMode(final boolean viewMode) {
        this.viewMode = viewMode;
    }

    /** The list credits. */
    private List<Acc_com_credit> listCredits;

    /**
     * Load credit history.
     */
    private void loadCreditHistory() {
        this.tabView = 0;
        this.listCredits = this.accountingDataService.getCreditHistoryByEntityId(this.accComEntity.getId_com_entity());
    }

    /**
     * Gets the list credits.
     *
     * @return the list credits
     */
    public List<Acc_com_credit> getListCredits() {
        return this.listCredits;
    }

    /**
     * Sets the list credits.
     *
     * @param listCredits the new list credits
     */
    public void setListCredits(final List<Acc_com_credit> listCredits) {
        this.listCredits = listCredits;
    }

    /**
     * Select credit event.
     *
     * @param evt the evt
     */
    public void selectCreditEvent(final SelectEvent evt) {
        if (null == evt.getObject()) {
            return;
        }
        // Show first tab in new screen
        this.tabView = 0;
        this.accComCredit = (Acc_com_credit) evt.getObject();
    }

    /**
     * Checks if is entity view mode.
     *
     * @return true, if is entity view mode
     */
    public boolean isEntityViewMode() {
        return this.entityViewMode;
    }

    /**
     * Sets the entity view mode.
     *
     * @param entityViewMode the new entity view mode
     */
    public void setEntityViewMode(final boolean entityViewMode) {
        this.entityViewMode = entityViewMode;
    }

    /**
     * Change source listener.
     */
    public void changeSourceListener() {
        this.mainIndustry = null;
        this.industry = null;
        this.accComIndustry.setCode_industry_main(null);
        this.accComIndustry.setCode_industry_sub(null);
    }

    /**
     * Gets the tab view.
     *
     * @return the tab view
     */
    public int getTabView() {
        return this.tabView;
    }

    /**
     * Sets the tab view.
     *
     * @param tabView the new tab view
     */
    public void setTabView(final int tabView) {
        this.tabView = tabView;
    }

    /** The tab view. */
    private int tabView;

    // Regex from windows
    /** The Constant PATTERN_FAX. */
    private static final String PATTERN_FAX = "^\\d{1,4}-\\d{4}$|^\\d{2,5}-\\d{1,4}-\\d{4}$|^0\\d0-\\d{4}-\\d{4}$";

    /**
     * Validation fax.
     */
    public void validationFax() {
        if (StringUtils.isEmpty(this.accComCredit.getFax_hq())) {
            return;
        }
        Pattern pattern = Pattern.compile(InputAccountingDataBean.PATTERN_FAX);
        Matcher matcher = pattern.matcher(this.accComCredit.getFax_hq());
        if (!matcher.matches()) {
            ErrorMessage.proc3_017_please_enter_a_valid_fax().show();
            this.accComCredit.setFax_hq(null);
        }
    }

    /** The report file. */
    private StreamedContent reportFile;

    /**
     * Gets the download file.
     *
     * @param entity the entity
     * @param credit the credit
     * @return the download file
     */
    private void getDownloadFile(final Acc_com_entity entity, final Acc_com_credit credit) {
        String sourceFilePath =
            UploadHelper.getUploadPath(SysConfig.getUploadAccountingDir(), credit.getId_credit(),
                entity.getId_com_entity());
        try {
            InputStream is = new FileInputStream(new File(sourceFilePath, credit.getPath_report()));
            this.reportFile =
                new DefaultStreamedContent(is, FileUtils.getContentType(credit.getPath_report()),
                    credit.getPath_report());
        } catch (final FileNotFoundException ex) {
            super.log.debug(ex);
            return;
        }
    }

    /**
     * Gets the report file.
     *
     * @return the report file
     */
    public StreamedContent getReportFile() {
        return this.reportFile;
    }

    /**
     * Sets the report file.
     *
     * @param reportFile the new report file
     */
    public void setReportFile(final StreamedContent reportFile) {
        this.reportFile = reportFile;
    }

    /**
     * Checks if is show download.
     *
     * @return true, if is show download
     */
    public boolean isShowDownload() {
        return this.showDownload;
    }

    /**
     * Sets the show download.
     *
     * @param showDownload the new show download
     */
    public void setShowDownload(final boolean showDownload) {
        this.showDownload = showDownload;
    }

    /** The show download. */
    private boolean showDownload;

    /**
     * Checks if is show detail report.
     *
     * @return true, if is show detail report
     */
    public boolean isShowDetailReport() {
        return this.showDetailReport;
    }

    /**
     * Sets the show detail report.
     *
     * @param showDetailReport the new show detail report
     */
    public void setShowDetailReport(final boolean showDetailReport) {
        this.showDetailReport = showDetailReport;
    }

    /**
     * Modify action.
     */
    public void modifyAction() {
        // Load all data with selected credit
        this.updateCredit();
        // Storage this selected credit
        Acc_com_credit selectedCredit = this.accComCredit;
        Acc_com_industry selectedIndustry = this.accComIndustry;
        this.inputFinancialBean.storageSelectedDataWithCurrentCredit();
        this.inputCustomerDataBean.storageSelectedDataWithCurrentCredit();

        // Reset all data of credit
        this.cancel();

        // Create new credit (report)
        this.createCredit();
        // Copy all data from storage to new data
        BeanCopier.copy(selectedCredit, this.accComCredit, "id_credit", "ac_request", "date_survey", "date_authorize");
        this.accComCredit.setAc_request(this.userCredential.getUserCode());
        this.accComCredit.setDate_survey(new Date());
        BeanCopier.copy(selectedIndustry, this.accComIndustry, "id_industry");
        this.inputFinancialBean.copyStorageToNewData();
        this.inputCustomerDataBean.copyStorageToNewData();
    }

    /**
     * Gets the storage list card from proc2.
     *
     * @return the storage list card from proc2
     */
    public List<FindDataNameCardEntity> getStorageListCardFromProc2() {
        return this.storageListCardFromProc2;
    }

    /**
     * Sets the storage list card from proc2.
     *
     * @param storageListCardFromProc2 the new storage list card from proc2
     */
    public void setStorageListCardFromProc2(final List<FindDataNameCardEntity> storageListCardFromProc2) {
        this.storageListCardFromProc2 = storageListCardFromProc2;
    }

    /**
     * Gets the storage key word id name card.
     *
     * @return the storage key word id name card
     */
    public String getStorageKeyWordIdNameCard() {
        return this.storageKeyWordIdNameCard;
    }

    /**
     * Sets the storage key word id name card.
     *
     * @param storageKeyWordIdNameCard the new storage key word id name card
     */
    public void setStorageKeyWordIdNameCard(final String storageKeyWordIdNameCard) {
        this.storageKeyWordIdNameCard = storageKeyWordIdNameCard;
    }

    /**
     * Gets the storage key word company name.
     *
     * @return the storage key word company name
     */
    public String getStorageKeyWordCompanyName() {
        return this.storageKeyWordCompanyName;
    }

    /**
     * Sets the storage key word company name.
     *
     * @param storageKeyWordCompanyName the new storage key word company name
     */
    public void setStorageKeyWordCompanyName(final String storageKeyWordCompanyName) {
        this.storageKeyWordCompanyName = storageKeyWordCompanyName;
    }

    /**
     * Switch to find data name card.
     */
    public void switchToFindDataNameCard() {
        Map<String, Object> attrs = new HashMap<String, Object>();
        attrs.put(ScreenContext.SharingDataKey.LIST_COMPANY_PROC2, this.storageListCardFromProc2);
        attrs.put(ScreenContext.SharingDataKey.KEY_WORD_ID_NAME_CARD, this.storageKeyWordIdNameCard);
        attrs.put(ScreenContext.SharingDataKey.KEY_WORD_COMPANY_NAME, this.storageKeyWordCompanyName);
        attrs.put(ScreenContext.SharingDataKey.KEY_WORD_CUSTOMER_CODE, this.storageKeyWorkCustomerCode);
        attrs.put(ScreenContext.SharingDataKey.ACTIVE_INDEX_FIND_ACCOUNTING_DATA, this.activeIndexFindAccountingData);
        this.screenBean.switchScreenWithObjectData(ScreenContext.SEARCH_NAME_CARD_DATA_PROC_1, true, attrs);
    }

    /**
     * Checks for credit draft.
     *
     * @return true, if successful
     */
    public boolean hasCreditDraft() {
        if (!this.accComEntity.isPersisted()) {
            return false;
        }
        return !this.accountingDataService.hasNotCreditWithDraftStatus(this.accComEntity.getId_com_entity(),
            this.userCredential.getUserCode());
    }

    /**
     * Edits the draft credit.
     */
    public void editDraftCredit() {
        this.accComCredit =
            this.accountingDataService.findDraftCreditOfUser(this.accComEntity.getId_com_entity(),
                this.userCredential.getUserCode());
        this.accComCredit.setAddresspoint_mst(this.userCredential.getUserEmployee().getAddresspoint_mst());
        this.accComCredit.setEmployee_mst(this.userCredential.getUserEmployee());
        this.updateCredit();
    }

    /**
     * Reset credit.
     */
    public void resetCredit() {
        this.cancel();
        this.createCredit();
    }

    /**
     * Modify action from detail.
     */
    public void modifyActionFromDetail() {
        Acc_com_credit currentCredit = this.accComCredit;
        this.resetDataCredit();
        this.accComCredit = currentCredit;
        this.modifyAction();
    }

    /**
     * Discard credit.
     */
    public void discardCredit() {
        ServiceResult<Acc_com_credit> result = this.accountingDataService.discardCredit(this.accComCredit);
        result.showMessages();
        this.resetDataCredit();
    }

    /**
     * Gets the storage key work customer code.
     *
     * @return the storage key work customer code
     */
    public String getStorageKeyWorkCustomerCode() {
        return this.storageKeyWorkCustomerCode;
    }

    /**
     * Sets the storage key work customer code.
     *
     * @param storageKeyWorkCustomerCode the new storage key work customer code
     */
    public void setStorageKeyWorkCustomerCode(final String storageKeyWorkCustomerCode) {
        this.storageKeyWorkCustomerCode = storageKeyWorkCustomerCode;
    }

    /**
     * Gets the tab index find accounting data.
     *
     * @return the tab index find accounting data
     */
    public int getTabIndexFindAccountingData() {
        return this.activeIndexFindAccountingData;
    }

    /**
     * Sets the tab index find accounting data.
     *
     * @param tabIndexFindAccountingData the new tab index find accounting data
     */
    public void setTabIndexFindAccountingData(final int tabIndexFindAccountingData) {
        this.activeIndexFindAccountingData = tabIndexFindAccountingData;
    }

}
