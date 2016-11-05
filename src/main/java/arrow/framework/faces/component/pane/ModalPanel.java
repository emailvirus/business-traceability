package arrow.framework.faces.component.pane;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.el.ELException;
import javax.el.MethodExpression;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

import arrow.framework.faces.util.FacesELUtils;

/**
 * The Class ModalPanel.
 */
public abstract class ModalPanel implements Serializable {

    /** The Constant ORIGINAL_MAX_HEIGHT. */
    public static final int ORIGINAL_MAX_HEIGHT = 600;

    /** The Constant ORIGINAL_MAX_WIDTH. */
    public static final int ORIGINAL_MAX_WIDTH = 800;

    /** The Constant ORIGINAL_MIN_WIDTH. */
    public static final int ORIGINAL_MIN_WIDTH = 400;

    /** The Constant ORIGINAL_TOP. */
    public static final int ORIGINAL_TOP = 5;

    /**
     * The messages.
     */
    private final List<String> messages = new ArrayList<String>();

    /**
     * The include param names.
     */
    private final List<String> includeParamNames = new ArrayList<String>();

    /**
     * The include params.
     */
    private final Map<String, Object> includeParams = Collections.synchronizedMap(new HashMap<String, Object>());

    /**
     * The action targets.
     */
    private final ArrayList<Object> actionTargets = new ArrayList<Object>();

    /**
     * The additional actions.
     */
    private final ArrayList<Object> additionalActions = new ArrayList<Object>();

    /****************************
     * Batch action.
     ****************************/
    private final Set<Serializable> selectedBeans = new HashSet<Serializable>();

    /**
     * The selection map.
     */
    private final Map<Serializable, Boolean> selectionMap =
            Collections.synchronizedMap(new HashMap<Serializable, Boolean>() {

                @Override
                public Boolean get(final Object obj) {
                    return ModalPanel.this.getSelectedBeans().contains(obj);
                }

                @SuppressWarnings(value = "NP_BOOLEAN_RETURN_NULL", justification = "Need return null")
                @Override
                public Boolean put(final Serializable obj, final Boolean booleanValue) {
                    if ((booleanValue != null) && booleanValue) {
                        ModalPanel.this.selectedBeans.add(obj);
                    }
                    else {
                        ModalPanel.this.selectedBeans.remove(obj);
                    }

                    return null;
                }
            });

    /**
     * The title.
     */
    private String title;

    /**
     * The data included.
     */
    private Object dataIncluded;

    /**
     * The limit to list.
     */
    private boolean limitToList;

    /**
     * The rendered.
     */
    private boolean rendered;

    /**
     * The include src.
     */
    private String includeSrc;

    /**
     * The default dismiss button.
     */
    private boolean defaultDismissButton = true;

    /**
     * The close popup control.
     */
    private boolean closePopupControl = true;

    /**
     * The re render.
     */
    private String reRender;

    /**
     * The panel button label.
     */
    private String panelButtonLabel;

    /**
     * The panel button action.
     */
    private String panelButtonAction;

    /**
     * The allow batch action.
     */
    private boolean allowBatchAction;

    /**
     * The allow command link.
     */
    private boolean allowCommandLink = true;

    /**
     * The batch action.
     */
    private String batchAction;

    /**
     * The max height.
     */
    private int maxHeight = ModalPanel.ORIGINAL_MAX_HEIGHT;

    /**
     * The max width.
     */
    private int maxWidth = ModalPanel.ORIGINAL_MAX_WIDTH;

    /**
     * The min width.
     */
    private int minWidth = ModalPanel.ORIGINAL_MIN_WIDTH;

    /**
     * The top.
     */
    private int top = ModalPanel.ORIGINAL_TOP;

    /**
     * Handle EL Exception.
     *
     * @param elException the el exception
     */
    public static void handleELException(final ELException elException) {
        // final Throwable cause = e.getCause();
        // if (cause != null) {
        // if (cause instanceof SynException) {
        // ErrorMessages.instance().add((SynException) cause);
        // cause.printStackTrace();
        // }
        // else {
        // throw new RuntimeException(e.getCause());
        // }
        // }
        // else {
        // throw new RuntimeException(e);
        // }
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(final String title) {
        this.cleanup();
        this.title = title;
    }

    /**
     * Cleanup.
     */
    protected void cleanup() {
        this.actionTargets.clear();
        this.additionalActions.clear();
        this.selectedBeans.clear();
        this.includeParamNames.clear();
        this.includeParams.clear();
        this.messages.clear();

        this.allowBatchAction = false;
        this.allowCommandLink = true;
        this.panelButtonLabel = null;
        this.panelButtonAction = null;
        this.includeSrc = null;
        this.dataIncluded = null;
        this.defaultDismissButton = true;
        this.closePopupControl = true;
        this.maxHeight = ModalPanel.ORIGINAL_MAX_HEIGHT;
        this.maxWidth = ModalPanel.ORIGINAL_MAX_WIDTH;
        this.minWidth = ModalPanel.ORIGINAL_MIN_WIDTH;
        this.top = ModalPanel.ORIGINAL_TOP;
    }

    /**
     * Gets the data included.
     *
     * @return the data included
     */
    public Object getDataIncluded() {
        return this.dataIncluded;
    }

    /**
     * Sets the data included.
     *
     * @param dataIncluded the new data included
     */
    public void setDataIncluded(final Object dataIncluded) {
        this.dataIncluded = dataIncluded;
    }

    /**
     * Checks if is limit to list.
     *
     * @return true, if is limit to list
     */
    public boolean isLimitToList() {
        return this.limitToList;
    }

    /**
     * Sets the limit to list.
     *
     * @param limitToList the new limit to list
     */
    public void setLimitToList(final boolean limitToList) {
        this.limitToList = limitToList;
    }

    /**
     * Checks if is rendered.
     *
     * @return true, if is rendered
     */
    public boolean isRendered() {
        return this.rendered;
    }

    /**
     * Show.
     */
    public void show() {
        this.rendered = true;
    }

    /**
     * Hide.
     */
    public void hide() {
        this.rendered = false;
    }

    /**
     * Gets the include src.
     *
     * @return the include src
     */
    public String getIncludeSrc() {
        return this.includeSrc;
    }

    /**
     * Sets the include src.
     *
     * @param src the new include src
     */
    public void setIncludeSrc(final String src) {
        this.cleanup();
        this.includeSrc = src;
    }

    /**
     * Gets the messages.
     *
     * @return the messages
     */
    public List<String> getMessages() {
        return this.messages;
    }

    /**
     * Sets the messages.
     *
     * @param message the new messages
     */
    public void setMessages(final String message) {
        this.messages.add(message);
    }

    /**
     * Gets the include param names.
     *
     * @return the include param names
     */
    public List<String> getIncludeParamNames() {
        return this.includeParamNames;
    }

    /**
     * Gets the include param name.
     *
     * @return the include param name
     */
    public String getIncludeParamName() {
        return null;
    }

    /**
     * Sets the include param name.
     *
     * @param name the new include param name
     */
    public void setIncludeParamName(final String name) {
        this.includeParamNames.add(name);
    }

    /**
     * Gets the include params.
     *
     * @return the include params
     */
    public Map<String, Object> getIncludeParams() {
        return this.includeParams;
    }

    /**
     * Gets the include param.
     *
     * @return the include param
     */
    public Object getIncludeParam() {
        return null;
    }

    /**
     * Sets the include param.
     *
     * @param param the new include param
     */
    public void setIncludeParam(final Object param) {
        this.includeParams.put(this.includeParamNames.get(this.includeParamNames.size() - 1), param);
    }

    /**
     * Gets the action targets.
     *
     * @return the action targets
     */
    public ArrayList<Object> getActionTargets() {
        return this.actionTargets;
    }

    /**
     * Gets the action target.
     *
     * @return the action target
     */
    public Object getActionTarget() {
        return null;
    }

    /**
     * Sets the action target.
     *
     * @param target the new action target
     */
    public void setActionTarget(final Object target) {
        this.actionTargets.add(target);
    }

    /**
     * Set additional action.
     *
     * @param action the new additional action
     */
    public void setAdditionalAction(final Object action) {
        if (action == null) {
            return;
        }
        this.additionalActions.add(action);
    }

    /**
     * Gets the default dismiss button.
     *
     * @return the default dismiss button
     */
    public boolean getDefaultDismissButton() {
        return this.defaultDismissButton;
    }

    /**
     * Sets the default dismiss button.
     *
     * @param booleanValue the new default dismiss button
     */
    public void setDefaultDismissButton(final boolean booleanValue) {
        this.defaultDismissButton = booleanValue;
    }

    /**
     * Checks if is close popup control.
     *
     * @return true, if is close popup control
     */
    public boolean isClosePopupControl() {
        return this.closePopupControl;
    }

    /**
     * Sets the close popup control.
     *
     * @param closePopupControl the new close popup control
     */
    public void setClosePopupControl(final boolean closePopupControl) {
        this.closePopupControl = closePopupControl;
    }

    /**
     * Gets the re render.
     *
     * @return the re render
     */
    public String getReRender() {
        return this.reRender;
    }

    /**
     * Sets the re render.
     *
     * @param reRender the new re render
     */
    public void setReRender(final String reRender) {
        this.reRender = reRender;
    }

    /**
     * Gets the panel button label.
     *
     * @return the panel button label
     */
    public String getPanelButtonLabel() {
        return this.panelButtonLabel;
    }

    /**
     * Sets the panel button label.
     *
     * @param panelButtonLabel the new panel button label
     */
    public void setPanelButtonLabel(final String panelButtonLabel) {
        this.panelButtonLabel = panelButtonLabel;
    }

    /**
     * Gets the panel button action.
     *
     * @return the panel button action
     */
    public String getPanelButtonAction() {
        return this.panelButtonAction;
    }

    /**
     * Sets the panel button action.
     *
     * @param panelButtonAction the new panel button action
     */
    public void setPanelButtonAction(final String panelButtonAction) {
        this.panelButtonAction = panelButtonAction;
    }

    /**
     * Execute action.
     */
    public void executeAction() {
        try {
            if (this.panelButtonAction != null) {
                FacesELUtils.invokeMethodExpression(this.panelButtonAction);
            }
            this.invokeAdditionalActions();
        } catch (final ELException e) {
            ModalPanel.handleELException(e);
        } finally {
            this.hide();
        }
    }

    /**
     * On close.
     */
    public void onClose() {
        this.hide();
    }

    /**
     * Gets the selected beans.
     *
     * @return the selected beans
     */
    public Set<Serializable> getSelectedBeans() {
        return this.selectedBeans;
    }

    /**
     * Clear selected beans.
     */
    public void clearSelectedBeans() {
        if (this.allowBatchAction) {
            this.selectedBeans.clear();
        }
    }

    /**
     * Gets the selection map.
     *
     * @return the selection map
     */
    public Map<Serializable, Boolean> getSelectionMap() {
        return this.selectionMap;
    }

    /**
     * Execute batch action.
     */
    public void executeBatchAction() {
        try {
            if (this.batchAction != null) {
                FacesELUtils.invokeMethodExpression(this.batchAction);
            }
        } catch (final ELException e) {
            ModalPanel.handleELException(e);
        } finally {
            this.clearSelectedBeans();
        }

        this.rendered = false;
    }

    /**
     * Gets the allow batch action.
     *
     * @return the allow batch action
     */
    public boolean getAllowBatchAction() {
        return this.allowBatchAction;
    }

    /**
     * Sets the allow batch action.
     *
     * @param allowBatchAction the new allow batch action
     */
    public void setAllowBatchAction(final boolean allowBatchAction) {
        this.allowBatchAction = allowBatchAction;
    }

    /**
     * Checks if is allow command link.
     *
     * @return true, if is allow command link
     */
    public boolean isAllowCommandLink() {
        return this.allowCommandLink;
    }

    /**
     * Sets the allow command link.
     *
     * @param allowCommandLink the new allow command link
     */
    public void setAllowCommandLink(final boolean allowCommandLink) {
        this.allowCommandLink = allowCommandLink;
    }

    /**
     * Gets the batch action.
     *
     * @return the batch action
     */
    public String getBatchAction() {
        return this.batchAction;
    }

    /**
     * Sets the batch action.
     *
     * @param batchAction the new batch action
     */
    public void setBatchAction(final String batchAction) {
        this.batchAction = batchAction;
    }

    /**
     * Gets the max height.
     *
     * @return the max height
     */
    public int getMaxHeight() {
        return this.maxHeight;
    }

    /**
     * Sets the max height.
     *
     * @param maxHeight the new max height
     */
    public void setMaxHeight(final int maxHeight) {
        this.maxHeight = maxHeight;
    }

    /**
     * Gets the max width.
     *
     * @return the max width
     */
    public int getMaxWidth() {
        return this.maxWidth;
    }

    /**
     * Sets the max width.
     *
     * @param maxWidth the new max width
     */
    public void setMaxWidth(final int maxWidth) {
        this.maxWidth = maxWidth;
    }

    /**
     * Gets the min width.
     *
     * @return the min width
     */
    public int getMinWidth() {
        return this.minWidth;
    }

    /**
     * Sets the min width.
     *
     * @param minWidth the new min width
     */
    public void setMinWidth(final int minWidth) {
        this.minWidth = minWidth;
    }

    /**
     * Gets the int value.
     *
     * @param value the value
     * @return the int value
     */
    public int getIntValue(final int value) {
        return value;
    }

    /**
     * Gets the top.
     *
     * @return the top
     */
    public int getTop() {
        return this.top;
    }

    // ///////////////////////////////////////////////////////////////////////////////////

    /**
     * Sets the top.
     *
     * @param top the new top
     */
    public void setTop(final int top) {
        this.top = top;
    }

    /**
     * Invoke additional actions.
     */
    protected void invokeAdditionalActions() {
        for (final Object action : this.additionalActions) {
            if (action instanceof String) {
                FacesELUtils.invokeMethodExpression(action.toString());
            }
            else if (action instanceof MethodExpression) {
                FacesELUtils.invokeMethodExpression((MethodExpression) action);
            }
        }
    }
}
