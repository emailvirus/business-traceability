package arrow.businesstraceability.control.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.omnifaces.util.Faces;

import arrow.businesstraceability.control.helper.ScreenContext;
import arrow.framework.logging.BaseLogger;


/**
 * The Class ScreenBean.
 *
 * @author ducva
 */
@Named
@ConversationScoped
public class ScreenBean implements Serializable {

    /** The em main. */
    @Inject
    protected EntityManager emMain;

    /** The screen contexts. */
    private LinkedList<ScreenContext> screenContexts = new LinkedList<ScreenContext>();

    /** The logger. */
    @Inject
    private BaseLogger logger;

    /** The cdi conversation. */
    @Inject
    private Conversation cdiConversation;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        this.logger.debug("Init ScreenBean");
        if (this.cdiConversation.isTransient()) {
            this.cdiConversation.begin();
        }
    }

    /**
     * Destroy.
     */
    @PreDestroy
    public void destroy() {
        this.logger.debug("Destroy ScreenBean");
    }

    /**
     * Clear view stage.
     */
    private void clearViewStage() {
        // Clear all ViewScoped beans
        Faces.getViewMap().clear();
        this.emMain.clear();
    }

    /**
     * Open next screen.
     *
     * @param screenCode the screen code
     */
    public void openNextScreen(final String screenCode) {
        final ScreenContext context = new ScreenContext(screenCode);
        this.switchScreen(context, false);
    }

    /**
     * Switch screen.
     *
     * @param screenCode the screen code
     * @param isClearStage the is clear stage
     * @param attributes the attributes
     */
    public void switchScreen(final String screenCode, final boolean isClearStage, final Map<String, String> attributes) {
        final ScreenContext context = new ScreenContext(screenCode);
        context.setAttributes(attributes);
        this.switchScreen(context, isClearStage);
    }

    /**
     * Switch screen with object data.
     *
     * @param screenCode the screen code
     * @param isClearStage the is clear stage
     * @param attributes the attributes
     */
    public void switchScreenWithObjectData(final String screenCode, final boolean isClearStage,
        final Map<String, Object> attributes) {
        final ScreenContext context = new ScreenContext(screenCode);
        context.setObjectAttributes(attributes);
        this.switchScreen(context, isClearStage);
    }

    /**
     * Switch screen.
     *
     * @param screenCode the screen code
     * @param isClearStage the is clear stage
     */
    public void switchScreen(final String screenCode, final boolean isClearStage) {
        final ScreenContext context = new ScreenContext(screenCode);
        this.switchScreen(context, isClearStage);
    }

    /**
     * Switch screen.
     *
     * @param screenCode the screen code
     */
    public void switchScreen(final String screenCode) {
        final ScreenContext context = new ScreenContext(screenCode);
        this.switchScreen(context);
    }

    /**
     * Switch screen.
     *
     * @param context the context
     * @param isClearStage the is clear stage
     */
    private void switchScreen(final ScreenContext context, final boolean isClearStage) {
        if (this.screenContexts == null) {
            this.screenContexts = new LinkedList<ScreenContext>();
        }

        this.screenContexts.addLast(context);

        if (isClearStage) {
            this.clearViewStage();
        }
    }

    /**
     * Switch screen.
     *
     * @param context the context
     */
    private void switchScreen(final ScreenContext context) {
        if (this.screenContexts == null) {
            this.screenContexts = new LinkedList<ScreenContext>();
        }

        this.screenContexts.addLast(context);
        this.clearViewStage();
    }

    /**
     * Switch root screen.
     *
     * @param context the context
     */
    public void switchRootScreen(final ScreenContext context) {
        this.screenContexts = new LinkedList<ScreenContext>();
        this.screenContexts.addLast(context);
        this.clearViewStage();
    }

    /**
     * Switch root screen.
     *
     * @param screenCode the screen code
     */
    public void switchRootScreen(final String screenCode) {
        this.switchRootScreen(new ScreenContext(screenCode));
    }

    /**
     * Switch to home.
     */
    public void switchToHome() {
        this.screenContexts = new LinkedList<ScreenContext>();
        this.clearViewStage();
    }

    /**
     * Gets the current screen context.
     *
     * @return the current screen context
     */
    public ScreenContext getCurrentScreenContext() {
        if (this.screenContexts.isEmpty()) {
            return null;
        }

        return this.screenContexts.getLast();
    }

    /**
     * Close.
     */
    // TODO: Close and go back to previous screen.
    public void close() {
        this.screenContexts.pop();
    }
}
