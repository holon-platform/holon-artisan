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

/**
 * Callback interface for operation progress steps notification.
 *
 * @since 1.0.0
 */
@FunctionalInterface
public interface OperationProgressCallback {

	/**
	 * Invoked when the operation progress changes.
	 * @param totalSteps Total steps count
	 * @param completedSteps Completed steps count
	 * @return The operation progress outcome: {@link OperationProgress#PROCEED} to proceed with the operation or
	 *         {@link OperationProgress#ABORT} to abort it, if supported
	 */
	OperationProgress onProgress(int totalSteps, int completedSteps);

}
