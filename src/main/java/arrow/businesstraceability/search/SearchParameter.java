package arrow.businesstraceability.search;

import java.util.Date;

import javax.el.ELException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import arrow.framework.persistence.locator.EmLocator;
import arrow.framework.util.StringUtils;
import arrow.framework.util.i18n.Messages;


/**
 * The Class SearchParameter.
 */
@SuppressWarnings("rawtypes")
public class SearchParameter {

    /** The type. */
    private final Type type = Type.STRING;

    /** The component. */
    private Component component;

    /** The path. */
    private String path;

    /** The title. */
    private String title;

    /** The name. */
    private String name;

    /** The sort by. */
    private String sortBy;

    /** The value. */
    private Object value;

    /** The date value. */
    private Date dateValue;

    /** The date from. */
    private Date dateFrom;

    /** The date to. */
    private Date dateTo;

    /** The exact clause. */
    private String exactClause;

    /** The approx clause. */
    private String approxClause;

    /** The is exact clause. */
    private boolean isExactClause = false;

    /** The has previous bucket. */
    private boolean hasPreviousBucket = false;

    /** The disabled. */
    private boolean disabled;

    /** The skip if empty. */
    private boolean skipIfEmpty = true;

    /** The is required. */
    private boolean isRequired = false;

    /** The value on change action. */
    private String valueOnChangeAction;

    /** The extract predicate. */
    private Predicate extractPredicate;

    /** The default order. */
    private Order defaultOrder;

    /** The singular attribute. */
    private SingularAttribute singularAttribute;

    /**
     * Instantiates a new search parameter.
     */
    public SearchParameter() {}

    /**
     * Instantiates a new search parameter.
     *
     * @param title the title
     * @param name the name
     */
    // for display parameter only. doesn't construct search clause
    public SearchParameter(final String title, final String name) {
        this(title, name, Component.OUTTXT);
    }

    /**
     * Instantiates a new search parameter.
     *
     * @param title the title
     * @param name the name
     * @param component the component
     */
    private SearchParameter(final String title, final String name, final Component component) {
        this.title = StringUtils.nullTrim(title);
        this.name = StringUtils.nullTrim(name);
        this.component = component;
    }

    /**
     * Instantiates a new search parameter.
     *
     * @param title the title
     * @param name the name
     * @param exactPredicate the exact predicate
     * @param defaultOrder the default order
     * @param attribute the attribute
     * @param approxClause the approx clause
     */
    // full featured search parameter.
    private SearchParameter(final String title, final String name, final Predicate exactPredicate,
            final Order defaultOrder, final SingularAttribute attribute, final String approxClause) {
        // Default sortBy is name. Ex: I display Phase No and also want to sort by this Phase No
        this("", title, name, exactPredicate, defaultOrder, approxClause);
    }

    // For case when I want to display by name, but sort by other field. Ex: Display Seq No but sort
    /**
     * Instantiates a new search parameter.
     *
     * @param path the path
     * @param title the title
     * @param name the name
     * @param exactPredicate the exact predicate
     * @param defaultOrder the default order
     * @param approxClause the approx clause
     */
    // by Ordering No
    private SearchParameter(final String path, final String title, final String name, final Predicate exactPredicate,
            final Order defaultOrder, final String approxClause) {
        this.path = path;
        this.title = StringUtils.nullTrim(title);
        this.name = StringUtils.nullTrim(name);
        this.extractPredicate = exactPredicate;
        this.approxClause = approxClause;
        this.defaultOrder = defaultOrder;
    }

    /**
     * Gets the param title.
     *
     * @param param the param
     * @param searchServiceType the search service type
     * @return the param title
     */
    private static String getParamTitle(final String param, final String searchServiceType) {
        if (searchServiceType != null) {
            final String paramTitleKey = "column-label." + searchServiceType + "." + param; // full
            // param
            // path
            final String paramTitle = Messages.get(paramTitleKey);
            if (!paramTitle.equals(paramTitleKey)) {
                return paramTitle;
            }
        }

        return SearchParameter.getParamTitle(param);
    }

    /**
     * Gets the param title.
     *
     * @param param the param
     * @return the param title
     */
    public static String getParamTitle(final String param) {
        return Messages.get("column-label." + SearchParameter.getParamName(param));
    }

    /**
     * Gets the param name.
     *
     * @param param the param
     * @return the param name
     */
    private static String getParamName(final String param) {
        return SearchParameter.getParamName(param, false);
    }

    /**
     * Gets the param name.
     *
     * @param param the param
     * @param trimByFirstDot the trim by first dot
     * @return the param name
     */
    private static String getParamName(final String param, final boolean trimByFirstDot) {
        final int dotIdx = (trimByFirstDot) ? param.indexOf('.') : param.lastIndexOf('.');
        return (dotIdx < 0) ? param : param.substring(dotIdx + 1);
    }

    // private Parameter(final String title, final String name, final Component component) {
    // this(title, name, component);
    // }

    /**
     * Creates the search parameter.
     *
     * @param searchService the search service
     * @param root the root
     * @param attribute the attribute
     * @return the search parameter
     */
    @SuppressWarnings("unchecked")
    public static SearchParameter createSearchParameter(final SearchService searchService, final Root root,
            final SingularAttribute attribute) {
        final CriteriaBuilder criteriaBuilder = EmLocator.getCriteriaBuilder();
        final Predicate pred =
                criteriaBuilder.like(criteriaBuilder.upper(root.get(attribute)),
                        criteriaBuilder.parameter(attribute.getJavaType(), attribute.getName()));
        final Order order = criteriaBuilder.asc(root.get(attribute));
        return new SearchParameter(StringUtils.replaceUnderscoreBySpaceByUpperCaseFirstChar(attribute.getName()),
                attribute.getName(), pred, order, attribute, null);
    }

    /**
     * Creates the search parameter.
     *
     * @param root the root
     * @param attribute the attribute
     * @return the search parameter
     */
    @SuppressWarnings("unchecked")
    public static SearchParameter createSearchParameter(final Root root, final SingularAttribute attribute) {
        final CriteriaBuilder criteriaBuilder = EmLocator.getCriteriaBuilder();
        final Predicate pred =
                criteriaBuilder.like(criteriaBuilder.upper(root.get(attribute)),
                        criteriaBuilder.parameter(attribute.getJavaType(), attribute.getName()));
        final Order order = criteriaBuilder.asc(root.get(attribute));
        return new SearchParameter(StringUtils.replaceUnderscoreBySpaceByUpperCaseFirstChar(attribute.getName()),
                attribute.getName(), pred, order, attribute, null);
    }

    /**
     * Creates the display parameter.
     *
     * @param searchServiceType the search service type
     * @param param the param
     * @param component the component
     * @return the search parameter
     */
    // for display parameter
    public static SearchParameter createDisplayParameter(final String searchServiceType, final String param,
            final Component component) {
        return new SearchParameter(SearchParameter.getParamTitle(param, searchServiceType),
                SearchParameter.getParamName(param), component);
    }

    // ////////////////////////////////////////////////////////////////////////
    // creating parameters for search factories

    // for regular parameter
    // public static Parameter createSearchParameter(final String searchServiceType, final String
    // param) {
    // return new Parameter(Type.STRING, param, Parameter.getParamTitle(param, searchServiceType),
    // Parameter.getParamName(param), "UPPER(e." + param + ") LIKE :" +
    // Parameter.getParamName(param),
    // null);
    // }

    /**
     * Creates the display parameter.
     *
     * @param searchServiceType the search service type
     * @param param the param
     * @return the search parameter
     */
    public static SearchParameter createDisplayParameter(final String searchServiceType, final String param) {
        return new SearchParameter(SearchParameter.getParamTitle(param, searchServiceType),
                SearchParameter.getParamName(param), Component.OUTTXT);
    }

    /**
     * Creates the display parameter.
     *
     * @param searchServiceType the search service type
     * @param param the param
     * @param trimByFirstDot the trim by first dot
     * @return the search parameter
     */
    public static SearchParameter createDisplayParameter(final String searchServiceType, final String param,
            final boolean trimByFirstDot) {
        return new SearchParameter(SearchParameter.getParamTitle(param, searchServiceType),
                SearchParameter.getParamName(param, trimByFirstDot), Component.OUTTXT);
    }

    // public static Parameter createSearchParameterSortedByAnother(final String searchServiceType,
    // final String param, final String sortBy) {
    // return new Parameter(Type.STRING, param, Parameter.getParamTitle(param, searchServiceType),
    // Parameter.getParamName(param), "UPPER(e." + param + ") LIKE :" +
    // Parameter.getParamName(param),
    // null, sortBy);
    // }
    //
    // // for special parameter, I want to pass name to it
    // public static Parameter createSearchParameter(final String searchServiceType, final String
    // param, final String paramName) {
    // return new Parameter(Type.STRING, param, Parameter.getParamTitle(param, searchServiceType),
    // paramName, "UPPER(e." + param + ") LIKE :" + paramName, null);
    // }
    //
    // public static Parameter createDateSearchParameter(final String searchServiceType, final
    // String
    // param) {
    // return new Parameter(Type.DATE, param, Parameter.getParamTitle(param, searchServiceType),
    // Parameter.getParamName(param), "e." + param + " = :" + Parameter.getParamName(param), null);
    // }
    //
    // public static Parameter createDateOnlySearchParameter(final String searchServiceType, final
    // String param) {
    // return new Parameter(Type.DATE_ONLY, param, Parameter.getParamTitle(param,
    // searchServiceType),
    // Parameter.getParamName(param), "e." + param + " BETWEEN :" + Parameter.getParamName(param)
    // + "From AND :" + Parameter.getParamName(param) + "To", null);
    // }
    //
    // public static Parameter createIntegerSearchParameter(final String searchServiceType, final
    // String param) {
    // return new Parameter(Type.INTEGER, param, Parameter.getParamTitle(param, searchServiceType),
    // Parameter.getParamName(param), "e." + param + " = :" + Parameter.getParamName(param), null);
    // }
    //
    // public static Parameter createDoubleSearchParameter(final String searchServiceType, final
    // String param) {
    // return new Parameter(Type.DOUBLE, param, Parameter.getParamTitle(param, searchServiceType),
    // Parameter.getParamName(param), "e." + param + " = :" + Parameter.getParamName(param), null);
    // }

    // for hidden parameter
    // default to skipIfEmpty = true and exact clause
    // public static Parameter createSearchParameter(final String searchServiceType, final Type
    // type,
    // final String param, final String exactClause) {
    // return Parameter.createSearchParameter(searchServiceType, true, type, param, exactClause,
    // true);
    // }
    //
    // // default to exact clause
    // public static Parameter createSearchParameter(final String searchServiceType, final boolean
    // skipIfEmpty, final Type type, final String param, final String exactClause) {
    // return Parameter.createSearchParameter(searchServiceType, skipIfEmpty, type, param,
    // exactClause, true);
    // }
    //
    // // default to skipIfEmpty = true;
    // public static Parameter createSearchParameter(final String searchServiceType, final Type
    // type,
    // final String param, final String exactOrApproxClause, final boolean isExactClause) {
    // return Parameter.createSearchParameter(searchServiceType, true, type, param,
    // exactOrApproxClause, isExactClause);
    // }
    //
    // public static Parameter createSearchParameter(final String searchServiceType, final Type
    // type,
    // final String param, final String exactOrApproxClause, final boolean isExactClause, final
    // boolean hasPreviousBucket) {
    // final Parameter p = Parameter.createSearchParameter(searchServiceType, true, type, param,
    // exactOrApproxClause, isExactClause);
    // p.setHasPreviousBucket(hasPreviousBucket);
    // return p;
    // }

    // explicitly specify whether this parameter should be skip if it's empty, and whether the
    // clause
    // is exact or approximate.
    // public static Parameter createSearchParameter(final String searchServiceType, final boolean
    // skipIfEmpty, final Type type, final String param, final String exactOrApproxClause, final
    // boolean isExactClause) {
    // final Parameter parameter = new Parameter(type, param, Parameter.getParamTitle(param,
    // searchServiceType), Parameter.getParamName(param), exactOrApproxClause, null);
    // parameter.isExactClause = isExactClause;
    // parameter.skipIfEmpty = skipIfEmpty;
    // return parameter;
    // }

    // for Input_panel
    // public static Parameter createInputParameter(final String param) {
    // return Parameter.createInputParameter(Type.STRING, param);
    // }

    // public static Parameter createInputParameter(final Type type, final String param) {
    // return new Parameter(type, param, Parameter.getParamTitle(param), param, null, null);
    // }

    /**
     * Gets the path.
     *
     * @return the path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Sets the path.
     *
     * @param path the new path
     */
    public void setPath(final String path) {
        this.path = path;
    }

    // public static Parameter createDisplayParameter(final Type type, final String
    // searchServiceType,
    // final String param) {
    // return new Parameter(type, Parameter.getParamTitle(param, searchServiceType),
    // Parameter.getParamName(param), Component.OUTTXT);
    // }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the sort by.
     *
     * @return the sort by
     */
    public String getSortBy() {
        return this.sortBy;
    }

    /**
     * Sets the sort by.
     *
     * @param sortBy the new sort by
     */
    public void setSortBy(final String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(final Object value) {
        this.value = value;
    }

    /**
     * Gets the date from.
     *
     * @return the date from
     */
    public Date getDateFrom() {
        return this.dateFrom;
    }

    /**
     * Sets the date from.
     *
     * @param date the new date from
     */
    public void setDateFrom(final Date date) {
        this.dateFrom = date;
    }

    /**
     * Gets the date to.
     *
     * @return the date to
     */
    public Date getDateTo() {
        return this.dateTo;
    }

    /**
     * Sets the date to.
     *
     * @param date the new date to
     */
    public void setDateTo(final Date date) {
        this.dateTo = date;
    }

    /**
     * Gets the date value.
     *
     * @return the date value
     */
    public Date getDateValue() {
        return this.dateValue;
    }

    /**
     * Sets the date value.
     *
     * @param date the new date value
     */
    public void setDateValue(final Date date) {
        this.dateValue = date;
    }

    /**
     * Checks if is exact.
     *
     * @return true, if is exact
     */
    public boolean isExact() {
        return this.isExactClause;
    }

    /**
     * Sets the exact.
     *
     * @param strict the new exact
     */
    public void setExact(final boolean strict) {
        this.isExactClause = strict;
    }

    /**
     * Checks if is disabled.
     *
     * @return true, if is disabled
     */
    public boolean isDisabled() {
        return this.disabled;
    }

    /**
     * Sets the disabled.
     *
     * @param disabled the new disabled
     */
    public void setDisabled(final boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Gets the component.
     *
     * @return the component
     */
    public Component getComponent() {
        return (this.component == null) ? Component.OUTTXT : this.component;
    }

    /**
     * Checks if is used.
     *
     * @return true, if is used
     */
    public boolean isUsed() {
        if (!this.isSkipIfEmpty()) {
            return true;
        }

        final boolean valueUsed =
                this.value instanceof String ? StringUtils.nullTrim((String) this.value).length() > 0
                        : this.value != null;
        final boolean datesUsed = (this.dateFrom != null) || (this.dateTo != null) || (this.dateValue != null);

        return valueUsed || datesUsed;
    }

    /**
     * Gets the clause.
     *
     * @return the clause
     */
    public String getClause() {
        return this.approxClause != null ? (this.isExactClause ? this.exactClause : this.approxClause)
                : this.exactClause;
    }

    /**
     * Gets the converted value.
     *
     * @return the converted value
     */
    public Object getConvertedValue() {
        if (this.type == Type.OBJECT) {
            return this.value;
        }
        else if (this.type == Type.STRING) {
            final String aValue = (String) this.value;
            if (this.value == null) {
                return null;
            }
            if (this.isExactClause) {
                return aValue.trim();
            }
            else {
                return "%" + aValue.trim().toUpperCase().replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_")
                        + "%";
            }
        }
        else if (this.type == Type.DOUBLE) {
            final Double aValue = (Double) this.value;
            if (this.value == null) {
                return Double.valueOf(0);
            }
            else {
                return aValue;
            }
        }
        else if (this.type == Type.INTEGER) {
            final Integer aValue = (Integer) this.value;
            if (this.value == null) {
                return Integer.valueOf(0);
            }
            else {
                return aValue;
            }
        }
        else if (this.type == Type.DATE) {
            return this.dateValue;
        }
        else if (this.type == Type.COLLECTION) {
            return this.value;
        }

        return null;
    }

    /**
     * Checks if is text component.
     *
     * @return true, if is text component
     */
    public boolean isTextComponent() {
        return Component.OUTTXT.equals(this.getComponent());
    }

    /**
     * Checks if is check box component.
     *
     * @return true, if is check box component
     */
    public boolean isCheckBoxComponent() {
        return Component.CHKBOX.equals(this.getComponent());
    }

    /**
     * Checks if is required.
     *
     * @return true, if is required
     */
    public boolean isRequired() {
        return this.isRequired;
    }

    /**
     * Sets the required.
     *
     * @param isRequired the new required
     */
    public void setRequired(final boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * Gets the value on change action.
     *
     * @return the value on change action
     */
    public String getValueOnChangeAction() {
        return this.valueOnChangeAction;
    }

    /**
     * Sets the value on change action.
     *
     * @param valueOnChangeAction the new value on change action
     */
    public void setValueOnChangeAction(final String valueOnChangeAction) {
        this.valueOnChangeAction = valueOnChangeAction;
    }

    /**
     * Value on change result.
     */
    public void valueOnChangeResult() {
        if (this.type == Type.STRING) {
            try {
                if (this.valueOnChangeAction != null) {
                    // FacesELUtils.createMethodExpression(this.valueOnChangeAction)
                    // Expressions.instance().createMethodExpression(this.valueOnChangeAction).invoke();
                    // if (ErrorMessages.instance().hasErrors()) {
                    // this.value = null;
                    // }
                    System.out.println("valueOnChangeResult");
                }
            } catch (final ELException e) {
                // PopupPanel.handleELException(e);
                System.out.println(e);
            }
        }
    }

    /**
     * Checks if is checks for previous bucket.
     *
     * @return true, if is checks for previous bucket
     */
    public boolean isHasPreviousBucket() {
        return this.hasPreviousBucket;
    }

    /**
     * Sets the checks for previous bucket.
     *
     * @param hasPreviousBucket the new checks for previous bucket
     */
    public void setHasPreviousBucket(final boolean hasPreviousBucket) {
        this.hasPreviousBucket = hasPreviousBucket;
    }

    /**
     * Gets the extract predicate.
     *
     * @return the extract predicate
     */
    public Predicate getExtractPredicate() {
        return this.extractPredicate;
    }

    /**
     * Sets the extract predicate.
     *
     * @param extractPredicate the new extract predicate
     */
    public void setExtractPredicate(final Predicate extractPredicate) {
        this.extractPredicate = extractPredicate;
    }

    /**
     * Gets the singular attribute.
     *
     * @return the singular attribute
     */
    public SingularAttribute getSingularAttribute() {
        return this.singularAttribute;
    }

    /**
     * Gets the default order.
     *
     * @return the default order
     */
    public Order getDefaultOrder() {
        return this.defaultOrder;
    }

    /**
     * Checks if is skip if empty.
     *
     * @return true, if is skip if empty
     */
    public boolean isSkipIfEmpty() {
        return this.skipIfEmpty;
    }

    /**
     * Sets the skip if empty.
     *
     * @param skipIfEmpty the new skip if empty
     */
    public void setSkipIfEmpty(final boolean skipIfEmpty) {
        this.skipIfEmpty = skipIfEmpty;
    }

    /**
     * The Enum Type.
     */
    public static enum Type {

        /** The object. */
        OBJECT,
        /** The string. */
        STRING,
        /** The number. */
        NUMBER,
        /** The double. */
        DOUBLE,
        /** The date range. */
        DATE_RANGE,
        /** The integer. */
        INTEGER,
        /** The date. */
        DATE,
        /** The date only. */
        DATE_ONLY,
        /** The collection. */
        COLLECTION
    }

    /**
     * The Enum Component.
     */
    public static enum Component {

        /** The chkbox. */
        CHKBOX,
        /** The rdobut. */
        RDOBUT,
        /** The outtxt. */
        OUTTXT,
        /** The dropdown. */
        DROPDOWN
    }

    /**
     * The Enum Discriminator.
     */
    public static enum Discriminator {

        /** The title key. */
        TITLE_KEY
    }
}
