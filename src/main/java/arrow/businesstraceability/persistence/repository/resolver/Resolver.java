package arrow.businesstraceability.persistence.repository.resolver;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.deltaspike.data.api.EntityManagerResolver;

@ApplicationScoped
public class Resolver implements EntityManagerResolver {
    @PersistenceContext(unitName = "business_traceability")
    private EntityManager em;

    @Override
    public EntityManager resolveEntityManager() {
        return this.em;
    }
}
