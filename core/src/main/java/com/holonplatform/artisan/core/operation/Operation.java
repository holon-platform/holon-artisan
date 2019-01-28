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
package com.holonplatform.artisan.core.operation;

import com.holonplatform.artisan.core.exceptions.InterruptedOperationException;

/**
 * Function which can be used to execute an operation and return the operation result, using a
 * {@link OperationProgressCallback} to notify the operation progress and handle operation interruption.
 * 
 * @param <T> The operation result type
 * 
 * @since 1.0.0
 */
@FunctionalInterface
public interface Operation<T> {

	/**
	 * Execute the operation.
	 * @param callback The callback which can be used to notify the operation progress
	 * @return The operation result
	 * @throws InterruptedOperationException If the operation was interrupted
	 * @throws Exception If an error occurred
	 */
	T execute(OperationProgressCallback callback) throws InterruptedOperationException, Exception;

}
