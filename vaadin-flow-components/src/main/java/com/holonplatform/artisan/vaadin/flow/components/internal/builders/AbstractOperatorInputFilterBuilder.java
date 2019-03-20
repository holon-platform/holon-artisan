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
package com.holonplatform.artisan.vaadin.flow.components.internal.builders;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.artisan.vaadin.flow.components.builders.InputFilterAdapterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.OperatorInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.DefaultOperatorQueryFilterProvider;
import com.holonplatform.artisan.vaadin.flow.components.internal.FilterOperatorSelect;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;

/**
 * Base {@link OperatorInputFilterBuilder} implementation.
 *
 * @param <T> Value type
 * @param <B> Concrete builder type
 * 
 * @since 1.0.0
 */
public abstract class AbstractOperatorInputFilterBuilder<T, B extends OperatorInputFilterBuilder<T, ValueChangeEvent<T>, B>>
		implements OperatorInputFilterBuilder<T, ValueChangeEvent<T>, B> {

	private final Property<? super T> property;

	private final List<InputFilterOperator> operators;

	private final FilterOperatorSelect operatorSelect;

	private Consumer<FilterOperatorSelectConfigurator> filterOperatorSelectConfiguration;

	public AbstractOperatorInputFilterBuilder(Property<? super T> property, InputFilterOperator... operators) {
		super();
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		this.property = property;
		this.operators = Arrays.asList(operators);
		this.operatorSelect = new FilterOperatorSelect(operators);
	}

	/**
	 * Get the actual builder.
	 * @return The actual builder
	 */
	protected abstract B getBuilder();

	/**
	 * Build the {@link Input} instance.
	 * @param operatorSelect The filter operator select
	 * @return The {@link Input} instance
	 */
	protected abstract Input<T> buildInput(FilterOperatorSelect operatorSelect);

	/**
	 * Get the ignore case mode supplier.
	 * @return the ignore case mode supplie
	 */
	protected Supplier<Boolean> getIgnoreCaseSupplier() {
		return () -> false;
	}

	/**
	 * Get the property to use to provide the query filter.
	 * @return the property
	 */
	protected Property<? super T> getProperty() {
		return property;
	}

	/**
	 * Get the filter operators select.
	 * @return the operators select
	 */
	protected FilterOperatorSelect getOperatorSelect() {
		return operatorSelect;
	}

	/**
	 * Get the operator supplier using the filter operators select, if visible.
	 * @return the operator supplier
	 */
	protected Supplier<InputFilterOperator> getOperatorSupplier() {
		if (operatorSelect.isVisible()) {
			return () -> operatorSelect.getValue();
		}
		final InputFilterOperator fixedOperator = operatorSelect.getDefaultOperator().orElse(InputFilterOperator.EQUAL);
		return () -> fixedOperator;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.OperatorInputFilterBuilder#
	 * filterOperatorSelectConfiguration(java.util.function.Consumer)
	 */
	@Override
	public B filterOperatorSelectConfiguration(Consumer<FilterOperatorSelectConfigurator> configuration) {
		this.filterOperatorSelectConfiguration = configuration;
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.builders.OperatorInputFilterBuilder#operatorSelectionAllowed(
	 * boolean)
	 */
	@Override
	public B operatorSelectionAllowed(boolean operatorSelectionAllowed) {
		getOperatorSelect().setVisible(operatorSelectionAllowed);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.OperatorInputFilterBuilder#defaultOperator(com.
	 * holonplatform.artisan.vaadin.flow.components.OperatorInputFilter.Operator)
	 */
	@Override
	public B defaultOperator(InputFilterOperator operator) {
		if (operator != null && operators.contains(operator)) {
			getOperatorSelect().setDefaultOperator(operator);
			getOperatorSelect().setValue(operator);
		}
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.OperatorInputFilterBuilder#build()
	 */
	@Override
	public InputFilter<T> build() {

		if (filterOperatorSelectConfiguration != null) {
			filterOperatorSelectConfiguration.accept(new DefaultFilterOperatorSelectConfigurator(getOperatorSelect()));
		}

		final Input<T> input = buildInput(getOperatorSelect());

		final InputFilterAdapterBuilder<T> builder = InputFilter.builder(input,
				new DefaultOperatorQueryFilterProvider<>(getProperty(), getOperatorSupplier(),
						getIgnoreCaseSupplier()));

		if (getOperatorSelect().isVisible()) {
			// reset callback
			builder.resetCallback(() -> getOperatorSelect().clear());
			// operator change
			if (operators.contains(InputFilterOperator.EMPTY) || operators.contains(InputFilterOperator.NOT_EMPTY)) {
				getOperatorSelect().addValueChangeListener(e -> {
					final InputFilterOperator operator = e.getValue();
					input.setReadOnly((operator != null
							&& (operator == InputFilterOperator.EMPTY || operator == InputFilterOperator.NOT_EMPTY)));
				});
			}
		}

		return builder.build();
	}

}
