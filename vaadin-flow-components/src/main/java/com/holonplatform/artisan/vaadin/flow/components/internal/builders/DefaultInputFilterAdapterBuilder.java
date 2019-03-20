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

import java.util.function.Function;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.builders.InputFilterAdapterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.InputFilterAdapter;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.vaadin.flow.components.Input;

/**
 * Default {@link InputFilterAdapterBuilder} implementation.
 *
 * @param <T> Value type
 *
 * @since 1.0.0
 */
public class DefaultInputFilterAdapterBuilder<T> implements InputFilterAdapterBuilder<T> {

	private static final long serialVersionUID = -4280481499103713048L;

	private final InputFilterAdapter<T> inputFilter;

	/**
	 * Constructor.
	 * @param input The {@link Input} to adapt (not null)
	 * @param filterProvider The function to provide a {@link QueryFilter} according to the Input value, or
	 *        <code>null</code> if none (not null)
	 */
	public DefaultInputFilterAdapterBuilder(Input<T> input, Function<T, QueryFilter> filterProvider) {
		super();
		this.inputFilter = new InputFilterAdapter<>(input, filterProvider);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.builders.InputFilterBuilder#resetCallback(java.lang.Runnable)
	 */
	@Override
	public InputFilterAdapterBuilder<T> resetCallback(Runnable resetCallback) {
		inputFilter.setResetCallback(resetCallback);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.InputFilterBuilder#build()
	 */
	@Override
	public InputFilter<T> build() {
		return inputFilter;
	}

}
