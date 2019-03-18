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

import java.util.function.Consumer;

import com.holonplatform.artisan.vaadin.flow.components.TabLayout.Tab;
import com.holonplatform.artisan.vaadin.flow.components.TabLayout.TabContent;
import com.holonplatform.artisan.vaadin.flow.components.builders.TabsBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.TabsBuilder.TabBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.DefaultTab;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.vaadin.flow.component.Component;

/**
 * Default {@link TabBuilder} implementation.
 *
 * @since 1.0.0
 */
public class DefaultTabBuilder implements TabBuilder {

	private final AbstractTabsBuilder<?> parentBuilder;

	private final DefaultTab instance;

	/**
	 * Constructor.
	 * @param parentBuilder Parent builder (not null)
	 * @param content Tab content provider (not null)
	 */
	public DefaultTabBuilder(AbstractTabsBuilder<?> parentBuilder, TabContent content) {
		super();
		ObjectUtils.argumentNotNull(parentBuilder, "Parent builder must be not null");
		this.parentBuilder = parentBuilder;
		this.instance = parentBuilder.getInstance().addTab(content);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#width(java.lang.String)
	 */
	@Override
	public TabBuilder width(String width) {
		instance.setWidth(width);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#height(java.lang.String)
	 */
	@Override
	public TabBuilder height(String height) {
		instance.setHeight(height);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minWidth(java.lang.String)
	 */
	@Override
	public TabBuilder minWidth(String minWidth) {
		instance.setMinWidth(minWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxWidth(java.lang.String)
	 */
	@Override
	public TabBuilder maxWidth(String maxWidth) {
		instance.setMaxWidth(maxWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minHeight(java.lang.String)
	 */
	@Override
	public TabBuilder minHeight(String minHeight) {
		instance.setMinHeight(minHeight);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxHeight(java.lang.String)
	 */
	@Override
	public TabBuilder maxHeight(String maxHeight) {
		instance.setMaxHeight(maxHeight);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasEnabledConfigurator#enabled(boolean)
	 */
	@Override
	public TabBuilder enabled(boolean enabled) {
		instance.setEnabled(enabled);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasLabelConfigurator#label(com.holonplatform.core.i18n.
	 * Localizable)
	 */
	@Override
	public TabBuilder label(Localizable label) {
		instance.setLabel(label);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.builders.TabsBuilder.TabBuilder#withComponent(com.vaadin.flow.
	 * component.Component)
	 */
	@Override
	public TabBuilder withComponent(Component component) {
		instance.addTabComponent(component);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.TabLayoutBuilder.TabBuilder#visible(boolean)
	 */
	@Override
	public TabBuilder visible(boolean visible) {
		instance.setVisible(visible);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.TabsBuilder.TabBuilder#scrollable()
	 */
	@Override
	public TabBuilder scrollable() {
		instance.setScrollable(true);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.TabLayoutBuilder.TabBuilder#tabFlexGrow(double)
	 */
	@Override
	public TabBuilder tabFlexGrow(double flexGrow) {
		instance.setFlexGrow(flexGrow);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.builders.TabLayoutBuilder.TabBuilder#contentFlexGrow(double)
	 */
	@Override
	public TabBuilder flexGrow(double flexGrow) {
		instance.setContentFlexGrow(flexGrow);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.TabLayoutBuilder.TabBuilder#selected()
	 */
	@Override
	public TabBuilder selected() {
		parentBuilder.setSelectTab(instance);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.builders.TabsBuilder.TabBuilder#onTabSelected(java.util.function
	 * .Consumer)
	 */
	@Override
	public TabBuilder onTabSelected(Consumer<Tab> onTabSelected) {
		instance.setTabSelectedConsumer(onTabSelected);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.TabLayoutBuilder.TabBuilder#add()
	 */
	@Override
	public TabsBuilder add() {
		return parentBuilder;
	}

}
