/*
 * package: arrow.framework.persistence.provider.hibernate file: ThreadLocalCurrentTenantIdentifierResolver.java time:
 * Jul 3, 2014
 * @author michael
 */
package arrow.framework.persistence.provider.hibernate;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;


/**
 * The Class ThreadLocalCurrentTenantIdentifierResolver.
 */
public class ThreadLocalCurrentTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    /** The tenant id. */
    private static ThreadLocal<String> tenantId = new ThreadLocal<String>();

    /**
     * Sets the.
     *
     * @param code the code
     */
    public static void set(final String code) {
        ThreadLocalCurrentTenantIdentifierResolver.tenantId.set(code);
    }

    /**
     * Gets the.
     *
     * @return the string
     */
    public static String get() {
        return ThreadLocalCurrentTenantIdentifierResolver.tenantId.get();
    }

    /**
     * Reset.
     */
    public static void reset() {
        ThreadLocalCurrentTenantIdentifierResolver.tenantId.remove();
    }


    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.context.spi.CurrentTenantIdentifierResolver#resolveCurrentTenantIdentifier()
     */
    @Override
    public String resolveCurrentTenantIdentifier() {
        return ThreadLocalCurrentTenantIdentifierResolver.get();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.hibernate.context.spi.CurrentTenantIdentifierResolver#validateExistingCurrentSessions()
     */
    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
