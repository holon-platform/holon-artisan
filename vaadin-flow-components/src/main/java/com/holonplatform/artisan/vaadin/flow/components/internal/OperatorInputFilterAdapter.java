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

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.core.Registration;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.vaadin.flow.components.Input;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * {@link InputFilter} with operator support adapter.
 * 
 * @param <T> Value type
 *
 * @since 1.0.0
 */
public class OperatorInputFilterAdapter<T> extends CustomField<T> implements InputFilter<T> {

	private static final long serialVersionUID = 3088317891629157757L;

	private final Property<? super T> property;
	private final Input<T> input;
	private final FilterOperatorSelect operatorSelect;
	private final Function<T, QueryFilter> filterProvider;

	public OperatorInputFilterAdapter(Property<? super T> property, Input<T> input, InputFilterOperator... operators) {
		super();
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		ObjectUtils.argumentNotNull(input, "Input must be not null");
		this.property = property;
		this.input = input;
		// style class
		getElement().getClassList().add("h-operator-input-filter");
		// operator select
		this.operatorSelect = new FilterOperatorSelect(operators);
		this.operatorSelect.getStyle().set("margin-right", "2px");
		// compose
		final HorizontalLayout content = new HorizontalLayout();
		content.setPadding(false);
		content.setMargin(false);
		content.setSpacing(true);
		content.setAlignItems(Alignment.CENTER);
		content.add(operatorSelect);
		final Component inputComponent = input.getComponent();
		content.add(inputComponent);
		content.setFlexGrow(1, inputComponent);
		add(content);
		// filter provider
		this.filterProvider = new DefaultOperatorQueryFilterProvider<>(getProperty(), getOperatorSupplier(),
				() -> false);
	}

	/**
	 * Get the query filter property.
	 * @return the property
	 */
	protected Property<? super T> getProperty() {
		return property;
	}

	/**
	 * Get the {@link Input} reference.
	 * @return the input reference
	 */
	protected Input<T> getInput() {
		return input;
	}

	/**
	 * Get the filter operator select.
	 * @return the filter operator select
	 */
	protected FilterOperatorSelect getOperatorSelect() {
		return operatorSelect;
	}

	@Override
	public Optional<QueryFilter> getFilter() {
		return Optional.ofNullable(filterProvider.apply(getValue()));
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

	@Override
	public Component getComponent() {
		return this;
	}

	/**
	 * Get the default operator, if available.
	 * @return Optional default operator
	 */
	public Optional<InputFilterOperator> getDefaultOperator() {
		return getOperatorSelect().getDefaultOperator();
	}

	/**
	 * Set the default operator.
	 * @param defaultOperator the default operator to set
	 */
	public void setDefaultOperator(InputFilterOperator defaultOperator) {
		getOperatorSelect().setDefaultOperator(defaultOperator);
	}

	/**
	 * Get whether the filter operator select is visible.
	 * @return Whether the filter operator select is visible
	 */
	public boolean isOperatorSelectVisible() {
		return getOperatorSelect().isVisible();
	}

	/**
	 * Set whether the filter operator select is visible.
	 * @param visible whether the filter operator select is visible
	 */
	public void setOperatorSelectVisible(boolean visible) {
		getOperatorSelect().setVisible(visible);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		getInput().setReadOnly(readOnly);
		if (getOperatorSelect().isVisible()) {
			getOperatorSelect().setReadOnly(readOnly);
		}
	}

	@Override
	public boolean isReadOnly() {
		return getInput().isReadOnly();
	}

	@Override
	public boolean isRequired() {
		return getInput().isRequired();
	}

	@Override
	public void setRequired(boolean required) {
		getInput().setRequired(required);
	}

	@Override
	public void focus() {
		getInput().focus();
	}

	@Override
	public Registration addValueChangeListener(
			com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener<T, com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent<T>> listener) {
		return getInput().addValueChangeListener(listener);
	}

	@Override
	protected T generateModelValue() {
		return getInput().getValue();
	}

	@Override
	protected void setPresentationValue(T newPresentationValue) {
		getInput().setValue(newPresentationValue);
	}

	@Override
	public void clear() {
		super.clear();
	}

}
