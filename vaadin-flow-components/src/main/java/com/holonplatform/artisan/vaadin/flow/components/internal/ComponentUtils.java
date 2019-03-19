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
package com.holonplatform.artisan.vaadin.flow.components.internal;

import java.io.Serializable;

/**
 * Utility class.
 *
 * @since 1.0.0
 */
public final class ComponentUtils implements Serializable {

	private static final long serialVersionUID = -6490988237342942337L;

	private ComponentUtils() {
	}

	/**
	 * Checks if given value is empty.
	 * @param value The value to check
	 * @return <code>true</code> if value is <code>null</code> or empty, <code>false</code> otherwise
	 */
	public static boolean isEmptyValue(Object value) {
		if (value == null) {
			return true;
		}
		if (value instanceof String && ((String) value).trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * If given value is a {@link String}, return the trimmed value.
	 * @param <T> Value type
	 * @param value The value to trim
	 * @return The trimmed value if a {@link String}, the original value otherwise
	 */
	@SuppressWarnings("unchecked")
	public static <T> T trimValue(T value) {
		if (value != null && value instanceof String) {
			return (T) ((String) value).trim();
		}
		return value;
	}

	/**
	 * Trim given value and return it as a {@link String}.
	 * @param value The value
	 * @return The trimmed value as a {@link String}
	 */
	public static String trimValueAsString(Object value) {
		if (value != null) {
			if (value instanceof String) {
				return ((String) value).trim();
			}
			return value.toString().trim();
		}
		return null;
	}

}
