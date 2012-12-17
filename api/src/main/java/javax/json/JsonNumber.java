/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package javax.json;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * {@code JsonNumber} represents an immutable JSON number value
 *
 * <p>
 * A {@link BigDecimal} may be used to store the numeric value internally.
 * The BigDecimal
 * may be constructed using {@link BigDecimal#BigDecimal(int) <code>int</code>},
 * {@link BigDecimal#BigDecimal(long) <code>long</code>},
 * {@link BigDecimal#BigDecimal(BigInteger) <code>BigInteger</code>},
 * {@link BigDecimal#valueOf(double) <code>double</code>} and
 * {@link BigDecimal#BigDecimal(String) <code>String</code>}.
 * Some of the method semantics in this class are defined using the
 * {@code BigDecimal} semantics.
 *
 * @author Jitendra Kotamraju
 */
public interface JsonNumber extends JsonValue {

    /**
     * JSON number type that is used to find out if a number is numerically
     * integer or a decimal.
     */
    enum NumberType {
        /**
         * Represents a number that is numerically integer.
         * The value can be accessed as int, long, or BigInteger using
         * different JsonNumber accessor methods.
         */
        INTEGER,

        /**
         * Represents a number that is numerically decimal. The value can
         * be accessed as double, or BigDecimal using different JsonNumber
         * accessor methods.
         */
        DECIMAL
    }

    /**
     * Returns a JSON number type for this number.
     * A {@link BigDecimal} may be used to store the numeric value internally
     * and the semantics of this method is defined using
     * {@link BigDecimal#scale()}.
     * If the scale of a value is zero, then its number type is
     * {@link NumberType#INTEGER INTEGER} else {@link NumberType#DECIMAL DECIMAL}.
     *
     * <p>
     * The number type can be used to invoke appropriate accessor methods to get
     * numeric value for the number.
     * <p>
     * <b>For example:</b>
     * <pre>
     * <code>
     * switch(getNumberType()) {
     *     case INTEGER :
     *         long l = getLongValue(); break;
     *     case DECIMAL :
     *         BigDecimal bd = getBigDecimalValue(); break;
     * }
     * </code>
     * </pre>
     *
     * @return a number type
     */
    NumberType getNumberType();

    /**
     * Returns JSON number as an {@code int} number. Note that this conversion
     * can lose information about the overall magnitude and precision of the
     * number value as well as return a result with the opposite sign.
     *
     * @return an {@code int} for JSON number.
     * @see java.math.BigDecimal#intValue()
     */
    int getIntValue();

    /**
     * Returns JSON number as an {@code int} number.
     *
     * @return an {@code int} for JSON number
     * @throws ArithmeticException cause if the number has a nonzero fractional
     *         part, or will not fit in an {@code int}
     * @see java.math.BigDecimal#intValueExact()
     */
    int getIntValueExact();

    /**
     * Returns JSON number as a {@code long} number. Note that this conversion
     * can lose information about the overall magnitude and precision of the
     * number value as well as return a result with the opposite sign.
     *
     * @return a {@code long} for JSON number.
     * @see java.math.BigDecimal#longValue()
     */
    long getLongValue();

    /**
     * Returns JSON number as a {@code long} number.
     *
     * @return a {@code long} for JSON number
     * @throws ArithmeticException if the number has a nonzero fractional
     *         part, or will not fit in a {@code long}.
     * @see java.math.BigDecimal#longValueExact()
     */
    long  getLongValueExact();

    /**
     * Returns JSON number as a {@link BigInteger} number. It is more of
     * a convenience method for {@code getBigDecimalValue().toBigInteger()}.
     * Note that this conversion can lose information about the overall
     * magnitude and precision of the number value as well as return a result
     * with the opposite sign.
     *
     * @return a BigInteger for JSON number.
     * @see java.math.BigDecimal#toBigInteger()
     */
    BigInteger getBigIntegerValue();

    /**
     * Returns JSON number as a {@link BigDecimal} number. It is more of
     * a convenience method for {@code getBigDecimalValue().toBigIntegerExact()}.
     *
     * @return a {@link BigInteger} for JSON number
     * @throws ArithmeticException if the number has a nonzero fractional part.
     * @see java.math.BigDecimal#toBigIntegerExact()
     */
    BigInteger getBigIntegerValueExact();

    /**
     * Returns JSON number as a {@code double} number. It is more of
     * a convenience method for {@code getBigDecimalValue().doubleValue()}.
     * Note that this conversion can lose information about the overall
     * magnitude and precision of the number value as well as return a result
     * with the opposite sign.
     *
     * @return a {@code double} for JSON number
     * @see java.math.BigDecimal#doubleValue()
     */
    double getDoubleValue();

    /**
     * Returns JSON number as a {@link BigDecimal}
     *
     * @return a {@link BigDecimal} for JSON number
     */
    BigDecimal getBigDecimalValue();

    /**
     * Returns a JSON representation of the JSON number value. The
     * representation would be equivalent to {@link BigDecimal#toString()}.
     *
     * @return JSON representation of the number
     */
    @Override
    String toString();

    /**
     * Compares the specified object with this JsonNumber for equality.
     * Returns {@code true} if and only if the specified object is also a
     * JsonNumber, and their {@link #getBigDecimalValue()} objects are
     * <i>equal</i>
     *
     * @param obj the object to be compared for equality with this JsonNumber
     * @return {@code true} if the specified object is equal to this JsonNumber
     */
    @Override
    boolean equals(Object obj);

    /**
     * Returns the hash code value for this JsonNumber object.  The hash code of
     * a JsonNumber object is defined to be its {@link #getBigDecimalValue()}
     * object's hash code.
     *
     * @return the hash code value for this JsonNumber object
     */
    @Override
    int hashCode();

}
