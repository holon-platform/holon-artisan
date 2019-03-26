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

import java.util.function.Consumer;

import com.holonplatform.artisan.vaadin.flow.components.builders.OperatorInputFilterConfigurator.FilterOperatorSelectConfigurator;
import com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.DefaultComponentConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.DefaultHasSizeConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.DefaultHasStyleConfigurator;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.Element;

/**
 * Default {@link FilterOperatorSelectConfigurator} implementation.
 *
 * @since 1.0.0
 */
public class DefaultFilterOperatorSelectConfigurator implements FilterOperatorSelectConfigurator {

	private final ComponentConfigurator<?> componentConfigurator;
	private final HasSizeConfigurator<?> sizeConfigurator;
	private final HasStyleConfigurator<?> styleConfigurator;

	public DefaultFilterOperatorSelectConfigurator(Select<?> select) {
		super();
		this.componentConfigurator = new DefaultComponentConfigurator(select);
		this.sizeConfigurator = new DefaultHasSizeConfigurator(select);
		this.styleConfigurator = new DefaultHasStyleConfigurator(select);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#id(java.lang.String)
	 */
	@Override
	public FilterOperatorSelectConfigurator id(String id) {
		componentConfigurator.id(id);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#visible(boolean)
	 */
	@Override
	public FilterOperatorSelectConfigurator visible(boolean visible) {
		componentConfigurator.visible(visible);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#elementConfiguration(java.util.function.
	 * Consumer)
	 */
	@Override
	public FilterOperatorSelectConfigurator elementConfiguration(Consumer<Element> element) {
		componentConfigurator.elementConfiguration(element);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withAttachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public FilterOperatorSelectConfigurator withAttachListener(ComponentEventListener<AttachEvent> listener) {
		componentConfigurator.withAttachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withDetachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public FilterOperatorSelectConfigurator withDetachListener(ComponentEventListener<DetachEvent> listener) {
		componentConfigurator.withDetachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withThemeName(java.lang.String)
	 */
	@Override
	public FilterOperatorSelectConfigurator withThemeName(String themeName) {
		componentConfigurator.withThemeName(themeName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener)
	 */
	@Override
	public FilterOperatorSelectConfigurator withEventListener(String eventType, DomEventListener listener) {
		componentConfigurator.withEventListener(eventType, listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener, java.lang.String)
	 */
	@Override
	public FilterOperatorSelectConfigurator withEventListener(String eventType, DomEventListener listener,
			String filter) {
		componentConfigurator.withEventListener(eventType, listener, filter);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#width(java.lang.String)
	 */
	@Override
	public FilterOperatorSelectConfigurator width(String width) {
		sizeConfigurator.width(width);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#height(java.lang.String)
	 */
	@Override
	public FilterOperatorSelectConfigurator height(String height) {
		sizeConfigurator.height(height);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minWidth(java.lang.String)
	 */
	@Override
	public FilterOperatorSelectConfigurator minWidth(String minWidth) {
		sizeConfigurator.minWidth(minWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxWidth(java.lang.String)
	 */
	@Override
	public FilterOperatorSelectConfigurator maxWidth(String maxWidth) {
		sizeConfigurator.maxWidth(maxWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minHeight(java.lang.String)
	 */
	@Override
	public FilterOperatorSelectConfigurator minHeight(String minHeight) {
		sizeConfigurator.minHeight(minHeight);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxHeight(java.lang.String)
	 */
	@Override
	public FilterOperatorSelectConfigurator maxHeight(String maxHeight) {
		sizeConfigurator.maxHeight(maxHeight);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleNames(java.lang.String[])
	 */
	@Override
	public FilterOperatorSelectConfigurator styleNames(String... styleNames) {
		styleConfigurator.styleNames(styleNames);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleName(java.lang.String)
	 */
	@Override
	public FilterOperatorSelectConfigurator styleName(String styleName) {
		styleConfigurator.styleName(styleName);
		return this;
	}

}
