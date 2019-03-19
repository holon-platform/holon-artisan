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
package com.holonplatform.artisan.vaadin.flow.components.internal;

import javax.annotation.Priority;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.property.PropertyRenderer;
import com.holonplatform.core.query.QueryFilter;
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
	@Override
	public InputFilter render(Property<? extends T> property) {

		// TODO specific input filters

		// By default, render as an Input and use the "equals" operator
		return property.renderIfAvailable(Input.class).map(input -> (Input<?>) input)
				.map(input -> InputFilter.from(input, value -> getDefaultFilter(property, value))).orElse(null);
	}

	@SuppressWarnings("unchecked")
	private static QueryFilter getDefaultFilter(Property property, Object value) {
		if (!ComponentUtils.isEmptyValue(value)) {
			return QueryFilter.eq(property, ComponentUtils.trimValue(value));
		}
		return null;
	}

}
