package arrow.businesstraceability.control.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

import arrow.businesstraceability.constant.BassSystemConstants;
import arrow.businesstraceability.constant.Constants;
import arrow.businesstraceability.control.service.BassExportService;
import arrow.framework.bean.AbstractBean;
import arrow.framework.faces.util.FaceletUtils;
import arrow.framework.util.StringUtils;

/**
 * The Class CompanyManagementBean.
 */
@Named
@ViewScoped
public class BassExportBean extends AbstractBean {

    @Inject
    private BassExportService bassExportService;

    private String sanComInfoFileName;

    private String sanComBranchFileName;

    private String sanComSiteFileName;

    private String sanComCnumberFileName;

    private String accComEntityFileName;

    private String accComCreditFileName;

    private boolean isVisible = false;

    private String savingPathInScreen;

    private String nameProject;

    private String folderDownLoadBassEncode;


    /**
     * Export File.
     *
     * @throws SQLException the SQL exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void export() throws SQLException, IOException {
        this.setVisible(true);
        String preFix = DateTimeFormat.forPattern("yyyyMMdd_HHmm").print(new DateTime());
        Map<String, String> mapFileTables = new HashMap<String, String>();
        this.accComEntityFileName =
            preFix + BassSystemConstants.FileNameElement.HYPHEN_CHARACTER
                + BassSystemConstants.BassExportTable.ACC_COM_ENTITY + BassSystemConstants.ExportFileType.CSV_EXTENSION;
        this.accComCreditFileName =
            preFix + BassSystemConstants.FileNameElement.HYPHEN_CHARACTER
                + BassSystemConstants.BassExportTable.ACC_COM_CREDIT + BassSystemConstants.ExportFileType.CSV_EXTENSION;
        this.sanComInfoFileName =
            preFix + BassSystemConstants.FileNameElement.HYPHEN_CHARACTER
                + BassSystemConstants.BassExportTable.SAN_COM_INFO + BassSystemConstants.ExportFileType.CSV_EXTENSION;
        this.sanComBranchFileName =
            preFix + BassSystemConstants.FileNameElement.HYPHEN_CHARACTER
                + BassSystemConstants.BassExportTable.SAN_COM_BRANCH + BassSystemConstants.ExportFileType.CSV_EXTENSION;
        this.sanComSiteFileName =
            preFix + BassSystemConstants.FileNameElement.HYPHEN_CHARACTER
                + BassSystemConstants.BassExportTable.SAN_COM_SITE + BassSystemConstants.ExportFileType.CSV_EXTENSION;
        this.sanComCnumberFileName =
            preFix + BassSystemConstants.FileNameElement.HYPHEN_CHARACTER
                + BassSystemConstants.BassExportTable.SAN_COM_CNUMBER
                + BassSystemConstants.ExportFileType.CSV_EXTENSION;
        mapFileTables.put(BassSystemConstants.BassExportTable.ACC_COM_ENTITY, this.accComEntityFileName);
        mapFileTables.put(BassSystemConstants.BassExportTable.ACC_COM_CREDIT, this.accComCreditFileName);
        mapFileTables.put(BassSystemConstants.BassExportTable.SAN_COM_INFO, this.sanComInfoFileName);
        mapFileTables.put(BassSystemConstants.BassExportTable.SAN_COM_BRANCH, this.sanComBranchFileName);
        mapFileTables.put(BassSystemConstants.BassExportTable.SAN_COM_SITE, this.sanComSiteFileName);
        mapFileTables.put(BassSystemConstants.BassExportTable.SAN_COM_CNUMBER, this.sanComCnumberFileName);
        this.bassExportService.createData(mapFileTables);
        this.nameProject = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        String folderPath = Faces.getInitParameter(Constants.BASS_UPLOAD_DIR_PARAM_NAME);
        String folderPathConfigFile =
            Faces.getInitParameter(Constants.BASS_CONFIG_DIR_PARAM_NAME) + Constants.BASS_CONFIG_EXPORT_FILE_NAME;
        this.setFolderDownLoadBassEncode(URLEncoder.encode(folderPath, Constants.DEFAULT_ENCODING));
        FileInputStream readFile = null;
        Properties prop = new Properties();
        try {
            readFile = new FileInputStream((folderPathConfigFile));
            try (InputStreamReader input = new InputStreamReader(readFile, Charset.forName(Constants.DEFAULT_ENCODING))) {
                prop.load(input);
                this.savingPathInScreen = prop.getProperty(Constants.BASS_CONFIG_EXPORT_PATH);
            };

        } catch (FileNotFoundException fnfe) {
            this.bassExportService.createFileConfigSarsBass(folderPathConfigFile);
            this.savingPathInScreen = StringUtils.EMPTY_STRING;
        }

    }

    /**
     * Setting saving path in config file.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void settingSavingPathInConfigFile() throws IOException {
        String folderPathConfigFile =
            Faces.getInitParameter(Constants.BASS_CONFIG_DIR_PARAM_NAME) + Constants.BASS_CONFIG_EXPORT_FILE_NAME;
        this.bassExportService.settingSavingPathInConfigFile(folderPathConfigFile, this.savingPathInScreen);
    }


    /**
     * Download file.
     *
     * @param fileName the file name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void downloadFile(final String fileName) throws IOException {
        InputStream is = this.bassExportService.getInputStream(fileName);
        FaceletUtils.sendFileToClient(fileName, is);
    }

    public String getSanComInfoFileName() {
        return this.sanComInfoFileName;
    }

    public void setSanComInfoFileName(final String sanComInfoFileName) {
        this.sanComInfoFileName = sanComInfoFileName;
    }

    public String getSanComBranchFileName() {
        return this.sanComBranchFileName;
    }

    public void setSanComBranchFileName(final String sanComBranchFileName) {
        this.sanComBranchFileName = sanComBranchFileName;
    }

    public String getSanComSiteFileName() {
        return this.sanComSiteFileName;
    }

    public void setSanComSiteFileName(final String sanComSiteFileName) {
        this.sanComSiteFileName = sanComSiteFileName;
    }

    public String getSanComCnumberFileName() {
        return this.sanComCnumberFileName;
    }

    public void setSanComCnumberFileName(final String sanComCnumberFileName) {
        this.sanComCnumberFileName = sanComCnumberFileName;
    }

    public String getAccComEntityFileName() {
        return this.accComEntityFileName;
    }

    public void setAccComEntityFileName(final String accComEntityFileName) {
        this.accComEntityFileName = accComEntityFileName;
    }

    public String getAccComCreditFileName() {
        return this.accComCreditFileName;
    }

    public void setAccComCreditFileName(final String accComCreditFileName) {
        this.accComCreditFileName = accComCreditFileName;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(final boolean isVisible) {
        this.isVisible = isVisible;
    }

    public String getSavingPathInScreen() {
        return this.savingPathInScreen;
    }

    public void setSavingPathInScreen(final String savingPathInScreen) {
        this.savingPathInScreen = savingPathInScreen;
    }

    public String getNameProject() {
        return this.nameProject;
    }

    public void setNameProject(final String nameProject) {
        this.nameProject = nameProject;
    }

    public String getFolderDownLoadBassEncode() {
        return this.folderDownLoadBassEncode;
    }

    public void setFolderDownLoadBassEncode(final String folderDownLoadBassEncode) {
        this.folderDownLoadBassEncode = folderDownLoadBassEncode;
    }
}
