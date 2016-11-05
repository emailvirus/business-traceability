package arrow.framework.faces.util;

import javax.faces.model.SelectItem;

import arrow.framework.util.i18n.Messages;


/**
 * The Class LabelKeySelectItem.
 */
public class LabelKeySelectItem extends SelectItem {

    /** The label key. */
    String labelKey;

    /**
     * Instantiates a new label key select item.
     *
     * @param value the value
     * @param labelKey the label key
     */
    public LabelKeySelectItem(final Object value, final String labelKey) {
        this.setValue(value);
        this.labelKey = labelKey;
    }

    /* (non-Javadoc)
     * @see javax.faces.model.SelectItem#getLabel()
     */
    @Override
    public String getLabel() {
        return Messages.get(this.labelKey);
    }

    /**
     * Gets the label key.
     *
     * @return the label key
     */
    public String getLabelKey() {
        return this.labelKey;
    }

    /**
     * Sets the label key.
     *
     * @param labelKey the new label key
     */
    public void setLabelKey(final String labelKey) {
        this.labelKey = labelKey;
    }
}
