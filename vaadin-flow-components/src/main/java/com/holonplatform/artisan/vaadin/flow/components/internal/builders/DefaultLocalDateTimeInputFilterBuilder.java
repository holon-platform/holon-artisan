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
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.function.Function;

import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.artisan.vaadin.flow.components.builders.LocalDateTimeInputFilterBuilder;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.builders.LocalDateTimeInputBuilder;
import com.holonplatform.vaadin.flow.components.builders.ShortcutConfigurator;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.Key;

/**
 * Default {@link LocalDateTimeInputFilterBuilder} implementation.
 *
 * @since 1.0.0
 */
public class DefaultLocalDateTimeInputFilterBuilder
		extends AbstractOperatorInputFilterBuilder<LocalDateTime, LocalDateTimeInputFilterBuilder>
		implements LocalDateTimeInputFilterBuilder {

	private final LocalDateTimeInputBuilder inputBuilder;

	public DefaultLocalDateTimeInputFilterBuilder(Property<? super LocalDateTime> property) {
		super(property, InputFilterOperator.EQUAL, InputFilterOperator.NOT_EQUAL, InputFilterOperator.GREATER_THAN,
				InputFilterOperator.GREATER_OR_EQUAL, InputFilterOperator.LESS_THAN, InputFilterOperator.LESS_OR_EQUAL,
				InputFilterOperator.EMPTY, InputFilterOperator.NOT_EMPTY);
		this.inputBuilder = LocalDateTimeInputBuilder.create();
		label(property);
	}

	@Override
	public LocalDateTimeInputFilterBuilder withValueChangeListener(
			ValueChangeListener<LocalDateTime, ValueChangeEvent<LocalDateTime>> listener) {
		inputBuilder.withValueChangeListener(listener);
		return this;
	}

	@Override
	public LocalDateTimeInputFilterBuilder locale(Locale locale) {
		inputBuilder.locale(locale);
		return this;
	}

	@Override
	public LocalDateTimeInputFilterBuilder updateLocaleOnAttach(boolean updateLocaleOnAttach) {
		inputBuilder.updateLocaleOnAttach(updateLocaleOnAttach);
		return this;
	}

	@Override
	public LocalDateTimeInputFilterBuilder min(LocalDateTime min) {
		inputBuilder.min(min);
		return this;
	}

	@Override
	public LocalDateTimeInputFilterBuilder max(LocalDateTime max) {
		inputBuilder.max(max);
		return this;
	}

	@Override
	public LocalDateTimeInputFilterBuilder initialPosition(LocalDateTime initialPosition) {
		inputBuilder.initialPosition(initialPosition);
		return this;
	}

	@Override
	public LocalDateTimeInputFilterBuilder weekNumbersVisible(boolean weekNumbersVisible) {
		inputBuilder.weekNumbersVisible(weekNumbersVisible);
		return this;
	}

	@Override
	public LocalDateTimeInputFilterBuilder localization(CalendarLocalization localization) {
		inputBuilder.localization(localization);
		return this;
	}

	@Override
	public CalendarLocalizationBuilder<LocalDateTime, LocalDateTimeInputFilterBuilder> localization() {
		return new DelegatedCalendarLocalizationBuilder<>(inputBuilder.localization(), this);
	}

	@Override
	public LocalDateTimeInputFilterBuilder withValue(LocalDateTime value) {
		inputBuilder.withValue(value);
		return this;
	}

	@Override
	public LocalDateTimeInputFilterBuilder tabIndex(int tabIndex) {
		inputBuilder.tabIndex(tabIndex);
		return this;
	}

	@Override
	public LocalDateTimeInputFilterBuilder withFocusListener(ComponentEventListener<FocusEvent<Component>> listener) {
		inputBuilder.withFocusListener(listener);
		return this;
	}

	@Override
	public LocalDateTimeInputFilterBuilder withBlurListener(ComponentEventListener<BlurEvent<Component>> listener) {
		inputBuilder.withBlurListener(listener);
		return this;
	}

	@Override
	public ShortcutConfigurator<LocalDateTimeInputFilterBuilder> withFocusShortcut(Key key) {
		return ShortcutConfigurator.delegated(inputBuilder.withFocusShortcut(key), this);
	}

	@Override
	public LocalDateTimeInputFilterBuilder placeholder(Localizable placeholder) {
		inputBuilder.placeholder(placeholder);
		return this;
	}

	@Override
	public LocalDateTimeInputFilterBuilder spacing(boolean spacing) {
		inputBuilder.spacing(spacing);
		return this;
	}

	@Override
	public LocalDateTimeInputFilterBuilder timeInputWidth(String timeInputWidth) {
		inputBuilder.timeInputWidth(timeInputWidth);
		return this;
	}

	@Override
	public LocalDateTimeInputFilterBuilder timeStep(Duration step) {
		inputBuilder.timeStep(step);
		return this;
	}

	@Override
	public <A> LocalDateTimeInputFilterBuilder withAdapter(Class<A> type, Function<Input<LocalDateTime>, A> adapter) {
		inputBuilder.withAdapter(type, adapter);
		return this;
	}

	@Override
	protected Input<LocalDateTime> buildInput() {
		return inputBuilder.build();
	}

	@Override
	protected LocalDateTimeInputFilterBuilder getConfigurator() {
		return this;
	}

}
