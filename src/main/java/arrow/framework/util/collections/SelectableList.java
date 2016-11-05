package arrow.framework.util.collections;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;


/**
 * The Interface SelectableList.
 *
 * @param <E> the element type
 */
public interface SelectableList<E> extends List<E> {

    /**
     * Select all.
     */
    public void selectAll();

    /**
     * Deselect all.
     */
    public void deselectAll();

    /**
     * Select.
     *
     * @param element the element
     * @param selected the selected
     */
    public void select(E element, boolean selected);

    /**
     * Gets the selected indexes.
     *
     * @return the selected indexes
     */
    public SortedSet<Integer> getSelectedIndexes();

    /**
     * Gets the element selection map.
     *
     * @return the element selection map
     */
    public Map<E, Boolean> getElementSelectionMap();

    /**
     * Gets the index selection map.
     *
     * @return the index selection map
     */
    public Map<Integer, Boolean> getIndexSelectionMap();
}
