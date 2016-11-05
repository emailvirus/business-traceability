package arrow.framework.faces.component.taghandler;

import java.io.IOException;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;

import com.sun.faces.facelets.el.VariableMapperWrapper;

import arrow.framework.bean.NopBean;
import arrow.framework.faces.util.FacesELUtils;
import arrow.framework.util.StringUtils;


/**
 * The Class InputEntityActionMapper.
 */
public class InputEntityActionMapper extends TagHandler {

    /** The Constant LISTENER. */
    public static final String LISTENER = "listener";

    /** The Constant VALIDATOR. */
    public static final String VALIDATOR = "validator";

    /** The Constant MAPPED_LISTENER. */
    public static final String MAPPED_LISTENER = "mappedListener";

    /** The Constant MAPPED_VALIDATOR. */
    public static final String MAPPED_VALIDATOR = "mappedValidator";

    /** The Constant VALIDATOR_PARAM_TYPES. */
    private static final Class<?>[] VALIDATOR_PARAM_TYPES =
            new Class[] {FacesContext.class, UIComponent.class, Object.class};

    /**
     * Instantiates a new input entity action mapper.
     *
     * @param config the config
     */
    public InputEntityActionMapper(final TagConfig config) {
        super(config);
    }

    /* (non-Javadoc)
     * @see javax.faces.view.facelets.FaceletHandler<br />
     * #apply(javax.faces.view.facelets.FaceletContext, javax.faces.component.UIComponent)
     */
    @Override
    public void apply(final FaceletContext ctx, final UIComponent parent) throws IOException {
        final VariableMapper originalVarMapper = ctx.getVariableMapper();
        final VariableMapper wrappedVarMapper = new VariableMapperWrapper(originalVarMapper);

        this.setupMappedListener(originalVarMapper, wrappedVarMapper);

        this.setupMappedValidator(originalVarMapper, wrappedVarMapper);

        this.setupSearchPanelSelectExpression(originalVarMapper, wrappedVarMapper);

        this.validateNonEmptyId(originalVarMapper);

        ctx.setVariableMapper(wrappedVarMapper);

        this.nextHandler.apply(ctx, parent);
    }

    /**
     * Validate non empty id.
     *
     * @param originalVarMapper the original var mapper
     */
    private void validateNonEmptyId(final VariableMapper originalVarMapper) {
        final ValueExpression inputValueExpression = originalVarMapper.resolveVariable("id");
        if (inputValueExpression == null) {
            throw new IllegalArgumentException("You must specify id for syn:inputEntity tag");
        }
    }

    /**
     * Setup mapped listener.
     *
     * @param originalVarMapper the original var mapper
     * @param wrappedVarMapper the wrapped var mapper
     */
    private void setupMappedListener(final VariableMapper originalVarMapper, final VariableMapper wrappedVarMapper) {
        final ValueExpression listenerValueExpression =
                originalVarMapper.resolveVariable(InputEntityActionMapper.LISTENER);
        String strExpression = null;
        if (listenerValueExpression != null) {
            strExpression = listenerValueExpression.getExpressionString();
        }
        else {
            strExpression = NopBean.NOP_METHOD_EXPRESSION;
        }
        final MethodExpression methodExpression =
                FacesELUtils.createMethodExpression(strExpression, String.class, new Class[0]);
        final ValueExpression mappedListenerExpression =
                FacesELUtils.createValueExpressionFromValue(methodExpression, MethodExpression.class);
        wrappedVarMapper.setVariable(InputEntityActionMapper.MAPPED_LISTENER, mappedListenerExpression);
    }

    /**
     * Setup mapped validator.
     *
     * @param originalVarMapper the original var mapper
     * @param varMapper the var mapper
     */
    private void setupMappedValidator(final VariableMapper originalVarMapper, final VariableMapper varMapper) {
        final ValueExpression validatorValueExpression =
                originalVarMapper.resolveVariable(InputEntityActionMapper.VALIDATOR);
        String strValidatorExpression = null;
        if (validatorValueExpression != null) {
            strValidatorExpression = validatorValueExpression.getExpressionString();
        }
        else {
            strValidatorExpression = NopBean.NOP_VALIDATOR_EXPRESSION;
        }
        final MethodExpression validatorMethodExpression = FacesELUtils.createMethodExpression(strValidatorExpression,
                Void.class, InputEntityActionMapper.VALIDATOR_PARAM_TYPES);
        final ValueExpression mappedValidatorExpression =
                FacesELUtils.createValueExpressionFromValue(validatorMethodExpression, MethodExpression.class);
        varMapper.setVariable(InputEntityActionMapper.MAPPED_VALIDATOR, mappedValidatorExpression);
    }

    /**
     * Setup search panel select expression.
     *
     * @param originalVarMapper the original var mapper
     * @param varMapper the var mapper
     */
    /*
     * #{bean1.bean2.attribute} -> #{bean1.bean2.setAttribute(searchPanel.selectedResult)}
     */
    private void setupSearchPanelSelectExpression(final VariableMapper originalVarMapper,
            final VariableMapper varMapper) {
        final String expression = originalVarMapper.resolveVariable("value").getExpressionString();
        final String[] pieces = expression.substring(2, expression.length() - 1).split("\\.");
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pieces.length; i++) {
            if (i < (pieces.length - 1)) {
                sb.append(pieces[i] + ".");
            }
            else {
                sb.append("set" + StringUtils.makeUpperCaseFirstCharKeepRemaingUnchanged(pieces[i])
                        + "(searchPanel.selectedResult)");
            }
        }
        final MethodExpression entitySetterMethodExpression =
                FacesELUtils.createMethodExpression(sb.toString(), Void.class, new Class[0]);
        final ValueExpression entitySetterExpression =
                FacesELUtils.createValueExpressionFromValue(entitySetterMethodExpression, MethodExpression.class);
        varMapper.setVariable("mappedSetter", entitySetterExpression);
    }
}
