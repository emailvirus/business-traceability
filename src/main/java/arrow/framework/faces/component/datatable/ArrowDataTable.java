package arrow.framework.faces.component.datatable;

import javax.faces.component.FacesComponent;

import org.primefaces.component.datatable.DataTable;

import arrow.framework.util.i18n.Messages;


/**
 * The Class ArrowDataTable.
 */
@FacesComponent(ArrowDataTable.COMPONENT_TYPE)
public class ArrowDataTable extends DataTable {

    /** The Constant COMPONENT_TYPE. */
    public static final String COMPONENT_TYPE = "arrow.framework.faces.component.dialog.ArrowDataTable";

    /* (non-Javadoc)
     * @see org.primefaces.component.api.UIData#getPaginatorTemplate()
     */
    @Override
    public String getPaginatorTemplate() {

        return "{CurrentPageReport}  {FirstPageLink} {PreviousPageLink} "
                + "{PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}";
    }

    /* (non-Javadoc)
     * @see org.primefaces.component.datatable.DataTable#getEmptyMessage()
     */
    @Override
    public String getEmptyMessage() {
        return Messages.get("noResults");
    }

}
