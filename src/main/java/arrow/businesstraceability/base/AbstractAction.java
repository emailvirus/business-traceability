package arrow.businesstraceability.base;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;


/**
 * The Class AbstractAction.
 */
public abstract class AbstractAction implements Serializable {

    /** The cdi conversation. */
    @Inject
    private Conversation cdiConversation;

    /**
     * Init data when start bean.
     */
    @PostConstruct
    public void startConversation() {
        // TODO: handle conversation nesting
        if (this.cdiConversation.isTransient()) {
            this.cdiConversation.begin();
        }
    }

    /**
     * Pre destroy.
     */
    @PreDestroy
    public void preDestroy() {}

}
