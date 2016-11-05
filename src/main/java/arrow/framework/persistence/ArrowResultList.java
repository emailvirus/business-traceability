package arrow.framework.persistence;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import arrow.framework.logging.BaseLogger;
import arrow.framework.logging.BaseLoggerProducer;
import arrow.framework.persistence.util.JpaUtils;
import arrow.framework.util.FilterUtils;
import arrow.framework.util.collections.CollectionUtils;
import arrow.framework.util.collections.KeyMappedAbstractDataTableList;
import arrow.framework.util.collections.SortOrder;

/**
 * The Class ArrowResultList.
 *
 * @param <T> the generic type
 */
public class ArrowResultList<T> extends KeyMappedAbstractDataTableList<T> {

    private static final BaseLogger LOGGER = BaseLoggerProducer.getLogger(ArrowResultList.class);

    /** The Constant PAGE_SIZE. */
    private static final int PAGE_SIZE = 100;

    /** The em. */
    private final EntityManager em;

    /** The cb. */
    private final CriteriaBuilder cb;

    /** The query. */
    private final CriteriaQuery<T> query;

    /** The selection. */
    private final Path<T> selection;

    /** The original restriction. */
    private final Predicate originalRestriction;

    /** The original orders. */
    private final List<Order> originalOrders;

    /** The additional orders. */
    private final List<Order> additionalOrders = new ArrayList<Order>();

    /** The loaded results map. */
    private final Map<Integer, T> loadedResultsMap = new HashMap<Integer, T>();

    /** The count. */
    private int count = -1;

    // externally set by the user of the query to constraint the result list.
    /** The first result. */
    // there would be separate internal firstResult / maxResult for paging
    private int firstResult = 0;

    /** The max results. */
    private Integer maxResults = null;

    /** The current filters. */
    private Map<String, String> currentFilters = Collections.emptyMap();

    /** The current sorters. */
    private List<Pair<String, SortOrder>> currentSorters = Collections.emptyList();

    /**
     * Constructor with params.
     *
     * @param em the em
     * @param query the query
     */
    public ArrowResultList(final EntityManager em, final CriteriaQuery<T> query) {
        this.em = em;
        this.cb = em.getCriteriaBuilder();

        this.query = query;

        try {
            this.selection = (Path<T>) query.getSelection();
        } catch (final ClassCastException e) {
            throw new IllegalStateException("SynResultList does not support Query with CompoundSelection");
        }

        this.originalRestriction = query.getRestriction();

        this.originalOrders = query.getOrderList();

        if (this.originalOrders.isEmpty()) {
            throw new IllegalStateException("Must specify at least PK ordering to use with SynResultList");
        }
    }

    /**
     * Reset results.
     */
    private void resetResults() {
        this.count = -1;
        this.loadedResultsMap.clear();
    }

    /**
     * Setting first result.
     *
     * @param index the new first result
     */
    public void setFirstResult(final int index) {
        if (index >= 0) {
            this.firstResult = index;
            this.resetResults();
        }
    }

    /**
     * Setting max result.
     *
     * @param index the new max result
     */
    public void setMaxResult(final Integer index) {
        if ((index == null) || (index < 0)) {
            this.maxResults = null;
        }
        else {
            this.maxResults = index;
        }

        this.resetResults();
    }

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.DataTableList#applyFilters(java.util.Map)
     */
    @Override
    public void applyFilters(Map<String, String> filters) {
        if (filters == null) {
            filters = Collections.emptyMap();
        }

        if (!CollectionUtils.areEquals(filters, this.currentFilters)) {
            this.currentFilters = filters;

            final List<Predicate> filterPredicates = new ArrayList<Predicate>();
            for (final Entry<String, String> filterItem : filters.entrySet()) {
                if ("globalFilter".equalsIgnoreCase(filterItem.getKey())) {
                    // no support for global filter
                    continue;
                }
                final Path<?> path = JpaUtils.buildPath(this.selection, filterItem.getKey());
                final Class<?> javaType = path.getJavaType();
                if (javaType.isEnum()) {
                    @SuppressWarnings({"unchecked", "rawtypes"})
                    final Class<? extends Enum> enumType = (Class<? extends Enum>) javaType;
                    try {
                        @SuppressWarnings("unchecked")
                        final Enum<?> enumValue =
                                Enum.valueOf(enumType, filters.get(filterItem.getKey()).toUpperCase());
                        final Predicate pred = this.cb.equal(path, enumValue);
                        filterPredicates.add(pred);
                    } catch (final IllegalArgumentException e) {
                        // Not a member of this Enum Type
                        final Predicate pred = this.cb.not(path.in((Object[]) enumType.getEnumConstants()));
                        filterPredicates.add(pred);
                    }
                }
                else if (String.class.isAssignableFrom(javaType)) {
                    @SuppressWarnings("unchecked")
                    final Expression<String> stringPath = this.cb.lower((Expression<String>) path);
                    final String[] filterStrings = FilterUtils.parseStringForLikeOperator(filterItem.getKey());

                    Predicate pred = null;

                    for (final String s : filterStrings) {
                        final Predicate subPred = this.cb.like(stringPath, s);

                        if (pred == null) {
                            pred = subPred;
                        }
                        else {
                            pred = this.cb.or(pred, subPred);
                        }
                    }

                    if (pred != null) {
                        filterPredicates.add(pred);
                    }
                }
                else if (Date.class.isAssignableFrom(javaType)) {
                    try {
                        @SuppressWarnings("unchecked")
                        final Expression<Date> datePath = (Expression<Date>) path;
                        final List<Date[]> dateRanges = FilterUtils.parseMultipleDateRanges(filterItem.getKey());

                        Predicate pred = null;

                        for (final Date[] dateRange : dateRanges) {
                            Predicate eachRange;
                            if (dateRange[0].equals(dateRange[1])) {
                                eachRange = this.cb.equal(path, dateRange[0]);
                            }
                            else {
                                eachRange = this.cb.and(this.cb.greaterThanOrEqualTo(datePath, dateRange[0]),
                                        this.cb.lessThanOrEqualTo(datePath, dateRange[1]));
                            }

                            if (pred == null) {
                                pred = eachRange;
                            }
                            else {
                                pred = this.cb.or(pred, eachRange);
                            }
                        }

                        if (pred != null) {
                            filterPredicates.add(pred);
                        }
                    } catch (final ParseException e) {
                        // TODO: filter text ParseException: do we want to display error to the user? or just
                        // ignore the invalid filter?
                        ArrowResultList.LOGGER.debug("Parsing failed", e);
                    }
                }
                else if (Number.class.isAssignableFrom(javaType)) {
                    try {
                        @SuppressWarnings("unchecked")
                        final Expression<Double> numberPath = (Expression<Double>) path;
                        final List<Double[]> numberRanges = FilterUtils.parseMultipleNumberRanges(filterItem.getKey());

                        Predicate pred = null;

                        for (final Double[] numberRange : numberRanges) {
                            Predicate eachRange;
                            if (numberRange[0] == null) { // From negative infinity to numberRange[1]
                                eachRange = this.cb.lessThanOrEqualTo(numberPath, numberRange[1]);
                            }
                            else if (numberRange[1] == null) { // From numberRange[0] to infinity
                                eachRange = this.cb.greaterThanOrEqualTo(numberPath, numberRange[0]);
                            }
                            else if (numberRange[0].equals(numberRange[1])) { // Equal
                                eachRange = this.cb.equal(path, numberRange[0]);
                            }
                            else { // Between
                                eachRange = this.cb.and(this.cb.greaterThanOrEqualTo(numberPath, numberRange[0]),
                                        this.cb.lessThanOrEqualTo(numberPath, numberRange[1]));
                            }

                            if (pred == null) {
                                pred = eachRange;
                            }
                            else {
                                pred = this.cb.or(pred, eachRange);
                            }
                        }

                        if (pred != null) {
                            filterPredicates.add(pred);
                        }
                    } catch (final ParseException e) {
                        ArrowResultList.LOGGER.debug("Parsing failed", e);
                    }
                }
            }

            filterPredicates.add(this.originalRestriction);
            this.query.where(filterPredicates.toArray(new Predicate[] {}));
            this.resetResults();
        }
    }

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.DataTableList#applySorters(java.util.List)
     */
    @Override
    public void applySorters(List<Pair<String, SortOrder>> sortSpecs) {
        if (sortSpecs == null) {
            sortSpecs = Collections.emptyList();
        }

        if (!CollectionUtils.areEquals(sortSpecs, this.currentSorters)) {
            this.currentSorters = sortSpecs;

            this.additionalOrders.clear();

            if (sortSpecs != null) {
                for (final Pair<String, SortOrder> sortSpec : sortSpecs) {
                    final String sortField = sortSpec.getLeft();
                    final SortOrder sortOrder = sortSpec.getRight();
                    if (sortOrder != SortOrder.UNSORTED) {
                        final Order order = JpaUtils.buildSortOrder(this.cb, this.selection, sortField,
                                sortOrder == SortOrder.ASCENDING);
                        this.additionalOrders.add(order);
                    }
                }
            }

            final List<Order> effectiveOrders = new ArrayList<Order>();
            effectiveOrders.addAll(this.additionalOrders);
            effectiveOrders.addAll(this.originalOrders);

            this.query.orderBy(effectiveOrders);
            this.resetResults();
        }
    }

    /* (non-Javadoc)
     * @see java.util.AbstractList#get(int)
     */
    @Override
    public T get(final int index) {
        if (!this.loadedResultsMap.containsKey(index)) {
            final int pageFirstResult = this.firstResult + index;
            final int pageMaxResults = this.maxResults == null ? ArrowResultList.PAGE_SIZE
                    : Math.min(this.maxResults - pageFirstResult, ArrowResultList.PAGE_SIZE);

            final List<T> results = this.em.createQuery(this.query).setFirstResult(pageFirstResult)
                    .setMaxResults(pageMaxResults).getResultList();
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
        if (this.count < 0) {
            final CriteriaQuery<Long> countQuery = JpaUtils.createCountQuery(this.em.getCriteriaBuilder(), this.query);
            this.count = this.em.createQuery(countQuery).getSingleResult().intValue() - this.firstResult;

            if (this.count < 0) {
                this.count = 0;
            }
            else if ((this.maxResults != null) && (this.count > this.maxResults)) {
                this.count = this.maxResults;
            }
        }

        return this.count;
    }

    // to allow sorting for caller that's not aware that this is a SynResultList
    /* (non-Javadoc)
     * @see java.util.AbstractList#set(int, java.lang.Object)
     */
    // note that such sorting would cause the list to be loaded fully
    @Override
    public T set(final int index, final T value) {
        final T previous = this.get(index);
        this.loadedResultsMap.put(index, value);
        return previous;
    }

}
