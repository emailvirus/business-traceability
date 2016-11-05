/**
 * -------------------------------------------------------- All Rights Reserved. Copyright(C) Arrow Technology, Ltd.
 * Vendor : Arrow Technology, Ltd. Author: Le Ha An Since: November 10, 2015
 * --------------------------------------------------------
 */

package arrow.businesstraceability.control.service;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.control.helper.AuthenticationData;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.User_login;
import arrow.businesstraceability.persistence.mapped.User_login_MAPPED;
import arrow.businesstraceability.persistence.repository.User_loginRepository;
import arrow.businesstraceability.util.EncryptStringUtils;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.logging.BaseLogger;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

@RunWith(JMockit.class)
public class AuthenticationServiceTest {

    @Mocked
    @Tested
    AuthenticationService service;

    @Injectable
    private BaseLogger logger;

    @Injectable
    protected EntityManager emMain;

    @Injectable
    private UserCredential currentUser;

    @Injectable
    private User_loginRepository repo;

    User_login user;

    Employee_mst employee;

    User_login retiredUser;

    User_login correctUser;

    Employee_mst retiredEmployee;

    private static String FAKE_USERNAME = "1000001";

    private static String FAKE_PASSWORD = "password";

    private static String FAKE_PASSSALT = "passsalt";

    // init data before test

    /**
     * Method prepare data. Run before test.
     *
     * @author Le Ha An
     */
    @Before
    public void before() {
        this.employee = new Employee_mst(1000001);
        this.user = new User_login(this.employee);
        this.user.setUl_passsalt(AuthenticationServiceTest.FAKE_PASSSALT);

        this.retiredEmployee = new Employee_mst(1000002);

        this.retiredEmployee.setEmp_condi_code(AuthenticationConstants.EmployeeStatus.RETIRED.charAt(0));
        this.retiredUser = new User_login(this.retiredEmployee);
        this.retiredUser.setUl_passsalt(EncryptStringUtils.encrypt("triarrow"));

        Employee_mst correctEmployee = new Employee_mst(9999999);

        correctEmployee.setEmp_condi_code(AuthenticationConstants.EmployeeStatus.WORKING.charAt(0));
        this.correctUser = new User_login(correctEmployee);
        this.correctUser.setUl_passsalt(EncryptStringUtils.encrypt("triarrow"));
    }

    /**
     * Test login but failed because input username not is numeric. Expect: - Status: Failed; - Return data: Null; -
     * Return message: List message, Not null;
     *
     * @author Le Ha An
     */
    @Test
    public void testLoginFailedBecauseUsernameIsNotNumeric() {
        ServiceResult<AuthenticationData> result =
                this.service.login(AuthenticationServiceTest.FAKE_USERNAME, AuthenticationServiceTest.FAKE_PASSWORD);

        Assertions.assertThat(result.isNotSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages()).isNotNull();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(1);
        Assertions.assertThat(result.getMessages().get(0).getMessageCode())
                .isEqualTo(ErrorMessage.auth_001_invalid_user_or_password().getMessageCode());
    }

    /**
     * Test login but failed because not found user in database. Expect: - Status: Failed; - Return data: Null; - Return
     * message: List message, Not null;
     *
     * @author Le Ha An
     */
    @Test
    public void testLoginFailedBecauseNotFoundDataInDb() {
        new Expectations() {
            {
                AuthenticationServiceTest.this.repo.findBy(new User_login_MAPPED.Pk(Integer.parseInt("11111")));
                this.result = null;
            }
        };

        ServiceResult<AuthenticationData> result = this.service.login("11111", AuthenticationServiceTest.FAKE_PASSWORD);

        new Verifications() {
            {
                AuthenticationServiceTest.this.repo.findBy(new User_login_MAPPED.Pk(Integer.parseInt("11111")));
                this.times = 1;
            }
        };
        Assertions.assertThat(result.isNotSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages()).isNotNull();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(1);
        Assertions.assertThat(result.getMessages().get(0).getMessageCode())
                .isEqualTo(ErrorMessage.auth_001_invalid_user_or_password().getMessageCode());
    }

    /**
     * Test login but failed because input wrong password. Expect: - Status: Failed; - Return data: Null; - Return
     * message: List message, Not null;
     *
     * @author Le Ha An
     */
    @Test
    public void testLoginFailedBecauseWrongPassword() {
        new Expectations() {
            {
                AuthenticationServiceTest.this.repo.findBy(new User_login_MAPPED.Pk(Integer.parseInt("9999999")));
                this.result = AuthenticationServiceTest.this.user;
            }
        };

        ServiceResult<AuthenticationData> result =
                this.service.login("9999999", AuthenticationServiceTest.FAKE_PASSWORD);

        new Verifications() {
            {
                AuthenticationServiceTest.this.repo.findBy(new User_login_MAPPED.Pk(Integer.parseInt("9999999")));
                this.times = 1;
            }
        };

        Assertions.assertThat(result.isNotSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages()).isNotNull();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(1);
        Assertions.assertThat(result.getMessages().get(0).getMessageCode())
                .isEqualTo(ErrorMessage.auth_001_invalid_user_or_password().getMessageCode());
    }

    /**
     * Test login but failed because input wrong password. Expect: - Status: Failed; - Return data: Null; - Return
     * message: List message, Not null;
     *
     * @param encrypt: mocked EncryptStringUtils
     * @author Le Ha An
     */
    @Test
    public void testLoginFailedBecauseEmployeeIsRetired() {
        new Expectations() {
            {
                AuthenticationServiceTest.this.repo.findBy(new User_login_MAPPED.Pk(Integer.parseInt("9999999")));
                this.result = AuthenticationServiceTest.this.retiredUser;
            }
        };

        ServiceResult<AuthenticationData> result = this.service.login("9999999", "triarrow");

        new Verifications() {
            {
                AuthenticationServiceTest.this.repo.findBy(new User_login_MAPPED.Pk(Integer.parseInt("9999999")));
                this.times = 1;
            }
        };

        Assertions.assertThat(result.isNotSuccessful()).isTrue();
        Assertions.assertThat(result.getMessages()).isNotNull();
        Assertions.assertThat(result.getMessages().size()).isEqualTo(1);
        Assertions.assertThat(result.getMessages().get(0).getMessageCode())
                .isEqualTo(ErrorMessage.auth_002_employee_has_retired().getMessageCode());
    }

    /**
     * Test login success. Expect: - Status: Success; - Return data: Not Null; - Return message: List message, Not null;
     *
     * @author Le Ha An
     */
    @Test
    public void testLoginSuccess() {
        new Expectations() {
            {
                AuthenticationServiceTest.this.repo.findBy(new User_login_MAPPED.Pk(Integer.parseInt("9999999")));
                this.result = AuthenticationServiceTest.this.correctUser;
            }
        };

        ServiceResult<AuthenticationData> result = this.service.login("9999999", "triarrow");

        new Verifications() {
            {
                AuthenticationServiceTest.this.repo.saveAndFlushAndRefresh(AuthenticationServiceTest.this.correctUser);
                this.times = 1;
            }
        };
        Assertions.assertThat(result.isSuccessful()).isTrue();
        Assertions.assertThat(result.getData()).isNotNull();
        Assertions.assertThat(result.getData().getEmployee()).isNotNull();
        Assertions.assertThat(result.getMessages().isEmpty()).isTrue();
    }


}
