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

import java.util.Map;
import java.util.function.Consumer;

import com.holonplatform.artisan.core.operation.Operation;
import com.holonplatform.artisan.vaadin.flow.components.OperationProgressDialog;
import com.holonplatform.artisan.vaadin.flow.components.builders.BaseOperationProgressDialogBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.DefaultOperationProgressDialog;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.vaadin.flow.components.builders.ButtonConfigurator.BaseButtonConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.DefaultHasSizeConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.DefaultHasStyleConfigurator;

/**
 * Abstract {@link BaseOperationProgressDialogBuilder} implementation.
 * 
 * @param <B> Concrete builder type
 * @param <T> Operation result type
 *
 * @since 1.0.0
 */
public abstract class AbstractOperationProgressDialogBuilder<T, B extends BaseOperationProgressDialogBuilder<T, B>>
		implements BaseOperationProgressDialogBuilder<T, B> {

	private final DefaultOperationProgressDialog<T> instance;

	private final DefaultHasStyleConfigurator styleConfigurator;
	private final DefaultHasSizeConfigurator sizeConfigurator;

	/**
	 * Constructor.
	 * @param operation The operation to execute (not null)
	 */
	public AbstractOperationProgressDialogBuilder(Operation<T> operation) {
		super();
		this.instance = new DefaultOperationProgressDialog<>(operation);
		this.styleConfigurator = new DefaultHasStyleConfigurator(this.instance);
		this.sizeConfigurator = new DefaultHasSizeConfigurator(this.instance);
	}

	protected abstract B getBuilder();

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.OperationProgressDialogConfigurator#id(java.lang.
	 * String)
	 */
	@Override
	public B id(String id) {
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
	public B withThemeName(String themeName) {
		this.instance.getElement().getThemeList().add(themeName);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.builders.OperationProgressDialogConfigurator#abortable(boolean)
	 */
	@Override
	public B abortable(boolean abortable) {
		this.instance.setAbortable(abortable);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.OperationProgressDialogConfigurator#
	 * abortButtonConfigurator(java.util.function.Consumer)
	 */
	@Override
	public B abortButtonConfigurator(Consumer<BaseButtonConfigurator> abortButtonConfigurator) {
		this.instance.setAbortConfigurator(abortButtonConfigurator);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.OperationProgressDialogConfigurator#
	 * withContextResourcesConfigurator(java.util.function.Consumer)
	 */
	@Override
	public B withContextResourcesConfigurator(Consumer<Map<String, Object>> contextResourcesConfigurator) {
		this.instance.addContextResourcesConfigurator(contextResourcesConfigurator);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#width(java.lang.String)
	 */
	@Override
	public B width(String width) {
		this.sizeConfigurator.width(width);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#height(java.lang.String)
	 */
	@Override
	public B height(String height) {
		this.sizeConfigurator.height(height);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleNames(java.lang.String[])
	 */
	@Override
	public B styleNames(String... styleNames) {
		this.styleConfigurator.styleNames(styleNames);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleName(java.lang.String)
	 */
	@Override
	public B styleName(String styleName) {
		this.styleConfigurator.styleName(styleName);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasTextConfigurator#text(com.holonplatform.core.i18n.
	 * Localizable)
	 */
	@Override
	public B text(Localizable text) {
		this.instance.setText(text);
		return getBuilder();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.BaseOperationProgressDialogBuilder#build()
	 */
	@Override
	public OperationProgressDialog<T> build() {
		return instance;
	}

}
