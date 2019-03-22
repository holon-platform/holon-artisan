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
package com.holonplatform.artisan.vaadin.flow.export.xls;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import com.holonplatform.artisan.vaadin.flow.export.xls.internal.XLSBooleanValue;
import com.holonplatform.artisan.vaadin.flow.export.xls.internal.XLSCalendarValue;
import com.holonplatform.artisan.vaadin.flow.export.xls.internal.XLSDateValue;
import com.holonplatform.artisan.vaadin.flow.export.xls.internal.XLSEnumValue;
import com.holonplatform.artisan.vaadin.flow.export.xls.internal.XLSFormulaValue;
import com.holonplatform.artisan.vaadin.flow.export.xls.internal.XLSLocalDateTimeValue;
import com.holonplatform.artisan.vaadin.flow.export.xls.internal.XLSLocalDateValue;
import com.holonplatform.artisan.vaadin.flow.export.xls.internal.XLSLocalTimeValue;
import com.holonplatform.artisan.vaadin.flow.export.xls.internal.XLSNumericValue;
import com.holonplatform.artisan.vaadin.flow.export.xls.internal.XLSStringValue;
import com.holonplatform.core.temporal.TemporalType;

/**
 * Represents an Excel value, tipycally used to set a sheet cell value.
 *
 * @param <T> Value type
 * 
 * @since 1.0.0
 */
public interface XLSValue<T> extends Serializable {

	/**
	 * Get the value type.
	 * @return the value type
	 */
	Class<? extends T> getValueType();

	/**
	 * Get the value, if available.
	 * @return The optional value
	 */
	Optional<T> getValue();

	/**
	 * Get the excel data type.
	 * @return The data type
	 */
	XLSDataType getDataType();

	/**
	 * Get the temporal type for date/time values.
	 * @return Optional temporal type
	 */
	Optional<TemporalType> getTemporalType();

	/**
	 * Get the data format to use.
	 * @return Optional data format
	 */
	Optional<String> getDataFormat();

	// ------- builders

	/**
	 * Create a {@link Boolean} type value.
	 * @param value The value (may be null)
	 * @return The {@link XLSValue}
	 */
	static XLSValue<Boolean> booleanValue(Boolean value) {
		return new XLSBooleanValue(value);
	}

	/**
	 * Create a {@link String} type value.
	 * @param value The value (may be null)
	 * @return The {@link XLSValue}
	 */
	static XLSValue<String> stringValue(String value) {
		return new XLSStringValue(value);
	}

	/**
	 * Create a {@link Number} type value.
	 * @param <N> Number type
	 * @param valueType Value type (not null)
	 * @param value The value (may be null)
	 * @return The {@link XLSValue}
	 */
	static <N extends Number> XLSValue<N> numericValue(Class<? extends N> valueType, N value) {
		return new XLSNumericValue<>(valueType, value, null);
	}

	/**
	 * Create a {@link Number} type value.
	 * @param <N> Number type
	 * @param valueType Value type (not null)
	 * @param value The value (may be null)
	 * @param dataFormat Optional data format
	 * @return The {@link XLSValue}
	 */
	static <N extends Number> XLSValue<N> numericValue(Class<? extends N> valueType, N value, String dataFormat) {
		return new XLSNumericValue<>(valueType, value, dataFormat);
	}

	/**
	 * Create a {@link Calendar} type value.
	 * @param value The value (may be null)
	 * @return The {@link XLSValue}
	 */
	static XLSValue<Calendar> calendarValue(Calendar value) {
		return new XLSCalendarValue(value, TemporalType.DATE_TIME, null);
	}

	/**
	 * Create a {@link Calendar} type value.
	 * @param value The value (may be null)
	 * @param temporalType Temporal type
	 * @return The {@link XLSValue}
	 */
	static XLSValue<Calendar> calendarValue(Calendar value, TemporalType temporalType) {
		return new XLSCalendarValue(value, temporalType, null);
	}

	/**
	 * Create a {@link Calendar} type value.
	 * @param value The value (may be null)
	 * @param dataFormat Optional data format
	 * @return The {@link XLSValue}
	 */
	static XLSValue<Calendar> calendarValue(Calendar value, String dataFormat) {
		return new XLSCalendarValue(value, TemporalType.DATE_TIME, dataFormat);
	}

	/**
	 * Create a {@link Calendar} type value.
	 * @param value The value (may be null)
	 * @param temporalType Temporal type
	 * @param dataFormat Optional data format
	 * @return The {@link XLSValue}
	 */
	static XLSValue<Calendar> calendarValue(Calendar value, TemporalType temporalType, String dataFormat) {
		return new XLSCalendarValue(value, temporalType, dataFormat);
	}

	/**
	 * Create a {@link Date} type value.
	 * @param value The value (may be null)
	 * @return The {@link XLSValue}
	 */
	static XLSValue<Date> dateValue(Date value) {
		return new XLSDateValue(value, TemporalType.DATE_TIME, null);
	}

	/**
	 * Create a {@link Date} type value.
	 * @param value The value (may be null)
	 * @param temporalType Temporal type
	 * @return The {@link XLSValue}
	 */
	static XLSValue<Date> dateValue(Date value, TemporalType temporalType) {
		return new XLSDateValue(value, temporalType, null);
	}

	/**
	 * Create a {@link Date} type value.
	 * @param value The value (may be null)
	 * @param dataFormat Optional data format
	 * @return The {@link XLSValue}
	 */
	static XLSValue<Date> dateValue(Date value, String dataFormat) {
		return new XLSDateValue(value, TemporalType.DATE_TIME, dataFormat);
	}

	/**
	 * Create a {@link Date} type value.
	 * @param value The value (may be null)
	 * @param temporalType Temporal type
	 * @param dataFormat Optional data format
	 * @return The {@link XLSValue}
	 */
	static XLSValue<Date> dateValue(Date value, TemporalType temporalType, String dataFormat) {
		return new XLSDateValue(value, temporalType, dataFormat);
	}

	/**
	 * Create a {@link LocalDate} type value.
	 * @param value The value (may be null)
	 * @return The {@link XLSValue}
	 */
	static XLSValue<LocalDate> localDateValue(LocalDate value) {
		return new XLSLocalDateValue(value, null);
	}

	/**
	 * Create a {@link LocalDate} type value.
	 * @param value The value (may be null)
	 * @param dataFormat Optional data format
	 * @return The {@link XLSValue}
	 */
	static XLSValue<LocalDate> localDateValue(LocalDate value, String dataFormat) {
		return new XLSLocalDateValue(value, dataFormat);
	}

	/**
	 * Create a {@link LocalDateTime} type value.
	 * @param value The value (may be null)
	 * @return The {@link XLSValue}
	 */
	static XLSValue<LocalDateTime> localDateTimeValue(LocalDateTime value) {
		return new XLSLocalDateTimeValue(value, null);
	}

	/**
	 * Create a {@link LocalDateTime} type value.
	 * @param value The value (may be null)
	 * @param dataFormat Optional data format
	 * @return The {@link XLSValue}
	 */
	static XLSValue<LocalDateTime> localDateTimeValue(LocalDateTime value, String dataFormat) {
		return new XLSLocalDateTimeValue(value, dataFormat);
	}

	/**
	 * Create a {@link LocalTime} type value.
	 * @param value The value (may be null)
	 * @return The {@link XLSValue}
	 */
	static XLSValue<LocalTime> localTimeValue(LocalTime value) {
		return new XLSLocalTimeValue(value, null);
	}

	/**
	 * Create a {@link LocalTime} type value.
	 * @param value The value (may be null)
	 * @param dataFormat Optional data format
	 * @return The {@link XLSValue}
	 */
	static XLSValue<LocalTime> localTimeValue(LocalTime value, String dataFormat) {
		return new XLSLocalTimeValue(value, dataFormat);
	}

	/**
	 * Create a Enum type value.
	 * @param <E> Enumeration type
	 * @param valueType Value type (not null)
	 * @param value The value (may be null)
	 * @return The {@link XLSValue}
	 */
	static <E extends Enum<E>> XLSValue<E> enumValue(Class<E> valueType, E value) {
		return new XLSEnumValue<>(valueType, value);
	}

	/**
	 * Create a formula value.
	 * @param value The formula (may be null)
	 * @return The {@link XLSValue}
	 */
	static XLSValue<String> formulaValue(String value) {
		return new XLSFormulaValue(value);
	}

}
