package arrow.businesstraceability.control.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;

import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.control.service.VerifySansanDataService;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.faces.message.Message;
import arrow.framework.faces.util.FaceletUtils;
import arrow.framework.util.MessageUtils;
import arrow.framework.util.collections.CollectionUtils;

/**
 * The Class UploadCsvBean.
 */
@Named
@javax.faces.view.ViewScoped
@ManagedBean
public class VerifySansanDataBean extends AbstractBean {


    /** The service. */
    @Inject
    private VerifySansanDataService service;

    /** The input file. */
    private String inputFile;

    /** The status msg. */
    private String statusMsg;

    private Date toTimeReg;

    private Date fromTimeReg;

    private Date dateReg;

    private String logFileName;

    private boolean isVisible = false;

    private Date startTime;

    private boolean disableImportCsvButton = true;

    private boolean disableConnectApiButton = true;


    /**
     * Gets the file allow type.
     *
     * @return the file allow type
     */
    public String getInputFileAllowType() {
        return Constants.CSV_FILE_ALLOW_TYPE;
    }

    /**
     * Gets the file allow type.
     *
     * @return the file allow type
     */
    public String getExpectedFileAllowType() {
        return Constants.CSV_FILE_ALLOW_TYPE;
    }

    /**
     * Gets the file size limit.
     *
     * @return the file size limit
     */
    public int getInputFileSizeLimit() {
        return Constants.CSV_FILE_SIZE_LIMIT;
    }

    /**
     * Gets the file size limit.
     *
     * @return the file size limit
     */
    public int getExpectedFileSizeLimit() {
        return Constants.CSV_FILE_SIZE_LIMIT;
    }

    /**
     * Upload file listener.
     *
     * @param event the event
     */
    public void uploadFileListener(final FileUploadEvent event) {
        this.setInputFile(event.getFile().getFileName());
        this.service.uploadCsvFile(event.getFile());
        this.disableImportCsvButton = false;
    }

    /**
     * Upload expected file listener.
     *
     * @param event the event
     */
    public void uploadExpectedFileListener(final FileUploadEvent event) {
        this.service.uploadCsvFile(event.getFile());
    }

    /**
     * Import sansan file to database.
     */
    public void importSansanFileToDatabase() {
        this.statusMsg = StringUtils.EMPTY;
        this.startTime = new Date();
        boolean success = this.service.importData(this.getInputFile());
        if (success) {
            InfoMessage.sansan_verify_007_process_import_successful().show();
        } else {
            ErrorMessage.sansan_verify_008_process_import_unsuccessful().show();
        }
        this.setVisible(true);
        String logFileName = this.service.getLogFileNameByStartTime(this.startTime);
        if (StringUtils.isNotEmpty(logFileName)) {
            this.setLogFileName(logFileName);
        }
        this.disableImportCsvButton = true;
    }

    /**
     * Call api.
     */
    public void callApi() {
        List<Message> errors = new ArrayList<>();
        if (this.dateReg == null) {
            errors.add(ErrorMessage.sansan_verify_017_process_call_api_date_required());
        }

        if ((this.fromTimeReg == null)) {
            errors.add(ErrorMessage.sansan_verify_018_process_call_api_starttime_required());
        }

        if (this.toTimeReg == null) {
            errors.add(ErrorMessage.sansan_verify_019_process_call_api_endtime_required());
        }

        if (CollectionUtils.isNotEmpty(errors)) {
            MessageUtils.showMessages(errors);
            return;
        }

        this.statusMsg = StringUtils.EMPTY;
        this.startTime = new Date();
        boolean success = this.service.callApi(this.fromTimeReg, this.toTimeReg, this.dateReg);
        if (success) {
            InfoMessage.sansan_verify_009_process_call_api_successful().show();
        } else {
            ErrorMessage.sansan_verify_010_process_call_api_unsuccessful().show();
        }

        this.setVisible(true);
        // this.setLogFileName(this.createLogFileName());
        String logFileName = this.service.getLogFileNameByStartTime(this.startTime);
        if (StringUtils.isEmpty(logFileName)) {
            ErrorMessage.sansan_verify_016_process_call_api_unsuccessful_other_import_running().show();
            return;
        }
        this.setLogFileName(logFileName);
    }

    /**
     * Compare expected.
     */
    public void compareExpected() {
        this.statusMsg = StringUtils.EMPTY;
        final StringBuffer message = new StringBuffer();
        try {
            List<String> errorMessage = this.service.verifyData();
            if (CollectionUtils.isEmpty(errorMessage)) {
                InfoMessage.sansan_verify_005_actual_data_and_expected_data_is_same().show();
            } else {
                for (String error : errorMessage) {
                    message.append(error + "\n");
                }
                ErrorMessage.sansan_verify_006_actual_data_and_expected_data_is_not_same().show();
                this.statusMsg = message.toString();
            }
        } catch (IOException ioe) {
            ErrorMessage.sansan_verify_011_an_error_has_occurred().show();
            this.statusMsg = ioe.getMessage();
        }
    }

    /**
     * Download file.
     *
     * @param fileName
     * @throws IOException
     */
    public void downloadFile(final String logFileName) throws IOException {
        String downloadPath = Faces.getInitParameter(Constants.SANSAN_LOG_DOWNLOAD_PARAM);
        File targetPath = new File(downloadPath + logFileName + ".log");
        InputStream input = new FileInputStream(targetPath);
        FaceletUtils.sendFileToClient(logFileName, input);
    }

    /**
     * Get log file.
     */
    public void getLogFile() {
        this.setVisible(true);
        this.setLogFileName(this.createLogFileName());
    }

    /**
     * Create log file.
     *
     * @return file name.
     */
    public String createLogFileName() {
        String preFix = DateTimeFormat.forPattern("yyyyMMdd_HHmm").print(new DateTime());
        return "import_" + preFix + ".log";
    }

    /**
     * Clean program test.
     */
    public void cleanProgramTest() {
        boolean success = this.service.cleanProgramTest();
        if (success) {
            InfoMessage.sansan_verify_014_clean_test_successful().show();
        } else {
            ErrorMessage.sansan_verify_015_clean_test_unsuccessful().show();
        }
    }


    /**
     * Gets the input file.
     *
     * @return the input file
     */
    public String getInputFile() {
        return this.inputFile;
    }

    /**
     * Sets the input file.
     *
     * @param inputFile the new input file
     */
    public void setInputFile(final String inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * Gets the status msg.
     *
     * @return the status msg
     */
    public String getStatusMsg() {
        return this.statusMsg;
    }

    /**
     * Sets the status msg.
     *
     * @param statusMsg the new status msg
     */
    public void setStatusMsg(final String statusMsg) {
        this.statusMsg = statusMsg;

    }

    public Date getToTimeReg() {
        return this.toTimeReg;

    }

    public void setToTimeReg(final Date toTimeReg) {
        this.toTimeReg = toTimeReg;

    }

    public Date getFromTimeReg() {
        return this.fromTimeReg;

    }

    public void setFromTimeReg(final Date fromTimeReg) {
        this.fromTimeReg = fromTimeReg;

    }

    public Date getDateReg() {
        return this.dateReg;

    }

    public void setDateReg(final Date dateReg) {
        this.dateReg = dateReg;

    }

    public String getLogFileName() {
        return this.logFileName;
    }

    public void setLogFileName(final String logFileName) {
        this.logFileName = logFileName;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(final boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(final Date startTime) {
        this.startTime = startTime;
    }

    public boolean isDisableImportCsvButton() {
        return this.disableImportCsvButton;
    }

    public void setDisableImportCsvButton(final boolean disableImportCsvButton) {
        this.disableImportCsvButton = disableImportCsvButton;
    }

    public boolean isDisableConnectApiButton() {
        return this.disableConnectApiButton;
    }

    public void setDisableConnectApiButton(final boolean disableConnectApiButton) {
        this.disableConnectApiButton = disableConnectApiButton;
    }

}
