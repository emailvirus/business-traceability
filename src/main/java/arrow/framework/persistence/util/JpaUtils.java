package arrow.framework.persistence.util;


import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Selection;

import org.apache.commons.lang3.SerializationUtils;


/**
 * The Class JpaUtils.
 */
public abstract class JpaUtils {

    /**
     * Create a row count CriteriaQuery from a CriteriaQuery.
     *
     * @param <T> the generic type
     * @param cb CriteriaBuider
     * @param query source CriteriaQuery
     * @return row count CriteriaQuery
     */
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "BC_UNCONFIRMED_CAST",
            justification = "DIRTY TRICK: clone via serialization and dirty casting"
                    + "if we check countQuery.getReturnType() we would still get the original type instead of"
                    + "Long.class. This appears to work for Hibernate 4.2.0, but might not be portable.")
    public static <T> CriteriaQuery<Long> createCountQuery(final CriteriaBuilder cb, final CriteriaQuery<T> query) {
        // DIRTY TRICK: clone via serialization and dirty casting
        // if we check countQuery.getReturnType() we would still get the original type instead of
        // Long.class
        // This appears to work for Hibernate 4.2.0, but might not be portable.
        @SuppressWarnings("unchecked")

        final CriteriaQuery<Long> countQuery = (CriteriaQuery<Long>) SerializationUtils
                .deserialize(SerializationUtils.serialize((Serializable) query));

        // clear orderBy otherwise the count query would return error
        countQuery.orderBy();

        // change the selection to count
        final Selection<?> originalSelection = countQuery.getSelection();
        if (originalSelection instanceof Expression) {
            countQuery.select(cb.count((Expression<?>) countQuery.getSelection()));
        }
        else {
            throw new IllegalStateException("Currently we do not support CompoundSelection yet");
        }

        return countQuery;
    }

    /**
     * Build path.
     *
     * @param root the root
     * @param path the path
     * @return the path
     */
    public static Path<?> buildPath(Path<?> root, final String path) {
        final String[] attributes = path.split("\\.");
        for (final String attribute : attributes) {
            root = root.get(attribute);
        }

        return root;
    }


    /**
     * Builds the sort order.
     *
     * @param cb the cb
     * @param root the root
     * @param path the path
     * @param isAscending the is ascending
     * @return the order
     */
    public static Order buildSortOrder(final CriteriaBuilder cb, final Path<?> root, final String path,
            final boolean isAscending) {
        final Path<?> sortPath = JpaUtils.buildPath(root, path);
        return isAscending ? cb.asc(sortPath) : cb.desc(sortPath);
    }
}
