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
import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.builders.InputConfigurator;

/**
 * {@link InputFilter} with operator selection support base builder.
 *
 * @param <T> Value type
 * @param <E> Value change event type
 * @param <B> Concrete builder type
 * 
 * @since 1.0.0
 */
public interface OperatorInputFilterBuilder<T, E extends ValueChangeEvent<T>, B extends OperatorInputFilterBuilder<T, E, B>>
		extends InputConfigurator<T, E, B> {

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.InputConfigurator#required()
	 */
	@Override
	default B required() {
		return required(true);
	}

	/**
	 * Set whether the operator selection is allowed.
	 * <p>
	 * If the operator selection is not allowed, the default filter operator will always be used.
	 * </p>
	 * @param operatorSelectionAllowed Whether the operator selection is allowed
	 * @return this
	 */
	B operatorSelectionAllowed(boolean operatorSelectionAllowed);

	/**
	 * Set the default filter operator.
	 * @param operator The default filter operator
	 * @return this
	 */
	B defaultOperator(InputFilterOperator operator);

	/**
	 * Build and returns the {@link InputFilter} instance.
	 * @return The {@link InputFilter} instance
	 */
	InputFilter<T> build();

}
