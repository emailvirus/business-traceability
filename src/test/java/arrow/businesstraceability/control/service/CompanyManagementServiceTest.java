/**
 * -------------------------------------------------------- All Rights Reserved. Copyright(C) Arrow Technology, Ltd.
 * Vendor : Arrow Technology, Ltd. Author: Le Ha An Since: November 10, 2015
 * --------------------------------------------------------
 */


package arrow.businesstraceability.control.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.persistence.dto.Company_mst_DTO;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Basepoint_info;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Industry_big_mst;
import arrow.businesstraceability.persistence.mapped.Company_mst_MAPPED.Pk;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.businesstraceability.persistence.repository.Basepoint_infoRepository;
import arrow.businesstraceability.persistence.repository.Company_mstRepository;
import arrow.businesstraceability.persistence.repository.Customer_info_viewRepository;
import arrow.framework.logging.BaseLogger;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.cdi.Instance;
import arrow.framework.util.collections.CollectionUtils;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class CompanyManagementServiceTest {

    @Mocked
    @Tested
    CompanyManagementService service;

    Company_mst_DTO newCompany;

    Company_mst_DTO persitedCompany;

    Company_mst_DTO responseCompany;

    Addresspoint_mst addresspoint;

    Addresspoint_mst secondAddresspoint;

    Industry_big_mst industry;

    List<Company_mst_DTO> listCompany;

    List<Addresspoint_mst> listBranch;

    List<Industry_big_mst> listIndustry;

    List<Basepoint_info> listBasepoint;

    @Mocked
    @Injectable
    private Basepoint_infoRepository basePointRepo;

    @Mocked
    @Injectable
    @Tested
    private AddressPointService addresspointService;

    @Mocked
    @Injectable
    private IndustryService industryService;

    @Mocked
    @Injectable
    private BaseLogger logger;

    @Mocked
    @Injectable
    protected EntityManager emMain;

    @Mocked
    @Injectable
    private UserCredential currentUser;

    @Mocked
    @Injectable
    private UserCredential userCredential;

    @Mocked
    @Injectable
    private Addresspoint_mstRepository addresspointRepo;

    @Mocked
    @Injectable
    private Company_mstRepository companyRepo;

    @Mocked
    @Injectable
    private Customer_info_viewRepository customerInfoRepo;
    // init data before test

    /**
     * Method prepare data. Run before test.
     *
     * @author Le Ha An
     */
    @Before
    public void before() {
        this.addresspoint = new Addresspoint_mst("22");
        this.secondAddresspoint = new Addresspoint_mst("33");

        this.industry = new Industry_big_mst(new Short("101"));
        this.listIndustry = new ArrayList<Industry_big_mst>();
        this.listIndustry.add(this.industry);

        this.newCompany = new Company_mst_DTO();
        this.newCompany.setAddresspoint_mst(this.addresspoint);

        this.persitedCompany = new Company_mst_DTO();
        this.persitedCompany.setAddresspoint_mst(this.addresspoint);
        this.persitedCompany.setPk(new Pk());
        this.persitedCompany.setCom_company_code("4400010000");

        this.responseCompany = new Company_mst_DTO();
        this.responseCompany.setAddresspoint_mst(this.addresspoint);
        this.responseCompany.setPk(new Pk());
        this.responseCompany.setCom_company_code("4400011111");

        this.listCompany = new ArrayList<Company_mst_DTO>();
        this.listBranch = new ArrayList<Addresspoint_mst>();
        this.listBasepoint = new ArrayList<Basepoint_info>();

        this.listCompany.add(this.responseCompany);
        this.listBranch.add(this.addresspoint);
        this.listBranch.add(this.secondAddresspoint);

        Basepoint_info firstBasepoint = new Basepoint_info(this.addresspoint, this.persitedCompany);
        firstBasepoint.setBas_delete_flg_DIRECT(true);
        Basepoint_info secondBasepoint = new Basepoint_info(this.addresspoint, this.responseCompany);
        this.listBasepoint.add(firstBasepoint);
        this.listBasepoint.add(secondBasepoint);
        this.newCompany.setBasepoint_infos(this.listBasepoint);
        this.persitedCompany.setBasepoint_infos(this.listBasepoint);
        this.responseCompany.setBasepoint_infos(this.listBasepoint);

    }

    /**
     * Test case for situation: - Save a new Company successfull. Expect: - Status: True; - Return Data: Not Null, Equal
     * expected data; - Return Message: List Messages, Not Null;
     *
     * @throws NumberFormatException
     * @author Le Ha An
     */
    @Test
    public void testSaveNewCompanySuccess() throws NumberFormatException {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.service.autoNumberring(this.anyString);
                this.result = "2200000001";
            }
        };
        this.newCompany.setIndustry_big_mst(new Industry_big_mst((short) 1));
        ServiceResult<Company_mst> result = this.service.saveCompany(this.newCompany, this.listBranch);

        new Verifications() {
            {
                CompanyManagementServiceTest.this.service.autoNumberring(this.anyString);
                this.times = 1;
            }

            {
                Assertions.assertThat(result.isSuccessful()).isTrue();
                Assertions.assertThat(result.getData()).isNotNull();
                Assertions.assertThat(result.getData().getCom_company_code()).isEqualTo("2200000001");
                Assertions.assertThat(result.getMessages()).isNotNull();
            }
        };
    }

    /**
     * Test case for situation: - Save a new Company successfull. Expect: - Status: True; - Return Data: Not Null, Equal
     * expected data; - Return Message: List Messages, Not Null;
     *
     * @throws NumberFormatException
     * @author Le Ha An
     */
    @Test
    public void testSaveNewCompanyFailed() throws NumberFormatException {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.service.autoNumberring(this.anyString);
                this.result = null;
            }
        };

        ServiceResult<Company_mst> result = this.service.saveCompany(this.newCompany, this.listBranch);

        new Verifications() {
            {
                CompanyManagementServiceTest.this.service.autoNumberring(this.anyString);
                this.times = 1;
            }

            {
                Assertions.assertThat(result.isSuccessful()).isFalse();
                Assertions.assertThat(result.getData()).isNull();
                Assertions.assertThat(result.getMessages()).isNotNull();
            }
        };
    }

    /**
     * Test case for situation: - Update a new Company successfull. Expect: - Status: True; - Return Data: Not Null,
     * Equal expected data; - Return Message: List Messages, Not Null;
     *
     * @param company_mst: mocked Company_mst param
     * @param instance: mocked Instance param
     * @author Le Ha An
     */
    @Test
    public void testUpdateCompanySuccess(@Mocked final Company_mst company_mst, @Mocked final Instance instance) {
        new Expectations() {
            {
                Company_mst.find(this.anyString);
                this.result = CompanyManagementServiceTest.this.responseCompany;

                Instance.get(BasepointInfoService.class).getAllBasepointInfoByCompanyCode(this.anyString);
                this.result = CompanyManagementServiceTest.this.listBasepoint;
            }
        };

        ServiceResult<Company_mst> result = this.service.saveCompany(this.persitedCompany, this.listBranch);

        new Verifications() {
            {
                Company_mst.find(this.anyString);
                this.times = 1;

                Instance.get(BasepointInfoService.class).getAllBasepointInfoByCompanyCode(this.anyString);
                this.times = 1;
            }

            {
                Assertions.assertThat(result.isSuccessful()).isTrue();
                Assertions.assertThat(result.getData()).isNotNull();
                Assertions.assertThat(result.getData().getCom_company_code())
                    .isEqualTo(CompanyManagementServiceTest.this.responseCompany.getCom_company_code());
                Assertions.assertThat(result.getMessages()).isNotNull();
            }
        };
    }

    /**
     * Test case for situation: - Create a new Company successfull. Expect: - Status: True; - Return Data: Not Null;
     *
     * @param company_mst_dto: mocked Company_mst_DTO
     * @author Le Ha An
     */
    @Test
    public void testCreateNew(@Mocked final Company_mst_DTO company_mst_dto) {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.companyRepo
                    .findAndRefresh(CompanyManagementServiceTest.this.persitedCompany);
                this.result = CompanyManagementServiceTest.this.persitedCompany;
            }
        };

        ServiceResult<Company_mst_DTO> result = this.service.createDTO(this.persitedCompany);

        new Verifications() {
            {
                CompanyManagementServiceTest.this.companyRepo
                    .findAndRefresh(CompanyManagementServiceTest.this.persitedCompany);
                this.times = 1;
            }

            {
                Assertions.assertThat(result.isSuccessful()).isTrue();
                Assertions.assertThat(result.getData()).isNotNull();
                Assertions.assertThat(result.getData().getClass())
                    .isEqualTo(CompanyManagementServiceTest.this.persitedCompany.getDto().getClass());
            }
        };
    }

    /**
     * Test case for situation: - Delete a Company successfull. Expect: - Status: True; - Return Message: List Messages,
     * Not Null;
     *
     * @author Le Ha An
     */
    @Test
    public void testDeleteCompany() {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.companyRepo
                    .findAndRefresh(CompanyManagementServiceTest.this.persitedCompany);
                this.result = CompanyManagementServiceTest.this.persitedCompany;
            }
        };

        ServiceResult<Company_mst> result =
            CompanyManagementServiceTest.this.service.deleteCompany(this.persitedCompany);

        new Verifications() {
            {
                CompanyManagementServiceTest.this.companyRepo
                    .findAndRefresh(CompanyManagementServiceTest.this.persitedCompany);
                this.times = 1;
            }

            {
                Assertions.assertThat(result.isSuccessful()).isTrue();
                Assertions.assertThat(result.getMessages()).isNotNull();
            }
        };
    }

    /**
     * Test case for situation: - Create new Company Code. Expect: - Return Data: String, Not Null, Equal expected data;
     *
     * @param typedQuery: mocked TypedQuery
     * @author Le Ha An
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void testAutoNumberingSuccess(@Mocked final TypedQuery typedQuery) {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.emMain.createQuery(this.anyString, Company_mst.class).getResultList();
                this.result = CompanyManagementServiceTest.this.listCompany;
            }
        };

        String companyCode = this.service.autoNumberring("22");

        new Verifications() {
            {
                CompanyManagementServiceTest.this.emMain.createQuery(this.anyString, Company_mst.class).getResultList();
                this.times = 1;
            }

            {
                Assertions.assertThat(companyCode).isEqualTo("2200011112");
            }
        };
    }

    /**
     * Test case for situation: - Create Company Code but do not find any company for this address. Expect: - Return
     * Data: String, Not Null, Equal expected data;
     *
     * @param typedQuery: mocked TypedQuery
     * @author Le Ha An
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void testAutoNumberingFailed(@Mocked final TypedQuery typedQuery) {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.emMain.createQuery(this.anyString, Company_mst.class)
                    .setParameter(this.anyString, this.anyString).getResultList();
                this.result = Collections.emptyList();
            }
        };

        String companyCode = this.service.autoNumberring("22");

        new Verifications() {
            {
                CompanyManagementServiceTest.this.emMain.createQuery(this.anyString, Company_mst.class).getResultList();
                this.times = 1;
            }

            {
                Assertions.assertThat(companyCode).isEqualTo("2200000001");
            }
        };
    }

    /**
     * Test case for situation: - Create new Company Code throw exception. Expect: - Return Data: Null;
     *
     * @param typedQuery: mocked TypedQuery
     * @author Le Ha An
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void testAutoNumberingWrongFormatNumber(@Mocked final TypedQuery typedQuery) {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.emMain.createQuery(this.anyString, Company_mst.class).getResultList();
                this.result = CompanyManagementServiceTest.this.listCompany;

                CompanyManagementServiceTest.this.service.extractNumber(this.anyString);
                this.result = new NumberFormatException();
            }
        };

        String companyCode = this.service.autoNumberring(this.addresspoint.getAdp_code());

        new Verifications() {
            {
                CompanyManagementServiceTest.this.emMain.createQuery(this.anyString, Company_mst.class).getResultList();
                this.times = 1;

                CompanyManagementServiceTest.this.service.extractNumber(this.anyString);
                this.times = 1;
            }

            {
                Assertions.assertThat(companyCode).isNull();
            }
        };
    }

    /**
     * Test case for situation: - Get All Address. Expect: - Return Data: List Address, Not Null, Equal expected data;
     *
     * @author Le Ha An
     */
    @Test
    public void testGetAllAddressHasData() {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.addresspointRepo.findAll();
                this.result = CompanyManagementServiceTest.this.listBranch;
            }
        };

        List<Addresspoint_mst> result = this.service.getAllAddress();

        new Verifications() {
            {
                CompanyManagementServiceTest.this.addresspointRepo.findAll();
                this.times = 1;
            }

            {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.size()).isEqualTo(2);
            }
        };
    }

    /**
     * Test case for situation: - Get all Address but has no data. Expect: - Return Data: List Address, Null;
     *
     * @author Le Ha An
     */
    @Test
    public void testGetAllAddressHasNoData() {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.addresspointRepo.findAll();
                this.result = Collections.emptyList();
            }
        };

        List<Addresspoint_mst> result = this.service.getAllAddress();

        new Verifications() {
            {
                CompanyManagementServiceTest.this.addresspointRepo.findAll();
                this.times = 1;
            }

            {
                Assertions.assertThat(result).isEqualTo(Collections.emptyList());
            }
        };
    }

    /**
     * Test case for situation: - Find duplicated Customer Code and has no Data. Expect: - Status: False; - Return Data:
     * Null; - Return Message: List Messages, Not Null;
     *
     * @author Le Ha An
     */
    @Test
    public void testFindDuplicateCustomerCodeHasNoData() {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.emMain.createQuery(this.anyString, Company_mst.class)
                    .setParameter(this.anyString, this.anyString).setParameter(this.anyString, this.anyString)
                    .getResultList();
                this.result = Collections.emptyList();
            }
        };

        ServiceResult<Company_mst> result = this.service.findDuplicateCustomerCode("abc", "def");

        new Verifications() {
            {
                CompanyManagementServiceTest.this.emMain.createQuery(this.anyString, Company_mst.class)
                    .setParameter(this.anyString, this.anyString).setParameter(this.anyString, this.anyString)
                    .getResultList();
                this.times = 1;
            }

            {
                Assertions.assertThat(result.isSuccessful()).isFalse();
                Assertions.assertThat(result.getData()).isEqualTo(null);
                Assertions.assertThat(result.getMessages()).isNotNull();
            }
        };
    }

    /**
     * Test case for situation: - Find duplicated Customer Code and has Data. Expect: - Status: True; - Return Data:
     * List Company, Not Null, Equal expected data; - Return Message: List Messages, Not Null;
     *
     * @author Le Ha An
     */
    @Test
    public void testFindDuplicateCustomerCodeHasData() {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.emMain.createQuery(this.anyString, Company_mst.class)
                    .setParameter(this.anyString, this.anyString).setParameter(this.anyString, this.anyString)
                    .getResultList();
                this.result = CompanyManagementServiceTest.this.listCompany;
            }
        };

        ServiceResult<Company_mst> result = this.service.findDuplicateCustomerCode("abc", "def");

        new Verifications() {
            {
                CompanyManagementServiceTest.this.emMain.createQuery(this.anyString, Company_mst.class)
                    .setParameter(this.anyString, this.anyString).setParameter(this.anyString, this.anyString)
                    .getResultList();
                this.times = 1;
            }

            {
                Assertions.assertThat(result.isSuccessful()).isTrue();
                Assertions.assertThat(result.getData()).isNotNull();
                Assertions.assertThat(result.getData().getCom_company_code())
                    .isEqualTo(CompanyManagementServiceTest.this.listCompany.get(0).getCom_company_code());
                Assertions.assertThat(result.getMessages()).isNotNull();
            }
        };
    }

    /**
     * Test case for situation: - Find duplicated Customer Code when customer_code empty. Expect: - Status: True; -
     * Return Data: Null; - Return Message: List Messages, Not Null;
     *
     * @author Le Ha An
     */
    @Test
    public void testFindDuplicateCustomerCodeWhenCustomerCodeEmpty() {
        ServiceResult<Company_mst> result = this.service.findDuplicateCustomerCode("abc", "");

        new Verifications() {
            {
                Assertions.assertThat(result.isSuccessful()).isFalse();
                Assertions.assertThat(result.getData()).isNull();
                Assertions.assertThat(result.getMessages()).isNotNull();
            }
        };
    }

    /**
     * Test case for situation: - Get companies when export excel and has data. Expect: - Return Data: List company, Not
     * null, Equal expected data;
     *
     * @param emLocator: mocked EmLocator
     * @author Le Ha An
     */
    @Test
    public void testGetCompanyForExportExcelHasData(@Mocked final EmLocator emLocator) {
        new Expectations() {
            {
                EmLocator.getEm().createQuery(this.anyString, Company_mst.class).getResultList();
                this.result = CompanyManagementServiceTest.this.listCompany;
            }
        };

        List<Company_mst> result = this.service.getCompanyForExportExcel();

        new Verifications() {
            {
                EmLocator.getEm().createQuery(this.anyString, Company_mst.class).getResultList();
                this.times = 1;
            }

            {
                Assertions.assertThat(CollectionUtils.isNotEmpty(result)).isTrue();
                Assertions.assertThat(result.size()).isEqualTo(CompanyManagementServiceTest.this.listCompany.size());
            }
        };
    }

    /**
     * Test case for situation: - Get companies when export excel and has no data. Expect: - Return Data: List company,
     * empty;
     *
     * @param emLocator: mocked EmLocator
     * @author Le Ha An
     */
    @Test
    public void testGetCompanyForExportExcelHasNoData(@Mocked final EmLocator emLocator) {
        new Expectations() {
            {
                EmLocator.getEm().createQuery(this.anyString, Company_mst.class).getResultList();
                this.result = Collections.emptyList();
            }
        };

        List<Company_mst> result = this.service.getCompanyForExportExcel();

        new Verifications() {
            {
                EmLocator.getEm().createQuery(this.anyString, Company_mst.class).getResultList();
                this.times = 1;
            }

            {
                Assertions.assertThat(CollectionUtils.isEmpty(result)).isTrue();
            }
        };
    }

    /**
     * Test case for situation: - Get all basepoint and has data. Expect: - Return Data: List basepoint, Not null, Equal
     * expected data;
     *
     * @author Le Ha An
     */
    @Test
    public void testGetAllBasepointInfoHasData() {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.basePointRepo.findAll();
                this.result = CompanyManagementServiceTest.this.listBasepoint;
            }
        };

        List<Basepoint_info> result = this.service.getAllBasepointInfo();

        new Verifications() {
            {
                CompanyManagementServiceTest.this.basePointRepo.findAll();
                this.times = 1;
            }

            {
                Assertions.assertThat(CollectionUtils.isNotEmpty(result)).isTrue();
                Assertions.assertThat(result.size()).isEqualTo(CompanyManagementServiceTest.this.listBasepoint.size());
            }
        };
    }

    /**
     * Test case for situation: - Get companies when export excel and has no data. Expect: - Return Data: List company,
     * empty;
     *
     * @author Le Ha An
     */
    @Test
    public void testGetAllBasepointInfoHasNoData() {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.basePointRepo.findAll();
                this.result = Collections.emptyList();
            }
        };

        List<Basepoint_info> result = this.service.getAllBasepointInfo();

        new Verifications() {
            {
                CompanyManagementServiceTest.this.basePointRepo.findAll();
                this.times = 1;
            }

            {
                Assertions.assertThat(CollectionUtils.isEmpty(result)).isTrue();
            }
        };
    }

    /**
     * Test case for situation: - Get select item head office and has no data. Expect: - Return Data: List select item,
     * empty;
     *
     * @author Le Ha An
     */
    @Test
    public void testGetSelectItemHeadOfficeHasNoData() {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.addresspointRepo.findAll();
                this.result = Collections.emptyList();
            }
        };

        List<SelectItem> result = this.service.getSelectItemHeadOffice();

        new Verifications() {
            {
                CompanyManagementServiceTest.this.addresspointRepo.findAll();
                this.times = 1;
            }

            {
                Assertions.assertThat(CollectionUtils.isEmpty(result)).isTrue();
            }
        };
    }

    /**
     * Test case for situation: - Get select item head office and has data. Expect: - Return Data: List select item, Not
     * null, Equal expected data;
     *
     * @author Le Ha An
     */
    @Test
    public void testGetSelectItemHeadOfficeHasData() {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.addresspointRepo.findAll();
                this.result = CompanyManagementServiceTest.this.listBranch;
            }
        };

        List<SelectItem> result = this.service.getSelectItemHeadOffice();

        new Verifications() {
            {
                CompanyManagementServiceTest.this.addresspointRepo.findAll();
                this.times = 1;
            }

            {
                Assertions.assertThat(CollectionUtils.isNotEmpty(result)).isTrue();
            }
        };
    }

    /**
     * Test case for situation: - Get select item industry_big_mst and has no data. Expect: - Return Data: List select
     * item, empty;
     *
     * @author Le Ha An
     */
    @Test
    public void testGetSelectItemIndustryBigMstHasNoData() {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.industryService.getAllIndustryBig();
                this.result = Collections.emptyList();
            }
        };

        List<SelectItem> result = this.service.getSelectItemIndustryBigMst();

        new Verifications() {
            {
                CompanyManagementServiceTest.this.industryService.getAllIndustryBig();
                this.times = 1;
            }

            {
                Assertions.assertThat(CollectionUtils.isEmpty(result)).isTrue();
            }
        };
    }

    /**
     * Test case for situation: - Get select item industry_big_mst and has data. Expect: - Return Data: List select
     * item, Not null, Equal expected data;
     *
     * @author Le Ha An
     */
    @Test
    public void testGetSelectItemIndustryBigMstHasData() {
        new Expectations() {
            {
                CompanyManagementServiceTest.this.industryService.getAllIndustryBig();
                this.result = CompanyManagementServiceTest.this.listIndustry;
            }
        };

        List<SelectItem> result = this.service.getSelectItemIndustryBigMst();

        new Verifications() {
            {
                CompanyManagementServiceTest.this.industryService.getAllIndustryBig();
                this.times = 1;
            }

            {
                Assertions.assertThat(CollectionUtils.isNotEmpty(result)).isTrue();
                Assertions.assertThat(result.size()).isEqualTo(CompanyManagementServiceTest.this.listIndustry.size());
            }
        };
    }

}
