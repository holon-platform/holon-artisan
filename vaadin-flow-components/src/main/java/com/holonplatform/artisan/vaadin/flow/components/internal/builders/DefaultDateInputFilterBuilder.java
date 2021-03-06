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

import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.artisan.vaadin.flow.components.builders.DateInputFilterBuilder;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.builders.DateInputBuilder;
import com.holonplatform.vaadin.flow.components.builders.ShortcutConfigurator;
import com.holonplatform.vaadin.flow.components.events.ReadonlyChangeListener;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.Key;

/**
 * Default {@link DateInputFilterBuilder} implementation.
 *
 * @since 1.0.0
 */
public class DefaultDateInputFilterBuilder extends AbstractOperatorInputFilterBuilder<Date, DateInputFilterBuilder>
		implements DateInputFilterBuilder {

	private final DateInputBuilder inputBuilder;
	private final DateInputBuilder additionalInputBuilder;

	public DefaultDateInputFilterBuilder(Property<? super Date> property) {
		super(property, InputFilterOperator.EQUAL, InputFilterOperator.NOT_EQUAL, InputFilterOperator.GREATER_THAN,
				InputFilterOperator.GREATER_OR_EQUAL, InputFilterOperator.LESS_THAN, InputFilterOperator.LESS_OR_EQUAL,
				InputFilterOperator.EMPTY, InputFilterOperator.NOT_EMPTY, InputFilterOperator.BETWEEN);
		this.inputBuilder = DateInputBuilder.create();
		this.additionalInputBuilder = DateInputBuilder.create();
		label(property);
	}

	@Override
	public DateInputFilterBuilder withValueChangeListener(ValueChangeListener<Date, ValueChangeEvent<Date>> listener) {
		inputBuilder.withValueChangeListener(listener);
		return this;
	}

	@Override
	public DateInputFilterBuilder timeZone(ZoneId zone) {
		inputBuilder.timeZone(zone);
		additionalInputBuilder.timeZone(zone);
		return this;
	}

	@Override
	public DateInputFilterBuilder locale(Locale locale) {
		inputBuilder.locale(locale);
		additionalInputBuilder.locale(locale);
		return this;
	}

	@Override
	public DateInputFilterBuilder updateLocaleOnAttach(boolean updateLocaleOnAttach) {
		inputBuilder.updateLocaleOnAttach(updateLocaleOnAttach);
		additionalInputBuilder.updateLocaleOnAttach(updateLocaleOnAttach);
		return this;
	}

	@Override
	public DateInputFilterBuilder min(Date min) {
		inputBuilder.min(min);
		additionalInputBuilder.min(min);
		return this;
	}

	@Override
	public DateInputFilterBuilder max(Date max) {
		inputBuilder.max(max);
		additionalInputBuilder.max(max);
		return this;
	}

	@Override
	public DateInputFilterBuilder autoOpen(boolean autoOpen) {
		inputBuilder.autoOpen(autoOpen);
		additionalInputBuilder.autoOpen(autoOpen);
		return this;
	}

	@Override
	public DateInputFilterBuilder initialPosition(Date initialPosition) {
		inputBuilder.initialPosition(initialPosition);
		additionalInputBuilder.initialPosition(initialPosition);
		return this;
	}

	@Override
	public DateInputFilterBuilder weekNumbersVisible(boolean weekNumbersVisible) {
		inputBuilder.weekNumbersVisible(weekNumbersVisible);
		additionalInputBuilder.weekNumbersVisible(weekNumbersVisible);
		return this;
	}

	@Override
	public DateInputFilterBuilder localization(CalendarLocalization localization) {
		inputBuilder.localization(localization);
		additionalInputBuilder.localization(localization);
		return this;
	}

	@Override
	public CalendarLocalizationBuilder<Date, DateInputFilterBuilder> localization() {
		return new DelegatedCalendarLocalizationBuilder<>(inputBuilder.localization(),
				additionalInputBuilder.localization(), this);
	}

	@Override
	public DateInputFilterBuilder withValue(Date value) {
		inputBuilder.withValue(value);
		return this;
	}

	@Override
	public DateInputFilterBuilder clearButtonVisible(boolean clearButtonVisible) {
		inputBuilder.clearButtonVisible(clearButtonVisible);
		return this;
	}

	@Override
	public DateInputFilterBuilder tabIndex(int tabIndex) {
		inputBuilder.tabIndex(tabIndex);
		return this;
	}

	@Override
	public DateInputFilterBuilder withFocusListener(ComponentEventListener<FocusEvent<Component>> listener) {
		inputBuilder.withFocusListener(listener);
		return this;
	}

	@Override
	public DateInputFilterBuilder withBlurListener(ComponentEventListener<BlurEvent<Component>> listener) {
		inputBuilder.withBlurListener(listener);
		return this;
	}

	@Override
	public DateInputFilterBuilder withReadonlyChangeListener(ReadonlyChangeListener listener) {
		inputBuilder.withReadonlyChangeListener(listener);
		return this;
	}

	@Override
	public ShortcutConfigurator<DateInputFilterBuilder> withFocusShortcut(Key key) {
		return ShortcutConfigurator.delegated(inputBuilder.withFocusShortcut(key), this);
	}

	@Override
	public DateInputFilterBuilder placeholder(Localizable placeholder) {
		inputBuilder.placeholder(placeholder);
		additionalInputBuilder.placeholder(placeholder);
		return this;
	}

	@Override
	public <A> DateInputFilterBuilder withAdapter(Class<A> type, Function<Input<Date>, A> adapter) {
		inputBuilder.withAdapter(type, adapter);
		return this;
	}

	@Override
	protected Input<Date> buildInput() {
		return inputBuilder.build();
	}

	@Override
	protected Optional<Input<Date>> buildAdditionalInput() {
		return Optional.of(additionalInputBuilder.build());
	}

	@Override
	protected DateInputFilterBuilder getConfigurator() {
		return this;
	}

}
