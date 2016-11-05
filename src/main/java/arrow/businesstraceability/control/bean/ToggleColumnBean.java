package arrow.businesstraceability.control.bean;

import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import arrow.framework.bean.AbstractBean;
import arrow.framework.util.collections.CollectionUtils;


/**
 * The Class ToggleColumnBean.
 */
@Named
@ViewScoped
public class ToggleColumnBean extends AbstractBean {

    /** The list. */
    private List<Boolean> list;

    /**
     * Gets the list.
     *
     * @return the list
     */
    public List<Boolean> getList() {
        if (CollectionUtils.isEmpty(this.list)) {
            // Default initialize 15 items respectively 15 column
            this.list =
                    Arrays.asList(true, true, true, true, true, true, true, true, true, true, true, true, true, true,
                            true);
        }
        return this.list;
    }

    /**
     * On toggle.
     *
     * @param event the e
     */
    public void onToggle(final ToggleEvent event) {
        this.list.set((Integer) event.getData(), event.getVisibility() == Visibility.VISIBLE);
    }
}
