package arrow.businesstraceability.control.bean.debug;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.constant.ElasticSearchConstants;
import arrow.businesstraceability.control.bean.debug.CompanyClusterer.PointStatus;
import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;
import arrow.framework.util.FileUtils;
import arrow.framework.util.JsonUtils;

public class DebugTestDBScanTest {
    private static final BaseLogger logger = BaseLoggerProducer.getLogger("DBScan");

    private Client client;

    private long startTime;

    private Trie<String, IndexedCluster<Company_mst_suggest>> processStep1(
        final List<Company_mst_suggest> listComWithUrl) {
        Trie<String, IndexedCluster<Company_mst_suggest>> mapUrlAndClusters = new PatriciaTrie<>();
        AtomicInteger clusterIndex = new AtomicInteger(1);
        listComWithUrl.parallelStream().forEach(com -> this.applyCluster(com, mapUrlAndClusters, clusterIndex));
        return mapUrlAndClusters;
    }

    private void applyCluster(final Company_mst_suggest com,
        final Trie<String, IndexedCluster<Company_mst_suggest>> mapUrlAndClusters, final AtomicInteger clusterIndex) {
        if (!mapUrlAndClusters.containsKey(com.getDomain())) {
            IndexedCluster<Company_mst_suggest> cluster = new IndexedCluster<>(clusterIndex.getAndIncrement());
            cluster.addPoint(com);
            mapUrlAndClusters.put(com.getDomain(), cluster);
        } else {
            mapUrlAndClusters.get(com.getDomain()).addPoint(com);
        }
    }

    // @Test
    public void test() throws SQLException, ClassNotFoundException {
        try {
            this.startTime = System.currentTimeMillis();
            DebugTestDBScanTest.logger.debugln("Start Time: %d", this.startTime);
            this.client = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(
                    InetAddress.getByName(ElasticSearchConstants.ElasticsearchProperties.HOST_VALUE),
                    ElasticSearchConstants.ElasticsearchProperties.PORT_VALUE));
            DebugTestDBScanTest.logger.debugln("Create client took: %d ms", this.getDuration());
            List<Company_mst_suggest> allCompanies = this.getAllCompanies();
            List<Company_mst_suggest> listComWithUrl = new ArrayList<>();
            List<Company_mst_suggest> listComWithNotExistUrl = new ArrayList<>();
            for (Company_mst_suggest com : allCompanies) {
                if (StringUtils.isEmpty(com.getDomain())) {
                    listComWithNotExistUrl.add(com);
                } else {
                    listComWithUrl.add(com);
                }
            }

            DebugTestDBScanTest.logger.info("Start DBScan with " + listComWithUrl.size() + " records. %n");
            DebugTestDBScanTest.logger.debug("From Code: %s => To Code: %s \n",
                listComWithUrl.get(0).getCom_company_code(),
                listComWithUrl.get(listComWithUrl.size() - 1).getCom_company_code());


            // step 1 : exist url
            DebugTestDBScanTest.logger.info("Begin step 1 ...%n");
            List<IndexedCluster<Company_mst_suggest>> listHighConfidentResults = new ArrayList<>();
            Trie<String, IndexedCluster<Company_mst_suggest>> mapUrlAndClusters = this.processStep1(listComWithUrl);

            for (IndexedCluster<Company_mst_suggest> cluster : mapUrlAndClusters.values()) {
                if (cluster.getPoints().size() > 1) {
                    cluster.setConfidentLevel(3);
                    listHighConfidentResults.add(cluster);
                }
            }

            DebugTestDBScanTest.logger.infof("Finish step 1 .");
            DebugTestDBScanTest.logger.infof("Took: %d ms", this.getDuration());

            // this.printResult(new ArrayList<>(mapUrlAndClusters.values()), Collections.emptyList());
            DebugTestDBScanTest.logger.infof("Begin step 2 ...");

            // step 2 : not exist url
            List<IndexedCluster<Company_mst_suggest>> listMediumConfidentResults = new ArrayList<>();
            Map<String, PointStatus> addedPoint = new HashMap<>();
            List<Company_mst_suggest> listNoise = this.getListNoiseByPopListComWithNotExistUrlWhenAddCluster(
                listComWithNotExistUrl, mapUrlAndClusters, addedPoint, listMediumConfidentResults);
            DebugTestDBScanTest.logger.infof("Finish step 2 .");
            DebugTestDBScanTest.logger.infof("Took: %d ms", this.getDuration());

            // this.printResult(new ArrayList<>(mapUrlAndClusters.values()), listNoise);

            DebugTestDBScanTest.logger.infof("Begin step 3 ...");
            double minPoints = 2.0;
            double eps = 1.0;
            CompanyClusterer clusterer = new CompanyClusterer(eps, minPoints, new CompanyDistanceMeasure());
            // clusterer.setVisited(addedPoint);
            clusterer.setResultSize(10000);
            clusterer.setWeightOfName(0.3f);
            clusterer.setWeightOfUrl(0.6f);
            clusterer.setWeightOfNameF(0.1f);
            List<IndexedCluster<Company_mst_suggest>> listLowConfidentResults = clusterer.cluster(listNoise);
            DebugTestDBScanTest.logger.infof("Finish step 3 ...");
            DebugTestDBScanTest.logger.infof("Took: %d ms", this.getDuration());
            // listLowConfidentResults.addAll(mapUrlAndClusters.values());
            // this.printResult(listLowConfidentResults, new ArrayList<>(clusterer.getListNoises().values()));
            long runningTime = this.getDuration();
            DebugTestDBScanTest.logger.debugln("======================= RESULT ==============================");
            DebugTestDBScanTest.logger.debugln("========== HIGH CONFIDENT DUPLICATED RESULTS ================");
            this.printListCluster(listHighConfidentResults);
            DebugTestDBScanTest.logger.debugln("========== MEDIUM CONFIDENT DUPLICATED RESULTS ================");
            this.printListCluster(listMediumConfidentResults);
            DebugTestDBScanTest.logger.debugln("========== LOW CONFIDENT DUPLICATED RESULTS ================");
            this.printListCluster(listLowConfidentResults);

            DebugTestDBScanTest.logger.infof("Took: %d ms", runningTime);
            this.client.close();
        } catch (final IOException e1) {
            DebugTestDBScanTest.logger.debug(e1);
        }
    }

    private void printListCluster(final List<IndexedCluster<Company_mst_suggest>> listHighConfidentResults) {
        for (IndexedCluster<Company_mst_suggest> cluster : listHighConfidentResults) {
            DebugTestDBScanTest.logger.debug(this.printOutCluster(cluster));
        }
    }

    private List<Company_mst_suggest> getAllCompanies() throws JsonParseException, JsonMappingException, IOException {
        return this.getListCompanyCluster(QueryBuilders.matchAllQuery());
    }

    private long getDuration() {
        long duration = System.currentTimeMillis() - this.startTime;
        this.startTime = System.currentTimeMillis();
        return duration;
    }

    private List<Company_mst_suggest> getListNoiseByPopListComWithNotExistUrlWhenAddCluster(
        final List<Company_mst_suggest> listComMissingUrl,
        final Trie<String, IndexedCluster<Company_mst_suggest>> mapUrlAndClusters,
        final Map<String, PointStatus> addedPoint,
        final List<IndexedCluster<Company_mst_suggest>> listMediumConfidentResults)
            throws JsonParseException, JsonMappingException, IOException {
        ListIterator<Company_mst_suggest> iterators = listComMissingUrl.listIterator();
        AtomicInteger clusterIndex = new AtomicInteger(1);
        while (iterators.hasNext()) {
            Company_mst_suggest com = iterators.next();
            Company_mst_suggest similar = this.getDomainRepresent(com);
            if (similar == null) {
                continue;
            }
            if (mapUrlAndClusters.containsKey(similar.getDomain())) {
                // IndexedCluster<Company_mst_suggest> cluster = mapUrlAndClusters.get(similar.getDomain());

                IndexedCluster<Company_mst_suggest> newCluster = new IndexedCluster<>(clusterIndex.getAndIncrement());
                newCluster.addPoint(similar);
                newCluster.addPoint(com);
                com.setSrcPoint(similar);
                listMediumConfidentResults.add(newCluster);
                // cluster.addPoint(com);
                addedPoint.put(com.getCom_company_code(), PointStatus.PART_OF_CLUSTER);
                iterators.remove();
            }
        }
        return listComMissingUrl;
    }

    private Company_mst_suggest getDomainRepresent(final Company_mst_suggest com)
        throws JsonParseException, JsonMappingException, IOException {
        List<Company_mst_suggest> listCom =
            this.getListCompanyCluster(this.getQueryByNameAndFurigana(com.getName(), com.getFurigana()));

        if (!CollectionUtils.isEmpty(listCom)) {
            Optional<Company_mst_suggest> match =
                listCom.parallelStream().filter(item -> StringUtils.isNotEmpty(item.getDomain())).findFirst();
            if (match.isPresent()) {
                return match.get();
            }
        }
        return null;
    }

    private QueryBuilder getQueryByNameAndFurigana(final String name, final String furigana) {
        BoolQueryBuilder b = QueryBuilders.boolQuery();
        b.must(QueryBuilders.matchQuery("name", name).prefixLength(1).fuzziness(Fuzziness.AUTO)
            .minimumShouldMatch("4<90%"));
        if (StringUtils.isNotEmpty(furigana)) {
            b.must(QueryBuilders.matchQuery("furigana", furigana).prefixLength(1).fuzziness(Fuzziness.AUTO)
                .minimumShouldMatch("4<90%"));
        }
        b.must(this.getQueryWithUrl());
        return b;
    }

    private QueryBuilder getQueryWithUrl() {
        return QueryBuilders.existsQuery("com_url");
    }

    private SearchRequestBuilder searchRequestBuilder = null;

    private List<Company_mst_suggest> getListCompanyCluster(final QueryBuilder queryBuilder)
        throws JsonParseException, JsonMappingException, IOException {
        try {
            DebugTestDBScanTest.logger.tracef("Query data");
            SearchResponse response = this.getSearchRequestBuilder().setQuery(queryBuilder).execute().actionGet();
            SearchHit[] hits = response.getHits().getHits();

            DebugTestDBScanTest.logger.tracef("Query data took %d ms", this.getDuration());

            List<Company_mst_suggest> listCom = new ArrayList<>();
            for (SearchHit searchHit : hits) {
                listCom.add(JsonUtils.toEntity(searchHit.getSourceAsString(), Company_mst_suggest.class));
            }
            DebugTestDBScanTest.logger.tracef("Convert data took %d ms", this.getDuration());
            return listCom;
        } catch (final UnknownHostException e) {
            DebugTestDBScanTest.logger.debug(e);
            return null;
        }
    }

    private String printOutCluster(final IndexedCluster<Company_mst_suggest> cluster) {
        StringBuilder result = new StringBuilder();
        result.append("============================================================================\n");
        result.append("Cluster Id:" + cluster.getIndex() + "\n");
        result.append("Cluster size:" + cluster.getPoints().size() + "\n");
        cluster.getPoints().stream().forEachOrdered(point -> result.append(point.printOut()));
        result.append("============================================================================\n");
        return result.toString();
    }

    public static void main(final String[] args) throws ClassNotFoundException, SQLException, IOException {
        boolean isPushData = true;
        DebugTestDBScanTest test = new DebugTestDBScanTest();
        if (isPushData) {
            test.pushData();
        } else {
            test.test();
        }
    }

    protected void pushData() throws ClassNotFoundException, SQLException, IOException {
        String indexName = "company_msts";
        String typeName = "company_mst";


        Client client = TransportClient.builder().build()
            .addTransportAddress(new InetSocketTransportAddress(
                InetAddress.getByName(ElasticSearchConstants.ElasticsearchProperties.HOST_VALUE),
                ElasticSearchConstants.ElasticsearchProperties.PORT_VALUE));

        final IndicesExistsResponse res = client.admin().indices().prepareExists(indexName).execute().actionGet();

        if (res.isExists()) {
            DebugTestDBScanTest.logger.debugln("Deleting Index %s, Type %s", indexName, typeName);
            final DeleteIndexRequestBuilder delIdx = client.admin().indices().prepareDelete(indexName);
            delIdx.execute().actionGet();
        }

        DebugTestDBScanTest.logger.debugln("Creating Index %s", indexName);
        CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(indexName);


        final String settingBuilder =
            FileUtils.loadResourceFile(ElasticSearchConstants.ElasticSearchFilePath.SETTING_PATH
                + ElasticSearchConstants.ElasticSearchFilePath.COMPANY_MST_SETTING);

        createIndexRequestBuilder.setSettings(settingBuilder);
        createIndexRequestBuilder.execute().actionGet();
        final String mappingBuilder =
            FileUtils.loadResourceFile(ElasticSearchConstants.ElasticSearchFilePath.MAPPING_PATH
                + ElasticSearchConstants.ElasticSearchFilePath.COMPANY_MST_MAPPING);
        PutMappingRequestBuilder pmrb = client.admin().indices().preparePutMapping(indexName).setType(typeName);
        pmrb.setSource(mappingBuilder);
        pmrb.execute().actionGet();

        DebugTestDBScanTest.logger.debugln("Loading data...");
        List<Company_mst_suggest> listCompany = DebugTestDBScanTest.prepareListData("", 0);
        BulkRequestBuilder bulk = client.prepareBulk();
        for (Company_mst_suggest company_mst : listCompany) {
            try {
                bulk.add(client.prepareIndex(indexName, typeName, company_mst.getCom_company_code())
                    .setSource(JsonUtils.getJson(company_mst)));
            } catch (final JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        DebugTestDBScanTest.logger.debugln("Pushing data...", listCompany.size());
        bulk.execute().actionGet();
        DebugTestDBScanTest.logger.debugln("DONE");
    }

    private static List<Company_mst_suggest> prepareListData(final String specifiedCompanyCode, final int batchSize)
        throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");
        Connection connection =
            DriverManager.getConnection("jdbc:postgresql://localhost:5432/business_traceability", "postgres", "x");
        Statement stmt = connection.createStatement();
        String query = "SELECT C.COM_COMPANY_CODE, C.COM_COMPANY_NAME, C.COM_COMPANY_FURIGANA, "
            + " C.COM_CUSTOMER_CODE,  C.COM_URL, C.COM_INDBIG_CODE, I.BIG_NAME, "
            + " C.COM_LIMITED_CODE, C.COM_LISTED_CODE, C.COM_POINT_CODE, A.ADP_NAME "
            + " FROM COMPANY_MST C INNER JOIN INDUSTRY_BIG_MST I ON I.BIG_CODE = C.COM_INDBIG_CODE "
            + " INNER JOIN ADDRESSPOINT_MST A ON A.ADP_CODE = C.COM_POINT_CODE";

        if (StringUtils.isNotEmpty(specifiedCompanyCode)) {
            query += " WHERE com_company_code='" + specifiedCompanyCode + "' ";
        }

        query += " ORDER BY com_company_code";

        if (batchSize > 0) {
            query += " LIMIT " + batchSize;
        }

        ResultSet rs = stmt.executeQuery(query);
        List<Company_mst_suggest> listData = new ArrayList<>();
        while (rs.next()) {
            String comCode = rs.getString("com_company_code");
            String comCustomerCode = rs.getString("com_customer_code");
            String comName = rs.getString("com_company_name");
            String comNameFu = rs.getString("com_company_furigana");
            String comUrl = arrow.framework.util.StringUtils.cleanupUrl(rs.getString("com_url"));
            short comBigCode = rs.getShort("com_indbig_code");
            String bigName = rs.getString("big_name");
            short comLimitedCode = rs.getShort("com_limited_code");
            short comListedCode = rs.getShort("com_listed_code");
            String comPointCode = rs.getString("com_point_code");
            String adpName = rs.getString("adp_name");

            Company_mst_suggest cluster = new Company_mst_suggest();
            cluster.setCom_company_code(comCode);
            cluster.setCom_company_furigana(comNameFu);

            if (StringUtils.isEmpty(comNameFu)) {
                comNameFu = arrow.framework.util.StringUtils.convertToHalfWidthKata(comName);
            }
            cluster.setFurigana(comNameFu);

            cluster.setCom_company_name(comName);
            cluster.setName(arrow.framework.util.StringUtils.convertToHalfWidthKata(comName));
            cluster.setCom_customer_code(comCustomerCode);
            cluster.setCom_url(comUrl);
            String domain = arrow.framework.util.StringUtils.extractDomain(comUrl);
            cluster.setDomain(domain);
            cluster.setDomainExt(arrow.framework.util.StringUtils.extractDomainExt(comUrl));

            cluster.setCom_indbig_code(comBigCode);
            cluster.setBig_name(bigName);
            cluster.setCom_limited_code(comLimitedCode);
            cluster.setCom_listed_code(comListedCode);
            cluster.setCom_point_code(comPointCode);
            cluster.setAdp_name(adpName);

            listData.add(cluster);
        }

        stmt.close();
        connection.close();
        return listData;
    }

    public SearchRequestBuilder getSearchRequestBuilder() {
        if (this.searchRequestBuilder == null) {
            this.searchRequestBuilder = this.client.prepareSearch(CompanyClusterer.INDEX_NAME).setSize(20000);
        }
        return this.searchRequestBuilder;
    }

    public void setSearchRequestBuilder(final SearchRequestBuilder searchRequestBuilder) {
        this.searchRequestBuilder = searchRequestBuilder;
    }


}
