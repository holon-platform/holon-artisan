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
package com.holonplatform.artisan.vaadin.flow.components.internal.support;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.utils.Obj;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.BoundComponentGroup.Binding;

/**
 * Default {@link InputFilterPropertyRegistry} implementation.
 *
 * @since 1.0.0
 */
public class DefaultInputFilterPropertyRegistry implements InputFilterPropertyRegistry {

	private static final long serialVersionUID = 163876909431298588L;

	private final Map<Property<?>, InputFilter<?>> components = new LinkedHashMap<>();

	@Override
	public void clear() {
		components.clear();
	}

	@Override
	public <T> void set(Property<T> property, InputFilter<T> component) {
		Obj.argumentNotNull(property, "Property must be not null");
		components.put(property, component);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Optional<InputFilter<T>> get(Property<T> property) {
		Obj.argumentNotNull(property, "Property must be not null");
		final InputFilter<T> component = (InputFilter<T>) components.get(property);
		return Optional.ofNullable(component);
	}

	@Override
	public Stream<Binding<Property<?>, InputFilter<?>>> stream() {
		return components.entrySet().stream().filter(e -> e.getValue() != null)
				.map(e -> Binding.create(e.getKey(), e.getValue()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Stream<Binding<Property<Object>, InputFilter<Object>>> bindings() {
		return components.entrySet().stream().filter(e -> e.getValue() != null)
				.map(e -> Binding.create((Property<Object>) e.getKey(), (InputFilter<Object>) e.getValue()));
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.internal.components.support.InputPropertyRegistry#size()
	 */
	@Override
	public int size() {
		return components.size();
	}

}
