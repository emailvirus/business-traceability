package arrow.businesstraceability.control.bean;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.omnifaces.cdi.ViewScoped;

import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.constant.ElasticSearchConstants;
import arrow.businesstraceability.control.helper.DataChangeInfo;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.CompanyManagementService;
import arrow.businesstraceability.control.service.DailyReportService;
import arrow.businesstraceability.control.service.ElasticSearchService;
import arrow.businesstraceability.debug.DBScan;
import arrow.businesstraceability.debug.ListCluster;
import arrow.businesstraceability.debug.Point;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Customer_info_view;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.util.TransformUtils;
import arrow.framework.bean.AbstractBean;
import arrow.framework.util.DateUtils;
import arrow.framework.util.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class DebugBean.
 */
@Named
@ViewScoped
public class DebugBeanCallSarsBatch extends AbstractBean {

    /** The Constant filePathRun. */
    private static final String FILE_PATH_RUN = "/Volumes/data/hongluu/projects/sars_batch/target/sars-batch.jar";

    /** The Constant filePathImport. */
    private static final String FILE_PATH_IMPORT = "/tmp/100.csv";

    private static String FILE_PATH_FOLDER = "/Volumes/data/hongluu/projects/sars_batch/target";

    /** The folder backup. */
    private static final String FOLDER_BACKUP = "/Users/HongLM/Desktop/sars_batch/back_up/";

    /** The to time reg. */
    private Date toTimeReg;

    /** The from time reg. */
    private Date fromTimeReg;

    /** The date reg. */
    private Date dateReg;

    /** The el service. */
    @Inject
    private ElasticSearchService elService;

    /** The com service. */
    @Inject
    private CompanyManagementService comService;

    /** The daily service. */
    @Inject
    DailyReportService dailyService;

    /** The selected company mst. */
    private Company_mst_suggest selectedCompanyMst;

    /** The selected customer. */
    private Customer_info_view selectedCustomer;

    /** The list company suggestion. */
    private List<Company_mst_suggest> listCompanySuggestion;

    /** The item select. */
    private String itemSelect;

    /** The db scan output. */
    private String dbScanOutput;

    /** The addrespoint. */
    private Addresspoint_mst addrespoint;

    /** The employee. */
    private Employee_mst employee;

    /**
     * test.
     */
    public void pushAll() {

        try {
            this.elService.pushAllData();

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Db scan.
     *
     * @throws UnknownHostException the unknown host exception
     */
    public void dbScan() throws UnknownHostException {
        DBScan dbScan = new DBScan();
        List<Point> listPoint = this.prepareData();
        List<ListCluster> listCluster = dbScan.dbScan(listPoint, 2, 2, 5);
        this.log.debug("=================== Result ==============\n");
        this.log.debugf("Number of Cluster: %d", listCluster.size());
        this.log.debug("=========================================\n");

        StringBuilder output = new StringBuilder();
        output.append("=================== Result ==============\n");
        output.append(String.format("Number of Cluster: %d", listCluster.size()));
        output.append("=========================================\n");
        for (ListCluster listCluster2 : listCluster) {
            output.append(listCluster2.printOut());
            this.log.debug(listCluster2.printOut());
        }

        this.dbScanOutput = output.toString();

    }

    /**
     * Prepare data.
     *
     * @return the list
     */
    private List<Point> prepareData() {
        List<Company_mst> listCom = this.comService.getListCompanies();
        return listCom.parallelStream().map(com1 -> new Point(TransformUtils.transform(com1)))
            .collect(Collectors.toList());
    }

    /** The selected company mst suggestion. */
    private Company_mst_suggest selectedCompanyMstSuggestion;

    /**
     * autoCompleteCompany.
     *
     * @param keyword the keyword
     * @return the list
     */
    public List<Company_mst_suggest> autoCompleteCompany(final String keyword) {
        ServiceResult<List<Company_mst_suggest>> result = this.elService.suggestCompany(keyword);
        if (result.isSuccessful()) {
            this.setListCompanySuggestion(result.getData());
        }
        return this.getListCompanySuggestion();

    }


    /**
     * Auto complete person in charge.
     *
     * @param keyword the keyword
     * @return the list
     */
    public List<Customer_info_view> autoCompletePersonInCharge(final String keyword) {
        List<Customer_info_view> views =
            this.elService.suggestPersonInChargeOrDepartment(keyword, "01", "0100000003",
                ElasticSearchConstants.CustomerInfoView.DAI_COMEMP_NAME);
        return views;
    }

    /**
     * Test delete companymst.
     */
    public void testDeleteCompanymst() {
        List<DataChangeInfo> listWillHandle = new ArrayList<>();
        List<Company_mst> listCompany = this.comService.getListCompanies();
        for (int i = 0; i < 3; i++) {
            DataChangeInfo dataChangeInfo = new DataChangeInfo(listCompany.get(i), DataChangeInfo.Action.DELETE);
            listWillHandle.add(dataChangeInfo);
        }
        this.elService.bulkProcess(listWillHandle);
    }

    /**
     * Push addres point mst to elastic search.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void pushAddresPointMstToElasticSearch() throws IOException {
        this.elService.pushAddresPointMstEmployeeMstToElasticSearch();
    }


    /**
     * Auto complete address point mst.
     *
     * @param keyword the keyword
     * @return the list
     */
    public List<Addresspoint_mst> autoCompleteAddressPointMst(final String keyword) {
        List<Addresspoint_mst> listAddrespointMst = null;
        if (StringUtils.isEmpty(keyword)) {
            listAddrespointMst = this.elService.findAllDataInIndex("addresspoint_mst");
        } else {
            listAddrespointMst = this.elService.findWithKeyWordInIndex("addresspoint_mst", keyword, "adp_name");
        }
        return listAddrespointMst;
    }

    /**
     * Autocomplete employee.
     *
     * @param keyword the keyword
     * @return the list
     */
    public List<Employee_mst> autocompleteEmployee(final String keyword) {
        List<Employee_mst> listEmployee = null;
        listEmployee =
            this.elService.findEmployeeWithAddrespointCode("employee_mst", this.addrespoint.getAdp_code(), keyword,
                "emp_name");
        return listEmployee;

    }

    /**
     * find id.
     *
     * @return the selected company mst
     */
    // public void testFindIdCompany() {
    // List<arrow.businesstraceability.persistence.entity.San_card_data> listSancard =
    // this.elService.getSanCardDataByByCompId("C689E8EAA4A0EF19006AAAF6A2DCCCEA");
    // }

    // public void isSancardDataChanged() {
    // SansanNameCardInfo record = new SansanNameCardInfo();
    // record.setIdCard("A28088961BA2D90F3C3C3CBBEC55BC4D");
    //
    // San_card_data sanCardCreatedByNameCardInfo = new San_card_data("A28088961BA2D90F3C3C3CBBEC55BC4D");
    //
    //
    // Boolean test = this.sansanImportFileDataService.isSancardDataChanged(record, sanCardCreatedByNameCardInfo);
    // System.out.println(test.toString());
    // }

    /**
     * Gets the selected company mst.
     *
     * @return the selected company mst
     */
    public Company_mst_suggest getSelectedCompanyMst() {
        return this.selectedCompanyMst;
    }

    /**
     * Sets the selected company mst.
     *
     * @param selectedCompanyMst the new selected company mst
     */
    public void setSelectedCompanyMst(final Company_mst_suggest selectedCompanyMst) {
        this.selectedCompanyMst = selectedCompanyMst;
    }

    /**
     * Gets the selected customer.
     *
     * @return the selected customer
     */
    public Customer_info_view getSelectedCustomer() {
        return this.selectedCustomer;
    }

    /**
     * Sets the selected customer.
     *
     * @param selectedCustomer the new selected customer
     */
    public void setSelectedCustomer(final Customer_info_view selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    /**
     * Gets the list company suggestion.
     *
     * @return the list company suggestion
     */
    public List<Company_mst_suggest> getListCompanySuggestion() {
        return this.listCompanySuggestion;
    }

    /**
     * Sets the list company suggestion.
     *
     * @param listCompanySuggestion the new list company suggestion
     */
    public void setListCompanySuggestion(final List<Company_mst_suggest> listCompanySuggestion) {
        this.listCompanySuggestion = listCompanySuggestion;
    }

    /**
     * Gets the selected company mst suggestion.
     *
     * @return the selected company mst suggestion
     */
    public Company_mst_suggest getSelectedCompanyMstSuggestion() {
        return this.selectedCompanyMstSuggestion;
    }

    /**
     * Sets the selected company mst suggestion.
     *
     * @param selectedCompanyMstSuggestion the new selected company mst suggestion
     */
    public void setSelectedCompanyMstSuggestion(final Company_mst_suggest selectedCompanyMstSuggestion) {
        this.selectedCompanyMstSuggestion = selectedCompanyMstSuggestion;
    }

    /**
     * Gets the item select.
     *
     * @return the item select
     */
    public String getItemSelect() {
        return this.itemSelect;
    }

    /**
     * Sets the item select.
     *
     * @param itemSelect the new item select
     */
    public void setItemSelect(final String itemSelect) {
        this.itemSelect = itemSelect;
    }

    /**
     * Gets the db scan output.
     *
     * @return the db scan output
     */
    public String getDbScanOutput() {
        return this.dbScanOutput;
    }

    /**
     * Sets the db scan output.
     *
     * @param dbScanOutput the new db scan output
     */
    public void setDbScanOutput(final String dbScanOutput) {
        this.dbScanOutput = dbScanOutput;
    }


    /**
     * Gets the addrespoint.
     *
     * @return the addrespoint
     */
    public Addresspoint_mst getAddrespoint() {
        return this.addrespoint;
    }


    /**
     * Sets the addrespoint.
     *
     * @param addrespoint the new addrespoint
     */
    public void setAddrespoint(final Addresspoint_mst addrespoint) {
        this.addrespoint = addrespoint;
    }


    /**
     * Gets the employee.
     *
     * @return the employee
     */
    public Employee_mst getEmployee() {
        return this.employee;
    }


    /**
     * Sets the employee.
     *
     * @param employee the new employee
     */
    public void setEmployee(final Employee_mst employee) {
        this.employee = employee;
    }

    /**
     * Import first time.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InterruptedException the interrupted exception
     */
    public void importFirstTime() throws IOException, InterruptedException {
        Runtime run = Runtime.getRuntime();
        Process proc =
            run.exec("java -jar " + DebugBeanCallSarsBatch.FILE_PATH_RUN + " --firstTime --fileSync --filePath="
                + DebugBeanCallSarsBatch.FILE_PATH_IMPORT + " --folderBackup=" + DebugBeanCallSarsBatch.FOLDER_BACKUP);
        proc.waitFor();
        String output = IOUtils.toString(proc.getInputStream());
        String errorOutput = IOUtils.toString(proc.getErrorStream());
        System.out.println(output);
        System.out.println(errorOutput);
        // TODO
    }

    /**
     * Import file.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InterruptedException the interrupted exception
     */
    public void importFile() throws IOException, InterruptedException {

        Runtime run = Runtime.getRuntime();
        Process proc =
            run.exec("java -jar " + DebugBeanCallSarsBatch.FILE_PATH_RUN + " --fileSync --filePath="
                + DebugBeanCallSarsBatch.FILE_PATH_IMPORT + " --folderBackup=" + DebugBeanCallSarsBatch.FOLDER_BACKUP);
        proc.waitFor();
        String output = IOUtils.toString(proc.getInputStream());
        String errorOutput = IOUtils.toString(proc.getErrorStream());
        System.out.println(output);
        System.out.println(errorOutput);

        // TODO
    }

    /**
     * Import api.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InterruptedException the interrupted exception
     */
    public void importApi() throws IOException, InterruptedException {
        Runtime run = Runtime.getRuntime();
        String registeredFrom = DateUtils.formatTime(this.getFromTimeReg());
        String registeredTo = DateUtils.formatTime(this.getToTimeReg());
        String registedDate = DateUtils.formatDateTime(this.getDateReg());
        System.out.println("java -jar " + DebugBeanCallSarsBatch.FILE_PATH_RUN + " --apiSync --folderBackup="
            + DebugBeanCallSarsBatch.FOLDER_BACKUP + " --registeredFrom= " + registeredFrom + " --registeredTo="
            + registeredTo + " --registeredDate=" + registedDate + " --testFileName=abc");
        StringBuilder query = new StringBuilder();
        query.append("java -jar " + DebugBeanCallSarsBatch.FILE_PATH_RUN)
            .append(" --apiSync --folderBackup=" + DebugBeanCallSarsBatch.FOLDER_BACKUP)
            .append(" --registeredFrom= " + registeredFrom).append(" --registeredTo=" + registeredTo)
            .append(" --registeredDate=" + registedDate + " --testFileName=abc");
        Process proc = run.exec(query.toString(), null, new File(DebugBeanCallSarsBatch.FILE_PATH_FOLDER));
        proc.waitFor();
        String output = IOUtils.toString(proc.getInputStream());
        String errorOutput = IOUtils.toString(proc.getErrorStream());
        System.out.println(output);
        System.out.println(errorOutput);
        // TODO
    }


    /**
     * Gets the to time reg.
     *
     * @return the to time reg
     */
    public Date getToTimeReg() {
        return this.toTimeReg;
    }


    /**
     * Sets the to time reg.
     *
     * @param toTimeReg the new to time reg
     */
    public void setToTimeReg(final Date toTimeReg) {
        this.toTimeReg = toTimeReg;
    }


    /**
     * Gets the from time reg.
     *
     * @return the from time reg
     */
    public Date getFromTimeReg() {
        return this.fromTimeReg;
    }


    /**
     * Sets the from time reg.
     *
     * @param fromTimeReg the new from time reg
     */
    public void setFromTimeReg(final Date fromTimeReg) {
        this.fromTimeReg = fromTimeReg;
    }


    /**
     * Gets the date reg.
     *
     * @return the date reg
     */
    public Date getDateReg() {
        return this.dateReg;
    }


    /**
     * Sets the date reg.
     *
     * @param dateReg the new date reg
     */
    public void setDateReg(final Date dateReg) {
        this.dateReg = dateReg;
    }


}
