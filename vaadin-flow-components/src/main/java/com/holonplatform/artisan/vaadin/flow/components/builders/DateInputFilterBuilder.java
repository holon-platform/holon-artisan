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

import java.util.Date;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultDateInputFilterBuilder;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.builders.DateInputConfigurator;

/**
 * {@link Date} type {@link InputFilter} builder.
 *
 * @since 1.0.0
 */
public interface DateInputFilterBuilder extends OperatorInputFilterBuilder<Date, DateInputFilterBuilder>,
		DateInputConfigurator<DateInputFilterBuilder> {

	/**
	 * Create a new {@link DateInputFilterBuilder}.
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link DateInputFilterBuilder}
	 */
	static DateInputFilterBuilder create(Property<Date> property) {
		return new DefaultDateInputFilterBuilder(property);
	}

}
