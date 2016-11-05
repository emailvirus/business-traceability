package arrow.framework.faces.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import arrow.businesstraceability.persistence.entity.Abstract_entity;
import arrow.framework.faces.util.ConverterUtils;
import arrow.framework.persistence.BasePk;
import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.NumberUtils;
import arrow.framework.util.StringUtils;
import arrow.framework.util.i18n.Messages;


/**
 * The Class EntityConverter.
 *
 * @param <T> the generic type
 */
public class EntityConverter<T> implements javax.faces.convert.Converter {

    /** The entity class. */
    private final Class<T> entityClass;

    /** The current field. */
    private final SingularAttribute<? super T, ?> currentField;

    /** The other fields map. */
    private Map<SingularAttribute<? super T, ?>, Object> otherFieldsMap =
            new HashMap<SingularAttribute<? super T, ?>, Object>();

    /**
     * Instantiates a new entity converter.
     *
     * @param entityClass the entity class
     * @param currentField the current field
     * @param otherFieldsMap the other fields map
     */
    public EntityConverter(final Class<T> entityClass, final SingularAttribute<? super T, ?> currentField,
            final Map<SingularAttribute<? super T, ?>, Object> otherFieldsMap) {
        super();
        this.entityClass = entityClass;
        this.currentField = currentField;
        this.otherFieldsMap = otherFieldsMap;
    }

    /* (non-Javadoc)
     * @see javax.faces.convert.Converter<br />
     * #getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
     */
    @Override
    public Object getAsObject(final FacesContext context, final UIComponent component, String plainInputValue) {
        plainInputValue = StringUtils.idTrim(plainInputValue);
        if (StringUtils.isEmpty(plainInputValue)) {
            return null;
        }

        try {
            final Object convertedInput =
                    NumberUtils.convertInputType(plainInputValue, this.currentField.getType().getClass());
            final List<FieldValuePair<T>> otherFieldValues = FieldValuePair.evaluateOtherFields(this.otherFieldsMap);

            final EntityManager em = EmLocator.getEm();
            final CriteriaBuilder criteriaBuilder = EmLocator.getCriteriaBuilder();
            final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
            final Root<T> root = criteriaQuery.from(this.entityClass);

            Predicate finalPredicate = criteriaBuilder.equal(root.get(this.currentField), convertedInput);
            for (int i = 0; i < otherFieldValues.size(); i++) {
                final FieldValuePair<T> valuePair = otherFieldValues.get(i);
                finalPredicate =
                        criteriaBuilder.and(finalPredicate,
                                criteriaBuilder.equal(root.get(valuePair.getFieldAttribute()), valuePair.getValue()));
            }
            criteriaQuery.where(finalPredicate);
            final TypedQuery<T> query = em.createQuery(criteriaQuery);

            return query.getSingleResult();
        } catch (final PersistenceException | NumberFormatException | SecurityException exp) {
            throw ConverterUtils.throwConverterException(component, exp, Messages.get("converter.invalidCode"));
        }
    }

    /* (non-Javadoc)
     * @see javax.faces.convert.Converter<br />
     * #getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */
    @Override
    public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
        if (value != null) {
            final Abstract_entity abstractEntity = (Abstract_entity) value;
            final BasePk abstractPk = abstractEntity.getPk();
            return abstractPk.getField(this.currentField.getName()).toString();
        }
        else {
            return null;
        }
    }
}
