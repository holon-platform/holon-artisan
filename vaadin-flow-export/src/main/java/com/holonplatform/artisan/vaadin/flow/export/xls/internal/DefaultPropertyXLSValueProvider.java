/*
 * Copyright 2016-2019 Axioma srl.
 * 
 * Licensed under the Commercial Holon Platform Module License Version 1 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * https://docs.holon-platform.com/license/chpml_v1.html
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
import java.util.Calendar;
import java.util.Date;

import jakarta.annotation.Priority;

import com.holonplatform.artisan.core.utils.Obj;
import com.holonplatform.artisan.vaadin.flow.export.xls.PropertyXLSValueProvider;
import com.holonplatform.artisan.vaadin.flow.export.xls.XLSPropertyValueContext;
import com.holonplatform.artisan.vaadin.flow.export.xls.XLSValue;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public XLSValue<?> provide(XLSPropertyValueContext<T> context, T value) {

		final Property<T> property = context.getProperty();

		// check type
		if (Obj.isBoolean(property.getType())) {
			return XLSValue.booleanValue((Boolean) value);
		}
		if (Obj.isNumber(property.getType())) {
			return XLSValue.numericValue((Class<? extends Number>) property.getType(), (Number) value);
		}
		if (Obj.isEnum(property.getType())) {
			return XLSValue.enumValue((Class<Enum>) property.getType(), (Enum) value);
		}
		if (Obj.isCalendar(property.getType())) {
			return XLSValue.calendarValue((Calendar) value, property.getTemporalType().orElse(TemporalType.DATE_TIME));
		}
		if (Obj.isDate(property.getType())) {
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

		// TODO check arrays/collections

		// Fallback to a String
		return XLSValue.stringValue(property.present(value));
	}

}
