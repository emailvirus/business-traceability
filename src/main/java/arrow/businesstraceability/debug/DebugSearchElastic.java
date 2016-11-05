package arrow.businesstraceability.debug;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.omnifaces.cdi.ViewScoped;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.persistence.entity.Customer_info_view;

@Named
@ViewScoped
public class DebugSearchElastic extends AbstractService {
    private Client client;

    private String textSearch;

    private List<Customer_info_view> listCustomers;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        try {
            this.client =
                    TransportClient
                    .builder()
                    .build()
                    .addTransportAddress(
                            new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load all data.
     */
    public void loadAllData() {
        SearchResponse response =
                this.client.prepareSearch("customers").setTypes("customer").setQuery(QueryBuilders.matchAllQuery())
                .setSize(9999).setExplain(false).execute().actionGet();
        System.out.println(response.status());
    }

    /**
     * Search with field name and branch office.
     */
    public void searchWithFieldNameAndBranchOffice() {
        SearchResponse response =
                this.client.prepareSearch("customers").setTypes("customer")
                .setQuery(QueryBuilders.matchQuery("com_url", this.textSearch)).setSize(2000).setExplain(false)
                        .execute().actionGet();

        System.out.println(response.toString());
    }

    public String getTextSearch() {
        return this.textSearch;
    }

    public void setTextSearch(final String textSearch) {
        this.textSearch = textSearch;
    }

    public List<Customer_info_view> getListCustomers() {
        return this.listCustomers;
    }

    public void setListCustomers(final List<Customer_info_view> listCustomers) {
        this.listCustomers = listCustomers;
    }
}
