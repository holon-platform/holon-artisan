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
package com.holonplatform.artisan.vaadin.flow.components;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;

import com.holonplatform.artisan.core.exceptions.InterruptedOperationException;
import com.holonplatform.artisan.core.exceptions.OperationExecutionException;
import com.holonplatform.artisan.core.operation.Operation;
import com.holonplatform.artisan.vaadin.flow.components.builders.OperationProgressDialogBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultOperationProgressDialogBuilder;
import com.holonplatform.core.internal.utils.ObjectUtils;
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
	 * @return A {@link CompletionStage} to handle the operation result
	 */
	CompletionStage<T> execute();

	/**
	 * Execute the operation and then execute the given Consumer, providing the operation result.
	 * @param thenExecute The Consumer to invoke with the operation result at the end of the operation execution (not
	 *        null)
	 */
	void execute(Consumer<T> thenExecute);

	/**
	 * Get a builder to create a {@link OperationProgressDialog} for given operation.
	 * @param <T> Operation result type
	 * @param operation The operation to execute (not null)
	 * @return A new {@link OperationProgressDialog} builder
	 */
	static <T> OperationProgressDialogBuilder<T> builder(Operation<T> operation) {
		return new DefaultOperationProgressDialogBuilder<>(operation);
	}

	/**
	 * Get a builder to create a {@link OperationProgressDialog} for given {@link Callable} operation, without progress
	 * steps notification.
	 * @param <T> Operation result type
	 * @param operation The operation to execute (not null)
	 * @return A new {@link OperationProgressDialog} builder
	 */
	static <T> OperationProgressDialogBuilder<T> builder(Callable<T> operation) {
		ObjectUtils.argumentNotNull(operation, "Operation must be not null");
		return builder(callback -> {
			try {
				callback.onProgress(0, 0);
				return operation.call();
			} catch (InterruptedOperationException ioe) {
				throw ioe;
			} catch (Exception e) {
				throw new OperationExecutionException(e);
			}
		});
	}

	/**
	 * Get a builder to create a {@link OperationProgressDialog} for given {@link Runnable} operation, without progress
	 * steps notification.
	 * <p>
	 * A {@link Void} type {@link OperationProgressDialog} is created and <code>null</code> is always returned as
	 * operation result.
	 * </p>
	 * @param operation The operation to execute (not null)
	 * @return A new {@link OperationProgressDialog} builder
	 */
	static OperationProgressDialogBuilder<Void> builder(Runnable operation) {
		ObjectUtils.argumentNotNull(operation, "Operation must be not null");
		return builder(callback -> {
			callback.onProgress(0, 0);
			operation.run();
			return null;
		});
	}

}
