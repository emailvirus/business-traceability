package arrow.businesstraceability.debug;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.exception.ArrException;
import arrow.framework.faces.component.pane.ModalPanel;


/**
 * The Class DebugModalBean.
 */
@Named
@ViewScoped
public class DebugModalBean extends ModalPanel {

    /**
     * Test action.
     *
     * @throws ArrException the arr exception
     */

    public void testAction() throws ArrException {
        System.out.println("Test");
        this.hide();

        throw new ArrException("test rollback");
    }
}
