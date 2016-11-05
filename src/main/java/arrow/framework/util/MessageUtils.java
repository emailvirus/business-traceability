package arrow.framework.util;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import arrow.framework.faces.message.Message;

/**
 * The Class MessageUtils.
 */
public class MessageUtils {

    /**
     * Show messages.
     *
     * @param messages the messages
     */
    public static void showMessages(final List<Message> messages) {
        if (CollectionUtils.isNotEmpty(messages)) {
            for (final Message message : messages) {
                message.show();
            }
        }
    }
}
