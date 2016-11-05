package arrow.businesstraceability.control.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.UploadedFile;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.config.SysConfig;
import arrow.businesstraceability.constant.AuthenticationConstants;
import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.control.bean.UserCredential;
import arrow.businesstraceability.control.helper.ServiceResult;
import arrow.businesstraceability.persistence.entity.Acc_com_credit;
import arrow.businesstraceability.persistence.entity.Acc_com_entity;
import arrow.businesstraceability.persistence.entity.Acc_com_finance;
import arrow.businesstraceability.persistence.entity.Acc_com_industry;
import arrow.businesstraceability.persistence.entity.Acc_com_relation;
import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.San_com_info;
import arrow.businesstraceability.persistence.entity.Tdb_detail_industry;
import arrow.businesstraceability.persistence.repository.Acc_com_creditRepository;
import arrow.businesstraceability.persistence.repository.Acc_com_entityRepository;
import arrow.businesstraceability.persistence.repository.Acc_com_financeRepository;
import arrow.businesstraceability.persistence.repository.Acc_com_industryRepository;
import arrow.businesstraceability.persistence.repository.Acc_com_relationRepository;
import arrow.businesstraceability.persistence.repository.Addresspoint_mstRepository;
import arrow.businesstraceability.persistence.repository.Employee_mstRepository;
import arrow.businesstraceability.persistence.repository.San_com_infoRepository;
import arrow.businesstraceability.persistence.repository.Tdb_detail_industryRepository;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.faces.util.BeanCopier;
import arrow.framework.util.collections.CollectionUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class InputAccountingDataService.
 */
public class InputAccountingDataService extends AbstractService {

    /** The san com info repo. */
    @Inject
    private San_com_infoRepository sanComInfoRepo;

    /** The acc com entity repo. */
    @Inject
    private Acc_com_entityRepository accComEntityRepo;

    /** The addresspoint mst repo. */
    @Inject
    private Addresspoint_mstRepository addresspointMstRepo;

    /** The employee mst repo. */
    @Inject
    private Employee_mstRepository employeeMstRepo;

    /** The tdb detail industry repo. */
    @Inject
    private Tdb_detail_industryRepository tdbDetailIndustryRepo;

    /** The acc com credit repo. */
    @Inject
    private Acc_com_creditRepository accComCreditRepo;

    /** The acc com industry repo. */
    @Inject
    private Acc_com_industryRepository accComIndustryRepo;

    /** The acc com finance repo. */
    @Inject
    private Acc_com_financeRepository accComFinanceRepo;

    /** The acc com relation repo. */
    @Inject
    private Acc_com_relationRepository accComRelationRepo;

    /**
     * Gets the san com info.
     *
     * @param idSanCom the id san com
     * @return the san com info
     */
    // TODO: remove method after merge with Process 2
    public San_com_info getSanComInfo(final int idintSanCom) {
        return this.sanComInfoRepo.getSanComInfoWithIdCompany(idintSanCom).getAnyResult();
    }

    /**
     * Validate acc com entity.
     *
     * @param accComEntity the acc com entity
     * @return the list
     */
    public List<Message> validateAccComEntity(final Acc_com_entity accComEntity) {
        List<Message> errors = new ArrayList<>();
        if (!accComEntity.isPersisted()) {
            long countEntitySameCustomerCode =
                this.accComEntityRepo.countEntityByCustomerCode(accComEntity.getIndx_customer()).getAnyResult();
            if (countEntitySameCustomerCode > 0) {
                errors.add(ErrorMessage.comp_005_customer_code_duplicated());
            }
        }

        return errors;
    }

    /**
     * Gets the list select item all branch.
     *
     * @return the list select item all branch
     */
    public List<Addresspoint_mst> getListSelectItemAllBranch() {
        return this.addresspointMstRepo.findAll();
    }

    /**
     * Gets the all employee in branch.
     *
     * @param adp_code the adp_code
     * @return the all employee in branch
     */
    public List<Employee_mst> getAllEmployeeInBranch(final String adp_code) {
        return this.employeeMstRepo.getAllActiveEmpByAdpCode(adp_code).getResultList();
    }

    /**
     * Find industry.
     *
     * @param code the code
     * @return the tdb_detail_industry
     */
    public Tdb_detail_industry findIndustry(final String code) {
        return this.tdbDetailIndustryRepo.findIndustryByCode(code).getAnyResult();
    }

    /**
     * Gets the all employee is manager hq.
     *
     * @return the all employee is manager hq
     */
    public List<Employee_mst> getAllEmployeeIsManagerHq() {
        return this.employeeMstRepo.getAllActiveEmployeeByPosititonAndAuthority(
            AuthenticationConstants.EmployeePosition.MANAGEMENT, UserCredential.HQ_OFFICER_AUTHORITY).getResultList();
    }

    /**
     * Save acc com entity.
     *
     * @param accComEntity the acc com entity
     * @return the service result
     */
    public ServiceResult<Acc_com_entity> createAccComEntity(final Acc_com_entity accComEntity) {
        Acc_com_entity currentEntity = new Acc_com_entity(this.genNextIdAccComEntity());
        BeanCopier.copy(accComEntity, currentEntity);
        Acc_com_entity newEntity = this.accComEntityRepo.saveAndFlushAndRefresh(currentEntity);
        return new ServiceResult<>(true, newEntity);
    }

    /**
     * Generate next id for acc_com_entity.
     *
     * @return the int
     */
    private int genNextIdAccComEntity() {
        Integer currentId = this.accComEntityRepo.getMaxIdOfEntity().getAnyResult();
        if (null == currentId) {
            return 1;
        }
        return currentId + 1;
    }

    /**
     * Save acc com credit with status.
     *
     * @param accComCredit the acc com credit
     * @param status the status
     * @return the service result
     */
    public ServiceResult<Acc_com_credit> createAccComCreditWithStatus(final Acc_com_credit accComCredit,
        final char status) {
        accComCredit.setStatus(status);
        Acc_com_credit currentCredit = new Acc_com_credit(this.genNextIdAccComCredit());
        BeanCopier.copy(accComCredit, currentCredit);
        Acc_com_credit newCredit = this.accComCreditRepo.saveAndFlushAndRefresh(currentCredit);
        return new ServiceResult<>(true, newCredit);
    }

    /**
     * Gen next id acc com credit.
     *
     * @return the int
     */
    private int genNextIdAccComCredit() {
        Integer currentId = this.accComCreditRepo.getMaxIdOfCredit().getAnyResult();
        if (null == currentId) {
            return 1;
        }
        return currentId + 1;
    }

    /**
     * Roll back acc com entity.
     *
     * @param accComEntity the acc com entity
     */
    public void rollBackAccComEntity(final Acc_com_entity accComEntity) {
        this.accComEntityRepo.removeAndFlush(accComEntity);
    }

    /**
     * Save acc com industry.
     *
     * @param accComIndustry the acc com industry
     * @return the service result
     */
    public ServiceResult<Acc_com_industry> createAccComIndustry(final Acc_com_industry accComIndustry) {
        Acc_com_industry currentIndustry = new Acc_com_industry(this.genNextIdAccComIndustry());
        BeanCopier.copy(accComIndustry, currentIndustry);
        Acc_com_industry newIndustry = this.accComIndustryRepo.saveAndFlushAndRefresh(currentIndustry);
        return new ServiceResult<>(true, newIndustry);
    }

    /**
     * Update acc com industry service result.
     *
     * @param accComIndustry the acc com industry
     * @return the service result
     */
    public ServiceResult<Acc_com_industry> updateAccComIndustry(final Acc_com_industry accComIndustry) {
        Acc_com_industry newIndustry = this.accComIndustryRepo.saveAndFlushAndRefresh(accComIndustry);
        return new ServiceResult<>(true, newIndustry);
    }

    /**
     * Gen next id acc com industry.
     *
     * @return the int
     */
    private int genNextIdAccComIndustry() {
        Integer currentId = this.accComIndustryRepo.getMaxIdOfIndustry().getAnyResult();
        if (null == currentId) {
            return 1;
        }
        return currentId + 1;
    }

    /**
     * Update acc com entity.
     *
     * @param accComEntity the acc com entity
     * @return the service result
     */
    public ServiceResult<Acc_com_entity> updateAccComEntity(final Acc_com_entity accComEntity) {
        Acc_com_entity newEntity = this.accComEntityRepo.saveAndFlushAndRefresh(accComEntity);
        return new ServiceResult<>(true, newEntity);
    }

    /**
     * Save report file.
     *
     * @param fileName the file name
     * @param uploadPath the upload path
     * @return the service result
     */
    public ServiceResult<Object> saveReportFile(final String fileName, final String uploadPath) {
        File targetPath = new File(uploadPath);
        if (!targetPath.exists()) {
            boolean resultMakeDir = targetPath.mkdirs();
            if (!resultMakeDir) {
                return new ServiceResult<>(false, ErrorMessage.proc3_009_upload_report_unsuccessfully());
            }
        }
        File targetFile = new File(targetPath, fileName);
        if (!targetFile.exists()) {
            try {
                boolean resultCreateFile = targetFile.createNewFile();
                if (!resultCreateFile) {
                    return new ServiceResult<>(false, ErrorMessage.proc3_009_upload_report_unsuccessfully());
                }
            } catch (IOException ieo) {
                ieo.printStackTrace();
            }
        }
        try {
            File sourceFile = new File(SysConfig.getUploadTempDir() + "/" + fileName);
            FileUtils.copyFile(sourceFile, targetFile);
            File sourcePath = new File(SysConfig.getUploadTempDir());
            FileDeleteStrategy.FORCE.delete(sourcePath);
            return new ServiceResult<>(true, InfoMessage.proc3_010_upload_report_successfully());
        } catch (final IOException e) {
            this.log.debug(e);
            return new ServiceResult<>(false, ErrorMessage.proc3_009_upload_report_unsuccessfully());
        }
    }

    /**
     * Update acc com credit.
     *
     * @param accComCredit the acc com credit
     * @return the service result
     */
    public ServiceResult<Acc_com_credit> updateAccComCredit(final Acc_com_credit accComCredit) {
        Acc_com_credit newCredit = this.accComCreditRepo.saveAndFlushAndRefresh(accComCredit);
        return new ServiceResult<>(true, newCredit);
    }

    /**
     * Save report file to temp path.
     *
     * @param file the file
     * @param uploadPath the upload path
     * @return the service result
     */
    public ServiceResult<Object> saveReportFileToTempPath(final UploadedFile file, final String uploadPath) {
        File targetPath = new File(uploadPath);
        if (!targetPath.exists()) {
            boolean result = targetPath.mkdirs();
            if (!result) {
                this.log.error("Upload dir is not available" + uploadPath);
                return new ServiceResult<>(false, ErrorMessage.proc3_009_upload_report_unsuccessfully());
            }
        }
        try (InputStream in = file.getInputstream();
            OutputStream out =
                new FileOutputStream(new File(targetPath, URLDecoder.decode(file.getFileName(),
                    Constants.DEFAULT_ENCODING)))) {
            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            return new ServiceResult<>(true, InfoMessage.proc3_010_upload_report_successfully());
        } catch (IOException ioe) {
            this.log.debug(ioe.getMessage(), ioe);
            return new ServiceResult<>(false, ErrorMessage.proc3_009_upload_report_unsuccessfully());
        }
    }

    /**
     * Gets acc com entity.
     *
     * @param idSanCom the id san com
     * @return the acc com entity
     */
    public Acc_com_entity getAccComEntity(final int idSanCom) {
        return this.accComEntityRepo.getComEntityByIdIntSanCom(idSanCom).getAnyResult();
    }

    /**
     * Gets credit history by entity id.
     *
     * @param id_com_entity the id com entity
     * @return the credit history by entity id
     */
    public List<Acc_com_credit> getCreditHistoryByEntityId(final int id_com_entity) {
        return this.accComCreditRepo.getAllCreditByIdComEntity(id_com_entity).getResultList();
    }

    /**
     * Gets acc com industry by entity and credit.
     *
     * @param id_com_entity the id com entity
     * @param id_credit the id credit
     * @return the acc com industry by entity and credit
     */
    public Acc_com_industry getAccComIndustryByEntityAndCredit(final int id_com_entity, final int id_credit) {
        return this.accComIndustryRepo.findAccComIndustyByEntityIdAndCreditId(id_com_entity, id_credit).getAnyResult();
    }

    /**
     * Gets entity view mode.
     *
     * @param id_com_entity the id com entity
     * @return the entity view mode
     */
    public boolean getEntityViewMode(final int id_com_entity) {
        long countCreditApproved =
            this.accComCreditRepo.countAllCreditWithStatus(id_com_entity, Constants.SAVED_CREDIT_STATUS);
        return countCreditApproved != 0;
    }

    /**
     * Validate acc com credit before save temporary.
     *
     * @param accComCredit the acc com credit
     * @return the list
     */
    public List<Message> validateAccComCreditBeforeSaveTemporary(final Acc_com_credit accComCredit) {
        List<Message> errors = new ArrayList<>();
        if (StringUtils.isEmpty(accComCredit.getCode_acc_surveyer())) {
            errors.add(ErrorMessage.proc3_015_information_source_is_required());
        }

        if (null == accComCredit.getDate_survey()) {
            errors.add(ErrorMessage.proc3_016_survey_date_is_required());
        }

        if (StringUtils.isEmpty(accComCredit.getAc_request_branch())) {
            errors.add(ErrorMessage.proc3_013_branch_of_request_user_is_required());
        }

        if (0 == accComCredit.getAc_request()) {
            errors.add(ErrorMessage.proc3_012_request_user_account_is_required());
        }

        if (StringUtils.isEmpty(accComCredit.getIndx_acc())) {
            errors.add(ErrorMessage.proc3_014_index_accounting_system_is_required());
        }

        return errors;
    }

    /**
     * Count acc com credit by survey date.
     *
     * @param id_com_entity the id_com_entity
     * @param id_credit the id_credit
     * @param ac_request the ac_request
     * @param code_acc_surveyer the code_acc_survey
     * @param date_survey the date_survey
     * @return the long
     */
    public boolean hasAccComCreditSameInfoSurvey(final int id_com_entity, final int id_credit, final int ac_request,
        final String code_acc_surveyer, final Date date_survey) {
        long counter =
            this.accComCreditRepo.countCreditSameInfoSurvey(id_com_entity, id_credit, ac_request, code_acc_surveyer,
                date_survey);
        return 0 != counter;
    }

    /**
     * Find survey date by id com entity and id credit.
     *
     * @param id_com_entity the id_com_entity
     * @param id_credit the id_credit
     * @return the date
     */
    public Date findSurveyDateByIdComEntityAndIdCredit(final int id_com_entity, final int id_credit) {
        return this.accComCreditRepo.findSurveyDateByIdEntityAndIdCredit(id_com_entity, id_credit).getAnyResult();
    }

    /**
     * Gets the latest id credit by id entity and status.
     *
     * @param id_com_entity the id_com_entity
     * @param status the status
     * @return the latest id credit by id entity and status
     */
    public int getLatestIdCreditByIdEntityAndStatus(final int id_com_entity, final char status) {
        return this.accComCreditRepo.findLatestIdCreditByIdEntityAndStatus(id_com_entity, status);
    }

    /**
     * Can create new credit.
     *
     * @param accComEntity the acc com entity
     * @param id_credit the id_credit
     * @param ac_request the ac_request
     * @return true, if successful
     */
    public boolean canSaveDraftCredit(final Acc_com_entity accComEntity, final int id_credit, final int ac_request) {
        if (accComEntity.isPersisted()) {
            return this.isDraftCreditNotDuplicateInfo(accComEntity.getId_com_entity(), id_credit, ac_request);
        } else {
            return true;
        }
    }

    /**
     * Checks if is draft credit not duplicate info.
     *
     * @param id_com_entity the id_com_entity
     * @param id_credit the id_credit
     * @param ac_request the ac_request
     * @return true, if is draft credit not duplicate info
     */
    private boolean isDraftCreditNotDuplicateInfo(final int id_com_entity, final int id_credit, final int ac_request) {
        long counter =
            this.accComCreditRepo.countAllOtherCreditWithAcRequestAndStatus(id_com_entity, id_credit, ac_request,
                Constants.DRAFT_CREDIT_STATUS);
        return 0 == counter;
    }

    /**
     * Checks for not credit with draft status.
     *
     * @param idComEntity the id com entity
     * @param ac_request the ac_request
     * @return true, if successful
     */
    public boolean hasNotCreditWithDraftStatus(final int idComEntity, final int ac_request) {
        long countDraftCredit =
            this.accComCreditRepo.countAllCreditWithAcRequestAndStatus(idComEntity, ac_request,
                Constants.DRAFT_CREDIT_STATUS);
        return 0 == countDraftCredit;
    }

    /**
     * Find draft credit of user.
     *
     * @param id_com_entity the id_com_entity
     * @param ac_request the ac_request
     * @return the acc_com_credit
     */
    public Acc_com_credit findDraftCreditOfUser(final int id_com_entity, final int ac_request) {
        return this.accComCreditRepo.findDraftCreditIdEntityAndByAcRequest(id_com_entity, ac_request).getAnyResult();
    }

    /**
     * Discard credit.
     *
     * @param accComCredit the acc com credit
     * @return the service result
     */
    public ServiceResult<Acc_com_credit> discardCredit(final Acc_com_credit accComCredit) {
        int idCredit = accComCredit.getId_credit();

        // Remove industry
        Acc_com_industry industry = this.accComIndustryRepo.findIndustryByIdCredit(idCredit).getAnyResult();
        if (null != industry) {
            this.accComIndustryRepo.removeAndFlush(industry);
        }

        // Remove finance
        List<Acc_com_finance> listFinances = this.accComFinanceRepo.getAllFinanceByIdCredit(idCredit).getResultList();
        if (CollectionUtils.isNotEmpty(listFinances)) {
            for (Acc_com_finance finance : listFinances) {
                this.accComFinanceRepo.removeAndFlush(finance);
            }
        }

        // Remove relation
        List<Acc_com_relation> listRelations =
            this.accComRelationRepo.getAllRelationByIdCredit(idCredit).getResultList();
        if (CollectionUtils.isNotEmpty(listRelations)) {
            for (Acc_com_relation relation : listRelations) {
                this.accComRelationRepo.removeAndFlush(relation);
            }
        }

        // Remove credit
        Acc_com_credit credit = this.accComCreditRepo.findByIdCredit(accComCredit.getId_credit());
        this.accComCreditRepo.removeAndFlush(credit);
        return new ServiceResult<>(true, InfoMessage.proc3_020_discard_successful());
    }

}
