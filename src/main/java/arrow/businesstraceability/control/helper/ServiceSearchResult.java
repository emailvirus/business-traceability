package arrow.businesstraceability.control.helper;

import java.util.Collections;
import java.util.List;

import arrow.framework.faces.message.Message;

public class ServiceSearchResult<T> extends ServiceResult<T> {

    private List<T> listItems;

    private long totalItems;

    public ServiceSearchResult(final boolean successful, final List<Message> messages) {
        super(successful, messages);
    }

    /**
     * Instantiates a new service search result.
     *
     * @param success the success
     * @param listItems the list items
     * @param totalItems the total items
     */
    public ServiceSearchResult(final boolean success, final List<T> listItems, final long totalItems) {
        super(success, Collections.emptyList());
        this.setListItems(listItems);
        this.setTotalItems(totalItems);
    }

    public List<T> getListItems() {
        return this.listItems;
    }

    public void setListItems(final List<T> listItems) {
        this.listItems = listItems;
    }

    public long getTotalItems() {
        return this.totalItems;
    }

    public void setTotalItems(final long totalItems) {
        this.totalItems = totalItems;
    }


}
