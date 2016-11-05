package arrow.framework.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.mozilla.universalchardet.UniversalDetector;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import arrow.businesstraceability.constant.CommonConstants;
import arrow.businesstraceability.util.CsvToBeanCustom;

/**
 * The Class CsvUtils.
 */
public class CsvUtils {

    /**
     * Gets the fields name of class.
     *
     * @param klazz the klazz
     * @return the fields name of class
     */
    public static String[] getFieldsNameOfClass(final Class<?> klazz) {
        Field[] fields = klazz.getDeclaredFields();
        List<String> list = new ArrayList<>();
        for (Field field : fields) {
            list.add(field.getName());
        }

        return list.toArray(new String[list.size()]);
    }

    /**
     * Removes the fields in list.
     *
     * @param listFieldNeedRemove the list field need remove
     * @param columns the columns
     * @return the string[]
     */
    public static String[] removeFieldsInList(final String[] listFieldNeedRemove, final String[] columns) {

        String[] finalColumns = new String[(columns.length - listFieldNeedRemove.length)];
        int count = 0;
        for (int j = 0; j < columns.length; j++) {
            if (!ArrayUtils.contains(listFieldNeedRemove, columns[j])) {
                finalColumns[count] = columns[j];
                count++;

            }
        }
        return finalColumns;
    }

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
        byte[] buf = new byte[4096];
        try (FileInputStream fis = new FileInputStream(filePath)) {
            UniversalDetector detector = new UniversalDetector(null);

            int nread;
            while (((nread = fis.read(buf)) > 0) && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            fis.close();
            detector.dataEnd();


            if (StringUtils.isEmpty(detector.getDetectedCharset())) {
                return CommonConstants.SANSAN_FILE_DEFAULT_ENCODING;
            } else {
                if (detector.getDetectedCharset().equals(CommonConstants.SANSAN_FILE_SHIFT_JIS_ENCODING)) {
                    return CommonConstants.SANSAN_FILE_DEFAULT_ENCODING;
                }
                return detector.getDetectedCharset();
            }
        } catch (final IOException e) {
            return null;
        }

    }
}
