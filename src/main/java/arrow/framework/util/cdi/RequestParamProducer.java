package arrow.framework.util.cdi;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.util.cdi.qualifier.RequestParam;


/**
 * The Class RequestParamProducer.
 */
public class RequestParamProducer implements Serializable {

    /** The faces context. */
    @Inject
    FacesContext facesContext;

    /**
     * Gets the request parameter.
     *
     * @param ip the ip
     * @return the request parameter
     */
    // Producer for @RequestParam
    @Produces
    @RequestParam
    String getRequestParameter(final InjectionPoint ip) {
        String name = ip.getAnnotated().getAnnotation(RequestParam.class).value();

        if (StringUtils.isEmpty(name)) {
            name = ip.getMember().getName();
        }

        return this.facesContext.getExternalContext().getRequestParameterMap().get(name);
    }
}
