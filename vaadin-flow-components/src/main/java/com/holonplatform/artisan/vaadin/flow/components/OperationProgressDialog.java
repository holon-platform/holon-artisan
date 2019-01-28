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
package com.holonplatform.artisan.vaadin.flow.components;

import java.util.Optional;

import com.holonplatform.artisan.core.exceptions.InterruptedOperationException;
import com.holonplatform.artisan.core.exceptions.OperationExecutionException;
import com.holonplatform.artisan.core.operation.Operation;
import com.holonplatform.artisan.vaadin.flow.components.builders.BaseOperationProgressDialogBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultOperationProgressDialogBuilder;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;

/**
 * A dialog which can be used to execute an asynchronous operation and notify the operation progress.
 * 
 * @param <T> Operation result type
 * 
 * @since 1.0.0
 */
public interface OperationProgressDialog<T> extends HasStyle, HasSize {

	/**
	 * Execute the operation and return the result. The dialog is opened just before the operation execution starts and
	 * closed when the operation execution ends.
	 * @return The operation result
	 * @throws InterruptedOperationException If the operation was interrupted by the user
	 * @throws OperationExecutionException If an error occurred during operation execution
	 */
	T execute() throws InterruptedOperationException, OperationExecutionException;

	/**
	 * Execute the operation and return the result or an empty Optional if the operation was interrupted by the user.
	 * @return The operation result, or an empty Optional if the operation was interrupted by the user
	 * @throws OperationExecutionException If an error occurred during operation execution
	 */
	default Optional<T> executeOrInterrupt() throws OperationExecutionException {
		try {
			return Optional.ofNullable(execute());
		} catch (@SuppressWarnings("unused") InterruptedOperationException e) {
			return Optional.empty();
		}
	}

	/**
	 * Interrupts the operation execution forcibly, closing the dialog.
	 */
	void interrupt();

	// ------- builders

	/**
	 * Get a builder to create a {@link OperationProgressDialog} for given operation.
	 * @param <T> Operation result type
	 * @param operation The operation to execute (not null)
	 * @return The {@link OperationProgressDialog} builder
	 */
	static <T> OperationProgressDialogBuilder<T> builder(Operation<T> operation) {
		return new DefaultOperationProgressDialogBuilder<>(operation);
	}

	public interface OperationProgressDialogBuilder<T>
			extends BaseOperationProgressDialogBuilder<T, OperationProgressDialogBuilder<T>> {

	}

}
