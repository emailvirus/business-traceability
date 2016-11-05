package arrow.businesstraceability.control.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.enterprise.context.ConversationScoped;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.omnifaces.util.Faces;

import com.opencsv.CSVWriter;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.constant.BassSystemConstants;
import arrow.businesstraceability.constant.Constants;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.CustomCsvWriter;
import arrow.framework.util.StringUtils;

/**
 * The Class SummaryReportService.
 */
@ConversationScoped
public class BassExportService extends AbstractService {

    private final Map<String, String> mapFileTables = new HashMap<>();

    /**
     * Create file data.
     *
     * @param mapFileTable the map file table
     * @throws SQLException the SQL exception
     */
    public void createData(final Map<String, String> mapFileTable) throws SQLException {
        this.mapFileTables.clear();
        this.mapFileTables.putAll(mapFileTable);
        Session session = EmLocator.getEm().unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(final Connection connection) throws SQLException {
                String folderPath = Faces.getInitParameter(Constants.BASS_UPLOAD_DIR_PARAM_NAME);
                String query = "";
                query = "update san_com_info set ts_export = now()";
                try (PreparedStatement st = connection.prepareStatement(query)) {
                    st.executeUpdate();
                } catch (SQLException ioe) {
                    BassExportService.this.log.debug(ioe.getMessage());
                }
                query = "update acc_com_entity set ts_export = now()";
                try (PreparedStatement st = connection.prepareStatement(query)) {
                    st.executeUpdate();
                } catch (SQLException ioe) {
                    BassExportService.this.log.debug(ioe.getMessage());
                }
                query =
                    "SELECT id_com_entity " + ",id_int_san_company " + ",indx_customer " + ", name_com_kana "
                        + ", name_company " + ", code_acc_client " + ",mynum_com " + ",id_credit " + ",y_start "
                        + ",y_establish " + ",to_char(date_creation, 'YYYY/MM/DD HH:MM:SS') as date_creation"
                        + ",ac_creation " + ",to_char(ts_export, 'YYYY/MM/DD HH:MM:SS') as ts_export"
                        + ",to_char(ts_UPDATE, 'YYYY/MM/DD HH:MM:SS') as ts_update" + ",ac_update "
                        + "FROM ACC_COM_ENTITY";
                try (PreparedStatement st = connection.prepareStatement(query)) {
                    ResultSet result = st.executeQuery();
                    BassExportService.this.writeToCsv(
                        result,
                        new File(folderPath, URLDecoder.decode(BassExportService.this.mapFileTables
                            .get(BassSystemConstants.BassExportTable.ACC_COM_ENTITY), Constants.DEFAULT_ENCODING)));
                } catch (IOException ioe) {
                    BassExportService.this.log.debug(ioe.getMessage());
                }
                query =
                    "SELECT credit.id_credit " + ",credit.id_com_entity " + ",credit.name_company2 "
                        + ",to_char(credit.date_valid_from, 'YYYY/MM/DD') as date_valid_from"
                        + ",to_char(credit.date_valid_to, 'YYYY/MM/DD') as date_valid_to"
                        + ",credit.indx_acc ,credit.code_zip " + ",credit.addr_all_hq " + ",credit.addr_all_hq2 "
                        + ",credit.tel_hq ,credit.fax_hq " + ",credit.code_acc_capital "
                        + ",credit.code_acc_fiscalmonth " + ",credit.code_acc_market " + ",credit.code_acc_tradecond "
                        + ",credit.code_acc_creditscore " + ",credit.code_acc_resurvey "
                        + ",credit.code_acc_suverysite " + ",credit.code_acc_surveyer "
                        + ",credit.code_acc_prohibitcause " + ",credit.code_acc_payment " + ",credit.code_acc_bankacc "
                        + ",credit.memo1 " + ",credit.memo2 " + ",credit.memo3 " + ",credit.indx_com "
                        + ",credit.ac_request_branch " + ",credit.ac_request " + ",credit.ac_middle_authorize "
                        + ",credit.ac_final_authorize " + ",credit.cause_decision " + ",credit.other_direction "
                        + ",credit.acc_direction " + ",to_char(credit.date_survey, 'YYYY/MM/DD') as date_survey"
                        + ",to_char(credit.date_authorize, 'YYYY/MM/DD') as date_authorize" + ",credit.score_credit "
                        + ",credit.addr_pref " + ",credit.addr_city " + ",credit.capital " + ",credit.n_employee "
                        + ",credit.url " + ",credit.path_report " + ",credit.status "
                        + "FROM ACC_COM_CREDIT credit INNER JOIN ACC_COM_ENTITY entity "
                        + "ON credit.ID_CREDIT = entity.ID_CREDIT";
                try (PreparedStatement st = connection.prepareStatement(query)) {
                    ResultSet result = st.executeQuery();
                    BassExportService.this.writeToCsv(
                        result,
                        new File(folderPath, URLDecoder.decode(BassExportService.this.mapFileTables
                            .get(BassSystemConstants.BassExportTable.ACC_COM_CREDIT), Constants.DEFAULT_ENCODING)));
                } catch (IOException ioe) {
                    BassExportService.this.log.debug(ioe.getMessage());
                }
                query =
                    "SELECT id_san_company" + ",id_company" + ",name_company" + ",name_com_eng" + ",name_com_kana"
                        + ",mynum_com" + ",id_sarscom" + ",to_char(ts_create, 'YYYY/MM/DD HH:MM:SS') as ts_create"
                        + ",to_char(ts_export, 'YYYY/MM/DD HH:MM:SS') as ts_export"
                        + ",to_char(ts_update, 'YYYY/MM/DD HH:MM:SS') as ts_update" + ",cn_update "
                        + "FROM SAN_COM_INFO";
                try (PreparedStatement st = connection.prepareStatement(query)) {
                    ResultSet result = st.executeQuery();
                    BassExportService.this.writeToCsv(
                        result,
                        new File(folderPath, URLDecoder.decode(
                            BassExportService.this.mapFileTables.get(BassSystemConstants.BassExportTable.SAN_COM_INFO),
                            Constants.DEFAULT_ENCODING)));
                } catch (IOException ioe) {
                    BassExportService.this.log.debug(ioe.getMessage());
                }
                query =
                    "SELECT id_branch" + ",id_company" + ",name_branch" + ",n_dupinfo" + ",id_card"
                        + ",to_char(date_latest_exchange, 'YYYY/MM/DD') as date_latest_exchange "
                        + "FROM SAN_COM_BRANCH branch "
                        + "WHERE branch.date_latest_exchange >= CURRENT_DATE - INTERVAL '3 year';";
                try (PreparedStatement st = connection.prepareStatement(query)) {
                    ResultSet result = st.executeQuery();
                    BassExportService.this.writeToCsv(
                        result,
                        new File(folderPath, URLDecoder.decode(BassExportService.this.mapFileTables
                            .get(BassSystemConstants.BassExportTable.SAN_COM_BRANCH), Constants.DEFAULT_ENCODING)));
                } catch (IOException ioe) {
                    BassExportService.this.log.debug(ioe.getMessage());
                }
                query =
                    "SELECT id_site" + ",id_company" + ",id_branch" + ",code_zip" + ",addr_all" + ",addr_pref"
                        + ",addr_city" + ",addr_block" + ",addr_bldg" + ",n_dupinfo" + ",id_card"
                        + ",to_char(date_latest_exchange, 'YYYY/MM/DD') as date_latest_exchange"
                        + "  FROM SAN_COM_SITE site "
                        + "WHERE site.date_latest_exchange >= CURRENT_DATE - INTERVAL '3 year'";
                try (PreparedStatement st = connection.prepareStatement(query)) {
                    ResultSet result = st.executeQuery();
                    BassExportService.this.writeToCsv(
                        result,
                        new File(folderPath, URLDecoder.decode(
                            BassExportService.this.mapFileTables.get(BassSystemConstants.BassExportTable.SAN_COM_SITE),
                            Constants.DEFAULT_ENCODING)));
                } catch (IOException ioe) {
                    BassExportService.this.log.debug(ioe.getMessage());
                }
                query =
                    "SELECT id_cnumber" + ",id_company" + ",id_branch" + ",id_site" + ",tel11" + ",fax" + ",n_dupinfo"
                        + ",id_card" + ",to_char(date_latest_exchange, 'YYYY/MM/DD') as date_latest_exchange"
                        + " FROM SAN_COM_CNUMBER cnumber "
                        + "WHERE cnumber.date_latest_exchange >= CURRENT_DATE - INTERVAL '3 year'";
                try (PreparedStatement st = connection.prepareStatement(query)) {
                    ResultSet result = st.executeQuery();
                    BassExportService.this.writeToCsv(
                        result,
                        new File(folderPath, URLDecoder.decode(BassExportService.this.mapFileTables
                            .get(BassSystemConstants.BassExportTable.SAN_COM_CNUMBER), Constants.DEFAULT_ENCODING)));
                } catch (IOException ioe) {
                    BassExportService.this.log.debug(ioe.getMessage());
                }
            }
        });
    }

    /**
     * Read file csv an convert to inputstream.
     *
     * @param fileName the file name
     * @return the input stream
     * @throws FileNotFoundException the file not found exception
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public InputStream getInputStream(final String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        String folderPath = Faces.getInitParameter(Constants.BASS_UPLOAD_DIR_PARAM_NAME);
        return new FileInputStream(new File(folderPath, URLDecoder.decode(fileName, Constants.DEFAULT_ENCODING)));
    }

    /**
     * Write to csv file.
     *
     * @param result
     * @param file
     * @throws IOException
     * @throws SQLException
     */
    private void writeToCsv(final ResultSet result, final File file) throws IOException, SQLException {
        CSVWriter csvWriter =
            new CustomCsvWriter(new FileWriter(file), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.DEFAULT_QUOTE_CHARACTER,
                CSVWriter.NO_ESCAPE_CHARACTER);
        csvWriter.writeAll(result, true);
        csvWriter.close();
        result.close();
    }

    /**
     * Setting saving path in config file.
     *
     * @param folderPath the folder path
     * @param savingPath the saving path
     * @throws FileNotFoundException the file not found exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void settingSavingPathInConfigFile(final String folderPath, final String savingPath)
        throws FileNotFoundException, IOException {
        try (InputStreamReader input =
            new InputStreamReader(new FileInputStream(folderPath), Charset.forName(Constants.DEFAULT_ENCODING))) {
            Properties prop = new Properties();
            prop.load(input);
            prop.setProperty(Constants.BASS_CONFIG_EXPORT_PATH, savingPath);
            try (OutputStreamWriter output =
                new OutputStreamWriter(new FileOutputStream(folderPath), Charset.forName(Constants.DEFAULT_ENCODING))) {
                prop.store(output, null);
            }
        };
    }

    /**
     * Creates the file config sars bass.
     *
     * @param folderPath the folder path
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void createFileConfigSarsBass(final String folderPath) throws IOException {
        try (OutputStreamWriter output =
            new OutputStreamWriter(new FileOutputStream(folderPath), Charset.forName(Constants.DEFAULT_ENCODING))) {
            Properties prop = new Properties();
            prop.setProperty(Constants.BASS_CONFIG_EXPORT_PATH, StringUtils.EMPTY_STRING);
            prop.store(output, null);
        };
    }

}
