package arrow.framework.faces.component.dialog;

import javax.faces.component.FacesComponent;

import org.primefaces.component.dialog.Dialog;


/**
 * The Class ArrowDialog.
 */
@FacesComponent(ArrowDialog.COMPONENT_TYPE)
public class ArrowDialog extends Dialog {

    /** The Constant COMPONENT_TYPE. */
    public static final String COMPONENT_TYPE = "arrow.framework.faces.component.dialog.ArrowDialog";

    /* (non-Javadoc)
     * @see org.primefaces.component.dialog.Dialog#isVisible()
     */
    // To Fix issue: Modal dialog cannot reopened after closed by x button.
    @Override
    public boolean isVisible() {
        return true;
    }
}
