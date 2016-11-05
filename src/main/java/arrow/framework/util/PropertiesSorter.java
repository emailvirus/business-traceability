/*
 * package: arrow.framework.util file: PropertiesSorter.java time: Jun 27, 2014
 * @author michael
 */
package arrow.framework.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;


/**
 * The Class PropertiesSorter.
 */
public class PropertiesSorter {

    /** The Constant PATH_PREFIX. */
    public static final String PATH_PREFIX = "./src/main/resources/arrow/i18n/";

    /** The Constant PROPERTY_FILE_SUFFIX. */
    public static final String PROPERTY_FILE_SUFFIX = ".properties";

    /** The Constant UTF8_BOM. */
    public static final String UTF8_BOM = "\uFEFF";

    /** The Constant UTF8_ENCODING. */
    public static final String UTF8_ENCODING = "UTF-8";

    /** The written property keys. */
    private static List<String> writtenPropertyKeys;

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws PropertyFileException the property file exception
     */
    public static void main(final String... args) throws IOException, PropertyFileException {
        final List<String> listBundlePrefix = new ArrayList<>();
        listBundlePrefix.add("messages");
        listBundlePrefix.add("error-messages");
        listBundlePrefix.add("database_labels");

        // Have to put msg-keys at last.
        listBundlePrefix.add("msg-keys");
        PropertiesSorter.writtenPropertyKeys = new ArrayList<String>();
        for (final String bundle : listBundlePrefix) {
            PropertiesSorter.sortResouces(bundle);
        }

        System.out.println("Finish!");
    }

    /**
     * Sort resouces.
     *
     * @param resourcePrefix the resource prefix
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws PropertyFileException the property file exception
     */
    private static void sortResouces(final String resourcePrefix) throws IOException, PropertyFileException {
        System.out.println("Start for " + resourcePrefix + " !");
        final Map<String, Properties> mapOfNameAndProperties = new HashMap<String, Properties>();
        final Map<String, PrintWriter> mapOfNameAndWriters = new HashMap<String, PrintWriter>();

        PropertiesSorter.readFilesToProperties(resourcePrefix, mapOfNameAndProperties, mapOfNameAndWriters);
        PropertiesSorter.writePropertiesToFiles(resourcePrefix, mapOfNameAndProperties, mapOfNameAndWriters);
        System.out.println("Done for " + resourcePrefix + " !");
    }

    /**
     * Read files to properties.
     *
     * @param resourcePrefix the resource prefix
     * @param mapOfNameAndProperties the map of name and properties
     * @param mapOfNameAndWriters the map of name and writers
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws PropertyFileException the property file exception
     */
    private static void readFilesToProperties(final String resourcePrefix,
            final Map<String, Properties> mapOfNameAndProperties, final Map<String, PrintWriter> mapOfNameAndWriters)
                    throws IOException, PropertyFileException {

        System.out.println("Read properties from :" + resourcePrefix);
        final File rootResourceFolder = new File(PropertiesSorter.PATH_PREFIX);
        File[] listFiles = rootResourceFolder.listFiles();
        if (null == listFiles) {
            return;
        }
        for (final File resourceFile : listFiles) {
            if (resourceFile.getName().endsWith(PropertiesSorter.PROPERTY_FILE_SUFFIX)
                    && resourceFile.getName().startsWith(resourcePrefix)) {
                PropertiesSorter.validatePropertyFile(resourceFile);

                final Properties prop = new Properties();
                FileOutputStream fos = null;

                try (BufferedReader bufferReader =
                        new BufferedReader(new InputStreamReader(new FileInputStream(resourceFile),
                                PropertiesSorter.UTF8_ENCODING))) {
                    // Remove the UTF8_BOM chacters and put back to the properties list
                    String firstLine = bufferReader.readLine();
                    if (firstLine != null) {
                        final boolean isUtf8File = firstLine.contains(PropertiesSorter.UTF8_BOM);
                        if (isUtf8File) {
                            firstLine = firstLine.replace(PropertiesSorter.UTF8_BOM, "");
                        }
                        if (firstLine.split("=").length == 1) {
                            prop.put(firstLine.split("=")[0], "");
                        }
                        else {
                            prop.put(firstLine.split("=")[0], firstLine.split("=")[1]);
                        }

                        prop.load(bufferReader);
                        bufferReader.close();

                        fos = new FileOutputStream(resourceFile);
                        // Write some prefix by to indicate that our file is UTF-8 with BOM
                        if (isUtf8File) {
                            fos.write(new byte[] {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
                        }
                        // fos.close();

                        final PrintWriter writer =
                                new PrintWriter(new OutputStreamWriter(new FileOutputStream(resourceFile),
                                        PropertiesSorter.UTF8_ENCODING), true);
                        mapOfNameAndProperties.put(resourceFile.getName(), prop);
                        mapOfNameAndWriters.put(resourceFile.getName(), writer);
                    }
                } finally {
                    if (null != fos) {
                        fos.close();
                    }
                }
            }
        }

    }

    /**
     * Write properties to files.
     *
     * @param resourcePrefix the resource prefix
     * @param mapOfNameAndProperties the map of name and properties
     * @param mapOfNameAndWriters the map of name and writers
     */
    private static void writePropertiesToFiles(final String resourcePrefix,
            final Map<String, Properties> mapOfNameAndProperties, final Map<String, PrintWriter> mapOfNameAndWriters) {

        System.out.println("Start write properties:" + resourcePrefix);
        Properties standardProperties = null;
        PrintWriter standardWriter = null;
        for (Entry<String, Properties> item : mapOfNameAndProperties.entrySet()) {
            if (PropertiesSorter.isStandardPropertyFile(item.getKey(), resourcePrefix)) {
                standardProperties = item.getValue();
                standardWriter = mapOfNameAndWriters.get(item.getKey());
            }
        }

        final Set<String> keys = new TreeSet<String>();
        keys.addAll(standardProperties.stringPropertyNames());
        for (final String key : keys) {

            // exclude msg-keys as it is not normal resource bundle.
            if (!resourcePrefix.equalsIgnoreCase("msg-keys")) {
                if (PropertiesSorter.writtenPropertyKeys.contains(key)) {
                    // Duplicate key, keep the first version
                    continue;
                }
                else {
                    PropertiesSorter.writtenPropertyKeys.add(key);
                }
            }

            PropertiesSorter.printPropertyEntry(key, standardProperties.getProperty(key), standardWriter);
            for (Entry<String, Properties> entry : mapOfNameAndProperties.entrySet()) {
                if (!PropertiesSorter.isStandardPropertyFile(entry.getKey(), resourcePrefix)) {

                    System.out.println("Write to :" + entry.getKey());
                    final Properties prop = entry.getValue();
                    final PrintWriter writer = mapOfNameAndWriters.get(entry.getKey());
                    if (prop.containsKey(key)) {

                        System.out.println("Write " + key + ": " + prop.getProperty(key) + " to " + entry.getKey());
                        PropertiesSorter.printPropertyEntry(key, prop.getProperty(key), writer);
                    }
                    else {
                        System.out.println("Write " + key + ": " + prop.getProperty(key));
                        PropertiesSorter.printPropertyEntry(key, standardProperties.getProperty(key), writer);
                    }
                }
            }
        }

        // Close the writer
        for (final Entry<String, PrintWriter> entry : mapOfNameAndWriters.entrySet()) {
            PrintWriter writer = entry.getValue();
            System.out.println("Flush and close :" + entry.getKey());
            writer.flush();
            writer.close();
        }
        standardWriter.close();

    }

    /**
     * Prints the property entry.
     *
     * @param key the key
     * @param value the value
     * @param writer the writer
     */
    private static void printPropertyEntry(final String key, final String value, final PrintWriter writer) {
        writer.println(key + "=" + value);
    }

    /** The Constant ERROR_MARKS. */
    private static final String[] ERROR_MARKS = {"====", "<<<<<", ">>>>>"};

    /**
     * Validate property file.
     *
     * @param file the file
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws PropertyFileException the property file exception
     */
    private static void validatePropertyFile(final File file) throws IOException, PropertyFileException {
        final String fileContentStr = FileUtils.readFile(file.getPath());
        for (final String errorMark : PropertiesSorter.ERROR_MARKS) {
            if (fileContentStr.contains(errorMark)) {
                throw new PropertyFileException("Found string " + errorMark + " on file " + file.getName());
            }
        }
    }

    /**
     * Checks if is standard property file.
     *
     * @param fileName the file name
     * @param resourcePrefix the resource prefix
     * @return true, if is standard property file
     */
    private static boolean isStandardPropertyFile(final String fileName, final String resourcePrefix) {
        return fileName.equalsIgnoreCase(resourcePrefix + PropertiesSorter.PROPERTY_FILE_SUFFIX);
    }

    /**
     * The Class PropertyFileException.
     */
    static class PropertyFileException extends Exception {

        /**
         * Instantiates a new property file exception.
         *
         * @param message the message
         */

        public PropertyFileException(final String message) {
            super(message + ". Resolve and run PropertySorter again!");
        }
    }
}
