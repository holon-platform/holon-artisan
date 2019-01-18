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
package com.holonplatform.artisan.vaadin.flow.export.xls.internal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Priority;

import com.holonplatform.artisan.vaadin.flow.export.xls.PropertyXLSValueProvider;
import com.holonplatform.artisan.vaadin.flow.export.xls.XLSValue;
import com.holonplatform.core.internal.utils.TypeUtils;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.temporal.TemporalType;

/**
 * Default {@link PropertyXLSValueProvider}.
 *
 * @param <T> Property value type
 *
 * @since 1.0.0
 */
@Priority(Integer.MAX_VALUE)
public class DefaultPropertyXLSValueProvider<T> implements PropertyXLSValueProvider<T> {

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.export.PropertyXLSValueProvider#provide(com.holonplatform.core.property.
	 * Property, java.lang.Object)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public XLSValue<?> provide(Property<T> property, T value) {
		// check type
		if (TypeUtils.isBoolean(property.getType())) {
			return XLSValue.booleanValue((Boolean) value);
		}
		if (TypeUtils.isNumber(property.getType())) {
			return XLSValue.numericValue((Number) value);
		}
		if (TypeUtils.isEnum(property.getType())) {
			return XLSValue.enumValue((Enum) value);
		}
		if (TypeUtils.isCalendar(property.getType())) {
			return XLSValue.calendarValue((Calendar) value, property.getTemporalType().orElse(TemporalType.DATE_TIME));
		}
		if (TypeUtils.isDate(property.getType())) {
			return XLSValue.dateValue((Date) value, property.getTemporalType().orElse(TemporalType.DATE_TIME));
		}
		if (LocalDate.class.isAssignableFrom(property.getType())) {
			return XLSValue.localDateValue((LocalDate) value);
		}
		if (LocalDateTime.class.isAssignableFrom(property.getType())) {
			return XLSValue.localDateTimeValue((LocalDateTime) value);
		}
		if (LocalTime.class.isAssignableFrom(property.getType())) {
			return XLSValue.localTimeValue((LocalTime) value);
		}
		if (OffsetDateTime.class.isAssignableFrom(property.getType())) {
			return XLSValue.offsetDateTimeValue((OffsetDateTime) value);
		}
		if (OffsetTime.class.isAssignableFrom(property.getType())) {
			return XLSValue.offsetTimeValue((OffsetTime) value);
		}

		// TODO check arrays/collections

		// Fallback to a String
		return XLSValue.stringValue(property.present(value));
	}

}
