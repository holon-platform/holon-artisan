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

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.artisan.vaadin.flow.components.builders.StringInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.FilterOperatorSelect;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.builders.ShortcutConfigurator;
import com.holonplatform.vaadin.flow.components.builders.StringInputBuilder;
import com.holonplatform.vaadin.flow.i18n.LocalizationProvider;
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
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.textfield.Autocapitalize;
import com.vaadin.flow.component.textfield.Autocomplete;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.Element;

/**
 * Default {@link StringInputFilterBuilder} implementation.
 *
 * @since 1.0.0
 */
public class DefaultStringInputFilterBuilder extends
		AbstractOperatorInputFilterBuilder<String, StringInputFilterBuilder> implements StringInputFilterBuilder {

	private final StringInputBuilder inputBuilder;

	private boolean ignoreCaseSelectionAllowed = true;
	private boolean ignoreCase = true;

	private MenuItem ignoreCaseTrue;
	private MenuItem ignoreCaseFalse;

	public DefaultStringInputFilterBuilder(Property<String> property) {
		super(property, InputFilterOperator.CONTAINS, InputFilterOperator.STARTS_WITH, InputFilterOperator.EQUAL,
				InputFilterOperator.NOT_EQUAL, InputFilterOperator.EMPTY, InputFilterOperator.NOT_EMPTY);
		this.inputBuilder = StringInputBuilder.create().clearButtonVisible(true).label(property);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.internal.builders.AbstractOperatorInputFilterBuilder#getBuilder(
	 * )
	 */
	@Override
	protected StringInputFilterBuilder getBuilder() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.internal.builders.AbstractOperatorInputFilterBuilder#buildInput(
	 * com.holonplatform.artisan.vaadin.flow.components.internal.FilterOperatorSelect)
	 */
	@Override
	protected Input<String> buildInput(FilterOperatorSelect operatorSelect) {
		if (operatorSelect.isVisible()) {
			inputBuilder.prefixComponent(operatorSelect);
		}

		final Input<String> input = inputBuilder.build();

		// context menu
		if (ignoreCaseSelectionAllowed) {
			final ContextMenu ctxMenu = new ContextMenu();

			ignoreCaseTrue = ctxMenu.addItem(LocalizationProvider.localize(IGNORE_CASE_TRUE_MENU_ITEM)
					.orElse(IGNORE_CASE_TRUE_MENU_ITEM.getMessage()));
			ignoreCaseTrue.setCheckable(true);
			ignoreCaseTrue.setChecked(ignoreCase);

			ignoreCaseFalse = ctxMenu.addItem(LocalizationProvider.localize(IGNORE_CASE_FALSE_MENU_ITEM)
					.orElse(IGNORE_CASE_FALSE_MENU_ITEM.getMessage()));
			ignoreCaseFalse.setCheckable(true);
			ignoreCaseFalse.setChecked(!ignoreCase);

			ignoreCaseTrue.addClickListener(e -> {
				boolean checked = e.getSource().isChecked();
				ignoreCaseFalse.setChecked(!checked);
			});

			ignoreCaseFalse.addClickListener(e -> {
				boolean checked = e.getSource().isChecked();
				ignoreCaseTrue.setChecked(!checked);
			});

			ctxMenu.setTarget(input.getComponent());

		}

		return input;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.internal.builders.AbstractOperatorInputFilterBuilder#
	 * getIgnoreCaseSupplier()
	 */
	@Override
	protected Supplier<Boolean> getIgnoreCaseSupplier() {
		return () -> {
			if (ignoreCaseTrue != null) {
				return ignoreCaseTrue.isChecked();
			}
			return ignoreCase;
		};
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.builders.StringInputFilterBuilder#defaultIgnoreCase(boolean)
	 */
	@Override
	public StringInputFilterBuilder defaultIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.builders.StringInputFilterBuilder#ignoreCaseSelectionAllowed(
	 * boolean)
	 */
	@Override
	public StringInputFilterBuilder ignoreCaseSelectionAllowed(boolean ignoreCaseSelectionAllowed) {
		this.ignoreCaseSelectionAllowed = ignoreCaseSelectionAllowed;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.InputConfigurator#readOnly(boolean)
	 */
	@Override
	public StringInputFilterBuilder readOnly(boolean readOnly) {
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
	public StringInputFilterBuilder withValueChangeListener(
			ValueChangeListener<String, ValueChangeEvent<String>> listener) {
		inputBuilder.withValueChangeListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.InputConfigurator#required(boolean)
	 */
	@Override
	public StringInputFilterBuilder required(boolean required) {
		inputBuilder.required(required);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#id(java.lang.String)
	 */
	@Override
	public StringInputFilterBuilder id(String id) {
		inputBuilder.id(id);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#visible(boolean)
	 */
	@Override
	public StringInputFilterBuilder visible(boolean visible) {
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
	public StringInputFilterBuilder elementConfiguration(Consumer<Element> element) {
		inputBuilder.elementConfiguration(element);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withAttachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public StringInputFilterBuilder withAttachListener(ComponentEventListener<AttachEvent> listener) {
		inputBuilder.withAttachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withDetachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public StringInputFilterBuilder withDetachListener(ComponentEventListener<DetachEvent> listener) {
		inputBuilder.withDetachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withThemeName(java.lang.String)
	 */
	@Override
	public StringInputFilterBuilder withThemeName(String themeName) {
		inputBuilder.withThemeName(themeName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener)
	 */
	@Override
	public StringInputFilterBuilder withEventListener(String eventType, DomEventListener listener) {
		inputBuilder.withEventListener(eventType, listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener, java.lang.String)
	 */
	@Override
	public StringInputFilterBuilder withEventListener(String eventType, DomEventListener listener, String filter) {
		inputBuilder.withEventListener(eventType, listener, filter);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.InputValueConfigurator#withValue(java.lang.Object)
	 */
	@Override
	public StringInputFilterBuilder withValue(String value) {
		inputBuilder.withValue(value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.TextInputConfigurator#maxLength(int)
	 */
	@Override
	public StringInputFilterBuilder maxLength(int maxLength) {
		inputBuilder.maxLength(maxLength);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.TextInputConfigurator#minLength(int)
	 */
	@Override
	public StringInputFilterBuilder minLength(int minLength) {
		inputBuilder.minLength(minLength);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.TextInputConfigurator#emptyValuesAsNull(boolean)
	 */
	@Override
	public StringInputFilterBuilder emptyValuesAsNull(boolean emptyValuesAsNull) {
		inputBuilder.emptyValuesAsNull(emptyValuesAsNull);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.TextInputConfigurator#blankValuesAsNull(boolean)
	 */
	@Override
	public StringInputFilterBuilder blankValuesAsNull(boolean blankValuesAsNull) {
		inputBuilder.blankValuesAsNull(blankValuesAsNull);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.TextInputConfigurator#autoselect(boolean)
	 */
	@Override
	public StringInputFilterBuilder autoselect(boolean autoselect) {
		inputBuilder.autoselect(autoselect);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.TextInputConfigurator#clearButtonVisible(boolean)
	 */
	@Override
	public StringInputFilterBuilder clearButtonVisible(boolean clearButtonVisible) {
		inputBuilder.clearButtonVisible(clearButtonVisible);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasEnabledConfigurator#enabled(boolean)
	 */
	@Override
	public StringInputFilterBuilder enabled(boolean enabled) {
		inputBuilder.enabled(enabled);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasAutocompleteConfigurator#autocomplete(com.vaadin.flow.
	 * component.textfield.Autocomplete)
	 */
	@Override
	public StringInputFilterBuilder autocomplete(Autocomplete autocomplete) {
		inputBuilder.autocomplete(autocomplete);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.HasAutocapitalizeConfigurator#autocapitalize(com.vaadin.flow.
	 * component.textfield.Autocapitalize)
	 */
	@Override
	public StringInputFilterBuilder autocapitalize(Autocapitalize autocapitalize) {
		inputBuilder.autocapitalize(autocapitalize);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasAutocorrectConfigurator#autocorrect(boolean)
	 */
	@Override
	public StringInputFilterBuilder autocorrect(boolean autocorrect) {
		inputBuilder.autocorrect(autocorrect);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.InputNotifierConfigurator#withInputListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public StringInputFilterBuilder withInputListener(ComponentEventListener<InputEvent> listener) {
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
	public StringInputFilterBuilder withKeyDownListener(ComponentEventListener<KeyDownEvent> listener) {
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
	public StringInputFilterBuilder withKeyPressListener(ComponentEventListener<KeyPressEvent> listener) {
		inputBuilder.withKeyPressListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.KeyNotifierConfigurator#withKeyUpListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public StringInputFilterBuilder withKeyUpListener(ComponentEventListener<KeyUpEvent> listener) {
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
	public StringInputFilterBuilder withKeyDownListener(Key key, ComponentEventListener<KeyDownEvent> listener,
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
	public StringInputFilterBuilder withKeyPressListener(Key key, ComponentEventListener<KeyPressEvent> listener,
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
	public StringInputFilterBuilder withKeyUpListener(Key key, ComponentEventListener<KeyUpEvent> listener,
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
	public StringInputFilterBuilder valueChangeMode(ValueChangeMode valueChangeMode) {
		inputBuilder.valueChangeMode(valueChangeMode);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#width(java.lang.String)
	 */
	@Override
	public StringInputFilterBuilder width(String width) {
		inputBuilder.width(width);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#height(java.lang.String)
	 */
	@Override
	public StringInputFilterBuilder height(String height) {
		inputBuilder.height(height);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minWidth(java.lang.String)
	 */
	@Override
	public StringInputFilterBuilder minWidth(String minWidth) {
		inputBuilder.minWidth(minWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxWidth(java.lang.String)
	 */
	@Override
	public StringInputFilterBuilder maxWidth(String maxWidth) {
		inputBuilder.maxWidth(maxWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minHeight(java.lang.String)
	 */
	@Override
	public StringInputFilterBuilder minHeight(String minHeight) {
		inputBuilder.minHeight(minHeight);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxHeight(java.lang.String)
	 */
	@Override
	public StringInputFilterBuilder maxHeight(String maxHeight) {
		inputBuilder.maxHeight(maxHeight);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleNames(java.lang.String[])
	 */
	@Override
	public StringInputFilterBuilder styleNames(String... styleNames) {
		inputBuilder.styleNames(styleNames);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleName(java.lang.String)
	 */
	@Override
	public StringInputFilterBuilder styleName(String styleName) {
		inputBuilder.styleName(styleName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasAutofocusConfigurator#autofocus(boolean)
	 */
	@Override
	public StringInputFilterBuilder autofocus(boolean autofocus) {
		inputBuilder.autofocus(autofocus);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#tabIndex(int)
	 */
	@Override
	public StringInputFilterBuilder tabIndex(int tabIndex) {
		inputBuilder.tabIndex(tabIndex);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#withFocusListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public StringInputFilterBuilder withFocusListener(ComponentEventListener<FocusEvent<Component>> listener) {
		inputBuilder.withFocusListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#withBlurListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public StringInputFilterBuilder withBlurListener(ComponentEventListener<BlurEvent<Component>> listener) {
		inputBuilder.withBlurListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#withFocusShortcut(com.vaadin.flow.
	 * component.Key)
	 */
	@Override
	public ShortcutConfigurator<StringInputFilterBuilder> withFocusShortcut(Key key) {
		return new DelegatedShortcutConfigurator<>(inputBuilder.withFocusShortcut(key), this);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.HasPrefixAndSuffixConfigurator#prefixComponent(com.vaadin.flow.
	 * component.Component)
	 */
	@Override
	public StringInputFilterBuilder prefixComponent(Component component) {
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
	public StringInputFilterBuilder suffixComponent(Component component) {
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
	public StringInputFilterBuilder withCompositionStartListener(
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
	public StringInputFilterBuilder withCompositionUpdateListener(
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
	public StringInputFilterBuilder withCompositionEndListener(ComponentEventListener<CompositionEndEvent> listener) {
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
	public StringInputFilterBuilder placeholder(Localizable placeholder) {
		inputBuilder.placeholder(placeholder);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasLabelConfigurator#label(com.holonplatform.core.i18n.
	 * Localizable)
	 */
	@Override
	public StringInputFilterBuilder label(Localizable label) {
		inputBuilder.label(label);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasTitleConfigurator#title(com.holonplatform.core.i18n.
	 * Localizable)
	 */
	@Override
	public StringInputFilterBuilder title(Localizable title) {
		inputBuilder.title(title);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasPatternConfigurator#pattern(java.lang.String)
	 */
	@Override
	public StringInputFilterBuilder pattern(String pattern) {
		inputBuilder.pattern(pattern);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasPatternConfigurator#preventInvalidInput(boolean)
	 */
	@Override
	public StringInputFilterBuilder preventInvalidInput(boolean preventInvalidInput) {
		inputBuilder.preventInvalidInput(preventInvalidInput);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.HasThemeVariantConfigurator#withThemeVariants(java.lang.Enum[])
	 */
	@Override
	public StringInputFilterBuilder withThemeVariants(TextFieldVariant... variants) {
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
	public StringInputFilterBuilder withDeferredLocalization(boolean deferredLocalization) {
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

}