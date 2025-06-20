/*
 * Copyright 2016-2017 Axioma srl.
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
import com.holonplatform.vaadin.flow.components.ValueHolder;
import com.holonplatform.vaadin.flow.components.events.InvalidChangeEventNotifier;
import com.holonplatform.vaadin.flow.components.events.ReadonlyChangeListener;
import com.holonplatform.vaadin.flow.components.support.InputAdaptersContainer;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasEnabled;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.value.HasValueChangeMode;

/**
 * Adapter class to build a {@link InputFilter} of a different value type from another
 * {@link InputFilter}, using a suitable {@link Converter}.
 * @param <T> Presentation value type
 * @param <V> Model value type
 * @since 5.2.0
 */
public class InputFilterConverterAdapter<T, V> implements InputFilter<V> {

	private static final long serialVersionUID = -2429215257047725962L;

	private final InputFilter<T> input;
	private final Converter<T, V> converter;

	private final InputAdaptersContainer<V> adapters = InputAdaptersContainer.create();

	/**
	 * Constructor.
	 * @param input The actual InputFilter (not null)
	 * @param converter The value converter (not null)
	 */
	public InputFilterConverterAdapter(InputFilter<T> input, Converter<T, V> converter) {
		super();
		Obj.argumentNotNull(input, "Input must be not null");
		Obj.argumentNotNull(converter, "Converter must be not null");
		this.input = input;
		this.converter = converter;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.InputFilter#getFilter()
	 */
	@Override
	public Optional<QueryFilter> getFilter() {
		return input.getFilter();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.components.ValueHolder#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(V value) {
		input.setValue(convertToPresentation(value));
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.components.ValueHolder#getValue()
	 */
	@Override
	public V getValue() {
		return convertToModel(input.getValue());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.components.ValueHolder#addValueChangeListener(com.holonplatform.vaadin.
	 * components. ValueHolder.ValueChangeListener)
	 */
	@Override
	public Registration addValueChangeListener(ValueHolder.ValueChangeListener<V, ValueChangeEvent<V>> listener) {
		Obj.argumentNotNull(listener, "ValueChangeListener must be not null");
		return input.addValueChangeListener(e -> listener.valueChange(ValueChangeEvent.create(this,
				convertToModel(e.getOldValue()), convertToModel(e.getValue()), e.isUserOriginated())));
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.components.ValueComponent#getComponent()
	 */
	@Override
	public Component getComponent() {
		return input.getComponent();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.components.Input#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		input.setReadOnly(readOnly);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.components.Input#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		return input.isReadOnly();
	}

	@Override
	public Registration addReadonlyChangeListener(ReadonlyChangeListener listener) {
		return input.addReadonlyChangeListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.components.Input#isRequired()
	 */
	@Override
	public boolean isRequired() {
		return input.isRequired();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.components.Input#setRequired(boolean)
	 */
	@Override
	public void setRequired(boolean required) {
		input.setRequired(required);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.MayHaveLabel#hasLabel()
	 */
	@Override
	public Optional<HasLabel> hasLabel() {
		return input.hasLabel();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.MayHaveTitle#hasTitle()
	 */
	@Override
	public Optional<HasTitle> hasTitle() {
		return input.hasTitle();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.MayHavePlaceholder#hasPlaceholder()
	 */
	@Override
	public Optional<HasPlaceholder> hasPlaceholder() {
		return input.hasPlaceholder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.components.Input#focus()
	 */
	@Override
	public void focus() {
		input.focus();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.components.ValueHolder#getEmptyValue()
	 */
	@Override
	public V getEmptyValue() {
		return convertToModel(input.getEmptyValue());
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.components.ValueHolder#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return input.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.components.ValueHolder#clear()
	 */
	@Override
	public void clear() {
		input.clear();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.HasComponent#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		input.setVisible(visible);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.HasComponent#isVisible()
	 */
	@Override
	public boolean isVisible() {
		return input.isVisible();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.HasComponent#hasEnabled()
	 */
	@Override
	public Optional<HasEnabled> hasEnabled() {
		return input.hasEnabled();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.HasComponent#hasStyle()
	 */
	@Override
	public Optional<HasStyle> hasStyle() {
		return input.hasStyle();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.HasComponent#hasSize()
	 */
	@Override
	public Optional<HasSize> hasSize() {
		return input.hasSize();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.Input#hasValidation()
	 */
	@Override
	public Optional<HasValidation> hasValidation() {
		return input.hasValidation();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.Input#hasInvalidChangeEventNotifier()
	 */
	@Override
	public Optional<InvalidChangeEventNotifier> hasInvalidChangeEventNotifier() {
		return input.hasInvalidChangeEventNotifier();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.ValueComponent#hasValueChangeMode()
	 */
	@Override
	public Optional<HasValueChangeMode> hasValueChangeMode() {
		return input.hasValueChangeMode();
	}

	@Override
	public <A> Optional<A> as(Class<A> type) {
		Obj.argumentNotNull(type, "Type must be not null");
		final Optional<A> adapter = adapters.getAs(this, type);
		if (adapter.isPresent()) {
			return adapter;
		}
		return input.as(type);
	}

	/**
	 * Set the adapter for given type.
	 * @param <A> Adapter type
	 * @param type Adapter type (not null)
	 * @param adapter Adapter function
	 */
	public <A> void setAdapter(Class<A> type, Function<Input<V>, A> adapter) {
		adapters.setAdapter(type, adapter);
	}

	/**
	 * Convert the presentation value type to model value type.
	 * @param value Value to convert
	 * @return Converted value
	 */
	private V convertToModel(T value) {
		return converter.convertToModel(value, _valueContext())
				.getOrThrow(error -> new InputValueConversionException(error));
	}

	/**
	 * Convert the model value type to presentation value type.
	 * @param value Value to convert
	 * @return Converted value
	 */
	private T convertToPresentation(V value) {
		return converter.convertToPresentation(value, _valueContext());
	}

	/**
	 * Build the {@link ValueContext} to be used with the converter.
	 * @return the {@link ValueContext}
	 */
	private ValueContext _valueContext() {
		final Component component = getComponent();
		if (component != null) {
			return new ValueContext((Binder<?>) null, component, (HasValue<?, ?>) null);
		} else {
			return new ValueContext((Binder<?>) null, (Component) null, (HasValue<?, ?>) null);
		}
	}

	@Override
	public Optional<Input<V>> getInputFilter() {
		return Optional.of(this);
	}

}
