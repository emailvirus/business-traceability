package arrow.businesstraceability.search;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Root;

import arrow.businesstraceability.persistence.entity.Abstract_entity;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.mapped.Company_mst_MAPPED_;
import arrow.framework.util.i18n.Messages;


/**
 * A factory for creating CommonSearchService objects.
 */
public class CommonSearchServiceFactory extends SearchServiceFactory {

    /** The Constant COMPANY_SEARCH_SERVICE. */
    public static final String COMPANY_SEARCH_SERVICE = "companySearch";

    /* (non-Javadoc)
     * @see arrow.businesstraceability.search.SearchServiceFactory<br />
     * #create(javax.persistence.EntityManager, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected SearchService create(final EntityManager em, final String searchServiceCode) {
        // Predicate additionalCondition = null;
        // Map<String, Object> additionalParameters = null;
        // final ArrayList<SearchParameter> parameters = new ArrayList<SearchParameter>();
        // ArrayList<SearchParameter> hiddenParameters = new ArrayList<SearchParameter>();
        // ArrayList<SearchParameter> displayParameters = new ArrayList<SearchParameter>();
        // Order orderBy = null;
        final String title = Messages.get("search-title." + searchServiceCode);
        final SearchService searchService = new SearchService(em, title);

        if (CommonSearchServiceFactory.COMPANY_SEARCH_SERVICE.equalsIgnoreCase(searchServiceCode)) {
            searchService.initSearchQuery();
            final Root<? extends Abstract_entity> defaultRoot =
                    searchService.getSearchCriteriaQuery().from(Company_mst.class);
            searchService.getSearchCriteriaQuery().select(defaultRoot);
            searchService.addSearchParameter(SearchParameter.createSearchParameter(defaultRoot,
                    Company_mst_MAPPED_.com_company_code));
            searchService.addSearchParameter(SearchParameter.createSearchParameter(defaultRoot,
                    Company_mst_MAPPED_.com_point_code));
            // orderBy =
            // searchService.getSearchCriteriaQuery().orderBy(searchService.getSearchCriteriaQuery()
        }

        // if (displayParameters.isEmpty()) {
        // displayParameters = parameters;
        // }
        return searchService;
    }
}
