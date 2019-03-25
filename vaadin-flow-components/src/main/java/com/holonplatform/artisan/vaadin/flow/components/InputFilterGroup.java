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

import com.holonplatform.artisan.vaadin.flow.components.builders.InputFilterGroupBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.DefaultInputFilterGroup;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.property.Property.PropertyNotFoundException;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.vaadin.flow.components.BoundComponentGroup;

/**
 * Group of {@link InputFilter} bound to a set a {@link Property}s.
 *
 * @since 1.0.0
 */
public interface InputFilterGroup extends BoundComponentGroup<Property<?>, InputFilter<?>> {

	/**
	 * Get the {@link InputFilter} bound to the given <code>property</code>, if available.
	 * @param <T> Property type
	 * @param property The property which identifies the {@link InputFilter} within the group (not null)
	 * @return Optional {@link InputFilter} bound to the given <code>property</code>
	 */
	<T> Optional<InputFilter<T>> getInputFilter(Property<T> property);

	/**
	 * Get the {@link InputFilter} bound to the given <code>property</code>, throwing a
	 * {@link PropertyNotFoundException} if not available.
	 * @param <T> Property type
	 * @param property The property which identifies the {@link InputFilter} within the group (not null)
	 * @return The {@link InputFilter} bound to the given <code>property</code>
	 * @throws PropertyNotFoundException If no {@link InputFilter} is bound to given property
	 */
	default <T> InputFilter<T> requireInput(Property<T> property) {
		return getInputFilter(property).orElseThrow(
				() -> new PropertyNotFoundException(property, "No Input available for property [" + property + "]"));
	}

	/**
	 * Get the overall {@link QueryFilter} according to the current {@link InputFilter} values, if available.
	 * <p>
	 * The overall {@link QueryFilter} is obtained composing the filters returned from each {@link InputFilter} of the
	 * group using the <code>AND</code> operator.
	 * </p>
	 * @return Optional group filter
	 */
	Optional<QueryFilter> getFilter();

	/**
	 * Reset all the input filters, clearing the values.
	 */
	void reset();

	// ------- Builders

	/**
	 * Get a {@link InputFilterGroupBuilder} to create and setup a {@link InputFilterGroup}.
	 * @param <P> Property type
	 * @param properties The property set (not null)
	 * @return A new {@link InputFilterGroupBuilder}
	 */
	@SuppressWarnings("rawtypes")
	static <P extends Property> InputFilterGroupBuilder builder(Iterable<P> properties) {
		return new DefaultInputFilterGroup.DefaultBuilder(properties);
	}

	/**
	 * Get a {@link InputFilterGroupBuilder} to create and setup a {@link InputFilterGroup}.
	 * @param properties The property set (not null)
	 * @return A new {@link InputFilterGroupBuilder}
	 */
	static InputFilterGroupBuilder builder(Property<?>... properties) {
		return builder(PropertySet.of(properties));
	}

}
