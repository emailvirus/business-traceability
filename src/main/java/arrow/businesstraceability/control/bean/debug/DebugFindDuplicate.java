package arrow.businesstraceability.control.bean.debug;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.constant.ElasticSearchConstants;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.control.service.ElasticSearchService;
import arrow.framework.bean.AbstractBean;
import arrow.framework.util.JsonUtils;

@Named
@ViewScoped
public class DebugFindDuplicate extends AbstractBean {

    private Company_mst_suggest companySuggest;

    @Inject
    private ElasticSearchService elasticSearchService;

    private List<Company_mst_suggest> listCompanySuggest;

    /**
     * Filter company.
     *
     * @param query the query
     * @return the list
     */
    public List<Company_mst_suggest> filterCompany(final String query) {
        ServiceResult<List<Company_mst_suggest>> result = this.elasticSearchService.suggestCompany(query);
        if (result.isSuccessful()) {
            this.listCompanySuggest = result.getData();
        }
        return this.listCompanySuggest;
    }

    public Company_mst_suggest getCompanySuggest() {
        return this.companySuggest;
    }

    public void setCompanySuggest(final Company_mst_suggest companySuggest) {
        this.companySuggest = companySuggest;
    }

    public List<Company_mst_suggest> getListDuplication() {
        return this.listDuplication;
    }

    public void setListDuplication(final List<Company_mst_suggest> listDuplication) {
        this.listDuplication = listDuplication;
    }

    private List<Company_mst_suggest> listDuplication;

    private List<Company_mst_suggest> filteredData;

    /**
     * Find duplicate.
     */
    public void findDuplicate() {
        if (this.companySuggest == null) {
            return;
        }
        this.listDuplication = new ArrayList<>();
        if (StringUtils.isNotEmpty(this.companySuggest.getCom_url())) {
            List<Company_mst_suggest> sameUrlList =
                this.findByDomain(arrow.framework.util.StringUtils.extractDomain(this.companySuggest.getCom_url()));

            for (Company_mst_suggest com : sameUrlList) {
                if (!com.getCom_company_code().equalsIgnoreCase(this.companySuggest.getCom_company_code())) {
                    com.setClusterIndex(3);
                    this.listDuplication.add(com);
                }
            }
        }
        List<Company_mst_suggest> similarName = this.findByNameAndFurigana(
            arrow.framework.util.StringUtils.convertToHalfWidthKata(this.companySuggest.getCom_company_name()),
            arrow.framework.util.StringUtils.convertToHalfWidthKata(this.companySuggest.getCom_company_furigana()));

        for (Company_mst_suggest com : similarName) {
            if (!com.getCom_company_code().equalsIgnoreCase(this.companySuggest.getCom_company_code())) {
                if (!this.listDuplication.contains(com)) {
                    com.setClusterIndex(2);
                    this.listDuplication.add(com);
                }
            }
        }
    }

    private List<Company_mst_suggest> findByNameAndFurigana(final String name, final String furigana) {
        try (Client client = TransportClient.builder().build()
            .addTransportAddress(new InetSocketTransportAddress(
                InetAddress.getByName(ElasticSearchConstants.ElasticsearchProperties.HOST_VALUE),
                ElasticSearchConstants.ElasticsearchProperties.PORT_VALUE))) {

            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder
                .must(QueryBuilders.matchQuery("name", name).prefixLength(1).minimumShouldMatch("4<90%").boost(0.6f));
            if (!StringUtils.isEmpty(furigana)) {
                builder.should(QueryBuilders.matchQuery("furigana", furigana).prefixLength(1).fuzziness(2)
                    .minimumShouldMatch("4<90%").boost(0.4f));
            }

            // builder.must(QueryBuilders.missingQuery("com_url"));

            SearchResponse response = client.prepareSearch(CompanyClusterer.INDEX_NAME).setSize(10000).setQuery(builder)
                .execute().actionGet();
            SearchHit[] hits = response.getHits().getHits();
            List<Company_mst_suggest> listCom = new ArrayList<>();
            for (SearchHit searchHit : hits) {
                Company_mst_suggest com = JsonUtils.toEntity(searchHit.getSourceAsString(), Company_mst_suggest.class);
                listCom.add(com);
            }
            return listCom;

        } catch (IOException uhe) {
            this.log.debug("Cannot convert json", uhe);
        }
        return Collections.emptyList();
    }

    private List<Company_mst_suggest> findByDomain(final String extractDomain) {
        try (Client client = TransportClient.builder().build()
            .addTransportAddress(new InetSocketTransportAddress(
                InetAddress.getByName(ElasticSearchConstants.ElasticsearchProperties.HOST_VALUE),
                ElasticSearchConstants.ElasticsearchProperties.PORT_VALUE))) {

            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.must(QueryBuilders.matchPhraseQuery("domain", extractDomain));

            SearchResponse response = client.prepareSearch(CompanyClusterer.INDEX_NAME).setSize(10000).setQuery(builder)
                .execute().actionGet();
            SearchHit[] hits = response.getHits().getHits();
            List<Company_mst_suggest> listCom = new ArrayList<>();
            for (SearchHit searchHit : hits) {
                Company_mst_suggest com = JsonUtils.toEntity(searchHit.getSourceAsString(), Company_mst_suggest.class);
                listCom.add(com);
            }

            return listCom;

        } catch (IOException uhe) {
            this.log.debug("Cannot Convert json", uhe);
        }
        return Collections.emptyList();
    }

    public List<Company_mst_suggest> getFilteredData() {
        return this.filteredData;
    }

    public void setFilteredData(final List<Company_mst_suggest> filteredData) {
        this.filteredData = filteredData;
    }
}
