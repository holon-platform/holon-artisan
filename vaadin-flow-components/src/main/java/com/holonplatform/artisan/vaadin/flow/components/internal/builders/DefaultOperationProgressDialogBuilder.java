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

import com.holonplatform.artisan.core.operation.Operation;
import com.holonplatform.artisan.vaadin.flow.components.OperationProgressDialog;
import com.holonplatform.artisan.vaadin.flow.components.OperationProgressDialog.OperationProgressDialogBuilder;

/**
 * Default {@link OperationProgressDialog} builder.
 * 
 * @param <T> Operation result type
 *
 * @since 1.0.0
 */
public class DefaultOperationProgressDialogBuilder<T>
		extends AbstractOperationProgressDialogBuilder<T, OperationProgressDialogBuilder<T>>
		implements OperationProgressDialogBuilder<T> {

	/**
	 * Constructor.
	 * @param operation The operation to execute (not null)
	 */
	public DefaultOperationProgressDialogBuilder(Operation<T> operation) {
		super(operation);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.internal.builders.AbstractOperationProgressDialogBuilder#
	 * getBuilder()
	 */
	@Override
	protected DefaultOperationProgressDialogBuilder<T> getBuilder() {
		return this;
	}

}
