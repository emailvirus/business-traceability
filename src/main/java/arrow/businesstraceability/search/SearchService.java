package arrow.businesstraceability.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

import arrow.framework.util.collections.CollectionUtils;


/**
 * The Class SearchService.
 */
@SuppressWarnings("rawtypes")
public class SearchService implements Serializable {

    /** The em. */
    private EntityManager em;

    /** The criteria builder. */
    private CriteriaBuilder criteriaBuilder;

    /** The search class. */
    private Class<?> searchClass;

    /** The search criteria query. */
    private CriteriaQuery searchCriteriaQuery;

    /**
     * Instantiates a new search service.
     */
    public SearchService() {

    }

    /**
     * Instantiates a new search service.
     *
     * @param em the em
     * @param title the title
     */
    public SearchService(final EntityManager em, final String title) {
        this.em = em;
        this.criteriaBuilder = em.getCriteriaBuilder();
        this.title = title;
    }

    /**
     * Instantiates a new search service.
     *
     * @param em the em
     * @param searchClass the search class
     */
    public SearchService(final EntityManager em, final Class<?> searchClass) {
        if (searchClass == null) {
            throw new IllegalArgumentException("searchClass must not be null");
        }
        this.em = em;
        this.criteriaBuilder = em.getCriteriaBuilder();

        this.searchClass = searchClass;
        this.initSearchQuery();
    }

    /**
     * Inits the search query.
     */
    public void initSearchQuery() {
        this.searchCriteriaQuery = this.criteriaBuilder.createQuery(this.searchClass);
    }

    /**
     * Gets the search criteria query.
     *
     * @return the search criteria query
     */
    public CriteriaQuery getSearchCriteriaQuery() {
        return this.searchCriteriaQuery;
    }

    /**
     * Adds the search parameter.
     *
     * @param searchParam the search param
     */
    public void addSearchParameter(final SearchParameter searchParam) {
        this.searchParameters.add(searchParam);
        if (!this.displayParameters.contains(searchParam)) {
            this.displayParameters.add(searchParam);
        }
    }

    /**
     * Instantiates a new search service.
     *
     * @param title the title
     * @param selectClause the select clause
     * @param searchParameters the search parameters
     * @param displayParameters the display parameters
     * @param hiddenParameters the hidden parameters
     * @param ctrlDB the ctrl db
     * @param additionalCondition the additional condition
     * @param additionalParameters the additional parameters
     * @param orderByClause the order by clause
     */
    public SearchService(final String title, final String selectClause,
            final ArrayList<SearchParameter> searchParameters, final ArrayList<SearchParameter> displayParameters,
            final ArrayList<SearchParameter> hiddenParameters, final boolean ctrlDB,
            final Predicate additionalCondition, final Map<String, Object> additionalParameters,
            final Order orderByClause) {
        if (selectClause == null) {
            throw new IllegalArgumentException("selectClause must not be NULL");
        }
        this.title = title;
        this.selectClause = selectClause;
        this.searchParameters = searchParameters;
        this.displayParameters = displayParameters;
        this.hiddenParameters = hiddenParameters;
        this.additionalCondition = additionalCondition;
        this.additionalParameters = additionalParameters;

        if (this.getSearchResults() != null) {
            this.searchResults.clear();
        }
    }

    /**
     * Instantiates a new search service.
     *
     * @param title the title
     * @param selectClause the select clause
     * @param parameters the parameters
     * @param displayParameters the display parameters
     * @param hiddenParameters the hidden parameters
     * @param ctrlDB the ctrl db
     * @param additionalCondition the additional condition
     * @param additionalParameters the additional parameters
     * @param orderByClause the order by clause
     * @param groupByClause the group by clause
     */
    public SearchService(final String title, final String selectClause, final ArrayList<SearchParameter> parameters,
            final ArrayList<SearchParameter> displayParameters, final ArrayList<SearchParameter> hiddenParameters,
            final boolean ctrlDB, final Predicate additionalCondition, final Map<String, Object> additionalParameters,
            final Order orderByClause, final String groupByClause) {
        this(title, selectClause, parameters, displayParameters, hiddenParameters, ctrlDB, additionalCondition,
                additionalParameters, orderByClause);
    }

    /** The select clause. */
    private String selectClause;

    /**
     * Gets the select clause.
     *
     * @return the select clause
     */
    protected String getSelectClause() {
        return this.selectClause;
    }

    /** The orders. */
    private final List<Order> orders = new ArrayList<>();

    /**
     * Gets the orders.
     *
     * @return the orders
     */
    public List<Order> getOrders() {
        return this.orders;
    }

    /**
     * Adds the order.
     *
     * @param order the order
     */
    public void addOrder(final Order order) {
        this.orders.add(order);
    }

    /** The additional condition. */
    private Predicate additionalCondition;

    /**
     * Gets the additional condition.
     *
     * @return the additional condition
     */
    protected Predicate getAdditionalCondition() {
        return this.additionalCondition;
    }

    /**
     * Sets the additional condition.
     *
     * @param additionalCondition the new additional condition
     */
    public void setAdditionalCondition(final Predicate additionalCondition) {
        this.additionalCondition = additionalCondition;
    }

    /** The additional parameters. */
    private Map<String, Object> additionalParameters;

    /** The result set class. */
    private Class<? extends Serializable> resultSetClass;

    /** The obj param. */
    private String[] objParam;

    /** The title. */
    private String title;

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /** The search parameters. */
    private List<SearchParameter> searchParameters = new ArrayList<>();

    /**
     * Gets the search parameters.
     *
     * @return the search parameters
     */
    public List<SearchParameter> getSearchParameters() {
        return this.searchParameters;
    }

    /** The hidden parameters. */
    private ArrayList<SearchParameter> hiddenParameters;

    /**
     * Gets the hidden parameters.
     *
     * @return the hidden parameters
     */
    public ArrayList<SearchParameter> getHiddenParameters() {
        return this.hiddenParameters;
    }

    // private Predicate<? extends Serializable> filter;
    //
    // public void setFilter(final Predicate<? extends Serializable> filter) {
    // this.filter = filter;
    // }

    /**
     * Use in the case there is just one hidden parameter.
     *
     * @return the parameter0
     */
    public SearchParameter getParameter0() {
        return this.getHiddenParameters().get(0);
    }

    /** The display parameters. */
    private ArrayList<SearchParameter> displayParameters = new ArrayList<>();

    /**
     * Gets the display parameters.
     *
     * @return the display parameters
     */
    public ArrayList<SearchParameter> getDisplayParameters() {
        return this.displayParameters;
    }

    /** The search results. */
    private List<? extends Serializable> searchResults;

    /**
     * Gets the search results.
     *
     * @return the search results
     */
    public List<? extends Serializable> getSearchResults() {
        return this.searchResults;
    }

    /**
     * Gets the result set class.
     *
     * @return the result set class
     */
    public Class<? extends Serializable> getResultSetClass() {
        return this.resultSetClass;
    }

    /**
     * Sets the result set class.
     *
     * @param resultSetClass the new result set class
     */
    public void setResultSetClass(final Class<? extends Serializable> resultSetClass) {
        this.resultSetClass = resultSetClass;
    }

    /**
     * Gets the obj param.
     *
     * @return the obj param
     */
    public String[] getObjParam() {
        return this.objParam;
    }

    /**
     * Sets the obj param.
     *
     * @param objParam the new obj param
     */
    public void setObjParam(final String[] objParam) {
        this.objParam = objParam;
    }

    /** The max results. */
    private int maxResults = 500;

    /**
     * Gets the max results.
     *
     * @return the max results
     */
    public int getMaxResults() {
        return this.maxResults;
    }

    /**
     * Sets the max results.
     *
     * @param maxResults the new max results
     */
    public void setMaxResults(final int maxResults) {
        this.maxResults = maxResults;
    }

    /** The match all. */
    private boolean matchAll = false;

    /**
     * Checks if is match all.
     *
     * @return true, if is match all
     */
    public boolean isMatchAll() {
        return this.matchAll;
    }

    /**
     * Sets the match all.
     *
     * @param matchAll the new match all
     */
    public void setMatchAll(final boolean matchAll) {
        this.matchAll = matchAll;
    }

    /**
     * Search.
     */
    public void search() {
        this.search(this.searchParameters);
    }

    /**
     * Search all.
     */
    public void searchAll() {
        this.search(new ArrayList<SearchParameter>());
    }

    /**
     * Search.
     *
     * @param param the param
     */
    public void search(final SearchParameter param) {
        for (final SearchParameter p : this.searchParameters) {
            if (p != param) {
                p.setValue(null);
            }
        }

        final ArrayList<SearchParameter> params = new ArrayList<SearchParameter>();
        params.add(param);
        if (this.resultSetClass == null) {
            this.search(params);
        }
        else {
            this.searchByNativeSql(params);
        }
    }

    /**
     * Search.
     *
     * @param params the params
     */
    @SuppressWarnings("unchecked")
    private void search(final List<SearchParameter> params) {
        Predicate finalPredicate = this.buildSearchPredicate(params);

        if (CollectionUtils.isNotEmpty(this.hiddenParameters)) {
            for (final SearchParameter param : this.hiddenParameters) {
                if (param.isUsed()) {
                    finalPredicate = this.criteriaBuilder.and(finalPredicate, param.getExtractPredicate());
                }
            }
        }

        if (this.getAdditionalCondition() != null) {
            finalPredicate = this.criteriaBuilder.and(finalPredicate, this.getAdditionalCondition());
        }

        this.searchCriteriaQuery.where(finalPredicate);

        this.setupQueryOrder(params);

        final Query query = this.em.createQuery(this.searchCriteriaQuery);

        if (CollectionUtils.isNotEmpty(params)) {
            for (final SearchParameter param : params) {
                if (param.isUsed()) {
                    // TODO: Support search by date
                    query.setParameter(param.getName(), param.getConvertedValue());
                }
            }
        }

        if (this.hiddenParameters != null) {
            for (final SearchParameter param : this.hiddenParameters) {
                if (param.isUsed() && (param.getName() != null)) {
                    // TODO: Support search by date
                    query.setParameter(param.getName(), param.getConvertedValue());
                }
            }
        }
        if (this.additionalParameters != null) {
            for (final String name : this.additionalParameters.keySet()) {
                query.setParameter(name, this.additionalParameters.get(name));
            }
        }

        query.setMaxResults(this.maxResults);
        this.searchResults = query.getResultList();
    }

    /**
     * Builds the search predicate.
     *
     * @param params the params
     * @return the predicate
     */
    private Predicate buildSearchPredicate(final List<SearchParameter> params) {
        Predicate searchPredicate = null;
        if (CollectionUtils.isNotEmpty(params)) {
            for (final SearchParameter param : params) {
                if (param.isUsed()) {
                    if (searchPredicate == null) {
                        searchPredicate = param.getExtractPredicate();
                    }
                    else if (this.isMatchAll()) {
                        searchPredicate = this.criteriaBuilder.and(searchPredicate, param.getExtractPredicate());
                    }
                    else {
                        searchPredicate = this.criteriaBuilder.or(searchPredicate, param.getExtractPredicate());
                    }
                }
            }
        }
        if (searchPredicate == null) {
            searchPredicate = this.criteriaBuilder.conjunction();
        }

        return searchPredicate;
    }

    /**
     * Sets the up query order.
     *
     * @param params the new up query order
     */
    @SuppressWarnings("unchecked")
    private void setupQueryOrder(final List<SearchParameter> params) {
        if (CollectionUtils.isNotEmpty(this.orders)) {
            this.searchCriteriaQuery.orderBy(this.orders);
        }
        else {
            final List<Order> listOfDefaultOrders = new ArrayList<>();
            for (final SearchParameter param : params) {
                if (param.isUsed()) {
                    listOfDefaultOrders.add(param.getDefaultOrder());
                }
            }
            this.searchCriteriaQuery.orderBy(listOfDefaultOrders);
        }
    }

    /**
     * Search by native sql.
     *
     * @param params the params
     */
    private void searchByNativeSql(final List<SearchParameter> params) {
        // final CriteriaBuilder criteriaBuilder = Resources.getCriteriaBuilder();
        // final CriteriaQuery<?> criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
        // final Root<?> root = criteriaQuery.from(this.entityClass);
        // final Predicate finalPredicate = criteriaBuilder.conjunction();
        // for (final Parameter param : params) {
        // if (param.isUsed()) {
        // criteriaBuilder.
        // }
        // }
        //
        // allPredicates[0] = criteriaBuilder.equal(root.get(this.currentField), convertedInput);
        // for (int i = 0; i < otherFieldValues.size(); i++) {
        // final FieldValuePair valuePair = otherFieldValues.get(i);
        // allPredicates[i + 1] = criteriaBuilder.equal(root.get(valuePair.fieldAttribute),
        // valuePair.value);
        // }
        // criteriaQuery.where(criteriaBuilder.and(allPredicates));
        //
        // final StringBuffer whereClause = new StringBuffer("");
        // // the selectClause query must have "where 1=1 ";
        // int paramPost = 1;
        // String basedPos = "";
        // for (final Parameter param : params) {
        // if (param.isUsed()) {
        // basedPos = " ?" + paramPost;
        //
        // if (param.getType() == Parameter.Type.STRING && !param.isExact() &&
        // param.getClause().toUpperCase().contains(" LIKE ")) {
        // basedPos = basedPos + " escape '\\\\' ";
        // }
        //
        // paramPost++;
        // whereClause.append(this.matchAll ? " AND " + param.getClause() + basedPos : " OR " +
        // param.getClause() + basedPos);
        // }
        //
        // }
        //
        // if (this.hiddenParameters != null) {
        // for (final Parameter param : this.hiddenParameters) {
        // if (param.getName() != null) {
        // basedPos = " ?" + paramPost;
        // basedPos += param.isHasPreviousBucket() ? ")" : "";
        // paramPost++;
        // whereClause.append(" AND " + param.getClause() + basedPos);
        // }
        //
        // }
        // }
        //
        // if (this.getAdditionalCondition() != null) {
        // whereClause.append(" AND ( " + this.getAdditionalCondition() + " ) ");
        // }
        //
        // if (this.orderByClause == null) {
        // this.orderByClause = "";
        // }
        //
        // final String rowsLimitClause = " FETCH FIRST " + this.maxResults + " ROWS ONLY";
        // final String queryString = this.getSelectClause() + whereClause + this.orderByClause +
        // rowsLimitClause;
        // final Query query = this.em.createNativeQuery(queryString);
        //
        // paramPost = 1;
        // for (final Parameter param : params) {
        // if (param.isUsed()) {
        // query.setParameter(paramPost++, param.getConvertedValue());
        // }
        // }
        // if (this.hiddenParameters != null) {
        // for (final Parameter param : this.hiddenParameters) {
        // if (param.getName() != null) {
        // query.setParameter(paramPost++, param.getConvertedValue());
        // }
        // }
        // }
        // if (this.additionalParameters != null) {
        // for (final String name : this.additionalParameters.keySet()) {
        // query.setParameter(name, this.additionalParameters.get(name));
        // }
        // }
        //
        // query.setMaxResults(this.maxResults);
        // final List<Object> list = query.getResultList();
        // final List<Object> resultList = new ArrayList();
        // if (this.objParam != null && !list.isEmpty()) {
        // try {
        // Object _classObj;
        // for (final Object obj : list) {
        // final Object[] resObj = obj instanceof String ? (new Object[] { obj }) : (Object[]) obj;
        // _classObj = this.resultSetClass.newInstance();
        // for (int i = 0; i < this.objParam.length; i++) {
        // try {
        // final Field field = this.resultSetClass.getDeclaredField(this.objParam[i]);
        //
        // if (field != null) {
        // field.setAccessible(true);
        //
        // field.set(_classObj, resObj[i] == null ? "" : resObj[i].toString());
        // }
        // }
        // catch (final SecurityException e) {
        // }
        // catch (final NoSuchFieldException e) {
        // }
        // catch (final IllegalArgumentException e) {
        // try {
        // final String methodName = Character.toUpperCase(this.objParam[i].charAt(0)) +
        // this.objParam[i].substring(1, this.objParam[i].length()).toLowerCase();
        // final Method method = this.resultSetClass.getDeclaredMethod("set" + methodName,
        // resObj[i].getClass());
        // if (method != null) {
        // method.setAccessible(true);
        // method.invoke(_classObj, resObj[i] == null ? "" : resObj[i]);
        // }
        // }
        // catch (final NoSuchMethodException nsme) {
        // }
        // catch (final InvocationTargetException ite) {
        // }
        // }
        // }
        //
        // resultList.add(_classObj);
        // }
        // }
        // catch (final IllegalAccessException e) {/* Ignore */
        // }
        // catch (final InstantiationException e) {/* Ignore */
        // }
        // }
        // this.searchResults = (List<? extends Serializable>) resultList;

    }

    /**
     * Gets the additional parameters.
     *
     * @return the additional parameters
     */
    public Map<String, Object> getAdditionalParameters() {
        return this.additionalParameters;
    }

    /**
     * Sets the additional parameters.
     *
     * @param additionalParameters the additional parameters
     */
    public void setAdditionalParameters(final Map<String, Object> additionalParameters) {
        this.additionalParameters = additionalParameters;
    }

}
