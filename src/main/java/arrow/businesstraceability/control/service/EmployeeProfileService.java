package arrow.businesstraceability.control.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.control.bean.EmployeeProfileBean;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.User_login;
import arrow.businesstraceability.persistence.mapped.User_login_MAPPED;
import arrow.businesstraceability.persistence.repository.Employee_mstRepository;
import arrow.businesstraceability.util.ArrowStringUtils;
import arrow.businesstraceability.util.EncryptStringUtils;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.util.StringUtils;

/**
 * The Class EmployeeProfileService.
 */
@Named
@ConversationScoped
public class EmployeeProfileService extends AbstractService {

    /** The repo. */
    @Inject
    private Employee_mstRepository repo;

    /**
     * Update profile.
     *
     * @param emp the emp
     * @return the service result
     */
    public ServiceResult<Employee_mst> updateProfile(final EmployeeProfileBean emp) {
        final List<Message> errors = new ArrayList<>();
        Employee_mst user = this.repo.findAndRefresh(emp.getEmployeeBean());
        try {
            user.setEmp_mail(emp.getEmpEmail());
            user.setEmp_mobile(emp.getEmpMobile());
            user.setEmp_tel(emp.getEmpTel());
            user = this.emMain.merge(user);
            return new ServiceResult<>(true, user);
        } catch (final PersistenceException e) {
            super.log.debug("EXCEPTION WHEN UPDATE PROFILE EMPLOYEE", e);
            errors.add(ErrorMessage.emp_001_update_profile_unsuccessfully());
            return new ServiceResult<>(false, errors);
        }
    }

    /**
     * Update password.
     *
     * @param emp the emp
     * @return the service result
     */
    public ServiceResult<User_login> updatePassword(final EmployeeProfileBean emp) {
        final List<Message> errors = new ArrayList<>();

        User_login user =
            this.emMain.find(User_login.class, new User_login_MAPPED.Pk(emp.getEmployeeBean().getEmp_code()));

        try {
            // user.setUl_password(emp.getNewPass());
            user.setUl_passsalt(EncryptStringUtils.encrypt(emp.getNewPass()));
            user = this.emMain.merge(user);
            return new ServiceResult<>(true, user);
        } catch (final PersistenceException e) {
            super.log.debug("EXCEPTION WHEN UPDATE PASSWORD EMPLOYEE", e);
            errors.add(ErrorMessage.emp_002_change_password_unsucessfully());
            return new ServiceResult<>(false, errors);
        }

    }

    /**
     * Validate change password.
     *
     * @param oldPass the old pass
     * @param newPass the new pass
     * @param confirmPass the confirm pass
     * @param userCode the user code
     * @return the service result
     */
    public ServiceResult<Boolean> validateChangePassword(final String oldPass, final String newPass,
        final String confirmPass, final int userCode) {
        final List<Message> errors = new ArrayList<>();
        final User_login currentUser = super.emMain.find(User_login.class, new User_login_MAPPED.Pk(userCode));
        if (currentUser != null) {
            // if (!StringUtils.areEqual(oldPass, currentUser.getUl_password())) {
            if (!StringUtils.areEqual(EncryptStringUtils.encrypt(oldPass), currentUser.getUl_passsalt())) {
                errors.add(ErrorMessage.emp_003_password_is_not_correct());
            } else {
                if ((newPass.length() > ArrowStringUtils.MAX_LENGTH_PASSWORD)
                    || (newPass.length() < ArrowStringUtils.MIN_LENGTH_PASSWORD)) {
                    errors.add(ErrorMessage.emp_004_password_length_is_violated());
                }
                if (!ArrowStringUtils.isAlphanumeric(newPass)) {
                    errors.add(ErrorMessage.emp_005_password_must_be_alphanumberic());
                }

                if (!StringUtils.areEqual(newPass, confirmPass)) {
                    errors.add(ErrorMessage.emp_006_password_confirm_not_matched());
                }
            }
        } else {
            errors.add(ErrorMessage.emp_007_user_does_not_exist());
        }
        return new ServiceResult<>(errors.isEmpty(), errors);
    }

    /**
     * Validate password.
     *
     * @param password the password
     * @return the service result
     */
    public ServiceResult<Boolean> validatePassword(final String password) {
        final List<Message> errors = new ArrayList<>();
        if ((password.length() > ArrowStringUtils.MAX_LENGTH_PASSWORD)
            || (password.length() < ArrowStringUtils.MIN_LENGTH_PASSWORD)) {
            errors.add(ErrorMessage.emp_004_password_length_is_violated());
        }
        if (!ArrowStringUtils.isAlphanumeric(password)) {
            errors.add(ErrorMessage.emp_005_password_must_be_alphanumberic());
        }

        return new ServiceResult<Boolean>(errors.isEmpty(), errors);
    }

    /**
     * Validate password and confirm password.
     *
     * @param password the password
     * @param confirmPassword the confirm password
     * @return the service result
     */
    public ServiceResult<Boolean> validatePasswordAndConfirmPassword(final String password, final String confirmPassword) {
        final List<Message> errors = new ArrayList<>();
        errors.addAll(this.validatePassword(password).getMessages());

        if (!StringUtils.areEqual(password, confirmPassword)) {
            errors.add(ErrorMessage.emp_006_password_confirm_not_matched());
        }

        return new ServiceResult<Boolean>(errors.isEmpty(), errors);
    }
}
