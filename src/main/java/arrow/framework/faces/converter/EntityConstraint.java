package arrow.framework.faces.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.faces.convert.Converter;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import arrow.framework.faces.util.FacesELUtils;
import arrow.framework.persistence.meta.util.MetaModelUtils;
import arrow.framework.util.StringUtils;
import arrow.framework.util.i18n.Messages;


/**
 * The Class EntityConstraint.
 *
 * @param <T> the generic type
 */
public class EntityConstraint<T> {

    /** The Constant AUTO_COMPLETE_METHOD_INFO. */
    private static final MethodInfo AUTO_COMPLETE_METHOD_INFO = new MethodInfo("$$$autoCompleteMethod", Void.class,
            new Class[] {String.class});

    /** The em. */
    private final EntityManager em;

    /** The other fields map. */
    private final Map<SingularAttribute<? super T, ?>, Object> otherFieldsMap =
            new HashMap<SingularAttribute<? super T, ?>, Object>();

    /** The current field. */
    private SingularAttribute<? super T, ?> currentField;

    /** The entity converter. */
    private EntityConverter<T> entityConverter;

    /** The entity class. */
    // private SearchService searchService;
    private Class<T> entityClass;

    /** The auto complete method expression. */
    private final MethodExpression autoCompleteMethodExpression = new MethodExpression() {

        @Override
        public MethodInfo getMethodInfo(final ELContext arg0) {
            return EntityConstraint.AUTO_COMPLETE_METHOD_INFO;
        }

        @Override
        public Object invoke(final ELContext arg0, final Object[] params) {
            final String queryStr = params[0].toString();

            final EntityConstraint<T> entityConstraint = EntityConstraint.this;
            final List<FieldValuePair<T>> otherFieldValues =
                    FieldValuePair.evaluateOtherFields(entityConstraint.otherFieldsMap);

            final Predicate[] allPredicates = new Predicate[otherFieldValues.size() + 2];

            final CriteriaBuilder criteriaBuilder = EntityConstraint.this.em.getCriteriaBuilder();
            final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityConstraint.entityClass);
            final Root<T> root = criteriaQuery.from(entityConstraint.entityClass);

            allPredicates[0] =
                    criteriaBuilder.like(
                            criteriaBuilder.upper(root.get(entityConstraint.getCurrentField()).as(String.class)),
                            StringUtils.buildLikeValueExpression(queryStr.toUpperCase()));
            // TODO: Incase the query string is equal to item value, should show the selection or not ????
            allPredicates[1] =
                    criteriaBuilder.notEqual(
                            criteriaBuilder.upper(root.get(entityConstraint.getCurrentField()).as(String.class)),
                            queryStr.toUpperCase());
            for (int i = 0; i < otherFieldValues.size(); i++) {
                final FieldValuePair<T> valuePair = otherFieldValues.get(i);
                allPredicates[i + 2] =
                        criteriaBuilder.equal(root.get(valuePair.getFieldAttribute()), valuePair.getValue());
            }
            criteriaQuery.where(criteriaBuilder.and(allPredicates));
            final TypedQuery<T> query = EntityConstraint.this.em.createQuery(criteriaQuery);
            return query.getResultList();
        }

        @Override
        public boolean equals(final Object arg0) {
            return this == arg0;
        }

        @Override
        public String getExpressionString() {
            return FacesELUtils.buildEL(EntityConstraint.AUTO_COMPLETE_METHOD_INFO.getName());
        }

        @Override
        public int hashCode() {
            return EntityConstraint.AUTO_COMPLETE_METHOD_INFO.hashCode();
        }

        @Override
        public boolean isLiteralText() {
            return false;
        }

    };

    // public SearchService buildAndGetSearchService()
    // {
    // if (this.searchService == null)
    // {
    // this.searchService = SearchServiceFactory.createSearchService(this.em, this.entityClass);
    // }
    //
    // return this.searchService;
    // }

    /**
     * Instantiates a new entity constraint.
     *
     * @param entityClass the entity class
     * @param emMain the em main
     */
    public EntityConstraint(final Class<T> entityClass, final EntityManager emMain) {
        if (entityClass == null) {
            throw new NullPointerException("Attribute entityClass can not be null");
        }

        this.entityClass = entityClass;
        this.em = emMain;
    }

    /**
     * Gets the converter.
     *
     * @return the converter
     */
    public Converter getConverter() {
        if (this.entityConverter == null) {
            this.entityConverter =
                    new EntityConverter<T>(this.entityClass, this.getCurrentField(), this.otherFieldsMap);
        }
        return this.entityConverter;
    }

    /**
     * Gets the complete method expression.
     *
     * @return the complete method expression
     */
    public MethodExpression getCompleteMethodExpression() {
        return this.autoCompleteMethodExpression;
    }

    /**
     * Sets the entity class.
     *
     * @param entityClass the new entity class
     */
    public void setEntityClass(final Class<T> entityClass) {
        if (entityClass == null) {
            throw new IllegalArgumentException("Attribute entityClass can not be null");
        }
        this.entityClass = entityClass;
    }

    /**
     * Gets the current field.
     *
     * @return the current field
     */
    public SingularAttribute<? super T, ?> getCurrentField() {
        if (this.currentField == null) {
            this.currentField = MetaModelUtils.getSinglePKAttribute(this.entityClass, this.em);
        }
        if (this.currentField == null) {
            throw new IllegalArgumentException("You must define currentField for this constraint");
        }

        return this.currentField;
    }

    /**
     * Sets the current field.
     *
     * @param currentField the current field
     */
    public void setCurrentField(final SingularAttribute<? super T, ?> currentField) {
        this.currentField = currentField;
    }

    /**
     * Adds the other field.
     *
     * @param fieldName the field name
     * @param fieldValue the field value
     */
    public void addOtherField(final SingularAttribute<? super T, ?> fieldName, final Object fieldValue) {
        this.otherFieldsMap.put(fieldName, fieldValue);
    }

    /**
     * Gets the search panel title.
     *
     * @return the search panel title
     */
    public String getSearchPanelTitle() {
        return Messages.get("search-title." + this.entityClass.getName());
    }
}
