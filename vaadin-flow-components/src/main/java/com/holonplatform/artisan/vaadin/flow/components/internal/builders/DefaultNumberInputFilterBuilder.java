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

import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Consumer;

import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.artisan.vaadin.flow.components.builders.NumberInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.FilterOperatorSelect;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.builders.NumberInputBuilder;
import com.holonplatform.vaadin.flow.components.builders.ShortcutConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.DelegatedShortcutConfigurator;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.CompositionEndEvent;
import com.vaadin.flow.component.CompositionStartEvent;
import com.vaadin.flow.component.CompositionUpdateEvent;
import com.vaadin.flow.component.DetachEvent;
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
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.Element;

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

	@SuppressWarnings("unchecked")
	public DefaultNumberInputFilterBuilder(Property<T> property) {
		super(property, InputFilterOperator.EQUAL, InputFilterOperator.NOT_EQUAL, InputFilterOperator.GREATER_THAN,
				InputFilterOperator.GREATER_OR_EQUAL, InputFilterOperator.LESS_THAN, InputFilterOperator.LESS_OR_EQUAL,
				InputFilterOperator.EMPTY, InputFilterOperator.NOT_EMPTY);
		this.inputBuilder = NumberInputBuilder.create((Class<T>) property.getType())
				// .clearButtonVisible(true) // TODO
				.label(property);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.internal.builders.AbstractOperatorInputFilterBuilder#getBuilder(
	 * )
	 */
	@Override
	protected NumberInputFilterBuilder<T> getBuilder() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.internal.builders.AbstractOperatorInputFilterBuilder#buildInput(
	 * com.holonplatform.artisan.vaadin.flow.components.internal.FilterOperatorSelect)
	 */
	@Override
	protected Input<T> buildInput(FilterOperatorSelect operatorSelect) {
		if (operatorSelect.isVisible()) {
			inputBuilder.prefixComponent(operatorSelect);
		}
		return inputBuilder.build();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.InputConfigurator#readOnly(boolean)
	 */
	@Override
	public NumberInputFilterBuilder<T> readOnly(boolean readOnly) {
		inputBuilder.readOnly(readOnly);
		return this;
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
	 * @see com.holonplatform.vaadin.flow.components.builders.InputConfigurator#required(boolean)
	 */
	@Override
	public NumberInputFilterBuilder<T> required(boolean required) {
		inputBuilder.required(required);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#id(java.lang.String)
	 */
	@Override
	public NumberInputFilterBuilder<T> id(String id) {
		inputBuilder.id(id);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#visible(boolean)
	 */
	@Override
	public NumberInputFilterBuilder<T> visible(boolean visible) {
		inputBuilder.visible(visible);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#elementConfiguration(java.util.function.
	 * Consumer)
	 */
	@Override
	public NumberInputFilterBuilder<T> elementConfiguration(Consumer<Element> element) {
		inputBuilder.elementConfiguration(element);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withAttachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public NumberInputFilterBuilder<T> withAttachListener(ComponentEventListener<AttachEvent> listener) {
		inputBuilder.withAttachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withDetachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public NumberInputFilterBuilder<T> withDetachListener(ComponentEventListener<DetachEvent> listener) {
		inputBuilder.withDetachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withThemeName(java.lang.String)
	 */
	@Override
	public NumberInputFilterBuilder<T> withThemeName(String themeName) {
		inputBuilder.withThemeName(themeName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener)
	 */
	@Override
	public NumberInputFilterBuilder<T> withEventListener(String eventType, DomEventListener listener) {
		inputBuilder.withEventListener(eventType, listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener, java.lang.String)
	 */
	@Override
	public NumberInputFilterBuilder<T> withEventListener(String eventType, DomEventListener listener, String filter) {
		inputBuilder.withEventListener(eventType, listener, filter);
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
	 * @see com.holonplatform.vaadin.flow.components.builders.HasEnabledConfigurator#enabled(boolean)
	 */
	@Override
	public NumberInputFilterBuilder<T> enabled(boolean enabled) {
		inputBuilder.enabled(enabled);
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
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#width(java.lang.String)
	 */
	@Override
	public NumberInputFilterBuilder<T> width(String width) {
		inputBuilder.width(width);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#height(java.lang.String)
	 */
	@Override
	public NumberInputFilterBuilder<T> height(String height) {
		inputBuilder.height(height);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minWidth(java.lang.String)
	 */
	@Override
	public NumberInputFilterBuilder<T> minWidth(String minWidth) {
		inputBuilder.minWidth(minWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxWidth(java.lang.String)
	 */
	@Override
	public NumberInputFilterBuilder<T> maxWidth(String maxWidth) {
		inputBuilder.maxWidth(maxWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minHeight(java.lang.String)
	 */
	@Override
	public NumberInputFilterBuilder<T> minHeight(String minHeight) {
		inputBuilder.minHeight(minHeight);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxHeight(java.lang.String)
	 */
	@Override
	public NumberInputFilterBuilder<T> maxHeight(String maxHeight) {
		inputBuilder.maxHeight(maxHeight);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleNames(java.lang.String[])
	 */
	@Override
	public NumberInputFilterBuilder<T> styleNames(String... styleNames) {
		inputBuilder.styleNames(styleNames);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleName(java.lang.String)
	 */
	@Override
	public NumberInputFilterBuilder<T> styleName(String styleName) {
		inputBuilder.styleName(styleName);
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
		return new DelegatedShortcutConfigurator<>(inputBuilder.withFocusShortcut(key), this);
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
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasLabelConfigurator#label(com.holonplatform.core.i18n.
	 * Localizable)
	 */
	@Override
	public NumberInputFilterBuilder<T> label(Localizable label) {
		inputBuilder.label(label);
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
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasPatternConfigurator#pattern(java.lang.String)
	 */
	@Override
	public NumberInputFilterBuilder<T> pattern(String pattern) {
		inputBuilder.pattern(pattern);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasPatternConfigurator#preventInvalidInput(boolean)
	 */
	@Override
	public NumberInputFilterBuilder<T> preventInvalidInput(boolean preventInvalidInput) {
		inputBuilder.preventInvalidInput(preventInvalidInput);
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
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.DeferrableLocalizationConfigurator#withDeferredLocalization(
	 * boolean)
	 */
	@Override
	public NumberInputFilterBuilder<T> withDeferredLocalization(boolean deferredLocalization) {
		inputBuilder.withDeferredLocalization(deferredLocalization);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasDeferrableLocalization#isDeferredLocalizationEnabled()
	 */
	@Override
	public boolean isDeferredLocalizationEnabled() {
		return inputBuilder.isDeferredLocalizationEnabled();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.NumberInputConfigurator#locale(java.util.Locale)
	 */
	@Override
	public NumberInputFilterBuilder<T> locale(Locale locale) {
		inputBuilder.locale(locale);
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
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.NumberInputConfigurator#allowNegative(boolean)
	 */
	@Override
	public NumberInputFilterBuilder<T> allowNegative(boolean allowNegative) {
		inputBuilder.allowNegative(allowNegative);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.NumberInputConfigurator#minDecimals(int)
	 */
	@Override
	public NumberInputFilterBuilder<T> minDecimals(int minDecimals) {
		inputBuilder.minDecimals(minDecimals);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.NumberInputConfigurator#maxDecimals(int)
	 */
	@Override
	public NumberInputFilterBuilder<T> maxDecimals(int maxDecimals) {
		inputBuilder.maxDecimals(maxDecimals);
		return this;
	}

}
