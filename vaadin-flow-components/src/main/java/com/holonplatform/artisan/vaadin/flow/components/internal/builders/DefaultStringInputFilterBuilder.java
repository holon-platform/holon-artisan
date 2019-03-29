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

import java.util.function.Function;
import java.util.function.Supplier;

import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.artisan.vaadin.flow.components.builders.StringInputFilterBuilder;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.builders.ShortcutConfigurator;
import com.holonplatform.vaadin.flow.components.builders.StringInputBuilder;
import com.holonplatform.vaadin.flow.i18n.LocalizationProvider;
import com.holonplatform.vaadin.flow.internal.components.builders.DelegatedShortcutConfigurator;
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
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.textfield.Autocapitalize;
import com.vaadin.flow.component.textfield.Autocomplete;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;

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
		this.inputBuilder = StringInputBuilder.create().emptyValuesAsNull(true).blankValuesAsNull(true)
				.clearButtonVisible(true);
		label(property);
	}

	@Override
	protected StringInputFilterBuilder getConfigurator() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.internal.builders.AbstractOperatorInputFilterBuilder#buildInput(
	 * com.holonplatform.artisan.vaadin.flow.components.internal.FilterOperatorSelect)
	 */
	@Override
	protected Input<String> buildInput() {
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

	@Override
	public <A> StringInputFilterBuilder withAdapter(Class<A> type, Function<Input<String>, A> adapter) {
		inputBuilder.withAdapter(type, adapter);
		return this;
	}

}
