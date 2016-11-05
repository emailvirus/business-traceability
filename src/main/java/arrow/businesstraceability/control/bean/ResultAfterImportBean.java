package arrow.businesstraceability.control.bean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.fasterxml.jackson.core.JsonProcessingException;

import arrow.businesstraceability.constant.SansanConstants;
import arrow.businesstraceability.control.service.SansanService;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.message.InfoMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class DebugBean.
 */
@Named
@ViewScoped
public class ResultAfterImportBean extends AbstractBean {

    /** The Constant tables. */
    private static final String[] TABLES;

    /** The table selected. */
    private String tableSelected;

    /** The id san company. */
    private String idSanCompany;

    /** The id san card data. */
    private String idSanCardData;

    /** The is render id comp. */
    private boolean isRenderIdComp;

    /** The is render id card. */
    private boolean isRenderIdCard;

    /** The is render count metadata. */
    private boolean isRenderCountMetadata;

    /** The data san table. */
    private String dataSanTable;

    /** The fields name. */
    private String fieldsName;

    /** The total record. */
    private long totalRecord;

    /** The map enum info. */
    private Map<String, Long> mapEnumInfo;

    /** The map enum proc. */
    private Map<String, Long> mapEnumProc;

    /** The map company metadata. */
    private Map<String, Long> mapCompanyMetadata;

    /** The sansan service. */
    @Inject
    private SansanService sansanService;

    static {
        TABLES = new String[] {"san_card_data", "san_card_tag", "san_com_info", "san_com_branch", "san_com_cnumber",
            "san_com_live_client", "san_com_live_owner", "san_com_live_stat", "san_com_mdomain", "san_com_site",
            "san_com_udomain", "san_com_whole_card", "san_idmap_card", "san_idmap_company"};
    }

    /**
     * After select table.
     */
    public void afterSelectTable() {
        this.resetData();
        if (this.tableSelected == null) {
            return;
        }
        if (this.tableSelected.equals(SansanConstants.TableName.SAN_CARD_DATA)
            || this.tableSelected.equals(SansanConstants.TableName.SAN_CARD_TAG)
            || this.tableSelected.equals(SansanConstants.TableName.SAN_IDMAP_CARD)) {
            this.setRenderIdCard(true);
        } else if (this.tableSelected.equals(SansanConstants.TableName.SAN_COM_WHOLE_CARD)) {
            this.setRenderIdComp(true);
            this.setRenderIdCard(true);
        } else {
            this.setRenderIdComp(true);
        }
    }

    /**
     * Reset data.
     */
    private void resetData() {
        this.setRenderIdComp(false);
        this.setRenderIdCard(false);
        this.isRenderCountMetadata = false;
        this.dataSanTable = null;
        this.fieldsName = null;
    }

    /**
     * Find data san table by.
     *
     * @throws ClassNotFoundException the class not found exception
     * @throws JsonProcessingException the json processing exception
     */
    public void findDataSanTableBy() throws ClassNotFoundException, JsonProcessingException {
        this.setDataSanTable(
            this.sansanService.getDataSanTable(this.tableSelected, this.idSanCompany, this.idSanCardData));
        if (this.dataSanTable == null) {
            InfoMessage.sansan_verify_013_no_result().show();
        }
    }

    /**
     * Count metadata.
     *
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException the illegal access exception
     */
    public void countMetadata() throws IllegalArgumentException, IllegalAccessException {
        this.isRenderCountMetadata = true;
        this.totalRecord = this.sansanService.getQuantityTotalRecordDataSanTable(this.tableSelected);
        if (this.tableSelected.equals(SansanConstants.TableName.SAN_CARD_DATA)) {
            this.mapEnumInfo = this.sansanService.getEnumInfoMetadata();
            this.setMapEnumProc(this.sansanService.getEnumProcMetadata());
        }
        if (this.tableSelected.equals(SansanConstants.TableName.SAN_COM_INFO)) {
            this.setMapCompanyMetadata(this.sansanService.getCompanyMetadata());
        }
    }

    /**
     * Gets the tables.
     *
     * @return the tables
     */
    public List<String> getTables() {
        return Arrays.asList(ResultAfterImportBean.TABLES);
    }

    /**
     * Gets the table selected.
     *
     * @return the table selected
     */
    public String getTableSelected() {
        return this.tableSelected;
    }

    /**
     * Sets the table selected.
     *
     * @param tableSelected the new table selected
     */
    public void setTableSelected(final String tableSelected) {
        this.tableSelected = tableSelected;
    }

    /**
     * Gets the id san company.
     *
     * @return the id san company
     */
    public String getIdSanCompany() {
        return this.idSanCompany;
    }

    /**
     * Sets the id san company.
     *
     * @param idSanCompany the new id san company
     */
    public void setIdSanCompany(final String idSanCompany) {
        this.idSanCompany = idSanCompany;
    }

    /**
     * Gets the id san card data.
     *
     * @return the id san card data
     */
    public String getIdSanCardData() {
        return this.idSanCardData;
    }

    /**
     * Sets the id san card data.
     *
     * @param idSanCardData the new id san card data
     */
    public void setIdSanCardData(final String idSanCardData) {
        this.idSanCardData = idSanCardData;
    }

    /**
     * Checks if is render id comp.
     *
     * @return true, if is render id comp
     */
    public boolean isRenderIdComp() {
        return this.isRenderIdComp;
    }

    /**
     * Sets the render id comp.
     *
     * @param isRenderIdComp the new render id comp
     */
    public void setRenderIdComp(final boolean isRenderIdComp) {
        this.isRenderIdComp = isRenderIdComp;
    }

    /**
     * Checks if is render id card.
     *
     * @return true, if is render id card
     */
    public boolean isRenderIdCard() {
        return this.isRenderIdCard;
    }

    /**
     * Sets the render id card.
     *
     * @param isRenderIdCard the new render id card
     */
    public void setRenderIdCard(final boolean isRenderIdCard) {
        this.isRenderIdCard = isRenderIdCard;
    }

    /**
     * Gets the fields name.
     *
     * @return the fields name
     * @throws ClassNotFoundException the class not found exception
     */
    public String getFieldsName() throws ClassNotFoundException {
        return this.sansanService.getFieldsNameOfClass(this.tableSelected);
    }

    /**
     * Sets the fields name.
     *
     * @param fieldsName the new fields name
     */
    public void setFieldsName(final String fieldsName) {
        this.fieldsName = fieldsName;
    }

    /**
     * Gets the data san table.
     *
     * @return the data san table
     */
    public String getDataSanTable() {
        return this.dataSanTable;
    }

    /**
     * Sets the data san table.
     *
     * @param dataSanTable the new data san table
     */
    public void setDataSanTable(final String dataSanTable) {
        this.dataSanTable = dataSanTable;
    }

    /**
     * Gets the total record.
     *
     * @return the total record
     */
    public long getTotalRecord() {
        return this.totalRecord;
    }

    /**
     * Sets the total record.
     *
     * @param totalRecord the new total record
     */
    public void setTotalRecord(final long totalRecord) {
        this.totalRecord = totalRecord;
    }

    /**
     * Gets the map enum info.
     *
     * @return the map enum info
     */
    public Map<String, Long> getMapEnumInfo() {
        return this.mapEnumInfo;
    }

    /**
     * Sets the map enum info.
     *
     * @param mapEnumInfo the map enum info
     */
    public void setMapEnumInfo(final Map<String, Long> mapEnumInfo) {
        this.mapEnumInfo = mapEnumInfo;
    }

    /**
     * Gets the map enum proc.
     *
     * @return the map enum proc
     */
    public Map<String, Long> getMapEnumProc() {
        return this.mapEnumProc;
    }

    /**
     * Sets the map enum proc.
     *
     * @param mapEnumProc the map enum proc
     */
    public void setMapEnumProc(final Map<String, Long> mapEnumProc) {
        this.mapEnumProc = mapEnumProc;
    }

    /**
     * Gets the map company metadata.
     *
     * @return the map company metadata
     */
    public Map<String, Long> getMapCompanyMetadata() {
        return this.mapCompanyMetadata;
    }

    /**
     * Sets the map company metadata.
     *
     * @param mapCompanyMetadata the map company metadata
     */
    public void setMapCompanyMetadata(final Map<String, Long> mapCompanyMetadata) {
        this.mapCompanyMetadata = mapCompanyMetadata;
    }

    /**
     * Checks if is render count metadata.
     *
     * @return true, if is render count metadata
     */
    public boolean isRenderCountMetadata() {
        return this.isRenderCountMetadata;
    }

    /**
     * Sets the render count metadata.
     *
     * @param isRenderCountMetadata the new render count metadata
     */
    public void setRenderCountMetadata(final boolean isRenderCountMetadata) {
        this.isRenderCountMetadata = isRenderCountMetadata;
    }


}
