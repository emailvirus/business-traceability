package arrow.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;

/**
 * The Class GenerateMessageKey.
 */
public class GenerateMessageKey {
    private static final BaseLogger LOGGER = BaseLoggerProducer.getLogger(GenerateMessageKey.class);

    /** The fmt level message. */
    static Template FMT_LEVEL_MESSAGE;

    /** The fmt message properties. */
    static Template FMT_MESSAGE_PROPERTIES;

    /** The fmt message code. */
    static Template FMT_MESSAGE_CODE;

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws TemplateException the template exception
     */
    public static void main(final String[] args) throws TemplateException {
        final String messageKeysPropertiesFilePath = "./src/main/resources/arrow/i18n/msg-keys.properties";
        final String targetPackage = "arrow.framework.faces.message";
        final String targetClassDirectory = "./src/main/java/arrow/framework/faces/message/";
        final String targetPropertiesDirectory = "./src/main/resources/arrow/i18n/";

        final Configuration cfg = new Configuration();

        // ignore locale
        cfg.setSetting(Configuration.LOCALIZED_LOOKUP_KEY, "NO");
        try {
            GenerateMessageKey.FMT_LEVEL_MESSAGE =
                    cfg.getTemplate("./src/main/resources/arrow/framework/LevelMessages.ftl");
            GenerateMessageKey.FMT_MESSAGE_PROPERTIES =
                    cfg.getTemplate("./src/main/resources/arrow/framework/error-messages.ftl");
            GenerateMessageKey.FMT_MESSAGE_CODE =
                    cfg.getTemplate("./src/main/resources/arrow/framework/MessageCodeDotJava.ftl");
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        final Properties loadedProps = new Properties();
        try {
            loadedProps.load(new FileInputStream(messageKeysPropertiesFilePath));
            final Map<String, Object> model = new HashMap<String, Object>();
            model.put(MessageModel.TARGET_PACKAGE_KEY, targetPackage);
            final List<MessageModel> listMsg = new ArrayList<>();
            final List<MessageModel> listErrorMsgs = new ArrayList<>();
            final List<MessageModel> listInfoMsgs = new ArrayList<>();
            final List<MessageModel> listWarnMsgs = new ArrayList<>();
            for (final Entry<Object, Object> msg : loadedProps.entrySet()) {
                final MessageModel msgModel = new MessageModel();
                final String key = (String) msg.getKey();
                final String value = (String) msg.getValue();
                msgModel.parse(key, value);
                listMsg.add(msgModel);

                if (key.toUpperCase().startsWith(MessageModel.ERROR_LEVEL_KEY)) {
                    listErrorMsgs.add(msgModel);
                }
                else if (key.toUpperCase().startsWith(MessageModel.INFO_LEVEL_KEY)) {
                    listInfoMsgs.add(msgModel);
                }
                else {
                    listWarnMsgs.add(msgModel);
                }
            }
            Collections.sort(listMsg, new Comparator<MessageModel>() {

                @Override
                public int compare(final MessageModel o1, final MessageModel o2) {
                    return o1.get(MessageModel.KEY_KEY).toString()
                            .compareToIgnoreCase(o2.get(MessageModel.KEY_KEY).toString());
                }

            });
            model.put(MessageModel.MESSAGES_KEY, listMsg);

            try {
                GenerateMessageKey.writeToFile(GenerateMessageKey.FMT_MESSAGE_PROPERTIES, model,
                        targetPropertiesDirectory + "error-messages.properties");
                GenerateMessageKey.writeToFile(GenerateMessageKey.FMT_MESSAGE_CODE, model,
                        targetClassDirectory + "MessageCode.java");

                model.put(MessageModel.MESSAGES_KEY, listErrorMsgs);
                model.put(MessageModel.SEVERITY_KEY, "Error");
                GenerateMessageKey.writeToFile(GenerateMessageKey.FMT_LEVEL_MESSAGE, model,
                        targetClassDirectory + "ErrorMessage.java");
                model.put(MessageModel.MESSAGES_KEY, listInfoMsgs);
                model.put(MessageModel.SEVERITY_KEY, "Info");
                GenerateMessageKey.writeToFile(GenerateMessageKey.FMT_LEVEL_MESSAGE, model,
                        targetClassDirectory + "InfoMessage.java");
                model.put(MessageModel.MESSAGES_KEY, listWarnMsgs);
                model.put(MessageModel.SEVERITY_KEY, "Warn");
                GenerateMessageKey.writeToFile(GenerateMessageKey.FMT_LEVEL_MESSAGE, model,
                        targetClassDirectory + "WarnMessage.java");

            } catch (final TemplateException e) {
                GenerateMessageKey.LOGGER.info("Template error", e);
            }

        } catch (final IOException e) {
            GenerateMessageKey.LOGGER.info("Cannot read file", e);
        }
    }

    /**
     * Write to file.
     *
     * @param template the template
     * @param model the model
     * @param filePath the file path
     * @throws TemplateException the template exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static void writeToFile(final Template template, final Map<String, Object> model, final String filePath)
            throws TemplateException, IOException {
        final StringWriter sw = new StringWriter();
        template.process(model, sw);
        StringUtils.writeFile(sw.toString(), new File(filePath));
        System.out.println(sw.toString());
        sw.close();
    }

    /**
     * The Class MessageModel.
     */
    public static class MessageModel extends HashMap<String, Object> {

        /** The Constant MESSAGES_KEY. */
        public static final String MESSAGES_KEY = "messages";

        /** The Constant ERROR_LEVEL_KEY. */
        public static final String ERROR_LEVEL_KEY = "ERROR";

        /** The Constant INFO_LEVEL_KEY. */
        public static final String INFO_LEVEL_KEY = "INFO";

        /** The Constant WARN_LEVEL_KEY. */
        public static final String WARN_LEVEL_KEY = "WARN";

        /** The Constant TARGET_PACKAGE_KEY. */
        public static final String TARGET_PACKAGE_KEY = "targetPackage";

        /** The Constant SEVERITY_KEY. */
        public static final String SEVERITY_KEY = "level";

        /** The Constant CODE_KEY. */
        public static final String CODE_KEY = "code";

        /** The Constant KEY_KEY. */
        public static final String KEY_KEY = "key";

        /** The Constant VALUE_KEY. */
        public static final String VALUE_KEY = "value";

        /** The Constant GETTER_KEY. */
        public static final String GETTER_KEY = "getter";

        /**
         * Parses the.
         *
         * @param key the key
         * @param value the value
         */
        public void parse(final String key, final String value) {
            final String[] splitted = key.split("\\.");
            final String level = splitted[0];
            final String code = splitted[1];

            this.put(MessageModel.SEVERITY_KEY, org.apache.commons.lang3.StringUtils.capitalize(level));
            if (level.toUpperCase().equals(MessageModel.WARN_LEVEL_KEY)) {
                this.put("levelName", "WARNING");
            }
            this.put(MessageModel.CODE_KEY, code);
            this.put(MessageModel.KEY_KEY, key);
            this.put(MessageModel.VALUE_KEY, value);
            this.put(MessageModel.GETTER_KEY, code + "_" + splitted[2]);

        }
    }
}
