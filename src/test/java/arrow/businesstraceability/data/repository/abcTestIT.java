package arrow.businesstraceability.data.repository;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.CreateSchema;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;

@CreateSchema({"truncate.sql"})
// @BelongsTo(AppSuite.class)
@RunWith(Arquillian.class)
public class abcTestIT {


    @Inject
    private Addresspoint_mstRepository repo;

    @Test
    @UsingDataSet("AddresspointRepositoryIT2.xls")
    public void testGetDistinctAddressPointOfSalesPersonBaseOnCustomerCode() throws Exception {
        System.out.println("==================testAbc=================");
        String companyCode = "4000000649";
        List<Addresspoint_mst> listAll = this.repo.getDistinctAddressPointOfSalesPersonBaseOnCustomerCode(companyCode);
        Assert.assertNotNull(listAll);
        Assert.assertEquals(1, listAll.size());
    }

}
