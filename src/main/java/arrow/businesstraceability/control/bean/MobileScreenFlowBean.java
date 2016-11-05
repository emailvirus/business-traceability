package arrow.businesstraceability.control.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.cdi.ViewScoped;

import arrow.businesstraceability.constant.MobilePageId;
import arrow.businesstraceability.control.helper.MobileContext;
import arrow.framework.faces.util.FacesELUtils;
import arrow.framework.logging.BaseLogger;


/**
 * The Class MobileScreenFlowBean.
 */
@Named
@ViewScoped
public class MobileScreenFlowBean implements Serializable {

    /** The logger. */
    @Inject
    private BaseLogger logger;

    /** The list contexts. */
    LinkedList<MobileContext> listContexts = new LinkedList<MobileContext>();

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        this.gotoHome();
    }

    /**
     * Goto page.
     *
     * @param pageId the page id
     * @return the string
     */
    public String gotoPage(final String pageId) {
        return this.gotoPage(pageId, false);
    }

    /**
     * Goto page.
     *
     * @param pageId the page id
     * @param isGoBack the is go back
     * @return the string
     */
    public String gotoPage(final String pageId, final boolean isGoBack) {
        MobileContext context = new MobileContext(pageId);
        this.listContexts.push(context);


        // execute callback
        if (!StringUtils.isEmpty(this.callbackExpression)) {

            FacesELUtils.invokeMethodExpression(FacesELUtils.buildEL(this.callbackExpression));
            this.callbackExpression = StringUtils.EMPTY;
            this.actionTargets.clear();
        }
        return context.getOutcome(isGoBack);
    }

    /**
     * Gets the current context.
     *
     * @return the current context
     */
    public MobileContext getCurrentContext() {
        return this.listContexts.getFirst();
    }

    /**
     * Goto home.
     *
     * @return the string
     */
    public String gotoHome() {
        this.listContexts.clear();
        return this.gotoPage(MobilePageId.HOME, true);
    }

    /**
     * Go back.
     *
     * @return the string
     */
    public String goBack() {
        // remove current context
        this.listContexts.pop();
        if (this.listContexts.isEmpty()) {
            // empty
            this.logger.debug("got problem: Empty Screens");
            return this.gotoHome();
        } else {
            MobileContext previousContext = this.listContexts.getFirst();
            return previousContext.getOutcome(true);
        }
    }

    /**
     * Gets the prev context.
     *
     * @return the prev context
     */
    public MobileContext getPrevContext() {
        if (this.listContexts.isEmpty()) {
            return null;
        }
        return this.listContexts.get(this.listContexts.size() - 1);
    }


    /**
     * Gets the callback expression.
     *
     * @return the callback expression
     */
    public String getCallbackExpression() {
        return this.callbackExpression;
    }

    /**
     * Sets the callback expression.
     *
     * @param callbackExpression the new callback expression
     */
    public void setCallbackExpression(final String callbackExpression) {
        this.callbackExpression = callbackExpression;
    }


    /**
     * Gets the action targets.
     *
     * @return the action targets
     */
    public Object[] getActionTargets() {
        return this.actionTargets.toArray();
    }

    /**
     * Sets the action target.
     *
     * @param actionTargets the new action target
     */
    public void setActionTarget(final Object actionTargets) {
        this.actionTargets.add(actionTargets);
    }

    /** The callback expression. */
    private String callbackExpression;

    /** The action targets. */
    private final List<Object> actionTargets = new ArrayList<>();

}
