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

import java.util.Optional;

import com.holonplatform.artisan.core.exceptions.InterruptedOperationException;
import com.holonplatform.artisan.core.exceptions.OperationExecutionException;
import com.holonplatform.artisan.vaadin.flow.components.OperationProgressDialog;

/**
 * Base {@link OperationProgressDialog} builder.
 * 
 * @param <B> Concrete builder type
 * @param <T> Operation result type
 *
 * @since 1.0.0
 */
public interface BaseOperationProgressDialogBuilder<T, B extends BaseOperationProgressDialogBuilder<T, B>>
		extends OperationProgressDialogConfigurator<T, B> {

	/**
	 * Build the operation progress dialog.
	 * @return The operation progress dialog
	 */
	OperationProgressDialog<T> build();

	/**
	 * Build the dialog, execute the operation and return the result. The dialog is opened just before the operation
	 * execution starts and closed when the operation execution ends.
	 * @return The operation result
	 * @throws InterruptedOperationException If the operation was interrupted by the user
	 * @throws OperationExecutionException If an error occurred during operation execution
	 */
	default T execute() throws InterruptedOperationException, OperationExecutionException {
		return build().execute();
	}

	/**
	 * Build the dialog, execute the operation and return the result or an empty Optional if the operation was
	 * interrupted by the user.
	 * @return The operation result, or an empty Optional if the operation was interrupted by the user
	 * @throws OperationExecutionException If an error occurred during operation execution
	 */
	default Optional<T> executeOrInterrupt() throws OperationExecutionException {
		return build().executeOrInterrupt();
	}

}
