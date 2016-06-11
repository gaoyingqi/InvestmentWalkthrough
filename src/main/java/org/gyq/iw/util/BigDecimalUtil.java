/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * <p>
 * Date: Sep 17, 2015
 */

package org.gyq.iw.util;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 */
public class BigDecimalUtil {

    private BigDecimalUtil() {
    }


    /**
     * 统一的除法运算
     *
     * @param dividend
     * @param divisor
     * @return
     * @since Sep 20, 2015
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {

        requireNonNull(dividend);
        requireNonNull(divisor);

        return dividend.divide(divisor, 4, RoundingMode.HALF_DOWN);
    }

    /**
     * 比较两个BigDecimal是否相等
     *
     * @param a an BigDecimal
     * @param b an BigDecimal to be compared with {@code a} for equality
     * @return {@code true} if the arguments are equal to each other
     * and {@code false} otherwise
     * @since Sep 20, 2015
     */
    public static boolean equals(BigDecimal a, BigDecimal b) {
        return (a == b) || (a != null && a.compareTo(b) == 0);
    }

    /**
     * 比较val是否比threshold大
     *
     * @param val
     * @param threshold
     * @return
     * @since Sep 20, 2015
     */
    public static boolean greaterThan(BigDecimal val, double threshold) {

        requireNonNull(val);
        return val.compareTo(BigDecimal.valueOf(threshold)) > 0;
    }

    /**
     * 比较val是否比threshold大
     *
     * @param val
     * @param threshold
     * @return
     * @since Sep 20, 2015
     */
    public static boolean greaterThan(BigDecimal val, BigDecimal threshold) {

        requireNonNull(val);
        return val.compareTo(threshold) > 0;
    }


    /**
     * 比较val是否比threshold大
     *
     * @param val
     * @param threshold
     * @return
     * @since Sep 20, 2015
     */
    public static boolean greaterEqualThan(BigDecimal val, double threshold) {

        requireNonNull(val);
        return val.compareTo(BigDecimal.valueOf(threshold)) >= 0;
    }

    /**
     * 比较val是否比threshold大
     *
     * @param val
     * @param threshold
     * @return
     * @since Sep 20, 2015
     */
    public static boolean greaterEqualThan(BigDecimal val, BigDecimal threshold) {

        requireNonNull(val);
        return val.compareTo(threshold) >= 0;
    }

    /**
     * 比较val是否比threshold小
     *
     * @param val
     * @param threshold
     * @return
     * @since Sep 20, 2015
     */
    public static boolean lessThan(BigDecimal val, double threshold) {

        requireNonNull(val);

        return val.compareTo(BigDecimal.valueOf(threshold)) < 0;
    }

    /**
     * 比较val是否比threshold小
     *
     * @param val
     * @param threshold
     * @return
     * @since Sep 20, 2015
     */
    public static boolean lessThan(BigDecimal val, BigDecimal threshold) {

        requireNonNull(val);

        return val.compareTo(threshold) < 0;
    }

    /**
     * 比较val是否比threshold小
     *
     * @param val
     * @param threshold
     * @return
     * @since Sep 20, 2015
     */
    public static boolean lessEqualThan(BigDecimal val, double threshold) {

        requireNonNull(val);

        return val.compareTo(BigDecimal.valueOf(threshold)) <= 0;
    }

    /**
     * 比较val是否比threshold小
     *
     * @param val
     * @param threshold
     * @return
     * @since Sep 20, 2015
     */
    public static boolean lessEqualThan(BigDecimal val, BigDecimal threshold) {

        requireNonNull(val);

        return val.compareTo(threshold) <= 0;
    }

    public static String getStringValue(BigDecimal val, int newScale, int roundingMode) {
        if (val.compareTo(new BigDecimal(val.intValue())) == 0) {
            val = val.setScale(0);
        } else {
            val = val.setScale(newScale, roundingMode);
        }
        return "" + val;
    }
}
