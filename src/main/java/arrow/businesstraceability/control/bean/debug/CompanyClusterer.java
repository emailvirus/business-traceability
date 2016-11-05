package arrow.businesstraceability.control.bean.debug;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.exception.ConvergenceException;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.ml.clustering.Clusterer;
import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.apache.commons.math3.util.MathUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.constant.ElasticSearchConstants;
import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;
import arrow.framework.util.JsonUtils;

/**
 * The Class CompanyClusterer.
 */
public class CompanyClusterer extends Clusterer<Company_mst_suggest> {

    /** The weight of url. */
    private float weightOfUrl = 0.7f;

    /** The Constant INDEX_NAME. */
    public static final String INDEX_NAME = "company_msts";

    /** The type name. */
    private String typeName = "company_mst";

    /** The result size. */
    private int resultSize = 1000;

    /** The eps. */
    private final double eps;

    /** The min pts. */
    private final double minPts;

    /** The client. */
    private TransportClient client;

    /** The list noises. */
    private final Trie<String, Company_mst_suggest> listNoises = new PatriciaTrie<>();

    /** The running time. */
    private long runningTime;

    /** The error. */
    private String error;

    /** The success. */
    private boolean success;

    /** The weight of name f. */
    private float weightOfNameF = 0.1f;

    /** The weight of name. */
    private float weightOfName = 0.2f;

    /** The visited. */
    private Map<String, PointStatus> visited;

    /** The Constant LOGGER. */
    private static final BaseLogger LOGGER = BaseLoggerProducer.getLogger("CompanyCluster");


    /** Status of a point during the clustering process. */
    public enum PointStatus {
        /** The point has is considered to be noise. */
        NOISE,
        /** The point is already part of a cluster. */
        PART_OF_CLUSTER
    }


    /**
     * Instantiates a new company clusterer.
     *
     * @param eps the eps
     * @param minPts the min pts
     * @param measure the measure
     * @throws NotPositiveException the not positive exception
     */
    public CompanyClusterer(final double eps, final double minPts, final DistanceMeasure measure)
        throws NotPositiveException {
        super(measure);

        if (eps < 0.0d) {
            throw new NotPositiveException(eps);
        }
        if (minPts < 0) {
            throw new NotPositiveException(minPts);
        }
        this.eps = eps;
        this.minPts = minPts;
        this.visited = new HashMap<>();
    }

    /* (non-Javadoc)
     * @see org.apache.commons.math3.ml.clustering.Clusterer#cluster(java.util.Collection)
     */
    @Override
    public List<IndexedCluster<Company_mst_suggest>> cluster(final Collection<Company_mst_suggest> points)
        throws MathIllegalArgumentException, ConvergenceException {
        // sanity checks
        MathUtils.checkNotNull(points);


        final List<IndexedCluster<Company_mst_suggest>> clusters = new ArrayList<IndexedCluster<Company_mst_suggest>>();


        try {
            this.client = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(
                    InetAddress.getByName(ElasticSearchConstants.ElasticsearchProperties.HOST_VALUE),
                    ElasticSearchConstants.ElasticsearchProperties.PORT_VALUE));
        } catch (final UnknownHostException uhe) {
            CompanyClusterer.LOGGER.debug(uhe);
        }
        try {
            long starttime = System.currentTimeMillis();
            int clusterIndex = 1;
            for (final Company_mst_suggest point : points) {
                if (this.visited.get(point.getCom_company_code()) != null) {
                    continue;
                }
                CompanyClusterer.LOGGER.tracef("Process company: %s \n", point.toString());
                final List<Company_mst_suggest> neighbors = this.getNeighbors(point, points);
                if (neighbors.size() >= this.minPts) {
                    // DBSCAN does not care about center points
                    final IndexedCluster<Company_mst_suggest> cluster = new IndexedCluster<>(clusterIndex);
                    clusters.add(this.expandCluster(cluster, point, neighbors, points, this.visited));
                    clusterIndex++;
                } else {
                    this.visited.put(point.getCom_company_code(), PointStatus.NOISE);
                    if (!this.listNoises.containsKey(point.getCom_company_code())) {
                        this.listNoises.put(point.getCom_company_code(), point);
                    }

                }
            }
            long endtime = System.currentTimeMillis();

            this.runningTime = endtime - starttime;
        } catch (IOException io) {
            this.success = false;
            this.error = io.getMessage();
        }
        this.success = true;
        this.client.close();
        return clusters;
    }


    /**
     * Expands the cluster to include density-reachable items.
     *
     * @param cluster Cluster to expand
     * @param point Point to add to cluster
     * @param neighbors List of neighbors
     * @param points the data set
     * @param visited the set of already visited points
     * @return the expanded cluster
     * @throws JsonParseException the json parse exception
     * @throws JsonMappingException the json mapping exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private IndexedCluster<Company_mst_suggest> expandCluster(final IndexedCluster<Company_mst_suggest> cluster,
        final Company_mst_suggest point, final List<Company_mst_suggest> neighbors,
        final Collection<Company_mst_suggest> points, final Map<String, PointStatus> visited)
            throws JsonParseException, JsonMappingException, IOException {
        cluster.addPoint(point);
        visited.put(point.getCom_company_code(), PointStatus.PART_OF_CLUSTER);

        List<Company_mst_suggest> seeds = new ArrayList<Company_mst_suggest>(neighbors);
        int index = 0;
        while (index < seeds.size()) {
            final Company_mst_suggest current = seeds.get(index);
            PointStatus pointStatus = visited.get(current.getCom_company_code());
            // only check non-visited points
            if ((pointStatus == null)) {
                final List<Company_mst_suggest> currentNeighbors = this.getNeighborsOfNeighbor(point, current, points);
                if (currentNeighbors.size() >= this.minPts) {
                    seeds = this.merge(seeds, currentNeighbors);
                }
            }

            if (pointStatus != PointStatus.PART_OF_CLUSTER) {
                visited.put(current.getCom_company_code(), PointStatus.PART_OF_CLUSTER);
                cluster.addPoint(current);
                this.listNoises.remove(current.getCom_company_code());
            }

            index++;
        }
        return cluster;
    }

    /**
     * Gets the neighbors of neighbor.
     *
     * @param firstPoint the first point
     * @param current the current
     * @param points the points
     * @return the neighbors of neighbor
     * @throws JsonParseException the json parse exception
     * @throws JsonMappingException the json mapping exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private List<Company_mst_suggest> getNeighborsOfNeighbor(final Company_mst_suggest firstPoint,
        final Company_mst_suggest current, final Collection<Company_mst_suggest> points)
            throws JsonParseException, JsonMappingException, IOException {
        CompanyClusterer.LOGGER.debugln("Finding Neighbors of Node:%s", current.printOut());
        CompanyClusterer.LOGGER.debugln("And, they should be 'know' Node %s, too", firstPoint.printOut());
        QueryBuilder stringQuery = this.buildQueryForNeighborsOfNeighbor(firstPoint, current);

        SearchResponse response = this.client.prepareSearch(CompanyClusterer.INDEX_NAME).setTypes(this.typeName)
            .setQuery(stringQuery).setFrom(0).setSize(this.resultSize).execute().actionGet();


        List<Company_mst_suggest> listCompanySuggest = new ArrayList<>();
        int allResult = response.getHits().getHits().length;

        CompanyClusterer.LOGGER.debugln("Found results: %d", allResult);

        if (allResult > this.resultSize) {
            CompanyClusterer.LOGGER.debugln("Using only %d result", this.resultSize);
        }
        for (int i = 0; i < Math.min(allResult, this.resultSize); i++) {
            Company_mst_suggest company =
                JsonUtils.toEntity(response.getHits().getAt(i).getSourceAsString(), Company_mst_suggest.class);
            if ((StringUtils.isEmpty(current.getCom_url()) || current.getCom_url().equals(company.getCom_url()))
                && (Math.abs(current.getCom_company_name().length() - company.getCom_company_name().length()) < 3)) {
                company.setSourcePoint(current);
                listCompanySuggest.add(company);
            }
        }


        CompanyClusterer.LOGGER.debugf("Found %d Neighbors", listCompanySuggest.size());
        return listCompanySuggest;
    }

    /**
     * Builds the query for neighbors.
     *
     * @param current the current
     * @return the query builder
     */
    private QueryBuilder buildQueryForNeightbors(final Company_mst_suggest current) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        if (StringUtils.isEmpty(current.getCom_url())) {
            builder.must(QueryBuilders.matchQuery("name", current.getName()).prefixLength(1).fuzziness(Fuzziness.AUTO)
                .minimumShouldMatch("4<90%").boost(this.getWeightOfName()));
            if (StringUtils.isNotEmpty(current.getFurigana())) {
                builder.must(QueryBuilders.matchQuery("furigana", current.getFurigana()).prefixLength(1)
                    .fuzziness(Fuzziness.AUTO).minimumShouldMatch("4<90%").boost(this.getWeightOfNameF()));
            }
            builder.must(QueryBuilders.missingQuery("com_url"));
        } else {

            // accept different domain extension
            builder.must(QueryBuilders.matchPhraseQuery("domain", current.getDomain()).boost(this.getWeightOfUrl()));
        }

        return builder;
    }

    /**
     * Builds the query for neighbors of neighbor.
     *
     * @param firstPoint the first point
     * @param current the current
     * @return the query builder
     */
    private QueryBuilder buildQueryForNeighborsOfNeighbor(final Company_mst_suggest firstPoint,
        final Company_mst_suggest current) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(this.buildQueryForNeightbors(current));
        // b.must(
        // QueryBuilders.queryStringQuery(firstPoint.getCom_company_name() + " AND " + current.getCom_company_name()));
        //
        // if (StringUtils.isNotEmpty(firstPoint.getCom_company_furigana())) {
        // b.must(QueryBuilders
        // .queryStringQuery(firstPoint.getCom_company_furigana() + " AND " + current.getCom_company_furigana()));
        // }
        // b.must(
        // QueryBuilders.matchQuery("com_company_name", firstPoint.getCom_company_name()).minimumShouldMatch("75%"));
        // b.minimumShouldMatch("2");
        return builder;
    }

    /**
     * Returns a list of density-reachable neighbors of a {@code point}.
     *
     * @param current the point to look for
     * @param points possible neighbors
     * @return the List of neighbors
     * @throws JsonParseException the json parse exception
     * @throws JsonMappingException the json mapping exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private List<Company_mst_suggest> getNeighbors(final Company_mst_suggest current,
        final Collection<Company_mst_suggest> points) throws JsonParseException, JsonMappingException, IOException {
        CompanyClusterer.LOGGER.tracef("Finding Neighbors of Node:%s", current.printOut());
        QueryBuilder stringQuery = this.buildQueryForNeightbors(current);
        long startTime = System.currentTimeMillis();
        SearchResponse response = this.client.prepareSearch(CompanyClusterer.INDEX_NAME).setTypes(this.typeName)
            .setQuery(stringQuery).setFrom(0).setSize(this.resultSize).execute().actionGet();
        CompanyClusterer.LOGGER.tracef("Query took %d ms", System.currentTimeMillis() - startTime);
        List<Company_mst_suggest> listCompanySuggest = new ArrayList<>();
        int allResult = response.getHits().getHits().length;

        CompanyClusterer.LOGGER.tracef("Found results: %d", allResult);

        if (allResult > this.resultSize) {
            CompanyClusterer.LOGGER.tracef("Using only %d result", this.resultSize);
        }

        for (int i = 0; i < Math.min(allResult, this.resultSize); i++) {
            Company_mst_suggest company =
                JsonUtils.toEntity(response.getHits().getAt(i).getSourceAsString(), Company_mst_suggest.class);
            company.setSourcePoint(current);
            listCompanySuggest.add(company);
        }
        CompanyClusterer.LOGGER.tracef("Convert data took %d ms", System.currentTimeMillis() - startTime);
        CompanyClusterer.LOGGER.tracef("Found %d Neighbors", listCompanySuggest.size());
        return listCompanySuggest;
    }


    /**
     * Merges two lists together.
     *
     * @param one first list
     * @param two second list
     * @return merged lists
     */
    private List<Company_mst_suggest> merge(final List<Company_mst_suggest> one, final List<Company_mst_suggest> two) {
        final Set<Company_mst_suggest> oneSet = new HashSet<>(one);
        for (Company_mst_suggest item : two) {
            if (!oneSet.contains(item)) {
                one.add(item);
            }
        }
        return one;
    }

    /**
     * Gets the eps.
     *
     * @return the eps
     */
    public double getEps() {
        return this.eps;
    }

    /**
     * Gets the list noises.
     *
     * @return the list noises
     */
    public Trie<String, Company_mst_suggest> getListNoises() {
        return this.listNoises;
    }

    /**
     * Gets the running time.
     *
     * @return the running time
     */
    public long getRunningTime() {
        return this.runningTime;
    }

    /**
     * Sets the running time.
     *
     * @param runningTime the new running time
     */
    public void setRunningTime(final long runningTime) {
        this.runningTime = runningTime;
    }

    /**
     * Gets the error.
     *
     * @return the error
     */
    public String getError() {
        return this.error;
    }

    /**
     * Sets the error.
     *
     * @param error the new error
     */
    public void setError(final String error) {
        this.error = error;
    }

    /**
     * Checks if is success.
     *
     * @return true, if is success
     */
    public boolean isSuccess() {
        return this.success;
    }

    /**
     * Sets the success.
     *
     * @param success the new success
     */
    public void setSuccess(final boolean success) {
        this.success = success;
    }

    /**
     * Gets the weight of url.
     *
     * @return the weight of url
     */
    public float getWeightOfUrl() {
        return this.weightOfUrl;
    }

    /**
     * Sets the weight of url.
     *
     * @param weightOfUrl the new weight of url
     */
    public void setWeightOfUrl(final float weightOfUrl) {
        this.weightOfUrl = weightOfUrl;
    }

    /**
     * Gets the weight of name f.
     *
     * @return the weight of name f
     */
    public float getWeightOfNameF() {
        return this.weightOfNameF;
    }

    /**
     * Sets the weight of name f.
     *
     * @param weightOfNameF the new weight of name f
     */
    public void setWeightOfNameF(final float weightOfNameF) {
        this.weightOfNameF = weightOfNameF;
    }

    /**
     * Gets the weight of name.
     *
     * @return the weight of name
     */
    public float getWeightOfName() {
        return this.weightOfName;
    }

    /**
     * Sets the weight of name.
     *
     * @param weightOfName the new weight of name
     */
    public void setWeightOfName(final float weightOfName) {
        this.weightOfName = weightOfName;
    }

    /**
     * Gets the result size.
     *
     * @return the result size
     */
    public int getResultSize() {
        return this.resultSize;
    }

    /**
     * Sets the result size.
     *
     * @param resultSize the new result size
     */
    public void setResultSize(final int resultSize) {
        this.resultSize = resultSize;
    }


    /**
     * Gets the type name.
     *
     * @return the type name
     */
    public String getTypeName() {
        return this.typeName;
    }

    /**
     * Sets the type name.
     *
     * @param typeName the new type name
     */
    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    /**
     * Gets the visited.
     *
     * @return the visited
     */
    public Map<String, PointStatus> getVisited() {
        return this.visited;
    }

    /**
     * Sets the visited.
     *
     * @param visited the visited
     */
    public void setVisited(final Map<String, PointStatus> visited) {
        this.visited = visited;
    }
}
