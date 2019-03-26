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

import java.time.Duration;
import java.time.LocalTime;
import java.util.Locale;

import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.artisan.vaadin.flow.components.builders.LocalTimeInputFilterBuilder;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.builders.LocalTimeInputBuilder;
import com.holonplatform.vaadin.flow.components.builders.ShortcutConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.DelegatedShortcutConfigurator;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.Key;

/**
 * Default {@link LocalTimeInputFilterBuilder} implementation.
 *
 * @since 1.0.0
 */
public class DefaultLocalTimeInputFilterBuilder
		extends AbstractOperatorInputFilterBuilder<LocalTime, LocalTimeInputFilterBuilder>
		implements LocalTimeInputFilterBuilder {

	private final LocalTimeInputBuilder inputBuilder;

	public DefaultLocalTimeInputFilterBuilder(Property<? super LocalTime> property) {
		super(property, InputFilterOperator.EQUAL, InputFilterOperator.NOT_EQUAL, InputFilterOperator.GREATER_THAN,
				InputFilterOperator.GREATER_OR_EQUAL, InputFilterOperator.LESS_THAN, InputFilterOperator.LESS_OR_EQUAL,
				InputFilterOperator.EMPTY, InputFilterOperator.NOT_EMPTY);
		this.inputBuilder = LocalTimeInputBuilder.create();
		label(property);
	}

	@Override
	public LocalTimeInputFilterBuilder withValueChangeListener(
			ValueChangeListener<LocalTime, ValueChangeEvent<LocalTime>> listener) {
		inputBuilder.withValueChangeListener(listener);
		return this;
	}

	@Override
	public LocalTimeInputFilterBuilder locale(Locale locale) {
		inputBuilder.locale(locale);
		return this;
	}

	@Override
	public LocalTimeInputFilterBuilder step(Duration step) {
		inputBuilder.step(step);
		return this;
	}

	@Override
	public LocalTimeInputFilterBuilder withValue(LocalTime value) {
		inputBuilder.withValue(value);
		return this;
	}

	@Override
	public LocalTimeInputFilterBuilder tabIndex(int tabIndex) {
		inputBuilder.tabIndex(tabIndex);
		return this;
	}

	@Override
	public LocalTimeInputFilterBuilder withFocusListener(ComponentEventListener<FocusEvent<Component>> listener) {
		inputBuilder.withFocusListener(listener);
		return this;
	}

	@Override
	public LocalTimeInputFilterBuilder withBlurListener(ComponentEventListener<BlurEvent<Component>> listener) {
		inputBuilder.withBlurListener(listener);
		return this;
	}

	@Override
	public ShortcutConfigurator<LocalTimeInputFilterBuilder> withFocusShortcut(Key key) {
		return new DelegatedShortcutConfigurator<>(inputBuilder.withFocusShortcut(key), this);
	}

	@Override
	public LocalTimeInputFilterBuilder placeholder(Localizable placeholder) {
		inputBuilder.placeholder(placeholder);
		return this;
	}

	@Override
	public LocalTimeInputFilterBuilder title(Localizable title) {
		inputBuilder.title(title);
		return this;
	}

	@Override
	protected Input<LocalTime> buildInput() {
		return inputBuilder.build();
	}

	@Override
	protected LocalTimeInputFilterBuilder getConfigurator() {
		return this;
	}

}
