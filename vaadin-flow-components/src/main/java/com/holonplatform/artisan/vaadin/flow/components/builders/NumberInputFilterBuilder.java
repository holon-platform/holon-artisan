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

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultNumberInputFilterBuilder;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.builders.NumberInputConfigurator;

/**
 * {@link Number} type {@link InputFilter} builder.
 * 
 * @param <T> Number type
 *
 * @since 1.0.0
 */
public interface NumberInputFilterBuilder<T extends Number>
		extends OperatorInputFilterBuilder<T, NumberInputFilterBuilder<T>>,
		NumberInputConfigurator<T, NumberInputFilterBuilder<T>> {

	/**
	 * Get a new {@link NumberInputFilterBuilder} to create a numeric type {@link InputFilter}.
	 * @param <T> Number type
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link NumberInputFilterBuilder}
	 */
	static <T extends Number> NumberInputFilterBuilder<T> create(Property<T> property) {
		return new DefaultNumberInputFilterBuilder<>(property);
	}

}
