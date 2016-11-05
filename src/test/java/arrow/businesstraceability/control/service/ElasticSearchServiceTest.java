package arrow.businesstraceability.control.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

import org.assertj.core.api.Assertions;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import arrow.businesstraceability.cache.entity.Company_mst_suggest;
import arrow.businesstraceability.constant.ElasticSearchConstants;
import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.persistence.entity.Customer_info_view;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.businesstraceability.persistence.repository.CompanyStartUpRepository;
import arrow.businesstraceability.persistence.repository.Company_mstRepository;
import arrow.businesstraceability.persistence.repository.CustomerInfoStartUpViewRepository;
import arrow.businesstraceability.persistence.repository.Employee_mstRepository;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.logging.BaseLogger;

@RunWith(JMockit.class)
public class ElasticSearchServiceTest {

    @Mocked
    @Tested
    ElasticSearchService elasticSearchService;

    @Injectable
    private Company_mstRepository companyRepo;

    @Injectable
    private final String elasticSearchHost = "127.0.0.1";

    @Injectable
    private final int elasticSearchPort = 9300;

    /** The log. */
    @Injectable
    protected BaseLogger log;

    /** The em main. */
    @Injectable
    protected EntityManager emMain;

    /** The current user. */
    @Injectable
    protected UserCredential currentUser;

    @Injectable
    Addresspoint_mstRepository addresspoint_mstRepository;

    @Injectable
    Employee_mstRepository employee_mstRepository;


    @Injectable
    private CustomerInfoStartUpViewRepository customerStartUpRepository;

    /** Repository using when server startup. */
    @Injectable
    private CompanyStartUpRepository companyStartUpRepository;

    List<Customer_info_view> listCustomerInfo = new ArrayList<>();

    List<Company_mst_suggest> listComSuggest = new ArrayList<>();

    List<Company_mst_suggest> listComSuggestSize20 = new ArrayList<>();

    Client client;

    Company_mst_suggest comSuggest;

    @Before
    public void before() {
        Customer_info_view customer_info_view1 = new Customer_info_view(1);
        Customer_info_view customer_info_view2 = new Customer_info_view(2);
        Customer_info_view customer_info_view3 = new Customer_info_view(3);
        customer_info_view1.setDai_compemp_name("abc");
        customer_info_view2.setDai_compemp_name("zzz");
        customer_info_view3.setDai_compemp_name("bc");
        customer_info_view1.setDai_employee_post("abc");
        customer_info_view2.setDai_employee_post("zzz");
        customer_info_view3.setDai_employee_post("bc");
        this.listCustomerInfo.add(customer_info_view1);
        this.listCustomerInfo.add(customer_info_view2);
        this.listCustomerInfo.add(customer_info_view3);
        this.comSuggest = new Company_mst_suggest("10001");
        this.comSuggest.setCom_company_furigana("a");
        this.comSuggest.setCom_company_name("b");
        this.comSuggest.setCom_customer_code("c");
        this.comSuggest.setCom_url("d");
        this.comSuggest.setListed((short) 1);
        this.comSuggest.setWorktype("e");
        for (int i = 0; i < 20; i++) {
            this.listComSuggestSize20.add(this.comSuggest);
        }
        this.listComSuggest.add(this.comSuggest);
    }

    // Verify suggestCompany size <20
    @Test
    public void suggestCompany() {


        new Expectations() {
            {
                Deencapsulation.invoke(ElasticSearchServiceTest.this.elasticSearchService,
                    "searchCompaniesByPrefixString", this.anyString, this.anyString, this.anyInt, this.anyString);
                this.result = ElasticSearchServiceTest.this.listComSuggest;
                Deencapsulation.invoke(ElasticSearchServiceTest.this.elasticSearchService,
                    "searchCompaniesByQueryString", this.withAny(ElasticSearchServiceTest.this.listComSuggest),
                    this.anyString, this.anyString, this.anyInt, this.anyString);
                this.result = ElasticSearchServiceTest.this.listComSuggest;


            }
        };
        ServiceResult<List<Company_mst_suggest>> listComSuggestActual;
        listComSuggestActual = ElasticSearchServiceTest.this.elasticSearchService.suggestCompany("xxxx");
        Assert.assertTrue(listComSuggestActual.isSuccessful());
        new Verifications() {
            {
                Deencapsulation.invoke(ElasticSearchServiceTest.this.elasticSearchService,
                    "searchCompaniesByPrefixString", this.anyString, this.anyString, this.anyInt, this.anyString);
                this.times = 1;
                Deencapsulation.invoke(ElasticSearchServiceTest.this.elasticSearchService,
                    "searchCompaniesByQueryString", ElasticSearchServiceTest.this.listComSuggest,
                    ElasticSearchConstants.Company_mst.INDEX_VALUE, ElasticSearchConstants.Company_mst.TYPE_VALUE, 20,
                    "xxxx");
                this.times = 1;
            }
        };
    }

    // Verify suggestCompany size = 20
    @Test
    public void suggestCompanySize20Test() {
        new Expectations() {
            {
                Deencapsulation.invoke(ElasticSearchServiceTest.this.elasticSearchService,
                    "searchCompaniesByPrefixString", this.anyString, this.anyString, this.anyInt, this.anyString);
                this.result = ElasticSearchServiceTest.this.listComSuggestSize20;
            }
        };
        ServiceResult<List<Company_mst_suggest>> listComSuggestActual;

        listComSuggestActual = ElasticSearchServiceTest.this.elasticSearchService.suggestCompany("zzzz");
        Assert.assertTrue(listComSuggestActual.isSuccessful());
        new Verifications() {
            {
                Deencapsulation.invoke(ElasticSearchServiceTest.this.elasticSearchService,
                    "searchCompaniesByPrefixString", this.anyString, this.anyString, this.anyInt, this.anyString);
                this.times = 1;
                Deencapsulation.invoke(ElasticSearchServiceTest.this.elasticSearchService,
                    "searchCompaniesByQueryString", ElasticSearchServiceTest.this.listComSuggestSize20,
                    ElasticSearchConstants.Company_mst.INDEX_VALUE, ElasticSearchConstants.Company_mst.TYPE_VALUE,
                    this.anyInt, "zzzz");
                this.times = 0;
            }
        };
    }

    // Verify suggestCompany throw NoNodeAvailableException
    @Test
    public void suggestCompanyNoNodeAvailableException() {
        new Expectations() {
            {
                Deencapsulation.invoke(ElasticSearchServiceTest.this.elasticSearchService,
                    "searchCompaniesByPrefixString", this.anyString, this.anyString, this.anyInt, this.anyString);
                this.result = new NoNodeAvailableException(this.anyString);
            }
        };
        ServiceResult<List<Company_mst_suggest>> listComSuggestActual;
        listComSuggestActual = ElasticSearchServiceTest.this.elasticSearchService.suggestCompany("zzzz");
        Assert.assertTrue(!listComSuggestActual.isSuccessful());
        Assertions.assertThat(listComSuggestActual.getMessages().get(0).getMessageCode()).isEqualTo(
            ErrorMessage.daily_020_noNodeAvailable().getMessageCode());
    }

    // Verify suggestCompany throw IOException
    @Test
    public void suggestCompanyIOException() {
        new Expectations() {
            {
                Deencapsulation.invoke(ElasticSearchServiceTest.this.elasticSearchService,
                    "searchCompaniesByPrefixString", this.anyString, this.anyString, this.anyInt, this.anyString);
                this.result = new IOException();
            }
        };
        ServiceResult<List<Company_mst_suggest>> listComSuggestActual;
        listComSuggestActual = ElasticSearchServiceTest.this.elasticSearchService.suggestCompany("zzzz");
        Assert.assertTrue(!listComSuggestActual.isSuccessful());
        Assertions.assertThat(listComSuggestActual.getMessages().get(0).getMessageCode()).isEqualTo(
            ErrorMessage.daily_021_can_not_get_suggestion().getMessageCode());
    }

    // Veryfy suggestPersonInChargeOrDepartment with empty keyword
    @Test
    public void suggestPersonInChargeOrDepartmentEmptyKeywordTest() {

        new Expectations() {
            {
                Deencapsulation.invoke(ElasticSearchServiceTest.this.elasticSearchService,
                    "searchCustomerByCompanyCodeAndCompointCode", this.anyString, this.anyString);
                this.result = ElasticSearchServiceTest.this.listCustomerInfo;
            }
        };
        List<Customer_info_view> suggestPersonInCharge;
        suggestPersonInCharge =
            ElasticSearchServiceTest.this.elasticSearchService.suggestPersonInChargeOrDepartment("", "ab", "c",
                ElasticSearchConstants.CustomerInfoView.DAI_COMEMP_NAME);
        Assert.assertTrue(suggestPersonInCharge.size() == 3);
    }

    // Veryfy suggestPersonInChargeOrDepartment type =dai_compemp_name
    @Test
    public void suggestDepartmentTypeDai_compemp_name() {

        new Expectations() {
            {
                Deencapsulation.invoke(ElasticSearchServiceTest.this.elasticSearchService,
                    "searchCustomerByCompanyCodeAndCompointCode", this.anyString, this.anyString);
                this.result = ElasticSearchServiceTest.this.listCustomerInfo;
            }
        };
        List<Customer_info_view> suggestPersonInCharge;
        suggestPersonInCharge =
            ElasticSearchServiceTest.this.elasticSearchService.suggestPersonInChargeOrDepartment("bc", "ab", "c",
                ElasticSearchConstants.CustomerInfoView.DAI_COMEMP_NAME);
        Assert.assertEquals(suggestPersonInCharge.size(), 2);
    }

    // Veryfy suggestPersonInChargeOrDepartment type =dai_employee_post
    @Test
    public void suggestDepartmentTypeDai_employee_post() {

        new Expectations() {
            {
                Deencapsulation.invoke(ElasticSearchServiceTest.this.elasticSearchService,
                    "searchCustomerByCompanyCodeAndCompointCode", this.anyString, this.anyString);
                this.result = ElasticSearchServiceTest.this.listCustomerInfo;
            }
        };
        List<Customer_info_view> suggestPersonInCharge;
        suggestPersonInCharge =
            ElasticSearchServiceTest.this.elasticSearchService.suggestPersonInChargeOrDepartment("bc", "abc", "c",
                ElasticSearchConstants.CustomerInfoView.DAI_COMEMP_POST);
        Assert.assertEquals(suggestPersonInCharge.size(), 2);
    }


}
