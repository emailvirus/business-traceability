package arrow.framework.faces.component.mobile.calendar;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.mobile.renderkit.CalendarRenderer;


/**
 * The Class ArrowCalendarRenderer.
 */
public class ArrowCalendarRenderer extends CalendarRenderer {

    /* (non-Javadoc)
     * @see org.primefaces.mobile.renderkit.CalendarRenderer<br />
     * #encodeMarkup(javax.faces.context.FacesContext, org.primefaces.component.calendar.Calendar, java.lang.String)
     */
    @Override
    protected void encodeMarkup(final FacesContext context, final Calendar calendar, final String value)
            throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        String clientId = calendar.getClientId(context);
        String inputId = clientId + "_input";
        String containerClass =
                calendar.isPopup() ? Calendar.MOBILE_POPUP_CONTAINER_CLASS : Calendar.MOBILE_INLINE_CONTAINER_CLASS;
        String style = calendar.getStyle();
        String styleClass = calendar.getStyleClass();
        styleClass = (styleClass == null) ? containerClass : containerClass + " " + styleClass;

        writer.startElement("span", calendar);
        writer.writeAttribute("id", clientId, null);
        writer.writeAttribute("class", styleClass, null);
        if (style != null) {
            writer.writeAttribute("style", style, null);
        }

        this.encodeInput(context, calendar, inputId, value);

        writer.endElement("span");
    }

}
