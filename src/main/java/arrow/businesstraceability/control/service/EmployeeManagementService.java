package arrow.businesstraceability.control.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.ConversationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.persistence.PersistenceException;

import freemarker.template.TemplateException;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.config.SysConfig;
import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.control.exception.TransactionRollbackException;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.persistence.dto.Employee_mst_DTO;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Authority_mst;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Position_mst;
import arrow.businesstraceability.persistence.entity.User_login;
import arrow.businesstraceability.persistence.mapped.Employee_mst_MAPPED;
import arrow.businesstraceability.persistence.mapped.User_login_MAPPED;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.businesstraceability.persistence.repository.Authority_mstRepository;
import arrow.businesstraceability.persistence.repository.Employee_mstRepository;
import arrow.businesstraceability.persistence.repository.Position_mstRepository;
import arrow.businesstraceability.util.ArrowStringUtils;
import arrow.businesstraceability.util.EncryptStringUtils;
import arrow.businesstraceability.util.mail.EmailHelper;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.faces.message.WarnMessage;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.persistence.ArrowQuery;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.StringUtils;

/**
 * The Class EmployeeManagementService.
 */
@Named
@ConversationScoped
public class EmployeeManagementService extends AbstractService {

    /** The employee mst business. */
    @Inject
    private EmployeeService employeeMstBusiness;

    /** The addresspoint repo. */
    @Inject
    private Addresspoint_mstRepository addresspointRepo;

    /** The position repo. */
    @Inject
    private Position_mstRepository positionRepo;

    /** The authority repo. */
    @Inject
    private Authority_mstRepository authorityRepo;

    /** The employee repo. */
    @Inject
    private Employee_mstRepository employeeRepo;

    /**
     * Filter datatable.
     *
     * @author lehoangngochan
     * @return list companies
     */
    public List<Employee_mst> getListEmployee() {
        final ArrowQuery<Employee_mst> query = new ArrowQuery<>(super.emMain);
        query.select("e").from("Employee_mst e ");

        query.addFilter("employee_code", "cast(e.emp_code AS text) LIKE ?");
        query.addSorter("employee_code", "e.emp_code");
        query.addFilter("employee_head_office", "upper(e.addresspoint_mst.adp_code) LIKE ?");
        query.addSorter("employee_head_office", "e.addresspoint_mst.adp_code");
        query.addNumberFilter("employee_position", "cast(e.position_mst.pos_code as double)");
        query.addSorter("employee_position", "e.position_mst.pos_code");
        query.addFilter("employee_name", "upper(e.emp_name) LIKE ?");
        query.addSorter("employee_name", "e.emp_name");
        query.addNumberFilter("employee_authority", "cast(e.emp_settle_authority as double)");
        query.addSorter("employee_authority", "e.emp_settle_authority");

        // query.addNumberFilter("employee_authority", "cast(e.emp_settle_authority as double)");
        // query.addDateRangeFilter("employee_hire_date", "e.emp_entery_date");
        query.addDateFromToFilter("employee_hire_date", "e.emp_entery_date");
        query.addSorter("employee_hire_date", "e.emp_entery_date");

        query.addFilter("employee_tel", "upper(e.emp_tel) LIKE ?");
        query.addSorter("employee_tel", "e.emp_tel");
        query.addFilter("employee_mobile", "upper(e.emp_mobile) LIKE ?");
        query.addSorter("employee_mobile", "e.emp_mobile");
        query.addFilter("employee_email", "upper(e.emp_mail) LIKE ?");
        query.addSorter("employee_email", "e.emp_mail");

        query.orderBy("emp_code");
        return query.getResultList();
    }

    /**
     * get SelectItem for Addresspoint.
     *
     * @author lehoangngochan
     * @return list select item
     */
    public List<SelectItem> getSelectItemAddresspoint() {
        final List<SelectItem> listItemAddresspoint = new ArrayList<SelectItem>();
        listItemAddresspoint.add(new SelectItem(ArrowStringUtils.EMPTY_STRING, ArrowStringUtils.EMPTY_STRING));

        List<Addresspoint_mst> list = this.addresspointRepo.findAll();

        for (final Addresspoint_mst add : list) {
            listItemAddresspoint.add(new SelectItem(add.getAdp_code(), add.getAdp_name_DIRECT()));
        }
        return listItemAddresspoint;
    }

    /**
     * get SelectItem for Position.
     *
     * @author lehoangngochan
     * @return list SelectItem
     */
    public List<SelectItem> getSelectItemPosition() {
        final List<SelectItem> listItemPosition = new ArrayList<SelectItem>();
        listItemPosition.add(new SelectItem(ArrowStringUtils.EMPTY_STRING, ArrowStringUtils.EMPTY_STRING));
        List<Position_mst> listPositions = this.positionRepo.findAll();
        for (final Position_mst position : listPositions) {
            listItemPosition.add(new SelectItem(position.getPos_code(), position.getPos_name_DIRECT()));
        }
        return listItemPosition;
    }

    /**
     * get SelectItem for Authority.
     *
     * @author lehoangngochan
     * @return list SelectItem
     */
    public List<SelectItem> getSelectItemAuthority() {
        final List<SelectItem> listItemAuthority = new ArrayList<SelectItem>();
        listItemAuthority.add(new SelectItem(ArrowStringUtils.EMPTY_STRING, ArrowStringUtils.EMPTY_STRING));
        for (final Authority_mst authority : this.authorityRepo.findAll()) {
            listItemAuthority.add(new SelectItem(authority.getAut_code(), authority.getAut_name_DIRECT()));
        }
        return listItemAuthority;
    }

    /**
     * Gets the auto numberring employee code.
     *
     * @param adpCode the adp code
     * @return the auto numberring employee code
     */
    protected Integer getAutoNumberringEmployeeCode(final int adpCode) {
        Integer employeeCode;
        final int latestEmployeeCode = this.employeeMstBusiness.getEmployeeForAutoNumberring(adpCode);
        employeeCode = latestEmployeeCode > 0 ? latestEmployeeCode + 1 : Integer.parseInt(adpCode + "000001");

        // recheck for sure
        while (Employee_mst.find(employeeCode) != null) {
            employeeCode++;
        }
        return employeeCode;
    }

    /**
     * Gets the employee for auto numberring.
     *
     * @param adpCode the adp code
     * @return the employee for auto numberring
     */
    public int getEmployeeForAutoNumberring(final int adpCode) {
        StringBuilder query = new StringBuilder();
        final String query1 =
                "SELECT max(max_emp_code) FROM (SELECT max(emp_code) AS max_emp_code FROM employee_mst em "
                        + "WHERE CAST(em.emp_code as text) LIKE :adpCode "
                        + "AND Length(CAST(em.emp_code AS text)) = 7 GROUP BY emp_adpcode) AS t";
        final String query2 =
                "SELECT max(max_emp_code) FROM (Select max(emp_code) AS max_emp_code FROM employee_mst em "
                        + "WHERE CAST(em.emp_code as text) LIKE :adpCode "
                        + "AND Length(CAST(em.emp_code AS text)) = 8 GROUP BY emp_adpcode) AS t";
        query = (adpCode < 10) ? query.append(query1) : query.append(query2);
        final List<?> result = EmLocator.getEm().createNativeQuery(query.toString())
                .setParameter("adpCode", adpCode + "%").getResultList();
        if ((result.size() == 0) || (result.get(0) == null)) {
            return 0;
        }
        return Integer.parseInt(result.get(0).toString());
    }

    /**
     * get Data for Edit.
     *
     * @author lehoangngochan
     * @param listEmployee the list employee
     * @return ServiceResult
     */
    public ServiceResult<Employee_mst_DTO> prepareForEditEmployee(final List<Employee_mst> listEmployee) {
        ServiceResult<Employee_mst_DTO> result = null;
        List<Message> messages = new ArrayList<Message>();
        int numberSelected = 0;
        Employee_mst_DTO empDTO = null;
        for (final Employee_mst emp : listEmployee) {
            if (emp.isSelected()) {
                numberSelected++;
                if (numberSelected >= 2) {
                    break;
                }
                this.employeeRepo.findAndRefresh(emp);
                empDTO = new Employee_mst_DTO();
                BeanCopier.copy(emp, empDTO);
            }
        }
        if (numberSelected == 1) {
            result = new ServiceResult<Employee_mst_DTO>(true, empDTO);
        }
        else {
            messages.add(ErrorMessage.emp_010_please_select_one_to_edit());
            result = new ServiceResult<Employee_mst_DTO>(false, null, messages);
        }
        return result;
    }

    /**
     * action delete Employee.
     *
     * @author lehoangngochan
     * @param listEmployee the list employee
     * @return serviceResult
     */
    public ServiceResult<Employee_mst> deleteEmployee(final List<Employee_mst> listEmployee) {
        final List<Message> message = new ArrayList<>();
        Boolean isFlag = Boolean.FALSE;
        for (final Employee_mst emp : listEmployee) {
            if (emp.isSelected()) {
                isFlag = Boolean.TRUE;
                emp.setEmp_delete_flg(Boolean.TRUE);
                final User_login user = this.emMain.find(User_login.class, new User_login_MAPPED.Pk(emp.getEmp_code()));
                this.emMain.remove(user);
                emp.setSelected(false);
                message.add(InfoMessage.comp_002_delete_successfully());
            }

            // if ifFlag is true, break
            if (isFlag) {
                break;
            }
        }
        return new ServiceResult<>(isFlag, null, message);
    }

    /**
     * change status retired to working and vice versa.
     *
     * @author vanlongdao
     * @param listEmployee the list employee
     * @return serviceResult
     */
    public ServiceResult<Employee_mst> changeEmployeeStatus(final List<Employee_mst> listEmployee) {
        final List<Message> messages = new ArrayList<>();
        Boolean isChangedStatus = Boolean.FALSE;
        for (final Employee_mst emp : listEmployee) {
            if (emp.isSelected()) {
                isChangedStatus = Boolean.TRUE;
                if (AuthenticationConstants.EmployeeStatus.RETIRED
                        .equalsIgnoreCase(emp.getEmp_condi_code().toString())) {
                    emp.setEmp_condi_code(AuthenticationConstants.EmployeeStatus.WORKING.charAt(0));
                }
                else {
                    emp.setEmp_condi_code(AuthenticationConstants.EmployeeStatus.RETIRED.charAt(0));
                }
                this.emMain.persist(emp);
            }
        }
        if (isChangedStatus) {
            messages.add(InfoMessage.emp_004_change_status_successfully());
        }
        else {
            messages.add(ErrorMessage.emp_011_please_select_to_change_status());
        }
        return new ServiceResult<>(isChangedStatus, null, messages);
    }

    /**
     * Auto complete for pulldownlist head office.
     *
     * @author lehoangngochan
     * @param query the query
     * @param listAllAddressPointMst the list all address point mst
     * @return list object exist in pulldownlist
     */
    public List<Addresspoint_mst> autoCompleteHeadOffice(final String query,
            final List<Addresspoint_mst> listAllAddressPointMst) {
        final List<Addresspoint_mst> suggestions = new ArrayList<Addresspoint_mst>();
        for (final Addresspoint_mst add : listAllAddressPointMst) {
            if (add.getAdp_name().startsWith(query) || add.getAdp_code().startsWith(query)) {
                suggestions.add(add);
            }
        }
        return suggestions;
    }

    /**
     * Auto complete for pulldownlist Position.
     *
     * @author lehoangngochan
     * @param query the query
     * @param listAllPositionMst the list all position mst
     * @return list object exist in pulldownlist
     */
    public List<Position_mst> autoCompletePosition(final String query, final List<Position_mst> listAllPositionMst) {
        final List<Position_mst> suggestions = new ArrayList<Position_mst>();
        for (final Position_mst position : listAllPositionMst) {
            if (position.getPos_name_DIRECT().startsWith(query)) {
                suggestions.add(position);
            }
        }
        return suggestions;
    }

    /**
     * Auto complete for pulldownlist Authority.
     *
     * @author lehoangngochan
     * @param query the query
     * @param listAllAuthorityMst the list all authority mst
     * @return list object exist in pulldownlist
     */
    public List<Authority_mst> autoCompleteAuthority(final String query,
            final List<Authority_mst> listAllAuthorityMst) {
        final List<Authority_mst> suggestions = new ArrayList<Authority_mst>();
        for (final Authority_mst authority : listAllAuthorityMst) {
            if (authority.getAut_name_DIRECT().startsWith(query)) {
                suggestions.add(authority);
            }
        }
        return suggestions;
    }

    /**
     * get All Address.
     *
     * @author lehoangngochan
     * @return list address point
     */
    public List<Addresspoint_mst> getAllAddress() {
        return this.addresspointRepo.findAll();
    }

    /**
     * get All Position.
     *
     * @author lehoangngochan
     * @return list Position_mst
     */
    public List<Position_mst> getAllPosition() {
        return this.positionRepo.findAll();
    }

    /**
     * get All Authority.
     *
     * @author lehoangngochan
     * @return list Authority
     */
    public List<Authority_mst> getAllAuthority() {
        return this.authorityRepo.findAll();
    }

    /**
     * Validate form.
     *
     * @return the boolean
     */
    protected Boolean validateForm() {
        final Boolean isReturn = Boolean.TRUE;

        return isReturn;
    }

    /**
     * action add or edit Employee.
     *
     * @param currentEmployee the current employee
     * @return ServiceResult
     * @throws TransactionRollbackException the transaction rollback exception
     */
    public ServiceResult<Employee_mst> saveEmployee(final Employee_mst_DTO currentEmployee)
            throws TransactionRollbackException {
        final List<Message> message = new ArrayList<>();
        ServiceResult<Employee_mst> result = null;
        if ((currentEmployee != null) && (currentEmployee.getEmp_code() == 0)) {
            final Integer empCode = this.getAutoNumberringEmployeeCode(
                    Integer.parseInt(currentEmployee.getAddresspoint_mst().getAdp_code()));

            // Insert Employee
            final Employee_mst newEmp = new Employee_mst(empCode);
            BeanCopier.copy(currentEmployee, newEmp);
            try {
                this.emMain.persist(newEmp);
                this.emMain.flush();
            } catch (PersistenceException pe) {
                this.log.debug("Fail when persist", pe);
                throw new TransactionRollbackException(pe);
            }

            // insert user login
            final User_login login = new User_login(newEmp);
            final String randomPass = this.generatePassword(8);
            final String encryptedPass = EncryptStringUtils.encrypt(randomPass);
            if (StringUtils.isNotEmpty(encryptedPass)) {
                // login.setUl_password(randomPass);
                login.setUl_passsalt(encryptedPass);
                login.setUl_locale(SysConfig.getDefaultLocale().getLanguage());
                this.emMain.persist(login);
                try {
                    this.emMain.flush();

                    // Send information to employee
                    this.sendMailEmployeeInfo(randomPass, login, newEmp.getEmp_mail());

                    message.add(InfoMessage.comp_001_save_successfully());
                    result = new ServiceResult<>(true, newEmp, message);
                } catch (final PersistenceException e) {
                    super.log.debug("EXCEPTION WHEN SAVE NEW EMPLOYEE , MAY BE SEND MAIL OR FLUSH DATA", e);
                    throw new TransactionRollbackException(e);
                } catch (MessagingException | IOException | TemplateException te) {
                    // ? roll back ?
                    super.log.debug(te);
                    message.add(WarnMessage.emp_009_cannot_send_mail());
                    result = new ServiceResult<>(false, newEmp, message);
                }
            }
            else {
                message.add(WarnMessage.emp_009_cannot_send_mail());
                result = new ServiceResult<>(false, newEmp, message);
            }
        }
        else if (currentEmployee != null) {
            Employee_mst employeeUpdate =
                    this.emMain.find(Employee_mst.class, new Employee_mst_MAPPED.Pk(currentEmployee.getEmp_code()));
            BeanCopier.copy(currentEmployee, employeeUpdate);
            message.add(InfoMessage.emp_003_save_successfully());
            result = new ServiceResult<>(true, employeeUpdate, message);
        }
        return result;
    }

    /**
     * Send mail to employee when add new employee.
     *
     * @param randomPass the random pass
     * @param userLogin the user login
     * @param employeeMail the employee mail
     * @throws MessagingException the messaging exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws TemplateException the template exception
     */

    public void sendMailEmployeeInfo(final String randomPass, final User_login userLogin, final String employeeMail)
            throws MessagingException, IOException, TemplateException {
        EmailHelper.sendNewAccountNotification(randomPass, userLogin, employeeMail);
    }

    /**
     * Generate password.
     *
     * @param len the len
     * @return the string
     */

    public String generatePassword(final int len) {
        final String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final Random rnd = new Random();
        final StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(str.charAt(rnd.nextInt(str.length())));
        }
        return sb.toString();
    }

    /**
     * Find employee by emp code.
     *
     * @param empCode the emp code
     * @return the employee_mst
     */
    public Employee_mst findEmployeeByEmpCode(final int empCode) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM employee_mst WHERE emp_code = :empCode");
        return (Employee_mst) this.emMain.createNativeQuery(query.toString(), Employee_mst.class)
                .setParameter("empCode", empCode).getSingleResult();
    }
}
