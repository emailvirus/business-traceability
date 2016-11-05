package arrow.businesstraceability.control.service;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;


/**
 * The Class GreetingService.
 */
@Named
@ConversationScoped
public class GreetingService implements Serializable {

    /**
     * Greet.
     *
     * @param name the name
     * @return the string
     */

    public String greet(final String name) {
        return "Hello " + name + "!";
    }
}
