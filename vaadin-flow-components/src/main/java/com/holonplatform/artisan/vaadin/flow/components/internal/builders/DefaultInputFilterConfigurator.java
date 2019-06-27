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

import com.holonplatform.artisan.core.utils.Obj;
import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.builders.InputFilterConfigurator;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator;
import com.holonplatform.vaadin.flow.components.events.ReadonlyChangeListener;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.Element;

/**
 * Default {@link InputFilterConfigurator} implementation.
 * 
 * @since 1.1.2
 */
public class DefaultInputFilterConfigurator<T> implements InputFilterConfigurator<T> {

	private final InputFilter<T> inputFilter;

	private final BaseComponentConfigurator componentConfigurator;

	public DefaultInputFilterConfigurator(InputFilter<T> inputFilter) {
		super();
		Obj.argumentNotNull(inputFilter, "InputFilter must be not null");
		this.inputFilter = inputFilter;
		this.componentConfigurator = ComponentConfigurator.create(inputFilter.getComponent());
	}

	@Override
	public InputFilterConfigurator<T> readOnly(boolean readOnly) {
		inputFilter.setReadOnly(readOnly);
		return this;
	}

	@Override
	public InputFilterConfigurator<T> withReadonlyChangeListener(ReadonlyChangeListener listener) {
		inputFilter.addReadonlyChangeListener(listener);
		return this;
	}

	@Override
	public InputFilterConfigurator<T> withValueChangeListener(ValueChangeListener<T, ValueChangeEvent<T>> listener) {
		inputFilter.addValueChangeListener(listener);
		return this;
	}

	@Override
	public InputFilterConfigurator<T> id(String id) {
		componentConfigurator.id(id);
		return this;
	}

	@Override
	public InputFilterConfigurator<T> visible(boolean visible) {
		componentConfigurator.visible(visible);
		return this;
	}

	@Override
	public InputFilterConfigurator<T> elementConfiguration(Consumer<Element> element) {
		componentConfigurator.elementConfiguration(element);
		return this;
	}

	@Override
	public InputFilterConfigurator<T> withAttachListener(ComponentEventListener<AttachEvent> listener) {
		componentConfigurator.withAttachListener(listener);
		return this;
	}

	@Override
	public InputFilterConfigurator<T> withDetachListener(ComponentEventListener<DetachEvent> listener) {
		componentConfigurator.withDetachListener(listener);
		return this;
	}

	@Override
	public InputFilterConfigurator<T> withThemeName(String themeName) {
		componentConfigurator.withThemeName(themeName);
		return this;
	}

	@Override
	public InputFilterConfigurator<T> withEventListener(String eventType, DomEventListener listener) {
		componentConfigurator.withEventListener(eventType, listener);
		return this;
	}

	@Override
	public InputFilterConfigurator<T> withEventListener(String eventType, DomEventListener listener, String filter) {
		componentConfigurator.withEventListener(eventType, listener, filter);
		return this;
	}

	@Override
	public InputFilterConfigurator<T> enabled(boolean enabled) {
		inputFilter.hasEnabled().ifPresent(he -> he.setEnabled(enabled));
		return this;
	}

}
