package arrow.businesstraceability.control.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Branch_position;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.businesstraceability.persistence.repository.Branch_positionRepository;
import arrow.businesstraceability.persistence.repository.Employee_mstRepository;
import arrow.businesstraceability.persistence.repository.Position_mstRepository;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.logging.BaseLogger;

@RunWith(JMockit.class)
public class BranchPossitionServiceTest {

    @Mocked
    @Tested
    BranchPositionService branchPositionService;


    /** The repo. */
    @Injectable
    private Branch_positionRepository branch_positionRepository;

    /** The address point repo. */
    @Injectable
    private Addresspoint_mstRepository addresspointRepo;

    /** The Employee_mst Repository . */
    @Injectable
    private Employee_mstRepository empRepo;

    /** The Position_mst Repository . */
    @Injectable
    private Position_mstRepository posRepo;

    /** The log. */
    @Injectable
    protected BaseLogger log;

    /** The em main. */
    @Injectable
    protected EntityManager emMain;

    /** The current user. */
    @Injectable
    protected UserCredential currentUser;

    List<Addresspoint_mst> listAddress;

    Addresspoint_mst address1;

    Addresspoint_mst address2;

    Branch_position branch1;

    Branch_position branch2;

    Branch_position branch3;

    Branch_position branch4;

    List<Branch_position> listInDB;

    List<Branch_position> listInScreen;

    List<Branch_position> listInDBDifferentPosCode;

    List<Branch_position> listInScreenDifferentPosCode;

    List<Branch_position> listInScreenSameInDb;

    List<Branch_position> listInScreenDifferentSize;

    @Before
    public void before() {

        this.address1 = new Addresspoint_mst("01");
        this.address1.setAdp_name("abc");
        this.address2 = new Addresspoint_mst("02");
        this.address2.setAdp_name("xyz");
        this.listAddress = new ArrayList<Addresspoint_mst>();
        this.listAddress.add(this.address1);
        this.listAddress.add(this.address2);
        this.branch1 = new Branch_position(01);
        this.branch2 = new Branch_position(02);
        this.branch3 = new Branch_position(03);
        this.branch4 = new Branch_position(04);
        this.branch1.setAdp_code("1");
        this.branch2.setAdp_code("1");
        this.branch3.setAdp_code("1");
        this.branch4.setAdp_code("1");

        this.branch1.setEmployee_code(1);
        this.branch3.setEmployee_code(3);
        this.branch2.setEmployee_code(2);
        this.branch1.setPos_code((short) 1);
        this.branch2.setPos_code((short) 2);
        this.branch3.setPos_code((short) 3);

        this.branch4.setEmployee_code(1);
        this.branch4.setPos_code((short) 4);

        this.listInDB = new ArrayList<Branch_position>();
        this.listInDB.add(this.branch2);
        this.listInDB.add(this.branch3);
        this.listInScreenDifferentPosCode = new ArrayList<Branch_position>();
        this.listInScreenDifferentPosCode.add(this.branch4);

        this.listInDBDifferentPosCode = new ArrayList<Branch_position>();
        this.listInDBDifferentPosCode.add(this.branch1);

        this.listInScreen = new ArrayList<Branch_position>();
        this.listInScreen.add(this.branch1);
        this.listInScreen.add(this.branch2);
        this.listInScreenSameInDb = new ArrayList<Branch_position>();
        this.listInScreenSameInDb.add(this.branch2);
        this.listInScreenSameInDb.add(this.branch3);
        this.listInScreenDifferentSize = new ArrayList<Branch_position>();
        this.listInScreenDifferentSize.add(this.branch2);
        this.listInScreenDifferentSize.add(this.branch3);
        this.listInScreenDifferentSize.add(this.branch2);
        this.listInScreenDifferentSize.add(this.branch3);

    }

    // Verify create DTO
    @Test
    public void createDTO() {
        new Expectations() {
            {
                BranchPossitionServiceTest.this.addresspointRepo.findAndRefresh((Addresspoint_mst) this.any);
                this.result = BranchPossitionServiceTest.this.address1;
            }
        };

        ServiceResult<Addresspoint_mst> result = this.branchPositionService.selectAddressPointInDb(this.address1);
        Assert.assertTrue(result.isSuccessful() == true);
        Assert.assertTrue(result.getData().getAdp_code().equals("01"));
        Assert.assertTrue(result.getData().getAdp_name().equals("abc"));
    }

    // Verify save BranchPosition Successful
    @Test
    public void saveBranchPositionTestSuccessful(@Mocked final BeanCopier bc) {

        new Expectations() {
            {
                BranchPossitionServiceTest.this.branchPositionService.getListBranchPositionByAdpCode(this.anyString,
                        this.anyBoolean);
                this.result = BranchPossitionServiceTest.this.listInDB;
            }
        };

        ServiceResult<Branch_position> result = this.branchPositionService.saveBranchPosition("01", this.listInScreen);
        Assert.assertTrue(result.isSuccessful() == true);
        Assertions.assertThat(result.getMessages().get(0).getMessageCode()).isEqualTo(
                InfoMessage.common_001_save_successfully().getMessageCode());

        new Verifications() {
            {
                BranchPossitionServiceTest.this.branchPositionService.getListBranchPositionByAdpCode(this.anyString,
                        this.anyBoolean);
                this.times = 1;
                BranchPossitionServiceTest.this.branch_positionRepository
                .saveAndFlushAndRefresh((Branch_position) this.any);
                this.times = 3;
            }
        };
    }

    // Verify save BranchPosition UnSuccessful
    @Test
    public void saveBranchPositionTestUnSuccessful(@Mocked final BeanCopier bc) {

        new Expectations() {
            {
                BranchPossitionServiceTest.this.branch_positionRepository
                .saveAndFlushAndRefresh((Branch_position) this.any);
                this.result = new PersistenceException();
            }
        };
        ServiceResult<Branch_position> result = this.branchPositionService.saveBranchPosition("01", this.listInScreen);
        Assert.assertTrue(result.isSuccessful() == false);
        Assertions.assertThat(result.getMessages().get(0).getMessageCode()).isEqualTo(
                ErrorMessage.common_002_save_unsuccessfully().getMessageCode());
    }

    // Verify method isChange with case same Size but different value employeeCode
    @Test
    public void isChangeSameSizeDifferentEmpCode() {

        new Expectations() {
            {
                BranchPossitionServiceTest.this.branch_positionRepository
                .findByAdpCode(this.anyString, this.anyBoolean).getResultList();
                this.result = BranchPossitionServiceTest.this.listInDB;
            }
        };
        Boolean result = this.branchPositionService.isChange("01", this.listInScreen);
        Assert.assertTrue(result == true);

    }

    // Verify method isChange with case same Size but different value possition Code
    @Test
    public void isChangeSameSizeDifferentPosCode() {

        new Expectations() {
            {
                BranchPossitionServiceTest.this.branch_positionRepository
                        .findByAdpCode(this.anyString, this.anyBoolean).getResultList();
                this.result = BranchPossitionServiceTest.this.listInDBDifferentPosCode;
            }
        };
        Boolean result = this.branchPositionService.isChange("01", this.listInScreenDifferentPosCode);
        Assert.assertTrue(result == true);

    }

    // Verify method isChange with case different Size
    @Test
    public void isChangeDifferentSize() {

        new Expectations() {
            {
                BranchPossitionServiceTest.this.branch_positionRepository
                        .findByAdpCode(this.anyString, this.anyBoolean).getResultList();
                this.result = BranchPossitionServiceTest.this.listInDB;
            }
        };
        Boolean result = this.branchPositionService.isChange("01", this.listInScreenDifferentSize);
        Assert.assertTrue(result == true);

    }

    // Verify method isChange with case list in DB and in Screen is same
    @Test
    public void isChangeTwoListSame() {

        new Expectations() {
            {
                BranchPossitionServiceTest.this.branch_positionRepository
                .findByAdpCode(this.anyString, this.anyBoolean).getResultList();
                this.result = BranchPossitionServiceTest.this.listInDB;
            }
        };
        Boolean result = this.branchPositionService.isChange("01", this.listInScreenSameInDb);
        Assert.assertTrue(result == false);

    }
}
