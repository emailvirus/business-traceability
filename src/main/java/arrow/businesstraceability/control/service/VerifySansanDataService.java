package arrow.businesstraceability.control.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.omnifaces.util.Faces;
import org.primefaces.model.UploadedFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.constant.ElasticSearchConstants;
import arrow.businesstraceability.constant.SansanConstants;
import arrow.businesstraceability.persistence.entity.San_card_data;
import arrow.businesstraceability.persistence.entity.San_card_tag;
import arrow.businesstraceability.persistence.entity.San_com_branch;
import arrow.businesstraceability.persistence.entity.San_com_cnumber;
import arrow.businesstraceability.persistence.entity.San_com_info;
import arrow.businesstraceability.persistence.entity.San_com_live_client;
import arrow.businesstraceability.persistence.entity.San_com_live_owner;
import arrow.businesstraceability.persistence.entity.San_com_live_stat;
import arrow.businesstraceability.persistence.entity.San_com_mdomain;
import arrow.businesstraceability.persistence.entity.San_com_site;
import arrow.businesstraceability.persistence.entity.San_com_udomain;
import arrow.businesstraceability.persistence.entity.San_com_whole_card;
import arrow.businesstraceability.persistence.entity.San_idmap_card;
import arrow.businesstraceability.persistence.entity.San_idmap_company;
import arrow.businesstraceability.persistence.entity.San_idmap_person;
import arrow.businesstraceability.persistence.repository.Batch_job_executionRepository;
import arrow.businesstraceability.persistence.repository.Batch_job_execution_paramsRepository;
import arrow.businesstraceability.persistence.repository.San_card_dataRepository;
import arrow.businesstraceability.persistence.repository.San_card_tagRepository;
import arrow.businesstraceability.persistence.repository.San_com_branchRepository;
import arrow.businesstraceability.persistence.repository.San_com_cnumberRepository;
import arrow.businesstraceability.persistence.repository.San_com_infoRepository;
import arrow.businesstraceability.persistence.repository.San_com_live_clientRepository;
import arrow.businesstraceability.persistence.repository.San_com_live_ownerRepository;
import arrow.businesstraceability.persistence.repository.San_com_live_statRepository;
import arrow.businesstraceability.persistence.repository.San_com_mdomainRepository;
import arrow.businesstraceability.persistence.repository.San_com_siteRepository;
import arrow.businesstraceability.persistence.repository.San_com_udomainRepository;
import arrow.businesstraceability.persistence.repository.San_com_whole_cardRepository;
import arrow.businesstraceability.persistence.repository.San_idmap_cardRepository;
import arrow.businesstraceability.persistence.repository.San_idmap_companyRepository;
import arrow.businesstraceability.persistence.repository.San_idmap_personRepository;
import arrow.businesstraceability.stream.reader.StreamGobbler;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.CompareUtils;
import arrow.framework.util.DateUtils;
import arrow.framework.util.collections.CollectionUtils;

/**
 * The type Upload csv service.
 */
public class VerifySansanDataService extends AbstractService {

    /** The san com info repo. */
    @Inject
    private San_com_infoRepository sanComInfoRepo;

    /** The elastic service. */
    @Inject
    private ElasticSearchService elasticService;

    /** The san card data repo. */
    @Inject
    private San_card_dataRepository sanCardDataRepo;

    /** The id map card repo. */
    @Inject
    private San_idmap_cardRepository idMapCardRepo;

    /** The id map company repo. */
    @Inject
    private San_idmap_companyRepository idMapCompanyRepo;

    /** The id map person repo. */
    @Inject
    private San_idmap_personRepository idMapPersonRepo;

    /** The san com branch repo. */
    @Inject
    private San_com_branchRepository sanComBranchRepo;

    /** The san com m domain repo. */
    @Inject
    private San_com_mdomainRepository sanComMDomainRepo;

    /** The san com u domain repo. */
    @Inject
    private San_com_udomainRepository sanComUDomainRepo;

    /** The san com whole card repo. */
    @Inject
    private San_com_whole_cardRepository sanComWholeCardRepo;

    /** The san com live owner repo. */
    @Inject
    private San_com_live_ownerRepository sanComLiveOwnerRepo;

    /** The san com live stat repo. */
    @Inject
    private San_com_live_statRepository sanComLiveStatRepo;

    /** The san com live client repo. */
    @Inject
    private San_com_live_clientRepository sanComLiveClientRepo;

    /** The san com site repo. */
    @Inject
    private San_com_siteRepository sanComSiteRepo;

    /** The san com c number repo. */
    @Inject
    private San_com_cnumberRepository sanComCNumberRepo;

    /** The san card tag repo. */
    @Inject
    private San_card_tagRepository sanCardTagRepo;

    @Inject
    private Batch_job_executionRepository batch_job_executionRepository;

    @Inject
    private Batch_job_execution_paramsRepository batch_job_execution_paramsRepository;

    /** The error message. */
    private List<String> errorMessage;

    /** The Constant SAN_COM_INFO. */
    private static final String SAN_COM_INFO = "san_com_info";

    /** The Constant SAN_COM_BRANCH. */
    private static final String SAN_COM_BRANCH = "san_com_branch";

    /** The Constant SAN_COM_MDOMAIN. */
    private static final String SAN_COM_MDOMAIN = "san_com_mdomain";

    /** The Constant SAN_COM_UDOMAIN. */
    private static final String SAN_COM_UDOMAIN = "san_com_udomain";

    /** The Constant SAN_COM_WHOLE_CARD. */
    private static final String SAN_COM_WHOLE_CARD = "san_com_whole_card";

    /** The Constant SAN_COM_LIVE_STAT. */
    private static final String SAN_COM_LIVE_STAT = "san_com_live_stat";

    /** The Constant SAN_COM_LIVE_OWNER. */
    private static final String SAN_COM_LIVE_OWNER = "san_com_live_owner";

    /** The Constant SAN_COM_LIVE_CLIENT. */
    private static final String SAN_COM_LIVE_CLIENT = "san_com_live_client";

    /** The Constant SAN_COM_SITE. */
    private static final String SAN_COM_SITE = "san_com_site";

    /** The Constant SAN_COM_CNUMBER. */
    private static final String SAN_COM_CNUMBER = "san_com_cnumber";

    /** The Constant SAN_IDMAP_CARD. */
    private static final String SAN_IDMAP_CARD = "san_idmap_card";

    /** The Constant SAN_IDMAP_COMPANY. */
    private static final String SAN_IDMAP_COMPANY = "san_idmap_company";

    /** The Constant SAN_IDMAP_PERSON. */
    private static final String SAN_IDMAP_PERSON = "san_idmap_person";

    /** The Constant SAN_CARD_DATA. */
    private static final String SAN_CARD_DATA = "san_card_data";

    /** The Constant SAN_CARD_TAG. */
    private static final String SAN_CARD_TAG = "san_card_tag";

    /** The Constant SLASH_CHARACTER. */
    private static final String SLASH_CHARACTER = "/";

    /** The Constant CSV_EXTENSION. */
    private static final String CSV_EXTENSION = ".csv";

    /** The Constant RUN_FILE_PATH. */
    private static String RUN_FILE_PATH = "/opt/sars_batch/sars-batch.jar";

    /** The Constant FOLDER_BACKUP. */
    private static String FOLDER_BACKUP = "/opt/sars_batch/backup/";

    /** The Constant RUN_FILE_FOLDER_PATH. */
    private static String RUN_FILE_FOLDER_PATH = "/opt/sars_batch";

    /** The Constant LOG_FILE. */
    private static final String LOG_FILE = "import.log";

    /**
     * Upload csv file.
     *
     * @param file the file
     * @return the string
     */
    public String uploadCsvFile(final UploadedFile file) {
        try {

            String uploadPath = Faces.getInitParameter(Constants.SANSAN_VERIFY_CSV_DIR_PARAM_NAME);
            File targetPath = new File(uploadPath);
            if (!targetPath.exists()) {
                boolean result = targetPath.mkdirs();
                if (!result) {
                    this.log.error("Upload dir is not available" + uploadPath);
                    ErrorMessage.csv_002_upload_unsuccessfully().show();
                    return StringUtils.EMPTY;
                }
            }

            String fileName = this.uploadFile(targetPath, file, null);
            InfoMessage.csv_001_upload_successfully().show();
            return fileName;
        } catch (final IOException e) {
            ErrorMessage.csv_002_upload_unsuccessfully().show();
            return StringUtils.EMPTY;
        }
    }

    /**
     * Import data.
     *
     * @param inputFile the input file
     * @return true, if successful
     */
    public boolean importData(final String inputFile) {
        String importFile = this.getInputFilePath(inputFile);
        try {
            boolean isExistedIndex = this.elasticService.checkIndexExisted("san_com_info");

            if (isExistedIndex) {
                Map<String, San_com_info> map =
                    this.elasticService.getMapfindAllDataInIndex("san_com_info", San_com_info.class, "id_company");
                if (map.isEmpty()) {
                    Runtime run = Runtime.getRuntime();
                    Process proc =
                        run.exec("java -jar " + VerifySansanDataService.RUN_FILE_PATH
                            + " --fileSync --firstTime --filePath=" + importFile + " --folderBackup="
                            + VerifySansanDataService.FOLDER_BACKUP, null, new File(
                            VerifySansanDataService.RUN_FILE_FOLDER_PATH));
                    StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream());
                    outputGobbler.start();
                    proc.waitFor();
                } else {
                    Runtime run = Runtime.getRuntime();
                    Process proc =
                        run.exec("java -jar " + VerifySansanDataService.RUN_FILE_PATH + " --fileSync --filePath="
                            + importFile + " --folderBackup=" + VerifySansanDataService.FOLDER_BACKUP, null, new File(
                            VerifySansanDataService.RUN_FILE_FOLDER_PATH));
                    StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream());
                    outputGobbler.start();
                    proc.waitFor();
                }
            } else {
                Runtime run = Runtime.getRuntime();
                Process proc =
                    run.exec("java -jar " + VerifySansanDataService.RUN_FILE_PATH
                        + " --fileSync --firstTime --filePath=" + importFile + " --folderBackup="
                        + VerifySansanDataService.FOLDER_BACKUP, null, new File(
                        VerifySansanDataService.RUN_FILE_FOLDER_PATH));
                StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream());
                outputGobbler.start();
                proc.waitFor();
            }
            return true;
        } catch (IOException io) {
            this.log.debug(io.getMessage());
            return false;
        } catch (InterruptedException ie) {
            this.log.debug(ie.getMessage());
            return false;
        }
    }

    /**
     * Call api.
     *
     * @param fromTimeReg the from time reg
     * @param toTimeReg the to time reg
     * @param dateReg the date reg
     * @return true, if successful
     */
    public boolean callApi(final Date fromTimeReg, final Date toTimeReg, final Date dateReg) {
        String registeredFrom = DateUtils.formatTime(fromTimeReg);
        String registeredTo = DateUtils.formatTime(toTimeReg);
        String registedDate = DateUtils.formatDateTime(dateReg);
        Runtime run = Runtime.getRuntime();
        Process proc;
        try {
            // proc =
            // run.exec("java -jar " + VerifySansanDataService.RUN_FILE_PATH + " --apiSync --folderBackup="
            // + VerifySansanDataService.FOLDER_BACKUP + " --registeredFrom= " + registeredFrom
            // + " --registeredTo=" + registeredTo + " --registeredDate=" + registedDate + " --testFileName=abc",
            // null, new File(VerifySansanDataService.RUN_FILE_FOLDER_PATH));
            proc =
                run.exec("java -jar " + VerifySansanDataService.RUN_FILE_PATH + " --apiSync --folderBackup="
                    + VerifySansanDataService.FOLDER_BACKUP + " --registeredFrom= " + registeredFrom
                    + " --registeredTo=" + registeredTo + " --registeredDateFrom=" + registedDate
                    + " --registeredDateTo=" + registedDate, null, new File(
                    VerifySansanDataService.RUN_FILE_FOLDER_PATH));
            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream());
            outputGobbler.start();
            proc.waitFor();
            return true;
        } catch (IOException io) {
            this.log.debug(io.getMessage());
            return false;
        } catch (InterruptedException ie) {
            this.log.debug(ie.getMessage());
            return false;
        }

    }

    /**
     * Gets the input file path.
     *
     * @param inputFileName the input file name
     * @return the input file path
     */
    private String getInputFilePath(final String inputFileName) {
        String uploadPath = Faces.getInitParameter(Constants.SANSAN_VERIFY_CSV_DIR_PARAM_NAME);
        if (!uploadPath.endsWith(VerifySansanDataService.SLASH_CHARACTER)) {
            return uploadPath + VerifySansanDataService.SLASH_CHARACTER + inputFileName;
        } else {
            return uploadPath + inputFileName;
        }
    }

    /**
     * Read file csv an convert to inputstream.
     *
     * @return the input stream
     * @throws FileNotFoundException the file not found exception
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public InputStream getInputStream() throws FileNotFoundException, UnsupportedEncodingException {
        return new FileInputStream(new File(VerifySansanDataService.RUN_FILE_FOLDER_PATH, URLDecoder.decode(
            VerifySansanDataService.LOG_FILE, Constants.DEFAULT_ENCODING)));
    }

    /**
     * Verify data.
     *
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public List<String> verifyData() throws IOException {
        this.errorMessage = new ArrayList<String>();
        this.compareSanCardData();
        this.compareSanCardTags();
        this.compareSanComInfo();
        this.compareSanComBranch();
        this.compareSanComSite();
        this.compareSanComCnumber();
        this.compareSanComMdomain();
        this.compareSanComUdomain();
        this.compareSanComWholeCard();
        this.compareSanComLiveClient();
        this.compareSanComLiveOwner();
        this.compareSanComLiveStat();
        this.compareSanIdMapCard();
        this.compareSanIdMapCompany();
        this.compareSanIdMapPerson();
        return this.errorMessage;

    }

    /**
     * Compare san card tags.
     *
     * @throws JsonProcessingException the json processing exception
     */
    private void compareSanCardTags() throws JsonProcessingException {
        List<San_card_tag> list = this.sanCardTagRepo.findAll();

        List<String> listIdMapCard = new ArrayList<String>();
        for (San_card_tag entity : list) {
            String idMap = this.idMapCardRepo.findIdSanCardByIdCard(entity.getId_card());
            listIdMapCard.add(idMap);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_card");
        listFieldCompare.add("tag");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("san_com_info");
        listExcludeField.add("san_card_data");

        this.errorMessage.addAll(CompareUtils.compareSanDataAndCsv(list, listIdMapCard, San_card_tag.class,
            San_idmap_card.class, this.getCsvFilePath(VerifySansanDataService.SAN_IDMAP_CARD),
            this.getCsvFilePath(VerifySansanDataService.SAN_CARD_TAG), "id_san_card", listFieldCompare,
            listExcludeField));
    }


    /**
     * Compare san com info.
     *
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void compareSanComInfo() throws IOException {
        String uploadPath = Faces.getInitParameter(Constants.SANSAN_VERIFY_CSV_DIR_PARAM_NAME);
        String expectedFile = "";
        if (!uploadPath.endsWith(VerifySansanDataService.SLASH_CHARACTER)) {
            expectedFile =
                uploadPath + VerifySansanDataService.SLASH_CHARACTER + VerifySansanDataService.SAN_COM_INFO
                    + VerifySansanDataService.CSV_EXTENSION;
        } else {
            expectedFile = uploadPath + VerifySansanDataService.SAN_COM_INFO + VerifySansanDataService.CSV_EXTENSION;
        }
        List<San_com_info> list = this.sanComInfoRepo.findAll();
        final List<String> listExcludeField = this.buildSanComInfoExcludedFields();
        this.errorMessage.addAll(CompareUtils.compareListDataAndCsv(list, expectedFile, "id_san_company",
            listExcludeField));
    }

    /**
     * Compare san com site.
     *
     * @throws JsonProcessingException the json processing exception
     */
    private void compareSanComSite() throws JsonProcessingException {
        List<San_com_site> list = this.sanComSiteRepo.findAll();

        List<String> listIdSanComp = new ArrayList<String>();
        for (San_com_site entity : list) {
            String idMap = this.idMapCompanyRepo.findIdSanCompanyByIdCompany(entity.getId_company());
            listIdSanComp.add(idMap);
        }

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("san_com_info");
        listExcludeField.add("san_card_data");
        listExcludeField.add("san_com_branch");
        listExcludeField.add("id_site");
        listExcludeField.add("id_branch");

        this.errorMessage.addAll(CompareUtils.compareSanComSite(list, listIdSanComp,
            this.getCsvFilePath(VerifySansanDataService.SAN_IDMAP_COMPANY),
            this.getCsvFilePath(VerifySansanDataService.SAN_COM_BRANCH),
            this.getCsvFilePath(VerifySansanDataService.SAN_COM_SITE), listExcludeField));
    }

    /**
     * Compare san com cnumber.
     *
     * @throws JsonProcessingException the json processing exception
     */
    private void compareSanComCnumber() throws JsonProcessingException {
        List<San_com_cnumber> list = this.sanComCNumberRepo.findAll();

        List<String> listIdSanComp = new ArrayList<String>();
        for (San_com_cnumber entity : list) {
            String idMap = this.idMapCompanyRepo.findIdSanCompanyByIdCompany(entity.getId_company());
            listIdSanComp.add(idMap);
        }

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("san_com_info");
        listExcludeField.add("san_card_data");
        listExcludeField.add("san_com_branch");
        listExcludeField.add("san_com_site");
        listExcludeField.add("id_site");
        listExcludeField.add("id_cnumber");
        listExcludeField.add("id_branch");

        this.errorMessage.addAll(CompareUtils.compareSanComCNumber(list, listIdSanComp,
            this.getCsvFilePath(VerifySansanDataService.SAN_IDMAP_COMPANY),
            this.getCsvFilePath(VerifySansanDataService.SAN_COM_BRANCH),
            this.getCsvFilePath(VerifySansanDataService.SAN_COM_SITE),
            this.getCsvFilePath(VerifySansanDataService.SAN_COM_CNUMBER), listExcludeField));
    }

    /**
     * Gets the csv file path.
     *
     * @param csvFileName the csv file name
     * @return the csv file path
     */
    private String getCsvFilePath(final String csvFileName) {
        String uploadPath = Faces.getInitParameter(Constants.SANSAN_VERIFY_CSV_DIR_PARAM_NAME);
        String filePath = "";
        if (!uploadPath.endsWith(VerifySansanDataService.SLASH_CHARACTER)) {
            filePath =
                uploadPath + VerifySansanDataService.SLASH_CHARACTER + csvFileName
                    + VerifySansanDataService.CSV_EXTENSION;
        } else {
            filePath = uploadPath + csvFileName + VerifySansanDataService.CSV_EXTENSION;
        }
        return filePath;
    }

    /**
     * Compare san com mdomain.
     *
     * @throws JsonProcessingException the json processing exception
     */
    private void compareSanComMdomain() throws JsonProcessingException {
        List<San_com_mdomain> list = this.sanComMDomainRepo.findAll();

        List<String> listIdMapComp = new ArrayList<String>();
        for (San_com_mdomain domain : list) {
            String idMap = this.idMapCompanyRepo.findIdSanCompanyByIdCompany(domain.getId_company());
            listIdMapComp.add(idMap);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_company");
        listFieldCompare.add("email_domain");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("san_com_info");
        listExcludeField.add("id_maildomain");

        this.errorMessage.addAll(CompareUtils.compareSanDataAndCsv(list, listIdMapComp, San_com_mdomain.class,
            San_idmap_company.class, this.getCsvFilePath(VerifySansanDataService.SAN_IDMAP_COMPANY),
            this.getCsvFilePath(VerifySansanDataService.SAN_COM_MDOMAIN), "id_san_company", listFieldCompare,
            listExcludeField));
    }

    /**
     * Compare san com udomain.
     *
     * @throws JsonProcessingException the json processing exception
     */
    private void compareSanComUdomain() throws JsonProcessingException {
        List<San_com_udomain> list = this.sanComUDomainRepo.findAll();

        List<String> listIdMapComp = new ArrayList<String>();
        for (San_com_udomain entity : list) {
            String idMap = this.idMapCompanyRepo.findIdSanCompanyByIdCompany(entity.getId_company());
            listIdMapComp.add(idMap);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_company");
        listFieldCompare.add("url_domain");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("san_com_info");
        listExcludeField.add("id_urldomain");

        this.errorMessage.addAll(CompareUtils.compareSanDataAndCsv(list, listIdMapComp, San_com_udomain.class,
            San_idmap_company.class, this.getCsvFilePath(VerifySansanDataService.SAN_IDMAP_COMPANY),
            this.getCsvFilePath(VerifySansanDataService.SAN_COM_UDOMAIN), "id_san_company", listFieldCompare,
            listExcludeField));

    }

    /**
     * Compare san com whole card.
     *
     * @throws JsonProcessingException the json processing exception
     */
    private void compareSanComWholeCard() throws JsonProcessingException {
        List<San_com_whole_card> list = this.sanComWholeCardRepo.findAll();

        List<String> listIdMapCard = new ArrayList<String>();
        for (San_com_whole_card entity : list) {
            String idMap = this.idMapCardRepo.findIdSanCardByIdCard(entity.getId_card());
            listIdMapCard.add(idMap);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_card");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_exchange");
        listExcludeField.add("date_register");
        listExcludeField.add("date_card_deletion");
        listExcludeField.add("san_com_info");
        listExcludeField.add("san_card_data");

        this.errorMessage.addAll(CompareUtils.compareSanDataAndCsv(list, listIdMapCard, San_com_whole_card.class,
            San_idmap_card.class, this.getCsvFilePath(VerifySansanDataService.SAN_IDMAP_CARD),
            this.getCsvFilePath(VerifySansanDataService.SAN_COM_WHOLE_CARD), "id_san_card", listFieldCompare,
            listExcludeField));

    }

    /**
     * Compare san com live client.
     *
     * @throws JsonProcessingException the json processing exception
     */
    private void compareSanComLiveClient() throws JsonProcessingException {
        List<San_com_live_client> list = this.sanComLiveClientRepo.findAll();

        List<String> listIdSanCompany = new ArrayList<String>();
        List<String> listIdSanPerson = new ArrayList<String>();
        for (San_com_live_client entity : list) {
            String idMapCompany = this.idMapCompanyRepo.findIdSanCompanyByIdCompany(entity.getId_company());
            listIdSanCompany.add(idMapCompany);

            String idMapPerson = this.idMapPersonRepo.findIdSanPersonByIdPerson(entity.getId_person());
            listIdSanPerson.add(idMapPerson);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_company");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("san_com_info");
        listExcludeField.add("san_card_data");
        listExcludeField.add("id_card");

        this.errorMessage.addAll(CompareUtils.compareSanComLiveClient(list, listIdSanCompany, listIdSanPerson,
            this.getCsvFilePath(VerifySansanDataService.SAN_COM_LIVE_CLIENT),
            this.getCsvFilePath(VerifySansanDataService.SAN_IDMAP_COMPANY),
            this.getCsvFilePath(VerifySansanDataService.SAN_IDMAP_PERSON), listExcludeField));
    }

    /**
     * Compare san com live owner.
     *
     * @throws JsonProcessingException the json processing exception
     */
    private void compareSanComLiveOwner() throws JsonProcessingException {
        List<San_com_live_owner> list = this.sanComLiveOwnerRepo.findAll();

        List<String> listIdMapComp = new ArrayList<String>();
        for (San_com_live_owner entity : list) {
            String idMap = this.idMapCompanyRepo.findIdSanCompanyByIdCompany(entity.getId_company());
            listIdMapComp.add(idMap);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_company");
        listFieldCompare.add("ac_user");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("san_com_info");
        listExcludeField.add("san_card_data");
        listExcludeField.add("id_card");

        this.errorMessage.addAll(CompareUtils.compareSanDataAndCsv(list, listIdMapComp, San_com_live_owner.class,
            San_idmap_company.class, this.getCsvFilePath(VerifySansanDataService.SAN_IDMAP_COMPANY),
            this.getCsvFilePath(VerifySansanDataService.SAN_COM_LIVE_OWNER), "id_san_company", listFieldCompare,
            listExcludeField));
    }

    /**
     * Compare san com live stat.
     *
     * @throws JsonProcessingException the json processing exception
     */
    private void compareSanComLiveStat() throws JsonProcessingException {
        List<San_com_live_stat> list = this.sanComLiveStatRepo.findAll();

        List<String> listIdMapComp = new ArrayList<String>();
        for (San_com_live_stat entity : list) {
            String idMap = this.idMapCompanyRepo.findIdSanCompanyByIdCompany(entity.getId_company());
            listIdMapComp.add(idMap);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_company");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("date_oldest_exchange");
        listExcludeField.add("san_com_info");
        listExcludeField.add("latestCard");
        listExcludeField.add("maxVPCard");
        listExcludeField.add("maxCPCard");

        listExcludeField.add("id_latest_card");
        listExcludeField.add("id_max_cp_card");
        listExcludeField.add("id_max_vp_card");

        this.errorMessage.addAll(CompareUtils.compareSanDataAndCsv(list, listIdMapComp, San_com_live_stat.class,
            San_idmap_company.class, this.getCsvFilePath(VerifySansanDataService.SAN_IDMAP_COMPANY),
            this.getCsvFilePath(VerifySansanDataService.SAN_COM_LIVE_STAT), "id_san_company", listFieldCompare,
            listExcludeField));
    }

    /**
     * Upload file.
     *
     * @param targetPath the target path
     * @param file the file
     * @param fileName the file name
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private String uploadFile(final File targetPath, final UploadedFile file, String fileName) throws IOException {
        if (StringUtils.isEmpty(fileName)) {
            fileName = file.getFileName();
        }
        try (OutputStream out =
            new FileOutputStream(new File(targetPath, URLDecoder.decode(fileName, Constants.DEFAULT_ENCODING)));
            InputStream in = file.getInputstream()) {
            int read = 0;
            final byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        }
        return fileName;
    }

    /**
     * Compare san card data.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void compareSanCardData() throws IOException {
        String uploadPath = Faces.getInitParameter(Constants.SANSAN_VERIFY_CSV_DIR_PARAM_NAME);
        String expectedFile = "";
        if (!uploadPath.endsWith(VerifySansanDataService.SLASH_CHARACTER)) {
            expectedFile =
                uploadPath + VerifySansanDataService.SLASH_CHARACTER + VerifySansanDataService.SAN_CARD_DATA
                    + VerifySansanDataService.CSV_EXTENSION;
        } else {
            expectedFile = uploadPath + VerifySansanDataService.SAN_CARD_DATA + VerifySansanDataService.CSV_EXTENSION;
        }
        List<San_card_data> list = this.sanCardDataRepo.findAll();
        final List<String> listExcludeField = this.buildSanCardDataExcludedFields();
        this.errorMessage.addAll(CompareUtils
            .compareListDataAndCsv(list, expectedFile, "id_san_card", listExcludeField));
    }

    /**
     * Builds the san card data excluded fields.
     *
     * @return the list
     */
    private List<String> buildSanCardDataExcludedFields() {
        return Arrays.asList("pk", SansanConstants.FIELD_EMAIL_NAME, SansanConstants.FIELD_EMAIL_DOMAIN,
            SansanConstants.FIELD_URL_DOMAIN, SansanConstants.FIELD_EMAIL_NAME2, SansanConstants.FIELD_EMAIL_DOMAIN2,
            SansanConstants.FIELD_URL_DOMAIN2, SansanConstants.FIELD_ENUM_INFO_VALIDATION,
            SansanConstants.FIELD_ENUM_IMPORT_PROC, SansanConstants.FIELD_TS_CREATE,
            SansanConstants.FIELD_TS_COM_CREATION, SansanConstants.FIELD_CN_FILEIMPORT,
            SansanConstants.FIELD_CN_APIIMPORT, "date_register", ElasticSearchConstants.San_card_data.TS_LAST_UPDATED,
            "isUpdated", "isPersisted", "date_exchange", SansanConstants.FIELD_CN_COMID_UPDATE,
            SansanConstants.FIELD_CN_CARDINFO_UPDATE, SansanConstants.FIELD_TAGS, SansanConstants.FIELD_ID_CARD,
            SansanConstants.FIELD_ID_COMPANY, SansanConstants.FIELD_ID_PERSON, "san_com_info", "selected",
            "object_version", "version", "last_updated_at");
    }

    /**
     * Compare san com branch.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void compareSanComBranch() throws JsonProcessingException {
        List<San_com_branch> list = this.sanComBranchRepo.findAll();

        List<String> listIdMapComp = new ArrayList<String>();
        for (San_com_branch branch : list) {
            String idMap = this.idMapCompanyRepo.findIdSanCompanyByIdCompany(branch.getId_company());
            listIdMapComp.add(idMap);
        }

        List<String> listFieldCompare = new ArrayList<String>();
        listFieldCompare.add("id_company");
        listFieldCompare.add("name_branch");

        List<String> listExcludeField = new ArrayList<String>();
        listExcludeField.addAll(SansanConstants.getListDefaultExcludeFields());
        listExcludeField.add("date_latest_exchange");
        listExcludeField.add("san_com_info");
        listExcludeField.add("san_card_data");
        listExcludeField.add("id_branch");

        this.errorMessage.addAll(CompareUtils.compareSanDataAndCsv(list, listIdMapComp, San_com_branch.class,
            San_idmap_company.class, this.getCsvFilePath(VerifySansanDataService.SAN_IDMAP_COMPANY),
            this.getCsvFilePath(VerifySansanDataService.SAN_COM_BRANCH), "id_san_company", listFieldCompare,
            listExcludeField));
    }

    /**
     * Builds the san_com_info excluded fields.
     *
     * @return the list
     */
    private List<String> buildSanComInfoExcludedFields() {
        return Arrays.asList("pk", "id_company", "id_sarscom", "company_mst", "ts_create", "ts_export", "ts_update",
            "last_updated_at", "object_version", "isPersisted", "isPersisted", "object_version", "version",
            "last_updated_at");
    }

    /**
     * Compare san id map card.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void compareSanIdMapCard() throws IOException {
        String uploadPath = Faces.getInitParameter(Constants.SANSAN_VERIFY_CSV_DIR_PARAM_NAME);
        String expectedFile = "";
        if (!uploadPath.endsWith(VerifySansanDataService.SLASH_CHARACTER)) {
            expectedFile =
                uploadPath + VerifySansanDataService.SLASH_CHARACTER + VerifySansanDataService.SAN_IDMAP_CARD
                    + VerifySansanDataService.CSV_EXTENSION;
        } else {
            expectedFile = uploadPath + VerifySansanDataService.SAN_IDMAP_CARD + VerifySansanDataService.CSV_EXTENSION;
        }
        List<San_idmap_card> list = this.idMapCardRepo.findAll();
        final List<String> listExcludeField = this.buildSanIdMapCardExcludedFields();
        this.errorMessage.addAll(CompareUtils
            .compareListDataAndCsv(list, expectedFile, "id_san_card", listExcludeField));

    }

    /**
     * Builds the san id map card excluded fields.
     *
     * @return the list
     */
    private List<String> buildSanIdMapCardExcludedFields() {
        return Arrays
            .asList("pk", "id_card", "isPersisted", "selected", "object_version", "version", "last_updated_at");
    }

    /**
     * Compare san id map company.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void compareSanIdMapCompany() throws IOException {
        String uploadPath = Faces.getInitParameter(Constants.SANSAN_VERIFY_CSV_DIR_PARAM_NAME);
        String expectedFile = "";
        if (!uploadPath.endsWith(VerifySansanDataService.SLASH_CHARACTER)) {
            expectedFile =
                uploadPath + VerifySansanDataService.SLASH_CHARACTER + VerifySansanDataService.SAN_IDMAP_COMPANY
                    + VerifySansanDataService.CSV_EXTENSION;
        } else {
            expectedFile =
                uploadPath + VerifySansanDataService.SAN_IDMAP_COMPANY + VerifySansanDataService.CSV_EXTENSION;
        }
        List<San_idmap_company> list = this.idMapCompanyRepo.findAll();
        final List<String> listExcludeField = this.buildSanIdMapCompanyExcludedFields();
        this.errorMessage.addAll(CompareUtils.compareListDataAndCsv(list, expectedFile, "id_san_company",
            listExcludeField));
    }

    /**
     * Builds the san id map company excluded fields.
     *
     * @return the list
     */
    private List<String> buildSanIdMapCompanyExcludedFields() {
        return Arrays.asList("pk", "id_company", "isPersisted", "selected", "object_version", "version",
            "last_updated_at");
    }

    /**
     * Compare san id map person.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void compareSanIdMapPerson() throws IOException {
        String uploadPath = Faces.getInitParameter(Constants.SANSAN_VERIFY_CSV_DIR_PARAM_NAME);
        String expectedFile = "";
        if (!uploadPath.endsWith(VerifySansanDataService.SLASH_CHARACTER)) {
            expectedFile =
                uploadPath + VerifySansanDataService.SLASH_CHARACTER + VerifySansanDataService.SAN_IDMAP_PERSON
                    + VerifySansanDataService.CSV_EXTENSION;
        } else {
            expectedFile =
                uploadPath + VerifySansanDataService.SAN_IDMAP_PERSON + VerifySansanDataService.CSV_EXTENSION;
        }
        List<San_idmap_person> list = this.idMapPersonRepo.findAll();
        final List<String> listExcludeField = this.buildSanIdMapPersonExcludedFields();
        this.errorMessage.addAll(CompareUtils.compareListDataAndCsv(list, expectedFile, "id_san_person",
            listExcludeField));
    }

    /**
     * Builds the san id map person excluded fields.
     *
     * @return the list
     */
    private List<String> buildSanIdMapPersonExcludedFields() {
        return Arrays.asList("pk", "id_person", "isPersisted", "selected", "object_version", "version",
            "last_updated_at");
    }

    /**
     * Clean program test.
     *
     * @return true, if successful
     */
    public boolean cleanProgramTest() {
        try {
            this.elasticService.deleteExistIndex(ElasticSearchConstants.San_card_data.INDEX_VALUE);
            this.elasticService.deleteExistIndex(ElasticSearchConstants.San_com_info.INDEX_VALUE);
            this.elasticService.deleteExistIndex(ElasticSearchConstants.San_com_branch.INDEX_VALUE);
            this.elasticService.deleteExistIndex(ElasticSearchConstants.San_com_site.INDEX_VALUE);
            this.elasticService.deleteExistIndex(ElasticSearchConstants.San_com_cnumber.INDEX_VALUE);
            this.elasticService.deleteExistIndex(ElasticSearchConstants.San_com_mdomain.INDEX_VALUE);
            this.elasticService.deleteExistIndex(ElasticSearchConstants.San_com_udomain.INDEX_VALUE);
            this.elasticService.deleteExistIndex(ElasticSearchConstants.San_com_whole_card.INDEX_VALUE);
            this.elasticService.deleteExistIndex(ElasticSearchConstants.San_com_live_stat.INDEX_VALUE);
            this.elasticService.deleteExistIndex(ElasticSearchConstants.San_com_live_owner.INDEX_VALUE);
            this.elasticService.deleteExistIndex(ElasticSearchConstants.San_com_live_client.INDEX_VALUE);
            this.elasticService.deleteExistIndex(ElasticSearchConstants.San_card_tag.INDEX_VALUE);
            this.elasticService.deleteExistIndex(ElasticSearchConstants.San_idmap_card.INDEX_VALUE);
            this.elasticService.deleteExistIndex(ElasticSearchConstants.San_idmap_company.INDEX_VALUE);
            this.elasticService.deleteExistIndex(ElasticSearchConstants.San_idmap_person.INDEX_VALUE);
            Session session = EmLocator.getEm().unwrap(Session.class);
            session.doWork(new Work() {
                @Override
                public void execute(final Connection connection) throws SQLException {
                    String query = "TRUNCATE TABLE san_com_info CASCADE";
                    try (PreparedStatement st = connection.prepareStatement(query)) {
                        st.executeQuery();
                    } catch (Exception ioe) {
                        VerifySansanDataService.this.log.debug(ioe.getMessage());
                    }
                    query = "TRUNCATE TABLE san_idmap_company CASCADE";
                    try (PreparedStatement st = connection.prepareStatement(query)) {
                        st.executeQuery();
                    } catch (Exception ioe) {
                        VerifySansanDataService.this.log.debug(ioe.getMessage());
                    }
                    query = "TRUNCATE TABLE san_idmap_person CASCADE";
                    try (PreparedStatement st = connection.prepareStatement(query)) {
                        st.executeQuery();
                    } catch (Exception ioe) {
                        VerifySansanDataService.this.log.debug(ioe.getMessage());
                    }
                    query = "TRUNCATE TABLE san_idmap_card CASCADE";
                    try (PreparedStatement st = connection.prepareStatement(query)) {
                        st.executeQuery();
                    } catch (Exception ioe) {
                        VerifySansanDataService.this.log.debug(ioe.getMessage());
                    }
                    query = "TRUNCATE TABLE san_com_live_client CASCADE";
                    try (PreparedStatement st = connection.prepareStatement(query)) {
                        st.executeQuery();
                    } catch (Exception ioe) {
                        VerifySansanDataService.this.log.debug(ioe.getMessage());
                    }
                    query = "TRUNCATE TABLE san_com_live_owner CASCADE";
                    try (PreparedStatement st = connection.prepareStatement(query)) {
                        st.executeQuery();
                    } catch (Exception ioe) {
                        VerifySansanDataService.this.log.debug(ioe.getMessage());
                    }
                    query = "TRUNCATE TABLE san_com_live_stat CASCADE";
                    try (PreparedStatement st = connection.prepareStatement(query)) {
                        st.executeQuery();
                    } catch (Exception ioe) {
                        VerifySansanDataService.this.log.debug(ioe.getMessage());
                    }
                    query = "TRUNCATE TABLE san_com_mdomain CASCADE";
                    try (PreparedStatement st = connection.prepareStatement(query)) {
                        st.executeQuery();
                    } catch (Exception ioe) {
                        VerifySansanDataService.this.log.debug(ioe.getMessage());
                    }

                    query = "TRUNCATE TABLE san_com_site CASCADE";
                    try (PreparedStatement st = connection.prepareStatement(query)) {
                        st.executeQuery();
                    } catch (Exception ioe) {
                        VerifySansanDataService.this.log.debug(ioe.getMessage());
                    }

                    query = "TRUNCATE TABLE san_com_udomain CASCADE";
                    try (PreparedStatement st = connection.prepareStatement(query)) {
                        st.executeQuery();
                    } catch (Exception ioe) {
                        VerifySansanDataService.this.log.debug(ioe.getMessage());
                    }
                    query = "TRUNCATE TABLE san_com_whole_card CASCADE";
                    try (PreparedStatement st = connection.prepareStatement(query)) {
                        st.executeQuery();
                    } catch (Exception ioe) {
                        VerifySansanDataService.this.log.debug(ioe.getMessage());
                    }
                    query = "TRUNCATE TABLE san_card_tag CASCADE";
                    try (PreparedStatement st = connection.prepareStatement(query)) {
                        st.executeQuery();
                    } catch (Exception ioe) {
                        VerifySansanDataService.this.log.debug(ioe.getMessage());
                    }
                    query = "TRUNCATE TABLE san_com_branch CASCADE";
                    try (PreparedStatement st = connection.prepareStatement(query)) {
                        st.executeQuery();
                    } catch (Exception ioe) {
                        VerifySansanDataService.this.log.debug(ioe.getMessage());
                    }
                }
            });
            return true;
        } catch (final Exception ex) {
            this.log.debug(ex.getMessage());
            return false;
        }
    }

    /**
     * Gets the log file name by start time.
     *
     * @param startTime the start time
     * @return the log file name by start time
     */
    public String getLogFileNameByStartTime(final Date startTime) {
        List<Integer> jobExecutionId = this.batch_job_executionRepository.getJobExecutionIdByStartTime(startTime);
        if (CollectionUtils.isEmpty(jobExecutionId)) {
            return null;
        }
        return this.batch_job_execution_paramsRepository.getLogFileNameById(jobExecutionId.get(0));
    }


}
