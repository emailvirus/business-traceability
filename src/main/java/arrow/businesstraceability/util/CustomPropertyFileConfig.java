package arrow.businesstraceability.util;

import org.apache.deltaspike.core.api.config.PropertyFileConfig;



/**
 * The Class CustomPropertyFileConfig.
 */
public class CustomPropertyFileConfig implements PropertyFileConfig {

    /* (non-Javadoc)
     * @see org.apache.deltaspike.core.api.config.PropertyFileConfig#getPropertyFileName()
     */
    @Override
    public String getPropertyFileName() {
        return "secretKey.properties";
    }

    /* (non-Javadoc)
     * @see org.apache.deltaspike.core.api.config.PropertyFileConfig#isOptional()
     */
    @Override
    public boolean isOptional() {
        return false;
    }
}
