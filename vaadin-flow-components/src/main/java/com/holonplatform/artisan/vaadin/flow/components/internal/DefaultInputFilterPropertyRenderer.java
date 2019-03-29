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
package com.holonplatform.artisan.vaadin.flow.components.internal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import javax.annotation.Priority;

import com.holonplatform.artisan.core.utils.Obj;
import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.builders.LocalTimeInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.utils.ComponentUtils;
import com.holonplatform.core.presentation.StringValuePresenter;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.property.PropertyRenderer;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.core.temporal.TemporalType;
import com.holonplatform.vaadin.flow.components.Components;
import com.holonplatform.vaadin.flow.components.Input;

/**
 * Default {@link PropertyRenderer} to create {@link InputFilter} type {@link Property} representations.
 * 
 * @param <T> Property type
 *
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
@Priority(Integer.MAX_VALUE)
public class DefaultInputFilterPropertyRenderer<T> implements PropertyRenderer<InputFilter, T> {

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.core.property.PropertyRenderer#getRenderType()
	 */
	@Override
	public Class<? extends InputFilter> getRenderType() {
		return InputFilter.class;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.core.property.PropertyRenderer#render(com.holonplatform.core.property.Property)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public InputFilter render(Property<? extends T> property) {
		if (property != null) {

			// Try to render the property according to the property type
			final Class<?> propertyType = property.getType();

			if (Obj.isString(propertyType)) {
				// String
				return renderString((Property<String>) property);
			}
			if (Obj.isBoolean(propertyType)) {
				// Boolean
				return renderBoolean((Property<Boolean>) property);
			}
			if (Obj.isEnum(propertyType)) {
				// Enum
				return renderEnum((Property<? extends Enum>) property);
			}
			if (LocalDate.class.isAssignableFrom(propertyType)) {
				// LocalDate
				return renderLocalDate((Property<LocalDate>) property);
			}
			if (LocalTime.class.isAssignableFrom(propertyType)) {
				// LocalDate
				return renderLocalTime((Property<LocalTime>) property);
			}
			if (LocalDateTime.class.isAssignableFrom(propertyType)) {
				// LocalDate
				return renderLocalDateTime((Property<LocalDateTime>) property);
			}
			if (Obj.isDate(propertyType)) {
				// Date
				return renderDate((Property<Date>) property);
			}
			if (Obj.isNumber(propertyType)) {
				// Number
				return renderNumber((Property<? extends Number>) property);
			}

			// By default, render as an Input and use the "equals" operator
			return property.renderIfAvailable(Input.class).map(input -> (Input<?>) input)
					.map(input -> InputFilter.from(input, value -> getDefaultFilter(property, value))).orElse(null);

		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private static QueryFilter getDefaultFilter(Property property, Object value) {
		if (!ComponentUtils.isEmptyValue(value)) {
			return QueryFilter.eq(property, ComponentUtils.trimValue(value));
		}
		return null;
	}

	/**
	 * Render the property as a {@link String} type {@link InputFilter}.
	 * @param property Property to render
	 * @return The {@link InputFilter} instance
	 */
	protected InputFilter<String> renderString(Property<String> property) {
		return InputFilter.string(property).build();
	}

	/**
	 * Render the property as a {@link Boolean} type {@link InputFilter}.
	 * @param property Property to render
	 * @return The {@link InputFilter} instance
	 */
	protected InputFilter<Boolean> renderBoolean(Property<Boolean> property) {
		return InputFilter.boolean_(property).build();
	}

	/**
	 * Render the property as a {@link Enum} type {@link InputFilter}.
	 * @param <E> Enumeration type
	 * @param property Property to render
	 * @return The {@link InputFilter} instance
	 */
	protected <E extends Enum<E>> InputFilter<E> renderEnum(Property<E> property) {
		return InputFilter.enumeration(property).build();
	}

	/**
	 * Render the property as a {@link LocalDate} type {@link InputFilter}.
	 * @param property Property to render
	 * @return The {@link InputFilter} instance
	 */
	protected InputFilter<LocalDate> renderLocalDate(Property<LocalDate> property) {
		return InputFilter.localDate(property).build();
	}

	/**
	 * Render the property as a {@link LocalTime} type {@link InputFilter}.
	 * @param property Property to render
	 * @return The {@link InputFilter} instance
	 */
	protected InputFilter<LocalTime> renderLocalTime(Property<LocalTime> property) {
		final LocalTimeInputFilterBuilder builder = InputFilter.localTime(property);
		property.getConfiguration().getParameter(Components.TIME_INPUT_STEP).ifPresent(s -> {
			builder.step(s);
		});
		return builder.build();
	}

	/**
	 * Render the property as a {@link LocalDateTime} type {@link InputFilter}.
	 * @param property Property to render
	 * @return The {@link InputFilter} instance
	 */
	protected InputFilter<LocalDateTime> renderLocalDateTime(Property<LocalDateTime> property) {
		return InputFilter.localDateTime(property).build();
	}

	/**
	 * Render the property as a {@link Date} type {@link InputFilter}.
	 * @param property Property to render
	 * @return The {@link InputFilter} instance
	 */
	protected InputFilter<Date> renderDate(Property<Date> property) {
		final TemporalType type = property.getConfiguration().getTemporalType().orElse(TemporalType.DATE);
		switch (type) {
		case TIME:
		case DATE_TIME:
			return InputFilter.dateTime(property).build();
		case DATE:
		default:
			return InputFilter.date(property).build();
		}
	}

	/**
	 * Render the property as a numeric type {@link InputFilter}.
	 * @param <N> Number type
	 * @param property Property to render
	 * @return The {@link InputFilter} instance
	 */
	protected <N extends Number> InputFilter<N> renderNumber(Property<N> property) {
		// configuration
		int decimals = property.getConfiguration().getParameter(StringValuePresenter.DECIMAL_POSITIONS).orElse(-1);
		// build Input
		return InputFilter.number(property).maxDecimals(decimals).build();
	}

}
