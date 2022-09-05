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

import java.util.Optional;
import java.util.function.Function;

import com.holonplatform.artisan.core.utils.Obj;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.artisan.vaadin.flow.components.builders.OperatorInputFilterAdapterBuilder;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.events.ReadonlyChangeListener;

/**
 * Default {@link OperatorInputFilterAdapterBuilder} implementation.
 * @param <T> Value type
 * @since 1.0.0
 */
public class DefaultOperatorInputFilterAdapterBuilder<T>
		extends AbstractOperatorInputFilterBuilder<T, OperatorInputFilterAdapterBuilder<T>>
		implements OperatorInputFilterAdapterBuilder<T> {

	private final Input<T> input;
	private final Optional<Input<T>> additionalInput;

	public DefaultOperatorInputFilterAdapterBuilder(Input<T> input, Optional<Input<T>> additionalInput,
			Property<? super T> property, InputFilterOperator... operators) {
		super(property, operators);
		Obj.argumentNotNull(input, "Input must be not null");
		Obj.argumentNotNull(additionalInput, "Additional Input must be not null");
		this.input = input;
		this.additionalInput = additionalInput;
	}

	public DefaultOperatorInputFilterAdapterBuilder(Input<T> input, Property<? super T> property,
			InputFilterOperator... operators) {
		super(property, operators);
		Obj.argumentNotNull(input, "Input must be not null");
		this.input = input;
		this.additionalInput = Optional.empty();
	}

	@Override
	protected Input<T> buildInput() {
		return input;
	}

	@Override
	protected Optional<Input<T>> buildAdditionalInput() {
		return additionalInput;
	}

	@Override
	protected OperatorInputFilterAdapterBuilder<T> getConfigurator() {
		return this;
	}

	@Override
	public OperatorInputFilterAdapterBuilder<T> withValueChangeListener(
			ValueChangeListener<T, ValueChangeEvent<T>> listener) {
		input.addValueChangeListener(listener);
		return this;
	}

	@Override
	public OperatorInputFilterAdapterBuilder<T> withReadonlyChangeListener(ReadonlyChangeListener listener) {
		input.addReadonlyChangeListener(listener);
		return this;
	}

	@Override
	public <A> OperatorInputFilterAdapterBuilder<T> withAdapter(Class<A> type, Function<Input<T>, A> adapter) {
		getComponent().setAdapter(type, adapter);
		return this;
	}

}
