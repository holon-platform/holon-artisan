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
package com.holonplatform.artisan.vaadin.flow.components;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import com.holonplatform.artisan.vaadin.flow.components.builders.BooleanInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.DateInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.DateTimeInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.EnumInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.EnumInputFilterBuilder.EnumMultiOptionInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.EnumInputFilterBuilder.EnumSingleOptionInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.EnumInputFilterBuilder.EnumSingleSelectInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.EnumInputFilterBuilder.GenericEnumInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.LocalDateInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.LocalDateTimeInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.LocalTimeInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.NumberInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.OperatorInputFilterAdapterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.StringInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.InputFilterAdapter;
import com.holonplatform.artisan.vaadin.flow.components.internal.InputFilterConverterAdapter;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultOperatorInputFilterAdapterBuilder;
import com.holonplatform.core.config.ConfigProperty;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.property.PropertyRenderer;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.vaadin.flow.components.Input;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.data.converter.Converter;

/**
 * An {@link Input} component which provides a {@link QueryFilter} according to the current input value, if available.
 *
 * @param <T> Value type
 *
 * @since 1.0.0
 */
public interface InputFilter<T> extends Input<T> {

	/**
	 * Get the {@link QueryFilter} according to the current input value, if available.
	 * @return Optional filter
	 */
	Optional<QueryFilter> getFilter();

	/**
	 * Reset the input filter, clearing its value.
	 */
	default void reset() {
		clear();
	}

	// ------- adapters

	/**
	 * Create an {@link InputFilter} from given <code>input</code>.
	 * @param <T> Value type
	 * @param input The {@link Input} component (not null)
	 * @param filterProvider The function to provide a {@link QueryFilter} according to the {@link Input} value, or
	 *        <code>null</code> if none (not null)
	 * @return A new {@link InputFilter}
	 */
	static <T> InputFilter<T> from(Input<T> input, Function<T, QueryFilter> filterProvider) {
		return new InputFilterAdapter<>(input, filterProvider);
	}

	/**
	 * Create an {@link InputFilter} from given <code>input</code> with a different value type, using given
	 * {@link Converter} to perform value conversions.
	 * @param <T> New value type
	 * @param <V> Original value type
	 * @param input The {@link Input} component (not null)
	 * @param filterProvider The function to provide a {@link QueryFilter} according to the {@link Input} value, or
	 *        <code>null</code> if none (not null)
	 * @param converter Value converter (not null)
	 * @return A new {@link InputFilter} of the converted value type
	 */
	static <T, V> InputFilter<T> from(Input<V> input, Function<T, QueryFilter> filterProvider,
			Converter<V, T> converter) {
		return from(Input.from(input, converter), filterProvider);
	}

	/**
	 * Create an {@link InputFilter} from given {@link HasValue} component.
	 * @param <F> {@link HasValue} component type
	 * @param <T> Value type
	 * @param field The field instance (not null)
	 * @param filterProvider The function to provide a {@link QueryFilter} according to the {@link Input} value, or
	 *        <code>null</code> if none (not null)
	 * @return A new {@link InputFilter} which wraps the given <code>field</code>
	 */
	static <T, F extends Component & HasValue<?, T>> InputFilter<T> from(F field,
			Function<T, QueryFilter> filterProvider) {
		return from(Input.from(field), filterProvider);
	}

	/**
	 * Create an {@link InputFilter} from given {@link HasValue} component with a different value type, using given
	 * {@link Converter} to perform value conversions.
	 * @param <F> {@link HasValue} component type
	 * @param <T> New value type
	 * @param <V> Original value type
	 * @param field The field instance (not null)
	 * @param filterProvider The function to provide a {@link QueryFilter} according to the {@link Input} value, or
	 *        <code>null</code> if none (not null)
	 * @param converter Value converter (not null)
	 * @return A new {@link InputFilter} which wraps the given <code>field</code>
	 */
	static <F extends Component & HasValue<?, V>, T, V> InputFilter<T> from(F field,
			Function<T, QueryFilter> filterProvider, Converter<V, T> converter) {
		return from(Input.from(field, converter), filterProvider);
	}

	/**
	 * Create a new {@link InputFilter} from another {@link InputFilter} with a different value type, using given
	 * {@link Converter} to perform value conversions.
	 * @param <T> New value type
	 * @param <V> Original value type
	 * @param input Actual {@link InputFilter} (not null)
	 * @param converter Value converter (not null)
	 * @return A new {@link InputFilter} of the converted value type
	 */
	static <T, V> InputFilter<T> from(InputFilter<V> input, Converter<V, T> converter) {
		return new InputFilterConverterAdapter<>(input, converter);
	}

	// ------- builders

	/**
	 * Get a builder to create an {@link InputFilter} with operator support from given <code>input</code>.
	 * @param <T> Value type
	 * @param input The {@link Input} component (not null)
	 * @param property The property to use as query filter expression (not null)
	 * @param operators The available filter operators (not null)
	 * @return A new {@link OperatorInputFilterAdapterBuilder}
	 */
	static <T> OperatorInputFilterAdapterBuilder<T> builder(Input<T> input, Property<? super T> property,
			InputFilterOperator... operators) {
		return new DefaultOperatorInputFilterAdapterBuilder<>(input, property, operators);
	}

	/**
	 * Get a builder to create a {@link String} type {@link InputFilter}.
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link StringInputFilterBuilder}
	 */
	static StringInputFilterBuilder string(Property<String> property) {
		return StringInputFilterBuilder.create(property);
	}

	/**
	 * Get a builder to create a numeric type {@link InputFilter}.
	 * @param <T> Number type
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link NumberInputFilterBuilder}
	 */
	static <T extends Number> NumberInputFilterBuilder<T> number(Property<T> property) {
		return NumberInputFilterBuilder.create(property);
	}

	/**
	 * Get a builder to create a <code>boolean</code> type {@link InputFilter}.
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link BooleanInputFilterBuilder}
	 */
	static BooleanInputFilterBuilder boolean_(Property<Boolean> property) {
		return BooleanInputFilterBuilder.create(property);
	}

	/**
	 * Get a builder to create a {@link LocalDate} type {@link InputFilter}.
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link LocalDateInputFilterBuilder}
	 */
	static LocalDateInputFilterBuilder localDate(Property<LocalDate> property) {
		return LocalDateInputFilterBuilder.create(property);
	}

	/**
	 * Get a builder to create a {@link LocalDateTime} type {@link InputFilter}.
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link LocalDateTimeInputFilterBuilder}
	 */
	static LocalDateTimeInputFilterBuilder localDateTime(Property<LocalDateTime> property) {
		return LocalDateTimeInputFilterBuilder.create(property);
	}

	/**
	 * Get a builder to create a {@link LocalTime} type {@link InputFilter}.
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link LocalTimeInputFilterBuilder}
	 */
	static LocalTimeInputFilterBuilder localTime(Property<LocalTime> property) {
		return LocalTimeInputFilterBuilder.create(property);
	}

	/**
	 * Get a builder to create a {@link Date} type {@link InputFilter}.
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link DateInputFilterBuilder}
	 */
	static DateInputFilterBuilder date(Property<Date> property) {
		return DateInputFilterBuilder.create(property);
	}

	/**
	 * Get a builder to create a {@link Date} with time type {@link InputFilter}.
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link DateTimeInputFilterBuilder}
	 */
	static DateTimeInputFilterBuilder dateTime(Property<Date> property) {
		return DateTimeInputFilterBuilder.create(property);
	}

	/**
	 * Get a builder to create an enumeration type {@link InputFilter}, using a single select as input component.
	 * @param <T> Enumeratin type
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link EnumSingleSelectInputFilterBuilder}
	 */
	static <T extends Enum<T>> EnumSingleSelectInputFilterBuilder<T> enumSingleSelect(Property<T> property) {
		return EnumInputFilterBuilder.singleSelect(property);
	}

	/**
	 * Get a builder to create an enumeration type {@link InputFilter}, using a radio button group as input component.
	 * @param <T> Enumeratin type
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link EnumSingleOptionInputFilterBuilder}
	 */
	static <T extends Enum<T>> EnumSingleOptionInputFilterBuilder<T> enumSingleOption(Property<T> property) {
		return EnumInputFilterBuilder.singleOption(property);
	}

	/**
	 * Get a builder to create an enumeration type {@link InputFilter}, using a checkbox group as input component.
	 * @param <T> Enumeratin type
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link EnumMultiOptionInputFilterBuilder}
	 */
	static <T extends Enum<T>> EnumMultiOptionInputFilterBuilder<T> enumMultiOption(Property<T> property) {
		return EnumInputFilterBuilder.multiOption(property);
	}

	/**
	 * Get a generic builder to create an enumeration type {@link InputFilter}, using given property and given rendering
	 * mode.
	 * <p>
	 * For specific builders according to rendering mode, use:
	 * <ul>
	 * <li>{@link #enumSingleSelect(Property)}</li>
	 * <li>{@link #enumSingleOption(Property)}</li>
	 * <li>{@link #enumMultiOption(Property)}</li>
	 * </ul>
	 * @param <T> Enumeration type
	 * @param property The property to use as filter expression (not null)
	 * @param mode The rendering mode (not null)
	 * @return A new {@link GenericEnumInputFilterBuilder}
	 */
	static <T extends Enum<T>> GenericEnumInputFilterBuilder<T> enumeration(Property<T> property, EnumFilterMode mode) {
		return EnumInputFilterBuilder.create(property, mode);
	}

	/**
	 * Get a generic builder to create an enumeration type {@link InputFilter}, using given property and the rendering
	 * mode specified in property configuration using {@link PROPERTY_ENUM_FILTER_MODE}. If the configuration value is
	 * not available, the {@link EnumFilterMode#SINGLE_SELECT} is used by default.
	 * <p>
	 * For specific builders according to rendering mode, use:
	 * <ul>
	 * <li>{@link #enumSingleSelect(Property)}</li>
	 * <li>{@link #enumSingleOption(Property)}</li>
	 * <li>{@link #enumMultiOption(Property)}</li>
	 * </ul>
	 * @param <T> Enumeration type
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link GenericEnumInputFilterBuilder}
	 */
	static <T extends Enum<T>> GenericEnumInputFilterBuilder<T> enumeration(Property<T> property) {
		return EnumInputFilterBuilder.create(property);
	}

	// ------- using renderers

	/**
	 * Create an {@link InputFilter} using given <code>property</code>, if a suitable renderer is available.
	 * @param <T> Value type
	 * @param property The property for which to create the {@link InputFilter} (not null)
	 * @return Optional {@link InputFilter} for given property
	 */
	@SuppressWarnings("unchecked")
	static <T> Optional<InputFilter<T>> create(Property<T> property) {
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		return property.renderIfAvailable(InputFilter.class).map(i -> (InputFilter<T>) i);
	}

	// ------- configuration

	/**
	 * Enumeration {@link InputFilter} rendering modes.
	 */
	public enum EnumFilterMode {

		/**
		 * The enumeration {@link InputFilter} is rendered using a <em>select</em>.
		 * <p>
		 * A single value can be selected.
		 * </p>
		 */
		SINGLE_SELECT,

		/**
		 * The enumeration {@link InputFilter} is rendered using a <em>radio button group</em>.
		 * <p>
		 * A single value can be selected.
		 * </p>
		 */
		SINGLE_OPTION,

		/**
		 * The enumeration {@link InputFilter} is rendered using a <em>checkbox group</em>.
		 * <p>
		 * Multiple values can be selected.
		 * </p>
		 */
		MULTI_OPTION;

	}

	/**
	 * A configuration value which can be used in {@link Property} configuration to specify the default
	 * {@link EnumFilterMode}.
	 */
	public static final ConfigProperty<EnumFilterMode> PROPERTY_ENUM_FILTER_MODE = ConfigProperty.create(
			"com.holonplatform.artisan.vaadin.flow.components.input.filter.enum.filter.mode", EnumFilterMode.class);

	// ------- renderer

	/**
	 * A convenience interface with a fixed {@link InputFilter} rendering type to use a {@link InputFilter}
	 * {@link PropertyRenderer} as a functional interface.
	 * 
	 * @param <T> Property type
	 */
	@FunctionalInterface
	public interface InputFilterPropertyRenderer<T> extends PropertyRenderer<InputFilter<T>, T> {

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.core.property.PropertyRenderer#getRenderType()
		 */
		@SuppressWarnings("unchecked")
		@Override
		default Class<? extends InputFilter<T>> getRenderType() {
			return (Class<? extends InputFilter<T>>) (Class<?>) InputFilter.class;
		}

		/**
		 * Create a new {@link InputFilterPropertyRenderer} using given function.
		 * @param <T> Property type
		 * @param function The function to use to provide the {@link InputFilter} (not null)
		 * @return A new {@link InputFilterPropertyRenderer}
		 */
		static <T> InputPropertyRenderer<T> create(Function<Property<? extends T>, InputFilter<T>> function) {
			return property -> function.apply(property);
		}

	}

}
