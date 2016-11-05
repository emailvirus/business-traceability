package arrow.businesstraceability.control.bean;

import java.io.Serializable;

import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;

import org.omnifaces.cdi.ViewScoped;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

import arrow.businesstraceability.search.SearchService;
import arrow.businesstraceability.search.SearchServiceFactory;
import arrow.framework.faces.component.pane.ModalPanel;
import arrow.framework.faces.util.FacesELUtils;
import arrow.framework.logging.BaseLogger;


/**
 * The Class SearchPanel.
 */
@Named
@ViewScoped
public class SearchPanel extends ModalPanel {

    /** The search service. */
    private SearchService searchService = new SearchService();

    /** The em main. */
    @Inject
    private EntityManager emMain;

    /** The log. */
    @Inject
    private BaseLogger log;

    /** The value expression. */
    private MethodExpression valueExpression;

    /** The search service type. */
    private String searchServiceType;

    /** The select result re render. */
    private String selectResultReRender;

    /** The select result action. */
    private Object selectResultAction;

    /** The select result validator. */
    private String selectResultValidator;

    /** The selected result. */
    private Serializable selectedResult;

    /** The searched. */
    private boolean searched = false;

    /** The on result select. */
    private String onResultSelect;

    /**
     * Get search service.
     *
     * @return the search service
     */
    public SearchService getSearchService() {
        if (this.searchService == null) {
            this.searchService = SearchServiceFactory.createSearchService(this.emMain, this.searchServiceType);
        }
        return this.searchService;
    }

    /**
     * Sets the search service.
     *
     * @param searchService the new search service
     */
    public void setSearchService(final SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * Select Result.
     *
     * @param selectedResult the selected result
     */
    @SuppressWarnings(value = "RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT",
        justification = "Cheat to UI component force get parent component")
    public void selectResult(final Serializable selectedResult) {
        UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getParent();
        this.selectedResult = selectedResult;

        try {
            if (this.selectResultAction != null) {
                if (this.selectResultAction instanceof String) {
                    FacesELUtils.invokeMethodExpression(this.selectResultAction.toString());
                }
            }
            this.invokeAdditionalActions();
        } catch (final ELException e) {
            ModalPanel.handleELException(e);
        }

        this.hide();
    }

    /**
     * Search button clicked.
     */
    public void searchButtonClicked() {
        this.clearSelectedBeans();
        this.searched = true;
    }

    /* (non-Javadoc)
     * @see arrow.framework.faces.component.pane.ModalPanel#show()
     */
    @Override
    public void show() {
        super.show();
        this.cleanSearchPanel();
    }

    /**
     * Clean search panel.
     */
    private void cleanSearchPanel() {
        this.selectedResult = null;
    }

    // //////////////////////////////////////////////// GETTER/SETTER
    // ///////////////////////////////////////////////
    /**
     * Gets the value expression.
     *
     * @return the value expression
     */
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public MethodExpression getValueExpression() {
        return this.valueExpression;
    }

    /**
     * Sets the value expression.
     *
     * @param valueExpression the new value expression
     */
    public void setValueExpression(final MethodExpression valueExpression) {
        this.valueExpression = valueExpression;
    }

    /**
     * Gets the search service type.
     *
     * @return the search service type
     */
    public String getSearchServiceType() {
        return this.searchServiceType;
    }

    /**
     * Setting search service type.
     *
     * @param searchServiceType the new search service type
     */
    public void setSearchServiceType(final String searchServiceType) {
        this.searchServiceType = searchServiceType;
        this.searchService = SearchServiceFactory.createSearchService(this.emMain, searchServiceType);
        this.setTitle(this.searchService.getTitle());
        this.searched = false;
    }

    /**
     * Adds the order by.
     *
     * @param orderBy the order by
     */
    public void addOrderBy(final Order orderBy) {
        this.getSearchService().addOrder(orderBy);
    }

    /**
     * Sets the additional condition.
     *
     * @param additionalCondition the new additional condition
     */
    public void setAdditionalCondition(final Predicate additionalCondition) {
        this.getSearchService().setAdditionalCondition(additionalCondition);
    }

    /**
     * Gets the select result re render.
     *
     * @return the select result re render
     */
    public String getSelectResultReRender() {
        return this.selectResultReRender;
    }

    /**
     * Sets the select result re render.
     *
     * @param selectResultReRender the new select result re render
     */
    public void setSelectResultReRender(final String selectResultReRender) {
        this.selectResultReRender = selectResultReRender;
    }

    /**
     * Gets the select result action.
     *
     * @return the select result action
     */
    public Object getSelectResultAction() {
        return this.selectResultAction;
    }

    /**
     * Sets the select result action.
     *
     * @param selectResultAction the new select result action
     */
    public void setSelectResultAction(final Object selectResultAction) {
        this.selectResultAction = selectResultAction;
    }

    /**
     * Gets the select result validator.
     *
     * @return the select result validator
     */
    public String getSelectResultValidator() {
        return this.selectResultValidator;
    }

    /**
     * Sets the select result validator.
     *
     * @param selectResultValidator the new select result validator
     */
    public void setSelectResultValidator(final String selectResultValidator) {
        this.selectResultValidator = selectResultValidator;
    }

    /**
     * Gets the selected result.
     *
     * @return the selected result
     */
    public Serializable getSelectedResult() {
        return this.selectedResult;
    }

    /**
     * Sets the selected result.
     *
     * @param selectedResult the new selected result
     */
    public void setSelectedResult(final Serializable selectedResult) {
        this.selectedResult = selectedResult;
    }

    /**
     * Return converted string of the selected result using specific converter. Notes that if the converted is not
     * matched with the selected result type, return null string.
     *
     * @param converter the converter
     * @return the selected result string
     */
    public String getSelectedResultString(final Converter converter) {
        try {
            final String strValue =
                converter.getAsString(FacesContext.getCurrentInstance(), null, this.getSelectedResult());
            return strValue;
        } catch (final ConverterException exp) {
            this.log.debug("EXCEPTION IN GET SELECTED RESULTS STRING, "
                + "MAY BE GET CURRENT INSTANCE OF FACECONTEXT OR GET AS STRING OF CONVERTER", exp);
            return null;
        }
    }

    /**
     * Checks if is searched.
     *
     * @return true, if is searched
     */
    public boolean isSearched() {
        return this.searched;
    }

    /**
     * Gets the on result select.
     *
     * @return the on result select
     */
    public String getOnResultSelect() {
        return this.onResultSelect;
    }

    /**
     * Sets the on result select.
     *
     * @param onResultSelect the new on result select
     */
    public void setOnResultSelect(final String onResultSelect) {
        this.onResultSelect = onResultSelect;
    }

    /**
     * Get all selected value.
     *
     * @return the selected all
     */
    public boolean getSelectedAll() {
        boolean selectedAll = true;
        if (this.getSelectedBeans().isEmpty()
            || (this.getSelectedBeans().size() != this.getSearchService().getSearchResults().size())) {
            selectedAll = false;
        }
        return selectedAll;
    }

    /**
     * Setting select all.
     *
     * @param bol the new selected all
     */
    public void setSelectedAll(final boolean bol) {
        if (bol) {
            for (final Serializable result : this.searchService.getSearchResults()) {
                this.getSelectedBeans().add(result);
            }
        } else {
            this.getSelectedBeans().clear();
        }
    }
}
