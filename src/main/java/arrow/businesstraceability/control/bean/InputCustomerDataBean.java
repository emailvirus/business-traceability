package arrow.businesstraceability.control.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.omnifaces.cdi.ViewScoped;

import arrow.businesstraceability.cache.entity.InputCustomerDataEntity;
import arrow.businesstraceability.control.helper.ScreenContext;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.InputCustomerDataService;
import arrow.businesstraceability.persistence.entity.Acc_com_credit;
import arrow.businesstraceability.persistence.entity.Acc_com_relation;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.util.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class InputCustomerDataBean.
 */
@Named
@ViewScoped
public class InputCustomerDataBean extends AbstractBean {


    /** The input customer data service. */
    @Inject
    private InputCustomerDataService inputCustomerDataService;

    /** The current faces context. */
    @Inject
    private FacesContext currentFacesContext;


    /** The input related company. */
    private String inputRelatedCompany;

    /** The option. */
    private String option;

    /** The view mode. */
    private Boolean viewMode;

    /** The is disable cancel edit. */
    private Boolean isDisableCancelEdit = true;

    /** The list customer. */
    private List<InputCustomerDataEntity> listCustomer;

    /** The selected customer. */
    private InputCustomerDataEntity selectedCustomer;

    /** The Constant DRAFT. */
    public static final Character DRAFT = 'D';

    /** The Constant SAVE. */
    public static final Character SAVE = 'S';

    /** The Constant SPILIT_NAME. */
    public static final String SPILIT_NAME = ",";

    /** The Constant CUSTOMER. */
    public static final String CUSTOMER = "C";

    /** The Constant RELATED_COMPANIES. */
    public static final String RELATED_COMPANIES = "R";

    /** The Constant PROVIDER. */
    public static final String PROVIDER = "P";

    /** The Constant LIMIT_LENGTH_COMPANY_NAME. */
    public static final int LIMIT_LENGTH_COMPANY_NAME = 60;

    /** The acc_com_credit. */
    private Acc_com_credit acc_com_credit;

    /** The list add new. */
    private List<InputCustomerDataEntity> listAddNew;

    /** The list cancel edit. */
    private List<InputCustomerDataEntity> listCancelEdit;

    /** The list id delete. */
    private List<Integer> listIdDelete;

    /** The list id edit. */
    private List<Integer> listIdEdit;

    /**
     * Inits the.
     */
    public void init() {
        this.viewMode =
            (Boolean) this.screenBean.getCurrentScreenContext().getObjectAttributes()
                .get(ScreenContext.SharingDataKey.VIEW_MODE);
        Acc_com_credit credit =
            (Acc_com_credit) this.screenBean.getCurrentScreenContext().getObjectAttributes()
                .get(ScreenContext.SharingDataKey.CREDIT_ENTITY);
        this.generateCompanyFromInputDataProcess3(credit);
        this.setOption(InputCustomerDataBean.CUSTOMER);
    }

    /**
     * Generate company from input.
     */
    public void generateCompanyFromInput() {
        if (StringUtils.isNotEmpty(this.inputRelatedCompany)) {
            String[] listCompany = this.inputRelatedCompany.split(InputCustomerDataBean.SPILIT_NAME);
            List<InputCustomerDataEntity> listDuplicatedItems = new ArrayList<>();
            for (int i = 0; i < listCompany.length; i++) {
                String cmpName = listCompany[i].trim();
                if (StringUtils.isNotEmpty(cmpName)) {
                    if (cmpName.length() > InputCustomerDataBean.LIMIT_LENGTH_COMPANY_NAME) {
                        cmpName = cmpName.substring(0, InputCustomerDataBean.LIMIT_LENGTH_COMPANY_NAME);
                    }
                    InputCustomerDataEntity temp = new InputCustomerDataEntity();
                    temp.setName_com_relation(cmpName);

                    temp.setEnum_com_relation(this.getOption().toCharArray()[0]);
                    if (!this.listCustomer.contains(temp)) {
                        this.listCustomer.add(temp);
                        this.listAddNew.add(temp);
                    } else {
                        listDuplicatedItems.add(temp);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(listDuplicatedItems)) {
                List<String> duplicatedNames =
                    listDuplicatedItems.stream().map(item -> item.getName_com_relation()).collect(Collectors.toList());
                Message info =
                    InfoMessage.proc5_003_duplicated_item(String.join(",", duplicatedNames.toArray(new String[] {})));
                info.show();
            }
            this.setIsDisableCancelEdit(true);
            this.inputRelatedCompany = StringUtils.EMPTY_STRING;
        }
    }

    /**
     * Generate company from input data process3.
     *
     * @param entity the entity
     */
    public void generateCompanyFromInputDataProcess3(final Acc_com_credit entity) {
        this.listCustomer = new ArrayList<InputCustomerDataEntity>();
        this.listIdDelete = new ArrayList<Integer>();
        this.listIdEdit = new ArrayList<Integer>();
        this.listAddNew = new ArrayList<InputCustomerDataEntity>();
        this.acc_com_credit = entity;
        if (!entity.isPersisted()) {
            return;
        }
        List<Acc_com_relation> listAccComRelation =
            this.inputCustomerDataService.getListAccComRelationByIdComEntityAndIdCredit(entity.getId_com_entity(),
                entity.getId_credit());
        if (CollectionUtils.isNotEmpty(listAccComRelation)) {
            for (int i = 0; i < listAccComRelation.size(); i++) {
                Acc_com_relation tempAccComRelation = listAccComRelation.get(i);
                this.listCustomer.add(this.inputCustomerDataService
                    .createInputCustomerDataEntityByAccComRelation(tempAccComRelation));
            }
        }
    }

    /**
     * Save.
     *
     * @param credit the credit
     */
    public void saveAccComRelation(final Acc_com_credit credit) {
        for (int i = 0; i < this.listIdDelete.size(); i++) {
            this.inputCustomerDataService.removeAccComRelationByIdComRelation(this.listIdDelete.get(i));
        }

        if (CollectionUtils.isNotEmpty(this.listAddNew)) {
            if (this.listAddNew.size() > this.listIdEdit.size()) {
                for (int i = 0; i < this.listIdEdit.size(); i++) {
                    InputCustomerDataEntity currentInputCustomerDataEntity = this.listAddNew.get(i);
                    Acc_com_relation tempAccComRelation =
                        this.inputCustomerDataService.getAccComRelationByPk(this.listIdEdit.get(i));
                    tempAccComRelation =
                        this.inputCustomerDataService.updateAccComRelationByAccComCreditAndInputCustomerDataEntity(
                            tempAccComRelation, credit, currentInputCustomerDataEntity);
                    this.inputCustomerDataService.saveAccComRelation(tempAccComRelation);
                }
                for (int i = this.listIdEdit.size(); i < this.listAddNew.size(); i++) {
                    InputCustomerDataEntity currentInputCustomerDataEntity = this.listAddNew.get(i);
                    Acc_com_relation tempAccComRelation =
                        new Acc_com_relation(this.inputCustomerDataService.genNextIdComRelation());
                    tempAccComRelation =
                        this.inputCustomerDataService.updateAccComRelationByAccComCreditAndInputCustomerDataEntity(
                            tempAccComRelation, credit, currentInputCustomerDataEntity);
                    this.inputCustomerDataService.saveAccComRelation(tempAccComRelation);
                }

            } else {
                for (int i = 0; i < this.listAddNew.size(); i++) {
                    InputCustomerDataEntity currentInputCustomerDataEntity = this.listAddNew.get(i);
                    Acc_com_relation tempAccComRelation =
                        this.inputCustomerDataService.getAccComRelationByPk(this.listIdEdit.get(i));
                    tempAccComRelation =
                        this.inputCustomerDataService.updateAccComRelationByAccComCreditAndInputCustomerDataEntity(
                            tempAccComRelation, credit, currentInputCustomerDataEntity);
                    this.inputCustomerDataService.saveAccComRelation(tempAccComRelation);
                }
                for (int i = this.listAddNew.size(); i < this.listIdEdit.size(); i++) {
                    this.inputCustomerDataService.removeAccComRelationByIdComRelation(this.listIdEdit.get(i));
                }
            }

        }
    }


    /**
     * Reset acc com relation.
     */
    public void resetAccComRelation() {
        this.acc_com_credit = null;
        this.listCustomer = null;
        this.selectedCustomer = null;
        this.option = null;
        this.listAddNew = null;
        this.listIdDelete = null;
        this.listIdEdit = null;
        this.inputRelatedCompany = null;
    }

    /**
     * Edits the record.
     */
    public void editRecord() {
        this.listCancelEdit = new ArrayList<InputCustomerDataEntity>();
        char selectedClassify = this.selectedCustomer.getEnum_com_relation();
        this.setOption(String.valueOf(selectedClassify));
        this.selectedCustomer.setSelected(false);
        StringBuffer sb = new StringBuffer();
        Iterator<InputCustomerDataEntity> itr = this.listCustomer.iterator();
        while (itr.hasNext()) {
            InputCustomerDataEntity element = itr.next();
            if ((element.getEnum_com_relation() == selectedClassify)) {
                sb.append(element.getName_com_relation());
                sb.append(InputCustomerDataBean.SPILIT_NAME);
                if (element.getIdComRelation() != 0) {
                    this.listIdEdit.add(element.getIdComRelation());
                } else {
                    this.listAddNew.remove(element);
                }
                this.listCancelEdit.add(element);
                itr.remove();
            }
        }
        this.selectedCustomer = null;
        if (StringUtils.isNotEmpty(sb.toString())) {
            this.inputRelatedCompany = sb.substring(0, sb.length() - 1);
        }
        this.setIsDisableCancelEdit(false);
    }

    /**
     * Cancel edit.
     */
    public void cancelEdit() {
        this.listCustomer.addAll(this.listCancelEdit);
        for (int i = 0; i < this.listCancelEdit.size(); i++) {
            InputCustomerDataEntity current = this.listCancelEdit.get(i);
            if (current.getIdComRelation() != 0) {
                this.listIdEdit.remove(Integer.valueOf(current.getIdComRelation()));
            } else {
                this.listAddNew.add(current);
            }
        }
        this.setIsDisableCancelEdit(true);
        this.inputRelatedCompany = StringUtils.EMPTY_STRING;
    }

    /**
     * Checks if is disable select column.
     *
     * @return the boolean
     */
    public Boolean isDisableSelectColumn() {
        return this.viewMode || !this.isDisableCancelEdit;
    }

    /**
     * Delete record.
     */
    public void deleteRecord() {
        List<Message> messages = new ArrayList<Message>();
        this.listCustomer.remove(this.selectedCustomer);
        this.selectedCustomer.setSelected(false);
        if (this.selectedCustomer.getIdComRelation() != 0) {
            this.listIdDelete.add(this.selectedCustomer.getIdComRelation());
        } else {
            this.listAddNew.remove(this.selectedCustomer);
        }
        if (this.listIdEdit.contains(this.selectedCustomer.getIdComRelation())) {
            this.listIdEdit.remove(this.selectedCustomer.getIdComRelation());
        }

        messages.add(InfoMessage.proc5_001_delete_successfully());
        ServiceResult<T> result = new ServiceResult<>(true, messages);
        result.showMessages(this.currentFacesContext);

        this.selectedCustomer = null;
    }

    /**
     * Toggle selection.
     *
     * @param customer the customer
     */
    public void toggleSelection(final InputCustomerDataEntity customer) {
        if (customer.isSelected()) {
            if (this.selectedCustomer != null) {
                this.selectedCustomer.setSelected(false);
            }
            this.selectedCustomer = customer;
        } else {
            this.selectedCustomer = null;
        }
    }

    /**
     * Checks if is disable edit and delete.
     *
     * @return true, if is disable edit and delete
     */
    public boolean isDisableEditAndDelete() {
        return (this.selectedCustomer == null) || this.viewMode;
    }

    /**
     * get list card in table.
     *
     * @return the list customer
     */
    public List<InputCustomerDataEntity> getListCustomer() {
        return this.listCustomer;
    }

    /**
     * Gets the value customer.
     *
     * @return the value customer
     */
    public String getValueCustomer() {
        return InputCustomerDataBean.CUSTOMER;

    }

    /**
     * Gets the value related companies.
     *
     * @return the value related companies
     */
    public String getValueRelatedCompanies() {
        return InputCustomerDataBean.RELATED_COMPANIES;

    }

    /**
     * Gets the value provider.
     *
     * @return the value provider
     */
    public String getValueProvider() {
        return InputCustomerDataBean.PROVIDER;

    }

    /**
     * Disable button.
     *
     * @return true, if successful
     */
    public boolean disableButton() {
        return (StringUtils.isEmpty(this.inputRelatedCompany) || this.viewMode);
    }

    /**
     * Gets the input related company.
     *
     * @return the input related company
     */
    public String getInputRelatedCompany() {
        return this.inputRelatedCompany;
    }

    /**
     * Sets the input related company.
     *
     * @param inputRelatedCompany the new input related company
     */
    public void setInputRelatedCompany(final String inputRelatedCompany) {
        this.inputRelatedCompany = inputRelatedCompany;
    }


    /**
     * Gets the option.
     *
     * @return the option
     */
    public String getOption() {
        return this.option;
    }


    /**
     * Sets the option.
     *
     * @param option the new option
     */
    public void setOption(final String option) {
        this.option = option;
    }

    /**
     * Gets the view mode.
     *
     * @return the view mode
     */
    public Boolean getViewMode() {
        return this.viewMode;
    }

    /**
     * Sets the view mode.
     *
     * @param viewMode the new view mode
     */
    public void setViewMode(final Boolean viewMode) {
        this.viewMode = viewMode;
    }

    /**
     * Gets the checks if is disable cancel edit.
     *
     * @return the checks if is disable cancel edit
     */
    public Boolean getIsDisableCancelEdit() {
        return this.isDisableCancelEdit;
    }

    /**
     * Sets the checks if is disable cancel edit.
     *
     * @param isDisableCancelEdit the new checks if is disable cancel edit
     */
    public void setIsDisableCancelEdit(final Boolean isDisableCancelEdit) {
        this.isDisableCancelEdit = isDisableCancelEdit;
    }

    /** The list storage customer data. */
    private List<InputCustomerDataEntity> listStorageCustomerData;

    /**
     * Storage selected data with current credit.
     */
    public void storageSelectedDataWithCurrentCredit() {
        this.listStorageCustomerData = this.listCustomer;
    }

    /**
     * Gets the list storage customer data.
     *
     * @return the list storage customer data
     */
    public List<InputCustomerDataEntity> getListStorageCustomerData() {
        return this.listStorageCustomerData;
    }

    /**
     * Sets the list storage customer data.
     *
     * @param listStorageCustomerData the new list storage customer data
     */
    public void setListStorageCustomerData(final List<InputCustomerDataEntity> listStorageCustomerData) {
        this.listStorageCustomerData = listStorageCustomerData;
    }

    /**
     * Copy storage to new data.
     */
    public void copyStorageToNewData() {
        for (InputCustomerDataEntity cust : this.listStorageCustomerData) {
            InputCustomerDataEntity newCust = new InputCustomerDataEntity();
            BeanCopier.copy(cust, newCust, "id_com_relation", "id_credit");
            this.listCustomer.add(newCust);
            this.listAddNew.add(newCust);
        }
    }

}
