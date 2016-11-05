/*
 * package: arrow.framework.util.solder.cdi.bean file: NopBean.java time: Jun 23, 2014
 * @author Hugh
 * @author Michael
 */
package arrow.framework.bean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;


/**
 * The NopBean (No Operation Bean).
 */
@ApplicationScoped
@Named
public class NopBean extends AbstractBean {

    /**
     * Nop method.
     */
    public void nopMethod() {
        // Do nothing
    }

    /**
     * Nop validator.
     *
     * @param context the context
     * @param component the component
     * @param value the value
     */
    public void nopValidator(final FacesContext context, final UIComponent component, final Object value) {
        // Do nothing
    }

    /** The Constant NOP_METHOD_EXPRESSION. */
    public static final String NOP_METHOD_EXPRESSION = "#{nopBean.nopMethod}";

    /** The Constant NOP_VALIDATOR_EXPRESSION. */
    public static final String NOP_VALIDATOR_EXPRESSION = "#{nopBean.nopValidator}";
}
