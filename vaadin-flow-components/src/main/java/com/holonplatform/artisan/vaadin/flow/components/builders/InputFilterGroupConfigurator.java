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
package com.holonplatform.artisan.vaadin.flow.components.builders;

import java.util.function.BiConsumer;
import java.util.function.Function;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.InputFilter.InputFilterPropertyRenderer;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterGroup;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.property.PropertyBox;
import com.holonplatform.core.property.PropertyRenderer;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.PropertyViewGroup;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.data.converter.Converter;

/**
 * {@link InputFilterGroup} configurator.
 *
 * @param <C> Concrete configurator type
 *
 * @since 1.o.0
 */
public interface InputFilterGroupConfigurator<C extends InputFilterGroupConfigurator<C>> {

	/**
	 * Set the given property as hidden. If a property is hidden, the {@link InputFilter} bound to the property will
	 * never be generated, but its value will be written to a {@link PropertyBox} using
	 * {@link PropertyViewGroup#getValue()}.
	 * @param <T> Property type
	 * @param property Property to set as hidden (not null)
	 * @return this
	 */
	<T> C hidden(Property<T> property);

	/**
	 * Set the specific {@link PropertyRenderer} to use to render the {@link InputFilter} to bind to given
	 * <code>property</code>.
	 * @param <T> Property type
	 * @param property Property (not null)
	 * @param renderer Property renderer (not null)
	 * @return this
	 */
	<T> C bind(Property<T> property, PropertyRenderer<InputFilter<T>, T> renderer);

	/**
	 * Set the function to use to render the {@link InputFilter} bound to given <code>property</code>.
	 * @param <T> Property type
	 * @param property The property to render (not null)
	 * @param function The function to use to render the property {@link InputFilter} (not null)
	 * @return this
	 */
	default <T> C bind(Property<T> property, Function<Property<? extends T>, InputFilter<T>> function) {
		return bind(property, InputFilterPropertyRenderer.create(function));
	}

	/**
	 * Bind the given <code>property</code> to given <code>inputFilter</code> instance. If the property was already
	 * bound to a {@link InputFilter}, the old input filter will be replaced by the new input filter.
	 * @param <T> Property type
	 * @param property Property (not null)
	 * @param inputFilter {@link InputFilter} to bind (not null)
	 * @return this
	 */
	default <T> C bind(Property<T> property, InputFilter<T> inputFilter) {
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		ObjectUtils.argumentNotNull(inputFilter, "InputFilter must be not null");
		return bind(property, InputFilterPropertyRenderer.create(p -> inputFilter));
	}

	/**
	 * Bind the given <code>property</code> to given <code>inputFilter</code> instance with different value type, using
	 * a {@link Converter} to perform value conversions. If the property was already bound to a {@link InputFilter}, the
	 * old input filter will be replaced by the new input filter.
	 * @param <T> Property type
	 * @param <V> Input value type
	 * @param property Property (not null)
	 * @param inputFilter {@link InputFilter} to bind (not null)
	 * @param converter Value converter (not null)
	 * @return this
	 */
	default <T, V> C bind(Property<T> property, InputFilter<V> inputFilter, Converter<V, T> converter) {
		return bind(property, InputFilter.from(inputFilter, converter));
	}

	/**
	 * Bind the given <code>property</code> to given <code>input</code>. If the property was already bound to a
	 * {@link InputFilter}, the old input filter will be replaced by the new input filter.
	 * @param <T> Property type
	 * @param property Property (not null)
	 * @param input The {@link Input} to bind (not null)
	 * @param filterProvider The function to provide a {@link QueryFilter} according to the {@link Input} value, or
	 *        <code>null</code> if none (not null)
	 * @return this
	 */
	default <T> C bindInput(Property<T> property, Input<T> input, Function<T, QueryFilter> filterProvider) {
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		ObjectUtils.argumentNotNull(input, "Input must be not null");
		return bind(property, InputFilter.from(input, filterProvider));
	}

	/**
	 * Bind the given <code>property</code> to given <code>input</code> component with different value type, using a
	 * {@link Converter} to perform value conversions. If the property was already bound to an {@link InputFilter}, the
	 * old input filter will be replaced by the new input filter.
	 * @param <T> Property type
	 * @param <V> Input value type
	 * @param property Property (not null)
	 * @param input The {@link Input} to bind (not null)
	 * @param converter Value converter (not null)
	 * @param filterProvider The function to provide a {@link QueryFilter} according to the {@link Input} value, or
	 *        <code>null</code> if none (not null)
	 * @return this
	 */
	default <T, V> C bindInput(Property<T> property, Input<V> input, Converter<V, T> converter,
			Function<T, QueryFilter> filterProvider) {
		return bind(property, InputFilter.from(input, filterProvider, converter));
	}

	/**
	 * Bind the given <code>property</code> to given {@link HasValue} component. If the property was already bound to a
	 * {@link InputFilter}, the old input filter will be replaced by the new input filter.
	 * @param <T> Property type
	 * @param <F> HasValue type
	 * @param property Property (not null)
	 * @param field HasValue component to bind (not null)
	 * @param filterProvider The function to provide a {@link QueryFilter} according to the field value, or
	 *        <code>null</code> if none (not null)
	 * @return this
	 */
	default <T, F extends Component & HasValue<?, T>> C bindField(Property<T> property, F field,
			Function<T, QueryFilter> filterProvider) {
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		ObjectUtils.argumentNotNull(field, "Field must be not null");
		return bind(property, InputFilter.from(field, filterProvider));
	}

	/**
	 * Bind the given <code>property</code> to given {@link HasValue} component with different value type, using a
	 * {@link Converter} to perform value conversions. If the property was already bound to an {@link InputFilter}, the
	 * old input filter will be replaced by the new input filter.
	 * @param <T> Property type
	 * @param <V> Input value type
	 * @param <F> HasValue type
	 * @param property Property (not null)
	 * @param field The field to bind (not null)
	 * @param converter Value converter (not null)
	 * @param filterProvider The function to provide a {@link QueryFilter} according to the field value, or
	 *        <code>null</code> if none (not null)
	 * @return this
	 */
	default <T, V, F extends Component & HasValue<?, V>> C bindField(Property<T> property, F field,
			Converter<V, T> converter, Function<T, QueryFilter> filterProvider) {
		return bind(property, InputFilter.from(field, filterProvider, converter));
	}

	/**
	 * Add a {@link BiConsumer} to allow further {@link InputFilter} configuration before the input filter is actually
	 * bound to a property.
	 * @param postProcessor the post processor to add (not null)
	 * @return this
	 */
	C withPostProcessor(BiConsumer<Property<?>, InputFilter<?>> postProcessor);

}
