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

import java.util.Map;
import java.util.function.Consumer;

import com.holonplatform.artisan.core.exceptions.InterruptedOperationException;
import com.holonplatform.artisan.core.exceptions.OperationExecutionException;
import com.holonplatform.artisan.vaadin.flow.components.OperationProgressDialog;
import com.holonplatform.vaadin.flow.components.builders.ButtonConfigurator.BaseButtonConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasTextConfigurator;

/**
 * {@link OperationProgressDialog} configurator.
 * 
 * @param <C> Concrete configurator type
 * @param <T> Operation result type
 *
 * @since 1.0.0
 */
public interface OperationProgressDialogConfigurator<T, C extends OperationProgressDialogConfigurator<T, C>>
		extends HasSizeConfigurator<C>, HasStyleConfigurator<C>, HasTextConfigurator<C> {

	/**
	 * Sets the id of the root element of the dialog.
	 * @param id the id to set, or <code>null</code> to remove any previously set id
	 * @return this
	 */
	C id(String id);

	/**
	 * Adds a theme name.
	 * @param themeName the theme name to add, not <code>null</code>
	 * @return this
	 */
	C withThemeName(String themeName);

	/**
	 * Set whether the operation is abortable, showing the abort button accordingly.
	 * @param abortable Whether the operation is abortable
	 * @return this
	 */
	C abortable(boolean abortable);

	/**
	 * Set the {@link BaseButtonConfigurator} consumer to use to configure the abort dialog button.
	 * @param abortButtonConfigurator the abort button configurator to set
	 * @return this
	 */
	C abortButtonConfigurator(Consumer<BaseButtonConfigurator> abortButtonConfigurator);

	/**
	 * Set the {@link OperationExecutionException} exception handler.
	 * @param operationExecutionExceptionHandler The {@link OperationExecutionException} handler to set (not null)
	 * @return this
	 */
	C operationExecutionExceptionHandler(Consumer<OperationExecutionException> operationExecutionExceptionHandler);

	/**
	 * Set the {@link InterruptedOperationException} exception handler.
	 * @param interruptedOperationExceptionHandler The {@link InterruptedOperationException} handler to set (not null)
	 * @return this
	 */
	C interruptedOperationExceptionHandler(
			Consumer<InterruptedOperationException> interruptedOperationExceptionHandler);

	/**
	 * Add a context resources configurator.
	 * @param contextResourcesConfigurator the context resources configurator to add
	 * @return this
	 */
	C withContextResourcesConfigurator(Consumer<Map<String, Object>> contextResourcesConfigurator);

}
