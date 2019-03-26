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
package com.holonplatform.artisan.vaadin.flow.components.builders;

import java.time.LocalDate;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultLocalDateInputFilterBuilder;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.builders.LocalDateInputConfigurator;

/**
 * {@link LocalDate} type {@link InputFilter} builder.
 *
 * @since 1.0.0
 */
public interface LocalDateInputFilterBuilder extends OperatorInputFilterBuilder<LocalDate, LocalDateInputFilterBuilder>,
		LocalDateInputConfigurator<LocalDateInputFilterBuilder> {

	/**
	 * Create a new {@link LocalDateInputFilterBuilder}.
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link LocalDateInputFilterBuilder}
	 */
	static LocalDateInputFilterBuilder create(Property<LocalDate> property) {
		return new DefaultLocalDateInputFilterBuilder(property);
	}

}
