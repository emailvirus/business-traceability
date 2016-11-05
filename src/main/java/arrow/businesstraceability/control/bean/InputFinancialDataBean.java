package arrow.businesstraceability.control.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.context.RequestContext;

import arrow.businesstraceability.constant.AccConstants;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.InputFinancialDataService;
import arrow.businesstraceability.persistence.entity.Acc_com_credit;
import arrow.businesstraceability.persistence.entity.Acc_com_entity;
import arrow.businesstraceability.persistence.entity.Acc_com_finance;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.util.collections.CollectionUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class InputAccDataBean.
 */
@Named
@ViewScoped
public class InputFinancialDataBean extends AbstractBean {

    /** The financial data service. */
    @Inject
    private InputFinancialDataService service;

    /** The list finance. */
    private List<Acc_com_finance> listFinance;

    /** The selected finance. */
    private Acc_com_finance selectedFinance;

    /** The map new finance. */
    private Map<Integer, Acc_com_finance> mapNewFinance;

    /** The fake id. */
    private AtomicInteger fakeId;

    /** The edit mode. */
    private boolean editMode;

    /** The map edited finance. */
    private Map<Integer, Acc_com_finance> mapEditedFinance;

    /** The map delete finance. */
    private Map<Integer, Acc_com_finance> mapDeleteFinance;

    /** The view mode. */
    private Boolean viewMode;

    /** The current id com entity. */
    private Integer currentIdComEntity;

    /** The current com entity. */
    private Acc_com_entity currentComEntity;


    /**
     * Inits the data when change screen.
     *
     * @param comEntity the com entity
     * @param credit the credit
     * @param viewMode the view mode
     */
    public void initDataWhenChangeScreen(final Acc_com_entity comEntity, final Acc_com_credit credit,
        final Boolean viewMode) {
        this.viewMode = viewMode;
        this.currentComEntity = comEntity;

        if (!credit.isPersisted()) {
            this.listFinance = new ArrayList<Acc_com_finance>();
        } else {
            this.listFinance = this.service.getListFinanceByIdCredit(credit.getId_credit());

        }

        this.mapNewFinance = new HashMap<Integer, Acc_com_finance>();
        this.mapEditedFinance = new HashMap<Integer, Acc_com_finance>();
        this.mapDeleteFinance = new HashMap<Integer, Acc_com_finance>();
    }

    /**
     * Adds the new finance.
     */
    public void addNewFinance() {
        Acc_com_finance newFinance = new Acc_com_finance(this.getNextFakeId());
        this.listFinance.add(newFinance);
        if ((this.selectedFinance != null) && !this.selectedFinance.isPersisted()
            && !this.mapNewFinance.containsValue(this.selectedFinance)) {
            this.listFinance.remove(this.selectedFinance);
        }
        this.selectedFinance = newFinance;
        this.triggerEditRow();
    }

    /**
     * Gets the next fake id.
     *
     * @return the next fake id
     */
    private int getNextFakeId() {
        if (this.fakeId == null) {
            final int maxId =
                CollectionUtils.isNotEmpty(this.listFinance) ? this.listFinance.get(this.listFinance.size() - 1)
                    .getId_finance() : 0;
            this.fakeId = new AtomicInteger(maxId + 1);
        }

        return this.fakeId.getAndIncrement();
    }

    /**
     * Delete finance.
     */
    public void deleteFinance() {
        if (this.getSelectedFinance().isPersisted()) {
            this.mapEditedFinance.remove(this.selectedFinance.getId_finance());
            this.mapDeleteFinance.put(this.selectedFinance.getId_finance(), this.selectedFinance);
        } else {
            this.mapNewFinance.remove(this.selectedFinance.getId_finance());
        }
        this.listFinance.remove(this.getSelectedFinance());
        this.selectedFinance = null;
        this.editMode = false;
        InfoMessage.proc4_003_delete_financial_data_successfully().show();
    }

    /**
     * Trigger edit row.
     */
    public void triggerEditRow() {
        if (CollectionUtils.isNotEmpty(this.listFinance) && (this.selectedFinance != null)) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("triggerRowEdit()");
            this.editMode = true;
        }
    }

    /**
     * Save to map edited.
     *
     */
    public void saveToMapEdited() {
        this.editMode = false;
        if (this.selectedFinance.isPersisted()) {
            this.mapEditedFinance.put(this.selectedFinance.getId_finance(), this.selectedFinance);
        } else {
            this.mapNewFinance.put(this.selectedFinance.getId_finance(), this.selectedFinance);
            if (!this.listFinance.contains(this.selectedFinance)) {
                this.listFinance.add(this.selectedFinance);
            }
        }
    }

    /**
     * Trigger cancel edit row.
     */
    public void triggerCancelEditRow() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("triggerCancelEditRow()");
        this.editMode = false;
    }

    /**
     * Checks if is row selected.
     *
     * @param finance the finance
     * @return true, if is row selected
     */
    public boolean isRowSelected(final Acc_com_finance finance) {
        return (this.selectedFinance != null) && (finance != null) && (this.selectedFinance == finance);
    }

    /**
     * Gets the list finance statement.
     *
     * @return the list finance statement
     */
    public List<SelectItem> getListFinanceStatement() {
        return AccConstants.getMapFinanceStatement();
    }

    /**
     * Convert finance statement.
     *
     * @param financeStatement the finance statement
     * @return the string
     */
    public String convertFinanceStatement(final Short financeStatement) {
        if (financeStatement == null) {
            return AccConstants.AccComFinance.UNKNOWN_STATEMENT;
        }
        switch (financeStatement) {
            case AccConstants.AccComFinance.YES_VALUE:
                return AccConstants.AccComFinance.YES_STATEMENT;
            case AccConstants.AccComFinance.NO_VALUE:
                return AccConstants.AccComFinance.NO_STATEMENT;
            default:
                return AccConstants.AccComFinance.UNKNOWN_STATEMENT;
        }
    }

    /**
     * Save finance to db.
     *
     * @param idComEntity the id com entity
     * @param credit the credit
     */
    public void saveFinanceToDB(final int idComEntity, final Acc_com_credit credit) {
        this.saveEditedFinanceToDb(idComEntity, credit);
        this.saveNewFinanceToDB(idComEntity, credit);
    }

    /**
     * Reset data.
     */
    public void resetData() {
        this.listFinance = null;
        this.mapDeleteFinance = null;
        this.mapEditedFinance = null;
        this.mapNewFinance = null;
        this.editMode = false;
        this.currentComEntity = null;
        this.currentIdComEntity = null;
        this.fakeId = null;
        this.viewMode = null;
        this.selectedFinance = null;
    }

    /**
     * Save new finance to db.
     *
     * @param idComEntity the id com entity
     * @param credit the credit
     * @return the service result
     */
    private ServiceResult<Acc_com_finance> saveNewFinanceToDB(final int idComEntity, final Acc_com_credit credit) {
        return this.service.saveMapFinanceToDb(this.mapNewFinance, idComEntity, credit);
    }

    /**
     * Save edited finance to db.
     *
     * @param idComEntity the id com entity
     * @param credit the credit
     * @return the service result
     */
    private ServiceResult<Acc_com_finance> saveEditedFinanceToDb(final int idComEntity, final Acc_com_credit credit) {
        return this.service.saveMapFinanceToDb(this.mapEditedFinance, idComEntity, credit);
    }

    /**
     * Delete acc data in db.
     *
     * @return the service result
     */
    public ServiceResult<Acc_com_finance> deleteFinanceInDB() {
        return this.service.deleteMapFinance(this.mapDeleteFinance);
    }

    /**
     * Gets the list finance.
     *
     * @return the list finance
     */
    public List<Acc_com_finance> getListFinance() {
        return this.listFinance;
    }

    /**
     * Sets the list finance.
     *
     * @param listFinance the new list finance
     */
    public void setListFinance(final List<Acc_com_finance> listFinance) {
        this.listFinance = listFinance;
    }

    /**
     * Gets the map new finance.
     *
     * @return the map new finance
     */
    public Map<Integer, Acc_com_finance> getMapNewFinance() {
        return this.mapNewFinance;
    }

    /**
     * Sets the map new finance.
     *
     * @param mapNewFinance the map new finance
     */
    public void setMapNewFinance(final Map<Integer, Acc_com_finance> mapNewFinance) {
        this.mapNewFinance = mapNewFinance;
    }

    /**
     * Gets the selected finance.
     *
     * @return the selected finance
     */
    public Acc_com_finance getSelectedFinance() {
        return this.selectedFinance;
    }

    /**
     * Sets the selected finance.
     *
     * @param selectedFinance the new selected finance
     */
    public void setSelectedFinance(final Acc_com_finance selectedFinance) {
        if ((this.selectedFinance != null) && !this.selectedFinance.isPersisted()
            && !this.mapNewFinance.containsValue(this.selectedFinance)) {
            this.listFinance.remove(this.selectedFinance);
        }
        this.selectedFinance = selectedFinance;
    }

    /**
     * Checks if is edits the mode.
     *
     * @return true, if is edits the mode
     */
    public boolean isEditMode() {
        return this.editMode;
    }

    /**
     * Sets the edits the mode.
     *
     * @param editMode the new edits the mode
     */
    public void setEditMode(final boolean editMode) {
        this.editMode = editMode;
    }

    /**
     * Gets the map edited finance.
     *
     * @return the map edited finance
     */
    public Map<Integer, Acc_com_finance> getMapEditedFinance() {
        return this.mapEditedFinance;
    }

    /**
     * Sets the map edited finance.
     *
     * @param mapEditedFinance the map edited finance
     */
    public void setMapEditedFinance(final Map<Integer, Acc_com_finance> mapEditedFinance) {
        this.mapEditedFinance = mapEditedFinance;
    }

    /**
     * Gets the map delete finance.
     *
     * @return the map delete finance
     */
    public Map<Integer, Acc_com_finance> getMapDeleteFinance() {
        return this.mapDeleteFinance;
    }

    /**
     * Sets the map delete finance.
     *
     * @param mapDeleteFinance the map delete finance
     */
    public void setMapDeleteFinance(final Map<Integer, Acc_com_finance> mapDeleteFinance) {
        this.mapDeleteFinance = mapDeleteFinance;
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
     * Gets the current id com entity.
     *
     * @return the current id com entity
     */
    public Integer getCurrentIdComEntity() {
        return this.currentIdComEntity;
    }

    /**
     * Sets the current id com entity.
     *
     * @param currentIdComEntity the new current id com entity
     */
    public void setCurrentIdComEntity(final Integer currentIdComEntity) {
        this.currentIdComEntity = currentIdComEntity;
    }

    /**
     * Gets the current com entity.
     *
     * @return the current com entity
     */
    public Acc_com_entity getCurrentComEntity() {
        return this.currentComEntity;
    }

    /**
     * Sets the current com entity.
     *
     * @param currentComEntity the new current com entity
     */
    public void setCurrentComEntity(final Acc_com_entity currentComEntity) {
        this.currentComEntity = currentComEntity;
    }

    /** The list storage selected finance. */
    private List<Acc_com_finance> listStorageSelectedFinance;

    /**
     * Storage selected data with current credit.
     */
    public void storageSelectedDataWithCurrentCredit() {
        this.listStorageSelectedFinance = this.listFinance;
    }

    /**
     * Gets the list storage selected finance.
     *
     * @return the list storage selected finance
     */
    public List<Acc_com_finance> getListStorageSelectedFinance() {
        return this.listStorageSelectedFinance;
    }

    /**
     * Sets the list storage selected finance.
     *
     * @param listStorageSelectedFinance the new list storage selected finance
     */
    public void setListStorageSelectedFinance(final List<Acc_com_finance> listStorageSelectedFinance) {
        this.listStorageSelectedFinance = listStorageSelectedFinance;
    }

    /**
     * Copy storage to new data.
     */
    public void copyStorageToNewData() {
        for (Acc_com_finance finance : this.listStorageSelectedFinance) {
            Acc_com_finance newFinance = new Acc_com_finance(this.getNextFakeId());
            BeanCopier.copy(finance, newFinance, "id_finance", "id_credit");
            this.mapNewFinance.put(newFinance.getId_finance(), newFinance);
            this.listFinance.add(newFinance);
        }
    }
}
