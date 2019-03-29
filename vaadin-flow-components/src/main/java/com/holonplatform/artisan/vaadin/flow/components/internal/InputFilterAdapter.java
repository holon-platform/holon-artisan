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
package com.holonplatform.artisan.vaadin.flow.components.internal;

import java.util.Optional;
import java.util.function.Function;

import com.holonplatform.artisan.core.utils.Obj;
import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.core.Registration;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.vaadin.flow.components.HasLabel;
import com.holonplatform.vaadin.flow.components.HasPlaceholder;
import com.holonplatform.vaadin.flow.components.HasTitle;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.events.InvalidChangeEventNotifier;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasEnabled;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.data.value.HasValueChangeMode;

/**
 * Adapter to turn a {@link Input} component into a {@link InputFilter}.
 *
 * @param <T> Value type
 *
 * @since 1.0.0
 */
public class InputFilterAdapter<T> implements InputFilter<T> {

	private static final long serialVersionUID = -2180283031752911899L;

	private final Input<T> input;
	private final Function<T, QueryFilter> filterProvider;

	private Runnable resetCallback;

	/**
	 * Constructor.
	 * @param input The {@link Input} to adapt (not null)
	 * @param filterProvider The function to provide a {@link QueryFilter} according to the Input value, or
	 *        <code>null</code> if none (not null)
	 */
	public InputFilterAdapter(Input<T> input, Function<T, QueryFilter> filterProvider) {
		super();
		Obj.argumentNotNull(input, "Input must be not null");
		Obj.argumentNotNull(filterProvider, "Filter provider must be not null");
		this.input = input;
		this.filterProvider = filterProvider;
	}

	/**
	 * Get the {@link Input} component.
	 * @return the input component
	 */
	protected Input<T> getInput() {
		return input;
	}

	/**
	 * Get the filter provider function.
	 * @return the filter provider
	 */
	protected Function<T, QueryFilter> getFilterProvider() {
		return filterProvider;
	}

	/**
	 * Get the callback to invoke when a {@link #reset()} is performed.
	 * @return the reset callback, if available
	 */
	public Optional<Runnable> getResetCallback() {
		return Optional.ofNullable(resetCallback);
	}

	/**
	 * Set the callback to invoke when a {@link #reset()} is performed.
	 * @param resetCallback the reset callback to set
	 */
	public void setResetCallback(Runnable resetCallback) {
		this.resetCallback = resetCallback;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.InputFilter#reset()
	 */
	@Override
	public void reset() {
		InputFilter.super.reset();
		// callback
		getResetCallback().ifPresent(c -> c.run());
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.Input#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		getInput().setReadOnly(readOnly);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.Input#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		return getInput().isReadOnly();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.Input#isRequired()
	 */
	@Override
	public boolean isRequired() {
		return getInput().isRequired();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.Input#setRequired(boolean)
	 */
	@Override
	public void setRequired(boolean required) {
		getInput().setRequired(required);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.Input#focus()
	 */
	@Override
	public void focus() {
		getInput().focus();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.ValueHolder#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(T value) {
		getInput().setValue(value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.ValueHolder#getValue()
	 */
	@Override
	public T getValue() {
		return getInput().getValue();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.ValueHolder#addValueChangeListener(com.holonplatform.vaadin.flow.
	 * components.ValueHolder.ValueChangeListener)
	 */
	@Override
	public Registration addValueChangeListener(ValueChangeListener<T, ValueChangeEvent<T>> listener) {
		return getInput().addValueChangeListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.HasComponent#getComponent()
	 */
	@Override
	public Component getComponent() {
		return getInput().getComponent();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.InputFilter#getFilter()
	 */
	@Override
	public Optional<QueryFilter> getFilter() {
		return Optional.ofNullable(getFilterProvider().apply(getValue()));
	}

	@Override
	public Optional<HasTitle> hasTitle() {
		return getInput().hasTitle();
	}

	@Override
	public Optional<HasPlaceholder> hasPlaceholder() {
		return getInput().hasPlaceholder();
	}

	@Override
	public Optional<InvalidChangeEventNotifier> hasInvalidChangeEventNotifier() {
		return getInput().hasInvalidChangeEventNotifier();
	}

	@Override
	public <A> Optional<A> as(Class<A> type) {
		return getInput().as(type);
	}

	@Override
	public Optional<HasValueChangeMode> hasValueChangeMode() {
		return getInput().hasValueChangeMode();
	}

	@Override
	public Optional<HasEnabled> hasEnabled() {
		return getInput().hasEnabled();
	}

	@Override
	public Optional<HasStyle> hasStyle() {
		return getInput().hasStyle();
	}

	@Override
	public Optional<HasSize> hasSize() {
		return getInput().hasSize();
	}

	@Override
	public Optional<HasLabel> hasLabel() {
		return getInput().hasLabel();
	}

	@Override
	public Optional<HasValidation> hasValidation() {
		return getInput().hasValidation();
	}

}
