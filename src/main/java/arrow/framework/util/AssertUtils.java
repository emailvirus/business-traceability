package arrow.framework.util;


/**
 * Developer Utils: when assertion fail, IllegalStateException would be thrown. Customer must never get this. Messages
 * need not be localized.
 *
 * @author HUGH
 *
 */
public abstract class AssertUtils {

    /**
     * Assert true.
     *
     * @param value the value
     */
    public static void assertTrue(final boolean value) {
        AssertUtils.assertTrueWithErrorMessage(value, "Value must be true");
    }

    /**
     * Assert true with error message.
     *
     * @param value the value
     * @param errorMessage the error message
     */
    public static void assertTrueWithErrorMessage(final boolean value, final String errorMessage) {
        if (!value) {
            throw new IllegalArgumentException(errorMessage);
        }
    }


    /**
     * Assert not null.
     *
     * @param <T> the generic type
     * @param reference the reference
     * @return the t
     */
    public static <T> T assertNotNull(final T reference) {
        if (reference == null) {
            throw new IllegalStateException("Value must not be null");
        }
        return reference;
    }


    /**
     * Assert non negative with name.
     *
     * @param value the value
     * @param name the name
     */
    public static void assertNonNegativeWithName(final int value, final String name) {
        if (value < 0) {
            throw new IllegalStateException(name + " must not be negative");
        }
    }

    /**
     * Not empty.
     *
     * @param str the str
     */
    public static void notEmpty(final String str) {
        AssertUtils.notEmpty(str, "The argument");
    }

    /**
     * Not empty.
     *
     * @param str the str
     * @param parameterName the parameter name
     */
    public static void notEmpty(final String str, final String parameterName) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException(parameterName + " cannot be empty.");
        }
    }

    /**
     * Gt.
     *
     * @param value the value
     * @param lowerBound the lower bound
     */
    public static void gt(final int value, final int lowerBound) {
        AssertUtils.gt(value, lowerBound, "The argument");
    }

    /**
     * Gt.
     *
     * @param value the value
     * @param lowerBound the lower bound
     * @param parameterName the parameter name
     */
    public static void gt(final int value, final int lowerBound, final String parameterName) {
        if (value <= lowerBound) {
            throw new IllegalArgumentException(parameterName + " must be greater than " + lowerBound + ".");
        }
    }

    /**
     * Lt.
     *
     * @param value the value
     * @param upperBound the upper bound
     */
    public static void lt(final int value, final int upperBound) {
        AssertUtils.lt(value, upperBound, "The argument");
    }

    /**
     * Lt.
     *
     * @param value the value
     * @param upperBound the upper bound
     * @param parameterName the parameter name
     */
    public static void lt(final int value, final int upperBound, final String parameterName) {
        if (value >= upperBound) {
            throw new IllegalArgumentException(parameterName + " must be less than " + upperBound + ".");
        }
    }
}
