package arrow.framework.bootstrap;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import org.apache.deltaspike.core.api.config.ConfigProperty;

import arrow.framework.util.cdi.Instance;


/**
 * This class uses CDI to alias Java EE resources, such as the persistence context, to CDI beans.
 */
@ApplicationScoped
public class Resources implements Serializable {


    /** The em main. */
    @PersistenceContext
    private EntityManager emMain;


    /** The pu name. */
    @Inject
    @ConfigProperty(name = "pu.name")
    private String puName;

    /**
     * Gets the em.
     *
     * @return the em
     */
    public static EntityManager getEm() {
        return Instance.get(Resources.class).getEmMain();
    }

    /**
     * Gets the em main.
     *
     * @return the em main
     */
    public EntityManager getEmMain() {
        return this.emMain;
    }

    /**
     * Gets the pu name.
     *
     * @return the pu name
     */
    public String getPuName() {
        return this.puName;
    }

    /**
     * Gets the criteria builder for current main db.
     *
     * @return the criteria builder for current main db
     */
    @Produces
    public CriteriaBuilder getCriteriaBuilderForCurrentMainDB() {
        return this.emMain.getEntityManagerFactory().getCriteriaBuilder();
    }

    /**
     * Produces em main.
     *
     * @return the entity manager
     */
    @Produces
    @ConversationScoped
    EntityManager producesEmMain() {
        return this.emMain;
    }

    /**
     * Produce faces context.
     *
     * @return the faces context
     */
    @Produces
    @RequestScoped
    FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
