package arrow.businesstraceability.persistence.repository;

import java.util.List;

public class RepoResult<T> {
    private List<T> listItems;

    private T item;

    private long totalItems;

    public List<T> getListItems() {
        return this.listItems;
    }

    public void setListItems(final List<T> listItems) {
        this.listItems = listItems;
    }

    public T getItem() {
        return this.item;
    }

    public void setItem(final T item) {
        this.item = item;
    }

    public long getTotalItems() {
        return this.totalItems;
    }

    public void setTotalItems(final long totalItems) {
        this.totalItems = totalItems;
    }


}
