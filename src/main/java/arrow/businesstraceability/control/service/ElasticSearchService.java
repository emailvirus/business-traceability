package arrow.businesstraceability.control.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.deltaspike.core.api.config.ConfigProperty;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.cache.entity.Daily_Report_View;
import arrow.businesstraceability.constant.ElasticSearchConstants;
import arrow.businesstraceability.control.helper.DataChangeInfo;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.persistence.entity.Abstract_entity;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Customer_info_view;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.San_com_live_stat;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.businesstraceability.persistence.repository.CompanyStartUpRepository;
import arrow.businesstraceability.persistence.repository.CustomerInfoStartUpViewRepository;
import arrow.businesstraceability.persistence.repository.Employee_mstRepository;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.util.JsonUtils;
import arrow.framework.util.StringUtils;

/**
 * The Class ElasticSearchService.
 */
@Named
public class ElasticSearchService extends AbstractService {

    /** Repository using when server startup. */
    @Inject
    private CustomerInfoStartUpViewRepository customerStartUpRepository;

    /** Repository using when server startup. */
    @Inject
    private CompanyStartUpRepository companyStartUpRepository;

    /**
     * client.
     */
    private Client client;

    /** The elastic search host. */
    @Inject
    @ConfigProperty(name = "ec.host")
    private String elasticSearchHost;

    /** The elastic search port. */
    @Inject
    @ConfigProperty(name = "ec.port")
    private int elasticSearchPort;

    /**
     * init.
     */
    @PostConstruct
    public void init() {
        try {
            this.client =
                TransportClient
                    .builder()
                    .build()
                    .addTransportAddress(
                        new InetSocketTransportAddress(InetAddress.getByName(this.elasticSearchHost),
                            this.elasticSearchPort));
        } catch (UnknownHostException uke) {

            this.log.debug(uke.getMessage());
        }

    }

    /**
     * push list customer to ElasticSearch Server.
     *
     * @return the service result
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private ServiceResult<Customer_info_view> pushAllCustomerInformation() throws IOException {
        try {
            List<Customer_info_view> listCustomerInfo = this.customerStartUpRepository.findAll();
            BulkRequestBuilder bulkRequest = this.getClient().prepareBulk();
            for (int i = 0; i < listCustomerInfo.size(); i++) {
                try {
                    bulkRequest.add(this.client.prepareIndex(ElasticSearchConstants.CustomerInfoView.INDEX_VALUE,
                        ElasticSearchConstants.CustomerInfoView.TYPE_VALUE, listCustomerInfo.get(i).getId() + "")
                        .setSource(JsonUtils.getJson(listCustomerInfo.get(i))));
                } catch (final Exception e) {
                    this.log.debug(e);
                }
            }
            bulkRequest.execute().actionGet();
            return new ServiceResult<>(true, InfoMessage.common_001_save_successfully());
        } catch (final NoNodeAvailableException e) {
            this.log.debug(e);
            return new ServiceResult<>(false, ErrorMessage.daily_020_noNodeAvailable());
        }
    }

    /**
     * searchCustomerByCompanyCodeAndCompointCode.
     *
     * @param comPointCode the com point code
     * @param companyCode the company code
     * @return the list
     */
    private List<Customer_info_view> searchCustomerByCompanyCodeAndCompointCode(final String comPointCode,
        final String companyCode) {
        SearchResponse response =
            this.client
                .prepareSearch(ElasticSearchConstants.CustomerInfoView.INDEX_VALUE)
                .setQuery(
                    (QueryBuilders.boolQuery().must(
                        QueryBuilders.termQuery(ElasticSearchConstants.CustomerInfoView.COM_COMPANY_CODE, companyCode))
                        .must(QueryBuilders.termQuery(ElasticSearchConstants.CustomerInfoView.BRANCH_POINT_CODE,
                            comPointCode)))).setSize(ElasticSearchConstants.CustomerInfoView.SIZE).execute()
                .actionGet();
        SearchHit[] hits = response.getHits().getHits();
        List<Customer_info_view> listCustomInfo = new ArrayList<>();
        for (int i = 0; i < hits.length; i++) {
            Customer_info_view srcCustomer = new Customer_info_view();
            try {
                srcCustomer =
                    (Customer_info_view) JsonUtils.convertJsonToObject(hits[i].getSourceAsString(), srcCustomer);
                Customer_info_view desCustomer = new Customer_info_view(srcCustomer.getId());
                BeanCopier.copy(srcCustomer, desCustomer);
                listCustomInfo.add(desCustomer);
            } catch (final IOException e) {

                this.log.debug(e.getMessage());
            }
        }
        return listCustomInfo;
    }

    /**
     * suggest person in charge.
     *
     * @param keyword the keyword
     * @param comPointCode the com point code
     * @param companyCode the company code
     * @param type the type
     * @return the list
     */
    public List<Customer_info_view> suggestPersonInChargeOrDepartment(String keyword, final String comPointCode,
        final String companyCode, final String type) {

        keyword = StringUtils.replaceSpecialCharacter(keyword).toLowerCase();
        List<Customer_info_view> listCustom =
            this.searchCustomerByCompanyCodeAndCompointCode(comPointCode, companyCode);
        if (StringUtils.isEmpty(keyword)) {
            return listCustom;
        }

        List<Customer_info_view> suggestions = new ArrayList<>();
        List<Customer_info_view> containList = new ArrayList<>();
        if (type.equals(ElasticSearchConstants.CustomerInfoView.DAI_COMEMP_NAME)) {
            for (Customer_info_view customer : listCustom) {
                if (org.apache.commons.lang3.StringUtils.startsWith(customer.getDai_compemp_name(), keyword)) {
                    suggestions.add(customer);
                } else if (org.apache.commons.lang3.StringUtils.contains(customer.getDai_compemp_name(), keyword)) {
                    containList.add(customer);
                }
            }
        }
        if (type.equals(ElasticSearchConstants.CustomerInfoView.DAI_COMEMP_POST)) {
            for (Customer_info_view customer : listCustom) {
                if ((customer.getDai_employee_post() != null) && customer.getDai_employee_post().startsWith(keyword)) {
                    suggestions.add(customer);
                } else if ((customer.getDai_employee_post() != null)
                    && customer.getDai_employee_post().contains(keyword)) {
                    containList.add(customer);
                }
            }
        }
        suggestions.addAll(containList);
        return suggestions;
    }

    /**
     * remove all record in one index.
     *
     * @param index the index
     */
    public void removeIndex(final String index) {
        this.client.admin().indices().delete(new DeleteIndexRequest(index)).actionGet();
    }

    /**
     * is record in list.
     *
     * @param listCom the list com
     * @param com the com
     * @return true, if is in list
     */
    private boolean isInList(final List<Company_mst_suggest> listCom, final Company_mst_suggest com) {
        for (Company_mst_suggest company_mst : listCom) {
            if (com.getCom_company_code().equals(company_mst.getCom_company_code())) {
                return true;
            }
        }
        return false;

    }

    /**
     * Search companies by query string.
     *
     * @param listComSuggestBefore the list com suggest before
     * @param index the index
     * @param type the type
     * @param size the size
     * @param searchValue the search value
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws NoNodeAvailableException the no node available exception
     */
    private List<Company_mst_suggest> searchCompaniesByQueryString(
        final List<Company_mst_suggest> listComSuggestBefore, final String index, final String type, final int size,
        final String searchValue) throws IOException, NoNodeAvailableException {
        List<Company_mst_suggest> listCompanySuggestResults = listComSuggestBefore;
        BoolQueryBuilder stringQuery =
            QueryBuilders
                .boolQuery()
                .should(
                    QueryBuilders
                        .queryStringQuery(
                            ElasticSearchConstants.WildCardSearchWord.MATCHES_ANY_SINGLE_CHARACTER
                                + ElasticSearchConstants.WildCardSearchWord.MATCHES_ANY_SEQUENCE + searchValue
                                + ElasticSearchConstants.WildCardSearchWord.MATCHES_ANY_SEQUENCE)
                        .field(ElasticSearchConstants.Company_mst.COM_CUSTOMER_CODE)
                        .boost(ElasticSearchConstants.Boost.HALFPOINT))
                .should(
                    QueryBuilders
                        .queryStringQuery(
                            ElasticSearchConstants.WildCardSearchWord.MATCHES_ANY_SINGLE_CHARACTER
                                + ElasticSearchConstants.WildCardSearchWord.MATCHES_ANY_SEQUENCE + searchValue
                                + ElasticSearchConstants.WildCardSearchWord.MATCHES_ANY_SEQUENCE)
                        .field(ElasticSearchConstants.Company_mst.COM_COMPANY_CODE)
                        .boost(ElasticSearchConstants.Boost.SIXTENTH))
                .should(
                    QueryBuilders
                        .queryStringQuery(
                            ElasticSearchConstants.WildCardSearchWord.MATCHES_ANY_SINGLE_CHARACTER
                                + ElasticSearchConstants.WildCardSearchWord.MATCHES_ANY_SEQUENCE + searchValue
                                + ElasticSearchConstants.WildCardSearchWord.MATCHES_ANY_SEQUENCE)
                        .field(ElasticSearchConstants.Company_mst.COM_COMPANY_NAME)
                        .boost(ElasticSearchConstants.Boost.ONEFIFTH))
                .should(
                    QueryBuilders
                        .queryStringQuery(
                            ElasticSearchConstants.WildCardSearchWord.MATCHES_ANY_SINGLE_CHARACTER
                                + ElasticSearchConstants.WildCardSearchWord.MATCHES_ANY_SEQUENCE + searchValue
                                + ElasticSearchConstants.WildCardSearchWord.MATCHES_ANY_SEQUENCE)
                        .field(ElasticSearchConstants.Company_mst.COM_COMPANY_FURIGANA)
                        .boost(ElasticSearchConstants.Boost.ONEFIFTH))
                .should(
                    QueryBuilders
                        .queryStringQuery(
                            ElasticSearchConstants.WildCardSearchWord.MATCHES_ANY_SINGLE_CHARACTER
                                + ElasticSearchConstants.WildCardSearchWord.MATCHES_ANY_SEQUENCE
                                + StringUtils.removeUrlToken(searchValue)
                                + ElasticSearchConstants.WildCardSearchWord.MATCHES_ANY_SEQUENCE)
                        .field(ElasticSearchConstants.Company_mst.COM_URL)
                        .boost(ElasticSearchConstants.Boost.ONEOVERTEN));
        SearchResponse response =
            this.client.prepareSearch(index).setTypes(type).setQuery(stringQuery).setFrom(0).setSize(size).execute()
                .actionGet();
        List<Company_mst_suggest> listCompanySuggest = new ArrayList<>();
        int allResult = response.getHits().getHits().length;
        // convert json to object
        for (int i = 0; i < allResult; i++) {
            Company_mst_suggest company = new Company_mst_suggest();
            company =
                (Company_mst_suggest) JsonUtils.convertJsonToObject(response.getHits().getAt(i).getSourceAsString(),
                    company);
            listCompanySuggest.add(company);
        }
        // check exists
        for (int i = 0; i < listCompanySuggest.size(); i++) {
            if (!this.isInList(listComSuggestBefore, listCompanySuggest.get(i))) {
                listCompanySuggestResults.add(listCompanySuggest.get(i));
            }
            if (listCompanySuggestResults.size() == size) {
                return listCompanySuggestResults;
            }
        }
        return listCompanySuggestResults;
    }

    /**
     * Search companies by query string.
     *
     * @param index the index
     * @param type the type
     * @param size the size
     * @param searchValue the search value
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws NoNodeAvailableException the no node available exception
     */
    private List<Company_mst_suggest> searchCompaniesByPrefixString(final String index, final String type,
        final int size, final String searchValue) throws IOException, NoNodeAvailableException {
        BoolQueryBuilder prefixQuery =
            QueryBuilders
                .boolQuery()
                .should(
                    QueryBuilders.prefixQuery(ElasticSearchConstants.Company_mst.COM_CUSTOMER_CODE, searchValue).boost(
                        ElasticSearchConstants.Boost.HALFPOINT))
                .should(
                    QueryBuilders.prefixQuery(ElasticSearchConstants.Company_mst.COM_COMPANY_CODE, searchValue).boost(
                        ElasticSearchConstants.Boost.HALFPOINT))
                .should(
                    QueryBuilders.prefixQuery(ElasticSearchConstants.Company_mst.COM_COMPANY_NAME, searchValue).boost(
                        ElasticSearchConstants.Boost.ONEFIFTH))
                .should(
                    QueryBuilders.prefixQuery(ElasticSearchConstants.Company_mst.COM_COMPANY_FURIGANA, searchValue)
                        .boost(ElasticSearchConstants.Boost.ONEFIFTH))
                .should(
                    QueryBuilders.prefixQuery(ElasticSearchConstants.Company_mst.COM_URL, searchValue).boost(
                        ElasticSearchConstants.Boost.ONEOVERTEN));
        SearchResponse response =
            this.client.prepareSearch(index).setTypes(type).setQuery(prefixQuery).setFrom(0).setSize(size).execute()
                .actionGet();
        List<Company_mst_suggest> listCompanySuggest = new ArrayList<>();
        int allResult = response.getHits().getHits().length;
        // convert json to object
        for (int i = 0; i < allResult; i++) {
            Company_mst_suggest company = new Company_mst_suggest();
            company =
                (Company_mst_suggest) JsonUtils.convertJsonToObject(response.getHits().getAt(i).getSourceAsString(),
                    company);
            listCompanySuggest.add(company);
        }
        return listCompanySuggest;
    }

    /**
     * Put tokenizer ngram and index.
     *
     * @return the service result
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private ServiceResult<Company_mst_suggest> pushAllCompanyMstInfomation() throws IOException {
        try {
            List<Company_mst> listCompanyMst = this.companyStartUpRepository.findAll();
            // convert to list CompanySuggest
            List<Company_mst_suggest> listCompanySuggest = new ArrayList<>();
            for (int i = 0; i < listCompanyMst.size(); i++) {
                Company_mst eachComp = listCompanyMst.get(i);
                if (!eachComp.getCom_delete_flg()) {
                    String comNameFu = eachComp.getCom_company_furigana();
                    String comName = eachComp.getCom_company_name();
                    String comUrl = eachComp.getCom_url();
                    Company_mst_suggest company_suggest = new Company_mst_suggest(eachComp.getCom_company_code());
                    company_suggest.setWorktype(listCompanyMst.get(i).getIndustry_big_mst().getBig_name());
                    company_suggest.setCom_customer_code(eachComp.getCom_customer_code());
                    company_suggest.setCom_company_name(comName);
                    company_suggest.setCom_company_furigana(comNameFu);
                    company_suggest.setCom_url(StringUtils.removeUrlToken(comUrl));
                    company_suggest.setListed(eachComp.getCom_listed_code());
                    company_suggest.setCom_indbig_code(eachComp.getCom_indbig_code());
                    company_suggest.setBig_name(eachComp.getIndustry_big_mst().getBig_name());
                    company_suggest.setCom_limited_code(eachComp.getCom_limited_code());
                    company_suggest.setCom_listed_code(eachComp.getCom_listed_code());
                    company_suggest.setCom_point_code(eachComp.getCom_point_code());
                    company_suggest.setAdp_name(eachComp.getAddresspoint_mst().getAdp_name());

                    if (StringUtils.isEmpty(comNameFu)) {
                        comNameFu = arrow.framework.util.StringUtils.convertToHalfWidthKata(comName);
                    }
                    company_suggest.setFurigana(comNameFu);

                    company_suggest.setName(arrow.framework.util.StringUtils.convertToHalfWidthKata(comName));
                    String domain = arrow.framework.util.StringUtils.extractDomain(comUrl);
                    company_suggest.setDomain(domain);
                    company_suggest.setDomainExt(arrow.framework.util.StringUtils.extractDomainExt(comUrl));

                    listCompanySuggest.add(company_suggest);
                }
            }
            BulkRequestBuilder bulkRequest = this.getClient().prepareBulk();
            for (int i = 0; i < listCompanySuggest.size(); i++) {
                try {
                    bulkRequest.add(this.client.prepareIndex(ElasticSearchConstants.Company_mst.INDEX_VALUE,
                        ElasticSearchConstants.Company_mst.TYPE_VALUE, listCompanySuggest.get(i).getCom_company_code())
                        .setSource(JsonUtils.getJson(listCompanySuggest.get(i))));
                } catch (final Exception e) {
                    this.log.debug(e);
                    return new ServiceResult<>(true, ErrorMessage.common_002_save_unsuccessfully());
                }
            }
            bulkRequest.execute().actionGet();
            return new ServiceResult<>(true, InfoMessage.common_001_save_successfully());
        } catch (final NoNodeAvailableException e) {
            this.log.debug(e);
            return new ServiceResult<>(false, ErrorMessage.daily_020_noNodeAvailable());
        }
    }

    /**
     * auto complete company.
     *
     * @param keyword the keyword
     * @return the service result
     */
    public ServiceResult<List<Company_mst_suggest>> suggestCompany(String keyword) {
        keyword = StringUtils.replaceSpecialCharacter(keyword).toLowerCase();
        List<Message> messages = new ArrayList<>();
        try {
            List<Company_mst_suggest> listCompanySuggest =
                this.searchCompaniesByPrefixString(ElasticSearchConstants.Company_mst.INDEX_VALUE,
                    ElasticSearchConstants.Company_mst.TYPE_VALUE,
                    ElasticSearchConstants.Company_mst_suggest.MAX_SIZE_SUGGEST, keyword);
            if (listCompanySuggest.size() == ElasticSearchConstants.Company_mst_suggest.MAX_SIZE_SUGGEST) {
                return new ServiceResult<>(true, listCompanySuggest, messages);
            }
            listCompanySuggest =
                this.searchCompaniesByQueryString(listCompanySuggest, ElasticSearchConstants.Company_mst.INDEX_VALUE,
                    ElasticSearchConstants.Company_mst.TYPE_VALUE,
                    ElasticSearchConstants.Company_mst_suggest.MAX_SIZE_SUGGEST, keyword);
            return new ServiceResult<>(true, listCompanySuggest, messages);
        } catch (final NoNodeAvailableException e) {
            this.log.debug(e.getMessage());
            messages.add(ErrorMessage.daily_020_noNodeAvailable());
            return new ServiceResult<>(false, null, messages);
        } catch (final IOException e) {
            this.log.debug(e.getMessage());
            messages.add(ErrorMessage.daily_021_can_not_get_suggestion());
            return new ServiceResult<>(false, null, messages);
        }

    }

    /**
     * Adds the delete request.
     *
     * @param index the index
     * @param type the type
     * @param id the id
     * @return the delete request
     */
    private DeleteRequest addDeleteRequest(final String index, final String type, final String id) {
        return this.client.prepareDelete(index, type, id).request();
    }

    /**
     * Adds the upsert request.
     *
     * @param index the index
     * @param type the type
     * @param obj the obj
     * @param id the id
     * @return the update request
     * @throws JsonProcessingException the json processing exception
     */
    private UpdateRequest addUpsertRequest(final String index, final String type, final Object obj, final String id)
        throws JsonProcessingException {
        IndexRequest indexRequest = new IndexRequest(index, type, id).source(JsonUtils.getJson(obj));
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(index);
        updateRequest.type(type);
        updateRequest.id(id);
        updateRequest.doc(JsonUtils.getJson(obj)).upsert(indexRequest);
        return updateRequest;
    }

    /**
     * Bulk process.
     *
     * @param listWillHandle the list will handle
     * @return the service result
     */
    public ServiceResult<List<DataChangeInfo>> bulkProcess(final List<DataChangeInfo> listWillHandle) {
        BulkRequestBuilder bulkReB = this.client.prepareBulk();
        try {
            for (DataChangeInfo dataChangeInfo : listWillHandle) {
                if (DataChangeInfo.DataType.COMPANY_MST == dataChangeInfo.getType()) {

                    if (dataChangeInfo.getAction() == DataChangeInfo.Action.DELETE) {
                        List<DeleteRequest> listDelRequest =
                            this.prepareDeleteRequestsWhenDeleteOneRecordOnTableCompanymst(dataChangeInfo
                                .getCompanyMst().getCom_company_code());
                        for (DeleteRequest deleteRequest : listDelRequest) {
                            bulkReB.add(deleteRequest);
                        }
                    }
                    if (dataChangeInfo.getAction() == DataChangeInfo.Action.UPSERT) {
                        List<UpdateRequest> listUpsertRequest =
                            this.prepareUpsertRequestsWhenSaveOneRecordOnTableCompanymst(dataChangeInfo);
                        for (UpdateRequest updateRequest : listUpsertRequest) {
                            bulkReB.add(updateRequest);
                        }
                    }
                }
                if (DataChangeInfo.DataType.CUSTOM_INFO_VIEW == dataChangeInfo.getType()) {
                    if (dataChangeInfo.getAction() == DataChangeInfo.Action.UPSERT) {

                        bulkReB.add(this.prepareUpdateRequestWhenAddNewRecordToTableDailyReport(dataChangeInfo
                            .getCustomerInfo()));

                    }
                }
            }
            if (bulkReB.request().requests().size() > 0) {
                bulkReB.execute().actionGet();
            }
            return new ServiceResult<>(true, listWillHandle, new ArrayList<>());
        } catch (IOException | NoNodeAvailableException io) {
            return new ServiceResult<>(false, listWillHandle, ErrorMessage.daily_020_noNodeAvailable());
        }

    }

    /**
     * Load mapping.
     *
     * @param filePath the file path
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private String loadConfigToFile(final String filePath) throws IOException {
        InputStream is = ElasticSearchService.class.getResourceAsStream(filePath);
        return IOUtils.toString(is);
    }

    /**
     * Delete exist index.
     *
     * @param indexName the index name
     */
    public void deleteExistIndex(final String indexName) {
        if (this.checkIndexExisted(indexName)) {
            final DeleteIndexRequestBuilder delIdx = this.client.admin().indices().prepareDelete(indexName);
            delIdx.execute().actionGet();
        }
    }

    /**
     * Check exist index.
     *
     * @param indexName the index name
     * @return true, if successful
     */
    public boolean checkIndexExisted(final String indexName) {
        final IndicesExistsResponse res = this.client.admin().indices().prepareExists(indexName).execute().actionGet();
        return res.isExists();
    }

    /**
     * Builds the delete requests when delete one record on companymst.
     *
     * @param companyCode the company code
     * @return the list
     */
    private List<DeleteRequest> prepareDeleteRequestsWhenDeleteOneRecordOnTableCompanymst(final String companyCode) {
        List<DeleteRequest> listDelRequest = new ArrayList<>();
        try {
            listDelRequest.add(this.addDeleteRequest(ElasticSearchConstants.Company_mst.INDEX_VALUE,
                ElasticSearchConstants.Company_mst.TYPE_VALUE, companyCode));
            List<Customer_info_view> listCom = this.findCustomerInfosByCompanyCode(companyCode);
            for (Customer_info_view customer_info_view : listCom) {
                DeleteRequest delRequestCustomer =
                    this.addDeleteRequest(ElasticSearchConstants.CustomerInfoView.INDEX_VALUE,
                        ElasticSearchConstants.CustomerInfoView.TYPE_VALUE, customer_info_view.getId() + "");
                listDelRequest.add(delRequestCustomer);
            }

        } catch (final IOException e) {
            this.log.debug(e);
            return null;
        }
        return listDelRequest;
    }

    /**
     * Prepare upsert requests when save one record on table companymst.
     *
     * @param dataChangeInfo the data change info
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private List<UpdateRequest> prepareUpsertRequestsWhenSaveOneRecordOnTableCompanymst(
        final DataChangeInfo dataChangeInfo) throws IOException {
        List<UpdateRequest> listUpdateRequest = new ArrayList<>();
        listUpdateRequest.add(this.addUpsertRequest(ElasticSearchConstants.Company_mst.INDEX_VALUE,
            ElasticSearchConstants.Company_mst.TYPE_VALUE, this.convertCompanymstToCompanyMstSuggest(dataChangeInfo
                .getCompanyMst()), dataChangeInfo.getCompanyMst().getCom_company_code()));
        return listUpdateRequest;

    }

    /**
     * Prepare update request when add new record to table daily report.
     *
     * @param customerInfo the customer info
     * @return the update request
     * @throws JsonProcessingException the json processing exception
     */
    private UpdateRequest prepareUpdateRequestWhenAddNewRecordToTableDailyReport(final Customer_info_view customerInfo)
        throws JsonProcessingException {
        return this.addUpsertRequest(ElasticSearchConstants.CustomerInfoView.INDEX_VALUE,
            ElasticSearchConstants.CustomerInfoView.TYPE_VALUE, customerInfo, String.valueOf(customerInfo.getId()));
    }

    /**
     * Find company mst by company code.
     *
     * @param companyCode the company code
     * @return the list
     * @throws JsonParseException the json parse exception
     * @throws JsonMappingException the json mapping exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private List<Customer_info_view> findCustomerInfosByCompanyCode(final String companyCode)
        throws JsonParseException, JsonMappingException, IOException {
        SearchResponse response =
            this.client
                .prepareSearch(ElasticSearchConstants.CustomerInfoView.INDEX_VALUE)
                .setQuery(
                    QueryBuilders.termQuery(ElasticSearchConstants.CustomerInfoView.COM_COMPANY_CODE, companyCode))
                .setSize(ElasticSearchConstants.CustomerInfoView.SIZE).execute().actionGet();
        List<Customer_info_view> listCom = new ArrayList<>();
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit searchHit : hits) {
            Customer_info_view com = new Customer_info_view();
            com = (Customer_info_view) JsonUtils.convertJsonToObject(searchHit.getSourceAsString(), com);
            listCom.add(com);
        }
        return listCom;

    }

    /**
     * Convert companymst to company mst suggest.
     *
     * @param com the com
     * @return the company_mst_suggest
     */
    private Company_mst_suggest convertCompanymstToCompanyMstSuggest(final Company_mst com) {
        Company_mst_suggest comSuggest = new Company_mst_suggest();
        BeanCopier.copy(com, comSuggest);
        comSuggest.setListed(com.getCom_listed_code());
        comSuggest.setWorktype(com.getIndustry_big_mst().getBig_name());
        comSuggest.setFurigana(arrow.framework.util.StringUtils.convertToHalfWidthKata(com.getCom_company_furigana()));
        comSuggest.setName(arrow.framework.util.StringUtils.convertToHalfWidthKata(com.getCom_company_name()));
        comSuggest.setAdp_name(com.getAddresspoint_mst().getAdp_name());
        comSuggest.setBig_name(com.getIndustry_big_mst().getBig_name());
        comSuggest.setDomain(arrow.framework.util.StringUtils.extractDomain(com.getCom_url()));
        comSuggest.setDomainExt(arrow.framework.util.StringUtils.extractDomainExt(com.getCom_url()));
        return comSuggest;
    }

    /**
     * Push all data.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void pushAllData() throws IOException {
        this.createAllIndex();
        this.pushAllCompanyMstInfomation();
        this.pushAllCustomerInformation();
    }

    /**
     * Creates the all index.
     */
    private void createAllIndex() {
        // custom
        this.createCustomerInfoViewIndex();

        try {
            // company
            this.createCompanyIndex();
        } catch (IOException ioe) {
            this.log.debug(ioe);
        }

    }

    /**
     * Creates the customer info view index.
     */
    private void createCustomerInfoViewIndex() {
        this.deleteExistIndex(ElasticSearchConstants.CustomerInfoView.INDEX_VALUE);
        this.client.admin().indices()
            .create(new CreateIndexRequest(ElasticSearchConstants.CustomerInfoView.INDEX_VALUE)).actionGet();
    }

    /**
     * Creates the company index.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void createCompanyIndex() throws IOException {
        this.deleteExistIndex(ElasticSearchConstants.Company_mst.INDEX_VALUE);
        CreateIndexRequestBuilder createIndexRequestBuilder =
            this.client.admin().indices().prepareCreate(ElasticSearchConstants.Company_mst.INDEX_VALUE);

        final String settingBuilder =
            this.loadConfigToFile(ElasticSearchConstants.ElasticSearchFilePath.SETTING_PATH
                + ElasticSearchConstants.ElasticSearchFilePath.COMPANY_MST_SETTING);

        createIndexRequestBuilder.setSettings(settingBuilder);
        createIndexRequestBuilder.execute().actionGet();
        final String mappingBuilder =
            this.loadConfigToFile(ElasticSearchConstants.ElasticSearchFilePath.MAPPING_PATH
                + ElasticSearchConstants.ElasticSearchFilePath.COMPANY_MST_MAPPING);
        PutMappingRequestBuilder pmrb =
            this.client.admin().indices().preparePutMapping(ElasticSearchConstants.Company_mst.INDEX_VALUE)
                .setType(ElasticSearchConstants.Company_mst.TYPE_VALUE);
        pmrb.setSource(mappingBuilder);
        pmrb.execute().actionGet();
    }

    /**
     * get client.
     *
     * @return the client
     */
    public Client getClient() {
        return this.client;
    }

    /**
     * Sets the client.
     *
     * @param client the new client
     */
    public void setClient(final Client client) {
        this.client = client;
    }

    /**
     * Close connection.
     */
    @PreDestroy
    public void closeConnectionToElasticServer() {
        this.client.close();
    }

    /**
     * Gets the all daily reports.
     *
     * @return the all daily reports
     */
    public List<Daily_Report_View> getAllDailyReports() {
        SearchResponse response =
            this.client.prepareSearch("dailyreports").setQuery(QueryBuilders.matchAllQuery()).setSize(1000).execute()
                .actionGet();
        SearchHit[] hits = response.getHits().getHits();
        List<Daily_Report_View> listDailyReports = new ArrayList<>();
        for (int i = 0; i < hits.length; i++) {
            Daily_Report_View srcReport = new Daily_Report_View();
            try {
                srcReport = (Daily_Report_View) JsonUtils.convertJsonToObject(hits[i].getSourceAsString(), srcReport);
                Daily_Report_View desReport = new Daily_Report_View(srcReport.getId());
                BeanCopier.copy(srcReport, desReport);
                listDailyReports.add(desReport);
            } catch (final IOException e) {
                this.log.debug(e.getMessage());
            }
        }
        return listDailyReports;
    }

    /**
     * Push data to elastic server.
     *
     * @param indexName the index name
     * @param typeName the type name
     * @param mapInput the map input
     */
    public void pushDataToElasticServer(final String indexName, final String typeName,
        final Map<String, ? extends Abstract_entity> mapInput) {

        BulkRequestBuilder bulkRequest = this.getClient().prepareBulk();
        mapInput
            .forEach((key, value) -> {
                try {
                    bulkRequest.add(this.client.prepareIndex(indexName, typeName, key).setSource(
                        JsonUtils.getJson(value)));
                } catch (final Exception e) {
                    this.log.debug(e);
                }
            });
        bulkRequest.setRefresh(true);
        bulkRequest.execute().actionGet();
    }

    /**
     * Push new data to elastic server.
     *
     * @param indexName the index name
     * @param typeName the type name
     * @param listInput the list input
     */
    public void pushNewDataToElasticServer(final String indexName, final String typeName,
        final Map<String, ? extends Abstract_entity> listInput) {

        BulkRequestBuilder bulkRequest = this.getClient().prepareBulk();
        try {
            for (Map.Entry<String, ? extends Abstract_entity> entry : listInput.entrySet()) {
                bulkRequest.add(this.client.prepareIndex(indexName, typeName, entry.getKey()).setSource(
                    JsonUtils.getJson(entry.getValue())));
            }
        } catch (final Exception e) {
            this.log.debug(e);
        }

        bulkRequest.execute().actionGet();
    }

    /**
     * Update data to elastic server.
     *
     * @param indexName the index name
     * @param typeName the type name
     * @param mapInput the map input
     */
    public void updateDataToElasticServer(final String indexName, final String typeName,
        final Map<String, ? extends Abstract_entity> mapInput) {
        BulkRequestBuilder bulkReB = this.client.prepareBulk();
        mapInput.forEach((key, value) -> {
            try {
                UpdateRequest updateRequest = this.addUpsertRequest(indexName, typeName, value, key);
                bulkReB.add(updateRequest);
            } catch (final JsonProcessingException e) {
                this.log.debug(e.getMessage(), e);
                return;
            }
        });
        if (bulkReB.request().requests().size() > 0) {
            bulkReB.setRefresh(true);
            bulkReB.execute().actionGet();
        }
    }

    /**
     * Creates the san card data index.
     *
     * @param indexValue the index value
     * @param settingPath the setting path
     * @param sanCardDataSetting the san card data setting
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void createDataIndex(final String indexValue, final String settingPath, final String sanCardDataSetting)
        throws IOException {
        if (!this.checkIndexExisted(indexValue)) {
            CreateIndexRequestBuilder createIndexRequestBuilder =
                this.client.admin().indices().prepareCreate(indexValue);

            final String settingBuilder = this.loadConfigToFile(settingPath + sanCardDataSetting);

            createIndexRequestBuilder.setSettings(settingBuilder);
            createIndexRequestBuilder.execute().actionGet();
        }

    }

    /**
     * Find all san card data with same id and name.
     *
     * @param idNameCard the id name card
     * @param nameCompany the name company
     * @return the list
     */
    public List<Map.Entry<Integer, String>> findAllSanCardDataWithSameIdAndName(String idNameCard,
        final String nameCompany) {
        SearchResponse response;
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if (StringUtils.isNotEmpty(idNameCard)) {
            idNameCard = idNameCard.toUpperCase().trim();
            qb =
                qb.must(QueryBuilders
                    .boolQuery()
                    .should(
                        QueryBuilders.prefixQuery(ElasticSearchConstants.San_card_data.FIELD_ID_CARD, idNameCard)
                            .boost(2.0f))
                    .should(
                        QueryBuilders.wildcardQuery(ElasticSearchConstants.San_card_data.FIELD_ID_CARD,
                            ElasticSearchConstants.CHARACTER_SEARCH_LIKE + idNameCard
                                + ElasticSearchConstants.CHARACTER_SEARCH_LIKE)));
            if (StringUtils.isNotEmpty(nameCompany)) {
                qb =
                    qb.must(QueryBuilders
                        .boolQuery()
                        .should(
                            QueryBuilders.prefixQuery(ElasticSearchConstants.San_card_data.FIELD_NAME_COMPANY,
                                nameCompany).boost(2.0f))
                        .should(
                            (QueryBuilders.wildcardQuery(ElasticSearchConstants.San_card_data.FIELD_NAME_COMPANY,
                                ElasticSearchConstants.CHARACTER_SEARCH_LIKE + nameCompany
                                    + ElasticSearchConstants.CHARACTER_SEARCH_LIKE))));
            }
            qb =
                qb.mustNot(QueryBuilders.termQuery(ElasticSearchConstants.San_card_data.FIELD_ENUM_INFO_VALIDATION,
                    ElasticSearchConstants.San_card_data.ENUM_INFO_VALIDATION_STATUS_DELETE));

            response =
                this.client
                    .prepareSearch(ElasticSearchConstants.San_card_data.INDEX_VALUE)
                    .setQuery((qb))
                    .setSize(ElasticSearchConstants.San_card_data.MAX_SIZE)
                    .addFields(ElasticSearchConstants.San_card_data.FIELD_ID_COMPANY_INT,
                        ElasticSearchConstants.San_card_data.FIELD_NAME_COMPANY).execute().actionGet();
        } else {
            qb =
                qb.must(QueryBuilders
                    .boolQuery()
                    .should(
                        QueryBuilders.prefixQuery(ElasticSearchConstants.San_com_info.FIELD_NAME_COMPANY, nameCompany)
                            .boost(2.0f))
                    .should(
                        (QueryBuilders.wildcardQuery(ElasticSearchConstants.San_com_info.FIELD_NAME_COMPANY,
                            ElasticSearchConstants.CHARACTER_SEARCH_LIKE + nameCompany
                                + ElasticSearchConstants.CHARACTER_SEARCH_LIKE))));
            response =
                this.client
                    .prepareSearch(ElasticSearchConstants.San_com_info.INDEX_VALUE)
                    .setQuery((qb))
                    .setSize(ElasticSearchConstants.San_card_data.MAX_SIZE)
                    .addFields(ElasticSearchConstants.San_com_info.FIELD_ID_COMPANY_INT,
                        ElasticSearchConstants.San_com_info.FIELD_NAME_COMPANY).execute().actionGet();
        }


        SearchHit[] hits = response.getHits().getHits();
        List<Map.Entry<Integer, String>> pairList = new ArrayList<>();
        for (int i = 0; i < hits.length; i++) {
            // list.add(JsonUtils.toEntity(hits[i].getSourceAsString(), San_card_data.class));
            Map.Entry<Integer, String> pair =
                new AbstractMap.SimpleEntry<>(hits[i].getFields().get("id_company").getValue(), hits[i].getFields()
                    .get("name_company").getValue());
            pairList.add(pair);
        }
        return pairList;
    }


    /**
     * Find all san card data with same id and name.
     *
     * @param idNameCard the id name card
     * @param nameCompany the name company
     * @return the list
     */
    public List<Map.Entry<Integer, String>> findAllSanCardDataWithSameIdAndNameAndIdSanCom(String idNameCard,
        final String nameCompany, final String idSanCom) {
        SearchResponse response;
        BoolQueryBuilder qb = QueryBuilders.boolQuery();
        if (StringUtils.isNotEmpty(idNameCard)) {
            idNameCard = idNameCard.toUpperCase().trim();
            qb =
                qb.must(QueryBuilders
                    .boolQuery()
                    .should(
                        QueryBuilders.prefixQuery(ElasticSearchConstants.San_card_data.FIELD_ID_CARD, idNameCard)
                            .boost(2.0f))
                    .should(
                        QueryBuilders.wildcardQuery(ElasticSearchConstants.San_card_data.FIELD_ID_CARD,
                            ElasticSearchConstants.CHARACTER_SEARCH_LIKE + idNameCard
                                + ElasticSearchConstants.CHARACTER_SEARCH_LIKE)));
            if (StringUtils.isNotEmpty(nameCompany)) {
                qb =
                    qb.must(QueryBuilders
                        .boolQuery()
                        .should(
                            QueryBuilders.prefixQuery(ElasticSearchConstants.San_card_data.FIELD_NAME_COMPANY,
                                nameCompany).boost(2.0f))
                        .should(
                            (QueryBuilders.wildcardQuery(ElasticSearchConstants.San_card_data.FIELD_NAME_COMPANY,
                                ElasticSearchConstants.CHARACTER_SEARCH_LIKE + nameCompany
                                    + ElasticSearchConstants.CHARACTER_SEARCH_LIKE))));
            }
            if (StringUtils.isNotEmpty(idSanCom)) {
                qb =
                    qb.must(QueryBuilders.boolQuery().should(
                        (QueryBuilders.termQuery(ElasticSearchConstants.San_card_data.FIELD_ID_COMPANY_INT, idSanCom))));
            }
            qb =
                qb.mustNot(QueryBuilders.termQuery(ElasticSearchConstants.San_card_data.FIELD_ENUM_INFO_VALIDATION,
                    ElasticSearchConstants.San_card_data.ENUM_INFO_VALIDATION_STATUS_DELETE));

            response =
                this.client
                    .prepareSearch(ElasticSearchConstants.San_card_data.INDEX_VALUE)
                    .setQuery((qb))
                    .setSize(ElasticSearchConstants.San_card_data.MAX_SIZE)
                    .addFields(ElasticSearchConstants.San_card_data.FIELD_ID_COMPANY_INT,
                        ElasticSearchConstants.San_card_data.FIELD_NAME_COMPANY).execute().actionGet();
        } else {
            if (StringUtils.isNotEmpty(nameCompany)) {
                qb =
                    qb.must(QueryBuilders
                        .boolQuery()
                        .should(
                            QueryBuilders.prefixQuery(ElasticSearchConstants.San_com_info.FIELD_NAME_COMPANY,
                                nameCompany).boost(2.0f))
                        .should(
                            (QueryBuilders.wildcardQuery(ElasticSearchConstants.San_com_info.FIELD_NAME_COMPANY,
                                ElasticSearchConstants.CHARACTER_SEARCH_LIKE + nameCompany
                                    + ElasticSearchConstants.CHARACTER_SEARCH_LIKE))));
            }
            if (StringUtils.isNotEmpty(idSanCom)) {
                qb =
                    qb.must(QueryBuilders.boolQuery().should(
                        (QueryBuilders.termQuery(ElasticSearchConstants.San_com_info.FIELD_ID_COMPANY_INT, idSanCom))));
            }
            qb =
                qb.mustNot(QueryBuilders.termQuery(ElasticSearchConstants.San_card_data.FIELD_ENUM_INFO_VALIDATION,
                    ElasticSearchConstants.San_card_data.ENUM_INFO_VALIDATION_STATUS_DELETE));
            response =
                this.client
                    .prepareSearch(ElasticSearchConstants.San_com_info.INDEX_VALUE)
                    .setQuery((qb))
                    .setSize(ElasticSearchConstants.San_card_data.MAX_SIZE)
                    .addFields(ElasticSearchConstants.San_com_info.FIELD_ID_COMPANY_INT,
                        ElasticSearchConstants.San_com_info.FIELD_NAME_COMPANY).execute().actionGet();
        }


        SearchHit[] hits = response.getHits().getHits();
        List<Map.Entry<Integer, String>> pairList = new ArrayList<>();
        for (int i = 0; i < hits.length; i++) {
            // list.add(JsonUtils.toEntity(hits[i].getSourceAsString(), San_card_data.class));
            Map.Entry<Integer, String> pair =
                new AbstractMap.SimpleEntry<>(hits[i].getFields().get("id_company").getValue(), hits[i].getFields()
                    .get("name_company").getValue());
            pairList.add(pair);
        }
        return pairList;
    }

    /**
     * Gets the list san com live stat by list id company.
     *
     * @param listId the list id
     * @return the list san com live stat by list id company
     */
    public Map<Integer, San_com_live_stat> getListSanComLiveStatByListIdCompany(final List<Integer> listId) {
        List<String> listIdString = listId.stream().map(id -> id.toString()).collect(Collectors.toList());
        Map<Integer, San_com_live_stat> map = new HashMap<>();
        SearchResponse response =
            this.client.prepareSearch(ElasticSearchConstants.San_com_live_stat.INDEX_VALUE)
                .setQuery(QueryBuilders.idsQuery().ids(listIdString))
                .setSize(ElasticSearchConstants.San_card_data.MAX_SIZE).execute().actionGet();
        SearchHit[] hits = response.getHits().getHits();
        if ((hits == null) || (hits.length == 0)) {
            return null;
        }
        try {
            for (int i = 0; i < hits.length; i++) {
                San_com_live_stat temp = JsonUtils.toEntity(hits[i].getSourceAsString(), San_com_live_stat.class);
                map.put(temp.getId_company(), temp);
            }
        } catch (IOException ioe) {
            this.log.debug(ioe);
            return null;
        }

        return map;

    }


    /** The addresspoint_mst repository. */
    // test perfomance 18/5
    @Inject
    Addresspoint_mstRepository addresspoint_mstRepository;

    /** The employee_mst repository. */
    @Inject
    Employee_mstRepository employee_mstRepository;

    /**
     * Push addres point mst employee mst to elastic search.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void pushAddresPointMstEmployeeMstToElasticSearch() throws IOException {
        try {
            List<Addresspoint_mst> listAddressPointMst = this.addresspoint_mstRepository.findAll();
            List<Employee_mst> listEmployeeMst = this.employee_mstRepository.findAll();
            BulkRequestBuilder bulkRequest = this.getClient().prepareBulk();
            for (int i = 0; i < listAddressPointMst.size(); i++) {
                try {
                    bulkRequest.add(this.client.prepareIndex("addresspoint_mst", "addresspoint_mst",
                        listAddressPointMst.get(i).getAdp_code()).setSource(
                        JsonUtils.getJson(listAddressPointMst.get(i))));
                } catch (final Exception e) {
                    this.log.debug(e);
                }
            }
            for (int i = 0; i < listEmployeeMst.size(); i++) {
                try {
                    bulkRequest.add(this.client.prepareIndex("employee_mst", "employee_mst",
                        String.valueOf(listEmployeeMst.get(i).getEmp_code())).setSource(
                        JsonUtils.getJson(listEmployeeMst.get(i))));
                } catch (final Exception e) {
                    this.log.debug(e);
                }
            }
            bulkRequest.execute().actionGet();
        } catch (final NoNodeAvailableException e) {
            this.log.debug(e);
        }
    }

    /**
     * Find all data in index.
     *
     * @param index the index
     * @return the list
     */
    public List<Addresspoint_mst> findAllDataInIndex(final String index) {
        SearchResponse response =
            this.client.prepareSearch(index).setQuery(QueryBuilders.matchAllQuery()).setSize(10000).execute()
                .actionGet();
        SearchHit[] hits = response.getHits().getHits();
        if (ArrayUtils.isEmpty(hits)) {
            return null;
        }
        List<Addresspoint_mst> listResult = new ArrayList<>();
        for (int i = 0; i < hits.length; i++) {
            try {
                Addresspoint_mst temp = new Addresspoint_mst(hits[i].getId());
                Addresspoint_mst temp2 = JsonUtils.toEntity(hits[i].getSourceAsString(), Addresspoint_mst.class);
                BeanCopier.copy(temp2, temp);
                listResult.add(temp);
            } catch (IOException ioE) {
                this.log.debug(ioE);
                return null;
            }
        }
        return listResult;
    }


    /**
     * Find employee with addrespoint code.
     *
     * @param index the index
     * @param adp_code the adp_code
     * @param keyword the keyword
     * @param field the field
     * @return the list
     */
    public List<Employee_mst> findEmployeeWithAddrespointCode(final String index, final String adp_code,
        final String keyword, final String field) {
        SearchResponse response = null;
        if (StringUtils.isEmpty(keyword)) {
            response =
                this.client.prepareSearch(index).setQuery(QueryBuilders.termQuery("emp_adpcode", adp_code))
                    .setSize(10000).execute().actionGet();
        } else {
            response =
                this.client
                    .prepareSearch(index)
                    .setQuery(
                        QueryBuilders
                            .boolQuery()
                            .must(QueryBuilders.termQuery("emp_adpcode", adp_code))
                            .must(
                                QueryBuilders.boolQuery().should(QueryBuilders.prefixQuery(field, keyword).boost(5.0f))
                                    .should(QueryBuilders.prefixQuery(field, "*" + keyword + "*")))).setSize(10000)
                    .execute().actionGet();
        }
        SearchHit[] hits = response.getHits().getHits();
        if (ArrayUtils.isEmpty(hits)) {
            return null;
        }
        List<Employee_mst> listResult = new ArrayList<>();
        for (int i = 0; i < hits.length; i++) {
            try {
                Employee_mst temp = new Employee_mst(Integer.parseInt((hits[i].getId())));
                Employee_mst temp2 = JsonUtils.toEntity(hits[i].getSourceAsString(), Employee_mst.class);
                BeanCopier.copy(temp2, temp);
                listResult.add(temp);
            } catch (IOException ioE) {
                this.log.debug(ioE);
                return null;
            }
        }
        return listResult;

    }

    /**
     * Find with key word in index.
     *
     * @param index the index
     * @param keyword the keyword
     * @param field the field
     * @return the list
     */
    public List<Addresspoint_mst> findWithKeyWordInIndex(final String index, final String keyword, final String field) {

        SearchResponse response =
            this.client
                .prepareSearch(index)
                .setQuery(
                    QueryBuilders.boolQuery().should(QueryBuilders.prefixQuery(field, keyword).boost(5.0f))
                        .should(QueryBuilders.prefixQuery(field, "*" + keyword + "*"))).setSize(10000).execute()
                .actionGet();
        SearchHit[] hits = response.getHits().getHits();
        if (ArrayUtils.isEmpty(hits)) {
            return null;
        }
        List<Addresspoint_mst> listResult = new ArrayList<>();
        for (int i = 0; i < hits.length; i++) {
            try {
                Addresspoint_mst temp = new Addresspoint_mst(hits[i].getId());
                Addresspoint_mst temp2 = JsonUtils.toEntity(hits[i].getSourceAsString(), Addresspoint_mst.class);
                BeanCopier.copy(temp2, temp);
                listResult.add(temp);
            } catch (IOException ioE) {
                this.log.debug(ioE);
                return null;
            }
        }
        return listResult;

    }

    /**
     * Gets the mapfind all data in index.
     *
     * @param <T> the generic type
     * @param <H> the generic type
     * @param indexValue the index value
     * @param entityClazz the entity clazz
     * @param fieldMapKey the field map key
     * @return the mapfind all data in index
     */
    public <T, H extends Abstract_entity> Map<T, H> getMapfindAllDataInIndex(final String indexValue,
        final Class<H> entityClazz, final String fieldMapKey) {
        Map<T, H> allData = new HashMap<>();
        SearchResponse response =
            this.client.prepareSearch(indexValue).setQuery(QueryBuilders.matchAllQuery()).setSize(1).execute()
                .actionGet();
        SearchHit[] hits = response.getHits().getHits();
        if ((hits == null) || (hits.length == 0)) {
            return new HashMap<>();
        }

        try {
            for (int i = 0; i < hits.length; i++) {
                H object = JsonUtils.toEntity(hits[i].getSourceAsString(), entityClazz);
                allData.put((T) hits[i].getSource().get(fieldMapKey), object);
            }
            return allData;
        } catch (IOException ioe) {
            this.log.debug(ioe);
            return new HashMap<>();
        }

    }
}
