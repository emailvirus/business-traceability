package arrow.framework.faces.component.mobile.toolbar;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.toolbar.Toolbar;
import org.primefaces.mobile.renderkit.ToolbarRenderer;


/**
 * The Class ArrowToolbarRenderer.
 */
public class ArrowToolbarRenderer extends ToolbarRenderer {

    /* (non-Javadoc)
     * @see org.primefaces.mobile.renderkit.ToolbarRenderer<br />
     * #encodeGroup(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
     */
    @Override
    protected void encodeGroup(final FacesContext context, final UIComponent group, final String styleClass)
            throws IOException {
        if (group == null) {
            return;
        }
        ResponseWriter writer = context.getResponseWriter();


        writer.startElement("div", null);
        writer.writeAttribute("class", styleClass, null);

        writer.startElement("div", null);
        writer.writeAttribute("class", Toolbar.MOBILE_GROUP_CONTAINER_CLASS, null);
        group.encodeAll(context);
        writer.endElement("div");

        writer.endElement("div");
    }
}
