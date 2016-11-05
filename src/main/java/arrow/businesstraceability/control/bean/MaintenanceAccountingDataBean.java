package arrow.businesstraceability.control.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.context.RequestContext;

import arrow.businesstraceability.cache.entity.FindDataNameCardEntity;
import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.control.service.ElasticSearchService;
import arrow.businesstraceability.control.service.MaintenanceAcountingDataService;
import arrow.businesstraceability.persistence.entity.Acc_com_bugyo90;
import arrow.businesstraceability.persistence.entity.Acc_com_entity;
import arrow.businesstraceability.persistence.entity.San_com_info;
import arrow.businesstraceability.persistence.entity.San_com_live_stat;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.util.DateUtils;
import arrow.framework.util.MessageUtils;
import arrow.framework.util.StringUtils;
import arrow.framework.util.collections.CollectionUtils;
import arrow.framework.util.i18n.Messages;

/**
 * The Class FindDataNameCard.
 */
@Named
@ViewScoped
public class MaintenanceAccountingDataBean extends AbstractBean {

    /** The elastic search service. */
    @Inject
    private ElasticSearchService elasticSearchService;

    /** The find data name card service. */
    @Inject
    private MaintenanceAcountingDataService maintenanceAccountingService;

    /** The acc com bugyo bean. */
    @Inject
    private AccComBugyoBean accComBugyoBean;

    /** The input customer code. */
    private String inputCustomerCode;

    /** The input id sansan company. */
    private String inputIdSansanCompany;

    /** The input national company name. */
    private String inputNationalCompanyName;

    /** The list accounting data. */
    private List<Acc_com_entity> listAccountingData;

    /** The input id name card. */
    private String inputIdNameCard;

    /** The input company name. */
    private String inputCompanySansanName;

    /** Sansan Company ID for Sansan Company info. */
    private String inputCompanySansan;

    /** The is render sansan company panel. */
    private boolean isRenderSansanCompanyPanel = false;

    /** The list company. */
    private List<FindDataNameCardEntity> listCompany;

    /** The list name card. */
    private List<FindDataNameCardEntity> listNameCard;

    /** The selected Id sansan company. */
    private String companyIdSansanMapWithAcc;

    /** The selected name sansan company. */
    private String companyNameSansanMapWithAcc;

    /** The client status. */
    private Map<String, String> clientStatus;

    /** The link status. */
    private int linkStatus = MaintenanceAccountingDataBean.UNASSOCIATED;

    /** The unassociated. */
    private static int UNASSOCIATED = 0;

    /** The list selected com entity. */
    private List<Acc_com_entity> listSelectedComEntity;

    /** The freeze reason. */
    private String freezeReason;

    /** The Constant TEMPORARY_FREEZE. */
    private static final String TEMPORARY_FREEZE = "00001";

    /** The Constant STOP_USING. */
    private static final String STOP_USING = "09999";

    /** The Constant NOT_AVAILABLE. */
    private static final String NOT_AVAILABLE = "N/A";

    /** The search by customer code. */
    private boolean searchByCustomerCode = true;

    /** The active index. */
    private int activeIndex = 0;

    /** The company id int sansan map with acc. */
    private String companyIdIntSansanMapWithAcc;

    /** The display code acc status. */
    private String displayCodeAccStatus;

    /** The display company name acc com. */
    private String displayCompanyNameAccCom;

    /** The display company name kana acc com. */
    private String displayCompanyNameKanaAccCom;

    /** The display year start acc com. */
    private String displayYearStartAccCom;

    /** The display year est acc com. */
    private String displayYearEstAccCom;

    /**
     * Generate title acc com entity.
     */
    public void generateTitleAccComEntity() {
        if ((this.clientStatus == null) || this.clientStatus.isEmpty()) {
            this.clientStatus = new HashMap<String, String>();
            List<Acc_com_bugyo90> bugyo90 = this.accComBugyoBean.getListAccComBugyo90();
            if (CollectionUtils.isNotEmpty(bugyo90)) {
                for (Acc_com_bugyo90 acc_com_bugyo90 : bugyo90) {
                    this.clientStatus.put(acc_com_bugyo90.getCode(), acc_com_bugyo90.getTitle());
                }
            }
        }
    }

    /**
     * Search by customer code.
     */
    public void searchByCustomerCode() {
        this.listAccountingData = new ArrayList<Acc_com_entity>();
        this.isRenderSansanCompanyPanel = false;

        this.setSearchByCustomerCode(true);
        this.generateTitleAccComEntity();
        if (StringUtils.isNotEmpty(this.inputCustomerCode)) {
            Acc_com_entity acc_com_entity =
                this.maintenanceAccountingService.getComEntityByCustomerCode(this.inputCustomerCode.concat(
                    Constants.CUSTOMER_CODE_SUFFIX).toUpperCase());
            if (acc_com_entity != null) {
                this.listAccountingData.add(acc_com_entity);
            }
        }

    }

    /**
     * Search by other conditions.
     */
    public void searchByOtherConditions() {
        this.listAccountingData = new ArrayList<Acc_com_entity>();
        this.isRenderSansanCompanyPanel = false;
        this.setSearchByCustomerCode(false);
        this.generateTitleAccComEntity();
        if (this.linkStatus == MaintenanceAccountingDataBean.UNASSOCIATED) {
            if (StringUtils.isNotEmpty(this.inputNationalCompanyName)) {
                this.setListAccountingData(this.maintenanceAccountingService
                    .getComEntityByCompanyNameAndIdMappingNull(this.inputNationalCompanyName));
                return;
            } else {
                this.setListAccountingData(this.maintenanceAccountingService.getAllAccComEntityNotMapping());
                return;
            }
        } else {
            if (StringUtils.isEmpty(this.inputIdSansanCompany) && StringUtils.isNotEmpty(this.inputNationalCompanyName)) {
                this.setListAccountingData(this.maintenanceAccountingService
                    .getComEntityByCompanyNameAndIdMappingNotNull(this.inputNationalCompanyName));
                return;
            }
            if (StringUtils.isEmpty(this.inputNationalCompanyName) && StringUtils.isNotEmpty(this.inputIdSansanCompany)) {
                if (StringUtils.isIntegerValue(this.inputIdSansanCompany)) {
                    this.setListAccountingData(this.maintenanceAccountingService.getAccComEntityByIdIntCompany(Integer
                        .valueOf(this.inputIdSansanCompany)));
                    return;
                } else {
                    this.setListAccountingData(this.maintenanceAccountingService
                        .getAccComWithIdSanCompanyAndIdMappingNotNull(this.inputIdSansanCompany));
                    return;
                }
            }
            if (StringUtils.isIntegerValue(this.inputIdSansanCompany)) {
                this.setListAccountingData(this.maintenanceAccountingService.getComEntityByIdIntSanCompanyAndName(
                    Integer.parseInt(this.inputIdSansanCompany), this.inputNationalCompanyName));
                return;
            }
            this.setListAccountingData(this.maintenanceAccountingService.getAccComEntityByIdSanCompanyAndName(
                this.inputIdSansanCompany, this.inputNationalCompanyName));
        }

    }

    /**
     * On select accounting action.
     *
     */
    public void onSelectAccounting() {
        this.inputIdNameCard = "";
        if (this.listSelectedComEntity.get(0).getId_int_san_company() != null) {
            this.inputCompanySansan = this.listSelectedComEntity.get(0).getId_int_san_company().toString();
            San_com_info comInfo =
                this.maintenanceAccountingService.getCompanyInfoByIdIntSansanCompany(this.listSelectedComEntity.get(0)
                    .getId_int_san_company());
            if (comInfo != null) {
                this.inputCompanySansanName = comInfo.getName_company();
            }
            this.searchSansanCompany();
        } else {
            this.inputCompanySansan = "";
            this.inputCompanySansanName = "";
            this.listCompany = new ArrayList<FindDataNameCardEntity>();

        }
        if (this.listSelectedComEntity.get(0).getId_credit() != null) {
            this.freezeReason =
                this.maintenanceAccountingService.getCode_acc_prohibitcauseByAccComEntity(this.listSelectedComEntity
                    .get(0));
        }
        this.displayCodeAccStatus = this.listSelectedComEntity.get(0).getCode_acc_client();
        this.displayCompanyNameAccCom = this.listSelectedComEntity.get(0).getName_company();
        this.displayCompanyNameKanaAccCom = this.listSelectedComEntity.get(0).getName_com_kana();
        this.displayYearEstAccCom = String.valueOf(this.listSelectedComEntity.get(0).getY_establish());
        this.displayYearStartAccCom = String.valueOf(this.listSelectedComEntity.get(0).getY_start());
        this.getCompanySansanInfoLabel();
        this.isRenderSansanCompanyPanel = true;
    }

    /**
     * Search sansan company.
     *
     */
    public void searchSansanCompany() {

        String idCompanyIntValue = "";
        if (StringUtils.isNotEmpty(this.inputCompanySansan)) {
            Integer idCompanyConverted =
                this.maintenanceAccountingService.convertToIdIntSanCompany(this.inputCompanySansan);
            if (idCompanyConverted == null) {
                return;
            }
            idCompanyIntValue = idCompanyConverted.toString();
        }
        List<Map.Entry<Integer, String>> listMapIdAndCompanyName =
            this.elasticSearchService.findAllSanCardDataWithSameIdAndNameAndIdSanCom(this.inputIdNameCard,
                this.inputCompanySansanName, idCompanyIntValue);
        this.listCompany = new ArrayList<FindDataNameCardEntity>();
        if (listMapIdAndCompanyName.size() != 0) {
            List<Integer> lisIdCompany = new ArrayList<Integer>();
            Iterator<Map.Entry<Integer, String>> itr = listMapIdAndCompanyName.iterator();
            if (StringUtils.isNotEmpty(this.inputIdNameCard)) {
                while (itr.hasNext()) {
                    Map.Entry<Integer, String> element = itr.next();
                    int idCompanyInListSan = element.getKey();
                    if (lisIdCompany.contains(idCompanyInListSan)) {
                        itr.remove();
                    } else {
                        lisIdCompany.add(idCompanyInListSan);
                    }
                }
            } else {
                while (itr.hasNext()) {
                    Map.Entry<Integer, String> element = itr.next();
                    lisIdCompany.add(element.getKey());
                }
            }
            Map<Integer, San_com_live_stat> listSanComStatOfCompany =
                this.elasticSearchService.getListSanComLiveStatByListIdCompany(lisIdCompany);
            if (listSanComStatOfCompany == null) {
                return;
            }
            for (int i = 0; i < listMapIdAndCompanyName.size(); i++) {
                FindDataNameCardEntity temp = new FindDataNameCardEntity();
                San_com_live_stat currentSanComLiveStat =
                    listSanComStatOfCompany.get(listMapIdAndCompanyName.get(i).getKey());
                if (currentSanComLiveStat == null) {
                    continue;
                }
                temp.setIdSanCompany(this.maintenanceAccountingService
                    .getIdSanCompanyInSanComInfoWithIdCompany(listMapIdAndCompanyName.get(i).getKey()));
                temp.setCompanyName(listMapIdAndCompanyName.get(i).getValue());
                temp.setCountUrlOfCompany(this.maintenanceAccountingService
                    .countNumberUrlOfCompanyByIdCompany(lisIdCompany.get(i)));
                temp.setSumOfCard(currentSanComLiveStat.getSn_card());
                temp.setSumOfOwner(currentSanComLiveStat.getSn_owner());
                temp.setSumOfClient(currentSanComLiveStat.getSn_client());
                temp.setLatestChanged(DateUtils.formatDate(currentSanComLiveStat.getDate_latest_exchange()));
                temp.setOldestChanged(DateUtils.formatDate(currentSanComLiveStat.getDate_oldest_exchange()));
                temp.setRegularContact(currentSanComLiveStat.getCn_cp());
                temp.setFreqOfAccess(currentSanComLiveStat.getCn_vp());
                temp.setIdCompany(currentSanComLiveStat.getId_company());
                Acc_com_entity accEntity =
                    this.maintenanceAccountingService.getAccountingDataByIdIntCompany(currentSanComLiveStat
                        .getId_company());
                if (accEntity != null) {
                    temp.setIdComEntity(accEntity.getId_com_entity());
                }
                if ((this.listSelectedComEntity.get(0).getId_int_san_company() != null)
                    && (this.listSelectedComEntity.get(0).getId_int_san_company().equals(listMapIdAndCompanyName.get(i)
                        .getKey()))) {
                    temp.setSelected(true);
                    this.listCompany.add(0, temp);
                    continue;
                }
                this.listCompany.add(temp);
            }
            this.getCompanySansanInfoLabel();
        }
    }

    /**
     * Validate acc com entity.
     *
     * @return true, if successful
     */
    public boolean validateAccComEntity() {
        List<Message> errors = new ArrayList<Message>();
        if (!this.getCompanyIdIntSansanMapWithAcc().equalsIgnoreCase(MaintenanceAccountingDataBean.NOT_AVAILABLE)) {
            Acc_com_entity accEntity =
                this.maintenanceAccountingService.getAccountingDataByIdIntCompany(Integer.valueOf(this
                    .getCompanyIdIntSansanMapWithAcc()));
            if ((accEntity != null)
                && (accEntity.getId_com_entity() != this.listSelectedComEntity.get(0).getId_com_entity())) {
                errors.add(ErrorMessage.maintenance_accounting_001_id_company_using_by_other_customer(accEntity
                    .getIndx_customer()));
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.execute("PrimeFaces.widgets['sansanCompanyTable'].unselectAllRows();");
            }
        }
        errors
            .addAll(this.validateAccComEntityBeforeSaveTemporary(this.listSelectedComEntity.get(0), this.freezeReason));

        if (CollectionUtils.isEmpty(errors)) {
            return true;
        }
        MessageUtils.showMessages(errors);
        return false;
    }

    /**
     * Gets the company sansan info label.
     *
     * @return the company sansan info label
     */
    private void getCompanySansanInfoLabel() {
        if (this.listSelectedComEntity.get(0).getId_int_san_company() != null) {
            San_com_info companySansan =
                this.maintenanceAccountingService.getCompanyInfoByIdIntSansanCompany(this.listSelectedComEntity.get(0)
                    .getId_int_san_company());
            if (companySansan != null) {
                this.companyIdSansanMapWithAcc =
                    companySansan.getId_san_company() + "(" + companySansan.getId_company() + ")";
                this.companyNameSansanMapWithAcc = companySansan.getName_company();
                this.companyIdIntSansanMapWithAcc = companySansan.getId_company().toString();
            } else {
                this.companyIdSansanMapWithAcc = Messages.get("not_available");
                this.companyNameSansanMapWithAcc = Messages.get("not_available");
                this.companyIdIntSansanMapWithAcc = Messages.get("not_available");
            }
        } else {
            this.companyIdSansanMapWithAcc = Messages.get("not_available");
            this.companyNameSansanMapWithAcc = Messages.get("not_available");
            this.companyIdIntSansanMapWithAcc = Messages.get("not_available");
        }
    }

    /**
     * cancel action.
     *
     */
    public void cancel() {
        this.isRenderSansanCompanyPanel = false;
    }

    /**
     * save accounting data.
     *
     */
    public void save() {
        RequestContext context = RequestContext.getCurrentInstance();
        Integer idCompanyMapped = null;
        // Validate all required fields before save temporary
        if (!this.getCompanyIdIntSansanMapWithAcc().equalsIgnoreCase(MaintenanceAccountingDataBean.NOT_AVAILABLE)) {
            idCompanyMapped = Integer.valueOf(this.getCompanyIdIntSansanMapWithAcc());
        }
        if (!this.validateAccComEntity()) {
            context.execute("loadFreezeReason()");
            return;
        }
        this.listSelectedComEntity.get(0).setCode_acc_client(this.getDisplayCodeAccStatus());
        this.listSelectedComEntity.get(0).setName_company(this.getDisplayCompanyNameAccCom());
        this.listSelectedComEntity.get(0).setName_com_kana(this.getDisplayCompanyNameKanaAccCom());
        this.listSelectedComEntity.get(0)
            .setY_start(
                StringUtils.isNotEmpty(this.getDisplayYearStartAccCom()) ? Integer.valueOf(this
                    .getDisplayYearStartAccCom()) : null);
        this.listSelectedComEntity.get(0).setY_establish(
            StringUtils.isNotEmpty(this.getDisplayYearEstAccCom()) ? Integer.valueOf(this.getDisplayYearEstAccCom())
                : null);
        Acc_com_entity researchAccCom =
            this.maintenanceAccountingService.findAccComEntityByIdComEntity(this.listSelectedComEntity.get(0)
                .getId_com_entity());
        BeanCopier.flatCopy(this.listSelectedComEntity.get(0), researchAccCom);
        this.maintenanceAccountingService.saveAccComEntityData(researchAccCom, idCompanyMapped);
        if (StringUtils.isNotEmpty(this.freezeReason)) {
            this.maintenanceAccountingService.saveCode_acc_prohibitcause(this.freezeReason, researchAccCom);
        }
        this.isRenderSansanCompanyPanel = false;
        // research after save
        if (this.searchByCustomerCode) {
            this.searchByCustomerCode();
            this.setActiveIndex(0);
        } else {
            this.searchByOtherConditions();
            this.setActiveIndex(1);
        }
        List<Message> success = new ArrayList<Message>();
        success.add(InfoMessage.common_001_save_successfully());
        MessageUtils.showMessages(success);
        context.update("proc2Panel");
        context.update("formAccountTable");
        context.execute("PF('blockUIWidget').hide();");
        context.execute("disableSelectBtnAfterSearch()");
        this.listSelectedComEntity = new ArrayList<Acc_com_entity>();
    }

    /**
     * Validate acc com credit before save temporary.
     *
     * @param accCom the acc com
     * @param freezeReason the freeze reason
     * @return the list
     */
    public List<Message> validateAccComEntityBeforeSaveTemporary(final Acc_com_entity accCom, final String freezeReason) {
        List<Message> errors = new ArrayList<>();
        if (StringUtils.isEmpty(this.displayCompanyNameAccCom)) {
            errors.add(ErrorMessage.maintenance_accounting_002_name_company_is_required());
        }

        if (StringUtils.isEmpty(this.displayCodeAccStatus)) {
            errors.add(ErrorMessage.maintenance_accounting_004_client_status_is_required());
        } else if (this.isFreezeReasonRequired()) {
            if (accCom.getId_credit() == null) {
                errors.add(ErrorMessage.maintenance_accounting_006_update_with_code_prohibition());
            } else if (StringUtils.isEmpty(this.freezeReason)) {
                errors.add(ErrorMessage.maintenance_accounting_005_client_code_prohibition_is_required());
            }
        }
        if (StringUtils.isEmpty(this.displayCompanyNameKanaAccCom)) {
            errors.add(ErrorMessage.maintenance_accounting_003_name_furigana_is_required());
        }
        if (StringUtils.isNotEmpty(this.displayYearStartAccCom)
            && !this.displayYearStartAccCom.matches("[1-2][0-9]{3}")) {
            errors.add(ErrorMessage.maintenance_accounting_007_year_of_starting_business_not_valid());
        }
        if (StringUtils.isNotEmpty(this.displayYearEstAccCom) && !this.displayYearEstAccCom.matches("[1-2][0-9]{3}")) {
            errors.add(ErrorMessage.maintenance_accounting_008_year_of_official_establishment_not_valid());
        }
        return errors;
    }

    /**
     * Checks if is freeze reason required.
     *
     * @return true, if is freeze reason required
     */
    public boolean isFreezeReasonRequired() {
        return (((this.displayCodeAccStatus.equals(MaintenanceAccountingDataBean.STOP_USING) || (this.displayCodeAccStatus
            .equals(MaintenanceAccountingDataBean.TEMPORARY_FREEZE)))));
    }

    /**
     * get list card in table.
     *
     * @return the list card
     */
    public List<FindDataNameCardEntity> getListCard() {
        return this.listCompany;
    }

    /**
     * getter.
     *
     * @return the inputCustomerCode
     */
    public String getInputCustomerCode() {
        return this.inputCustomerCode;
    }

    /**
     * setter.
     *
     * @param inputCustomerCode the inputCustomerCode to set
     */
    public void setInputCustomerCode(final String inputCustomerCode) {
        this.inputCustomerCode = inputCustomerCode;
    }

    /**
     * getter.
     *
     * @return the inputIdSansanCompany
     */
    public String getInputIdSansanCompany() {
        return this.inputIdSansanCompany;
    }

    /**
     * setter.
     *
     * @param inputIdSansanCompany the inputIdSansanCompany to set
     */
    public void setInputIdSansanCompany(final String inputIdSansanCompany) {
        this.inputIdSansanCompany = inputIdSansanCompany;
    }

    /**
     * getter.
     *
     * @return the inputNationalCompanyName
     */
    public String getInputNationalCompanyName() {
        return this.inputNationalCompanyName;
    }

    /**
     * Sets the input national company name.
     *
     * @param inputNationalCompanyName the new input national company name
     */
    public void setInputNationalCompanyName(final String inputNationalCompanyName) {
        this.inputNationalCompanyName = inputNationalCompanyName;
    }


    /**
     * Gets the list accounting data.
     *
     * @return the list accounting data
     */
    public List<Acc_com_entity> getListAccountingData() {
        return this.listAccountingData;
    }


    /**
     * Sets the list accounting data.
     *
     * @param listAccountingData the new list accounting data
     */
    public void setListAccountingData(final List<Acc_com_entity> listAccountingData) {
        this.listAccountingData = listAccountingData;
    }


    /**
     * Gets the input id name card.
     *
     * @return the input id name card
     */
    public String getInputIdNameCard() {
        return this.inputIdNameCard;
    }


    /**
     * Sets the input id name card.
     *
     * @param inputIdNameCard the new input id name card
     */
    public void setInputIdNameCard(final String inputIdNameCard) {
        this.inputIdNameCard = inputIdNameCard;
    }


    /**
     * Gets the input company sansan name.
     *
     * @return the input company sansan name
     */
    public String getInputCompanySansanName() {
        return this.inputCompanySansanName;
    }


    /**
     * Sets the input company sansan name.
     *
     * @param inputCompanySansanName the new input company sansan name
     */
    public void setInputCompanySansanName(final String inputCompanySansanName) {
        this.inputCompanySansanName = inputCompanySansanName;
    }


    /**
     * Gets the input company sansan.
     *
     * @return the input company sansan
     */
    public String getInputCompanySansan() {
        return this.inputCompanySansan;
    }


    /**
     * Sets the input company sansan.
     *
     * @param inputCompanySansan the new input company sansan
     */
    public void setInputCompanySansan(final String inputCompanySansan) {
        this.inputCompanySansan = inputCompanySansan;
    }


    /**
     * Checks if is render sansan company panel.
     *
     * @return true, if is render sansan company panel
     */
    public boolean isRenderSansanCompanyPanel() {
        return this.isRenderSansanCompanyPanel;
    }


    /**
     * Sets the render sansan company panel.
     *
     * @param isRenderSansanCompanyPanel the new render sansan company panel
     */
    public void setRenderSansanCompanyPanel(final boolean isRenderSansanCompanyPanel) {
        this.isRenderSansanCompanyPanel = isRenderSansanCompanyPanel;
    }


    /**
     * Gets the company id sansan map with acc.
     *
     * @return the company id sansan map with acc
     */
    public String getCompanyIdSansanMapWithAcc() {
        return this.companyIdSansanMapWithAcc;
    }


    /**
     * Sets the company id sansan map with acc.
     *
     * @param companyIdSansanMapWithAcc the new company id sansan map with acc
     */
    public void setCompanyIdSansanMapWithAcc(final String companyIdSansanMapWithAcc) {
        this.companyIdSansanMapWithAcc = companyIdSansanMapWithAcc;
    }


    /**
     * Gets the company name sansan map with acc.
     *
     * @return the company name sansan map with acc
     */
    public String getCompanyNameSansanMapWithAcc() {
        return this.companyNameSansanMapWithAcc;
    }


    /**
     * Sets the company name sansan map with acc.
     *
     * @param companyNameSansanMapWithAcc the new company name sansan map with acc
     */
    public void setCompanyNameSansanMapWithAcc(final String companyNameSansanMapWithAcc) {
        this.companyNameSansanMapWithAcc = companyNameSansanMapWithAcc;
    }


    /**
     * Gets the client status.
     *
     * @return the client status
     */
    public Map<String, String> getClientStatus() {
        return this.clientStatus;
    }


    /**
     * Sets the client status.
     *
     * @param clientStatus the client status
     */
    public void setClientStatus(final Map<String, String> clientStatus) {
        this.clientStatus = clientStatus;
    }

    /**
     * Gets the link status.
     *
     * @return the link status
     */
    public int getLinkStatus() {
        return this.linkStatus;
    }

    /**
     * Sets the link status.
     *
     * @param linkStatus the new link status
     */
    public void setLinkStatus(final int linkStatus) {
        this.linkStatus = linkStatus;
    }

    /**
     * Gets the list name card.
     *
     * @return the list name card
     */
    public List<FindDataNameCardEntity> getListNameCard() {
        if (CollectionUtils.isEmpty(this.listNameCard)) {
            this.listNameCard = new ArrayList<FindDataNameCardEntity>();
        }
        return this.listNameCard;
    }

    /**
     * Sets the list name card.
     *
     * @param listNameCard the new list name card
     */
    public void setListNameCard(final List<FindDataNameCardEntity> listNameCard) {
        this.listNameCard = listNameCard;
    }

    /**
     * Gets the list selected com entity.
     *
     * @return the list selected com entity
     */
    public List<Acc_com_entity> getListSelectedComEntity() {
        return this.listSelectedComEntity;
    }

    /**
     * Sets the list selected com entity.
     *
     * @param listSelectedComEntity the new list selected com entity
     */
    public void setListSelectedComEntity(final List<Acc_com_entity> listSelectedComEntity) {
        this.listSelectedComEntity = listSelectedComEntity;
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
     * Checks if is search by customer code.
     *
     * @return true, if is search by customer code
     */
    public boolean isSearchByCustomerCode() {
        return this.searchByCustomerCode;
    }

    /**
     * Sets the search by customer code.
     *
     * @param searchByCustomerCode the new search by customer code
     */
    public void setSearchByCustomerCode(final boolean searchByCustomerCode) {
        this.searchByCustomerCode = searchByCustomerCode;
    }

    /**
     * Gets the active index.
     *
     * @return the active index
     */
    public int getActiveIndex() {
        return this.activeIndex;
    }

    /**
     * Sets the active index.
     *
     * @param activeIndex the new active index
     */
    public void setActiveIndex(final int activeIndex) {
        this.activeIndex = activeIndex;
    }

    /**
     * Gets the company id int sansan map with acc.
     *
     * @return the company id int sansan map with acc
     */
    public String getCompanyIdIntSansanMapWithAcc() {
        return this.companyIdIntSansanMapWithAcc;
    }

    /**
     * Sets the company id int sansan map with acc.
     *
     * @param companyIdIntSansanMapWithAcc the new company id int sansan map with acc
     */
    public void setCompanyIdIntSansanMapWithAcc(final String companyIdIntSansanMapWithAcc) {
        this.companyIdIntSansanMapWithAcc = companyIdIntSansanMapWithAcc;
    }

    /**
     * Gets the display code acc status.
     *
     * @return the display code acc status
     */
    public String getDisplayCodeAccStatus() {
        return this.displayCodeAccStatus;
    }

    /**
     * Sets the display code acc status.
     *
     * @param displayCodeAccStatus the new display code acc status
     */
    public void setDisplayCodeAccStatus(final String displayCodeAccStatus) {
        this.displayCodeAccStatus = displayCodeAccStatus;
    }

    /**
     * Gets the display company name acc com.
     *
     * @return the display company name acc com
     */
    public String getDisplayCompanyNameAccCom() {
        return this.displayCompanyNameAccCom;
    }

    /**
     * Sets the display company name acc com.
     *
     * @param displayCompanyNameAccCom the new display company name acc com
     */
    public void setDisplayCompanyNameAccCom(final String displayCompanyNameAccCom) {
        this.displayCompanyNameAccCom = displayCompanyNameAccCom;
    }

    /**
     * Gets the display company name kana acc com.
     *
     * @return the display company name kana acc com
     */
    public String getDisplayCompanyNameKanaAccCom() {
        return this.displayCompanyNameKanaAccCom;
    }

    /**
     * Sets the display company name kana acc com.
     *
     * @param displayCompanyNameKanaAccCom the new display company name kana acc com
     */
    public void setDisplayCompanyNameKanaAccCom(final String displayCompanyNameKanaAccCom) {
        this.displayCompanyNameKanaAccCom = displayCompanyNameKanaAccCom;
    }

    /**
     * Gets the display year start acc com.
     *
     * @return the display year start acc com
     */
    public String getDisplayYearStartAccCom() {
        return this.displayYearStartAccCom;
    }

    /**
     * Sets the display year start acc com.
     *
     * @param displayYearStartAccCom the new display year start acc com
     */
    public void setDisplayYearStartAccCom(final String displayYearStartAccCom) {
        this.displayYearStartAccCom = displayYearStartAccCom;
    }

    /**
     * Gets the display year est acc com.
     *
     * @return the display year est acc com
     */
    public String getDisplayYearEstAccCom() {
        return this.displayYearEstAccCom;
    }

    /**
     * Sets the display year est acc com.
     *
     * @param displayYearEstAccCom the new display year est acc com
     */
    public void setDisplayYearEstAccCom(final String displayYearEstAccCom) {
        this.displayYearEstAccCom = displayYearEstAccCom;
    }


}
