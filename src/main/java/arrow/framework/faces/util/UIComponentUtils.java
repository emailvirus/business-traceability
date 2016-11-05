package arrow.framework.faces.util;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIForm;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;


/**
 * The Class UIComponentUtils.
 */
public class UIComponentUtils {

    /**
     * Get containing form.
     *
     * @param component the component
     * @return the containing form
     */
    public static UIForm getContainingForm(final UIComponent component) {
        final UIComponent parentComponent = component.getParent();

        if (parentComponent == null) {
            return null;
        }

        if (parentComponent instanceof UIForm) {
            return (UIForm) parentComponent;
        }

        return UIComponentUtils.getContainingForm(parentComponent);
    }

    /**
     * Find component by id.
     *
     * @param <T> the generic type
     * @param clazz the clazz
     * @param id must be the client id, NOT the JSF id. Otherwise we can't ensure the uniqueness.
     * @return the t
     */
    @SuppressWarnings("unchecked")
    public static <T extends UIComponent> T findComponentById(final Class<T> clazz, final String id) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null) {
            throw new IllegalStateException("FacesContext not available");
        }

        final UIViewRoot viewRoot = facesContext.getViewRoot();
        if (viewRoot == null) {
            throw new IllegalStateException("ViewRoot not available");
        }

        try {
            return (T) viewRoot.findComponent(id);
        } catch (final ClassCastException e) {
            return null;
        }
    }

    /**
     * Notice: if the specified <code>component</code> is an instance of {@link UIData}, that UIData is not counted,
     * because it's not a 'containing' one.
     *
     * @param component the component
     * @return the containing ui data
     */
    public static UIData getContainingUIData(final UIComponent component) {
        if (component.getParent() == null) {
            return null;
        }
        else if (component.getParent() instanceof UIData) {
            return (UIData) component.getParent();
        }
        else {
            return UIComponentUtils.getContainingUIData(component.getParent());
        }
    }

    /**
     * Reset submitting value for the JSF component that is EditableValueHolder.
     *
     * @param component the component
     */
    public static void resetInputValueIfPossible(final UIComponent component) {
        if (component instanceof EditableValueHolder) {
            ((EditableValueHolder) component).resetValue();
        }
    }
}
