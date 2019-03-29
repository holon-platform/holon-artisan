/*
 * Copyright 2016-2019 Axioma srl.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.holonplatform.artisan.core.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility class to work with objects.
 *
 * @since 1.0.1
 */
public final class Obj implements Serializable {

	private static final long serialVersionUID = 1843728976064864155L;

	private Obj() {
	}

	/**
	 * Checks that the specified method or constructor argument is not <code>null</code>.
	 * @param <T> Argument type
	 * @param argument Argument to check
	 * @param message Exception message if given <code>argument</code> is <code>null</code>
	 * @return <code>argument</code> if not <code>null</code>
	 * @throws IllegalArgumentException if given <code>argument</code> is <code>null</code>
	 */
	public static <T> T argumentNotNull(T argument, String message) {
		if (argument == null) {
			throw new IllegalArgumentException(message);
		}
		return argument;
	}

	/**
	 * Check if a class is a {@link String}
	 * @param type Type to check
	 * @return <code>true</code> if it is a string
	 */
	public static boolean isString(Class<?> type) {
		return type != null && String.class.isAssignableFrom(type);
	}

	/**
	 * Check if a class is a {@link Character}
	 * @param type Type to check
	 * @return <code>true</code> if it is a character
	 */
	public static boolean isCharacter(Class<?> type) {
		return type != null && (Character.class.isAssignableFrom(type) || char.class == type);
	}

	/**
	 * Check if a class is a {@link Date}
	 * @param type Type to check
	 * @return <code>true</code> if it is a date
	 */
	public static boolean isDate(Class<?> type) {
		return type != null && Date.class.isAssignableFrom(type);
	}

	/**
	 * Check if a class is a {@link Calendar}
	 * @param type Type to check
	 * @return <code>true</code> if it is a Calendar
	 */
	public static boolean isCalendar(Class<?> type) {
		return type != null && Calendar.class.isAssignableFrom(type);
	}

	/**
	 * Check if a class is a {@link Temporal}
	 * @param type Type to check
	 * @return <code>true</code> if it is a Temporal type
	 */
	public static boolean isTemporal(Class<?> type) {
		return type != null && Temporal.class.isAssignableFrom(type);
	}

	/**
	 * Check if a class is a number ({@link Number} subclass or numeric primitive class)
	 * @param type Type to check
	 * @return <code>true</code> if it is a number
	 */
	public static boolean isNumber(Class<?> type) {
		return type != null && (Number.class.isAssignableFrom(type) || int.class == type || long.class == type
				|| double.class == type || float.class == type || short.class == type);
	}

	/**
	 * Check if a class is an integer (primitive or wrapper)
	 * @param type Type to check
	 * @return <code>true</code> if it is an integer
	 */
	public static boolean isInteger(Class<?> type) {
		return type != null && (Integer.class.isAssignableFrom(type) || int.class == type);
	}

	/**
	 * Check if a class is a long (primitive or wrapper)
	 * @param type Type to check
	 * @return <code>true</code> if it is a long
	 */
	public static boolean isLong(Class<?> type) {
		return type != null && (Long.class.isAssignableFrom(type) || long.class == type);
	}

	/**
	 * Check if a class is a short (primitive or wrapper)
	 * @param type Type to check
	 * @return <code>true</code> if it is a short
	 */
	public static boolean isShort(Class<?> type) {
		return type != null && (Short.class.isAssignableFrom(type) || short.class == type);
	}

	/**
	 * Check if a class is a byte (primitive or wrapper)
	 * @param type Type to check
	 * @return <code>true</code> if it is a byte
	 */
	public static boolean isByte(Class<?> type) {
		return type != null && (Byte.class.isAssignableFrom(type) || byte.class == type);
	}

	/**
	 * Check if a class is a double (primitive or wrapper)
	 * @param type Type to check
	 * @return <code>true</code> if it is a double
	 */
	public static boolean isDouble(Class<?> type) {
		return type != null && (Double.class.isAssignableFrom(type) || double.class == type);
	}

	/**
	 * Check if a class is a float (primitive or wrapper)
	 * @param type Type to check
	 * @return <code>true</code> if it is a float
	 */
	public static boolean isFloat(Class<?> type) {
		return type != null && (Float.class.isAssignableFrom(type) || float.class == type);
	}

	/**
	 * Check if a class is a BigDecimal
	 * @param type Type to check
	 * @return <code>true</code> if it is a BigDecimal
	 */
	public static boolean isBigDecimal(Class<?> type) {
		return type != null && BigDecimal.class.isAssignableFrom(type);
	}

	/**
	 * Check if a class is a boolean (primitive or wrapper)
	 * @param type Type to check
	 * @return <code>true</code> if it is a boolean
	 */
	public static boolean isBoolean(Class<?> type) {
		return type != null && (Boolean.class.isAssignableFrom(type) || boolean.class == type);
	}

	/**
	 * Check if a class is an {@link Enum}
	 * @param type Type to check
	 * @return <code>true</code> if it is a Enum
	 */
	public static boolean isEnum(Class<?> type) {
		return type != null && Enum.class.isAssignableFrom(type);
	}

}
