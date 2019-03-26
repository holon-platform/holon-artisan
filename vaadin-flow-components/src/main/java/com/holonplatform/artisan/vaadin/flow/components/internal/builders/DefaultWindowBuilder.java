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

import com.holonplatform.artisan.vaadin.flow.components.Window;
import com.holonplatform.artisan.vaadin.flow.components.builders.WindowBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.DefaultWindow;
import com.holonplatform.artisan.vaadin.flow.components.internal.WindowVariant;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.vaadin.flow.i18n.LocalizationProvider;
import com.holonplatform.vaadin.flow.internal.components.builders.DefaultComponentConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.DefaultHasSizeConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.DefaultHasStyleConfigurator;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.Element;

/**
 * Default {@link WindowBuilder} implementation.
 *
 * @since 1.0.0
 */
public class DefaultWindowBuilder implements WindowBuilder {

	private final DefaultWindow instance;

	private final DefaultComponentConfigurator componentConfigurator;
	private final DefaultHasStyleConfigurator styleConfigurator;
	private final DefaultHasSizeConfigurator sizeConfigurator;

	/**
	 * Default constructor
	 */
	public DefaultWindowBuilder() {
		super();
		this.instance = new DefaultWindow();
		this.componentConfigurator = new DefaultComponentConfigurator(this.instance.getComponent());
		this.styleConfigurator = new DefaultHasStyleConfigurator(this.instance);
		this.sizeConfigurator = new DefaultHasSizeConfigurator(this.instance);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#id(java.lang.String)
	 */
	@Override
	public WindowBuilder id(String id) {
		this.componentConfigurator.id(id);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#visible(boolean)
	 */
	@Override
	public WindowBuilder visible(boolean visible) {
		this.componentConfigurator.visible(visible);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withAttachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public WindowBuilder withAttachListener(ComponentEventListener<AttachEvent> listener) {
		this.componentConfigurator.withAttachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withDetachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public WindowBuilder withDetachListener(ComponentEventListener<DetachEvent> listener) {
		this.componentConfigurator.withDetachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withThemeName(java.lang.String)
	 */
	@Override
	public WindowBuilder withThemeName(String themeName) {
		this.componentConfigurator.withThemeName(themeName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener)
	 */
	@Override
	public WindowBuilder withEventListener(String eventType, DomEventListener listener) {
		this.componentConfigurator.withEventListener(eventType, listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener, java.lang.String)
	 */
	@Override
	public WindowBuilder withEventListener(String eventType, DomEventListener listener, String filter) {
		this.componentConfigurator.withEventListener(eventType, listener, filter);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#elementConfiguration(java.util.function.
	 * Consumer)
	 */
	@Override
	public WindowBuilder elementConfiguration(Consumer<Element> element) {
		this.componentConfigurator.elementConfiguration(element);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#width(java.lang.String)
	 */
	@Override
	public WindowBuilder width(String width) {
		this.sizeConfigurator.width(width);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#height(java.lang.String)
	 */
	@Override
	public WindowBuilder height(String height) {
		this.sizeConfigurator.height(height);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minWidth(java.lang.String)
	 */
	@Override
	public WindowBuilder minWidth(String minWidth) {
		this.sizeConfigurator.minWidth(minWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxWidth(java.lang.String)
	 */
	@Override
	public WindowBuilder maxWidth(String maxWidth) {
		this.sizeConfigurator.maxWidth(maxWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minHeight(java.lang.String)
	 */
	@Override
	public WindowBuilder minHeight(String minHeight) {
		this.sizeConfigurator.minHeight(minHeight);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxHeight(java.lang.String)
	 */
	@Override
	public WindowBuilder maxHeight(String maxHeight) {
		this.sizeConfigurator.maxHeight(maxHeight);
		return this;
	}

	@Override
	public WindowBuilder fullSize() {
		return withThemeVariants(WindowVariant.FULL_WIDTH, WindowVariant.FULL_HEIGHT);
	}

	@Override
	public WindowBuilder fullWidth() {
		return withThemeVariants(WindowVariant.FULL_WIDTH);
	}

	@Override
	public WindowBuilder fullHeight() {
		return withThemeVariants(WindowVariant.FULL_HEIGHT);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleNames(java.lang.String[])
	 */
	@Override
	public WindowBuilder styleNames(String... styleNames) {
		this.styleConfigurator.styleNames(styleNames);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleName(java.lang.String)
	 */
	@Override
	public WindowBuilder styleName(String styleName) {
		this.styleConfigurator.styleName(styleName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.WindowBuilder#title(com.holonplatform.core.i18n.
	 * Localizable)
	 */
	@Override
	public WindowBuilder title(Localizable title) {
		ObjectUtils.argumentNotNull(title, "Localizable title must be not null");
		this.instance.setTitle(LocalizationProvider.localize(title).orElse(null));
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.WindowBuilder#content(com.vaadin.flow.component.
	 * Component)
	 */
	@Override
	public WindowBuilder content(Component content) {
		ObjectUtils.argumentNotNull(content, "Window content must be not null");
		this.instance.setContent(content);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.WindowBuilder#closable(boolean)
	 */
	@Override
	public WindowBuilder closable(boolean closable) {
		this.instance.setClosable(closable);
		this.instance.setCloseOnEsc(closable);
		this.instance.setCloseOnOutsideClick(closable);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.WindowBuilder#resizable(boolean)
	 */
	@Override
	public WindowBuilder resizable(boolean resizable) {
		this.instance.setResizable(resizable);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.WindowBuilder#withHeaderComponent(com.vaadin.flow.
	 * component.Component)
	 */
	@Override
	public WindowBuilder withHeaderComponent(Component component) {
		ObjectUtils.argumentNotNull(component, "Window header content must be not null");
		this.instance.addHeaderComponent(component);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.WindowBuilder#withFooterComponent(com.vaadin.flow.
	 * component.Component)
	 */
	@Override
	public WindowBuilder withFooterComponent(Component component) {
		ObjectUtils.argumentNotNull(component, "Window footer content must be not null");
		this.instance.addFooterComponent(component);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.WindowBuilder#closeOnEsc(boolean)
	 */
	@Override
	public WindowBuilder closeOnEsc(boolean closeOnEsc) {
		this.instance.setCloseOnEsc(closeOnEsc);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.WindowBuilder#closeOnOutsideClick(boolean)
	 */
	@Override
	public WindowBuilder closeOnOutsideClick(boolean closeOnOutsideClick) {
		this.instance.setCloseOnOutsideClick(closeOnOutsideClick);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.WindowBuilder#build()
	 */
	@Override
	public Window build() {
		return this.instance;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.WindowBuilder#withThemeVariants(com.holonplatform.
	 * artisan.vaadin.flow.components.internal.WindowVariant[])
	 */
	@Override
	public WindowBuilder withThemeVariants(WindowVariant... variants) {
		this.instance.addThemeVariants(variants);
		return this;
	}

}
