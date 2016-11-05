package arrow.businesstraceability.control.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Faces;
import org.primefaces.model.UploadedFile;

import arrow.businesstraceability.base.AbstractService;
import arrow.businesstraceability.constant.Constants;
import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.faces.message.InfoMessage;
import arrow.framework.util.DateUtils;

/**
 * The type Upload csv service.
 */
public class UploadCsvService extends AbstractService {

    /** The Constant YEAR. */
    private static final String YEAR = "YYYY";

    /** The Constant MONTH. */
    private static final String MONTH = "MM";

    /**
     * Upload csv file.
     *
     * @param file the file
     * @param selectedMonth the selected month
     */
    public void uploadCsvFile(final UploadedFile file, String selectedMonth) {
        try {
            String uploadPath = Faces.getInitParameter(Constants.UPLOAD_CSV_DIR_PARAM_NAME);
            File targetPath = new File(uploadPath);
            if (!targetPath.exists()) {
                boolean result = targetPath.mkdirs();
                if (!result) {
                    this.log.error("Upload dir is not available" + uploadPath);
                    ErrorMessage.csv_002_upload_unsuccessfully().show();
                    return;
                }
            }

            String fileName = Constants.UPLOAD_CSV_FILE_NAME_TEMPLATE;
            fileName = fileName.replace(UploadCsvService.YEAR, DateUtils.getCurrentYear());
            if (selectedMonth.length() == 1) {
                selectedMonth = "0" + selectedMonth;
            }
            fileName = fileName.replace(UploadCsvService.MONTH, selectedMonth);

            this.uploadFile(targetPath, file, fileName);
            this.uploadFile(targetPath, file, null);
            InfoMessage.csv_001_upload_successfully().show();
        } catch (final IOException e) {
            ErrorMessage.csv_002_upload_unsuccessfully().show();
        }
    }

    /**
     * Upload file.
     *
     * @param targetPath the target path
     * @param file the file
     * @param fileName the file name
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void uploadFile(final File targetPath, final UploadedFile file, String fileName) throws IOException {
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
    }
}
