package arrow.framework.util.collections;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;


/**
 * The Class LazyLoadDataTableList.
 *
 * @param <T> the generic type
 */
public class LazyLoadDataTableList<T> extends KeyMappedAbstractDataTableList<T> {

    /** The backing list. */
    private final List<T> backingList;

    /** The filtered and sorted list. */
    private List<T> filteredAndSortedList;

    /** The current filters. */
    private Map<String, String> currentFilters = Collections.emptyMap();

    /** The current sorters. */
    private List<Pair<String, SortOrder>> currentSorters = Collections.emptyList();

    /**
     * Instantiates a new lazy load data table list.
     *
     * @param backingList the backing list
     */
    public LazyLoadDataTableList(final List<T> backingList) {
        this.backingList = backingList;
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

            // re-filtering requires re-sorting as well, so need to force refresh
            this.refresh();
        }
    }

    /* (non-Javadoc)
     * @see arrow.framework.util.collections.DataTableList#applySorters(java.util.List)
     */
    @Override
    public void applySorters(List<Pair<String, SortOrder>> sorters) {
        if (sorters == null) {
            sorters = Collections.emptyList();
        }

        if (!CollectionUtils.areEquals(sorters, this.currentSorters)) {
            this.currentSorters = sorters;

            // if the list is already filtered, only need to re-sort
            if (!this.currentSorters.isEmpty() && (this.filteredAndSortedList != null)) {
                ListUtils.sort(this.filteredAndSortedList, this.currentSorters);
            }
            else {
                // otherwise we force filter to avoid modifying original backing list
                this.refresh();
            }
        }
    }

    /**
     * call this to force apply current filters and sorters when the backing list is modified externally.
     */
    public void refresh() {
        if (this.currentFilters.isEmpty() && this.currentSorters.isEmpty()) {
            this.filteredAndSortedList = null;
        }
        else {
            this.filteredAndSortedList = ListUtils.filter(this.backingList, this.currentFilters);
            ListUtils.sort(this.filteredAndSortedList, this.currentSorters);
        }
    }


    /* (non-Javadoc)
     * @see java.util.AbstractList#get(int)
     */
    @Override
    public T get(final int index) {
        return this.getEffectiveList().get(index);
    }

    /* (non-Javadoc)
     * @see java.util.AbstractCollection#size()
     */
    @Override
    public int size() {
        return this.getEffectiveList().size();
    }


    /**
     * Gets the effective list.
     *
     * @return the effective list
     */
    private List<T> getEffectiveList() {
        return this.filteredAndSortedList != null ? this.filteredAndSortedList : this.backingList;
    }
}
