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

import java.util.Optional;
import java.util.function.Function;

import com.holonplatform.artisan.vaadin.flow.components.builders.InputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.StringInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.InputFilterAdapter;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultInputFilterBuilder;
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

	// ------- builders

	/**
	 * Get a builder to create an {@link InputFilter} from given <code>input</code>.
	 * @param <T> Value type
	 * @param input The {@link Input} component (not null)
	 * @param filterProvider The function to provide a {@link QueryFilter} according to the {@link Input} value, or
	 *        <code>null</code> if none (not null)
	 * @return A new {@link InputFilterBuilder}
	 */
	static <T> InputFilterBuilder<T> builder(Input<T> input, Function<T, QueryFilter> filterProvider) {
		return new DefaultInputFilterBuilder<>(input, filterProvider);
	}

	/**
	 * Get a builder to create a {@link String} type {@link InputFilter}.
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link StringInputFilterBuilder}
	 */
	static StringInputFilterBuilder string(Property<String> property) {
		return StringInputFilterBuilder.create(property);
	}

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
