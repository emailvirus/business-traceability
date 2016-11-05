package arrow.framework.faces.component.picklist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.column.Column;
import org.primefaces.component.picklist.PickList;
import org.primefaces.component.picklist.PickListRenderer;


/**
 * The Class ArrowPickListRenderer.
 */
public class ArrowPickListRenderer extends PickListRenderer {

    /** The sub columns. */
    private final List<Column> subColumns = new ArrayList<>();

    /* (non-Javadoc)
     * @see org.primefaces.component.picklist.PickListRenderer<br />
     * #encodeCaption(javax.faces.context.FacesContext, javax.faces.component.UIComponent)
     */
    @Override
    protected void encodeCaption(final FacesContext context, final UIComponent caption) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();

        writer.startElement("div", null);
        writer.writeAttribute("class", PickList.CAPTION_CLASS, null);
        caption.encodeAll(context);

        if (!this.subColumns.isEmpty()) {

            writer.startElement("table", null);
            writer.writeAttribute("style", "arrow-picklist-sub-caption", null);
            writer.startElement("thead", null);
            for (final Column column : this.subColumns) {
                if (!column.isRendered()) {
                    return;
                }

                final String headerText = column.getHeaderText();
                final UIComponent headerFacet = column.getFacet("header");
                final String styleClass =
                        column.getStyleClass() == null ? "ui-state-default" : "ui-state-default "
                                + column.getStyleClass();

                writer.startElement("th", null);
                writer.writeAttribute("class", styleClass, null);

                if (column.getStyle() != null) {
                    writer.writeAttribute("style", column.getStyle(), null);
                }

                if (headerFacet != null) {
                    headerFacet.encodeAll(context);
                }
                else if (headerText != null) {
                    writer.write(headerText);
                }

                writer.endElement("th");
            }
            writer.endElement("thead");
            writer.endElement("table");
        }

        writer.endElement("div");
    }

    /* (non-Javadoc)
     * @see org.primefaces.component.picklist.PickListRenderer<br />
     * #encodeList(javax.faces.context.FacesContext, org.primefaces.component.picklist.PickList, <br />
     * java.lang.String, java.lang.String, java.util.List, javax.faces.component.UIComponent, boolean)
     */
    @SuppressWarnings("rawtypes")
    @Override
    protected void encodeList(final FacesContext context, final PickList pickList, final String listId,
            String styleClass, final List model, final UIComponent caption, final boolean filter) throws IOException {
        final ResponseWriter writer = context.getResponseWriter();

        writer.startElement("td", null);

        if (filter) {
            this.encodeFilter(context, pickList, listId + "_filter");
        }

        if (caption != null) {
            this.subColumns.clear();
            for (final UIComponent child : pickList.getChildren()) {
                if ((child instanceof Column) && child.isRendered()) {
                    final Column column = (Column) child;
                    if (((column.getHeaderText() != null) || (column.getFacet("header") != null))) {
                        this.subColumns.add(column);
                    }
                }
            }

            this.encodeCaption(context, caption);
            styleClass += " ui-corner-bottom";
        }
        else {
            styleClass += " ui-corner-all";
        }

        writer.startElement("ul", null);
        writer.writeAttribute("class", styleClass, null);

        this.encodeOptions(context, pickList, model);

        writer.endElement("ul");

        this.encodeListInput(context, listId);

        writer.endElement("td");
    }

}
