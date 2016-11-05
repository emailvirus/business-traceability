package arrow.businesstraceability.debug;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.constant.ElasticSearchConstants;
import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;
import arrow.framework.util.JsonUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class DBScan.
 */
public class DBScan implements Serializable {

    /**
     * Db scan.
     *
     * @return the list
     */
    private Client client;

    /** The max result size. */
    private int maxResultSize;

    /** The list noise. */
    private List<Point> listNoise = new ArrayList<>();

    /** The Constant log. */
    private static final BaseLogger LOG = BaseLoggerProducer.getLogger("DBScan");

    /**
     * Db scan.
     *
     * @param data the data
     * @param eps the eps
     * @param minPts the min pts
     * @param maxResultSize the max result size
     * @return the list
     * @throws UnknownHostException the unknown host exception
     */
    public List<ListCluster> dbScan(final List<Point> data, final int eps, final int minPts, final int maxResultSize)
        throws UnknownHostException {
        this.maxResultSize = maxResultSize;
        List<ListCluster> listCluster = new ArrayList<ListCluster>();
        this.client = TransportClient.builder().build()
            .addTransportAddress(new InetSocketTransportAddress(
                InetAddress.getByName(ElasticSearchConstants.ElasticsearchProperties.HOST_VALUE),
                ElasticSearchConstants.ElasticsearchProperties.PORT_VALUE));
        int index = 0;
        for (Point point : data) {
            if (point.isVisited()) {
                continue;
            }
            point.setVisited(true);
            List<Point> neightbor = this.findNeiElasticSearch(point, eps, data);
            if (neightbor.size() < minPts) {

                point.setNoise(true);
                // point.setVisited(true);
                this.getListNoise().add(point);
            } else {
                ListCluster cluster = new ListCluster();
                cluster.setIndex(index);
                index++;
                this.expandCluster(point, neightbor, cluster, minPts, data, listCluster, eps);
                listCluster.add(cluster);

            }
        }
        this.client.close();
        return listCluster;
    }

    /**
     * Expand cluster.
     *
     * @param point the point
     * @param neightbor the neightbor
     * @param cluster the cluster
     * @param minPts the min pts
     * @param data the data
     * @param listCluster the list cluster
     * @param eps the eps
     */
    public void expandCluster(final Point point, final List<Point> neightbor, final ListCluster cluster,
        final int minPts, final List<Point> data, final List<ListCluster> listCluster, final int eps) {
        point.setNoise(false);
        cluster.getListPoint().add(point);
        DBScan.LOG.debugf("Process Point: %s \n", point.printOut());
        for (ListIterator<Point> iter1 = neightbor.listIterator(); iter1.hasNext();) {
            DBScan.LOG.debugf("Number of Neightbor: %d\n", neightbor.size());
            Point point2 = iter1.next();
            if (!point2.isVisited()) {
                point2.setVisited(true);
                List<Point> neightbor2 = this.findNeiElasticSearch(point2, eps, data);
                if (neightbor2.size() >= minPts) {
                    for (ListIterator<Point> iter2 = neightbor2.listIterator(); iter2.hasNext();) {
                        Point point3 = iter2.next();
                        iter1.add(point3);

                    }
                }
            }
            if (!this.checkInCluster(point2, listCluster)) {
                point2.setNoise(false);
                cluster.getListPoint().add(point2);
            }
        }

    }

    /**
     * Check in cluster.
     *
     * @param point the point
     * @param listCluster the list cluster
     * @return true, if successful
     */
    public boolean checkInCluster(final Point point, final List<ListCluster> listCluster) {
        for (int i = 0; i < listCluster.size(); i++) {
            List<Point> temList = listCluster.get(i).getListPoint();
            for (Point point3 : temList) {
                if (point3.equals(point)) {
                    return true;
                }
            }
        }
        return false;
    }

    // public List<Point> findNeightbor(final Point point, final int eps, final List<Point> data) {
    // List<Point> neighbors = new ArrayList<Point>();
    // for (int i = 0; i < data.size(); i++) {
    // String temp = data.get(i).name;
    // if (!point.id.equals(data.get(i).id)) {
    // int count = this.LevenshteinDistance(temp, point.name);
    // if (count <= eps) {
    // neighbors.add(data.get(i));
    // }
    // }
    // }
    // return neighbors;
    // }

    /**
     * Find nei elastic search.
     *
     * @param point the point
     * @param eps the eps
     * @param data the data
     * @return the list
     */
    public List<Point> findNeiElasticSearch(final Point point, final int eps, final List<Point> data) {
        List<Point> neighbors = new ArrayList<Point>();
        String compName = point.getCompanyMst().getCom_company_name();
        String compFurigana = point.getCompanyMst().getCom_company_furigana();
        String url = point.getCompanyMst().getCom_url();
        try {
            List<Company_mst_suggest> listCom = this.searchCompaniesByQueryString("company_msts", "company_mst",
                this.maxResultSize, compName, compFurigana, url);

            return listCom.parallelStream().map(com -> new Point(com)).collect(Collectors.toList());
        } catch (final NoNodeAvailableException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return neighbors;
    }

    /**
     * Search companies by query string.
     *
     * @param index the index
     * @param type the type
     * @param size the size
     * @param comName the com name
     * @param compFurigana the comp furigana
     * @param url the url
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws NoNodeAvailableException the no node available exception
     */
    private List<Company_mst_suggest> searchCompaniesByQueryString(final String index, final String type,
        final int size, final String comName, final String compFurigana, final String url)
            throws IOException, NoNodeAvailableException {

        BoolQueryBuilder stringQuery = QueryBuilders.boolQuery();
        if (StringUtils.isNotEmpty(url) || url.contains("unkown.info")) {
            stringQuery
                .should(QueryBuilders.matchPhraseQuery(ElasticSearchConstants.Company_mst.COM_URL, url).boost(0.7f))
                .should(QueryBuilders.matchQuery(ElasticSearchConstants.Company_mst.COM_URL, url)
                    .minimumShouldMatch("75%").boost(0.2f));
        }
        stringQuery.should(QueryBuilders.matchQuery(ElasticSearchConstants.Company_mst.COM_COMPANY_NAME, comName)
            .minimumShouldMatch("75%").boost(0.2f));
        if (StringUtils.isNotEmpty(compFurigana)) {
            stringQuery
                .should(QueryBuilders.matchQuery(ElasticSearchConstants.Company_mst.COM_COMPANY_FURIGANA, compFurigana)
                    .minimumShouldMatch("75%").boost(0.1f));
        }


        SearchResponse response = this.client.prepareSearch(index).setTypes(type).setQuery(stringQuery).setFrom(0)
            .setSize(size).execute().actionGet();
        List<Company_mst_suggest> listCompanySuggest = new ArrayList<Company_mst_suggest>();
        int allResult = response.getHits().getHits().length;
        // convert json to object
        for (int i = 0; i < allResult; i++) {
            Company_mst_suggest company = new Company_mst_suggest();
            company = (Company_mst_suggest) JsonUtils
                .convertJsonToObject(response.getHits().getAt(i).getSourceAsString(), company);
            listCompanySuggest.add(company);
        }
        return listCompanySuggest;
    }

    /**
     * Levenshtein distance.
     *
     * @param lhs the lhs
     * @param rhs the rhs
     * @return the int
     */
    public int levenshteinDistance(final CharSequence lhs, final CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) {
            cost[i] = i;
        }

        // dynamically computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for (int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }
        return cost[len0 - 1];
    }

    /**
     * Gets the list noise.
     *
     * @return the list noise
     */
    public List<Point> getListNoise() {
        return this.listNoise;
    }

    /**
     * Sets the list noise.
     *
     * @param listNoise the new list noise
     */
    public void setListNoise(final List<Point> listNoise) {
        this.listNoise = listNoise;
    }
}
