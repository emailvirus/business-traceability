package arrow.businesstraceability.search;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;
import arrow.framework.persistence.meta.util.MetaModelUtils;
import arrow.framework.util.collections.CollectionUtils;

/**
 * A factory for creating SearchService objects.
 */
public abstract class SearchServiceFactory {

    private static final BaseLogger LOGGER = BaseLoggerProducer.getLogger();

    /** The Constant BUSINESS_ENTITY_PACKAGE_PREFIX. */
    private static final String BUSINESS_ENTITY_PACKAGE_PREFIX = "arrow.businesstraceability.persistence.entity.";

    /** The factories. */
    private static List<SearchServiceFactory> factories = new ArrayList<SearchServiceFactory>();

    static {
        SearchServiceFactory.factories.add(new CommonSearchServiceFactory());
    }

    /**
     * Creates the.
     *
     * @param em the em
     * @param type the type
     * @return the search service
     */
    protected abstract SearchService create(EntityManager em, String type);

    /**
     * Creates a new SearchService object.
     *
     * @param em the em
     * @param entityClass the entity class
     * @return the search service
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static SearchService createSearchService(final EntityManager em, final Class<?> entityClass) {
        final SearchService newSearchService = new SearchService(em, entityClass);
        final Root defaultRoot = newSearchService.getSearchCriteriaQuery().from(entityClass);
        final List<SingularAttribute> fundamentalAttributes =
                MetaModelUtils.getFundamentalSingulareAttributesByOrder(em, entityClass);
        if (CollectionUtils.isEmpty(fundamentalAttributes)) {
            throw new IllegalArgumentException(
                    "You must setup fundamental attribute to initialize search service for class "
                            + entityClass.getName());
        }

        for (final SingularAttribute attribute : fundamentalAttributes) {
            newSearchService.addSearchParameter(
                    SearchParameter.createSearchParameter(newSearchService, defaultRoot, attribute));
        }

        return newSearchService;
    }

    /**
     * Creates a new SearchService object.
     *
     * @param em the em
     * @param searchServiceType the search service type
     * @return the search service
     */
    public static SearchService createSearchService(final EntityManager em, final String searchServiceType) {
        // try to get simple search first
        final String entityClassName = SearchServiceFactory.BUSINESS_ENTITY_PACKAGE_PREFIX + searchServiceType;

        try {
            final Class<?> entityClass = Class.forName(entityClassName);

            return SearchServiceFactory.createSearchService(em, entityClass);
        } catch (final ClassNotFoundException e) {
            System.out.println(e);
            for (final SearchServiceFactory factory : SearchServiceFactory.factories) {
                try {
                    final SearchService searchService = factory.create(em, searchServiceType);
                    if (searchService != null) {
                        return searchService;
                    }
                } catch (final IllegalArgumentException e2) {
                    SearchServiceFactory.LOGGER.debug("Cannot create Search Service", e2);
                }
            }
        }
        return null;
    }
}
