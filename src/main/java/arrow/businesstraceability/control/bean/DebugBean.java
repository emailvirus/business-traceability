package arrow.businesstraceability.control.bean;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import com.fasterxml.jackson.core.JsonProcessingException;

import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.constant.ElasticSearchConstants;
import arrow.businesstraceability.constant.SansanConstants;
import arrow.businesstraceability.control.helper.DataChangeInfo;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.CompanyManagementService;
import arrow.businesstraceability.control.service.DailyReportService;
import arrow.businesstraceability.control.service.ElasticSearchService;
import arrow.businesstraceability.control.service.SansanService;
import arrow.businesstraceability.debug.DBScan;
import arrow.businesstraceability.debug.ListCluster;
import arrow.businesstraceability.debug.Point;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Customer_info_view;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.San_card_data;
import arrow.businesstraceability.persistence.entity.San_card_tag;
import arrow.businesstraceability.persistence.entity.San_com_branch;
import arrow.businesstraceability.persistence.entity.San_com_cnumber;
import arrow.businesstraceability.persistence.entity.San_com_live_client;
import arrow.businesstraceability.persistence.entity.San_com_live_owner;
import arrow.businesstraceability.persistence.entity.San_com_live_stat;
import arrow.businesstraceability.persistence.entity.San_com_mdomain;
import arrow.businesstraceability.persistence.entity.San_com_site;
import arrow.businesstraceability.persistence.entity.San_com_udomain;
import arrow.businesstraceability.persistence.entity.San_com_whole_card;
import arrow.businesstraceability.persistence.entity.San_idmap_card;
import arrow.businesstraceability.persistence.entity.San_idmap_company;
import arrow.businesstraceability.persistence.entity.San_idmap_person;
import arrow.businesstraceability.util.TransformUtils;
import arrow.framework.bean.AbstractBean;
import arrow.framework.util.CompareUtils;
import arrow.framework.util.StringUtils;

/**
 * The Class DebugBean.
 */
@Named
@ViewScoped
public class DebugBean extends AbstractBean {

    /** The Constant PATH_SAN_CARD_TAG_CSV. */
    private static String PATH_SAN_CARD_TAG_CSV =
        "/home/arrow/arrow/repositories/business-traceability/src/main/resources/san_card_tag.csv";

    /** The Constant PATH_SAN_CARD_DATA_CSV. */
    private static String PATH_SAN_CARD_DATA_CSV =
        "/home/arrow/arrow/repositories/business-traceability/src/main/resources/san_card_data.csv";

    /** The Constant PATH_SAN_IDMAP_CARD_CSV. */
    private static String PATH_SAN_IDMAP_CARD_CSV =
        "/home/arrow/arrow/repositories/business-traceability/src/main/resources/san_idmap_card.csv";

    /** The Constant PATH_SAN_IDMAP_COMPANY_CSV. */
    private static String PATH_SAN_IDMAP_COMPANY_CSV =
        "/home/arrow/arrow/repositories/business-traceability/src/main/resources/san_idmap_company.csv";

    /** The Constant PATH_SAN_IDMAP_PERSON_CSV. */
    private static String PATH_SAN_IDMAP_PERSON_CSV =
        "/home/arrow/arrow/repositories/business-traceability/src/main/resources/san_idmap_person.csv";

    /** The path san com branch csv. */
    private static String PATH_SAN_COM_BRANCH_CSV =
        "/home/arrow/arrow/repositories/business-traceability/src/main/resources/san_com_branch.csv";

    /** The path san com mdomain csv. */
    private static String PATH_SAN_COM_MDOMAIN_CSV =
        "/home/arrow/arrow/repositories/business-traceability/src/main/resources/san_com_mdomain.csv";

    /** The path san com udomain csv. */
    private static String PATH_SAN_COM_UDOMAIN_CSV =
        "/home/arrow/arrow/repositories/business-traceability/src/main/resources/san_com_udomain.csv";

    /** The path san com whole card csv. */
    private static String PATH_SAN_COM_WHOLE_CARD_CSV =
        "/home/arrow/arrow/repositories/business-traceability/src/main/resources/san_com_whole_card.csv";

    /** The path san com live owner csv. */
    private static String PATH_SAN_COM_LIVE_OWNER_CSV =
        "/home/arrow/arrow/repositories/business-traceability/src/main/resources/san_com_live_owner.csv";

    /** The path san com live stat csv. */
    private static String PATH_SAN_COM_LIVE_STAT_CSV =
        "/home/arrow/arrow/repositories/business-traceability/src/main/resources/san_com_live_stat.csv";

    /** The path san com live client csv. */
    private static String PATH_SAN_COM_LIVE_CLIENT_CSV =
        "/home/arrow/arrow/repositories/business-traceability/src/main/resources/san_com_live_client.csv";

    /** The path san com site csv. */
    private static String PATH_SAN_COM_SITE_CSV =
        "/home/arrow/arrow/repositories/business-traceability/src/main/resources/san_com_site.csv";

    /** The path san com cnumber csv. */
    private static String PATH_SAN_COM_CNUMBER_CSV =
        "/home/arrow/arrow/repositories/business-traceability/src/main/resources/san_com_cnumber.csv";

    /** The el service. */
    @Inject
    private ElasticSearchService elService;

    /** The com service. */
    @Inject
    private CompanyManagementService comService;

    /** The daily service. */
    @Inject
    DailyReportService dailyService;

    /** The sansan service. */
    @Inject
    SansanService sansanService;

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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // this.elService.deleteOneRecord("company_msts", "company_mst", "2");
        // Company_mst_suggest com = new Company_mst_suggest("1300000093");
        // com.setCom_company_furigana("12312");
        // com.setCom_company_name("123");
        // com.setCom_url("123");
        // com.setListed((short) 0);
        // com.setWorktype("123");
        // com.setCom_customer_code("123");
        // this.elService.updateOneRecord(com, "company_msts", "company_mst", "2");
        // try {
        // this.elService.loadMapping();
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // this.elService.test();

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
     * @throws JsonProcessingException the json processing exception
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


    public void testCompareSanCard() throws JsonProcessingException {
        List<San_card_data> list = this.sansanService.getAllSanCardData();
        final List<String> listExcludeField = this.buildSanCardDataExcludedFields();
        List<String> errorMessage =
            CompareUtils.compareListDataAndCsv(list, DebugBean.PATH_SAN_CARD_DATA_CSV,
                SansanConstants.FIELD_ID_SAN_CARD, listExcludeField);
        System.out.println("=============================");
        for (String message : errorMessage) {
            System.out.println(message);
        }
        System.out.println("=============================");
    }

    /**
     * Builds the san card data excluded fields.
     *
     * @return the list
     */
    private List<String> buildSanCardDataExcludedFields() {
        return Arrays.asList("pk", SansanConstants.FIELD_EMAIL_NAME, SansanConstants.FIELD_EMAIL_DOMAIN,
            SansanConstants.FIELD_URL_DOMAIN, SansanConstants.FIELD_EMAIL_NAME2, SansanConstants.FIELD_EMAIL_DOMAIN2,
            SansanConstants.FIELD_URL_DOMAIN2, SansanConstants.FIELD_ENUM_INFO_VALIDATION,
            SansanConstants.FIELD_ENUM_IMPORT_PROC, SansanConstants.FIELD_TS_CREATE,
            SansanConstants.FIELD_TS_COM_CREATION, SansanConstants.FIELD_CN_FILEIMPORT,
            SansanConstants.FIELD_CN_APIIMPORT, "date_register", ElasticSearchConstants.San_card_data.TS_LAST_UPDATED,
            "isUpdated", "isPersisted", "date_exchange", SansanConstants.FIELD_CN_COMID_UPDATE,
            SansanConstants.FIELD_CN_CARDINFO_UPDATE, SansanConstants.FIELD_TAGS, SansanConstants.FIELD_ID_CARD,
            SansanConstants.FIELD_ID_COMPANY, SansanConstants.FIELD_ID_PERSON, "san_com_info", "selected",
            "object_version", "version", "last_updated_at");
    }

    /**
     * Test compare san id map card.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void testCompareSanIdMapCard() throws JsonProcessingException {
        List<San_idmap_card> list = this.sansanService.getAllSanIdMapCard();
        final List<String> listExcludeField = this.buildSanIdMapCardExcludedFields();
        List<String> errorMessage =
            CompareUtils.compareListDataAndCsv(list, DebugBean.PATH_SAN_IDMAP_CARD_CSV,
                SansanConstants.FIELD_ID_SAN_CARD, listExcludeField);
        System.out.println("=============================");
        for (String message : errorMessage) {
            System.out.println(message);
        }
        System.out.println("=============================");
    }

    /**
     * Builds the san id map card excluded fields.
     *
     * @return the list
     */
    private List<String> buildSanIdMapCardExcludedFields() {
        return Arrays.asList("pk", "isPersisted", "selected", "object_version", "version", "last_updated_at");
    }

    /**
     * Test compare san id map company.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void testCompareSanIdMapCompany() throws JsonProcessingException {
        List<San_idmap_company> list = this.sansanService.getAllSanIdMapCompany();
        final List<String> listExcludeField = this.buildSanIdMapCompanyExcludedFields();
        List<String> errorMessage =
            CompareUtils.compareListDataAndCsv(list, DebugBean.PATH_SAN_IDMAP_COMPANY_CSV,
                SansanConstants.FIELD_ID_SAN_COMPANY, listExcludeField);
        System.out.println("=============================");
        for (String message : errorMessage) {
            System.out.println(message);
        }
        System.out.println("=============================");
    }

    /**
     * Builds the san id map company excluded fields.
     *
     * @return the list
     */
    private List<String> buildSanIdMapCompanyExcludedFields() {
        return Arrays.asList("pk", "isPersisted", "selected", "object_version", "version", "last_updated_at");
    }

    /**
     * Test compare san id map person.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void testCompareSanIdMapPerson() throws JsonProcessingException {
        List<San_idmap_person> list = this.sansanService.getAllSanIdMapPerson();
        final List<String> listExcludeField = this.buildSanIdMapPersonExcludedFields();
        List<String> errorMessage =
            CompareUtils.compareListDataAndCsv(list, DebugBean.PATH_SAN_IDMAP_PERSON_CSV,
                SansanConstants.FIELD_ID_SAN_PERSON, listExcludeField);
        System.out.println("=============================");
        for (String message : errorMessage) {
            System.out.println(message);
        }
        System.out.println("=============================");
    }

    /**
     * Builds the san id map person excluded fields.
     *
     * @return the list
     */
    private List<String> buildSanIdMapPersonExcludedFields() {
        return Arrays.asList("pk", "isPersisted", "selected", "object_version", "version", "last_updated_at");
    }

    /**
     * Test compare san com branch.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void testCompareSanComBranch() throws JsonProcessingException {
        List<San_com_branch> list = this.sansanService.getAllSanComBranch();

        List<String> listIdMapComp = new ArrayList<String>();
        for (San_com_branch entity : list) {
            String idMap = this.sansanService.findIdSanCompanyByIdCompany(entity.getId_company());
            listIdMapComp.add(idMap);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_company");
        listFieldCompare.add("name_branch");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("san_com_info");
        listExcludeField.add("san_card_data");

        List<String> errorMessage =
            CompareUtils.compareSanDataAndCsv(list, listIdMapComp, San_com_branch.class, San_idmap_company.class,
                DebugBean.PATH_SAN_IDMAP_COMPANY_CSV, DebugBean.PATH_SAN_COM_BRANCH_CSV, "id_san_company",
                listFieldCompare, listExcludeField);

        System.out.println("=============================");
        for (String message : errorMessage) {
            System.out.println(message);
        }
        System.out.println("=============================");
    }

    /**
     * Test compare san com m domain.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void testCompareSanComMDomain() throws JsonProcessingException {
        List<San_com_mdomain> list = this.sansanService.getAllSanComMDomain();

        List<String> listIdMapComp = new ArrayList<String>();
        for (San_com_mdomain entity : list) {
            String idMap = this.sansanService.findIdSanCompanyByIdCompany(entity.getId_company());
            listIdMapComp.add(idMap);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_company");
        listFieldCompare.add("email_domain");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("san_com_info");

        List<String> errorMessage =
            CompareUtils.compareSanDataAndCsv(list, listIdMapComp, San_com_mdomain.class, San_idmap_company.class,
                DebugBean.PATH_SAN_IDMAP_COMPANY_CSV, DebugBean.PATH_SAN_COM_MDOMAIN_CSV, "id_san_company",
                listFieldCompare, listExcludeField);

        System.out.println("=============================");
        for (String message : errorMessage) {
            System.out.println(message);
        }
        System.out.println("=============================");
    }

    /**
     * Test compare san com u domain.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void testCompareSanComUDomain() throws JsonProcessingException {
        List<San_com_udomain> list = this.sansanService.getAllSanComUDomain();

        List<String> listIdMapComp = new ArrayList<String>();
        for (San_com_udomain entity : list) {
            String idMap = this.sansanService.findIdSanCompanyByIdCompany(entity.getId_company());
            listIdMapComp.add(idMap);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_company");
        listFieldCompare.add("url_domain");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("san_com_info");

        List<String> errorMessage =
            CompareUtils.compareSanDataAndCsv(list, listIdMapComp, San_com_udomain.class, San_idmap_company.class,
                DebugBean.PATH_SAN_IDMAP_COMPANY_CSV, DebugBean.PATH_SAN_COM_UDOMAIN_CSV, "id_san_company",
                listFieldCompare, listExcludeField);

        System.out.println("=============================");
        for (String message : errorMessage) {
            System.out.println(message);
        }
        System.out.println("=============================");
    }

    /**
     * Test compare san com whole card.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void testCompareSanComWholeCard() throws JsonProcessingException {
        List<San_com_whole_card> list = this.sansanService.getAllSanComWholeCard();

        List<String> listIdMapComp = new ArrayList<String>();
        for (San_com_whole_card entity : list) {
            String idMap = this.sansanService.findIdSanCardByIdCard(entity.getId_card());
            listIdMapComp.add(idMap);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_card");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_exchange");
        listExcludeField.add("date_register");
        listExcludeField.add("date_card_deletion");
        listExcludeField.add("san_com_info");
        listExcludeField.add("san_card_data");

        List<String> errorMessage =
            CompareUtils.compareSanDataAndCsv(list, listIdMapComp, San_com_whole_card.class, San_idmap_card.class,
                DebugBean.PATH_SAN_IDMAP_CARD_CSV, DebugBean.PATH_SAN_COM_WHOLE_CARD_CSV, "id_san_card",
                listFieldCompare, listExcludeField);

        System.out.println("=============================");
        for (String message : errorMessage) {
            System.out.println(message);
        }
        System.out.println("=============================");
    }

    /**
     * Test compare san com live owner.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void testCompareSanComLiveOwner() throws JsonProcessingException {
        List<San_com_live_owner> list = this.sansanService.getAllSanComLiveOwner();

        List<String> listIdMapComp = new ArrayList<String>();
        for (San_com_live_owner entity : list) {
            String idMap = this.sansanService.findIdSanCompanyByIdCompany(entity.getId_company());
            listIdMapComp.add(idMap);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_company");
        listFieldCompare.add("ac_user");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("san_com_info");
        listExcludeField.add("san_card_data");

        List<String> errorMessage =
            CompareUtils.compareSanDataAndCsv(list, listIdMapComp, San_com_live_owner.class, San_idmap_company.class,
                DebugBean.PATH_SAN_IDMAP_COMPANY_CSV, DebugBean.PATH_SAN_COM_LIVE_OWNER_CSV, "id_san_company",
                listFieldCompare, listExcludeField);

        System.out.println("=============================");
        for (String message : errorMessage) {
            System.out.println(message);
        }
        System.out.println("=============================");
    }

    /**
     * Test compare san com live stat.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void testCompareSanComLiveStat() throws JsonProcessingException {
        List<San_com_live_stat> list = this.sansanService.getAllSanComLiveStat();

        List<String> listIdMapComp = new ArrayList<String>();
        for (San_com_live_stat entity : list) {
            String idMap = this.sansanService.findIdSanCompanyByIdCompany(entity.getId_company());
            listIdMapComp.add(idMap);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_company");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("date_oldest_exchange");
        listExcludeField.add("san_com_info");
        listExcludeField.add("latestCard");
        listExcludeField.add("maxVPCard");
        listExcludeField.add("maxCPCard");

        List<String> errorMessage =
            CompareUtils.compareSanDataAndCsv(list, listIdMapComp, San_com_live_stat.class, San_idmap_company.class,
                DebugBean.PATH_SAN_IDMAP_COMPANY_CSV, DebugBean.PATH_SAN_COM_LIVE_STAT_CSV, "id_san_company",
                listFieldCompare, listExcludeField);

        System.out.println("=============================");
        for (String message : errorMessage) {
            System.out.println(message);
        }
        System.out.println("=============================");
    }

    /**
     * Test compare san com live client.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void testCompareSanComLiveClient() throws JsonProcessingException {
        List<San_com_live_client> list = this.sansanService.getAllSanComLiveClient();

        List<String> listIdSanCompany = new ArrayList<String>();
        List<String> listIdSanPerson = new ArrayList<String>();
        for (San_com_live_client entity : list) {
            String idMapCompany = this.sansanService.findIdSanCompanyByIdCompany(entity.getId_company());
            listIdSanCompany.add(idMapCompany);

            String idMapPerson = this.sansanService.findIdSanPersonByIdPerson(entity.getId_person());
            listIdSanPerson.add(idMapPerson);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_company");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("san_com_info");
        listExcludeField.add("san_card_data");

        List<String> errorMessage =
            CompareUtils.compareSanComLiveClient(list, listIdSanCompany, listIdSanPerson,
                DebugBean.PATH_SAN_COM_LIVE_CLIENT_CSV, DebugBean.PATH_SAN_IDMAP_COMPANY_CSV,
                DebugBean.PATH_SAN_IDMAP_PERSON_CSV, listExcludeField);

        System.out.println("=============================");
        for (String message : errorMessage) {
            System.out.println(message);
        }
        System.out.println("=============================");
    }

    /**
     * Test compare san com site.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void testCompareSanComSite() throws JsonProcessingException {
        List<San_com_site> list = this.sansanService.getAllSanComsite();

        List<String> listIdSanComp = new ArrayList<String>();
        for (San_com_site entity : list) {
            String idMap = this.sansanService.findIdSanCompanyByIdCompany(entity.getId_company());
            listIdSanComp.add(idMap);
        }

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("san_com_info");
        listExcludeField.add("san_card_data");
        listExcludeField.add("san_com_branch");

        List<String> errorMessage =
            CompareUtils.compareSanComSite(list, listIdSanComp, DebugBean.PATH_SAN_IDMAP_COMPANY_CSV,
                DebugBean.PATH_SAN_COM_BRANCH_CSV, DebugBean.PATH_SAN_COM_SITE_CSV, listExcludeField);

        System.out.println("=============================");
        for (String message : errorMessage) {
            System.out.println(message);
        }
        System.out.println("=============================");
    }

    /**
     * Test compare san com c number.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void testCompareSanComCNumber() throws JsonProcessingException {
        List<San_com_cnumber> list = this.sansanService.getAllSanComCNumber();

        List<String> listIdSanComp = new ArrayList<String>();
        for (San_com_cnumber entity : list) {
            String idMap = this.sansanService.findIdSanCompanyByIdCompany(entity.getId_company());
            listIdSanComp.add(idMap);
        }

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("san_com_info");
        listExcludeField.add("san_card_data");
        listExcludeField.add("san_com_branch");
        listExcludeField.add("san_com_site");

        List<String> errorMessage =
            CompareUtils.compareSanComCNumber(list, listIdSanComp, DebugBean.PATH_SAN_IDMAP_COMPANY_CSV,
                DebugBean.PATH_SAN_COM_BRANCH_CSV, DebugBean.PATH_SAN_COM_SITE_CSV, DebugBean.PATH_SAN_COM_CNUMBER_CSV,
                listExcludeField);

        System.out.println("=============================");
        for (String message : errorMessage) {
            System.out.println(message);
        }
        System.out.println("=============================");
    }

    /**
     * Test compare san card tag.
     *
     * @throws JsonProcessingException the json processing exception
     */

    public void testCompareSanCardTag() throws JsonProcessingException {
        List<San_card_tag> list = this.sansanService.getAllSanCardTag();

        List<String> listIdMapCard = new ArrayList<String>();
        for (San_card_tag entity : list) {
            String idMap = this.sansanService.findIdSanCardByIdCard(entity.getId_card());
            listIdMapCard.add(idMap);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_card");
        listFieldCompare.add("tag");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("san_com_info");
        listExcludeField.add("san_card_data");

        List<String> errorMessage =
            CompareUtils.compareSanDataAndCsv(list, listIdMapCard, San_card_tag.class, San_idmap_card.class,
                DebugBean.PATH_SAN_IDMAP_CARD_CSV, DebugBean.PATH_SAN_CARD_TAG_CSV, "id_san_card", listFieldCompare,
                listExcludeField);

        System.out.println("=============================");
        for (String message : errorMessage) {
            System.out.println(message);
        }
        System.out.println("=============================");
    }

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
}
