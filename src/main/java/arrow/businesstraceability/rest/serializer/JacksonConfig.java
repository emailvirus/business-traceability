package arrow.businesstraceability.rest.serializer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import arrow.businesstraceability.restfull.module.BusinessTraceabilityMixinModule;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JacksonConfig implements ContextResolver<ObjectMapper> {
    private final ObjectMapper objectMapper;

    /**
     * Instantiates a new jackson config.
     *
     * @throws Exception the exception
     */
    public JacksonConfig() {
        this.objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.objectMapper.setDateFormat(df).registerModule(new BusinessTraceabilityMixinModule());

        this.objectMapper.setVisibilityChecker(this.objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
            .withCreatorVisibility(JsonAutoDetect.Visibility.NONE).withFieldVisibility(JsonAutoDetect.Visibility.NONE)
            .withGetterVisibility(JsonAutoDetect.Visibility.NONE).withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
            .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public ObjectMapper getContext(final Class<?> objectType) {
        return this.objectMapper;
    }
}
