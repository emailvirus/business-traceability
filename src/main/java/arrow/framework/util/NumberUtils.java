package arrow.framework.util;

import java.math.BigDecimal;
import java.util.List;

import arrow.framework.util.collections.CollectionUtils;

/**
 * The Class NumberUtils.
 */
public class NumberUtils {

    /**
     * Round.
     *
     * @param value the value
     * @param decimalPlaces the decimal places
     * @return the double
     */
    public static double round(final Double value, final int decimalPlaces) {
        if (value == null) {
            return 0.0;
        }

        return BigDecimal.valueOf(value).setScale(10, BigDecimal.ROUND_HALF_UP)
                .setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * Pow.
     *
     * @param numOne the number one
     * @param numTwo the number two
     * @return the int
     */
    public static int pow(final int numOne, final int numTwo) {
        if (numTwo <= 0) {
            return (int) Math.pow(numOne, numTwo);
        }

        if (numTwo == 1) {
            return numOne;
        }

        return numOne * NumberUtils.pow(numOne, numTwo - 1);
    }

    /**
     * Calculates the greatest common divisor.
     *
     * @param numX the number X
     * @param numY the number Y
     * @return the int
     */
    public static int calcGcd(int numX, int numY) {
        if ((numX == 0) && (numY == 0)) {
            throw new IllegalArgumentException("At least 1 argument must be non - zero.");
        }

        if (numY == 0) {
            return numX;
        }

        final int r = numX % numY;
        numX = numY;
        numY = r;

        return NumberUtils.calcGcd(numX, numY);
    }

    /**
     * Calculates the least common multiple.
     *
     * @param numX the number X
     * @param numY the number Y
     * @return the int
     */
    public static int calcLcm(final int numX, final int numY) {
        return (numX / NumberUtils.calcGcd(numX, numY)) * numY;
    }

    /**
     * Calc lcm.
     *
     * @param intList the int list
     * @return the int
     */
    public static int calcLcm(final List<Integer> intList) {
        if (CollectionUtils.isEmpty(intList) || (intList.size() < 1)) {
            throw new IllegalArgumentException("The list size must be >= 1");
        }

        int lcm = intList.get(0);

        for (int i = 1; i < intList.size(); ++i) {
            lcm = NumberUtils.calcLcm(lcm, intList.get(i));
        }

        return lcm;
    }

    /**
     * Are equal.
     *
     * @param firstValue the first value
     * @param secondValue the second value
     * @return true, if successful
     */
    public static boolean areEqual(final Integer firstValue, final Integer secondValue) {
        if ((firstValue == null) || (secondValue == null)) {
            return (firstValue == null) && (secondValue == null);
        }

        return firstValue.intValue() == secondValue.intValue();
    }

    /**
     * Are equal.
     *
     * @param firstValue the first value
     * @param secondValue the second value
     * @return true, if successful
     */
    public static boolean areEqual(final Double firstValue, final Double secondValue) {
        if ((firstValue == null) || (secondValue == null)) {
            return (firstValue == null) && (secondValue == null);
        }

        return Double.doubleToLongBits(firstValue) == Double.doubleToLongBits(secondValue);
    }

    /**
     * Gets the digit qty.
     *
     * @param number the number
     * @return the digit qty
     */
    public static int getDigitQty(final int number) {
        return String.valueOf(number).length();
    }

    /**
     * Convert input type.
     *
     * @param input the input
     * @param type the type
     * @return the object
     */
    public static Object convertInputType(final String input, final Class<?> type) {
        if (Integer.class.equals(type) || int.class.equals(type)) {
            return Integer.valueOf(input);
        }
        else if (Double.class.equals(type) || double.class.equals(type)) {
            return Double.valueOf(input);
        }
        return input;
    }
}
