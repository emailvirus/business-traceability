package arrow.businesstraceability.control.service;

import javax.persistence.EntityManager;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.framework.logging.BaseLogger;


@RunWith(JMockit.class)
public class AddressPointServiceTest {
    private static final String ADP_CODE = "9999";

    @Mocked
    @Tested
    private AddressPointService service;

    @Mocked
    @Injectable
    private BaseLogger logger;

    @Mocked
    @Injectable
    protected EntityManager emMain;

    @Mocked
    @Injectable
    protected UserCredential currentUser;

    @Mocked
    @Injectable
    private Addresspoint_mstRepository repo;

    private Addresspoint_mst addressResultFind;

    @Before
    public void init() {
        this.addressResultFind = new Addresspoint_mst(AddressPointServiceTest.ADP_CODE);
    }

    @Test
    public void testFindAddresspointByAdpCode() {
        new Expectations() {
            {
                AddressPointServiceTest.this.repo.findAddresspointByCode(this.anyString).getAnyResult();
                this.result = AddressPointServiceTest.this.addressResultFind;
            }
        };

        Addresspoint_mst result = this.service.findAddresspointByAdpCode(AddressPointServiceTest.ADP_CODE);

        new Verifications() {
            {
                AddressPointServiceTest.this.repo.findAddresspointByCode(this.anyString).getAnyResult();
                this.times = 1;
            }
        };

        Assertions.assertThat(result.getAdp_code()).isEqualTo(AddressPointServiceTest.ADP_CODE);
    }
}
