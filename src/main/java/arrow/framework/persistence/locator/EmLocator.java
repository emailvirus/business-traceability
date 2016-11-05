package arrow.framework.persistence.locator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import org.hibernate.jpa.internal.EntityManagerFactoryRegistry;

import arrow.framework.bootstrap.Resources;
import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;
import arrow.framework.util.cdi.Instance;

/**
 * The Class EmLocator.
 */
@Named
public class EmLocator implements Serializable {

    /** The Constant LOG. */
    private static final BaseLogger LOG = BaseLoggerProducer.getLogger();

    /** The main emf map. */
    private static Map<String, EntityManagerFactory> mainEmfMap = new HashMap<String, EntityManagerFactory>();

    /** The main emf. */
    private static EntityManagerFactory mainEmf;

    /**
     * Gets the em.
     *
     * @return the em
     */
    public static EntityManager getEm() {
        return Instance.get(Resources.class).getEmMain();
    }

    /**
     * Gets the criteria builder.
     *
     * @return the criteria builder
     */
    public static CriteriaBuilder getCriteriaBuilder() {
        return EmLocator.getMainEmf(Instance.get(Resources.class).getPuName()).getCriteriaBuilder();
    }

    /**
     * Gets the main emf.
     *
     * @param puName the pu name
     * @return the main emf
     */
    @SuppressWarnings(value = "LI_LAZY_INIT_STATIC", justification = "Init mainemf of framework!")
    public static EntityManagerFactory getMainEmf(final String puName) {

        if (EmLocator.mainEmf == null) {
            EmLocator.mainEmf = Persistence.createEntityManagerFactory(puName);
            EmLocator.LOG.debug("EMF created for pu " + puName);
        }

        return EmLocator.mainEmf;
    }

    /**
     * Gets the criteria builder.
     *
     * @param puName the pu name
     * @return the criteria builder
     */
    public static CriteriaBuilder getCriteriaBuilder(final String puName) {
        return EmLocator.getMainEmf(puName).getCriteriaBuilder();
    }

    /**
     * Reset emf.
     */
    // to reset the EMFs in exceptional cases
    public void resetEmf() {
        for (final String companyCode : EmLocator.mainEmfMap.keySet()) {
            EntityManagerFactoryRegistry.INSTANCE.removeEntityManagerFactory(companyCode,
                    EmLocator.mainEmfMap.get(companyCode));
        }

        EmLocator.mainEmfMap.clear();
    }
}
