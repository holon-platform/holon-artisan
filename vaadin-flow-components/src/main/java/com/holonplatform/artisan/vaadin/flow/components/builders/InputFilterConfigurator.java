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
package com.holonplatform.artisan.vaadin.flow.components.builders;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultInputFilterConfigurator;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasEnabledConfigurator;
import com.holonplatform.vaadin.flow.components.events.ReadonlyChangeListener;

/**
 * {@link InputFilter} configurator.
 *
 * @param <T> Value type
 * 
 * @since 1.1.2
 */
public interface InputFilterConfigurator<T>
		extends ComponentConfigurator<InputFilterConfigurator<T>>, HasEnabledConfigurator<InputFilterConfigurator<T>> {

	/**
	 * Set whether the input component is read-only.
	 * <p>
	 * When the input component is read-only, the user can not change the value of the input.
	 * </p>
	 * @param readOnly Whether to set the input as read-only
	 * @return this
	 */
	InputFilterConfigurator<T> readOnly(boolean readOnly);

	/**
	 * Set the input component as read-only. The user can not change the value of the input.
	 * @return this
	 */
	default InputFilterConfigurator<T> readOnly() {
		return readOnly(true);
	}

	/**
	 * Add a {@link ReadonlyChangeListener} to be notified when the input read-only state changes.
	 * @param listener The {@link ReadonlyChangeListener} to add (not null)
	 * @return this
	 */
	InputFilterConfigurator<T> withReadonlyChangeListener(ReadonlyChangeListener listener);

	/**
	 * Add a {@link ValueChangeListener} to be notified when the input value changes.
	 * @param listener The {@link ValueChangeListener} to add (not null)
	 * @return this
	 */
	InputFilterConfigurator<T> withValueChangeListener(ValueChangeListener<T, ValueChangeEvent<T>> listener);

	/**
	 * Create a new {@link InputFilterConfigurator}.
	 * @param <T> Input value type
	 * @param inputFilter The {@link InputFilter} to configure (not null)
	 * @return A new {@link InputFilterConfigurator} instance
	 */
	static <T> InputFilterConfigurator<T> create(InputFilter<T> inputFilter) {
		return new DefaultInputFilterConfigurator<>(inputFilter);
	}

}
