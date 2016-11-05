package arrow.framework.persistence.locator;

import java.io.Serializable;


/**
 * The Class BaseEmLookup.
 */
public abstract class BaseEmLookup implements Serializable {

    /**
     * Gets the persistence unit name.
     *
     * @return the persistence unit name
     */

    public abstract String getPersistenceUnitName();
}
