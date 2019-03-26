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
package com.holonplatform.artisan.vaadin.flow.components.internal.builders;

import java.util.Map;
import java.util.function.Consumer;

import com.holonplatform.artisan.core.exceptions.InterruptedOperationException;
import com.holonplatform.artisan.core.exceptions.OperationExecutionException;
import com.holonplatform.artisan.core.operation.Operation;
import com.holonplatform.artisan.vaadin.flow.components.builders.OperationProgressDialogConfigurator;
import com.holonplatform.artisan.vaadin.flow.components.internal.DefaultOperationProgressDialog;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.vaadin.flow.components.builders.ButtonConfigurator.BaseButtonConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.DefaultHasSizeConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.DefaultHasStyleConfigurator;

/**
 * Abstract {@link OperationProgressDialogConfigurator} implementation.
 * 
 * @param <C> Concrete configurator type
 * @param <T> Operation result type
 *
 * @since 1.0.0
 */
public abstract class AbstractOperationProgressDialogConfigurator<T, C extends OperationProgressDialogConfigurator<T, C>>
		implements OperationProgressDialogConfigurator<T, C> {

	protected final DefaultOperationProgressDialog<T> instance;

	private final DefaultHasStyleConfigurator styleConfigurator;
	private final DefaultHasSizeConfigurator sizeConfigurator;

	/**
	 * Constructor.
	 * @param operation The operation to execute (not null)
	 */
	public AbstractOperationProgressDialogConfigurator(Operation<T> operation) {
		super();
		this.instance = new DefaultOperationProgressDialog<>(operation);
		this.styleConfigurator = new DefaultHasStyleConfigurator(this.instance);
		this.sizeConfigurator = new DefaultHasSizeConfigurator(this.instance);
	}

	protected abstract C getBuilder();

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.OperationProgressDialogConfigurator#id(java.lang.
	 * String)
	 */
	@Override
	public C id(String id) {
		this.instance.setId(id);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.builders.OperationProgressDialogConfigurator#withThemeName(java.
	 * lang.String)
	 */
	@Override
	public C withThemeName(String themeName) {
		this.instance.getElement().getThemeList().add(themeName);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.builders.OperationProgressDialogConfigurator#abortable(boolean)
	 */
	@Override
	public C abortable(boolean abortable) {
		this.instance.setAbortable(abortable);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.OperationProgressDialogConfigurator#
	 * abortButtonConfigurator(java.util.function.Consumer)
	 */
	@Override
	public C abortButtonConfigurator(Consumer<BaseButtonConfigurator> abortButtonConfigurator) {
		this.instance.setAbortConfigurator(abortButtonConfigurator);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.OperationProgressDialogConfigurator#
	 * operationExecutionExceptionHandler(java.util.function.Consumer)
	 */
	@Override
	public C operationExecutionExceptionHandler(
			Consumer<OperationExecutionException> operationExecutionExceptionHandler) {
		this.instance.setOperationExecutionExceptionHandler(operationExecutionExceptionHandler);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.OperationProgressDialogConfigurator#
	 * interruptedOperationExceptionHandler(java.util.function.Consumer)
	 */
	@Override
	public C interruptedOperationExceptionHandler(
			Consumer<InterruptedOperationException> interruptedOperationExceptionHandler) {
		this.instance.setInterruptedOperationExceptionHandler(interruptedOperationExceptionHandler);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.OperationProgressDialogConfigurator#
	 * withContextResourcesConfigurator(java.util.function.Consumer)
	 */
	@Override
	public C withContextResourcesConfigurator(Consumer<Map<String, Object>> contextResourcesConfigurator) {
		this.instance.addContextResourcesConfigurator(contextResourcesConfigurator);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#width(java.lang.String)
	 */
	@Override
	public C width(String width) {
		this.sizeConfigurator.width(width);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#height(java.lang.String)
	 */
	@Override
	public C height(String height) {
		this.sizeConfigurator.height(height);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minWidth(java.lang.String)
	 */
	@Override
	public C minWidth(String minWidth) {
		this.sizeConfigurator.minWidth(minWidth);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxWidth(java.lang.String)
	 */
	@Override
	public C maxWidth(String maxWidth) {
		this.sizeConfigurator.maxWidth(maxWidth);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minHeight(java.lang.String)
	 */
	@Override
	public C minHeight(String minHeight) {
		this.sizeConfigurator.minHeight(minHeight);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxHeight(java.lang.String)
	 */
	@Override
	public C maxHeight(String maxHeight) {
		this.sizeConfigurator.maxHeight(maxHeight);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleNames(java.lang.String[])
	 */
	@Override
	public C styleNames(String... styleNames) {
		this.styleConfigurator.styleNames(styleNames);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleName(java.lang.String)
	 */
	@Override
	public C styleName(String styleName) {
		this.styleConfigurator.styleName(styleName);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasTextConfigurator#text(com.holonplatform.core.i18n.
	 * Localizable)
	 */
	@Override
	public C text(Localizable text) {
		this.instance.setText(text);
		return getBuilder();
	}

}
