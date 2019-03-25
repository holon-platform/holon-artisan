/*
 * Copyright 2016-2018 Axioma srl.
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
package com.holonplatform.artisan.vaadin.flow.components.internal.support;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.BoundComponentGroup.Binding;

/**
 * {@link Property} and {@link InputFilter} binding registry.
 *
 * @since 1.0.0
 */
public interface InputFilterPropertyRegistry extends Serializable {

	/**
	 * Clear all bindings.
	 */
	void clear();

	/**
	 * Bind given property to given component.
	 * @param <T> Property type
	 * @param property The property to bind (not null)
	 * @param component The component
	 */
	<T> void set(Property<T> property, InputFilter<T> component);

	/**
	 * Get the {@link InputFilter} bound to given property, if available.
	 * @param <T> Property type
	 * @param property The property for which to obtain the component (not null)
	 * @return Optional {@link InputFilter} bound to given property
	 */
	<T> Optional<InputFilter<T>> get(Property<T> property);

	/**
	 * Get the registered bindings.
	 * @return the registered bindings
	 */
	Stream<Binding<Property<?>, InputFilter<?>>> stream();

	/**
	 * Get the registered bindings.
	 * @return the registered bindings
	 */
	Stream<Binding<Property<Object>, InputFilter<Object>>> bindings();

	/**
	 * Get the bindings size.
	 * @return the bindings size
	 */
	int size();

	/**
	 * Create a new {@link InputFilterPropertyRegistry}.
	 * @return A new registry
	 */
	static InputFilterPropertyRegistry create() {
		return new DefaultInputFilterPropertyRegistry();
	}

}
