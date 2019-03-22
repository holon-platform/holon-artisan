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
import java.util.function.Supplier;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.artisan.vaadin.flow.components.builders.OperatorInputFilterBuilder.FilterOperatorSelectConfigurator;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultFilterOperatorSelectConfigurator;
import com.holonplatform.core.Registration;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.vaadin.flow.components.HasLabel;
import com.holonplatform.vaadin.flow.components.HasPlaceholder;
import com.holonplatform.vaadin.flow.components.HasTitle;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.events.InvalidChangeEventNotifier;
import com.holonplatform.vaadin.flow.internal.components.events.DefaultValueChangeEvent;
import com.holonplatform.vaadin.flow.internal.components.support.RegistrationAdapter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.value.HasValueChangeMode;

/**
 * {@link InputFilter} with operator support adapter.
 * 
 * @param <T> Value type
 *
 * @since 1.0.0
 */
public class OperatorInputFilterAdapter<T> extends CustomField<T> implements InputFilter<T>, HasStyle {

	private static final long serialVersionUID = 3088317891629157757L;

	private final Property<? super T> property;
	private final Input<T> input;
	private final FilterOperatorSelect operatorSelect;

	private Supplier<Boolean> ignoreCaseSupplier = () -> false;

	public OperatorInputFilterAdapter(Property<? super T> property, FilterOperatorSelect operatorSelect) {
		this(property, null, operatorSelect);
	}

	public OperatorInputFilterAdapter(Property<? super T> property, Input<T> input,
			FilterOperatorSelect operatorSelect) {
		super();
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		ObjectUtils.argumentNotNull(operatorSelect, "Filter operator select must be not null");
		this.property = property;
		this.input = input;
		// style classes
		getElement().getClassList().add("h-input-filter");
		getElement().getClassList().add("h-operator-input-filter");
		// operator select
		this.operatorSelect = operatorSelect;
		this.operatorSelect.getStyle().set("margin-right", "2px");
		this.operatorSelect.addValueChangeListener(e -> {
			getInput().ifPresent(i -> i.setReadOnly((e.getValue() != null
					&& (e.getValue() == InputFilterOperator.EMPTY || e.getValue() == InputFilterOperator.NOT_EMPTY))));
		});
		if (input != null) {
			build(input);
		}
	}

	/**
	 * Build the component.
	 * @param input The input component to use (not null)
	 */
	public void build(Input<T> input) {
		ObjectUtils.argumentNotNull(input, "Input must be not null");
		getChildren().forEach(c -> remove(c));
		final HorizontalLayout content = new HorizontalLayout();
		content.setPadding(false);
		content.setMargin(false);
		content.setSpacing(false);
		content.setAlignItems(Alignment.CENTER);
		content.add(getOperatorSelect());
		final Component inputComponent = input.getComponent();
		content.add(inputComponent);
		content.setFlexGrow(1, inputComponent);
		add(content);
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
	 * @return Optional input reference
	 */
	protected Optional<Input<T>> getInput() {
		return Optional.ofNullable(input);
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
		return Optional.ofNullable(InputFilterOperator.getQueryFilter(getValue(), getProperty(),
				getFilterOperator().orElse(null), isIgnoreCase()));
	}

	/**
	 * Get the filter ignore case mode.
	 * @return the filter ignore case mode
	 */
	protected boolean isIgnoreCase() {
		final Boolean ignoreCase = ignoreCaseSupplier.get();
		return (ignoreCase != null) ? ignoreCase : false;
	}

	/**
	 * Get the current filter operator, if available.
	 * @return Optional filter operator
	 */
	protected Optional<InputFilterOperator> getFilterOperator() {
		if (getOperatorSelect().isVisible()) {
			return Optional.ofNullable(getOperatorSelect().getValue());
		}
		return getOperatorSelect().getDefaultOperator();
	}

	@Override
	public void reset() {
		InputFilter.super.reset();
		// reset filter operator
		if (getOperatorSelect().isVisible()) {
			getOperatorSelect().clear();
		}
	}

	@Override
	public Component getComponent() {
		return this;
	}

	/**
	 * Get a {@link FilterOperatorSelectConfigurator} to configure the filter operator select.
	 * @return A new {@link FilterOperatorSelectConfigurator}
	 */
	public FilterOperatorSelectConfigurator getFilterOperatorSelectConfigurator() {
		return new DefaultFilterOperatorSelectConfigurator(getOperatorSelect());
	}

	/**
	 * Get the supplier for the filter ignore case mode.
	 * @return the ignore case supplier
	 */
	public Supplier<Boolean> getIgnoreCaseSupplier() {
		return ignoreCaseSupplier;
	}

	/**
	 * Set the supplier for the filter ignore case mode.
	 * @param ignoreCaseSupplier the ignore case supplier to set (not null)
	 */
	public void setIgnoreCaseSupplier(Supplier<Boolean> ignoreCaseSupplier) {
		ObjectUtils.argumentNotNull(ignoreCaseSupplier, "Ignore case supplier must be not null");
		this.ignoreCaseSupplier = ignoreCaseSupplier;
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
	public boolean isRequired() {
		return isRequiredIndicatorVisible();
	}

	@Override
	public void setRequired(boolean required) {
		setRequiredIndicatorVisible(required);
	}

	@Override
	public void focus() {
		getInput().ifPresent(i -> i.focus());
	}

	@Override
	public Registration addValueChangeListener(
			com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener<T, com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent<T>> listener) {
		ObjectUtils.argumentNotNull(listener, "ValueChangeListener must be not null");
		return RegistrationAdapter.adapt(super.addValueChangeListener(e -> listener
				.valueChange(new DefaultValueChangeEvent<>(this, e.getOldValue(), e.getValue(), e.isFromClient()))));
	}

	@Override
	protected T generateModelValue() {
		return getInput().map(i -> i.getValue()).orElse(null);
	}

	@Override
	protected void setPresentationValue(T newPresentationValue) {
		getInput().ifPresent(i -> i.setValue(newPresentationValue));
	}

	@Override
	public Optional<HasLabel> hasLabel() {
		return Optional.of(HasLabel.create(() -> getLabel(), l -> setLabel(l)));
	}

	@Override
	public Optional<HasValidation> hasValidation() {
		return Optional.of(this);
	}

	@Override
	public Optional<HasPlaceholder> hasPlaceholder() {
		return getInput().flatMap(i -> i.hasPlaceholder());
	}

	@Override
	public Optional<HasValueChangeMode> hasValueChangeMode() {
		return getInput().flatMap(i -> i.hasValueChangeMode());
	}

	@Override
	public Optional<HasTitle> hasTitle() {
		return getInput().flatMap(i -> i.hasTitle());
	}

	@Override
	public Optional<InvalidChangeEventNotifier> hasInvalidChangeEventNotifier() {
		return getInput().flatMap(i -> i.hasInvalidChangeEventNotifier());
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
	}

	@Override
	public boolean isReadOnly() {
		return super.isReadOnly();
	}

	@Override
	public void clear() {
		InputFilter.super.clear();
	}

}
