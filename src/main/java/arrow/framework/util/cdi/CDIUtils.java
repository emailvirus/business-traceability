package arrow.framework.util.cdi;

import java.lang.annotation.Annotation;

import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.transaction.TransactionScoped;


/**
 * The Class CDIUtils.
 */
public abstract class CDIUtils {

    /**
     * Gets the bean manager.
     *
     * @return the bean manager
     */
    public static BeanManager getBeanManager() {
        return CDI.current().getBeanManager();
    }

    /**
     * Check context is active.
     *
     * @param scopeClass the scope class
     * @return true, if is context active
     */
    public static boolean isContextActive(final Class<? extends Annotation> scopeClass) {
        try {
            return CDIUtils.getBeanManager().getContext(scopeClass).isActive();
        } catch (final ContextNotActiveException e) {
            return false;
        }
    }

    /**
     * Checks if is conversation context active.
     *
     * @return true, if is conversation context active
     */
    public static boolean isConversationContextActive() {
        return CDIUtils.isContextActive(ConversationScoped.class);
    }

    /**
     * Checks if is transaction context active.
     *
     * @return true, if is transaction context active
     */
    public static boolean isTransactionContextActive() {
        return CDIUtils.isContextActive(TransactionScoped.class);
    }

}
