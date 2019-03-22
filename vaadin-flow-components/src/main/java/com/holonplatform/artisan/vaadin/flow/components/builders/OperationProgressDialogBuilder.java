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

import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;

import com.holonplatform.artisan.vaadin.flow.components.OperationProgressDialog;

/**
 * Base {@link OperationProgressDialog} builder.
 * 
 * @param <T> Operation result type
 *
 * @since 1.0.0
 */
public interface OperationProgressDialogBuilder<T>
		extends OperationProgressDialogConfigurator<T, OperationProgressDialogBuilder<T>> {

	/**
	 * Build the operation progress dialog.
	 * @return The operation progress dialog
	 */
	OperationProgressDialog<T> build();

	/**
	 * Build the dialog, execute the operation and return the result. The dialog is opened just before the operation
	 * execution starts and closed when the operation execution ends.
	 * @return A {@link CompletionStage} to handle the operation result
	 */
	default CompletionStage<T> execute() {
		return build().execute();
	}

	/**
	 * Build the dialog, execute the operation and then execute the given Consumer, providing the operation result.
	 * @param thenExecute The Consumer to invoke with the operation result at the end of the operation execution (not
	 *        null)
	 */
	default void execute(Consumer<T> thenExecute) {
		build().execute(thenExecute);
	}

}
