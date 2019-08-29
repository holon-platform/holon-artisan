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

import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.artisan.vaadin.flow.components.builders.LocalDateInputFilterBuilder;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.builders.LocalDateInputBuilder;
import com.holonplatform.vaadin.flow.components.builders.ShortcutConfigurator;
import com.holonplatform.vaadin.flow.components.events.ReadonlyChangeListener;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.Key;

/**
 * Default {@link LocalDateInputFilterBuilder} implementation.
 *
 * @since 1.0.0
 */
public class DefaultLocalDateInputFilterBuilder
		extends AbstractOperatorInputFilterBuilder<LocalDate, LocalDateInputFilterBuilder>
		implements LocalDateInputFilterBuilder {

	private final LocalDateInputBuilder inputBuilder;
	private final LocalDateInputBuilder additionalInputBuilder;

	public DefaultLocalDateInputFilterBuilder(Property<? super LocalDate> property) {
		super(property, InputFilterOperator.EQUAL, InputFilterOperator.NOT_EQUAL, InputFilterOperator.GREATER_THAN,
				InputFilterOperator.GREATER_OR_EQUAL, InputFilterOperator.LESS_THAN, InputFilterOperator.LESS_OR_EQUAL,
				InputFilterOperator.EMPTY, InputFilterOperator.NOT_EMPTY, InputFilterOperator.BETWEEN);
		this.inputBuilder = LocalDateInputBuilder.create();
		this.additionalInputBuilder = LocalDateInputBuilder.create();
		label(property);
	}

	@Override
	public LocalDateInputFilterBuilder withValueChangeListener(
			ValueChangeListener<LocalDate, ValueChangeEvent<LocalDate>> listener) {
		inputBuilder.withValueChangeListener(listener);
		return this;
	}

	@Override
	public LocalDateInputFilterBuilder locale(Locale locale) {
		inputBuilder.locale(locale);
		additionalInputBuilder.locale(locale);
		return this;
	}

	@Override
	public LocalDateInputFilterBuilder updateLocaleOnAttach(boolean updateLocaleOnAttach) {
		inputBuilder.updateLocaleOnAttach(updateLocaleOnAttach);
		additionalInputBuilder.updateLocaleOnAttach(updateLocaleOnAttach);
		return this;
	}

	@Override
	public LocalDateInputFilterBuilder min(LocalDate min) {
		inputBuilder.min(min);
		additionalInputBuilder.min(min);
		return this;
	}

	@Override
	public LocalDateInputFilterBuilder max(LocalDate max) {
		inputBuilder.max(max);
		additionalInputBuilder.max(max);
		return this;
	}

	@Override
	public LocalDateInputFilterBuilder initialPosition(LocalDate initialPosition) {
		inputBuilder.initialPosition(initialPosition);
		additionalInputBuilder.initialPosition(initialPosition);
		return this;
	}

	@Override
	public LocalDateInputFilterBuilder weekNumbersVisible(boolean weekNumbersVisible) {
		inputBuilder.weekNumbersVisible(weekNumbersVisible);
		additionalInputBuilder.weekNumbersVisible(weekNumbersVisible);
		return this;
	}

	@Override
	public LocalDateInputFilterBuilder localization(CalendarLocalization localization) {
		inputBuilder.localization(localization);
		additionalInputBuilder.localization(localization);
		return this;
	}

	@Override
	public CalendarLocalizationBuilder<LocalDate, LocalDateInputFilterBuilder> localization() {
		return new DelegatedCalendarLocalizationBuilder<>(inputBuilder.localization(),
				additionalInputBuilder.localization(), this);
	}

	@Override
	public LocalDateInputFilterBuilder withValue(LocalDate value) {
		inputBuilder.withValue(value);
		return this;
	}

	@Override
	public LocalDateInputFilterBuilder clearButtonVisible(boolean clearButtonVisible) {
		inputBuilder.clearButtonVisible(clearButtonVisible);
		return this;
	}

	@Override
	public LocalDateInputFilterBuilder tabIndex(int tabIndex) {
		inputBuilder.tabIndex(tabIndex);
		return this;
	}

	@Override
	public LocalDateInputFilterBuilder withFocusListener(ComponentEventListener<FocusEvent<Component>> listener) {
		inputBuilder.withFocusListener(listener);
		return this;
	}

	@Override
	public LocalDateInputFilterBuilder withBlurListener(ComponentEventListener<BlurEvent<Component>> listener) {
		inputBuilder.withBlurListener(listener);
		return this;
	}

	@Override
	public LocalDateInputFilterBuilder withReadonlyChangeListener(ReadonlyChangeListener listener) {
		inputBuilder.withReadonlyChangeListener(listener);
		return this;
	}

	@Override
	public ShortcutConfigurator<LocalDateInputFilterBuilder> withFocusShortcut(Key key) {
		return ShortcutConfigurator.delegated(inputBuilder.withFocusShortcut(key), this);
	}

	@Override
	public LocalDateInputFilterBuilder placeholder(Localizable placeholder) {
		inputBuilder.placeholder(placeholder);
		additionalInputBuilder.placeholder(placeholder);
		return this;
	}

	@Override
	public <A> LocalDateInputFilterBuilder withAdapter(Class<A> type, Function<Input<LocalDate>, A> adapter) {
		inputBuilder.withAdapter(type, adapter);
		return this;
	}

	@Override
	protected Input<LocalDate> buildInput() {
		return inputBuilder.build();
	}

	@Override
	protected Optional<Input<LocalDate>> buildAdditionalInput() {
		return Optional.of(additionalInputBuilder.build());
	}

	@Override
	protected LocalDateInputFilterBuilder getConfigurator() {
		return this;
	}

}
