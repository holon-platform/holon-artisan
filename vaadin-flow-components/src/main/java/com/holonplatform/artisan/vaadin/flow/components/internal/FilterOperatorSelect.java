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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.vaadin.flow.i18n.LocalizationProvider;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.select.Select;

/**
 * {@link InputFilterOperator} select.
 *
 * @since 1.0.0
 */
public class FilterOperatorSelect extends Select<InputFilterOperator> implements HasTheme {

	private static final long serialVersionUID = -8923120517061581962L;

	private final List<InputFilterOperator> operators;

	private InputFilterOperator defaultOperator;

	public FilterOperatorSelect(InputFilterOperator... operators) {
		super(operators);
		this.operators = Arrays.asList(operators);
		this.defaultOperator = this.operators.isEmpty() ? null : this.operators.get(0);

		// configuration
		setEmptySelectionAllowed(false);
		addThemeName("filter-operator-select");

		// default width
		setWidth("4.2em");

		// labels
		setItemLabelGenerator(operator -> operator.getSymbol());

		// captions
		setTextRenderer(operator -> LocalizationProvider.localize(operator.getCaption()).orElse(operator.getSymbol()));

		// init
		if (defaultOperator != null) {
			setValue(defaultOperator);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.vaadin.flow.component.HasValue#clear()
	 */
	@Override
	public void clear() {
		setValue(getDefaultOperator().orElse(getEmptyValue()));
	}

	/**
	 * Get the default operator, if available.
	 * @return Optional default operator
	 */
	public Optional<InputFilterOperator> getDefaultOperator() {
		return Optional.ofNullable(defaultOperator);
	}

	/**
	 * Set the default operator.
	 * @param defaultOperator the default operator to set
	 */
	public void setDefaultOperator(InputFilterOperator defaultOperator) {
		if (defaultOperator == null || !operators.contains(defaultOperator)) {
			if (!operators.isEmpty()) {
				this.defaultOperator = operators.get(0);
			}
		} else {
			this.defaultOperator = defaultOperator;
		}
	}

}
