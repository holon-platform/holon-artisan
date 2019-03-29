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

import java.util.List;

import com.holonplatform.artisan.vaadin.flow.components.utils.Obj;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.vaadin.flow.components.builders.BaseTemporalInputConfigurator;
import com.holonplatform.vaadin.flow.components.builders.BaseTemporalInputConfigurator.CalendarLocalizationBuilder;

/**
 * Delegated {@link CalendarLocalizationBuilder}.
 * 
 * @param <D> Temporal data type
 * @param <C> Configurator type
 *
 */
public class DelegatedCalendarLocalizationBuilder<D, C extends BaseTemporalInputConfigurator<D, C>>
		implements CalendarLocalizationBuilder<D, C> {

	private final CalendarLocalizationBuilder<D, ?> delegate;
	private final C parent;

	public DelegatedCalendarLocalizationBuilder(CalendarLocalizationBuilder<D, ?> delegate, C parent) {
		super();
		Obj.argumentNotNull(delegate, "Delegate configurator must be not null");
		Obj.argumentNotNull(parent, "Parent configurator must be not null");
		this.delegate = delegate;
		this.parent = parent;
	}

	@Override
	public CalendarLocalizationBuilder<D, C> monthNames(List<Localizable> monthNames) {
		delegate.monthNames(monthNames);
		return this;
	}

	@Override
	public CalendarLocalizationBuilder<D, C> weekDays(List<Localizable> weekDays) {
		delegate.weekDays(weekDays);
		return this;
	}

	@Override
	public CalendarLocalizationBuilder<D, C> weekDaysShort(List<Localizable> weekDaysShort) {
		delegate.weekDaysShort(weekDaysShort);
		return this;
	}

	@Override
	public CalendarLocalizationBuilder<D, C> firstDayOfWeek(int firstDayOfWeek) {
		delegate.firstDayOfWeek(firstDayOfWeek);
		return this;
	}

	@Override
	public CalendarLocalizationBuilder<D, C> week(Localizable message) {
		delegate.week(message);
		return this;
	}

	@Override
	public CalendarLocalizationBuilder<D, C> calendar(Localizable message) {
		delegate.calendar(message);
		return this;
	}

	@Override
	public CalendarLocalizationBuilder<D, C> clear(Localizable message) {
		delegate.clear(message);
		return this;
	}

	@Override
	public CalendarLocalizationBuilder<D, C> today(Localizable message) {
		delegate.today(message);
		return this;
	}

	@Override
	public CalendarLocalizationBuilder<D, C> cancel(Localizable message) {
		delegate.cancel(message);
		return this;
	}

	@Override
	public C set() {
		return parent;
	}

}
