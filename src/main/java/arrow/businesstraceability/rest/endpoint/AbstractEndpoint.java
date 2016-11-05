package arrow.businesstraceability.rest.endpoint;

import java.io.Serializable;

import javax.inject.Inject;

import arrow.framework.logging.BaseLogger;

public class AbstractEndpoint implements Serializable {

    @Inject
    protected BaseLogger log;
}
