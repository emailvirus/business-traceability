package arrow.framework.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import arrow.businesstraceability.constant.Constants;


/**
 * The Class FileUtils.
 */
public class FileUtils {

    /**
     * Recursively delete a directory.
     *
     * @param directory a directory
     * @throws IOException if any
     */
    public static void deleteDirectory(final File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }
        FileUtils.deleteChildrenFiles(directory);
        if (!directory.delete()) {
            throw new IOException("Unable to delete " + directory);
        }
    }

    /**
     * Delete children files.
     *
     * @param directory the directory
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static void deleteChildrenFiles(final File directory) throws IOException {
        File[] listFiles = directory.listFiles();
        if (listFiles == null) {
            return;
        }
        for (final File subFile : listFiles) {
            if (subFile.isDirectory()) {
                FileUtils.deleteDirectory(subFile);
            } else if (!subFile.delete()) {
                throw new IOException("Cannot delete this file!");
            }
        }
    }

    /**
     * Random access file.
     *
     * @param is the is
     * @return the random access file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static RandomAccessFile toRandomAccessFile(final InputStream is) throws IOException {
        final RandomAccessFile raf = new RandomAccessFile(File.createTempFile("isc", "tmp"), "rwd");

        final byte[] buffer = new byte[2048];
        int tmp = 0;

        while ((tmp = is.read(buffer)) != -1) {
            raf.write(buffer, 0, tmp);
        }

        raf.seek(0);

        return raf;
    }

    /**
     * Read file action.
     *
     * @param filePath the file path
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String readFile(final String filePath) throws IOException {
        final StringBuffer fileData = new StringBuffer(1000);
        // final BufferedReader reader = new BufferedReader(new FileReader(filePath));
        try (BufferedReader reader =
            new BufferedReader(new InputStreamReader(new FileInputStream(filePath), Charset.forName("UTF-8")))) {
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                final String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
            return fileData.toString();
        }
    }

    /**
     * Load resource file.
     *
     * @param filePath the file path
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String loadResourceFile(final String filePath) throws IOException {
        InputStream is = FileUtils.class.getResourceAsStream(filePath);
        return IOUtils.toString(is);
    }

    /**
     * Gets the content type.
     *
     * @param filePath the file path
     * @return the content type
     */
    public static String getContentType(final String filePath) {
        String extension = FilenameUtils.getExtension(filePath);
        String contentType = null;
        switch (extension) {
            case Constants.PDF_FILE_TYPE:
                contentType = Constants.PDF_CONTENT_TYPE;
                break;

            case Constants.DOC_FILE_TYPE:
                contentType = Constants.DOC_CONTENT_TYPE;
                break;

            case Constants.DOCX_FILE_TYPE:
                contentType = Constants.DOCX_CONTENT_TYPE;
                break;

            case Constants.XLS_FILE_TYPE:
                contentType = Constants.XLS_CONTENT_TYPE;
                break;

            case Constants.XLSX_FILE_TYPE:
                contentType = Constants.XLSX_CONTENT_TYPE;
                break;
            default:
                contentType = null;
                break;
        }
        return contentType;
    }

}
