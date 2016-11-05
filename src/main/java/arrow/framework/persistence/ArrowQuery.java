package arrow.framework.persistence;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import arrow.framework.faces.message.ErrorMessage;
import arrow.framework.persistence.util.Condition;
import arrow.framework.util.DateUtils;
import arrow.framework.util.StringUtils;
import arrow.framework.util.collections.SelectableList;

/**
 * The Class ArrowQuery.
 *
 * @param <E> the element type
 */
public class ArrowQuery<E> {

    /** The Constant DEFAULT_MAX_RESULTS. */
    public static final int DEFAULT_MAX_RESULTS = 2000;

    /** The managed. */
    private final boolean managed;

    /** The result list. */
    private final ResultList<E> resultList = new ResultList<E>(this);

    /** The where condition. */
    private final Condition.Conjunction whereCondition = new Condition.Conjunction();

    /** The filters. */
    private final Map<String, String> filters = new HashMap<String, String>();

    /** The sorters. */
    private final Map<String, String> sorters = new HashMap<String, String>();

    /** The type. */
    private Type type;

    /** The query string. */
    private String queryString;

    /** The query. */
    private Query query;

    /** The count query string. */
    private String countQueryString;

    /** The count query. */
    private Query countQuery;

    /** The original count query string. */
    private String originalCountQueryString;

    /** The original count query. */
    private Query originalCountQuery;

    /** The em. */
    // managed SynQuery
    private EntityManager em;

    /** The selects. */
    private StatementList selects;

    /** The deletes. */
    private StatementList deletes;

    /** The updates. */
    private StatementList updates;

    /** The froms. */
    private StatementList froms;

    /** The left joins. */
    private StatementList leftJoins;

    /** The inner joins. */
    private StatementList innerJoins;

    /** The effective where condition. */
    private Condition effectiveWhereCondition;

    /** The having condition. */
    private Condition havingCondition;

    /** The group bys. */
    private StatementList groupBys;

    /** The order bys. */
    private StatementList orderBys;

    /** The max results. */
    private Integer maxResults = ArrowQuery.DEFAULT_MAX_RESULTS;

    /** The first result. */
    private Integer firstResult;

    /**
     * Instantiates a new arrow query.
     *
     * @param query the query
     * @param countQuery the count query
     */
    // unmanaged SynQuery
    public ArrowQuery(final Query query, final Query countQuery) {
        this.query = query;
        this.countQuery = countQuery;
        this.managed = false;
    }

    /**
     * Instantiates a new arrow query.
     *
     * @param em the em
     */
    public ArrowQuery(final EntityManager em) {
        this.managed = true;
        this.em = em;
    }

    /**
     * Gets the name from field path.
     *
     * @param fieldPath the field path
     * @return the name from field path
     */
    // convenience methods to add both filter and sorter
    private static String getNameFromFieldPath(final String fieldPath) {
        return StringUtils.getLastToken(fieldPath, "\\.");
    }

    /**
     * Replace question marks.
     *
     * @param query the query
     * @return the string
     */
    public static String replaceQuestionMarks(final String query) {
        final StringBuffer buffer = new StringBuffer(query);
        int pointer = 1;
        int count = 1;

        while (pointer > 0) {
            pointer = buffer.indexOf("?", pointer) + 1;
            if (pointer > 0) {
                buffer.replace(pointer - 1, pointer, "?" + count++);
            }
        }

        return buffer.toString();
    }

    /**
     * Accept type of <code>SynQuery.ResultList</code> and reload up-to-date result from the database.
     *
     * @param resultList the result list
     */

    public static void resetResults(final List<?> resultList) {
        /* Won't use SynCollection.isNotEmpty() as resultList need to reload even if it was empty */
        if ((resultList != null) && (resultList instanceof ArrowQuery.ResultList)) {
            ((ArrowQuery.ResultList<?>) resultList).resetResults();
        }
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Checks if is managed.
     *
     * @return true, if is managed
     */
    public boolean isManaged() {
        return this.managed;
    }

    /**
     * Assert managed.
     */
    private void assertManaged() {
        if (!this.managed) {
            throw new UnsupportedOperationException("JPA queries are NOT managed internally");
        }
    }

    /**
     * Assert unmanaged.
     */
    private void assertUnmanaged() {
        if (this.managed) {
            throw new UnsupportedOperationException("JPA queries are managed internally");
        }
    }


    /**
     * Gets the result list.
     *
     * @return the result list
     */
    public ResultList<E> getResultList() {
        return this.resultList;
    }

    /**
     * Gets the single result.
     *
     * @return the single result
     */
    @SuppressWarnings("unchecked")
    public E getSingleResult() {
        return (E) this.getJPAQuery().getSingleResult();
    }

    /**
     * Gets the JPA query.
     *
     * @return the JPA query
     */
    public Query getJPAQuery() {
        if (this.query == null) {
            this.buildJPAQueries();
        }
        return this.query;
    }

    /**
     * Sets the JPA query.
     *
     * @param jpaQuery the new JPA query
     */
    public void setJPAQuery(final Query jpaQuery) {
        this.assertUnmanaged();
        this.query = jpaQuery;
        this.resultList.resetResults();
    }

    /**
     * Sets the lock mode.
     *
     * @param lockModeType the new lock mode
     */
    public void setLockMode(final LockModeType lockModeType) {
        this.getJPAQuery().setLockMode(lockModeType);
    }

    /**
     * Gets the JPA count query.
     *
     * @return the JPA count query
     */
    public Query getJPACountQuery() {
        if (this.countQuery == null) {
            this.buildJPAQueries();
        }
        return this.countQuery;
    }

    /**
     * Sets the JPA count query.
     *
     * @param jpaCountQuery the new JPA count query
     */
    public void setJPACountQuery(final Query jpaCountQuery) {
        this.assertUnmanaged();
        this.countQuery = jpaCountQuery;
        this.resultList.resetResults();
    }

    /**
     * Gets the JPA original count query.
     *
     * @return the JPA original count query
     */
    public Query getJPAOriginalCountQuery() {
        if (this.originalCountQuery == null) {
            this.buildJPAQueries();
        }
        return this.originalCountQuery;
    }

    /**
     * Sets the JPA original count query.
     *
     * @param jpaOriginalCountQuery the new JPA original count query
     */
    public void setJPAOriginalCountQuery(final Query jpaOriginalCountQuery) {
        this.assertUnmanaged();
        this.originalCountQuery = jpaOriginalCountQuery;
        this.resultList.resetResults();
    }

    /**
     * Sets the entity manager.
     *
     * @param entityManager the new entity manager
     */
    public void setEntityManager(final EntityManager entityManager) {
        this.assertManaged();
        this.em = entityManager;
        this.resetQueries();
    }

    /**
     * Force re create queries.
     */
    private void forceReCreateQueries() {
        this.query = null;
        this.countQuery = null;
    }

    /**
     * Reset queries.
     */
    private void resetQueries() {
        this.assertManaged();
        this.query = null;
        this.countQuery = null;
        this.resultList.resetResults();
    }

    /**
     * Select.
     *
     * @param select the select
     * @return the arrow query
     */
    // select
    public ArrowQuery<E> select(final String select) {
        if (this.type == null) {
            this.type = Type.SELECT;
        }
        else if (this.type != Type.SELECT) {
            throw new IllegalStateException("not SELECT");
        }

        this.getSelects().add(select);
        return this;
    }

    /**
     * Distinct.
     *
     * @return the arrow query
     */
    public ArrowQuery<E> distinct() {
        this.distinct(true);
        return this;
    }

    /**
     * Distinct.
     *
     * @param isDistinct the is distinct
     * @return the arrow query
     */
    public ArrowQuery<E> distinct(final boolean isDistinct) {
        this.getSelects().setPrefix(isDistinct ? "SELECT DISTINCT " : "SELECT ");
        return this;
    }

    /**
     * Delete.
     *
     * @param delete the delete
     * @return the arrow query
     */
    // delete/update: not fully implemented
    public ArrowQuery<E> delete(final String delete) {
        if (this.type == null) {
            this.type = Type.DELETE;
        }
        else if (this.type != Type.DELETE) {
            throw new IllegalStateException("not DELETE");
        }

        this.getDeletes().add(delete);
        return this;
    }

    /**
     * Update.
     *
     * @param update the update
     * @return the arrow query
     */
    public ArrowQuery<E> update(final String update) {
        if (this.type == null) {
            this.type = Type.UPDATE;
        }
        else if (this.type != Type.UPDATE) {
            throw new IllegalStateException("not UPDATE");
        }

        this.getUpdates().add(update);
        return this;
    }

    /**
     * From.
     *
     * @param from the from
     * @return the arrow query
     */
    // from
    public ArrowQuery<E> from(final String from) {
        this.getFroms().add(from);
        return this;
    }

    /**
     * Left join.
     *
     * @param join the join
     * @return the arrow query
     */
    // joins
    public ArrowQuery<E> leftJoin(final String join) {
        this.getLeftJoins().add(join);
        return this;
    }

    /**
     * Left join fetch.
     *
     * @param join the join
     * @return the arrow query
     */
    public ArrowQuery<E> leftJoinFetch(final String join) {
        this.getLeftJoins().add("FETCH " + join);
        return this;
    }

    /**
     * Inner join.
     *
     * @param join the join
     */
    public void innerJoin(final String join) {
        this.getInnerJoins().add(join);
    }

    /**
     * Where.
     *
     * @param where the where
     * @return the arrow query
     */
    // where
    public ArrowQuery<E> where(final Condition where) {
        this.whereCondition.add(where);
        return this;
    }

    /**
     * Where.
     *
     * @param condition the condition
     * @param params the params
     * @return the arrow query
     */
    public ArrowQuery<E> where(final String condition, final Object... params) {
        return this.where(new Condition(condition, params));
    }

    /**
     * Group by.
     *
     * @param groupBy the group by
     * @return the arrow query
     */
    // group by
    public ArrowQuery<E> groupBy(final String groupBy) {
        this.getGroupBy().add(groupBy);
        return this;
    }

    /**
     * Having.
     *
     * @param having the having
     * @return the arrow query
     */
    // having
    public ArrowQuery<E> having(final Condition having) {
        this.havingCondition = having;
        return this;
    }

    /**
     * Order by.
     *
     * @param orderBy the order by
     * @return the arrow query
     */
    // order by
    public ArrowQuery<E> orderBy(final String orderBy) {
        this.getOrderBy().add(orderBy);
        return this;
    }

    /**
     * Sets the max results.
     *
     * @param max the max
     * @return the arrow query
     */
    // result limit and offset
    public ArrowQuery<E> setMaxResults(final Integer max) {
        this.maxResults = max;
        return this;
    }

    /**
     * Sets the first result.
     *
     * @param result the result
     * @return the arrow query
     */
    public ArrowQuery<E> setFirstResult(final Integer result) {
        this.firstResult = result;
        return this;
    }

    /**
     * Adds the filter.
     *
     * @param name the name
     * @param filterType the filter type
     * @param queryFragment the query fragment
     * @return the arrow query
     */
    public ArrowQuery<E> addFilter(final String name, final ResultList.FilterType filterType,
            final String queryFragment) {
        // convert legacy DATE filter to DATE_RANGE filter
        if (filterType == ResultList.FilterType.DATE) {
            // extract dateFieldPath from queryFragment by removing " = ?"
            String dateFieldPath = queryFragment.trim();
            dateFieldPath = dateFieldPath.substring(0, dateFieldPath.length() - 1);
            dateFieldPath = dateFieldPath.trim();
            dateFieldPath = dateFieldPath.substring(0, dateFieldPath.length() - 1);
            dateFieldPath = dateFieldPath.trim();

            if (!dateFieldPath.contains(" ") && !dateFieldPath.contains("?") && !dateFieldPath.contains("=")) {
                this.addDateRangeFilter(name, dateFieldPath);
                return this;
            }
        }

        // convert legacy NUMBER filter to support range
        else if ((filterType == ResultList.FilterType.NUMBER) && !queryFragment.contains(">=")) {
            // extract dateFieldPath from queryFragment by removing " = ?"
            String numberFieldPath = queryFragment.trim();
            numberFieldPath = numberFieldPath.substring(0, numberFieldPath.length() - 1);
            numberFieldPath = numberFieldPath.trim();
            numberFieldPath = numberFieldPath.substring(0, numberFieldPath.length() - 1);
            numberFieldPath = numberFieldPath.trim();

            if (!numberFieldPath.contains(" ") && !numberFieldPath.contains("?") && !numberFieldPath.contains("=")) {
                this.addNumberFilter(name, numberFieldPath);
                return this;
            }
        }

        this.filters.put(name, queryFragment);
        this.resultList.addFilter(name, filterType);
        return this;

    }

    /**
     * Adds the filter.
     *
     * @param name the name
     * @param queryFragment the query fragment
     * @return the arrow query
     */
    public ArrowQuery<E> addFilter(final String name, final String queryFragment) {
        return this.addFilter(name, ResultList.FilterType.STRING, queryFragment);
    }

    /**
     * date range filter would give a single input field where user manually type a date range without calendar lookup.
     *
     * @param name the name
     * @param dateFieldPath the date field path
     * @return the arrow query
     */
    public ArrowQuery<E> addDateRangeFilter(final String name, final String dateFieldPath) {
        final String queryFragment = dateFieldPath + " >= ? AND " + dateFieldPath + " <= ?";
        return this.addFilter(name, ResultList.FilterType.DATE_RANGE, queryFragment);
    }

    /**
     * date from/to filter would give a 2 input fields with calendar lookups.
     *
     * @param name the name
     * @param dateFieldPath the date field path
     * @return the arrow query
     */
    public ArrowQuery<E> addDateFromToFilter(final String name, final String dateFieldPath) {
        final String queryFragment = dateFieldPath + " >= ? AND " + dateFieldPath + " <= ?";
        return this.addFilter(name, ResultList.FilterType.DATE_FROM_TO, queryFragment);
    }

    /**
     * number range filter.
     *
     * @param name the name
     * @param numberFieldPath the number field path
     * @return the arrow query
     */
    public ArrowQuery<E> addNumberFilter(final String name, final String numberFieldPath) {
        final String queryFragment = numberFieldPath + " > ? AND " + numberFieldPath + " < ?";
        return this.addFilter(name, ResultList.FilterType.NUMBER, queryFragment);
    }

    /**
     * Adds the filter and sorter for string.
     *
     * @param name the name
     * @param fieldPath the field path
     * @return the arrow query
     */
    public ArrowQuery<E> addFilterAndSorterForString(final String name, final String fieldPath) {
        this.addFilter(name, "UPPER(" + fieldPath + ") LIKE ?");
        this.addSorter(name, fieldPath);

        return this;
    }

    /**
     * Adds the filter and sorter for string.
     *
     * @param fieldPath the field path
     * @return the arrow query
     */
    public ArrowQuery<E> addFilterAndSorterForString(final String fieldPath) {
        return this.addFilterAndSorterForString(ArrowQuery.getNameFromFieldPath(fieldPath), fieldPath);
    }

    /**
     * Adds the filter and sorter for number.
     *
     * @param name the name
     * @param fieldPath the field path
     * @return the arrow query
     */
    public ArrowQuery<E> addFilterAndSorterForNumber(final String name, final String fieldPath) {
        this.addNumberFilter(name, fieldPath);
        this.addSorter(name, fieldPath);

        return this;
    }

    /**
     * Adds the filter and sorter for number.
     *
     * @param fieldPath the field path
     * @return the arrow query
     */
    public ArrowQuery<E> addFilterAndSorterForNumber(final String fieldPath) {
        return this.addFilterAndSorterForNumber(ArrowQuery.getNameFromFieldPath(fieldPath), fieldPath);
    }

    /**
     * Adds the filter and sorter for date range.
     *
     * @param name the name
     * @param fieldPath the field path
     * @return the arrow query
     */
    public ArrowQuery<E> addFilterAndSorterForDateRange(final String name, final String fieldPath) {
        this.addDateRangeFilter(name, fieldPath);
        this.addSorter(name, fieldPath);

        return this;
    }

    /**
     * Adds the filter and sorter for date range.
     *
     * @param fieldPath the field path
     * @return the arrow query
     */
    public ArrowQuery<E> addFilterAndSorterForDateRange(final String fieldPath) {
        return this.addFilterAndSorterForDateRange(ArrowQuery.getNameFromFieldPath(fieldPath), fieldPath);
    }

    /**
     * Adds the sorter.
     *
     * @param name the name
     * @param queryFragment the query fragment
     * @return the arrow query
     */
    public ArrowQuery<E> addSorter(final String name, final String queryFragment) {
        this.sorters.put(name, queryFragment);
        return this;
    }

    /**
     * Sort.
     *
     * @param name the name
     */
    public void sort(final String name) {
        this.resultList.sort(name);
    }

    /**
     * Sort.
     *
     * @param name the name
     * @param isAscending the is ascending
     */
    public void sort(final String name, final boolean isAscending) {
        this.resultList.sort(name, isAscending);
    }

    /**
     * Builds the jpa queries.
     */
    private void buildJPAQueries() {
        final String main = this.type == Type.SELECT ? this.getSelects().build()
                : this.type == Type.DELETE ? this.getDeletes().build() : this.getUpdates().build();
        String selectCount = null;
        if (this.type == Type.SELECT) {
            selectCount = "SELECT COUNT(" + main.substring(7) + ")";
        }

        final String fromsAfterBuild = this.getFroms().build();
        final String leftJoinAfterBuild = this.getLeftJoins().build();
        final String outerJoins = this.getInnerJoins().build();

        if (this.resultList.appliedFilters.isEmpty()) {
            this.effectiveWhereCondition = this.whereCondition;
        }
        else {
            final Condition.Conjunction conjunction = new Condition.Conjunction();
            conjunction.add(this.whereCondition);
            for (final Condition condition : this.resultList.appliedFilters.values()) {
                conjunction.add(condition);
            }

            this.effectiveWhereCondition = conjunction;
        }
        final String where = (this.effectiveWhereCondition == null) || this.effectiveWhereCondition.isEmpty() ? ""
                : " WHERE " + this.effectiveWhereCondition.build();
        final String originalWhere = (this.whereCondition == null) || this.whereCondition.isEmpty() ? ""
                : " WHERE " + this.whereCondition.build();

        final String groupBy = this.getGroupBy().build();
        final String having = (this.havingCondition == null) || this.havingCondition.isEmpty() ? ""
                : " HAVING " + this.havingCondition.build();

        String orderBy = null;
        if (this.resultList.currentSorter != null) {
            String newOrder = this.sorters.get(this.resultList.currentSorter);
            final String[] orders = newOrder.split(",");
            if (orders.length > 1) {
                StringBuffer tempOrder = new StringBuffer();
                int count = 1;
                for (final String order : orders) {
                    tempOrder.append(
                            ((count > 1 ? ", " : "") + order + (this.resultList.isAscending ? " ASC" : " DESC")));
                    count++;
                }
                newOrder = tempOrder.toString();
            }
            else {
                newOrder += (this.resultList.isAscending ? " ASC" : " DESC");
            }
            orderBy = " ORDER BY " + newOrder;
        }

        final String defaultOrderBy = this.getOrderBy().build();

        if (orderBy == null) {
            orderBy = defaultOrderBy;
        }

        else {
            if (defaultOrderBy.length() > 0) {
                orderBy = orderBy + ","
                        + StringUtils.trim(defaultOrderBy.substring(defaultOrderBy.indexOf("ORDER BY") + 8));
            }
        }

        final String afterMain = ArrowQuery
                .replaceQuestionMarks(fromsAfterBuild + leftJoinAfterBuild + outerJoins + where + groupBy + having);
        final String originalAfterMain = ArrowQuery.replaceQuestionMarks(
                fromsAfterBuild + leftJoinAfterBuild + outerJoins + originalWhere + groupBy + having);

        this.queryString = main + afterMain + orderBy;
        this.countQueryString = selectCount == null ? null : selectCount + afterMain;
        this.originalCountQueryString = selectCount == null ? null : selectCount + originalAfterMain;
        // build the query and set the parameters
        this.query = this.em.createQuery(this.queryString);
        this.countQuery = this.countQueryString == null ? null : this.em.createQuery(this.countQueryString);
        this.originalCountQuery =
                this.originalCountQueryString == null ? null : this.em.createQuery(this.originalCountQueryString);

        final List<Object> params = this.getParams();

        for (int i = 0; i < params.size(); i++) {
            this.query.setParameter(i + 1, params.get(i));
            if (this.countQuery != null) {
                this.countQuery.setParameter(i + 1, params.get(i));
            }

            if ((this.originalCountQuery != null) && ((i + 1) <= this.originalCountQuery.getParameters().size())) {
                this.originalCountQuery.setParameter(i + 1, params.get(i));
            }
        }

        if (this.firstResult != null) {
            this.query.setFirstResult(this.firstResult);
        }
        if (this.maxResults != null) {
            this.query.setMaxResults(this.maxResults);
        }

    }

    /**
     * Gets the params.
     *
     * @return the params
     */
    private List<Object> getParams() {
        final List<Object> params = new ArrayList<Object>();
        if ((this.effectiveWhereCondition != null) && !this.effectiveWhereCondition.isEmpty()) {
            params.addAll(this.effectiveWhereCondition.getParameters());
        }
        if ((this.havingCondition != null) && !this.havingCondition.isEmpty()) {
            params.addAll(this.havingCondition.getParameters());
        }
        return params;
    }

    /**
     * Gets the selects.
     *
     * @return the selects
     */
    private synchronized StatementList getSelects() {
        if (this.selects == null) {
            this.selects = new StatementList("SELECT ");
            this.selects.setSeparator(", ");
        }
        return this.selects;
    }

    /**
     * Gets the deletes.
     *
     * @return the deletes
     */
    private synchronized StatementList getDeletes() {
        if (this.deletes == null) {
            this.deletes = new StatementList("DELETE ");
            this.deletes.setSeparator(", ");
        }
        return this.deletes;
    }

    /**
     * Gets the updates.
     *
     * @return the updates
     */
    private synchronized StatementList getUpdates() {
        if (this.updates == null) {
            this.updates = new StatementList("UPDATE ");
            this.updates.setSeparator(", ");
        }
        return this.updates;
    }

    /**
     * Gets the froms.
     *
     * @return the froms
     */
    private synchronized StatementList getFroms() {
        if (this.froms == null) {
            this.froms = new StatementList(" FROM ");
            this.froms.setSeparator(", ");
        }
        return this.froms;
    }

    /**
     * Gets the left joins.
     *
     * @return the left joins
     */
    private synchronized StatementList getLeftJoins() {
        if (this.leftJoins == null) {
            this.leftJoins = new StatementList(" LEFT JOIN ");
            this.leftJoins.setSeparator(" LEFT JOIN ");
        }
        return this.leftJoins;
    }

    /**
     * Gets the inner joins.
     *
     * @return the inner joins
     */
    private synchronized StatementList getInnerJoins() {
        if (this.innerJoins == null) {
            this.innerJoins = new StatementList(" INNER JOIN ");
            this.innerJoins.setSeparator(" INNER JOIN ");
        }
        return this.innerJoins;
    }

    /**
     * Gets the order by.
     *
     * @return the order by
     */
    private synchronized StatementList getOrderBy() {
        if (this.orderBys == null) {
            this.orderBys = new StatementList(" ORDER BY ");
            this.orderBys.setSeparator(", ");
        }
        return this.orderBys;
    }

    /**
     * Gets the group by.
     *
     * @return the group by
     */
    private synchronized StatementList getGroupBy() {
        if (this.groupBys == null) {
            this.groupBys = new StatementList(" GROUP BY ");
            this.groupBys.setSeparator(", ");
        }
        return this.groupBys;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return System.identityHashCode(this) + " " + this.queryString;
    }

    /**
     * The Enum Type.
     */
    public static enum Type {

        /** The select. */
        SELECT,
        /** The update. */
        UPDATE,
        /** The delete. */
        DELETE
    }

    /**
     * *************************** List element selection ***********************.
     *
     * @param <E> the element type
     */

    // result list class
    public static class ResultList<E> extends AbstractList<E> implements SelectableList<E> {

        /** The Constant DEFAULT_PAGE_SIZE. */
        private static final int DEFAULT_PAGE_SIZE = 20;

        /** The syn query. */
        private final ArrowQuery<E> synQuery;

        /** The applied filters. */
        private final Map<String, Condition> appliedFilters = new HashMap<String, Condition>();

        /** The filter map. */
        private final Map<String, Filter> filterMap = new HashMap<String, Filter>();

        /** ******************** Methods for List interface ******************. */
        private final Map<Integer, E> loadedResultsMap = new HashMap<Integer, E>();

        /** The selected indexes. */
        private final SortedSet<Integer> selectedIndexes = new TreeSet<Integer>();

        /** The element selection map. */
        private final Map<E, Boolean> elementSelectionMap = new AbstractMap<E, Boolean>() {
            @Override
            public Set<Map.Entry<E, Boolean>> entrySet() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean containsKey(final Object key) {
                return ResultList.this.selectedIndexes.contains(key);
            }

            @Override
            public Boolean get(final Object key) {
                return ResultList.this.selectedIndexes.contains(ResultList.this.indexOf(key));
            }

            @Override
            public Boolean put(final E key, final Boolean value) {
                if (value) {
                    ResultList.this.selectedIndexes.add(ResultList.this.indexOf(key));
                }

                else {
                    ResultList.this.selectedIndexes.remove(ResultList.this.indexOf(key));
                }

                return value;
            }
        };

        /** The page size. */
        private int pageSize = ResultList.DEFAULT_PAGE_SIZE;

        /** The current sorter. */
        // sorting
        private String currentSorter;

        /** The is ascending. */
        private boolean isAscending = true;

        /** The result count. */
        private int resultCount = -1;

        /** The index selection map. */
        private final Map<Integer, Boolean> indexSelectionMap = new AbstractMap<Integer, Boolean>() {
            @Override
            public Set<Map.Entry<Integer, Boolean>> entrySet() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean containsKey(final Object key) {
                final int index = (Integer) key;
                return (index > -1) && (index < ResultList.this.size());
            }

            @Override
            public Boolean get(final Object key) {
                return ResultList.this.selectedIndexes.contains(key);
            }

            @Override
            public Boolean put(final Integer key, final Boolean value) {
                if (value) {
                    ResultList.this.selectedIndexes.add(key);
                }

                else {
                    ResultList.this.selectedIndexes.remove(key);
                }

                return value;
            }
        };

        /** The original count. */
        private int originalCount = -1;

        /**
         * Instantiates a new result list.
         *
         * @param query the query
         */
        public ResultList(final ArrowQuery<E> query) {
            this.synQuery = query;
        }

        /**
         * **************** Business methods for this result list *****************.
         */

        public void resetResults() {
            this.loadedResultsMap.clear();
            this.resultCount = -1;
            this.originalCount = -1;
            // if ((this.synQuery != null) && StringUtils.isNotEmpty(this.synQuery.resetResultsEventName)) {
            // // TODO: Need a feature to raise this event.
            // // Events.instance().raiseEvent(this.synQuery.resetResultsEventName);
            // }
            this.selectedIndexes.clear();
        }

        /**
         * Gets the page size.
         *
         * @return the page size
         */
        public int getPageSize() {
            return this.pageSize;
        }

        /**
         * Sets the page size.
         *
         * @param pageSize the new page size
         */
        public void setPageSize(final int pageSize) {
            this.pageSize = pageSize;
        }

        /**
         * Apply filter.
         *
         * @param name the name
         * @param value the value
         * @param type the type
         */
        private void applyFilter(final String name, Object value, final ResultList.FilterType type) {
            if ((value == null) || ((value instanceof String) && (((String) value).trim().length() == 0))) {
                this.appliedFilters.remove(name);
            }
            else {
                if (type == FilterType.DATE_RANGE) {
                    Object[] dates = null;
                    if (value instanceof String) {
                        dates = DateUtils.parseDateRange((String) value);
                    }
                    else {
                        dates = (Date[]) value;
                    }

                    if (dates.length > 0) {
                        this.appliedFilters.put(name, new Condition(this.synQuery.filters.get(name), dates));
                    }
                    else {
                        this.filterMap.get(name).setValue(null);
                    }
                }
                else if (type == FilterType.DATE_FROM_TO) {

                    // need add time portion.
                    Date dateFrom = ((Date[]) value)[0];
                    dateFrom = DateUtils.getLowerBound(dateFrom);
                    Date dateTo = ((Date[]) value)[1];
                    dateTo = DateUtils.getUpperBound(dateTo);
                    final Object[] dates = new Object[] {dateFrom, dateTo};
                    this.isAscending = !this.isAscending; // Micheal: bypass reversing order
                    this.appliedFilters.put(name, new Condition(this.synQuery.filters.get(name), dates));
                }
                else if (type == FilterType.OR_STRING) { // for query fragment contains multiple OR
                    if (value instanceof String) {
                        value = StringUtils.likePattern((String) value);
                    }
                    final String conditionFragment = this.synQuery.filters.get(name);
                    final String[] fragments = conditionFragment.split("OR");
                    final Object[] values = new Object[fragments.length];

                    for (int i = 0; i < fragments.length; i++) {
                        values[i] = String.valueOf(value);
                    }
                    this.appliedFilters.put(name, new Condition(this.synQuery.filters.get(name), values));
                }
                else if (type == FilterType.NUMBER) {
                    Object[] intRange = null;

                    if (value instanceof String) {
                        intRange = StringUtils.parseNumberRange((String) value);
                    }
                    else {
                        intRange = (Integer[]) value;
                    }

                    if (intRange.length > 0) {
                        this.appliedFilters.put(name, new Condition(this.synQuery.filters.get(name), intRange));
                    }

                    else {
                        this.filterMap.get(name).setValue(null);
                    }
                }
                else if (type == FilterType.EQUAL_STRING) {
                    if (value instanceof String) {
                        value = StringUtils.trim((String) value);
                    }
                    this.appliedFilters.put(name, new Condition(this.synQuery.filters.get(name), value));
                }
                else {
                    if (value instanceof String) {
                        value = StringUtils.likePattern((String) value);
                    }
                    this.appliedFilters.put(name, new Condition(this.synQuery.filters.get(name), value));
                }
            }
            this.synQuery.resetQueries();
        }

        // private void sort(final Comparator<E> comparator)
        // {
        // List<E> temp = new ArrayList<E>(this);
        // Collections.sort(temp, comparator);
        //
        // for (int i = 0; i < this.size(); i++)
        // {
        // this.loadedResultsMap.put(i, temp.get(i));
        // }
        // }

        /**
         * Adds the filter.
         *
         * @param name the name
         * @param type the type
         */
        private void addFilter(final String name, final FilterType type) {
            this.filterMap.put(name, new Filter(name, type));
        }

        // this is the where the filters can be applied from xhtml: filter('name').setValue('filter
        /**
         * Filter.
         *
         * @param name the name
         * @return the filter
         */
        // value');
        public Filter filter(final String name) {
            return this.filterMap.get(name);
        }

        /**
         * Sort.
         *
         * @param name the name
         */
        // these are the public method to be called from facelet
        public void sort(final String name) {
            if (name.equals(this.currentSorter)) {
                this.sort(name, !this.isAscending);
            }
            else {
                this.sort(name, true);
            }
        }

        /**
         * Sort.
         *
         * @param name the name
         * @param isAscending the is ascending
         */
        public void sort(final String name, final boolean isAscending) {
            if (this.synQuery.sorters.containsKey(name)) {
                this.currentSorter = name;
                this.isAscending = isAscending;
                this.synQuery.resetQueries();
            }
        }

        /* (non-Javadoc)
         * @see java.util.AbstractList#get(int)
         */
        @SuppressWarnings("unchecked")
        @Override
        public E get(final int index) {
            if (!this.loadedResultsMap.containsKey(index)) {
                final int firstResult = this.synQuery.firstResult == null ? index : this.synQuery.firstResult + index;
                final int maxResult = this.synQuery.maxResults == null ? this.pageSize
                        : Math.min(this.synQuery.maxResults - firstResult, this.pageSize);

                // try to recreate query with new Entity Manager, because the old Entity Manager is
                // closed after request end.
                this.synQuery.forceReCreateQueries();
                final List<E> results = this.synQuery.getJPAQuery().setFirstResult(firstResult).setMaxResults(maxResult)
                        .getResultList();
                for (int j = 0; j < results.size(); j++) {
                    this.loadedResultsMap.put(index + j, results.get(j));
                }
            }
            return this.loadedResultsMap.get(index);
        }

        /* (non-Javadoc)
         * @see java.util.AbstractCollection#size()
         */
        @Override
        public int size() {
            this.synQuery.forceReCreateQueries();
            final int size = this.realSize();
            return this.synQuery.maxResults == null ? size : Math.min(this.synQuery.maxResults, size);
        }

        /**
         * Real size.
         *
         * @return the int
         */
        public int realSize() {
            if (this.resultCount < 0) {
                this.resultCount = ((Long) this.synQuery.getJPACountQuery().getSingleResult()).intValue();
            }
            return this.resultCount;
        }

        /**
         * Original size.
         *
         * @return the int
         */
        public int originalSize() {
            if (this.originalCount < 0) {
                this.originalCount = ((Long) this.synQuery.getJPAOriginalCountQuery().getSingleResult()).intValue();
            }
            return this.originalCount;
        }

        /**
         * Gets the applied filters.
         *
         * @return the applied filters
         */
        public Map<String, Condition> getAppliedFilters() {
            return this.appliedFilters;
        }

        /* (non-Javadoc)
         * @see arrow.framework.util.collections.SelectableList#selectAll()
         */
        @Override
        public void selectAll() {
            this.selectedIndexes.clear();
            for (int i = 0; i < this.size(); i++) {
                this.selectedIndexes.add(i);
            }
        }

        /* (non-Javadoc)
         * @see arrow.framework.util.collections.SelectableList#deselectAll()
         */
        @Override
        public void deselectAll() {
            this.selectedIndexes.clear();
        }

        /* (non-Javadoc)
         * @see arrow.framework.util.collections.SelectableList#select(java.lang.Object, boolean)
         */
        @Override
        public void select(final E element, final boolean selected) {
            this.getElementSelectionMap().put(element, selected);
        }

        /* (non-Javadoc)
         * @see arrow.framework.util.collections.SelectableList#getSelectedIndexes()
         */
        @Override
        public SortedSet<Integer> getSelectedIndexes() {
            return this.selectedIndexes;
        }

        /* (non-Javadoc)
         * @see arrow.framework.util.collections.SelectableList#getElementSelectionMap()
         */
        @Override
        public Map<E, Boolean> getElementSelectionMap() {
            return this.elementSelectionMap;
        }

        /* (non-Javadoc)
         * @see arrow.framework.util.collections.SelectableList#getIndexSelectionMap()
         */
        @Override
        public Map<Integer, Boolean> getIndexSelectionMap() {
            return this.indexSelectionMap;
        }

        /**
         * Gets the filter map.
         *
         * @return the filter map
         */
        public Map<String, Filter> getFilterMap() {
            return this.filterMap;
        }

        // Types of filter
        // Only STRING and DATE(single) were implemented at the present
        /**
         * The Enum FilterType.
         */
        // Used by xhtml to display related input type like text box, calendar, etc.
        public enum FilterType {

            /** The string. */
            STRING,
            /** The number. */
            NUMBER,
            /** The date. */
            DATE,
            /** The date range. */
            DATE_RANGE,
            /** The or string. */
            OR_STRING,
            /** The date from to. */
            DATE_FROM_TO,
            /** The equal string. */
            EQUAL_STRING
        }

        // filtering
        /**
         * The Class Filter.
         */
        // filter helper inner class
        public class Filter {

            /** The name. */
            private final String name;

            /** The value. */
            private Object value;

            /** The date from. */
            private Object dateFrom;

            /** The date to. */
            private Object dateTo;

            /** The type. */
            private FilterType type;

            /**
             * Instantiates a new filter.
             *
             * @param name the name
             */
            // Create a String filter
            Filter(final String name) {
                this(name, FilterType.STRING);
            }

            /**
             * Instantiates a new filter.
             *
             * @param name the name
             * @param type the type
             */
            Filter(final String name, final FilterType type) {
                this.name = name;
                this.type = type;
            }

            /**
             * Gets the date from.
             *
             * @return the date from
             */
            public Object getDateFrom() {
                return this.dateFrom;
            }

            /**
             * Sets the date from.
             *
             * @param dateFrom the new date from
             */
            public void setDateFrom(final Object dateFrom) {
                if ((dateFrom != null) && (this.dateTo != null)) {
                    final Date val = (Date) dateFrom;
                    final Date val1 = (Date) this.dateTo;
                    if (val.after(val1)) {
                        ErrorMessage.sys_001_date_from_must_smaller_date_to().show();
                        return;
                    }
                }

                this.dateFrom = dateFrom;
                if ((this.dateFrom != null) && (this.dateTo == null)) {
                    this.dateTo = this.dateFrom;
                }
                this.applyDateFromTo(this.dateFrom, this.dateTo);
            }

            /**
             * Gets the date to.
             *
             * @return the date to
             */
            public Object getDateTo() {
                return this.dateTo;
            }

            /**
             * Sets the date to.
             *
             * @param dateTo the new date to
             */
            public void setDateTo(final Object dateTo) {
                if ((dateTo != null) && (this.dateFrom != null)) {
                    final Date val = (Date) dateTo;
                    final Date val1 = (Date) this.dateFrom;
                    if (val.before(val1)) {
                        ErrorMessage.sys_001_date_from_must_smaller_date_to().show();
                        return;
                    }
                }
                this.dateTo = dateTo;
                this.applyDateFromTo(this.dateFrom, this.dateTo);
            }

            /**
             * Apply date from to.
             *
             * @param fromDate the from date
             * @param toDate the to date
             */
            private void applyDateFromTo(final Object fromDate, final Object toDate) {
                ResultList.this.applyFilter(this.name,
                        ((fromDate == null) && (this.dateTo == null) ? null
                                : new Date[] {(Date) (fromDate == null ? DateUtils.MIN_DATE : fromDate),
                                              (Date) (toDate == null ? DateUtils.MAX_DATE : toDate)}),
                        FilterType.DATE_FROM_TO);
            }

            /**
             * Gets the name.
             *
             * @return the name
             */
            public String getName() {
                return this.name;
            }

            /**
             * Gets the value.
             *
             * @return the value
             */
            public Object getValue() {
                return this.value;
            }

            /**
             * Sets the value.
             *
             * @param value the new value
             */
            public void setValue(final Object value) {
                this.value = value;
                ResultList.this.applyFilter(this.name, value, this.type);
            }

            /**
             * Gets the type.
             *
             * @return the type
             */
            public FilterType getType() {
                return this.type;
            }
        }
    }
}
