package arrow.framework.util;


/**
 * The Class BooleanUtils.
 */
public class BooleanUtils {

    /**
     * Are equal.
     *
     * @param firstValue the first value
     * @param secondValue the second value
     * @return true, if successful
     */
    public static boolean areEqual(final Boolean firstValue, final Boolean secondValue) {
        if ((firstValue == null) || (secondValue == null)) {
            return (firstValue == null) && (secondValue == null);
        }

        return firstValue.booleanValue() == secondValue.booleanValue();
    }

}
