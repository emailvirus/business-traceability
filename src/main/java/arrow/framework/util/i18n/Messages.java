package arrow.framework.util.i18n;


import java.io.Serializable;
import java.text.MessageFormat;
import java.util.AbstractMap;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;


/**
 * The Class Messages.
 */
public class Messages implements Serializable {
    private static final BaseLogger LOGGER = BaseLoggerProducer.getLogger(Messages.class);

    /**
     * Gets the.
     *
     * @param msgKey the msg key
     * @param params the params
     * @return the string
     */


    public static String get(final String msgKey, final Object... params) {
        try {
            final String message = BaseResourceBundle.getBundle().getString(msgKey);
            return MessageFormat.format(message, params);
        } catch (final MissingResourceException mre) {
            Messages.LOGGER.debug("Failed when try to get message key:" + msgKey, mre);
            return msgKey;
        }
    }

    /**
     * Produce message map.
     *
     * @return the map
     */
    @Produces
    @Named("messages")
    @RequestScoped
    Map<String, String> produceMessageMap() {
        final java.util.ResourceBundle bundle = BaseResourceBundle.getBundle();

        return new AbstractMap<String, String>() {
            @Override
            public String get(final Object key) {
                if (key instanceof String) {
                    final String resourceKey = (String) key;
                    try {
                        return bundle.getString(resourceKey);
                    } catch (final MissingResourceException mre) {
                        Messages.LOGGER.debug("Failed when try to get message key:" + resourceKey, mre);
                        return resourceKey;
                    }
                }
                else {
                    return null;
                }
            }

            @Override
            public Set<Map.Entry<String, String>> entrySet() {
                final Set<Map.Entry<String, String>> entrySet = new HashSet<Map.Entry<String, String>>();

                final Enumeration<String> keys = bundle.getKeys();

                while (keys.hasMoreElements()) {
                    final String key = keys.nextElement();

                    entrySet.add(new Map.Entry<String, String>() {
                        @Override
                        public String getKey() {
                            return key;
                        }

                        @Override
                        public String getValue() {
                            return get(key);
                        }

                        @Override
                        public String setValue(final String arg0) {
                            throw new UnsupportedOperationException();
                        }
                    });
                }

                return entrySet;
            }
        };
    }


    /**
     * The Class Message.
     */
    public static class Message {

        /** The key. */
        private final String key;

        /** The params. */
        private final Object[] params;

        /**
         * Instantiates a new message.
         *
         * @param key the key
         * @param params the params
         */
        public Message(final String key, final Object... params) {
            this.key = key;
            this.params = params;
        }

        /**
         * Gets the key.
         *
         * @return the key
         */
        public String getKey() {
            return this.key;
        }

        /**
         * Gets the params.
         *
         * @return the params
         */
        public Object[] getParams() {
            return this.params;
        }
    }
}
