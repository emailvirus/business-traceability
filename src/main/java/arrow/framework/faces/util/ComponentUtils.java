package arrow.framework.faces.util;

import javax.faces.component.UIComponent;


public class ComponentUtils {

    @SuppressWarnings("unchecked")
    public static <T> T getAttribute(final UIComponent component, final String key) {
        return (T) component.getAttributes().get(key);
    }

}
