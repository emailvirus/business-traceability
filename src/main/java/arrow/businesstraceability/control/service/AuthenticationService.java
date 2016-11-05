package arrow.businesstraceability.control.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.control.helper.AuthenticationData;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.User_login;
import arrow.businesstraceability.persistence.mapped.User_login_MAPPED;
import arrow.businesstraceability.persistence.repository.User_loginRepository;
import arrow.businesstraceability.util.ArrowStringUtils;
import arrow.businesstraceability.util.EncryptStringUtils;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.util.DateUtils;
import arrow.framework.util.StringUtils;

/**
 * The Class AuthenticationService.
 */
public class AuthenticationService extends AbstractService {

    @Inject
    private User_loginRepository repo;

    /**
     * system login.
     *
     * @author ducva
     * @param employeeId the employee id
     * @param password the password
     * @return the service result
     */
    public ServiceResult<AuthenticationData> login(final String employeeId, final String password) {
        final ServiceResult<User_login> result = this.validateCredential(employeeId, password);

        // validate successfully without errors
        if (result.isSuccessful()) {

            boolean isFirstLogin = (result.getData().getUl_login_time() == null);

            User_login entity = this.updateLoginTime(result.getData(), DateUtils.getCurrentDatetime());

            // login success
            final AuthenticationData authData =
                    new AuthenticationData(entity.getUl_user_code(), entity.getEmployee_mst());
            authData.setFirstLogin(isFirstLogin);

            return new ServiceResult<>(true, authData, result.getMessages());
        }

        return new ServiceResult<>(result.isSuccessful(), result.getMessages());
    }

    /**
     * Validate credential.
     *
     * @param username the username
     * @param password the password
     * @param emMain the em main
     * @return the list
     */
    private ServiceResult<User_login> validateCredential(final String username, final String password) {
        final List<Message> errors = new ArrayList<>();

        if (!ArrowStringUtils.isNumeric(username)) {
            errors.add(ErrorMessage.auth_001_invalid_user_or_password());
            return new ServiceResult<User_login>(false, errors);
        }

        final User_login entity = this.repo.findBy(new User_login_MAPPED.Pk(Integer.parseInt(username)));

        if ((entity == null) || (entity.getEmployee_mst() == null)) {
            errors.add(ErrorMessage.auth_001_invalid_user_or_password());
            return new ServiceResult<User_login>(false, errors);
        }

        String encryptedPass = EncryptStringUtils.encrypt(password);
        if (!StringUtils.areEqual(encryptedPass, entity.getUl_passsalt())) {
            errors.add(ErrorMessage.auth_001_invalid_user_or_password());
            return new ServiceResult<User_login>(false, errors);
        } else if (this.isRetiredEmployee(entity.getEmployee_mst())) {
            errors.add(ErrorMessage.auth_002_employee_has_retired());
            return new ServiceResult<User_login>(false, errors);
        }
        return new ServiceResult<User_login>(true, entity);
    }

    private boolean isRetiredEmployee(final Employee_mst employee_mst) {
        return AuthenticationConstants.EmployeeStatus.RETIRED
                .equalsIgnoreCase(employee_mst.getEmp_condi_code() == null ? null : employee_mst.getEmp_condi_code()
                        .toString());
    }

    /**
     * Update login time.
     *
     * @param userLogin the user login
     * @param loginTime the login time
     * @return the user_login
     */
    private User_login updateLoginTime(final User_login userLogin, final java.util.Date loginTime) {
        userLogin.setUl_login_time_DIRECT(loginTime);
        userLogin.setUl_session_id(null);
        return this.repo.saveAndFlushAndRefresh(userLogin);
    }


}
