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
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultStringInputFilterBuilder;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.builders.StringInputConfigurator;

/**
 * {@link String} type {@link InputFilter} builder.
 *
 * @since 1.0.0
 */
public interface StringInputFilterBuilder
		extends OperatorInputFilterBuilder<String, StringInputFilterBuilder>,
		StringInputConfigurator<StringInputFilterBuilder> {

	public static final Localizable IGNORE_CASE_TRUE_MENU_ITEM = Localizable.of("Ignore case",
			"com.holonplatform.artisan.vaadin.flow.components.input.filter.ignore.case.true");
	public static final Localizable IGNORE_CASE_FALSE_MENU_ITEM = Localizable.of("Case sensitive",
			"com.holonplatform.artisan.vaadin.flow.components.input.filter.ignore.case.false");

	/**
	 * Set whether to ignore case by default.
	 * @param ignoreCase Whether to ignore case
	 * @return this
	 */
	StringInputFilterBuilder defaultIgnoreCase(boolean ignoreCase);

	/**
	 * Set whether the ignore case selection is allowed.
	 * @param ignoreCaseSelectionAllowed Whether the ignore case selection is allowed
	 * @return this
	 */
	StringInputFilterBuilder ignoreCaseSelectionAllowed(boolean ignoreCaseSelectionAllowed);

	/**
	 * Create a new {@link StringInputFilterBuilder}.
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link StringInputFilterBuilder}
	 */
	static StringInputFilterBuilder create(Property<String> property) {
		return new DefaultStringInputFilterBuilder(property);
	}

}
