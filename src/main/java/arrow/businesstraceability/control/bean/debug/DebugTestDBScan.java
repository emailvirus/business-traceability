package arrow.businesstraceability.control.bean.debug;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.primefaces.context.RequestContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.constant.ElasticSearchConstants;
import arrow.businesstraceability.control.bean.debug.CompanyClusterer.PointStatus;
import arrow.framework.util.JsonUtils;

/**
 * The Class DebugTestDBScan.
 */
@Named
@SessionScoped
@ManagedBean(name = "debugTestDBScan")
public class DebugTestDBScan extends AbstractService {

    private Client client;

    private List<IndexedCluster<Company_mst_suggest>> listClusters = new ArrayList<>();

    private List<Company_mst_suggest> listNoise = new ArrayList<>();

    /**
     * Process.
     */

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

    /**
     * Gets the query by name and furigana.
     *
     * @param name the name
     * @param furigana the furigana
     * @return the query by name and furigana
     */
    private QueryBuilder getQueryByNameAndFurigana(final String name, final String furigana) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.matchQuery("name", name).prefixLength(1).fuzziness(Fuzziness.AUTO)
            .minimumShouldMatch("4<90%"));
        if (StringUtils.isNotEmpty(furigana)) {
            builder.must(QueryBuilders.matchQuery("furigana", furigana).prefixLength(1).fuzziness(Fuzziness.AUTO)
                .minimumShouldMatch("4<90%"));
        }
        builder.must(this.getQueryWithUrl());
        return builder;
    }

    private QueryBuilder getQueryWithUrl() {
        return QueryBuilders.existsQuery("com_url");
    }

    private List<Company_mst_suggest> getListCompanyCluster(final QueryBuilder queryBuilder)
        throws JsonParseException, JsonMappingException, IOException {
        try {
            SearchResponse response = this.client.prepareSearch(CompanyClusterer.INDEX_NAME).setQuery(queryBuilder)
                .setSize(20000).execute().actionGet();
            SearchHit[] hits = response.getHits().getHits();
            List<Company_mst_suggest> listCom = new ArrayList<>();
            for (SearchHit searchHit : hits) {
                Company_mst_suggest com = new Company_mst_suggest();
                com = (Company_mst_suggest) JsonUtils.convertJsonToObject(searchHit.getSourceAsString(), com);
                listCom.add(com);
            }
            return listCom;
        } catch (final UnknownHostException e) {
            this.log.debug(e);
            return null;
        }
    }

    private final Set<Company_mst_suggest> listAllCompanies = new HashSet<>();

    /**
     * Gets the list all companies.
     *
     * @return the list all companies
     */
    public Set<Company_mst_suggest> getListAllCompanies() {
        return this.listAllCompanies;
    }

    private List<Company_mst_suggest> filteredData = new ArrayList<>();

    private List<Company_mst_suggest> filteredMediumData = new ArrayList<>();

    private List<Company_mst_suggest> filteredLowData = new ArrayList<>();

    /**
     * Sets the filtered medium data.
     *
     * @param filteredMediumData the new filtered medium data
     */
    public void setFilteredMediumData(final List<Company_mst_suggest> filteredMediumData) {
        this.filteredMediumData = filteredMediumData;
    }

    /**
     * Sets the filtered low data.
     *
     * @param filteredLowData the new filtered low data
     */
    public void setFilteredLowData(final List<Company_mst_suggest> filteredLowData) {
        this.filteredLowData = filteredLowData;
    }

    /**
     * Gets the filtered medium data.
     *
     * @return the filtered medium data
     */
    public List<Company_mst_suggest> getFilteredMediumData() {
        return this.filteredMediumData;
    }

    /**
     * Gets the filtered low data.
     *
     * @return the filtered low data
     */
    public List<Company_mst_suggest> getFilteredLowData() {
        return this.filteredLowData;
    }

    /**
     * Sets the filtered data.
     *
     * @param filteredData the new filtered data
     */
    public void setFilteredData(final List<Company_mst_suggest> filteredData) {
        this.filteredData = filteredData;
    }

    private long startTime;

    private List<Company_mst_suggest> listLowConfidentPoints;

    private List<Company_mst_suggest> listHighConfidentPoints;

    private List<Company_mst_suggest> listMediumConfidentPoints;

    /**
     * Gets the list high confident points.
     *
     * @return the list high confident points
     */
    public List<Company_mst_suggest> getListHighConfidentPoints() {
        if (CollectionUtils.isEmpty(this.listHighConfidentPoints)) {
            this.listHighConfidentPoints = new ArrayList<>();
            AtomicInteger clusterIndex = new AtomicInteger(1);
            for (IndexedCluster<Company_mst_suggest> cluster : this.listHighConfidentResults) {
                cluster.getPoints().stream().forEachOrdered(point -> {
                    point.setClusterIndex(clusterIndex.get());
                    this.listHighConfidentPoints.add(point);
                });
                clusterIndex.incrementAndGet();
            }
        }
        return this.listHighConfidentPoints;

    }

    /**
     * Sets the list high confident points.
     *
     * @param listHighConfidentPoints the new list high confident points
     */
    public void setListHighConfidentPoints(final List<Company_mst_suggest> listHighConfidentPoints) {
        this.listHighConfidentPoints = listHighConfidentPoints;
    }

    private List<Company_mst_suggest> getAllCompanies() throws JsonParseException, JsonMappingException, IOException {
        return this.getListCompanyCluster(QueryBuilders.matchAllQuery());
    }

    /**
     * Process.
     *
     * @throws SQLException the SQL exception
     * @throws ClassNotFoundException the class not found exception
     */
    public void process() throws SQLException, ClassNotFoundException {

        try {
            this.startTime = System.currentTimeMillis();
            this.log.debugln("Start Time: %d", this.startTime);
            this.client = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(
                    InetAddress.getByName(ElasticSearchConstants.ElasticsearchProperties.HOST_VALUE),
                    ElasticSearchConstants.ElasticsearchProperties.PORT_VALUE));
            this.log.debugln("Create client took: %d ms", this.getDuration());
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

            this.log.info("Start DBScan with " + listComWithUrl.size() + " records. %n");
            this.log.debug("From Code: %s => To Code: %s \n", listComWithUrl.get(0).getCom_company_code(),
                listComWithUrl.get(listComWithUrl.size() - 1).getCom_company_code());


            // step 1 : exist url
            this.log.info("Begin step 1 ...%n");

            Trie<String, IndexedCluster<Company_mst_suggest>> mapUrlAndClusters = this.processStep1(listComWithUrl);
            this.listHighConfidentResults.clear();
            for (IndexedCluster<Company_mst_suggest> cluster : mapUrlAndClusters.values()) {
                if (cluster.getPoints().size() > 1) {
                    cluster.setConfidentLevel(3);
                    this.listHighConfidentResults.add(cluster);
                }
            }

            this.log.infof("Finish step 1 .");
            this.log.infof("Took: %d ms", this.getDuration());
            this.log.infof("Begin step 2 ...");

            // step 2 : not exist url

            Map<String, PointStatus> addedPoint = new HashMap<>();
            this.listMediumConfidentResults.clear();
            List<Company_mst_suggest> listNoise = this.getListNoiseByPopListComWithNotExistUrlWhenAddCluster(
                listComWithNotExistUrl, mapUrlAndClusters, addedPoint, this.listMediumConfidentResults);
            this.log.infof("Finish step 2 .");
            this.log.infof("Took: %d ms", this.getDuration());

            this.log.infof("Begin step 3 ...");
            double minPoints = 2.0;
            double eps = 1.0;
            CompanyClusterer clusterer = new CompanyClusterer(eps, minPoints, new CompanyDistanceMeasure());
            this.listLowConfidentResults = clusterer.cluster(listNoise);
            // clusterer.setVisited(addedPoint);
            clusterer.setResultSize(20000);
            clusterer.setWeightOfName(0.3f);
            clusterer.setWeightOfUrl(0.6f);
            clusterer.setWeightOfNameF(0.1f);

            this.log.infof("Finish step 3 ...");
            this.log.infof("Took: %d ms", this.getDuration());
            RequestContext.getCurrentInstance().execute("PF('globalBlockUI_js').unblock()");

        } catch (final IOException e1) {
            this.log.debug(e1);
        }

    }

    List<IndexedCluster<Company_mst_suggest>> listHighConfidentResults = new ArrayList<>();

    /**
     * Gets the list high confident results.
     *
     * @return the list high confident results
     */
    public List<IndexedCluster<Company_mst_suggest>> getListHighConfidentResults() {
        return this.listHighConfidentResults;
    }

    /**
     * Sets the list high confident results.
     *
     * @param listHighConfidentResults the new list high confident results
     */
    public void setListHighConfidentResults(final List<IndexedCluster<Company_mst_suggest>> listHighConfidentResults) {
        this.listHighConfidentResults = listHighConfidentResults;
    }

    /**
     * Gets the list low confident results.
     *
     * @return the list low confident results
     */
    public List<IndexedCluster<Company_mst_suggest>> getListLowConfidentResults() {
        return this.listLowConfidentResults;
    }

    /**
     * Sets the list low confident results.
     *
     * @param listLowConfidentResults the new list low confident results
     */
    public void setListLowConfidentResults(final List<IndexedCluster<Company_mst_suggest>> listLowConfidentResults) {
        this.listLowConfidentResults = listLowConfidentResults;
    }

    /**
     * Gets the list medium confident results.
     *
     * @return the list medium confident results
     */
    public List<IndexedCluster<Company_mst_suggest>> getListMediumConfidentResults() {
        return this.listMediumConfidentResults;
    }

    /**
     * Sets the list medium confident results.
     *
     * @param listMediumConfidentResults the new list medium confident results
     */
    public void setListMediumConfidentResults(
        final List<IndexedCluster<Company_mst_suggest>> listMediumConfidentResults) {
        this.listMediumConfidentResults = listMediumConfidentResults;
    }

    List<IndexedCluster<Company_mst_suggest>> listLowConfidentResults = new ArrayList<>();

    List<IndexedCluster<Company_mst_suggest>> listMediumConfidentResults = new ArrayList<>();

    private long getDuration() {
        long duration = System.currentTimeMillis() - this.startTime;
        this.startTime = System.currentTimeMillis();
        return duration;
    }

    /**
     * Gets the client.
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
     * Gets the list clusters.
     *
     * @return the list clusters
     */
    public List<IndexedCluster<Company_mst_suggest>> getListClusters() {
        return this.listClusters;
    }

    /**
     * Sets the list clusters.
     *
     * @param listClusters the new list clusters
     */
    public void setListClusters(final List<IndexedCluster<Company_mst_suggest>> listClusters) {
        this.listClusters = listClusters;
    }

    /**
     * Gets the output.
     *
     * @return the output
     */
    public String getOutput() {
        return this.output;
    }

    /**
     * Sets the output.
     *
     * @param output the new output
     */
    public void setOutput(final String output) {
        this.output = output;
    }

    /**
     * Gets the list noise.
     *
     * @return the list noise
     */
    public List<Company_mst_suggest> getListNoise() {
        return this.listNoise;
    }

    /**
     * Sets the list noise.
     *
     * @param listNoise the new list noise
     */
    public void setListNoise(final List<Company_mst_suggest> listNoise) {
        this.listNoise = listNoise;
    }

    /**
     * Gets the filtered data.
     *
     * @return the filtered data
     */
    public List<Company_mst_suggest> getFilteredData() {
        return this.filteredData;
    }

    /**
     * Gets the list medium confident points.
     *
     * @return the list medium confident points
     */
    public List<Company_mst_suggest> getListMediumConfidentPoints() {
        if (CollectionUtils.isEmpty(this.listMediumConfidentPoints)) {
            this.listMediumConfidentPoints = new ArrayList<>();
            AtomicInteger clusterIndex = new AtomicInteger(1);
            for (IndexedCluster<Company_mst_suggest> cluster : this.listMediumConfidentResults) {
                cluster.getPoints().stream().forEachOrdered(point -> {
                    point.setClusterIndex(clusterIndex.get());
                    this.listMediumConfidentPoints.add(point);
                });
                clusterIndex.incrementAndGet();
            }
        }
        return this.listMediumConfidentPoints;
    }

    /**
     * Sets the list medium confident points.
     *
     * @param listMediumConfidentPoints the new list medium confident points
     */
    public void setListMediumConfidentPoints(final List<Company_mst_suggest> listMediumConfidentPoints) {
        this.listMediumConfidentPoints = listMediumConfidentPoints;
    }

    /**
     * Gets the list low confident points.
     *
     * @return the list low confident points
     */
    public List<Company_mst_suggest> getListLowConfidentPoints() {
        if (CollectionUtils.isEmpty(this.listLowConfidentPoints)) {
            this.listLowConfidentPoints = new ArrayList<>();
            AtomicInteger clusterIndex = new AtomicInteger(1);
            for (IndexedCluster<Company_mst_suggest> cluster : this.listLowConfidentResults) {
                cluster.getPoints().stream().forEachOrdered(point -> {
                    point.setClusterIndex(clusterIndex.get());
                    this.listLowConfidentPoints.add(point);
                });
                clusterIndex.incrementAndGet();
            }
        }
        return this.listLowConfidentPoints;
    }

    /**
     * Sets the list low confident points.
     *
     * @param listLowConfidentPoints the new list low confident points
     */
    public void setListLowConfidentPoints(final List<Company_mst_suggest> listLowConfidentPoints) {
        this.listLowConfidentPoints = listLowConfidentPoints;
    }

    /** The output. */
    private String output;

}
