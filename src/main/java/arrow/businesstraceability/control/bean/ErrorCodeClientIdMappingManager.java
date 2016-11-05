package arrow.businesstraceability.control.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

import arrow.framework.util.StringUtils;


/**
 * The Class ErrorCodeClientIdMappingManager.
 */
@ViewScoped
@Named
public class ErrorCodeClientIdMappingManager implements Serializable {

    /** The message code client ids map. */
    final Map<String, List<String>> messageCodeClientIdsMap = new HashMap<String, List<String>>();

    /**
     * Gets the message code client ids map.
     *
     * @return the message code client ids map
     */
    public Map<String, List<String>> getMessageCodeClientIdsMap() {
        return this.messageCodeClientIdsMap;
    }

    /**
     * Put. Input value errorCodes is string contains list of error codes separated by comma Eg: errorCode1,
     * errorCode2..., errorCode2
     *
     * @param errorCodes the error codes
     * @param clientId the client id
     */
    public void put(final String errorCodes, final String clientId) {
        if (StringUtils.isNotEmpty(errorCodes)) {
            final String[] codes = errorCodes.split(",");

            for (String errorCode : codes) {
                errorCode = errorCode.trim();

                if (StringUtils.isNotEmpty(errorCode)) {
                    if (this.messageCodeClientIdsMap.containsKey(errorCode)) {
                        if (!this.messageCodeClientIdsMap.get(errorCode).contains(clientId)) {
                            this.messageCodeClientIdsMap.get(errorCode).add(clientId);
                        }
                    }
                    else {
                        final List<String> clientIds = new ArrayList<>();
                        clientIds.add(clientId);
                        this.messageCodeClientIdsMap.put(errorCode, clientIds);
                    }
                }
            }
        }

    }
}
