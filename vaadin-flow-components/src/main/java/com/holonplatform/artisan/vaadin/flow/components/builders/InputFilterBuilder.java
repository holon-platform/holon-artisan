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

import java.io.Serializable;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;

/**
 * {@link InputFilter} builder.
 * 
 * @param <T> Value type
 *
 * @since 1.0.0
 */
public interface InputFilterBuilder<T> extends Serializable {

	/**
	 * Set the callback to invoke when {@link InputFilter#reset()} is performed.
	 * @param resetCallback the reset callback to set
	 * @return this
	 */
	InputFilterBuilder<T> resetCallback(Runnable resetCallback);

	/**
	 * Build the {@link InputFilter} instance.
	 * @return the {@link InputFilter} instance
	 */
	InputFilter<T> build();

}
