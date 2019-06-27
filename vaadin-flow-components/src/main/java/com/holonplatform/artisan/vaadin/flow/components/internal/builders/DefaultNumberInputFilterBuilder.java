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
package com.holonplatform.artisan.vaadin.flow.components.internal.builders;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.artisan.vaadin.flow.components.builders.NumberInputFilterBuilder;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.builders.NumberInputBuilder;
import com.holonplatform.vaadin.flow.components.builders.ShortcutConfigurator;
import com.holonplatform.vaadin.flow.components.events.ReadonlyChangeListener;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.CompositionEndEvent;
import com.vaadin.flow.component.CompositionStartEvent;
import com.vaadin.flow.component.CompositionUpdateEvent;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.InputEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyDownEvent;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.KeyPressEvent;
import com.vaadin.flow.component.KeyUpEvent;
import com.vaadin.flow.component.textfield.Autocomplete;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;

/**
 * Default {@link NumberInputFilterBuilder} implementation.
 *
 * @param <T> Number type
 *
 * @since 1.0.0
 */
public class DefaultNumberInputFilterBuilder<T extends Number> extends
		AbstractOperatorInputFilterBuilder<T, NumberInputFilterBuilder<T>> implements NumberInputFilterBuilder<T> {

	private final NumberInputBuilder<T> inputBuilder;
	private final NumberInputBuilder<T> additionalInputBuilder;

	@SuppressWarnings("unchecked")
	public DefaultNumberInputFilterBuilder(Property<T> property) {
		super(property, InputFilterOperator.EQUAL, InputFilterOperator.NOT_EQUAL, InputFilterOperator.GREATER_THAN,
				InputFilterOperator.GREATER_OR_EQUAL, InputFilterOperator.LESS_THAN, InputFilterOperator.LESS_OR_EQUAL,
				InputFilterOperator.EMPTY, InputFilterOperator.NOT_EMPTY, InputFilterOperator.BETWEEN);
		this.inputBuilder = NumberInputBuilder.create((Class<T>) property.getType()).clearButtonVisible(true);
		this.additionalInputBuilder = NumberInputBuilder.create((Class<T>) property.getType()).clearButtonVisible(true);
		label(property);
	}

	@Override
	protected NumberInputFilterBuilder<T> getConfigurator() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.internal.builders.AbstractOperatorInputFilterBuilder#buildInput(
	 * com.holonplatform.artisan.vaadin.flow.components.internal.FilterOperatorSelect)
	 */
	@Override
	protected Input<T> buildInput() {
		return inputBuilder.build();
	}

	@Override
	protected Optional<Input<T>> buildAdditionalInput() {
		return Optional.of(additionalInputBuilder.build());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.InputConfigurator#withValueChangeListener(com.holonplatform.
	 * vaadin.flow.components.ValueHolder.ValueChangeListener)
	 */
	@Override
	public NumberInputFilterBuilder<T> withValueChangeListener(ValueChangeListener<T, ValueChangeEvent<T>> listener) {
		inputBuilder.withValueChangeListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.InputValueConfigurator#withValue(java.lang.Object)
	 */
	@Override
	public NumberInputFilterBuilder<T> withValue(T value) {
		inputBuilder.withValue(value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasAutocompleteConfigurator#autocomplete(com.vaadin.flow.
	 * component.textfield.Autocomplete)
	 */
	@Override
	public NumberInputFilterBuilder<T> autocomplete(Autocomplete autocomplete) {
		inputBuilder.autocomplete(autocomplete);
		additionalInputBuilder.autocomplete(autocomplete);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.NumberInputConfigurator#autoselect(boolean)
	 */
	@Override
	public NumberInputFilterBuilder<T> autoselect(boolean autoselect) {
		inputBuilder.autoselect(autoselect);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.NumberInputConfigurator#clearButtonVisible(boolean)
	 */
	@Override
	public NumberInputFilterBuilder<T> clearButtonVisible(boolean clearButtonVisible) {
		inputBuilder.clearButtonVisible(clearButtonVisible);
		additionalInputBuilder.clearButtonVisible(clearButtonVisible);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.InputNotifierConfigurator#withInputListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public NumberInputFilterBuilder<T> withInputListener(ComponentEventListener<InputEvent> listener) {
		inputBuilder.withInputListener(listener);
		additionalInputBuilder.withInputListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.KeyNotifierConfigurator#withKeyDownListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public NumberInputFilterBuilder<T> withKeyDownListener(ComponentEventListener<KeyDownEvent> listener) {
		inputBuilder.withKeyDownListener(listener);
		additionalInputBuilder.withKeyDownListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.KeyNotifierConfigurator#withKeyPressListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public NumberInputFilterBuilder<T> withKeyPressListener(ComponentEventListener<KeyPressEvent> listener) {
		inputBuilder.withKeyPressListener(listener);
		additionalInputBuilder.withKeyPressListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.KeyNotifierConfigurator#withKeyUpListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public NumberInputFilterBuilder<T> withKeyUpListener(ComponentEventListener<KeyUpEvent> listener) {
		inputBuilder.withKeyUpListener(listener);
		additionalInputBuilder.withKeyUpListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.KeyNotifierConfigurator#withKeyDownListener(com.vaadin.flow.
	 * component.Key, com.vaadin.flow.component.ComponentEventListener, com.vaadin.flow.component.KeyModifier[])
	 */
	@Override
	public NumberInputFilterBuilder<T> withKeyDownListener(Key key, ComponentEventListener<KeyDownEvent> listener,
			KeyModifier... modifiers) {
		inputBuilder.withKeyDownListener(key, listener, modifiers);
		additionalInputBuilder.withKeyDownListener(key, listener, modifiers);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.KeyNotifierConfigurator#withKeyPressListener(com.vaadin.flow.
	 * component.Key, com.vaadin.flow.component.ComponentEventListener, com.vaadin.flow.component.KeyModifier[])
	 */
	@Override
	public NumberInputFilterBuilder<T> withKeyPressListener(Key key, ComponentEventListener<KeyPressEvent> listener,
			KeyModifier... modifiers) {
		inputBuilder.withKeyPressListener(key, listener, modifiers);
		additionalInputBuilder.withKeyPressListener(key, listener, modifiers);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.KeyNotifierConfigurator#withKeyUpListener(com.vaadin.flow.
	 * component.Key, com.vaadin.flow.component.ComponentEventListener, com.vaadin.flow.component.KeyModifier[])
	 */
	@Override
	public NumberInputFilterBuilder<T> withKeyUpListener(Key key, ComponentEventListener<KeyUpEvent> listener,
			KeyModifier... modifiers) {
		inputBuilder.withKeyUpListener(key, listener, modifiers);
		additionalInputBuilder.withKeyUpListener(key, listener, modifiers);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.HasValueChangeModeConfigurator#valueChangeMode(com.vaadin.flow.
	 * data.value.ValueChangeMode)
	 */
	@Override
	public NumberInputFilterBuilder<T> valueChangeMode(ValueChangeMode valueChangeMode) {
		inputBuilder.valueChangeMode(valueChangeMode);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasAutofocusConfigurator#autofocus(boolean)
	 */
	@Override
	public NumberInputFilterBuilder<T> autofocus(boolean autofocus) {
		inputBuilder.autofocus(autofocus);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#tabIndex(int)
	 */
	@Override
	public NumberInputFilterBuilder<T> tabIndex(int tabIndex) {
		inputBuilder.tabIndex(tabIndex);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#withFocusListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public NumberInputFilterBuilder<T> withFocusListener(ComponentEventListener<FocusEvent<Component>> listener) {
		inputBuilder.withFocusListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#withBlurListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public NumberInputFilterBuilder<T> withBlurListener(ComponentEventListener<BlurEvent<Component>> listener) {
		inputBuilder.withBlurListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#withFocusShortcut(com.vaadin.flow.
	 * component.Key)
	 */
	@Override
	public ShortcutConfigurator<NumberInputFilterBuilder<T>> withFocusShortcut(Key key) {
		return ShortcutConfigurator.delegated(inputBuilder.withFocusShortcut(key), this);
	}

	@Override
	public NumberInputFilterBuilder<T> withReadonlyChangeListener(ReadonlyChangeListener listener) {
		inputBuilder.withReadonlyChangeListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.HasPrefixAndSuffixConfigurator#prefixComponent(com.vaadin.flow.
	 * component.Component)
	 */
	@Override
	public NumberInputFilterBuilder<T> prefixComponent(Component component) {
		inputBuilder.prefixComponent(component);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.HasPrefixAndSuffixConfigurator#suffixComponent(com.vaadin.flow.
	 * component.Component)
	 */
	@Override
	public NumberInputFilterBuilder<T> suffixComponent(Component component) {
		inputBuilder.suffixComponent(component);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.CompositionNotifierConfigurator#withCompositionStartListener(
	 * com.vaadin.flow.component.ComponentEventListener)
	 */
	@Override
	public NumberInputFilterBuilder<T> withCompositionStartListener(
			ComponentEventListener<CompositionStartEvent> listener) {
		inputBuilder.withCompositionStartListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.CompositionNotifierConfigurator#withCompositionUpdateListener(
	 * com.vaadin.flow.component.ComponentEventListener)
	 */
	@Override
	public NumberInputFilterBuilder<T> withCompositionUpdateListener(
			ComponentEventListener<CompositionUpdateEvent> listener) {
		inputBuilder.withCompositionUpdateListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.CompositionNotifierConfigurator#withCompositionEndListener(com.
	 * vaadin.flow.component.ComponentEventListener)
	 */
	@Override
	public NumberInputFilterBuilder<T> withCompositionEndListener(
			ComponentEventListener<CompositionEndEvent> listener) {
		inputBuilder.withCompositionEndListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.HasPlaceholderConfigurator#placeholder(com.holonplatform.core.
	 * i18n.Localizable)
	 */
	@Override
	public NumberInputFilterBuilder<T> placeholder(Localizable placeholder) {
		inputBuilder.placeholder(placeholder);
		additionalInputBuilder.placeholder(placeholder);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasTitleConfigurator#title(com.holonplatform.core.i18n.
	 * Localizable)
	 */
	@Override
	public NumberInputFilterBuilder<T> title(Localizable title) {
		inputBuilder.title(title);
		additionalInputBuilder.title(title);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasPatternConfigurator#pattern(java.lang.String)
	 */
	@Override
	public NumberInputFilterBuilder<T> pattern(String pattern) {
		inputBuilder.pattern(pattern);
		additionalInputBuilder.pattern(pattern);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasPatternConfigurator#preventInvalidInput(boolean)
	 */
	@Override
	public NumberInputFilterBuilder<T> preventInvalidInput(boolean preventInvalidInput) {
		inputBuilder.preventInvalidInput(preventInvalidInput);
		additionalInputBuilder.preventInvalidInput(preventInvalidInput);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.HasThemeVariantConfigurator#withThemeVariants(java.lang.Enum[])
	 */
	@Override
	public NumberInputFilterBuilder<T> withThemeVariants(TextFieldVariant... variants) {
		inputBuilder.withThemeVariants(variants);
		additionalInputBuilder.withThemeVariants(variants);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.NumberInputConfigurator#locale(java.util.Locale)
	 */
	@Override
	public NumberInputFilterBuilder<T> locale(Locale locale) {
		inputBuilder.locale(locale);
		additionalInputBuilder.locale(locale);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.NumberInputConfigurator#numberFormat(java.text.NumberFormat)
	 */
	@Override
	public NumberInputFilterBuilder<T> numberFormat(NumberFormat numberFormat) {
		inputBuilder.numberFormat(numberFormat);
		additionalInputBuilder.numberFormat(numberFormat);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.NumberInputConfigurator#numberFormatPattern(java.lang.String)
	 */
	@Override
	public NumberInputFilterBuilder<T> numberFormatPattern(String numberFormatPattern) {
		inputBuilder.numberFormatPattern(numberFormatPattern);
		additionalInputBuilder.numberFormatPattern(numberFormatPattern);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.NumberInputConfigurator#allowNegative(boolean)
	 */
	@Override
	public NumberInputFilterBuilder<T> allowNegative(boolean allowNegative) {
		inputBuilder.allowNegative(allowNegative);
		additionalInputBuilder.allowNegative(allowNegative);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.NumberInputConfigurator#minDecimals(int)
	 */
	@Override
	public NumberInputFilterBuilder<T> minDecimals(int minDecimals) {
		inputBuilder.minDecimals(minDecimals);
		additionalInputBuilder.minDecimals(minDecimals);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.NumberInputConfigurator#maxDecimals(int)
	 */
	@Override
	public NumberInputFilterBuilder<T> maxDecimals(int maxDecimals) {
		inputBuilder.maxDecimals(maxDecimals);
		additionalInputBuilder.maxDecimals(maxDecimals);
		return this;
	}

	@Override
	public <A> NumberInputFilterBuilder<T> withAdapter(Class<A> type, Function<Input<T>, A> adapter) {
		inputBuilder.withAdapter(type, adapter);
		return this;
	}

}
