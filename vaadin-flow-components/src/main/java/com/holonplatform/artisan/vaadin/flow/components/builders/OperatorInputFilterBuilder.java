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
package com.holonplatform.artisan.vaadin.flow.components.builders;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;

/**
 * {@link InputFilter} with operator selection support base builder.
 *
 * @param <T> Value type
 * @param <B> Concrete builder type
 * 
 * @since 1.0.0
 */
public interface OperatorInputFilterBuilder<T, B extends OperatorInputFilterBuilder<T, B>> extends
		OperatorInputFilterConfigurator<T, ValueChangeEvent<T>, B>, InputFilterBuilder<T, ValueChangeEvent<T>, B> {

}
