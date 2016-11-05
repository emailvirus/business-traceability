package arrow.businesstraceability.control.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.persistence.PersistenceException;

import org.omnifaces.util.Faces;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

import freemarker.template.TemplateException;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.persistence.entity.User_login;
import arrow.businesstraceability.util.EncryptStringUtils;
import arrow.businesstraceability.util.mail.EmailHelper;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.util.DateUtils;
import arrow.framework.util.StringUtils;

/**
 * The Class RecoverPasswordService.
 */
@Named
@ConversationScoped
public class RecoverPasswordService extends AbstractService {

    /**
     * Send employee info.
     *
     * @param email the email
     * @param employeeCode the employee code
     * @return the service result
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws TemplateException the template exception
     * @throws MessagingException the messaging exception
     */

    public ServiceResult<User_login> sendEmployeeInfo(final String email, final String employeeCode)
            throws IOException, TemplateException, MessagingException {
        final List<Message> errors = new ArrayList<>();

        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT u FROM User_login u, Employee_mst e ").append(" WHERE ");
        sb.append("u.ul_user_code=e.emp_code").append(" AND ");
        sb.append("e.emp_mail='" + email).append("' AND ");
        sb.append("e.emp_code=" + employeeCode).append("");
        List<User_login> users = super.emMain.createQuery(sb.toString(), User_login.class).getResultList();

        if (users.size() > 0) {
            for (final User_login user : users) {
                final UUID uniqueId = UUID.randomUUID();
                final String sessionId = uniqueId.toString();
                if (this.updateSessionId(user, sessionId).isSuccessful()) {
                    final StringBuilder url = new StringBuilder();
                    url.append(Faces.getRequestBaseURL());
                    url.append(AuthenticationConstants.INDEX_PAGE + "?");
                    url.append("path=recover_password").append("&");
                    url.append("sessionId=" + sessionId);
                    EmailHelper.sendRecoverPassword(user, email, url.toString());
                }
            }
        }
        else {
            errors.add(ErrorMessage.resetpass_005_employeeId_or_email_not_correct());
            return new ServiceResult<User_login>(false, errors);
        }
        return new ServiceResult<User_login>(true, errors);
    }

    /**
     * Update session id.
     *
     * @param user the user
     * @param sessionId the session id
     * @return the service result
     */
    @SuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT", justification = "Not use return value")
    public ServiceResult<Boolean> updateSessionId(final User_login user, final String sessionId) {
        final List<Message> errors = new ArrayList<>();
        try {
            user.setUl_session_id(sessionId);
            this.emMain.merge(user);
        } catch (final PersistenceException e) {
            super.log.debug("EXCEPTION WHEN UPDATE SESSIONID INTO USER_LOGIN TABLE", e);
            errors.add(ErrorMessage.resetpass_001_have_not_send());
            return new ServiceResult<>(false, errors);
        }
        return new ServiceResult<>(true, errors);
    }

    /**
     * Change password.
     *
     * @param newPassword the new password
     * @param reNewPassword the re new password
     * @param sessionId the session id
     * @return the service result
     */
    public ServiceResult<User_login> changePassword(final String newPassword, final String reNewPassword,
            final String sessionId) {
        final List<Message> errors = new ArrayList<>();
        if (!StringUtils.areEqual(newPassword, reNewPassword)) {
            errors.add(ErrorMessage.resetpass_004_password_confirm_not_matched());
        }
        if (errors.size() > 0) {
            return new ServiceResult<>(errors.isEmpty(), errors);
        }

        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT u FROM User_login u").append(" WHERE ");
        sb.append("u.ul_session_id='" + sessionId).append("' AND ");
        sb.append("u.last_updated_at >= '" + DateUtils.getPreviousDayFromCurrentDate(DateUtils.getCurrentDatetime()))
                .append("'");
        final List<User_login> users = super.emMain.createQuery(sb.toString(), User_login.class).getResultList();
        if (users.size() > 0) {
            for (final User_login user : users) {
                try {

                    // user.setUl_password(newPassword);
                    user.setUl_passsalt(EncryptStringUtils.encrypt(newPassword));
                    user.setUl_session_id(null);
                    // this.emMain.merge(user);
                    this.emMain.flush();
                } catch (final PersistenceException e) {
                    super.log.debug("EXCEPTION WHEN CHANGE PASSWORD , MAY BE NOT ENCRYPT PASSWORD OR INSERT", e);
                    errors.add(ErrorMessage.resetpass_003_change_pass_not_success());
                    return new ServiceResult<>(false, errors);
                }
            }
            return new ServiceResult<>(true);
        }
        else {
            errors.add(ErrorMessage.resetpass_002_sessionid_expired());
        }
        return new ServiceResult<>(false, errors);
    }
}
