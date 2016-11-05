package arrow.businesstraceability.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mozilla.universalchardet.UniversalDetector;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import arrow.businesstraceability.constant.Constants;

// TODO: Auto-generated Javadoc
/**
 * The Class CsvUtils.
 */
public class CsvUtils {

    /**
     * Read files.
     *
     * @param <T> the generic type
     * @param filePath the file path
     * @param entityClazz the entity clazz
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static <T> List<T> readFiles(final String filePath, final Class<T> entityClazz) throws IOException {
        String encoding = CsvUtils.detectEncoding(filePath);
        if (StringUtils.isEmpty(encoding)) {
            return Collections.emptyList();
        }

        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filePath), encoding));
        HeaderColumnNameMappingStrategy<T> castleStrategy = new HeaderColumnNameMappingStrategy<T>();
        castleStrategy.setType(entityClazz);
        CsvToBean<T> csvToBean = new CsvToBeanCustom<T>();
        List<T> list = csvToBean.parse(castleStrategy, reader);
        return list;
    }


    /**
     * Detect encoding.
     *
     * @param filePath the file path
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static String detectEncoding(final String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            UniversalDetector detector = new UniversalDetector(null);

            byte[] buf = new byte[4096];
            int nread;
            while (((nread = fis.read(buf)) > 0) && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }

            detector.dataEnd();

            if (StringUtils.isEmpty(detector.getDetectedCharset())) {
                return Constants.SANSAN_FILE_DEFAULT_ENCODING;
            } else {
                if (detector.getDetectedCharset().equals(Constants.SANSAN_FILE_SHIFT_JIS_ENCODING)) {
                    return Constants.SANSAN_FILE_DEFAULT_ENCODING;
                }
                return detector.getDetectedCharset();
            }
        } catch (final IOException e) {
            return null;
        }

    }
}
