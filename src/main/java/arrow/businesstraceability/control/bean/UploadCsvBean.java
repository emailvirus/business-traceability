package arrow.businesstraceability.control.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.FileUploadEvent;

import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.control.service.UploadCsvService;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.util.LabelKeySelectItem;

/**
 * The Class UploadCsvBean.
 */
@Named
@ViewScoped
public class UploadCsvBean extends AbstractBean {

    /** The Constant FROM_MONTH. */
    private static final int FROM_MONTH = 1;

    /** The Constant TO_MONTH. */
    private static final int TO_MONTH = 12;

    /** The service. */
    @Inject
    private UploadCsvService service;

    /** The list months. */
    private List<SelectItem> listMonths;

    /** The selected month. */
    private String selectedMonth;

    /**
     * Gets the file allow type.
     *
     * @return the file allow type
     */
    public String getFileAllowType() {
        return Constants.CSV_FILE_ALLOW_TYPE;
    }

    /**
     * Gets the file size limit.
     *
     * @return the file size limit
     */
    public int getFileSizeLimit() {
        return Constants.CSV_FILE_SIZE_LIMIT;
    }

    /**
     * Upload file listener.
     *
     * @param event the event
     */
    public void uploadFileListener(final FileUploadEvent event) {
        this.service.uploadCsvFile(event.getFile(), this.getSelectedMonth());
    }

    /**
     * Gets the list months.
     *
     * @return the list months
     */
    public List<SelectItem> getListMonths() {
        this.listMonths = new ArrayList<SelectItem>();
        for (int i = UploadCsvBean.FROM_MONTH; i <= UploadCsvBean.TO_MONTH; i++) {
            this.listMonths.add(new LabelKeySelectItem(String.valueOf(i), String.valueOf(i)));
        }
        return this.listMonths;
    }

    /**
     * Sets the list months.
     *
     * @param listMonths the new list months
     */
    public void setListMonths(final List<SelectItem> listMonths) {
        this.listMonths = listMonths;
    }

    /**
     * Gets the selected month.
     *
     * @return the selected month
     */
    public String getSelectedMonth() {
        if (StringUtils.isEmpty(this.selectedMonth)) {
            this.selectedMonth = this.getListMonths().get(0).getValue().toString();
        }
        return this.selectedMonth;
    }

    /**
     * Sets the selected month.
     *
     * @param selectedMonth the new selected month
     */
    public void setSelectedMonth(final String selectedMonth) {
        this.selectedMonth = selectedMonth;
    }
}
