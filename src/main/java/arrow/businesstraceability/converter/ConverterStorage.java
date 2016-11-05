package arrow.businesstraceability.converter;

import javax.faces.convert.Converter;
import javax.inject.Named;

import arrow.businesstraceability.persistence.entity.Addresspoint_mst;
import arrow.businesstraceability.persistence.entity.Authority_mst;
import arrow.businesstraceability.persistence.entity.Company_mst;
import arrow.businesstraceability.persistence.entity.Customer_info_view;
import arrow.businesstraceability.persistence.entity.Daily_activity_type;
import arrow.businesstraceability.persistence.entity.Employee_mst;
import arrow.businesstraceability.persistence.entity.Industry_big_mst;
import arrow.businesstraceability.persistence.entity.Position_mst;
import arrow.framework.faces.constant.NumberSigns;
import arrow.framework.faces.converter.BaseNumberConverter;
import arrow.framework.faces.converter.EntityConstraint;
import arrow.framework.faces.converter.NonNegativeNumberConverter;
import arrow.framework.faces.converter.PositiveNumberConverter;
import arrow.framework.faces.converter.TemporaryEntityConverter;
import arrow.framework.persistence.locator.EmLocator;

/**
 * The Class ConverterStorage.
 */
@Named
public class ConverterStorage {

    private static final String ROUNDING_PATTERN = "#,##0";

    private static final String ROUNDING_PATTERN_NO_GROUP_SEPARATOR = "###0";

    private static String getRoundingPattern(final int decimalPlaces) {
        if (decimalPlaces <= 0) {
            return ConverterStorage.ROUNDING_PATTERN;
        }

        StringBuilder pattern = new StringBuilder(ConverterStorage.ROUNDING_PATTERN + ".");
        for (int i = 0; i < decimalPlaces; i++) {
            pattern.append("0");
        }

        return pattern.toString();
    }

    /**
     * Gets the rounding pattern no group separator.
     *
     * @param decimalPlaces the decimal places
     * @return the rounding pattern no group separator
     */
    private static String getRoundingPatternNoGroupSeparator(final int decimalPlaces) {
        if (decimalPlaces <= 0) {
            return ConverterStorage.ROUNDING_PATTERN_NO_GROUP_SEPARATOR;
        }

        StringBuilder pattern = new StringBuilder(ConverterStorage.ROUNDING_PATTERN_NO_GROUP_SEPARATOR + ".");
        for (int i = 0; i < decimalPlaces; i++) {
            pattern.append("0");
        }

        return pattern.toString();
    }

    /**
     * Gets the converter.
     *
     * @param decimalPlaces the decimal places
     * @param constraint the constraint
     * @param ngs the ngs
     * @return the converter
     */
    private static BaseNumberConverter getConverter(final int decimalPlaces, final NumberSigns constraint,
        final boolean ngs) {
        final BaseNumberConverter snc =
            (constraint == NumberSigns.NON_NEGATIVE) ? new NonNegativeNumberConverter()
                : (constraint == NumberSigns.POSITIVE) ? new PositiveNumberConverter() : new BaseNumberConverter();

        final String pattern =
            ngs ? ConverterStorage.getRoundingPatternNoGroupSeparator(decimalPlaces) : ConverterStorage
                .getRoundingPattern(decimalPlaces);
        snc.setPattern(pattern);

        return snc;
    }

    /**
     * Gets the integer converter.
     *
     * @return the integer converter
     */
    public BaseNumberConverter getIntegerConverter() {
        return ConverterStorage.getConverter(0, NumberSigns.NONE, false);
    }

    /**
     * Gets the double converter.
     *
     * @return the double converter
     */
    public BaseNumberConverter getDoubleConverter() {
        return ConverterStorage.getConverter(2, NumberSigns.NONE, false);
    }

    /**
     * NOTES: List your converter here. AND EACH converter have to has Unit Test
     *
     * @return the address point mst converter
     */
    public Converter getAddressPointMstConverter() {
        // Map<SingularAttribute<? super Addresspoint_mst, ?>, Object> otherFieldsMap = new
        // HashMap<SingularAttribute<? super Addresspoint_mst, ?>,
        // Object>();
        // return new EntityConverter<Addresspoint_mst>(Addresspoint_mst.class,
        // Addresspoint_mst_MAPPED_.adp_code, otherFieldsMap);
        return new EntityConstraint<>(Addresspoint_mst.class, EmLocator.getEm()).getConverter();
    }

    /**
     * NOTES: List your converter here. AND EACH converter have to has Unit Test
     *
     * @return the address point mst converter
     */
    public Converter getCustomerViewConverter() {
        // Map<SingularAttribute<? super Addresspoint_mst, ?>, Object> otherFieldsMap = new
        // HashMap<SingularAttribute<? super Addresspoint_mst, ?>,
        // Object>();
        // return new EntityConverter<Addresspoint_mst>(Addresspoint_mst.class,
        // Addresspoint_mst_MAPPED_.adp_code, otherFieldsMap);
        return new EntityConstraint<>(Customer_info_view.class, EmLocator.getEm()).getConverter();
    }

    /**
     * Gets the industry big info converter.
     *
     * @return the industry big info converter
     */
    public Converter getIndustryBigInfoConverter() {
        return new EntityConstraint<>(Industry_big_mst.class, EmLocator.getEm()).getConverter();
    }

    /**
     * Gets the daily activity type converter.
     *
     * @return the daily activity type converter
     */
    public Converter getDailyActivityTypeConverter() {
        return new EntityConstraint<>(Daily_activity_type.class, EmLocator.getEm()).getConverter();
    }

    /**
     * Gets the position mst converter.
     *
     * @return the position mst converter
     */
    public Converter getPositionMstConverter() {
        return new EntityConstraint<>(Position_mst.class, EmLocator.getEm()).getConverter();
    }

    /**
     * Gets the authority mst converter.
     *
     * @return the authority mst converter
     */
    public Converter getAuthorityMstConverter() {
        return new EntityConstraint<>(Authority_mst.class, EmLocator.getEm()).getConverter();
    }

    /**
     * Gets the employee converter.
     *
     * @return the employee converter
     */
    public Converter getEmployeeConverter() {
        return new EntityConstraint<>(Employee_mst.class, EmLocator.getEm()).getConverter();
    }

    /**
     * Gets the company converter.
     *
     * @return the company converter
     */
    public Converter getCompanyConverter() {
        return new EntityConstraint<>(Company_mst.class, EmLocator.getEm()).getConverter();
    }

    /**
     * Gets the positive number converter.
     *
     * @return the positive number converter
     */
    public BaseNumberConverter getPositiveNumberConverter() {
        return ConverterStorage.getConverter(0, NumberSigns.POSITIVE, false);
    }

    public Converter getCompanySuggestConverter() {
        return new TemporaryEntityConverter();
    }

    public Converter getCurrencyConverter() {
        return ArrowConverterFactory.getCurrencyConverter();
    }
}
